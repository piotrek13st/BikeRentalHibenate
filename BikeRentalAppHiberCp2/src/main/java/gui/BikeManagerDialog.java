package gui;

import gui.tablemodels.BikeTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import logic.BikeDBManager;
import logic.RecordExistsException;
import logic.RecordNotFoundException;
import model.thing.Bike;
import model.thing.MTBike;
import model.thing.MountainBike;
import model.thing.Thing;
import model.thing.TraditionalBike;

import org.hibernate.SessionFactory;

public class BikeManagerDialog extends JDialog {

	private JPanel contentPane;
	private JTable table;
	private ListSelectionModel rowSM;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnEdit;
	private AddBikesDialog addDialog;

	private SessionFactory factory;
	private List<Thing> list;

	private static JDialog frame;
	private JButton btnAppend;

	/**
	 * Create the frame.
	 * 
	 * @param cl
	 *            - identify whether client or bike
	 */
	public BikeManagerDialog(JFrame parent, SessionFactory factory) {
		super(parent, true);
		this.factory = factory;
		setTitle("Bike Management");
		initComponents();
		createEvents();
	}

	/**
	 * 
	 * @param parent
	 * @param cl
	 *            - identify whether Client or Bikes
	 * @wbp.parser.constructor
	 */
	public BikeManagerDialog(JDialog parent, SessionFactory factory) {
		super(parent, true);
		this.factory = factory;
		setTitle("Bike Management");
		initComponents();
		createEvents();
	}

	private void initComponents() {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		btnAdd = new JButton("Add");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnAdd, 25,
				SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnAdd, 20,
				SpringLayout.WEST, contentPane);
		contentPane.add(btnAdd);

		btnDelete = new JButton("Delete");
		sl_contentPane.putConstraint(SpringLayout.EAST, btnAdd, -11,
				SpringLayout.WEST, btnDelete);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnDelete, 0,
				SpringLayout.NORTH, btnAdd);
		contentPane.add(btnDelete);

		btnEdit = new JButton("Edit");
		sl_contentPane.putConstraint(SpringLayout.EAST, btnDelete, -6,
				SpringLayout.WEST, btnEdit);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnEdit, 165,
				SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnEdit, -191,
				SpringLayout.EAST, contentPane);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnEdit);

		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnEdit, -18,
				SpringLayout.NORTH, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 18,
				SpringLayout.SOUTH, btnAdd);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 0,
				SpringLayout.WEST, btnAdd);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -34,
				SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -10,
				SpringLayout.EAST, contentPane);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);

		// DbContacts db = new DbContacts();

		table = new JTable();

		scrollPane.setViewportView(table);

		btnAppend = new JButton("Append");
		btnAppend.setVisible(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnAppend, 6,
				SpringLayout.SOUTH, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnAppend, 0,
				SpringLayout.EAST, scrollPane);
		contentPane.add(btnAppend);

	}

	private void createEvents() {
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Bike con = null;

				if (addDialog == null || !addDialog.isFocused()) {
					addDialog = new AddBikesDialog(frame);

					con = addDialog.showDialog(con);

					if (con != null) {
						BikeTableModel tmodel = (BikeTableModel) table
								.getModel();

						try {
							tmodel.addRow(con);
						} catch (RecordExistsException e1) {

							e1.printStackTrace();
						}

					}

				}
			}
		});

		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int[] rows = table.getSelectedRows();
				if (rows.length == 0)
					return;
				Bike con = null;
				try {
					con = ((BikeTableModel) table.getModel()).getRow(rows[0]);
				} catch (RecordNotFoundException e2) {
					e2.printStackTrace();
				}

				if (addDialog == null) {
					addDialog = new AddBikesDialog(frame);
				}

				con = addDialog.showDialog(con);
				if ((con) != null) {
					BikeTableModel tmodel = (BikeTableModel) table.getModel();
					try {
						tmodel.modifyRow(con, rows[0]);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();
				BikeTableModel tm = (BikeTableModel) table.getModel();
				for (int i = rows.length - 1; i >= 0; --i) {
					tm.removeRow(rows[i]);
				}
			}
		});
	}

	public void showDialog() {
		try {
			table.setModel(new BikeTableModel(new BikeDBManager(factory), null,
					null));
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
		setVisible(true);
	}

	/**
	 * 
	 * @param option
	 *            0-all bikes, 1-only traditionals, 2-mountain, 3-mtbike
	 * @return
	 */
	public List<Thing> showDialogForBikes(int option, Date from, Date to) {
		btnAppend.setVisible(true);
		list = null;

		try {
			if (from == null || to == null) {
				switch (option) {
				case 0:
					table.setModel(new BikeTableModel(
							new BikeDBManager(factory), null, true));
					break;
				case 1:
					table.setModel(new BikeTableModel(
							new BikeDBManager(factory), TraditionalBike.class,
							true));
					break;
				case 2:
					table.setModel(new BikeTableModel(
							new BikeDBManager(factory), MountainBike.class,
							true));
					break;
				case 3:
					table.setModel(new BikeTableModel(
							new BikeDBManager(factory), MTBike.class, true));
					break;
				default:
					return null;
				}
			} else {
				switch (option) {
				case 0:
					table.setModel(new BikeTableModel(
							new BikeDBManager(factory), null, true, from, to));
					break;
				case 1:
					table.setModel(new BikeTableModel(
							new BikeDBManager(factory), TraditionalBike.class,
							true, from, to));
					break;
				case 2:
					table.setModel(new BikeTableModel(
							new BikeDBManager(factory), MountainBike.class,
							true, from, to));
					break;
				case 3:
					table.setModel(new BikeTableModel(
							new BikeDBManager(factory), MTBike.class, true,
							from, to));
					break;
				default:
					return null;
				}
			}
		} catch (RecordNotFoundException e2) {
			e2.printStackTrace();
		}

		btnAppend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();
				if (rows.length > 0) {
					BikeTableModel tm = (BikeTableModel) table.getModel();

					list = new LinkedList<>();
					for (int i = rows.length - 1; i >= 0; --i) {
						try {
							list.add(tm.getRow(rows[i]));
						} catch (RecordNotFoundException e1) {
							e1.printStackTrace();
						}

					}
				}
				BikeManagerDialog.this.setVisible(false);
			}

		});

		setVisible(true);
		return list;

	}
}

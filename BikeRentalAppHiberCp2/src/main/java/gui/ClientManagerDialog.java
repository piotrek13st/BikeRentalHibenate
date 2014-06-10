package gui;

import gui.tablemodels.ClientTableModel;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.hibernate.SessionFactory;

import db.util.HibernateUtil;
import logic.ClientDBManager;
import logic.RecordExistsException;
import logic.RecordNotFoundException;
import model.client.Client;



public class ClientManagerDialog extends JDialog {

	private JPanel contentPane;
	private JTable table;
    private ListSelectionModel rowSM;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnEdit;
    private AddClientDialog addDialog;
    
    SessionFactory factory;
    Client cl=null;
    
    private static JDialog frame;
    
	public ClientManagerDialog(SessionFactory factory) {
		super();
		//super(getParent(),true);
		this.factory=factory;
		setTitle("Management");
		initComponents();
		createEvents();
	}

	/**
	 * Create the frame.
	 * @param cl - identify whether client or bike
	 */
    public ClientManagerDialog(JFrame parent, SessionFactory factory) {
		super(parent,true);
		this.factory=factory;
		setTitle("Management");
		initComponents();
		createEvents();
	}
	
	/**
	 * 
	 * @param parent
	 * @param cl	- identify whether Client or Bikes
	 * @wbp.parser.constructor
	 */
	public ClientManagerDialog(JDialog parent, SessionFactory factory) {
		super(parent,true);
		this.factory=factory;
		setTitle("Management");
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
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnAdd, 25, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnAdd, 20, SpringLayout.WEST, contentPane);
		contentPane.add(btnAdd);
		
		btnDelete = new JButton("Delete");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnDelete, -335, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnAdd, -11, SpringLayout.WEST, btnDelete);
		contentPane.add(btnDelete);
		
		btnEdit = new JButton("Edit");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnEdit, 165, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnEdit, -191, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnDelete, -6, SpringLayout.WEST, btnEdit);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnEdit);
		
		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnEdit, -18, SpringLayout.NORTH, scrollPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 18, SpringLayout.SOUTH, btnAdd);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, btnAdd);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -34, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, contentPane);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);
		
//		DbContacts db = new DbContacts();
		
		

		try {
			table = new JTable(new ClientTableModel(new ClientDBManager(factory)));
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	
		scrollPane.setViewportView(table);
		
	}
		
		private void createEvents() {
			btnAdd.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {								
					Client con=null;
					
					if(addDialog == null || !addDialog.isFocused()) {
						addDialog = new AddClientDialog(frame);
						
						con=addDialog.showDialog(con);
										
					    if(con!=null) {
					    	ClientTableModel tmodel = (ClientTableModel)table.getModel();
					    	
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
					
					int [] rows=table.getSelectedRows();
					if(rows.length==0) 
						return;
					Client con=null;
						try {
							con = ((ClientTableModel)table.getModel()).getRow(rows[0]);
						} catch (RecordNotFoundException e2) {
							e2.printStackTrace();
						}
				
					
					if(addDialog == null) {
						addDialog = new AddClientDialog(frame);
					} 
						
						con=addDialog.showDialog(con);
					    if((con)!=null) {
					    	ClientTableModel tmodel = (ClientTableModel)table.getModel();
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
					int [] rows=table.getSelectedRows();
					ClientTableModel tm=(ClientTableModel)table.getModel();
					for(int i=rows.length-1; i>=0; --i) {
						tm.removeRow(rows[i]);
					}
				}
			});
		}
		
		public void showDialog() {    
			setVisible(true);	
		}
		
		public Client showDialogForClient() {
			
			
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					int [] rows=table.getSelectedRows();
					if(rows.length==0) 
						return;
						try {
							cl = ((ClientTableModel)table.getModel()).getRow(rows[0]);
						} catch (RecordNotFoundException e2) {
							e2.printStackTrace();
						}
						
						setVisible(false);
				}
			});
			
			setVisible(true);
					
			return cl;
			
		}
		
}

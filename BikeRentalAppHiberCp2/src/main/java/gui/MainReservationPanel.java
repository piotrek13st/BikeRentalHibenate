package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import model.thing.Bike;

import org.hibernate.SessionFactory;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import db.util.HibernateUtil;

public class MainReservationPanel extends JFrame implements ActionListener {

	private JPanel contentPane;
	
	private SessionFactory factory;
	private JButton btnBikeMgm;
	private JButton btnClientManagements;
	private static MainReservationPanel frame;
	private JDialog mgntBikeDialog=null;
	private JDialog mgntClientDialog=null;
	private JDialog mgntOrderDialog=null;
	private JButton btnOrdersManagement;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					frame = new MainReservationPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainReservationPanel() {
		setResizable(false);
		initComponents();
		createEvents();
		
	}
	
	private void initComponents() {
		factory=HibernateUtil.getSessionFactory();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 170, 143);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		btnBikeMgm = new JButton("Bike Menagement");
		contentPane.add(btnBikeMgm, "2, 2");
		
		btnClientManagements = new JButton("Client Management");
		contentPane.add(btnClientManagements, "2, 4");
		
		btnOrdersManagement = new JButton("Orders Management");
		contentPane.add(btnOrdersManagement, "2, 6");
	}
	
	private void createEvents() {
		btnBikeMgm.addActionListener(this);
		btnClientManagements.addActionListener(this);
		btnOrdersManagement.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source==btnClientManagements) {
			if(mgntClientDialog==null) {
				mgntClientDialog=new ClientManagerDialog(frame, factory);
			}
			((ClientManagerDialog)mgntClientDialog).showDialog();
		} else if(source == btnBikeMgm) {
			if(mgntBikeDialog==null) {
				mgntBikeDialog=new BikeManagerDialog(frame, factory);
			}
			((BikeManagerDialog)mgntBikeDialog).showDialog();
		} else if(source == btnOrdersManagement) {
			if(mgntOrderDialog==null) {
				mgntOrderDialog=new OrderManagerDialog(frame, factory);
			}
			((OrderManagerDialog)mgntOrderDialog).showDialog();
		}
		
	}

}

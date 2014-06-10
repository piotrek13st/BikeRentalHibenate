package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.transaction.Transactional.TxType;

import org.hibernate.SessionFactory;

import logic.RecordExistsException;
import model.client.Client;
import model.date.DatePair;
import model.date.EndBeforStartException;
import model.order.Order;
import model.order.Order.Status;
import model.thing.Bike;
import model.thing.Thing;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.event.MouseAdapter;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import java.util.Calendar;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;



public class AddOrderDialog extends JDialog implements ActionListener {
		
	private boolean okPressed;
	private JButton btnConfirm;
	private JButton btnCancel;
	
	private Client client;
	private List<Thing> thingList;
	
	ClientManagerDialog clientDialog=null;
	

	private JPanel panel;
	private JLabel lblClient;
	private JTextField textClient;
	private JLabel lblDateMaded;
	private JLabel lblStatus;
	private JComboBox cboxStatus;
	private JLabel lblFrom;
	private JLabel lblToTime;
	private JLabel lblPrizehour;
	private JTextField textPrice;
	private JLabel lblTime;
	private JTextField textTotalTime;
	private JLabel lblTotalPrice;
	private JTextField textTotalPrice;
	private JLabel lblNumOfBikes;
	private JTextField textNumOfBikes;
	private JButton btnAddBike;
	private JButton btnCalculate;
	
	private SessionFactory factory; 
	private JSpinner spinnerFromDate;
	private JSpinner spinnerToDate;
	private JSpinner spinnerMadedDate;
	private JComboBox cboxBikeTypes;
	/**
	 * Create the dialog.
	 */
	public AddOrderDialog(JDialog parent, SessionFactory factory) {
		super(parent,true);
		this.factory=factory;
		setResizable(false);
		initComponents();
		createEvents();
		
	}
	
	
	

	
	
	private void initComponents() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 296, 364);
		getContentPane().setLayout(new BorderLayout());
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnConfirm = new JButton("Confirm");
				buttonPane.add(btnConfirm);
				
			}
			{
				btnCancel = new JButton("Cancel");
				buttonPane.add(btnCancel);
			}
		}
		{
			panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new FormLayout(new ColumnSpec[] {
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,}));
			{
				lblClient = new JLabel("Client");
				lblClient.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel.add(lblClient, "2, 2");
			}
			{
				textClient = new JTextField();
				
				panel.add(textClient, "5, 2, 4, 1, fill, default");
				textClient.setColumns(10);
				textClient.setFocusable(false);
			}
			{
				lblDateMaded = new JLabel("Date maded");
				lblDateMaded.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel.add(lblDateMaded, "2, 4");
			}
			{
				spinnerMadedDate = new JSpinner();
				spinnerMadedDate.setEnabled(false);
				panel.add(spinnerMadedDate, "5, 4, 4, 1");
			}
			{
				lblStatus = new JLabel("Status");
				lblStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel.add(lblStatus, "2, 6");
			}
			{
				cboxStatus = new JComboBox(new String[]{"CREATING ORDER",
						"CONFIRMED",
						"CANCELED",
						"IN REALISATION",
						"ENDED"});
				panel.add(cboxStatus, "5, 6, 4, 1, fill, default");
			}
			{
				lblFrom = new JLabel("From");
				lblFrom.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel.add(lblFrom, "2, 8");
			}
			{
				spinnerFromDate = new JSpinner();

				panel.add(spinnerFromDate, "5, 8, 4, 1");
			}
			{
				lblToTime = new JLabel("To");
				lblToTime.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel.add(lblToTime, "2, 10, left, bottom");
			}
			{
				spinnerToDate = new JSpinner();
				
				panel.add(spinnerToDate, "5, 10, 4, 1");
			}
			{
				lblNumOfBikes = new JLabel("Number of Bikes");
				lblNumOfBikes.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel.add(lblNumOfBikes, "2, 12");
			}
			{
				textNumOfBikes = new JTextField();
				panel.add(textNumOfBikes, "5, 12, 4, 1, fill, default");
				textNumOfBikes.setColumns(10);
				textNumOfBikes.setEditable(false);
			}
			{
				lblPrizehour = new JLabel("Price/hour");
				lblPrizehour.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel.add(lblPrizehour, "2, 14");
			}
			{
				textPrice = new JTextField();
				panel.add(textPrice, "5, 14, 4, 1, fill, default");
				textPrice.setColumns(10);
			}
			{
				lblTime = new JLabel("Total Time ");
				lblTime.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel.add(lblTime, "2, 16");
			}
			{
				textTotalTime = new JTextField();
				textTotalTime.setColumns(10);
				panel.add(textTotalTime, "5, 16, 4, 1, fill, default");
			}
			{
				lblTotalPrice = new JLabel("Total Price");
				lblTotalPrice.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel.add(lblTotalPrice, "2, 18");
			}
			{
				textTotalPrice = new JTextField();
				textTotalPrice.setColumns(10);
				panel.add(textTotalPrice, "5, 18, 4, 1, fill, default");
			}
			{
				btnAddBike = new JButton("Add bike");
				btnAddBike.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				panel.add(btnAddBike, "2, 20, 3, 1");
			}
			{
				cboxBikeTypes = new JComboBox(new String[]{"All", "Traditional", "Mountain", "MTBike"});
				panel.add(cboxBikeTypes, "5, 20, 4, 1, fill, default");
			}
			{
				btnCalculate = new JButton("Calculate");
				panel.add(btnCalculate, "2, 22, 3, 1");
			}
			
		}
	}
	
	private void createEvents() {
		btnConfirm.addActionListener(this);
		btnCancel.addActionListener(this);
		btnAddBike.addActionListener(this);
		cboxStatus.addActionListener(this);

		textClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ClientManagerDialog x = new ClientManagerDialog(AddOrderDialog.this, factory);
				client=x.showDialogForClient();
				
				if(client!=null) {
					textClient.setText(client.getClientName());
				}
			
			}
		});
		
		spinnerToDate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Long time=(((Date)spinnerToDate.getValue()).getTime()-((Date)spinnerFromDate.getValue()).getTime())/1000/60;
				textTotalTime.setText(""+ time/60 + "h " +time%60 +"min");
			}
		});
		
		spinnerFromDate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Long time=(((Date)spinnerToDate.getValue()).getTime()-((Date)spinnerFromDate.getValue()).getTime())/1000/60+1;
				textTotalTime.setText(""+ time/60 + "h " +time%60 +"min");
			}
		});
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		    if (source == btnConfirm) {
		      okPressed = true;
		      setVisible(false);
		    } else if (source == btnCancel){
		    	setVisible(false);
		    } else if (source == cboxStatus || source==btnCalculate){
		    	showCalculation();
		    } else if (source == btnAddBike) {
		    	BikeManagerDialog bikeDialog = new BikeManagerDialog(this, factory);
		    	thingList.addAll(bikeDialog.showDialogForBikes(cboxBikeTypes.getSelectedIndex(), 
		    			(Date)spinnerFromDate.getValue(), (Date)spinnerToDate.getValue()));
		    	textNumOfBikes.setText(""+thingList.size());
		    	
		    } else if(source == spinnerFromDate || source== spinnerToDate) {
		    	textTotalTime.setText(""+(((Date)spinnerToDate.getValue()).getTime()-((Date)spinnerFromDate.getValue()).getTime()));
		    }
		    
		    
	}
		    
		      
	public Order showDialog(Order con) {
		thingList=null;
		client=null;
		
		if(con!=null) {
			
			textClient.setText(con.getClient().getClientName());
			
			Date d1=con.getDatePair().getStartAtDate();
			Date d2=con.getDatePair().getEndAtDate();
			
			spinnerMadedDate.setModel(new SpinnerDateModel(con.getMadedOrderDate(),null,null, Calendar.ALL_STYLES));
			spinnerFromDate.setModel(new SpinnerDateModel(d1, null, null, Calendar.ALL_STYLES));
			spinnerToDate.setModel(new SpinnerDateModel(d2, null, null, Calendar.ALL_STYLES));
		
			
			Status status=con.getStatus();
			if(status==Status.CREATING_ORDER) {
				cboxStatus.setSelectedIndex(0);
			} else if (status==Status.CONFIRMED) {
				cboxStatus.setSelectedIndex(1);
			} else if (status==Status.CANCELED) {
				cboxStatus.setSelectedIndex(2); 
			} else if (status==Status.IN_REALISATION) {
				cboxStatus.setSelectedIndex(3);
			} else if (status==Status.ENDED) {
				cboxStatus.setSelectedIndex(4);
			}
			
			
			textPrice.setText(""+con.calculatePrice());
			
			textTotalTime.setText((d2.getTime()-d1.getTime())/1000+ " h");
			thingList=con.getThingList();
			if(thingList!=null)
				textNumOfBikes.setText(""+thingList.size());
			else 
				textNumOfBikes.setText("0");
			
			client=con.getClient();
		} else{
			thingList=new LinkedList<>();
			spinnerToDate.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.ALL_STYLES));
			spinnerFromDate.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.ALL_STYLES));
			spinnerMadedDate.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.ALL_STYLES));
			
		}
	    okPressed = false;
	    setVisible(true);
	    if (okPressed) {
	    	Order ord=new Order();
	    	
	    	ord.setClient(client);
	    	ord.setMadedOrderDate((Date)spinnerMadedDate.getValue());
	    	DatePair dp=null;
	    	try {
				dp=new DatePair((Date)spinnerFromDate.getValue(), (Date)spinnerToDate.getValue() ) ;
			} catch (EndBeforStartException e) {
				e.printStackTrace();
			}
	    	ord.setDatePair(dp);
	    	ord.setPrice(0f);
	    	//ord.setPrice(Float.parseFloat(textTotalPrice.getText()));
	    	
	    	int index=cboxStatus.getSelectedIndex();
	    	if(index==0) {
	    		ord.setStatus(Status.CREATING_ORDER);
	    	} else if (index==1) {
	    		ord.setStatus(Status.CONFIRMED);
	    	} else if (index==2) {
	    		ord.setStatus(Status.CANCELED);
	    	} else if (index==3) {
	    		ord.setStatus(Status.IN_REALISATION);
	    	} else if (index==4) {
	    		ord.setStatus(Status.ENDED);
	    	}
	    	if(thingList!=null) {
	    		ord.setThingList(thingList);
	    	}
	    	return ord;
	    	
	    }
	    return null;
	}
	
	private void showCalculation() {
		
	}
}

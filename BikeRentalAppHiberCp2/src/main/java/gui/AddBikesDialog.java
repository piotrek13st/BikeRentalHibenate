package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.client.Client;
import model.client.Institution;
import model.client.Person;
import model.thing.Bike;
import model.thing.MTBike;
import model.thing.MountainBike;
import model.thing.Thing;
import model.thing.TraditionalBike;

import javax.swing.JToolBar;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;



public class AddBikesDialog extends JDialog implements ActionListener {
    

	private final JPanel contentPanel = new JPanel();
	private JLabel lblAbsoluteDst;
	private JLabel lblderailleurs;
	private JLabel lblDistAfterSer;
	private JLabel lblWheelDiameter;
	
	private JTextField textWheelDiameter;
	private JTextField textAbsoluteDst;
	private JTextField textServiceDst;
	private JTextField textDstAftServ;
	
	
	
	private boolean okPressed;
	private JButton btnConfirm;
	private JButton btnCancel;
	
	
	private JComboBox cmbBikeType;
	private JTextField textDerailleurs;
	private JTextField textModel;
	private JLabel lblDailyRentalPrice;
	private JTextField textDailyRenPrice;
	private JCheckBox chckbxNewCheckBox;
	private JLabel lblNumOf;
	private JLabel lblNumOfReservations;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			AddDialog dialog = new AddDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	/**
	 * Create the dialog.
	 */
	public AddBikesDialog(JDialog frame) {
		super(frame,true);
		setResizable(false);
		initComponents();
		createEvents();
	}
	

	
	public Bike showDialog(Bike con) {
		if(con!=null) {
			
			if(con instanceof MTBike) {
				setMTBikeMode();
				textModel.setText(((MTBike) con).getMtbModel());
				textDerailleurs.setText(""+((MTBike) con).getDerailleurAmount());
			} else if(con instanceof MountainBike){
				setMountainBikeMode();
				textDerailleurs.setText(""+((MountainBike) con).getDerailleurAmount());
				
			} else {
				setTraditionalBikeMode();
			
			}
			cmbBikeType.setEnabled(false);
			textAbsoluteDst.setText(""+con.getAbsoluteDisctance());
			textDailyRenPrice.setText(""+con.getDailyRentalPrice());
			textDstAftServ.setText(""+con.getDistanceAfterService());
			textServiceDst.setText(""+ con.getServicingDistance());
			textWheelDiameter.setText("" + con.getWheelDiameter());
			//lblNumOfReservations.setText(""+con.getDatesOfReservations().size());
			if(con.isFunctional()) 
				chckbxNewCheckBox.setSelected(true);
			else 
				chckbxNewCheckBox.setSelected(false);
		
		} else {
			setTraditionalBikeMode();
			cmbBikeType.setEnabled(true);
			lblNumOfReservations.setText("0");
		}
	    okPressed = false;
	    setVisible(true);
	    if (okPressed) {
	    	Bike cli=null;
	    	int index=cmbBikeType.getSelectedIndex();
	    	if(index==0) {
	    		cli=new TraditionalBike();
	    	} else if(index == 1){
	    		cli=new MountainBike();
	    		((MountainBike)cli).setDerailleurAmount(Integer.parseInt(textDerailleurs.getText()));
	    	} else if(index ==2) {
	    		cli=new MTBike();
	    		((MTBike)cli).setDerailleurAmount(Integer.parseInt(textDerailleurs.getText()));
	    		((MTBike)cli).setMtbModel(textModel.getText());
	    	}
	    	cli.setAbsoluteDisctance(Integer.parseInt(textAbsoluteDst.getText()));
	    	cli.setDailyRentalPrice(Float.parseFloat(textDailyRenPrice.getText()));
	    	cli.setDistanceAfterService(Integer.parseInt(textDstAftServ.getText()));
	    	cli.setFunctional(chckbxNewCheckBox.isSelected());
	    	cli.setWheelDiameter(Integer.parseInt(textWheelDiameter.getText()));
	    	cli.setServicingDistance(Integer.parseInt(textServiceDst.getText()));
	    	
    		return cli;
	    }
	    return null;
	}

	private void createEvents() {
		btnConfirm.addActionListener(this);
		btnCancel.addActionListener(this);
		cmbBikeType.addActionListener(this);
		
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 296, 368);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			lblAbsoluteDst = new JLabel("Absolute Distance");
			lblAbsoluteDst.setBounds(15, 75, 105, 15);
			lblAbsoluteDst.setFont(new Font("Arial", Font.BOLD, 12));
			contentPanel.add(lblAbsoluteDst);
		}
		{
			lblderailleurs = new JLabel("Derailleurs Num");
			lblderailleurs.setBounds(15, 168, 93, 15);
			lblderailleurs.setFont(new Font("Arial", Font.BOLD, 12));
			contentPanel.add(lblderailleurs);
		}
		{
			lblDistAfterSer = new JLabel("Dst After Service");
			lblDistAfterSer.setBounds(15, 135, 105, 15);
			lblDistAfterSer.setFont(new Font("Arial", Font.BOLD, 12));
			contentPanel.add(lblDistAfterSer);
		}
		
		textAbsoluteDst = new JTextField("");
		textAbsoluteDst.setBounds(130, 73, 150, 20);
		contentPanel.add(textAbsoluteDst);
		textAbsoluteDst.setColumns(10);
		
		textServiceDst = new JTextField("");
		textServiceDst.setBounds(130, 104, 150, 20);
		textServiceDst.setColumns(10);
		contentPanel.add(textServiceDst);
		
		textDstAftServ = new JTextField("");
		textDstAftServ.setBounds(130, 133, 150, 20);
		textDstAftServ.setColumns(10);
		contentPanel.add(textDstAftServ);
		
		
		textWheelDiameter = new JTextField("");
		textWheelDiameter.setBounds(130, 42, 150, 20);
		textWheelDiameter.setColumns(10);
		contentPanel.add(textWheelDiameter);
		
		lblWheelDiameter = new JLabel("Wheel Diameter");
		lblWheelDiameter.setBounds(15, 44, 93, 15);
		lblWheelDiameter.setFont(new Font("Arial", Font.BOLD, 12));
		contentPanel.add(lblWheelDiameter);
		
		
		cmbBikeType = new JComboBox(new String[]{"Traditional", "Mountain", "MTBike"});
		
		cmbBikeType.setBounds(130, 11, 150, 20);
		contentPanel.add(cmbBikeType);
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Arial", Font.BOLD, 12));
		lblType.setBounds(15, 14, 61, 15);
		contentPanel.add(lblType);
		
		JLabel lblServiceDst = new JLabel("Service Distance");
		lblServiceDst.setFont(new Font("Arial", Font.BOLD, 12));
		lblServiceDst.setBounds(15, 106, 105, 15);
		contentPanel.add(lblServiceDst);
		
		textDerailleurs = new JTextField("");
		textDerailleurs.setColumns(10);
		textDerailleurs.setBounds(130, 166, 150, 20);
		contentPanel.add(textDerailleurs);
		
		JLabel lblModel = new JLabel("Model");
		lblModel.setFont(new Font("Arial", Font.BOLD, 12));
		lblModel.setBounds(15, 200, 93, 15);
		contentPanel.add(lblModel);
		
		textModel = new JTextField("");
		textModel.setColumns(10);
		textModel.setBounds(130, 198, 150, 20);
		contentPanel.add(textModel);
		
		lblDailyRentalPrice = new JLabel("Daily Rental Price");
		lblDailyRentalPrice.setFont(new Font("Arial", Font.BOLD, 12));
		lblDailyRentalPrice.setBounds(15, 231, 105, 15);
		contentPanel.add(lblDailyRentalPrice);
		
		textDailyRenPrice = new JTextField("");
		textDailyRenPrice.setColumns(10);
		textDailyRenPrice.setBounds(130, 229, 150, 20);
		contentPanel.add(textDailyRenPrice);
		
		chckbxNewCheckBox = new JCheckBox("");
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.setBounds(93, 253, 27, 29);
		contentPanel.add(chckbxNewCheckBox);
		
		JLabel lblIsfunctional = new JLabel("Is Functional");
		lblIsfunctional.setFont(new Font("Arial", Font.BOLD, 12));
		lblIsfunctional.setBounds(15, 264, 105, 15);
		contentPanel.add(lblIsfunctional);
		
		lblNumOf = new JLabel("Num of reservations:");
		lblNumOf.setBounds(140, 268, 105, 14);
		contentPanel.add(lblNumOf);
		
		lblNumOfReservations = new JLabel("0");
		lblNumOfReservations.setFont(new Font("Arial", Font.BOLD, 12));
		lblNumOfReservations.setBounds(250, 267, 40, 15);
		contentPanel.add(lblNumOfReservations);
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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		    if (source == btnConfirm) {
		      okPressed = true;
		      setVisible(false);
		    } else if (source == btnCancel){
		    	setVisible(false);
		    } else if (source ==cmbBikeType) {
		    	int index=cmbBikeType.getSelectedIndex();	
		    	
		    	if(index==0) {
		    		textDerailleurs.setEnabled(false);
		    		textModel.setEnabled(false);
		    		textDailyRenPrice.setText("20");
		    		textServiceDst.setText("5000");
		    		textDerailleurs.setText("---");
		    		textModel.setText("---");
		    		
		    	} else if (index ==1) {
		    		textDerailleurs.setEnabled(true);
		    		textModel.setEnabled(false);
		    		textDailyRenPrice.setText("30");
		    		textServiceDst.setText("3000");
		    		textModel.setText("---");
		    	} else if (index ==2) {
		    		textDerailleurs.setEnabled(true);
		    		textModel.setEnabled(true);
		    		textDailyRenPrice.setText("50");
		    		textServiceDst.setText("1000");
		    	}
		    } 
		    
		      
	}
	
	private void setTraditionalBikeMode() {
		cmbBikeType.setSelectedIndex(0);
		textDerailleurs.setEnabled(false);
		textModel.setEnabled(false);
		textDerailleurs.setText("---");
		textModel.setText("---");
	}
	
	private void setMountainBikeMode() {
		textDerailleurs.setEnabled(true);
		textModel.setEnabled(false);
		textModel.setText("---");
		cmbBikeType.setSelectedIndex(1);
	}
	
	private void setMTBikeMode() {
		textDerailleurs.setEnabled(true);
		textModel.setEnabled(true);
		cmbBikeType.setSelectedIndex(2);
	}
}

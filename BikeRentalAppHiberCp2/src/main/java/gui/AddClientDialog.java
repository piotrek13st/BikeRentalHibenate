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



public class AddClientDialog extends JDialog implements ActionListener {
    

	private final JPanel contentPanel = new JPanel();
	private JLabel lblLastName;
	private JLabel lblNewLabel;
	private JLabel lblEmaili;
	
	private JTextField textName;
	private JTextField textPhone;
	private JTextField textEmail;
	
	
	private boolean okPressed;
	private JButton btnConfirm;
	private JButton btnCancel;
	private JRadioButton rdbtnPerson;
	private JRadioButton rdbtnInstitution;
	ButtonGroup radioGroup;
	private JLabel lblFirstName;
	private JTextField textFirstName;

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
	public AddClientDialog(JDialog parent) {
		super(parent,true);
		setResizable(false);
		initComponents();
		createEvents();
	}
	
	public Client showDialog(Client con) {
		if(con!=null) {
			
			if(con instanceof Person) {
				textFirstName.setText(((Person) con).getName());
				textName.setText(((Person) con).getSurname());
				setPersonMode();
				rdbtnInstitution.setEnabled(false);
				rdbtnPerson.setEnabled(false);
			} else {
				textName.setText(con.getClientName());
				rdbtnInstitution.setSelected(true);
				setInstitutionMode();
			}
			textPhone.setText(con.getPhoneNumber());
			textEmail.setText(con.getEmail());
			rdbtnInstitution.setEnabled(false);
			rdbtnPerson.setEnabled(false);
		} else {
			rdbtnInstitution.setEnabled(true);
			rdbtnPerson.setEnabled(true);
		}
	    okPressed = false;
	    setVisible(true);
	    if (okPressed) {
	    	Client cli;
	    	if(rdbtnPerson.isSelected()) {
	    		cli=new Person();
	    		((Person)cli).setName(textFirstName.getText().toString());
	    		((Person)cli).setSurname(textName.getText().toString());
	    	} else {
	    		cli=new Institution();
	    		((Institution)cli).setInstitutionName(textName.getText().toString());
	    	} 
	    	cli.setPhoneNumber(textPhone.getText().toString());
    		cli.setEmail(textEmail.getText().toString());
	    	
    		return cli;
	    }
	    return null;
	}

	private void createEvents() {
		btnConfirm.addActionListener(this);
		btnCancel.addActionListener(this);
		rdbtnPerson.addActionListener(this);
		rdbtnInstitution.addActionListener(this);
		
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 296, 271);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			lblLastName = new JLabel("Last Name");
			lblLastName.setBounds(15, 59, 61, 15);
			lblLastName.setFont(new Font("Arial", Font.BOLD, 12));
			contentPanel.add(lblLastName);
		}
		{
			lblNewLabel = new JLabel("Phone");
			lblNewLabel.setBounds(15, 97, 36, 15);
			lblNewLabel.setFont(new Font("Arial", Font.BOLD, 12));
			contentPanel.add(lblNewLabel);
		}
		{
			lblEmaili = new JLabel("Email");
			lblEmaili.setBounds(15, 135, 31, 15);
			lblEmaili.setFont(new Font("Arial", Font.BOLD, 12));
			contentPanel.add(lblEmaili);
		}
		
		textName = new JTextField("");
		textName.setBounds(82, 57, 189, 20);
		contentPanel.add(textName);
		textName.setColumns(10);
		
		textPhone = new JTextField("");
		textPhone.setBounds(82, 97, 189, 20);
		textPhone.setColumns(10);
		contentPanel.add(textPhone);
		
		textEmail = new JTextField("");
		textEmail.setBounds(81, 133, 189, 20);
		textEmail.setColumns(10);
		contentPanel.add(textEmail);
		
		rdbtnPerson = new JRadioButton("Person");
		rdbtnPerson.setBounds(15, 171, 59, 23);
		contentPanel.add(rdbtnPerson);
		
		rdbtnInstitution = new JRadioButton("Institution");
		rdbtnInstitution.setBounds(81, 171, 75, 23);
		
		radioGroup = new ButtonGroup();
		radioGroup.add(rdbtnInstitution);
		radioGroup.add(rdbtnPerson);
		rdbtnPerson.setSelected(true);
		
		contentPanel.add(rdbtnInstitution);
		
		textFirstName = new JTextField("");
		textFirstName.setBounds(82, 18, 188, 20);
		textFirstName.setColumns(10);
		contentPanel.add(textFirstName);
		
		lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(15, 20, 61, 15);
		lblFirstName.setFont(new Font("Arial", Font.BOLD, 12));
		contentPanel.add(lblFirstName);
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
		    } else if (source ==rdbtnPerson) {
		    		setPersonMode();
		    } else if (source ==rdbtnInstitution) {
		    		setInstitutionMode();
		    }
		    
		      
	}
	
	private void setPersonMode() {
		lblFirstName.setVisible(true);
		textFirstName.setVisible(true);
		lblLastName.setText("Last Name");
	}
	
	private void setInstitutionMode() {
		lblFirstName.setVisible(false);
		textFirstName.setVisible(false);
		lblLastName.setText("Name");
	}
	
}

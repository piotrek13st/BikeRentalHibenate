//package gui;
//
//import java.sql.SQLException;
//
//import javax.swing.table.AbstractTableModel;
//import javax.swing.table.DefaultTableModel;
//
//import db.DbContacts;
//import db.DbManagerInterface;
//import model.contact.Contact;
//
//public class ContactDbTableModel extends DefaultTableModel {
//	DbContacts db;
//	public ContactDbTableModel(DbContacts dtb) throws SQLException {
//		super(new String[] {"Id", "Name", "Phone", "Email"}, 0);
//		db=dtb;
//		try {
//			db.createTable();
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());	
//		}
//		
//		for(Contact it: db.getAll()) {
//			addRow(new Object[]{it.getId(), it.getName(), it.getPhoneNumber(), it.getEmail()});	
//		}
//		
//		
//			
//	}
//	
//	public void addRow(Contact contact) throws SQLException  {
//			int id=db.add(contact);
//			
//			addRow(new Object[]{id, contact.getName(), contact.getPhoneNumber(), contact.getEmail()});
//	}
//	
//	@Override
//	public void removeRow(int row) {	
//		try {
//			db.remove((int)getValueAt(row, 0));
//			//super.removeRow(row);
//			fireTableRowsDeleted(row, row);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		super.removeRow(row);
//	}
//	
////	public void removeRows(int [] rows) {	
////		try {
////			for(int i=0; i<rows.length; ++i)
////				db.remove((int)getValueAt(rows[i], 0));
////				
////			//super.removeRow(row);
////			
////		} catch (SQLException e) {
////			e.printStackTrace();
////		}
////		super.removeRow(row);
////	}
//	
//	public void modifyRow(Contact contact, int row) throws Exception {
//		int id= (int)getValueAt(row, 0);
//		db.set(id, DbContacts.Param.DB_NAME, contact.getName());
//		setValueAt(contact.getName(), row, 1);
//		db.set(id, DbContacts.Param.DB_PHONE, contact.getPhoneNumber());
//		setValueAt(contact.getPhoneNumber(), row, 2);
//		db.set(id, DbContacts.Param.DB_EMAIL, contact.getEmail());
//		setValueAt(contact.getEmail(), row, 3);
//	}
//	
//	public Contact getRow(int row) throws SQLException {
//		return db.get((int)getValueAt(row, 0));
//	}
//	
//
//}

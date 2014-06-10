package gui.tablemodels;

import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;




import logic.ClientDBManager;
import logic.RecordExistsException;
import logic.RecordNotFoundException;
import model.client.Client;


public class ClientTableModel extends DefaultTableModel {
	
	ClientDBManager dbm;
	public ClientTableModel(ClientDBManager manager) throws RecordNotFoundException  {
		super(new String[] {"Id", "Name", "Phone", "Email"}, 0);
		dbm=manager;
		for(Client it: dbm.getRecords()) {
			addRow(new Object[]{it.getId(), it.getClientName(), it.getPhoneNumber(), it.getEmail()});	
		}		
	}
	
	public void addRow(Client contact) throws RecordExistsException   {
			Long id=dbm.addRecord(contact);		
			addRow(new Object[]{id, contact.getClientName(), contact.getPhoneNumber(), contact.getEmail()});
	}
	
	@Override
	public void removeRow(int row) {	

			dbm.removeRecord((Long)getValueAt(row, 0));
			fireTableRowsDeleted(row, row);
			super.removeRow(row);
	}
	
//	public void removeRows(int [] rows) {	
//		try {
//			for(int i=0; i<rows.length; ++i)
//				db.remove((int)getValueAt(rows[i], 0));
//				
//			//super.removeRow(row);
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		super.removeRow(row);
//	}
	
	public void modifyRow(Client contact, int row) {
		Long id= (Long)getValueAt(row, 0);

		dbm.updateRecord(id, contact);
		setValueAt(contact.getClientName(), row, 1);
		setValueAt(contact.getPhoneNumber(), row, 2);
		setValueAt(contact.getEmail(), row, 3);
	}
	
	public Client getRow(int row) throws RecordNotFoundException {
		return dbm.getRecordById((Long)getValueAt(row, 0));
	}
	

}

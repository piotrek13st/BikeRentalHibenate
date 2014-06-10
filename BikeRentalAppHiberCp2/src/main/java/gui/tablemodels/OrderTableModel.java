package gui.tablemodels;

import javax.swing.table.DefaultTableModel;

import logic.OrderDBManager;
import logic.RecordExistsException;
import logic.RecordNotFoundException;
import model.order.Order;
import model.order.Order.Status;


public class OrderTableModel extends DefaultTableModel {
	
	OrderDBManager dbm;
	public OrderTableModel(OrderDBManager manager) throws RecordNotFoundException  {
		super(new String[] {"Id", "Client", "Date", "Status"}, 0);
		dbm=manager;
		for(Order it: dbm.getRecords()) {
			addRow(new Object[]{it.getId(), it.getClient().getClientName(), it.getMadedOrderDate().toString(), it.getStatus().toString()});	
		}		
	}
	
	public void selectRecordsByStatus(Status status) throws RecordNotFoundException {
		for(Order it: dbm.getRecords()) {
			if(it.getStatus().equals(status))
				addRow(new Object[]{it.getId(), it.getClient().getClientName(), it.getMadedOrderDate().toString(), it.getStatus().toString()});	
		}	
	}
	
	public Long addRow(Order order) throws RecordExistsException   {
			Long id=dbm.addRecord(order);		
			addRow(new Object[]{order.getId(), order.getClient().getClientName(), order.getMadedOrderDate().toString(), order.getStatus().toString()});
			return id;
	}
	
	@Override
	public void removeRow(int row) {	

			dbm.removeRecord((Long)getValueAt(row, 0));
			fireTableRowsDeleted(row, row);
			super.removeRow(row);
	}

	
	public void modifyRow(Order order, int row) {
		Long id= (Long)getValueAt(row, 0);

		dbm.updateRecord(id, order);
		setValueAt(order.getStatus().toString(), row, 1);
	
	}
	
	public Order getRow(int row) throws RecordNotFoundException {
		return dbm.getRecordById((Long)getValueAt(row, 0));
	}
	

}

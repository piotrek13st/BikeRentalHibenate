package gui.tablemodels;

import java.util.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import logic.BikeDBManager;
import logic.RecordExistsException;
import logic.RecordNotFoundException;
import model.thing.Bike;
import model.thing.MTBike;
import model.thing.MountainBike;
import model.thing.TraditionalBike;


public class BikeTableModel extends DefaultTableModel {
	
	BikeDBManager dbm;
	public BikeTableModel(BikeDBManager manager, Class<? extends Bike> bikeClass, Boolean isFunctional) throws RecordNotFoundException  {
		super(new String[] {"Id", "Type", "Wheel Diameter" , "Model"}, 0);
		dbm=manager;
	    List<Bike> lb=null;
	    if(bikeClass==null) {
	    	lb=dbm.getRecords();
	    } 
	    else if(bikeClass.getSimpleName().equalsIgnoreCase(TraditionalBike.class.getSimpleName())) {
	    	lb=dbm.getTraditionalBikes();
	    } else if(bikeClass.getSimpleName().equalsIgnoreCase(MountainBike.class.getSimpleName())) {
	    	lb=dbm.getMountainBikes();
	    } else if(bikeClass.getSimpleName().equalsIgnoreCase(MTBike.class.getSimpleName())) {
	    	lb=dbm.getMTBikes();
	    } 

		for(Bike it: lb) {
				addRow(new Object[]{it.getId(), it.getClass().getSimpleName(), it.getWheelDiameter(), 
						( (it instanceof MTBike) ? ((MTBike)it).getMtbModel() :"---")} );
		}
		
	}
	
	public BikeTableModel(BikeDBManager manager, Class<? extends Bike> bikeClass, Boolean isFunctional,
				Date from, Date to) throws RecordNotFoundException  {
		super(new String[] {"Id", "Type", "Wheel Diameter" , "Model"}, 0);
		dbm=manager;
	    List<Bike> lb=null;
	    if(bikeClass==null) {
	    	lb=dbm.getRecords();
	    } 
	    else if(bikeClass.getSimpleName().equalsIgnoreCase(TraditionalBike.class.getSimpleName())) {
	    	lb=dbm.getTraditionalBikes();
	    } else if(bikeClass.getSimpleName().equalsIgnoreCase(MountainBike.class.getSimpleName())) {
	    	lb=dbm.getMountainBikes();
	    } else if(bikeClass.getSimpleName().equalsIgnoreCase(MTBike.class.getSimpleName())) {
	    	lb=dbm.getMTBikes();
	    } 

	    if(from != null && to != null)
		for(Bike it: lb) {
				if(it.isAvailable(from, to))
					addRow(new Object[]{it.getId(), it.getClass().getSimpleName(), it.getWheelDiameter(), 
						( (it instanceof MTBike) ? ((MTBike)it).getMtbModel() :"---")} );
		}
		
	}
	
	public void addRow(Bike bk) throws RecordExistsException   {
			Long id=dbm.addRecord(bk);		
			addRow(new Object[]{bk.getId(), bk.getClass().getSimpleName(), bk.getWheelDiameter(), 
					( (bk instanceof MTBike) ? ((MTBike)bk).getMtbModel() :"---")} );
	}
	
	@Override
	public void removeRow(int row) {	

			dbm.removeRecord((Long)getValueAt(row, 0));
			fireTableRowsDeleted(row, row);
			super.removeRow(row);
	}
	
	
	public void modifyRow(Bike contact, int row) {
		Long id= (Long)getValueAt(row, 0);
		dbm.updateRecord(id, contact);
	}
	
	public Bike getRow(int row) throws RecordNotFoundException {
		return dbm.getRecordById((Long)getValueAt(row, 0));
	}
	

}

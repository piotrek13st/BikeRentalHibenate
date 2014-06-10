package model.thing;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
public class TraditionalBike extends Bike {

	private static final long serialVersionUID = -8453907241327038643L;

	public TraditionalBike(long id) {
		super(id);
	}
	
	public TraditionalBike() {
		super(0L);
	}

	@Override
	public String getName() {
		return "Traditional";
	}
	
}

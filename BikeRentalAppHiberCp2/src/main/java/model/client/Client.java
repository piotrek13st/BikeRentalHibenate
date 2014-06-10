package model.client;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.check.Checkable;
import model.id.Idenifiable;

//@Entity
//@Table(name = "PERSON")
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(
//    name="discriminator",
//    discriminatorType=DiscriminatorType.STRING
//)

@Entity
@Table(name = "CLIENT")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name="clienttype",
		discriminatorType=DiscriminatorType.STRING
		)
@DiscriminatorValue(value="---")
abstract public class Client implements Comparable<Client>, Checkable<Client>, Idenifiable, Serializable{
	
	@Id 
	@GeneratedValue
	@Column(name= "CLIENT_ID")
	private long id;
	
	@Column(name="PHONE")
	private String phoneNumber;
	
	@Column(name="EMAIL")
	private String email;
	
	
	protected static Logger logger= LogManager.getLogger(Client.class.getSimpleName());
	
	public Client(long id) {
		this.id=id;
	}
	
	public Client(long id, String phoneNumber) {
		this(id);
		this.phoneNumber=phoneNumber;
	}
	
	public Client() {
		this(0L);
	}
	
	public Client(long id, String phoneNumber, String email) {
		this(id, phoneNumber);
		this.email=email;
	}
	
	@Override
	public int compareTo(Client o) {
		return this.getClientName().compareToIgnoreCase(o.getClientName());
	}
	
	
	/**check if clients have the same email address. If any has email equals null  
	 * then clients are compared by name
	 * 
	 * @param obj - client to compare with
	 * @return true if equals 
	 */
	@Override
	public boolean check(Client obj) {
		if(email!=null && obj.email!=null) {
			return email.equalsIgnoreCase(obj.email);
		}
		return getClientName().equalsIgnoreCase(obj.getClientName());
	}
	
	abstract public String getClientName();
	
	public void setEmail(String email) {
		this.email=email;
	}
	
	public String getEmail() {
		return email;
	}
	
	@Override
	public long getId() {
		return id;
	}
	
	@Override
	public void setId(long id) {
		this.id=id;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber=phoneNumber;
	}
	
	@Override
	public boolean equals(Object obj) {
		if((this.getClientName().equalsIgnoreCase(((Client)obj).getClientName()) && 
				this.phoneNumber.equals(((Client)obj).getPhoneNumber())))
			return true;
		else
			return false;
		
	}
	
	@Override
	public String toString() {
		return String.format("%-10s%-22s%-22s%18s", new Long(id).toString()+":", getClientName(), getEmail(), getPhoneNumber());
	}
	
}

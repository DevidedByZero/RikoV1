package orders;

import java.util.Date;

public class Row{

	private final int MIN_SUPP = 1, MAX_SUPP = 8, DEFAULT_SUPP = 999;

	private String description, firstName, lastName, phone,
	supplierName, contactName, contactPhone;

	//TODO change to object?
	private String status;
	Date statusDate, orderDate;

	private int branch;
	
	private int supNum;

	private int orderNum;


	public Row(){
		description = firstName = lastName = phone =
				supplierName = contactName = contactPhone = status = "";
		orderDate = statusDate = null;
		branch = 15;
		orderNum = 0;
		supNum = 999;
	}

	public String toString(){
		return firstName + lastName + phone + status + "\n";

	}


	//SETTERS
	/**@param n the order number to set*/
	public void setOrderNum(int n) {
		orderNum = n;
	}

	/**@param s the status to set*/
	public void setStatus(String s) {
		status = s;
	}

	/**@param sd the status date to set*/
	public void setStatusDate(Date sd) {
		statusDate = sd;
	}

	/**@param branch the orderDate to set*/
	public void setBranch(int b) {
		branch = b;
	}

	/**@param orderDate the orderDate to set*/
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}


	/**@param description the description to set*/
	public void setDescription(String description) {
		this.description = description;
	}


	/**@param firstName the firstName to set*/
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**@param lastName the lastName to set*/
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**@param phone the phone to set*/
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**@param supplier the supplier number to set*/
	public void setSupNum(int supplier) {
		if(supplier >= MIN_SUPP && supplier <= MAX_SUPP)
			supNum = supplier;
		else
			supNum = DEFAULT_SUPP;
	}


	/**@param supplierName the supplierName to set*/
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}


	/**@param contactName the contactName to set*/
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	/**@param contactPhone the contactPhone to set*/
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	//END SETTERS




	//GETTERS
	/**@return the order number*/
	public int getOrderNum() {
		return orderNum;
	}

	/**@return the current status*/
	public String getStatus() {
		return status;
	}

	/**@return the current status date*/
	public Date getStatusDate() {
		return statusDate;
	}

	/**@return the branch number*/
	public int getBranch() {
		return branch;
	}

	/**@return the orderDate*/
	public Date getOrderDate() {
		return orderDate;
	}

	/**@return the description*/
	public String getDescription() {
		return description;
	}


	/**@return the firstName*/
	public String getFirstName() {
		return firstName;
	}


	/**@return the lastName*/
	public String getLastName() {
		return lastName;
	}


	/**@return the phone*/
	public String getPhone() {
		return phone;
	}

	/**@return the supplier number*/
	public int getSupNum() {
		return supNum;
	}


	/**@return the supplierName*/
	public String getSupplierName() {
		return supplierName;
	}


	/**@return the contactName*/
	public String getContactName() {
		return contactName;
	}


	/**@return the contactPhone*/
	public String getContactPhone() {
		return contactPhone;
	}
	//END GETTERS










}

package com.jj.spring.finalex.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class Customer {

    @Id
    private String id;
    private String address;
    private String reservationId;  //reference to the reservation id
    private String details;

    public Customer() {
    }

    public Customer(String address, String reservationId, String details) {
        this.address = address;
        this.reservationId = reservationId;
        this.details = details;
    }

    //getters and setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getReservationId() {
        return reservationId;
    }
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

	@Override
	public String toString() {
		return "Customer [id=" + id + ", address=" + address + ", reservationId=" + reservationId + ", details="
				+ details + "]";
	}
}

package com.jj.spring.finalex.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "reservations")
public class Reservation {

	//using server-side validations for fields
	
    @Id
    private String id;

    @NotBlank(message="first name is required")
    private String firstName;

    @NotBlank(message="last name is required")
    private String lastName;

    @Min(value = 1, message = "there must be at least one passenger")
    private int noOfPassengers;

    @NotBlank(message="travel class is required")
    private String travelClass;

    @NotBlank(message="phone number is required")
    @Pattern(regexp="\\d{10}", message="phone number must be exactly 10 digits")
    private String phoneNumber;

    @NotNull(message="departure time is required")
    private LocalTime time;

    @NotNull(message="date of departing is required")
    @FutureOrPresent(message="date of departing must be today or in the future")
    private LocalDate dateOfDeparting;

    //Fields to link to Customer and Payment records.
    private String customerId;  //to link the customer record to the reservation
    private String paymentId;   //to link the payment record to the reservation


	//Constructors
    public Reservation() {
    }

    public Reservation(String firstName, String lastName, int noOfPassengers, String travelClass,
                       String phoneNumber, LocalTime time, LocalDate dateOfDeparting) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.noOfPassengers = noOfPassengers;
        this.travelClass = travelClass;
        this.phoneNumber = phoneNumber;
        this.time = time;
        this.dateOfDeparting = dateOfDeparting;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getNoOfPassengers() {
		return noOfPassengers;
	}

	public void setNoOfPassengers(int noOfPassengers) {
		this.noOfPassengers = noOfPassengers;
	}

	public String getTravelClass() {
		return travelClass;
	}

	public void setTravelClass(String travelClass) {
		this.travelClass = travelClass;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public LocalDate getDateOfDeparting() {
		return dateOfDeparting;
	}

	public void setDateOfDeparting(LocalDate dateOfDeparting) {
		this.dateOfDeparting = dateOfDeparting;
	}

    public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", noOfPassengers="
				+ noOfPassengers + ", travelClass=" + travelClass + ", phoneNumber=" + phoneNumber + ", time=" + time
				+ ", dateOfDeparting=" + dateOfDeparting + ", customerId=" + customerId + ", paymentId=" + paymentId
				+ "]";
	}

}

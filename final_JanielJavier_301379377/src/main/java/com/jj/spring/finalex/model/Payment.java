package com.jj.spring.finalex.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "payments")
public class Payment {

    @Id
    private String id;
    private double amount;
    private LocalDate date;

    public Payment() {}

    public Payment(double amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    //calculate total amount based on travel class and number of passengers, then add 10% tax
    public double calculate(String travelClass, int noOfPassengers) {
        double basePrice = 0.0;
        if(travelClass.equalsIgnoreCase("Economy")) {
            basePrice = 100.0;
        } else if(travelClass.equalsIgnoreCase("Business")) {
            basePrice = 200.0;
        } else if(travelClass.equalsIgnoreCase("First")) {
            basePrice = 300.0;
        }
        double subtotal = basePrice * noOfPassengers;
        double tax = subtotal * 0.10; //10% tax
        return subtotal + tax;
    }

    //getters and setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

	@Override
	public String toString() {
		return "Payment [id=" + id + ", amount=" + amount + ", date=" + date + "]";
	}
}

package ov2;

import java.util.ArrayList;
import java.util.Stack;

public class ServingArea {
	
	private int totalOrders = 0, eatingOrders = 0, takeawayOrders = 0;
	
	ArrayList<Customer> listOfCustomers = new ArrayList<Customer>();
	//Stack<Customer> queue = new Stack<Customer>();
	
	
	public ServingArea() {
		SushiBar.write(
				"***** WELCOME CUSTOMERS - THE SHOP IS OPEN NOW. *****"
				);
	}

	
	public synchronized boolean enter(Customer c) {
		c.print(Action.ENTER);
		c.print(Action.WAIT);
		while(!(listOfCustomers.size() < SushiBar.capacity)) {
			//queue.push(c);
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		listOfCustomers.add(c);
		return true;
	}
	
	
	public synchronized void leave(Customer c) {
		//queue.pop().notify();
		c.print(Action.LEAVE);
		listOfCustomers.remove(c);
		SushiBar.write("\tNow there is a free seat in the shop.");
		notify();
		// Check if the leaving customer is the last customer of the day.
		if(this.listOfCustomers.size() == 0 && !SushiBar.isOpen) {
			SushiBar.write(
					"***** NO MORE CUSTOMERS - THE SHOP IS CLOSED NOW. *****"
					);
			statistics();	
		}
	}
	
	public synchronized void eat(Customer c, int totalOrders, 
			int eatingOrders, int takeawayOrders) {
		c.print(Action.EAT);
		this.totalOrders += totalOrders;
		this.eatingOrders += eatingOrders;
		this.takeawayOrders += takeawayOrders;
	}
	
	
	private void statistics() {
		SushiBar.write("\nStatistics:\n"
				+ "Total number of orders: " + this.totalOrders + "\n"
				+ "Total number of takeaway orders: " + this.takeawayOrders + "\n"
				+ "Total number of orders eaten at serving area: " + this.eatingOrders);
	}
}

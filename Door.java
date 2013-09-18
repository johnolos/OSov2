package ov2;


public class Door implements Runnable {
	Customer customer;
	private int id = 1;
	
	ServingArea servingArea;
	
	public Door(ServingArea servingArea) {
		this.servingArea = servingArea;
	}

	@Override
	public void run() {
		while(SushiBar.isOpen) {
			// Instance a new customer but does not run it.
			customer = new Customer(id++, this.servingArea);
			Thread customerThread = new Thread(customer);
			customerThread.start();
			
			try {
				Thread.sleep(SushiBar.doorWait); // Time between customers
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}

package com.ccland.test;

import java.util.Date;

public class TimePrinter implements Runnable {

	private int pausetime;
	private String name;

	public TimePrinter(int pausetime, String name) {
		this.pausetime = pausetime;
		this.name = name;
	}

	@Override
	public void run() {

		while (true) {
			try {
				System.out.println(this.name + ":"
						+ new Date(System.currentTimeMillis()));

				Thread.sleep(this.pausetime);

			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new TimePrinter(1000, "tom-----"));
		t1.start();
		Thread t2 = new Thread(new TimePrinter(3000, "jerry---"));
		t2.start();
	}
}

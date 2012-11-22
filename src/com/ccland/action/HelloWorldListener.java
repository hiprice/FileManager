package com.ccland.action;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.ccland.dfs.Calculator;

public class HelloWorldListener extends ContextLoaderListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("HelloWorldListener End--------------------");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("HelloWorldListener Start--------------------");
		Calculator cal = new Calculator();
		System.out.println("3+4＝" + cal.add(3, 4));

	}
}

package com.ccland.test;

import junit.framework.TestCase;

import org.junit.Assert;

import com.ccland.dfs.Calculator;

public class test extends TestCase {

	/**
	 * 自定义测试方法，方法名必须以'test'开头
	 * 
	 * @throws Throwable
	 */
	public void testCal() throws Throwable {
		System.out.println("正在测试中............");
		Calculator c = new Calculator();
		Assert.assertEquals(5, c.add(2, 3));

	}

	@Override
	protected void setUp() throws Exception {
		System.out.println("测试前--------------");

	}

	@Override
	protected void tearDown() throws Exception {
		System.out.println("测试后--------------");
	}

}

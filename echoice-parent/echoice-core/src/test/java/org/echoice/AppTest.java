package org.echoice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.google.common.collect.Lists;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
		PageRequest pageRequest=new PageRequest(2, 16);
		System.out.println(pageRequest.getOffset());

		Page<Object> page=new PageImpl<Object>(new ArrayList<Object>(), pageRequest, 400);

		System.out.println(page.getTotalElements()+":"+pageRequest.next().getPageNumber());
		List values=null;
		Lists.newArrayList(values);
	}
}

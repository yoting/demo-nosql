package com.gusi.demo.nosql.mongo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class MongoMain {

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		MongoBusiness mongoBusiness = (MongoBusiness) ac.getBean("mongoBusiness");
		mongoBusiness.demoMongo();
	}
}

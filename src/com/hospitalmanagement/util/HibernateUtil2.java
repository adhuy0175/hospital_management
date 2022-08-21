package com.hospitalmanagement.util;

import java.util.Properties;
import java.util.ResourceBundle;

import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import com.hospitalmanagement.model.Doctor;
import com.hospitalmanagement.model.Medicine;
import com.hospitalmanagement.model.Patient;
import com.hospitalmanagement.model.Test;

public class HibernateUtil2 {
	private static SessionFactory sessionFactory;
	
	static {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("oracle");
		Properties properties = new Properties();
		properties.setProperty(Environment.URL, resourceBundle.getString("db.url"));
		properties.setProperty(Environment.USER, resourceBundle.getString("db.username"));
		properties.setProperty(Environment.PASS, resourceBundle.getString("db.password"));
		properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.Oracle8iDialect");
		properties.setProperty(Environment.DRIVER, "oracle.jdbc.driver.OracleDriver");
		properties.setProperty(Environment.HBM2DDL_AUTO, "update");
		properties.setProperty(Environment.SHOW_SQL, "true");
		
		try {
			Configuration configuration = new Configuration();
			configuration.setProperties(properties);
			// Add class		
			configuration.addPackage("com.hospitalmanagement.model");
			configuration.addAnnotatedClass(Patient.class);
			configuration.addAnnotatedClass(Test.class);
			configuration.addAnnotatedClass(Medicine.class);
			configuration.addAnnotatedClass(Doctor.class);
			// Add resource
			configuration.addResource("mapping/Department.hbm.xml");
			configuration.addResource("mapping/Illness.hbm.xml");
			
			sessionFactory = configuration.buildSessionFactory();
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
}

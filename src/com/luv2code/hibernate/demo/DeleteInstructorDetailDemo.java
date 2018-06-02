package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class DeleteInstructorDetailDemo {

	public static void main(String[] args) {
	
		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		// use the session object to save Java object
		try {
			
			// start a transaction
			session.beginTransaction();
			
			// get instructor detail object
			int theId = 3;
			InstructorDetail tempInstructorDetail =
					session.get(InstructorDetail.class, theId);
			
			// print instructor detail
			System.out.println("Instructor detail: " + tempInstructorDetail);
			
			// print associated instructor
			System.out.println("Associated instructor: " + 
								tempInstructorDetail.getInstructor());
			
			// remove bi-directional link
			tempInstructorDetail.getInstructor().setInstructorDetail(null);
			
			// delete instructor detail
			System.out.println("Deleting instructor detail: " + 
								tempInstructorDetail);
			session.delete(tempInstructorDetail);
			
			// commit transaction
			session.getTransaction().commit();
			System.out.println("Done");
			
			
		} catch (Exception exc) {
			exc.printStackTrace();
		
		} finally {
			
			// close session to handle connection leaks
			session.close();
			
			factory.close();
		}
		
	}

}

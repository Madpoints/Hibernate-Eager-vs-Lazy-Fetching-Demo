package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class EagerLazyDemo {

	public static void main(String[] args) {
	
		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		// use the session object to save Java object
		try {
			
			// start a transaction
			session.beginTransaction();
			
			// get instructor from db
			int id = 1;
			Instructor tempInstructor = session.get(Instructor.class, id);
			
			// get courses for the instructor
			System.out.println("Getting courses for instructor: " + tempInstructor);
			
			// commit transaction
			session.getTransaction().commit();
			
			// close the session
			session.close();
			
			// should fail because courses are lazy loaded
			System.out.println(tempInstructor.getFirstName() + " " +
					tempInstructor.getLastName() + "'s " +
					"courses: " + tempInstructor.getCourses());
			
			
			System.out.println("Done");
			
			
		} finally {
			
			// clean up code
			session.close();
			factory.close();
		}
		
	}

}

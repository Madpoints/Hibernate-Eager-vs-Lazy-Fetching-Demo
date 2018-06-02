package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class FetchJoinDemo {

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
			
			// HQL query
			Query<Instructor> query = 
					session.createQuery("select i from Instructor i " + 
										"JOIN FETCH i.courses " + 
										"where i.id=:theInstructorId", 
										Instructor.class);
			
			// set parameter on query
			query.setParameter("theInstructorId", id);
			
			// execute query and get instructor
			Instructor tempInstructor = query.getSingleResult();
			
			System.out.println(tempInstructor.getFirstName() + " " +
					tempInstructor.getLastName() + "'s " +
					"courses: " + tempInstructor.getCourses());
			
			
			// commit transaction
			session.getTransaction().commit();
			
			// close the session
			System.out.println("\nClosing session...\n");
			session.close();
			
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

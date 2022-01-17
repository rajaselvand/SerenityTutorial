package com.kgisl.junit.studentInfo;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kgisl.model.StudentClass;
import com.kgisl.testbase.TestBase;
import com.kgisl.utils.TestUtils;


import io.restassured.http.ContentType;



import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
@RunWith(SerenityRunner.class)
public class StudentCRUDTest2 extends TestBase{
	
	
	static String firstName = "Rajaselvan"+TestUtils.getRandomValue();
	static String lastName = "David"+TestUtils.getRandomValue();
	static String programme = "Computer Science";
	String email = TestUtils.getRandomValue()+"rajaselvan@gmail.com";
	int studentId; 
	
	@Title("This test will create a new student")
	@Test
	public void createStudent(){
		
		ArrayList<String> courses = new ArrayList<String>();
		courses.add("Java");
		courses.add("C++");
		
		StudentClass student = new StudentClass();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setProgramme(programme);
		student.setCourses(courses);
	
		SerenityRest.rest().given()
		.contentType(ContentType.JSON)
		.log()
		.all()
		.when()
		.body(student)
		.post()
		.then()
		.log()
		.all()
		.statusCode(201);
		
	}
	
	@Title("Verify if the student was added to the application")
	@Test
	public void getStudent(){
		createStudent();
		String pl = "findAll{it.firstName=='";
		String p2 = "'}.get(0)";
	 HashMap<String,Object> value = SerenityRest.rest().given()
		.when()
		.get("/list")
		.then()
		.log()
		.all()
		.statusCode(200)
		.extract()
		.path(pl+firstName+p2)
		;
	 
	 System.out.print("The value is: "+value);
	 
	 assertThat(value,hasValue(firstName));
	 studentId = (int) value.get("id");
	 
	
	}
	
	
	@Title("Verify student is updated ")
	@Test
	public void putStudent(){
		
		getStudent();
		ArrayList<String> courses = new ArrayList<String>();
		courses.add("Java");
		courses.add("C++");
		
		firstName = firstName+"updated";
		
		StudentClass student = new StudentClass();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setProgramme(programme);
		student.setCourses(courses);
	
		SerenityRest.rest().given()
		.contentType(ContentType.JSON)
		.log()
		.all()
		.when()
		.body(student)
		.put("/"+studentId)
		.then()
		.log()
		.all()
		;
		
		String pl = "findAll{it.firstName=='";
		String p2 = "'}.get(0)";	
	//Verify firstName has value for updated student	
	 HashMap<String,Object> value = SerenityRest.rest().given()
		.when()
		.get("/list")
		.then()
		.log()
		.all()
		.statusCode(200)
		.extract()
		.path(pl+firstName+p2)
		;
	 
	 System.out.print("The value is: "+value);
	 
	 assertThat(value,hasValue(firstName));
	
	}

	@Title("Delete the student and verify the student is deleted!")
	@Test
	public void deleteStudent(){
		getStudent();
		
		SerenityRest.rest().given()
		.contentType(ContentType.JSON)
		.log()
		.all()
		.when()
		.delete("/"+studentId)
		;
		
		SerenityRest.rest().given()
		.when()
		.get("/"+studentId)
		.then()
		.log()
		.all()
		.statusCode(404);
		
		
		
	}
	
}

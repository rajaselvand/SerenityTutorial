package com.kgisl.junit.studentInfo;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kgisl.cucumber.serenity.StudentSerenitySteps;
import com.kgisl.model.StudentClass;
import com.kgisl.testbase.TestBase;
import com.kgisl.utils.ReusableSpecification;
import com.kgisl.utils.TestUtils;


import io.restassured.http.ContentType;



import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
@RunWith(SerenityRunner.class)
public class StudentCRUDTest extends TestBase{
	
	
	String firstName = "Rajaselvan"+TestUtils.getRandomValue();
	String lastName = "David"+TestUtils.getRandomValue();
	String programme = "Computer Science";
	String email = TestUtils.getRandomValue()+"rajaselvan@gmail.com";
	int studentId; 
	
	@Steps
	StudentSerenitySteps steps;
	
	@Title("This test will create a new student")
	@Test
	public void createStudent(){
		
		ArrayList<String> courses = new ArrayList<String>();
		courses.add("Java");
		courses.add("C++");
		
		steps.createStudent(firstName, lastName, programme, email, courses)
		.statusCode(201)
		.spec(ReusableSpecification.getGenericResponseSpec());
		
	}
	
	@Title("Verify if the student was added to the application")
	@Test
	public void getStudent(){
		createStudent();
		
	 HashMap<String,Object> value = steps.getStudentInfoByFirstName(firstName);
	 
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
		
		steps.updateStudent(studentId, firstName, lastName, programme, email, courses);
		
		/*String pl = "findAll{it.firstName=='";
		String p2 = "'}.get(0)";	*/
	//Verify firstName has value for updated student	
	 HashMap<String,Object> value = steps.getStudentInfoByFirstName(firstName);
	 
	 System.out.print("The value is: "+value);
	 
	 assertThat(value,hasValue(firstName));
	
	}

	@Title("Delete the student and verify the student is deleted!")
	@Test
	public void deleteStudent(){
		getStudent();
		
		steps.deleteStudent(studentId);
		
		steps.getStudentById(studentId)
		.statusCode(404);
		
		
		
	}
	
}

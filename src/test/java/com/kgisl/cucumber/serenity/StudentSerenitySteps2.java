package com.kgisl.cucumber.serenity;

import java.util.HashMap;
import java.util.List;

import com.kgisl.model.StudentClass;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class StudentSerenitySteps2 {
	
	@Step("Creating student with firstName:{0},lastName:{1},programme:{2},email:{3},courses:{4}")
	public ValidatableResponse createStudent(String firstName,String lastName,String programme,String email,List<String>courses){
		
		StudentClass student = new StudentClass();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setProgramme(programme);
		student.setCourses(courses);
	
		return SerenityRest.rest().given()
		.contentType(ContentType.JSON)
		.when()
		.body(student)
		.post()
		.then();
	}
	
	@Step("Getting the student information with firstName: {0}")
	public  HashMap<String,Object> getStudentInfoByFirstName(String firstName){
		String pl = "findAll{it.firstName=='";
		String p2 = "'}.get(0)";
	 return SerenityRest.rest().given()
		.when()
		.get("/list")
		.then()
		.log()
		.all()
		.statusCode(200)
		.extract()
		.path(pl+firstName+p2)
		;
	}
	
	@Step("updating student information with studentId:{0}, firstName:{1},lastName:{2},programme:{3},email:{4},courses:{5}")
	public ValidatableResponse updateStudent(int studentId, String firstName,String lastName,String programme,String email,List<String>courses){
		
		StudentClass student = new StudentClass();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setProgramme(programme);
		student.setCourses(courses);
	
		return SerenityRest.rest().given()
		.contentType(ContentType.JSON)
		.when()
		.body(student)
		.put("/"+studentId)
		.then();
	}
	
	@Step("Deleting Student information with ID: {0}")
	public void deleteStudent(int studentId){
		SerenityRest.rest().given()
		.when()
		.delete("/"+studentId)
		;
	}
	
	@Step("Getting information of the student with ID: {0}")
	public ValidatableResponse getStudentById(int studentId){
		return SerenityRest.rest().given()
				.when()
				.get("/"+studentId)
				.then();
	}

}

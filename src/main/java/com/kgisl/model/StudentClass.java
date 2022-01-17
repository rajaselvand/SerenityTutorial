package com.kgisl.model;

import java.util.List;

public class StudentClass {
	
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String programme;
	private List<String>courses;
	
	public void setId(int id){
		this.id=id;
	}

	public int getId(){
		return id;
	}
	
	public void setFirstName(String firstName){
		this.firstName=firstName;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public void setLastName(String lastName){
		this.lastName=lastName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public void setEmail(String email){
		this.email=email;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setProgramme(String programme){
		this.programme=programme;
	}
	
	public String getProgramme(){
		return programme;
	}
	
	public void setCourses(List<String> courses){
		this.courses=courses;
	}
	
	public List<String> getCourses(){
		return courses;
	}
}

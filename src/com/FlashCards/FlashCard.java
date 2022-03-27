package com.FlashCards;
//abstract class with question and answers strings 
public class FlashCard {

	

	private String questions;
	private String answers;
	
	//build contractor
	public FlashCard(String q, String a) {
		this.questions = q; 
		this.answers = a;
	}
	
	//getters and setters 
	public String getQuestions() {
		return questions;
	}


	public void setQuestions(String questions) {
		this.questions = questions;
	}


	public String getAnswers() {
		return answers;
	}


	public void setAnswers(String answers) {
		this.answers = answers;
	}

}

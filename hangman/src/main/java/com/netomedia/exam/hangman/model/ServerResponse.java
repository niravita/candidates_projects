package com.netomedia.exam.hangman.model;

public class ServerResponse {

	private String token;
	private String hangman;
	private Boolean correct;
	
	public ServerResponse() {
		
	}
	
	public ServerResponse(String token, String hangman, Boolean correct) {
		super();
		this.token = token;
		this.hangman = hangman;
		this.correct = correct;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getHangman() {
		return hangman;
	}
	public void setHangman(String hangman) {
		this.hangman = hangman;
	}
	public Boolean isCorrect() {
		return correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	
	@Override
	public String toString() {
		return "ServerResponse [token=" + token + ", hangman=" + hangman + ", correct=" + correct + "]";
	}
}

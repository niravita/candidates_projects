package com.netomedia.exam.hangman.model;

public class ServerResponse {

	private String token;
	private String hangman;
	private Boolean correct;
	private Integer failedAttempts;
	private boolean gameEnded;

	public ServerResponse() {

	}

	public ServerResponse(String token, String hangman, Boolean correct) {
		super();
		this.token = token;
		this.hangman = hangman;
		this.correct = correct;
	}

	public ServerResponse(String token, String hangman, Boolean correct, boolean gameEnded, Integer failedAttempts) {
		super();
		this.token = token;
		this.hangman = hangman;
		this.correct = correct;
		this.gameEnded = gameEnded;
		this.failedAttempts = failedAttempts;
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

	public Integer getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(Integer failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	public void setGameEnded(boolean gameEnded) {
		this.gameEnded = gameEnded;
	}

	public Boolean getCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

	public boolean getGameEnded() {
		return gameEnded;
	}

	public void setGameEnded(Boolean gameEnded) {
		this.gameEnded = gameEnded;
	}

	@Override
	public String toString() {
		return "ServerResponse [token=" + token + ", hangman=" + hangman + ", correct=" + correct + ", failedAttempts="
				+ failedAttempts + ", gameEnded=" + gameEnded + "]";
	}

}

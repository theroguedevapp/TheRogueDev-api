package br.com.theroguedev.api.exceptions;

public class UsernameOrPasswordInvalidException extends RuntimeException {
  public UsernameOrPasswordInvalidException(String message) {
    super(message);
  }
}

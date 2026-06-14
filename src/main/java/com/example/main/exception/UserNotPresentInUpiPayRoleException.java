package com.example.main.exception;

public class UserNotPresentInUpiPayRoleException extends RuntimeException
{
    public UserNotPresentInUpiPayRoleException(String message)
    {
        super(message);
    }
}

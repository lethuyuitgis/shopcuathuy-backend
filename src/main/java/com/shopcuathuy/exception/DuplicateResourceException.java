package com.shopcuathuy.exception;

/**
 * Exception thrown when trying to create a resource that already exists
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
public class DuplicateResourceException extends RuntimeException {
    
    public DuplicateResourceException(String message) {
        super(message);
    }
    
    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}

package models;

import java.time.LocalDateTime;

/**
 * 
 * The password resetting and the information attached are all handled here
 * 
 * The handled variables are the new, one-time password, expiration, and whether or not the code has already been used
 * 
 **/

public class PasswordReset {
    private String oneTimePassword;
    private LocalDateTime expirationTime;
    private boolean used;

    // The entire password reset, consisting of a one-time password, expiration, and the used tracker
    public PasswordReset(String oneTimePassword, LocalDateTime expirationTime) {
        this.oneTimePassword = oneTimePassword;
        this.expirationTime = expirationTime;
        this.used = false;
    }

    // Getters and Setters
    public String getOneTimePassword() {
        return oneTimePassword;	// Get the password
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;	// Set the password
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;	// Get the expiration
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;	// Set the expiration
    }
   
    // Tracking whether or not the one-time password is expired
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }

    // Tracking whether or not the one-time password is used
    public boolean isUsed() {
        return used;
    }
    
    // This changes a password from "not-used" to "used"
    public void setUsed(boolean used) {
        this.used = used;
    }

    // Checking if a password is valid
    public boolean isValidForUse() {
        return !isExpired() && !isUsed(); // Is the password expired or used?
    }
}

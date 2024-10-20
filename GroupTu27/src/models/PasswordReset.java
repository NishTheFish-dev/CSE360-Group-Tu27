package models;

import java.time.LocalDateTime;

/**
* The password resetting and the information attached are all handled here
* The handled variables are the new, one-time password, expiration, and whether or not the code has already been used
**/
public class PasswordReset {
    private String oneTimePassword;
    private LocalDateTime expirationTime;
    private boolean used;

    // Constructor for Password Reset
    public PasswordReset(String oneTimePassword, LocalDateTime expirationTime) {
        this.oneTimePassword = oneTimePassword;
        this.expirationTime = expirationTime;
        this.used = false; // Initially, the password is unused
    }

    // Getters and Setters
    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    // Tracking whether or not the one-time password is expired
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }

    // Tracking whether or not the one-time password is used
    public boolean isUsed() {
        return used;
    }

    // Mark the password as used
    public void setUsed(boolean used) {
        this.used = used;
    }

    // Check if the password can still be used (i.e., not expired and not used)
    public boolean isValidForUse() {
        return !isExpired() && !isUsed();
    }
}

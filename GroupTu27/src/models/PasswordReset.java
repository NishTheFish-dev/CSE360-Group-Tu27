package models;

import java.time.LocalDateTime;

public class PasswordReset {
    private String oneTimePassword;
    private LocalDateTime expirationTime;
    private boolean used;

    public PasswordReset(String oneTimePassword, LocalDateTime expirationTime) {
        this.oneTimePassword = oneTimePassword;
        this.expirationTime = expirationTime;
        this.used = false;
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

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isValidForUse() {
        return !isExpired() && !isUsed();
    }
}

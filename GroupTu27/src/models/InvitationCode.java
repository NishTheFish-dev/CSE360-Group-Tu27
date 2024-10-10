package models;

import java.time.LocalDateTime;

/**
 * 
 * The Invitation codes and the information attached are all handled here
 * 
 * The handled variables are the code, role, and expiration
 * 
 **/

public class InvitationCode {
    private String code;
    private Role assignedRole;
    private LocalDateTime expirationTime;

    // The entire invitation code consisting of the code, role, and expiration
    public InvitationCode(String code, Role assignedRole, LocalDateTime expirationTime) {
        this.code = code;
        this.assignedRole = assignedRole;
        this.expirationTime = expirationTime;
    }

    // Getters and Setters
    public String getCode() {
        return code;	// Get the code
    }

    public void setCode(String code) {
        this.code = code;	// Set the code
    }

    public Role getAssignedRole() {
        return assignedRole;	// Get the assigned role
    }

    public void setAssignedRole(Role assignedRole) {
        this.assignedRole = assignedRole;	// Set the assigned role
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;	// Get the expiration time
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;	// Set the expiration time
    }

    // This function keeps track of whether or not a code is expired
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);		
    }
}

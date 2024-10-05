package models;

import java.time.LocalDateTime;

public class InvitationCode {
    private String code;
    private Role assignedRole;
    private LocalDateTime expirationTime;

    public InvitationCode(String code, Role assignedRole, LocalDateTime expirationTime) {
        this.code = code;
        this.assignedRole = assignedRole;
        this.expirationTime = expirationTime;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Role getAssignedRole() {
        return assignedRole;
    }

    public void setAssignedRole(Role assignedRole) {
        this.assignedRole = assignedRole;
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
}

package models;

/**
 * 
 * The list of roles, and their assignments are handled here
 * 
 * The role names is the only variable handled here
 * 
 **/

public class Role {
    private String roleName;

    // The roles are checked via their role name
    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;	// Get role name
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;	// Set role name
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Role role = (Role) obj;
        return roleName.equals(role.roleName);
    }

    // Return the hashcode of the role name
    @Override
    public int hashCode() {
        return roleName.hashCode();
    }

    // Return role name as a string
    @Override
    public String toString() {
        return roleName;
    }
}

/**
 * The UserFacade interface provides methods for managing users in the system.
 * It defines operations such as adding, updating, and retrieving user information.
 */
package com.rc.porms.appl.facade.user;
import com.rc.porms.appl.model.user.User;

import java.util.List;

public interface UserFacade {

    /**
     * Retrieves a user based on their username.
     *
     * @param username The username of the user.
     * @return The user object corresponding to the provided username, or null if not found.
     */
    User getUserByUsername(String username);

    List<User> getAllUsers();

    void setLocked(String username);

    void setUnLocked(String username);
}

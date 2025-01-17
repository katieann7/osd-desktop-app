package com.rc.porms;

import com.rc.porms.appl.facade.user.UserFacade;
import com.rc.porms.appl.facade.user.impl.UserFacadeImpl;
import com.rc.porms.data.user.dao.UserDao;
import com.rc.porms.data.user.dao.impl.UserDaoImpl;

public class UserInfoMgtApplication {
    private UserFacade userFacade;

    /**
     * This creates a new com.user.information.management.UserInfoMgtApplication
     *
     * @return the userFacade this helps for managing student data.
     */
    public UserInfoMgtApplication() {
        UserDao userDaoImpl = new UserDaoImpl();
        this.userFacade = new UserFacadeImpl(userDaoImpl);
    }

}


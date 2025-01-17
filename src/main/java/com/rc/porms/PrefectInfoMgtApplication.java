package com.rc.porms;


import com.rc.porms.appl.facade.prefect.communityservice.CommunityServiceFacade;
import com.rc.porms.appl.facade.prefect.communityservice.impl.CommunityServiceFacadeImpl;
import com.rc.porms.appl.facade.prefect.offense.OffenseFacade;
import com.rc.porms.appl.facade.prefect.offense.impl.OffenseFacadeImpl;
import com.rc.porms.appl.facade.prefect.violation.ViolationFacade;
import com.rc.porms.appl.facade.prefect.violation.impl.ViolationFacadeImpl;
import com.rc.porms.data.prefect.communityservice.CommunityServiceDao;
import com.rc.porms.data.prefect.communityservice.dao.impl.CommunityServiceDaoImpl;
import com.rc.porms.data.prefect.offense.OffenseDao;
import com.rc.porms.data.prefect.offense.dao.impl.OffenseDaoImpl;
import com.rc.porms.data.prefect.violation.ViolationDao;
import com.rc.porms.data.prefect.violation.dao.impl.ViolationDaoImpl;

public class PrefectInfoMgtApplication {
    private OffenseFacade offenseFacade;
    private ViolationFacade violationFacade;
    private CommunityServiceFacade communityserviceFacade;


    /**
     * This creates a new com.prefect.information.management.PrefectInfoMgtApplication
     *
     * @return the PrefectFacade this helps for managing student data.
     */

    public PrefectInfoMgtApplication(){

        CommunityServiceDao communityServiceDaoImpl = new CommunityServiceDaoImpl();
        this.communityserviceFacade = new CommunityServiceFacadeImpl(communityServiceDaoImpl);

        OffenseDao offenseDaoImpl = new OffenseDaoImpl();
        this.offenseFacade = new OffenseFacadeImpl(offenseDaoImpl);

        ViolationDao violationDaoImpl = new ViolationDaoImpl();
        this.violationFacade = new ViolationFacadeImpl(violationDaoImpl);

    }
    public CommunityServiceFacade getCommunityserviceFacade() {
        return communityserviceFacade;
    }

    public OffenseFacade getOffenseFacade() {
        return offenseFacade;
    }

    public ViolationFacade getViolationFacade() {
        return violationFacade;
    }

}


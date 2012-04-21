package model.availableapioverrides.services;

import model.availableapioverrides.views.AAOJoinedUpdtVOImpl;
import model.availableapioverrides.views.AvailApiBlockfacesVwRoVOImpl;
import model.availableapioverrides.views.AvailApiBlocksVwRoVOImpl;
import model.availableapioverrides.views.AvailApiOspInventoryVwRoVOImpl;
import model.availableapioverrides.views.AvailApiPmDistrictsVwRoVOImpl;

import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Feb 27 21:50:03 PST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AvailApiOverridesAMImpl extends ApplicationModuleImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public AvailApiOverridesAMImpl() {
    }

    /**
     * Container's getter for AvailApiOverridesUpdtVO1.
     * @return AvailApiOverridesUpdtVO1
     */
    public ViewObjectImpl getAvailApiOverridesUpdtVO1() {
        return (ViewObjectImpl)findViewObject("AvailApiOverridesUpdtVO1");
    }

    /**
     * Container's getter for AvailApiBlockfacesVwRoVO1.
     * @return AvailApiBlockfacesVwRoVO1
     */
    public AvailApiBlockfacesVwRoVOImpl getAvailApiBlockfacesVwRoVO1() {
        return (AvailApiBlockfacesVwRoVOImpl)findViewObject("AvailApiBlockfacesVwRoVO1");
    }

    /**
     * Container's getter for AvailApiBlocksVwRoVO1.
     * @return AvailApiBlocksVwRoVO1
     */
    public AvailApiBlocksVwRoVOImpl getAvailApiBlocksVwRoVO1() {
        return (AvailApiBlocksVwRoVOImpl)findViewObject("AvailApiBlocksVwRoVO1");
    }

    /**
     * Container's getter for AvailApiOspInventoryVwRoVO1.
     * @return AvailApiOspInventoryVwRoVO1
     */
    public AvailApiOspInventoryVwRoVOImpl getAvailApiOspInventoryVwRoVO1() {
        return (AvailApiOspInventoryVwRoVOImpl)findViewObject("AvailApiOspInventoryVwRoVO1");
    }

    /**
     * Container's getter for AvailApiPmDistrictsVwRoVO1.
     * @return AvailApiPmDistrictsVwRoVO1
     */
    public AvailApiPmDistrictsVwRoVOImpl getAvailApiPmDistrictsVwRoVO1() {
        return (AvailApiPmDistrictsVwRoVOImpl)findViewObject("AvailApiPmDistrictsVwRoVO1");
    }

    /**
     * Container's getter for AAOJoinedUpdtVO1.
     * @return AAOJoinedUpdtVO1
     */
    public AAOJoinedUpdtVOImpl getAAOJoinedUpdtVO1() {
        return (AAOJoinedUpdtVOImpl)findViewObject("AAOJoinedUpdtVO1");
    }
}
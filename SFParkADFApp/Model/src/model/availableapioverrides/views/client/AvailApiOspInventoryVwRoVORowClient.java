package model.availableapioverrides.views.client;

import oracle.jbo.client.remote.RowImpl;
import oracle.jbo.domain.Number;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Feb 27 21:30:31 PST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AvailApiOspInventoryVwRoVORowClient extends RowImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public AvailApiOspInventoryVwRoVORowClient() {
    }

    public Number getOspId() {
        return (Number)getAttribute("OspId");
    }

    public void setOspId(Number value) {
        setAttribute("OspId", value);
    }
}

package model.availableapioverrides.views.client;

import oracle.jbo.client.remote.RowImpl;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.RowID;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Feb 27 12:46:19 PST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AvailApiPmDistrictsVwRoVORowClient extends RowImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public AvailApiPmDistrictsVwRoVORowClient() {
    }

    public void setSelectedPmDistrict(Boolean value) {
        setAttribute("SelectedPmDistrict", value);
    }

    public void setRowID(RowID value) {
        setAttribute("RowID", value);
    }

    public void setPmDistrictName(String value) {
        setAttribute("PmDistrictName", value);
    }

    public void setPmDistrictId(Number value) {
        setAttribute("PmDistrictId", value);
    }

    public Boolean getSelectedPmDistrict() {
        return (Boolean)getAttribute("SelectedPmDistrict");
    }

    public RowID getRowID() {
        return (RowID)getAttribute("RowID");
    }

    public String getPmDistrictName() {
        return (String)getAttribute("PmDistrictName");
    }

    public Number getPmDistrictId() {
        return (Number)getAttribute("PmDistrictId");
    }
}

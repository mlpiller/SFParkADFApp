package model.availableapioverrides.views.common;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.RowID;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Feb 27 12:46:19 PST 2012
// ---------------------------------------------------------------------
public interface AvailApiPmDistrictsVwRoVORow extends Row {
    void setSelectedPmDistrict(Boolean value);

    void setRowID(RowID value);

    void setPmDistrictName(String value);

    void setPmDistrictId(Number value);

    Boolean getSelectedPmDistrict();

    RowID getRowID();

    String getPmDistrictName();

    Number getPmDistrictId();
}

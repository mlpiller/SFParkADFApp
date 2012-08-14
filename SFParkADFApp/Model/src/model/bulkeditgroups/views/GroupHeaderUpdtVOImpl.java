package model.bulkeditgroups.views;

import model.bulkeditgroups.views.common.GroupHeaderUpdtVO;

import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jun 27 18:49:50 PDT 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class GroupHeaderUpdtVOImpl extends ViewObjectImpl implements GroupHeaderUpdtVO {
    /**
     * This is the default constructor (do not remove).
     */
    public GroupHeaderUpdtVOImpl() {
    }

    /**
     * Returns the bind variable value for CreateDateRangeStart.
     * @return bind variable value for CreateDateRangeStart
     */
    public Date getCreateDateRangeStart() {
        return (Date)getNamedWhereClauseParam("CreateDateRangeStart");
    }

    /**
     * Sets <code>value</code> for bind variable CreateDateRangeStart.
     * @param value value to bind as CreateDateRangeStart
     */
    public void setCreateDateRangeStart(Date value) {
        setNamedWhereClauseParam("CreateDateRangeStart", value);
    }

    /**
     * Returns the bind variable value for CreateDateRangeEnd.
     * @return bind variable value for CreateDateRangeEnd
     */
    public Date getCreateDateRangeEnd() {
        return (Date)getNamedWhereClauseParam("CreateDateRangeEnd");
    }

    /**
     * Sets <code>value</code> for bind variable CreateDateRangeEnd.
     * @param value value to bind as CreateDateRangeEnd
     */
    public void setCreateDateRangeEnd(Date value) {
        setNamedWhereClauseParam("CreateDateRangeEnd", value);
    }
}
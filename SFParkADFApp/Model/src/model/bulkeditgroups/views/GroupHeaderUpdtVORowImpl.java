package model.bulkeditgroups.views;

import model.bulkeditgroups.entities.common.SdoGeometry;

import oracle.jbo.RowIterator;
import oracle.jbo.domain.ClobDomain;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jun 27 18:52:27 PDT 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class GroupHeaderUpdtVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CreatedDt {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getCreatedDt();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setCreatedDt((Timestamp)value);
            }
        }
        ,
        EffFromDt {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getEffFromDt();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setEffFromDt((Date)value);
            }
        }
        ,
        EffToDt {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getEffToDt();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setEffToDt((Date)value);
            }
        }
        ,
        Geom {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getGeom();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setGeom((SdoGeometry)value);
            }
        }
        ,
        GroupDesc {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getGroupDesc();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setGroupDesc((String)value);
            }
        }
        ,
        GroupId {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getGroupId();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setGroupId((DBSequence)value);
            }
        }
        ,
        GroupName {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getGroupName();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setGroupName((String)value);
            }
        }
        ,
        GroupType {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getGroupType();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setGroupType((String)value);
            }
        }
        ,
        LastUpdDt {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getLastUpdDt();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setLastUpdDt((Timestamp)value);
            }
        }
        ,
        LastUpdPgm {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getLastUpdPgm();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setLastUpdPgm((String)value);
            }
        }
        ,
        LastUpdUser {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getLastUpdUser();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setLastUpdUser((String)value);
            }
        }
        ,
        Spaces {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getSpaces();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setSpaces((ClobDomain)value);
            }
        }
        ,
        SelectedGroupHeaderRow {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getSelectedGroupHeaderRow();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setSelectedGroupHeaderRow((Boolean)value);
            }
        }
        ,
        BlockGroupsUpdtVO {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getBlockGroupsUpdtVO();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ParkingSpaceGroupsUpdtVO {
            public Object get(GroupHeaderUpdtVORowImpl obj) {
                return obj.getParkingSpaceGroupsUpdtVO();
            }

            public void put(GroupHeaderUpdtVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(GroupHeaderUpdtVORowImpl object);

        public abstract void put(GroupHeaderUpdtVORowImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int CREATEDDT = AttributesEnum.CreatedDt.index();
    public static final int EFFFROMDT = AttributesEnum.EffFromDt.index();
    public static final int EFFTODT = AttributesEnum.EffToDt.index();
    public static final int GEOM = AttributesEnum.Geom.index();
    public static final int GROUPDESC = AttributesEnum.GroupDesc.index();
    public static final int GROUPID = AttributesEnum.GroupId.index();
    public static final int GROUPNAME = AttributesEnum.GroupName.index();
    public static final int GROUPTYPE = AttributesEnum.GroupType.index();
    public static final int LASTUPDDT = AttributesEnum.LastUpdDt.index();
    public static final int LASTUPDPGM = AttributesEnum.LastUpdPgm.index();
    public static final int LASTUPDUSER = AttributesEnum.LastUpdUser.index();
    public static final int SPACES = AttributesEnum.Spaces.index();
    public static final int SELECTEDGROUPHEADERROW = AttributesEnum.SelectedGroupHeaderRow.index();
    public static final int BLOCKGROUPSUPDTVO = AttributesEnum.BlockGroupsUpdtVO.index();
    public static final int PARKINGSPACEGROUPSUPDTVO = AttributesEnum.ParkingSpaceGroupsUpdtVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public GroupHeaderUpdtVORowImpl() {
    }

    /**
     * Gets GroupHeaderEO entity object.
     * @return the GroupHeaderEO
     */
    public EntityImpl getGroupHeaderEO() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for CREATED_DT using the alias name CreatedDt.
     * @return the CREATED_DT
     */
    public Timestamp getCreatedDt() {
        return (Timestamp) getAttributeInternal(CREATEDDT);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED_DT using the alias name CreatedDt.
     * @param value value to set the CREATED_DT
     */
    public void setCreatedDt(Timestamp value) {
        setAttributeInternal(CREATEDDT, value);
    }

    /**
     * Gets the attribute value for EFF_FROM_DT using the alias name EffFromDt.
     * @return the EFF_FROM_DT
     */
    public Date getEffFromDt() {
        return (Date) getAttributeInternal(EFFFROMDT);
    }

    /**
     * Sets <code>value</code> as attribute value for EFF_FROM_DT using the alias name EffFromDt.
     * @param value value to set the EFF_FROM_DT
     */
    public void setEffFromDt(Date value) {
        setAttributeInternal(EFFFROMDT, value);
    }

    /**
     * Gets the attribute value for EFF_TO_DT using the alias name EffToDt.
     * @return the EFF_TO_DT
     */
    public Date getEffToDt() {
        return (Date) getAttributeInternal(EFFTODT);
    }

    /**
     * Sets <code>value</code> as attribute value for EFF_TO_DT using the alias name EffToDt.
     * @param value value to set the EFF_TO_DT
     */
    public void setEffToDt(Date value) {
        setAttributeInternal(EFFTODT, value);
    }

    /**
     * Gets the attribute value for GEOM using the alias name Geom.
     * @return the GEOM
     */
    public SdoGeometry getGeom() {
        return (SdoGeometry)getAttributeInternal(GEOM);
    }

    /**
     * Sets <code>value</code> as attribute value for GEOM using the alias name Geom.
     * @param value value to set the GEOM
     */
    public void setGeom(SdoGeometry value) {
        setAttributeInternal(GEOM, value);
    }

    /**
     * Gets the attribute value for GROUP_DESC using the alias name GroupDesc.
     * @return the GROUP_DESC
     */
    public String getGroupDesc() {
        return (String) getAttributeInternal(GROUPDESC);
    }

    /**
     * Sets <code>value</code> as attribute value for GROUP_DESC using the alias name GroupDesc.
     * @param value value to set the GROUP_DESC
     */
    public void setGroupDesc(String value) {
        setAttributeInternal(GROUPDESC, value);
    }

    /**
     * Gets the attribute value for GROUP_ID using the alias name GroupId.
     * @return the GROUP_ID
     */
    public DBSequence getGroupId() {
        return (DBSequence)getAttributeInternal(GROUPID);
    }

    /**
     * Sets <code>value</code> as attribute value for GROUP_ID using the alias name GroupId.
     * @param value value to set the GROUP_ID
     */
    public void setGroupId(DBSequence value) {
        setAttributeInternal(GROUPID, value);
    }

    /**
     * Gets the attribute value for GROUP_NAME using the alias name GroupName.
     * @return the GROUP_NAME
     */
    public String getGroupName() {
        return (String) getAttributeInternal(GROUPNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for GROUP_NAME using the alias name GroupName.
     * @param value value to set the GROUP_NAME
     */
    public void setGroupName(String value) {
        setAttributeInternal(GROUPNAME, value);
    }

    /**
     * Gets the attribute value for GROUP_TYPE using the alias name GroupType.
     * @return the GROUP_TYPE
     */
    public String getGroupType() {
        return (String) getAttributeInternal(GROUPTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for GROUP_TYPE using the alias name GroupType.
     * @param value value to set the GROUP_TYPE
     */
    public void setGroupType(String value) {
        setAttributeInternal(GROUPTYPE, value);
    }

    /**
     * Gets the attribute value for LAST_UPD_DT using the alias name LastUpdDt.
     * @return the LAST_UPD_DT
     */
    public Timestamp getLastUpdDt() {
        return (Timestamp) getAttributeInternal(LASTUPDDT);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_UPD_DT using the alias name LastUpdDt.
     * @param value value to set the LAST_UPD_DT
     */
    public void setLastUpdDt(Timestamp value) {
        setAttributeInternal(LASTUPDDT, value);
    }

    /**
     * Gets the attribute value for LAST_UPD_PGM using the alias name LastUpdPgm.
     * @return the LAST_UPD_PGM
     */
    public String getLastUpdPgm() {
        return (String) getAttributeInternal(LASTUPDPGM);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_UPD_PGM using the alias name LastUpdPgm.
     * @param value value to set the LAST_UPD_PGM
     */
    public void setLastUpdPgm(String value) {
        setAttributeInternal(LASTUPDPGM, value);
    }

    /**
     * Gets the attribute value for LAST_UPD_USER using the alias name LastUpdUser.
     * @return the LAST_UPD_USER
     */
    public String getLastUpdUser() {
        return (String) getAttributeInternal(LASTUPDUSER);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_UPD_USER using the alias name LastUpdUser.
     * @param value value to set the LAST_UPD_USER
     */
    public void setLastUpdUser(String value) {
        setAttributeInternal(LASTUPDUSER, value);
    }

    /**
     * Gets the attribute value for SPACES using the alias name Spaces.
     * @return the SPACES
     */
    public ClobDomain getSpaces() {
        return (ClobDomain) getAttributeInternal(SPACES);
    }

    /**
     * Sets <code>value</code> as attribute value for SPACES using the alias name Spaces.
     * @param value value to set the SPACES
     */
    public void setSpaces(ClobDomain value) {
        setAttributeInternal(SPACES, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SelectedGroupHeaderRow.
     * @return the SelectedGroupHeaderRow
     */
    public Boolean getSelectedGroupHeaderRow() {
        return (Boolean) getAttributeInternal(SELECTEDGROUPHEADERROW);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SelectedGroupHeaderRow.
     * @param value value to set the  SelectedGroupHeaderRow
     */
    public void setSelectedGroupHeaderRow(Boolean value) {
        setAttributeInternal(SELECTEDGROUPHEADERROW, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link BlockGroupsUpdtVO.
     */
    public RowIterator getBlockGroupsUpdtVO() {
        return (RowIterator)getAttributeInternal(BLOCKGROUPSUPDTVO);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link ParkingSpaceGroupsUpdtVO.
     */
    public RowIterator getParkingSpaceGroupsUpdtVO() {
        return (RowIterator)getAttributeInternal(PARKINGSPACEGROUPSUPDTVO);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value,
                                         AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}
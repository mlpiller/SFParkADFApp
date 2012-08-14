package bulkeditgroups.backing;

import bulkeditgroups.common.BulkEditUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import model.bulkeditgroups.services.GroupsAMImpl;

import model.bulkeditgroups.views.BlockGroupsUpdtVORowImpl;
import model.bulkeditgroups.views.BlocksRoVORowImpl;
import model.bulkeditgroups.views.ParkingSpaceGroupsUpdtVOImpl;

import model.bulkeditgroups.views.ParkingSpaceGroupsUpdtVORowImpl;

import model.bulkeditgroups.views.ParkingSpaceInventoryRoVORowImpl;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichQuery;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.QueryEvent;

import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
/**
 * Description:
 * This class controls behavior of the selectParkingSpacesPage.jspx
 *
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120626-01 Mark Piller - Oracle Consulting  Created this class file
 */
public class SelectParkingSpaces {
    private static ADFLogger adfLogger = ADFLogger.createADFLogger(SelectParkingSpaces.class);

    private RichTable parkingSpaceTable;
    private String userAction = "";
    private static String ALL_PARKING_SPACES = "ALL_PARKING_SPACES";
    private static String SELECTED_PARKING_SPACES = "SELECTED_PARKING_SPACES";
    private RichQuery parkingSpaceInventoryQuery;

    public SelectParkingSpaces() {
    }

    public void setUserAction(String userAction){
        this.userAction = userAction;
    }
    
    public String getUserAction(){
        return userAction;
    }

    public void setParkingSpaceTable(RichTable parkingSpaceTable) {
        this.parkingSpaceTable = parkingSpaceTable;
    }

    public RichTable getParkingSpaceTable() {
        return parkingSpaceTable;
    }

    public void saveSelectedParkingSpacesButtonHandler(ActionEvent actionEvent) {
        // pass to calling view the user selected button
        userAction = "save";

        saveParkingSpaces(SELECTED_PARKING_SPACES);
    } // saveSelectedParkingSpacesButtonHandler

    public void saveParkingSpaces(String typeOfSave){
        boolean getAnotherRow = false;
        boolean rowIsSelectedForAddToGroup = false;
        boolean saveAllParkingSpaces = false;
        Number parkingSpaceGroupId = new Number(0);

        if(typeOfSave.equals(ALL_PARKING_SPACES)){
            saveAllParkingSpaces = true;
        }

        // get the Group ID for the Selected Block Group
        // this was passed from adf-config as a parameter
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        parkingSpaceGroupId = (Number)adfFacesContext.getPageFlowScope().get("groupId");

        // Get the application module
        GroupsAMImpl groupsAM = getApplicationModule();
        
        // Get the Parking Spaces Groups View Object for inserting new Parking Spaces
        ParkingSpaceGroupsUpdtVOImpl parkingSpacesGroupsVO = (ParkingSpaceGroupsUpdtVOImpl)groupsAM.getParkingSpaceGroupsUpdtVO1();
        // Set row currency to the beginning of the View Object
        parkingSpacesGroupsVO.reset();

        // Identify the Parking Spaces rows selected for adding to the Parking Spaces Groups table
        CollectionModel parkingSpaceModel = (CollectionModel)parkingSpaceTable.getValue();
        JUCtrlHierBinding parkingSpaceBinding = (JUCtrlHierBinding)parkingSpaceModel.getWrappedData();
        DCIteratorBinding parkingSpaceDCIteratorBinding = parkingSpaceBinding.getDCIteratorBinding();
        RowSetIterator parkingSpaceRSIterator = parkingSpaceDCIteratorBinding.getRowSetIterator();
        adfLogger.info("\tDEBUG:  RSIterator START Row Count: " + parkingSpaceRSIterator.getRowCount());
        parkingSpaceRSIterator.reset(); // set currency to beginning of the table

        if(parkingSpaceRSIterator.getRowCount() > 0 || parkingSpaceRSIterator.hasNext()){
            getAnotherRow = true;
        }
        
        while(getAnotherRow){
            ParkingSpaceInventoryRoVORowImpl parkingSpaceRow = (ParkingSpaceInventoryRoVORowImpl)parkingSpaceRSIterator.getCurrentRow();
            adfLogger.info("\tDEBUG:  Parking Space ID: " + parkingSpaceRow.getParkingSpaceId());

            if(parkingSpaceRow.getSelectedParkingSpaceRow() != null){
                rowIsSelectedForAddToGroup = parkingSpaceRow.getSelectedParkingSpaceRow();
            } else {
                rowIsSelectedForAddToGroup = false;
            } // if(parkingSpaceRow.getSelectedParkingSpaceGroupRow()

            if(saveAllParkingSpaces){
                rowIsSelectedForAddToGroup = true;
            }

            // If the row is selected...
            // Insert the row into the Parking Spaces Group Table
            if(rowIsSelectedForAddToGroup){
                ParkingSpaceGroupsUpdtVORowImpl parkingSpaceGroupsRow = (ParkingSpaceGroupsUpdtVORowImpl)parkingSpacesGroupsVO.createRow();

                parkingSpaceGroupsRow.setNewRowState(parkingSpaceGroupsRow.STATUS_INITIALIZED);
                parkingSpaceGroupsRow.setPsGroupId(parkingSpaceGroupId);
                parkingSpaceGroupsRow.setParkingSpaceId(parkingSpaceRow.getParkingSpaceId());
                parkingSpaceGroupsRow.setLastUpdPgm("ADFTools");
                parkingSpaceGroupsRow.setLastUpdUser(BulkEditUtils.getLoginId());

                parkingSpacesGroupsVO.insertRow(parkingSpaceGroupsRow);
            } // if(rowIsSelectedForAddToGroup)

            if(parkingSpaceRSIterator.hasNext()){
                parkingSpaceRSIterator.next();
            } else {
                getAnotherRow = false;
            }

        } // while(getAnotherRow)

        // remove the query parameters the user put in to search
        // for these Parking Spaces
        BulkEditUtils.resetQueryPanelValues(this.getParkingSpaceInventoryQuery());
        
    } // saveParkingSpaces

    public GroupsAMImpl getApplicationModule() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data.GroupsAMDataControl.dataProvider}",
                                            Object.class);
        return (GroupsAMImpl)valueExp.getValue(elContext);
    } // getApplicationModule


    public void cancelParkingSpaceSelectionsButtonHandler(ActionEvent actionEvent) {
        // remove the query parameters the user put in to search
        // for these Parking Spaces
        BulkEditUtils.resetQueryPanelValues(this.getParkingSpaceInventoryQuery());

        // pass to calling view the user selected button
        userAction = "cancel";
    } // cancelParkingSpaceSelectionsButtonHandler

    public void selectAllParkingSpacesButtonHandler(ActionEvent actionEvent){
        // pass to calling view the user selected button
        userAction = "save";

        saveParkingSpaces(ALL_PARKING_SPACES);
    } // selectAllParkingSpacesButtonHandler

    public void setParkingSpaceInventoryQuery(RichQuery parkingSpaceInventoryQuery) {
        this.parkingSpaceInventoryQuery = parkingSpaceInventoryQuery;
    }

    public RichQuery getParkingSpaceInventoryQuery() {
        return parkingSpaceInventoryQuery;
    }

    /**
     * This replaces the default query event for the search panel.
     * The need for this is that the logic must prevent display of any
     * Parking Space IDs that are already included in the GROUP assignment.
     * Otherwise there will be an insert error because of uniqueness on the index
     * 
     * For more information:  
     * How-to tell the ViewCriteria a use chose in an af:query component
     * https://blogs.oracle.com/jdevotnharvest/entry/how-to_tell_the_viewcriteria_a_user_chose_in_an_afquery_component
     * http://www.oracle.com/technetwork/developer-tools/adf/learnmore/dec2010-otn-harvest-199274.pdf
     * 
     * @param queryEvent
     */
    public void onQueryListenerHandler(QueryEvent queryEvent) {
       BulkEditUtils.queryListenerHandler(queryEvent, parkingSpaceTable);
    } // onQueryListenerHandler

} // SelectParkingSpaces 

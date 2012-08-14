package bulkeditgroups.backing;


import bulkeditgroups.common.ADFUtils;
import bulkeditgroups.common.BulkEditUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.PhaseEvent;

import model.bulkeditgroups.services.GroupsAMImpl;
import model.bulkeditgroups.views.GroupHeaderUpdtVORowImpl;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.nav.RichCommandToolbarButton;
import oracle.adf.view.rich.component.rich.output.RichMessages;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.QueryEvent;

import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import javax.faces.application.Application;
import javax.el.ExpressionFactory;
import javax.el.ELContext;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;

import model.bulkeditgroups.views.BlockGroupsUpdtVOImpl;
import model.bulkeditgroups.views.BlockGroupsUpdtVORowImpl;
import model.bulkeditgroups.views.BlocksRoVOImpl;
import model.bulkeditgroups.views.BlocksRoVORowImpl;
import model.bulkeditgroups.views.GroupHeaderUpdtVOImpl;
import model.bulkeditgroups.views.ParkingSpaceGroupsUpdtVOImpl;
import model.bulkeditgroups.views.ParkingSpaceGroupsUpdtVORowImpl;

import model.bulkeditgroups.views.ParkingSpaceInventoryRoVOImpl;

import oracle.adf.controller.v2.context.LifecycleContext;
import oracle.adf.controller.v2.lifecycle.Lifecycle;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;

/**
 * Description:
 * This class controls behavior of the groupHeaderListingPage.jspx
 *
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120626-01 Mark Piller - Oracle Consulting  Created this class file
 */
public class GroupHeaderListing {
    private static ADFLogger adfLogger = ADFLogger.createADFLogger(GroupHeaderListing.class);

    private RichPanelGroupLayout detailsPanelGroupLayout;
    private RichTable groupHeaderTable;
    private RichTable parkingSpaceGroupsTable;
    private RichTable blockGroupsTable;

    private RichOutputText groupHeaderMessages;
    private RichPanelHeader groupHeaderButtonPanelHeader;

    private RichOutputText blockGroupMessages;
    private RichPanelHeader blockGroupsButtonPanelHeader;

    private RichOutputText parkingSpaceGroupMessages;

    private String userAddEditGroupAction = "";
    private String userAddBlocksAction = "";
    private String userAddParkingSpacesAction = "";
    private String switcherFacet = "";
    private String groupHeaderAction = null;
    private String version = "";
    private String groupName = "";

    private static String PRK_SPACE = "PRK_SPACE";
    private static String BLOCK = "BLOCK";
    private static String SELECTED_ROWS = "SelectedRows";
    private static String ALL_ROWS = "AllRows";

    private Number groupId;

    private RichCommandToolbarButton saveGroupHeaderButton;
    private RichCommandToolbarButton saveBlockGroupsButton;
    private RichCommandToolbarButton saveParkingSpaceGroupsButton;
    private RichPanelHeader blockGroupPanelHeader;
    private RichPanelHeader parkingSpaceGroupPanelHeader;

    public GroupHeaderListing() {
    }

    public void setGroupName(String groupName){
        this.groupName = groupName;
    }

    public String getGroupName(){
        return groupName;
    }
    
    public void setUserAddBlocksAction(String userAction){
        this.userAddBlocksAction = userAction;
    }

    public String getUserAddBlocksAction(){
        return userAddBlocksAction;
    }
    
    public void setUserAddParkingSpacesAction(String userAction){
        this.userAddParkingSpacesAction = userAction;
    }
    
    public String getUserAddParkingSpacesAction(){
        return userAddParkingSpacesAction;
    }
    
    public void setUserAddEditGroupAction(String userAction){
        this.userAddEditGroupAction = userAction;
    }

    public String getUserAddEditGroupAction(){
        return userAddEditGroupAction;
    }
    
    public void setGroupId(Number groupId){
        this.groupId = groupId;
    }
    
    public Number getGroupId(){
        return groupId;
    }

    public void setSwitcherFacet(String switcherFacet) {
        this.switcherFacet = switcherFacet;
    }

    public String getSwitcherFacet() {
        return switcherFacet;
    }

    public void setDetailsPanelGroupLayout(RichPanelGroupLayout detailsPanelGroupLayout) {
        this.detailsPanelGroupLayout = detailsPanelGroupLayout;
    }

    public RichPanelGroupLayout getDetailsPanelGroupLayout() {
        return detailsPanelGroupLayout;
    }

    public void setGroupHeaderTable(RichTable groupHeaderTable) {
        this.groupHeaderTable = groupHeaderTable;
    }

    public RichTable getGroupHeaderTable() {
        return groupHeaderTable;
    }

    public void setParkingSpaceGroupsTable(RichTable parkingSpaceGroupsTable) {
        this.parkingSpaceGroupsTable = parkingSpaceGroupsTable;
    }

    public RichTable getParkingSpaceGroupsTable() {
        return parkingSpaceGroupsTable;
    }

    public void setBlockGroupsTable(RichTable blockGroupsTable) {
        this.blockGroupsTable = blockGroupsTable;
    }

    public RichTable getBlockGroupsTable() {
        return blockGroupsTable;
    }

    /**
     * groupHeaderTableRowSelectionListenerHandler
     * 
     * This changes the details table based on the user's selection on the master table
     * 
     * @param selectionEvent
     */
    public void groupHeaderTableRowSelectionHandler(SelectionEvent selectionEvent) {
        setUpdateCredentials();
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        Number selectedGroupId = new Number(0);
        
        // invoke the default collection listener
        // this preserves the default functionality of the select table row (make active)
        ADFUtils.invokeEL("#{bindings.GroupHeaderUpdtVO1.collectionModel.makeCurrent}",new Class[] { SelectionEvent.class },new Object[] { selectionEvent });

        // through the bindings... get the table that the selection took place on
        RichTable selectionTable = (RichTable)selectionEvent.getSource();
        CollectionModel model = (CollectionModel)selectionTable.getValue();
        JUCtrlHierBinding tableBinding = (JUCtrlHierBinding)model.getWrappedData();

        // get the row that was selected and identify the group
        // save the Group ID to memory
        RowKeySet rks = selectionEvent.getAddedSet();
        Iterator iterator = rks.iterator();
        if (iterator.hasNext()){
            List rowKey = (List)iterator.next();
            JUCtrlHierNodeBinding node = tableBinding.findNodeByKeyPath(rowKey);
            // Row row = node.getRow();
            GroupHeaderUpdtVORowImpl groupHeaderRow = (GroupHeaderUpdtVORowImpl)node.getRow();
            //switcherFacet = row.getAttribute("GroupType").toString();
            switcherFacet = groupHeaderRow.getGroupType();
            selectedGroupId = groupHeaderRow.getGroupId().getSequenceNumber();
            groupName = groupHeaderRow.getGroupName();
            groupId = selectedGroupId;
            adfLogger.info("\tDEBUG:  Selected Group ID is: " + selectedGroupId);
            adfFacesContext.getPageFlowScope().put("selectedGroupId", selectedGroupId);
            adfLogger.info("\tDEBUG:  setting switcherFacet to: " + switcherFacet);
        } // if (iterator.hasNext())

        // refresh the panel group where the switcher so the display changes
        AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
        setMessage(groupHeaderMessages, "Green","");
        if(switcherFacet.equals(PRK_SPACE)){
            setMessage(parkingSpaceGroupMessages, "Green","");
            adfFacesCtx.addPartialTarget(parkingSpaceGroupMessages);
        } else if(switcherFacet.equals(BLOCK)) {
            setMessage(blockGroupMessages, "Green","");
            adfFacesCtx.addPartialTarget(blockGroupMessages);
        } // if(switcherFacet.equals(PRK_SPACE))
        
        adfFacesCtx.addPartialTarget(detailsPanelGroupLayout);
    } // groupHeaderTableRowSelectionHandler

    public void setUpdateCredentials(){
        // save the action to the pageFlowScope
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.getPageFlowScope().put("lastUpdateProgram", "ADFTools");
        adfFacesContext.getPageFlowScope().put("lastUpdatedBy", BulkEditUtils.getLoginId());        
    } // setUpdateCredentials

    public void addGroupHeaderButtonHandler(ActionEvent actionEvent) {
        groupHeaderAction = "Add";

        // save the action, lastUpdatedBy and lastUpdateProgram to the pageFlowScope
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.getPageFlowScope().put("action", groupHeaderAction);
        adfFacesContext.getPageFlowScope().put("lastUpdatedBy", "Mark Piller");
        adfFacesContext.getPageFlowScope().put("lastUpdateProgram", "ADFTools");
    } // addGroupButtonActionListenerHandler

    public void editGroupHeaderButtonHandler(ActionEvent actionEvent) {
        groupHeaderAction = "Edit";

        // save the action to the pageFlowScope
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.getPageFlowScope().put("action", groupHeaderAction);
        adfFacesContext.getPageFlowScope().put("lastUpdatedBy","Mark Piller");
        adfFacesContext.getPageFlowScope().put("lastUpdateProgram", "ADFTools");
    } // editGroupHeaderButtonActionListenerHandler

    /**
     * deleteSelectedGroupHeaderRowsActionListenerHandler()
     * 
     * This identifies records selected by the user to be deleted.
     * This will delete the record from the Group Header and also
     * from the details table (either Parking Space Groups or Block Groups)
     * 
     * @param actionEvent
     */
    public void deleteSelectedGroupHeaderRowsHandler(ActionEvent actionEvent) {
        boolean rowIsSelectedForDeletion = false;
        boolean rowsAreDeleted = false;
        boolean getAnotherRow = false;

        String groupType = null;
        int rowCount = 0;
        Number headerGroupId = new Number(0);

        // Identify the rows selected for deletion in the Group Header table
        CollectionModel groupHeaderModel = (CollectionModel)groupHeaderTable.getValue();
        JUCtrlHierBinding groupHeaderBinding = (JUCtrlHierBinding)groupHeaderModel.getWrappedData();
        DCIteratorBinding groupHeaderDCIteratorBinding = groupHeaderBinding.getDCIteratorBinding();
        RowSetIterator groupHeaderRSIterator = groupHeaderDCIteratorBinding.getRowSetIterator();
        adfLogger.info("Group Header RSIterator START Row Count: " + groupHeaderRSIterator.getRowCount());
        groupHeaderRSIterator.reset(); // set currency to beginning of the table
        
        if(groupHeaderRSIterator.getRowCount() > 0 || groupHeaderRSIterator.hasNext()){
            getAnotherRow = true;
        }
        
        while(getAnotherRow){
            GroupHeaderUpdtVORowImpl groupHeaderRow = (GroupHeaderUpdtVORowImpl)groupHeaderRSIterator.getCurrentRow();
            headerGroupId = groupHeaderRow.getGroupId().getSequenceNumber();
            groupType = groupHeaderRow.getGroupType();

            if(groupHeaderRow.getSelectedGroupHeaderRow() != null){
                rowIsSelectedForDeletion = groupHeaderRow.getSelectedGroupHeaderRow();
            } else {
                rowIsSelectedForDeletion = false;
            }

            rowCount++;
            adfLogger.info("\tDEBUG:  " + rowCount + ". Group Header ID: " + headerGroupId +
                           " Group Desc: " + groupHeaderRow.getGroupDesc() + 
                           " Group is selected: " + rowIsSelectedForDeletion);

            if (rowIsSelectedForDeletion) {
                if (groupType.equals("PRK_SPACE")) {
                    deleteParkingSpaceGroupItemsWithGroupId(headerGroupId);
                    groupHeaderRow.remove();
                    rowsAreDeleted = true;
                } else if (groupType.equals("BLOCK")) {
                    deleteBlockGroupItemsWithGroupId(headerGroupId);
                    groupHeaderRow.remove();
                    rowsAreDeleted = true;
                }
                if (groupHeaderRSIterator.getRowCount() > 0){
                    getAnotherRow = true;
                } else {
                    getAnotherRow = false;
                }
            } else {
                if(groupHeaderRSIterator.hasNext()){
                    groupHeaderRSIterator.next();
                } else {
                    getAnotherRow = false;
                }
            }

        } // while(getAnotherRow)

        if(rowsAreDeleted){
            groupHeaderRSIterator.reset();
            setMessage(groupHeaderMessages, "Red", "You have unsaved Group Header Deletions");

            AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
            // note: if attempting to PPR the message.. then the table does not refresh
            // adfFacesCtx.addPartialTarget(groupHeaderButtonPanelHeader);

            // enable the save button
            saveGroupHeaderButton.setDisabled(false);
            adfFacesCtx.addPartialTarget(saveGroupHeaderButton);
            
            // refresh the display with the changes
            adfFacesCtx.addPartialTarget(groupHeaderTable);
            adfFacesCtx.addPartialTarget(detailsPanelGroupLayout);
        }

        System.out.println();
    } // deleteSelectedGroupHeaderRowsActionListenerHandler

    public void deleteParkingSpaceGroupItemsWithGroupId(Number groupId){
        boolean getAnotherRow = false;
        
        // Get the application module
        GroupsAMImpl groupsAM = getApplicationModule();

        // get the Parking Space Groups View Object
        ParkingSpaceGroupsUpdtVOImpl parkingSpaceGroupsVO = groupsAM.getParkingSpaceGroupsUpdtVO1();
        
        // pass through each row and delete all rows with the targeted groupId
        parkingSpaceGroupsVO.reset(); // set row currency to beginning of view object
        
        if(parkingSpaceGroupsVO.getRowCount() > 0 || parkingSpaceGroupsVO.hasNext() ){
            getAnotherRow = true;
        }

        adfLogger.info("\tDEBUG:  Parking Space Group " + groupId + " row count before deletions:  " + parkingSpaceGroupsVO.getRowCount());
        while (getAnotherRow) {
            adfLogger.info("\tDEBUG:  get another row from the Parking Space Group");
            ParkingSpaceGroupsUpdtVORowImpl parkingSpaceGroupRow =
                (ParkingSpaceGroupsUpdtVORowImpl)parkingSpaceGroupsVO.getCurrentRow();

            // test if the row object is null
            // if it is, then move the row currency to the next row
            if(parkingSpaceGroupRow == null){
                adfLogger.info("\tDEBUG:  parkingSpaceGroupRow is NULL.   parkingSpaceGroupsVO.getRowCount() is: " + parkingSpaceGroupsVO.getRowCount());
                adfLogger.info("\tDEBUG:  try to move pointer to next row");
                parkingSpaceGroupsVO.next();
                parkingSpaceGroupRow = (ParkingSpaceGroupsUpdtVORowImpl)parkingSpaceGroupsVO.getCurrentRow();
                adfLogger.info("\tDEBUG:  Now testing for null row after doing next()");
                if(parkingSpaceGroupRow == null){
                    adfLogger.info("\tDEBUG:  parkingSpaceGroupRow is NULL after performing next()");
                } else {
                    adfLogger.info("\tDEBUG:  parkingSpaceGroupRow IS NOT NULL after performing next()");
                }
            } else {
                adfLogger.info("\tDEBUG:  parkingSpaceGroupRow is not null");
            }

            if (parkingSpaceGroupRow.getPsGroupId().equals(groupId)) {
                adfLogger.info("\tDEBUG:  Deleting Group ID:  " +
                               parkingSpaceGroupRow.getPsGroupId() +
                               "  Parking Space ID:" +
                               parkingSpaceGroupRow.getParkingSpaceId());
                parkingSpaceGroupRow.remove();
                if (parkingSpaceGroupsVO.getRowCount() > 0) {
                    getAnotherRow = true;
                } else {
                    getAnotherRow = false;
                }
            } else {
                if (parkingSpaceGroupsVO.hasNext()) {
                    parkingSpaceGroupsVO.next();
                } else {
                    getAnotherRow = false;
                } // if(parkingSpaceGroupsVO.hasNext())
            } // if(parkingSpaceGroupRow.getPsGroupId().equals(groupId))
        } // while(getAnotherRow)

        adfLogger.info("\tDEBUG:  Parking Space Group row count after deletions:  " + parkingSpaceGroupsVO.getRowCount());
    } // deleteParkingSpaceGroupItems

    public void deleteBlockGroupItemsWithGroupId(Number groupId){
        boolean getAnotherRow = false;
        
        // Get the application module
        GroupsAMImpl groupsAM = getApplicationModule();

        // get the Block Groups View Object
        BlockGroupsUpdtVOImpl blockGroupsVO = groupsAM.getBlockGroupsUpdtVO1();

        // pass through each row and delete all rows with the targeted groupId
        blockGroupsVO.reset(); // set row currency to beginning of view object

        if(blockGroupsVO.getRowCount() > 0 || blockGroupsVO.hasNext() ){
            getAnotherRow = true;
        }

        adfLogger.info("\tDEBUG:  Block Group ID: " + groupId + " row count before deletions:  " + blockGroupsVO.getRowCount());
        while (getAnotherRow) {
            adfLogger.info("\tDEBUG:  get another row from the Block Group");
            BlockGroupsUpdtVORowImpl blockGroupsRow =
                (BlockGroupsUpdtVORowImpl)blockGroupsVO.getCurrentRow();
            
            // test if the row object is null
            // if it is, then move the row currency to the next row
            if(blockGroupsRow == null){
                adfLogger.info("\tDEBUG:  blockGroupsRow is NULL.   blockGroupsVO.getRowCount() is " + blockGroupsVO.getRowCount());
                adfLogger.info("\tDEBUG:  try to move pointer to next row");
                blockGroupsVO.next();
                blockGroupsRow = (BlockGroupsUpdtVORowImpl)blockGroupsVO.getCurrentRow();
                adfLogger.info("\tDEBUG:  Now testing for null row after doing next()");
                if(blockGroupsRow == null){
                    adfLogger.info("\tDEBUG:  blockGroupsRow is NULL after performing next()");
                } else {
                    adfLogger.info("\tDEBUG:  blockGroupsRow IS NOT NULL after performing next()");
                }
            } else {
                adfLogger.info("\tDEBUG: blockGroupsRow is not null");
            }

            if (blockGroupsRow.getGroupId().equals(groupId)) {
                adfLogger.info("\tDEBUG:  Deleting Group ID:  " +
                               blockGroupsRow.getGroupId() +
                               "  Block ID:" +
                               blockGroupsRow.getBlockId());
                blockGroupsRow.remove();
                if (blockGroupsVO.getRowCount() > 0) {
                    getAnotherRow = true;
                } else {
                    getAnotherRow = false;
                }
            } else {
                if (blockGroupsVO.hasNext()) {
                    blockGroupsVO.next();
                } else {
                    getAnotherRow = false;
                } // if(blockGroupsVO.hasNext())
            } // if(blockGroupsRow.getGroupId().equals(groupId))
        } // while(getAnotherRow)

        adfLogger.info("\tDEBUG:  Block Group ID: " + groupId + " row count after deletions:  " + blockGroupsVO.getRowCount());
    } // deleteBlockGroupItems

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

    public void setGroupHeaderMessages(RichOutputText groupHeaderMessages) {
        this.groupHeaderMessages = groupHeaderMessages;
    }

    public RichOutputText getGroupHeaderMessages() {
        return groupHeaderMessages;
    }

    public void successfulSaveGroupHeaderMessage(){
        groupHeaderMessages.setInlineStyle("font-size:small; color:Green; font-weight:bold;");
        groupHeaderMessages.setValue("Successful save of your changes");
        AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
        //DOES NOT WORK adfFacesCtx.addPartialTarget(groupHeaderMessages);
        adfFacesCtx.addPartialTarget(groupHeaderButtonPanelHeader);
    } // successfulSaveGroupHeaderMessage

    public void setGroupHeaderButtonPanelHeader(RichPanelHeader groupHeaderButtonPanelHeader) {
        this.groupHeaderButtonPanelHeader = groupHeaderButtonPanelHeader;
    }

    public RichPanelHeader getGroupHeaderButtonPanelHeader() {
        return groupHeaderButtonPanelHeader;
    }

    public void validateAndSaveGroupHeaderButtonHandler(ActionEvent actionEvent) {
        groupHeaderMessages.setInlineStyle("font-size:x-small; color:Green; font-weight:bold;");
        groupHeaderMessages.setValue("Successful save of your changes");
        AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
        //DOES NOT WORK adfFacesCtx.addPartialTarget(groupHeaderMessages);
        adfFacesCtx.addPartialTarget(groupHeaderButtonPanelHeader);
    } // validateAndSaveGroupHeaderButtonHandler

    public void displayHeaderActionResults(ReturnEvent returnEvent){
        String statementClause = "";
        
        // determine if the user cancelled or saved their data
        Map<Object, Object> map = returnEvent.getReturnParameters();
        String userAction = map.get("userAction").toString();

        // set focus on first row
        // Identify the rows selected for deletion in the Group Header table
        CollectionModel groupHeaderModel = (CollectionModel)groupHeaderTable.getValue();
        JUCtrlHierBinding groupHeaderBinding = (JUCtrlHierBinding)groupHeaderModel.getWrappedData();
        DCIteratorBinding groupHeaderDCIteratorBinding = groupHeaderBinding.getDCIteratorBinding();
        RowSetIterator groupHeaderRSIterator = groupHeaderDCIteratorBinding.getRowSetIterator();
        GroupHeaderUpdtVORowImpl groupHeaderRow = (GroupHeaderUpdtVORowImpl)groupHeaderRSIterator.getRowAtRangeIndex(0) ;
        Key key = groupHeaderRow.getKey();
        groupHeaderDCIteratorBinding.setCurrentRowWithKey(key.toStringFormat(true));
        
        if(groupHeaderAction.equals("Add")){
            statementClause = "Add Group Header";
        } else {
            statementClause = "Edit Group Header changes";
        }

        if(userAction.equals("cancel")){
            setMessage(groupHeaderMessages, "Green", "Successful cancel of " + statementClause);
        } else if(userAction.equals("save")) {
            // notify the user the save was successful
            setMessage(groupHeaderMessages, "Green", "Successful save of " + statementClause);
        }

        refreshGroupHeaderTable();
    } // displayHeaderActionResults

    public void addGroupHeaderReturnListenerHandler(ReturnEvent returnEvent) {
        displayHeaderActionResults(returnEvent);
    } // addGroupHeaderReturnListenerHandler

    public void editGroupHeaderReturnListenerHandler(ReturnEvent returnEvent) {
        displayHeaderActionResults(returnEvent);
    } // editGroupHeaderReturnListenerHandler

    public void refreshGroupHeaderTable(){
        AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
        adfFacesCtx.addPartialTarget(groupHeaderTable);
    }

    public void addBlocksButtonHandler(ActionEvent actionEvent) {
        setGroupIdForTaskFlow();
    }

    public void addBlocksButtonReturnHandler(ReturnEvent returnEvent) {
        AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();

        // determine if the user cancelled or saved their data
        Map<Object, Object> map = returnEvent.getReturnParameters();
        String userAction = map.get("userAction").toString();

        if(userAction.equals("cancel")){
            setMessage(blockGroupMessages, "Green", "Successful cancel of Add Block(s) changes");
        } else if(userAction.equals("save")) {
            // notify the user they need to save the changes
            setMessage(blockGroupMessages, "Red","You have unsaved Block Additions");
            // make the save button available
            saveBlockGroupsButton.setDisabled(false);
            adfFacesCtx.addPartialTarget(saveBlockGroupsButton);
            // refresh the table with the changes
            adfFacesCtx.addPartialTarget(blockGroupsTable);
        }

    } // addBlocksButtonReturnHandler

    public void setBlockGroupMessages(RichOutputText blockGroupMessages) {
        this.blockGroupMessages = blockGroupMessages;
    }

    public RichOutputText getBlockGroupMessages() {
        return blockGroupMessages;
    }

    public void setBlockGroupsButtonPanelHeader(RichPanelHeader blockGroupsButtonPanelHeader) {
        this.blockGroupsButtonPanelHeader = blockGroupsButtonPanelHeader;
    }

    public RichPanelHeader getBlockGroupsButtonPanelHeader() {
        return blockGroupsButtonPanelHeader;
    }

    public void saveBlockGroupsButtonHandler(ActionEvent actionEvent) {
        // notify the user they need to save the changes
        setMessage(blockGroupMessages, "Green",
                   "Successful save of Block Additions");

        AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
        // disable the save button
        saveBlockGroupsButton.setDisabled(true);
        adfFacesCtx.addPartialTarget(saveBlockGroupsButton);
        // refresh the table with the changes
        adfFacesCtx.addPartialTarget(blockGroupsTable);
    } // saveBlockGroupsButtonHandler

    public void saveParkingSpaceGroupButtonHandler(ActionEvent actionEvent) {
        // notify the user they need to save the changes
        setMessage(parkingSpaceGroupMessages, "Green",
                   "Successful save of Parking Space Additions");

        AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
        // disable the save button
        saveParkingSpaceGroupsButton.setDisabled(true);
        adfFacesCtx.addPartialTarget(saveParkingSpaceGroupsButton);
        // refresh the table with the changes
        adfFacesCtx.addPartialTarget(parkingSpaceGroupsTable);
    } // saveParkingSpaceGroupButtonHandler

    public void setParkingSpaceGroupMessages(RichOutputText parkingSpaceGroupMessages) {
        this.parkingSpaceGroupMessages = parkingSpaceGroupMessages;
    }

    public RichOutputText getParkingSpaceGroupMessages() {
        return parkingSpaceGroupMessages;
    }

    public void addParkingSpacesButtonHandler(ActionEvent actionEvent) {
        setGroupIdForTaskFlow();
    } // addParkingSpacesButtonHandler

    /**
     *  deleteSelectedParkingSpaceRowsButtonHandler()
     *  
     * This deletes the user selected Parking Spaces belonging to the Parking Space Group
     * and refreshes the display to notify the user of the need to save the changes.
     *  
     * @param actionEvent
     */
    public void deleteSelectedParkingSpaceRowsButtonHandler(ActionEvent actionEvent) {
        deleteParkingSpaces(SELECTED_ROWS);
    } // deleteSelectedParkingSpaceRowsButtonHandler

    public void deleteParkingSpaces(String typeOfDeletion){
        boolean rowIsSelectedForDeletion = false;
        boolean rowsAreDeleted = false;
        boolean getAnotherRow = false;
        boolean deleteAllRows = false;
        int rowCount = 0;

        if(typeOfDeletion.equals(ALL_ROWS)){
            deleteAllRows = true;
        }
        
        // Get the application module
        GroupsAMImpl groupsAM = getApplicationModule();

        // get the Parking Space Groups View Object
        ParkingSpaceGroupsUpdtVOImpl parkingSpaceGroupsVO = groupsAM.getParkingSpaceGroupsUpdtVO1();
        
        // pass through each row and delete all rows that the user selected
        parkingSpaceGroupsVO.reset(); // set row currency to beginning of view object
        
        if(parkingSpaceGroupsVO.getRowCount() > 0 || parkingSpaceGroupsVO.hasNext() ){
            getAnotherRow = true;
        }
        
        while(getAnotherRow){
            ParkingSpaceGroupsUpdtVORowImpl parkingSpaceGroupRow = (ParkingSpaceGroupsUpdtVORowImpl)parkingSpaceGroupsVO.getCurrentRow();

            if(parkingSpaceGroupRow.getSelectedParkingSpaceGroupRow() != null){
                rowIsSelectedForDeletion = parkingSpaceGroupRow.getSelectedParkingSpaceGroupRow();
            } else {
                rowIsSelectedForDeletion = false;
            }

            if(deleteAllRows){
                rowIsSelectedForDeletion = true;
            }
            
            rowCount++;
            adfLogger.info("\tDEBUG:  " + rowCount + ". Parking Space ID: " + parkingSpaceGroupRow.getParkingSpaceId() +
                           " PS is selected: " + rowIsSelectedForDeletion);

            if (rowIsSelectedForDeletion) {
                parkingSpaceGroupRow.remove();
                rowsAreDeleted = true;
                if(parkingSpaceGroupsVO.getRowCount() > 0){
                    getAnotherRow = true;
                } else {
                    getAnotherRow = false;
                } // if(parkingSpaceGroupsVO.getRowCount() > 0)
            } else {
                if(parkingSpaceGroupsVO.hasNext()){
                    parkingSpaceGroupsVO.next();
                } else {
                    getAnotherRow = false;
                } // if(parkingSpaceGroupsVO.hasNext())
            } // if (rowIsSelectedForDeletion)
            
        } // while(getAnotherRow)
        
        adfLogger.info("\tDEBUG:  Parking Space Group row count after deletions:  " + parkingSpaceGroupsVO.getRowCount());

        if (rowsAreDeleted) {
            // notify the user they need to save the changes
            setMessage(parkingSpaceGroupMessages, "Red",
                       "You have unsaved Parking Space Deletions");
            // enable the save button
            saveParkingSpaceGroupsButton.setDisabled(false);
            // refresh the display
            AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
            adfFacesCtx.addPartialTarget(saveParkingSpaceGroupsButton);
            adfFacesCtx.addPartialTarget(parkingSpaceGroupsTable); // refresh removal of detail
        } // if (rowsAreDeleted)
        
    } // deleteParkingSpaces

    /**
     * setMessage()
     * 
     * This is used to notify the user of changes made that need to be saved or have been successfully saved.
     *
     * @param messageRichOutputText
     * @param styleColor
     * @param msg
     */
    public void setMessage(RichOutputText messageRichOutputText, String styleColor, String msg){
        // styleColor is either Red or Green
        String styleLine = "font-size:x-small; color:" + styleColor + "; font-weight:bold;";
        messageRichOutputText.setInlineStyle(styleLine);
        messageRichOutputText.setValue(msg);

        AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
        adfFacesCtx.addPartialTarget(messageRichOutputText);
    } // setMessage



    /**
     * deletedSelectedBlockRowsButtonrHandler()
     * 
     * This deletes the user selected Blocks belonging to the Block Group
     * and refreshes the display to notify the user of the need to save the changes.
     * 
     * @param actionEvent
     */
    public void deletedSelectedBlockRowsButtonrHandler(ActionEvent actionEvent) {
        deleteBlocks(SELECTED_ROWS);
    } // deletedSelectedBlockRowsButtonrHandler

    public void deleteBlocks(String typeOfDeletion){
        boolean rowIsSelectedForDeletion = false;
        boolean rowsAreDeleted = false;
        boolean getAnotherRow = false;
        boolean deleteAllRows = false;
        int rowCount = 0;

        if(typeOfDeletion.equals(ALL_ROWS)){
            deleteAllRows = true;
        }
        
        // Get the application module
        GroupsAMImpl groupsAM = getApplicationModule();

        // get the Block Groups View Object
        BlockGroupsUpdtVOImpl blockGroupsVO = groupsAM.getBlockGroupsUpdtVO1();
        
        // pass through each row and delete all rows that the user selected
        blockGroupsVO.reset(); // set row currency to beginning of view object
        
        if(blockGroupsVO.getRowCount() > 0 || blockGroupsVO.hasNext() ){
            getAnotherRow = true;
        }
        
        while(getAnotherRow){
            BlockGroupsUpdtVORowImpl blockGroupRow = (BlockGroupsUpdtVORowImpl)blockGroupsVO.getCurrentRow();

            if(blockGroupRow.getSelectedBlockGroupRow() != null){
                rowIsSelectedForDeletion = blockGroupRow.getSelectedBlockGroupRow();
            } else {
                rowIsSelectedForDeletion = false;
            }

            if(deleteAllRows){
                rowIsSelectedForDeletion = true;
            }

            rowCount++;
            adfLogger.info("\tDEBUG:  " + rowCount + ". Block ID: " + blockGroupRow.getBlockId() +
                           " PS is selected: " + rowIsSelectedForDeletion);

            if (rowIsSelectedForDeletion) {
                blockGroupRow.remove();
                rowsAreDeleted = true;
                if(blockGroupsVO.getRowCount() > 0){
                    getAnotherRow = true;
                } else {
                    getAnotherRow = false;
                } // if(blockGroupsVO.getRowCount() > 0)
            } else {
                if(blockGroupsVO.hasNext()){
                    blockGroupsVO.next();
                } else {
                    getAnotherRow = false;
                } // if(blockGroupsVO.hasNext())
            } // if (rowIsSelectedForDeletion)
            
        } // while(getAnotherRow)
        
        adfLogger.info("\tDEBUG:  Block Group row count after deletions:  " + blockGroupsVO.getRowCount());

        if (rowsAreDeleted) {
            // notify the user they need to save the changes
            setMessage(blockGroupMessages, "Red", "You have unsaved Block Deletions");
            // enable the save button
            saveBlockGroupsButton.setDisabled(false);
            // refresh the display
            AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
            adfFacesCtx.addPartialTarget(saveBlockGroupsButton);
            adfFacesCtx.addPartialTarget(blockGroupsTable); // refresh removal of detail
        } // if (rowsAreDeleted)
    } // deleteBlocks
    
    public void setSaveGroupHeaderButton(RichCommandToolbarButton saveGroupHeaderButton) {
        this.saveGroupHeaderButton = saveGroupHeaderButton;
    }

    public RichCommandToolbarButton getSaveGroupHeaderButton() {
        return saveGroupHeaderButton;
    }

    public void setSaveBlockGroupsButton(RichCommandToolbarButton saveBlockGroupsButton) {
        this.saveBlockGroupsButton = saveBlockGroupsButton;
    }

    public RichCommandToolbarButton getSaveBlockGroupsButton() {
        return saveBlockGroupsButton;
    }

    public void setSaveParkingSpaceGroupsButton(RichCommandToolbarButton saveParkingSpaceGroupsButton) {
        this.saveParkingSpaceGroupsButton = saveParkingSpaceGroupsButton;
    }

    public RichCommandToolbarButton getSaveParkingSpaceGroupsButton() {
        return saveParkingSpaceGroupsButton;
    }

    public void addParkingSpacesButtonReturnHandler(ReturnEvent returnEvent) {
        handleAddParkingSpaceEvent(returnEvent);
    } // addParkingSpacesButtonReturnHandler

    /**
     * This logic is called by getGroupName() method whenever the GroupName is null
     * 
     * @return
     */
    public String getGroupNameFromGroupHeaderTable(){
        String groupName = "";

        // Find the Group Header iterator and get the Group Name from this
        BindingContainer bindings = ADFUtils.getBindingContainer();
        DCIteratorBinding dcIter = (DCIteratorBinding) bindings.get("GroupHeaderUpdtVO1Iterator");
        GroupHeaderUpdtVORowImpl row = (GroupHeaderUpdtVORowImpl) dcIter.getCurrentRow();
        if(row.getGroupName() != null){
            groupName = row.getGroupName();
        }

        return groupName;
    } // getGroupNameFromTable

    public void deleteAllParkingSpacesButtonHandler(ActionEvent actionEvent) {
        deleteParkingSpaces(ALL_ROWS);
    }

    public void deleteAllBlocksButtonHandler(ActionEvent actionEvent) {
        deleteBlocks(ALL_ROWS);
    }

    public void setBlockGroupPanelHeader(RichPanelHeader blockGroupPanelHeader) {
        this.blockGroupPanelHeader = blockGroupPanelHeader;
    }

    public RichPanelHeader getBlockGroupPanelHeader() {
        return blockGroupPanelHeader;
    }

    public void setParkingSpaceGroupPanelHeader(RichPanelHeader parkingSpaceGroupPanelHeader) {
        this.parkingSpaceGroupPanelHeader = parkingSpaceGroupPanelHeader;
    }

    public RichPanelHeader getParkingSpaceGroupPanelHeader() {
        return parkingSpaceGroupPanelHeader;
    }

    public void addMeterRateSchedButtonHandler(ActionEvent actionEvent) {
        setGroupIdForTaskFlow();
    } // addMeterRateSchedButtonHandler()

    public void addMeterRateSchedButtonReturnHandler(ReturnEvent returnEvent) {
        handleAddParkingSpaceEvent(returnEvent);
    } // addMeterRateSchedButtonReturnHandler()

    public void handleAddParkingSpaceEvent(ReturnEvent returnEvent){
        AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();

        // determine if the user cancelled or saved their data
        Map<Object, Object> map = returnEvent.getReturnParameters();
        String userAction = map.get("userAction").toString();

        if(userAction.equals("cancel")){
            setMessage(parkingSpaceGroupMessages, "Green", "Successful cancel of Add Parking Space(s) changes");
        } else if(userAction.equals("save")) {
            // notify the user they need to save the changes
            setMessage(parkingSpaceGroupMessages, "Red","You have unsaved Parking Space Additions");
            // enable the save button
            saveParkingSpaceGroupsButton.setDisabled(false);
            adfFacesCtx.addPartialTarget(saveParkingSpaceGroupsButton);
            adfFacesCtx.addPartialTarget(parkingSpaceGroupsTable); // refresh additions display
        }
    } // handleAddParkingSpaceEvent()

    public void addMeterOpSchedButtonHandler(ActionEvent actionEvent) {
        setGroupIdForTaskFlow();
    }

    public void addMeterOpSchedButtonReturnHandler(ReturnEvent returnEvent) {
        handleAddParkingSpaceEvent(returnEvent);
    }
    
    public void setGroupIdForTaskFlow(){
        Number groupId = new Number(0);
        
        CollectionModel groupHeaderModel = (CollectionModel)groupHeaderTable.getValue();
        JUCtrlHierBinding groupHeaderBinding = (JUCtrlHierBinding)groupHeaderModel.getWrappedData();
        DCIteratorBinding groupHeaderDCIteratorBinding = groupHeaderBinding.getDCIteratorBinding();
        RowSetIterator groupHeaderRSIterator = groupHeaderDCIteratorBinding.getRowSetIterator();
        GroupHeaderUpdtVORowImpl groupHeaderRow = (GroupHeaderUpdtVORowImpl)groupHeaderRSIterator.getCurrentRow();
        // get the Group ID
        groupId = groupHeaderRow.getGroupId().getSequenceNumber();
        
        // save the Group ID to class variable
        // this is passed to Bounded Task Flow > 
        // select-parking-spaces-from-meter-rate-schedules-for-group-btf
        // select-parking-spaces-for-group-btf
        // select-blocks-for-block-group-btf
        this.setGroupId(groupId);
        
        adfLogger.info("\tDEBUG:  Parking Space Group ID: " + groupId);
    } // setGroupIdForTaskFlow()

    /**
     * This supports the Create Date filters
     * 
     * @param queryEvent
     */
    public void onQueryListenerHandler(QueryEvent queryEvent) {
        // default EL string created when dragging the table
        // to the JSF page > groupHeaderListingPage.jspx
        // #{bindings.GroupHeaderUpdtVO1Query.processQuery}

        FilterableQueryDescriptor fqd = (FilterableQueryDescriptor)queryEvent.getDescriptor();
        Map map = fqd.getFilterCriteria();
        BindingContext bctx = BindingContext.getCurrent();
        DCBindingContainer bindings = (DCBindingContainer)bctx.getCurrentBindingsEntry();

        // access the method bindings to set the bind variables on the
        // ViewCriteria
        // methods: setCreateDateRangeStart and setCreateDateRangeEnd
        // are found in the groupHeaderListingPage.jspx bindings
        OperationBinding rangeStartOperationBinding = 
            bindings.getOperationBinding("setCreateDateRangeStart");
        OperationBinding rangeEndOperationBinding =
            bindings.getOperationBinding("setCreateDateRangeEnd");

        // get the temporary, transient attributes from the filter map.
        // Note that the attributes exist no-where in the business service
        // but only in the map that represents the filter criteria
        // Found in af:InputDate component on groupHeaderListingPage.jspx
        // e.g.  #{vs.filterCriteria.CreatedStartRange}
        //       #{vs.filterCriteria.CreatedEndRange}
        Object createdDateStartRange = map.get("CreatedStartRange");
        Object createdDateEndRange = map.get("CreatedEndRange");

        // set the start and end date of the range to search. If the attribute
        // values are not set then NULL is passed to the biding variables, which
        // more or less resets the View Criteria to allow all data to be queried
        rangeStartOperationBinding.getParamsMap().put("value", createdDateStartRange);
        rangeEndOperationBinding.getParamsMap().put("value", createdDateEndRange);

        // remove temporary attributes as they don't exist in the
        // business service and would cause a Null Pointer Error (NPE) if passed with
        // the query. 
        map.remove("CreatedStartRange");
        map.remove("CreatedEndRange");

        // set bind variable on the business service. Note that this does not
        // yet query the View Object 
        rangeStartOperationBinding.execute();
        rangeEndOperationBinding.execute();
        BulkEditUtils.invokeMethodExpression("#{bindings.GroupHeaderUpdtVO1Query.processQuery}", Object.class,
                                QueryEvent.class, queryEvent);

        // put filter values back so search filter is not empty after table is
        // queried 
        map.put("CreatedStartRange", createdDateStartRange);
        map.put("CreatedEndRange", createdDateEndRange);

    } // onQueryListenerHandler
    
} // GroupHeaderListing

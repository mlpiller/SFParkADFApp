package bulkeditgroups.backing;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import model.bulkeditgroups.services.GroupsAMImpl;

import model.bulkeditgroups.views.BlockGroupsUpdtVOImpl;
import model.bulkeditgroups.views.BlockGroupsUpdtVORowImpl;
import model.bulkeditgroups.views.BlocksRoVOImpl;

import model.bulkeditgroups.views.BlocksRoVORowImpl;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichQuery;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.RowSetIterator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import oracle.jbo.domain.Number;
import bulkeditgroups.common.BulkEditUtils;

import oracle.adf.view.rich.event.QueryEvent;

/**
 * Description:
 * This class controls behavior of the selectBlocksPage.jspx
 *
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120626-01 Mark Piller - Oracle Consulting  Created this class file
 */
public class SelectBlocksPage {
    private static ADFLogger adfLogger = ADFLogger.createADFLogger(SelectBlocksPage.class);

    private RichTable blocksTable;
    private String userAction = "";
    private static String ALL_BLOCKS = "ALL_BLOCKS";
    private static String SELECTED_BLOCKS = "SELECTED_BLOCKS";
    private RichQuery blocksQuery;


    public SelectBlocksPage() {
    }

    public void setUserAction(String userAction){
        this.userAction = userAction;
    }
    
    public String getUserAction(){
        return userAction;
    }

    public void saveSelectedBlocksButtonHandler(ActionEvent actionEvent) {
        // pass to calling view the user selected button
        userAction = "save";

        saveBlocks(SELECTED_BLOCKS);
    } // saveSelectedBlocksActionListenerHandler

    public void saveBlocks(String typeOfSave){
        boolean getAnotherRow = false;
        boolean rowIsSelectedForAddToGroup = false;
        boolean saveAllBlocks = false;
        Number blockGroupId = new Number(0);

        if(typeOfSave.equals("ALL_BLOCKS")){
            saveAllBlocks = true;
        }
        
        // get the Group ID for the Selected Block Group
        // this was passed from adf-config as a parameter
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        blockGroupId = (Number)adfFacesContext.getPageFlowScope().get("groupId");

        // Get the application module
        GroupsAMImpl groupsAM = getApplicationModule();
        
        // Get Block Groups View Object for inserting data
        BlockGroupsUpdtVOImpl blockGroupsVO = groupsAM.getBlockGroupsUpdtVO1();
        // Set row currency to the beginning of the View Object
        blockGroupsVO.reset();
        
        // Identify the Block rows selected for adding to the Block Groups table
        CollectionModel blocksModel = (CollectionModel)blocksTable.getValue();
        JUCtrlHierBinding blocksBinding = (JUCtrlHierBinding)blocksModel.getWrappedData();
        DCIteratorBinding blocksDCIteratorBinding = blocksBinding.getDCIteratorBinding();
        RowSetIterator blocksRSIterator = blocksDCIteratorBinding.getRowSetIterator();
        adfLogger.info("\tDEBUG:  RSIterator START Row Count: " + blocksRSIterator.getRowCount());
        blocksRSIterator.reset(); // set currency to beginning of the table

        if(blocksRSIterator.getRowCount() > 0 || blocksRSIterator.hasNext()){
            getAnotherRow = true;
        }
        
        while(getAnotherRow){
            BlocksRoVORowImpl blocksRow = (BlocksRoVORowImpl)blocksRSIterator.getCurrentRow();
            adfLogger.info("\tDEBUG:  Block ID: " + blocksRow.getBlockId());

            if(blocksRow.getSelectedBlockRow() != null){
                rowIsSelectedForAddToGroup = blocksRow.getSelectedBlockRow();
            } else {
                rowIsSelectedForAddToGroup = false;
            } // if(blocksRow.getSelectedBlockRow() != null)

            if(saveAllBlocks){
                rowIsSelectedForAddToGroup = true;
            }
            
            // If the row is selected...
            // Insert the row into the Blocks Group Table
            if(rowIsSelectedForAddToGroup){
                BlockGroupsUpdtVORowImpl blockGroupsRow = (BlockGroupsUpdtVORowImpl)blockGroupsVO.createRow();

                blockGroupsRow.setNewRowState(blockGroupsRow.STATUS_INITIALIZED);
                blockGroupsRow.setGroupId(blockGroupId);
                blockGroupsRow.setBlockId(blocksRow.getBlockId());
                blockGroupsRow.setBLOCK_DESC(blocksRow.getBlockDesc());
                blockGroupsRow.setLastUpdPgm("ADFTools");
                blockGroupsRow.setLastUpdUser(BulkEditUtils.getLoginId());

                blockGroupsVO.insertRow(blockGroupsRow);
            } // if(rowIsSelectedForAddToGroup)

            if(blocksRSIterator.hasNext()){
                blocksRSIterator.next();
            } else {
                getAnotherRow = false;
            }
            
        } // while(getAnotherRow)

        // remove the query parameters the user put in to search
        // for these Blocks
        BulkEditUtils.resetQueryPanelValues(this.getBlocksQuery());

    } // saveBlocks

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

    public void setBlocksTable(RichTable blocksTable) {
        this.blocksTable = blocksTable;
    }

    public RichTable getBlocksTable() {
        return blocksTable;
    }

    public void cancelBlockSelectionsButtonHandler(ActionEvent actionEvent) {
        // remove the query parameters the user put in to search
        // for these Blocks
        BulkEditUtils.resetQueryPanelValues(this.getBlocksQuery());

        // pass to calling view the user selected button
        userAction = "cancel";
    }

    public void selectAllBlocksButtonHandler(ActionEvent actionEvent) {
        // pass to calling view the user selected button
        userAction = "save";

        saveBlocks(ALL_BLOCKS);
    } // selectAllBlocksButtonHandler

    public void setBlocksQuery(RichQuery blocksQuery) {
        this.blocksQuery = blocksQuery;
    }

    public RichQuery getBlocksQuery() {
        return blocksQuery;
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
       BulkEditUtils.queryListenerHandler(queryEvent, blocksTable);
    } // onQueryListenerHandler

} // SelectBlocksPage

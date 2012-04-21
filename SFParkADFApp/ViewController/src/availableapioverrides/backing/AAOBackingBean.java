package availableapioverrides.backing;

import oracle.adf.view.rich.component.rich.output.RichMessage;
import oracle.adf.view.rich.component.rich.output.RichMessages;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import shared.util.adf.ADFUtils;
import shared.util.jsf.JSFUtils;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import model.availableapioverrides.services.AvailApiOverridesAMImpl;
import model.availableapioverrides.views.AAOJoinedUpdtVOImpl;
import model.availableapioverrides.views.AAOJoinedUpdtVORowImpl;
import model.availableapioverrides.views.AvailApiBlockfacesVwRoVOImpl;
import model.availableapioverrides.views.AvailApiBlockfacesVwRoVORowImpl;
import model.availableapioverrides.views.AvailApiBlocksVwRoVOImpl;
import model.availableapioverrides.views.AvailApiBlocksVwRoVORowImpl;
import model.availableapioverrides.views.AvailApiOspInventoryVwRoVOImpl;
import model.availableapioverrides.views.AvailApiOspInventoryVwRoVORowImpl;
import model.availableapioverrides.views.AvailApiPmDistrictsVwRoVOImpl;
import model.availableapioverrides.views.AvailApiPmDistrictsVwRoVORowImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;

/**
 * Description:
 * This class controls behavior of the ManageAvailableApiOverridesPage.jspx
 *
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120321-01 Mark Piller - Oracle Consulting  add logic to provide description in inserted row
 */
public class AAOBackingBean {
    private RichPopup pmDistrictsPopup;
    private RichSelectOneChoice selectOneOverrideItem;
    private RichTable aaoTable;
    private RichPopup ospInventoryPopup;
    private RichPopup blocksPopup;
    private RichPopup blockfacePopup;
    private RichCommandButton validateAndSaveButton;
    private RichOutputText clientMessages;

    public AAOBackingBean() {
    }

    /**
     *  This is used to delete each row selected by the user on the UI
     * @param actionEvent
     */
    public void deleteSelectedRowsActionListener(ActionEvent actionEvent) {
        boolean rowIsSelectedForDeletion = false;
        boolean rowsAreDeleted = false;
        
        // Get the application module
        AvailApiOverridesAMImpl aaoAM = getApplicationModule();

        // Get the view object for the Available API Overrides
        AAOJoinedUpdtVOImpl aaoVO = aaoAM.getAAOJoinedUpdtVO1();

        // System.out.println("\tDEBUG:  aaoVO.getRangeSize():  " + aaoVO.getRangeSize());
        // System.out.println("\tDEBUG:  aaoVO.getRangeStart():  " + aaoVO.getRangeStart());
        // System.out.println("\tDEBUG:  View Object row count: " + aaoVO.getRowCount());

        // set currency to the slot before the first row
        aaoVO.reset();

        // System.out.println("\tDEBUG:  Current row index after reset: " + aaoVO.getCurrentRowIndex());
        AAOJoinedUpdtVORowImpl row = null;
        int rowIndex = -1;
        while (aaoVO.hasNext()) {
            // identify if the row is selected for deletion
            rowIndex = aaoVO.getCurrentRowIndex();
            row = (AAOJoinedUpdtVORowImpl)aaoVO.getCurrentRow();
            // System.out.println("\n\tDEBUG:   ROW index: " + rowIndex + "  ID: " + row.getId());

            if(row.getSelectedForDeletion() == null){
                rowIsSelectedForDeletion = false;
            } else {
                rowIsSelectedForDeletion = row.getSelectedForDeletion();
            }

            if (rowIsSelectedForDeletion == true) {
                // DELETION LOGIC
                // System.out.println("\tDEBUG:  ROW selected for deletion: " + rowIndex + "  ID: " + row.getId());
                aaoVO.removeCurrentRow();
                rowsAreDeleted = true;
                // System.out.println("\tDEBUG:  View Object row count AFTER deleted row: " + aaoVO.getRowCount());

            } else {
                // move to next row
                // System.out.println("\tDEBUG:  ROW NOT selected for deletion: " + rowIndex + "  ID: " + row.getId());
                // System.out.println("\tDEBUG:  Test getting another row");
                if (aaoVO.hasNext()) {
                    // System.out.println("\tDEBUG:  More rows exist... get next row");
                    aaoVO.next();
                    rowIndex = aaoVO.getCurrentRowIndex();
                    // System.out.println("\tDEBUG:  Row index after moving to next(): " + rowIndex + " current View row count: " + aaoVO.getRowCount());
                }
            }  // if (true == row.getAttribute("SelectedForDeletion"))
        } // while (aaoVO.hasNext())

        // test for case of one row remaining that must be deleted
        if(aaoVO.getRowCount() > 0) {
            rowIndex = aaoVO.getCurrentRowIndex();
            row = (AAOJoinedUpdtVORowImpl)aaoVO.getCurrentRow();

            if (row.getSelectedForDeletion() == null) {
                rowIsSelectedForDeletion = false;
            } else {
                rowIsSelectedForDeletion = row.getSelectedForDeletion();
            }
            if (rowIsSelectedForDeletion == true) {
                aaoVO.removeCurrentRow();
                rowsAreDeleted = true;
            }
        }

        if (rowsAreDeleted) {
            validateAndSaveButton.setDisabled(false);
            clientMessages.setInlineStyle("font-size:small; color:Red; font-weight:bold;");
            clientMessages.setValue("You have unsaved changes");

            refreshAllViewObjects();

            AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
            adfFacesCtx.addPartialTarget(validateAndSaveButton);
        } // rowsAreDeleted
        
    } // deleteSelectedRowsActionListener


    /**
     *  This logic returns an instance of the current Application Module
     * @return
     */
    public AvailApiOverridesAMImpl getApplicationModule() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data.AvailApiOverridesAMDataControl.dataProvider}",
                                            Object.class);
        return (AvailApiOverridesAMImpl)valueExp.getValue(elContext);
    } // getApplicationModule

    // This is created to make logic shorter when using BindingContainer
    // BindingContainer is oracle.binding.BindingContainer
    // BindingContext is oracle.binding.BindingContainer
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setPmDistrictsPopup(RichPopup pmDistrictsPopup) {
        this.pmDistrictsPopup = pmDistrictsPopup;
    }

    public RichPopup getPmDistrictsPopup() {
        return pmDistrictsPopup;
    }

    public void setSelectOneOverrideItem(RichSelectOneChoice selectOneOverrideItem) {
        this.selectOneOverrideItem = selectOneOverrideItem;
    }

    public RichSelectOneChoice getSelectOneOverrideItem() {
        return selectOneOverrideItem;
    }

    /**
     * @param actionEvent
     */
    public void addItemActionListener(ActionEvent actionEvent) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();

        Object userSelected = selectOneOverrideItem.getValue();
        if (userSelected == null) {
            // do nothing
        } else {
            String userSelection = userSelected.toString();
            if (userSelection.equals("PMDistricts")) {
                pmDistrictsPopup.show(hints);
            } else if (userSelection.equals("OspInventory")) {
                ospInventoryPopup.show(hints);
            } else if (userSelection.equals("Blocks")) {
                blocksPopup.show(hints);
            } else if (userSelection.equals("Blockfaces")) {
                blockfacePopup.show(hints);
            }
        } // if (userSelected == null)
    } // addItemActionListener

    public void lookupPmDistrictsDialogListener(DialogEvent dialogEvent) {
        Boolean getAnotherRow = false;
        Boolean rowsAreAdded = false;

        // Get the application module
        AvailApiOverridesAMImpl aaoAM = getApplicationModule();

        // Get the view object used for selecting PM Districts
        AvailApiPmDistrictsVwRoVOImpl pmDistrictsVO = aaoAM.getAvailApiPmDistrictsVwRoVO1();
        AvailApiPmDistrictsVwRoVORowImpl pmDistrictsRow = null;
        pmDistrictsVO.reset(); // return to top of the Districts Listing

        // Get the view object for the Available API Overrides
        AAOJoinedUpdtVOImpl aaoVO = aaoAM.getAAOJoinedUpdtVO1();
        AAOJoinedUpdtVORowImpl aaoRow = null;

        if (pmDistrictsVO.hasNext()){
            getAnotherRow = true;
        }

        while (getAnotherRow) {
            // identify if the row is selected for insertion
            pmDistrictsRow = (AvailApiPmDistrictsVwRoVORowImpl)pmDistrictsVO.getCurrentRow();
            try {
                if (pmDistrictsRow.getSelectedPmDistrict() != null) {
                    if (true == pmDistrictsRow.getSelectedPmDistrict()) {
                        aaoRow = (AAOJoinedUpdtVORowImpl)aaoVO.createRow();
                        aaoRow.setNewRowState(aaoRow.STATUS_INITIALIZED);
                        aaoRow.setId(pmDistrictsRow.getPmDistrictId());
                        aaoRow.setDescription(pmDistrictsRow.getPmDistrictName()); // 20120321-01 MLP added
                        aaoRow.setAAOLastUpdPgm("ADF Tool");
                        aaoRow.setAAOLastUpdUser(getLoginId());
                        aaoVO.insertRow(aaoRow);
                        rowsAreAdded = true;
                        pmDistrictsRow.setSelectedPmDistrict(false);
                    }
                }
            } catch (Exception e) {
                // System.out.println("ERROR: " + e.getMessage());
                // System.out.println("SelectedPMDistrict:  " +
                //                   pmDistrictsRow.getSelectedPmDistrict().booleanValue());
                //e.printStackTrace();
            }

            // move to next row
            if (pmDistrictsVO.hasNext()){
                pmDistrictsVO.next();
            }else {
                getAnotherRow = false;
            }
        } // end while

        if (rowsAreAdded == true) {
            aaoVO.reset();

            // refresh the display with the new changes
            AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
            validateAndSaveButton.setDisabled(false);
            clientMessages.setInlineStyle("font-size:small; color:Red; font-weight:bold;");
            clientMessages.setValue("You have unsaved changes");

            adfFacesCtx.addPartialTarget(aaoTable);
            adfFacesCtx.addPartialTarget(clientMessages); // this message must be in this location to display
            adfFacesCtx.addPartialTarget(validateAndSaveButton);
        } // rowsAreAdded

    } // lookupPmDistrictsDialogListener

    public void setAaoTable(RichTable aaoTable) {
        this.aaoTable = aaoTable;
    }

    public RichTable getAaoTable() {
        return aaoTable;
    }

    /**
     * @param dialogEvent
     */
    public void lookupOspInventoryDialogListener(DialogEvent dialogEvent) {
        Boolean getAnotherRow = false;
        Boolean rowsAreAdded = false;

        // Get the application module
        AvailApiOverridesAMImpl aaoAM = getApplicationModule();

        // Get the view object used for selecting OSP Inventory Items
        AvailApiOspInventoryVwRoVOImpl ospInventoryVO = aaoAM.getAvailApiOspInventoryVwRoVO1();
        AvailApiOspInventoryVwRoVORowImpl ospInventoryRow = null;
        ospInventoryVO.reset(); // return to top of the OSP Inventory Listing

        // Get the view object for the Available API Overrides
        AAOJoinedUpdtVOImpl aaoVO = aaoAM.getAAOJoinedUpdtVO1();
        AAOJoinedUpdtVORowImpl aaoRow = null;

        if (ospInventoryVO.hasNext()) {
            getAnotherRow = true;
        }

        while (getAnotherRow) {
            // identify if the row is selected for insertion
            ospInventoryRow = (AvailApiOspInventoryVwRoVORowImpl)ospInventoryVO.getCurrentRow();
            try {
                if (ospInventoryRow.getSelectedOspInventory() != null) {
                    if (true == ospInventoryRow.getSelectedOspInventory()) {
                        aaoRow = (AAOJoinedUpdtVORowImpl)aaoVO.createRow();
                        aaoRow.setNewRowState(aaoRow.STATUS_INITIALIZED);
                        aaoRow.setId(ospInventoryRow.getOspId());
                        aaoRow.setDescription(ospInventoryRow.getFacilityName()); // 20120321-01 MLP added
                        aaoRow.setAAOLastUpdPgm("ADF Tool");
                        aaoRow.setAAOLastUpdUser(getLoginId());
                        aaoVO.insertRow(aaoRow);
                        rowsAreAdded = true;
                        ospInventoryRow.setSelectedOspInventory(false);
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                System.out.println("SelectedOSPInventory:  " +
                                   ospInventoryRow.getSelectedOspInventory().booleanValue());
                //e.printStackTrace();
            }

            // move to next row
            if (ospInventoryVO.hasNext()) {
                ospInventoryVO.next();
            } else {
                getAnotherRow = false;
            }
        } // end while

        if (rowsAreAdded) {
            aaoVO.reset();

            // refresh the display with the new changes
            validateAndSaveButton.setDisabled(false);
            clientMessages.setInlineStyle("font-size:small; color:Red; font-weight:bold;");
            clientMessages.setValue("You have unsaved changes");

            AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
            adfFacesCtx.addPartialTarget(aaoTable);
            adfFacesCtx.addPartialTarget(clientMessages); // this message must be in this location to display
            adfFacesCtx.addPartialTarget(validateAndSaveButton);
        } // rowsAreAdded
    } // lookupOspInventoryDialogListener

    public void setOspInventoryPopup(RichPopup ospInventoryPopup) {
        this.ospInventoryPopup = ospInventoryPopup;
    }

    public RichPopup getOspInventoryPopup() {
        return ospInventoryPopup;
    }

    public void setBlocksPopup(RichPopup blocksPopup) {
        this.blocksPopup = blocksPopup;
    }

    public RichPopup getBlocksPopup() {
        return blocksPopup;
    }

    public void lookupBlocksDialogListener(DialogEvent dialogEvent) {
        Boolean getAnotherRow = false;
        Boolean rowsAreAdded = false;

        // Get the application module
        AvailApiOverridesAMImpl aaoAM = getApplicationModule();

        // Get the view object used for selecting Block Items
        AvailApiBlocksVwRoVOImpl blocksVO = aaoAM.getAvailApiBlocksVwRoVO1();
        AvailApiBlocksVwRoVORowImpl blocksRow = null;
        blocksVO.reset(); // return to top of the Blocks Listing

        // Get the view object for the Available API Overrides
        AAOJoinedUpdtVOImpl aaoVO = aaoAM.getAAOJoinedUpdtVO1();
        AAOJoinedUpdtVORowImpl aaoRow = null;

        if (blocksVO.hasNext()) {
            getAnotherRow = true;
        }

        while (getAnotherRow) {
            // identify if the row is selected for insertion
            blocksRow = (AvailApiBlocksVwRoVORowImpl)blocksVO.getCurrentRow();
            try {
                if (blocksRow.getSelectedBlock() != null) {
                    if (true == blocksRow.getSelectedBlock()) {
                        aaoRow = (AAOJoinedUpdtVORowImpl)aaoVO.createRow();
                        aaoRow.setNewRowState(aaoRow.STATUS_INITIALIZED);
                        aaoRow.setId(blocksRow.getBlockId());
                        aaoRow.setDescription(blocksRow.getDescription()); // 20120321-01 MLP added
                        aaoRow.setAAOLastUpdPgm("ADF Tool");
                        aaoRow.setAAOLastUpdUser(getLoginId());
                        aaoVO.insertRow(aaoRow);
                        rowsAreAdded = true;
                        blocksRow.setSelectedBlock(false);
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                System.out.println("SelectedBlock:  " +
                                   blocksRow.getSelectedBlock().booleanValue());
                //e.printStackTrace();
            }

            // move to next row
            if (blocksVO.hasNext()) {
                blocksVO.next();
            } else {
                getAnotherRow = false;
            }
        } // end while

        if (rowsAreAdded) {
            aaoVO.reset();

            // refresh the display with the new changes
            validateAndSaveButton.setDisabled(false);
            clientMessages.setInlineStyle("font-size:small; color:Red; font-weight:bold;");
            clientMessages.setValue("You have unsaved changes");

            AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
            adfFacesCtx.addPartialTarget(aaoTable);
            adfFacesCtx.addPartialTarget(clientMessages); // this message must be in this location to display
            adfFacesCtx.addPartialTarget(validateAndSaveButton);
        } // rowsAreAdded
    } // lookupBlocksDialogListener

    public void lookupBlockFacesDialogListener(DialogEvent dialogEvent) {
        Boolean getAnotherRow = false;
        Boolean rowsAreAdded = false;

        // Get the application module
        AvailApiOverridesAMImpl aaoAM = getApplicationModule();

        // Get the view object used for selecting Block Items
        AvailApiBlockfacesVwRoVOImpl blockfacesVO = aaoAM.getAvailApiBlockfacesVwRoVO1();
        AvailApiBlockfacesVwRoVORowImpl blockfacesRow = null;
        blockfacesVO.reset(); // return to top of the Blocks Listing

        // Get the view object for the Available API Overrides
        AAOJoinedUpdtVOImpl aaoVO = aaoAM.getAAOJoinedUpdtVO1();
        AAOJoinedUpdtVORowImpl aaoRow = null;

        if (blockfacesVO.hasNext()) {
            getAnotherRow = true;
        }

        while (getAnotherRow) {
            // identify if the row is selected for insertion
            blockfacesRow = (AvailApiBlockfacesVwRoVORowImpl)blockfacesVO.getCurrentRow();
            try {
                if (blockfacesRow.getSelectedBlockface() != null) {
                    if (true == blockfacesRow.getSelectedBlockface()) {
                        aaoRow = (AAOJoinedUpdtVORowImpl)aaoVO.createRow();
                        aaoRow.setNewRowState(aaoRow.STATUS_INITIALIZED);
                        aaoRow.setId(blockfacesRow.getBlockfaceId());
                        aaoRow.setDescription(blockfacesRow.getDescription()); // 20120321-01 MLP added
                        aaoRow.setAAOLastUpdPgm("ADF Tool");
                        aaoRow.setAAOLastUpdUser(getLoginId());
                        aaoVO.insertRow(aaoRow);
                        rowsAreAdded = true;
                        blockfacesRow.setSelectedBlockface(false);
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                System.out.println("SelectedBlockface:  " +
                                   blockfacesRow.getSelectedBlockface().booleanValue());
                //e.printStackTrace();
            }

            // move to next row
            if (blockfacesVO.hasNext()) {
                blockfacesVO.next();
            } else {
                getAnotherRow = false;
            }
        } // end while

        if (rowsAreAdded) {
            aaoVO.reset();

            // refresh the display with the new changes
            validateAndSaveButton.setDisabled(false);
            clientMessages.setInlineStyle("font-size:small; color:Red; font-weight:bold;");
            clientMessages.setValue("You have unsaved changes");

            AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();
            adfFacesCtx.addPartialTarget(aaoTable);
            adfFacesCtx.addPartialTarget(clientMessages); // this message must be in this location to display
            adfFacesCtx.addPartialTarget(validateAndSaveButton);
        } // rowsAreAdded
        
    } // lookupBlockFacesDialogListener

    public void setBlockfacePopup(RichPopup blockfacePopup) {
        this.blockfacePopup = blockfacePopup;
    }

    public RichPopup getBlockfacePopup() {
        return blockfacePopup;
    }

    public String getLoginId() {
        String user = "Unknown";
        // get the user who signed in and place in the Duplicated by component
        try {
            user = ADFContext.getCurrent().getSecurityContext().getUserName();
        } catch (Exception e) {
            e.printStackTrace();
        } // end try for logged in user ID

        return user;
    } // getLoginId

    public void validateAndSaveActionListener(ActionEvent actionEvent) {
        ADFUtils.invokeEL("#{bindings.Commit.execute}");
        validateAndSaveButton.setDisabled(true);
        clientMessages.setInlineStyle("font-size:small; color:Green; font-weight:bold;");
        clientMessages.setValue("Successful save of your changes");

        // Refresh the table after the save
        refreshAllViewObjects();
    }

    public void refreshAllViewObjects() {
        AvailApiOverridesAMImpl aaoAM = getApplicationModule();
        AAOJoinedUpdtVOImpl aaoVO = aaoAM.getAAOJoinedUpdtVO1();
        aaoVO.executeQuery();
        aaoVO.reset();

        AvailApiPmDistrictsVwRoVOImpl pmDistrictsVO = aaoAM.getAvailApiPmDistrictsVwRoVO1();
        pmDistrictsVO.executeQuery();
        pmDistrictsVO.reset();

        AvailApiOspInventoryVwRoVOImpl ospInventoryVO = aaoAM.getAvailApiOspInventoryVwRoVO1();
        ospInventoryVO.executeQuery();
        ospInventoryVO.reset();

        AvailApiBlocksVwRoVOImpl blocksVO = aaoAM.getAvailApiBlocksVwRoVO1();
        blocksVO.executeQuery();
        blocksVO.reset();

        AvailApiBlockfacesVwRoVOImpl blockfacesVO = aaoAM.getAvailApiBlockfacesVwRoVO1();
        blockfacesVO.executeQuery();
        blockfacesVO.reset();
    } // refreshAllViewObjects

    public void setValidateAndSaveButton(RichCommandButton validateAndSaveButton) {
        this.validateAndSaveButton = validateAndSaveButton;
    }

    public RichCommandButton getValidateAndSaveButton() {
        return validateAndSaveButton;
    }

    public void setClientMessages(RichOutputText clientMessages) {
        this.clientMessages = clientMessages;
    }

    public RichOutputText getClientMessages() {
        return clientMessages;
    }

} // AAOBackingBean

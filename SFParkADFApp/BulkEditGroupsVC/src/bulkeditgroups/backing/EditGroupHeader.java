package bulkeditgroups.backing;

import javax.faces.event.ActionEvent;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import bulkeditgroups.common.BulkEditUtils;

/**
 * Description:
 * This class controls behavior of the editGroupHeaderPage.jspx
 *
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120626-01 Mark Piller - Oracle Consulting  Created this class file
 */
public class EditGroupHeader {
    private static ADFLogger adfLogger = ADFLogger.createADFLogger(EditGroupHeader.class);

    private RichInputDate createdDate;
    private RichInputDate lastUpdateDate;
    private RichInputText lastUpdateUser;
    private RichInputText lastUpdateProgram;
    private String lastUpdtProgram = "ADFTools";
    private String userAction = "";
    private String inputUserAction = "";

    public EditGroupHeader() {
    }

    public void setInputUserAction(String inputUserAction){
        this.inputUserAction = inputUserAction;
    }
    
    public String getInputUserAction(){
        return inputUserAction;
    }

    public void setUserAction(String userAction){
        this.userAction = userAction;
    }
    
    public String getUserAction(){
        return userAction;
    }

    public void setCreatedDate(RichInputDate createdDate) {
        this.createdDate = createdDate;
    }

    public RichInputDate getCreatedDate() {
        return createdDate;
    }

    public void setLastUpdateDate(RichInputDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public RichInputDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    public boolean isGroupTypeReadOnly() {
        boolean readOnly = false;
        
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        String inputAction = (String)adfFacesContext.getPageFlowScope().get("action");
        if(inputAction.equals("Edit")){
            readOnly = true;
        }
        return readOnly;
    }

    public void setLastUpdateUser(RichInputText lastUpdateUser) {
        String user = BulkEditUtils.getLoginId(); 
        lastUpdateUser.setValue(user);
        this.lastUpdateUser = lastUpdateUser;
    }

    public RichInputText getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateProgram(RichInputText lastUpdateProgram) {
        lastUpdateProgram.setValue(lastUpdtProgram);
        this.lastUpdateProgram = lastUpdateProgram;
    }

    public RichInputText getLastUpdateProgram() {
        return lastUpdateProgram;
    }

    public void saveGroupHeaderButtonHandler(ActionEvent actionEvent) {
        userAction = "save";
    }

    public void cancelGroupHeaderButtonHandler(ActionEvent actionEvent) {
        userAction = "cancel";
    }
} // EditGroupHeader

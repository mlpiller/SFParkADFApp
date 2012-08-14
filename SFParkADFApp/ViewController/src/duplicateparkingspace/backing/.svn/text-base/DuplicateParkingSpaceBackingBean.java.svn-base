package duplicateparkingspace.backing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;


/**
 *
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120111-01 Mark Piller - Oracle Consulting  created this bean
 *
 */
public class DuplicateParkingSpaceBackingBean {
    // Current Parking Space Data Components
    private RichInputText currentParkingSpaceId;
    private RichInputText currentPostId;
    private RichInputText currentMeterType;
    private RichInputText currentMultiSpacePayStationId;
    private RichInputText currentMultiSpaceNumber;
    private RichInputText currentLastUpdateUser;

    // Duplicate Parking Space Data Components
    private RichInputText parameterMultiSpaceNumber;
    private RichInputText parameterMultiSpacePayStationId;
    private RichInputText parameterPostId;

    // Local Variables
    private String postId = "";
    private String duplicatedBy = "";
    private String payStationId = "";
    private String nextActivity = "";

    private int spaceNumber = 0;
    private int parkingSpaceId = 0;
    private int phaseCounts = 0;

    // This flag controls behaviors whenever the Parking Space
    // is a Multi Space parking space or not
    private boolean isMultiSpaceParkingId = false;
    private RichCommandButton duplicateButton;


    public DuplicateParkingSpaceBackingBean() {
    }


    /****************************
     * LOCAL VARIABLE ACCESSORS
     ****************************/
    public String getNextActivity() {
        return nextActivity;
    }

    public void setNextActivity(String nextActivity) {
        this.nextActivity = nextActivity;
    }

    public String getPostIdStringValue() {
        return postId;
    }

    public void setPostIdStringValue(String postId) {
        this.postId = postId;
    }

    public int getParkingSpaceIdNumberValue() {
        return parkingSpaceId;
    }

    public void setParkingSpaceIdNumberValue(int parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    public String getDuplicatedByValue() {
        return duplicatedBy;
    }

    public void setDuplicatedByValue(String duplicatedBy) {
        this.duplicatedBy = duplicatedBy;
    }

    public int getSpaceNumberValue() {
        return spaceNumber;
    }

    public void setSpaceNumberValue(int spaceNumber) {
        this.spaceNumber = spaceNumber;
    }

    public String getPayStationId() {
        return payStationId;
    }

    public void setPayStationId(String payStationId) {
        this.payStationId = payStationId;
    }





    /************************************************
     * ACCESSORS FOR CURRENT PARKING SPACE DATA
     ************************************************/
    public void setCurrentParkingSpaceId(RichInputText currentParkingSpaceId) {
        this.currentParkingSpaceId = currentParkingSpaceId;
    }

    public RichInputText getCurrentParkingSpaceId() {
        return currentParkingSpaceId;
    }

    public void setCurrentPostId(RichInputText currentPostId) {
        // System.out.println("setCurrentPostId>>   currentPostId parameter: " + currentPostId);
        this.currentPostId = currentPostId;
        // when there is a value from the current post id
        // set the local variable to the same value
        if (currentPostId.getValue() != null) {
            // System.out.println("setCurrentPostId>>   currentPostId is not null: " + currentPostId);
            String postId = currentPostId.getValue().toString();
            // System.out.println("setCurrentPostId>>   postId copied from currentPostId: " + postId);
            setPostIdStringValue(postId);
            // System.out.println("setCurrentPostId>>   executed setPostIdStringValue(): " + postId);
        }
    }

    public RichInputText getCurrentPostId() {
        return currentPostId;
    }

    public void setCurrentMeterType(RichInputText currentMeterType) {
        this.currentMeterType = currentMeterType;
    }

    public RichInputText getCurrentMeterType() {
        return currentMeterType;
    }

    public void setCurrentMultiSpacePayStationId(RichInputText currentMultiSpacePayStationId) {
        this.currentMultiSpacePayStationId = currentMultiSpacePayStationId;
    }

    public RichInputText getCurrentMultiSpacePayStationId() {
        return currentMultiSpacePayStationId;
    }

    public void setCurrentMultiSpaceNumber(RichInputText currentMultiSpaceNumber) {
        this.currentMultiSpaceNumber = currentMultiSpaceNumber;
    }

    public RichInputText getCurrentMultiSpaceNumber() {
        return currentMultiSpaceNumber;
    }

    public void setCurrentLastUpdateUser(RichInputText currentLastUpdateUser) {
        this.currentLastUpdateUser = currentLastUpdateUser;
    }

    public RichInputText getCurrentLastUpdateUser() {
        return currentLastUpdateUser;
    }


    /***********************************************
     * ACCESSORS FOR DUPLICATE PARKING SPACE DATA
     ***********************************************/
    public void setParameterMultiSpaceNumber(RichInputText parameterMultiSpaceNumber) {
        this.parameterMultiSpaceNumber = parameterMultiSpaceNumber;
    }

    public RichInputText getParameterMultiSpaceNumber() {
        return parameterMultiSpaceNumber;
    }

    public void setParameterMultiSpacePayStationId(RichInputText parameterMultiSpacePayStationId) {
        this.parameterMultiSpacePayStationId = parameterMultiSpacePayStationId;
    }

    public RichInputText getParameterMultiSpacePayStationId() {
        return parameterMultiSpacePayStationId;
    }

    public void setParameterPostId(RichInputText parameterPostId) {
        this.parameterPostId = parameterPostId;
    }

    public RichInputText getParameterPostId() {
        return parameterPostId;
    }




    /*********************
     * CUSTOM VALIDATORS
     *********************/
    public void IsValidSpaceNumber(FacesContext facesContext, UIComponent uIComponent,
                                   Object object) {
        // System.out.println("*** START IsValidSpaceNumber()");

        // only run this method if the data is multi-space
        if (!isMultiSpaceParkingId) {
            return;
        }

        boolean validFormat = false;

        // get the value to validate
        // newSpaceNumber is between 1 and 99
        int newSpaceNumber = (Integer)object;
        if ((newSpaceNumber > 0) && (newSpaceNumber < 100)) {
            validFormat = true;
        }
        // System.out.println("newSpaceNumber to validate: " + newSpaceNumber);

        if (validFormat) {
            // get the current bindings and then search for the view object
            BindingContainer bindings =
                (BindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();

            DCIteratorBinding it =
                (DCIteratorBinding)bindings.get("ParkingSpaceInventoryROView1Iterator");

            // access View Object
            ViewObject viewObject = it.getViewObject();

            // Create View Criteria
            ViewCriteria viewCriteria = viewObject.createViewCriteria();

            // Get the View Criteria Row
            ViewCriteriaRow viewCriteriaRow = viewCriteria.createViewCriteriaRow();

            // set search spec
            // uniqueness is MS_PAY_STATION_ID + MS_SPACE_NUM is not in PARKING_SPACE_INVENTORY  if  METER_TYPE = ‘MS’
            // refer to this for setAttribute > http://docs.oracle.com/cd/E12839_01/apirefs.1111/e10653/oracle/jbo/ViewCriteriaRow.html
            viewCriteriaRow.setAttribute("MsSpaceNum", "= '" + newSpaceNumber + "'");
            viewCriteriaRow.setAttribute("MsPayStationId", "= '" + payStationId + "'");
            viewCriteria.addElement(viewCriteriaRow);

            // apply the view criteria to the view object
            viewObject.applyViewCriteria(viewCriteria);

            // execute the query
            viewObject.executeQuery();

            // should return 1 row
            int count = viewObject.getRowCount();

            if (count == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Multi Space Number is not unique",
                                                              "The Multi Space Number + Pay Station Id you entered is not unique. Please enter a unique Multi Space Number."));
            } else {
                // Multi Space Number is valid... store it in page flow memory
                // (MemoryVariableName, VariableValue)
                AdfFacesContext.getCurrentInstance().getPageFlowScope().put("spaceNumber",
                                                                            newSpaceNumber);
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Invalid Multi Space Number",
                                                          "The Multi Space Number you entered is invalid. Please enter a number between 1 and 99."));
        }

        // System.out.println("*** END IsValidSpaceNumber()");
    } // IsValidSpaceNumber()

    public void IsValidPostId(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // System.out.println("*** START IsValidPostId()");
        boolean validFormat = false;

        // get the value to validate
        String newPostId = (String)object;
        if ((newPostId == null) || (newPostId.length() == 0)) {
            return;
        }
        // System.out.println("newPostId to validate: " + newPostId);

        // Post ID must be numeric and in format of ###-#####
        String regExExpression = "([0-9]{3}-[0-9]{5})";
        CharSequence inputStr = newPostId;
        Pattern pattern = Pattern.compile(regExExpression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        validFormat = matcher.matches();

        if (validFormat) {
            // get the current bindings and then search for the view object
            BindingContainer bindings =
                (BindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();

            //Refer to your View Object Iterator that was added into your JSPX page
            // "ParkingSpaceInventorySearchVO1Iterator"

            // this failed... try refering to another View Object
            DCIteratorBinding it =
                (DCIteratorBinding)bindings.get("ParkingSpaceInventoryROView1Iterator");
            //DCIteratorBinding it =(DCIteratorBinding)bindings.get("ParkingSpaceInventorySearchVO1Iterator");

            // access View Object
            ViewObject viewObject = it.getViewObject();

            // Create View Criteria
            ViewCriteria viewCriteria = viewObject.createViewCriteria();

            // Get the View Criteria Row
            ViewCriteriaRow viewCriteriaRow = viewCriteria.createViewCriteriaRow();

            // set search spec
            viewCriteriaRow.setAttribute("PostId", "= '" + newPostId + "'");
            viewCriteria.addElement(viewCriteriaRow);

            // apply the view criteria to the view object
            viewObject.applyViewCriteria(viewCriteria);

            // execute the query
            viewObject.executeQuery();

            // should return 1 row
            int count = viewObject.getRowCount();

            if (count == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Post Id is not unique.",
                                                              "The Post Id you entered is not unique. Please enter a unique Post Id."));
            } else {
                // Post Id is valid... store it in page flow memory
                // (MemoryVariableName, VariableValue)
                AdfFacesContext.getCurrentInstance().getPageFlowScope().put("postId", newPostId);
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Invalid Post Id format",
                                                          "The Post Id you entered (" + newPostId +
                                                          ") is an invalid format. Please enter format as ###-#####."));
        }

        // System.out.println("*** END IsValidPostId()");
    } // IsValidPostId






    /************************
     * BUTTON EVENT HANDLERS
     ************************/

    /*
     * This causes the page to go on to a new activity
     * that calls the database function to duplicate the Parking Space
     */
    public void DuplicationButtonActionListener(ActionEvent actionEvent) {
        // System.out.println("*** START DuplicationButtonActionListener() ***");

        setNextActivity("RUN DUPLICATION");

        // get the post id revision
        //String paramPostId = parameterPostId.getValue().toString();
        //System.out.println("Parameter Post ID that user entered is: " + paramPostId);
        //System.out.println("Post ID attribute: " + postId);

        // get the context of this JSP page
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();

        // save next activity indicator (MemoryVariableName, VariableValue)
        //adfFacesContext.getPageFlowScope().put("nextActivity",nextActivity);
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("nextActivity", nextActivity);
        
        // prevent the user from entering more duplicates
        duplicateButton.setDisabled(true);
        parameterPostId.setDisabled(true);
        parameterMultiSpaceNumber.setDisabled(true);

        // confirm the memory variables
        /*
        System.out.println("Memory parkingSpaceId: " +
                           adfFacesContext.getCurrentInstance().getPageFlowScope().get("parkingSpaceId"));
        System.out.println("Memory paramParkingSpaceId: " +
                           adfFacesContext.getCurrentInstance().getPageFlowScope().get("paramParkingSpaceId"));
        System.out.println("Memory postId: " +
                           adfFacesContext.getCurrentInstance().getPageFlowScope().get("postId"));
        System.out.println("Memory spaceNumber: " +
                           adfFacesContext.getCurrentInstance().getPageFlowScope().get("spaceNumber"));
        System.out.println("Memory userId: " +
                           adfFacesContext.getCurrentInstance().getPageFlowScope().get("userId"));
        System.out.println("Memory payStationId: " +
                           adfFacesContext.getCurrentInstance().getPageFlowScope().get("payStationId"));
        System.out.println("Memory nextActivity: " +
                           adfFacesContext.getCurrentInstance().getPageFlowScope().get("nextActivity"));

        System.out.println("*** END DuplicationButtonActionListener() ***");
        */
        
    } // DuplicationButtonActionListener




    /*
     * VALUE CHANGE LISTENER
     */
    public void PostIdValueChangeListenerHandler(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

        Object newValue = valueChangeEvent.getNewValue();
        // System.out.println("newValue: " + newValue.toString());
        Object oldValue = valueChangeEvent.getOldValue();
        // System.out.println("oldValue: " + oldValue.toString());

        if (newValue.equals(oldValue)) {
            // do nothing
        } else {
            // save new value to places
            // System.out.println("setting PostId because of change listener");
            setPostIdStringValue(newValue.toString());
        } // end if
    }  // PostIdValueChangeListenerHandler





    /**************************
     * OTHER EVENT HANDLERS
     **************************/
    public void BeforePhaseEvent(PhaseEvent phaseEvent) {
        // this setup is only to run one time through
        // System.out.println("*** START BeforePhaseEvent() >> phase count: " + phaseCounts + " <<  ***");
        // System.out.println("phaseEvent.getPhaseId(): " + phaseEvent.getPhaseId());
        String duplicateParkingSpaceErrorMessage = null;

        phaseCounts++;
        String meterType = null;
        String newPostId = null;

        String user = null;

        // Determine if this is a multi meter parking space
        // If meterType is MS
        //    display Multi Space Pay Station Id as read only
        //    display Multi Space Number as editable field
        // Else
        //    hide Multi Space Pay Station Id
        //    hide Multi Space Number
        // System.out.println("BeforePhaseEvent>>   Checking Meter Type and setting visibility on components");
        // System.out.println("BeforePhaseEvent>>   currentMeterType.getValue(): " + currentMeterType.getValue());
        meterType = currentMeterType.getValue().toString();
        if (meterType.equals("MS")) {
            // System.out.println("BeforePhaseEvent>>   meterType is MS - setting Multi fields visible");
            currentMultiSpacePayStationId.setVisible(true);
            currentMultiSpaceNumber.setVisible(true);

            parameterMultiSpaceNumber.setVisible(true);
            parameterMultiSpacePayStationId.setVisible(true);

            isMultiSpaceParkingId = true;
        } else {
            // System.out.println("BeforePhaseEvent>>   meterType is NOT MS - setting Multi fields INvisible");
            currentMultiSpacePayStationId.setVisible(false);
            currentMultiSpaceNumber.setVisible(false);

            parameterMultiSpaceNumber.setVisible(false);
            parameterMultiSpacePayStationId.setVisible(false);

            isMultiSpaceParkingId = false;
        } // end if meterType.equals("MS")

        // copy the values in the current Parking Space data to the new Parking Space data
        // we need 4 parameters

        // p_PARKING_SPACE_ID IN NUMBER
        String temp = null;
        int parkingSpaceId;
        try {
            // System.out.println("BeforePhaseEvent>>   currentParkingSpaceId.getValue(): " + currentParkingSpaceId.getValue());
            temp = currentParkingSpaceId.getValue().toString();
            // System.out.println("BeforePhaseEvent>>   temp is from getValue(): " + temp);
            parkingSpaceId = Integer.parseInt(temp);
            // System.out.println("BeforePhaseEvent>>   parkingSpaceId is converted temp: " + parkingSpaceId);
            setParkingSpaceIdNumberValue(parkingSpaceId);
            // System.out.println("BeforePhaseEvent>>   getParkingSpaceIdNumberValue: " + getParkingSpaceIdNumberValue());
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("paramParkingSpaceId",
                                                                        parkingSpaceId);
        } catch (NumberFormatException nfe) {
            duplicateParkingSpaceErrorMessage = "Error retrieving parking space ID";
            // System.out.println("BeforePhaseEvent>>     ERROR IN CURRENT PARKING SPACE ID LOGIC");
            nfe.printStackTrace();
        } // end try for parkingSpaceId


        // p_POST_ID in VARCHAR2
        // create the shortened Post Id for presentation to the user
        //RichInputText currentPostId = getCurrentPostId();
        try {
            // System.out.println("BeforePhaseEvent>>   postId:  " + postId);
            // only set the postId one time in this method
            if (postId.equals("")) {
                newPostId = currentPostId.getValue().toString();
                // System.out.println("BeforePhaseEvent>>   newPostId:  " + newPostId);
                newPostId = newPostId.substring(0, 6);
                // System.out.println("BeforePhaseEvent>>   newPostId substring:  " + newPostId);
                setPostIdStringValue(newPostId);
                // System.out.println("BeforePhaseEvent>>   getPostIdStringValue:  " + getPostIdStringValue());
                // (MemoryVariableName, VariableValue)
                AdfFacesContext.getCurrentInstance().getPageFlowScope().put("postId", postId);
            }
        } catch (Exception e) {
            duplicateParkingSpaceErrorMessage = "Error retrieving post id";
            // System.out.println("BeforePhaseEvent>>     ERROR IN CURRENT POST ID LOGIC");
            e.printStackTrace();
        } // end try for postId

        // p_SPACE_NUM in NUMBER
        int multiSpaceNumber = 0;
        try {
            // only set the space number one time in this method
            if (spaceNumber == 0) {
                // System.out.println("BeforePhaseEvent>>   currentMultiSpaceNumberRIT.getValue(): " + currentMultiSpaceNumber.getValue());
                temp = currentMultiSpaceNumber.getValue().toString();
                // System.out.println("BeforePhaseEvent>>   temp is from getValue(): " + temp);
                multiSpaceNumber = Integer.parseInt(temp);
                // System.out.println("BeforePhaseEvent>>   multiSpaceNumber is converted temp: " + multiSpaceNumber);
                setSpaceNumberValue(multiSpaceNumber);
                // System.out.println("BeforePhaseEvent>>   getSpaceNumberValue: " + getSpaceNumberValue());
                // (MemoryVariableName, VariableValue)
                AdfFacesContext.getCurrentInstance().getPageFlowScope().put("spaceNumber",
                                                                            spaceNumber);
            }
        } catch (NumberFormatException nfe) {
            duplicateParkingSpaceErrorMessage = "Error retrieving multi space number";
            // System.out.println("BeforePhaseEvent>>     ERROR IN CURRENT MULTI SPACE NUMBER LOGIC");
            nfe.printStackTrace();
        } // end try for multiSpaceNumber


        // p_USER in VARCHAR2
        // get the user who signed in and place in the Duplicated by component
        try {
            user = ADFContext.getCurrent().getSecurityContext().getUserName();
            setDuplicatedByValue(user);
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("userId", user);
        } catch (Exception e) {
            duplicateParkingSpaceErrorMessage = "Error retrieving current user login ID";
            // System.out.println("BeforePhaseEvent>>     ERROR IN CURRENT LOGIN USER ID LOGIC");
            e.printStackTrace();
        } // end try for logged in user ID


        // Set Pay Station ID to use later to verify for uniqueness
        try {
            String stationId = currentMultiSpacePayStationId.getValue().toString();
            setPayStationId(stationId);
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("payStationId",
                                                                        payStationId);
        } catch (Exception e) {
            duplicateParkingSpaceErrorMessage = "Error retrieving pay station ID";
            // System.out.println("BeforePhaseEvent>>   ERROR IN CURRENT PAY STATION ID LOGIC");
            e.printStackTrace();
        } // end try for payStationId


        // Display ERRORS - if any
        if ((duplicateParkingSpaceErrorMessage == null) ||
            (duplicateParkingSpaceErrorMessage.length() > 0)) {
            // no errors... do nothing
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Error Retrieving Data from Database",
                                                          duplicateParkingSpaceErrorMessage));
        }
        // System.out.println("*** END BeforePhaseEvent() ***");
    }  // BeforePhaseEvent


    public void setDuplicateButton(RichCommandButton duplicateButton) {
        this.duplicateButton = duplicateButton;
    }

    public RichCommandButton getDuplicateButton() {
        return duplicateButton;
    }
}  // DuplicateParkingSpaceBackingBean

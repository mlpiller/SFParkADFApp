package bulkeditgroups.common;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Map;

import oracle.jbo.domain.Number;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import model.bulkeditgroups.services.GroupsAMImpl;

import model.bulkeditgroups.views.GroupHeaderUpdtVOImpl;

import model.bulkeditgroups.views.GroupHeaderUpdtVORowImpl;

import oracle.adf.controller.v2.context.LifecycleContext;
import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;

/**
 * Description:
 * This class is not in usage but is included if debugging is necessary w
 *
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- ------------------------------------------
 * 20120626-01 Mark Piller - Oracle Consulting  Created this class file
 */
public class CustomPagePhaseListener implements PagePhaseListener{
    private static ADFLogger adfLogger = ADFLogger.createADFLogger(CustomPagePhaseListener.class);
    private String groupName;

    public void setGroupName(String groupName){
        this.groupName = groupName;
    }
    
    public String getGroupName(){
        return groupName;
    }

    public void afterPhase(PagePhaseEvent event)
    {
        Number groupId = new Number(0);
        String groupName = "";
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date myDate = new Date();
        LifecycleContext lcContext = event.getLifecycleContext();

        adfLogger.info("\tDEBUG:  " + dateFormat.format(myDate) +  "   In afterPhase: " + event.getPhaseId());
        adfLogger.info("\tDEBUG:  In afterPhase > debug value (name of event being processed): " + event.getDebugValue());
        adfLogger.info("\tDEBUG:  In afterPhase > BindingContext: " + lcContext.getBindingContext().ADF_CONTEXT);
        if(event.getPhaseId() == Lifecycle.PREPARE_RENDER_ID){
            // groupId = getGroupId();
            groupName = getGroupNameFromTable();
            adfLogger.info("\tDEBUG:  In afterPhase Group Header ID: " + groupId);
            if(groupId.compareTo(0) > 0){
                adfLogger.info("\tDEBUG:  ADDING GROUP ID TO VIEWSCOPE ");
                ADFContext adfContext = ADFContext.getCurrent();
                Map viewScope = adfContext.getViewScope();
                viewScope.put("groupName", groupName);
                this.setGroupName(groupName);
                viewScope.put("myTest", groupName);
                // viewScope.GroupHeaderListingBean.groupName
            }
        }
    }
    
    
    public void beforePhase(PagePhaseEvent event)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date myDate = new Date();
        adfLogger.info("\tDEBUG:  " + dateFormat.format(myDate) +  "   In beforePhase: " + event.getPhaseId());
        adfLogger.info("\tDEBUG:  In beforePhase > debug value (name of event being processed): " + event.getDebugValue());
       
    }
    public String getGroupNameFromTable(){
        String groupName = "";

        // Get the application module
        try {
            GroupsAMImpl groupsAM = getApplicationModule();
            GroupHeaderUpdtVOImpl groupHeaderVO = groupsAM.getGroupHeaderUpdtVO1();
            GroupHeaderUpdtVORowImpl groupHeaderRow =
                (GroupHeaderUpdtVORowImpl)groupHeaderVO.getCurrentRow();
            groupName = groupHeaderRow.getGroupName();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }

        return groupName;
    } // getGroupNameFromTable

    public Number getGroupId(){
        Number groupId = new Number(0);

        try {
            // Get the application module
            GroupsAMImpl groupsAM = getApplicationModule();

            // get the group header table
            GroupHeaderUpdtVOImpl groupHeaderVO = groupsAM.getGroupHeaderUpdtVO1();

            // get the group header row
            GroupHeaderUpdtVORowImpl groupHeaderRow =
                (GroupHeaderUpdtVORowImpl)groupHeaderVO.getCurrentRow();

            // get the Group Id
            groupId = groupHeaderRow.getGroupId().getSequenceNumber();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        
        return groupId;
        
    } // getGroupId

    public GroupsAMImpl getApplicationModule() {
        GroupsAMImpl myGroupsAM = null;
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            Application app = fc.getApplication();
            ExpressionFactory elFactory = app.getExpressionFactory();
            ELContext elContext = fc.getELContext();
            ValueExpression valueExp =
                elFactory.createValueExpression(elContext, "#{data.GroupsAMDataControl.dataProvider}",
                                                Object.class);
            myGroupsAM = (GroupsAMImpl)valueExp.getValue(elContext);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        return myGroupsAM;
    } // getApplicationModule

}

package bulkeditgroups.common;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import model.bulkeditgroups.services.GroupsAMImpl;
import model.bulkeditgroups.views.MeterOpScheduleRoVOImpl;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichQuery;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.QueryDescriptor;

import oracle.adf.view.rich.model.QueryModel;

import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;

public class BulkEditUtils {
    public static final ADFLogger LOGGER = ADFLogger.createADFLogger(BulkEditUtils.class);

    public static String getLoginId() {
        String user = "Unknown";
        // get the user who signed in and place in the Duplicated by component
        try {
            user = ADFContext.getCurrent().getSecurityContext().getUserName();
        } catch (Exception e) {
            e.printStackTrace();
        } // end try for logged in user ID

        return user;
    } // getLoginId


    /**
     * This method makes certain that the parameters defined by the user in
     * the query panel above the query results table will be reset to no values
     * on display.
     * 
     * For more information refer to:
     * http://jobinesh.blogspot.com/2011/04/programmatically-resetting-and-search.html
     * 
     */
    public static void resetQueryPanelValues(RichQuery richQuery){
        RichQuery queryComp = richQuery;
        QueryModel queryModel = queryComp.getModel();
        QueryDescriptor queryDescriptor = queryComp.getValue();
        queryModel.reset(queryDescriptor);
        queryComp.refresh(FacesContext.getCurrentInstance());
    } // resetQueryPanelValues
    

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
    public static void queryListenerHandler(QueryEvent queryEvent, RichTable queryResultsTable) {
        Number groupId = new Number(0);
        // The generated QueryListener replaced by this method 
        // #{bindings.ImplicitViewCriteriaQuery.processQuery}

        // Get the view object for the query
        CollectionModel queryResultsTableModel = (CollectionModel)queryResultsTable.getValue();
        JUCtrlHierBinding queryResultsTableBinding = (JUCtrlHierBinding)queryResultsTableModel.getWrappedData();
        ViewObject vo = queryResultsTableBinding.getViewObject();
        // MeterOpScheduleRoVOImpl vo = (MeterOpScheduleRoVOImpl)meterOpSchedBinding.getViewObject();
        VariableValueManager vm = vo.ensureVariableManager();

        // get the Group ID from memory and set the binding variable with the Group ID
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        groupId = (Number)adfFacesContext.getPageFlowScope().get("groupId");
        vm.setVariableValue("BindGroupId", groupId);
        
        // call default Query Event (now with the new binding variable value)
        invokeQueryEventMethodExpression("#{bindings.ImplicitViewCriteriaQuery.processQuery}",queryEvent);
    } // queryListenerHandler


    /**
     * This is used only for queries on the resuls table
     * Currently, this is just a placeholder if additional logic is needed or wanted
     * 
     * For more information:  
     * How-to tell the ViewCriteria a use chose in an af:query component
     * https://blogs.oracle.com/jdevotnharvest/entry/how-to_tell_the_viewcriteria_a_user_chose_in_an_afquery_component
     * http://www.oracle.com/technetwork/developer-tools/adf/learnmore/dec2010-otn-harvest-199274.pdf
     * 
     * @param queryEvent
     */
    public static void onQueryTable(QueryEvent queryEvent) { 
      // The generated QueryListener replaced by this method 
      // #{bindings.ImplicitViewCriteriaQuery.processQuery} 
      QueryDescriptor qdes = queryEvent.getDescriptor();

      //print or log selected View Criteria 
      System.out.println("NAME " + qdes.getName());
      invokeQueryEventMethodExpression( "#{bindings.ImplicitViewCriteriaQuery.processQuery}",queryEvent);
    } // onQueryTable

    /**
     * This executes the query handled in queryListenerHandler() after any custom
     * manipulation of the query event is positioned
     * 
     * @param expression
     * @param queryEvent
     */
    private static void invokeQueryEventMethodExpression( String expression, QueryEvent queryEvent){ 
      FacesContext fctx = FacesContext.getCurrentInstance(); 
      ELContext elctx = fctx.getELContext(); 
      ExpressionFactory efactory = fctx.getApplication().getExpressionFactory(); 
      MethodExpression me = efactory.createMethodExpression(elctx,expression, Object.class, new Class[]{QueryEvent.class});
      me.invoke(elctx, new Object[]{queryEvent}); 
    } // invokeQueryEventMethodExpression

    public static Object invokeMethodExpression(String expr, Class returnType, Class[] argTypes,
                                         Object[] args) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elctx = fc.getELContext();
        ExpressionFactory elFactory = fc.getApplication().getExpressionFactory();
        MethodExpression methodExpr =
            elFactory.createMethodExpression(elctx, expr, returnType, argTypes);
        return methodExpr.invoke(elctx, args);
    }

    public static Object invokeMethodExpression(String expr, Class returnType, Class argType,
                                         Object argument) {
        return invokeMethodExpression(expr, returnType, new Class[] { argType },
                                      new Object[] { argument });
    }
}


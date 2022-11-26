package com.sun.faces.flow.builder;

import com.sun.faces.flow.FlowImpl;
import com.sun.faces.flow.ParameterImpl;
import com.sun.faces.util.Util;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowCallBuilder;
import javax.faces.flow.builder.MethodCallBuilder;
import javax.faces.flow.builder.NavigationCaseBuilder;
import javax.faces.flow.builder.ReturnBuilder;
import javax.faces.flow.builder.SwitchBuilder;
import javax.faces.flow.builder.ViewBuilder;

public class FlowBuilderImpl extends FlowBuilder {
   private FlowImpl flow = new FlowImpl();
   private ExpressionFactory expressionFactory;
   private ELContext elContext;
   private FacesContext context;
   private boolean didInit;
   private boolean hasId;

   public FlowBuilderImpl(FacesContext context) {
      this.context = context;
      this.expressionFactory = context.getApplication().getExpressionFactory();
      this.elContext = context.getELContext();
      this.didInit = false;
      this.hasId = false;
   }

   public NavigationCaseBuilder navigationCase() {
      return new NavigationCaseBuilderImpl(this);
   }

   public ViewBuilder viewNode(String viewNodeId, String vdlDocumentId) {
      Util.notNull("viewNodeId", viewNodeId);
      Util.notNull("vdlDocumentId", vdlDocumentId);
      ViewBuilder result = new ViewBuilderImpl(this, viewNodeId, vdlDocumentId);
      return result;
   }

   public SwitchBuilder switchNode(String switchNodeId) {
      Util.notNull("switchNodeId", switchNodeId);
      return new SwitchBuilderImpl(this, switchNodeId);
   }

   public ReturnBuilder returnNode(String returnNodeId) {
      Util.notNull("returnNodeId", returnNodeId);
      return new ReturnBuilderImpl(this, returnNodeId);
   }

   public MethodCallBuilder methodCallNode(String methodCallNodeId) {
      Util.notNull("methodCallNodeId", methodCallNodeId);
      return new MethodCallBuilderImpl(this, methodCallNodeId);
   }

   public FlowCallBuilder flowCallNode(String flowCallNodeId) {
      return new FlowCallBuilderImpl(this, flowCallNodeId);
   }

   public FlowBuilder id(String definingDocumentId, String flowId) {
      Util.notNull("definingDocumentId", definingDocumentId);
      Util.notNull("flowId", flowId);
      this.flow.setId(definingDocumentId, flowId);
      this.hasId = true;
      return this;
   }

   public FlowBuilder initializer(MethodExpression methodExpression) {
      Util.notNull("methodExpression", methodExpression);
      this.flow.setInitializer(methodExpression);
      return this;
   }

   public FlowBuilder initializer(String methodExpression) {
      Util.notNull("methodExpression", methodExpression);
      MethodExpression me = this.expressionFactory.createMethodExpression(this.elContext, methodExpression, (Class)null, new Class[0]);
      this.flow.setInitializer(me);
      return this;
   }

   public FlowBuilder finalizer(MethodExpression methodExpression) {
      this.flow.setFinalizer(methodExpression);
      return this;
   }

   public FlowBuilder finalizer(String methodExpression) {
      MethodExpression me = this.expressionFactory.createMethodExpression(this.elContext, methodExpression, (Class)null, new Class[0]);
      this.flow.setFinalizer(me);
      return this;
   }

   public FlowBuilder inboundParameter(String name, ValueExpression value) {
      ParameterImpl param = new ParameterImpl(name, value);
      this.flow._getInboundParameters().put(name, param);
      return this;
   }

   public FlowBuilder inboundParameter(String name, String value) {
      ValueExpression ve = this.expressionFactory.createValueExpression(this.elContext, value, Object.class);
      this.inboundParameter(name, ve);
      return this;
   }

   public Flow getFlow() {
      if (!this.hasId) {
         throw new IllegalStateException("Flow must have a defining document id and flow id.");
      } else {
         if (!this.didInit) {
            this.flow.init(this.context);
            String startNodeId = this.flow.getStartNodeId();
            if (null == startNodeId) {
               String flowId = this.flow.getId();
               this.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();
            }

            this.didInit = true;
         }

         return this.flow;
      }
   }

   public FlowImpl _getFlow() {
      return this.flow;
   }

   ExpressionFactory getExpressionFactory() {
      return this.expressionFactory;
   }

   ELContext getELContext() {
      return this.elContext;
   }
}

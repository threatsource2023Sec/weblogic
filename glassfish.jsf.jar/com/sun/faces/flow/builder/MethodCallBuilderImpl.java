package com.sun.faces.flow.builder;

import com.sun.faces.flow.MethodCallNodeImpl;
import java.util.List;
import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.flow.builder.MethodCallBuilder;

public class MethodCallBuilderImpl extends MethodCallBuilder {
   private FlowBuilderImpl root;
   private String methodCallId;
   private MethodCallNodeImpl methodCallNode;
   private static final Class[] EMPTY_ARGS = new Class[0];

   public MethodCallBuilderImpl(FlowBuilderImpl root, String id) {
      this.root = root;
      this.methodCallId = id;
      this.methodCallNode = new MethodCallNodeImpl(id);
      this.root._getFlow()._getMethodCalls().add(this.methodCallNode);
   }

   public MethodCallBuilder defaultOutcome(String outcome) {
      ELContext elc = this.root.getELContext();
      ValueExpression ve = this.root.getExpressionFactory().createValueExpression(elc, outcome, String.class);
      this.methodCallNode.setOutcome(ve);
      return this;
   }

   public MethodCallBuilder defaultOutcome(ValueExpression ve) {
      this.methodCallNode.setOutcome(ve);
      return this;
   }

   public MethodCallBuilder expression(String methodExpression) {
      ELContext elc = this.root.getELContext();
      MethodExpression me = this.root.getExpressionFactory().createMethodExpression(elc, methodExpression, (Class)null, EMPTY_ARGS);
      this.methodCallNode.setMethodExpression(me);
      return this;
   }

   public MethodCallBuilder expression(String methodExpression, Class[] paramTypes) {
      ELContext elc = this.root.getELContext();
      MethodExpression me = this.root.getExpressionFactory().createMethodExpression(elc, methodExpression, (Class)null, paramTypes);
      this.methodCallNode.setMethodExpression(me);
      return this;
   }

   public MethodCallBuilder parameters(List parameters) {
      this.methodCallNode._getParameters().addAll(parameters);
      return this;
   }

   public MethodCallBuilder expression(MethodExpression me) {
      this.methodCallNode.setMethodExpression(me);
      return this;
   }

   public MethodCallBuilder markAsStartNode() {
      this.root._getFlow().setStartNodeId(this.methodCallId);
      return this;
   }
}

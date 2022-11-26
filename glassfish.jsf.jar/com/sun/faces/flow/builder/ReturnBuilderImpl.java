package com.sun.faces.flow.builder;

import com.sun.faces.flow.ReturnNodeImpl;
import com.sun.faces.util.Util;
import javax.el.ValueExpression;
import javax.faces.flow.builder.ReturnBuilder;

public class ReturnBuilderImpl extends ReturnBuilder {
   private FlowBuilderImpl root;
   String id;

   public ReturnBuilderImpl(FlowBuilderImpl root, String id) {
      this.root = root;
      this.id = id;
   }

   public ReturnBuilder markAsStartNode() {
      this.root._getFlow().setStartNodeId(this.id);
      return this;
   }

   public ReturnBuilder fromOutcome(String outcome) {
      Util.notNull("outcome", outcome);
      ReturnNodeImpl returnNode = new ReturnNodeImpl(this.id);
      returnNode.setFromOutcome(outcome);
      this.root._getFlow()._getReturns().put(this.id, returnNode);
      return this;
   }

   public ReturnBuilder fromOutcome(ValueExpression outcome) {
      Util.notNull("outcome", outcome);
      ReturnNodeImpl returnNode = new ReturnNodeImpl(this.id);
      returnNode.setFromOutcome(outcome);
      this.root._getFlow()._getReturns().put(this.id, returnNode);
      return this;
   }
}

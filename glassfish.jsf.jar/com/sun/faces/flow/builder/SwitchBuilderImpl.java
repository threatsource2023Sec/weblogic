package com.sun.faces.flow.builder;

import com.sun.faces.flow.SwitchNodeImpl;
import com.sun.faces.util.Util;
import javax.el.ValueExpression;
import javax.faces.flow.builder.SwitchBuilder;
import javax.faces.flow.builder.SwitchCaseBuilder;

public class SwitchBuilderImpl extends SwitchBuilder {
   private FlowBuilderImpl root;
   private String switchId;
   private SwitchNodeImpl switchNode;
   private SwitchCaseBuilderImpl switchCaseBuilder;

   SwitchBuilderImpl(FlowBuilderImpl root, String id) {
      this.root = root;
      this.switchId = id;
      this.switchNode = new SwitchNodeImpl(id);
      root._getFlow()._getSwitches().put(id, this.switchNode);
      this.switchCaseBuilder = new SwitchCaseBuilderImpl(this);
   }

   public SwitchCaseBuilder defaultOutcome(String outcome) {
      Util.notNull("outcome", outcome);
      this.switchNode.setDefaultOutcome(outcome);
      return this.switchCaseBuilder;
   }

   public SwitchCaseBuilder defaultOutcome(ValueExpression outcome) {
      Util.notNull("outcome", outcome);
      this.switchNode.setDefaultOutcome(outcome);
      return this.switchCaseBuilder;
   }

   public SwitchBuilderImpl markAsStartNode() {
      this.root._getFlow().setStartNodeId(this.switchId);
      return this;
   }

   public SwitchCaseBuilder switchCase() {
      return this.switchCaseBuilder.switchCase();
   }

   FlowBuilderImpl getRoot() {
      return this.root;
   }

   SwitchNodeImpl getSwitchNode() {
      return this.switchNode;
   }
}

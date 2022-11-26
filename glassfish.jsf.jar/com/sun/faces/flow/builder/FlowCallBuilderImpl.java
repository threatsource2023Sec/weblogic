package com.sun.faces.flow.builder;

import com.sun.faces.flow.FlowCallNodeImpl;
import com.sun.faces.flow.ParameterImpl;
import com.sun.faces.util.Util;
import java.util.List;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.flow.builder.FlowCallBuilder;

public class FlowCallBuilderImpl extends FlowCallBuilder {
   private FlowBuilderImpl root;
   private String flowCallNodeId;
   private String flowDocumentId;
   private String flowId;

   public FlowCallBuilderImpl(FlowBuilderImpl root, String id) {
      this.root = root;
      this.flowCallNodeId = id;
   }

   public FlowCallBuilder flowReference(String flowDocumentId, String flowId) {
      Util.notNull("flowDocumentId", flowDocumentId);
      Util.notNull("flowId", flowId);
      this.flowDocumentId = flowDocumentId;
      this.flowId = flowId;
      this.getFlowCall();
      return this;
   }

   private FlowCallNodeImpl getFlowCall() {
      Util.notNull("flowCallNodeId", this.flowCallNodeId);
      Util.notNull("flowwDocumentId", this.flowDocumentId);
      Util.notNull("flowId", this.flowId);
      Map flowCalls = this.root._getFlow()._getFlowCalls();
      FlowCallNodeImpl flowCall = (FlowCallNodeImpl)flowCalls.get(this.flowCallNodeId);
      if (null == flowCall) {
         flowCall = new FlowCallNodeImpl(this.flowCallNodeId, this.flowDocumentId, this.flowId, (List)null);
         flowCalls.put(this.flowCallNodeId, flowCall);
      }

      return flowCall;
   }

   public FlowCallBuilder outboundParameter(String name, ValueExpression value) {
      Util.notNull("name", name);
      Util.notNull("value", value);
      ParameterImpl param = new ParameterImpl(name, value);
      FlowCallNodeImpl flowCall = this.getFlowCall();
      flowCall._getOutboundParameters().put(name, param);
      return this;
   }

   public FlowCallBuilder outboundParameter(String name, String value) {
      Util.notNull("name", name);
      Util.notNull("value", value);
      ValueExpression ve = this.root.getExpressionFactory().createValueExpression(this.root.getELContext(), value, Object.class);
      this.outboundParameter(name, ve);
      return this;
   }

   public FlowCallBuilder markAsStartNode() {
      this.root._getFlow().setStartNodeId(this.flowCallNodeId);
      return this;
   }
}

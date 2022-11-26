package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class OperationMBeanImpl extends XMLElementMBeanDelegate implements OperationMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_invocationStyle = false;
   private String invocationStyle;
   private boolean isSet_componentName = false;
   private String componentName;
   private boolean isSet_style = false;
   private String style;
   private boolean isSet_operationName = false;
   private String operationName;
   private boolean isSet_handlerChain = false;
   private HandlerChainMBean handlerChain;
   private boolean isSet_namespace = false;
   private String namespace;
   private boolean isSet_method = false;
   private String method;
   private boolean isSet_component = false;
   private ComponentMBean component;
   private boolean isSet_conversationPhase = false;
   private String conversationPhase;
   private boolean isSet_inSecuritySpec = false;
   private String inSecuritySpec;
   private boolean isSet_outSecuritySpec = false;
   private String outSecuritySpec;
   private boolean isSet_handlerChainName = false;
   private String handlerChainName;
   private boolean isSet_portTypeName = false;
   private String portTypeName;
   private boolean isSet_params = false;
   private ParamsMBean params;
   private boolean isSet_reliableDelivery = false;
   private ReliableDeliveryMBean reliableDelivery;
   private boolean isSet_encoding = false;
   private String encoding;

   public String getInvocationStyle() {
      return this.invocationStyle;
   }

   public void setInvocationStyle(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.invocationStyle;
      this.invocationStyle = value;
      this.isSet_invocationStyle = value != null;
      this.checkChange("invocationStyle", old, this.invocationStyle);
   }

   public String getComponentName() {
      return this.componentName;
   }

   public void setComponentName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.componentName;
      this.componentName = value;
      this.isSet_componentName = value != null;
      this.checkChange("componentName", old, this.componentName);
   }

   public String getStyle() {
      return this.style;
   }

   public void setStyle(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.style;
      this.style = value;
      this.isSet_style = value != null;
      this.checkChange("style", old, this.style);
   }

   public String getOperationName() {
      return this.operationName;
   }

   public void setOperationName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.operationName;
      this.operationName = value;
      this.isSet_operationName = value != null;
      this.checkChange("operationName", old, this.operationName);
   }

   public HandlerChainMBean getHandlerChain() {
      return this.handlerChain;
   }

   public void setHandlerChain(HandlerChainMBean value) {
      HandlerChainMBean old = this.handlerChain;
      this.handlerChain = value;
      this.isSet_handlerChain = value != null;
      this.checkChange("handlerChain", old, this.handlerChain);
   }

   public String getNamespace() {
      return this.namespace;
   }

   public void setNamespace(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.namespace;
      this.namespace = value;
      this.isSet_namespace = value != null;
      this.checkChange("namespace", old, this.namespace);
   }

   public String getMethod() {
      return this.method;
   }

   public void setMethod(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.method;
      this.method = value;
      this.isSet_method = value != null;
      this.checkChange("method", old, this.method);
   }

   public ComponentMBean getComponent() {
      return this.component;
   }

   public void setComponent(ComponentMBean value) {
      ComponentMBean old = this.component;
      this.component = value;
      this.isSet_component = value != null;
      this.checkChange("component", old, this.component);
   }

   public String getConversationPhase() {
      return this.conversationPhase;
   }

   public void setConversationPhase(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.conversationPhase;
      this.conversationPhase = value;
      this.isSet_conversationPhase = value != null;
      this.checkChange("conversationPhase", old, this.conversationPhase);
   }

   public String getInSecuritySpec() {
      return this.inSecuritySpec;
   }

   public void setInSecuritySpec(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.inSecuritySpec;
      this.inSecuritySpec = value;
      this.isSet_inSecuritySpec = value != null;
      this.checkChange("inSecuritySpec", old, this.inSecuritySpec);
   }

   public String getOutSecuritySpec() {
      return this.outSecuritySpec;
   }

   public void setOutSecuritySpec(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.outSecuritySpec;
      this.outSecuritySpec = value;
      this.isSet_outSecuritySpec = value != null;
      this.checkChange("outSecuritySpec", old, this.outSecuritySpec);
   }

   public String getHandlerChainName() {
      return this.handlerChainName;
   }

   public void setHandlerChainName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.handlerChainName;
      this.handlerChainName = value;
      this.isSet_handlerChainName = value != null;
      this.checkChange("handlerChainName", old, this.handlerChainName);
   }

   public String getPortTypeName() {
      return this.portTypeName;
   }

   public void setPortTypeName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.portTypeName;
      this.portTypeName = value;
      this.isSet_portTypeName = value != null;
      this.checkChange("portTypeName", old, this.portTypeName);
   }

   public ParamsMBean getParams() {
      return this.params;
   }

   public void setParams(ParamsMBean value) {
      ParamsMBean old = this.params;
      this.params = value;
      this.isSet_params = value != null;
      this.checkChange("params", old, this.params);
   }

   public ReliableDeliveryMBean getReliableDelivery() {
      return this.reliableDelivery;
   }

   public void setReliableDelivery(ReliableDeliveryMBean value) {
      ReliableDeliveryMBean old = this.reliableDelivery;
      this.reliableDelivery = value;
      this.isSet_reliableDelivery = value != null;
      this.checkChange("reliableDelivery", old, this.reliableDelivery);
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void setEncoding(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.encoding;
      this.encoding = value;
      this.isSet_encoding = value != null;
      this.checkChange("encoding", old, this.encoding);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<operation");
      if (this.isSet_namespace) {
         result.append(" namespace=\"").append(String.valueOf(this.getNamespace())).append("\"");
      }

      if (this.isSet_invocationStyle) {
         result.append(" invocation-style=\"").append(String.valueOf(this.getInvocationStyle())).append("\"");
      }

      if (this.isSet_encoding) {
         result.append(" encoding=\"").append(String.valueOf(this.getEncoding())).append("\"");
      }

      if (this.isSet_handlerChainName) {
         result.append(" handler-chain=\"").append(String.valueOf(this.getHandlerChainName())).append("\"");
      }

      if (this.isSet_operationName) {
         result.append(" name=\"").append(String.valueOf(this.getOperationName())).append("\"");
      }

      if (this.isSet_style) {
         result.append(" style=\"").append(String.valueOf(this.getStyle())).append("\"");
      }

      if (this.isSet_method) {
         result.append(" method=\"").append(String.valueOf(this.getMethod())).append("\"");
      }

      if (this.isSet_componentName) {
         result.append(" component=\"").append(String.valueOf(this.getComponentName())).append("\"");
      }

      if (this.isSet_portTypeName) {
         result.append(" port-type-name=\"").append(String.valueOf(this.getPortTypeName())).append("\"");
      }

      if (this.isSet_conversationPhase) {
         result.append(" conversation-phase=\"").append(String.valueOf(this.getConversationPhase())).append("\"");
      }

      if (this.isSet_inSecuritySpec) {
         result.append(" in-security-spec=\"").append(String.valueOf(this.getInSecuritySpec())).append("\"");
      }

      if (this.isSet_outSecuritySpec) {
         result.append(" out-security-spec=\"").append(String.valueOf(this.getOutSecuritySpec())).append("\"");
      }

      result.append(">\n");
      if (null != this.getParams()) {
         result.append(this.getParams().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getReliableDelivery()) {
         result.append(this.getReliableDelivery().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</operation>\n");
      return result.toString();
   }
}

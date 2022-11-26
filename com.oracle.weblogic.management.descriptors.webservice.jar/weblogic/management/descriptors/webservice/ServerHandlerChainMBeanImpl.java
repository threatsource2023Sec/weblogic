package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ServerHandlerChainMBeanImpl extends XMLElementMBeanDelegate implements ServerHandlerChainMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_inboundHandlerChain = false;
   private InboundHandlerChainMBean inboundHandlerChain;
   private boolean isSet_outboundHandlerChain = false;
   private OutboundHandlerChainMBean outboundHandlerChain;

   public InboundHandlerChainMBean getInboundHandlerChain() {
      return this.inboundHandlerChain;
   }

   public void setInboundHandlerChain(InboundHandlerChainMBean value) {
      InboundHandlerChainMBean old = this.inboundHandlerChain;
      this.inboundHandlerChain = value;
      this.isSet_inboundHandlerChain = value != null;
      this.checkChange("inboundHandlerChain", old, this.inboundHandlerChain);
   }

   public OutboundHandlerChainMBean getOutboundHandlerChain() {
      return this.outboundHandlerChain;
   }

   public void setOutboundHandlerChain(OutboundHandlerChainMBean value) {
      OutboundHandlerChainMBean old = this.outboundHandlerChain;
      this.outboundHandlerChain = value;
      this.isSet_outboundHandlerChain = value != null;
      this.checkChange("outboundHandlerChain", old, this.outboundHandlerChain);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<server-handler-chain");
      result.append(">\n");
      if (null != this.getInboundHandlerChain()) {
         result.append(this.getInboundHandlerChain().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getOutboundHandlerChain()) {
         result.append(this.getOutboundHandlerChain().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</server-handler-chain>\n");
      return result.toString();
   }
}

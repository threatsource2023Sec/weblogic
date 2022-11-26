package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class InboundHandlerChainMBeanImpl extends XMLElementMBeanDelegate implements InboundHandlerChainMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_handlerChains = false;
   private HandlerChainsMBean handlerChains;

   public HandlerChainsMBean getHandlerChains() {
      return this.handlerChains;
   }

   public void setHandlerChains(HandlerChainsMBean value) {
      HandlerChainsMBean old = this.handlerChains;
      this.handlerChains = value;
      this.isSet_handlerChains = value != null;
      this.checkChange("handlerChains", old, this.handlerChains);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<inbound-handler-chain");
      result.append(">\n");
      if (null != this.getHandlerChains()) {
         result.append(this.getHandlerChains().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</inbound-handler-chain>\n");
      return result.toString();
   }
}

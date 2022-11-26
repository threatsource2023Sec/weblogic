package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class CommonHandlerChainMBeanImpl extends XMLElementMBeanDelegate implements CommonHandlerChainMBean {
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
      result.append(ToXML.indent(indentLevel)).append("<common-handler-chain");
      result.append(">\n");
      if (null != this.getHandlerChains()) {
         result.append(this.getHandlerChains().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</common-handler-chain>\n");
      return result.toString();
   }
}

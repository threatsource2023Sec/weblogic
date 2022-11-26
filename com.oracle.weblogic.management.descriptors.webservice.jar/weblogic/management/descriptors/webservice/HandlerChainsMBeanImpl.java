package weblogic.management.descriptors.webservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class HandlerChainsMBeanImpl extends XMLElementMBeanDelegate implements HandlerChainsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_handlerChains = false;
   private List handlerChains;

   public HandlerChainMBean[] getHandlerChains() {
      if (this.handlerChains == null) {
         return new HandlerChainMBean[0];
      } else {
         HandlerChainMBean[] result = new HandlerChainMBean[this.handlerChains.size()];
         result = (HandlerChainMBean[])((HandlerChainMBean[])this.handlerChains.toArray(result));
         return result;
      }
   }

   public void setHandlerChains(HandlerChainMBean[] value) {
      this.isSet_handlerChains = true;
      if (this.handlerChains == null) {
         this.handlerChains = Collections.synchronizedList(new ArrayList());
      } else {
         this.handlerChains.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.handlerChains.add(value[i]);
         }
      }

   }

   public void addHandlerChain(HandlerChainMBean value) {
      this.isSet_handlerChains = true;
      if (this.handlerChains == null) {
         this.handlerChains = Collections.synchronizedList(new ArrayList());
      }

      this.handlerChains.add(value);
   }

   public void removeHandlerChain(HandlerChainMBean value) {
      if (this.handlerChains != null) {
         this.handlerChains.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      if (null != this.getHandlerChains()) {
         if (this.getHandlerChains().length > 0) {
            result.append(ToXML.indent(indentLevel)).append("<handler-chains");
            result.append(">\n");
         }

         for(int i = 0; i < this.getHandlerChains().length; ++i) {
            result.append(this.getHandlerChains()[i].toXML(indentLevel + 2));
         }

         if (this.getHandlerChains().length > 0) {
            result.append(ToXML.indent(indentLevel)).append("</handler-chains>\n");
         }
      }

      return result.toString();
   }
}

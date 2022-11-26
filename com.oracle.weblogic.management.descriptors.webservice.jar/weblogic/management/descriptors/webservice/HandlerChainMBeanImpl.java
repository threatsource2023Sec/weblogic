package weblogic.management.descriptors.webservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class HandlerChainMBeanImpl extends XMLElementMBeanDelegate implements HandlerChainMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_handlers = false;
   private List handlers;
   private boolean isSet_handlerChainName = false;
   private String handlerChainName;

   public HandlerMBean[] getHandlers() {
      if (this.handlers == null) {
         return new HandlerMBean[0];
      } else {
         HandlerMBean[] result = new HandlerMBean[this.handlers.size()];
         result = (HandlerMBean[])((HandlerMBean[])this.handlers.toArray(result));
         return result;
      }
   }

   public void setHandlers(HandlerMBean[] value) {
      HandlerMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getHandlers();
      }

      this.isSet_handlers = true;
      if (this.handlers == null) {
         this.handlers = Collections.synchronizedList(new ArrayList());
      } else {
         this.handlers.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.handlers.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Handlers", _oldVal, this.getHandlers());
      }

   }

   public void addHandler(HandlerMBean value) {
      this.isSet_handlers = true;
      if (this.handlers == null) {
         this.handlers = Collections.synchronizedList(new ArrayList());
      }

      this.handlers.add(value);
   }

   public void removeHandler(HandlerMBean value) {
      if (this.handlers != null) {
         this.handlers.remove(value);
      }
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

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<handler-chain");
      if (this.isSet_handlerChainName) {
         result.append(" name=\"").append(String.valueOf(this.getHandlerChainName())).append("\"");
      }

      result.append(">\n");
      if (null != this.getHandlers()) {
         for(int i = 0; i < this.getHandlers().length; ++i) {
            result.append(this.getHandlers()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</handler-chain>\n");
      return result.toString();
   }
}

package weblogic.management.descriptors.webservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class WebServicesMBeanImpl extends XMLElementMBeanDelegate implements WebServicesMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_webServices = false;
   private List webServices;
   private boolean isSet_handlerChains = false;
   private HandlerChainsMBean handlerChains;

   public WebServiceMBean[] getWebServices() {
      if (this.webServices == null) {
         return new WebServiceMBean[0];
      } else {
         WebServiceMBean[] result = new WebServiceMBean[this.webServices.size()];
         result = (WebServiceMBean[])((WebServiceMBean[])this.webServices.toArray(result));
         return result;
      }
   }

   public HandlerChainsMBean getHandlerChains() {
      if (this.handlerChains == null) {
         this.handlerChains = new HandlerChainsMBeanImpl();
      }

      return this.handlerChains;
   }

   public void setWebServices(WebServiceMBean[] value) {
      this.isSet_webServices = true;
      if (this.webServices == null) {
         this.webServices = Collections.synchronizedList(new ArrayList());
      } else {
         this.webServices.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.webServices.add(value[i]);
         }
      }

   }

   public void setHandlerChains(HandlerChainsMBean value) {
      this.handlerChains = value;
      this.isSet_handlerChains = true;
   }

   public void addWebService(WebServiceMBean value) {
      this.isSet_webServices = true;
      if (this.webServices == null) {
         this.webServices = Collections.synchronizedList(new ArrayList());
      }

      this.webServices.add(value);
   }

   public void removeWebService(WebServiceMBean value) {
      if (this.webServices != null) {
         this.webServices.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<web-services");
      result.append(">\n");
      if (null != this.getHandlerChains()) {
         result.append(this.getHandlerChains().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getWebServices()) {
         for(int i = 0; i < this.getWebServices().length; ++i) {
            result.append(this.getWebServices()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</web-services>\n");
      return result.toString();
   }
}

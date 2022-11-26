package weblogic.servlet.internal;

import java.util.Collection;
import java.util.Iterator;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;

public class WebAppContainerMBeanUpdateListener extends WebComponentBeanUpdateListener {
   protected Collection httpServers;

   public WebAppContainerMBeanUpdateListener(Collection httpServers) {
      this.httpServers = httpServers;
   }

   protected void handlePropertyRemove(BeanUpdateEvent.PropertyUpdate prop) {
   }

   protected void handlePropertyChange(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) {
      String propertyName = prop.getPropertyName();
      Iterator var4;
      HttpServer server;
      if (propertyName.equals("XPoweredByHeaderLevel")) {
         var4 = this.httpServers.iterator();

         while(var4.hasNext()) {
            server = (HttpServer)var4.next();
            server.setXPoweredByHeader();
         }
      }

      if (propertyName.equals("HttpTraceSupportEnabled")) {
         var4 = this.httpServers.iterator();

         while(var4.hasNext()) {
            server = (HttpServer)var4.next();
            server.setHttpTraceSupportEnabled();
         }
      }

      if (propertyName.equals("WeblogicPluginEnabled")) {
         var4 = this.httpServers.iterator();

         while(var4.hasNext()) {
            server = (HttpServer)var4.next();
            server.setWeblogicPluginEnabled();
         }
      }

      if (propertyName.equals("AuthCookieEnabled")) {
         var4 = this.httpServers.iterator();

         while(var4.hasNext()) {
            server = (HttpServer)var4.next();
            server.setAuthCookieEnabled();
         }
      }

      if (propertyName.equals("WAPEnabled")) {
         var4 = this.httpServers.iterator();

         while(var4.hasNext()) {
            server = (HttpServer)var4.next();
            server.setWAPEnabled();
         }
      }

   }

   protected void prepareBeanAdd(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) throws BeanUpdateRejectedException {
   }

   protected void handleBeanAdd(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) {
   }

   protected void handleBeanRemove(BeanUpdateEvent.PropertyUpdate prop) {
   }
}

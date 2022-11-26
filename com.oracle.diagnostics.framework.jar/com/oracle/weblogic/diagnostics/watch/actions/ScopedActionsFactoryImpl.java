package com.oracle.weblogic.diagnostics.watch.actions;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ServiceHandle;

class ScopedActionsFactoryImpl implements ScopedActionsFactory {
   @Inject
   private ActionsManager manager;
   private Map actionHandles = new HashMap();
   private Annotation[] qualifiers = new Annotation[0];
   private String[] scopeActionTypes = null;

   protected ScopedActionsFactoryImpl(Annotation... qualifiers) {
      this.qualifiers = qualifiers;
   }

   public String[] getActionTypes() {
      if (this.scopeActionTypes == null) {
         this.scopeActionTypes = this.manager.getKnownActionTypes(this.qualifiers);
      }

      return (String[])Arrays.copyOf(this.scopeActionTypes, this.scopeActionTypes.length);
   }

   public Action getAction(String type) {
      Action serviceInstance = null;
      ServiceHandle handle = this.getActionServiceHandle(type);
      if (handle != null) {
         serviceInstance = (Action)handle.getService();
      }

      return serviceInstance;
   }

   public ActionConfigBean getActionConfig(String type) {
      return this.manager.getActionConfig(type);
   }

   public void destroy() {
      Iterator iterator = this.actionHandles.values().iterator();

      while(iterator.hasNext()) {
         List handles = (List)iterator.next();

         for(Iterator handlesIt = handles.iterator(); handlesIt.hasNext(); handlesIt.remove()) {
            ServiceHandle handle = (ServiceHandle)handlesIt.next();
            if (handle.isActive() && !this.isSingleton(handle)) {
               handle.destroy();
            }
         }

         iterator.remove();
      }

   }

   private boolean isSingleton(ServiceHandle handle) {
      Action service = handle.isActive() ? (Action)handle.getService() : null;
      return service != null && service.getClass().getAnnotation(Singleton.class) != null;
   }

   private ServiceHandle getActionServiceHandle(String type) {
      List handleList = (List)this.actionHandles.get(type);
      if (handleList == null) {
         handleList = new ArrayList();
         this.actionHandles.put(type, handleList);
      }

      ServiceHandle handle = this.manager.getActionServiceHandle(type, this.qualifiers);
      if (handle != null) {
         ((List)handleList).add(handle);
      }

      return handle;
   }
}

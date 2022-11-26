package com.sun.faces.lifecycle;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

public class LifecycleFactoryImpl extends LifecycleFactory {
   private static Logger LOGGER;
   protected ConcurrentHashMap lifecycleMap = null;

   public LifecycleFactoryImpl() {
      super((LifecycleFactory)null);
      this.lifecycleMap = new ConcurrentHashMap();
      this.lifecycleMap.put("DEFAULT", new LifecycleImpl(FacesContext.getCurrentInstance()));
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("Created Default Lifecycle");
      }

   }

   public void addLifecycle(String lifecycleId, Lifecycle lifecycle) {
      if (lifecycleId == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "lifecycleId"));
      } else if (lifecycle == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "lifecycle"));
      } else if (null != this.lifecycleMap.get(lifecycleId)) {
         Object[] params = new Object[]{lifecycleId};
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.LIFECYCLE_ID_ALREADY_ADDED", params);
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.warning(MessageUtils.getExceptionMessageString("com.sun.faces.LIFECYCLE_ID_ALREADY_ADDED", params));
         }

         throw new IllegalArgumentException(message);
      } else {
         this.lifecycleMap.put(lifecycleId, lifecycle);
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("addedLifecycle: " + lifecycleId + " " + lifecycle);
         }

      }
   }

   public Lifecycle getLifecycle(String lifecycleId) throws FacesException {
      if (null == lifecycleId) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "lifecycleId"));
      } else if (null == this.lifecycleMap.get(lifecycleId)) {
         Object[] params = new Object[]{lifecycleId};
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.CANT_CREATE_LIFECYCLE_ERROR", params);
         throw new IllegalArgumentException(message);
      } else {
         Lifecycle result = (Lifecycle)this.lifecycleMap.get(lifecycleId);
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("getLifecycle: " + lifecycleId + " " + result);
         }

         return result;
      }
   }

   public Iterator getLifecycleIds() {
      return this.lifecycleMap.keySet().iterator();
   }

   static {
      LOGGER = FacesLogger.LIFECYCLE.getLogger();
   }
}

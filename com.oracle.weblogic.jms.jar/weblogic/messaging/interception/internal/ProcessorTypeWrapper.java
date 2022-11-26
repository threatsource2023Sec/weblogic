package weblogic.messaging.interception.internal;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import weblogic.messaging.interception.MIExceptionLogger;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.messaging.interception.interfaces.ProcessorFactory;
import weblogic.messaging.interception.interfaces.ProcessorHandle;

class ProcessorTypeWrapper {
   private HashMap processorWrapperMap = new HashMap(0);
   private String name = null;
   private ProcessorFactory factory = null;

   ProcessorTypeWrapper(String name, String factoryName) throws InterceptionServiceException {
      this.name = name;
      this.factory = this.instantiateProcessorFactory(factoryName);
   }

   ProcessorTypeWrapper(String name) {
      this.name = name;
   }

   private ProcessorFactory instantiateProcessorFactory(String factoryName) throws InterceptionServiceException {
      try {
         Class factoryClass = Class.forName(factoryName);
         Constructor[] constructors = factoryClass.getConstructors();
         Constructor constructor = null;

         for(int i = 0; i < constructors.length; ++i) {
            Class[] parameters = constructors[i].getParameterTypes();
            if (parameters.length == 0) {
               constructor = constructors[i];
               break;
            }
         }

         if (constructor == null) {
            throw new InterceptionServiceException(MIExceptionLogger.logProcessorFactoryCreateErrorLoggable("ProcessorFactory requires no argument constructor").getMessage());
         } else {
            return (ProcessorFactory)constructor.newInstance((Object[])null);
         }
      } catch (Exception var7) {
         if (var7 instanceof InterceptionServiceException) {
            throw (InterceptionServiceException)var7;
         } else {
            throw new InterceptionServiceException(MIExceptionLogger.logProcessorFactoryCreateUnknownErrorLoggable("Fail to construct ProcessorFactory").getMessage(), var7);
         }
      }
   }

   String getName() {
      return this.name;
   }

   ProcessorFactory getFactory() {
      return this.factory;
   }

   void setFactoryName(String factoryName) throws InterceptionServiceException {
      this.factory = this.instantiateProcessorFactory(factoryName);
   }

   synchronized ProcessorWrapper findOrCreateProcessorWrapper(String processorName) {
      ProcessorWrapper p = (ProcessorWrapper)this.processorWrapperMap.get(processorName);
      if (p == null) {
         p = new ProcessorWrapper(this.name, processorName, this);
         this.processorWrapperMap.put(processorName, p);
      }

      return p;
   }

   synchronized ProcessorWrapper findProcessorWrapper(String processorName) {
      return (ProcessorWrapper)this.processorWrapperMap.get(processorName);
   }

   void removeProcessorWrapper(String processorName) {
      this.processorWrapperMap.remove(processorName);
   }

   Iterator getProcessors() {
      HashMap pwMap = null;
      HashMap pHandleMap = new HashMap(0);
      synchronized(this) {
         pwMap = (HashMap)this.processorWrapperMap.clone();
      }

      Object[] pws = pwMap.values().toArray();

      for(int i = 0; i < pws.length; ++i) {
         ProcessorWrapper pw = (ProcessorWrapper)pws[i];
         ProcessorHandle pHandle = pw.getProcessorHandle();
         if (pHandle != null) {
            pHandleMap.put(pw.getName(), pHandle);
         }
      }

      return pHandleMap.values().iterator();
   }

   public int getProcessorsSize() {
      return this.processorWrapperMap.keySet().size();
   }
}

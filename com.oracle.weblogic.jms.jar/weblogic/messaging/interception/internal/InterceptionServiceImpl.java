package weblogic.messaging.interception.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import weblogic.messaging.interception.MIExceptionLogger;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.messaging.interception.interfaces.AssociationHandle;
import weblogic.messaging.interception.interfaces.AssociationListener;
import weblogic.messaging.interception.interfaces.InterceptionPointHandle;
import weblogic.messaging.interception.interfaces.InterceptionPointNameDescriptionListener;
import weblogic.messaging.interception.interfaces.InterceptionPointNameDescriptor;
import weblogic.messaging.interception.interfaces.InterceptionService;
import weblogic.messaging.interception.interfaces.Processor;
import weblogic.messaging.interception.interfaces.ProcessorHandle;

public class InterceptionServiceImpl implements InterceptionService {
   private HashMap interceptionPointTypeMap = new HashMap(0);
   private HashMap interceptionPointTypeListenersMap = new HashMap(0);
   private HashMap processorTypeMap = new HashMap(0);
   private static InterceptionServiceImpl singleton = null;

   private InterceptionServiceImpl() {
   }

   public static synchronized InterceptionService getInterceptionService() {
      if (singleton == null) {
         singleton = new InterceptionServiceImpl();
      }

      return singleton;
   }

   public AssociationHandle addAssociation(String interceptionPointType, String[] interceptionPointName, String processorType, String processorName, boolean activated, int depth) throws InterceptionServiceException {
      if (interceptionPointType == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logAddAssociationInputErrorLoggable("InterceptionPointType cannot be null").getMessage());
      } else if (interceptionPointName == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logAddAssociationInputErrorLoggable("InterceptionPointName cannot be null").getMessage());
      } else if (processorType == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logAddAssociationInputErrorLoggable("ProcessorType cannot be null").getMessage());
      } else if (processorName == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logAddAssociationInputErrorLoggable("ProcessorName cannot be null").getMessage());
      } else {
         String[] copyIPName = copyIPName(interceptionPointName);
         InterceptionPointTypeWrapper iptw;
         ProcessorTypeWrapper ptw;
         synchronized(this) {
            iptw = (InterceptionPointTypeWrapper)this.interceptionPointTypeMap.get(interceptionPointType);
            ptw = (ProcessorTypeWrapper)this.processorTypeMap.get(processorType);
            if (iptw == null) {
               throw new InterceptionServiceException(MIExceptionLogger.logAddAssociationUnknownInterceptionPointTypeErrorLoggable("Unknown InterceptionPointType").getMessage());
            }

            if (ptw == null) {
               ptw = new ProcessorTypeWrapper(processorType);
               this.processorTypeMap.put(processorType, ptw);
            }
         }

         iptw.validate(copyIPName);
         InterceptionPoint ip = iptw.findOrCreateInterceptionPoint(copyIPName);
         ProcessorWrapper pw = ptw.findOrCreateProcessorWrapper(processorName);
         AssociationListener listener = iptw.getAssociationListener();
         if (listener != null) {
            listener.onAddAssociation(interceptionPointType, copyIPName(interceptionPointName), processorType, processorName, activated, depth);
         }

         return AssociationManager.createAssociation(ip, pw, activated, depth);
      }
   }

   public AssociationHandle addAssociation(String interceptionPointType, String[] interceptionPointName, String processorType, String processorName, boolean activated) throws InterceptionServiceException {
      return this.addAssociation(interceptionPointType, interceptionPointName, processorType, processorName, activated, Integer.MAX_VALUE);
   }

   public void removeAssociation(AssociationHandle associationHandle) throws InterceptionServiceException {
      AssociationListener listener = null;
      if (associationHandle == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logRemoveAssociationInputErrorLoggable("AssociationHandle cannot be null").getMessage());
      } else {
         Association a = (Association)associationHandle;
         AssociationManager.removeAssociation(a);
         synchronized(this) {
            InterceptionPointTypeWrapper iptw = (InterceptionPointTypeWrapper)this.interceptionPointTypeMap.get(a.getIPType());
            listener = iptw.getAssociationListener();
         }

         if (listener != null) {
            listener.onRemoveAssociation(a.getIPType(), a.getIPName(), a.getPType(), a.getPName());
         }
      }
   }

   public void registerInterceptionPointNameDescription(String interceptionPointType, InterceptionPointNameDescriptor[] descriptor, AssociationListener listener) throws InterceptionServiceException {
      if (interceptionPointType == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logRegisterInterceptionPointNameDescriptionInputErrorLoggable("InterceptionPointType cannot be null").getMessage());
      } else if (descriptor == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logRegisterInterceptionPointNameDescriptionInputErrorLoggable("Descriptor cannot be null").getMessage());
      } else {
         Iterator itr = null;
         synchronized(this) {
            InterceptionPointTypeWrapper iptw = (InterceptionPointTypeWrapper)this.interceptionPointTypeMap.get(interceptionPointType);
            if (iptw != null) {
               throw new InterceptionServiceException(MIExceptionLogger.logRegisterInterceptionPointNameDescriptionInputErrorLoggable("InterceptionPointType has been registered").getMessage());
            }

            InterceptionPointNameDescriptor[] copyIPND = copyIPND(descriptor);
            iptw = new InterceptionPointTypeWrapper(interceptionPointType, copyIPND, listener);
            this.interceptionPointTypeMap.put(interceptionPointType, iptw);
            itr = this.removeListeners(interceptionPointType);
         }

         this.notifyListeners(itr);
      }
   }

   public synchronized InterceptionPointNameDescriptor[] getInterceptionPointNameDescription(String interceptionPointType) {
      if (interceptionPointType == null) {
         return null;
      } else {
         InterceptionPointTypeWrapper iptw = (InterceptionPointTypeWrapper)this.interceptionPointTypeMap.get(interceptionPointType);
         return iptw == null ? null : copyIPND(iptw.getIPND());
      }
   }

   public InterceptionPointHandle registerInterceptionPoint(String interceptionPointType, String[] interceptionPointName) throws InterceptionServiceException {
      if (interceptionPointType == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logRegisterInterceptionPointInputErrorLoggable("InterceptionPointType cannot be null").getMessage());
      } else if (interceptionPointName == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logRegisterInterceptionPointInputErrorLoggable("InterceptionPointName cannot be null").getMessage());
      } else {
         String[] copyIPName = copyIPName(interceptionPointName);
         InterceptionPointTypeWrapper iptw;
         synchronized(this) {
            iptw = (InterceptionPointTypeWrapper)this.interceptionPointTypeMap.get(interceptionPointType);
            if (iptw == null) {
               throw new InterceptionServiceException(MIExceptionLogger.logRegisterInterceptionPointUnknownInterceptionPointTypeErrorLoggable("Unknown InterceptionPointType").getMessage());
            }
         }

         iptw.validate(copyIPName);
         InterceptionPoint ip = iptw.findOrCreateInterceptionPoint(copyIPName);
         return ip.createHandle();
      }
   }

   public void unRegisterInterceptionPoint(InterceptionPointHandle handle) throws InterceptionServiceException {
      if (handle == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logUnRegisterInterceptionPointInputErrorLoggable("InterceptionPointHandle cannot be null").getMessage());
      } else {
         ((InterceptionPointHandleImpl)handle).unregister();
      }
   }

   public void registerProcessorType(String type, String factoryName) throws InterceptionServiceException {
      if (type == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logRegisterProcessorTypeInputErrorLoggable("ProcessorType cannot be null").getMessage());
      } else if (factoryName == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logRegisterProcessorTypeInputErrorLoggable("ProcessorFactory cannot be null").getMessage());
      } else {
         synchronized(this.processorTypeMap) {
            ProcessorTypeWrapper ptw = (ProcessorTypeWrapper)this.processorTypeMap.get(type);
            if (ptw != null && ptw.getFactory() != null) {
               throw new InterceptionServiceException(MIExceptionLogger.logRegisterProcessorTypeInputErrorLoggable("ProcessorType has been registered").getMessage());
            } else {
               if (ptw == null) {
                  ptw = new ProcessorTypeWrapper(type, factoryName);
                  this.processorTypeMap.put(type, ptw);
               } else {
                  ptw.setFactoryName(factoryName);
               }

            }
         }
      }
   }

   public ProcessorHandle addProcessor(String type, String name, String metaData) throws InterceptionServiceException {
      if (type == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logAddProcessorInputErrorLoggable("ProcessorType cannot be null").getMessage());
      } else if (name == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logAddProcessorInputErrorLoggable("ProcessorName cannot be null").getMessage());
      } else {
         ProcessorTypeWrapper ptw;
         synchronized(this) {
            ptw = (ProcessorTypeWrapper)this.processorTypeMap.get(type);
            if (ptw == null) {
               throw new InterceptionServiceException(MIExceptionLogger.logAddProcessorUnknownProcessorTypeErrorLoggable("Unknown ProcessorType").getMessage());
            }
         }

         ProcessorWrapper pw = ptw.findOrCreateProcessorWrapper(name);
         InterceptionServiceException ise = null;
         synchronized(pw) {
            if (pw.getProcessor() != null) {
               throw new InterceptionServiceException(MIExceptionLogger.logAddProcessorInputErrorLoggable("Processor exists").getMessage());
            }

            Processor p = null;

            try {
               p = ptw.getFactory().create(name, metaData);
            } catch (RuntimeException var11) {
               throw new InterceptionServiceException(MIExceptionLogger.logAddProcessorInputErrorLoggable("Failed to create processor").getMessage(), var11);
            } catch (Error var12) {
               throw new InterceptionServiceException(MIExceptionLogger.logAddProcessorInputErrorLoggable("Failed to create processor").getMessage(), var12);
            }

            ise = pw.addProcessor(p);
         }

         if (ise != null) {
            throw ise;
         } else {
            return new ProcessorHandleImpl(pw);
         }
      }
   }

   public void removeProcessor(ProcessorHandle handle) throws InterceptionServiceException {
      if (handle == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logRemoveProcessorInputErrorLoggable("ProcessorHandle cannot be null").getMessage());
      } else {
         ProcessorWrapper pw = ((ProcessorHandleImpl)handle).getProcessorWrapper();
         if (pw == null) {
            throw new InterceptionServiceException(MIExceptionLogger.logRemoveProcessorInputErrorLoggable("Processor has been removed").getMessage());
         } else {
            InterceptionServiceException ise = pw.removeProcessor(handle);
            pw.removeProcessorWrapperIfNotUsed();
            if (ise != null) {
               throw ise;
            }
         }
      }
   }

   public void removeProcessor(String type, String name) throws InterceptionServiceException {
      if (type == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logRemoveProcessorInputErrorLoggable("ProcessorType cannot be null").getMessage());
      } else if (name == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logRemoveProcessorInputErrorLoggable("ProcessorName cannot be null").getMessage());
      } else {
         ProcessorTypeWrapper ptw;
         synchronized(this) {
            ptw = (ProcessorTypeWrapper)this.processorTypeMap.get(type);
            if (ptw == null) {
               throw new InterceptionServiceException(MIExceptionLogger.logRemoveProcessorInputErrorLoggable("Unknown ProcessorType").getMessage());
            }
         }

         ProcessorWrapper pw = ptw.findProcessorWrapper(name);
         if (pw == null) {
            throw new InterceptionServiceException(MIExceptionLogger.logRemoveProcessorInputErrorLoggable("Processor not found").getMessage());
         } else {
            InterceptionServiceException ise = pw.removeProcessor((ProcessorHandle)null);
            pw.removeProcessorWrapperIfNotUsed();
            if (ise != null) {
               throw ise;
            }
         }
      }
   }

   public Iterator getAssociationHandles() {
      return AssociationManager.getAssociations();
   }

   public AssociationHandle getAssociationHandle(String interceptionPointType, String[] interceptionPointName) throws InterceptionServiceException {
      if (interceptionPointType == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logGetAssociationHandleInputErrorLoggable("InterceptionPointType cannot be null").getMessage());
      } else if (interceptionPointName == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logGetAssociationHandleInputErrorLoggable("InterceptionPointName cannot be null").getMessage());
      } else {
         String[] copyIPName = copyIPName(interceptionPointName);
         InterceptionPointTypeWrapper iptw;
         synchronized(this) {
            iptw = (InterceptionPointTypeWrapper)this.interceptionPointTypeMap.get(interceptionPointType);
            if (iptw == null) {
               throw new InterceptionServiceException(MIExceptionLogger.logGetAssociationHandleInputErrorLoggable("Unknown InterceptionPointType").getMessage());
            }
         }

         iptw.validate(copyIPName);
         InterceptionPoint ip = iptw.findInterceptionPoint(copyIPName);
         return ip == null ? null : ip.getAssociation();
      }
   }

   public Iterator getProcessorHandles(String type) throws InterceptionServiceException {
      if (type == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logGetProcessorHandlesInputErrorLoggable("ProcessorType cannot be null").getMessage());
      } else {
         ProcessorTypeWrapper ptw;
         synchronized(this) {
            ptw = (ProcessorTypeWrapper)this.processorTypeMap.get(type);
            if (ptw == null) {
               throw new InterceptionServiceException(MIExceptionLogger.logGetProcessorHandlesInputErrorLoggable("Unknown ProcessorType").getMessage());
            }
         }

         return ptw.getProcessors();
      }
   }

   public ProcessorHandle getProcessorHandle(String type, String name) throws InterceptionServiceException {
      if (type == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logGetProcessorHandleInputErrorLoggable("ProcessorType cannot be null").getMessage());
      } else if (name == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logGetProcessorHandleInputErrorLoggable("ProcessorName cannot be null").getMessage());
      } else {
         ProcessorTypeWrapper ptw;
         synchronized(this) {
            ptw = (ProcessorTypeWrapper)this.processorTypeMap.get(type);
            if (ptw == null) {
               throw new InterceptionServiceException(MIExceptionLogger.logGetProcessorHandleInputErrorLoggable("Unknown ProcessorType").getMessage());
            }
         }

         ProcessorWrapper pw = ptw.findProcessorWrapper(name);
         return pw == null ? null : pw.getProcessorHandle();
      }
   }

   public void registerInterceptionPointNameDescriptionListener(InterceptionPointNameDescriptionListener listener) throws InterceptionServiceException {
      if (listener == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logRegisterInterceptionPointNameDescriptionListenerInputErrorLoggable("Listener cannot be null").getMessage());
      } else if (listener.getType() == null) {
         throw new InterceptionServiceException(MIExceptionLogger.logRegisterInterceptionPointNameDescriptionListenerInputErrorLoggable("Listener.getType() cannot be null").getMessage());
      } else {
         Object obj = null;
         synchronized(this) {
            obj = this.interceptionPointTypeMap.get(listener.getType());
         }

         if (obj != null) {
            this.notifyListener(listener);
         } else {
            this.addListener(listener);
         }

      }
   }

   public static String[] copyIPName(String[] interceptionPointName) {
      String[] copyIPName = new String[interceptionPointName.length];

      for(int i = 0; i < interceptionPointName.length; ++i) {
         copyIPName[i] = interceptionPointName[i];
      }

      return copyIPName;
   }

   public static InterceptionPointNameDescriptor[] copyIPND(InterceptionPointNameDescriptor[] descriptor) {
      InterceptionPointNameDescriptor[] copyIPND = new InterceptionPointNameDescriptor[descriptor.length];

      for(int i = 0; i < descriptor.length; ++i) {
         copyIPND[i] = descriptor[i];
      }

      return copyIPND;
   }

   public int getIPTMapSize(String name) {
      if (name == null) {
         return this.interceptionPointTypeMap.keySet().size();
      } else {
         Iterator itr = this.interceptionPointTypeMap.keySet().iterator();
         int i = 0;

         while(itr.hasNext()) {
            String myname = (String)itr.next();
            if (name.equals(myname)) {
               ++i;
            }
         }

         return i;
      }
   }

   public int getPTMapSize() {
      return this.processorTypeMap.keySet().size();
   }

   public int getIPsSize(String name) {
      if (this.getIPTMapSize((String)null) == 0) {
         return 0;
      } else {
         Iterator itr = null;
         synchronized(this) {
            itr = ((HashMap)this.interceptionPointTypeMap.clone()).values().iterator();
         }

         int i = 0;

         while(true) {
            InterceptionPointTypeWrapper iptw;
            do {
               if (!itr.hasNext()) {
                  return i;
               }

               iptw = (InterceptionPointTypeWrapper)itr.next();
            } while(name != null && name.equals(iptw.getName()));

            i += iptw.getIPsSize();
         }
      }
   }

   public int getAssociationsSize(String name) {
      return AssociationManager.getAssociationsSize(name);
   }

   public int getProcessorsSize() {
      if (this.getPTMapSize() == 0) {
         return 0;
      } else {
         Iterator itr = null;
         synchronized(this) {
            itr = ((HashMap)this.processorTypeMap.clone()).values().iterator();
         }

         int i;
         ProcessorTypeWrapper ptw;
         for(i = 0; itr.hasNext(); i += ptw.getProcessorsSize()) {
            ptw = (ProcessorTypeWrapper)itr.next();
         }

         return i;
      }
   }

   private void notifyListener(InterceptionPointNameDescriptionListener listener) {
      listener.onRegister();
   }

   private void notifyListeners(Iterator itr) {
      InterceptionPointNameDescriptionListener listener = null;

      while(itr.hasNext()) {
         listener = (InterceptionPointNameDescriptionListener)itr.next();
         this.notifyListener(listener);
      }

   }

   private synchronized void addListener(InterceptionPointNameDescriptionListener listener) {
      List list = (List)this.interceptionPointTypeListenersMap.get(listener.getType());
      if (list == null) {
         list = new LinkedList();
         this.interceptionPointTypeListenersMap.put(listener.getType(), list);
      }

      ((List)list).add(listener);
   }

   private synchronized Iterator removeListeners(String name) {
      List list = (List)this.interceptionPointTypeListenersMap.get(name);
      if (list == null) {
         return (new LinkedList()).listIterator();
      } else {
         this.interceptionPointTypeListenersMap.remove(name);
         return list.listIterator();
      }
   }
}

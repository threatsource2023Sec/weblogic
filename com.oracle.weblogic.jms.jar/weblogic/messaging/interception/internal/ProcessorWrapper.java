package weblogic.messaging.interception.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.messaging.interception.interfaces.Processor;
import weblogic.messaging.interception.interfaces.ProcessorHandle;

public class ProcessorWrapper {
   private String type = null;
   private String name = null;
   private Processor p = null;
   private HashMap associationsMap = new HashMap(0);
   private long registrationTime = 0L;
   private ProcessorHandle pHandle = null;
   private boolean forcedShutdown = false;
   private ProcessorTypeWrapper ptw = null;

   ProcessorWrapper(String type, String name, ProcessorTypeWrapper ptw) {
      this.type = type;
      this.name = name;
      this.ptw = ptw;
   }

   public String getType() {
      return this.type;
   }

   public String getName() {
      return this.name;
   }

   synchronized void setProcessorHandle(ProcessorHandle pHandle) {
      this.pHandle = pHandle;
   }

   synchronized ProcessorHandle getProcessorHandle() {
      return this.pHandle;
   }

   synchronized long getRegistrationTime() {
      return this.registrationTime;
   }

   synchronized Processor getProcessor() {
      return this.p;
   }

   InterceptionServiceException addProcessor(Processor p) {
      InterceptionServiceException ise = null;
      int count;
      synchronized(this) {
         this.p = p;
         this.registrationTime = System.currentTimeMillis();
         count = this.associationsMap.size();
         this.forcedShutdown = false;
      }

      if (count > 0) {
         ise = updateState(p, true);
      }

      if (ise != null) {
         synchronized(this) {
            if (p == this.p) {
               this.p = null;
               this.registrationTime = 0L;
               this.forcedShutdown = true;
            }
         }
      }

      return ise;
   }

   InterceptionServiceException removeProcessor(ProcessorHandle pHandle) {
      Processor proc = null;
      synchronized(this) {
         if (pHandle != null && this.pHandle != pHandle) {
            return new InterceptionServiceException("Processor has been removed");
         }

         proc = this.getProcessor();
      }

      if (proc == null) {
         return new InterceptionServiceException("Processor has been removed");
      } else {
         InterceptionServiceException ise = this.removeProcessor(proc, false);
         return ise;
      }
   }

   InterceptionServiceException removeProcessor(Processor p, boolean forcedShutdown) {
      InterceptionServiceException ise = null;
      ProcessorHandle pHandle = null;
      synchronized(this) {
         if (p == this.p) {
            this.p = null;
            this.registrationTime = 0L;
            pHandle = this.pHandle;
            this.pHandle = null;
            this.forcedShutdown = forcedShutdown;
         }
      }

      if (pHandle != null) {
         ((ProcessorHandleImpl)pHandle).removeProcessorWrapper();
      }

      ise = shutdownProcessor(p);
      return ise;
   }

   private static InterceptionServiceException shutdownProcessor(Processor proc) {
      Throwable throwable = null;

      try {
         proc.onShutdown();
      } catch (RuntimeException var3) {
         throwable = var3;
      } catch (Error var4) {
         throwable = var4;
      }

      if (throwable == null) {
         return null;
      } else {
         return throwable instanceof Error ? new InterceptionServiceException("Processor throws illegal error", (Throwable)throwable) : new InterceptionServiceException("Processor throws illegal runtime exception", (Throwable)throwable);
      }
   }

   private static InterceptionServiceException updateState(Processor p, boolean state) {
      InterceptionServiceException ise = null;
      Throwable t = null;

      try {
         p.associationStateChange(state);
      } catch (RuntimeException var5) {
         t = var5;
         ise = shutdownProcessor(p);
      } catch (Error var6) {
         t = var6;
         ise = shutdownProcessor(p);
      }

      if (ise != null) {
         return ise;
      } else if (t != null) {
         return t instanceof Error ? new InterceptionServiceException("Processor throws illegal error", (Throwable)t) : new InterceptionServiceException("Processor throws illegal runtime exception", (Throwable)t);
      } else {
         return null;
      }
   }

   void addAssociation(Association association) throws InterceptionServiceException {
      int count;
      Processor proc;
      synchronized(this) {
         this.associationsMap.put(association.getInternalName(), association);
         count = this.associationsMap.size();
         proc = this.p;
      }

      if (count == 1 && proc != null) {
         InterceptionServiceException ise = updateState(proc, true);
         if (ise != null) {
            synchronized(this) {
               if (proc == this.p) {
                  this.p = null;
                  this.registrationTime = 0L;
                  this.forcedShutdown = true;
               }
            }

            throw ise;
         }
      }

   }

   void removeProcessorWrapperIfNotUsed() {
      synchronized(this.ptw) {
         synchronized(this) {
            if (this.pHandle != null || this.p != null || this.associationsMap.size() > 0) {
               return;
            }

            this.ptw.removeProcessorWrapper(this.name);
         }

      }
   }

   void removeAssociation(Association association) throws InterceptionServiceException {
      Processor proc;
      int count;
      synchronized(this) {
         proc = this.p;
         this.associationsMap.remove(association.getInternalName());
         count = this.associationsMap.size();
      }

      if (count == 0 && proc != null) {
         InterceptionServiceException ise = updateState(proc, false);
         if (ise != null) {
            synchronized(this) {
               if (proc == this.p) {
                  this.p = null;
                  this.registrationTime = 0L;
                  this.forcedShutdown = true;
               }
            }

            throw ise;
         }
      }

   }

   public synchronized Iterator getAssociationInfos() {
      HashMap map = null;
      synchronized(this) {
         map = (HashMap)this.associationsMap.clone();
      }

      Object[] associations = map.values().toArray();
      LinkedList infos = new LinkedList();

      for(int i = 0; i < associations.length; ++i) {
         infos.add(((Association)associations[i]).getInfoInternal());
      }

      return infos.listIterator();
   }

   synchronized boolean forcedShutdown() {
      return this.forcedShutdown;
   }
}

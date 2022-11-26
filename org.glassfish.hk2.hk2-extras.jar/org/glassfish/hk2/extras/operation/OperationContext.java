package org.glassfish.hk2.extras.operation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.extras.operation.internal.OperationHandleImpl;
import org.glassfish.hk2.extras.operation.internal.SingleOperationManager;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.jvnet.hk2.annotations.Contract;

@Contract
public abstract class OperationContext implements Context {
   private SingleOperationManager manager;
   private final HashMap operationMap = new HashMap();
   private final HashSet creating = new HashSet();
   private final HashMap closingOperations = new HashMap();
   private boolean shuttingDown = false;

   public Object findOrCreate(ActiveDescriptor activeDescriptor, ServiceHandle root) {
      SingleOperationManager localManager;
      LinkedList closingOperationStack;
      boolean closingOperation;
      synchronized(this) {
         localManager = this.manager;
         closingOperationStack = (LinkedList)this.closingOperations.get(Thread.currentThread().getId());
         closingOperation = closingOperationStack != null && !closingOperationStack.isEmpty();
      }

      if (localManager == null) {
         throw new IllegalStateException("There is no manager for " + this.getScope().getName() + " on thread " + Thread.currentThread().getId());
      } else {
         OperationHandleImpl operation = localManager.getCurrentOperationOnThisThread();
         if (operation == null) {
            synchronized(this) {
               if (!closingOperation) {
                  throw new IllegalStateException("There is no current operation of type " + this.getScope().getName() + " on thread " + Thread.currentThread().getId());
               }

               operation = (OperationHandleImpl)closingOperationStack.get(0);
            }
         }

         LinkedHashMap serviceMap;
         synchronized(this) {
            serviceMap = (LinkedHashMap)this.operationMap.get(operation);
            if (serviceMap == null) {
               if (closingOperation || this.shuttingDown) {
                  throw new IllegalStateException("The operation " + operation.getIdentifier() + " is closing.  A new instance of " + activeDescriptor + " cannot be created");
               }

               serviceMap = new LinkedHashMap();
               this.operationMap.put(operation, serviceMap);
            }

            Object retVal = serviceMap.get(activeDescriptor);
            if (retVal != null) {
               return retVal;
            }

            if (this.supportsNullCreation() && serviceMap.containsKey(activeDescriptor)) {
               return null;
            }

            if (closingOperation || this.shuttingDown) {
               throw new IllegalStateException("The operation " + operation.getIdentifier() + " is closing.  A new instance of " + activeDescriptor + " cannot be created after searching existing descriptors");
            }

            while(true) {
               if (!this.creating.contains(activeDescriptor)) {
                  retVal = serviceMap.get(activeDescriptor);
                  if (retVal != null) {
                     return retVal;
                  }

                  if (this.supportsNullCreation() && serviceMap.containsKey(activeDescriptor)) {
                     return null;
                  }

                  this.creating.add(activeDescriptor);
                  break;
               }

               try {
                  this.wait();
               } catch (InterruptedException var26) {
                  throw new RuntimeException(var26);
               }
            }
         }

         Object retVal = null;
         boolean success = false;
         boolean var23 = false;

         try {
            var23 = true;
            retVal = activeDescriptor.create(root);
            if (retVal == null && !this.supportsNullCreation()) {
               throw new IllegalArgumentException("The operation for context " + this.getScope().getName() + " does not support null creation, but descriptor " + activeDescriptor + " returned null");
            }

            success = true;
            var23 = false;
         } finally {
            if (var23) {
               synchronized(this) {
                  if (success) {
                     serviceMap.put(activeDescriptor, retVal);
                  }

                  this.creating.remove(activeDescriptor);
                  this.notifyAll();
               }
            }
         }

         synchronized(this) {
            if (success) {
               serviceMap.put(activeDescriptor, retVal);
            }

            this.creating.remove(activeDescriptor);
            this.notifyAll();
            return retVal;
         }
      }
   }

   public boolean containsKey(ActiveDescriptor descriptor) {
      SingleOperationManager localManager;
      synchronized(this) {
         localManager = this.manager;
      }

      if (localManager == null) {
         return false;
      } else {
         OperationHandleImpl operation = localManager.getCurrentOperationOnThisThread();
         if (operation == null) {
            return false;
         } else {
            synchronized(this) {
               HashMap serviceMap = (HashMap)this.operationMap.get(operation);
               return serviceMap == null ? false : serviceMap.containsKey(descriptor);
            }
         }
      }
   }

   public void destroyOne(ActiveDescriptor descriptor) {
      synchronized(this) {
         Iterator var3 = this.operationMap.values().iterator();

         while(var3.hasNext()) {
            HashMap serviceMap = (HashMap)var3.next();
            Object killMe = serviceMap.remove(descriptor);
            if (killMe != null) {
               descriptor.dispose(killMe);
            }
         }

      }
   }

   public void closeOperation(OperationHandleImpl operation) {
      long tid = Thread.currentThread().getId();
      HashMap serviceMap;
      LinkedList stack;
      synchronized(this) {
         stack = (LinkedList)this.closingOperations.get(tid);
         if (stack == null) {
            stack = new LinkedList();
            this.closingOperations.put(tid, stack);
         }

         stack.addFirst(operation);
         serviceMap = (HashMap)this.operationMap.get(operation);
      }

      boolean var22 = false;

      label184: {
         try {
            var22 = true;
            if (serviceMap == null) {
               var22 = false;
               break label184;
            }

            LinkedList destructionList = new LinkedList();
            Iterator var7 = serviceMap.entrySet().iterator();

            Map.Entry entry;
            while(var7.hasNext()) {
               entry = (Map.Entry)var7.next();
               destructionList.addFirst(entry);
            }

            var7 = destructionList.iterator();

            while(var7.hasNext()) {
               entry = (Map.Entry)var7.next();
               ActiveDescriptor desc = (ActiveDescriptor)entry.getKey();
               Object value = entry.getValue();

               try {
                  desc.dispose(value);
               } catch (Throwable var26) {
                  Logger.getLogger().debug(this.getClass().getName(), "closeOperation", var26);
               }
            }

            var22 = false;
         } finally {
            if (var22) {
               synchronized(this) {
                  this.operationMap.remove(operation);
                  stack.removeFirst();
                  if (stack.isEmpty()) {
                     this.closingOperations.remove(tid);
                  }

               }
            }
         }

         synchronized(this) {
            this.operationMap.remove(operation);
            stack.removeFirst();
            if (stack.isEmpty()) {
               this.closingOperations.remove(tid);
            }

            return;
         }
      }

      synchronized(this) {
         this.operationMap.remove(operation);
         stack.removeFirst();
         if (stack.isEmpty()) {
            this.closingOperations.remove(tid);
         }

      }
   }

   public void shutdown() {
      Set toShutDown;
      synchronized(this) {
         this.shuttingDown = true;
         toShutDown = this.operationMap.keySet();
      }

      boolean var12 = false;

      try {
         var12 = true;
         Iterator var2 = toShutDown.iterator();

         while(true) {
            if (!var2.hasNext()) {
               var12 = false;
               break;
            }

            OperationHandleImpl shutDown = (OperationHandleImpl)var2.next();
            shutDown.closeOperation();
         }
      } finally {
         if (var12) {
            synchronized(this) {
               this.operationMap.clear();
            }
         }
      }

      synchronized(this) {
         this.operationMap.clear();
      }
   }

   public boolean supportsNullCreation() {
      return false;
   }

   public boolean isActive() {
      return true;
   }

   public synchronized void setOperationManager(SingleOperationManager manager) {
      this.manager = manager;
   }

   public String toString() {
      return "OperationContext(" + this.getScope().getName() + "," + System.identityHashCode(this) + ")";
   }
}

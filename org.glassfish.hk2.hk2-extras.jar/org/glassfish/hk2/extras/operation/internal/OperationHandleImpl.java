package org.glassfish.hk2.extras.operation.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.extras.operation.OperationHandle;
import org.glassfish.hk2.extras.operation.OperationIdentifier;
import org.glassfish.hk2.extras.operation.OperationState;

public class OperationHandleImpl implements OperationHandle {
   private final SingleOperationManager parent;
   private final OperationIdentifier identifier;
   private final Object operationLock;
   private OperationState state;
   private final HashSet activeThreads = new HashSet();
   private Object userData;

   OperationHandleImpl(SingleOperationManager parent, OperationIdentifier identifier, Object operationLock, ServiceLocator locator) {
      this.parent = parent;
      this.identifier = identifier;
      this.operationLock = operationLock;
      this.state = OperationState.SUSPENDED;
   }

   public OperationIdentifier getIdentifier() {
      return this.identifier;
   }

   public OperationState getState() {
      synchronized(this.operationLock) {
         return this.state;
      }
   }

   void shutdownByFiat() {
      this.state = OperationState.CLOSED;
   }

   private void checkState() {
      synchronized(this.operationLock) {
         if (OperationState.CLOSED.equals(this.state)) {
            throw new IllegalStateException(this + " is closed");
         }
      }
   }

   public Set getActiveThreads() {
      synchronized(this.operationLock) {
         return Collections.unmodifiableSet(this.activeThreads);
      }
   }

   public void suspend(long threadId) {
      synchronized(this.operationLock) {
         if (!OperationState.CLOSED.equals(this.state)) {
            this.parent.disassociateThread(threadId, this);
            if (this.activeThreads.remove(threadId) && this.activeThreads.isEmpty()) {
               this.state = OperationState.SUSPENDED;
            }

         }
      }
   }

   public void suspend() {
      this.suspend(Thread.currentThread().getId());
   }

   public void resume(long threadId) throws IllegalStateException {
      synchronized(this.operationLock) {
         this.checkState();
         if (!this.activeThreads.contains(threadId)) {
            OperationHandleImpl existing = this.parent.getCurrentOperationOnThisThread(threadId);
            if (existing != null) {
               throw new IllegalStateException("The operation " + existing + " is active on " + threadId);
            } else {
               if (this.activeThreads.isEmpty()) {
                  this.state = OperationState.ACTIVE;
               }

               this.activeThreads.add(threadId);
               this.parent.associateWithThread(threadId, this);
            }
         }
      }
   }

   public void resume() throws IllegalStateException {
      this.resume(Thread.currentThread().getId());
   }

   public void close() {
      this.parent.disposeAllOperationServices(this);
      synchronized(this.operationLock) {
         Iterator var2 = this.activeThreads.iterator();

         while(var2.hasNext()) {
            long threadId = (Long)var2.next();
            this.parent.disassociateThread(threadId, this);
         }

         this.activeThreads.clear();
         this.state = OperationState.CLOSED;
         this.parent.closeOperation(this);
      }
   }

   public void closeOperation() {
      this.close();
   }

   public synchronized Object getOperationData() {
      return this.userData;
   }

   public synchronized void setOperationData(Object data) {
      this.userData = data;
   }

   public int hashCode() {
      return this.identifier.hashCode();
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else {
         return !(o instanceof OperationHandleImpl) ? false : this.identifier.equals(((OperationHandleImpl)o).identifier);
      }
   }

   public String toString() {
      return "OperationHandleImpl(" + this.identifier + "," + System.identityHashCode(this) + ")";
   }
}

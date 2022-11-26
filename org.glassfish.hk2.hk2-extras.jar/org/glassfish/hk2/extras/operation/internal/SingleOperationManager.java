package org.glassfish.hk2.extras.operation.internal;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.extras.operation.OperationContext;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

public class SingleOperationManager {
   private static final String ID_PREAMBLE = "OperationIdentifier(";
   private final Object operationLock = new Object();
   private final Annotation scope;
   private final HashMap openScopes = new HashMap();
   private final HashMap threadToHandleMap = new HashMap();
   private final ServiceLocator locator;
   private final OperationContext context;
   private long scopedIdentifier;
   private final ActiveDescriptor operationDescriptor;
   private boolean closed = false;

   SingleOperationManager(Annotation scope, ServiceLocator locator) {
      this.scope = scope;
      this.locator = locator;
      OperationContext found = null;
      Iterator var4 = locator.getAllServices(OperationContext.class, new Annotation[0]).iterator();

      while(var4.hasNext()) {
         OperationContext context = (OperationContext)var4.next();
         if (context.getScope().equals(scope.annotationType())) {
            found = context;
            break;
         }
      }

      if (found == null) {
         throw new IllegalStateException("Could not find the OperationContext for scope " + scope);
      } else {
         this.context = found;
         this.context.setOperationManager(this);
         OperationDescriptor opDesc = new OperationDescriptor(scope, this);
         this.operationDescriptor = ServiceLocatorUtilities.addOneDescriptor(locator, opDesc);
      }
   }

   private OperationIdentifierImpl allocateNewIdentifier() {
      return new OperationIdentifierImpl("OperationIdentifier(" + this.scopedIdentifier++ + "," + this.scope.annotationType().getName() + ")", this.scope);
   }

   public OperationHandleImpl createOperation() {
      synchronized(this.operationLock) {
         if (this.closed) {
            throw new IllegalStateException("This manager has been closed");
         } else {
            OperationIdentifierImpl id = this.allocateNewIdentifier();
            OperationHandleImpl created = new OperationHandleImpl(this, id, this.operationLock, this.locator);
            this.openScopes.put(id, created);
            return created;
         }
      }
   }

   void closeOperation(OperationHandleImpl closeMe) {
      this.openScopes.remove(closeMe.getIdentifier());
   }

   void disposeAllOperationServices(OperationHandleImpl closeMe) {
      this.context.closeOperation(closeMe);
   }

   void associateWithThread(long threadId, OperationHandleImpl handle) {
      this.threadToHandleMap.put(threadId, handle);
   }

   void disassociateThread(long threadId, OperationHandleImpl toRemove) {
      OperationHandleImpl activeOnThread = (OperationHandleImpl)this.threadToHandleMap.get(threadId);
      if (activeOnThread != null && activeOnThread.equals(toRemove)) {
         this.threadToHandleMap.remove(threadId);
      }
   }

   OperationHandleImpl getCurrentOperationOnThisThread(long threadId) {
      return (OperationHandleImpl)this.threadToHandleMap.get(threadId);
   }

   public OperationHandleImpl getCurrentOperationOnThisThread() {
      long threadId = Thread.currentThread().getId();
      synchronized(this.operationLock) {
         return this.closed ? null : this.getCurrentOperationOnThisThread(threadId);
      }
   }

   Set getAllOperations() {
      HashSet retVal = new HashSet();
      synchronized(this.operationLock) {
         if (this.closed) {
            return Collections.emptySet();
         } else {
            retVal.addAll(this.openScopes.values());
            return Collections.unmodifiableSet(retVal);
         }
      }
   }

   void shutdown() {
      synchronized(this.operationLock) {
         if (!this.closed) {
            this.closed = true;
            Iterator var2 = this.openScopes.values().iterator();

            while(var2.hasNext()) {
               OperationHandleImpl closeMe = (OperationHandleImpl)var2.next();
               closeMe.shutdownByFiat();
            }

            this.openScopes.clear();
            this.threadToHandleMap.clear();
            ServiceLocatorUtilities.removeOneDescriptor(this.locator, this.operationDescriptor);
         }
      }
   }

   public String toString() {
      return "SingleOperationManager(" + this.scope.annotationType().getName() + ",closed=" + this.closed + "," + System.identityHashCode(this) + ")";
   }
}

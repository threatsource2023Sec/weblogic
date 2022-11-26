package com.oracle.jms.jmspool.internal;

import com.oracle.jms.jmspool.PhantomReferenceCloseable;
import com.oracle.jms.jmspool.ReferenceManager;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.cmm.Scrubber;

@Service
public class ReferenceManagerImpl implements ReferenceManager, Scrubber {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugJMSWrappers");
   private final ReferenceQueue danglingObjects = new ReferenceQueue();
   private final HashSet outstandingReferences = new HashSet();

   public ReferenceQueue getReferenceQueue() {
      return this.danglingObjects;
   }

   public synchronized int poll() {
      int count = 0;

      while(true) {
         Reference reference;
         boolean didRemove;
         do {
            reference = this.danglingObjects.poll();
            if (reference == null) {
               return count;
            }

            ++count;
            didRemove = this.outstandingReferences.remove(reference);
         } while(!(reference instanceof PhantomReferenceCloseable));

         PhantomReferenceCloseable prc = (PhantomReferenceCloseable)reference;
         if (logger.isDebugEnabled()) {
            logger.debug("" + this + " is releasing phantom resource " + prc + ". didRemove=" + didRemove);
         }

         try {
            prc.closePhantomReference();
         } catch (Throwable var6) {
            if (logger.isDebugEnabled()) {
               logger.debug("Error cleaning up leaked objects", var6);
            }
         }
      }
   }

   public void scrubADubDub() {
      if (logger.isDebugEnabled()) {
         logger.debug("" + this + " is releasing phantom resources in the scrubber");
      }

      this.poll();
   }

   public synchronized void registerReference(Reference ref) {
      this.outstandingReferences.add(ref);
   }

   public synchronized boolean unregisterReference(Reference ref) {
      boolean retVal = this.outstandingReferences.remove(ref);
      return retVal;
   }

   public synchronized int getNumberOfRegisteredReferences() {
      return this.outstandingReferences.size();
   }

   public String toString() {
      return "ReferenceManagerImpl(" + this.getNumberOfRegisteredReferences() + "," + System.identityHashCode(this) + ")";
   }
}

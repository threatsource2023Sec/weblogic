package org.opensaml.xacml.ctx.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.opensaml.xacml.ctx.DecisionType.DECISION;
import org.opensaml.xacml.policy.EffectType;
import org.opensaml.xacml.policy.ObligationType;
import org.opensaml.xacml.policy.ObligationsType;

public class ObligationService {
   private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);
   private Set obligationHandlers = new TreeSet(new ObligationHandlerComparator());

   public Set getObligationHandlers() {
      return Collections.unmodifiableSet(this.obligationHandlers);
   }

   public void addObligationhandler(BaseObligationHandler handler) {
      if (handler != null) {
         Lock writeLock = this.rwLock.writeLock();
         writeLock.lock();

         try {
            this.obligationHandlers.add(handler);
         } finally {
            writeLock.unlock();
         }

      }
   }

   public void addObligationhandler(Collection handlers) {
      if (handlers != null && !handlers.isEmpty()) {
         Lock writeLock = this.rwLock.writeLock();
         writeLock.lock();

         try {
            this.obligationHandlers.addAll(handlers);
         } finally {
            writeLock.unlock();
         }

      }
   }

   public void removeObligationHandler(BaseObligationHandler handler) {
      if (handler != null) {
         Lock writeLock = this.rwLock.writeLock();
         writeLock.lock();

         try {
            this.obligationHandlers.remove(handler);
         } finally {
            writeLock.unlock();
         }

      }
   }

   public void processObligations(ObligationProcessingContext context) throws ObligationProcessingException {
      Lock readLock = this.rwLock.readLock();
      readLock.lock();

      try {
         Iterator handlerItr = this.obligationHandlers.iterator();
         Map effectiveObligations = this.preprocessObligations(context);

         while(handlerItr.hasNext()) {
            BaseObligationHandler handler = (BaseObligationHandler)handlerItr.next();
            if (effectiveObligations.containsKey(handler.getObligationId())) {
               handler.evaluateObligation(context, (ObligationType)effectiveObligations.get(handler.getObligationId()));
            }
         }
      } finally {
         readLock.unlock();
      }

   }

   protected Map preprocessObligations(ObligationProcessingContext context) {
      HashMap effectiveObligations = new HashMap();
      ObligationsType obligations = context.getAuthorizationDecisionResult().getObligations();
      if (obligations != null && obligations.getObligations() != null) {
         EffectType activeEffect;
         if (context.getAuthorizationDecisionResult().getDecision().getDecision() == DECISION.Permit) {
            activeEffect = EffectType.Permit;
         } else {
            activeEffect = EffectType.Deny;
         }

         Iterator var5 = obligations.getObligations().iterator();

         while(var5.hasNext()) {
            ObligationType obligation = (ObligationType)var5.next();
            if (obligation != null && obligation.getFulfillOn() == activeEffect) {
               effectiveObligations.put(obligation.getObligationId(), obligation);
            }
         }

         return effectiveObligations;
      } else {
         return effectiveObligations;
      }
   }

   private class ObligationHandlerComparator implements Comparator {
      private ObligationHandlerComparator() {
      }

      public int compare(BaseObligationHandler o1, BaseObligationHandler o2) {
         if (o1.getHandlerPrecedence() == o2.getHandlerPrecedence()) {
            return o1.getObligationId().compareTo(o2.getObligationId());
         } else {
            return o1.getHandlerPrecedence() < o2.getHandlerPrecedence() ? -1 : 1;
         }
      }

      // $FF: synthetic method
      ObligationHandlerComparator(Object x1) {
         this();
      }
   }
}

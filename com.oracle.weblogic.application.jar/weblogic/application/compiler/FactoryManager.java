package weblogic.application.compiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.application.utils.EarUtils;
import weblogic.diagnostics.debug.DebugLogger;

public final class FactoryManager {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainerTools");
   private List factories = new ArrayList();

   public void addFactory(Factory factory) {
      this.factories.add(factory);
   }

   public Object create(Object m) throws FactoryException {
      List claimingFactories = new ArrayList();
      Comparable topClaim = null;
      Iterator var4 = this.factories.iterator();

      Comparable finalClaim;
      while(var4.hasNext()) {
         Factory factory = (Factory)var4.next();
         finalClaim = factory.claim(m);
         if (finalClaim != null) {
            if (topClaim == null) {
               topClaim = finalClaim;
               claimingFactories.add(factory);
            } else {
               int comp = finalClaim.compareTo(topClaim);
               if (comp == 0) {
                  claimingFactories.add(factory);
               } else if (comp > 0) {
                  topClaim = finalClaim;
                  claimingFactories.clear();
                  claimingFactories.add(factory);
               }
            }
         }
      }

      if (debugLogger.isDebugEnabled()) {
         this.debug("Narrowed down list of factories (1st pass) for (" + m + "): " + claimingFactories);
      }

      List finalClaims = new ArrayList();
      if (claimingFactories.size() > 1) {
         List finalFactories = new ArrayList();
         finalClaim = null;
         Iterator var14 = claimingFactories.iterator();

         while(var14.hasNext()) {
            Factory factory = (Factory)var14.next();
            Comparable c = factory.claim(m, claimingFactories);
            if (c != null) {
               if (finalClaim == null) {
                  finalClaim = c;
                  finalFactories.add(factory);
                  finalClaims.add(c);
               } else {
                  int comp = c.compareTo(finalClaim);
                  if (comp == 0) {
                     finalFactories.add(factory);
                     finalClaims.add(c);
                  } else if (comp > 0) {
                     finalClaim = c;
                     finalFactories.clear();
                     finalFactories.add(factory);
                     finalClaims.add(c);
                  }
               }
            }
         }

         claimingFactories = finalFactories;
         if (debugLogger.isDebugEnabled()) {
            this.debug("Narrowed down list of factories (2nd pass) for (" + m + "): " + finalFactories);
         }
      }

      String message;
      if (claimingFactories.size() == 0) {
         message = "Unable to find a suitable factory among registered factories";
         if (debugLogger.isDebugEnabled()) {
            this.debug(message + ": " + this.factories);
         }

         throw new NoClaimsFactoryException(message);
      } else if (claimingFactories.size() > 1) {
         message = "Unable to resolve deadlock in factory claims";
         if (debugLogger.isDebugEnabled()) {
            this.debug(message + ": " + claimingFactories + ". Respective claims: " + finalClaims);
         }

         throw new UnresolvedClaimsFactoryException(message);
      } else {
         return ((Factory)claimingFactories.get(0)).create(m);
      }
   }

   private void debug(String message) {
      debugLogger.debug(EarUtils.addClassName(message));
   }
}

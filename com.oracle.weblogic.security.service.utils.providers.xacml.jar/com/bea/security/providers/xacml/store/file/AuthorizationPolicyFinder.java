package com.bea.security.providers.xacml.store.file;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.PolicySetMember;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.PolicyFinder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.security.spi.Resource;
import weblogic.utils.collections.ConcurrentHashMap;

class AuthorizationPolicyFinder extends AbstractCachedPolicyFinder implements CachedPolicyFinder {
   private Map activeAuthorizationEntries = new ConcurrentHashMap();
   private Map entitlementAuthorizationEntries = new ConcurrentHashMap();

   public AuthorizationPolicyFinder(PolicyFinder finder, LoggerSpi logger, int cacheSize) throws URISyntaxException {
      super(logger, cacheSize, finder);
   }

   public void loadEntry(Index.Entry e) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      AbstractPolicy ap = e.getData();
      String resource = null;
      if (e.getStatus() == 3) {
         if (this.debugEnabled) {
            this.log.debug("Adding entitlement ATZ policy.");
         }

         resource = this.converter.getResourceId(ap.getId().toString());
         this.addPolicySetMember(ap, this.entitlementAuthorizationEntries, resource);
      } else if (e.getStatus() == 0) {
         if (this.debugEnabled) {
            this.log.debug("Adding non-entitlement ATZ policy.");
         }

         resource = this.rmu.getTargetResource(ap.getTarget());
         this.addPolicySetMember(ap, this.activeAuthorizationEntries, resource);
      }

   }

   private void addPolicySetMember(AbstractPolicy psm, Map repository, String resource) throws PolicyStoreException, DocumentParseException, URISyntaxException {
      if (resource == null) {
         resource = "";
      }

      Collection col = (Collection)repository.get(resource);
      if (col == null) {
         col = new ArrayList();
         repository.put(resource, col);
      }

      ((Collection)col).add(psm);
   }

   protected Set loadPolicyForResource(Resource r) throws PolicyStoreException, DocumentParseException, URISyntaxException {
      Collection kmc = this.calculateKnownMatch(r);
      Set col = new HashSet();
      boolean lookingForEntitlement = true;
      Resource walker = r;

      while(true) {
         String resource = walker != null ? walker.toString() : "";
         Collection applicables = (Collection)this.activeAuthorizationEntries.get(resource);
         Iterator var8;
         PolicySetMember psm;
         if (applicables != null) {
            var8 = applicables.iterator();

            while(var8.hasNext()) {
               psm = (PolicySetMember)var8.next();
               col.add(this.generateRecord(psm, kmc));
            }
         }

         if (lookingForEntitlement) {
            applicables = (Collection)this.entitlementAuthorizationEntries.get(resource);
            if (applicables != null) {
               var8 = applicables.iterator();

               while(var8.hasNext()) {
                  psm = (PolicySetMember)var8.next();
                  col.add(this.generateRecord(psm, kmc));
               }

               lookingForEntitlement = false;
            }
         }

         if (walker == null) {
            return !col.isEmpty() ? col : null;
         }

         walker = walker.getParentResource();
      }
   }

   private Collection calculateKnownMatch(Resource r) {
      return this.rmu.generateKnownMatch(r);
   }

   public void reset() {
      super.reset();
      this.activeAuthorizationEntries.clear();
      this.entitlementAuthorizationEntries.clear();
   }
}

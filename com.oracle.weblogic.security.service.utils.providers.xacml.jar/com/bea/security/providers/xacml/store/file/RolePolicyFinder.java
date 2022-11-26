package com.bea.security.providers.xacml.store.file;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.PolicySetMember;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.cache.resource.ResourcePolicyIdUtil;
import com.bea.security.xacml.store.PolicyFinder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.security.spi.Resource;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.collections.ConcurrentHashSet;

class RolePolicyFinder extends AbstractCachedPolicyFinder implements CachedPolicyFinder {
   private final Map activeRoleAssignmentEntries = new ConcurrentHashMap();
   private final Map entitlementRoleAssignmentEntries = new ConcurrentHashMap();

   public RolePolicyFinder(PolicyFinder delegate, LoggerSpi logger, int cacheSize) throws URISyntaxException {
      super(logger, cacheSize, delegate);
   }

   public void loadEntry(Index.Entry e) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      AbstractPolicy ap = e.getData();
      String role = null;
      String resource = null;
      Object entries;
      if (e.getStatus() == 3) {
         ResourcePolicyIdUtil.RoleResource roleResource = this.converter.getRoleResourceId(ap.getId().toString());
         resource = roleResource.getResourceId();
         if (resource == null) {
            resource = "";
         }

         role = roleResource.getRole();
         if (role == null) {
            role = "";
         }

         entries = (Map)this.entitlementRoleAssignmentEntries.get(resource);
         if (entries == null) {
            entries = new HashMap();
            this.entitlementRoleAssignmentEntries.put(resource, entries);
         }

         ((Map)entries).put(role, e);
      } else if (e.getStatus() == 0) {
         resource = this.rmu.getTargetResource(ap.getTarget());
         if (resource == null) {
            resource = "";
         }

         role = this.romu.getTargetRole(ap.getTarget());
         if (role == null) {
            role = "";
         }

         Map roleMap = (Map)this.activeRoleAssignmentEntries.get(resource);
         if (roleMap == null) {
            roleMap = new HashMap();
            this.activeRoleAssignmentEntries.put(resource, roleMap);
         }

         entries = (Set)((Map)roleMap).get(role);
         if (entries == null) {
            entries = new ConcurrentHashSet();
            ((Map)roleMap).put(role, entries);
         }

         ((Set)entries).add(e);
      }

   }

   protected Map loadPolicyForResource(Resource r) throws PolicyStoreException, DocumentParseException, URISyntaxException {
      Map map = new HashMap();
      Map emap = new HashMap();
      Resource walker = r;

      while(true) {
         String key = walker != null ? walker.toString() : "";
         Map aRoleEntries = (Map)this.activeRoleAssignmentEntries.get(key);
         String role;
         if (aRoleEntries != null) {
            Iterator var7 = aRoleEntries.entrySet().iterator();

            label74:
            while(true) {
               Map.Entry e;
               Set s;
               do {
                  if (!var7.hasNext()) {
                     break label74;
                  }

                  e = (Map.Entry)var7.next();
                  s = (Set)e.getValue();
               } while(s == null);

               role = (String)e.getKey();
               Set col = (Set)map.get(role);
               if (col == null) {
                  col = new HashSet();
                  map.put(role, col);
               }

               Collection kmc = this.calculateKnownMatch(r, role);
               Iterator var13 = s.iterator();

               while(var13.hasNext()) {
                  Index.Entry z = (Index.Entry)var13.next();
                  ((Set)col).add(this.generateRecord(z, kmc));
               }
            }
         }

         Map roleEntries = (Map)this.entitlementRoleAssignmentEntries.get(key);
         if (roleEntries != null) {
            Iterator var19 = roleEntries.entrySet().iterator();

            while(var19.hasNext()) {
               Map.Entry e = (Map.Entry)var19.next();
               role = (String)e.getKey();
               Collection kmc = this.calculateKnownMatch(r, role);
               if (!emap.containsKey(role)) {
                  emap.put(role, Collections.singleton(this.generateRecord((PolicySetMember)e.getValue(), kmc)));
               }
            }
         }

         if (walker == null) {
            if (map.isEmpty()) {
               return !emap.isEmpty() ? emap : null;
            }

            Iterator var15 = emap.entrySet().iterator();

            while(var15.hasNext()) {
               Map.Entry ee = (Map.Entry)var15.next();
               String role = (String)ee.getKey();
               Set rm = (Set)map.get(role);
               if (rm == null) {
                  map.put(role, ee.getValue());
               } else {
                  rm.addAll((Collection)ee.getValue());
               }
            }

            return !map.isEmpty() ? map : null;
         }

         walker = walker.getParentResource();
      }
   }

   protected Collection calculateKnownMatch(Resource r, String role) {
      Collection col = this.calculateKnownMatch(r);
      if (col == null) {
         col = new ArrayList();
      }

      ((Collection)col).add(this.romu.getEnableRoleMatch());
      if (role != null) {
         ((Collection)col).add(this.romu.generateRoleAssignmentMatch(role));
      }

      return (Collection)col;
   }

   protected Collection calculateKnownMatch(Resource r) {
      return this.rmu.generateKnownMatch(r);
   }

   public void reset() {
      this.activeRoleAssignmentEntries.clear();
      this.entitlementRoleAssignmentEntries.clear();
      super.reset();
   }
}

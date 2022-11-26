package com.bea.security.providers.xacml.store;

import com.bea.common.security.store.data.WLSRoleCollectionInfo;
import com.bea.common.security.store.data.WLSRoleCollectionInfoId;
import com.bea.common.security.store.data.XACMLEntry;
import com.bea.common.security.store.data.XACMLEntryId;
import com.bea.common.security.store.data.XACMLRoleAssignmentPolicy;
import com.bea.common.security.store.data.XACMLRoleAssignmentPolicyId;
import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.XACMLException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySetMember;
import com.bea.common.store.bootstrap.EntryConverter;
import com.bea.common.store.bootstrap.internal.DefaultBootStrapPersistenceImpl;
import com.bea.common.store.service.RemoteCommitEvent;
import com.bea.common.store.service.RemoteCommitListener;
import com.bea.security.providers.xacml.BasicEvaluationCtx;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IOException;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.cache.resource.MultipleResourceTargetException;
import com.bea.security.xacml.cache.resource.ResourceMatchUtil;
import com.bea.security.xacml.cache.resource.ResourcePolicyIdUtil;
import com.bea.security.xacml.cache.role.MultipleRoleTargetException;
import com.bea.security.xacml.cache.role.RoleMatchUtil;
import com.bea.security.xacml.target.KnownMatch;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import weblogic.security.spi.Resource;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.collections.ConcurrentHashSet;
import weblogic.utils.collections.SecondChanceCacheMap;

public class RoleAssignmentPolicyStore extends BasePolicyStore implements ApplicableRoleAssignmentPolicyFinder {
   private static final String HEADER = "RoleAssignmentPolicyStore: ";
   private static final String WLS_ROLE_INFO = "WLSRoleInfo";
   private static final String PCI_KEY = "RoleCollectionInfo#";
   private static final int PCI_KEY_LEN = "RoleCollectionInfo#".length();
   private final SecondChanceCacheMap roleAssignmentCache;
   private final Map activeRoleAssignmentEntries;
   private final Map entitlementRoleAssignmentEntries;
   private final ResourceMatchUtil rmu;
   private final RoleMatchUtil romu;

   public RoleAssignmentPolicyStore(PolicyStoreConfigInfo info, AttributeRegistry registry) throws URISyntaxException {
      super(info, registry);
      this.roleAssignmentCache = new SecondChanceCacheMap(cacheCapacity);
      this.activeRoleAssignmentEntries = new ConcurrentHashMap();
      this.entitlementRoleAssignmentEntries = new ConcurrentHashMap();
      this.rmu = new ResourceMatchUtil();
      this.romu = new RoleMatchUtil();
   }

   public void init() throws DocumentParseException, PolicyStoreException, URISyntaxException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("RoleAssignmentPolicyStore: Loading Bootstrap template");
      }

      EntryConverter entryConverter = new XACMLEntryConverter(this.domainName, this.realmName);
      this.bootstrapService.loadLDIFXACMLRoleMapperTemplate(this.log, this.storeService, entryConverter, this.domainName, this.realmName);
      this.bootstrapService.updateXACMLRoleMapperPolicies(this.log, new GlobalPolicyUpdateImpl(this, this.log), new DefaultBootStrapPersistenceImpl(this.storeService), this.domainName, this.realmName);
      super.init();
   }

   protected Class getPolicyObjectClass() throws PolicyStoreException {
      try {
         return Class.forName("com.bea.common.security.store.data.XACMLRoleAssignmentPolicy");
      } catch (ClassNotFoundException var2) {
         throw new PolicyStoreException(var2);
      }
   }

   protected XACMLEntryId newPolicyObjectId(String typeName, String cn, String xacmlVersion) {
      return new XACMLRoleAssignmentPolicyId(this.domainName, this.realmName, typeName, cn, xacmlVersion);
   }

   protected XACMLEntry newPolicyObject(String typeName, String cn, String xacmlVersion) {
      return new XACMLRoleAssignmentPolicy(this.domainName, this.realmName, typeName, cn, xacmlVersion, (byte[])null, (String)null);
   }

   protected XACMLEntryId getPolicyObjectId(XACMLEntry pc) {
      XACMLRoleAssignmentPolicy policy = (XACMLRoleAssignmentPolicy)pc;
      return this.newPolicyObjectId(policy.getTypeName(), policy.getCn(), policy.getXacmlVersion());
   }

   protected String getMetaDataElementName() {
      return "WLSRoleInfo";
   }

   protected String getXacmlRoleScope(XACMLEntry pc) {
      Collection scope = ((XACMLRoleAssignmentPolicy)pc).getXacmlRole();
      return scope != null && scope.size() > 0 ? (String)scope.iterator().next() : null;
   }

   protected void addXacmlScope(XACMLEntry pc, int status, AbstractPolicy ap) {
      XACMLRoleAssignmentPolicy policy = (XACMLRoleAssignmentPolicy)pc;
      boolean debug = this.log.isDebugEnabled();
      String uid = ap.getId().toString();
      String res = "";
      if (status == 3) {
         ResourcePolicyIdUtil.RoleResource rr = this.converter.getRoleResourceId(uid);
         if (rr != null) {
            res = rr.getResourceId();
         }
      } else {
         try {
            res = this.rmu.getTargetResource(ap.getTarget());
         } catch (MultipleResourceTargetException var11) {
            if (debug) {
               this.log.debug("Unable to generate xacmlResourceScope for " + (ap instanceof Policy ? "Policy" : "PolicySet") + ": " + ap.getId() + " Version: " + ap.getVersion());
            }
         }
      }

      if (res != null && !"".equals(res)) {
         if (debug) {
            this.log.debug("Using xacmlResourceScope: " + res);
         }

         ArrayList xacmlResourceScope = new ArrayList(1);
         xacmlResourceScope.add(res);
         policy.setXacmlResourceScope(xacmlResourceScope);
      }

      String role = "";
      if (status == 3) {
         ResourcePolicyIdUtil.RoleResource rr = this.converter.getRoleResourceId(uid);
         if (rr != null) {
            role = rr.getRole();
         }
      } else {
         try {
            role = this.romu.getTargetRole(ap.getTarget());
         } catch (MultipleRoleTargetException var10) {
            if (debug) {
               this.log.debug("Unable to generate xacmlRole for " + (ap instanceof Policy ? "Policy" : "PolicySet") + ": " + ap.getId() + " Version: " + ap.getVersion());
            }
         }
      }

      if (role != null && !"".equals(role)) {
         if (debug) {
            this.log.debug("Using xacmlRole: " + role);
         }

         ArrayList xacmlRoleScope = new ArrayList(1);
         xacmlRoleScope.add(role);
         policy.setXacmlRole(xacmlRoleScope);
      }

   }

   protected void modifyXacmlScope(XACMLEntry pc, int status, AbstractPolicy ap) {
      XACMLRoleAssignmentPolicy policy = (XACMLRoleAssignmentPolicy)pc;
      boolean debug = this.log.isDebugEnabled();
      if (status == 0) {
         String res = "";

         try {
            res = this.rmu.getTargetResource(ap.getTarget());
         } catch (MultipleResourceTargetException var10) {
            if (debug) {
               this.log.debug("Unable to generate xacmlResourceScope for " + (ap instanceof Policy ? "Policy" : "PolicySet") + ": " + ap.getId() + " Version: " + ap.getVersion());
            }
         }

         if (res != null && !"".equals(res)) {
            if (debug) {
               this.log.debug("Using xacmlResourceScope: " + res);
            }

            ArrayList xacmlResourceScope = new ArrayList(1);
            xacmlResourceScope.add(res);
            policy.setXacmlResourceScope(xacmlResourceScope);
         }

         String role = "";

         try {
            role = this.romu.getTargetRole(ap.getTarget());
         } catch (MultipleRoleTargetException var9) {
            if (debug) {
               this.log.debug("Unable to generate xacmlRole for " + (ap instanceof Policy ? "Policy" : "PolicySet") + ": " + ap.getId() + " Version: " + ap.getVersion());
            }
         }

         if (role != null && !"".equals(role)) {
            if (debug) {
               this.log.debug("Using xacmlRole: " + role);
            }

            ArrayList xacmlRoleScope = new ArrayList(1);
            xacmlRoleScope.add(role);
            policy.setXacmlRole(xacmlRoleScope);
         }
      }

   }

   protected void load() throws DocumentParseException, PolicyStoreException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("RoleAssignmentPolicyStore: Entering RoleAssignmentPolicyStore.load()");
      }

      Class policyObjectClass = this.getPolicyObjectClass();
      PersistenceManager manager = this.storeService.getPersistenceManager();
      Query query = null;

      try {
         query = manager.newQuery(policyObjectClass, "this.domainName == domainName && this.realmName == realmName && this.xacmlStatus == xacmlStatus");
         query.declareParameters("String domainName, String realmName, String xacmlStatus");
         Collection result = (Collection)query.execute(this.domainName, this.realmName, (new Integer(0)).toString());
         if (debug) {
            this.log.debug("RoleAssignmentPolicyStore: Found " + result.size() + " general role assignment policies or policy sets");
         }

         Iterator var6 = result.iterator();

         Object pc;
         XACMLRoleAssignmentPolicy policy;
         String resource;
         Collection scope;
         String role;
         Object existing;
         while(var6.hasNext()) {
            pc = var6.next();
            policy = (XACMLRoleAssignmentPolicy)pc;
            resource = "";
            scope = policy.getXacmlResourceScope();
            if (scope != null && scope.size() > 0) {
               resource = (String)scope.iterator().next();
            }

            role = "";
            scope = policy.getXacmlRole();
            if (scope != null && scope.size() > 0) {
               role = (String)scope.iterator().next();
            }

            existing = (Map)this.activeRoleAssignmentEntries.get(resource);
            if (existing == null) {
               existing = new ConcurrentHashMap();
               this.activeRoleAssignmentEntries.put(resource, existing);
            }

            Set se = (Set)((Map)existing).get(role);
            if (se == null) {
               se = new ConcurrentHashSet();
               ((Map)existing).put(role, se);
            }

            BasePolicyStore.Entry e = new RoleResourceEntry(policy, resource, role);
            ((Set)se).add(e);
            this.allEntries.put(e.getID(), e);
         }

         result = (Collection)query.execute(this.domainName, this.realmName, (new Integer(1)).toString());
         if (debug) {
            this.log.debug("RoleAssignmentPolicyStore: Found " + result.size() + " general 'if referenced' role assignment policies or policy sets");
         }

         var6 = result.iterator();

         while(var6.hasNext()) {
            pc = var6.next();
            policy = (XACMLRoleAssignmentPolicy)pc;
            resource = null;
            scope = policy.getXacmlRole();
            if (scope != null && scope.size() > 0) {
               resource = (String)scope.iterator().next();
            }

            BasePolicyStore.Entry e = new RoleResourceEntry(policy, (String)null, resource);
            this.allEntries.put(e.getID(), e);
         }

         result = (Collection)query.execute(this.domainName, this.realmName, (new Integer(3)).toString());
         if (debug) {
            this.log.debug("RoleAssignmentPolicyStore: Found " + result.size() + " entitlement role assignment policies");
         }

         var6 = result.iterator();

         while(var6.hasNext()) {
            pc = var6.next();
            policy = (XACMLRoleAssignmentPolicy)pc;
            resource = "";
            scope = policy.getXacmlResourceScope();
            if (scope != null && scope.size() > 0) {
               resource = (String)scope.iterator().next();
            }

            scope = policy.getXacmlRole();
            if (scope != null && scope.size() > 0) {
               role = (String)scope.iterator().next();
               existing = (Map)this.entitlementRoleAssignmentEntries.get(resource);
               if (existing == null) {
                  existing = new ConcurrentHashMap();
                  this.entitlementRoleAssignmentEntries.put(resource, existing);
               }

               BasePolicyStore.Entry e = new RoleResourceEntry(policy, resource, role);
               ((Map)existing).put(role, e);
               this.allEntries.put(e.getID(), e);
            }
         }
      } finally {
         if (query != null) {
            query.closeAll();
         }

         manager.close();
      }

      if (debug) {
         this.log.debug("RoleAssignmentPolicyStore: Exiting RoleAssignmentPolicyStore.load()");
      }

   }

   public Map findRoleAssignmentPolicy(EvaluationCtx context) throws DocumentParseException, IOException, URISyntaxException {
      if (context instanceof BasicEvaluationCtx) {
         Resource r = ((BasicEvaluationCtx)context).getResource();
         RoleAssignmentCacheEntry entry = this.retrieveRoleAssignmentEntry(r);
         if (entry != null) {
            return entry.getRoleAssignmentPolicy();
         }
      }

      return null;
   }

   private RoleAssignmentCacheEntry retrieveRoleAssignmentEntry(Resource r) throws DocumentParseException, IOException, URISyntaxException {
      RoleAssignmentCacheEntry entry = (RoleAssignmentCacheEntry)this.roleAssignmentCache.get(r);
      if (entry == null) {
         entry = new RoleAssignmentCacheEntry(this.loadRoleAssignmentPolicyForResource(r));
         this.roleAssignmentCache.put(r, entry);
      }

      return entry;
   }

   private Map loadRoleAssignmentPolicyForResource(Resource r) throws DocumentParseException, IOException, URISyntaxException {
      Map map = new HashMap();
      Collection rMatch = this.calculateKnownMatch(r);
      Map emap = new EMap(r, rMatch);
      Resource walker = r;

      while(true) {
         String key = walker != null ? walker.toString() : "";
         Map aRoleEntries = (Map)this.activeRoleAssignmentEntries.get(key);
         if (aRoleEntries != null) {
            Iterator var8 = aRoleEntries.entrySet().iterator();

            label67:
            while(true) {
               Map.Entry e;
               Set s;
               do {
                  if (!var8.hasNext()) {
                     break label67;
                  }

                  e = (Map.Entry)var8.next();
                  s = (Set)e.getValue();
               } while(s == null);

               String role = (String)e.getKey();
               Collection kmc = this.calculateKnownMatch(rMatch, role);
               Set col = (Set)map.get(role);
               if (col == null) {
                  col = new HashSet();
                  map.put(role, col);
               }

               Iterator var14 = s.iterator();

               while(var14.hasNext()) {
                  BasePolicyStore.Entry z = (BasePolicyStore.Entry)var14.next();
                  ((Set)col).add(this.generateRecord(z, kmc));
               }
            }
         }

         if (walker == null) {
            if (map.isEmpty()) {
               return !emap.isEmpty() ? emap : null;
            }

            Iterator var16 = emap.entrySet().iterator();

            while(var16.hasNext()) {
               Map.Entry ee = (Map.Entry)var16.next();
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

   protected Collection calculateKnownMatch(final Resource r) {
      return new Collection() {
         private Collection inner = null;

         private void initialize() {
            if (this.inner == null) {
               this.inner = RoleAssignmentPolicyStore.super.calculateKnownMatch(r);
            }

         }

         public boolean add(KnownMatch arg0) {
            throw new UnsupportedOperationException();
         }

         public boolean addAll(Collection arg0) {
            throw new UnsupportedOperationException();
         }

         public void clear() {
            throw new UnsupportedOperationException();
         }

         public boolean contains(Object arg0) {
            this.initialize();
            return this.inner.contains(arg0);
         }

         public boolean containsAll(Collection arg0) {
            this.initialize();
            return this.inner.containsAll(arg0);
         }

         public boolean isEmpty() {
            this.initialize();
            return this.inner.isEmpty();
         }

         public Iterator iterator() {
            this.initialize();
            return this.inner.iterator();
         }

         public boolean remove(Object arg0) {
            throw new UnsupportedOperationException();
         }

         public boolean removeAll(Collection arg0) {
            throw new UnsupportedOperationException();
         }

         public boolean retainAll(Collection arg0) {
            throw new UnsupportedOperationException();
         }

         public int size() {
            this.initialize();
            return this.inner.size();
         }

         public Object[] toArray() {
            this.initialize();
            return this.inner.toArray();
         }

         public Object[] toArray(Object[] arg0) {
            this.initialize();
            return this.inner.toArray(arg0);
         }
      };
   }

   protected Collection calculateKnownMatch(final Collection r, final String role) {
      return new Collection() {
         private KnownMatch roleMatch = null;

         private KnownMatch getRoleMatch() {
            if (this.roleMatch == null && role != null) {
               this.roleMatch = RoleAssignmentPolicyStore.this.omu.generateRoleAssignmentMatch(role);
            }

            return this.roleMatch;
         }

         public boolean add(KnownMatch arg0) {
            throw new UnsupportedOperationException();
         }

         public boolean addAll(Collection arg0) {
            throw new UnsupportedOperationException();
         }

         public void clear() {
            throw new UnsupportedOperationException();
         }

         public boolean contains(Object arg0) {
            KnownMatch roleMatch = this.getRoleMatch();
            return RoleAssignmentPolicyStore.this.omu.getEnableRoleMatch().equals(arg0) || roleMatch != null && roleMatch.equals(arg0) || r != null && r.contains(arg0);
         }

         public boolean containsAll(Collection arg0) {
            Iterator it = arg0.iterator();

            do {
               if (!it.hasNext()) {
                  return true;
               }
            } while(this.contains(it.next()));

            return false;
         }

         public boolean isEmpty() {
            return false;
         }

         public Iterator iterator() {
            return new Iterator() {
               private boolean initial = true;
               private boolean checkedRole = false;
               private Iterator rIt = null;

               private Iterator getRIterator() {
                  if (this.rIt == null && r != null) {
                     this.rIt = r.iterator();
                  }

                  return this.rIt;
               }

               public boolean hasNext() {
                  if (this.initial) {
                     return true;
                  } else if (!this.checkedRole && getRoleMatch() != null) {
                     return true;
                  } else {
                     Iterator it = this.getRIterator();
                     return it != null ? it.hasNext() : false;
                  }
               }

               public KnownMatch next() {
                  if (this.initial) {
                     this.initial = false;
                     return RoleAssignmentPolicyStore.this.omu.getEnableRoleMatch();
                  } else {
                     if (!this.checkedRole) {
                        this.checkedRole = true;
                        KnownMatch rkm = getRoleMatch();
                        if (rkm != null) {
                           return rkm;
                        }
                     }

                     Iterator it = this.getRIterator();
                     if (it != null) {
                        return (KnownMatch)it.next();
                     } else {
                        throw new NoSuchElementException();
                     }
                  }
               }

               public void remove() {
                  throw new UnsupportedOperationException();
               }
            };
         }

         public boolean remove(Object arg0) {
            throw new UnsupportedOperationException();
         }

         public boolean removeAll(Collection arg0) {
            throw new UnsupportedOperationException();
         }

         public boolean retainAll(Collection arg0) {
            throw new UnsupportedOperationException();
         }

         public int size() {
            int size = 1;
            if (role != null) {
               ++size;
            }

            if (r != null) {
               size += r.size();
            }

            return size;
         }

         public Object[] toArray() {
            KnownMatch[] a = new KnownMatch[this.size()];
            a[0] = RoleAssignmentPolicyStore.this.omu.getEnableRoleMatch();
            int i = 1;
            KnownMatch roleMatch = this.getRoleMatch();
            if (roleMatch != null) {
               a[i++] = roleMatch;
            }

            if (r != null) {
               Object[] ra = r.toArray();
               System.arraycopy(ra, 0, a, i + 1, ra.length);
            }

            return a;
         }

         public Object[] toArray(Object[] a) {
            int size = this.size();
            if (a.length < size) {
               Class newType = a.getClass();
               a = newType == Object[].class ? (Object[])(new Object[size]) : (Object[])((Object[])Array.newInstance(newType.getComponentType(), size));
            }

            int i = 1;
            KnownMatch roleMatch = this.getRoleMatch();
            if (roleMatch != null) {
               a[i++] = roleMatch;
            }

            if (r != null) {
               Object[] ra = r.toArray();
               System.arraycopy(ra, 0, a, i + 1, ra.length);
            }

            if (a.length > size) {
               a[size] = null;
            }

            return a;
         }
      };
   }

   protected void entryAdded(XACMLEntryId id, AbstractPolicy data, int status, String resourceScope, String roleScope) {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("Entering LDAPRoleAssignmentPolicyStore::entryAdded(" + id + ", " + status + ")");
      }

      RoleResourceEntry e = new RoleResourceEntry(id, data, status, resourceScope, roleScope);
      synchronized(this.allEntries) {
         switch (status) {
            case 0:
               Map me = (Map)this.activeRoleAssignmentEntries.get(resourceScope);
               if (me == null) {
                  me = new ConcurrentHashMap();
                  this.activeRoleAssignmentEntries.put(resourceScope, me);
               }

               Set s = (Set)((Map)me).get(roleScope);
               if (s == null) {
                  s = new ConcurrentHashSet();
                  ((Map)me).put(roleScope, s);
               }

               ((Set)s).add(e);
               this.allEntries.put(id, e);
               break;
            case 1:
               this.allEntries.put(id, e);
            case 2:
            default:
               break;
            case 3:
               Map existing = (Map)this.entitlementRoleAssignmentEntries.get(resourceScope);
               if (existing == null) {
                  existing = new ConcurrentHashMap();
                  this.entitlementRoleAssignmentEntries.put(resourceScope, existing);
               }

               ((Map)existing).put(roleScope, e);
               this.allEntries.put(id, e);
         }
      }

      if (debug) {
         this.log.debug("Refreshing cache");
      }

      this.roleAssignmentCache.clear();
      if (debug) {
         this.log.debug("Exiting LDAPRoleAssignmentPolicyStore::entryAdded()");
      }

   }

   protected void entryRemoved(BasePolicyStore.Entry e) {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("Entering LDAPRoleAssignmentPolicyStore::entryRemoved(" + e.getID() + ")");
      }

      synchronized(this.allEntries) {
         this.allEntries.remove(e.getID());
         switch (e.getStatus()) {
            case 0:
               String role = "";
               String resource = "";
               if (e instanceof RoleResourceEntry) {
                  RoleResourceEntry re = (RoleResourceEntry)e;
                  resource = re.getResource();
                  role = re.getRole();
               }

               Map me = (Map)this.activeRoleAssignmentEntries.get(resource);
               if (me != null) {
                  Set s = (Set)me.get(role);
                  if (s != null && s.remove(e) && s.isEmpty()) {
                     me.remove(role);
                     if (me.isEmpty()) {
                        this.activeRoleAssignmentEntries.remove(resource);
                     }
                  }
               }
               break;
            case 3:
               String role2 = "";
               String resource2 = "";
               if (e instanceof RoleResourceEntry) {
                  RoleResourceEntry re = (RoleResourceEntry)e;
                  resource2 = re.getResource();
                  role2 = re.getRole();
               }

               Map m = (Map)this.entitlementRoleAssignmentEntries.get(resource2);
               if (m != null) {
                  m.remove(role2);
                  if (m.isEmpty()) {
                     this.entitlementRoleAssignmentEntries.remove(resource2);
                  }
               }
         }
      }

      if (debug) {
         this.log.debug("Refreshing cache");
      }

      this.roleAssignmentCache.clear();
      if (debug) {
         this.log.debug("Exiting LDAPRoleAssignmentPolicyStore::entryRemoved()");
      }

   }

   protected RemoteCommitListener getChangeListener() {
      return new RemoteCommitListener() {
         public void afterCommit(RemoteCommitEvent event) {
            try {
               Collection ids = event.getAddedObjectIds();
               if (ids != null && ids.size() > 0) {
                  RoleAssignmentPolicyStore.this.changeAdd(ids);
               }

               ids = event.getDeletedObjectIds();
               if (ids != null && ids.size() > 0) {
                  RoleAssignmentPolicyStore.this.changeDelete(ids);
               }

               ids = event.getUpdatedObjectIds();
               if (ids != null && ids.size() > 0) {
                  RoleAssignmentPolicyStore.this.changeUpdate(ids);
               }
            } catch (Exception var3) {
               if (RoleAssignmentPolicyStore.this.log.isDebugEnabled()) {
                  RoleAssignmentPolicyStore.this.log.debug("Failed to honor change notification", var3);
               }
            }

            RoleAssignmentPolicyStore.this.roleAssignmentCache.clear();
         }
      };
   }

   private void changeAdd(Collection objectIds) throws URISyntaxException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("RoleAssignmentPolicyStore: Entering changeAdd(" + objectIds.size() + ")");
      }

      PersistenceManager manager = this.storeService.getPersistenceManager();

      try {
         Iterator var3 = objectIds.iterator();

         while(var3.hasNext()) {
            Object id = var3.next();
            if (id instanceof XACMLRoleAssignmentPolicyId) {
               if (this.isPreEssexGlobalPolicy(id)) {
                  if (this.log.isDebugEnabled()) {
                     this.log.debug("RoleAssignmentPolicyStore: Skip pre-essex style global policy add remote commit message");
                  }
               } else {
                  XACMLRoleAssignmentPolicy pc = (XACMLRoleAssignmentPolicy)((XACMLRoleAssignmentPolicyId)id).getObject(manager);
                  int status = Integer.parseInt(pc.getXacmlStatus());
                  String resource = "";
                  Collection scope = pc.getXacmlResourceScope();
                  if (scope != null && scope.size() > 0) {
                     resource = (String)scope.iterator().next();
                  }

                  String role = "";
                  scope = pc.getXacmlRole();
                  if (scope != null && scope.size() > 0) {
                     role = (String)scope.iterator().next();
                  }

                  BasePolicyStore.Entry e = new RoleResourceEntry(pc, resource, role);
                  synchronized(this.allEntries) {
                     switch (status) {
                        case 0:
                           Map me = (Map)this.activeRoleAssignmentEntries.get(resource);
                           if (me == null) {
                              me = new ConcurrentHashMap();
                              this.activeRoleAssignmentEntries.put(resource, me);
                           }

                           Set s = (Set)((Map)me).get(role);
                           if (s == null) {
                              s = new ConcurrentHashSet();
                              ((Map)me).put(role, s);
                           }

                           ((Set)s).add(e);
                           this.allEntries.put(e.getID(), e);
                           break;
                        case 1:
                           this.allEntries.put(e.getID(), e);
                        case 2:
                        default:
                           break;
                        case 3:
                           Map existing = (Map)this.entitlementRoleAssignmentEntries.get(resource);
                           if (existing == null) {
                              existing = new ConcurrentHashMap();
                              this.entitlementRoleAssignmentEntries.put(resource, existing);
                           }

                           ((Map)existing).put(role, e);
                           this.allEntries.put(e.getID(), e);
                     }
                  }
               }
            }
         }
      } finally {
         manager.close();
      }

   }

   private void changeDelete(Collection objectIds) {
      if (this.log.isDebugEnabled()) {
         this.log.debug("RoleAssignmentPolicyStore: Entering changeDelete(" + objectIds.size() + ")");
      }

      Iterator var2 = objectIds.iterator();

      while(var2.hasNext()) {
         Object id = var2.next();
         if (id instanceof XACMLRoleAssignmentPolicyId) {
            if (this.isPreEssexGlobalPolicy(id)) {
               if (this.log.isDebugEnabled()) {
                  this.log.debug("RoleAssignmentPolicyStore: Skip pre-essex style global policy delete remote commit message");
               }
            } else {
               synchronized(this.allEntries) {
                  BasePolicyStore.Entry e = (BasePolicyStore.Entry)this.allEntries.remove(id);
                  if (e != null) {
                     String role = "";
                     String resource = "";
                     if (e instanceof RoleResourceEntry) {
                        RoleResourceEntry re = (RoleResourceEntry)e;
                        resource = re.getResource();
                        role = re.getRole();
                     }

                     switch (e.getStatus()) {
                        case 0:
                           Map me = (Map)this.activeRoleAssignmentEntries.get(resource);
                           if (me != null) {
                              Set s = (Set)me.get(role);
                              if (s != null) {
                                 s.remove(e);
                                 if (s.isEmpty()) {
                                    me.remove(role);
                                    if (me.isEmpty()) {
                                       this.activeRoleAssignmentEntries.remove(resource);
                                    }
                                 }
                              }
                           }
                           break;
                        case 3:
                           Map m = (Map)this.entitlementRoleAssignmentEntries.get(resource);
                           if (m != null) {
                              m.remove(role);
                              if (m.isEmpty()) {
                                 this.entitlementRoleAssignmentEntries.remove(resource);
                              }
                           }
                     }
                  }
               }
            }
         }
      }

   }

   protected boolean isPreEssexGlobalPolicy(Object id) {
      String longId = ((XACMLRoleAssignmentPolicyId)id).toString();
      String shortId = longId.substring(0, longId.indexOf(44));
      if (this.log.isDebugEnabled()) {
         this.log.debug("RoleAssignmentPolicyStore: isPreEssexGlobalPolicy: shortId=" + shortId + ", longId=" + longId);
      }

      return shortId.startsWith("cn=urn:bea:xacml:2.0:entitlement:") && shortId.endsWith(":top");
   }

   private void changeUpdate(Collection objectIds) throws URISyntaxException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("Entering changeUpdate(" + objectIds.size() + ")");
      }

      this.changeDelete(objectIds);
      this.changeAdd(objectIds);
   }

   public void setMetaDataEntry(String key, String value) throws PolicyStoreException {
      String wlsCollectionName = this.getMetaDataKeyName(key);
      if (this.log.isDebugEnabled()) {
         this.log.debug("RoleAssignmentPolicyStore: setMetaDataEntry(" + wlsCollectionName + ")");
      }

      PersistenceManager manager = this.storeService.getPersistenceManager();

      try {
         manager.currentTransaction().begin();

         try {
            WLSRoleCollectionInfoId id = new WLSRoleCollectionInfoId(this.domainName, this.realmName, wlsCollectionName);
            WLSRoleCollectionInfo pc = (WLSRoleCollectionInfo)this.getObjectById(manager, id);
            if (pc == null) {
               pc = new WLSRoleCollectionInfo(this.domainName, this.realmName, wlsCollectionName);
               manager.makePersistent(pc);
            }

            pc.setWlsXmlFragment(value.getBytes("UTF8"));
         } catch (Throwable var11) {
            manager.currentTransaction().rollback();
            throw var11;
         }

         manager.currentTransaction().commit();
      } catch (Throwable var12) {
         throw new PolicyStoreException(var12);
      } finally {
         manager.close();
      }

   }

   public String getMetaDataEntry(String key) throws PolicyStoreException {
      String wlsCollectionName = this.getMetaDataKeyName(key);
      if (this.log.isDebugEnabled()) {
         this.log.debug("RoleAssignmentPolicyStore: getMetaDataEntry(" + wlsCollectionName + ")");
      }

      PersistenceManager manager = this.storeService.getPersistenceManager();

      String var7;
      try {
         WLSRoleCollectionInfoId id = new WLSRoleCollectionInfoId(this.domainName, this.realmName, wlsCollectionName);
         WLSRoleCollectionInfo pc = (WLSRoleCollectionInfo)this.getObjectById(manager, id);
         if (pc == null) {
            Object var13 = null;
            return (String)var13;
         }

         byte[] wlsXmlFragment = pc.getWlsXmlFragment();
         if (wlsXmlFragment != null) {
            var7 = new String(wlsXmlFragment, "UTF8");
            return var7;
         }

         var7 = null;
      } catch (Throwable var11) {
         throw new PolicyStoreException(var11);
      } finally {
         manager.close();
      }

      return var7;
   }

   private String getMetaDataKeyName(String key) throws PolicyStoreException {
      if (key == null) {
         throw new PolicyStoreException("Key not supplied!");
      } else {
         int nameIdx = key.indexOf("RoleCollectionInfo#");
         if (nameIdx == -1) {
            throw new PolicyStoreException("Unknown key!");
         } else {
            String keyName = key.substring(nameIdx + PCI_KEY_LEN);
            if (keyName.length() == 0) {
               throw new PolicyStoreException("Key name not supplied!");
            } else {
               return keyName;
            }
         }
      }
   }

   public List readAllMetaDataEntries() throws PolicyStoreException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("RoleAssignmentPolicyStore: readAllMetaDataEntries()");
      }

      PersistenceManager manager = this.storeService.getPersistenceManager();
      Query query = null;

      LinkedList var14;
      try {
         query = manager.newQuery(WLSRoleCollectionInfo.class, "this.domainName == domainName && this.realmName == realmName");
         query.declareParameters("String domainName, String realmName");
         Collection result = (Collection)query.execute(this.domainName, this.realmName);
         List list = new LinkedList();
         Iterator var5 = result.iterator();

         while(var5.hasNext()) {
            Object pc = var5.next();
            WLSRoleCollectionInfo info = (WLSRoleCollectionInfo)pc;
            byte[] wlsXmlFragment = info.getWlsXmlFragment();
            if (wlsXmlFragment != null) {
               list.add(new String(wlsXmlFragment, "UTF8"));
            }
         }

         var14 = list;
      } catch (Exception var12) {
         throw new PolicyStoreException(var12);
      } finally {
         if (query != null) {
            query.closeAll();
         }

         manager.close();
      }

      return var14;
   }

   protected class RoleResourceEntry extends BasePolicyStore.Entry {
      private String resource;
      private String role;

      public RoleResourceEntry(XACMLEntryId id, AbstractPolicy data, int status, String resource, String role) {
         super(id, data, status);
         this.resource = resource;
         this.role = role;
      }

      public RoleResourceEntry(XACMLEntry pc, String resource, String role) throws URISyntaxException {
         super(pc, false);
         this.resource = resource;
         this.role = role;
      }

      public String getResource() {
         return this.resource;
      }

      public String getRole() {
         return this.role;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!super.equals(other)) {
            return false;
         } else if (!(other instanceof RoleResourceEntry)) {
            return false;
         } else {
            RoleResourceEntry reo = (RoleResourceEntry)other;
            return this.role == reo.role || this.role != null && this.role.equals(reo.role) && this.resource == reo.resource || this.resource != null && this.resource.equals(reo.resource);
         }
      }

      public int hashCode() {
         int code = super.hashCode();
         HashCodeUtil.hash(code, this.resource);
         HashCodeUtil.hash(code, this.role);
         return code;
      }

      protected void update(AbstractPolicy data, int status, String resourceScope, String roleScope) {
         boolean debug = RoleAssignmentPolicyStore.this.log.isDebugEnabled();
         if (debug) {
            RoleAssignmentPolicyStore.this.log.debug("Entering RoleAssignmentPolicyStore::update()");
         }

         int oldStatus = this.getStatus();
         super.update(data, status, resourceScope, roleScope);
         boolean resourceChange = resourceScope != null && !resourceScope.equals(this.resource);
         boolean roleChange = roleScope != null && !roleScope.equals(this.role);
         boolean haveChange = oldStatus != status || resourceChange || roleChange;
         boolean doRemove = oldStatus == 0 && haveChange;
         boolean doAdd = status == 0 && haveChange;
         synchronized(RoleAssignmentPolicyStore.this.allEntries) {
            if (doRemove) {
               Map me = (Map)RoleAssignmentPolicyStore.this.activeRoleAssignmentEntries.get(this.resource);
               if (me != null) {
                  Set s = (Set)me.get(this.role);
                  if (s != null && s.remove(this) && s.isEmpty()) {
                     me.remove(this.role);
                     if (me.isEmpty()) {
                        RoleAssignmentPolicyStore.this.activeRoleAssignmentEntries.remove(this.resource);
                     }
                  }
               }
            }

            if (doAdd) {
               Map mex = (Map)RoleAssignmentPolicyStore.this.activeRoleAssignmentEntries.get(resourceScope);
               if (mex == null) {
                  mex = new ConcurrentHashMap();
                  RoleAssignmentPolicyStore.this.activeRoleAssignmentEntries.put(resourceScope, mex);
               }

               Set sx = (Set)((Map)mex).get(roleScope);
               if (sx == null) {
                  sx = new ConcurrentHashSet();
                  ((Map)mex).put(roleScope, sx);
               }

               ((Set)sx).add(this);
            }

            if (resourceChange) {
               this.resource = resourceScope;
            }

            if (roleChange) {
               this.role = roleScope;
            }
         }

         if (debug) {
            RoleAssignmentPolicyStore.this.log.debug("Refreshing cache");
         }

         RoleAssignmentPolicyStore.this.roleAssignmentCache.clear();
         if (debug) {
            RoleAssignmentPolicyStore.this.log.debug("Exiting RoleAssignmentPolicyStore::update()");
         }

      }
   }

   private static class RoleAssignmentCacheEntry {
      private Map roleAssignmentPolicy;

      public RoleAssignmentCacheEntry(Map roleAssignmentPolicy) {
         this.roleAssignmentPolicy = roleAssignmentPolicy;
      }

      public Map getRoleAssignmentPolicy() {
         return this.roleAssignmentPolicy;
      }
   }

   private class EMap implements Map {
      private Map inner = new ConcurrentHashMap();
      private Resource r;
      private Collection rMatch;
      private boolean loaded = false;

      public EMap(Resource r, Collection rMatch) {
         this.r = r;
         this.rMatch = rMatch;
      }

      private void loadAll() throws IOException, URISyntaxException, DocumentParseException {
         if (!this.loaded) {
            this.loaded = true;
            Resource walker = this.r;

            while(true) {
               String key = walker != null ? walker.toString() : "";
               Map roleEntries = (Map)RoleAssignmentPolicyStore.this.entitlementRoleAssignmentEntries.get(key);
               if (roleEntries != null) {
                  Iterator var4 = roleEntries.entrySet().iterator();

                  while(var4.hasNext()) {
                     Map.Entry e = (Map.Entry)var4.next();
                     String role = (String)e.getKey();
                     if (!this.inner.containsKey(role)) {
                        this.inner.put(role, Collections.singleton(RoleAssignmentPolicyStore.this.generateRecord((PolicySetMember)e.getValue(), RoleAssignmentPolicyStore.this.calculateKnownMatch(this.rMatch, role))));
                     }
                  }
               }

               if (walker == null) {
                  return;
               }

               walker = walker.getParentResource();
            }
         }
      }

      private Set loadRole(String role) throws IOException, URISyntaxException, DocumentParseException {
         if (this.loaded) {
            return (Set)this.inner.get(role);
         } else {
            Set ir = (Set)this.inner.get(role);
            if (ir != null) {
               return ir;
            } else {
               Resource walker = this.r;

               while(true) {
                  String key = walker != null ? walker.toString() : "";
                  Map roleEntries = (Map)RoleAssignmentPolicyStore.this.entitlementRoleAssignmentEntries.get(key);
                  if (roleEntries != null) {
                     BasePolicyStore.Entry e = (BasePolicyStore.Entry)roleEntries.get(role);
                     if (e != null) {
                        Set rec = Collections.singleton(RoleAssignmentPolicyStore.this.generateRecord(e, RoleAssignmentPolicyStore.this.calculateKnownMatch(this.rMatch, role)));
                        this.inner.put(role, rec);
                        return rec;
                     }
                  }

                  if (walker == null) {
                     return null;
                  }

                  walker = walker.getParentResource();
               }
            }
         }
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public boolean containsKey(Object key) {
         if (!(key instanceof String)) {
            return false;
         } else {
            try {
               return this.loadRole((String)key) != null;
            } catch (XACMLException var3) {
               throw new RuntimeException(var3);
            }
         }
      }

      public boolean containsValue(Object value) {
         try {
            this.loadAll();
         } catch (XACMLException var3) {
            throw new RuntimeException(var3);
         }

         return this.inner.containsValue(value);
      }

      public Set entrySet() {
         try {
            this.loadAll();
         } catch (XACMLException var2) {
            throw new RuntimeException(var2);
         }

         return this.inner.entrySet();
      }

      public Set get(Object key) {
         if (!(key instanceof String)) {
            return null;
         } else {
            try {
               return this.loadRole((String)key);
            } catch (XACMLException var3) {
               throw new RuntimeException(var3);
            }
         }
      }

      public boolean isEmpty() {
         if (this.loaded) {
            return this.inner.isEmpty();
         } else if (!this.inner.isEmpty()) {
            return false;
         } else {
            Resource walker = this.r;

            while(true) {
               String key = walker != null ? walker.toString() : "";
               Map roleEntries = (Map)RoleAssignmentPolicyStore.this.entitlementRoleAssignmentEntries.get(key);
               if (roleEntries != null) {
                  return false;
               }

               if (walker == null) {
                  return true;
               }

               walker = walker.getParentResource();
            }
         }
      }

      public Set keySet() {
         try {
            this.loadAll();
         } catch (XACMLException var2) {
            throw new RuntimeException(var2);
         }

         return this.inner.keySet();
      }

      public Set put(String key, Set value) {
         throw new UnsupportedOperationException();
      }

      public void putAll(Map m) {
         throw new UnsupportedOperationException();
      }

      public Set remove(Object key) {
         throw new UnsupportedOperationException();
      }

      public int size() {
         try {
            this.loadAll();
         } catch (XACMLException var2) {
            throw new RuntimeException(var2);
         }

         return this.inner.size();
      }

      public Collection values() {
         try {
            this.loadAll();
         } catch (XACMLException var2) {
            throw new RuntimeException(var2);
         }

         return this.inner.values();
      }
   }
}

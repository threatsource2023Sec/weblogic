package com.bea.security.providers.xacml.store;

import com.bea.common.security.store.data.WLSPolicyCollectionInfo;
import com.bea.common.security.store.data.WLSPolicyCollectionInfoId;
import com.bea.common.security.store.data.XACMLAuthorizationPolicy;
import com.bea.common.security.store.data.XACMLAuthorizationPolicyId;
import com.bea.common.security.store.data.XACMLEntry;
import com.bea.common.security.store.data.XACMLEntryId;
import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.Policy;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import weblogic.security.spi.Resource;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.collections.ConcurrentHashSet;
import weblogic.utils.collections.SecondChanceCacheMap;

public class AuthorizationPolicyStore extends BasePolicyStore implements ApplicableAuthorizationPolicyFinder {
   private static final String HEADER = "AuthorizationPolicyStore: ";
   private static final String WLS_POLICY_INFO = "WLSPolicyInfo";
   private static final String PCI_KEY = "PolicyCollectionInfo#";
   private static final int PCI_KEY_LEN = "PolicyCollectionInfo#".length();
   private final SecondChanceCacheMap authorizationCache;
   private final Map activeAuthorizationEntries;
   private final Map entitlementAuthorizationEntries;
   private final ResourceMatchUtil rmu;
   private boolean disableLazyLoadPolicies = Boolean.getBoolean("weblogic.security.disableLazyLoadPolicies");

   public AuthorizationPolicyStore(PolicyStoreConfigInfo info, AttributeRegistry registry) throws URISyntaxException {
      super(info, registry);
      this.authorizationCache = new SecondChanceCacheMap(cacheCapacity);
      this.activeAuthorizationEntries = new ConcurrentHashMap();
      this.entitlementAuthorizationEntries = new ConcurrentHashMap();
      this.rmu = new ResourceMatchUtil();
   }

   public void init() throws PolicyStoreException, DocumentParseException, URISyntaxException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("AuthorizationPolicyStore: Loading Bootstrap template");
      }

      EntryConverter entryConverter = new XACMLEntryConverter(this.domainName, this.realmName);
      this.bootstrapService.loadLDIFXACMLAuthorizerTemplate(this.log, this.storeService, entryConverter, this.domainName, this.realmName);
      this.bootstrapService.updateXACMLAuthorizerPolicies(this.log, new GlobalPolicyUpdateImpl(this, this.log), new DefaultBootStrapPersistenceImpl(this.storeService), this.domainName, this.realmName);
      super.init();
   }

   protected Class getPolicyObjectClass() throws PolicyStoreException {
      try {
         return Class.forName("com.bea.common.security.store.data.XACMLAuthorizationPolicy");
      } catch (ClassNotFoundException var2) {
         throw new PolicyStoreException(var2);
      }
   }

   protected XACMLEntryId newPolicyObjectId(String typeName, String cn, String xacmlVersion) {
      return new XACMLAuthorizationPolicyId(this.domainName, this.realmName, typeName, cn, xacmlVersion);
   }

   protected XACMLEntry newPolicyObject(String typeName, String cn, String xacmlVersion) {
      return new XACMLAuthorizationPolicy(this.domainName, this.realmName, typeName, cn, xacmlVersion, (byte[])null, (String)null);
   }

   protected XACMLEntryId getPolicyObjectId(XACMLEntry pc) {
      XACMLAuthorizationPolicy policy = (XACMLAuthorizationPolicy)pc;
      return this.newPolicyObjectId(policy.getTypeName(), policy.getCn(), policy.getXacmlVersion());
   }

   protected String getMetaDataElementName() {
      return "WLSPolicyInfo";
   }

   protected void addXacmlScope(XACMLEntry pc, int status, AbstractPolicy ap) {
      XACMLAuthorizationPolicy policy = (XACMLAuthorizationPolicy)pc;
      boolean debug = this.log.isDebugEnabled();
      String res = "";
      if (status == 3) {
         res = this.converter.getResourceId(ap.getId().toString());
      } else {
         try {
            res = this.rmu.getTargetResource(ap.getTarget());
         } catch (MultipleResourceTargetException var8) {
            if (debug) {
               this.log.debug("Unable to generate xacmlResourceScope for " + (ap instanceof Policy ? "Policy" : "PolicySet") + ": " + ap.getId() + " Version: " + ap.getVersion());
            }
         }
      }

      if (res != null && !"".equals(res)) {
         if (this.log.isDebugEnabled()) {
            this.log.debug("Using xacmlResourceScope: " + res);
         }

         ArrayList xacmlResourceScope = new ArrayList(1);
         xacmlResourceScope.add(res);
         policy.setXacmlResourceScope(xacmlResourceScope);
      }

   }

   protected void modifyXacmlScope(XACMLEntry pc, int status, AbstractPolicy ap) {
      XACMLAuthorizationPolicy policy = (XACMLAuthorizationPolicy)pc;
      boolean debug = this.log.isDebugEnabled();
      if (status == 0) {
         String res = null;

         try {
            res = this.rmu.getTargetResource(ap.getTarget());
         } catch (MultipleResourceTargetException var8) {
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
      }

   }

   protected void load() throws PolicyStoreException, DocumentParseException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("AuthorizationPolicyStore: Entering AuthorizationPolicyStore.load()");
      }

      Class policyObjectClass = this.getPolicyObjectClass();
      PersistenceManager manager = this.storeService.getPersistenceManager();
      Query query = null;

      try {
         query = manager.newQuery(policyObjectClass, "this.domainName == domainName && this.realmName == realmName && this.xacmlStatus == xacmlStatus");
         query.declareParameters("String domainName, String realmName, String xacmlStatus");
         Collection result = (Collection)query.execute(this.domainName, this.realmName, (new Integer(0)).toString());
         if (debug) {
            this.log.debug("AuthorizationPolicyStore: Found " + result.size() + " general authorization policies or policy sets");
         }

         Iterator var6 = result.iterator();

         Object pc;
         XACMLAuthorizationPolicy policy;
         String resource;
         Collection scope;
         while(var6.hasNext()) {
            pc = var6.next();
            policy = (XACMLAuthorizationPolicy)pc;
            resource = "";
            scope = policy.getXacmlResourceScope();
            if (scope != null && scope.size() > 0) {
               resource = (String)scope.iterator().next();
            }

            Set ce = (Set)this.activeAuthorizationEntries.get(resource);
            if (ce == null) {
               ce = new ConcurrentHashSet();
               this.activeAuthorizationEntries.put(resource, ce);
            }

            BasePolicyStore.Entry e = new ResourceEntry(policy, resource);
            ((Set)ce).add(e);
            this.allEntries.put(e.getID(), e);
         }

         result = (Collection)query.execute(this.domainName, this.realmName, (new Integer(1)).toString());
         if (debug) {
            this.log.debug("AuthorizationPolicyStore: Found " + result.size() + " general 'if referenced' authorization policies or policy sets");
         }

         var6 = result.iterator();

         while(var6.hasNext()) {
            pc = var6.next();
            policy = (XACMLAuthorizationPolicy)pc;
            BasePolicyStore.Entry e = new ResourceEntry(policy, (String)null);
            this.allEntries.put(e.getID(), e);
         }

         result = (Collection)query.execute(this.domainName, this.realmName, (new Integer(3)).toString());
         if (debug) {
            this.log.debug("AuthorizationPolicyStore: Found " + result.size() + " entitlement authorization policies");
         }

         var6 = result.iterator();

         while(var6.hasNext()) {
            pc = var6.next();
            policy = (XACMLAuthorizationPolicy)pc;
            resource = "";
            scope = policy.getXacmlResourceScope();
            if (scope != null && scope.size() > 0) {
               resource = (String)scope.iterator().next();
            }

            BasePolicyStore.Entry e = new ResourceEntry(policy, resource, !this.disableLazyLoadPolicies);
            this.entitlementAuthorizationEntries.put(resource, e);
            this.allEntries.put(e.getID(), e);
         }
      } finally {
         if (query != null) {
            query.closeAll();
         }

         manager.close();
      }

      if (debug) {
         this.log.debug("AuthorizationPolicyStore: Exiting AuthorizationPolicyStore.load()");
      }

   }

   private AuthorizationCacheEntry retrieveAuthorizationEntry(Resource r) throws DocumentParseException, IOException, URISyntaxException {
      AuthorizationCacheEntry entry = (AuthorizationCacheEntry)this.authorizationCache.get(r);
      if (entry == null) {
         entry = new AuthorizationCacheEntry(this.loadAuthorizationPolicyForResource(r));
         this.authorizationCache.put(r, entry);
      }

      return entry;
   }

   public Set findAuthorizationPolicy(EvaluationCtx context) throws DocumentParseException, IOException, URISyntaxException {
      if (context instanceof BasicEvaluationCtx) {
         Resource r = ((BasicEvaluationCtx)context).getResource();
         AuthorizationCacheEntry entry = this.retrieveAuthorizationEntry(r);
         if (entry != null) {
            return entry.getAuthorizationPolicy();
         }
      }

      return null;
   }

   private Set loadAuthorizationPolicyForResource(Resource r) throws DocumentParseException, IOException, URISyntaxException {
      Collection kmc = this.calculateKnownMatch(r);
      Set col = new HashSet();
      Resource walker = r;
      boolean lookingEntitlement = true;

      while(true) {
         String key = walker != null ? walker.toString() : "";
         Collection ce = (Collection)this.activeAuthorizationEntries.get(key);
         if (ce != null) {
            Iterator var8 = ce.iterator();

            while(var8.hasNext()) {
               BasePolicyStore.Entry e = (BasePolicyStore.Entry)var8.next();
               col.add(this.generateRecord(e, kmc));
            }
         }

         if (lookingEntitlement) {
            BasePolicyStore.Entry e = (BasePolicyStore.Entry)this.entitlementAuthorizationEntries.get(key);
            if (e != null) {
               col.add(this.generateRecord(e, kmc));
               lookingEntitlement = false;
            }
         }

         if (walker == null) {
            return !col.isEmpty() ? col : null;
         }

         walker = walker.getParentResource();
      }
   }

   protected void entryAdded(XACMLEntryId id, AbstractPolicy data, int status, String resourceScope, String ignore) {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("Entering AuthorizationPolicyStore::entryAdded(" + id + ", " + status + ")");
      }

      BasePolicyStore.Entry e = new ResourceEntry(id, data, status, resourceScope);
      synchronized(this.allEntries) {
         switch (status) {
            case 0:
               Set ce = (Set)this.activeAuthorizationEntries.get(resourceScope);
               if (ce == null) {
                  ce = new ConcurrentHashSet();
                  this.activeAuthorizationEntries.put(resourceScope, ce);
               }

               ((Set)ce).add(e);
               this.allEntries.put(id, e);
               break;
            case 1:
               this.allEntries.put(id, e);
            case 2:
            default:
               break;
            case 3:
               this.entitlementAuthorizationEntries.put(resourceScope, e);
               this.allEntries.put(id, e);
         }
      }

      if (debug) {
         this.log.debug("Refreshing cache");
      }

      this.authorizationCache.clear();
      if (debug) {
         this.log.debug("Exiting AuthorizationPolicyStore::entryAdded()");
      }

   }

   protected void entryRemoved(BasePolicyStore.Entry e) {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("Entering AuthorizationPolicyStore::entryRemoved(" + e.getID() + ")");
      }

      synchronized(this.allEntries) {
         this.allEntries.remove(e.getID());
         switch (e.getStatus()) {
            case 0:
               String resource = "";
               if (e instanceof ResourceEntry) {
                  resource = ((ResourceEntry)e).getResource();
               }

               Collection ce = (Collection)this.activeAuthorizationEntries.get(resource);
               if (ce != null && ce.remove(e) && ce.isEmpty()) {
                  this.activeAuthorizationEntries.remove(resource);
               }
               break;
            case 3:
               String resource2 = "";
               if (e instanceof ResourceEntry) {
                  resource2 = ((ResourceEntry)e).getResource();
               }

               this.entitlementAuthorizationEntries.remove(resource2);
         }
      }

      if (debug) {
         this.log.debug("Refreshing cache");
      }

      this.authorizationCache.clear();
      if (debug) {
         this.log.debug("Exiting AuthorizationPolicyStore::entryRemoved()");
      }

   }

   protected RemoteCommitListener getChangeListener() {
      return new RemoteCommitListener() {
         public void afterCommit(RemoteCommitEvent event) {
            try {
               Collection ids = event.getAddedObjectIds();
               if (ids != null && ids.size() > 0) {
                  AuthorizationPolicyStore.this.changeAdd(ids);
               }

               ids = event.getDeletedObjectIds();
               if (ids != null && ids.size() > 0) {
                  AuthorizationPolicyStore.this.changeDelete(ids);
               }

               ids = event.getUpdatedObjectIds();
               if (ids != null && ids.size() > 0) {
                  AuthorizationPolicyStore.this.changeUpdate(ids);
               }
            } catch (Exception var3) {
               if (AuthorizationPolicyStore.this.log.isDebugEnabled()) {
                  AuthorizationPolicyStore.this.log.debug("Failed to honor change notification", var3);
               }
            }

            AuthorizationPolicyStore.this.authorizationCache.clear();
         }
      };
   }

   private void changeAdd(Collection objectIds) throws URISyntaxException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("AuthorizationPolicyStore: Entering changeAdd(" + objectIds.size() + ")");
      }

      PersistenceManager manager = this.storeService.getPersistenceManager();

      try {
         Iterator var3 = objectIds.iterator();

         while(var3.hasNext()) {
            Object id = var3.next();
            if (id instanceof XACMLAuthorizationPolicyId) {
               if (this.isPreEssexGlobalPolicy(id)) {
                  if (this.log.isDebugEnabled()) {
                     this.log.debug("AuthorizationPolicyStore: Skip pre-essex style global policy add remote commit message");
                  }
               } else {
                  XACMLAuthorizationPolicy pc = (XACMLAuthorizationPolicy)((XACMLAuthorizationPolicyId)id).getObject(manager);
                  int status = Integer.parseInt(pc.getXacmlStatus());
                  String resource = "";
                  Collection scope = pc.getXacmlResourceScope();
                  if (scope != null && scope.size() > 0) {
                     resource = (String)scope.iterator().next();
                  }

                  BasePolicyStore.Entry e = new ResourceEntry(pc, resource);
                  synchronized(this.allEntries) {
                     switch (status) {
                        case 0:
                           Set ce = (Set)this.activeAuthorizationEntries.get(resource);
                           if (ce == null) {
                              ce = new HashSet();
                              this.activeAuthorizationEntries.put(resource, ce);
                           }

                           ((Set)ce).add(e);
                           this.allEntries.put(id, e);
                           break;
                        case 1:
                           this.allEntries.put(id, e);
                        case 2:
                        default:
                           break;
                        case 3:
                           this.entitlementAuthorizationEntries.put(resource, e);
                           this.allEntries.put(id, e);
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
         this.log.debug("AuthorizationPolicyStore: Entering changeDelete(" + objectIds.size() + ")");
      }

      Iterator var2 = objectIds.iterator();

      while(var2.hasNext()) {
         Object id = var2.next();
         if (id instanceof XACMLAuthorizationPolicyId) {
            if (this.isPreEssexGlobalPolicy(id)) {
               if (this.log.isDebugEnabled()) {
                  this.log.debug("AuthorizationPolicyStore: Skip pre-essex style global policy add remote commit message");
               }
            } else {
               BasePolicyStore.Entry e = (BasePolicyStore.Entry)this.allEntries.remove(id);
               if (e != null) {
                  switch (e.getStatus()) {
                     case 0:
                        String resource = "";
                        if (e instanceof ResourceEntry) {
                           resource = ((ResourceEntry)e).getResource();
                        }

                        Collection ce = (Collection)this.activeAuthorizationEntries.get(resource);
                        boolean result = false;
                        if (ce != null) {
                           result = ce.remove(e);
                           if (result && ce.isEmpty()) {
                              this.activeAuthorizationEntries.remove(resource);
                           }
                        }

                        if (this.log.isDebugEnabled()) {
                           this.log.debug("Active entry " + (result ? "removed" : "not removed") + " from cache");
                        }
                        break;
                     case 3:
                        String resource2 = "";
                        if (e instanceof ResourceEntry) {
                           resource2 = ((ResourceEntry)e).getResource();
                        }

                        this.entitlementAuthorizationEntries.remove(resource2);
                  }
               }
            }
         }
      }

   }

   protected boolean isPreEssexGlobalPolicy(Object id) {
      String longId = ((XACMLAuthorizationPolicyId)id).toString();
      String shortId = longId.substring(0, longId.indexOf(44));
      if (this.log.isDebugEnabled()) {
         this.log.debug("AuthorizationPolicyStore: isPreEssexGlobalPolicy: shortId=" + shortId + ", longId=" + longId);
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
         this.log.debug("AuthorizationPolicyStore: setMetaDataEntry(" + wlsCollectionName + ")");
      }

      PersistenceManager manager = this.storeService.getPersistenceManager();

      try {
         manager.currentTransaction().begin();

         try {
            WLSPolicyCollectionInfoId id = new WLSPolicyCollectionInfoId(this.domainName, this.realmName, wlsCollectionName);
            WLSPolicyCollectionInfo pc = (WLSPolicyCollectionInfo)this.getObjectById(manager, id);
            if (pc == null) {
               pc = new WLSPolicyCollectionInfo(this.domainName, this.realmName, wlsCollectionName);
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
         this.log.debug("AuthorizationPolicyStore: getMetaDataEntry(" + wlsCollectionName + ")");
      }

      PersistenceManager manager = this.storeService.getPersistenceManager();

      String var7;
      try {
         WLSPolicyCollectionInfoId id = new WLSPolicyCollectionInfoId(this.domainName, this.realmName, wlsCollectionName);
         WLSPolicyCollectionInfo pc = (WLSPolicyCollectionInfo)this.getObjectById(manager, id);
         if (pc == null) {
            Object var13 = null;
            return (String)var13;
         }

         byte[] wlsXmlFragment = pc.getWlsXmlFragment();
         if (wlsXmlFragment == null) {
            var7 = null;
            return var7;
         }

         var7 = new String(wlsXmlFragment, "UTF8");
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
         int nameIdx = key.indexOf("PolicyCollectionInfo#");
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
         this.log.debug("AuthorizationPolicyStore: readAllMetaDataEntries()");
      }

      PersistenceManager manager = this.storeService.getPersistenceManager();
      Query query = null;

      LinkedList var14;
      try {
         query = manager.newQuery(WLSPolicyCollectionInfo.class, "this.domainName == domainName && this.realmName == realmName");
         query.declareParameters("String domainName, String realmName");
         Collection result = (Collection)query.execute(this.domainName, this.realmName);
         List list = new LinkedList();
         Iterator var5 = result.iterator();

         while(var5.hasNext()) {
            Object pc = var5.next();
            WLSPolicyCollectionInfo info = (WLSPolicyCollectionInfo)pc;
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

   protected class ResourceEntry extends BasePolicyStore.Entry {
      private String resource;

      public ResourceEntry(XACMLEntryId id, AbstractPolicy data, int status, String resource) {
         super(id, data, status);
         this.resource = resource;
      }

      public ResourceEntry(XACMLEntry pc, String resource) throws URISyntaxException {
         super(pc, false);
         this.resource = resource;
      }

      public ResourceEntry(XACMLEntry pc, String resource, boolean lazyLoadData) throws URISyntaxException {
         super(pc, lazyLoadData);
         this.resource = resource;
      }

      public String getResource() {
         return this.resource;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!super.equals(other)) {
            return false;
         } else if (!(other instanceof ResourceEntry)) {
            return false;
         } else {
            ResourceEntry reo = (ResourceEntry)other;
            return this.resource == reo.resource || this.resource != null && this.resource.equals(reo.resource);
         }
      }

      public int hashCode() {
         int code = super.hashCode();
         HashCodeUtil.hash(code, this.resource);
         return code;
      }

      protected void update(AbstractPolicy data, int status, String resourceScope, String ignore) {
         boolean debug = AuthorizationPolicyStore.this.log.isDebugEnabled();
         if (debug) {
            AuthorizationPolicyStore.this.log.debug("Entering AuthorizationPolicyStore::update()");
         }

         int oldStatus = this.getStatus();
         super.update(data, status, resourceScope, ignore);
         boolean resourceChange = resourceScope != null && this.resource != resourceScope && !resourceScope.equals(this.resource);
         boolean haveChange = oldStatus != status || resourceChange;
         boolean doRemove = oldStatus == 0 && haveChange;
         boolean doAdd = status == 0 && haveChange;
         synchronized(AuthorizationPolicyStore.this.allEntries) {
            if (doRemove) {
               Collection ce = (Collection)AuthorizationPolicyStore.this.activeAuthorizationEntries.get(this.resource);
               if (ce != null && ce.remove(this) && ce.isEmpty()) {
                  AuthorizationPolicyStore.this.activeAuthorizationEntries.remove(this.resource);
               }
            }

            if (doAdd) {
               Set cex = (Set)AuthorizationPolicyStore.this.activeAuthorizationEntries.get(resourceScope);
               if (cex == null) {
                  cex = new ConcurrentHashSet();
                  AuthorizationPolicyStore.this.activeAuthorizationEntries.put(resourceScope, cex);
               }

               ((Set)cex).add(this);
            }

            if (resourceChange) {
               this.resource = resourceScope;
            }
         }

         if (debug) {
            AuthorizationPolicyStore.this.log.debug("Refreshing cache");
         }

         AuthorizationPolicyStore.this.authorizationCache.clear();
         if (debug) {
            AuthorizationPolicyStore.this.log.debug("Exiting AuthorizationPolicyStore::update()");
         }

      }

      protected byte[] loadXACMLEntryData() throws PolicyStoreException {
         if (this.getID() != null && this.getID().getCn() != null) {
            boolean debug = AuthorizationPolicyStore.this.log.isDebugEnabled();
            if (debug) {
               AuthorizationPolicyStore.this.log.debug("AuthorizationPolicyStore: Entering AuthorizationPolicyStore.loadXACMLEntryData()");
            }

            Class policyObjectClass = AuthorizationPolicyStore.this.getPolicyObjectClass();
            PersistenceManager manager = AuthorizationPolicyStore.this.storeService.getPersistenceManager();
            Query query = null;

            byte[] var7;
            try {
               query = manager.newQuery(policyObjectClass, "this.domainName == domainName && this.realmName == realmName && this.cn == id");
               query.declareParameters("String domainName, String realmName, String id");
               Collection result = (Collection)query.execute(AuthorizationPolicyStore.this.domainName, AuthorizationPolicyStore.this.realmName, this.getID().getCn());
               XACMLAuthorizationPolicy policy;
               if (result == null || result.isEmpty()) {
                  policy = null;
                  return (byte[])policy;
               }

               policy = (XACMLAuthorizationPolicy)result.iterator().next();
               if (debug) {
                  AuthorizationPolicyStore.this.log.debug("AuthorizationPolicyStore: Found policy" + policy);
               }

               var7 = policy.getXacmlDocument();
            } finally {
               if (query != null) {
                  query.closeAll();
               }

               manager.close();
            }

            return var7;
         } else {
            return null;
         }
      }
   }

   private static class AuthorizationCacheEntry {
      private Set authorizationPolicy;

      public AuthorizationCacheEntry(Set authorizationPolicy) {
         this.authorizationPolicy = authorizationPolicy;
      }

      public Set getAuthorizationPolicy() {
         return this.authorizationPolicy;
      }
   }
}

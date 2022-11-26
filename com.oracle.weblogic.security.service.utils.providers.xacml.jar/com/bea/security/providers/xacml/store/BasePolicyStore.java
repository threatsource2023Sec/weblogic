package com.bea.security.providers.xacml.store;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.JAXPFactoryService;
import com.bea.common.security.store.data.TopId;
import com.bea.common.security.store.data.XACMLEntry;
import com.bea.common.security.store.data.XACMLEntryId;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicyIdReference;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.common.security.xacml.policy.PolicySetIdReference;
import com.bea.common.security.xacml.policy.PolicySetMember;
import com.bea.common.store.bootstrap.BootStrapService;
import com.bea.common.store.service.RemoteCommitListener;
import com.bea.common.store.service.StoreService;
import com.bea.security.providers.xacml.entitlement.EntitlementAwarePolicyStore;
import com.bea.security.providers.xacml.entitlement.EntitlementConverter;
import com.bea.security.xacml.ConcurrentModificationException;
import com.bea.security.xacml.IOException;
import com.bea.security.xacml.PolicyMetaData;
import com.bea.security.xacml.PolicyMetaDataException;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.cache.resource.ResourceMatchUtil;
import com.bea.security.xacml.cache.role.RoleMatchUtil;
import com.bea.security.xacml.policy.PolicyUtils;
import com.bea.security.xacml.store.PolicyFinder;
import com.bea.security.xacml.store.PolicyRecord;
import com.bea.security.xacml.store.PolicySetRecord;
import com.bea.security.xacml.store.Record;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.jdo.JDOException;
import javax.jdo.JDOFatalDataStoreException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.JDOOptimisticVerificationException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import weblogic.security.providers.utils.Utils;
import weblogic.security.spi.Resource;
import weblogic.utils.collections.ConcurrentHashMap;

public abstract class BasePolicyStore implements MetaDataPolicyStore, EntitlementAwarePolicyStore, PolicyFinder {
   protected static final String ALL_ROLES = "";
   private static final String HEADER = "BasePolicyStore: ";
   protected static final String POLICIES_TYPE = "Policies";
   protected static final String POLICYSETS_TYPE = "PolicySets";
   protected static final int cacheCapacity = Integer.getInteger("weblogic.security.policyStoreCacheCapacity", 5000);
   private static final String WLSCREATORINFO = "wlsCreatorInfo";
   private static final String WLSCOLLECTIONNAME = "wlsCollectionName";
   private static final String METADATA_VALUE = "wlsXmlFragment";
   private static final String POLICYID_SEARCH = "policyId";
   private static final String RESSCOPE_SEARCH = "resourceScope";
   private static final String TIMESTAMP_SEARCH = "modifyTimestamp";
   private static final String CREATE_TIMESTAMP = "createTimestamp";
   protected final String domainName;
   protected final String realmName;
   protected final LoggerSpi log;
   private final AttributeRegistry registry;
   protected final EntitlementConverter converter;
   private final ResourceMatchUtil rmu;
   protected final RoleMatchUtil omu;
   protected final Map allEntries;
   protected StoreService storeService;
   protected BootStrapService bootstrapService;
   private JAXPFactoryService jaxpService;

   public BasePolicyStore(PolicyStoreConfigInfo info, AttributeRegistry registry) throws URISyntaxException {
      this.storeService = info.getStoreService();
      this.bootstrapService = info.getBootstrapService();
      this.jaxpService = info.getJaxpService();
      this.registry = registry;
      this.domainName = info.getDomainName();
      this.realmName = info.getRealmName();
      this.log = info.getLogger();
      this.converter = new EntitlementConverter(this.log);
      this.allEntries = new ConcurrentHashMap();
      this.rmu = new ResourceMatchUtil();
      this.omu = new RoleMatchUtil();
   }

   public void init() throws PolicyStoreException, DocumentParseException, URISyntaxException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.init()");
      }

      this.load();
      this.storeService.addRemoteCommitListener(this.getPolicyObjectClass(), this.getChangeListener());
      if (this.log.isDebugEnabled()) {
         this.log.debug("BasePolicyStore: Exiting BasePolicyStore.init()");
      }

   }

   protected abstract Class getPolicyObjectClass() throws PolicyStoreException;

   protected abstract XACMLEntryId newPolicyObjectId(String var1, String var2, String var3);

   protected abstract XACMLEntry newPolicyObject(String var1, String var2, String var3);

   protected abstract XACMLEntryId getPolicyObjectId(XACMLEntry var1);

   protected abstract void addXacmlScope(XACMLEntry var1, int var2, AbstractPolicy var3);

   protected abstract void modifyXacmlScope(XACMLEntry var1, int var2, AbstractPolicy var3);

   protected String getXacmlRoleScope(XACMLEntry pc) {
      return null;
   }

   protected String getXacmlResourceScope(XACMLEntry pc) {
      Collection scope = pc.getXacmlResourceScope();
      return scope != null && scope.size() > 0 ? (String)scope.iterator().next() : null;
   }

   protected abstract RemoteCommitListener getChangeListener();

   protected abstract void load() throws PolicyStoreException, DocumentParseException, URISyntaxException;

   protected abstract String getMetaDataElementName();

   public Set readAllPolicies() throws DocumentParseException, PolicyStoreException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.readAllPolicies()");
      }

      Set policies = new HashSet();
      PersistenceManager manager = this.storeService.getPersistenceManager();
      Query query = null;

      try {
         query = manager.newQuery(this.getPolicyObjectClass(), "this.domainName == domainName && this.realmName == realmName && this.typeName == typeName");
         query.declareParameters("String domainName, String realmName, String typeName");
         Collection result = (Collection)query.execute(this.domainName, this.realmName, "Policies");
         Iterator var6 = result.iterator();

         while(var6.hasNext()) {
            Object pc = var6.next();
            Policy policy = (Policy)this.getAbstractPolicy((XACMLEntry)pc);
            if (policy != null) {
               policies.add(policy);
            }
         }
      } finally {
         if (query != null) {
            query.closeAll();
         }

         manager.close();
      }

      if (debug) {
         this.log.debug("BasePolicyStore: BasePolicyStore.readAllPolicies() results:");
         if (policies.size() > 0) {
            Iterator var12 = policies.iterator();

            while(var12.hasNext()) {
               Policy p = (Policy)var12.next();
               this.log.debug("Id: " + p.getId() + ", Version: " + p.getVersion());
            }
         } else {
            this.log.debug("BasePolicyStore: BasePolicyStore.readAllPolicies() No results found");
         }
      }

      return policies;
   }

   public Set readAllPolicySets() throws DocumentParseException, PolicyStoreException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.readAllPolicySets()");
      }

      Set policySets = new HashSet();
      PersistenceManager manager = this.storeService.getPersistenceManager();
      Query query = null;

      try {
         query = manager.newQuery(this.getPolicyObjectClass(), "this.domainName == domainName && this.realmName == realmName && this.typeName == typeName");
         query.declareParameters("String domainName, String realmName, String typeName");
         Collection result = (Collection)query.execute(this.domainName, this.realmName, "PolicySets");
         Iterator var6 = result.iterator();

         while(var6.hasNext()) {
            Object pc = var6.next();
            PolicySet policy = (PolicySet)this.getAbstractPolicy((XACMLEntry)pc);
            policySets.add(policy);
         }
      } finally {
         if (query != null) {
            query.closeAll();
         }

         manager.close();
      }

      if (debug) {
         this.log.debug("BasePolicyStore: BasePolicyStore.readAllPolicySets() results:");
         if (policySets.size() > 0) {
            Iterator var12 = policySets.iterator();

            while(var12.hasNext()) {
               PolicySet p = (PolicySet)var12.next();
               this.log.debug("Id: " + p.getId() + ", Version: " + p.getVersion());
            }
         } else {
            this.log.debug("BasePolicyStore: BasePolicyStore.readAllPolicySets() No results found");
         }
      }

      return policySets;
   }

   protected Object getObjectById(PersistenceManager manager, TopId id) {
      return this.getObjectById(manager, id, false);
   }

   protected Object getObjectById(PersistenceManager manager, TopId id, boolean isDetach) {
      try {
         return id.getObject(manager, isDetach);
      } catch (JDOObjectNotFoundException var5) {
         return null;
      }
   }

   private XACMLEntry getPolicyObject(URI identifier, String version, String typeName) {
      String cn = identifier.toString();
      XACMLEntryId id = this.newPolicyObjectId(typeName, cn, version);
      PersistenceManager manager = this.storeService.getPersistenceManager();

      XACMLEntry var7;
      try {
         var7 = (XACMLEntry)this.getObjectById(manager, id, true);
      } finally {
         manager.close();
      }

      return var7;
   }

   public boolean hasPolicy(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.hasPolicy(" + identifier + "," + version + ")");
      }

      XACMLEntry pc = this.getPolicyObject(identifier, version, "Policies");
      if (debug) {
         this.log.debug("BasePolicyStore: BasePolicyStore.hasPolicy() result: " + (pc != null ? "found" : "not found"));
      }

      return pc != null;
   }

   public boolean hasPolicySet(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.hasPolicySet(" + identifier + "," + version + ")");
      }

      XACMLEntry pc = this.getPolicyObject(identifier, version, "PolicySets");
      if (debug) {
         this.log.debug("BasePolicyStore: BasePolicyStore.hasPolicySet() result: " + (pc != null ? "found" : "not found"));
      }

      return pc != null;
   }

   public Policy readPolicy(URI identifier, String version) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.readPolicy(" + identifier + "," + version + ")");
      }

      Policy p = null;
      XACMLEntry pc = this.getPolicyObject(identifier, version, "Policies");
      if (pc != null) {
         p = (Policy)this.getAbstractPolicy(pc);
      }

      if (debug) {
         this.log.debug("BasePolicyStore: BasePolicyStore.readPolicy() result: " + (p != null ? "found" : "not found"));
      }

      return p;
   }

   public PolicySet readPolicySet(URI identifier, String version) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.readPolicySet(" + identifier + "," + version + ")");
      }

      PolicySet p = null;
      XACMLEntry pc = this.getPolicyObject(identifier, version, "PolicySets");
      if (pc != null) {
         p = (PolicySet)this.getAbstractPolicy(pc);
      }

      if (debug) {
         this.log.debug("BasePolicyStore: BasePolicyStore.readPolicySet() result: " + (p != null ? "found" : "not found"));
      }

      return p;
   }

   public void addPolicy(Policy policy) throws DocumentParseException, IOException, PolicyStoreException {
      this.addPolicy(policy, 0);
   }

   public void addPolicy(Policy policy, int status) throws DocumentParseException, IOException, PolicyStoreException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.addPolicy(" + policy.getId() + "::" + policy.getVersion() + "," + status + ")");
      }

      this.addPolicy(policy, status, (PolicyMetaData)null, true);
   }

   public void addPolicy(Policy policy, int status, PolicyMetaData data) throws DocumentParseException, PolicyMetaDataException, IOException, PolicyStoreException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("BasePolicyStore: Entering PolicyMetaData addPolicy(" + policy.getId() + "::" + policy.getVersion() + "," + status + ")");
      }

      this.checkPolicyMetaData(data);
      this.addPolicy(policy, status, data, true);
   }

   public void addPolicySet(PolicySet set) throws DocumentParseException, IOException, PolicyStoreException {
      this.addPolicySet(set, 0);
   }

   public void addPolicySet(PolicySet set, int status) throws DocumentParseException, IOException, PolicyStoreException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.addPolicySet(" + set.getId() + "::" + set.getVersion() + "," + status + ")");
      }

      this.addPolicy(set, status, (PolicyMetaData)null, false);
   }

   public void addPolicySet(PolicySet set, int status, PolicyMetaData data) throws DocumentParseException, PolicyMetaDataException, IOException, PolicyStoreException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("BasePolicyStore: Entering PolicyMetaData addPolicySet(" + set.getId() + "::" + set.getVersion() + "," + status + ")");
      }

      this.checkPolicyMetaData(data);
      this.addPolicy(set, status, data, false);
   }

   public void updatePolicies(Set updatePolicies) throws PolicyStoreException {
      PersistenceManager manager = this.storeService.getPersistenceManager();
      Transaction tx = manager.currentTransaction();

      try {
         tx.begin();
         Iterator var8 = updatePolicies.iterator();

         while(true) {
            while(var8.hasNext()) {
               AbstractPolicy[] item = (AbstractPolicy[])var8.next();
               URI id = item[0].getId();
               String version = item[0].getVersion();
               int status;
               PolicyMetaData metaData;
               if (item[0] instanceof Policy) {
                  status = this.getPolicyStatus(id, version);
                  metaData = this.getPolicyMetaDataEntry(id, version, true);
                  metaData = metaData != null && metaData.getValue() != null ? metaData : null;
                  this.deletePolicy(id, version);
                  if (this.getPolicyObject(item[1].getId(), item[1].getVersion(), "Policies") == null) {
                     this.addPolicy((Policy)item[1], status, metaData);
                  }
               } else {
                  status = this.getPolicySetStatus(id, version);
                  metaData = this.getPolicyMetaDataEntry(id, version, false);
                  metaData = metaData != null && metaData.getValue() != null ? metaData : null;
                  this.deletePolicySet(id, version);
                  if (this.getPolicyObject(item[1].getId(), item[1].getVersion(), "PolicySets") == null) {
                     this.addPolicySet((PolicySet)item[1], status, metaData);
                  }
               }
            }

            tx.commit();
            return;
         }
      } catch (Throwable var13) {
         tx.rollback();
         throw new PolicyStoreException(var13);
      } finally {
         manager.close();
      }
   }

   private XACMLEntry newPolicyObject(AbstractPolicy ap, boolean isPolicy) {
      String cn = ap.getId().toString();
      String typeName = isPolicy ? "Policies" : "PolicySets";
      String xacmlVersion = ap.getVersion();
      return this.newPolicyObject(typeName, cn, xacmlVersion);
   }

   private void setPolicyData(XACMLEntry pc, AbstractPolicy ap, PolicyMetaData md, int status) {
      try {
         pc.setXacmlStatus(String.valueOf(status));
         pc.setXacmlDocument(ap.toString().getBytes("UTF8"));
         if (md != null && md.getValue() != null) {
            pc.setWlsCollectionName(md.getIndexValue("wlsCollectionName"));
            pc.setWlsCreatorInfo(md.getIndexValue("wlsCreatorInfo"));
            pc.setWlsXmlFragment(md.getValue().getBytes("UTF8"));
         }

      } catch (UnsupportedEncodingException var6) {
         throw new RuntimeException(var6);
      }
   }

   private void addPolicy(AbstractPolicy ap, int status, PolicyMetaData md, boolean isPolicy) throws DocumentParseException, IOException, PolicyStoreException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering addPolicy(" + ap.getId() + ")");
      }

      XACMLEntry pc = this.newPolicyObject(ap, isPolicy);
      this.setPolicyData(pc, ap, md, status);
      this.addXacmlScope(pc, status, ap);
      String resourceScope = this.getXacmlResourceScope(pc);
      if (resourceScope == null) {
         resourceScope = "";
      }

      if (debug) {
         this.log.debug("Storing policy (set)");
      }

      pc = (XACMLEntry)this.makePersistent(pc);
      String roleScope = this.getXacmlRoleScope(pc);
      if (roleScope == null) {
         roleScope = "";
      }

      if (debug) {
         this.log.debug("Calling entryAdded to flush caches");
      }

      this.entryAdded(this.getPolicyObjectId(pc), ap, status, resourceScope, roleScope);
      if (debug) {
         this.log.debug("Exiting addPolicy()");
      }

   }

   private Object makePersistent(Object pc) throws PolicyStoreException {
      PersistenceManager manager = this.storeService.getPersistenceManager();
      Transaction tx = manager.currentTransaction();
      Object copy = null;

      try {
         tx.begin();

         try {
            copy = manager.makePersistent(pc);
         } catch (Throwable var13) {
            tx.rollback();
            throw var13;
         }

         tx.commit();
         tx.begin();

         try {
            copy = manager.detachCopy(copy);
         } catch (Throwable var12) {
            tx.rollback();
            throw var12;
         }

         tx.commit();
      } catch (JDOOptimisticVerificationException var14) {
         throw new ConcurrentModificationException(var14);
      } catch (Throwable var15) {
         throw new PolicyStoreException(var15);
      } finally {
         manager.close();
      }

      return copy;
   }

   public void setPolicy(Policy policy) throws DocumentParseException, PolicyStoreException {
      this.setPolicy(policy, 0);
   }

   public void setPolicy(Policy policy, int status) throws DocumentParseException, PolicyStoreException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.setPolicy(" + policy.getId() + "::" + policy.getVersion() + "," + status + ")");
      }

      this.setPolicy(policy, status, (PolicyMetaData)null, true);
   }

   public void setPolicy(Policy policy, int status, PolicyMetaData data) throws DocumentParseException, PolicyMetaDataException, PolicyStoreException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("BasePolicyStore: Entering PolicyMetaData setPolicy(" + policy.getId() + "::" + policy.getVersion() + "," + status + ")");
      }

      this.checkPolicyMetaData(data);
      this.setPolicy(policy, status, data, true);
   }

   private void setPolicy(AbstractPolicy ap, int status, PolicyMetaData md, boolean isPolicy) throws DocumentParseException, PolicyStoreException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering setPolicy(" + ap.getId() + ")");
      }

      String cn = ap.getId().toString();
      String typeName = isPolicy ? "Policies" : "PolicySets";
      String xacmlVersion = ap.getVersion();
      XACMLEntryId id = this.newPolicyObjectId(typeName, cn, xacmlVersion);
      XACMLEntry pc = null;
      PersistenceManager manager = this.storeService.getPersistenceManager();
      String resourceScope = null;
      String roleScope = null;
      boolean present = false;

      try {
         manager.currentTransaction().begin();

         try {
            pc = (XACMLEntry)this.getObjectById(manager, id);
            present = pc != null;
            if (!present) {
               pc = this.newPolicyObject(typeName, cn, xacmlVersion);
               this.addXacmlScope(pc, status, ap);
            } else {
               this.modifyXacmlScope(pc, status, ap);
            }

            resourceScope = this.getXacmlResourceScope(pc);
            roleScope = this.getXacmlRoleScope(pc);
            this.setPolicyData(pc, ap, md, status);
            if (!present) {
               manager.makePersistent(pc);
            }

            if (resourceScope == null) {
               resourceScope = "";
            }

            if (roleScope == null) {
               roleScope = "";
            }
         } catch (Throwable var71) {
            manager.currentTransaction().rollback();
            throw var71;
         }

         manager.currentTransaction().commit();
      } catch (JDOException var72) {
         JDOException e = var72;
         manager.currentTransaction().begin();
         boolean nowPresent = false;
         boolean updateCache = false;

         try {
            pc = (XACMLEntry)this.getObjectById(manager, id);
            nowPresent = pc != null;
         } catch (Throwable var69) {
            manager.currentTransaction().rollback();
            if (var72 instanceof JDOOptimisticVerificationException) {
               throw new ConcurrentModificationException(var72);
            }

            throw new PolicyStoreException(var69);
         }

         manager.currentTransaction().commit();
         boolean var60 = false;

         try {
            var60 = true;
            if (nowPresent != present) {
               updateCache = true;
               throw new ConcurrentModificationException(e);
            }

            if (e instanceof JDOOptimisticVerificationException) {
               updateCache = true;
               throw new ConcurrentModificationException(e);
            }

            if (present) {
               if (e instanceof JDOFatalDataStoreException) {
                  Throwable[] t = e.getNestedExceptions();
                  if (t != null) {
                     boolean fatal = false;

                     for(int i = 0; i < t.length; ++i) {
                        if (!(t[i] instanceof JDOObjectNotFoundException)) {
                           fatal = true;
                           break;
                        }
                     }

                     if (!fatal) {
                        if (this.log.isDebugEnabled()) {
                           this.log.debug("BasePolicyStore: Managed and amin server data are out of synch");
                        }

                        updateCache = true;
                        throw new ConcurrentModificationException(e);
                     }

                     var60 = false;
                  } else {
                     var60 = false;
                  }
               } else {
                  var60 = false;
               }
            } else {
               var60 = false;
            }
         } finally {
            if (var60) {
               if (updateCache) {
                  Entry e1;
                  if (pc != null) {
                     try {
                        ap = this.getAbstractPolicy(pc);
                     } catch (Exception var64) {
                     }

                     synchronized(this.allEntries) {
                        if (resourceScope == null) {
                           resourceScope = "";
                        }

                        if (roleScope == null) {
                           roleScope = "";
                        }

                        e1 = (Entry)this.allEntries.get(id);
                        if (e1 != null) {
                           if (debug) {
                              this.log.debug("Calling update() on cache entry");
                           }

                           e1.update(ap, status, resourceScope, roleScope);
                        } else {
                           this.entryAdded(id, ap, status, resourceScope, roleScope);
                        }
                     }
                  } else {
                     synchronized(this.allEntries) {
                        e1 = (Entry)this.allEntries.get(id);
                        if (e1 != null) {
                           if (debug) {
                              this.log.debug("BasePolicyStore: Calling entryRemoved() to update caches");
                           }

                           this.entryRemoved(e1);
                        }
                     }
                  }
               }

            }
         }

         if (updateCache) {
            Entry e1;
            if (pc != null) {
               try {
                  ap = this.getAbstractPolicy(pc);
               } catch (Exception var67) {
               }

               synchronized(this.allEntries) {
                  if (resourceScope == null) {
                     resourceScope = "";
                  }

                  if (roleScope == null) {
                     roleScope = "";
                  }

                  e1 = (Entry)this.allEntries.get(id);
                  if (e1 != null) {
                     if (debug) {
                        this.log.debug("Calling update() on cache entry");
                     }

                     e1.update(ap, status, resourceScope, roleScope);
                  } else {
                     this.entryAdded(id, ap, status, resourceScope, roleScope);
                  }
               }
            } else {
               synchronized(this.allEntries) {
                  e1 = (Entry)this.allEntries.get(id);
                  if (e1 != null) {
                     if (debug) {
                        this.log.debug("BasePolicyStore: Calling entryRemoved() to update caches");
                     }

                     this.entryRemoved(e1);
                  }
               }
            }
         }

         throw new PolicyStoreException(var72);
      } catch (Throwable var73) {
         throw new PolicyStoreException(var73);
      } finally {
         try {
            manager.close();
         } catch (Exception var61) {
            this.log.error("Exception " + var61.getClass().getName() + " occur while persistent manager closes in setPolicy: ");
            this.log.error(var61.getMessage() + " ", var61);
         }

      }

      synchronized(this.allEntries) {
         Entry e = (Entry)this.allEntries.get(id);
         if (e != null) {
            if (debug) {
               this.log.debug("Calling update() on cache entry");
            }

            e.update(ap, status, resourceScope, roleScope);
         } else {
            this.entryAdded(id, ap, status, resourceScope, roleScope);
         }
      }

      if (debug) {
         this.log.debug("BasePolicyStore: Exiting setPolicy(" + ap.getId() + ")");
      }

   }

   public PolicyMetaData getPolicyMetaDataEntry(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("BasePolicyStore: Entering getPolicyMetaDataEntry(" + identifier + "," + version + ")");
      }

      return this.getPolicyMetaDataEntry(identifier, version, true);
   }

   public void setPolicySet(PolicySet set) throws DocumentParseException, PolicyStoreException {
      this.setPolicySet(set, 0);
   }

   public void setPolicySet(PolicySet set, int status) throws DocumentParseException, PolicyStoreException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.setPolicySet(" + set.getId() + "::" + set.getVersion() + "," + status + ")");
      }

      this.setPolicy(set, status, (PolicyMetaData)null, false);
   }

   public void setPolicySet(PolicySet set, int status, PolicyMetaData data) throws DocumentParseException, PolicyMetaDataException, PolicyStoreException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("BasePolicyStore: Entering PolicyMetaData setPolicySet(" + set.getId() + "::" + set.getVersion() + "," + status + ")");
      }

      this.checkPolicyMetaData(data);
      this.setPolicy(set, status, data, false);
   }

   public PolicyMetaData getPolicySetMetaDataEntry(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("BasePolicyStore: Entering getPolicySetMetaDataEntry(" + identifier + "," + version + ")");
      }

      return this.getPolicyMetaDataEntry(identifier, version, false);
   }

   public void setPolicyStatus(URI identifier, String version, int status) throws DocumentParseException, PolicyStoreException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.setPolicyStatus(" + identifier + "::" + version + "," + status + ")");
      }

      String cn = identifier.toString();
      XACMLEntryId id = this.newPolicyObjectId("Policies", cn, version);
      XACMLEntry pc = null;
      PersistenceManager manager = this.storeService.getPersistenceManager();

      try {
         manager.currentTransaction().begin();

         try {
            pc = (XACMLEntry)id.getObject(manager);
            pc.setXacmlStatus(String.valueOf(status));
         } catch (Throwable var17) {
            manager.currentTransaction().rollback();
            throw var17;
         }

         manager.currentTransaction().commit();
      } catch (Throwable var18) {
         if (debug) {
            this.log.debug("BasePolicyStore: Error while updating policy status", var18);
         }

         throw new PolicyStoreException(var18);
      } finally {
         manager.close();
      }

      synchronized(this.allEntries) {
         Entry e = (Entry)this.allEntries.get(id);
         if (e != null) {
            if (debug) {
               this.log.debug("Calling update() on cache entry");
            }

            e.update((AbstractPolicy)null, status, (String)null, (String)null);
         } else if (debug) {
            this.log.debug("Cache entry to update not found!!!: " + id);
         }

      }
   }

   public int getPolicyStatus(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.getPolicyStatus(" + identifier + "::" + version + ")");
      }

      XACMLEntry pc = this.getPolicyObject(identifier, version, "Policies");
      if (pc == null) {
         if (debug) {
            this.log.debug("BasePolicyStore: policy not found");
         }

         throw new PolicyStoreException("Policy not found");
      } else {
         try {
            int r = Integer.parseInt(pc.getXacmlStatus());
            if (debug) {
               this.log.debug("BasePolicyStore: result: " + r);
            }

            return r;
         } catch (NumberFormatException var6) {
            throw new PolicyStoreException(var6);
         }
      }
   }

   public void setPolicySetStatus(URI identifier, String version, int status) throws PolicyStoreException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.setPolicySetStatus(" + identifier + "::" + version + "," + status + ")");
      }

      String cn = identifier.toString();
      XACMLEntryId id = this.newPolicyObjectId("PolicySets", cn, version);
      XACMLEntry pc = null;
      PersistenceManager manager = this.storeService.getPersistenceManager();

      try {
         manager.currentTransaction().begin();

         try {
            pc = (XACMLEntry)id.getObject(manager);
            pc.setXacmlStatus(String.valueOf(status));
         } catch (Throwable var17) {
            manager.currentTransaction().rollback();
            throw var17;
         }

         manager.currentTransaction().commit();
      } catch (Throwable var18) {
         if (debug) {
            this.log.debug("BasePolicyStore: Error while updating policy set status", var18);
         }

         throw new PolicyStoreException(var18);
      } finally {
         manager.close();
      }

      synchronized(this.allEntries) {
         Entry e = (Entry)this.allEntries.get(id);
         if (e != null) {
            if (debug) {
               this.log.debug("Calling update() on cache entry");
            }

            e.update((AbstractPolicy)null, status, (String)null, (String)null);
         } else if (debug) {
            this.log.debug("Cache entry to update not found!!!: " + id);
         }

      }
   }

   public int getPolicySetStatus(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.getPolicySetStatus(" + identifier + "::" + version + ")");
      }

      XACMLEntry pc = this.getPolicyObject(identifier, version, "PolicySets");
      if (pc == null) {
         if (debug) {
            this.log.debug("BasePolicyStore: policy set not found");
         }

         throw new PolicyStoreException("Policy set not found");
      } else {
         try {
            int r = Integer.parseInt(pc.getXacmlStatus());
            if (debug) {
               this.log.debug("BasePolicyStore: result: " + r);
            }

            return r;
         } catch (NumberFormatException var6) {
            throw new PolicyStoreException(var6);
         }
      }
   }

   public boolean deletePolicy(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.deletePolicy(" + identifier + "::" + version + ",)");
      }

      String cn = identifier.toString();
      XACMLEntryId id = this.newPolicyObjectId("Policies", cn, version);
      XACMLEntry pc = null;
      PersistenceManager manager = this.storeService.getPersistenceManager();

      try {
         manager.currentTransaction().begin();
         pc = (XACMLEntry)this.getObjectById(manager, id);
         if (pc == null) {
            if (debug) {
               this.log.debug("BasePolicyStore: Exiting BasePolicyStore.deletePolicy() -- no delete needed");
            }

            manager.currentTransaction().rollback();
            boolean var8 = false;
            return var8;
         }

         try {
            manager.deletePersistent(pc);
         } catch (Exception var15) {
            manager.currentTransaction().rollback();
            throw var15;
         }

         manager.currentTransaction().commit();
      } catch (Throwable var17) {
         if (debug) {
            this.log.debug("BasePolicyStore: Error while deleting policy", var17);
         }

         throw new PolicyStoreException(var17);
      } finally {
         manager.close();
      }

      synchronized(this.allEntries) {
         Entry e = (Entry)this.allEntries.get(id);
         if (e != null) {
            if (debug) {
               this.log.debug("BasePolicyStore: Calling entryRemoved() to update caches");
            }

            this.entryRemoved(e);
         }
      }

      if (debug) {
         this.log.debug("BasePolicyStore: Exiting BasePolicyStore.deletePolicy()");
      }

      return true;
   }

   public boolean deletePolicySet(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: Entering BasePolicyStore.deletePolicySet(" + identifier + "::" + version + ",)");
      }

      String cn = identifier.toString();
      XACMLEntryId id = this.newPolicyObjectId("PolicySets", cn, version);
      XACMLEntry pc = null;
      PersistenceManager manager = this.storeService.getPersistenceManager();

      try {
         manager.currentTransaction().begin();
         pc = (XACMLEntry)this.getObjectById(manager, id);
         if (pc == null) {
            if (debug) {
               this.log.debug("BasePolicyStore: Exiting BasePolicyStore.deletePolicySet() -- no delete needed");
            }

            manager.currentTransaction().rollback();
            boolean var8 = false;
            return var8;
         }

         try {
            manager.deletePersistent(pc);
         } catch (Throwable var15) {
            manager.currentTransaction().rollback();
            throw var15;
         }

         manager.currentTransaction().commit();
      } catch (Throwable var17) {
         if (debug) {
            this.log.debug("BasePolicyStore: Error while deleting policy set", var17);
         }

         throw new PolicyStoreException(var17);
      } finally {
         manager.close();
      }

      synchronized(this.allEntries) {
         Entry e = (Entry)this.allEntries.get(id);
         if (e != null) {
            if (debug) {
               this.log.debug("BasePolicyStore: Calling entryRemoved() to update caches");
            }

            this.entryRemoved(e);
         }
      }

      if (debug) {
         this.log.debug("BasePolicyStore: Exiting BasePolicyStore.deletePolicySet()");
      }

      return true;
   }

   public List readPolicy(PolicyMetaData data) throws DocumentParseException, URISyntaxException, PolicyStoreException, PolicyMetaDataException {
      if (data == null) {
         return null;
      } else {
         this.checkPolicyMetaData(data);
         boolean debug = this.log.isDebugEnabled();
         if (debug) {
            this.log.debug("BasePolicyStore: Entering PolicyMetaData readPolicy(" + data.getElementName() + ")");
         }

         List list = new LinkedList();
         this.readPolicy(data, list, true);
         if (debug) {
            if (!list.isEmpty()) {
               this.log.debug("BasePolicyStore: PolicyMetaData readPolicy(): Completed");
            } else {
               this.log.debug("BasePolicyStore: PolicyMetaData readPolicy(): No results found");
            }
         }

         return list;
      }
   }

   public List readPolicySet(PolicyMetaData data) throws DocumentParseException, URISyntaxException, PolicyStoreException, PolicyMetaDataException {
      if (data == null) {
         return null;
      } else {
         this.checkPolicyMetaData(data);
         if (this.log.isDebugEnabled()) {
            this.log.debug("BasePolicyStore: Entering PolicyMetaData readPolicySet(" + data.getElementName() + ")");
         }

         List list = new LinkedList();
         this.readPolicy(data, list, false);
         if (this.log.isDebugEnabled()) {
            if (!list.isEmpty()) {
               this.log.debug("BasePolicyStore: PolicyMetaData readPolicySet(): Completed");
            } else {
               this.log.debug("BasePolicyStore: PolicyMetaData readPolicySet(): No results found");
            }
         }

         return list;
      }
   }

   private void readPolicy(PolicyMetaData data, List list, boolean isPolicy) throws DocumentParseException, URISyntaxException, PolicyStoreException, PolicyMetaDataException {
      String typeName = isPolicy ? "Policies" : "PolicySets";
      String filter = "this.domainName == domainName && this.realmName == realmName && this.typeName == typeName";
      String declarations = "String domainName, String realmName, String typeName";
      ArrayList parameters = new ArrayList();
      parameters.add(this.domainName);
      parameters.add(this.realmName);
      parameters.add(typeName);
      String variables = null;
      String cn = data.getIndexValue("policyId");
      if (cn != null) {
         filter = filter + " && this.cn.matches(cn)";
         declarations = declarations + ", String cn";
         parameters.add(Utils.convertLDAPPatternForJDO(cn, this.storeService));
      }

      String scope = data.getIndexValue("resourceScope");
      if (scope != null) {
         filter = filter + " && this.xacmlResourceScope.contains(xrs) && xrs.matches(xacmlResourceScope)";
         declarations = declarations + ", String xacmlResourceScope";
         parameters.add(Utils.convertLDAPPatternForJDO(scope, this.storeService));
         variables = "String xrs";
      }

      String creator = data.getIndexValue("wlsCreatorInfo");
      if (creator != null) {
         filter = filter + " && this.wlsCreatorInfo == wlsCreatorInfo";
         declarations = declarations + ", String wlsCreatorInfo";
         parameters.add(creator);
      }

      String cName = data.getIndexValue("wlsCollectionName");
      if (cName != null) {
         filter = filter + " && this.wlsCollectionName == wlsCollectionName";
         declarations = declarations + ", String wlsCollectionName";
         parameters.add(cName);
      }

      String beforeTime = data.getIndexValue("modifyTimestamp");
      if (beforeTime != null) {
         filter = filter + " && ( ( this.createTimestamp < beforeDate && this.modifyTimestamp == null ) || this.modifyTimestamp < beforeDate )";
         declarations = declarations + ", java.util.Date beforeDate";
         parameters.add(new Date(Long.parseLong(beforeTime)));
      }

      boolean debug = this.log.isDebugEnabled();
      if (debug) {
         this.log.debug("BasePolicyStore: PolicyMetaData readPolicy() filter: " + filter);
      }

      PersistenceManager manager = this.storeService.getPersistenceManager();
      Query query = null;

      try {
         query = manager.newQuery(this.getPolicyObjectClass(), filter);
         query.declareParameters(declarations);
         if (variables != null && variables.length() > 0) {
            query.declareVariables(variables);
         }

         Collection result = (Collection)query.executeWithArray(parameters.toArray());
         Iterator var18 = result.iterator();

         while(var18.hasNext()) {
            Object pc = var18.next();
            if (debug) {
               this.log.debug("BasePolicyStore: PolicyMetaData readPolicy(): " + pc);
            }

            XACMLEntry policy = (XACMLEntry)pc;
            AbstractPolicy p = this.getAbstractPolicy(policy);
            if (p != null) {
               PolicyMetaData md = this.getPolicyMetaData(policy);
               list.add(new PolicyInfoImpl(p, md));
            }
         }
      } finally {
         if (query != null) {
            query.closeAll();
         }

         manager.close();
      }

   }

   private AbstractPolicy getAbstractPolicy(XACMLEntry pc) throws URISyntaxException, DocumentParseException, IOException {
      AbstractPolicy ap = null;
      byte[] policyDoc = pc.getXacmlDocument();
      if (policyDoc != null) {
         ap = PolicyUtils.read(this.registry, (InputStream)(new ByteArrayInputStream(policyDoc)), this.jaxpService.newDocumentBuilderFactory());
      }

      return ap;
   }

   private PolicyMetaData getPolicyMetaData(XACMLEntry pc) {
      PolicyMetaData md = null;
      String mdValue = null;
      byte[] metadata = pc.getWlsXmlFragment();
      if (metadata != null) {
         try {
            mdValue = new String(metadata, "UTF8");
         } catch (UnsupportedEncodingException var8) {
            throw new RuntimeException(var8);
         }
      }

      if (mdValue != null) {
         Map mdIndex = new HashMap();
         String mdCreator = pc.getWlsCreatorInfo();
         if (mdCreator != null) {
            mdIndex.put("wlsCreatorInfo", mdCreator);
         }

         String mdCol = pc.getWlsCollectionName();
         if (mdCol != null) {
            mdIndex.put("wlsCollectionName", mdCol);
         }

         md = new PolicyMetaDataImpl(this.getMetaDataElementName(), mdValue, mdIndex);
      }

      return md;
   }

   private void checkPolicyMetaData(PolicyMetaData data) throws PolicyMetaDataException {
      if (data != null && !this.getMetaDataElementName().equals(data.getElementName())) {
         throw new PolicyMetaDataException("Unknown Policy MetaData Element: " + data.getElementName());
      }
   }

   private PolicyMetaData getPolicyMetaDataEntry(URI identifier, String version, boolean isPolicy) throws PolicyStoreException, URISyntaxException {
      boolean debug = this.log.isDebugEnabled();
      String typeName = isPolicy ? "Policies" : "PolicySets";
      XACMLEntry pc = this.getPolicyObject(identifier, version, typeName);
      if (pc == null) {
         if (debug) {
            this.log.debug("BasePolicyStore: " + typeName + " not found");
         }

         return null;
      } else {
         PolicyMetaData pmd = this.getPolicyMetaData(pc);
         if (pmd == null) {
            pmd = new PolicyMetaDataImpl((String)null, (String)null, new HashMap());
         }

         return (PolicyMetaData)pmd;
      }
   }

   public PolicyFinder getFinder() {
      return this;
   }

   public boolean isTransactionSupported() {
      return false;
   }

   public void commit() throws PolicyStoreException {
   }

   public void rollback() throws PolicyStoreException {
   }

   public boolean getAutoCommit() throws PolicyStoreException {
      return false;
   }

   public void setAutoCommit(boolean autoCommit) throws PolicyStoreException {
      throw new UnsupportedOperationException();
   }

   protected Collection calculateKnownMatch(Resource r) {
      return this.rmu.generateKnownMatch(r);
   }

   protected Record generateRecord(PolicySetMember psm, Collection kmc) throws DocumentParseException, IOException, URISyntaxException {
      IdReference ref;
      AbstractPolicy data;
      if (psm instanceof Entry) {
         ref = ((Entry)psm).getIdReference();
         data = ((Entry)psm).getData();
      } else if (psm instanceof IdReference) {
         ref = (IdReference)psm;
         data = null;
      } else {
         ref = ((AbstractPolicy)psm).getReference();
         data = (AbstractPolicy)psm;
      }

      return (Record)(ref instanceof PolicyIdReference ? new PolicyRecord(ref.getReference(), ref.getVersion(), this, (Policy)data, kmc) : new PolicySetRecord(ref.getReference(), ref.getVersion(), this, (PolicySet)data, kmc));
   }

   public Set getAllPolicies() throws DocumentParseException {
      try {
         Set ps = new HashSet();
         Iterator var2 = this.allEntries.values().iterator();

         while(var2.hasNext()) {
            Entry e = (Entry)var2.next();
            if (e.isPolicy()) {
               ps.add(e.getRecord());
            }
         }

         return ps;
      } catch (URISyntaxException var4) {
         throw new DocumentParseException(var4);
      } catch (IOException var5) {
         throw new DocumentParseException(var5);
      }
   }

   public Set getAllPolicySets() throws DocumentParseException {
      try {
         Set ps = new HashSet();
         Iterator var2 = this.allEntries.values().iterator();

         while(var2.hasNext()) {
            Entry e = (Entry)var2.next();
            if (!e.isPolicy()) {
               ps.add(e.getRecord());
            }
         }

         return ps;
      } catch (URISyntaxException var4) {
         throw new DocumentParseException(var4);
      } catch (IOException var5) {
         throw new DocumentParseException(var5);
      }
   }

   public AbstractPolicy find(IdReference reference) throws DocumentParseException {
      try {
         Entry p = null;
         boolean refPol = reference instanceof PolicyIdReference;
         Iterator var4 = this.allEntries.values().iterator();

         while(true) {
            Entry e;
            String eirv;
            do {
               IdReference eir;
               do {
                  do {
                     do {
                        if (!var4.hasNext()) {
                           return p != null ? p.getData() : null;
                        }

                        e = (Entry)var4.next();
                     } while(e.isPolicy() != refPol);

                     eir = e.getIdReference();
                     eirv = eir.getVersion();
                  } while(!eir.getReference().equals(reference.getReference()));
               } while(!ConstraintUtil.meetsConstraint(eirv, reference));
            } while(p != null && !ConstraintUtil.isLater(eirv, p.getIdReference().getVersion()));

            p = e;
         }
      } catch (URISyntaxException var8) {
         throw new DocumentParseException(var8);
      } catch (IOException var9) {
         throw new DocumentParseException(var9);
      }
   }

   public AbstractPolicy find(IdReference reference, Iterator otherFinders) throws DocumentParseException {
      return this.find(reference);
   }

   protected abstract void entryAdded(XACMLEntryId var1, AbstractPolicy var2, int var3, String var4, String var5);

   protected abstract void entryRemoved(Entry var1);

   protected abstract boolean isPreEssexGlobalPolicy(Object var1);

   protected class Entry implements PolicySetMember {
      private XACMLEntryId id;
      private IdReference idReference;
      private int status;
      private AbstractPolicy data;
      private Record record;
      private InputStream unparsedData;
      private boolean lazyLoadUnparsedData;
      private boolean isPolicy;

      public Entry(XACMLEntryId id, AbstractPolicy data, int status) {
         this.id = id;
         this.data = data;
         this.status = status;
         this.idReference = data.getReference();
         this.isPolicy = this.idReference instanceof PolicyIdReference;
      }

      public Entry(XACMLEntry pc, boolean lazyLoadUnparsedData) throws URISyntaxException {
         this.id = BasePolicyStore.this.getPolicyObjectId(pc);
         this.data = null;
         this.unparsedData = null;
         byte[] xacmlDocument = pc.getXacmlDocument();
         if (xacmlDocument != null && xacmlDocument.length > 0) {
            if (lazyLoadUnparsedData) {
               this.lazyLoadUnparsedData = lazyLoadUnparsedData;
            } else {
               this.unparsedData = new ByteArrayInputStream(xacmlDocument);
            }
         }

         this.status = Integer.parseInt(pc.getXacmlStatus());
         this.isPolicy = "Policies".equals(pc.getTypeName());

         try {
            String cn = pc.getCn();
            String version = pc.getXacmlVersion();
            URI identifier = new URI(cn);
            this.idReference = (IdReference)(this.isPolicy ? new PolicyIdReference(identifier, version) : new PolicySetIdReference(identifier, version));
         } catch (java.net.URISyntaxException var8) {
            throw new URISyntaxException(var8);
         }
      }

      public Record getRecord() throws DocumentParseException, IOException, URISyntaxException {
         if (this.record == null) {
            AbstractPolicy d = this.getData();
            IdReference ir = d.getReference();
            this.record = (Record)(d instanceof Policy ? new PolicyRecord(ir.getReference(), ir.getVersion(), BasePolicyStore.this, (Policy)d) : new PolicySetRecord(ir.getReference(), ir.getVersion(), BasePolicyStore.this, (PolicySet)d));
         }

         return this.record;
      }

      public boolean isPolicy() {
         return this.isPolicy;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof Entry)) {
            return false;
         } else {
            Entry o = (Entry)other;
            return this.id == o.id || this.id != null && this.id.equals(o.id);
         }
      }

      public int hashCode() {
         return this.id.hashCode();
      }

      public int getStatus() {
         return this.status;
      }

      public IdReference getIdReference() {
         return this.idReference;
      }

      public XACMLEntryId getID() {
         return this.id;
      }

      public AbstractPolicy getData() throws DocumentParseException, IOException, URISyntaxException {
         synchronized(this) {
            if (this.data == null) {
               if (this.lazyLoadUnparsedData) {
                  try {
                     byte[] entryData = this.loadXACMLEntryData();
                     if (entryData != null && entryData.length > 0) {
                        this.unparsedData = new ByteArrayInputStream(entryData);
                     }
                  } catch (PolicyStoreException var4) {
                     if (BasePolicyStore.this.log.isDebugEnabled()) {
                        BasePolicyStore.this.log.debug("BasePolicyStore: getData()", var4);
                     }

                     throw new DocumentParseException(var4);
                  }

                  this.lazyLoadUnparsedData = false;
               }

               if (this.unparsedData != null) {
                  this.data = PolicyUtils.read(BasePolicyStore.this.registry, this.unparsedData, BasePolicyStore.this.jaxpService.newDocumentBuilderFactory());
                  this.idReference = this.data.getReference();
                  this.unparsedData = null;
               }
            }

            return this.data;
         }
      }

      protected void update(AbstractPolicy data, int status, String ignore, String alsoIgnore) {
         if (data != null) {
            this.data = data;
            this.idReference = data.getReference();
            this.record = null;
         }

         this.status = status;
      }

      protected byte[] loadXACMLEntryData() throws PolicyStoreException {
         return null;
      }
   }
}

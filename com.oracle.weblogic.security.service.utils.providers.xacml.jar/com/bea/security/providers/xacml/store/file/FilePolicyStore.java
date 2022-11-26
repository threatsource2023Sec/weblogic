package com.bea.security.providers.xacml.store.file;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.JAXPFactoryService;
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
import com.bea.common.security.xacml.policy.Target;
import com.bea.security.providers.xacml.entitlement.EntitlementAwarePolicyStore;
import com.bea.security.providers.xacml.store.ApplicableAuthorizationPolicyFinder;
import com.bea.security.providers.xacml.store.ApplicableRoleAssignmentPolicyFinder;
import com.bea.security.providers.xacml.store.MetaDataPolicyStore;
import com.bea.security.providers.xacml.store.PolicyInfoImpl;
import com.bea.security.providers.xacml.store.PolicyMetaDataImpl;
import com.bea.security.utils.lock.ReadWriteLock;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.InvalidRoleAssignmentPolicyException;
import com.bea.security.xacml.PolicyMetaData;
import com.bea.security.xacml.PolicyMetaDataException;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.cache.role.RoleMatchUtil;
import com.bea.security.xacml.store.PolicyFinder;
import com.bea.security.xacml.store.PolicyStore;
import com.bea.security.xacml.store.Record;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FilePolicyStore implements PolicyStore, MetaDataPolicyStore, ApplicableAuthorizationPolicyFinder, ApplicableRoleAssignmentPolicyFinder, EntitlementAwarePolicyStore {
   private static final int DEFAULT_CACHE_SIZE = 5000;
   private static final String WLS_ROLE_INFO = "WLSRoleInfo";
   private static final String WLS_POLICY_INFO = "WLSPolicyInfo";
   private static final String ROLE_COLLECTION_FILE_NAME = "roleCollectionInfo.xml";
   private static final String POLICY_COLLECTION_FILE_NAME = "policyCollectionInfo.xml";
   protected File store;
   protected int storeType;
   private Index index;
   protected WlsCollectionStore wlsCollectionStore;
   private CachedPolicyFinder atzPolicyFinder;
   private CachedPolicyFinder rolePolicyFinder;
   private ReadWriteLock lock;
   private LoggerSpi logger;
   private boolean debugEnabled;
   private RoleMatchUtil omu;

   public FilePolicyStore(AttributeRegistry aRegistry, String store, JAXPFactoryService jaxpService) throws PolicyStoreException, DocumentParseException, URISyntaxException {
      this(aRegistry, new File(store), jaxpService);
   }

   public FilePolicyStore(AttributeRegistry aRegistry, File store, JAXPFactoryService jaxpService) throws PolicyStoreException, DocumentParseException, URISyntaxException {
      this.storeType = -1;
      this.debugEnabled = false;
      if (!store.exists()) {
         store.mkdirs();
      }

      this.store = store;
      this.lock = new ReadWriteLock();
      this.index = new Index(this, aRegistry, store, jaxpService, new PlainFileAccess());
      this.atzPolicyFinder = new AuthorizationPolicyFinder(this, this.logger, 5000);
      this.rolePolicyFinder = new RolePolicyFinder(this, this.logger, 5000);
      this.omu = new RoleMatchUtil();
      this.load();
   }

   protected FilePolicyStore(AttributeRegistry aRegistry, File store, JAXPFactoryService jaxpService, FileAccess fileAccess, int cacheSize, char[] password, String algorithm, int storeType, LoggerSpi logger) throws PolicyStoreException, DocumentParseException, URISyntaxException {
      this.storeType = -1;
      this.debugEnabled = false;
      this.logger = logger;
      this.storeType = storeType;
      this.debugEnabled = logger != null && logger.isDebugEnabled();
      if (!store.exists()) {
         store.mkdirs();
      }

      this.store = store;
      this.lock = new ReadWriteLock();
      this.index = new Index(this, aRegistry, store, jaxpService, fileAccess);
      String collectionStoreFileName = storeType == 0 ? "policyCollectionInfo.xml" : "roleCollectionInfo.xml";
      String pciKey = storeType == 0 ? "PolicyCollectionInfo#" : "RoleCollectionInfo#";
      this.wlsCollectionStore = new WlsCollectionStore(store, password, algorithm, jaxpService, collectionStoreFileName, pciKey);
      if (cacheSize <= 0) {
         cacheSize = 5000;
      }

      this.atzPolicyFinder = new AuthorizationPolicyFinder(this, logger, cacheSize);
      this.rolePolicyFinder = new RolePolicyFinder(this, logger, cacheSize);
      this.omu = new RoleMatchUtil();
      this.load();
   }

   public PolicyFinder getFinder() {
      return this;
   }

   public Set readAllPolicies() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      HashSet result;
      try {
         this.lock.readLock();
         Set entries = this.index.getAllPolicies();
         if (entries != null) {
            result = new HashSet();
            Iterator var3 = entries.iterator();

            while(var3.hasNext()) {
               Index.Entry e = (Index.Entry)var3.next();
               result.add((Policy)e.getData());
            }

            HashSet var8 = result;
            return var8;
         }

         result = null;
      } finally {
         this.lock.readUnLock();
      }

      return result;
   }

   public Set readAllPolicySets() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      try {
         this.lock.readLock();
         Set entries = this.index.getAllPolicySets();
         HashSet result;
         if (entries == null) {
            result = null;
            return result;
         } else {
            result = new HashSet();
            Iterator var3 = entries.iterator();

            while(var3.hasNext()) {
               Index.Entry e = (Index.Entry)var3.next();
               result.add((PolicySet)e.getData());
            }

            HashSet var8 = result;
            return var8;
         }
      } finally {
         this.lock.readUnLock();
      }
   }

   public boolean hasPolicy(URI identifier, String version) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      boolean var4;
      try {
         this.lock.readLock();
         Index.Entry e = this.index.getPolicyForIdVersion(identifier, version);
         var4 = e != null;
      } finally {
         this.lock.readUnLock();
      }

      return var4;
   }

   public boolean hasPolicySet(URI identifier, String version) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      boolean var4;
      try {
         this.lock.readLock();
         Index.Entry e = this.index.getPolicySetForIdVersion(identifier, version);
         var4 = e != null;
      } finally {
         this.lock.readUnLock();
      }

      return var4;
   }

   public Policy readPolicy(URI identifier, String version) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      Policy var4;
      try {
         this.lock.readLock();
         Index.Entry e = this.index.getPolicyForIdVersion(identifier, version);
         var4 = e != null ? (Policy)e.getData() : null;
      } finally {
         this.lock.readUnLock();
      }

      return var4;
   }

   public PolicySet readPolicySet(URI identifier, String version) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      PolicySet var4;
      try {
         this.lock.readLock();
         Index.Entry e = this.index.getPolicySetForIdVersion(identifier, version);
         var4 = e != null ? (PolicySet)e.getData() : null;
      } finally {
         this.lock.readUnLock();
      }

      return var4;
   }

   public void addPolicy(Policy policy) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      this.addPolicy(policy, 0);
   }

   public void addPolicy(Policy policy, int status) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      this.addPolicy(policy, status, (PolicyMetaData)null);
   }

   public void addPolicySet(PolicySet set) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      this.addPolicySet(set, 0);
   }

   public void addPolicySet(PolicySet set, int status) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      this.addPolicySet(set, status, (PolicyMetaData)null);
   }

   public void setPolicy(Policy policy) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      this.setPolicy(policy, -1);
   }

   public void setPolicy(Policy policy, int status) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      this.setPolicy(policy, status, (PolicyMetaData)null);
   }

   public void setPolicySet(PolicySet set) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      this.setPolicySet(set, -1);
   }

   public void setPolicySet(PolicySet set, int status) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      this.setPolicySet(set, status, (PolicyMetaData)null);
   }

   public void setPolicyStatus(URI identifier, String version, int status) throws PolicyStoreException, URISyntaxException {
      if (this.store.isDirectory()) {
         try {
            this.lock.writeLock();
            Index.Entry e = this.index.getPolicyForIdVersion(identifier, version);
            if (e == null) {
               throw new PolicyStoreException("Policy not found");
            }

            if (e.getStatus() != status) {
               e.setStatus(status);
               this.reload();
            }
         } catch (DocumentParseException var8) {
            throw new PolicyStoreException(var8);
         } finally {
            this.lock.writeUnLock();
         }

      } else {
         throw new PolicyStoreException("Policy store not modifiable");
      }
   }

   public int getPolicyStatus(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      int var4;
      try {
         this.lock.readLock();
         Index.Entry e = this.index.getPolicyForIdVersion(identifier, version);
         if (e == null) {
            throw new PolicyStoreException("Policy not found");
         }

         var4 = e.getStatus();
      } catch (DocumentParseException var8) {
         throw new PolicyStoreException(var8);
      } finally {
         this.lock.readUnLock();
      }

      return var4;
   }

   public void setPolicySetStatus(URI identifier, String version, int status) throws PolicyStoreException, URISyntaxException {
      if (this.store.isDirectory()) {
         try {
            this.lock.writeLock();
            Index.Entry e = this.index.getPolicySetForIdVersion(identifier, version);
            if (e == null) {
               throw new PolicyStoreException("Policy set not found");
            }

            if (e.getStatus() != status) {
               e.setStatus(status);
               this.reload();
            }
         } catch (DocumentParseException var8) {
            throw new PolicyStoreException(var8);
         } finally {
            this.lock.writeUnLock();
         }

      } else {
         throw new PolicyStoreException("Policy store not modifiable");
      }
   }

   public int getPolicySetStatus(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      int var4;
      try {
         this.lock.readLock();
         Index.Entry e = this.index.getPolicySetForIdVersion(identifier, version);
         if (e == null) {
            throw new PolicyStoreException("Policy set not found");
         }

         var4 = e.getStatus();
      } catch (DocumentParseException var8) {
         throw new PolicyStoreException(var8);
      } finally {
         this.lock.readUnLock();
      }

      return var4;
   }

   public boolean deletePolicy(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      if (!this.store.isDirectory()) {
         throw new PolicyStoreException("Policy store not modifiable");
      } else {
         boolean var5;
         try {
            this.lock.writeLock();
            Index.Entry e = this.index.getPolicyForIdVersion(identifier, version);
            boolean result;
            if (e == null) {
               result = false;
               return result;
            }

            result = this.index.remove(e);
            if (result) {
               this.reload();
            }

            var5 = result;
         } catch (DocumentParseException var9) {
            throw new PolicyStoreException(var9);
         } finally {
            this.lock.writeUnLock();
         }

         return var5;
      }
   }

   public boolean deletePolicySet(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      if (!this.store.isDirectory()) {
         throw new PolicyStoreException("Policy store not modifiable");
      } else {
         boolean var5;
         try {
            this.lock.writeLock();
            Index.Entry e = this.index.getPolicySetForIdVersion(identifier, version);
            boolean result;
            if (e == null) {
               result = false;
               return result;
            }

            result = this.index.remove(e);
            if (result) {
               this.reload();
            }

            var5 = result;
         } catch (DocumentParseException var9) {
            throw new PolicyStoreException(var9);
         } finally {
            this.lock.writeUnLock();
         }

         return var5;
      }
   }

   public boolean isTransactionSupported() {
      return false;
   }

   public void commit() throws PolicyStoreException {
      throw new UnsupportedOperationException();
   }

   public void rollback() throws PolicyStoreException {
      throw new UnsupportedOperationException();
   }

   public boolean getAutoCommit() throws PolicyStoreException {
      return false;
   }

   public void setAutoCommit(boolean autoCommit) throws PolicyStoreException {
      throw new UnsupportedOperationException();
   }

   public Set getAllPolicies() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      HashSet var7;
      try {
         this.lock.readLock();
         Iterator it = this.index.iterator();
         Set l = new HashSet();

         while(it.hasNext()) {
            Record rec = ((Index.Entry)it.next()).getRecord();
            if (rec.getReference() instanceof PolicyIdReference) {
               l.add(rec);
            }
         }

         var7 = !l.isEmpty() ? l : null;
      } finally {
         this.lock.readUnLock();
      }

      return var7;
   }

   public Set getAllPolicySets() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      HashSet var7;
      try {
         this.lock.readLock();
         Iterator it = this.index.iterator();
         Set l = new HashSet();

         while(it.hasNext()) {
            Record rec = ((Index.Entry)it.next()).getRecord();
            if (rec.getReference() instanceof PolicySetIdReference) {
               l.add(rec);
            }
         }

         var7 = !l.isEmpty() ? l : null;
      } finally {
         this.lock.readUnLock();
      }

      return var7;
   }

   public AbstractPolicy find(IdReference idReference, Iterator otherFinders) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.find(idReference);
   }

   public AbstractPolicy find(IdReference idReference) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      Index.Entry e = this.index.findEntry(idReference);
      return e != null ? e.getData() : null;
   }

   public Set findAuthorizationPolicy(EvaluationCtx context) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return (Set)this.atzPolicyFinder.findPolicy(context);
   }

   public Map findRoleAssignmentPolicy(EvaluationCtx context) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return (Map)this.rolePolicyFinder.findPolicy(context);
   }

   public void updatePolicies(Set updatePolicies) throws PolicyStoreException {
      try {
         Iterator var6 = updatePolicies.iterator();

         while(var6.hasNext()) {
            AbstractPolicy[] item = (AbstractPolicy[])var6.next();
            URI id = item[0].getId();
            String version = item[0].getVersion();
            int status;
            PolicyMetaData metaData;
            if (item[0] instanceof Policy) {
               status = this.getPolicyStatus(id, version);
               metaData = this.getPolicyMetaDataEntry(id, version);
               this.deletePolicy(id, version);
               this.addPolicy((Policy)item[1], status, metaData);
            } else {
               status = this.getPolicySetStatus(id, version);
               metaData = this.getPolicySetMetaDataEntry(id, version);
               this.deletePolicySet(id, version);
               this.addPolicySet((PolicySet)item[1], status, metaData);
            }
         }

      } catch (Throwable var8) {
         throw new PolicyStoreException(var8);
      }
   }

   private void reload() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      try {
         this.lock.writeLock();
         this.atzPolicyFinder.reset();
         this.rolePolicyFinder.reset();
         this.load();
      } finally {
         this.lock.writeUnLock();
      }

   }

   private void load() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      if (this.debugEnabled) {
         this.logger.debug("FileStore: Loading policies");
      }

      Iterator it = this.index.iterator();

      while(it.hasNext()) {
         Index.Entry e = (Index.Entry)it.next();
         if (this.debugEnabled) {
            this.logger.debug("Loading entry: " + e.getFileName() + " (status=" + e.getStatus() + ") " + e.getIdReference().getReference());
         }

         if (e.getStatus() != 2) {
            Target t = e.getData().getTarget();

            try {
               if (this.omu.isRoleTarget(t)) {
                  this.rolePolicyFinder.loadEntry(e);
               } else {
                  this.atzPolicyFinder.loadEntry(e);
               }
            } catch (InvalidRoleAssignmentPolicyException var5) {
               throw new DocumentParseException(var5);
            }
         }
      }

   }

   public void addPolicy(Policy policy, int status, PolicyMetaData data) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      try {
         this.lock.writeLock();
         if (!this.store.isDirectory()) {
            throw new PolicyStoreException("Policy store is not a directory.");
         }

         Index.Entry e = this.index.getPolicyForIdVersion(policy.getId(), policy.getVersion());
         if (e != null) {
            throw new PolicyStoreException("Policy already exists");
         }

         e = this.index.createEntry(policy, status, data);
         this.index.add(e);
         this.reload();
      } finally {
         this.lock.writeUnLock();
      }

   }

   public void addPolicySet(PolicySet set, int status, PolicyMetaData data) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      try {
         this.lock.writeLock();
         if (!this.store.isDirectory()) {
            throw new PolicyStoreException("Policy store does not support multiple files");
         }

         Index.Entry e = this.index.getPolicySetForIdVersion(set.getId(), set.getVersion());
         if (e != null) {
            throw new PolicyStoreException("Policy set already exists");
         }

         e = this.index.createEntry(set, status, data);
         this.index.add(e);
         this.reload();
      } finally {
         this.lock.writeUnLock();
      }

   }

   public void setPolicy(Policy policy, int status, PolicyMetaData data) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      try {
         this.lock.writeLock();
         if (!this.store.isDirectory()) {
            throw new PolicyStoreException("Policy store not modifiable");
         }

         Index.Entry e = this.index.getPolicyForIdVersion(policy.getId(), policy.getVersion());
         if (e != null) {
            if (status == -1) {
               status = e.getStatus();
            }

            this.index.remove(e);
         }

         if (status == -1) {
            status = 0;
         }

         e = this.index.createEntry(policy, status, data);
         this.index.add(e);
         this.reload();
      } finally {
         this.lock.writeUnLock();
      }

   }

   public void setPolicySet(PolicySet set, int status, PolicyMetaData data) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      try {
         this.lock.writeLock();
         if (!this.store.isDirectory()) {
            throw new PolicyStoreException("Policy store not modifiable");
         }

         Index.Entry e = this.index.getPolicySetForIdVersion(set.getId(), set.getVersion());
         if (e != null) {
            if (status == -1) {
               status = e.getStatus();
            }

            this.index.remove(e);
         }

         if (status == -1) {
            status = 0;
         }

         e = this.index.createEntry(set, status, data);
         this.index.add(e);
         this.reload();
      } finally {
         this.lock.writeUnLock();
      }

   }

   public PolicyMetaData getPolicyMetaDataEntry(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      Object var5;
      try {
         this.lock.readLock();
         Index.Entry e = this.index.getPolicyForIdVersion(identifier, version);
         Object pmd;
         if (e == null) {
            pmd = null;
            return (PolicyMetaData)pmd;
         }

         pmd = this.getPolicyMetaData(e);
         if (pmd == null) {
            pmd = new PolicyMetaDataImpl((String)null, (String)null, new HashMap());
         }

         var5 = pmd;
      } catch (DocumentParseException var9) {
         throw new PolicyStoreException(var9);
      } finally {
         this.lock.readUnLock();
      }

      return (PolicyMetaData)var5;
   }

   public PolicyMetaData getPolicySetMetaDataEntry(URI identifier, String version) throws PolicyStoreException, URISyntaxException {
      Object pmd;
      try {
         this.lock.readLock();
         Index.Entry e = this.index.getPolicySetForIdVersion(identifier, version);
         if (e != null) {
            pmd = this.getPolicyMetaData(e);
            if (pmd == null) {
               pmd = new PolicyMetaDataImpl((String)null, (String)null, new HashMap());
            }

            Object var5 = pmd;
            return (PolicyMetaData)var5;
         }

         pmd = null;
      } catch (DocumentParseException var9) {
         throw new PolicyStoreException(var9);
      } finally {
         this.lock.readUnLock();
      }

      return (PolicyMetaData)pmd;
   }

   public List readPolicy(PolicyMetaData data) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.readEntriesByMetaData(data, true);
   }

   public List readPolicySet(PolicyMetaData data) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.readEntriesByMetaData(data, false);
   }

   public void setMetaDataEntry(String key, String value) throws PolicyStoreException {
      this.wlsCollectionStore.setMetaDataEntry(key, value);
   }

   public String getMetaDataEntry(String key) throws PolicyStoreException {
      return this.wlsCollectionStore.getMetaDataEntry(key);
   }

   public List readAllMetaDataEntries() throws PolicyStoreException {
      return this.wlsCollectionStore.getAllMetaDataEntry();
   }

   private List readEntriesByMetaData(PolicyMetaData data, boolean isPolicy) throws PolicyMetaDataException, PolicyStoreException, DocumentParseException, URISyntaxException {
      if (data == null) {
         return null;
      } else {
         this.checkPolicyMetaData(data);

         LinkedList var12;
         try {
            this.lock.readLock();
            List entries = isPolicy ? this.index.listpoliciesByMetaData(data) : this.index.listpolicySetsByMetaData(data);
            List results = new LinkedList();
            if (entries != null) {
               Iterator var5 = entries.iterator();

               while(var5.hasNext()) {
                  Index.Entry e = (Index.Entry)var5.next();
                  AbstractPolicy policy = e.getData();
                  if (policy != null) {
                     PolicyMetaData md = this.getPolicyMetaData(e);
                     results.add(new PolicyInfoImpl(policy, md));
                  }
               }
            }

            var12 = results;
         } finally {
            this.lock.readUnLock();
         }

         return var12;
      }
   }

   private PolicyMetaData getPolicyMetaData(Index.Entry e) throws PolicyStoreException {
      PolicyMetaData md = null;
      String mdValue = e.getWlsXMLFragment();
      if (mdValue != null) {
         Map mdIndex = new HashMap();
         String mdCreator = e.getWlsCreatorInfo();
         if (mdCreator != null) {
            mdIndex.put("wlsCreatorInfo", mdCreator);
         }

         String mdCol = e.getWlsCollectionName();
         if (mdCol != null) {
            mdIndex.put("wlsCollectionName", mdCol);
         }

         String mdElementName = this.getMetaDataElementName();
         md = new PolicyMetaDataImpl(mdElementName, mdValue, mdIndex);
      }

      return md;
   }

   private String getMetaDataElementName() throws PolicyStoreException {
      if (this.storeType == 0) {
         return "WLSPolicyInfo";
      } else if (this.storeType == 1) {
         return "WLSRoleInfo";
      } else {
         throw new PolicyStoreException("illegal store type.");
      }
   }

   private void checkPolicyMetaData(PolicyMetaData data) throws PolicyMetaDataException, PolicyStoreException {
      if (data != null && !this.getMetaDataElementName().equals(data.getElementName())) {
         throw new PolicyMetaDataException("Unknown Policy MetaData Element: " + data.getElementName());
      }
   }

   private static class PlainFileAccess implements FileAccess {
      private PlainFileAccess() {
      }

      public String getPolicyFileNamePrefix(boolean isPolicy) {
         return isPolicy ? "pol" : "pset";
      }

      public String getPolicyFileNameSuffix() {
         return ".xml";
      }

      public String getIndexFileName() {
         return "xacml.index";
      }

      public boolean isAcceptableName(String name) {
         return name.endsWith(".xml");
      }

      public InputStream filterRead(InputStream stream) {
         return stream;
      }

      public OutputStream filterWrite(OutputStream stream) {
         return stream;
      }

      // $FF: synthetic method
      PlainFileAccess(Object x0) {
         this();
      }
   }
}

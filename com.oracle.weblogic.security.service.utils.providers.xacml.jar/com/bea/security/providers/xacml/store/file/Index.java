package com.bea.security.providers.xacml.store.file;

import com.bea.common.security.ApiLogger;
import com.bea.common.security.service.JAXPFactoryService;
import com.bea.common.security.utils.HashCodeUtil;
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
import com.bea.common.security.xacml.policy.Target;
import com.bea.security.providers.xacml.store.ConstraintUtil;
import com.bea.security.utils.lock.FileRWLock;
import com.bea.security.xacml.PolicyMetaData;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.cache.resource.ResourceMatchUtil;
import com.bea.security.xacml.cache.resource.ResourcePolicyIdUtil;
import com.bea.security.xacml.policy.PolicyUtils;
import com.bea.security.xacml.store.PolicyFinder;
import com.bea.security.xacml.store.PolicyRecord;
import com.bea.security.xacml.store.PolicySetRecord;
import com.bea.security.xacml.store.Record;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import weblogic.security.providers.utils.Utils;

class Index extends IndexSchemaObject {
   public static final String WLSCOLLECTIONNAME = "wlsCollectionName";
   public static final String WLSCREATORINFO = "wlsCreatorInfo";
   private static final String POLICYID_SEARCH = "policyId";
   private static final String RESSCOPE_SEARCH = "resourceScope";
   private static final String TIMESTAMP_SEARCH = "modifyTimestamp";
   private PolicyFinder finder;
   private AttributeRegistry registry;
   private File store;
   private File indexFile;
   private Collection entries;
   private Map fileNameIndex = new HashMap();
   private Map policyIdVersionIndex = new HashMap();
   private Map policySetIdVersionIndex = new HashMap();
   private MetaDataMap policyMetaDataIndex = new MetaDataMap();
   private MetaDataMap policySetMetaDataIndex = new MetaDataMap();
   private List unreadEntries = new ArrayList();
   private JAXPFactoryService jaxpService;
   private FileAccess fileAccess;
   private FileRWLock indexFileLock;

   public Index(PolicyFinder finder, AttributeRegistry registry, File store, JAXPFactoryService jaxpService, FileAccess fileAccess) throws PolicyStoreException, DocumentParseException, URISyntaxException {
      this.finder = finder;
      this.registry = registry;
      this.store = store;
      this.fileAccess = fileAccess;
      this.indexFile = new File(store, fileAccess.getIndexFileName());
      this.jaxpService = jaxpService;

      try {
         if (store.isDirectory()) {
            this.indexFileLock = new FileRWLock(this.indexFile.getAbsolutePath());
            if (this.indexFile.exists()) {
               this.initializeIndex();
            } else {
               this.entries = new ArrayList();
            }
         } else {
            this.indexFileLock = new FileRWLock(store.getAbsolutePath());
            this.entries = new ArrayList();
            Entry e = new Entry(store.getName(), store.lastModified(), 0);
            e.read(false);
            this.localAdd(e);
         }

      } catch (SAXException var7) {
         throw new DocumentParseException(var7);
      } catch (ParserConfigurationException var8) {
         throw new DocumentParseException(var8);
      } catch (IOException var9) {
         throw new com.bea.security.xacml.IOException(var9);
      }
   }

   private void initializeIndex() throws ParserConfigurationException, SAXException, DocumentParseException, URISyntaxException, IOException, PolicyStoreException {
      InputStream stream = null;

      try {
         this.indexFileLock.readLock();
         stream = this.fileAccess.filterRead(new FileInputStream(this.indexFile));
         Node n = this.getRootNode(stream);
         List entries = new ArrayList();
         NodeList nodes = n.getChildNodes();

         for(int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if (node.getNodeName().equals("Entry")) {
               entries.add(new Entry(node));
            }
         }

         this.entries = entries;
      } finally {
         this.indexFileLock.readUnlock();
         if (stream != null) {
            stream.close();
         }

      }

      this.rebuildIndex();
      this.convertOldData();
      this.resynchronizeIndex();
   }

   private void convertOldData() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      URI entitlementCombiner = null;

      try {
         entitlementCombiner = new URI("urn:bea:xacml:2.0:entitlement:policy-combining-algorithm:most-specific");
      } catch (java.net.URISyntaxException var10) {
         throw new URISyntaxException(var10);
      }

      Collection entPolicySets = new ArrayList();
      Iterator ite = this.entries.iterator();

      while(true) {
         Entry e;
         AbstractPolicy ap;
         do {
            do {
               if (!ite.hasNext()) {
                  this.removeAll(entPolicySets);
                  return;
               }

               e = (Entry)ite.next();
               ap = e.getData();
            } while(!(ap instanceof PolicySet));
         } while(!entitlementCombiner.equals(ap.getCombiningAlgId()));

         List psml = ((PolicySet)ap).getPoliciesPolicySetsAndReferences();
         if (psml != null) {
            Iterator var7 = psml.iterator();

            while(var7.hasNext()) {
               PolicySetMember psm = (PolicySetMember)var7.next();
               if (psm instanceof IdReference) {
                  Entry eRef = this.findEntry((IdReference)psm);
                  if (eRef != null) {
                     eRef.setStatus(3);
                  }
               }
            }
         }

         entPolicySets.add(e);
      }
   }

   private Node getRootNode(InputStream stream) throws PolicyStoreException, ParserConfigurationException {
      DocumentBuilderFactory dbFactory = this.jaxpService.newDocumentBuilderFactory();
      dbFactory.setIgnoringComments(true);
      dbFactory.setNamespaceAware(true);
      dbFactory.setValidating(false);
      DocumentBuilder db = dbFactory.newDocumentBuilder();

      Document doc;
      try {
         doc = db.parse(stream);
      } catch (IOException var6) {
         throw new PolicyStoreException(ApiLogger.getIllegalFileFormatForFileStore());
      } catch (SAXException var7) {
         throw new PolicyStoreException(ApiLogger.getIllegalFileFormatForFileStore());
      }

      return doc.getDocumentElement();
   }

   private void write() throws PolicyStoreException {
      try {
         try {
            OutputStream fos = null;
            this.indexFileLock.writeLock();

            try {
               fos = this.fileAccess.filterWrite(this.indexFileLock.getFileOutputStream());
               this.encode(fos);
            } finally {
               if (fos != null) {
                  fos.close();
               }

            }
         } finally {
            this.indexFileLock.writeUnlock();
         }

      } catch (IOException var12) {
         throw new com.bea.security.xacml.IOException(var12);
      }
   }

   private void rebuildIndex() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      this.fileNameIndex.clear();
      this.policyIdVersionIndex.clear();
      this.policySetIdVersionIndex.clear();
      this.policyMetaDataIndex.clear();
      this.policySetMetaDataIndex.clear();
      this.unreadEntries.clear();
      Iterator var1 = this.entries.iterator();

      while(var1.hasNext()) {
         Entry e = (Entry)var1.next();
         this.addToFileNameIndex(e);
         IdReference idReference = e.idReference;
         if (idReference != null) {
            this.addToIdVersionIndex(e);
            this.addToMetaDataIndex(e);
         } else {
            this.unreadEntries.add(e);
         }
      }

   }

   private void addToIdVersionIndex(Entry e) {
      IdReference idReference = e.idReference;
      Map iMap = idReference instanceof PolicyIdReference ? this.policyIdVersionIndex : this.policySetIdVersionIndex;
      Map vMap = (Map)iMap.get(idReference.getReference());
      if (vMap == null) {
         vMap = new HashMap();
         iMap.put(idReference.getReference(), vMap);
      }

      ((Map)vMap).put(idReference.getVersion(), e);
   }

   private void removeFromIdVersionIndex(Entry e) {
      IdReference idReference = e.idReference;
      Map iMap = idReference instanceof PolicyIdReference ? this.policyIdVersionIndex : this.policySetIdVersionIndex;
      Map vMap = (Map)iMap.get(idReference.getReference());
      if (vMap != null) {
         vMap.remove(idReference.getVersion());
      }

   }

   private void addToFileNameIndex(Entry e) {
      this.fileNameIndex.put(e.getFileName(), e);
   }

   private void addToMetaDataIndex(Entry e) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      IdReference idReference = e.idReference;
      MetaDataKey key = new MetaDataKey(e);
      Map metaDataMap = idReference instanceof PolicyIdReference ? this.policyMetaDataIndex : this.policySetMetaDataIndex;
      metaDataMap.put(key, e);
   }

   private void removeFromMetaDataIndex(Entry e) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      IdReference idReference = e.idReference;
      Map metaDataIndex = idReference instanceof PolicyIdReference ? this.policyMetaDataIndex : this.policySetMetaDataIndex;
      metaDataIndex.remove(new MetaDataKey(e));
   }

   private void resynchronizeIndex() throws PolicyStoreException, DocumentParseException, URISyntaxException {
      File[] files = this.store.listFiles(new FilenameFilter() {
         public boolean accept(File f, String fileName) {
            return Index.this.fileAccess.isAcceptableName(fileName);
         }
      });
      boolean indexChanged = false;
      Set fileNames = new HashSet();
      File[] var4 = files;
      int var5 = files.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         File f = var4[var6];
         fileNames.add(f.getName());
         Entry e = (Entry)this.fileNameIndex.get(f.getName());
         if (e == null) {
            this.add(new Entry(f.getName(), f.lastModified()));
            indexChanged = true;
         } else if (f.lastModified() != e.getLastModified()) {
            e.setLastModified(f.lastModified());
            indexChanged = true;
         }
      }

      Iterator it = this.iterator();

      while(it.hasNext()) {
         if (!fileNames.contains(((Entry)it.next()).getFileName())) {
            it.remove();
            indexChanged = true;
         }
      }

      if (!this.unreadEntries.isEmpty()) {
         Iterator uit = this.unreadEntries.iterator();

         while(uit.hasNext()) {
            Entry e = (Entry)uit.next();
            e.read();
            uit.remove();
         }

         indexChanged = true;
      }

      if (indexChanged) {
         this.write();
      }

   }

   public String getElementName() {
      return "Index";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.entries != null) {
         ps.print("\n");
         Iterator var3 = this.entries.iterator();

         while(var3.hasNext()) {
            Entry e = (Entry)var3.next();
            if (e.idReference != null) {
               e.encode(nsMap, ps);
            }
         }
      }

   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.entries);
      return result;
   }

   public Entry getPolicyForIdVersion(URI id, String version) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      Map vMap = (Map)this.policyIdVersionIndex.get(id);
      if (vMap != null) {
         return (Entry)vMap.get(version);
      } else {
         if (!this.unreadEntries.isEmpty()) {
            PolicyRecord record = new PolicyRecord(id, version, this.finder);
            Iterator it = this.unreadEntries.iterator();

            while(it.hasNext()) {
               Entry e = (Entry)it.next();
               e.read();
               it.remove();
               if (record.equals(e.getRecord())) {
                  this.write();
                  return e;
               }
            }

            this.write();
         }

         return null;
      }
   }

   public Entry getPolicySetForIdVersion(URI id, String version) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      Map vMap = (Map)this.policySetIdVersionIndex.get(id);
      if (vMap != null) {
         return (Entry)vMap.get(version);
      } else {
         if (!this.unreadEntries.isEmpty()) {
            PolicySetRecord record = new PolicySetRecord(id, version, this.finder);
            Iterator it = this.unreadEntries.iterator();

            while(it.hasNext()) {
               Entry e = (Entry)it.next();
               e.read();
               it.remove();
               if (record.equals(e.getRecord())) {
                  this.write();
                  return e;
               }
            }

            this.write();
         }

         return null;
      }
   }

   public Collection getPolicyForId(URI id) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.getForId(id, this.policyIdVersionIndex);
   }

   public Collection getPolicySetForId(URI id) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.getForId(id, this.policySetIdVersionIndex);
   }

   private Collection getForId(URI id, Map iMap) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      if (!this.unreadEntries.isEmpty()) {
         Iterator it = this.unreadEntries.iterator();

         while(it.hasNext()) {
            Entry e = (Entry)it.next();
            e.read();
            it.remove();
         }

         this.write();
      }

      Map vMap = (Map)iMap.get(id);
      return vMap != null ? vMap.values() : null;
   }

   public Set getAllPolicies() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.getAll(this.policyIdVersionIndex);
   }

   public Set getAllPolicySets() throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return this.getAll(this.policySetIdVersionIndex);
   }

   private Set getAll(Map iMap) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      if (!this.unreadEntries.isEmpty()) {
         Iterator it = this.unreadEntries.iterator();

         while(it.hasNext()) {
            Entry e = (Entry)it.next();
            e.read();
            it.remove();
         }

         this.write();
      }

      Set result = new HashSet();
      Iterator var8 = iMap.values().iterator();

      while(var8.hasNext()) {
         Map vMap = (Map)var8.next();
         Iterator var5 = vMap.values().iterator();

         while(var5.hasNext()) {
            Entry e = (Entry)var5.next();
            result.add(e);
         }
      }

      return !result.isEmpty() ? result : null;
   }

   public int size() {
      return this.entries.size();
   }

   public boolean isEmpty() {
      return this.entries.isEmpty();
   }

   public boolean contains(Object o) {
      return this.entries.contains(o);
   }

   public Iterator iterator() {
      final Iterator it = this.entries.iterator();
      return new Iterator() {
         private Entry next;

         public boolean hasNext() {
            return it.hasNext();
         }

         public Entry next() {
            this.next = (Entry)it.next();
            return this.next;
         }

         public void remove() {
            if (Index.this.store.isDirectory()) {
               it.remove();
               this.next.delete();
               Index.this.fileNameIndex.remove(this.next.getFileName());
               IdReference id = this.next.idReference;
               if (id == null) {
                  throw new UnsupportedOperationException();
               }

               Map iMap = id instanceof PolicyIdReference ? Index.this.policyIdVersionIndex : Index.this.policySetIdVersionIndex;
               Map vMap = (Map)iMap.get(id.getReference());
               if (vMap != null) {
                  vMap.remove(id.getVersion());
               }
            }

         }
      };
   }

   public boolean add(Entry o) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      if (this.store.isDirectory()) {
         boolean result = this.entries.add(o);
         this.fileNameIndex.put(o.getFileName(), o);
         IdReference idReference = o.getIdReference();
         if (idReference != null) {
            this.addToIdVersionIndex(o);
            this.addToMetaDataIndex(o);
         } else {
            this.unreadEntries.add(o);
         }

         this.write();
         return result;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   private boolean localAdd(Entry o) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      if (o.getData() == null) {
         throw new PolicyStoreException("Can't read data from the policy store: " + o.getFileName());
      } else {
         boolean result = this.entries.add(o);
         this.fileNameIndex.put(o.getFileName(), o);
         IdReference idReference = o.getIdReference();
         if (idReference != null) {
            this.addToIdVersionIndex(o);
            this.addToMetaDataIndex(o);
         } else {
            this.unreadEntries.add(o);
         }

         return result;
      }
   }

   public boolean remove(Entry e) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      if (this.store.isDirectory()) {
         boolean result = this.entries.remove(e);
         if (result) {
            e.delete();
            this.fileNameIndex.remove(e.getFileName());
            IdReference idReference = e.getIdReference();
            if (idReference != null) {
               this.removeFromIdVersionIndex(e);
               this.removeFromMetaDataIndex(e);
            } else {
               this.unreadEntries.remove(e);
            }

            this.write();
         }

         return result;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public boolean containsAll(Collection c) {
      return this.entries.containsAll(c);
   }

   public boolean addAll(Collection c) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      if (!this.store.isDirectory()) {
         throw new UnsupportedOperationException();
      } else {
         boolean result = this.entries.addAll(c);
         if (result) {
            Iterator var3 = c.iterator();

            while(var3.hasNext()) {
               Entry e = (Entry)var3.next();
               this.fileNameIndex.put(e.getFileName(), e);
               IdReference idReference = e.getIdReference();
               if (idReference != null) {
                  this.addToIdVersionIndex(e);
                  this.addToMetaDataIndex(e);
               } else {
                  this.unreadEntries.add(e);
               }
            }

            this.write();
         }

         return result;
      }
   }

   public boolean removeAll(Collection c) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      if (!this.store.isDirectory()) {
         throw new UnsupportedOperationException();
      } else {
         boolean result = this.entries.removeAll(c);
         if (result) {
            Iterator var3 = c.iterator();

            while(var3.hasNext()) {
               Object o = var3.next();
               if (o instanceof Entry) {
                  Entry e = (Entry)o;
                  e.delete();
                  this.fileNameIndex.remove(e.getFileName());
                  IdReference idReference = e.getIdReference();
                  if (idReference != null) {
                     this.removeFromIdVersionIndex(e);
                     this.removeFromMetaDataIndex(e);
                  } else {
                     this.unreadEntries.remove(e);
                  }
               }
            }

            this.write();
         }

         return result;
      }
   }

   public boolean retainAll(Collection c) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      if (this.store.isDirectory()) {
         boolean result = this.entries.retainAll(c);
         if (result) {
            this.rebuildIndex();
            this.write();
         }

         return result;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void clear() throws DocumentParseException, PolicyStoreException {
      if (this.store.isDirectory()) {
         this.entries.clear();
         this.fileNameIndex.clear();
         this.policyIdVersionIndex.clear();
         this.policySetIdVersionIndex.clear();
         this.write();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public Entry findEntry(IdReference idReference) throws PolicyStoreException, DocumentParseException, URISyntaxException {
      Collection entries = idReference instanceof PolicyIdReference ? this.getPolicyForId(idReference.getReference()) : this.getPolicySetForId(idReference.getReference());
      Entry ent = null;
      Record entRec = null;
      if (entries != null) {
         Iterator var5 = entries.iterator();

         while(true) {
            Entry e;
            Record r;
            do {
               do {
                  if (!var5.hasNext()) {
                     return ent;
                  }

                  e = (Entry)var5.next();
                  r = e.getRecord();
               } while(!ConstraintUtil.meetsConstraint(r.getVersion(), idReference));
            } while(ent != null && !ConstraintUtil.isEarlier(entRec.getVersion(), r.getVersion()));

            ent = e;
            entRec = e.getRecord();
         }
      } else {
         return ent;
      }
   }

   public Entry createEntry(AbstractPolicy data, int status, PolicyMetaData metaData) throws PolicyStoreException {
      if (this.store.isDirectory()) {
         try {
            return new Entry(this.fileAccess.getPolicyFileNamePrefix(data instanceof Policy), this.fileAccess.getPolicyFileNameSuffix(), data, status, metaData);
         } catch (IOException var5) {
            throw new com.bea.security.xacml.IOException(var5);
         }
      } else {
         throw new UnsupportedOperationException("Store does not support multiple files");
      }
   }

   private static File createTempFile(String prefix, String suffix, File storeDirectory) throws IOException {
      return File.createTempFile(prefix, suffix, storeDirectory);
   }

   public List listpolicySetsByMetaData(PolicyMetaData data) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      if (!this.unreadEntries.isEmpty()) {
         Iterator it = this.unreadEntries.iterator();

         while(it.hasNext()) {
            Entry e = (Entry)it.next();
            e.read();
            it.remove();
         }

         this.write();
      }

      return this.policySetMetaDataIndex.queryByMetaData(new MetaDataKey(data));
   }

   public List listpoliciesByMetaData(PolicyMetaData data) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      if (!this.unreadEntries.isEmpty()) {
         Iterator it = this.unreadEntries.iterator();

         while(it.hasNext()) {
            Entry e = (Entry)it.next();
            e.read();
            it.remove();
         }

         this.write();
      }

      MetaDataKey key = new MetaDataKey(data);
      return this.policyMetaDataIndex.queryByMetaData(key);
   }

   static class MetaDataKey {
      private String policyId;
      private String wlsCreatorInfo;
      private String wlsCollectionName;
      private String resourceScope;
      private long time;
      private String version;

      public MetaDataKey() {
      }

      public MetaDataKey(Entry e) throws DocumentParseException, URISyntaxException, PolicyStoreException {
         this.policyId = e.getIdReference().getReference().toString();
         this.wlsCreatorInfo = e.getWlsCreatorInfo();
         this.wlsCollectionName = e.getWlsCollectionName();
         this.time = e.getLastModified();
         this.version = e.getIdReference().getVersion();
         AbstractPolicy ap = e.getData();
         if (ap instanceof PolicySet && e.getStatus() == 3) {
            this.resourceScope = ResourcePolicyIdUtil.getResourceId(ap.getId().toString());
         } else {
            this.resourceScope = (new ResourceMatchUtil()).getTargetResource(ap.getTarget());
         }

      }

      public MetaDataKey(PolicyMetaData data) {
         this.policyId = data.getIndexValue("policyId");
         this.wlsCreatorInfo = data.getIndexValue("wlsCreatorInfo");
         this.wlsCollectionName = data.getIndexValue("wlsCollectionName");
         this.resourceScope = data.getIndexValue("resourceScope");
         this.time = data.getIndexValue("modifyTimestamp") == null ? Long.MAX_VALUE : Long.parseLong(data.getIndexValue("modifyTimestamp"));
      }

      public String toString() {
         return "[policyId=" + this.policyId + "]; [wlsCreatorInfo=" + this.wlsCreatorInfo + "]; [wlsCollectionName=" + this.wlsCollectionName + "]; [resourceScope=" + this.resourceScope + "]; [time=" + this.time + "]";
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof MetaDataKey)) {
            return false;
         } else {
            MetaDataKey compare = (MetaDataKey)o;
            return this.policyId.equals(compare.getPolicyId()) && this.version.equals(compare.getVersion());
         }
      }

      public boolean matches(Object o) {
         if (o != null && o instanceof MetaDataKey) {
            MetaDataKey compare = (MetaDataKey)o;
            return this.checkMatches(this.policyId, compare.getPolicyId()) && this.checkMatches(this.resourceScope, compare.getResourceScope()) && (this.wlsCreatorInfo == null || this.wlsCreatorInfo.equals(compare.getWlsCreatorInfo())) && (this.wlsCollectionName == null || this.wlsCollectionName.equals(compare.getWlsCollectionName())) && this.time > compare.getTime();
         } else {
            return false;
         }
      }

      public int hashCode() {
         return 1;
      }

      private boolean checkMatches(String s1, String s2) {
         return s1 == null || ".*".equals(Utils.escapeRegChar(s1)) || s2 != null && s2.toLowerCase().matches(Utils.escapeRegChar(s1).toLowerCase());
      }

      public String getPolicyId() {
         return this.policyId;
      }

      public void setPolicyId(String policyId) {
         this.policyId = policyId;
      }

      public String getResourceScope() {
         return this.resourceScope;
      }

      public void setResourceScope(String resourceScope) {
         this.resourceScope = resourceScope;
      }

      public long getTime() {
         return this.time;
      }

      public void setTime(long time) {
         this.time = time;
      }

      public String getWlsCollectionName() {
         return this.wlsCollectionName;
      }

      public void setWlsCollectionName(String wlsCollectionName) {
         this.wlsCollectionName = wlsCollectionName;
      }

      public String getWlsCreatorInfo() {
         return this.wlsCreatorInfo;
      }

      public void setWlsCreatorInfo(String wlsCreatorInfo) {
         this.wlsCreatorInfo = wlsCreatorInfo;
      }

      public String getVersion() {
         return this.version;
      }

      public void setVersion(String version) {
         this.version = version;
      }
   }

   public class Entry extends IndexSchemaObject implements PolicySetMember {
      private static final String ENUM_ACTIVE = "Active";
      private static final String ENUM_IF_REFERENCED = "IfReferenced";
      private static final String ENUM_INACTIVE = "Inactive";
      private static final String ENUM_ENTITLEMENT = "Entitlement";
      private String fileName;
      private long lastModified;
      private IdReference idReference;
      private Target target;
      private int status;
      private AbstractPolicy data;
      private Record record;
      private String wlsCreatorInfo;
      private String wlsCollectionName;
      private String wlsXMLFragment;

      private Entry(String prefix, String suffix, AbstractPolicy data, int status, PolicyMetaData metaData) throws IOException {
         this(Index.createTempFile(prefix, suffix, Index.this.store), data, status, metaData);
      }

      private Entry(File entryFile, AbstractPolicy data, int status, PolicyMetaData metaData) throws IOException {
         this.fileName = entryFile.getName();
         this.data = data;
         this.target = data.getTarget();
         this.status = status;
         if (metaData != null && metaData.getValue() != null) {
            this.wlsCollectionName = metaData.getIndexValue("wlsCollectionName");
            this.wlsCreatorInfo = metaData.getIndexValue("wlsCreatorInfo");
            this.wlsXMLFragment = metaData.getValue();
         }

         try {
            OutputStream fos = null;
            Index.this.indexFileLock.writeLock();

            try {
               fos = Index.this.fileAccess.filterWrite(new FileOutputStream(entryFile));
               data.encode(fos);
            } finally {
               if (fos != null) {
                  fos.close();
               }

            }
         } finally {
            Index.this.indexFileLock.writeUnlock();
         }

         this.lastModified = entryFile.lastModified();
         this.idReference = data.getReference();
      }

      private Entry(String fileName, long lastModified) {
         this(fileName, lastModified, (IdReference)null);
      }

      private Entry(String fileName, long lastModified, int status) {
         this(fileName, lastModified, (IdReference)null, status);
      }

      private Entry(String fileName, long lastModified, IdReference idReference) {
         this(fileName, lastModified, idReference, 1);
      }

      private Entry(String fileName, long lastModified, IdReference idReference, int status) {
         this.fileName = fileName;
         this.lastModified = lastModified;
         this.idReference = idReference;
         this.status = status;
      }

      private Entry(Node root) throws DocumentParseException, URISyntaxException {
         NamedNodeMap attrs = root.getAttributes();
         this.fileName = attrs.getNamedItem("FileName").getNodeValue();
         this.lastModified = Long.parseLong(attrs.getNamedItem("LastModified").getNodeValue());
         String status = attrs.getNamedItem("Status").getNodeValue();
         if ("Active".equals(status)) {
            this.status = 0;
         } else if ("Inactive".equals(status)) {
            this.status = 2;
         } else if ("IfReferenced".equals(status)) {
            this.status = 1;
         } else {
            this.status = 3;
         }

         NodeList nodes = root.getChildNodes();

         for(int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if (node.getNodeName().equals("PolicyIdReference")) {
               this.idReference = new PolicyIdReference(node);
            } else if (node.getNodeName().equals("PolicySetIdReference")) {
               this.idReference = new PolicySetIdReference(node);
            } else if (node.getNodeName().equals("Target")) {
               this.target = new Target(Index.this.registry, node);
            } else if (node.getNodeName().equals("WlsCreatorInfo")) {
               this.wlsCreatorInfo = node.getFirstChild() == null ? null : node.getFirstChild().getNodeValue();
            } else if (node.getNodeName().equals("WlsCollectionName")) {
               this.wlsCollectionName = node.getFirstChild() == null ? null : node.getFirstChild().getNodeValue();
            } else if (node.getNodeName().equals("WlsXmlFragment")) {
               CDATASection section = (CDATASection)node.getFirstChild();
               this.wlsXMLFragment = section.getNodeValue();
            }
         }

      }

      public String getElementName() {
         return "Entry";
      }

      public void encodeAttributes(PrintStream ps) {
         ps.print(" FileName=\"");
         ps.print(this.fileName);
         ps.print('"');
         ps.print(" LastModified=\"");
         ps.print(this.lastModified);
         ps.print('"');
         ps.print(" Status=\"");
         switch (this.status) {
            case 0:
               ps.print("Active");
               break;
            case 1:
               ps.print("IfReferenced");
               break;
            case 2:
               ps.print("Inactive");
               break;
            default:
               ps.print("Entitlement");
         }

         ps.print('"');
      }

      public boolean hasChildren() {
         return true;
      }

      public void encodeChildren(Map nsMap, PrintStream ps) {
         ps.print("\n");
         if (this.idReference != null) {
            nsMap.remove(this.idReference.getNamespace());
            this.idReference.encode(nsMap, ps);
         }

         if (this.target != null) {
            nsMap.remove(this.target.getNamespace());
            this.target.encode(nsMap, ps);
         }

         if (this.wlsCreatorInfo != null) {
            ps.print("<WlsCreatorInfo>" + this.escapeXML(this.wlsCreatorInfo) + "</WlsCreatorInfo>");
         }

         if (this.wlsCollectionName != null) {
            ps.print("<WlsCollectionName>" + this.escapeXML(this.wlsCollectionName) + "</WlsCollectionName>");
         }

         if (this.wlsXMLFragment != null) {
            ps.print("<WlsXmlFragment><![CDATA[" + this.wlsXMLFragment + "]]></WlsXmlFragment>");
         }

      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof Entry)) {
            return false;
         } else {
            Entry o = (Entry)other;
            return (this.fileName == o.fileName || this.fileName != null && this.fileName.equals(o.fileName)) && this.lastModified == o.lastModified && (this.idReference == o.idReference || this.idReference != null && this.idReference.equals(o.idReference)) && (this.target == o.target || this.target != null && this.target.equals(o.target));
         }
      }

      public int internalHashCode() {
         int result = 23;
         result = HashCodeUtil.hash(result, this.fileName);
         result = HashCodeUtil.hash(result, this.lastModified);
         result = HashCodeUtil.hash(result, this.idReference);
         result = HashCodeUtil.hash(result, this.target);
         return result;
      }

      public int getStatus() {
         return this.status;
      }

      public String getFileName() {
         return this.fileName;
      }

      public long getLastModified() {
         return this.lastModified;
      }

      public IdReference getIdReference() throws DocumentParseException, URISyntaxException, PolicyStoreException {
         if (this.idReference == null) {
            this.read(true);
         }

         return this.idReference;
      }

      public Target getTarget() throws DocumentParseException, URISyntaxException, PolicyStoreException {
         if (this.idReference == null) {
            this.read(true);
         }

         return this.target;
      }

      private void setLastModified(long lastModified) {
         this.lastModified = lastModified;
         if (this.idReference != null) {
            Map iMap = this.idReference instanceof PolicyIdReference ? Index.this.policyIdVersionIndex : Index.this.policySetIdVersionIndex;
            Map vMap = (Map)iMap.get(this.idReference.getReference());
            if (vMap != null) {
               vMap.remove(this.idReference.getVersion());
            }
         }

         this.idReference = null;
         this.target = null;
         this.data = null;
         Index.this.unreadEntries.add(this);
      }

      public AbstractPolicy getData() throws DocumentParseException, URISyntaxException, PolicyStoreException {
         this.read(true);
         return this.data;
      }

      public Record getRecord() throws DocumentParseException, URISyntaxException, PolicyStoreException {
         if (this.record == null) {
            this.read(true);
            this.record = (Record)(this.data instanceof Policy ? new PolicyRecord(this.idReference.getReference(), this.idReference.getVersion(), Index.this.finder, (Policy)this.data) : new PolicySetRecord(this.idReference.getReference(), this.idReference.getVersion(), Index.this.finder, (PolicySet)this.data));
         }

         return this.record;
      }

      public void setStatus(int status) throws PolicyStoreException {
         this.status = status;
         Index.this.write();
      }

      private void read() throws DocumentParseException, URISyntaxException, PolicyStoreException {
         this.read(false);
      }

      private void read(boolean doRemoveFromUnreadEntries) throws DocumentParseException, PolicyStoreException, URISyntaxException {
         if (this.data == null) {
            try {
               File entryFile = Index.this.store.isDirectory() ? new File(Index.this.store, this.fileName) : Index.this.store;
               if (entryFile.exists()) {
                  InputStream stream = null;

                  try {
                     Index.this.indexFileLock.readLock();
                     stream = Index.this.fileAccess.filterRead(new FileInputStream(entryFile));
                     this.data = PolicyUtils.read(Index.this.registry, stream, Index.this.jaxpService.newDocumentBuilderFactory());
                  } finally {
                     Index.this.indexFileLock.writeUnlock();
                     if (stream != null) {
                        stream.close();
                     }

                  }

                  this.idReference = this.data.getReference();
                  this.target = this.data.getTarget();
                  Index.this.addToIdVersionIndex(this);
                  Index.this.addToMetaDataIndex(this);
                  if (doRemoveFromUnreadEntries) {
                     Index.this.unreadEntries.remove(this);
                  }
               }
            } catch (IOException var8) {
               throw new com.bea.security.xacml.IOException(var8);
            }
         }

      }

      private void delete() {
         File entryFile = new File(Index.this.store, this.fileName);
         if (entryFile.exists()) {
            entryFile.delete();
         }

      }

      public String getWlsCollectionName() {
         return this.wlsCollectionName;
      }

      public void setWlsCollectionName(String wlsCollectionInfo) {
         this.wlsCollectionName = wlsCollectionInfo;
      }

      public String getWlsCreatorInfo() {
         return this.wlsCreatorInfo;
      }

      public void setWlsCreatorInfo(String wlsCreatorInfo) {
         this.wlsCreatorInfo = wlsCreatorInfo;
      }

      public String getWlsXMLFragment() {
         return this.wlsXMLFragment;
      }

      public void setWlsXMLFragment(String wlsXMLFragment) {
         this.wlsXMLFragment = wlsXMLFragment;
      }

      // $FF: synthetic method
      Entry(String x1, long x2, int x3, Object x4) {
         this(x1, x2, x3);
      }

      // $FF: synthetic method
      Entry(Node x1, Object x2) throws DocumentParseException, URISyntaxException {
         this(x1);
      }

      // $FF: synthetic method
      Entry(String x1, long x2, Object x3) {
         this(x1, x2);
      }

      // $FF: synthetic method
      Entry(String x1, String x2, AbstractPolicy x3, int x4, PolicyMetaData x5, Object x6) throws IOException {
         this(x1, x2, x3, x4, x5);
      }
   }

   class MetaDataMap extends HashMap {
      public List queryByMetaData(Object o) {
         if (!(o instanceof MetaDataKey)) {
            return null;
         } else {
            MetaDataKey key = (MetaDataKey)o;
            List list = new LinkedList();
            Iterator ite = this.entrySet().iterator();

            while(ite.hasNext()) {
               Map.Entry tmp = (Map.Entry)ite.next();
               if (key.matches(tmp.getKey())) {
                  list.add(tmp.getValue());
               }
            }

            return list.size() == 0 ? null : list;
         }
      }
   }
}

package com.bea.security.providers.xacml.entitlement;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.utils.Pair;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicyIdReference;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.common.security.xacml.policy.PolicySetIdReference;
import com.bea.common.security.xacml.policy.PolicySetMember;
import com.bea.security.providers.xacml.ConflictException;
import com.bea.security.providers.xacml.Cursor;
import com.bea.security.providers.xacml.StoredPolicyCollectionInfo;
import com.bea.security.providers.xacml.UniqueIdentifier;
import com.bea.security.providers.xacml.XMLEscaper;
import com.bea.security.providers.xacml.store.MetaDataPolicyStore;
import com.bea.security.providers.xacml.store.PolicyMetaDataImpl;
import com.bea.security.xacml.PolicyInfo;
import com.bea.security.xacml.PolicyMetaData;
import com.bea.security.xacml.PolicySetInfo;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.cache.resource.MultipleResourceTargetException;
import com.bea.security.xacml.cache.resource.ResourceMatchUtil;
import com.bea.security.xacml.cache.resource.ResourcePolicyIdUtil;
import com.bea.security.xacml.store.PolicyStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.management.utils.CreateException;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.NotFoundException;
import weblogic.management.utils.RemoveException;
import weblogic.security.spi.Resource;

public class PolicyManager {
   public static final int SEARCH_TYPE_STARTS_WITH = 0;
   public static final int SEARCH_TYPE_ENDS_WITH = 1;
   public static final int SEARCH_TYPE_CONTAINS = 2;
   protected static final String STORE_POLICYID = "policyId";
   protected static final String STORE_RESSCOPE = "resourceScope";
   protected static final String CREATOR_MBEAN = "mbean";
   protected static final String CREATOR_DEPLOY = "deploy";
   protected static final String CREATOR_INFO = "wlsCreatorInfo";
   protected static final String COLLECTION_NAME = "wlsCollectionName";
   private static final String WLS_POLICY_INFO = "WLSPolicyInfo";
   private static final String WLS_XML_START = "<WLSPolicyInfo ";
   protected static final String WLS_XML_CREATE = "wlsCreatorInfo=\"";
   protected static final String WLS_XML_CNAME = "wlsCollectionName=\"";
   public static final String PCI_KEY = "PolicyCollectionInfo#";
   private static final String PCI_XML_START = "<PolicyCollectionInfo ";
   protected static final String PCI_XML_NAME = "Name=\"";
   protected static final String PCI_XML_TIMESTAMP = "TimeStamp=\"";
   protected static final String PCI_XML_VERSION = "Version=\"";
   protected static final String XML_ATTR_END = "\" ";
   protected static final String XML_END = "\"/>";
   protected static final int PCI_XML_NAME_LEN = "Name=\"".length();
   protected static final int PCI_XML_TIMESTAMP_LEN = "TimeStamp=\"".length();
   protected static final int PCI_XML_VERSION_LEN = "Version=\"".length();
   private PolicyStore store;
   private EntitlementConverter converter;
   private ResourceMatchUtil rmu;
   private UniqueIdentifier ui = new UniqueIdentifier("pm");
   private LoggerSpi log;

   public PolicyManager(PolicyStore store, EntitlementConverter converter, LoggerSpi log) throws URISyntaxException {
      this.store = store;
      this.converter = converter;
      this.log = log;
      this.rmu = new ResourceMatchUtil();
   }

   public boolean hasPolicy(Resource resource) throws NotFoundException {
      return this.hasPolicy(resource != null ? resource.toString() : null);
   }

   public boolean hasPolicy(String resource) throws NotFoundException {
      try {
         URI id;
         try {
            id = new URI(this.converter.getPolicyId(resource));
         } catch (java.net.URISyntaxException var4) {
            throw new URISyntaxException(var4);
         }

         return this.store.hasPolicy(id, "1.0");
      } catch (DocumentParseException var5) {
         throw new NotFoundException(var5);
      } catch (PolicyStoreException var6) {
         throw new NotFoundException(var6);
      } catch (URISyntaxException var7) {
         throw new NotFoundException(var7);
      }
   }

   public String getPolicy(Resource resource) throws NotFoundException {
      return this.getPolicy(resource != null ? resource.toString() : null);
   }

   public String getPolicy(String resource) throws NotFoundException {
      try {
         URI id;
         try {
            id = new URI(this.converter.getPolicyId(resource));
         } catch (java.net.URISyntaxException var5) {
            throw new URISyntaxException(var5);
         }

         Policy pol = this.store.readPolicy(id, "1.0");
         if (pol != null) {
            List rules = pol.getRules();
            if (rules != null && !rules.isEmpty()) {
               return pol.getDescription();
            }
         }

         throw new NotFoundException(SecurityLogger.getFailedToSetResource());
      } catch (DocumentParseException var6) {
         throw new NotFoundException(SecurityLogger.getFailedToSetResource(), var6);
      } catch (PolicyStoreException var7) {
         throw new NotFoundException(SecurityLogger.getFailedToSetResource(), var7);
      } catch (URISyntaxException var8) {
         throw new NotFoundException(SecurityLogger.getFailedToSetResource(), var8);
      }
   }

   public void setPolicy(Resource resource, String expression) throws CreateException {
      Policy policy = this.convertExpression(resource, expression);
      this.setPolicy(policy, true, (String)null);
   }

   private Policy convertExpression(Resource resource, String expression) throws CreateException {
      String resId = resource != null ? resource.toString() : null;
      return this.convertExpression(resId, expression);
   }

   public void setPolicy(String resource, String expression) throws CreateException {
      Policy policy = this.convertExpression(resource, expression);
      this.setPolicy(policy, false, (String)null);
   }

   private Policy convertExpression(String resource, String expression) throws CreateException {
      try {
         Policy pol = this.converter.convertResourceExpression(resource, expression);
         if (this.log.isDebugEnabled()) {
            this.log.debug("Converted policy:\n" + pol);
         }

         return pol;
      } catch (DocumentParseException var4) {
         throw new CreateException(var4);
      } catch (URISyntaxException var5) {
         throw new CreateException(var5);
      }
   }

   private void setPolicy(Policy pol, boolean deploy, String name) throws CreateException {
      try {
         if (this.store instanceof MetaDataPolicyStore) {
            MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;
            Map index = new HashMap();
            String createInfo = "deploy";
            if (!deploy) {
               createInfo = "mbean";
            }

            index.put("wlsCreatorInfo", createInfo);
            String value;
            if (name != null) {
               index.put("wlsCollectionName", name);
               String xmlName = XMLEscaper.escapeXMLChars(name);
               value = "<WLSPolicyInfo wlsCreatorInfo=\"" + createInfo + "\" " + "wlsCollectionName=\"" + xmlName + "\"/>";
            } else {
               value = "<WLSPolicyInfo wlsCreatorInfo=\"" + createInfo + "\"/>";
            }

            PolicyMetaData md = new PolicyMetaDataImpl("WLSPolicyInfo", value, index);
            if (mstore instanceof EntitlementAwarePolicyStore) {
               mstore.setPolicy(pol, 3, md);
            } else {
               mstore.setPolicy(pol, 1, md);
               this.setTopPolicySet(pol);
            }
         } else if (this.store instanceof EntitlementAwarePolicyStore) {
            this.store.setPolicy(pol, 3);
         } else {
            this.store.setPolicy(pol, 1);
            this.setTopPolicySet(pol);
         }

      } catch (DocumentParseException var9) {
         throw new CreateException(var9);
      } catch (PolicyStoreException var10) {
         throw new CreateException(var10);
      } catch (URISyntaxException var11) {
         throw new CreateException(var11);
      }
   }

   private void setTopPolicySet(Policy pol) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      URI tid;
      try {
         tid = new URI(this.converter.getTopPolicyId());
      } catch (java.net.URISyntaxException var7) {
         throw new URISyntaxException(var7);
      }

      PolicySet ps = this.store.readPolicySet(tid, "1.0");
      if (ps == null) {
         ps = this.converter.createTopPolicySet();
      }

      IdReference ref = pol.getReference();
      List members = ps.getPoliciesPolicySetsAndReferences();
      if (members == null || !members.contains(ref)) {
         List upMembers = new ArrayList();
         if (members != null) {
            upMembers.addAll(members);
         }

         upMembers.add(ref);
         this.store.setPolicySet(new PolicySet(ps.getId(), ps.getTarget(), ps.getCombiningAlgId(), ps.getDescription(), ps.getVersion(), ps.getDefaults(), ps.getCombinerParameters(), ps.getObligations(), upMembers, ps.getPolicyCombinerParameters(), ps.getPolicySetCombinerParameters()), 0);
      }

   }

   public boolean setPolicyCollectionEntry(String name, Resource resource, String expression) throws CreateException, ConflictException {
      if (!(this.store instanceof MetaDataPolicyStore)) {
         throw new CreateException("Invalid PolicyStore");
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;
         boolean created = true;

         URI policyId;
         try {
            policyId = new URI(this.converter.getPolicyId(resource));
         } catch (java.net.URISyntaxException var12) {
            throw new CreateException(var12);
         }

         if (this.log.isDebugEnabled()) {
            this.log.debug("setPolicyCollectionEntry: " + policyId);
         }

         try {
            PolicyMetaData pmd = mstore.getPolicyMetaDataEntry(policyId, "1.0");
            if (pmd != null) {
               created = false;
               String value = pmd.getValue();
               if (this.log.isDebugEnabled()) {
                  this.log.debug("setPolicyCollectionEntry existing metadata: " + value);
               }

               if (!"deploy".equals(pmd.getIndexValue("wlsCreatorInfo"))) {
                  String resId = resource != null ? resource.toString() : "NULL";
                  throw new ConflictException("Policy definition customized: " + resId);
               }
            }

            Policy policy = this.convertExpression(resource, expression);
            this.setPolicy(policy, true, name);
            return created;
         } catch (PolicyStoreException var10) {
            throw new CreateException(var10);
         } catch (URISyntaxException var11) {
            throw new CreateException(var11);
         }
      }
   }

   public void setPolicyImport(String resource, String expression, boolean deploy, String name, boolean overwrite) throws CreateException, ConflictException {
      if (!(this.store instanceof MetaDataPolicyStore)) {
         throw new CreateException("Invalid PolicyStore");
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;

         try {
            if (!overwrite) {
               URI policyId;
               try {
                  policyId = new URI(this.converter.getPolicyId(resource));
               } catch (java.net.URISyntaxException var9) {
                  throw new CreateException(var9);
               }

               if (this.log.isDebugEnabled()) {
                  this.log.debug("setPolicyImport: " + policyId);
               }

               PolicyMetaData pmd = mstore.getPolicyMetaDataEntry(policyId, "1.0");
               if (pmd != null) {
                  if (this.log.isDebugEnabled()) {
                     this.log.debug("setPolicyImport: policy exists, do not overwrite");
                  }

                  throw new ConflictException(resource);
               }
            }

            Policy policy = this.convertExpression(resource, expression);
            this.setPolicy(policy, deploy, name);
         } catch (PolicyStoreException var10) {
            throw new CreateException(var10);
         } catch (URISyntaxException var11) {
            throw new CreateException(var11);
         }
      }
   }

   public void removePolicy(Resource resource) throws RemoveException {
      this.removePolicy(resource != null ? resource.toString() : null);
   }

   public void removePolicy(String resource) throws RemoveException {
      try {
         URI id;
         try {
            id = new URI(this.converter.getPolicyId(resource));
         } catch (java.net.URISyntaxException var4) {
            throw new URISyntaxException(var4);
         }

         if (this.store.hasPolicy(id, "1.0")) {
            this.store.deletePolicy(id, "1.0");
            if (!(this.store instanceof EntitlementAwarePolicyStore)) {
               this.removeTopPolicySet(id, "1.0");
            }
         }

      } catch (DocumentParseException var5) {
         throw new RemoveException(var5);
      } catch (PolicyStoreException var6) {
         throw new RemoveException(var6);
      } catch (URISyntaxException var7) {
         throw new RemoveException(var7);
      }
   }

   public void removePolicySet(Resource resource) throws RemoveException {
      this.removePolicySet(resource != null ? resource.toString() : null);
   }

   public void removePolicySet(String resource) throws RemoveException {
      try {
         URI id;
         try {
            id = new URI(this.converter.getPolicyId(resource));
         } catch (java.net.URISyntaxException var4) {
            throw new URISyntaxException(var4);
         }

         if (this.store.hasPolicySet(id, "1.0")) {
            this.store.deletePolicySet(id, "1.0");
         }

      } catch (DocumentParseException var5) {
         throw new RemoveException(var5);
      } catch (PolicyStoreException var6) {
         throw new RemoveException(var6);
      } catch (URISyntaxException var7) {
         throw new RemoveException(var7);
      }
   }

   private void removeTopPolicySet(URI polId, String polVer) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      URI tid;
      try {
         tid = new URI(this.converter.getTopPolicyId());
      } catch (java.net.URISyntaxException var8) {
         throw new URISyntaxException(var8);
      }

      PolicySet ps = this.store.readPolicySet(tid, "1.0");
      if (ps != null) {
         IdReference ref = new PolicyIdReference(polId, polVer);
         List members = ps.getPoliciesPolicySetsAndReferences();
         if (members != null && members.contains(ref)) {
            List upMembers = new ArrayList();
            if (members != null) {
               upMembers.addAll(members);
            }

            upMembers.remove(ref);
            this.store.setPolicySet(new PolicySet(ps.getId(), ps.getTarget(), ps.getCombiningAlgId(), ps.getDescription(), ps.getVersion(), ps.getDefaults(), ps.getCombinerParameters(), ps.getObligations(), upMembers, ps.getPolicyCombinerParameters(), ps.getPolicySetCombinerParameters()));
         }
      }

   }

   public Set getResourceIds() throws NotFoundException {
      Set resourceIds = new HashSet();

      try {
         Set policies = this.store.readAllPolicies();
         if (policies != null) {
            Iterator var3 = policies.iterator();

            while(var3.hasNext()) {
               Policy p = (Policy)var3.next();
               String id = this.converter.getResourceId(p.getId().toString());
               if (id != null) {
                  resourceIds.add(id);
               }
            }
         }

         return resourceIds;
      } catch (DocumentParseException var6) {
         throw new NotFoundException(var6);
      } catch (PolicyStoreException var7) {
         throw new NotFoundException(var7);
      } catch (URISyntaxException var8) {
         throw new NotFoundException(var8);
      }
   }

   public Cursor listResources(final int max, final CursorFilter filter) throws NotFoundException {
      Set policies = null;

      try {
         policies = this.store.readAllPolicies();
      } catch (DocumentParseException var5) {
         throw new NotFoundException(var5);
      } catch (PolicyStoreException var6) {
         throw new NotFoundException(var6);
      } catch (URISyntaxException var7) {
         throw new NotFoundException(var7);
      }

      final Iterator it = policies != null ? policies.iterator() : null;
      return new CursorImpl(this.ui.getNext()) {
         private int returnedCount = 0;

         public boolean hasNext() {
            return this.next != null || this.search();
         }

         public CursorElement next() throws InvalidCursorException {
            if (this.next == null) {
               this.search();
               if (this.next == null) {
                  throw new InvalidCursorException(SecurityLogger.getDefAuthImplNoSearchResults());
               }
            }

            CursorElement nM = this.next;
            this.next = null;
            return nM;
         }

         private boolean search() {
            if ((max == 0 || this.returnedCount++ < max) && it != null) {
               Policy p;
               String id;
               do {
                  do {
                     if (!it.hasNext()) {
                        return false;
                     }

                     p = (Policy)it.next();
                     id = PolicyManager.this.converter.getResourceId(p.getId().toString());
                  } while(id == null);
               } while(filter != null && !filter.isValidResource(id));

               String collectionName = null;
               boolean isDeployment = false;
               if (PolicyManager.this.store instanceof MetaDataPolicyStore) {
                  MetaDataPolicyStore mstore = (MetaDataPolicyStore)PolicyManager.this.store;

                  try {
                     PolicyMetaData pmd = mstore.getPolicyMetaDataEntry(p.getId(), p.getVersion());
                     if (pmd != null) {
                        collectionName = pmd.getIndexValue("wlsCollectionName");
                        String deploy = pmd.getIndexValue("wlsCreatorInfo");
                        isDeployment = "deploy".equals(deploy);
                     }
                  } catch (Exception var8) {
                     if (PolicyManager.this.log.isDebugEnabled()) {
                        PolicyManager.this.log.debug("listResources.CursorImpl.search() metadata: " + var8.toString());
                     }
                  }
               }

               this.next = new CursorElement(id, (String)null, p.getDescription(), (String)null, isDeployment, collectionName);
               return true;
            } else {
               return false;
            }
         }
      };
   }

   public Collection getPredicates() {
      return this.converter.getPredicates();
   }

   public void createPolicyCollectionInfo(String name, String version, String timeStamp) throws CreateException {
      if (!(this.store instanceof MetaDataPolicyStore)) {
         throw new CreateException("Invalid PolicyStore");
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;
         if (this.log.isDebugEnabled()) {
            this.log.debug("createPolicyCollectionInfo: " + name + " : " + version + " : " + timeStamp);
         }

         String key = "PolicyCollectionInfo#" + name;
         String xmlName = XMLEscaper.escapeXMLChars(name);
         String value = "<PolicyCollectionInfo Name=\"" + xmlName + "\" " + "TimeStamp=\"" + timeStamp + "\" " + "Version=\"" + version + "\"/>";

         try {
            mstore.setMetaDataEntry(key, value);
            if (this.log.isDebugEnabled()) {
               this.log.debug("createPolicyCollectionInfo: " + value);
            }

         } catch (PolicyStoreException var9) {
            throw new CreateException(var9);
         }
      }
   }

   public StoredPolicyCollectionInfo getPolicyCollectionInfo(String name) {
      if (!(this.store instanceof MetaDataPolicyStore)) {
         return null;
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;
         StoredPolicyCollectionInfo pci = null;
         String value = null;
         String key = "PolicyCollectionInfo#" + name;
         if (this.log.isDebugEnabled()) {
            this.log.debug("getPolicyCollectionInfo: " + key);
         }

         try {
            value = mstore.getMetaDataEntry(key);
            pci = this.getPolicyCollectionInfoValue(value);
         } catch (PolicyStoreException var7) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("getPolicyCollectionInfo:" + var7.toString(), var7);
            }
         } catch (RuntimeException var8) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("getPolicyCollectionInfo run-time:" + var8.toString(), var8);
            }
         }

         return pci;
      }
   }

   private StoredPolicyCollectionInfo getPolicyCollectionInfoValue(String value) {
      if (value == null) {
         return null;
      } else {
         StoredPolicyCollectionInfo pci = null;
         int nameIdx = value.indexOf("Name=\"");
         int start = nameIdx + PCI_XML_NAME_LEN;
         if (nameIdx != -1) {
            int end = value.indexOf(34, start);
            String xmlName = value.substring(start, end);
            String sName = XMLEscaper.unescapeXMLChars(xmlName);
            start = value.indexOf("TimeStamp=\"", end + 1);
            start += PCI_XML_TIMESTAMP_LEN;
            end = value.indexOf(34, start);
            String sTime = value.substring(start, end);
            start = value.indexOf("Version=\"", end + 1);
            start += PCI_XML_VERSION_LEN;
            end = value.indexOf(34, start);
            String sVer = value.substring(start, end);
            pci = new StoredPolicyCollectionInfo(sName, sVer, sTime);
            if (this.log.isDebugEnabled()) {
               this.log.debug("getPolicyCollectionInfoValue: " + sName + " : " + sVer + " : " + sTime);
            }
         }

         return pci;
      }
   }

   public List getAllPolicyCollectionInfo() {
      List result = null;
      List values = this.getAllPolicyCollectionInfoValues();
      if (values != null) {
         result = new LinkedList();
         Iterator var3 = values.iterator();

         while(var3.hasNext()) {
            String value = (String)var3.next();
            StoredPolicyCollectionInfo pci = this.getPolicyCollectionInfoValue(value);
            if (pci != null) {
               result.add(pci);
            }
         }
      }

      return result;
   }

   public List getAllPolicyCollectionInfoValues() {
      List result = null;
      if (this.store instanceof MetaDataPolicyStore) {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;

         try {
            result = mstore.readAllMetaDataEntries();
         } catch (PolicyStoreException var4) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("getAllPolicyCollectionInfoValues:" + var4.toString(), var4);
            }
         }
      }

      return result;
   }

   public Set listPolicyCollection(String collectionName) throws NotFoundException {
      if (!(this.store instanceof MetaDataPolicyStore)) {
         throw new NotFoundException("Invalid PolicyStore");
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;
         if (this.log.isDebugEnabled()) {
            this.log.debug("listPolicyCollection: '" + collectionName + "'");
         }

         Set list = new HashSet();

         try {
            Map index = new HashMap();
            index.put("wlsCollectionName", collectionName);
            index.put("wlsCreatorInfo", "deploy");
            PolicyMetaData md = new PolicyMetaDataImpl("WLSPolicyInfo", (String)null, index);
            List policies = mstore.readPolicy(md);
            if (policies != null) {
               Iterator var7 = policies.iterator();

               while(var7.hasNext()) {
                  PolicyInfo info = (PolicyInfo)var7.next();
                  Policy pol = info.getPolicy();
                  String resId = this.converter.getResourceId(pol.getId().toString());
                  if (resId != null) {
                     if (this.log.isDebugEnabled()) {
                        this.log.debug("listPolicyCollection policy id: '" + pol.getId() + "'");
                     }

                     list.add(resId);
                  }
               }
            }

            return list;
         } catch (DocumentParseException var11) {
            throw new NotFoundException(var11);
         } catch (PolicyStoreException var12) {
            throw new NotFoundException(var12);
         } catch (URISyntaxException var13) {
            throw new NotFoundException(var13);
         }
      }
   }

   public Pair listPolicies(String searchId, String filterId, boolean deploy, int searchType) throws NotFoundException {
      if (!(this.store instanceof MetaDataPolicyStore)) {
         throw new NotFoundException("Invalid PolicyStore");
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;
         Set pols = new HashSet();
         Set ps = new HashSet();
         if (this.log.isDebugEnabled()) {
            this.log.debug("listPolicies: '" + searchId + "':'" + filterId + "'");
         }

         try {
            Map index = new HashMap();
            String id = getSearchResourceScope(searchId, searchType);
            index.put("resourceScope", id);
            if (deploy) {
               index.put("wlsCreatorInfo", "deploy");
            }

            PolicyMetaData md = new PolicyMetaDataImpl("WLSPolicyInfo", (String)null, index);
            List policies = mstore.readPolicy(md);
            if (policies != null) {
               Iterator var12 = policies.iterator();

               while(var12.hasNext()) {
                  PolicyInfo info = (PolicyInfo)var12.next();
                  Policy pol = info.getPolicy();
                  boolean isEntitlement = true;
                  String resId = this.converter.getResourceId(pol.getId().toString());
                  if (resId == null) {
                     isEntitlement = false;
                     resId = this.rmu.getTargetResource(pol.getTarget());
                  }

                  if (resourceMatches(resId, filterId)) {
                     if (this.log.isDebugEnabled()) {
                        this.log.debug("listPolicies policy id: '" + pol.getId() + "'");
                     }

                     pols.add(resId);
                  }
               }
            }

            List pSets = mstore.readPolicySet(md);
            if (pSets != null) {
               Iterator var22 = pSets.iterator();

               while(var22.hasNext()) {
                  PolicySetInfo info = (PolicySetInfo)var22.next();
                  PolicySet pol = info.getPolicySet();
                  boolean isEntitlement = true;
                  String resId = this.rmu.getTargetResource(pol.getTarget());
                  if (resourceMatches(resId, filterId)) {
                     if (this.log.isDebugEnabled()) {
                        this.log.debug("listPolicies policy set id: '" + pol.getId() + "'");
                     }

                     ps.add(resId);
                  }
               }
            }
         } catch (DocumentParseException var18) {
            throw new NotFoundException(var18);
         } catch (PolicyStoreException var19) {
            throw new NotFoundException(var19);
         } catch (URISyntaxException var20) {
            throw new NotFoundException(var20);
         }

         return new Pair(pols, ps);
      }
   }

   public void copyPolicies(String searchId, String filterId, String sourceId, String destId) throws CreateException {
      if (!(this.store instanceof MetaDataPolicyStore)) {
         throw new CreateException("Invalid PolicyStore");
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;
         if (this.log.isDebugEnabled()) {
            this.log.debug("copyPolicy: '" + sourceId + "':'" + destId + "'");
         }

         try {
            Map index = new HashMap();
            String id = getSearchResourceScope(searchId, 2);
            index.put("resourceScope", id);
            PolicyMetaData md = new PolicyMetaDataImpl("WLSPolicyInfo", (String)null, index);
            Map copiedPolicies = new HashMap();
            List policies = mstore.readPolicy(md);
            String resId;
            PolicyMetaData pmd;
            String resource;
            if (policies != null) {
               Iterator var11 = policies.iterator();

               label98:
               while(true) {
                  while(true) {
                     PolicyInfo info;
                     Policy pol;
                     boolean isEntitlement;
                     do {
                        if (!var11.hasNext()) {
                           break label98;
                        }

                        info = (PolicyInfo)var11.next();
                        pol = info.getPolicy();
                        isEntitlement = true;
                        resId = this.converter.getResourceId(pol.getId().toString());
                        if (resId == null) {
                           isEntitlement = false;
                           resId = this.rmu.getTargetResource(pol.getTarget());
                        }
                     } while(!resourceMatches(resId, filterId));

                     pmd = info.getMetaDataEntry();
                     if (pmd != null && "deploy".equals(pmd.getIndexValue("wlsCreatorInfo"))) {
                        if (this.log.isDebugEnabled()) {
                           this.log.debug("copyPolicy skipped: " + pol.getId());
                        }
                     } else {
                        if (this.log.isDebugEnabled()) {
                           this.log.debug("copyPolicy id: '" + pol.getId() + "'");
                        }

                        resource = getUpdatedResource(resId, sourceId, destId);
                        Policy p = new Policy(isEntitlement ? getUpdatedId(pol.getId(), sourceId, destId) : pol.getId(), this.rmu.updateTarget(pol.getTarget(), resId, resource), pol.getCombiningAlgId(), pol.getDescription(), isEntitlement ? pol.getVersion() : getUpdatedVersion(pol.getVersion()), pol.getDefaults(), pol.getCombinerParameters(), pol.getObligations(), pol.getRules(), pol.getRuleCombinerParameters(), pol.getVariableDefinitions());
                        copiedPolicies.put(pol.getReference(), p.getReference());
                        if (isEntitlement) {
                           if (mstore instanceof EntitlementAwarePolicyStore) {
                              mstore.setPolicy(p, 3, pmd);
                           } else {
                              mstore.setPolicy(p, 1, pmd);
                              this.setTopPolicySet(p);
                           }
                        } else {
                           mstore.setPolicy(p, mstore.getPolicyStatus(pol.getId(), pol.getVersion()), pmd);
                        }
                     }
                  }
               }
            }

            List pSets = mstore.readPolicySet(md);
            if (pSets != null) {
               Iterator var23 = pSets.iterator();

               while(true) {
                  while(true) {
                     PolicySetInfo info;
                     PolicySet pol;
                     do {
                        if (!var23.hasNext()) {
                           return;
                        }

                        info = (PolicySetInfo)var23.next();
                        pol = info.getPolicySet();
                        resId = this.rmu.getTargetResource(pol.getTarget());
                     } while(!resourceMatches(resId, filterId));

                     pmd = info.getMetaDataEntry();
                     if (pmd != null && "deploy".equals(pmd.getIndexValue("wlsCreatorInfo"))) {
                        if (this.log.isDebugEnabled()) {
                           this.log.debug("copyPolicy skipped (set): " + pol.getId());
                        }
                     } else {
                        if (this.log.isDebugEnabled()) {
                           this.log.debug("copyPolicy id (set): '" + pol.getId() + "'");
                        }

                        resource = getUpdatedResource(resId, sourceId, destId);
                        PolicySet p = new PolicySet(pol.getId(), this.rmu.updateTarget(pol.getTarget(), resId, resource), pol.getCombiningAlgId(), pol.getDescription(), getUpdatedVersion(pol.getVersion()), pol.getDefaults(), pol.getCombinerParameters(), pol.getObligations(), updateReferences(pol.getPoliciesPolicySetsAndReferences(), filterId, this.converter, this.rmu, copiedPolicies, mstore), pol.getPolicyCombinerParameters(), pol.getPolicySetCombinerParameters());
                        mstore.setPolicySet(p, mstore.getPolicySetStatus(pol.getId(), pol.getVersion()), pmd);
                     }
                  }
               }
            }
         } catch (DocumentParseException var19) {
            throw new CreateException(var19);
         } catch (PolicyStoreException var20) {
            throw new CreateException(var20);
         } catch (URISyntaxException var21) {
            throw new CreateException(var21);
         }
      }
   }

   protected static boolean resourceMatches(String resource, String filter) {
      boolean match = false;
      if (resource != null && filter != null) {
         int idx = resource.indexOf(filter);
         if (idx != -1) {
            int filterEnd = idx + filter.length();
            if (resource.length() <= filterEnd || resource.charAt(filterEnd) == ',') {
               match = true;
            }
         }
      }

      return match;
   }

   protected static String getUpdatedResource(String id, String source, String dest) {
      String s = id.toString();
      int start = s.indexOf(source);
      if (start == -1) {
         return id;
      } else {
         StringBuffer sb = new StringBuffer(s);
         int end = start + source.length();
         sb.replace(start, end, dest);
         return sb.toString();
      }
   }

   protected static URI getUpdatedId(URI id, String source, String dest) throws URISyntaxException {
      try {
         source = ResourcePolicyIdUtil.getEscaper().escapeString(source);
         dest = ResourcePolicyIdUtil.getEscaper().escapeString(dest);
         String s = id.toString();
         int start = s.indexOf(source);
         if (start == -1) {
            return id;
         } else {
            StringBuffer sb = new StringBuffer(s);
            int end = start + source.length();
            sb.replace(start, end, dest);
            return new URI(sb.toString());
         }
      } catch (java.net.URISyntaxException var7) {
         throw new URISyntaxException(var7);
      }
   }

   protected static String getUpdatedVersion(String version) {
      int idx = version.lastIndexOf(46);
      if (idx > 0) {
         String last = version.substring(idx + 1);
         int next = Integer.parseInt(last) + 1;
         return version.substring(0, idx + 1) + String.valueOf(next);
      } else {
         return String.valueOf(Integer.parseInt(version) + 1);
      }
   }

   protected static List updateReferences(List members, String filterId, EntitlementConverter converter, ResourceMatchUtil rmu, Map copiedReferences, MetaDataPolicyStore mstore) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      List x = new ArrayList();
      if (members != null) {
         Iterator var7 = members.iterator();

         while(true) {
            while(var7.hasNext()) {
               PolicySetMember m = (PolicySetMember)var7.next();
               IdReference i;
               if (m instanceof PolicyIdReference) {
                  i = (IdReference)copiedReferences.get(m);
                  if (i != null) {
                     x.add(i);
                     continue;
                  }
               } else if (m instanceof PolicySetIdReference) {
                  i = (IdReference)m;
                  if (i.getEarliestVersion() == null && i.getLatestVersion() == null) {
                     PolicySet pol = mstore.readPolicySet(i.getReference(), i.getVersion());
                     String resId = converter.getResourceId(pol.getId().toString());
                     if (resId == null) {
                        try {
                           resId = rmu.getTargetResource(pol.getTarget());
                        } catch (MultipleResourceTargetException var13) {
                        }
                     }

                     if (resourceMatches(resId, filterId)) {
                        x.add(new PolicySetIdReference(pol.getId(), getUpdatedVersion(pol.getVersion())));
                        continue;
                     }
                  }
               }

               x.add(m);
            }

            return x.isEmpty() ? null : x;
         }
      } else {
         return x.isEmpty() ? null : x;
      }
   }

   protected static String getSearchResourceScope(String resource, int searchType) {
      if (resource == null) {
         return "*";
      } else {
         switch (searchType) {
            case 0:
               return resource + "*";
            case 1:
               return "*" + resource;
            case 2:
               return "*" + resource + "*";
            default:
               throw new IllegalArgumentException("Invalid serach type: " + searchType);
         }
      }
   }

   public static void checkResourceId(String resourceId) {
      if (resourceId == null || resourceId.trim().length() == 0) {
         throw new IllegalArgumentException(SecurityLogger.getNoResourceIdentifier());
      }
   }
}

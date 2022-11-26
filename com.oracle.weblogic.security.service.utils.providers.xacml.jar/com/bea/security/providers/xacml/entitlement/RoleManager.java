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
import com.bea.security.providers.xacml.ConflictException;
import com.bea.security.providers.xacml.Cursor;
import com.bea.security.providers.xacml.StoredRoleCollectionInfo;
import com.bea.security.providers.xacml.UniqueIdentifier;
import com.bea.security.providers.xacml.XMLEscaper;
import com.bea.security.providers.xacml.store.MetaDataPolicyStore;
import com.bea.security.providers.xacml.store.PolicyMetaDataImpl;
import com.bea.security.xacml.PolicyInfo;
import com.bea.security.xacml.PolicyMetaData;
import com.bea.security.xacml.PolicySetInfo;
import com.bea.security.xacml.PolicyStoreException;
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

public class RoleManager {
   private static final String WLS_ROLE_INFO = "WLSRoleInfo";
   private static final String WLS_XML_START = "<WLSRoleInfo ";
   public static final String PCI_KEY = "RoleCollectionInfo#";
   private static final String PCI_XML_START = "<RoleCollectionInfo ";
   private PolicyStore store;
   private EntitlementConverter converter;
   private ResourceMatchUtil rmu;
   private UniqueIdentifier ui = new UniqueIdentifier("rm");
   private LoggerSpi log;

   public RoleManager(PolicyStore store, EntitlementConverter converter, LoggerSpi log) throws URISyntaxException {
      this.store = store;
      this.converter = converter;
      this.log = log;
      this.rmu = new ResourceMatchUtil();
   }

   public boolean hasRole(Resource resource, String role) throws NotFoundException {
      return this.hasRole(resource != null ? resource.toString() : null, role);
   }

   public boolean hasRole(String resource, String role) throws NotFoundException {
      try {
         URI id;
         try {
            id = new URI(this.converter.getPolicyId(resource, role));
         } catch (java.net.URISyntaxException var5) {
            throw new URISyntaxException(var5);
         }

         return this.store.hasPolicy(id, "1.0");
      } catch (DocumentParseException var6) {
         throw new NotFoundException(var6);
      } catch (PolicyStoreException var7) {
         throw new NotFoundException(var7);
      } catch (URISyntaxException var8) {
         throw new NotFoundException(var8);
      }
   }

   public String getRole(Resource resource, String role) throws NotFoundException {
      return this.getRole(resource != null ? resource.toString() : null, role);
   }

   public String getRole(String resource, String role) throws NotFoundException {
      try {
         URI id;
         try {
            id = new URI(this.converter.getPolicyId(resource, role));
         } catch (java.net.URISyntaxException var6) {
            throw new URISyntaxException(var6);
         }

         Policy pol = this.store.readPolicy(id, "1.0");
         if (pol != null) {
            List rules = pol.getRules();
            if (rules != null && !rules.isEmpty()) {
               return this.getRoleFromDescription(pol.getDescription());
            }
         }

         throw new NotFoundException(SecurityLogger.getFailedToSetResource());
      } catch (DocumentParseException var7) {
         throw new NotFoundException(SecurityLogger.getFailedToSetResource(), var7);
      } catch (PolicyStoreException var8) {
         throw new NotFoundException(SecurityLogger.getFailedToSetResource(), var8);
      } catch (URISyntaxException var9) {
         throw new NotFoundException(SecurityLogger.getFailedToSetResource(), var9);
      }
   }

   public String getRoleAuxiliary(Resource resource, String role) throws NotFoundException {
      return this.getRoleAuxiliary(resource != null ? resource.toString() : null, role);
   }

   public String getRoleAuxiliary(String resource, String role) throws NotFoundException {
      try {
         URI id;
         try {
            id = new URI(this.converter.getPolicyId(resource, role));
         } catch (java.net.URISyntaxException var6) {
            throw new URISyntaxException(var6);
         }

         Policy pol = this.store.readPolicy(id, "1.0");
         if (pol != null) {
            List rules = pol.getRules();
            if (rules != null && !rules.isEmpty()) {
               return this.getAuxiliaryFromDescription(pol.getDescription());
            }
         }

         throw new NotFoundException(SecurityLogger.getFailedToSetResource());
      } catch (DocumentParseException var7) {
         throw new NotFoundException(SecurityLogger.getFailedToSetResource(), var7);
      } catch (PolicyStoreException var8) {
         throw new NotFoundException(SecurityLogger.getFailedToSetResource(), var8);
      } catch (URISyntaxException var9) {
         throw new NotFoundException(SecurityLogger.getFailedToSetResource(), var9);
      }
   }

   private String getRoleFromDescription(String description) {
      if (description != null) {
         int idx = description.indexOf("<aux:ent>");
         if (idx != -1) {
            idx += "<aux:ent>".length();
            int eidx = description.indexOf("</aux:ent>", idx);
            if (eidx != -1) {
               return description.substring(idx, eidx);
            }
         }

         return description;
      } else {
         return "";
      }
   }

   private String getAuxiliaryFromDescription(String description) {
      if (description != null) {
         int idx = description.indexOf("<aux:data>");
         if (idx != -1) {
            idx += "<aux:data>".length();
            int eidx = description.indexOf("</aux:data>", idx);
            if (eidx != -1) {
               return description.substring(idx, eidx);
            }
         }
      }

      return null;
   }

   public void setRole(Resource resource, String role, String expression, String auxiliary) throws CreateException {
      Policy policy = this.convertExpression(resource, role, expression, auxiliary);
      this.setRole(policy, role, true, (String)null);
   }

   private Policy convertExpression(Resource resource, String role, String expression, String auxiliary) throws CreateException {
      String resId = resource != null ? resource.toString() : null;
      return this.convertExpression(resId, role, expression, auxiliary);
   }

   public void setRole(String resource, String role, String expression, String auxiliary) throws CreateException {
      Policy policy = this.convertExpression(resource, role, expression, auxiliary);
      this.setRole(policy, role, false, (String)null);
   }

   private Policy convertExpression(String resource, String role, String expression, String auxiliary) throws CreateException {
      try {
         if (auxiliary == null) {
            try {
               auxiliary = this.getRoleAuxiliary(resource, role);
            } catch (NotFoundException var6) {
            }
         }

         Policy pol = this.converter.convertRoleExpression(resource, role, expression, auxiliary);
         if (this.log.isDebugEnabled()) {
            this.log.debug("Converted role policy:\n" + pol);
         }

         return pol;
      } catch (DocumentParseException var7) {
         throw new CreateException(var7);
      } catch (URISyntaxException var8) {
         throw new CreateException(var8);
      }
   }

   public void setRoleImport(String resource, String role, String expression, String auxiliary, boolean deploy, String name, boolean overwrite) throws CreateException, ConflictException {
      if (resource.equals("null")) {
         resource = "";
      }

      if (!(this.store instanceof MetaDataPolicyStore)) {
         throw new CreateException("Invalid PolicyStore");
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;

         try {
            if (!overwrite) {
               URI policyId;
               try {
                  policyId = new URI(this.converter.getPolicyId(resource, role));
               } catch (java.net.URISyntaxException var11) {
                  throw new CreateException(var11);
               }

               if (this.log.isDebugEnabled()) {
                  this.log.debug("setRoleImport: " + policyId);
               }

               PolicyMetaData pmd = mstore.getPolicyMetaDataEntry(policyId, "1.0");
               if (pmd != null) {
                  if (this.log.isDebugEnabled()) {
                     this.log.debug("setRoleImport: role exists, do not overwrite");
                  }

                  throw new ConflictException(role + " : " + resource);
               }
            }

            Policy policy = this.convertExpression(resource, role, expression, auxiliary);
            this.setRole(policy, role, deploy, name);
         } catch (PolicyStoreException var12) {
            throw new CreateException(var12);
         } catch (URISyntaxException var13) {
            throw new CreateException(var13);
         }
      }
   }

   private void setRole(Policy pol, String role, boolean deploy, String name) throws CreateException {
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
               value = "<WLSRoleInfo wlsCreatorInfo=\"" + createInfo + "\" " + "wlsCollectionName=\"" + xmlName + "\"/>";
            } else {
               value = "<WLSRoleInfo wlsCreatorInfo=\"" + createInfo + "\"/>";
            }

            PolicyMetaData md = new PolicyMetaDataImpl("WLSRoleInfo", value, index);
            if (mstore instanceof EntitlementAwarePolicyStore) {
               mstore.setPolicy(pol, 3, md);
            } else {
               mstore.setPolicy(pol, 1, md);
               this.setTopPolicySet(pol, role);
            }
         } else if (this.store instanceof EntitlementAwarePolicyStore) {
            this.store.setPolicy(pol, 3);
         } else {
            this.store.setPolicy(pol, 1);
            this.setTopPolicySet(pol, role);
         }

      } catch (DocumentParseException var10) {
         throw new CreateException(var10);
      } catch (PolicyStoreException var11) {
         throw new CreateException(var11);
      } catch (URISyntaxException var12) {
         throw new CreateException(var12);
      }
   }

   private void setTopPolicySet(Policy pol, String role) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      URI tid;
      try {
         tid = new URI(this.converter.getTopPolicyId(role));
      } catch (java.net.URISyntaxException var8) {
         throw new URISyntaxException(var8);
      }

      PolicySet ps = this.store.readPolicySet(tid, "1.0");
      if (ps == null) {
         ps = this.converter.createTopPolicySet(role);
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

   public void setRoleAuxiliary(Resource resource, String role, String auxiliary) throws CreateException {
      this.setRoleAuxiliary(resource != null ? resource.toString() : null, role, auxiliary);
   }

   public void setRoleAuxiliary(String resource, String role, String auxiliary) throws CreateException {
      try {
         String expression;
         try {
            expression = this.getRole(resource, role);
         } catch (NotFoundException var6) {
            expression = "~Grp(everyone)";
         }

         Policy pol = this.converter.convertRoleExpression(resource, role, expression, auxiliary);
         if (this.store instanceof EntitlementAwarePolicyStore) {
            this.store.setPolicy(pol, 3);
         } else {
            this.store.setPolicy(pol, 1);
            this.setTopPolicySet(pol, role);
         }

      } catch (DocumentParseException var7) {
         throw new CreateException(var7);
      } catch (PolicyStoreException var8) {
         throw new CreateException(var8);
      } catch (URISyntaxException var9) {
         throw new CreateException(var9);
      }
   }

   public boolean setRoleCollectionEntry(String name, Resource resource, String role, String expression) throws CreateException, ConflictException {
      if (!(this.store instanceof MetaDataPolicyStore)) {
         throw new CreateException("Invalid PolicyStore");
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;
         boolean created = true;

         URI policyId;
         try {
            policyId = new URI(this.converter.getPolicyId(resource, role));
         } catch (java.net.URISyntaxException var13) {
            throw new CreateException(var13);
         }

         if (this.log.isDebugEnabled()) {
            this.log.debug("setRoleCollectionEntry: " + policyId);
         }

         try {
            PolicyMetaData pmd = mstore.getPolicyMetaDataEntry(policyId, "1.0");
            if (pmd != null) {
               created = false;
               String value = pmd.getValue();
               if (this.log.isDebugEnabled()) {
                  this.log.debug("setRoleCollectionEntry existing metadata: " + value);
               }

               if (!"deploy".equals(pmd.getIndexValue("wlsCreatorInfo"))) {
                  String resId = resource != null ? resource.toString() : "NULL";
                  throw new ConflictException("Role definition customized: " + role + " : " + resId);
               }
            }

            Policy policy = this.convertExpression((Resource)resource, role, expression, (String)null);
            this.setRole(policy, role, true, name);
            return created;
         } catch (PolicyStoreException var11) {
            throw new CreateException(var11);
         } catch (URISyntaxException var12) {
            throw new CreateException(var12);
         }
      }
   }

   public void removeRole(Resource resource, String role) throws RemoveException {
      this.removeRole(resource != null ? resource.toString() : null, role);
   }

   public void removeRole(String resource, String role) throws RemoveException {
      try {
         URI id;
         try {
            id = new URI(this.converter.getPolicyId(resource, role));
         } catch (java.net.URISyntaxException var5) {
            throw new URISyntaxException(var5);
         }

         if (this.store.hasPolicy(id, "1.0")) {
            this.store.deletePolicy(id, "1.0");
            if (!(this.store instanceof EntitlementAwarePolicyStore)) {
               this.removeTopPolicySet(id, "1.0", role);
            }
         }

      } catch (DocumentParseException var6) {
         throw new RemoveException(var6);
      } catch (PolicyStoreException var7) {
         throw new RemoveException(var7);
      } catch (URISyntaxException var8) {
         throw new RemoveException(var8);
      }
   }

   public void removeRoleSet(Resource resource, String role) throws RemoveException {
      this.removeRoleSet(resource != null ? resource.toString() : null, role);
   }

   public void removeRoleSet(String resource, String role) throws RemoveException {
      try {
         URI id;
         try {
            id = new URI(this.converter.getPolicyId(resource, role));
         } catch (java.net.URISyntaxException var5) {
            throw new URISyntaxException(var5);
         }

         if (this.store.hasPolicySet(id, "1.0")) {
            this.store.deletePolicySet(id, "1.0");
         }

      } catch (DocumentParseException var6) {
         throw new RemoveException(var6);
      } catch (PolicyStoreException var7) {
         throw new RemoveException(var7);
      } catch (URISyntaxException var8) {
         throw new RemoveException(var8);
      }
   }

   private void removeTopPolicySet(URI polId, String polVer, String role) throws DocumentParseException, PolicyStoreException, URISyntaxException {
      URI tid;
      try {
         tid = new URI(this.converter.getTopPolicyId(role));
      } catch (java.net.URISyntaxException var9) {
         throw new URISyntaxException(var9);
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
            if (upMembers.isEmpty()) {
               this.store.deletePolicySet(tid, "1.0");
            } else {
               this.store.setPolicySet(new PolicySet(ps.getId(), ps.getTarget(), ps.getCombiningAlgId(), ps.getDescription(), ps.getVersion(), ps.getDefaults(), ps.getCombinerParameters(), ps.getObligations(), upMembers, ps.getPolicyCombinerParameters(), ps.getPolicySetCombinerParameters()));
            }
         }
      }

   }

   public Set getRoleResourceIds() throws NotFoundException {
      Set resourceIds = new HashSet();

      try {
         Set policies = this.store.readAllPolicies();
         if (policies != null) {
            Iterator var3 = policies.iterator();

            while(var3.hasNext()) {
               Policy p = (Policy)var3.next();
               ResourcePolicyIdUtil.RoleResource id = this.converter.getRoleResourceId(p.getId().toString());
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

   public Cursor listRoles(final int max, final CursorFilter filter) throws NotFoundException {
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

      if (this.log.isDebugEnabled()) {
         this.log.debug("listRoles() found unfiltered policy count: " + (policies != null ? policies.size() : 0));
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
               String res;
               String role;
               boolean isMatch;
               do {
                  ResourcePolicyIdUtil.RoleResource id;
                  do {
                     if (!it.hasNext()) {
                        return false;
                     }

                     p = (Policy)it.next();
                     id = RoleManager.this.converter.getRoleResourceId(p.getId().toString());
                  } while(id == null);

                  res = id.getResourceId();
                  role = id.getRole();
                  isMatch = filter == null || filter.isValidRole(res, role);
               } while(!isMatch);

               String collectionName = null;
               boolean isDeployment = false;
               if (RoleManager.this.store instanceof MetaDataPolicyStore) {
                  MetaDataPolicyStore mstore = (MetaDataPolicyStore)RoleManager.this.store;

                  try {
                     PolicyMetaData pmd = mstore.getPolicyMetaDataEntry(p.getId(), p.getVersion());
                     if (pmd != null) {
                        collectionName = pmd.getIndexValue("wlsCollectionName");
                        String deploy = pmd.getIndexValue("wlsCreatorInfo");
                        isDeployment = "deploy".equals(deploy);
                     }
                  } catch (Exception var11) {
                     if (RoleManager.this.log.isDebugEnabled()) {
                        RoleManager.this.log.debug("listRoles.CursorImpl.search() metadata: " + var11.toString());
                     }
                  }
               }

               this.next = new CursorElement(res, role, RoleManager.this.getRoleFromDescription(p.getDescription()), RoleManager.this.getAuxiliaryFromDescription(p.getDescription()), isDeployment, collectionName);
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

   public Set listRoleCollection(String collectionName) throws NotFoundException {
      if (!(this.store instanceof MetaDataPolicyStore)) {
         throw new NotFoundException("Invalid PolicyStore");
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;
         if (this.log.isDebugEnabled()) {
            this.log.debug("listRoleCollection: '" + collectionName + "'");
         }

         Set list = new HashSet();

         try {
            Map index = new HashMap();
            index.put("wlsCollectionName", collectionName);
            index.put("wlsCreatorInfo", "deploy");
            PolicyMetaData md = new PolicyMetaDataImpl("WLSRoleInfo", (String)null, index);
            List policies = mstore.readPolicy(md);
            if (policies != null) {
               Iterator var7 = policies.iterator();

               while(var7.hasNext()) {
                  PolicyInfo info = (PolicyInfo)var7.next();
                  Policy pol = info.getPolicy();
                  ResourcePolicyIdUtil.RoleResource r = this.converter.getRoleResourceId(pol.getId().toString());
                  if (r != null) {
                     if (this.log.isDebugEnabled()) {
                        this.log.debug("listRoleCollection policy id: '" + pol.getId() + "'");
                     }

                     list.add(r);
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

   public Pair listRoles(String searchId, String filterId, boolean deploy, int searchType) throws NotFoundException {
      if (!(this.store instanceof MetaDataPolicyStore)) {
         throw new NotFoundException("Invalid PolicyStore");
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;
         Set pols = new HashSet();
         Set ps = new HashSet();
         if (this.log.isDebugEnabled()) {
            this.log.debug("listRoles: '" + searchId + "':'" + filterId + "'");
         }

         try {
            Map index = new HashMap();
            String id = PolicyManager.getSearchResourceScope(searchId, searchType);
            index.put("resourceScope", id);
            if (deploy) {
               index.put("wlsCreatorInfo", "deploy");
            }

            PolicyMetaData md = new PolicyMetaDataImpl("WLSRoleInfo", (String)null, index);
            List policies = mstore.readPolicy(md);
            boolean isEntitlement;
            if (policies != null) {
               Iterator var12 = policies.iterator();

               while(var12.hasNext()) {
                  PolicyInfo info = (PolicyInfo)var12.next();
                  Policy pol = info.getPolicy();
                  isEntitlement = true;
                  ResourcePolicyIdUtil.RoleResource r = this.converter.getRoleResourceId(pol.getId().toString());
                  String resId;
                  if (r != null) {
                     resId = r.getResourceId();
                  } else {
                     isEntitlement = false;
                     resId = this.rmu.getTargetResource(pol.getTarget());
                  }

                  if (PolicyManager.resourceMatches(resId, filterId)) {
                     if (this.log.isDebugEnabled()) {
                        this.log.debug("listRoles policy id: '" + pol.getId() + "'");
                     }

                     pols.add(r != null ? r : new ResourcePolicyIdUtil.RoleResource(resId, (String)null));
                  }
               }
            }

            List pSets = mstore.readPolicySet(md);
            if (pSets != null) {
               Iterator var22 = pSets.iterator();

               while(var22.hasNext()) {
                  PolicySetInfo info = (PolicySetInfo)var22.next();
                  PolicySet pol = info.getPolicySet();
                  isEntitlement = true;
                  String resId = this.rmu.getTargetResource(pol.getTarget());
                  if (PolicyManager.resourceMatches(resId, filterId)) {
                     if (this.log.isDebugEnabled()) {
                        this.log.debug("listPolicy policy set id: '" + pol.getId() + "'");
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

   public void copyRoles(String searchId, String filterId, String sourceId, String destId) throws CreateException {
      if (!(this.store instanceof MetaDataPolicyStore)) {
         throw new CreateException("Invalid PolicyStore");
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;
         if (this.log.isDebugEnabled()) {
            this.log.debug("copyRoles: '" + sourceId + "':'" + destId + "'");
         }

         try {
            Map index = new HashMap();
            String id = PolicyManager.getSearchResourceScope(searchId, 2);
            index.put("resourceScope", id);
            PolicyMetaData md = new PolicyMetaDataImpl("WLSRoleInfo", (String)null, index);
            Map copiedPolicies = new HashMap();
            List policies = mstore.readPolicy(md);
            if (policies != null) {
               Iterator var11 = policies.iterator();

               label99:
               while(true) {
                  while(true) {
                     PolicyInfo info;
                     Policy pol;
                     String resId;
                     boolean isEntitlement;
                     ResourcePolicyIdUtil.RoleResource r;
                     do {
                        if (!var11.hasNext()) {
                           break label99;
                        }

                        info = (PolicyInfo)var11.next();
                        pol = info.getPolicy();
                        isEntitlement = true;
                        r = this.converter.getRoleResourceId(pol.getId().toString());
                        if (r != null) {
                           resId = r.getResourceId();
                        } else {
                           isEntitlement = false;
                           resId = this.rmu.getTargetResource(pol.getTarget());
                        }
                     } while(!PolicyManager.resourceMatches(resId, filterId));

                     PolicyMetaData pmd = info.getMetaDataEntry();
                     if (pmd != null && "deploy".equals(pmd.getIndexValue("wlsCreatorInfo"))) {
                        if (this.log.isDebugEnabled()) {
                           this.log.debug("copyRoles skipped: " + pol.getId());
                        }
                     } else {
                        if (this.log.isDebugEnabled()) {
                           this.log.debug("copyRoles id: '" + pol.getId() + "'");
                        }

                        String resource = PolicyManager.getUpdatedResource(resId, sourceId, destId);
                        Policy p = new Policy(isEntitlement ? PolicyManager.getUpdatedId(pol.getId(), sourceId, destId) : pol.getId(), this.rmu.updateTarget(pol.getTarget(), resId, resource), pol.getCombiningAlgId(), pol.getDescription(), isEntitlement ? pol.getVersion() : PolicyManager.getUpdatedVersion(pol.getVersion()), pol.getDefaults(), pol.getCombinerParameters(), pol.getObligations(), pol.getRules(), pol.getRuleCombinerParameters(), pol.getVariableDefinitions());
                        copiedPolicies.put(pol.getReference(), p.getReference());
                        if (isEntitlement) {
                           if (mstore instanceof EntitlementAwarePolicyStore) {
                              mstore.setPolicy(p, 3, pmd);
                           } else {
                              mstore.setPolicy(p, 1, pmd);
                              this.setTopPolicySet(p, r.getRole());
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
               Iterator var24 = pSets.iterator();

               while(true) {
                  while(true) {
                     PolicySetInfo info;
                     PolicySet pol;
                     String resId;
                     do {
                        if (!var24.hasNext()) {
                           return;
                        }

                        info = (PolicySetInfo)var24.next();
                        pol = info.getPolicySet();
                        resId = this.rmu.getTargetResource(pol.getTarget());
                     } while(!PolicyManager.resourceMatches(resId, filterId));

                     PolicyMetaData pmd = info.getMetaDataEntry();
                     if (pmd != null && "deploy".equals(pmd.getIndexValue("wlsCreatorInfo"))) {
                        if (this.log.isDebugEnabled()) {
                           this.log.debug("copyPolicy skipped (set): " + pol.getId());
                        }
                     } else {
                        if (this.log.isDebugEnabled()) {
                           this.log.debug("copyPolicy id (set): '" + pol.getId() + "'");
                        }

                        String resource = PolicyManager.getUpdatedResource(resId, sourceId, destId);
                        PolicySet p = new PolicySet(pol.getId(), this.rmu.updateTarget(pol.getTarget(), resId, resource), pol.getCombiningAlgId(), pol.getDescription(), PolicyManager.getUpdatedVersion(pol.getVersion()), pol.getDefaults(), pol.getCombinerParameters(), pol.getObligations(), PolicyManager.updateReferences(pol.getPoliciesPolicySetsAndReferences(), filterId, this.converter, this.rmu, copiedPolicies, mstore), pol.getPolicyCombinerParameters(), pol.getPolicySetCombinerParameters());
                        mstore.setPolicySet(p, mstore.getPolicySetStatus(pol.getId(), pol.getVersion()), pmd);
                     }
                  }
               }
            }
         } catch (DocumentParseException var20) {
            throw new CreateException(var20);
         } catch (PolicyStoreException var21) {
            throw new CreateException(var21);
         } catch (URISyntaxException var22) {
            throw new CreateException(var22);
         }
      }
   }

   public void createRoleCollectionInfo(String name, String version, String timeStamp) throws CreateException {
      if (!(this.store instanceof MetaDataPolicyStore)) {
         throw new CreateException("Invalid PolicyStore");
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;
         if (this.log.isDebugEnabled()) {
            this.log.debug("createRoleCollectionInfo: " + name + " : " + version + " : " + timeStamp);
         }

         String key = "RoleCollectionInfo#" + name;
         String xmlName = XMLEscaper.escapeXMLChars(name);
         String value = "<RoleCollectionInfo Name=\"" + xmlName + "\" " + "TimeStamp=\"" + timeStamp + "\" " + "Version=\"" + version + "\"/>";

         try {
            mstore.setMetaDataEntry(key, value);
            if (this.log.isDebugEnabled()) {
               this.log.debug("createRoleCollectionInfo: " + value);
            }

         } catch (PolicyStoreException var9) {
            throw new CreateException(var9);
         }
      }
   }

   public StoredRoleCollectionInfo getRoleCollectionInfo(String name) {
      if (!(this.store instanceof MetaDataPolicyStore)) {
         return null;
      } else {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;
         StoredRoleCollectionInfo rci = null;
         String value = null;
         String key = "RoleCollectionInfo#" + name;
         if (this.log.isDebugEnabled()) {
            this.log.debug("getRoleCollectionInfo: " + key);
         }

         try {
            value = mstore.getMetaDataEntry(key);
            rci = this.getRoleCollectionInfoValue(value);
         } catch (PolicyStoreException var7) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("getRoleCollectionInfo:" + var7.toString(), var7);
            }
         } catch (RuntimeException var8) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("getRoleCollectionInfo run-time:" + var8.toString(), var8);
            }
         }

         return rci;
      }
   }

   private StoredRoleCollectionInfo getRoleCollectionInfoValue(String value) {
      if (value == null) {
         return null;
      } else {
         StoredRoleCollectionInfo rci = null;
         int nameIdx = value.indexOf("Name=\"");
         int start = nameIdx + PolicyManager.PCI_XML_NAME_LEN;
         if (nameIdx != -1) {
            int end = value.indexOf(34, start);
            String xmlName = value.substring(start, end);
            String sName = XMLEscaper.unescapeXMLChars(xmlName);
            start = value.indexOf("TimeStamp=\"", end + 1);
            start += PolicyManager.PCI_XML_TIMESTAMP_LEN;
            end = value.indexOf(34, start);
            String sTime = value.substring(start, end);
            start = value.indexOf("Version=\"", end + 1);
            start += PolicyManager.PCI_XML_VERSION_LEN;
            end = value.indexOf(34, start);
            String sVer = value.substring(start, end);
            rci = new StoredRoleCollectionInfo(sName, sVer, sTime);
            if (this.log.isDebugEnabled()) {
               this.log.debug("getRoleCollectionInfoValue: " + sName + " : " + sVer + " : " + sTime);
            }
         }

         return rci;
      }
   }

   public List getAllRoleCollectionInfo() {
      List result = null;
      List values = this.getAllRoleCollectionInfoValues();
      if (values != null) {
         result = new LinkedList();
         Iterator var3 = values.iterator();

         while(var3.hasNext()) {
            String value = (String)var3.next();
            StoredRoleCollectionInfo rci = this.getRoleCollectionInfoValue(value);
            if (rci != null) {
               result.add(rci);
            }
         }
      }

      return result;
   }

   public List getAllRoleCollectionInfoValues() {
      List result = null;
      if (this.store instanceof MetaDataPolicyStore) {
         MetaDataPolicyStore mstore = (MetaDataPolicyStore)this.store;

         try {
            result = mstore.readAllMetaDataEntries();
         } catch (PolicyStoreException var4) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("getAllRoleCollectionInfoValues:" + var4.toString(), var4);
            }
         }
      }

      return result;
   }
}

package com.bea.security.providers.xacml.store;

import com.bea.common.security.store.data.WLSPolicyCollectionInfo;
import com.bea.common.security.store.data.WLSRoleCollectionInfo;
import com.bea.common.security.store.data.XACMLAuthorizationPolicy;
import com.bea.common.security.store.data.XACMLEntry;
import com.bea.common.security.store.data.XACMLRoleAssignmentPolicy;
import com.bea.common.store.bootstrap.Entry;
import com.bea.common.store.bootstrap.EntryConverter;
import com.bea.security.providers.utils.LdiftEntry;
import com.bea.security.xacml.cache.resource.Escaping;
import com.bea.security.xacml.cache.resource.ResourcePolicyIdUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class XACMLEntryConverter implements EntryConverter {
   private String domainName;
   private String realmName;
   private static final Escaping escaper = ResourcePolicyIdUtil.getEscaper();

   public XACMLEntryConverter(String domainName, String realmName) {
      this.domainName = domainName;
      this.realmName = realmName;
   }

   private String unescape(String val) {
      return val != null ? escaper.unescapeString(val) : null;
   }

   private Collection unescape(Collection vals) {
      if (vals == null) {
         return null;
      } else {
         Collection res = new ArrayList();
         Iterator var3 = vals.iterator();

         while(var3.hasNext()) {
            String val = (String)var3.next();
            res.add(this.unescape(val));
         }

         return res;
      }
   }

   public Object convert(Entry e) {
      LdiftEntry entry = new LdiftEntry(e);
      if (entry.isEntryClass("xacmlRoleAssignmentPolicy")) {
         XACMLRoleAssignmentPolicy pc = (XACMLRoleAssignmentPolicy)this.createXACMLEntry(entry, false);
         pc.setXacmlRole(this.unescape(entry.getStringAttributeValues("xacmlRole")));
         this.setXACMLEntryOtherInfo(pc, entry);
         return pc;
      } else if (entry.isEntryClass("xacmlAuthorizationPolicy")) {
         XACMLAuthorizationPolicy pc = (XACMLAuthorizationPolicy)this.createXACMLEntry(entry, true);
         pc.setXacmlResourceScope(this.unescape(entry.getStringAttributeValues("xacmlResourceScope")));
         this.setXACMLEntryOtherInfo(pc, entry);
         return pc;
      } else {
         String wlsCollectionName;
         if (entry.isEntryClass("wlsRoleCollectionInfo")) {
            wlsCollectionName = this.unescape(entry.getStringAttributeValue("wlsCollectionName"));
            WLSRoleCollectionInfo pc = new WLSRoleCollectionInfo(this.domainName, this.realmName, wlsCollectionName);
            pc.setWlsCollectionVersion(entry.getStringAttributeValue("wlsCollectionVersion"));
            pc.setWlsCollectionTimestamp(entry.getStringAttributeValue("wlsCollectionTimestamp"));
            pc.setWlsXmlFragment(entry.getBinaryAttributeValue("wlsXmlFragment"));
            return pc;
         } else if (entry.isEntryClass("wlsPolicyCollectionInfo")) {
            wlsCollectionName = this.unescape(entry.getStringAttributeValue("wlsCollectionName"));
            WLSPolicyCollectionInfo pc = new WLSPolicyCollectionInfo(this.domainName, this.realmName, wlsCollectionName);
            pc.setWlsCollectionVersion(entry.getStringAttributeValue("wlsCollectionVersion"));
            pc.setWlsCollectionTimestamp(entry.getStringAttributeValue("wlsCollectionTimestamp"));
            pc.setWlsXmlFragment(entry.getBinaryAttributeValue("wlsXmlFragment"));
            return pc;
         } else {
            return null;
         }
      }
   }

   private void setXACMLEntryOtherInfo(XACMLEntry pc, LdiftEntry entry) {
      pc.setXacmlResourceScope(this.unescape(entry.getStringAttributeValues("xacmlResourceScope")));
      pc.setWlsCollectionName(this.unescape(entry.getStringAttributeValue("wlsCollectionName")));
      pc.setWlsCreatorInfo(entry.getStringAttributeValue("wlsCreatorInfo"));
      pc.setWlsXmlFragment(entry.getBinaryAttributeValue("wlsXmlFragment"));
   }

   private XACMLEntry createXACMLEntry(LdiftEntry entry, boolean isAtz) {
      String cn = this.unescape(entry.getStringAttributeValue("cn"));
      String typeName = entry.containsDNAttributeValue("ou", "Policies") ? "Policies" : "PolicySets";
      String xacmlVersion = entry.getStringAttributeValue("xacmlVersion");
      byte[] xacmlDocument = entry.getBinaryAttributeValue("xacmlDocument");
      String xacmlStatus = entry.getStringAttributeValue("xacmlStatus");
      return (XACMLEntry)(isAtz ? new XACMLAuthorizationPolicy(this.domainName, this.realmName, typeName, cn, xacmlVersion, xacmlDocument, xacmlStatus) : new XACMLRoleAssignmentPolicy(this.domainName, this.realmName, typeName, cn, xacmlVersion, xacmlDocument, xacmlStatus));
   }
}

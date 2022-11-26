package com.bea.security.providers.xacml.store.file;

import com.bea.common.store.bootstrap.Entry;
import com.bea.common.store.bootstrap.EntryConverter;
import com.bea.security.providers.utils.LdiftEntry;
import com.bea.security.xacml.cache.resource.Escaping;
import com.bea.security.xacml.cache.resource.ResourcePolicyIdUtil;

class FileEntryConverter implements EntryConverter {
   private static final Escaping escaper = ResourcePolicyIdUtil.getEscaper();

   private String unescape(String val) {
      return val != null ? escaper.unescapeString(val) : null;
   }

   public Object convert(Entry e) {
      LdiftEntry entry = new LdiftEntry(e);
      if (!entry.isEntryClass("xacmlRoleAssignmentPolicy") && !entry.isEntryClass("xacmlAuthorizationPolicy")) {
         if (entry.isEntryClass("wlsPolicyCollectionInfo")) {
            return this.generateCollectionInfo(entry, WlsCollectionStore.TYPE_POLICYCOLLECTION);
         } else {
            return entry.isEntryClass("wlsRoleCollectionInfo") ? this.generateCollectionInfo(entry, WlsCollectionStore.TYPE_ROLECOLLECTION) : null;
         }
      } else {
         String cn = this.unescape(entry.getStringAttributeValue("cn"));
         String version = entry.getStringAttributeValue("xacmlVersion");
         byte[] doc = entry.getBinaryAttributeValue("xacmlDocument");
         String status = entry.getStringAttributeValue("xacmlStatus");
         String mdCreator = entry.getStringAttributeValue("wlsCreatorInfo");
         String colName = this.unescape(entry.getStringAttributeValue("wlsCollectionName"));
         String xmlFrag = entry.getBinaryAttributeStringValue("wlsXmlFragment");
         return new XACMLEntry(cn, version, doc, status, mdCreator, colName, xmlFrag, entry.isEntryClass("xacmlAuthorizationPolicy"));
      }
   }

   private WlsCollectionStore.WlsCollectionInfo generateCollectionInfo(LdiftEntry entry, String type) {
      String name = this.unescape(entry.getStringAttributeValue("wlsCollectionName"));
      String version = this.unescape(entry.getStringAttributeValue("wlsCollectionVersion"));
      long time = Long.parseLong(entry.getStringAttributeValue("wlsCollectionTimestamp"));
      String xml = entry.getBinaryAttributeStringValue("wlsXmlFragment");
      return new WlsCollectionStore.WlsCollectionInfo(name, version, time, xml, type);
   }
}

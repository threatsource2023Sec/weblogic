package com.bea.security.providers.xacml.store.file;

import com.bea.security.providers.xacml.store.PolicyMetaDataImpl;
import com.bea.security.xacml.PolicyMetaData;
import java.util.HashMap;
import java.util.Map;

class XACMLEntry {
   private String id;
   private String version;
   private byte[] xacmlDocument;
   private String status;
   private PolicyMetaData metadata;

   public XACMLEntry() {
   }

   public XACMLEntry(String id, String version, byte[] xacmlDocument, String status, String mdCreator, String mdCol, String mdValue, boolean isATZ) {
      this.id = id;
      this.status = status;
      this.version = version;
      this.xacmlDocument = xacmlDocument;
      this.metadata = this.generateMetaData(mdCreator, mdCol, mdValue, isATZ);
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getVersion() {
      return this.version;
   }

   public void setVersion(String version) {
      this.version = version;
   }

   public byte[] getXacmlDocument() {
      return this.xacmlDocument;
   }

   public void setXacmlDocument(byte[] xacmlDocument) {
      this.xacmlDocument = xacmlDocument;
   }

   public String toString() {
      return "[XAMLEntry:id=" + this.id + ", version=" + this.version + "]";
   }

   private PolicyMetaData generateMetaData(String mdCreator, String mdCol, String mdValue, boolean isATZ) {
      PolicyMetaData md = null;
      if (mdValue != null) {
         Map mdIndex = new HashMap();
         if (mdCreator != null) {
            mdIndex.put("wlsCreatorInfo", mdCreator);
         }

         if (mdCol != null) {
            mdIndex.put("wlsCollectionName", mdCol);
         }

         String mdElementName = isATZ ? "WLSPolicyInfo" : "WLSRoleInfo";
         md = new PolicyMetaDataImpl(mdElementName, mdValue, mdIndex);
      }

      return md;
   }

   public PolicyMetaData getMetadata() {
      return this.metadata;
   }

   public void setMetadata(PolicyMetaData metadata) {
      this.metadata = metadata;
   }
}

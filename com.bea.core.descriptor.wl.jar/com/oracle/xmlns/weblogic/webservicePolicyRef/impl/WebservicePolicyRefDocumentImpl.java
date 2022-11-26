package com.oracle.xmlns.weblogic.webservicePolicyRef.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.webservicePolicyRef.WebservicePolicyRefDocument;
import com.oracle.xmlns.weblogic.webservicePolicyRef.WebservicePolicyRefType;
import javax.xml.namespace.QName;

public class WebservicePolicyRefDocumentImpl extends XmlComplexContentImpl implements WebservicePolicyRefDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBSERVICEPOLICYREF$0 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "webservice-policy-ref");

   public WebservicePolicyRefDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WebservicePolicyRefType getWebservicePolicyRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebservicePolicyRefType target = null;
         target = (WebservicePolicyRefType)this.get_store().find_element_user(WEBSERVICEPOLICYREF$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWebservicePolicyRef(WebservicePolicyRefType webservicePolicyRef) {
      this.generatedSetterHelperImpl(webservicePolicyRef, WEBSERVICEPOLICYREF$0, 0, (short)1);
   }

   public WebservicePolicyRefType addNewWebservicePolicyRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebservicePolicyRefType target = null;
         target = (WebservicePolicyRefType)this.get_store().add_element_user(WEBSERVICEPOLICYREF$0);
         return target;
      }
   }
}

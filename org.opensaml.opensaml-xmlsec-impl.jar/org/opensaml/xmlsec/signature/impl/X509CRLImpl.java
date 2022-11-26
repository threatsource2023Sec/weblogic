package org.opensaml.xmlsec.signature.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.shibboleth.utilities.java.support.collection.IndexingObjectStore;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xmlsec.signature.X509CRL;

public class X509CRLImpl extends AbstractXMLObject implements X509CRL {
   private static final IndexingObjectStore B64_CRL_STORE = new IndexingObjectStore();
   private String b64CRLIndex;

   protected X509CRLImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getValue() {
      return (String)B64_CRL_STORE.get(this.b64CRLIndex);
   }

   public void setValue(String newValue) {
      String currentCert = (String)B64_CRL_STORE.get(this.b64CRLIndex);
      String b64Cert = this.prepareForAssignment(currentCert, newValue);
      if (!Objects.equals(currentCert, b64Cert)) {
         B64_CRL_STORE.remove(this.b64CRLIndex);
         this.b64CRLIndex = B64_CRL_STORE.put(b64Cert);
      }

   }

   public List getOrderedChildren() {
      return Collections.emptyList();
   }

   protected void finalize() throws Throwable {
      super.finalize();
      B64_CRL_STORE.remove(this.b64CRLIndex);
   }
}

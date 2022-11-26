package org.opensaml.xmlsec.signature.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.shibboleth.utilities.java.support.collection.IndexingObjectStore;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xmlsec.signature.X509Certificate;

public class X509CertificateImpl extends AbstractXMLObject implements X509Certificate {
   private static final IndexingObjectStore B64_CERT_STORE = new IndexingObjectStore();
   private String b64CertIndex;

   protected X509CertificateImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getValue() {
      return (String)B64_CERT_STORE.get(this.b64CertIndex);
   }

   public void setValue(String newValue) {
      String currentCert = (String)B64_CERT_STORE.get(this.b64CertIndex);
      String b64Cert = this.prepareForAssignment(currentCert, newValue);
      if (!Objects.equals(currentCert, b64Cert)) {
         B64_CERT_STORE.remove(this.b64CertIndex);
         this.b64CertIndex = B64_CERT_STORE.put(b64Cert);
      }

   }

   public List getOrderedChildren() {
      return Collections.emptyList();
   }

   protected void finalize() throws Throwable {
      super.finalize();
      B64_CERT_STORE.remove(this.b64CertIndex);
   }
}

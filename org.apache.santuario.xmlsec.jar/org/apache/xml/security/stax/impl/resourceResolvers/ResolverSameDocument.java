package org.apache.xml.security.stax.impl.resourceResolvers;

import java.io.InputStream;
import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.ResourceResolver;
import org.apache.xml.security.stax.ext.ResourceResolverLookup;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class ResolverSameDocument implements ResourceResolver, ResourceResolverLookup {
   private String id;
   private boolean firstElementOccured = false;

   public ResolverSameDocument() {
   }

   public ResolverSameDocument(String uri) {
      this.id = XMLSecurityUtils.dropReferenceMarker(uri);
   }

   public String getId() {
      return this.id;
   }

   public ResourceResolverLookup canResolve(String uri, String baseURI) {
      if (uri != null && (uri.isEmpty() || uri.charAt(0) == '#')) {
         return uri.startsWith("#xpointer") ? null : this;
      } else {
         return null;
      }
   }

   public ResourceResolver newInstance(String uri, String baseURI) {
      return new ResolverSameDocument(uri);
   }

   public boolean isSameDocumentReference() {
      return true;
   }

   public boolean matches(XMLSecStartElement xmlSecStartElement) {
      return this.matches(xmlSecStartElement, XMLSecurityConstants.ATT_NULL_Id);
   }

   public boolean matches(XMLSecStartElement xmlSecStartElement, QName idAttributeNS) {
      if (this.id.isEmpty()) {
         if (this.firstElementOccured) {
            return false;
         } else {
            this.firstElementOccured = true;
            return true;
         }
      } else {
         Attribute attribute = xmlSecStartElement.getAttributeByName(idAttributeNS);
         return attribute != null && attribute.getValue().equals(this.id);
      }
   }

   public InputStream getInputStreamFromExternalReference() throws XMLSecurityException {
      return null;
   }
}

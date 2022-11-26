package weblogic.xml.saaj;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import weblogic.xml.domimpl.DocumentImpl;

class SOAPHeaderElementImpl extends SOAPElementImpl implements SOAPHeaderElement {
   static final long serialVersionUID = 1073915940914366315L;

   SOAPHeaderElementImpl(DocumentImpl ownerDocument, String namespaceURI, String localName, String prefix) throws DOMException {
      super(ownerDocument, namespaceURI, localName, prefix);
   }

   SOAPHeaderElementImpl(DocumentImpl ownerDocument, String namespaceURI, String localName, String prefix, int numAttrs) throws DOMException {
      super(ownerDocument, namespaceURI, localName, prefix, numAttrs);
   }

   public void setParentElement(SOAPElement element) throws SOAPException {
      if (!(element instanceof SOAPHeader)) {
         throw new SOAPException("Parent of SOAPHeaderElement must be a SOAPHeader");
      } else {
         super.setParentElement(element);
      }
   }

   boolean isSoap11() {
      return ((SOAPElementImpl)this.getParentElement()).isSoap11();
   }

   private String getSoapNamespace() {
      Node parent = this.getParentNode();

      assert parent != null;

      return parent.getNamespaceURI();
   }

   public boolean getMustUnderstand() {
      return toBoolean(this.getAttributeNS(this.getSoapNamespace(), "mustUnderstand"));
   }

   public void setMustUnderstand(boolean b) {
      this.setAttributeNS(this.getSoapNamespace(), "mustUnderstand", b ? "1" : "0");
   }

   public String getActor() {
      return this.getAttributeNS(this.getSoapNamespace(), "actor");
   }

   public void setActor(String actor) {
      this.setAttributeNS(this.getSoapNamespace(), "actor", actor);
   }

   public void setRole(String uri) throws SOAPException {
      if (this.isSoap11()) {
         throw new UnsupportedOperationException("Not supported for Soap 1.1");
      } else {
         this.setAttributeNS(SOAPConstants.HEADER12_ROLE.getNamespaceURI(), SOAPConstants.HEADER12_ROLE.getLocalPart(), uri);
      }
   }

   public String getRole() {
      if (this.isSoap11()) {
         throw new UnsupportedOperationException("Not supported for Soap 1.1");
      } else {
         return this.getAttributeNS(SOAPConstants.HEADER12_ROLE.getNamespaceURI(), SOAPConstants.HEADER12_ROLE.getLocalPart());
      }
   }

   public void setRelay(boolean relay) throws SOAPException {
      if (this.isSoap11()) {
         throw new UnsupportedOperationException("Not supported for Soap 1.1");
      } else {
         this.setAttributeNS(SOAPConstants.HEADER12_RELAY.getNamespaceURI(), SOAPConstants.HEADER12_RELAY.getLocalPart(), relay ? "1" : "0");
      }
   }

   public boolean getRelay() {
      if (this.isSoap11()) {
         throw new UnsupportedOperationException("Not supported for Soap 1.1");
      } else {
         return toBoolean(this.getAttributeNS(SOAPConstants.HEADER12_RELAY.getNamespaceURI(), SOAPConstants.HEADER12_RELAY.getLocalPart()));
      }
   }

   private static boolean toBoolean(String value) {
      if (value == null) {
         return false;
      } else {
         return "1".equals(value) || "true".equals(value);
      }
   }
}

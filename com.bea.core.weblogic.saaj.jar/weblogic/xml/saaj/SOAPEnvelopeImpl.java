package weblogic.xml.saaj;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import weblogic.xml.domimpl.ChildNode;
import weblogic.xml.domimpl.DocumentImpl;
import weblogic.xml.domimpl.ElementBase;
import weblogic.xml.domimpl.NodeImpl;

class SOAPEnvelopeImpl extends SOAPElementImpl implements SOAPEnvelope {
   static final long serialVersionUID = 463794944567583814L;
   private String soapNS;

   SOAPEnvelopeImpl(DocumentImpl ownerDocument, Element domElement) {
      super(ownerDocument, domElement.getNamespaceURI(), domElement.getLocalName(), domElement.getPrefix());
      this.soapNS = domElement.getNamespaceURI();
      this.copyAttributes(this, domElement);
      this.copyChildren(this, domElement);
   }

   SOAPEnvelopeImpl(DocumentImpl ownerDocument, String soapNS, String prefix) throws DOMException {
      this(ownerDocument, soapNS, prefix, true);
   }

   SOAPEnvelopeImpl(DocumentImpl ownerDocument, String soapNS, String prefix, boolean init_children) throws DOMException {
      super(ownerDocument, soapNS, "Envelope", prefix);
      this.soapNS = soapNS;
      if (init_children) {
         this.addInitialChildren(ownerDocument);
      }

   }

   public void isOwned(boolean value) {
      super.isOwned(value);
   }

   public SOAPElement addAttribute(Name name, String value) throws SOAPException {
      if (!this.isSoap11() && "encodingStyle".equals(name.getLocalName()) && "http://www.w3.org/2003/05/soap-envelope".equals(name.getURI())) {
         throw new SOAPException("SOAP1.2 does not allow encodingStyle attribute to be set on Envelope");
      } else {
         return super.addAttribute(name, value);
      }
   }

   public SOAPElement setElementQName(QName newName) throws SOAPException {
      throw new SOAPException("Cannot change the qname of SOAP envelope");
   }

   public SOAPElement addChildElement(String localname) throws SOAPException {
      if (!this.isSoap11()) {
         throw new SOAPException("SOAP1.2 does not allow trailing blocks after the Body");
      } else {
         return super.addChildElement(localname);
      }
   }

   public SOAPElement addChildElement(Name name) throws SOAPException {
      if (!this.isSoap11()) {
         throw new SOAPException("SOAP1.2 does not allow trailing blocks after the Body");
      } else {
         return super.addChildElement(name);
      }
   }

   public SOAPElement addChildElement(SOAPElement element) throws SOAPException {
      if (!this.isSoap11()) {
         throw new SOAPException("SOAP1.2 does not allow trailing blocks after the Body");
      } else {
         return super.addChildElement(element);
      }
   }

   public SOAPElement addChildElement(String localname, String prefix) throws SOAPException {
      if (!this.isSoap11()) {
         throw new SOAPException("SOAP1.2 does not allow trailing blocks after the Body");
      } else {
         return super.addChildElement(localname, prefix);
      }
   }

   public SOAPElement addChildElement(String localname, String prefix, String uri) throws SOAPException {
      if (!this.isSoap11()) {
         throw new SOAPException("SOAP1.2 does not allow trailing blocks after the Body");
      } else {
         return super.addChildElement(localname, prefix, uri);
      }
   }

   public SOAPElement addChildElement(QName qname) throws SOAPException {
      if (!this.isSoap11()) {
         throw new SOAPException("SOAP1.2 does not allow trailing blocks after the Body");
      } else {
         return super.addChildElement(qname);
      }
   }

   private void addInitialChildren(DocumentImpl ownerDocument) {
      this.addSoapHeader(ownerDocument);
      this.addSoapBody(ownerDocument);

      assert this.findHeader() != null;

      assert this.findBody() != null;

   }

   private SOAPHeader addSoapHeader(DocumentImpl ownerDocument) {
      SOAPHeaderImpl sh = this.newSoapHeader(ownerDocument, this.getPrefix());
      this.insertBefore(sh, this.getFirstChild());
      sh.isSaajTyped(true);
      return sh;
   }

   private SOAPBody addSoapBody(DocumentImpl ownerDocument) {
      SOAPBodyImpl sh = this.newSoapBody(ownerDocument, this.getPrefix());
      this.appendChild(sh);
      sh.isSaajTyped(true);
      return sh;
   }

   private SOAPBodyImpl newSoapBody(DocumentImpl ownerDocument, String prefix) {
      SOAPBodyImpl sh = new SOAPBodyImpl(ownerDocument, this.soapNS, prefix);
      sh.isSaajTyped(true);
      return sh;
   }

   private SOAPHeaderImpl newSoapHeader(DocumentImpl ownerDocument, String prefix) {
      SOAPHeaderImpl sh = new SOAPHeaderImpl(ownerDocument, this.soapNS, prefix);
      sh.isSaajTyped(true);
      return sh;
   }

   public SOAPBody addBody() throws SOAPException {
      if (this.getBody() != null) {
         throw new SOAPException("SOAPEnvelope already contains a SOAPBody");
      } else {
         return this.addSoapBody(this.ownerDocument);
      }
   }

   public SOAPBody getBody() throws SOAPException {
      return this.findBody();
   }

   public SOAPHeader addHeader() throws SOAPException {
      if (this.getHeader() != null) {
         throw new SOAPException("SOAPEnvelope already contains a SOAPHeader");
      } else {
         return this.addSoapHeader(this.ownerDocument);
      }
   }

   public SOAPHeader getHeader() throws SOAPException {
      return this.findHeader();
   }

   private SOAPHeader findHeader() {
      for(ChildNode n = this.firstChild; n != null; n = n.nextSibling()) {
         if (this.matchesHeader(n)) {
            if (!n.isSaajTyped()) {
               assert !(n instanceof SOAPHeader);

               SOAPHeaderImpl sh = this.newSoapHeader(this.ownerDocument, this.getPrefix());
               this.updateSaajChild(sh, (ElementBase)n);

               assert sh.isSaajTyped();

               return sh;
            }

            assert n instanceof SOAPHeader;

            return (SOAPHeader)n;
         }
      }

      return null;
   }

   private boolean matchesHeader(Node n) {
      return "Header".equals(n.getLocalName()) && this.soapNS.equals(n.getNamespaceURI());
   }

   private SOAPBody findBody() {
      for(ChildNode n = this.firstChild; n != null; n = n.nextSibling()) {
         if (this.matchesBody(n)) {
            if (!n.isSaajTyped()) {
               SOAPBodyImpl sb = this.newSoapBody(this.ownerDocument, this.getPrefix());
               this.updateSaajChild(sb, (ElementBase)n);

               assert sb.isSaajTyped();

               return sb;
            }

            assert n instanceof SOAPBody;

            return (SOAPBody)n;
         }
      }

      return null;
   }

   private boolean matchesBody(Node n) {
      return this.matchesBody(n.getLocalName(), n.getNamespaceURI());
   }

   public Name createName(String s) throws SOAPException {
      return new NameImpl(s);
   }

   public Name createName(String lname, String prefix, String uri) throws SOAPException {
      return new NameImpl(lname, prefix, uri);
   }

   public SOAPElementImpl createAndAppendSaajElement(NodeImpl parent, String namespaceURI, String localName, String prefix, int num_atts) {
      if (this.soapNS.equals(namespaceURI)) {
         if ("Body".equals(localName)) {
            SOAPBodyImpl sb = new SOAPBodyImpl(this.ownerDocument, this.soapNS, prefix, num_atts);
            sb.isSaajTyped(true);
            this.appendChild(sb);
            return sb;
         }

         if ("Header".equals(localName)) {
            SOAPHeaderImpl sh = new SOAPHeaderImpl(this.ownerDocument, this.soapNS, prefix, num_atts);
            sh.isSaajTyped(true);
            this.appendChild(sh);
            return sh;
         }
      }

      return super.createAndAppendSaajElement(parent, namespaceURI, localName, prefix, num_atts);
   }

   private boolean matchesBody(String localName, String namespaceURI) {
      return "Body".equals(localName) && this.soapNS.equals(namespaceURI);
   }
}

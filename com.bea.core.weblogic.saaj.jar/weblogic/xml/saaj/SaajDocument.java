package weblogic.xml.saaj;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import weblogic.xml.domimpl.ChildNode;
import weblogic.xml.domimpl.DocumentImpl;
import weblogic.xml.domimpl.ElementNSImpl;
import weblogic.xml.domimpl.NodeImpl;

class SaajDocument extends DocumentImpl implements SaajNode {
   private static final long serialVersionUID = -8558391027400117302L;
   private String soapNS;

   SaajDocument() {
      this(true, "http://schemas.xmlsoap.org/soap/envelope/");
   }

   SaajDocument(boolean initChildren, String soapNS) {
      this.soapNS = soapNS;
      if (initChildren) {
         this.initChildren(true, "env");
      }

      this.isSaajTyped(true);
   }

   private SOAPEnvelopeImpl initChildren(boolean initSubChildren, String soapEnvPrefix) {
      SOAPEnvelopeImpl se = new SOAPEnvelopeImpl(this, this.soapNS, soapEnvPrefix, initSubChildren);
      se.isSaajTyped(true);
      this.isSaajTyped(true);
      this.appendChild(se);
      return se;
   }

   protected void isOwned(boolean owned) {
      super.isOwned(owned);
      if (!owned) {
         this.isSaajTyped(false);
      }

   }

   public Element createElement(String tagName) throws DOMException {
      return new SOAPElementImpl(this, (String)null, tagName, (String)null);
   }

   public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
      return new SOAPElementImpl(this, namespaceURI, qualifiedName);
   }

   public ElementNSImpl createElementNS(String namespaceURI, String localName, String prefix) throws DOMException {
      return new SOAPElementImpl(this, namespaceURI, localName, prefix);
   }

   protected ElementNSImpl createElementNS(String namespaceURI, String localName, String prefix, int numAttrs) throws DOMException {
      return new SOAPElementImpl(this, namespaceURI, localName, prefix, numAttrs);
   }

   public Text createTextNode(String data) {
      return new TextImpl(this.ownerDocument, data);
   }

   SOAPEnvelopeImpl getEnvelope() throws SOAPException {
      if (!this.docElement.isSaajTyped()) {
         QName root = new QName(this.docElement.getNamespaceURI(), this.docElement.getLocalName());
         if (!root.equals(new QName(this.soapNS, "Envelope"))) {
            throw new VersionMismatchException(this.soapNS, this.docElement.getLocalName(), this.docElement.getNamespaceURI());
         }

         SOAPEnvelopeImpl env = new SOAPEnvelopeImpl(this.ownerDocument, this.docElement);
         env.isSaajTyped(true);
         env.isOwned(true);
         this.docElement = env;
         this.firstChild = env;
      }

      assert this.docElement.isSaajTyped();

      assert this.docElement instanceof SOAPEnvelopeImpl : "docElement is " + this.docElement.getClass();

      return (SOAPEnvelopeImpl)this.docElement;
   }

   public SaajNode createAndAppendSaajElement(NodeImpl parent, String namespaceURI, String localName, String prefix, int numAttrs) {
      if ("Envelope".equals(localName) && this.soapNS.equals(namespaceURI)) {
         SOAPEnvelopeImpl soapEnvelope = this.initChildren(false, prefix);
         return soapEnvelope;
      } else {
         return (SaajNode)super.createAndAppendElement(parent, namespaceURI, localName, prefix, numAttrs);
      }
   }

   public ChildNode fixChildSaajType(ChildNode child) {
      throw new AssertionError("not used");
   }

   protected ElementNSImpl createAndAppendElement(NodeImpl parent, String uri, String localName, String prefix, int totalAttrs) {
      SaajNode saajNode = (SaajNode)parent;
      return (ElementNSImpl)saajNode.createAndAppendSaajElement(parent, uri, localName, prefix, totalAttrs);
   }
}

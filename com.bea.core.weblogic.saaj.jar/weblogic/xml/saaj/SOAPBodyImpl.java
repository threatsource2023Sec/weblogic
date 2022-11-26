package weblogic.xml.saaj;

import java.util.Locale;
import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import weblogic.xml.domimpl.ChildNode;
import weblogic.xml.domimpl.DocumentImpl;
import weblogic.xml.domimpl.ElementBase;
import weblogic.xml.domimpl.NodeImpl;

class SOAPBodyImpl extends SOAPElementImpl implements SOAPBody {
   static final long serialVersionUID = -2014431100500312174L;
   private String soapNS;

   SOAPBodyImpl(DocumentImpl ownerDocument, String soapNS, String prefix) throws DOMException {
      super(ownerDocument, soapNS, "Body", prefix);
      this.soapNS = soapNS;
      this.isSaajTyped(true);
   }

   SOAPBodyImpl(DocumentImpl ownerDocument, String soapNS, String prefix, int numAttrs) throws DOMException {
      super(ownerDocument, soapNS, "Body", prefix, numAttrs);
      this.soapNS = soapNS;
      this.isSaajTyped(true);
   }

   public SOAPElement setElementQName(QName newName) throws SOAPException {
      throw new SOAPException("Cannot change the qname of SOAP body");
   }

   public boolean hasFault() {
      return this.findFaultElement() != null;
   }

   public void setParentElement(SOAPElement element) throws SOAPException {
      if (!(element instanceof SOAPEnvelope)) {
         throw new SOAPException("Parent of SOAPBody must be a SOAPEnvelope");
      } else {
         super.setParentElement(element);
      }
   }

   public SOAPFault addFault() throws SOAPException {
      if (this.hasFault()) {
         throw new SOAPException("Error: Fault already exists");
      } else {
         return this.createSOAPFault();
      }
   }

   protected SOAPFault createSOAPFault() throws SOAPException {
      String soapEnvPrefix = this.lookupPrefix(this.soapNS);

      assert soapEnvPrefix != null;

      SOAPFaultImpl fault = this.createEmptySOAPFault(this.ownerDocument, this.soapNS, this.getPrefix());
      this.appendChild(fault);

      assert this == fault.getParentNode();

      fault.setFaultCode(fault.getDefaultFaultCode().getLocalPart(), soapEnvPrefix, this.soapNS);
      fault.setFaultString("Server Error");

      assert this == fault.getParentNode();

      return fault;
   }

   public SOAPFault getFault() {
      return this.findFaultElement();
   }

   private SOAPFault findFaultElement() {
      for(ChildNode node = this.firstChild; node != null; node = node.nextSibling()) {
         if (this.matchesFault(node.getLocalName(), node.getNamespaceURI())) {
            if (!node.isSaajTyped()) {
               SOAPFaultImpl soapFault = this.createEmptySOAPFault(this.ownerDocument, this.soapNS, node.getPrefix());
               this.updateSaajChild(soapFault, (ElementBase)node);

               assert soapFault.isSaajTyped();

               return soapFault;
            }

            return (SOAPFault)node;
         }
      }

      return null;
   }

   private boolean matchesFault(String localName, String namespaceURI) {
      return "Fault".equals(localName) && this.soapNS.equals(namespaceURI);
   }

   public SOAPBodyElement addBodyElement(Name name) throws SOAPException {
      SOAPElementImpl bodyElement = new SOAPElementImpl(this.ownerDocument, name);
      Attr ns_att = bodyElement.createNamespaceAttr();
      bodyElement.setAttributeNodeNS(ns_att);
      this.appendChild(bodyElement);
      return bodyElement;
   }

   public SOAPBodyElement addDocument(Document document) throws SOAPException {
      Element elem = document.getDocumentElement();
      if (elem == null) {
         return null;
      } else {
         SOAPElementImpl importedNode = (SOAPElementImpl)this.getOwnerDocument().importNode(elem, true);
         this.appendChild(importedNode);
         return importedNode;
      }
   }

   public SOAPFault addFault(Name faultCode, String faultString) throws SOAPException {
      SOAPFault fault = this.addFault();
      fault.setFaultCode(faultCode);
      fault.setFaultString(faultString);
      return fault;
   }

   public SOAPFault addFault(Name faultCode, String faultString, Locale locale) throws SOAPException {
      SOAPFault fault = this.addFault();
      fault.setFaultCode(faultCode);
      fault.setFaultString(faultString, locale);
      return fault;
   }

   public SOAPElementImpl createAndAppendSaajElement(NodeImpl parent, String namespaceURI, String localName, String prefix, int numAttrs) {
      assert parent == this;

      if (this.matchesFault(localName, namespaceURI)) {
         SOAPFaultImpl sb = this.createEmptySOAPFault(this.ownerDocument, this.soapNS, prefix);
         this.appendChild(sb);

         assert this.firstChild != null;

         assert this.hasChildNodes();

         return sb;
      } else {
         return super.createAndAppendSaajElement(parent, namespaceURI, localName, prefix, numAttrs);
      }
   }

   public SOAPFault addFault(QName qname, String faultString, Locale locale) throws SOAPException {
      return this.addFault((Name)(new NameImpl(qname.getLocalPart(), qname.getPrefix(), qname.getNamespaceURI())), faultString, locale);
   }

   public SOAPFault addFault(QName qname, String faultString) throws SOAPException {
      return this.addFault((Name)(new NameImpl(qname.getLocalPart(), qname.getPrefix(), qname.getNamespaceURI())), faultString);
   }

   public SOAPBodyElement addBodyElement(QName qname) throws SOAPException {
      return this.addBodyElement((Name)(new NameImpl(qname.getLocalPart(), qname.getPrefix(), qname.getNamespaceURI())));
   }

   public Document extractContentAsDocument() throws SOAPException {
      NodeList nodeList = this.getChildNodes();
      int nodeLength = nodeList.getLength();
      if (nodeLength > 1) {
         int elementCount = 0;

         for(int i = 0; i < nodeLength; ++i) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == 1) {
               ++elementCount;
            }

            if (elementCount > 1) {
               throw new SOAPException("The SOAPBody content cannot be extraced since it contains more than 1 child element");
            }
         }
      }

      DocumentImpl document = new DocumentImpl();
      Node node = this.firstElementChild();
      Node node = node.getParentNode().removeChild(node);
      if (node != null) {
         document.appendChild(document.importNode(node, true));
      }

      return document;
   }

   protected SOAPFaultImpl createEmptySOAPFault(DocumentImpl ownerDocument, String soapNS, String prefix) {
      SOAPFaultImpl fault = this.isSoap11() ? new SOAPFaultImpl(ownerDocument, soapNS, prefix) : new SOAP12FaultImpl(ownerDocument, soapNS, prefix);
      ((SOAPFaultImpl)fault).isSaajTyped(true);
      return (SOAPFaultImpl)fault;
   }
}

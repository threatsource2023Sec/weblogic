package weblogic.xml.saaj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import org.w3c.dom.DOMException;
import weblogic.xml.domimpl.ChildNode;
import weblogic.xml.domimpl.DocumentImpl;
import weblogic.xml.domimpl.ElementBase;
import weblogic.xml.domimpl.NodeImpl;
import weblogic.xml.util.EmptyIterator;

class SOAPHeaderImpl extends SOAPElementImpl implements SOAPHeader {
   static final long serialVersionUID = 4132687059226303541L;

   SOAPHeaderImpl(DocumentImpl ownerDocument, String soapNS, String prefix) throws DOMException {
      super(ownerDocument, soapNS, "Header", prefix);
   }

   SOAPHeaderImpl(DocumentImpl ownerDocument, String soapNS, String prefix, int num_attrs) throws DOMException {
      super(ownerDocument, soapNS, "Header", prefix, num_attrs);
   }

   public SOAPElement setElementQName(QName newName) throws SOAPException {
      throw new SOAPException("Cannot change the qname of SOAP header");
   }

   public SOAPElement addTextNode(String value) throws SOAPException {
      if ("http://www.w3.org/2003/05/soap-envelope".equals(this.getNamespaceURI())) {
         throw new SOAPException("It's illegal to add text node to SOAP 1.2 header");
      } else {
         return super.addTextNode(value);
      }
   }

   public Iterator examineAllHeaderElements() {
      ChildNode firstHeaderElement = this.firstElementChild();
      if (firstHeaderElement == null) {
         return EmptyIterator.getInstance();
      } else {
         if (!((ChildNode)firstHeaderElement).isSaajTyped()) {
            firstHeaderElement = this.fixChildSaajType((ChildNode)firstHeaderElement);

            assert ((ChildNode)firstHeaderElement).isSaajTyped();
         }

         return new HeaderElementsIterator((ChildNode)firstHeaderElement);
      }
   }

   public Iterator extractAllHeaderElements() {
      return this.getHeaderElements((String)null, true, false);
   }

   public Iterator examineHeaderElements(String actor) {
      return this.getHeaderElementsForActor(actor, false, false);
   }

   protected Iterator getHeaderElementsForActor(String actor, boolean detach, boolean mustUnderstand) {
      if (actor != null && !actor.equals("")) {
         return this.getHeaderElements(actor, detach, mustUnderstand);
      } else {
         throw new IllegalArgumentException("Invalid value for actor");
      }
   }

   public Iterator examineMustUnderstandHeaderElements(String actor) {
      return this.getHeaderElements(actor, false, true);
   }

   public Iterator extractHeaderElements(String actor) {
      return this.getHeaderElementsForActor(actor, true, false);
   }

   public void setParentElement(SOAPElement element) throws SOAPException {
      if (!(element instanceof SOAPEnvelope)) {
         throw new SOAPException("Parent of SOAPHeader must be a SOAPEnvelope");
      } else {
         super.setParentElement(element);
      }
   }

   protected Iterator getHeaderElements(String actor, boolean detach, boolean mustUnderstand) {
      List elementList = new ArrayList();
      Iterator eachChild = this.getChildElements();
      Object currentChild = this.iterate(eachChild);

      while(true) {
         while(currentChild != null) {
            if (!(currentChild instanceof SOAPHeaderElement)) {
               currentChild = this.iterate(eachChild);
            } else {
               SOAPHeaderElementImpl currentElement = (SOAPHeaderElementImpl)currentChild;
               currentChild = this.iterate(eachChild);
               boolean isMustUnderstandMatching = !mustUnderstand || currentElement.getMustUnderstand();
               boolean doAdd = false;
               if (actor == null && isMustUnderstandMatching) {
                  doAdd = true;
               } else {
                  String currentActor;
                  if ("http://schemas.xmlsoap.org/soap/envelope/".equals(this.getNamespaceURI())) {
                     currentActor = currentElement.getActor();
                  } else {
                     currentActor = currentElement.getRole();
                  }

                  if (currentActor == null) {
                     currentActor = "";
                  }

                  if (currentActor.equalsIgnoreCase(actor) && isMustUnderstandMatching) {
                     doAdd = true;
                  }
               }

               if (doAdd) {
                  elementList.add(currentElement);
                  if (detach) {
                     currentElement.detachNode();
                  }
               }
            }
         }

         return elementList.listIterator();
      }
   }

   private Object iterate(Iterator each) {
      return each.hasNext() ? each.next() : null;
   }

   public SOAPHeaderElement addHeaderElement(Name name) throws SOAPException {
      if (name.getURI() != null && !"".equals(name.getURI())) {
         SOAPHeaderElementImpl header_elem = new SOAPHeaderElementImpl(this.ownerDocument, name.getURI(), name.getLocalName(), name.getPrefix());
         header_elem.isSaajTyped(true);
         this.appendChild(header_elem);
         return header_elem;
      } else {
         throw new SOAPException("No namespace specified on the supplied name.");
      }
   }

   public ChildNode fixChildSaajType(ChildNode child) {
      if (child.getNodeType() == 1) {
         SOAPHeaderElementImpl header_elem = new SOAPHeaderElementImpl(this.ownerDocument, child.getNamespaceURI(), child.getLocalName(), child.getPrefix());
         return (ChildNode)this.updateSaajChild(header_elem, (ElementBase)child);
      } else {
         return super.fixChildSaajType(child);
      }
   }

   public SOAPElementImpl createAndAppendSaajElement(NodeImpl parent, String namespaceURI, String localName, String prefix, int num_atts) {
      assert this == parent;

      SOAPHeaderElementImpl se = new SOAPHeaderElementImpl(this.ownerDocument, namespaceURI, localName, prefix, num_atts);
      se.isSaajTyped(true);
      this.appendChild(se);
      return se;
   }

   public SOAPHeaderElement addHeaderElement(QName qname) throws SOAPException {
      return this.addHeaderElement((Name)(new NameImpl(qname)));
   }

   public SOAPHeaderElement addNotUnderstoodHeaderElement(QName name) throws SOAPException {
      if (this.isSoap11()) {
         throw new UnsupportedOperationException("Not supported for Soap 1.1");
      } else {
         SOAPHeaderElement header = this.addHeaderElement(SOAPConstants.HEADER12_NOT_UNDERSTOOD);
         header.setAttributeNS((String)null, "qname", this.qnameToString(name.getLocalPart(), name.getPrefix(), name.getNamespaceURI()));
         return header;
      }
   }

   public SOAPHeaderElement addUpgradeHeaderElement(Iterator supportedSOAPURIs) throws SOAPException {
      SOAPHeaderElement upgrade = null;
      if ("http://www.w3.org/2003/05/soap-envelope".equals(this.getNamespaceURI())) {
         upgrade = this.addHeaderElement(SOAPConstants.HEADER12_UPGRADE);
      } else {
         upgrade = this.addHeaderElement(new QName("http://schemas.xmlsoap.org/soap/envelope/", "Upgrade"));
      }

      while(supportedSOAPURIs.hasNext()) {
         String uri = (String)supportedSOAPURIs.next();
         SOAPElement supportedEnvelope = upgrade.addChildElement(SOAPConstants.HEADER12_SUPPORTED_ENVELOPE);
         supportedEnvelope.setAttributeNS((String)null, "qname", this.qnameToString("Envelope", "soapns", uri));
      }

      return upgrade;
   }

   public SOAPHeaderElement addUpgradeHeaderElement(String[] supportedSoapUris) throws SOAPException {
      return this.addUpgradeHeaderElement(Arrays.asList(supportedSoapUris).iterator());
   }

   public SOAPElement addChildElement(String localname) throws SOAPException {
      throw new SOAPException("SOAP requires all Header ChildElements to be namespace qualified");
   }

   public SOAPHeaderElement addUpgradeHeaderElement(String supportedSoapUri) throws SOAPException {
      SOAPHeaderElement upgrade = null;
      if ("http://www.w3.org/2003/05/soap-envelope".equals(this.getNamespaceURI())) {
         upgrade = this.addHeaderElement(SOAPConstants.HEADER12_UPGRADE);
      } else {
         upgrade = this.addHeaderElement(new QName("http://schemas.xmlsoap.org/soap/envelope/", "Upgrade"));
      }

      SOAPElement supportedEnvelope = upgrade.addChildElement(SOAPConstants.HEADER12_SUPPORTED_ENVELOPE);
      supportedEnvelope.setAttributeNS((String)null, "qname", this.qnameToString("Envelope", "soapns", supportedSoapUri));
      return upgrade;
   }

   private static class HeaderElementsIterator implements Iterator {
      private ChildNode curr;

      public HeaderElementsIterator(ChildNode first_child) {
         assert first_child instanceof SOAPHeaderElement : " type is " + first_child.getClass();

         this.curr = first_child;
      }

      public boolean hasNext() {
         return this.curr != null;
      }

      public Object next() {
         ChildNode retval = this.curr;

         assert retval instanceof SOAPHeaderElement;

         this.curr = SOAPElementImpl.nextElementSibling(this.curr);
         if (this.curr != null) {
            if (!this.curr.isSaajTyped()) {
               SaajNode parentNode = (SaajNode)this.curr.getParentNode();
               this.curr = parentNode.fixChildSaajType(this.curr);
            }

            assert this.curr instanceof SOAPHeaderElement;
         }

         return retval;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}

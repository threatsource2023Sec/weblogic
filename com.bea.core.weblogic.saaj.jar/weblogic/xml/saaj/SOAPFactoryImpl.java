package weblogic.xml.saaj;

import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import org.w3c.dom.Element;
import weblogic.xml.domimpl.DocumentImpl;

public class SOAPFactoryImpl extends SOAPFactory {
   public Detail createDetail() throws SOAPException {
      return new DetailImpl(this.createDocument());
   }

   DocumentImpl createDocument() {
      return new SaajDocument();
   }

   public Name createName(String localName) throws SOAPException {
      return new NameImpl(localName);
   }

   public SOAPElement createElement(String localName) throws SOAPException {
      return new SOAPElementImpl(this.createDocument(), (String)null, localName, (String)null);
   }

   public SOAPElement createElement(Name name) throws SOAPException {
      return new SOAPElementImpl(this.createDocument(), name);
   }

   public SOAPElement createElement(QName qname) throws SOAPException {
      return new SOAPElementImpl(this.createDocument(), qname);
   }

   public SOAPElement createElement(String localName, String prefix, String namespaceURI) throws SOAPException {
      return new SOAPElementImpl(this.createDocument(), namespaceURI, localName, prefix);
   }

   public Name createName(String localName, String prefix, String namespaceURI) throws SOAPException {
      return new NameImpl(localName, prefix, namespaceURI);
   }

   public SOAPElement createElement(Element domElement) throws SOAPException {
      if (domElement == null) {
         return null;
      } else {
         return domElement instanceof SOAPElementImpl ? (SOAPElementImpl)domElement : new SOAPElementImpl(this.createDocument(), domElement);
      }
   }

   public SOAPFault createFault(String reasonText, QName faultCode) throws SOAPException {
      SOAPFaultImpl fault = new SOAPFaultImpl(this.createDocument());
      fault.setFaultString(reasonText);
      fault.setFaultCode(faultCode);
      return fault;
   }

   public SOAPFault createFault() throws SOAPException {
      SOAPFaultImpl fault = new SOAPFaultImpl(this.createDocument());
      fault.setFaultCode(fault.getDefaultFaultCode());
      fault.setFaultString("Server Error");
      return fault;
   }
}

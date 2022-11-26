package weblogic.xml.saaj;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import weblogic.xml.domimpl.ChildNode;
import weblogic.xml.domimpl.DocumentImpl;
import weblogic.xml.domimpl.ElementBase;
import weblogic.xml.domimpl.NodeImpl;
import weblogic.xml.schema.types.XSDQName;

class SOAPFaultImpl extends SOAPElementImpl implements SOAPFault {
   static final long serialVersionUID = -2640649162317787579L;
   static final Name XML_LANG = new NameImpl("lang", "xml", "http://www.w3.org/XML/1998/namespace");

   SOAPFaultImpl(DocumentImpl ownerDocument, String soapNS, String prefix) throws DOMException {
      super(ownerDocument, soapNS, "Fault", prefix);
   }

   SOAPFaultImpl(DocumentImpl ownerDocument) throws DOMException {
      super(ownerDocument, "http://schemas.xmlsoap.org/soap/envelope/", "Fault", "env");
   }

   public void setFaultCode(String faultCode) throws SOAPException {
      this.setFaultCode(XSDQName.convertXml(faultCode, (NamespaceContext)this, true));
   }

   public void setFaultCode(QName faultCode) throws SOAPException {
      this.setFaultCode(faultCode.getLocalPart(), faultCode.getPrefix(), faultCode.getNamespaceURI());
   }

   public void setFaultCode(Name faultCode) throws SOAPException {
      this.setFaultCode(faultCode.getLocalName(), faultCode.getPrefix(), faultCode.getURI());
   }

   protected void setFaultCode(String localName, String prefix, String namespaceURI) throws SOAPException {
      this.checkNullNamespaceURI(namespaceURI, "Code");
      if ("".equals(prefix)) {
         Iterator it = this.getNamespacePrefixes();
         Set prefixes = new HashSet();

         while(it.hasNext()) {
            String next = it.next().toString();
            prefixes.add(next);
         }

         for(int i = 0; i < prefixes.size(); ++i) {
            String p = "ns" + i;
            if (!prefixes.contains(p)) {
               prefix = p;
               break;
            }
         }

         if (prefix.equals("")) {
            prefix = "ns" + prefixes.size();
         }
      }

      this.setTextValue("faultcode", this.qnameToString(localName, prefix, namespaceURI));
   }

   public String getFaultCode() {
      return this.getTextValue("faultcode");
   }

   public Name getFaultCodeAsName() {
      return new NameImpl(this.getFaultCodeAsQName());
   }

   public QName getFaultCodeAsQName() {
      SOAPElementImpl faultCodeElement = this.findUnqualifiedElement("faultcode");
      if (faultCodeElement == null) {
         return null;
      } else {
         String textValue = this.getTextValueFromElementNode(faultCodeElement);

         assert textValue != null;

         return XSDQName.convertXml(textValue, (NamespaceContext)faultCodeElement);
      }
   }

   protected QName getDefaultFaultCode() {
      return new QName("http://schemas.xmlsoap.org/soap/envelope/", "Server", "env");
   }

   public void setFaultActor(String faultActor) throws SOAPException {
      this.setTextValue("faultactor", faultActor);

      assert faultActor.equals(this.getFaultActor());

   }

   public String getFaultActor() {
      return this.getTextValue("faultactor");
   }

   public void setFaultString(String faultString) throws SOAPException {
      this.setTextValue("faultstring", faultString);
   }

   public void setFaultString(String faultString, Locale locale) throws SOAPException {
      this.setFaultString(faultString);
      SOAPElementImpl faultStringElement = this.findUnqualifiedElement("faultstring");
      faultStringElement.addAttribute(XML_LANG, localeToXmlLang(locale));
   }

   public String getFaultString() {
      return this.getTextValue("faultstring");
   }

   public Locale getFaultStringLocale() {
      SOAPElementImpl faultStringElement = this.findUnqualifiedElement("faultstring");
      if (faultStringElement != null) {
         String langAttribute = faultStringElement.getAttributeValue(XML_LANG);
         if (langAttribute != null) {
            return xmlLangToLocale(langAttribute);
         }
      }

      return null;
   }

   private SOAPElementImpl findUnqualifiedElement(String localName) {
      for(ChildNode node = this.firstChild; node != null; node = node.nextSibling()) {
         if (localName.equals(node.getLocalName()) && !node.hasUri()) {
            return (SOAPElementImpl)node;
         }
      }

      return null;
   }

   private String getTextValue(String elementName) {
      SOAPElementImpl element = this.findUnqualifiedElement(elementName);
      return element == null ? null : this.getTextValueFromElementNode(element);
   }

   protected String getTextValueFromElementNode(SOAPElement element) {
      Node firstChild = element.getFirstChild();
      return firstChild == null ? "" : firstChild.getNodeValue();
   }

   private SOAPElementImpl setTextValue(String elementName, String elementValue) throws SOAPException {
      SOAPElementImpl element = this.findUnqualifiedElement(elementName);
      if (element != null) {
         element.detachNode();
      }

      element = (SOAPElementImpl)this.addChildElement(elementName).addTextNode(elementValue);
      return element;
   }

   protected static String localeToXmlLang(Locale locale) {
      String xmlLang = locale.getLanguage();
      String country = locale.getCountry();
      if (!"".equals(country)) {
         xmlLang = xmlLang + "-" + country;
      }

      return xmlLang;
   }

   protected static Locale xmlLangToLocale(String xmlLang) {
      if (xmlLang == null) {
         return null;
      } else {
         int index = xmlLang.indexOf("-");
         if (index == -1) {
            index = xmlLang.indexOf("_");
         }

         if (index == -1) {
            return new Locale(xmlLang, "");
         } else {
            String language = xmlLang.substring(0, index);
            String country = xmlLang.substring(index + 1);
            return new Locale(language, country);
         }
      }
   }

   public ChildNode fixChildSaajType(ChildNode child) {
      if (child.getNodeType() == 1) {
         assert child instanceof SOAPElementImpl;

         if (this.matchesDetail(child)) {
            DetailImpl detail = new DetailImpl(this.ownerDocument);
            return (ChildNode)this.updateSaajChild(detail, (ElementBase)child);
         }
      }

      return super.fixChildSaajType(child);
   }

   protected boolean matchesDetail(NodeImpl child) {
      return "detail".equals(child.getLocalName()) && !child.hasUri();
   }

   public Detail getDetail() {
      SOAPElementImpl soapElement = this.findUnqualifiedElement("detail");
      if (soapElement == null) {
         return null;
      } else if (soapElement.isSaajTyped()) {
         return (Detail)soapElement;
      } else {
         DetailImpl detail = new DetailImpl(this.ownerDocument);
         this.updateSaajChild(detail, soapElement);
         return detail;
      }
   }

   public Detail addDetail() throws SOAPException {
      if (this.findUnqualifiedElement("detail") != null) {
         throw new SOAPException("detail already exists");
      } else {
         DetailImpl detail = new DetailImpl(this.ownerDocument);
         detail.isSaajTyped(true);
         this.appendChild(detail);
         return detail;
      }
   }

   public SOAPElementImpl createAndAppendSaajElement(NodeImpl parent, String uri, String localName, String prefix, int num_atts) {
      assert parent == this;

      if (!"detail".equals(localName) || uri != null && uri.length() != 0) {
         return super.createAndAppendSaajElement(parent, uri, localName, prefix, num_atts);
      } else {
         DetailImpl detail = new DetailImpl(this.ownerDocument);
         detail.isSaajTyped(true);
         this.appendChild(detail);

         assert this.firstChild != null;

         assert this.hasChildNodes();

         return detail;
      }
   }

   public void appendFaultSubcode(QName subcode) throws SOAPException {
      throw new UnsupportedOperationException("Not supported for Soap 1.1");
   }

   public Iterator getFaultSubcodes() {
      throw new UnsupportedOperationException("Not supported for Soap 1.1");
   }

   public void removeAllFaultSubcodes() {
      throw new UnsupportedOperationException("Not supported for Soap 1.1");
   }

   public boolean hasDetail() {
      SOAPElementImpl detailElement = this.findUnqualifiedElement("detail");
      return detailElement != null;
   }

   public Iterator getFaultReasonLocales() throws SOAPException {
      throw new UnsupportedOperationException("Not supported for Soap 1.1");
   }

   public Iterator getFaultReasonTexts() throws SOAPException {
      throw new UnsupportedOperationException("Not supported for Soap 1.1");
   }

   public String getFaultReasonText(Locale locale) throws SOAPException {
      throw new UnsupportedOperationException("Not supported for Soap 1.1");
   }

   public void addFaultReasonText(String text, Locale locale) throws SOAPException {
      throw new UnsupportedOperationException("Not supported for Soap 1.1");
   }

   public String getFaultNode() {
      throw new UnsupportedOperationException("Not supported for Soap 1.1");
   }

   public void setFaultNode(String uri) throws SOAPException {
      throw new UnsupportedOperationException("Not supported for Soap 1.1");
   }

   public String getFaultRole() {
      throw new UnsupportedOperationException("Not supported for Soap 1.1");
   }

   public void setFaultRole(String uri) throws SOAPException {
      throw new UnsupportedOperationException("Not supported for Soap 1.1");
   }

   void checkNullNamespaceURI(String namespace, String codeType) throws SOAPException {
      if (namespace == null || "".equals(namespace)) {
         throw new SOAPException("Fault" + codeType + " must be namespace qualified");
      }
   }
}

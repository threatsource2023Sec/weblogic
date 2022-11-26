package weblogic.xml.saaj;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import org.w3c.dom.DOMException;
import weblogic.xml.domimpl.ChildNode;
import weblogic.xml.domimpl.DocumentImpl;
import weblogic.xml.domimpl.ElementBase;
import weblogic.xml.domimpl.NodeImpl;
import weblogic.xml.schema.types.XSDQName;

class SOAP12FaultImpl extends SOAPFaultImpl implements SOAPFault {
   private static final long serialVersionUID = 8348623837493707205L;

   SOAP12FaultImpl(DocumentImpl ownerDocument) throws DOMException {
      super(ownerDocument, "http://www.w3.org/2003/05/soap-envelope", "env");
   }

   SOAP12FaultImpl(DocumentImpl ownerDocument, String soapNS, String prefix) throws DOMException {
      super(ownerDocument, soapNS, prefix);
   }

   protected void setFaultCode(String localname, String prefix, String uri) throws SOAPException {
      this.checkNullNamespaceURI(uri, "Code");
      if (!this.isStandardFaultCode(uri, localname)) {
         throw new SOAPException(new QName(uri, localname) + ": Fault code is not standard for SOAP 1.2");
      } else {
         String qnameString = this.qnameToString(localname, prefix, uri);
         SOAPElementImpl faultCodeElement = this.findOrCreateElement(SOAPConstants.FAULT12_CODE);
         this.removeAllChildren(faultCodeElement, SOAPConstants.FAULT12_VALUE);
         faultCodeElement.addChildElement(SOAPConstants.FAULT12_VALUE).addTextNode(qnameString);
      }
   }

   private boolean isStandardFaultCode(String namespace, String localName) {
      QName faultCodeQName = new QName(namespace, localName);
      return javax.xml.soap.SOAPConstants.SOAP_DATAENCODINGUNKNOWN_FAULT.equals(faultCodeQName) || javax.xml.soap.SOAPConstants.SOAP_MUSTUNDERSTAND_FAULT.equals(faultCodeQName) || javax.xml.soap.SOAPConstants.SOAP_RECEIVER_FAULT.equals(faultCodeQName) || javax.xml.soap.SOAPConstants.SOAP_SENDER_FAULT.equals(faultCodeQName) || javax.xml.soap.SOAPConstants.SOAP_VERSIONMISMATCH_FAULT.equals(faultCodeQName);
   }

   public QName getFaultCodeAsQName() {
      SOAPElementImpl valueElement = this.findFaultCodeValueElement();
      String colonizedName = this.getTextValueFromElementNode(valueElement);

      assert colonizedName != null;

      return XSDQName.convertXml(colonizedName, (NamespaceContext)valueElement);
   }

   private SOAPElementImpl findFaultCodeValueElement() {
      SOAPElementImpl el = this.findFirstElement(SOAPConstants.FAULT12_CODE);
      if (el == null) {
         return null;
      } else {
         Iterator it = el.getChildElements(SOAPConstants.FAULT12_VALUE);
         return this.findFirstElement(it);
      }
   }

   protected QName getDefaultFaultCode() {
      return new QName("http://www.w3.org/2003/05/soap-envelope", "Sender", "env");
   }

   private void removeDefaultReason() {
      SOAPElementImpl reason = this.findFirstElement(SOAPConstants.FAULT12_Reason);
      if (reason != null) {
         Iterator it = reason.getChildElements(SOAPConstants.FAULT12_TEXT);
         SOAPElementImpl faultText = this.findFirstElement(it);
         if (faultText != null && faultText.getValue().equals("Server Error")) {
            faultText.detachNode();
         }
      }

   }

   public String getFaultCode() {
      SOAPElementImpl valueElement = this.findFaultCodeValueElement();
      return valueElement == null ? null : this.getTextValueFromElementNode(valueElement);
   }

   private SOAPElementImpl findFirstElement(QName elementType) {
      Iterator childElements = this.getChildElements(elementType);
      return this.findFirstElement(childElements);
   }

   private SOAPElementImpl findFirstElement(Iterator it) {
      return it != null && it.hasNext() ? (SOAPElementImpl)it.next() : null;
   }

   private void removeAllChildren(SOAPElementImpl parent, QName type) {
      Iterator it = parent.getChildElements(type);

      while(it.hasNext()) {
         Node node = (Node)it.next();
         node.detachNode();
      }

   }

   public void setFaultActor(String faultActor) throws SOAPException {
      this.setFaultRole(faultActor);
   }

   public String getFaultActor() {
      return this.getFaultRole();
   }

   public void setFaultString(String faultString) throws SOAPException {
      this.clearFaultString();
      this.addFaultReasonText(faultString, Locale.getDefault());
   }

   public void setFaultString(String faultString, Locale locale) throws SOAPException {
      this.clearFaultString();
      this.addFaultReasonText(faultString, locale);
   }

   private void clearFaultString() {
      SOAPElementImpl reason = this.findFirstElement(SOAPConstants.FAULT12_Reason);
      if (reason != null) {
         this.removeAllChildren(reason, SOAPConstants.FAULT12_TEXT);
      }

   }

   public String getFaultString() {
      String reason = null;

      try {
         Iterator it = this.getFaultReasonTexts();
         if (it.hasNext()) {
            reason = (String)it.next();
         }
      } catch (SOAPException var3) {
      }

      return reason;
   }

   public Locale getFaultStringLocale() {
      Locale locale = null;

      try {
         Iterator it = this.getFaultReasonLocales();
         if (it.hasNext()) {
            locale = (Locale)it.next();
         }
      } catch (SOAPException var3) {
      }

      return locale;
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
            DetailImpl d = new DetailImpl(this.ownerDocument, "http://www.w3.org/2003/05/soap-envelope");
            return (ChildNode)this.updateSaajChild(d, (ElementBase)child);
         }
      }

      return super.fixChildSaajType(child);
   }

   protected boolean matchesDetail(NodeImpl child) {
      return "Detail".equals(child.getLocalName()) && "http://www.w3.org/2003/05/soap-envelope".equals(child.getNamespaceURI());
   }

   public Detail getDetail() {
      SOAPElementImpl detailElement = this.findDetailElement();
      if (detailElement == null) {
         return null;
      } else if (detailElement.isSaajTyped()) {
         return (Detail)detailElement;
      } else {
         DetailImpl detail = new DetailImpl(this.ownerDocument, "http://www.w3.org/2003/05/soap-envelope");
         this.updateSaajChild(detail, detailElement);
         return detail;
      }
   }

   private SOAPElementImpl findDetailElement() {
      Iterator childElements = this.getChildElements(SOAPConstants.FAULT12_DETAIL);
      return this.findFirstElement(childElements);
   }

   public Detail addDetail() throws SOAPException {
      if (this.findDetailElement() != null) {
         throw new SOAPException("detail already exists");
      } else {
         DetailImpl d = new DetailImpl(this.ownerDocument, "http://www.w3.org/2003/05/soap-envelope");
         d.isSaajTyped(true);
         this.appendChild(d);
         return d;
      }
   }

   public SOAPElementImpl createAndAppendSaajElement(NodeImpl parent, String uri, String localName, String prefix, int num_atts) {
      assert parent == this;

      if ("Detail".equals(localName) && "http://www.w3.org/2003/05/soap-envelope".equals(uri)) {
         DetailImpl d = new DetailImpl(this.ownerDocument, "http://www.w3.org/2003/05/soap-envelope");
         d.isSaajTyped(true);
         this.appendChild(d);

         assert this.firstChild != null;

         assert this.hasChildNodes();

         return d;
      } else {
         return super.createAndAppendSaajElement(parent, uri, localName, prefix, num_atts);
      }
   }

   public void setFaultCode(QName name) throws SOAPException {
      this.setFaultCode(name.getLocalPart(), name.getPrefix(), name.getNamespaceURI());
   }

   public void removeAllFaultSubcodes() {
      SOAPElementImpl code = this.findFirstElement(SOAPConstants.FAULT12_CODE);
      if (code != null) {
         this.removeAllChildren(code, SOAPConstants.FAULT12_SUBCODE);
      }
   }

   public void appendFaultSubcode(QName name) throws SOAPException {
      this.checkNullNamespaceURI(name.getNamespaceURI(), "SubCode");
      SOAPElementImpl code = this.findOrCreateElement(SOAPConstants.FAULT12_CODE);

      while(true) {
         Iterator it = code.getChildElements(SOAPConstants.FAULT12_SUBCODE);
         if (!it.hasNext()) {
            SOAPElementImpl subcode = (SOAPElementImpl)code.addChildElement(SOAPConstants.FAULT12_SUBCODE);
            subcode.addChildElement(SOAPConstants.FAULT12_VALUE).addTextNode(this.qnameToString(name.getLocalPart(), name.getPrefix(), name.getNamespaceURI()));
            return;
         }

         code = (SOAPElementImpl)it.next();
      }
   }

   public boolean hasDetail() {
      return this.getDetail() != null;
   }

   public Iterator getFaultReasonLocales() throws SOAPException {
      Iterator it = this.getChildElements(SOAPConstants.FAULT12_Reason);
      if (!it.hasNext()) {
         return it;
      } else {
         List locales = new LinkedList();
         SOAPElementImpl reason = (SOAPElementImpl)it.next();
         it = reason.getChildElements(SOAPConstants.FAULT12_TEXT);

         while(it.hasNext()) {
            SOAPElementImpl text = (SOAPElementImpl)it.next();
            String localeValue = text.getAttributeValue(XML_LANG);
            if (localeValue == null) {
               throw new SOAPException("required attribute xml:lang is not found.");
            }

            String[] ss = localeValue.split("-");
            if (ss.length == 2) {
               locales.add(new Locale(ss[0], ss[1]));
            } else {
               locales.add(new Locale(localeValue));
            }
         }

         return Collections.unmodifiableList(locales).iterator();
      }
   }

   public Iterator getFaultReasonTexts() throws SOAPException {
      Iterator it = this.getChildElements(SOAPConstants.FAULT12_Reason);
      return !it.hasNext() ? it : new Iterator() {
         Iterator delegate;

         {
            this.delegate = ((SOAPElementImpl)SOAP12FaultImpl.this.getChildElements(SOAPConstants.FAULT12_Reason).next()).getChildElements(SOAPConstants.FAULT12_TEXT);
         }

         public boolean hasNext() {
            return this.delegate.hasNext();
         }

         public Object next() {
            SOAPElementImpl text = (SOAPElementImpl)this.delegate.next();
            return SOAP12FaultImpl.this.getTextValueFromElementNode(text);
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public String getFaultReasonText(Locale locale) throws SOAPException {
      Iterator it = this.getChildElements(SOAPConstants.FAULT12_Reason);
      SOAPElementImpl reason = this.findFirstElement(it);
      if (reason != null) {
         it = reason.getChildElements(SOAPConstants.FAULT12_TEXT);

         while(it.hasNext()) {
            SOAPElementImpl text = (SOAPElementImpl)it.next();
            if (localeToXmlLang(locale).equals(text.getAttributeValue(XML_LANG))) {
               return this.getTextValueFromElementNode(text);
            }
         }
      }

      return null;
   }

   public void addFaultReasonText(String text, Locale locale) throws SOAPException {
      this.removeDefaultReason();
      SOAPElementImpl reason = this.findOrCreateElement(SOAPConstants.FAULT12_Reason);
      Iterator it = reason.getChildElements(SOAPConstants.FAULT12_TEXT);

      while(it.hasNext()) {
         SOAPElementImpl currentText = (SOAPElementImpl)it.next();
         if (localeToXmlLang(locale).equals(currentText.getAttributeValue(XML_LANG))) {
            currentText.detachNode();
         }
      }

      SOAPElementImpl textNode = (SOAPElementImpl)reason.addChildElement(SOAPConstants.FAULT12_TEXT);
      textNode.addTextNode(text);
      textNode.addAttribute(XML_LANG, localeToXmlLang(locale));
   }

   public String getFaultNode() {
      SOAPElementImpl node = this.findFirstElement(SOAPConstants.FAULT12_NODE);
      return node == null ? null : this.getTextValueFromElementNode(node);
   }

   public void setFaultNode(String uri) throws SOAPException {
      SOAPElementImpl faultNode = this.deleteExistingAndCreateNewElement(SOAPConstants.FAULT12_NODE);
      faultNode.addTextNode(uri);
   }

   public String getFaultRole() {
      SOAPElementImpl role = this.findFirstElement(SOAPConstants.FAULT12_ROLE);
      return role == null ? null : this.getTextValueFromElementNode(role);
   }

   public void setFaultRole(String uri) throws SOAPException {
      SOAPElementImpl role = this.deleteExistingAndCreateNewElement(SOAPConstants.FAULT12_ROLE);
      role.addTextNode(uri);
   }

   private SOAPElementImpl findOrCreateElement(QName name) throws SOAPException {
      Iterator it = this.getChildElements(name);
      return it.hasNext() ? (SOAPElementImpl)it.next() : (SOAPElementImpl)this.addChildElement(name);
   }

   private SOAPElementImpl deleteExistingAndCreateNewElement(QName name) throws SOAPException {
      SOAPElementImpl node = this.findFirstElement(name);
      if (node != null) {
         node.detachNode();
      }

      return (SOAPElementImpl)this.addChildElement(name);
   }

   public Iterator getFaultSubcodes() {
      return new SubCodeIterator();
   }

   private class SubCodeIterator implements Iterator {
      private SOAPElementImpl current;

      SubCodeIterator() {
         Iterator it = SOAP12FaultImpl.this.getChildElements(SOAPConstants.FAULT12_CODE);
         if (!it.hasNext()) {
            this.current = SOAP12FaultImpl.this;
         } else {
            this.current = (SOAPElementImpl)it.next();
         }

      }

      public boolean hasNext() {
         Iterator it = this.current.getChildElements(SOAPConstants.FAULT12_SUBCODE);
         if (!it.hasNext()) {
            return false;
         } else {
            SOAPElementImpl subcode = (SOAPElementImpl)it.next();
            it = subcode.getChildElements(SOAPConstants.FAULT12_VALUE);
            return it.hasNext();
         }
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            Iterator it = this.current.getChildElements(SOAPConstants.FAULT12_SUBCODE);
            SOAPElementImpl subcode = (SOAPElementImpl)it.next();
            it = subcode.getChildElements(SOAPConstants.FAULT12_VALUE);
            SOAPElementImpl value = (SOAPElementImpl)it.next();
            String codeValue = SOAP12FaultImpl.this.getTextValueFromElementNode(value);
            QName result = XSDQName.convertXml(codeValue, (NamespaceContext)value);
            this.current = subcode;
            return result;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}

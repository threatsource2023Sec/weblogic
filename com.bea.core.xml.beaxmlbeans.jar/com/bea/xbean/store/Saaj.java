package com.bea.xbean.store;

import com.bea.xbean.soap.Detail;
import com.bea.xbean.soap.DetailEntry;
import com.bea.xbean.soap.Name;
import com.bea.xbean.soap.Node;
import com.bea.xbean.soap.SOAPBody;
import com.bea.xbean.soap.SOAPBodyElement;
import com.bea.xbean.soap.SOAPElement;
import com.bea.xbean.soap.SOAPEnvelope;
import com.bea.xbean.soap.SOAPException;
import com.bea.xbean.soap.SOAPFault;
import com.bea.xbean.soap.SOAPHeader;
import com.bea.xbean.soap.SOAPHeaderElement;
import com.bea.xbean.soap.SOAPPart;
import com.bea.xbean.soap.Text;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface Saaj {
   String SAAJ_IMPL = "SAAJ_IMPL";

   void setCallback(SaajCallback var1);

   Class identifyElement(QName var1, QName var2);

   void soapNode_detachNode(Node var1);

   void soapNode_recycleNode(Node var1);

   String soapNode_getValue(Node var1);

   void soapNode_setValue(Node var1, String var2);

   SOAPElement soapNode_getParentElement(Node var1);

   void soapNode_setParentElement(Node var1, SOAPElement var2);

   void soapElement_removeContents(SOAPElement var1);

   String soapElement_getEncodingStyle(SOAPElement var1);

   void soapElement_setEncodingStyle(SOAPElement var1, String var2);

   boolean soapElement_removeNamespaceDeclaration(SOAPElement var1, String var2);

   Iterator soapElement_getAllAttributes(SOAPElement var1);

   Iterator soapElement_getChildElements(SOAPElement var1);

   Iterator soapElement_getNamespacePrefixes(SOAPElement var1);

   SOAPElement soapElement_addAttribute(SOAPElement var1, Name var2, String var3) throws SOAPException;

   SOAPElement soapElement_addChildElement(SOAPElement var1, SOAPElement var2) throws SOAPException;

   SOAPElement soapElement_addChildElement(SOAPElement var1, Name var2) throws SOAPException;

   SOAPElement soapElement_addChildElement(SOAPElement var1, String var2) throws SOAPException;

   SOAPElement soapElement_addChildElement(SOAPElement var1, String var2, String var3) throws SOAPException;

   SOAPElement soapElement_addChildElement(SOAPElement var1, String var2, String var3, String var4) throws SOAPException;

   SOAPElement soapElement_addNamespaceDeclaration(SOAPElement var1, String var2, String var3);

   SOAPElement soapElement_addTextNode(SOAPElement var1, String var2);

   String soapElement_getAttributeValue(SOAPElement var1, Name var2);

   Iterator soapElement_getChildElements(SOAPElement var1, Name var2);

   Name soapElement_getElementName(SOAPElement var1);

   String soapElement_getNamespaceURI(SOAPElement var1, String var2);

   Iterator soapElement_getVisibleNamespacePrefixes(SOAPElement var1);

   boolean soapElement_removeAttribute(SOAPElement var1, Name var2);

   SOAPBody soapEnvelope_addBody(SOAPEnvelope var1) throws SOAPException;

   SOAPBody soapEnvelope_getBody(SOAPEnvelope var1) throws SOAPException;

   SOAPHeader soapEnvelope_getHeader(SOAPEnvelope var1) throws SOAPException;

   SOAPHeader soapEnvelope_addHeader(SOAPEnvelope var1) throws SOAPException;

   Name soapEnvelope_createName(SOAPEnvelope var1, String var2);

   Name soapEnvelope_createName(SOAPEnvelope var1, String var2, String var3, String var4);

   Iterator soapHeader_examineAllHeaderElements(SOAPHeader var1);

   Iterator soapHeader_extractAllHeaderElements(SOAPHeader var1);

   Iterator soapHeader_examineHeaderElements(SOAPHeader var1, String var2);

   Iterator soapHeader_examineMustUnderstandHeaderElements(SOAPHeader var1, String var2);

   Iterator soapHeader_extractHeaderElements(SOAPHeader var1, String var2);

   SOAPHeaderElement soapHeader_addHeaderElement(SOAPHeader var1, Name var2);

   void soapPart_removeAllMimeHeaders(SOAPPart var1);

   void soapPart_removeMimeHeader(SOAPPart var1, String var2);

   Iterator soapPart_getAllMimeHeaders(SOAPPart var1);

   SOAPEnvelope soapPart_getEnvelope(SOAPPart var1);

   Source soapPart_getContent(SOAPPart var1);

   void soapPart_setContent(SOAPPart var1, Source var2);

   String[] soapPart_getMimeHeader(SOAPPart var1, String var2);

   void soapPart_addMimeHeader(SOAPPart var1, String var2, String var3);

   void soapPart_setMimeHeader(SOAPPart var1, String var2, String var3);

   Iterator soapPart_getMatchingMimeHeaders(SOAPPart var1, String[] var2);

   Iterator soapPart_getNonMatchingMimeHeaders(SOAPPart var1, String[] var2);

   boolean soapBody_hasFault(SOAPBody var1);

   SOAPFault soapBody_addFault(SOAPBody var1) throws SOAPException;

   SOAPFault soapBody_getFault(SOAPBody var1);

   SOAPBodyElement soapBody_addBodyElement(SOAPBody var1, Name var2);

   SOAPBodyElement soapBody_addDocument(SOAPBody var1, Document var2);

   SOAPFault soapBody_addFault(SOAPBody var1, Name var2, String var3) throws SOAPException;

   SOAPFault soapBody_addFault(SOAPBody var1, Name var2, String var3, java.util.Locale var4) throws SOAPException;

   Detail soapFault_addDetail(SOAPFault var1) throws SOAPException;

   Detail soapFault_getDetail(SOAPFault var1);

   String soapFault_getFaultActor(SOAPFault var1);

   String soapFault_getFaultCode(SOAPFault var1);

   Name soapFault_getFaultCodeAsName(SOAPFault var1);

   String soapFault_getFaultString(SOAPFault var1);

   java.util.Locale soapFault_getFaultStringLocale(SOAPFault var1);

   void soapFault_setFaultActor(SOAPFault var1, String var2);

   void soapFault_setFaultCode(SOAPFault var1, Name var2) throws SOAPException;

   void soapFault_setFaultCode(SOAPFault var1, String var2) throws SOAPException;

   void soapFault_setFaultString(SOAPFault var1, String var2);

   void soapFault_setFaultString(SOAPFault var1, String var2, java.util.Locale var3);

   void soapHeaderElement_setMustUnderstand(SOAPHeaderElement var1, boolean var2);

   boolean soapHeaderElement_getMustUnderstand(SOAPHeaderElement var1);

   void soapHeaderElement_setActor(SOAPHeaderElement var1, String var2);

   String soapHeaderElement_getActor(SOAPHeaderElement var1);

   boolean soapText_isComment(Text var1);

   DetailEntry detail_addDetailEntry(Detail var1, Name var2);

   Iterator detail_getDetailEntries(Detail var1);

   public interface SaajCallback {
      void setSaajData(org.w3c.dom.Node var1, Object var2);

      Object getSaajData(org.w3c.dom.Node var1);

      Element createSoapElement(QName var1, QName var2);

      Element importSoapElement(Document var1, Element var2, boolean var3, QName var4);
   }
}

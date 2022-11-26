package com.bea.common.security.saml.registry;

import java.util.ArrayList;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class SAMLXMLUtil {
   public static final String XMLNS_SPR = "xmlns:spr";
   public static final String SPR_NAME_SPACE_PREFIX = "spr";
   public static final String SPR_NAME_SPACE_URI = "urn:bea:security:saml:1.1:partner-registry";
   public static final String ASSERTING_PARTY_REGISTRY = "AssertingPartyRegistry";
   public static final String RELYING_PARTY_REGISTRY = "RelyingPartyRegistry";
   public static final String PARTNER_CERTIFICATE = "PartnerCertificate";
   public static final String PARTNER_CERTIFICATE_ID_ATTR = "Id";
   public static final String ASSERTING_PARTY = "AssertingParty";
   public static final String RELYING_PARTY = "RelyingParty";
   public static final String PARTNER_ID_ATTR = "Id";
   public static final String PARTNER_PROFILE_ATTR = "Profile";
   public static final String PARTNER_ENABLED_ATTR = "Enabled";
   public static final String PARTNER_DESCRIPTION = "Description";
   public static final String XMLNS_DS = "xmlns:ds";
   public static final String DS_NAME_SPACE_PREFIX = "ds";
   public static final String DS_NAME_SPACE_URI = "http://www.w3.org/2000/09/xmldsig#";
   public static final String KEY_INFO = "KeyInfo";
   public static final String KEY_INFO_ID_ATTR = "Id";
   public static final String X509_DATA = "X509Data";
   public static final String X509_CERTIFICATE = "X509Certificate";

   public static Element generateTwoLevelElement(Document dom, String namespaceURI, String tagName, String subTagName, String[] textValues) throws DOMException {
      Element element = generateNormalElement(dom, namespaceURI, tagName, (String)null);
      if (textValues != null) {
         for(int i = 0; i < textValues.length; ++i) {
            Element subElement = generateNormalElement(dom, namespaceURI, subTagName, textValues[i]);
            element.appendChild(subElement);
         }
      }

      return element;
   }

   public static Element generateNormalElement(Document dom, String namespaceURI, String tagName, String textValue) throws DOMException {
      Element element = null;
      if (namespaceURI != null && namespaceURI.length() > 0) {
         element = dom.createElementNS(namespaceURI, tagName);
      } else {
         element = dom.createElement(tagName);
      }

      if (textValue != null && textValue.length() > 0) {
         Text textElement = dom.createTextNode(textValue);
         element.appendChild(textElement);
      }

      return element;
   }

   public static String getSingleValueFromElement(Element element) {
      if (element == null) {
         return null;
      } else {
         Node firstChild = element.getFirstChild();
         return firstChild != null && firstChild instanceof Text ? ((Text)firstChild).getNodeValue() : null;
      }
   }

   public static String[] getMultiValuesFromElement(Element element) {
      NodeList children = element.getChildNodes();
      if (children != null && children.getLength() != 0) {
         ArrayList values = new ArrayList();

         for(int i = 0; i < children.getLength(); ++i) {
            if (children.item(i) instanceof Element) {
               String value = getSingleValueFromElement((Element)children.item(i));
               if (value != null) {
                  values.add(value);
               }
            }
         }

         return (String[])((String[])values.toArray(new String[values.size()]));
      } else {
         return null;
      }
   }

   public static String getPartnerId(Element partnerElement) {
      return partnerElement.getAttribute("Id");
   }

   public static Element getChildElement(Element element, String childTagName) {
      NodeList nodeList = element.getElementsByTagNameNS("urn:bea:security:saml:1.1:partner-registry", childTagName);
      return nodeList != null && nodeList.getLength() != 0 ? (Element)nodeList.item(0) : null;
   }
}

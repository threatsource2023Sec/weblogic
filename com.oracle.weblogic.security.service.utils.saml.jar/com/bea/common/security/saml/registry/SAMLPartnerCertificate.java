package com.bea.common.security.saml.registry;

import com.bea.common.logger.spi.LoggerSpi;
import java.io.IOException;
import java.io.Serializable;
import java.security.cert.X509Certificate;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import weblogic.management.utils.InvalidParameterException;
import weblogic.utils.encoders.BASE64Decoder;

public class SAMLPartnerCertificate implements Serializable {
   private transient LoggerSpi log = null;
   String alias = null;
   X509Certificate cert = null;

   protected boolean isDebugEnabled() {
      return this.log != null ? this.log.isDebugEnabled() : false;
   }

   protected void logDebug(String method, String msg) {
      if (this.log != null && this.log.isDebugEnabled()) {
         this.log.debug("SAMLPartnerEntry: " + method + ": " + msg);
      }

   }

   public SAMLPartnerCertificate() {
   }

   public SAMLPartnerCertificate(LoggerSpi logger, String alias, X509Certificate cert) throws InvalidParameterException {
      if (alias != null && cert != null) {
         this.log = logger;
         this.alias = alias;
         this.cert = cert;
      } else {
         throw new InvalidParameterException("Partner Certificate with empty alias or X509Certificate");
      }
   }

   public SAMLPartnerCertificate(LoggerSpi logger, Element certificateElement) throws InvalidParameterException {
      if (certificateElement == null) {
         throw new InvalidParameterException("Empty PartnerCertificate element");
      } else {
         this.log = logger;
         this.loadCertFromDOMElement(certificateElement);
      }
   }

   private void loadCertFromDOMElement(Element partnerCertificateElement) throws InvalidParameterException {
      String method = "loadCertFromDOMElement";
      if (partnerCertificateElement == null) {
         throw new InvalidParameterException("Invalid import certificate parameter");
      } else {
         this.alias = partnerCertificateElement.getAttribute("Id");
         if (this.alias != null && this.alias.length() != 0) {
            NodeList keyInfoNodeList = partnerCertificateElement.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "KeyInfo");
            if (keyInfoNodeList != null && keyInfoNodeList.getLength() == 1) {
               Element keyInfoElement = (Element)keyInfoNodeList.item(0);
               if (keyInfoElement != null) {
                  NodeList x509CertificateNodeList = keyInfoElement.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "X509Certificate");
                  if (x509CertificateNodeList == null || x509CertificateNodeList.getLength() != 1) {
                     throw new RuntimeException("Filter Certificate from KeyInfo node failed,  X509Certificate element format error.");
                  }

                  Element x509CertificateElement = (Element)x509CertificateNodeList.item(0);
                  Text certificateTextNode = (Text)x509CertificateElement.getFirstChild();
                  if (certificateTextNode == null) {
                     throw new RuntimeException("Filter Certificate from KeyInfo node failed,  X509Certificate element format error.");
                  }

                  String encodedCertData = certificateTextNode.getNodeValue();
                  if (encodedCertData == null || encodedCertData.length() == 0) {
                     throw new RuntimeException("Filter Certificate from KeyInfo node failed,  X509Certificate element format error.");
                  }

                  byte[] encodedBytes = null;

                  byte[] encodedBytes;
                  try {
                     encodedBytes = (new BASE64Decoder()).decodeBuffer(encodedCertData);
                  } catch (IOException var11) {
                     throw new RuntimeException("Filter Certificate from KeyInfo node failed, IOExcption when base64 decoding the certificate data");
                  }

                  this.cert = SAMLCertRegLDAPDelegate.readCertificateFromEncoded(encodedBytes);
                  if (this.cert == null) {
                     throw new RuntimeException("Import Certificate from KeyInfo node failed,  X509Certificate data format error.");
                  }
               }
            }

         } else {
            throw new RuntimeException("The Partner Certificate element is not well formatted, missing attribute: Id");
         }
      }
   }

   public Element toDOMElement(Document dom) throws DOMException {
      String derString = SAMLCertRegLDAPDelegate.getCertificateDERFormat(this.cert);
      Element partnerCertificateElement = dom.createElementNS("urn:bea:security:saml:1.1:partner-registry", "spr:PartnerCertificate");
      partnerCertificateElement.setAttribute("Id", this.alias);
      Element keyInfoElement = dom.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:KeyInfo");
      partnerCertificateElement.appendChild(keyInfoElement);
      Element x509DataElement = SAMLXMLUtil.generateTwoLevelElement(dom, "http://www.w3.org/2000/09/xmldsig#", "ds:X509Data", "ds:X509Certificate", new String[]{derString});
      keyInfoElement.appendChild(x509DataElement);
      return partnerCertificateElement;
   }

   public String getAlias() {
      return this.alias;
   }

   public void setAlias(String newAlias) {
      this.alias = newAlias;
   }

   public X509Certificate getCert() {
      return this.cert;
   }
}

package org.opensaml.saml.saml1.core.impl;

import com.google.common.base.Strings;
import javax.annotation.Nonnull;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.ResponseAbstractType;
import org.opensaml.xmlsec.signature.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public abstract class ResponseAbstractTypeUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(ResponseUnmarshaller.class);

   public XMLObject unmarshall(Element domElement) throws UnmarshallingException {
      ResponseAbstractType response = (ResponseAbstractType)super.unmarshall(domElement);
      if (response.getVersion() != SAMLVersion.VERSION_10 && !Strings.isNullOrEmpty(response.getID())) {
         domElement.setIdAttributeNS((String)null, "ResponseID", true);
      }

      return response;
   }

   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      ResponseAbstractType response = (ResponseAbstractType)parentSAMLObject;
      if (childSAMLObject instanceof Signature) {
         response.setSignature((Signature)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      ResponseAbstractType response = (ResponseAbstractType)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("ResponseID")) {
            response.setID(attribute.getValue());
         } else if (attribute.getLocalName().equals("InResponseTo")) {
            response.setInResponseTo(attribute.getValue());
         } else if (attribute.getLocalName().equals("IssueInstant") && !Strings.isNullOrEmpty(attribute.getValue())) {
            response.setIssueInstant(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else {
            int minor;
            if (attribute.getLocalName().equals("MajorVersion")) {
               try {
                  minor = Integer.parseInt(attribute.getValue());
                  if (minor != 1) {
                     throw new UnmarshallingException("MajorVersion was invalid, must be 1");
                  }
               } catch (NumberFormatException var7) {
                  this.log.error("Failed to parse major version", var7);
                  throw new UnmarshallingException(var7);
               }
            } else if (attribute.getLocalName().equals("MinorVersion")) {
               try {
                  minor = Integer.parseInt(attribute.getValue());
               } catch (NumberFormatException var6) {
                  this.log.error("Failed to parse minor version", var6);
                  throw new UnmarshallingException(var6);
               }

               if (minor == 0) {
                  response.setVersion(SAMLVersion.VERSION_10);
               } else if (minor == 1) {
                  response.setVersion(SAMLVersion.VERSION_11);
               }
            } else if (attribute.getLocalName().equals("Recipient")) {
               response.setRecipient(attribute.getValue());
            } else {
               super.processAttribute(samlObject, attribute);
            }
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}

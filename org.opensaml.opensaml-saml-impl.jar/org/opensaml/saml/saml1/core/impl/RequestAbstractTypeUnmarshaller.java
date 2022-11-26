package org.opensaml.saml.saml1.core.impl;

import com.google.common.base.Strings;
import javax.annotation.Nonnull;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.RequestAbstractType;
import org.opensaml.saml.saml1.core.RespondWith;
import org.opensaml.xmlsec.signature.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public abstract class RequestAbstractTypeUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(RequestAbstractType.class);

   public XMLObject unmarshall(Element domElement) throws UnmarshallingException {
      RequestAbstractType request = (RequestAbstractType)super.unmarshall(domElement);
      if (request.getVersion() != SAMLVersion.VERSION_10 && !Strings.isNullOrEmpty(request.getID())) {
         domElement.setIdAttributeNS((String)null, "RequestID", true);
      }

      return request;
   }

   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      RequestAbstractType request = (RequestAbstractType)parentSAMLObject;
      if (childSAMLObject instanceof Signature) {
         request.setSignature((Signature)childSAMLObject);
      } else if (childSAMLObject instanceof RespondWith) {
         request.getRespondWiths().add((RespondWith)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlElement, Attr attribute) throws UnmarshallingException {
      RequestAbstractType request = (RequestAbstractType)samlElement;
      if (attribute.getNamespaceURI() == null) {
         if ("RequestID".equals(attribute.getLocalName())) {
            request.setID(attribute.getValue());
         } else if ("IssueInstant".equals(attribute.getLocalName()) && !Strings.isNullOrEmpty(attribute.getValue())) {
            DateTime cal = new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC());
            request.setIssueInstant(cal);
         } else {
            int minor;
            if (attribute.getLocalName().equals("MajorVersion")) {
               try {
                  minor = Integer.parseInt(attribute.getValue());
                  if (minor != 1) {
                     throw new UnmarshallingException("MajorVersion was invalid, must be 1");
                  }
               } catch (NumberFormatException var7) {
                  this.log.error("Failed to parse major version string", var7);
                  throw new UnmarshallingException(var7);
               }
            } else if ("MinorVersion".equals(attribute.getLocalName())) {
               try {
                  minor = Integer.parseInt(attribute.getValue());
               } catch (NumberFormatException var6) {
                  this.log.error("Unable to parse minor version string", var6);
                  throw new UnmarshallingException(var6);
               }

               if (minor == 0) {
                  request.setVersion(SAMLVersion.VERSION_10);
               } else if (minor == 1) {
                  request.setVersion(SAMLVersion.VERSION_11);
               }
            } else {
               super.processAttribute(samlElement, attribute);
            }
         }
      } else {
         super.processAttribute(samlElement, attribute);
      }

   }
}

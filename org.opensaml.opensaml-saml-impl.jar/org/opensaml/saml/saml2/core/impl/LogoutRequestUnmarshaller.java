package org.opensaml.saml.saml2.core.impl;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.LogoutRequest;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.SessionIndex;
import org.w3c.dom.Attr;

public class LogoutRequestUnmarshaller extends RequestAbstractTypeUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      LogoutRequest req = (LogoutRequest)parentSAMLObject;
      if (childSAMLObject instanceof BaseID) {
         req.setBaseID((BaseID)childSAMLObject);
      } else if (childSAMLObject instanceof NameID) {
         req.setNameID((NameID)childSAMLObject);
      } else if (childSAMLObject instanceof EncryptedID) {
         req.setEncryptedID((EncryptedID)childSAMLObject);
      } else if (childSAMLObject instanceof SessionIndex) {
         req.getSessionIndexes().add((SessionIndex)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      LogoutRequest req = (LogoutRequest)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("Reason")) {
            req.setReason(attribute.getValue());
         } else if (attribute.getLocalName().equals("NotOnOrAfter") && !Strings.isNullOrEmpty(attribute.getValue())) {
            req.setNotOnOrAfter(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}

package org.opensaml.saml.ext.saml2delrestrict.impl;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2delrestrict.Delegate;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.w3c.dom.Attr;

public class DelegateUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      Delegate delegate = (Delegate)samlObject;
      if (attribute.getNamespaceURI() == null) {
         String attrName = attribute.getLocalName();
         if ("ConfirmationMethod".equals(attrName)) {
            delegate.setConfirmationMethod(attribute.getValue());
         } else if ("DelegationInstant".equals(attrName)) {
            delegate.setDelegationInstant(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Delegate delegate = (Delegate)parentSAMLObject;
      if (childSAMLObject instanceof BaseID) {
         delegate.setBaseID((BaseID)childSAMLObject);
      } else if (childSAMLObject instanceof NameID) {
         delegate.setNameID((NameID)childSAMLObject);
      } else if (childSAMLObject instanceof EncryptedID) {
         delegate.setEncryptedID((EncryptedID)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}

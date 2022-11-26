package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.IDPList;
import org.opensaml.saml.saml2.core.RequesterID;
import org.opensaml.saml.saml2.core.Scoping;
import org.w3c.dom.Attr;

public class ScopingUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Scoping scoping = (Scoping)parentSAMLObject;
      if (childSAMLObject instanceof IDPList) {
         scoping.setIDPList((IDPList)childSAMLObject);
      } else if (childSAMLObject instanceof RequesterID) {
         scoping.getRequesterIDs().add((RequesterID)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      Scoping scoping = (Scoping)samlObject;
      if (attribute.getLocalName().equals("ProxyCount") && attribute.getNamespaceURI() == null) {
         scoping.setProxyCount(Integer.valueOf(attribute.getValue()));
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}

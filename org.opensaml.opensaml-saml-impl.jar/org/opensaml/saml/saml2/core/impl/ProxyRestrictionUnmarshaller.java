package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.ProxyRestriction;
import org.w3c.dom.Attr;

public class ProxyRestrictionUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      ProxyRestriction proxyRestriction = (ProxyRestriction)parentObject;
      if (childObject instanceof Audience) {
         proxyRestriction.getAudiences().add((Audience)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      ProxyRestriction proxyRestriction = (ProxyRestriction)samlObject;
      if (attribute.getLocalName().equals("Count") && attribute.getNamespaceURI() == null) {
         proxyRestriction.setProxyCount(Integer.valueOf(attribute.getValue()));
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}

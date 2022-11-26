package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.OrganizationDisplayName;
import org.opensaml.saml.saml2.metadata.OrganizationName;
import org.opensaml.saml.saml2.metadata.OrganizationURL;
import org.w3c.dom.Attr;

public class OrganizationUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Organization org = (Organization)parentSAMLObject;
      if (childSAMLObject instanceof Extensions) {
         org.setExtensions((Extensions)childSAMLObject);
      } else if (childSAMLObject instanceof OrganizationName) {
         org.getOrganizationNames().add((OrganizationName)childSAMLObject);
      } else if (childSAMLObject instanceof OrganizationDisplayName) {
         org.getDisplayNames().add((OrganizationDisplayName)childSAMLObject);
      } else if (childSAMLObject instanceof OrganizationURL) {
         org.getURLs().add((OrganizationURL)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      if (attribute.getNamespaceURI() == null) {
         super.processAttribute(samlObject, attribute);
      } else {
         this.processUnknownAttribute((Organization)samlObject, attribute);
      }

   }
}

package org.opensaml.saml.ext.saml2mdattr.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2mdattr.EntityAttributes;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;

public class EntityAttributesUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      EntityAttributes entityAttrs = (EntityAttributes)parentObject;
      if (childObject instanceof Attribute) {
         entityAttrs.getAttributes().add((Attribute)childObject);
      } else if (childObject instanceof Assertion) {
         entityAttrs.getAssertions().add((Assertion)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}

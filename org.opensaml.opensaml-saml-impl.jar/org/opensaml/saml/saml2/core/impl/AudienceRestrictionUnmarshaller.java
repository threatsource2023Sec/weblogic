package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.AudienceRestriction;

public class AudienceRestrictionUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      AudienceRestriction audienceRestriction = (AudienceRestriction)parentObject;
      if (childObject instanceof Audience) {
         audienceRestriction.getAudiences().add((Audience)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}

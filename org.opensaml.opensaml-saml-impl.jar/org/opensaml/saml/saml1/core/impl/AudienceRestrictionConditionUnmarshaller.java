package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.Audience;
import org.opensaml.saml.saml1.core.AudienceRestrictionCondition;

public class AudienceRestrictionConditionUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      AudienceRestrictionCondition audienceRestrictionCondition = (AudienceRestrictionCondition)parentSAMLObject;
      if (childSAMLObject instanceof Audience) {
         audienceRestrictionCondition.getAudiences().add((Audience)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}

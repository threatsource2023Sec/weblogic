package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.EnvironmentMatchType;
import org.w3c.dom.Element;

public class EnvironmentMatchTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      EnvironmentMatchType matchType = (EnvironmentMatchType)xmlObject;
      if (!Strings.isNullOrEmpty(matchType.getMatchId())) {
         domElement.setAttributeNS((String)null, "MatchId", matchType.getMatchId());
      }

   }
}

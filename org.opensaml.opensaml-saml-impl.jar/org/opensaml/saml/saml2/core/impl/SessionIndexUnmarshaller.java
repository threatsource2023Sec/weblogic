package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.SessionIndex;

public class SessionIndexUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      SessionIndex si = (SessionIndex)samlObject;
      si.setSessionIndex(elementContent);
   }
}

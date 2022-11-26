package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2mdui.DiscoHints;

public class DiscoHintsUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      DiscoHints info = (DiscoHints)parentSAMLObject;
      info.getXMLObjects().add(childSAMLObject);
   }
}

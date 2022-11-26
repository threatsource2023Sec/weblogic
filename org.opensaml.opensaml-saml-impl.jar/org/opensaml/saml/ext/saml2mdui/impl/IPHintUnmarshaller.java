package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2mdui.IPHint;

public class IPHintUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      IPHint hint = (IPHint)samlObject;
      hint.setHint(elementContent);
   }
}

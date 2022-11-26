package org.opensaml.saml.ext.saml2mdquery.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2mdquery.ActionNamespace;

public class ActionNamespaceUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      ActionNamespace actionNamespace = (ActionNamespace)samlObject;
      actionNamespace.setValue(elementContent);
   }
}

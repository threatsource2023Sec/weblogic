package org.opensaml.saml.ext.saml2mdquery.impl;

import com.google.common.base.Strings;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.ext.saml2mdquery.ActionNamespace;
import org.w3c.dom.Element;

public class ActionNamespaceMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      ActionNamespace actionNamespace = (ActionNamespace)xmlObject;
      if (!Strings.isNullOrEmpty(actionNamespace.getValue())) {
         ElementSupport.appendTextContent(domElement, actionNamespace.getValue());
      }

   }
}

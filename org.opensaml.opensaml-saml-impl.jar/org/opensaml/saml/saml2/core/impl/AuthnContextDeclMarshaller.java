package org.opensaml.saml.saml2.core.impl;

import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.AuthnContextDecl;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class AuthnContextDeclMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AuthnContextDecl authnCtxDecl = (AuthnContextDecl)xmlObject;
      Iterator var5 = authnCtxDecl.getUnknownAttributes().entrySet().iterator();

      while(true) {
         Attr attribute;
         Map.Entry entry;
         do {
            if (!var5.hasNext()) {
               return;
            }

            entry = (Map.Entry)var5.next();
            attribute = AttributeSupport.constructAttribute(domElement.getOwnerDocument(), (QName)entry.getKey());
            attribute.setValue((String)entry.getValue());
            domElement.setAttributeNodeNS(attribute);
         } while(!XMLObjectProviderRegistrySupport.isIDAttribute((QName)entry.getKey()) && !authnCtxDecl.getUnknownAttributes().isIDAttribute((QName)entry.getKey()));

         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      }
   }

   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AuthnContextDecl authnCtxDecl = (AuthnContextDecl)xmlObject;
      if (authnCtxDecl.getTextContent() != null) {
         ElementSupport.appendTextContent(domElement, authnCtxDecl.getTextContent());
      }

   }
}

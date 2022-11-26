package org.opensaml.saml.ext.saml2cb.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryUnmarshaller;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;
import org.w3c.dom.Attr;

public class ChannelBindingsUnmarshaller extends XSBase64BinaryUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      ChannelBindings cb = (ChannelBindings)xmlObject;
      QName attrName = QNameSupport.getNodeQName(attribute);
      if (attribute.getLocalName().equals("Type") && attribute.getNamespaceURI() == null) {
         cb.setType(attribute.getValue());
      } else if (ChannelBindings.SOAP11_MUST_UNDERSTAND_ATTR_NAME.equals(attrName)) {
         cb.setSOAP11MustUnderstand(XSBooleanValue.valueOf(attribute.getValue()));
      } else if (ChannelBindings.SOAP11_ACTOR_ATTR_NAME.equals(attrName)) {
         cb.setSOAP11Actor(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}

package org.opensaml.saml.saml2.ecp.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.schema.impl.XSStringUnmarshaller;
import org.opensaml.saml.saml2.ecp.RelayState;
import org.w3c.dom.Attr;

public class RelayStateUnmarshaller extends XSStringUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      RelayState relayState = (RelayState)xmlObject;
      QName attrName = QNameSupport.getNodeQName(attribute);
      if (RelayState.SOAP11_MUST_UNDERSTAND_ATTR_NAME.equals(attrName)) {
         relayState.setSOAP11MustUnderstand(XSBooleanValue.valueOf(attribute.getValue()));
      } else if (RelayState.SOAP11_ACTOR_ATTR_NAME.equals(attrName)) {
         relayState.setSOAP11Actor(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}

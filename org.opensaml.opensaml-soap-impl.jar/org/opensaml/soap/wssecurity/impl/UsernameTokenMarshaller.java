package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.UsernameToken;
import org.w3c.dom.Element;

public class UsernameTokenMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      UsernameToken usernameToken = (UsernameToken)xmlObject;
      if (!Strings.isNullOrEmpty(usernameToken.getWSUId())) {
         XMLObjectSupport.marshallAttribute(UsernameToken.WSU_ID_ATTR_NAME, usernameToken.getWSUId(), domElement, true);
      }

      XMLObjectSupport.marshallAttributeMap(usernameToken.getUnknownAttributes(), domElement);
   }
}

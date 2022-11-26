package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.SecurityTokenReference;
import org.w3c.dom.Element;

public class SecurityTokenReferenceMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      SecurityTokenReference str = (SecurityTokenReference)xmlObject;
      if (!Strings.isNullOrEmpty(str.getWSUId())) {
         XMLObjectSupport.marshallAttribute(SecurityTokenReference.WSU_ID_ATTR_NAME, str.getWSUId(), domElement, true);
      }

      List usages = str.getWSSEUsages();
      if (usages != null && !usages.isEmpty()) {
         XMLObjectSupport.marshallAttribute(SecurityTokenReference.WSSE_USAGE_ATTR_NAME, usages, domElement, false);
      }

      XMLObjectSupport.marshallAttributeMap(str.getUnknownAttributes(), domElement);
   }
}

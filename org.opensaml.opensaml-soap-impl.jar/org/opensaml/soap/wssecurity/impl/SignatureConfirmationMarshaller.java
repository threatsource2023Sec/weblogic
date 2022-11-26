package org.opensaml.soap.wssecurity.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.SignatureConfirmation;
import org.w3c.dom.Element;

public class SignatureConfirmationMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      SignatureConfirmation sc = (SignatureConfirmation)xmlObject;
      if (!Strings.isNullOrEmpty(sc.getWSUId())) {
         XMLObjectSupport.marshallAttribute(SignatureConfirmation.WSU_ID_ATTR_NAME, sc.getWSUId(), domElement, true);
      }

      if (!Strings.isNullOrEmpty(sc.getValue())) {
         domElement.setAttributeNS((String)null, "Value", sc.getValue());
      }

   }
}

package org.opensaml.xmlsec.signature.impl;

import com.google.common.base.Strings;
import java.math.BigInteger;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.X509SerialNumber;
import org.w3c.dom.Attr;

public class X509SerialNumberUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      X509SerialNumber x509SerialNumber = (X509SerialNumber)xmlObject;
      if (!Strings.isNullOrEmpty(elementContent)) {
         x509SerialNumber.setValue(new BigInteger(elementContent));
      }

   }
}

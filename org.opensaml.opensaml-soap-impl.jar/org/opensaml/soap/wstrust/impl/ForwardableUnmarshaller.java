package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.soap.wstrust.Forwardable;

public class ForwardableUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      if (elementContent != null) {
         Forwardable forwardable = (Forwardable)xmlObject;
         XSBooleanValue value = XSBooleanValue.valueOf(elementContent);
         forwardable.setValue(value);
      }

   }
}

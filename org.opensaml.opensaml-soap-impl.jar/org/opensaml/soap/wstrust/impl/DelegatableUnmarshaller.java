package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.soap.wstrust.Delegatable;

public class DelegatableUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      if (elementContent != null) {
         Delegatable delegatable = (Delegatable)xmlObject;
         XSBooleanValue value = XSBooleanValue.valueOf(elementContent);
         delegatable.setValue(value);
      }

   }
}

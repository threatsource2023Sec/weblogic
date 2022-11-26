package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.soap.wssecurity.Salt;

public class SaltUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      Salt salt = (Salt)xmlObject;
      salt.setValue(elementContent);
   }
}

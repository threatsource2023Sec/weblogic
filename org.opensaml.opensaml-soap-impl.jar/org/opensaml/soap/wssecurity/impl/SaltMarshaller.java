package org.opensaml.soap.wssecurity.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.soap.wssecurity.Salt;
import org.w3c.dom.Element;

public class SaltMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Salt salt = (Salt)xmlObject;
      ElementSupport.appendTextContent(domElement, salt.getValue());
   }
}

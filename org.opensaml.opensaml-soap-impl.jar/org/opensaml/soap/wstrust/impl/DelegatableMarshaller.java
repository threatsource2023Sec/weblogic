package org.opensaml.soap.wstrust.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.soap.wstrust.Delegatable;
import org.w3c.dom.Element;

public class DelegatableMarshaller extends AbstractWSTrustObjectMarshaller {
   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Delegatable delegatable = (Delegatable)xmlObject;
      XSBooleanValue value = delegatable.getValue();
      ElementSupport.appendTextContent(domElement, value.toString());
   }
}

package org.opensaml.soap.wstrust.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.soap.wstrust.Forwardable;
import org.w3c.dom.Element;

public class ForwardableMarshaller extends AbstractWSTrustObjectMarshaller {
   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Forwardable forwardable = (Forwardable)xmlObject;
      XSBooleanValue value = forwardable.getValue();
      ElementSupport.appendTextContent(domElement, value.toString());
   }
}

package org.opensaml.soap.wssecurity.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.soap.wssecurity.Iteration;
import org.w3c.dom.Element;

public class IterationMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Iteration iteration = (Iteration)xmlObject;
      if (iteration.getValue() != null) {
         ElementSupport.appendTextContent(domElement, iteration.getValue().toString());
      }

   }
}

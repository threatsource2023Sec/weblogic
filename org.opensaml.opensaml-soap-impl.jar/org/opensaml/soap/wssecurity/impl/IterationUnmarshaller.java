package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.soap.wssecurity.Iteration;

public class IterationUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      Iteration iteration = (Iteration)xmlObject;
      if (elementContent != null) {
         iteration.setValue(Integer.valueOf(elementContent));
      }

   }
}

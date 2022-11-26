package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.Action;
import org.opensaml.soap.wsaddressing.ProblemAction;
import org.opensaml.soap.wsaddressing.SoapAction;
import org.w3c.dom.Attr;

public class ProblemActionUnmarshaller extends AbstractWSAddressingObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      ProblemAction pa = (ProblemAction)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(pa.getUnknownAttributes(), attribute);
   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ProblemAction pa = (ProblemAction)parentXMLObject;
      if (childXMLObject instanceof Action) {
         pa.setAction((Action)childXMLObject);
      } else if (childXMLObject instanceof SoapAction) {
         pa.setSoapAction((SoapAction)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}

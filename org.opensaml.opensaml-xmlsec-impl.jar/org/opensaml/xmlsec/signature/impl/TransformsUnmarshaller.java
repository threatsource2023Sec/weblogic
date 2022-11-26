package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.Transform;
import org.opensaml.xmlsec.signature.Transforms;

public class TransformsUnmarshaller extends AbstractXMLSignatureUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Transforms transforms = (Transforms)parentXMLObject;
      if (childXMLObject instanceof Transform) {
         transforms.getTransforms().add((Transform)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}

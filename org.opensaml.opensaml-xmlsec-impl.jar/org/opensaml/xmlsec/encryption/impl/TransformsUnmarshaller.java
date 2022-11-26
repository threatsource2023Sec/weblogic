package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.Transforms;
import org.opensaml.xmlsec.signature.Transform;

public class TransformsUnmarshaller extends AbstractXMLEncryptionUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Transforms transforms = (Transforms)parentXMLObject;
      if (childXMLObject instanceof Transform) {
         transforms.getTransforms().add((Transform)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}

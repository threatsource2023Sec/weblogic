package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.DSAKeyValue;
import org.opensaml.xmlsec.signature.G;
import org.opensaml.xmlsec.signature.J;
import org.opensaml.xmlsec.signature.P;
import org.opensaml.xmlsec.signature.PgenCounter;
import org.opensaml.xmlsec.signature.Q;
import org.opensaml.xmlsec.signature.Seed;
import org.opensaml.xmlsec.signature.Y;

public class DSAKeyValueUnmarshaller extends AbstractXMLSignatureUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      DSAKeyValue keyValue = (DSAKeyValue)parentXMLObject;
      if (childXMLObject instanceof P) {
         keyValue.setP((P)childXMLObject);
      } else if (childXMLObject instanceof Q) {
         keyValue.setQ((Q)childXMLObject);
      } else if (childXMLObject instanceof G) {
         keyValue.setG((G)childXMLObject);
      } else if (childXMLObject instanceof Y) {
         keyValue.setY((Y)childXMLObject);
      } else if (childXMLObject instanceof J) {
         keyValue.setJ((J)childXMLObject);
      } else if (childXMLObject instanceof Seed) {
         keyValue.setSeed((Seed)childXMLObject);
      } else if (childXMLObject instanceof PgenCounter) {
         keyValue.setPgenCounter((PgenCounter)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}

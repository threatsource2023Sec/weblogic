package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.DHKeyValue;
import org.opensaml.xmlsec.encryption.Generator;
import org.opensaml.xmlsec.encryption.P;
import org.opensaml.xmlsec.encryption.PgenCounter;
import org.opensaml.xmlsec.encryption.Public;
import org.opensaml.xmlsec.encryption.Q;
import org.opensaml.xmlsec.encryption.Seed;

public class DHKeyValueUnmarshaller extends AbstractXMLEncryptionUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      DHKeyValue keyValue = (DHKeyValue)parentXMLObject;
      if (childXMLObject instanceof P) {
         keyValue.setP((P)childXMLObject);
      } else if (childXMLObject instanceof Q) {
         keyValue.setQ((Q)childXMLObject);
      } else if (childXMLObject instanceof Generator) {
         keyValue.setGenerator((Generator)childXMLObject);
      } else if (childXMLObject instanceof Public) {
         keyValue.setPublic((Public)childXMLObject);
      } else if (childXMLObject instanceof Seed) {
         keyValue.setSeed((Seed)childXMLObject);
      } else if (childXMLObject instanceof PgenCounter) {
         keyValue.setPgenCounter((PgenCounter)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}

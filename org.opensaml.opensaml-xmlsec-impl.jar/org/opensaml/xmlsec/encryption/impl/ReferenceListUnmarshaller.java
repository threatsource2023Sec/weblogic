package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.DataReference;
import org.opensaml.xmlsec.encryption.KeyReference;
import org.opensaml.xmlsec.encryption.ReferenceList;

public class ReferenceListUnmarshaller extends AbstractXMLEncryptionUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ReferenceList rl = (ReferenceList)parentXMLObject;
      if (childXMLObject instanceof DataReference) {
         rl.getReferences().add((DataReference)childXMLObject);
      } else if (childXMLObject instanceof KeyReference) {
         rl.getReferences().add((KeyReference)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}

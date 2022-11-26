package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wssecurity.Created;
import org.opensaml.soap.wssecurity.Expires;
import org.opensaml.soap.wstrust.Lifetime;

public class LifetimeUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Lifetime lifetime = (Lifetime)parentXMLObject;
      if (childXMLObject instanceof Created) {
         lifetime.setCreated((Created)childXMLObject);
      } else if (childXMLObject instanceof Expires) {
         lifetime.setExpires((Expires)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}

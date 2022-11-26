package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Timestamp;

public class TimestampBuilder extends AbstractWSSecurityObjectBuilder {
   public Timestamp buildObject() {
      return (Timestamp)this.buildObject(Timestamp.ELEMENT_NAME);
   }

   public Timestamp buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new TimestampImpl(namespaceURI, localName, namespacePrefix);
   }
}

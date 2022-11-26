package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.soap.wstrust.Challenge;

public class ChallengeImpl extends XSStringImpl implements Challenge {
   public ChallengeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}

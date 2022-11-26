package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.ProblemHeaderQName;

public class ProblemHeaderQNameImpl extends AttributedQNameImpl implements ProblemHeaderQName {
   public ProblemHeaderQNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}

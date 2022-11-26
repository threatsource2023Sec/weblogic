package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.Action;

public class ActionImpl extends AttributedURIImpl implements Action {
   public ActionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}

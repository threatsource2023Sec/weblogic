package org.python.apache.xerces.dom;

import org.w3c.dom.DOMImplementation;

public class DeferredDOMImplementationImpl extends DOMImplementationImpl {
   static final DeferredDOMImplementationImpl singleton = new DeferredDOMImplementationImpl();

   public static DOMImplementation getDOMImplementation() {
      return singleton;
   }
}

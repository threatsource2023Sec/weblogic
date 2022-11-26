package org.apache.xmlbeans.impl.soap;

public interface Node extends org.w3c.dom.Node {
   String getValue();

   void setParentElement(SOAPElement var1) throws SOAPException;

   SOAPElement getParentElement();

   void detachNode();

   void recycleNode();

   void setValue(String var1);
}

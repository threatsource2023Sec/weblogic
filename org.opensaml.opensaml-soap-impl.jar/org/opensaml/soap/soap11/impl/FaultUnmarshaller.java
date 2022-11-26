package org.opensaml.soap.soap11.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSQName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.soap.soap11.Detail;
import org.opensaml.soap.soap11.Fault;
import org.opensaml.soap.soap11.FaultActor;
import org.opensaml.soap.soap11.FaultCode;
import org.opensaml.soap.soap11.FaultString;
import org.w3c.dom.Attr;

public class FaultUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Fault fault = (Fault)parentXMLObject;
      if (childXMLObject instanceof XSQName) {
         fault.setCode((FaultCode)childXMLObject);
      } else if (childXMLObject instanceof XSString) {
         fault.setMessage((FaultString)childXMLObject);
      } else if (childXMLObject instanceof XSURI) {
         fault.setActor((FaultActor)childXMLObject);
      } else if (childXMLObject instanceof Detail) {
         fault.setDetail((Detail)childXMLObject);
      }

   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
   }
}

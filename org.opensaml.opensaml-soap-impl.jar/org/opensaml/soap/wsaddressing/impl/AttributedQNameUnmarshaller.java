package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.impl.XSQNameUnmarshaller;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wsaddressing.AttributedQName;
import org.w3c.dom.Attr;

public class AttributedQNameUnmarshaller extends XSQNameUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AttributedQName attributedQName = (AttributedQName)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(attributedQName.getUnknownAttributes(), attribute);
   }
}

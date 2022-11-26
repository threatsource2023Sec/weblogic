package org.opensaml.core.xml.schema.impl;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.XSBase64Binary;
import org.w3c.dom.Element;

public class XSBase64BinaryMarshaller extends AbstractXMLObjectMarshaller {
   protected void marshallAttributes(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
   }

   protected void marshallElementContent(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
      XSBase64Binary xsBase64Binary = (XSBase64Binary)xmlObject;
      ElementSupport.appendTextContent(domElement, xsBase64Binary.getValue());
   }
}

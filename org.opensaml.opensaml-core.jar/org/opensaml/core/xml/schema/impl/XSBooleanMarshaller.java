package org.opensaml.core.xml.schema.impl;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.XSBoolean;
import org.w3c.dom.Element;

public class XSBooleanMarshaller extends AbstractXMLObjectMarshaller {
   protected void marshallAttributes(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
   }

   protected void marshallElementContent(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
      XSBoolean xsiBoolean = (XSBoolean)xmlObject;
      if (xsiBoolean.getValue() != null && xsiBoolean.getValue().getValue() != null) {
         ElementSupport.appendTextContent(domElement, xsiBoolean.getValue().getValue().toString());
      }

   }
}

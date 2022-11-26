package org.opensaml.core.xml.schema.impl;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBoolean;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.w3c.dom.Attr;

public class XSBooleanUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(@Nonnull XMLObject parentXMLObject, @Nonnull XMLObject childXMLObject) throws UnmarshallingException {
   }

   protected void processAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) throws UnmarshallingException {
   }

   protected void processElementContent(@Nonnull XMLObject xmlObject, @Nonnull String elementContent) {
      XSBoolean xsiBoolean = (XSBoolean)xmlObject;
      xsiBoolean.setValue(XSBooleanValue.valueOf(elementContent));
   }
}

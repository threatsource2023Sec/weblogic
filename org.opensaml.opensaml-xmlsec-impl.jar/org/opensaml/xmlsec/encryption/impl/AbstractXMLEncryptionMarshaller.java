package org.opensaml.xmlsec.encryption.impl;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.w3c.dom.Element;

public abstract class AbstractXMLEncryptionMarshaller extends AbstractXMLObjectMarshaller {
   protected void marshallAttributes(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
   }

   protected void marshallElementContent(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
   }
}

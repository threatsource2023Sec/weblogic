package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2mdrpi.Publication;
import org.w3c.dom.Attr;

public class PublicationUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      Publication info = (Publication)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if ("publisher".equals(attribute.getName())) {
            info.setPublisher(attribute.getValue());
         } else if ("creationInstant".equals(attribute.getName())) {
            info.setCreationInstant(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else if ("publicationId".equals(attribute.getName())) {
            info.setPublicationId(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}

package org.opensaml.saml.ext.saml2mdrpi;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;

public interface Publication extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Publication";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "Publication", "mdrpi");
   String TYPE_LOCAL_NAME = "PublicationType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "PublicationType", "mdrpi");
   String PUBLISHER_ATTRIB_NAME = "publisher";
   String CREATION_INSTANT_ATTRIB_NAME = "creationInstant";
   String PUBLICATION_ID_ATTRIB_NAME = "publicationId";

   String getPublisher();

   void setPublisher(String var1);

   DateTime getCreationInstant();

   void setCreationInstant(DateTime var1);

   String getPublicationId();

   void setPublicationId(String var1);
}

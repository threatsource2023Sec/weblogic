package org.opensaml.saml.ext.saml2mdrpi;

import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;

public interface PublicationInfo extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "PublicationInfo";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "PublicationInfo", "mdrpi");
   String TYPE_LOCAL_NAME = "PublicationInfoType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "PublicationInfoType", "mdrpi");
   String PUBLISHER_ATTRIB_NAME = "publisher";
   String CREATION_INSTANT_ATTRIB_NAME = "creationInstant";
   String PUBLICATION_ID_ATTRIB_NAME = "publicationId";

   String getPublisher();

   void setPublisher(String var1);

   DateTime getCreationInstant();

   void setCreationInstant(DateTime var1);

   String getPublicationId();

   void setPublicationId(String var1);

   List getUsagePolicies();
}

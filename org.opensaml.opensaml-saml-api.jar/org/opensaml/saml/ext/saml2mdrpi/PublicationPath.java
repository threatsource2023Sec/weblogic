package org.opensaml.saml.ext.saml2mdrpi;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface PublicationPath extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "PublicationPath";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "PublicationPath", "mdrpi");
   String TYPE_LOCAL_NAME = "PublicationPathType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "PublicationPathType", "mdrpi");

   List getPublications();
}

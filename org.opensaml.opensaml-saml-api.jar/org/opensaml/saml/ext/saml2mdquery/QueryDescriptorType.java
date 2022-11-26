package org.opensaml.saml.ext.saml2mdquery;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public interface QueryDescriptorType extends RoleDescriptor {
   String TYPE_LOCAL_NAME = "QueryDescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ext:query", "QueryDescriptorType", "query");
   String WANT_ASSERTIONS_SIGNED_ATTRIB_NAME = "WantAssertionsSigned";

   Boolean getWantAssertionsSigned();

   XSBooleanValue getWantAssertionsSignedXSBoolean();

   void setWantAssertionsSigned(Boolean var1);

   void setWantAssertionsSigned(XSBooleanValue var1);

   List getNameIDFormat();
}

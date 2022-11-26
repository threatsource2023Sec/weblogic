package org.opensaml.saml.saml2.ecp;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.IDPList;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface Request extends SAMLObject, MustUnderstandBearing, ActorBearing {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Request";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "Request", "ecp");
   String TYPE_LOCAL_NAME = "RequestType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RequestType", "ecp");
   String PROVIDER_NAME_ATTRIB_NAME = "ProviderName";
   String IS_PASSIVE_NAME_ATTRIB_NAME = "IsPassive";

   Issuer getIssuer();

   void setIssuer(Issuer var1);

   IDPList getIDPList();

   void setIDPList(IDPList var1);

   String getProviderName();

   void setProviderName(String var1);

   Boolean isPassive();

   XSBooleanValue isPassiveXSBoolean();

   void setPassive(Boolean var1);

   void setPassive(XSBooleanValue var1);
}

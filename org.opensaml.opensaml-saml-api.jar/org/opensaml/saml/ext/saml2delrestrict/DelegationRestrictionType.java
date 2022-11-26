package org.opensaml.saml.ext.saml2delrestrict;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.Condition;

public interface DelegationRestrictionType extends Condition {
   String TYPE_LOCAL_NAME = "DelegationRestrictionType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:conditions:delegation", "DelegationRestrictionType", "del");

   List getDelegates();
}

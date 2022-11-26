package org.opensaml.saml.saml2.common;

import com.google.common.base.Predicate;
import org.opensaml.core.xml.XMLObject;

public class IsTimeboundSAMLObjectValidPredicate implements Predicate {
   public boolean apply(XMLObject input) {
      return input == null ? false : SAML2Support.isValid(input);
   }
}

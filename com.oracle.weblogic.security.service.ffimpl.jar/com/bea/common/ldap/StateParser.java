package com.bea.common.ldap;

import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPModificationSet;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public interface StateParser {
   DistinguishedNameId parseState(OpenJPAStateManager var1);

   LDAPAttributeSet create(OpenJPAStateManager var1);

   LDAPModificationSet deltas(OpenJPAStateManager var1);
}

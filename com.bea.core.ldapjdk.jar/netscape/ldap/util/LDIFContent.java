package netscape.ldap.util;

import netscape.ldap.LDAPControl;

public interface LDIFContent {
   int ATTRIBUTE_CONTENT = 0;
   int ADD_CONTENT = 1;
   int DELETE_CONTENT = 2;
   int MODIFICATION_CONTENT = 3;
   int MODDN_CONTENT = 4;

   int getType();

   LDAPControl[] getControls();

   void setControls(LDAPControl[] var1);

   String toString();
}

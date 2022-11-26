package com.bea.core.jatmi.intf;

public interface TCAuthenticatedUser {
   void setAsCurrentUser();

   Object[] getPrincipals();
}

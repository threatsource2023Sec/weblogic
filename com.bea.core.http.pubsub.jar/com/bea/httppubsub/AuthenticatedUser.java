package com.bea.httppubsub;

import java.security.Principal;
import javax.servlet.http.HttpSession;

public interface AuthenticatedUser {
   String getUserName();

   Principal getUserPrincipal();

   boolean isUserInRole(String var1);

   HttpSession getSession();
}

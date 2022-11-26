package com.bea.httppubsub;

import java.security.Principal;
import java.util.Set;
import javax.servlet.http.HttpSession;

public abstract class LocalClient implements Client {
   public AuthenticatedUser getAuthenticatedUser() {
      return new AuthenticatedUser() {
         public String getUserName() {
            return "LocalClient";
         }

         public Principal getUserPrincipal() {
            return new Principal() {
               public boolean equals(Object another) {
                  if (another == this) {
                     return true;
                  } else {
                     return !(another instanceof Principal) ? false : "LocalClient".equals(((Principal)another).getName());
                  }
               }

               public String toString() {
                  return "LocalClient";
               }

               public int hashCode() {
                  return "LocalClient".hashCode();
               }

               public String getName() {
                  return "LocalClient";
               }
            };
         }

         public boolean isUserInRole(String role) {
            return true;
         }

         public HttpSession getSession() {
            return null;
         }
      };
   }

   public void setAuthenticatedUser(AuthenticatedUser user) {
   }

   public Set getChannelSubscriptions() {
      return null;
   }

   public String getBrowserId() {
      return null;
   }

   public abstract void registerMessageListener(DeliveredMessageListener var1);

   public abstract void unregisterMessageListener(DeliveredMessageListener var1);

   public abstract void setCommentFilterRequired(boolean var1);
}

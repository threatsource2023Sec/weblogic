package weblogic.security.providers.authentication;

import java.util.List;
import java.util.Set;

public interface IDCSTokenAssert {
   void parse() throws IllegalArgumentException;

   void validate(String var1, String var2) throws IllegalArgumentException;

   String validatePeekTenant(String var1) throws IllegalArgumentException;

   String getUserName() throws IllegalArgumentException;

   String getUserID() throws IllegalArgumentException;

   String getTenant() throws IllegalArgumentException;

   Set getGroups() throws IllegalArgumentException;

   Set getAppRoles() throws IllegalArgumentException;

   boolean isClientOnly() throws IllegalArgumentException;

   String getClientName() throws IllegalArgumentException;

   String getClientID() throws IllegalArgumentException;

   String getClientTenant() throws IllegalArgumentException;

   String getResourceTenant() throws IllegalArgumentException;

   String getScope() throws IllegalArgumentException;

   String getTokenType() throws IllegalArgumentException;

   List getAudience() throws IllegalArgumentException;

   public static enum ValidationLevel {
      FULL,
      NORMAL,
      SIGNATURE,
      NONE;
   }
}

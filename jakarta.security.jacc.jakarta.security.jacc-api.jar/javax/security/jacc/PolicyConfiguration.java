package javax.security.jacc;

import java.security.Permission;
import java.security.PermissionCollection;

public interface PolicyConfiguration {
   String getContextID() throws PolicyContextException;

   void addToRole(String var1, PermissionCollection var2) throws PolicyContextException;

   void addToRole(String var1, Permission var2) throws PolicyContextException;

   void addToUncheckedPolicy(PermissionCollection var1) throws PolicyContextException;

   void addToUncheckedPolicy(Permission var1) throws PolicyContextException;

   void addToExcludedPolicy(PermissionCollection var1) throws PolicyContextException;

   void addToExcludedPolicy(Permission var1) throws PolicyContextException;

   void removeRole(String var1) throws PolicyContextException;

   void removeUncheckedPolicy() throws PolicyContextException;

   void removeExcludedPolicy() throws PolicyContextException;

   void linkConfiguration(PolicyConfiguration var1) throws PolicyContextException;

   void delete() throws PolicyContextException;

   void commit() throws PolicyContextException;

   boolean inService() throws PolicyContextException;
}

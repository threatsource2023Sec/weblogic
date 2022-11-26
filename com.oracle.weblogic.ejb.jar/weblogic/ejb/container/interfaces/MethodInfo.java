package weblogic.ejb.container.interfaces;

import java.lang.reflect.Method;
import java.util.Set;

public interface MethodInfo {
   Method getMethod();

   String getSignature();

   String getMethodName();

   String[] getMethodParams();

   void setMethodDescriptorMethodType(short var1);

   short getMethodDescriptorMethodType();

   String getMethodInterfaceType();

   boolean hasRole(String var1);

   boolean hasRoles();

   void setTxIsolationLevel(int var1);

   void addSecurityRoleRestriction(String var1);

   void setTransactionAttribute(short var1);

   short getTransactionAttribute();

   int getTxIsolationLevel();

   void setRetryOnRollbackCount(int var1);

   int getRetryOnRollbackCount();

   int getSelectForUpdate();

   void setSelectForUpdate(int var1);

   Set getSecurityRoleNames();

   void setIsExcluded(boolean var1);

   boolean getIsExcluded();

   void setUnchecked(boolean var1);

   boolean getUnchecked();

   void setIdempotent(boolean var1);

   boolean isIdempotent();

   void setSkipStateReplication(boolean var1);

   boolean getSkipStateReplication();
}

package weblogic.jdbc.common.internal;

import java.util.Collection;

public interface SwitchingContext extends Cloneable {
   JDBCConnectionPool getPool();

   void setPool(JDBCConnectionPool var1);

   String getPDBName();

   void setPDBName(String var1);

   String getPDBServiceName();

   void setPDBServiceName(String var1);

   String getProxyUser();

   void setProxyUser(String var1);

   char[] getProxyPassword();

   void setProxyPassword(char[] var1);

   Role addRole(String var1, char[] var2);

   Role removeRole(String var1);

   boolean hasRoles();

   Collection getRoles();

   boolean setRoles(Collection var1);

   void clearRoles();

   Object clone() throws CloneNotSupportedException;

   boolean requiresPDBServiceSwitch(SwitchingContext var1);

   public interface Role {
      String getName();

      void setName(String var1);

      char[] getPassword();

      void setPassword(char[] var1);
   }
}

package weblogic.jdbc.common.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;

public class SwitchingContextImpl implements SwitchingContext {
   JDBCConnectionPool pool;
   String pdbName;
   String pdbServiceName;
   Map roles = new HashMap();
   String proxyUser;
   char[] proxyp;

   SwitchingContextImpl() {
   }

   SwitchingContextImpl(JDBCConnectionPool pool, String containerName, String serviceName) {
      this.pool = pool;
      this.pdbName = containerName;
      this.pdbServiceName = serviceName;
   }

   SwitchingContextImpl(JDBCConnectionPool pool, JDBCDataSourceBean dsBean) {
      this.pool = pool;
      this.pdbName = JDBCUtil.getPDBName(dsBean);
      this.pdbServiceName = JDBCUtil.getPDBServiceName(dsBean);
      this.initSecurity(dsBean);
   }

   SwitchingContextImpl(JDBCConnectionPool pool, String pdbName, String serviceName, JDBCDataSourceBean dsBean) {
      this.pool = pool;
      this.pdbName = pdbName;
      this.pdbServiceName = serviceName;
      this.initSecurity(dsBean);
   }

   private void initSecurity(JDBCDataSourceBean dsBean) {
      List roleNames = JDBCUtil.getRoleNames(dsBean);
      Iterator var3 = roleNames.iterator();

      while(var3.hasNext()) {
         String roleName = (String)var3.next();
         if (roleName != null && roleName.length() > 0) {
            String rp = JDBCUtil.getRolePassword(dsBean, roleName);
            this.addRole(roleName, rp != null ? rp.toCharArray() : null);
         }
      }

      this.proxyUser = JDBCUtil.getProxyUser(dsBean);
      String pp = JDBCUtil.getProxyPassword(dsBean, this.proxyUser);
      if (pp != null) {
         this.proxyp = pp.toCharArray();
      }

   }

   public Object clone() throws CloneNotSupportedException {
      SwitchingContextImpl copy = new SwitchingContextImpl();
      copy.setPool(this.pool);
      copy.setPDBName(this.pdbName);
      copy.setPDBServiceName(this.pdbServiceName);
      copy.setRoles(this.roles.values());
      copy.setProxyUser(this.proxyUser);
      copy.setProxyPassword(this.proxyp);
      return copy;
   }

   public JDBCConnectionPool getPool() {
      return this.pool;
   }

   public void setPool(JDBCConnectionPool pool) {
      this.pool = pool;
   }

   public String getPDBName() {
      return this.pdbName;
   }

   public void setPDBName(String name) {
      this.pdbName = name;
   }

   public String getPDBServiceName() {
      return this.pdbServiceName;
   }

   public void setPDBServiceName(String name) {
      this.pdbServiceName = name;
   }

   public String getProxyUser() {
      return this.proxyUser;
   }

   public void setProxyUser(String user) {
      this.proxyUser = user;
   }

   public char[] getProxyPassword() {
      return this.proxyp;
   }

   public void setProxyPassword(char[] p) {
      this.proxyp = p;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("{");
      sb.append("pdbName=" + this.pdbName);
      sb.append(",");
      sb.append("pdbServiceName=" + this.pdbServiceName);
      sb.append("}");
      return sb.toString();
   }

   public int hashCode() {
      int hashcode = 0;
      if (this.pdbName != null) {
         hashcode += this.pdbName.hashCode();
      }

      if (this.pdbServiceName != null) {
         hashcode += this.pdbServiceName.hashCode();
      }

      if (this.proxyUser != null) {
         hashcode += this.proxyUser.hashCode();
      }

      if (!this.roles.isEmpty()) {
         hashcode += this.roles.hashCode();
      }

      return hashcode;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof SwitchingContextImpl)) {
         return false;
      } else {
         SwitchingContextImpl that = (SwitchingContextImpl)obj;
         if (!JDBCUtil.isEqualIgnoringCase(this.pdbName, that.pdbName)) {
            return false;
         } else if (!JDBCUtil.isEqualIgnoringCase(this.pdbServiceName, that.pdbServiceName)) {
            return false;
         } else {
            return !JDBCUtil.isEqualIgnoringCase(this.proxyUser, that.proxyUser) ? false : this.roles.equals(that.roles);
         }
      }
   }

   public SwitchingContext.Role addRole(String name, char[] p) {
      SwitchingContext.Role role = new RoleImpl(name, p);
      this.roles.put(name, role);
      return role;
   }

   public SwitchingContext.Role removeRole(String name) {
      return (SwitchingContext.Role)this.roles.remove(name);
   }

   public Collection getRoles() {
      return this.roles.values();
   }

   public boolean hasRoles() {
      return this.roles.size() > 0;
   }

   public void clearRoles() {
      this.roles.clear();
   }

   public boolean setRoles(Collection newRoles) {
      this.roles.clear();
      Iterator var2 = newRoles.iterator();

      while(var2.hasNext()) {
         SwitchingContext.Role role = (SwitchingContext.Role)var2.next();
         this.roles.put(role.getName(), role);
      }

      return true;
   }

   public boolean requiresPDBServiceSwitch(SwitchingContext sc) {
      if (sc == null) {
         return true;
      } else {
         SwitchingContextImpl existing = (SwitchingContextImpl)sc;
         if (this.pdbName != null && !this.pdbName.equals(existing.pdbName)) {
            return true;
         } else {
            return this.pdbServiceName != null && !this.pdbServiceName.equals(existing.pdbServiceName);
         }
      }
   }

   public class RoleImpl implements SwitchingContext.Role {
      String name;
      char[] p;

      public RoleImpl(String name, char[] p) {
         this.name = name;
         this.p = p;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public char[] getPassword() {
         return this.p;
      }

      public void setPassword(char[] p) {
         this.p = p;
      }

      public String toString() {
         return "{" + this.name + "}";
      }

      public int hashCode() {
         return this.name == null ? super.hashCode() : this.name.hashCode();
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof RoleImpl)) {
            return false;
         } else {
            RoleImpl other = (RoleImpl)obj;
            if (this.name == null && other.name == null) {
               return true;
            } else {
               return this.name != null ? this.name.equals(other.name) : false;
            }
         }
      }
   }
}

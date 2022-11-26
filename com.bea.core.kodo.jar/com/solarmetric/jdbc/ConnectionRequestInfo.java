package com.solarmetric.jdbc;

import org.apache.commons.lang.StringUtils;

public class ConnectionRequestInfo {
   private String user = null;
   private String pass = null;

   public ConnectionRequestInfo() {
   }

   public ConnectionRequestInfo(String user, String pass) {
      this.setUsername(user);
      this.setPassword(pass);
   }

   public int hashCode() {
      return ((this.getUsername() == null ? 0 : this.getUsername().hashCode()) + (this.getPassword() == null ? 0 : this.getPassword().hashCode())) % Integer.MAX_VALUE;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ConnectionRequestInfo)) {
         return false;
      } else {
         ConnectionRequestInfo cri = (ConnectionRequestInfo)other;
         return StringUtils.equals(this.getUsername(), cri.getUsername()) && StringUtils.equals(this.getPassword(), cri.getPassword());
      }
   }

   public void setUsername(String user) {
      this.user = user;
   }

   public String getUsername() {
      return this.user;
   }

   public void setPassword(String pass) {
      this.pass = pass;
   }

   public String getPassword() {
      return this.pass;
   }
}

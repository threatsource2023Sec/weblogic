package kodo.ee;

import javax.resource.cci.ConnectionSpec;
import javax.resource.spi.ConnectionRequestInfo;
import org.apache.commons.lang.StringUtils;

public class KodoConnectionRequestInfo implements ConnectionRequestInfo {
   private String username;
   private String password;

   public KodoConnectionRequestInfo(ConnectionSpec connectionSpec) {
   }

   public void setUserName(String username) {
      this.username = username;
   }

   public String getUserName() {
      return this.username;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getPassword() {
      return this.password;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof KodoConnectionRequestInfo)) {
         return false;
      } else {
         KodoConnectionRequestInfo jcri = (KodoConnectionRequestInfo)other;
         return StringUtils.equals(jcri.password, this.password) && StringUtils.equals(jcri.username, this.username);
      }
   }

   public int hashCode() {
      return ((this.username == null ? 0 : this.username.hashCode()) + (this.password == null ? 0 : this.password.hashCode())) % Integer.MAX_VALUE;
   }
}

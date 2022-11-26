package javax.resource.spi.security;

import java.io.Serializable;
import javax.resource.spi.ManagedConnectionFactory;

public final class PasswordCredential implements Serializable {
   private String userName;
   private char[] password;
   private ManagedConnectionFactory mcf;

   public PasswordCredential(String userName, char[] password) {
      this.userName = userName;
      this.password = (char[])((char[])password.clone());
   }

   public String getUserName() {
      return this.userName;
   }

   public char[] getPassword() {
      return this.password;
   }

   public ManagedConnectionFactory getManagedConnectionFactory() {
      return this.mcf;
   }

   public void setManagedConnectionFactory(ManagedConnectionFactory mcf) {
      this.mcf = mcf;
   }

   public boolean equals(Object other) {
      if (!(other instanceof PasswordCredential)) {
         return false;
      } else {
         PasswordCredential pc = (PasswordCredential)other;
         if (!this.userName.equals(pc.userName)) {
            return false;
         } else if (this.password.length != pc.password.length) {
            return false;
         } else {
            for(int i = 0; i < this.password.length; ++i) {
               if (this.password[i] != pc.password[i]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int hashCode() {
      String s = this.userName;
      int passwordHash = 0;
      char[] var3 = this.password;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char passChar = var3[var5];
         passwordHash += passChar;
      }

      return s.hashCode() + passwordHash;
   }
}

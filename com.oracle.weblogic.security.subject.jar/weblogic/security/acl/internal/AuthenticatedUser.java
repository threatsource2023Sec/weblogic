package weblogic.security.acl.internal;

import java.net.InetAddress;
import weblogic.security.HMAC;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.UserInfo;

public class AuthenticatedUser implements UserInfo {
   private static final long serialVersionUID = 6699361079932480379L;
   public static final String REALM_NAME = "wl_realm";
   private long timeStamp;
   private String name;
   private byte[] signature;
   private byte qos = 101;
   private InetAddress inetAddress = null;
   private InetAddress localAddress = null;
   private int localPort = -1;
   private transient Object sslCertificate = null;

   public AuthenticatedUser() {
   }

   public AuthenticatedUser(String n, String secret) {
      this.timeStamp = System.currentTimeMillis();
      this.name = n;
      if (secret != null) {
         this.signature = HMAC.digest(this.name.getBytes(), secret.getBytes(), String.valueOf(this.timeStamp).getBytes());
      }

   }

   public AuthenticatedUser(String n, String secret, long tmStamp) {
      this.timeStamp = tmStamp;
      this.name = n;
      if (secret != null) {
         this.signature = HMAC.digest(this.name.getBytes(), secret.getBytes(), String.valueOf(this.timeStamp).getBytes());
      }

   }

   public AuthenticatedUser(String name, byte[] signature, byte[] salt) {
      this.name = name;
      this.signature = signature;
      this.timeStamp = Long.parseLong(new String(salt));
   }

   public AuthenticatedUser(AuthenticatedUser au) {
      this.timeStamp = au.timeStamp;
      this.name = au.name;
      this.signature = au.signature;
   }

   public String getName() {
      return this.name;
   }

   public String getRealmName() {
      return "wl_realm";
   }

   public final boolean verify(String secret) {
      Class ourClass = this.getClass();
      if (ourClass.equals(AuthenticatedSubject.class)) {
         return true;
      } else {
         return ourClass.equals(AuthenticatedUser.class) && secret != null && HMAC.verify(this.signature, this.name.getBytes(), secret.getBytes(), String.valueOf(this.timeStamp).getBytes());
      }
   }

   public String toString() {
      return this.name;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o == null) {
         return false;
      } else if (!(o instanceof AuthenticatedUser)) {
         return false;
      } else {
         AuthenticatedUser other = (AuthenticatedUser)o;
         if (this.signature == null) {
            if (other.signature != null) {
               return false;
            }
         } else {
            if (other.signature == null) {
               return false;
            }

            if (this.signature.length != other.signature.length) {
               return false;
            }

            for(int x = 0; x < this.signature.length; ++x) {
               if (this.signature[x] != other.signature[x]) {
                  return false;
               }
            }
         }

         boolean var10000;
         label96: {
            if (this.name.equals(other.name) && this.qos == other.qos) {
               label90: {
                  if (this.inetAddress == null) {
                     if (other.inetAddress != null) {
                        break label90;
                     }
                  } else if (!this.inetAddress.equals(other.inetAddress)) {
                     break label90;
                  }

                  if (this.localAddress == null) {
                     if (other.localAddress != null) {
                        break label90;
                     }
                  } else if (!this.localAddress.equals(other.localAddress)) {
                     break label90;
                  }

                  if (this.localPort == other.localPort) {
                     if (this.sslCertificate == null) {
                        if (other.sslCertificate == null) {
                           break label96;
                        }
                     } else if (this.sslCertificate.equals(other.sslCertificate)) {
                        break label96;
                     }
                  }
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      try {
         return this.name.hashCode() ^ this.qos ^ this.localPort;
      } catch (NullPointerException var2) {
         throw new RuntimeException(SecurityLogger.getNPEInAUHashCode("" + this.getClass()));
      }
   }

   public long getTimeStamp() {
      return this.timeStamp;
   }

   public byte[] getSignature() {
      return this.signature;
   }

   public byte[] getSalt() {
      return String.valueOf(this.timeStamp).getBytes();
   }

   public byte getQOS() {
      return this.qos;
   }

   public void setQOS(byte qos) {
      this.qos = qos;
   }

   protected void setName(String name) {
      this.name = name;
   }

   protected void setSignature(byte[] signature) {
      this.signature = signature;
   }
}

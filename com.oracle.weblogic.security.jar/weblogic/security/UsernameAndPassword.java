package weblogic.security;

import java.io.Serializable;
import java.util.Arrays;
import weblogic.security.internal.encryption.ClearOrEncryptedService;

public class UsernameAndPassword implements Serializable {
   private String username = null;
   private byte[] password = null;
   private transient ClearOrEncryptedService es = null;

   public UsernameAndPassword() {
   }

   public UsernameAndPassword(String username, char[] password) {
      this.setUsername(username);
      this.setPassword(password);
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public char[] getPassword() {
      if (this.password == null) {
         return null;
      } else {
         byte[] passwordBytes = this.es == null ? this.password : this.es.decryptBytes(this.password);
         char[] passwordChars = getUTF16Chars(passwordBytes);
         if (passwordBytes != this.password) {
            Arrays.fill(passwordBytes, (byte)0);
         }

         return passwordChars;
      }
   }

   public void setPassword(char[] pwd) {
      byte[] passwordBytes = null;
      if (pwd != null) {
         passwordBytes = getUTF16Bytes(pwd);
         if (this.es != null) {
            byte[] tmp = passwordBytes;
            passwordBytes = this.es.encryptBytes(passwordBytes);
            if (tmp != passwordBytes) {
               Arrays.fill(tmp, (byte)0);
            }
         }
      }

      if (this.password != null) {
         Arrays.fill(this.password, (byte)0);
      }

      this.password = passwordBytes;
   }

   public boolean isPasswordSet() {
      return this.password != null;
   }

   public boolean isUsernameSet() {
      return this.username != null;
   }

   public void setEncryption(ClearOrEncryptedService es) {
      if (this.password != null) {
         byte[] oldPassword = this.es != null ? this.es.decryptBytes(this.password) : this.password;
         byte[] newPassword = es != null ? es.encryptBytes(oldPassword) : oldPassword;
         if (oldPassword != newPassword) {
            Arrays.fill(oldPassword, (byte)0);
         }

         if (this.password != newPassword) {
            Arrays.fill(this.password, (byte)0);
         }

         this.password = newPassword;
      }

      this.es = es;
   }

   public void dispose() {
      this.setPassword((char[])null);
      this.setUsername((String)null);
   }

   public void finalize() throws Throwable {
      this.dispose();
      super.finalize();
   }

   private static final char[] getUTF16Chars(byte[] bytes) {
      if (bytes == null) {
         return null;
      } else if (bytes.length % 2 == 1) {
         throw new IllegalArgumentException("Odd byte array length: " + bytes.length);
      } else {
         char[] chars = new char[bytes.length / 2];
         int i = 0;

         for(int j = 0; i < chars.length; ++i) {
            chars[i] = (char)((255 & bytes[j++]) << 8 | 255 & bytes[j++]);
         }

         return chars;
      }
   }

   private static final byte[] getUTF16Bytes(char[] chars) {
      if (chars == null) {
         return null;
      } else {
         byte[] bytes = new byte[chars.length * 2];
         int i = 0;

         for(int j = 0; i < chars.length; ++i) {
            bytes[j++] = (byte)(chars[i] >>> 8 & 255);
            bytes[j++] = (byte)(chars[i] & 255);
         }

         return bytes;
      }
   }
}

package weblogic.security.acl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Vector;
import weblogic.diagnostics.debug.DebugLogger;

/** @deprecated */
@Deprecated
public class DefaultUserInfoImpl implements UserInfo, Serializable {
   private static final long serialVersionUID = -419061834872911373L;
   protected String realmName;
   protected String id;
   protected Vector certificates;
   private byte[] obfuscatedPassword;
   private static final String CHAR_ENCODING = "UTF-8";
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSecurityAtn");

   public DefaultUserInfoImpl() {
      this.realmName = null;
      this.id = null;
      this.certificates = new Vector(0);
      this.obfuscatedPassword = null;
   }

   public String getRealmName() {
      return this.realmName;
   }

   public String getName() {
      return this.id;
   }

   public void setName(String newName) {
      this.id = newName;
   }

   public String toString() {
      return "{" + this.getRealmName() + "," + this.getName() + "}";
   }

   public DefaultUserInfoImpl(String name, Object credential) {
      this(name, credential, "weblogic");
   }

   public DefaultUserInfoImpl(String name, Object credential, String realmName) {
      this.realmName = null;
      this.id = null;
      this.certificates = new Vector(0);
      this.obfuscatedPassword = null;
      this.id = name;
      this.realmName = realmName;
      this.setCredential(credential);
      if (this.id == null && this.certificates != null && this.certificates.size() > 0) {
         this.id = ((X509Certificate)this.certificates.elementAt(0)).getSubjectDN().getName();
      }

   }

   protected void setCredential(Object c) {
      if (c instanceof String) {
         this.obfuscatedPassword = obfuscate((String)c);
      } else if (c instanceof X509Certificate) {
         this.certificates.addElement(c);
      } else if (c instanceof char[]) {
         byte[] clearBytes = getClearTextBytes((char[])((char[])c));
         this.obfuscatedPassword = obfuscate(clearBytes);
         Arrays.fill(clearBytes, (byte)0);
      } else if (c instanceof Object[]) {
         Object[] cs = (Object[])((Object[])c);

         for(int i = 0; i < cs.length; ++i) {
            this.setCredential(cs[i]);
         }
      }

   }

   public boolean hasPassword() {
      return this.obfuscatedPassword != null;
   }

   public String getPassword() {
      if (this.obfuscatedPassword != null) {
         try {
            byte[] clearBytes = unobfuscate(this.obfuscatedPassword);
            String clear = new String(clearBytes, "UTF-8");
            Arrays.fill(clearBytes, (byte)0);
            return clear;
         } catch (UnsupportedEncodingException var3) {
            if (DEBUG.isDebugEnabled()) {
               DebugLogger.println("The impossible happened: 1");
            }
         }
      }

      return null;
   }

   public char[] getPasswordAsCharArray() {
      char[] clearText = null;
      if (this.obfuscatedPassword != null) {
         byte[] clearBytes = unobfuscate(this.obfuscatedPassword);
         clearText = getClearTextChars(clearBytes);
         Arrays.fill(clearBytes, (byte)0);
      }

      return clearText;
   }

   public boolean hasCertificates() {
      return this.certificates.size() > 0;
   }

   public Vector getCertificates() {
      return (Vector)this.certificates.clone();
   }

   private static byte[] obfuscate(String clear) {
      try {
         byte[] clearBytes = clear.getBytes("UTF-8");
         byte[] obfuscatedBytes = obfuscate(clearBytes);
         Arrays.fill(clearBytes, (byte)0);
         return obfuscatedBytes;
      } catch (UnsupportedEncodingException var3) {
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("The impossible happened: 2");
         }

         return null;
      }
   }

   private static byte[] obfuscate(byte[] clearBytes) {
      return flipBytes(clearBytes);
   }

   private static byte[] unobfuscate(byte[] obfuscatedBytes) {
      return flipBytes(obfuscatedBytes);
   }

   private static byte[] flipBytes(byte[] bytes) {
      if (bytes == null) {
         return null;
      } else {
         byte[] flipped = new byte[bytes.length];

         for(int i = 0; i < bytes.length; ++i) {
            flipped[i] = bytes[i];
            flipped[i] = (byte)(flipped[i] ^ 255);
         }

         return flipped;
      }
   }

   private static byte[] getClearTextBytes(char[] clearText) {
      try {
         ByteArrayOutputStream bos = new ByteArrayOutputStream(clearText.length * 2);
         OutputStreamWriter writer = new OutputStreamWriter(bos, "UTF-8");
         writer.write(clearText, 0, clearText.length);
         writer.flush();
         byte[] bytes = bos.toByteArray();
         bos.reset();

         for(int i = 0; i < bytes.length; ++i) {
            bos.write(0);
         }

         return bytes;
      } catch (UnsupportedEncodingException var5) {
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("The impossible happened: 3");
         }
      } catch (IOException var6) {
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("The impossible happened: 4");
         }
      }

      return null;
   }

   private static char[] getClearTextChars(byte[] clearBytes) {
      try {
         ByteArrayInputStream bos = new ByteArrayInputStream(clearBytes);
         InputStreamReader reader = new InputStreamReader(bos, "UTF-8");
         char[] plainText = new char[clearBytes.length];
         int charsRead = reader.read(plainText, 0, plainText.length);
         bos.reset();
         if (charsRead < plainText.length) {
            char[] temp = new char[charsRead];
            System.arraycopy(plainText, 0, temp, 0, charsRead);
            Arrays.fill(plainText, '0');
            plainText = temp;
         }

         return plainText;
      } catch (UnsupportedEncodingException var6) {
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("The impossible happened: 5");
         }
      } catch (IOException var7) {
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("The impossible happened: 6");
         }
      }

      return null;
   }

   public int hashCode() {
      return (this.realmName == null ? 0 : this.realmName.hashCode()) ^ (this.id == null ? 0 : this.id.hashCode()) ^ (this.obfuscatedPassword == null ? 0 : Arrays.hashCode(this.obfuscatedPassword)) ^ (this.certificates == null ? 0 : this.certificates.hashCode());
   }

   public boolean equals(Object obj) {
      if (obj != null && obj instanceof DefaultUserInfoImpl) {
         boolean var10000;
         label21: {
            DefaultUserInfoImpl that = (DefaultUserInfoImpl)obj;
            if (this.id == null) {
               if (that.id != null) {
                  break label21;
               }
            } else if (!this.id.equals(that.id)) {
               break label21;
            }

            if (this.equalsInAllButName(that)) {
               var10000 = true;
               return var10000;
            }
         }

         var10000 = false;
         return var10000;
      } else {
         return false;
      }
   }

   public boolean equalsInAllButName(DefaultUserInfoImpl that) {
      boolean var10000;
      label30: {
         String thatRealmName = that.getRealmName();
         byte[] thatPassword = that.obfuscatedPassword;
         if (this.realmName == null) {
            if (thatRealmName != null) {
               break label30;
            }
         } else if (!this.realmName.equals(thatRealmName)) {
            break label30;
         }

         if (this.obfuscatedPassword == null) {
            if (thatPassword != null) {
               break label30;
            }
         } else if (!Arrays.equals(this.obfuscatedPassword, thatPassword)) {
            break label30;
         }

         if (this.equalsCertificatesOnly(that)) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   private boolean equalsCertificatesOnly(DefaultUserInfoImpl other) {
      Vector otherCertificates = other.getCertificates();
      int thisNum = this.certificates.size();
      int otherNum = otherCertificates.size();
      if (thisNum == 0 && otherNum == 0) {
         return true;
      } else if (thisNum != otherNum) {
         return false;
      } else {
         Vector thisClone = (Vector)this.certificates.clone();
         Vector otherClone = (Vector)otherCertificates.clone();
         return thisClone.equals(otherClone);
      }
   }
}

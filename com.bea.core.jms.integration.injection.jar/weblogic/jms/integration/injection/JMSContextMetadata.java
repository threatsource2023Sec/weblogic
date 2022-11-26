package weblogic.jms.integration.injection;

import java.io.Serializable;
import java.security.MessageDigest;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSPasswordCredential;
import javax.jms.JMSSessionMode;

public class JMSContextMetadata implements Serializable {
   private static final long serialVersionUID = -1320372190519961043L;
   public static final String DEFAULT_CONNECTION_FACTORY = "java:comp/DefaultJMSConnectionFactory";
   private final String lookup;
   private final int sessionMode;
   private final String userName;
   private final String password;
   private String fingerPrint;

   JMSContextMetadata(JMSConnectionFactory jmsConnectionFactoryAnnot, JMSSessionMode sessionModeAnnot, JMSPasswordCredential credentialAnnot) {
      if (jmsConnectionFactoryAnnot == null) {
         this.lookup = null;
      } else {
         this.lookup = jmsConnectionFactoryAnnot.value().trim();
      }

      if (sessionModeAnnot == null) {
         this.sessionMode = 1;
      } else {
         this.sessionMode = sessionModeAnnot.value();
      }

      if (credentialAnnot == null) {
         this.userName = null;
         this.password = null;
      } else {
         this.userName = credentialAnnot.userName();
         this.password = this.getUnAliasedPwd(credentialAnnot.password());
      }

   }

   public String getLookup() {
      return this.lookup;
   }

   public int getSessionMode() {
      return this.sessionMode;
   }

   public String getUserName() {
      return this.userName;
   }

   public String getPassword() {
      return this.password;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("JMSContextMetadata[");
      sb.append("lookup=").append(this.lookup);
      sb.append(", sessionMode=").append(this.sessionMode);
      sb.append(", username=").append(this.userName);
      sb.append(", password=");
      if (this.password != null) {
         sb.append("xxxxxx");
      } else {
         sb.append("null");
      }

      sb.append(" [fingerPrint[").append(this.getFingerPrint());
      sb.append("]]");
      return sb.toString();
   }

   public String getFingerPrint() {
      if (this.fingerPrint == null) {
         try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte delimer = 124;
            md.update(delimer);
            String cf = this.lookup;
            if (this.lookup == null) {
               cf = "java:comp/DefaultJMSConnectionFactory";
            }

            md.update(cf.getBytes("ISO-8859-1"));
            md.update(delimer);
            md.update((byte)this.sessionMode);
            md.update(delimer);
            if (this.userName != null) {
               md.update(this.userName.getBytes("ISO-8859-1"));
            }

            md.update(delimer);
            if (this.password != null) {
               md.update(this.password.getBytes("ISO-8859-1"));
            }

            md.update(delimer);
            byte[] result = md.digest();
            StringBuffer buff = new StringBuffer();

            for(int i = 0; i < result.length; ++i) {
               String byteStr = Integer.toHexString(result[i] & 255);
               if (byteStr.length() < 2) {
                  buff.append('0');
               }

               buff.append(byteStr);
            }

            this.fingerPrint = buff.toString();
         } catch (Exception var8) {
            throw new RuntimeException("Couldn't make digest of JMSContextMetadata content", var8);
         }
      }

      return this.fingerPrint;
   }

   private String getUnAliasedPwd(String password) {
      return password;
   }
}

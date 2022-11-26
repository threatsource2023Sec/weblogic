package javax.security.auth.message.callback;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import javax.security.auth.callback.Callback;
import javax.security.auth.x500.X500Principal;

public class PrivateKeyCallback implements Callback {
   private Request request;
   private PrivateKey key;
   private Certificate[] chain;

   public PrivateKeyCallback(Request request) {
      this.request = request;
   }

   public Request getRequest() {
      return this.request;
   }

   public void setKey(PrivateKey key, Certificate[] chain) {
      this.key = key;
      this.chain = chain;
   }

   public PrivateKey getKey() {
      return this.key;
   }

   public Certificate[] getChain() {
      return this.chain;
   }

   public static class DigestRequest implements Request {
      private String algorithm;
      private byte[] digest;

      public DigestRequest(byte[] digest, String algorithm) {
         this.digest = digest;
         this.algorithm = algorithm;
      }

      public byte[] getDigest() {
         return this.digest;
      }

      public String getAlgorithm() {
         return this.algorithm;
      }
   }

   public static class IssuerSerialNumRequest implements Request {
      private X500Principal issuer;
      private BigInteger serialNum;

      public IssuerSerialNumRequest(X500Principal issuer, BigInteger serialNumber) {
         this.issuer = issuer;
         this.serialNum = serialNumber;
      }

      public X500Principal getIssuer() {
         return this.issuer;
      }

      public BigInteger getSerialNum() {
         return this.serialNum;
      }
   }

   public static class SubjectKeyIDRequest implements Request {
      private byte[] id;

      public SubjectKeyIDRequest(byte[] subjectKeyID) {
         if (subjectKeyID != null) {
            this.id = (byte[])subjectKeyID.clone();
         }

      }

      public byte[] getSubjectKeyID() {
         return this.id;
      }
   }

   public static class AliasRequest implements Request {
      private String alias;

      public AliasRequest(String alias) {
         this.alias = alias;
      }

      public String getAlias() {
         return this.alias;
      }
   }

   public interface Request {
   }
}

package org.apache.xml.security.stax.securityToken;

import org.apache.xml.security.stax.ext.ComparableType;

public class SecurityTokenConstants {
   public static final TokenUsage TokenUsage_Signature = new TokenUsage("Signature");
   public static final TokenUsage TokenUsage_Encryption = new TokenUsage("Encryption");
   public static final KeyUsage KeyUsage_Signature_Verification = new KeyUsage("Signature_Verification");
   public static final KeyUsage KeyUsage_Decryption = new KeyUsage("Decryption");
   public static final KeyIdentifier KeyIdentifier_KeyValue = new KeyIdentifier("KeyValue");
   public static final KeyIdentifier KeyIdentifier_KeyName = new KeyIdentifier("KeyName");
   public static final KeyIdentifier KeyIdentifier_IssuerSerial = new KeyIdentifier("IssuerSerial");
   public static final KeyIdentifier KeyIdentifier_SkiKeyIdentifier = new KeyIdentifier("SkiKeyIdentifier");
   public static final KeyIdentifier KeyIdentifier_X509KeyIdentifier = new KeyIdentifier("X509KeyIdentifier");
   public static final KeyIdentifier KeyIdentifier_X509SubjectName = new KeyIdentifier("X509SubjectName");
   public static final KeyIdentifier KeyIdentifier_NoKeyInfo = new KeyIdentifier("NoKeyInfo");
   public static final KeyIdentifier KeyIdentifier_EncryptedKey = new KeyIdentifier("EncryptedKey");
   public static final TokenType EncryptedKeyToken = new TokenType("EncryptedKeyToken");
   public static final TokenType X509V3Token = new TokenType("X509V3Token");
   public static final TokenType X509V1Token = new TokenType("X509V1Token");
   public static final TokenType X509Pkcs7Token = new TokenType("X509Pkcs7Token");
   public static final TokenType X509PkiPathV1Token = new TokenType("X509PkiPathV1Token");
   public static final TokenType KeyValueToken = new TokenType("KeyValueToken");
   public static final TokenType KeyNameToken = new TokenType("KeyNameToken");
   public static final TokenType DefaultToken = new TokenType("DefaultToken");
   public static final TokenType DerivedKeyToken = new TokenType("DerivedKeyToken");

   public static class TokenType extends ComparableType {
      public TokenType(String name) {
         super(name);
      }
   }

   public static class KeyIdentifier extends ComparableType {
      public KeyIdentifier(String name) {
         super(name);
      }
   }

   public static class KeyUsage extends ComparableType {
      public KeyUsage(String name) {
         super(name);
      }
   }

   public static class TokenUsage extends ComparableType {
      public TokenUsage(String name) {
         super(name);
      }
   }
}

package weblogic.security.utils;

public final class SignedRequestInfo {
   private String requestSignature;
   private String ts;
   private String clientServerName;
   private String targetServerName;
   private String nonce;

   public SignedRequestInfo(String signature, String timestamp, String clientServerName, String targetServerName, String nonce) {
      this.requestSignature = signature;
      this.ts = timestamp;
      this.clientServerName = clientServerName;
      this.targetServerName = targetServerName;
      this.nonce = nonce;
   }

   public String getSignature() {
      return this.requestSignature;
   }

   public String getClientServerName() {
      return this.clientServerName;
   }

   public String getTargetServerName() {
      return this.targetServerName;
   }

   public String getTimeStamp() {
      return this.ts;
   }

   public String getNonce() {
      return this.nonce;
   }

   public String toString() {
      String data = "SignedRequestInfo - nonce: " + this.getNonce() + " signature: " + this.getSignature() + " client: " + this.getClientServerName() + " target: " + this.getTargetServerName() + " timestamp: " + this.getTimeStamp();
      return data;
   }
}

package weblogic.wtc.jatmi;

public final class atncredtdom implements atncred {
   private String cred_desired_name;
   private String cred_target_name;
   private byte[] cred_identity_proof;
   private int cred_version_number;
   private int cred_usage;
   private int cred_security_type;
   private long cred_timestamp = System.currentTimeMillis();
   protected static final int GSS_C_BOTH = 0;
   protected static final int GSS_C_INDEFINITE = -1;

   public atncredtdom(String desired, String target, byte[] identity) {
      if (desired != null) {
         this.cred_desired_name = new String(desired);
      }

      if (target != null) {
         this.cred_target_name = new String(target);
      }

      if (identity != null) {
         this.cred_identity_proof = new byte[identity.length];
         System.arraycopy(identity, 0, this.cred_identity_proof, 0, identity.length);
      }

      this.cred_usage = 0;
   }

   public String getDesiredName() {
      return this.cred_desired_name;
   }
}

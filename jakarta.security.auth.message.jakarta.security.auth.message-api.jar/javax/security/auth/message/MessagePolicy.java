package javax.security.auth.message;

public class MessagePolicy {
   private TargetPolicy[] targetPolicies;
   private boolean mandatory;

   public MessagePolicy(TargetPolicy[] targetPolicies, boolean mandatory) {
      if (targetPolicies == null) {
         throw new IllegalArgumentException("invalid null targetPolicies");
      } else {
         this.targetPolicies = (TargetPolicy[])targetPolicies.clone();
         this.mandatory = mandatory;
      }
   }

   public boolean isMandatory() {
      return this.mandatory;
   }

   public TargetPolicy[] getTargetPolicies() {
      return (TargetPolicy[])this.targetPolicies.clone();
   }

   public interface ProtectionPolicy {
      String AUTHENTICATE_SENDER = "#authenticateSender";
      String AUTHENTICATE_CONTENT = "#authenticateContent";
      String AUTHENTICATE_RECIPIENT = "#authenticateRecipient";

      String getID();
   }

   public interface Target {
      Object get(MessageInfo var1);

      void remove(MessageInfo var1);

      void put(MessageInfo var1, Object var2);
   }

   public static class TargetPolicy {
      private Target[] targets;
      private ProtectionPolicy protectionPolicy;

      public TargetPolicy(Target[] targets, ProtectionPolicy protectionPolicy) {
         if (protectionPolicy == null) {
            throw new IllegalArgumentException("invalid null protectionPolicy");
         } else {
            if (targets != null && targets.length != 0) {
               this.targets = (Target[])targets.clone();
            } else {
               this.targets = null;
            }

            this.protectionPolicy = protectionPolicy;
         }
      }

      public Target[] getTargets() {
         return this.targets;
      }

      public ProtectionPolicy getProtectionPolicy() {
         return this.protectionPolicy;
      }
   }
}

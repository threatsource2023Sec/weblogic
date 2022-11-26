package weblogic.rmi.internal.dgc;

public interface DGCPolicyConstants {
   int DEFAULT = -1;
   int LEASED = 0;
   String LEASED_POLICY = "leased";
   int REFERENCE_COUNTED = 1;
   String REFERENCE_COUNTED_POLICY = "referenceCounted";
   int MANAGED = 2;
   String MANAGED_POLICY = "managed";
   int USE_IT_OR_LOSE_IT = 3;
   String USE_IT_OR_LOSE_IT_POLICY = "useItOrLoseIt";
   int DEACTIVATED_ON_METHOD_BOUNDRIES = 4;
   String DEACTIVATED_ON_METHOD_BOUNDRIES_POLICY = "deactivateOnMethodBoundries";
}

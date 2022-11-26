package weblogic.cluster.singleton;

import java.io.IOException;
import java.util.Map;

public class ReplicatedLeasingBasis extends SimpleLeasingBasis {
   public static final String BASIS_NAME = "ReplicatedLeasingBasis";

   public ReplicatedLeasingBasis(String leaseType) throws IOException {
      super(getReplicatedMap(leaseType));
   }

   protected static Map getReplicatedMap(String leaseType) throws IOException {
      return null;
   }
}

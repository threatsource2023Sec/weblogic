package weblogic.cluster.singleton;

import java.io.IOException;

public class ClusterReformationInProgressException extends IOException {
   public ClusterReformationInProgressException(String message) {
      super(message);
   }
}

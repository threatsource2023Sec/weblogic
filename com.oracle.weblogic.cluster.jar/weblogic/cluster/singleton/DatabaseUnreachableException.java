package weblogic.cluster.singleton;

import java.io.IOException;

public class DatabaseUnreachableException extends IOException {
   public DatabaseUnreachableException(String s) {
      super(s);
   }
}

package weblogic.nodemanager;

import java.io.IOException;

public class NMException extends IOException {
   public NMException(String msg) {
      super(msg);
   }
}

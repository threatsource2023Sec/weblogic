package org.apache.velocity.runtime.log;

import org.apache.velocity.runtime.RuntimeServices;

public class NullLogSystem implements LogSystem {
   public void init(RuntimeServices rs) throws Exception {
   }

   public void logVelocityMessage(int level, String message) {
   }
}

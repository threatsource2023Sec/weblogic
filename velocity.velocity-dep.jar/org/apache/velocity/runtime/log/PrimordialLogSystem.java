package org.apache.velocity.runtime.log;

import java.util.Enumeration;
import java.util.Vector;
import org.apache.velocity.runtime.RuntimeServices;

public class PrimordialLogSystem implements LogSystem {
   private Vector pendingMessages = new Vector();
   private RuntimeServices rsvc = null;

   public void init(RuntimeServices rs) throws Exception {
      this.rsvc = rs;
   }

   public void logVelocityMessage(int level, String message) {
      synchronized(this) {
         Object[] data = new Object[]{new Integer(level), message};
         this.pendingMessages.addElement(data);
      }
   }

   public void dumpLogMessages(LogSystem newLogger) {
      synchronized(this) {
         if (!this.pendingMessages.isEmpty()) {
            Enumeration e = this.pendingMessages.elements();

            while(e.hasMoreElements()) {
               Object[] data = (Object[])e.nextElement();
               newLogger.logVelocityMessage((Integer)data[0], (String)data[1]);
            }
         }

      }
   }
}

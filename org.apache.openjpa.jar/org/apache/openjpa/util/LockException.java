package org.apache.openjpa.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.openjpa.lib.util.Localizer;

public class LockException extends StoreException {
   private static final transient Localizer _loc = Localizer.forPackage(LockException.class);
   private int timeout = -1;

   public LockException(Object failed) {
      super(_loc.get("lock-failed", (Object)Exceptions.toString(failed)));
      this.setFailedObject(failed);
   }

   public LockException(Object failed, int timeout) {
      super(_loc.get("lock-timeout", Exceptions.toString(failed), String.valueOf(timeout)));
      this.setFailedObject(failed);
      this.setTimeout(timeout);
   }

   public int getSubtype() {
      return 1;
   }

   public int getTimeout() {
      return this.timeout;
   }

   public LockException setTimeout(int timeout) {
      this.timeout = timeout;
      return this;
   }

   public String toString() {
      String str = super.toString();
      return this.timeout < 0 ? str : str + Exceptions.SEP + "Timeout: " + this.timeout;
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeInt(this.timeout);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this.timeout = in.readInt();
   }
}

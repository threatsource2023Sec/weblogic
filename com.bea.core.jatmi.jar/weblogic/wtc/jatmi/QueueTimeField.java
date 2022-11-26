package weblogic.wtc.jatmi;

import java.io.Serializable;

public final class QueueTimeField implements Serializable {
   private static final long serialVersionUID = -4304569527535747878L;
   private int myTime;
   private boolean myIsRelative;

   public QueueTimeField(int aTime, boolean isRelative) {
      this.myTime = aTime;
      this.myIsRelative = isRelative;
   }

   public int getTime() {
      return this.myTime;
   }

   public boolean isRelative() {
      return this.myIsRelative;
   }

   public String toString() {
      return new String(this.myTime + ":" + (this.myIsRelative ? "relative" : "absolute"));
   }
}

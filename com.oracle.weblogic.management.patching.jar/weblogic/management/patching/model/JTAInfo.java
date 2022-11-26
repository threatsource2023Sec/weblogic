package weblogic.management.patching.model;

import java.io.Serializable;

public class JTAInfo implements Serializable {
   String ups;
   String destination;
   boolean failback = false;

   public String getUPS() {
      return this.ups;
   }

   public void setUPS(String ups) {
      this.ups = ups;
   }

   public String getDestination() {
      return this.destination;
   }

   public void setDestination(String destination) {
      this.destination = destination;
   }

   public boolean getFailback() {
      return this.failback;
   }

   public void setFailback(boolean failback) {
      this.failback = failback;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(" ups: ");
      sb.append(this.ups);
      sb.append(", destination: ");
      sb.append(this.destination);
      sb.append(", failback: ");
      sb.append(this.failback);
      return sb.toString();
   }
}

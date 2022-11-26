package weblogic.management.patching.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JMSInfo implements Serializable {
   private static final long serialVersionUID = 2572538263570318321L;
   Map mtList = new HashMap();
   boolean failback = false;
   String destination;

   public String getUPS(String mt) {
      return (String)this.mtList.get(mt);
   }

   public void setUPS(String mt, String ups) {
      this.mtList.put(mt, ups);
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

   public Map getMTList() {
      return this.mtList;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(" mtList: ");
      sb.append(this.mtList.toString());
      sb.append(", destination: ");
      sb.append(this.destination);
      sb.append(", failback: ");
      sb.append(this.failback);
      return sb.toString();
   }
}

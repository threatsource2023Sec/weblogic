package weblogic.admin.plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NMMachineChangeList {
   private final String transactionID;
   private final HashMap changes = new HashMap();

   public NMMachineChangeList(String transactionID) {
      this.transactionID = transactionID;
   }

   public String getTransactionID() {
      return this.transactionID;
   }

   public void addChangeListForType(String componentType, NMComponentTypeChangeList changeList) {
      this.changes.put(componentType, changeList);
   }

   public Map getChanges() {
      return this.changes;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("NMMachineChangeList: ");
      sb.append("transactionID=").append(this.transactionID);
      sb.append(", changeCount=").append(this.changes.size());
      Iterator var2 = this.changes.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry change = (Map.Entry)var2.next();
         sb.append("component type: " + (String)change.getKey());
         sb.append("change file list: " + change.getValue());
      }

      return sb.toString();
   }
}

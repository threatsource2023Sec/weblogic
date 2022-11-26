package weblogic.connector.external;

import java.util.ArrayList;
import java.util.Iterator;

public class RAComplianceException extends Exception {
   private static final long serialVersionUID = -8625948130972539284L;
   ArrayList messages = new ArrayList();

   public void addMessage(String message) {
      this.messages.add(message);
   }

   private String calculateMsg() {
      if (this.messages.isEmpty()) {
         return "";
      } else if (this.messages.size() == 1) {
         return (String)this.messages.get(0);
      } else {
         int num = 1;
         StringBuilder sb = new StringBuilder();

         for(Iterator var3 = this.messages.iterator(); var3.hasNext(); ++num) {
            String message = (String)var3.next();
            if (num == 1) {
               sb.append("[1] " + message);
            } else {
               sb.append("\n[" + num + "] " + message);
            }
         }

         return sb.toString();
      }
   }

   public String toString() {
      return this.getMessage();
   }

   public String getMessage() {
      return this.calculateMsg();
   }

   public String[] getMessages() {
      return (String[])this.messages.toArray(new String[0]);
   }
}

package com.bea.diagnostics.notifications;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class NotificationAdapter implements Notification {
   private Map dataMap;
   private NotificationSource source;

   protected NotificationAdapter(NotificationSource ns, Map data) {
      this.dataMap = data;
      this.source = ns;
   }

   public NotificationSource getSource() {
      return this.source;
   }

   public void setSource(NotificationSource source) {
      this.source = source;
   }

   public Object getValue(Object key) {
      return this.dataMap.get(key);
   }

   public void setValue(Object key, Object value) {
      this.dataMap.put(key, value);
   }

   public List keyList() {
      return new ArrayList(this.dataMap.keySet());
   }

   public String toString() {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      Iterator it = this.keyList().iterator();
      pw.print("{ ");
      if (this.source != null) {
         pw.print(this.source.getName() + ", ");
      }

      pw.print("(");

      while(it.hasNext()) {
         Object key = it.next();
         pw.print("[");
         if (key != null) {
            pw.print(key.toString() + ",");
            Object value = this.dataMap.get(key);
            if (value != null) {
               pw.print(value.toString());
            }
         }

         pw.print("]");
         if (it.hasNext()) {
            pw.print(", ");
         }
      }

      pw.print(") }");
      pw.close();
      return sw.getBuffer().toString();
   }

   protected Map getData() {
      return this.dataMap;
   }
}

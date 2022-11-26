package weblogic.xml.babel.dtd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EntityTable {
   private Map values = new HashMap();

   public void put(String name, String value) {
      this.values.put(name, value);
   }

   public void put(String name, ExternalID value) {
      this.values.put(name, value);
   }

   public boolean contains(String name) {
      return this.values.containsKey(name);
   }

   public ExternalID getExternalID(String name) throws DTDException {
      if (!this.contains(name)) {
         throw new DTDException("Undefined Symbol:" + name);
      } else {
         ExternalID value = (ExternalID)this.values.get(name);
         if (value == null) {
            throw new DTDException("Undefined Symbol:" + name);
         } else {
            return value;
         }
      }
   }

   public String get(String name) throws DTDException {
      String value = (String)this.values.get(name);
      if (value == null) {
         throw new DTDException("Undefined Symbol:" + name);
      } else {
         return value;
      }
   }

   public String toString() {
      Set set = this.values.entrySet();
      String val = "";

      Map.Entry entry;
      for(Iterator i = set.iterator(); i.hasNext(); val = val + entry.getKey() + ":" + entry.getValue() + "\n") {
         entry = (Map.Entry)i.next();
      }

      return val;
   }
}

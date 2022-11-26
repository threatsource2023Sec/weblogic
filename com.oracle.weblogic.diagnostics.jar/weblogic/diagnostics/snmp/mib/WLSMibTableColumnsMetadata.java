package weblogic.diagnostics.snmp.mib;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class WLSMibTableColumnsMetadata implements Serializable {
   private static final long serialVersionUID = 5241613599471877544L;
   private Map columnAttributeMap = new HashMap();
   private Map attributeColumnMap = new HashMap();

   public WLSMibTableColumnsMetadata(List columnAttributeTuples) {
      for(int i = 0; i < columnAttributeTuples.size(); ++i) {
         String[] tuple = (String[])((String[])columnAttributeTuples.get(i));
         String column = tuple[0];
         String attribute = tuple[1];
         this.columnAttributeMap.put(column, attribute);
         this.attributeColumnMap.put(attribute, column);
      }

   }

   public String getColumnName(String attribute) {
      return (String)this.attributeColumnMap.get(attribute);
   }

   public String getAttributeName(String column) {
      return (String)this.columnAttributeMap.get(column);
   }

   public Iterator getSnmpTableColumnNames() {
      return this.columnAttributeMap.keySet().iterator();
   }

   public Iterator getAttributeNames() {
      return this.attributeColumnMap.keySet().iterator();
   }

   public Map getColumnAttributeMap() {
      return this.columnAttributeMap;
   }

   public Map getAttributeColumnMap() {
      return this.attributeColumnMap;
   }
}

package weblogic.diagnostics.snmp.agent.monfox;

import java.util.Map;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.ext.table.SnmpMibTableAdaptor;
import weblogic.diagnostics.debug.DebugLogger;

public class TableRow extends SnmpMibTableAdaptor.Row {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private static final int SIZE_LIMIT = 512;
   private Map columnValues;

   public TableRow(Map columnValues) {
      this.columnValues = columnValues;
   }

   public SnmpValue getValue(SnmpOid arg0) throws SnmpValueException {
      String snmpColumnName = arg0.getOidInfo().getName();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Getting SNMP Column " + snmpColumnName);
      }

      Object value = this.columnValues.get(snmpColumnName);
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Got attribute " + snmpColumnName + " value " + value);
      }

      if (value == null) {
         value = "";
      }

      if (value instanceof Number) {
         return SnmpValue.getInstance(arg0, ((Number)value).longValue());
      } else {
         String str = value.toString();
         if (str.length() > 512) {
            str = str.substring(0, 512);
         }

         return SnmpValue.getInstance(arg0, str);
      }
   }

   public void setValue(SnmpOid arg0, SnmpValue arg1) throws SnmpValueException {
   }
}

package weblogic.diagnostics.snmp.agent.monfox;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import monfox.toolkit.snmp.agent.ext.table.SnmpMibTableAdaptor;
import monfox.toolkit.snmp.metadata.builder.MibApi;
import weblogic.diagnostics.snmp.agent.MBeanServerSubAgentX;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.diagnostics.snmp.agent.SNMPConstants;
import weblogic.diagnostics.snmp.agent.SNMPUtil;

public class MBeanServerSubAgentXImpl extends SNMPSubAgentXImpl implements MBeanServerSubAgentX, SNMPConstants {
   private Map snmpColumns = new HashMap();

   public MBeanServerSubAgentXImpl(String masterAgentHost, int masterAgentPort, String subAgentId, String oidSubTree) throws SNMPAgentToolkitException {
      super(masterAgentHost, masterAgentPort, subAgentId, oidSubTree);
   }

   public void addSNMPTableRowForMBeanInstance(MBeanServerConnection mbeanServerConnection, String typeName, ObjectName objectName) throws SNMPAgentToolkitException {
      SnmpMibTableAdaptor tableAdaptor = null;

      try {
         if (!this.snmpTables.containsKey(typeName)) {
            tableAdaptor = this.createSNMPTable(mbeanServerConnection, typeName, objectName);
            this.snmpTables.put(typeName, tableAdaptor);
         } else {
            tableAdaptor = (SnmpMibTableAdaptor)this.snmpTables.get(typeName);
         }

         Map columnMetadata = (Map)this.snmpColumns.get(typeName);
         if (columnMetadata != null) {
            MBeanInstanceTableRow row = new MBeanInstanceTableRow(mbeanServerConnection, objectName, columnMetadata);
            tableAdaptor.addRow(row);
         }

      } catch (Exception var7) {
         throw new SNMPAgentToolkitException(var7);
      }
   }

   public void deleteSNMPTableRowForMBeanInstance(String typeName, ObjectName objectName) throws SNMPAgentToolkitException {
      SnmpMibTableAdaptor tableAdaptor = (SnmpMibTableAdaptor)this.snmpTables.get(typeName);
      if (tableAdaptor != null) {
         String index = MBeanInstanceTableRow.computeIndex(objectName.toString());
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Deleting custom mbean row for index " + index);
         }

         try {
            tableAdaptor.removeRow(new String[]{index});
         } catch (Exception var6) {
            throw new SNMPAgentToolkitException(var6);
         }
      }

   }

   private SnmpMibTableAdaptor createSNMPTable(MBeanServerConnection mbeanServerConnection, String typeName, ObjectName objectName) throws Exception {
      String tableName = SNMPUtil.convertTypeNameToSNMPTableName(typeName);
      String prefix = tableName;
      MibApi.Table table = this.mibApi.createTable(this.moduleName, tableName, "Dynamically created table for type " + typeName);
      MBeanAttributeInfo[] attributeInfos = mbeanServerConnection.getMBeanInfo(objectName).getAttributes();
      Comparator attributeInfoComparator = new Comparator() {
         public int compare(Object o1, Object o2) {
            MBeanAttributeInfo info1 = (MBeanAttributeInfo)o1;
            MBeanAttributeInfo info2 = (MBeanAttributeInfo)o2;
            return info1.getName().compareTo(info2.getName());
         }
      };
      Arrays.sort(attributeInfos, attributeInfoComparator);
      String indexColumn = tableName + "Index";
      table.addColumn(indexColumn, "SNMPv2-TC.DisplayString", "na", "Index column", true);
      String objectNameColumn = tableName + "ObjectName";
      table.addColumn(objectNameColumn, "SNMPv2-TC.DisplayString", "ro", "ObjectName column", false);
      Map columnMetaData = new HashMap();
      columnMetaData.put(indexColumn, "Index");
      columnMetaData.put(objectNameColumn, "ObjectName");
      if (attributeInfos != null) {
         for(int i = 0; i < attributeInfos.length; ++i) {
            MBeanAttributeInfo ai = attributeInfos[i];
            String attributeName = ai.getName();
            String attributeType = ai.getType();
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Discovered attribute " + attributeName + " of type " + attributeType);
            }

            String snmpColumnName = prefix + attributeName;
            String snmpTypeName = "SNMPv2-TC.DisplayString";
            table.addColumn(snmpColumnName, snmpTypeName, "ro", ai.getDescription(), false);
            columnMetaData.put(snmpColumnName, attributeName);
         }
      }

      table.exportToMetadata();
      this.snmpColumns.put(typeName, columnMetaData);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Adding table name " + tableName + " for type " + typeName);
      }

      SnmpMibTableAdaptor ta = new SnmpMibTableAdaptor(this.snmpMetadata, tableName);
      ta.isLazyLoadingEnabled(true);
      this.snmpMib.add(ta);
      return ta;
   }
}

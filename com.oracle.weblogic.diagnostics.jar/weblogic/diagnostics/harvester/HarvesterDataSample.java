package weblogic.diagnostics.harvester;

import java.util.Date;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.utils.SharedConstants;
import weblogic.utils.PlatformConstants;

public final class HarvesterDataSample extends DataRecord {
   static final long serialVersionUID = 1L;
   private static final ColumnInfo[] columnInfos = ArchiveConstants.getColumns(2);
   private static final int RECORD_SIZE;
   private static final int RECORDID_INDEX;
   private static final int TIMESTAMP_INDEX;
   private static final int DOMAIN_INDEX;
   private static final int SERVER_INDEX;
   private static final int TYPE_INDEX;
   private static final int INST_NAME_INDEX;
   private static final int ATTR_NAME_INDEX;
   private static final int ATTR_TYPE_INDEX;
   private static final int ATTR_VALUE_INDEX;
   private static final int WLDFMODULE_VALUE_INDEX;
   private static final int PARTITION_ID_VALUE_INDEX;
   private static final int PARTITION_NAME_VALUE_INDEX;

   public HarvesterDataSample() {
   }

   public HarvesterDataSample(String sourceModule, long timestamp, String typeName, String instanceName, String attributeName, Object attributeValue, String partitionId, String partitionName) {
      super(new Object[RECORD_SIZE]);
      Object[] data = this.getValues();
      data[TIMESTAMP_INDEX] = new Long(timestamp);
      data[DOMAIN_INDEX] = SharedConstants.DOMAIN_NAME;
      data[SERVER_INDEX] = SharedConstants.SERVER_NAME;
      data[TYPE_INDEX] = typeName;
      data[INST_NAME_INDEX] = instanceName;
      data[ATTR_NAME_INDEX] = attributeName;
      data[ATTR_TYPE_INDEX] = new Integer(this.getValueType(attributeValue));
      data[ATTR_VALUE_INDEX] = attributeValue;
      data[WLDFMODULE_VALUE_INDEX] = sourceModule;
      data[PARTITION_ID_VALUE_INDEX] = partitionId;
      data[PARTITION_NAME_VALUE_INDEX] = partitionName;
   }

   public long getRecordID() {
      Object o = this.getValues()[RECORDID_INDEX];
      return o instanceof Long ? (Long)o : 0L;
   }

   public long getTimestamp() {
      Object o = this.getValues()[TIMESTAMP_INDEX];
      return o instanceof Long ? (Long)o : 0L;
   }

   public String getInstanceName() {
      return (String)this.getValues()[INST_NAME_INDEX];
   }

   public String getDomainName() {
      return (String)this.getValues()[DOMAIN_INDEX];
   }

   public String getServerName() {
      return (String)this.getValues()[SERVER_INDEX];
   }

   public String getTypeName() {
      return (String)this.getValues()[TYPE_INDEX];
   }

   public String getAttributeName() {
      return (String)this.getValues()[ATTR_NAME_INDEX];
   }

   public String getSourceModule() {
      return (String)this.getValues()[WLDFMODULE_VALUE_INDEX];
   }

   public Object getAttributeValue() {
      return this.getValues()[ATTR_VALUE_INDEX];
   }

   public String getPartitionId() {
      return (String)this.getValues()[PARTITION_ID_VALUE_INDEX];
   }

   public String getPartitionName() {
      return (String)this.getValues()[PARTITION_NAME_VALUE_INDEX];
   }

   public String toString() {
      return this.toStringLong();
   }

   public String toStringShort() {
      StringBuffer buf = new StringBuffer();
      String typeAsString2 = columnInfos[ATTR_TYPE_INDEX].getColumnTypeName();
      buf.append("Sample: ^" + this.getAttributeName() + "^" + this.getAttributeValue() + "^" + this.getTypeName() + "^" + typeAsString2 + "^" + this.getInstanceName());
      buf.append(PlatformConstants.EOL);
      return buf.toString();
   }

   public String toStringLong() {
      StringBuffer buf = new StringBuffer();
      long ts = this.getTimestamp();
      buf.append("   RecordID: " + this.getRecordID() + PlatformConstants.EOL);
      buf.append("  Timestamp: [" + ts + "] (sometime slightly after) " + new Date(ts) + PlatformConstants.EOL);
      buf.append("  Domain: " + this.getDomainName() + PlatformConstants.EOL);
      buf.append("  Server: " + this.getServerName() + PlatformConstants.EOL);
      buf.append("  Source module: " + this.getSourceModule() + PlatformConstants.EOL);
      buf.append("  Type: " + this.getTypeName() + PlatformConstants.EOL);
      buf.append("  Instance: " + this.getInstanceName() + PlatformConstants.EOL);
      buf.append("  Attribute: " + this.getAttributeName() + PlatformConstants.EOL);
      String typeAsString = columnInfos[ATTR_TYPE_INDEX].getColumnTypeName();
      buf.append("  Attribute type: " + typeAsString + PlatformConstants.EOL);
      buf.append("  Attribute value: " + this.getAttributeValue() + PlatformConstants.EOL);
      buf.append("  Partition:" + this.getPartitionName() + "-" + this.getPartitionId() + PlatformConstants.EOL);
      buf.append(PlatformConstants.EOL);
      return buf.toString();
   }

   private static final int getColumnIndex(String columnNameIn) {
      for(int i = 0; i < columnInfos.length; ++i) {
         ColumnInfo column = columnInfos[i];
         String columnName = column.getColumnName();
         if (columnName.equals(columnNameIn)) {
            return i;
         }
      }

      throw new AssertionError(LogSupport.getGenericHarvesterProblemText("Column name " + columnNameIn + " not found in column info from diagnostics achive subsystem"));
   }

   private int getValueType(Object attributeValue) {
      if (attributeValue instanceof Number) {
         return 4;
      } else {
         return attributeValue instanceof String ? 5 : 6;
      }
   }

   static {
      RECORD_SIZE = ArchiveConstants.HARVESTER_ARCHIVE_COLUMNS_COUNT;
      RECORDID_INDEX = getColumnIndex("RECORDID");
      TIMESTAMP_INDEX = getColumnIndex("TIMESTAMP");
      DOMAIN_INDEX = getColumnIndex("DOMAIN");
      SERVER_INDEX = getColumnIndex("SERVER");
      TYPE_INDEX = getColumnIndex("TYPE");
      INST_NAME_INDEX = getColumnIndex("NAME");
      ATTR_NAME_INDEX = getColumnIndex("ATTRNAME");
      ATTR_TYPE_INDEX = getColumnIndex("ATTRTYPE");
      ATTR_VALUE_INDEX = getColumnIndex("ATTRVALUE");
      WLDFMODULE_VALUE_INDEX = getColumnIndex("WLDFMODULE");
      PARTITION_ID_VALUE_INDEX = getColumnIndex("PARTITION_ID");
      PARTITION_NAME_VALUE_INDEX = getColumnIndex("PARTITION_NAME");
   }
}

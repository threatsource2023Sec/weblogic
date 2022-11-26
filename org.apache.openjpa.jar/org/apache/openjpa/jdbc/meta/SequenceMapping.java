package org.apache.openjpa.jdbc.meta;

import java.io.File;
import org.apache.openjpa.jdbc.conf.JDBCSeqValue;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.meta.SequenceMetaData;

public class SequenceMapping extends SequenceMetaData {
   public static final String IMPL_VALUE_TABLE = "value-table";
   public static final String IMPL_TABLE = "table";
   public static final String IMPL_CLASS_TABLE = "class-table";
   private static final String PROP_TABLE = "Table";
   private static final String PROP_SEQUENCE_COL = "SequenceColumn";
   private static final String PROP_PK_COL = "PrimaryKeyColumn";
   private static final String PROP_PK_VALUE = "PrimaryKeyValue";
   private File _mapFile = null;
   private String _table = null;
   private String _sequenceColumn = null;
   private String _primaryKeyColumn = null;
   private String _primaryKeyValue = null;

   public SequenceMapping(String name, MappingRepository repos) {
      super(name, repos);
   }

   public File getMappingFile() {
      return this._mapFile;
   }

   public void setMappingFile(File file) {
      this._mapFile = file;
   }

   public String getTable() {
      return this._table;
   }

   public void setTable(String table) {
      this._table = table;
   }

   public String getSequenceColumn() {
      return this._sequenceColumn;
   }

   public void setSequenceColumn(String sequenceColumn) {
      this._sequenceColumn = sequenceColumn;
   }

   public String getPrimaryKeyColumn() {
      return this._primaryKeyColumn;
   }

   public void setPrimaryKeyColumn(String primaryKeyColumn) {
      this._primaryKeyColumn = primaryKeyColumn;
   }

   public String getPrimaryKeyValue() {
      return this._primaryKeyValue;
   }

   public void setPrimaryKeyValue(String primaryKeyValue) {
      this._primaryKeyValue = primaryKeyValue;
   }

   protected PluginValue newPluginValue(String property) {
      return new JDBCSeqValue(property);
   }

   protected void addStandardProperties(StringBuffer props) {
      super.addStandardProperties(props);
      this.appendProperty(props, "Table", this._table);
      this.appendProperty(props, "SequenceColumn", this._sequenceColumn);
      this.appendProperty(props, "PrimaryKeyColumn", this._primaryKeyColumn);
      this.appendProperty(props, "PrimaryKeyValue", this._primaryKeyValue);
   }
}

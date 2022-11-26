package weblogic.jdbc.rowset;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import javax.sql.RowSetMetaData;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLOutputStream;

/** @deprecated */
@Deprecated
public interface WLRowSetMetaData extends RowSetMetaData {
   int VERIFY_READ_COLUMNS = 1;
   int VERIFY_MODIFIED_COLUMNS = 2;
   int VERIFY_SELECTED_COLUMNS = 3;
   int VERIFY_NONE = 4;
   int VERIFY_AUTO_VERSION_COLUMNS = 5;
   int VERIFY_VERSION_COLUMNS = 6;

   int getOptimisticPolicy();

   String getOptimisticPolicyAsString();

   void setOptimisticPolicy(int var1) throws SQLException;

   void setOptimisticPolicyAsString(String var1) throws SQLException;

   void setBatchInserts(boolean var1);

   boolean getBatchInserts();

   void setBatchDeletes(boolean var1);

   boolean getBatchDeletes();

   void setBatchUpdates(boolean var1);

   boolean getBatchUpdates();

   void setVerboseSQL(boolean var1);

   boolean getVerboseSQL();

   void setGroupDeletes(boolean var1);

   boolean getGroupDeletes();

   void setGroupDeleteSize(int var1) throws SQLException;

   int getGroupDeleteSize();

   void setBatchVerifySize(int var1) throws SQLException;

   int getBatchVerifySize();

   int findColumn(String var1) throws SQLException;

   String getQualifiedTableName(String var1) throws SQLException;

   String getQualifiedTableName(int var1) throws SQLException;

   void setTableName(String var1) throws SQLException;

   void setTableName(String var1, String var2) throws SQLException;

   void setPrimaryKeyColumn(int var1, boolean var2) throws SQLException;

   void setPrimaryKeyColumn(String var1, boolean var2) throws SQLException;

   boolean isPrimaryKeyColumn(int var1) throws SQLException;

   boolean isPrimaryKeyColumn(String var1) throws SQLException;

   boolean haveSetPKColumns();

   boolean isReadOnly(String var1) throws SQLException;

   void setReadOnly(int var1, boolean var2) throws SQLException;

   void setReadOnly(String var1, boolean var2) throws SQLException;

   boolean isReadOnly() throws SQLException;

   void setReadOnly(boolean var1) throws SQLException;

   void setVerifySelectedColumn(int var1, boolean var2) throws SQLException;

   boolean isSelectedColumn(int var1) throws SQLException;

   boolean isSelectedColumn(String var1) throws SQLException;

   void setVerifySelectedColumn(String var1, boolean var2) throws SQLException;

   void setAutoVersionColumn(int var1, boolean var2) throws SQLException;

   void setAutoVersionColumn(String var1, boolean var2) throws SQLException;

   boolean isAutoVersionColumn(String var1) throws SQLException;

   boolean isAutoVersionColumn(int var1) throws SQLException;

   void setVersionColumn(int var1, boolean var2) throws SQLException;

   void setVersionColumn(String var1, boolean var2) throws SQLException;

   boolean isVersionColumn(String var1) throws SQLException;

   boolean isVersionColumn(int var1) throws SQLException;

   void setWriteTableName(String var1) throws SQLException;

   String getWriteTableName();

   void markUpdateProperties(String var1, String var2, String var3) throws SQLException;

   String getWriteColumnName(int var1) throws SQLException;

   String getWriteColumnName(String var1) throws SQLException;

   void setWriteColumnName(int var1, String var2) throws SQLException;

   void setWriteColumnName(String var1, String var2) throws SQLException;

   void setDefaultNamespace(String var1);

   String getDefaultNamespace();

   void setRowName(String var1);

   String getRowName();

   void setRowSetName(String var1);

   String getRowSetName();

   void writeXMLSchema(XMLOutputStream var1) throws IOException, SQLException;

   void loadXMLSchema(XMLInputStream var1) throws IOException, SQLException;

   String getXMLSchemaLocation();

   void setXMLSchemaLocation(String var1);

   Properties getRowAttributes(String var1, String var2) throws SQLException;

   Properties setRowAttributes(String var1, String var2, Properties var3) throws SQLException;

   Map getAllRowAttributes();

   Properties getColAttributes(String var1, String var2, String var3) throws SQLException;

   Properties getColAttributes(String var1, String var2, int var3) throws SQLException;

   Map getAllColAttributes(String var1) throws SQLException;

   Map getAllColAttributes(int var1) throws SQLException;

   Properties setColAttributes(String var1, String var2, int var3, Properties var4) throws SQLException;

   Properties setColAttributes(String var1, String var2, String var3, Properties var4) throws SQLException;
}

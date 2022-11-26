package weblogic.jdbc.rowset;

import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.WebRowSet;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLOutputStream;

/** @deprecated */
@Deprecated
public interface WLCachedRowSet extends RowSet, CachedRowSet, WebRowSet, FilteredRowSet, SortedRowSet, Serializable {
   int UNCHANGED_CURRENT = 1;
   int CHANGED_CURRENT = 2;
   int CHANGED_ORIGINAL = 4;
   int CURRENT_ALL = 8;
   int CHANGED_ALL = 16;
   int ALL_ROWS = 32;

   void populate(ResultSetMetaData var1) throws SQLException;

   String executeAndGuessTableName() throws SQLException;

   boolean executeAndGuessTableNameAndPrimaryKeys() throws SQLException;

   boolean isComplete();

   Map getCurrentRow() throws SQLException;

   Map getRow(int var1) throws SQLException;

   Map[] getRows(int var1, int var2) throws SQLException;

   Map[] getRows() throws SQLException;

   void setDataSource(DataSource var1) throws SQLException;

   DataSource getDataSource() throws SQLException;

   void loadXML(XMLInputStream var1) throws IOException, SQLException;

   void writeXML(XMLOutputStream var1) throws IOException, SQLException;

   void writeXML(XMLOutputStream var1, int var2) throws IOException, SQLException;

   void setRowSynced() throws SQLException;

   void setRowSetSynced() throws SQLException;

   void moveToUpdateRow() throws SQLException;
}

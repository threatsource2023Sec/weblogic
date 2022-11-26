package org.apache.openjpa.jdbc.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.PrimaryKey;

public class FoxProDictionary extends DBDictionary {
   public FoxProDictionary() {
      this.platform = "Visual FoxPro";
      this.joinSyntax = 1;
      this.supportsForeignKeys = false;
      this.supportsDeferredConstraints = false;
      this.maxTableNameLength = 30;
      this.maxColumnNameLength = 30;
      this.maxIndexNameLength = 8;
      this.maxConstraintNameLength = 8;
      this.binaryTypeName = "GENERAL";
      this.blobTypeName = "GENERAL";
      this.longVarbinaryTypeName = "GENERAL";
      this.clobTypeName = "MEMO";
      this.longVarcharTypeName = "MEMO";
      this.dateTypeName = "TIMESTAMP";
      this.timeTypeName = "TIMESTAMP";
      this.varcharTypeName = "CHARACTER{0}";
      this.bigintTypeName = "DOUBLE";
      this.numericTypeName = "INTEGER";
      this.smallintTypeName = "INTEGER";
      this.bitTypeName = "NUMERIC(1)";
      this.integerTypeName = "INTEGER";
      this.tinyintTypeName = "INTEGER";
      this.decimalTypeName = "DOUBLE";
      this.doubleTypeName = "DOUBLE";
      this.realTypeName = "DOUBLE";
      this.floatTypeName = "NUMERIC(19,16)";
      this.characterColumnSize = 240;
   }

   public String getString(ResultSet rs, int column) throws SQLException {
      String str = rs.getString(column);
      if (str != null) {
         str = str.trim();
      }

      return str;
   }

   public void setNull(PreparedStatement stmnt, int idx, int colType, Column col) throws SQLException {
      switch (colType) {
         case 2004:
            stmnt.setBytes(idx, (byte[])null);
            break;
         case 2005:
            stmnt.setString(idx, (String)null);
            break;
         default:
            super.setNull(stmnt, idx, colType, col);
      }

   }

   protected String appendSize(Column col, String typeName) {
      if (col.getSize() == 0) {
         if ("CHARACTER".equals(typeName)) {
            col.setSize(240);
         } else if ("NUMERIC".equals(typeName)) {
            col.setSize(19);
         }
      }

      return super.appendSize(col, typeName);
   }

   protected String getPrimaryKeyConstraintSQL(PrimaryKey pk) {
      return null;
   }

   public String[] getCreateIndexSQL(Index index) {
      return new String[0];
   }

   public Column[] getColumns(DatabaseMetaData meta, String catalog, String schemaName, String tableName, String columnName, Connection conn) throws SQLException {
      try {
         Column[] cols = super.getColumns(meta, catalog, schemaName, tableName, columnName, conn);

         for(int i = 0; cols != null && i < cols.length; ++i) {
            if (cols[i].getType() == 11) {
               cols[i].setType(93);
            } else if ("MEMO".equals(cols[i].getTypeName())) {
               cols[i].setType(2005);
            }
         }

         return cols;
      } catch (SQLException var9) {
         if (var9.getErrorCode() == 562) {
            return null;
         } else {
            throw var9;
         }
      }
   }

   public PrimaryKey[] getPrimaryKeys(DatabaseMetaData meta, String catalog, String schemaName, String tableName, Connection conn) throws SQLException {
      return null;
   }
}

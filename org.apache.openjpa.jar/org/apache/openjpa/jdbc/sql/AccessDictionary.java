package org.apache.openjpa.jdbc.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.schema.Column;

public class AccessDictionary extends DBDictionary {
   public AccessDictionary() {
      this.platform = "Microsoft Access";
      this.joinSyntax = 1;
      this.validationSQL = "SELECT 1";
      this.reservedWordSet.add("VALUE");
      this.supportsAutoAssign = true;
      this.autoAssignTypeName = "COUNTER";
      this.lastGeneratedKeyQuery = "SELECT @@identity";
      this.maxTableNameLength = 64;
      this.maxColumnNameLength = 64;
      this.maxIndexNameLength = 64;
      this.maxConstraintNameLength = 64;
      this.useGetBytesForBlobs = true;
      this.useGetBestRowIdentifierForPrimaryKeys = true;
      this.binaryTypeName = "LONGBINARY";
      this.blobTypeName = "LONGBINARY";
      this.longVarbinaryTypeName = "LONGBINARY";
      this.clobTypeName = "LONGCHAR";
      this.longVarcharTypeName = "LONGCHAR";
      this.bigintTypeName = "REAL";
      this.numericTypeName = "REAL";
      this.integerTypeName = "INTEGER";
      this.smallintTypeName = "SMALLINT";
      this.tinyintTypeName = "SMALLINT";
      this.supportsForeignKeys = false;
      this.supportsDeferredConstraints = false;
      this.maxIndexesPerTable = 32;
      this.substringFunctionName = "MID";
   }

   public void setLong(PreparedStatement stmnt, int idx, long val, Column col) throws SQLException {
      if (val < 2147483647L && val > -2147483648L) {
         stmnt.setInt(idx, (int)val);
      } else {
         stmnt.setDouble(idx, (double)val);
      }

   }
}

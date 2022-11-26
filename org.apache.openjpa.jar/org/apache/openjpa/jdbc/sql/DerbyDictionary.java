package org.apache.openjpa.jdbc.sql;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import javax.sql.DataSource;

public class DerbyDictionary extends AbstractDB2Dictionary {
   public boolean shutdownOnClose = true;

   public DerbyDictionary() {
      this.platform = "Apache Derby";
      this.validationSQL = "VALUES(1)";
      this.stringLengthFunction = "LENGTH({0})";
      this.substringFunctionName = "SUBSTR";
      this.maxConstraintNameLength = 18;
      this.maxIndexNameLength = 128;
      this.maxColumnNameLength = 30;
      this.maxTableNameLength = 128;
      this.useGetBytesForBlobs = true;
      this.useSetBytesForBlobs = true;
      this.allowsAliasInBulkClause = false;
      this.supportsDeferredConstraints = false;
      this.supportsSelectForUpdate = true;
      this.supportsDefaultDeleteAction = false;
      this.requiresCastForMathFunctions = true;
      this.requiresCastForComparisons = true;
      this.supportsComments = true;
      this.fixedSizeTypeNameSet.addAll(Arrays.asList("BIGINT", "INTEGER"));
      this.reservedWordSet.addAll(Arrays.asList("ALIAS", "BIGINT", "BOOLEAN", "CALL", "CLASS", "COPY", "DB2J_DEBUG", "EXECUTE", "EXPLAIN", "FILE", "FILTER", "GETCURRENTCONNECTION", "INDEX", "INSTANCEOF", "KEY", "METHOD", "NEW", "OFF", "OUT", "PROPERTIES", "PUBLICATION", "RECOMPILE", "REFRESH", "RENAME", "RUNTIMESTATISTICS", "STATEMENT", "STATISTICS", "TIMING", "WAIT", "XML"));
   }

   public void closeDataSource(DataSource dataSource) {
      super.closeDataSource(dataSource);
      if (this.shutdownOnClose) {
         if (this.conf != null && this.conf.getConnectionDriverName() != null && this.conf.getConnectionDriverName().indexOf("EmbeddedDriver") != -1) {
            try {
               DriverManager.getConnection(this.conf.getConnectionURL() + ";shutdown=true");
            } catch (SQLException var3) {
            }
         }

      }
   }
}

package org.apache.openjpa.jdbc.sql;

import java.sql.SQLException;
import java.util.Arrays;
import org.apache.openjpa.jdbc.kernel.exps.FilterValue;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.ReferentialIntegrityException;

public class JDataStoreDictionary extends DBDictionary {
   public JDataStoreDictionary() {
      this.platform = "Borland JDataStore";
      this.joinSyntax = 1;
      this.supportsDeferredConstraints = false;
      this.allowsAliasInBulkClause = false;
      this.maxTableNameLength = 31;
      this.maxColumnNameLength = 31;
      this.maxIndexNameLength = 31;
      this.maxConstraintNameLength = 31;
      this.useGetStringForClobs = true;
      this.useSetStringForClobs = true;
      this.useGetBytesForBlobs = true;
      this.blobTypeName = "VARBINARY";
      this.clobTypeName = "VARCHAR";
      this.supportsLockingWithDistinctClause = false;
      this.supportsQueryTimeout = false;
      this.supportsAutoAssign = true;
      this.lastGeneratedKeyQuery = "SELECT MAX({0}) FROM {1}";
      this.autoAssignClause = "AUTOINCREMENT";
      this.fixedSizeTypeNameSet.addAll(Arrays.asList("SHORT", "INT", "LONG", "DOUBLE PRECISION", "BOOLEAN"));
      this.searchStringEscape = "";
   }

   public void substring(SQLBuffer buf, FilterValue str, FilterValue start, FilterValue end) {
      buf.append("SUBSTRING(");
      str.appendTo(buf);
      buf.append(" FROM (");
      start.appendTo(buf);
      buf.append(" + 1) FOR (");
      if (end == null) {
         buf.append("CHAR_LENGTH(");
         str.appendTo(buf);
         buf.append(")");
      } else {
         end.appendTo(buf);
      }

      buf.append(" - (");
      start.appendTo(buf);
      buf.append(")))");
   }

   public void indexOf(SQLBuffer buf, FilterValue str, FilterValue find, FilterValue start) {
      buf.append("(POSITION(");
      find.appendTo(buf);
      buf.append(" IN ");
      if (start != null) {
         this.substring(buf, str, start, (FilterValue)null);
      } else {
         str.appendTo(buf);
      }

      buf.append(") - 1");
      if (start != null) {
         buf.append(" + ");
         start.appendTo(buf);
      }

      buf.append(")");
   }

   public OpenJPAException newStoreException(String msg, SQLException[] causes, Object failed) {
      OpenJPAException ke = super.newStoreException(msg, causes, failed);
      if (ke instanceof ReferentialIntegrityException && causes[0].getMessage().indexOf("Duplicate key value for") > -1) {
         ((ReferentialIntegrityException)ke).setIntegrityViolation(2);
      }

      return ke;
   }
}

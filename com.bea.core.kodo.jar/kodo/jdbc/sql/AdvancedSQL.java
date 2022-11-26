package kodo.jdbc.sql;

import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.Union;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.util.Localizer;

public class AdvancedSQL implements Configurable {
   private static final Localizer _loc = Localizer.forPackage(AdvancedSQL.class);
   protected JDBCConfiguration conf = null;
   protected DBDictionary dict = null;
   private boolean _supportsUnion = true;
   private boolean _supportsUnionWithUnalignedOrdering = true;
   private int _batchLimit = -1;
   private int _batchParameterLimit = -1;
   private boolean _supportsUpdateCountsForBatch = true;
   private boolean _supportsTotalCountsForBatch = false;

   public boolean getSupportsUnion() {
      return this._supportsUnion;
   }

   public void setSupportsUnion(boolean supports) {
      this._supportsUnion = supports;
   }

   public boolean getSupportsUnionWithUnalignedOrdering() {
      return this._supportsUnionWithUnalignedOrdering;
   }

   public void setSupportsUnionWithUnalignedOrdering(boolean supports) {
      this._supportsUnionWithUnalignedOrdering = supports;
   }

   public int getBatchLimit() {
      return this._batchLimit;
   }

   public void setBatchLimit(int batchLimit) {
      this._batchLimit = batchLimit;
   }

   public int getBatchParameterLimit() {
      return this._batchParameterLimit;
   }

   public void setBatchParameterLimit(int batchLimit) {
      this._batchParameterLimit = batchLimit;
   }

   public boolean getSupportsUpdateCountsForBatch() {
      return this._supportsUpdateCountsForBatch;
   }

   public void setSupportsUpdateCountsForBatch(boolean supports) {
      this._supportsUpdateCountsForBatch = supports;
   }

   public boolean getSupportsTotalCountsForBatch() {
      return this._supportsTotalCountsForBatch;
   }

   public void setSupportsTotalCountsForBatch(boolean supports) {
      this._supportsTotalCountsForBatch = supports;
   }

   public SQLBuffer toSelectCount(Union union) {
      SQLBuffer buf = new SQLBuffer(this.dict);
      Select[] sels = union.getSelects();

      for(int i = 0; i < sels.length; ++i) {
         if (i > 0) {
            buf.append(" UNION ");
            if (union.isDistinct()) {
               buf.append("ALL ");
            }
         }

         buf.append(this.dict.toSelectCount(sels[i]));
      }

      return buf;
   }

   public SQLBuffer toSelect(Union union, boolean forUpdate, JDBCFetchConfiguration fetch) {
      SQLBuffer buf = new SQLBuffer(this.dict);
      Select[] sels = union.getSelects();

      for(int i = 0; i < sels.length; ++i) {
         if (i > 0) {
            buf.append(" UNION ");
            if (union.isDistinct()) {
               buf.append("ALL ");
            }
         }

         buf.append(this.dict.toSelect(sels[i], forUpdate, fetch));
      }

      String order = union.getOrdering();
      if (order != null) {
         buf.append(" ORDER BY ").append(order);
      }

      return buf;
   }

   public boolean canBatch(Column col) {
      return true;
   }

   public void setConfiguration(Configuration c) {
      this.conf = (JDBCConfiguration)c;
      this.dict = this.conf.getDBDictionaryInstance();
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }
}

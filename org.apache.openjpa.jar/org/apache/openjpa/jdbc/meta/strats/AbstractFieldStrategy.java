package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.FieldStrategy;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.SelectExecutor;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public abstract class AbstractFieldStrategy extends AbstractStrategy implements FieldStrategy {
   private static final Localizer _loc = Localizer.forPackage(AbstractFieldStrategy.class);
   protected FieldMapping field = null;

   protected void assertNotMappedBy() {
      if (this.field != null && this.field.getMappedBy() != null) {
         throw new MetaDataException(_loc.get("cant-mapped-by", this.field, this.getAlias()));
      }
   }

   public void setFieldMapping(FieldMapping owner) {
      this.field = owner;
   }

   public int supportsSelect(Select sel, int type, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) {
      return 0;
   }

   public void selectEagerJoin(Select sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
   }

   public void selectEagerParallel(SelectExecutor sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
   }

   public boolean isEagerSelectToMany() {
      return false;
   }

   public int select(Select sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
      return -1;
   }

   public Object loadEagerParallel(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Object res) throws SQLException {
      return res;
   }

   public void loadEagerJoin(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
   }

   public Object toDataStoreValue(Object val, JDBCStore store) {
      return val;
   }

   public Object toKeyDataStoreValue(Object val, JDBCStore store) {
      return val;
   }

   public void appendIsEmpty(SQLBuffer sql, Select sel, Joins joins) {
      sql.append("1 <> 1");
   }

   public void appendIsNotEmpty(SQLBuffer sql, Select sel, Joins joins) {
      sql.append("1 = 1");
   }

   public void appendIsNull(SQLBuffer sql, Select sel, Joins joins) {
      sql.append("1 <> 1");
   }

   public void appendIsNotNull(SQLBuffer sql, Select sel, Joins joins) {
      sql.append("1 <> 1");
   }

   public void appendSize(SQLBuffer sql, Select sel, Joins joins) {
      sql.append("1");
   }

   public Joins join(Joins joins, boolean forceOuter) {
      return joins;
   }

   public Joins joinKey(Joins joins, boolean forceOuter) {
      return joins;
   }

   public Joins joinRelation(Joins joins, boolean forceOuter, boolean traverse) {
      return joins;
   }

   public Joins joinKeyRelation(Joins joins, boolean forceOuter, boolean traverse) {
      return joins;
   }

   public Object loadProjection(JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return null;
   }

   public Object loadKeyProjection(JDBCStore store, JDBCFetchConfiguration fetch, Result res, Joins joins) throws SQLException {
      return null;
   }

   public boolean isVersionable() {
      return false;
   }

   public void where(OpenJPAStateManager sm, JDBCStore store, RowManager rm, Object prevValue) throws SQLException {
   }
}

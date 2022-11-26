package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.BitSet;
import java.util.Collection;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowImpl;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ArrayStateImage;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;

public class StateComparisonVersionStrategy extends AbstractVersionStrategy {
   public static final String ALIAS = "state-comparison";
   private static final Localizer _loc = Localizer.forPackage(StateComparisonVersionStrategy.class);

   public String getAlias() {
      return "state-comparison";
   }

   public void map(boolean adapt) {
      ClassMapping cls = this.vers.getClassMapping();
      if (cls.getJoinablePCSuperclassMapping() == null && cls.getEmbeddingMetaData() == null) {
         this.vers.getMappingInfo().assertNoSchemaComponents(this.vers, true);
      } else {
         throw new MetaDataException(_loc.get("not-base-vers", (Object)cls));
      }
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      FieldMapping[] fields = (FieldMapping[])((FieldMapping[])sm.getMetaData().getFields());
      Object[] state = ArrayStateImage.newImage(fields.length);
      BitSet loaded = ArrayStateImage.getLoaded(state);

      for(int i = 0; i < fields.length; ++i) {
         if (!fields[i].isPrimaryKey() && fields[i].isVersionable()) {
            loaded.set(i);
            state[i] = sm.fetch(fields[i].getIndex());
         }
      }

      sm.setNextVersion(state);
   }

   public void customInsert(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
      this.insert(sm, store, (RowManager)null);
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Object[] state = (Object[])((Object[])sm.getVersion());
      if (state != null) {
         BitSet loaded = ArrayStateImage.getLoaded(state);
         Object[] nextState = ArrayStateImage.clone(state);
         FieldMapping[] fields = (FieldMapping[])((FieldMapping[])sm.getMetaData().getFields());
         if (sm.isVersionCheckRequired()) {
            int i = 0;

            for(int max = loaded.length(); i < max; ++i) {
               if (loaded.get(i)) {
                  if (sm.getDirty().get(i) && !sm.getFlushed().get(i)) {
                     nextState[i] = sm.fetch(fields[i].getIndex());
                  }

                  Row row = rm.getRow(fields[i].getTable(), 0, sm, false);
                  if (row != null) {
                     fields[i].where(sm, store, rm, state[i]);
                     row.setFailedObject(sm.getManagedInstance());
                  }
               }
            }
         }

         sm.setNextVersion(nextState);
      }
   }

   public CustomUpdate customUpdate(OpenJPAStateManager sm, JDBCStore store, Table table, boolean record) throws SQLException {
      CustomUpdate custom = new CustomUpdate(table);
      Object[] state = (Object[])((Object[])sm.getVersion());
      if (state == null) {
         return custom;
      } else {
         BitSet loaded = ArrayStateImage.getLoaded(state);
         Object[] nextState = null;
         if (record) {
            nextState = ArrayStateImage.clone(state);
         }

         FieldMapping[] fields = (FieldMapping[])((FieldMapping[])sm.getMetaData().getFields());
         int i = 0;

         for(int max = loaded.length(); i < max; ++i) {
            if (loaded.get(i)) {
               if (record && sm.getDirty().get(i) && !sm.getFlushed().get(i)) {
                  nextState[i] = sm.fetch(fields[i].getIndex());
               }

               if (fields[i].getTable() == table) {
                  fields[i].where(sm, store, custom, state[i]);
               }
            }
         }

         if (record) {
            sm.setNextVersion(nextState);
         }

         return custom;
      }
   }

   public void afterLoad(OpenJPAStateManager sm, JDBCStore store) {
      FieldMapping[] fields = (FieldMapping[])((FieldMapping[])sm.getMetaData().getFields());
      Object[] state = (Object[])((Object[])sm.getVersion());
      if (state == null) {
         state = ArrayStateImage.newImage(fields.length);
      }

      BitSet loaded = ArrayStateImage.getLoaded(state);

      for(int i = 0; i < fields.length; ++i) {
         if (!fields[i].isPrimaryKey() && fields[i].isVersionable() && sm.getLoaded().get(fields[i].getIndex()) && !loaded.get(i) && !sm.getDirty().get(fields[i].getIndex())) {
            loaded.set(i);
            state[i] = sm.fetch(fields[i].getIndex());
         }
      }

      sm.setVersion(state);
   }

   public boolean checkVersion(OpenJPAStateManager sm, JDBCStore store, boolean updateVersion) throws SQLException {
      if (updateVersion) {
         sm.setVersion((Object)null);
      }

      return !updateVersion;
   }

   public int compareVersion(Object v1, Object v2) {
      return ArrayStateImage.sameVersion((Object[])((Object[])v1), (Object[])((Object[])v2)) ? 3 : 4;
   }

   public static class CustomUpdate extends RowImpl implements RowManager {
      private CustomUpdate(Table table) {
         this(table.getColumns());
      }

      private CustomUpdate(Column[] cols) {
         super((Column[])cols, 0);
      }

      public String getSQL() {
         Column[] cols = this.getTable().getColumns();
         StringBuffer buf = new StringBuffer();
         boolean hasWhere = false;

         for(int i = 0; i < cols.length; ++i) {
            Object val = this.getWhere(cols[i]);
            if (val != null) {
               if (hasWhere) {
                  buf.append(" AND ");
               }

               if (val == NULL) {
                  buf.append(cols[i]).append(" IS NULL");
               } else {
                  buf.append(cols[i]).append(" = ?");
               }

               hasWhere = true;
            }
         }

         return buf.toString();
      }

      protected RowImpl newInstance(Column[] cols, int action) {
         return new CustomUpdate(cols);
      }

      public boolean hasAutoAssignConstraints() {
         return false;
      }

      public Collection getInserts() {
         throw new InternalException();
      }

      public Collection getUpdates() {
         throw new InternalException();
      }

      public Collection getDeletes() {
         throw new InternalException();
      }

      public Collection getSecondaryUpdates() {
         throw new InternalException();
      }

      public Collection getSecondaryDeletes() {
         throw new InternalException();
      }

      public Collection getAllRowUpdates() {
         throw new InternalException();
      }

      public Collection getAllRowDeletes() {
         throw new InternalException();
      }

      public Row getRow(Table table, int action, OpenJPAStateManager sm, boolean create) {
         return table != this.getTable() ? null : this;
      }

      public Row getSecondaryRow(Table table, int action) {
         throw new InternalException();
      }

      public void flushSecondaryRow(Row row) {
      }

      public Row getAllRows(Table table, int action) {
         throw new InternalException();
      }

      public void flushAllRows(Row row) {
      }

      public void setObject(Column col, Object val) throws SQLException {
         throw new InternalException();
      }

      // $FF: synthetic method
      CustomUpdate(Table x0, Object x1) {
         this(x0);
      }
   }
}

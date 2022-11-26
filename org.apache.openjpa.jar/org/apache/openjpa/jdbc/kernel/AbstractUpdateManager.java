package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.Discriminator;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.Strategy;
import org.apache.openjpa.jdbc.meta.Version;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.OptimisticException;

public abstract class AbstractUpdateManager implements UpdateManager, Configurable {
   protected JDBCConfiguration conf = null;
   protected DBDictionary dict = null;

   public void setConfiguration(Configuration conf) {
      this.conf = (JDBCConfiguration)conf;
      this.dict = this.conf.getDBDictionaryInstance();
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public Collection flush(Collection states, JDBCStore store) {
      Connection conn = store.getConnection();

      Collection var5;
      try {
         PreparedStatementManager psMgr = this.newPreparedStatementManager(store, conn);
         var5 = this.flush(states, store, psMgr);
      } finally {
         try {
            conn.close();
         } catch (SQLException var12) {
         }

      }

      return var5;
   }

   private Collection flush(Collection states, JDBCStore store, PreparedStatementManager psMgr) {
      RowManager rowMgr = this.newRowManager();
      Collection customs = new LinkedList();
      Collection exceps = null;

      Iterator itr;
      for(itr = states.iterator(); itr.hasNext(); exceps = this.populateRowManager((OpenJPAStateManager)itr.next(), rowMgr, store, exceps, customs)) {
      }

      exceps = this.flush(rowMgr, psMgr, exceps);
      itr = customs.iterator();

      while(itr.hasNext()) {
         try {
            ((CustomMapping)itr.next()).execute(store);
         } catch (SQLException var9) {
            exceps = this.addException(exceps, SQLExceptions.getStore(var9, this.dict));
         } catch (OpenJPAException var10) {
            exceps = this.addException(exceps, var10);
         }
      }

      Collection psExceps = psMgr.getExceptions();
      if (exceps == null) {
         return psExceps;
      } else if (psExceps == null) {
         return exceps;
      } else {
         exceps.addAll(psExceps);
         return exceps;
      }
   }

   protected abstract RowManager newRowManager();

   protected abstract PreparedStatementManager newPreparedStatementManager(JDBCStore var1, Connection var2);

   protected abstract Collection flush(RowManager var1, PreparedStatementManager var2, Collection var3);

   protected Collection populateRowManager(OpenJPAStateManager sm, RowManager rowMgr, JDBCStore store, Collection exceps, Collection customs) {
      try {
         if (sm.getPCState() == PCState.PNEW && !sm.isFlushed()) {
            this.insert(sm, (ClassMapping)sm.getMetaData(), rowMgr, store, customs);
         } else if (sm.getPCState() != PCState.PNEWFLUSHEDDELETED && sm.getPCState() != PCState.PDELETED) {
            BitSet dirty;
            if ((dirty = ImplHelper.getUpdateFields(sm)) != null) {
               this.update(sm, dirty, (ClassMapping)sm.getMetaData(), rowMgr, store, customs);
            } else if (sm.isVersionUpdateRequired()) {
               this.updateIndicators(sm, (ClassMapping)sm.getMetaData(), rowMgr, store, customs, true);
            } else if (sm.isVersionCheckRequired() && !((ClassMapping)sm.getMetaData()).getVersion().checkVersion(sm, store, false)) {
               exceps = this.addException(exceps, new OptimisticException(sm.getManagedInstance()));
            }
         } else {
            this.delete(sm, (ClassMapping)sm.getMetaData(), rowMgr, store, customs);
         }
      } catch (SQLException var7) {
         exceps = this.addException(exceps, SQLExceptions.getStore(var7, this.dict));
      } catch (OpenJPAException var8) {
         exceps = this.addException(exceps, var8);
      }

      return exceps;
   }

   protected Collection addException(Collection exceps, Exception err) {
      if (exceps == null) {
         exceps = new LinkedList();
      }

      ((Collection)exceps).add(err);
      return (Collection)exceps;
   }

   protected void insert(OpenJPAStateManager sm, ClassMapping mapping, RowManager rowMgr, JDBCStore store, Collection customs) throws SQLException {
      Boolean custom = mapping.isCustomInsert(sm, store);
      if (!Boolean.FALSE.equals(custom)) {
         mapping.customInsert(sm, store);
      }

      if (!Boolean.TRUE.equals(custom)) {
         ClassMapping sup = mapping.getJoinablePCSuperclassMapping();
         if (sup != null) {
            this.insert(sm, sup, rowMgr, store, customs);
         }

         mapping.insert(sm, store, rowMgr);
         FieldMapping[] fields = mapping.getDefinedFieldMappings();
         BitSet dirty = sm.getDirty();

         for(int i = 0; i < fields.length; ++i) {
            if (dirty.get(fields[i].getIndex()) && !this.bufferCustomInsert(fields[i], sm, store, customs)) {
               fields[i].insert(sm, store, rowMgr);
            }
         }

         if (sup == null) {
            Version vers = mapping.getVersion();
            if (!this.bufferCustomInsert(vers, sm, store, customs)) {
               vers.insert(sm, store, rowMgr);
            }

            Discriminator dsc = mapping.getDiscriminator();
            if (!this.bufferCustomInsert(dsc, sm, store, customs)) {
               dsc.insert(sm, store, rowMgr);
            }
         }

      }
   }

   private boolean bufferCustomInsert(Strategy strat, OpenJPAStateManager sm, JDBCStore store, Collection customs) {
      Boolean custom = strat.isCustomInsert(sm, store);
      if (!Boolean.FALSE.equals(custom)) {
         customs.add(new CustomMapping(0, sm, strat));
      }

      return Boolean.TRUE.equals(custom);
   }

   protected void delete(OpenJPAStateManager sm, ClassMapping mapping, RowManager rowMgr, JDBCStore store, Collection customs) throws SQLException {
      Boolean custom = mapping.isCustomDelete(sm, store);
      if (!Boolean.FALSE.equals(custom)) {
         mapping.customDelete(sm, store);
      }

      if (!Boolean.TRUE.equals(custom)) {
         FieldMapping[] fields = mapping.getDefinedFieldMappings();

         for(int i = 0; i < fields.length; ++i) {
            if (!this.bufferCustomDelete(fields[i], sm, store, customs)) {
               fields[i].delete(sm, store, rowMgr);
            }
         }

         ClassMapping sup = mapping.getJoinablePCSuperclassMapping();
         if (sup == null) {
            Version vers = mapping.getVersion();
            if (!this.bufferCustomDelete(vers, sm, store, customs)) {
               vers.delete(sm, store, rowMgr);
            }

            Discriminator dsc = mapping.getDiscriminator();
            if (!this.bufferCustomDelete(dsc, sm, store, customs)) {
               dsc.delete(sm, store, rowMgr);
            }
         }

         mapping.delete(sm, store, rowMgr);
         if (sup != null) {
            this.delete(sm, sup, rowMgr, store, customs);
         }

      }
   }

   private boolean bufferCustomDelete(Strategy strat, OpenJPAStateManager sm, JDBCStore store, Collection customs) {
      Boolean custom = strat.isCustomDelete(sm, store);
      if (!Boolean.FALSE.equals(custom)) {
         customs.add(new CustomMapping(3, sm, strat));
      }

      return Boolean.TRUE.equals(custom);
   }

   protected void update(OpenJPAStateManager sm, BitSet dirty, ClassMapping mapping, RowManager rowMgr, JDBCStore store, Collection customs) throws SQLException {
      Boolean custom = mapping.isCustomUpdate(sm, store);
      if (!Boolean.FALSE.equals(custom)) {
         mapping.customUpdate(sm, store);
      }

      if (!Boolean.TRUE.equals(custom)) {
         FieldMapping[] fields = mapping.getDefinedFieldMappings();

         for(int i = 0; i < fields.length; ++i) {
            if (dirty.get(fields[i].getIndex()) && !this.bufferCustomUpdate(fields[i], sm, store, customs)) {
               fields[i].update(sm, store, rowMgr);
            }
         }

         ClassMapping sup = mapping.getJoinablePCSuperclassMapping();
         if (sup == null) {
            this.updateIndicators(sm, mapping, rowMgr, store, customs, false);
         } else {
            this.update(sm, dirty, sup, rowMgr, store, customs);
         }

         mapping.update(sm, store, rowMgr);
      }
   }

   protected void updateIndicators(OpenJPAStateManager sm, ClassMapping mapping, RowManager rowMgr, JDBCStore store, Collection customs, boolean versionUpdateOnly) throws SQLException {
      while(mapping.getJoinablePCSuperclassMapping() != null) {
         mapping = mapping.getJoinablePCSuperclassMapping();
      }

      Version vers = mapping.getVersion();
      if (!this.bufferCustomUpdate(vers, sm, store, customs)) {
         vers.update(sm, store, rowMgr);
      }

      if (versionUpdateOnly) {
         mapping.update(sm, store, rowMgr);
      } else {
         Discriminator dsc = mapping.getDiscriminator();
         if (!this.bufferCustomUpdate(dsc, sm, store, customs)) {
            dsc.update(sm, store, rowMgr);
         }
      }

   }

   private boolean bufferCustomUpdate(Strategy strat, OpenJPAStateManager sm, JDBCStore store, Collection customs) {
      Boolean custom = strat.isCustomUpdate(sm, store);
      if (!Boolean.FALSE.equals(custom)) {
         customs.add(new CustomMapping(1, sm, strat));
      }

      return Boolean.TRUE.equals(custom);
   }

   protected static class CustomMapping {
      public static final int INSERT = 0;
      public static final int UPDATE = 1;
      public static final int DELETE = 3;
      private final int _action;
      private final OpenJPAStateManager _sm;
      private final Strategy _strat;

      public CustomMapping(int action, OpenJPAStateManager sm, Strategy strat) {
         this._action = action;
         this._sm = sm;
         this._strat = strat;
      }

      public void execute(JDBCStore store) throws SQLException {
         switch (this._action) {
            case 0:
               this._strat.customInsert(this._sm, store);
               break;
            case 1:
               this._strat.customUpdate(this._sm, store);
            case 2:
            default:
               break;
            case 3:
               this._strat.customDelete(this._sm, store);
         }

      }
   }
}

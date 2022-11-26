package kodo.jdbc.kernel;

import com.solarmetric.graph.DepthFirstAnalysis;
import com.solarmetric.graph.Edge;
import com.solarmetric.graph.Graph;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import org.apache.openjpa.jdbc.kernel.PreparedStatementManager;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.PrimaryRow;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowImpl;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.RowManagerImpl;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.OpenJPAException;

public class ConstraintUpdateManager extends AutoOrderUpdateManager {
   private static final Localizer _loc = Localizer.forPackage(ConstraintUpdateManager.class);

   public boolean orderDirty() {
      return true;
   }

   protected RowManager newRowManager() {
      return new RowManagerImpl(false);
   }

   protected Collection flush(RowManager rowMgr, PreparedStatementManager psMgr, Collection exceps) {
      RowManagerImpl rmimpl = (RowManagerImpl)rowMgr;
      this.flush(rmimpl.getAllRowDeletes(), psMgr);
      this.flush(rmimpl.getSecondaryDeletes(), psMgr);
      this.flush(rmimpl.getAllRowUpdates(), psMgr);
      Collection inserts = rmimpl.getInserts();
      Collection updates = rmimpl.getUpdates();
      Collection deletes = rmimpl.getDeletes();
      Graph[] graphs = new Graph[2];
      this.analyzeForeignKeys(inserts, updates, deletes, rmimpl, graphs);
      boolean autoAssign = rmimpl.hasAutoAssignConstraints();

      try {
         this.flushGraph(graphs[0], psMgr, autoAssign);
      } catch (SQLException var13) {
         exceps = this.addException(exceps, SQLExceptions.getStore(var13, this.dict));
      } catch (OpenJPAException var14) {
         exceps = this.addException(exceps, var14);
      }

      this.flush(inserts, psMgr);
      this.flush(updates, psMgr);

      try {
         this.flushGraph(graphs[1], psMgr, autoAssign);
      } catch (SQLException var11) {
         exceps = this.addException(exceps, SQLExceptions.getStore(var11, this.dict));
      } catch (OpenJPAException var12) {
         exceps = this.addException(exceps, var12);
      }

      this.flush(deletes, psMgr);
      this.flush(rmimpl.getSecondaryUpdates(), psMgr);
      psMgr.flush();
      return exceps;
   }

   private void analyzeForeignKeys(Collection inserts, Collection updates, Collection deletes, RowManagerImpl rowMgr, Graph[] graphs) {
      Map insertMap = null;
      if (!deletes.isEmpty() && !inserts.isEmpty()) {
         insertMap = new HashMap((int)((double)inserts.size() * 1.33 + 1.0));
         Iterator itr = inserts.iterator();

         while(itr.hasNext()) {
            OpenJPAStateManager sm = ((Row)itr.next()).getPrimaryKey();
            if (sm != null && sm.getObjectId() != null) {
               insertMap.put(sm.getObjectId(), sm);
            }
         }
      }

      boolean ignoreUpdates = true;
      Iterator itr = deletes.iterator();

      while(true) {
         PrimaryRow row;
         do {
            if (!itr.hasNext()) {
               if (ignoreUpdates) {
                  graphs[0] = this.analyzeAgainstInserts(inserts, rowMgr, graphs[0]);
               } else {
                  graphs[1] = this.analyzeAgainstInserts(updates, rowMgr, graphs[1]);
                  graphs[1] = this.analyzeAgainstInserts(inserts, rowMgr, graphs[1]);
               }

               return;
            }

            row = (PrimaryRow)itr.next();
         } while(!row.isValid());

         Row row2 = this.getInsertRow(insertMap, rowMgr, row);
         if (row2 != null) {
            ignoreUpdates = false;
            graphs[1] = this.addEdge(graphs[1], row, (PrimaryRow)row2, (Object)null);
         }

         ForeignKey[] fks = row.getTable().getForeignKeys();

         for(int j = 0; j < fks.length; ++j) {
            OpenJPAStateManager fkVal = row.getForeignKeySet(fks[j]);
            if (fkVal == null) {
               fkVal = row.getForeignKeyWhere(fks[j]);
            }

            if (fkVal != null) {
               row2 = rowMgr.getRow(fks[j].getPrimaryKeyTable(), 2, fkVal, false);
               if (row2 != null && row2.isValid() && row2 != row) {
                  graphs[1] = this.addEdge(graphs[1], row, (PrimaryRow)row2, fks[j]);
               }
            }
         }
      }
   }

   private Row getInsertRow(Map insertMap, RowManagerImpl rowMgr, Row row) {
      if (insertMap == null) {
         return null;
      } else {
         OpenJPAStateManager sm = row.getPrimaryKey();
         if (sm == null) {
            return null;
         } else {
            Object oid = sm.getObjectId();
            OpenJPAStateManager nsm = (OpenJPAStateManager)insertMap.get(oid);
            if (nsm == null) {
               return null;
            } else {
               row = rowMgr.getRow(row.getTable(), 1, nsm, false);
               return row != null && !row.isValid() ? null : row;
            }
         }
      }
   }

   private Graph analyzeAgainstInserts(Collection rows, RowManagerImpl rowMgr, Graph graph) {
      Iterator itr = rows.iterator();

      while(true) {
         PrimaryRow row;
         do {
            if (!itr.hasNext()) {
               return graph;
            }

            row = (PrimaryRow)itr.next();
         } while(!row.isValid());

         ForeignKey[] fks = row.getTable().getForeignKeys();

         Row row2;
         int j;
         for(j = 0; j < fks.length; ++j) {
            if (row.getForeignKeySet(fks[j]) != null) {
               row2 = rowMgr.getRow(fks[j].getPrimaryKeyTable(), 1, row.getForeignKeySet(fks[j]), false);
               if (row2 != null && row2.isValid() && (row2 != row || fks[j].isDeferred() || fks[j].isLogical())) {
                  graph = this.addEdge(graph, (PrimaryRow)row2, row, fks[j]);
               }
            }
         }

         Column[] cols = row.getTable().getRelationIdColumns();

         for(j = 0; j < cols.length; ++j) {
            OpenJPAStateManager sm = row.getRelationIdSet(cols[j]);
            if (sm != null) {
               row2 = rowMgr.getRow(getBaseTable(sm), 1, sm, false);
               if (row2 != null && row2.isValid()) {
                  graph = this.addEdge(graph, (PrimaryRow)row2, row, cols[j]);
               }
            }
         }
      }
   }

   private static Table getBaseTable(OpenJPAStateManager sm) {
      ClassMapping cls;
      for(cls = (ClassMapping)sm.getMetaData(); cls.getJoinablePCSuperclassMapping() != null; cls = cls.getJoinablePCSuperclassMapping()) {
      }

      return cls.getTable();
   }

   private Graph addEdge(Graph graph, PrimaryRow row1, PrimaryRow row2, Object fk) {
      if (graph == null) {
         graph = new Graph();
      }

      row1.setDependent(true);
      row2.setDependent(true);
      graph.addNode(row1);
      graph.addNode(row2);
      Edge edge = new Edge(row1, row2, true);
      edge.setUserObject(fk);
      graph.addEdge(edge);
      return graph;
   }

   private void flushGraph(Graph graph, PreparedStatementManager psMgr, boolean autoAssign) throws SQLException {
      if (graph != null) {
         Comparator comp = null;
         if (!autoAssign && this.batch && this.maxBatch) {
            if (this.sqlComparator == null) {
               this.sqlComparator = new AutoOrderUpdateManager.SQLComparator(this.dict);
            }

            comp = this.sqlComparator;
         }

         DepthFirstAnalysis dfa = new DepthFirstAnalysis(graph);
         Collection nodes = dfa.getSortedNodes(comp);
         Collection backs = dfa.getEdges(2);
         Collection insertUpdates = null;
         Collection deleteUpdates = null;
         Iterator itr = backs.iterator();

         while(itr.hasNext()) {
            Edge edge = (Edge)itr.next();
            if (edge.getUserObject() == null) {
               throw new InternalException(_loc.get("del-ins-cycle"));
            }

            PrimaryRow row = (PrimaryRow)edge.getTo();
            PrimaryRow update;
            ForeignKey fk;
            if (row.getAction() == 2) {
               row = (PrimaryRow)edge.getFrom();
               update = new PrimaryRow(row.getTable(), 0, (OpenJPAStateManager)null);
               row.copyInto(update, true);
               if (edge.getUserObject() instanceof ForeignKey) {
                  fk = (ForeignKey)edge.getUserObject();
                  update.setForeignKey(fk, row.getForeignKeyIO(fk), (OpenJPAStateManager)null);
               } else {
                  update.setNull((Column)edge.getUserObject());
               }

               if (deleteUpdates == null) {
                  deleteUpdates = new LinkedList();
               }

               deleteUpdates.add(update);
            } else {
               update = new PrimaryRow(row.getTable(), 0, (OpenJPAStateManager)null);
               if (row.getAction() == 1) {
                  if (row.getPrimaryKey() == null) {
                     throw new InternalException(_loc.get("ref-cycle"));
                  }

                  update.wherePrimaryKey(row.getPrimaryKey());
               } else {
                  row.copyInto(update, true);
               }

               if (edge.getUserObject() instanceof ForeignKey) {
                  fk = (ForeignKey)edge.getUserObject();
                  update.setForeignKey(fk, row.getForeignKeyIO(fk), row.getForeignKeySet(fk));
                  row.clearForeignKey(fk);
               } else {
                  Column col = (Column)edge.getUserObject();
                  update.setRelationId(col, row.getRelationIdSet(col), row.getRelationIdCallback(col));
                  row.clearRelationId(col);
               }

               if (insertUpdates == null) {
                  insertUpdates = new LinkedList();
               }

               insertUpdates.add(update);
            }
         }

         if (deleteUpdates != null) {
            this.flush(deleteUpdates, psMgr);
         }

         itr = nodes.iterator();

         while(itr.hasNext()) {
            psMgr.flush((RowImpl)itr.next());
         }

         if (insertUpdates != null) {
            this.flush(insertUpdates, psMgr);
         }

      }
   }
}

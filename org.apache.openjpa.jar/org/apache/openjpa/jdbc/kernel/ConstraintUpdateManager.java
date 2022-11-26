package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import org.apache.openjpa.lib.graph.DepthFirstAnalysis;
import org.apache.openjpa.lib.graph.Edge;
import org.apache.openjpa.lib.graph.Graph;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UserException;

public class ConstraintUpdateManager extends AbstractUpdateManager {
   private static final Localizer _loc = Localizer.forPackage(ConstraintUpdateManager.class);

   public boolean orderDirty() {
      return true;
   }

   protected PreparedStatementManager newPreparedStatementManager(JDBCStore store, Connection conn) {
      return new PreparedStatementManagerImpl(store, conn);
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
            graphs[1] = this.addEdge(graphs[1], (PrimaryRow)row2, row, (Object)null);
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
                  graphs[1] = this.addEdge(graphs[1], (PrimaryRow)row2, row, fks[j]);
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
                  graph = this.addEdge(graph, row, (PrimaryRow)row2, fks[j]);
               }
            }
         }

         Column[] cols = row.getTable().getRelationIdColumns();

         for(j = 0; j < cols.length; ++j) {
            OpenJPAStateManager sm = row.getRelationIdSet(cols[j]);
            if (sm != null) {
               row2 = rowMgr.getRow(getBaseTable(sm), 1, sm, false);
               if (row2 != null && row2.isValid()) {
                  graph = this.addEdge(graph, row, (PrimaryRow)row2, cols[j]);
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

   protected void flushGraph(Graph graph, PreparedStatementManager psMgr, boolean autoAssign) throws SQLException {
      if (graph != null) {
         DepthFirstAnalysis dfa = this.newDepthFirstAnalysis(graph, autoAssign);
         Collection insertUpdates = new LinkedList();
         Collection deleteUpdates = new LinkedList();
         boolean recalculate = this.resolveCycles(graph, dfa.getEdges(2), deleteUpdates, insertUpdates);
         recalculate |= this.resolveCycles(graph, dfa.getEdges(3), deleteUpdates, insertUpdates);
         if (recalculate) {
            dfa = this.recalculateDepthFirstAnalysis(graph, autoAssign);
         }

         this.flush(deleteUpdates, psMgr);
         Collection nodes = dfa.getSortedNodes();
         Iterator itr = nodes.iterator();

         while(itr.hasNext()) {
            psMgr.flush((RowImpl)itr.next());
         }

         this.flush(insertUpdates, psMgr);
      }
   }

   private void addDeleteUpdate(Edge edge, Collection deleteUpdates) throws SQLException {
      PrimaryRow row = (PrimaryRow)edge.getTo();
      RowImpl update = new PrimaryRow(row.getTable(), 0, (OpenJPAStateManager)null);
      row.copyInto(update, true);
      if (edge.getUserObject() instanceof ForeignKey) {
         ForeignKey fk = (ForeignKey)edge.getUserObject();
         update.setForeignKey(fk, row.getForeignKeyIO(fk), (OpenJPAStateManager)null);
      } else {
         update.setNull((Column)edge.getUserObject());
      }

      deleteUpdates.add(update);
   }

   private void addInsertUpdate(PrimaryRow row, Edge edge, Collection insertUpdates) throws SQLException {
      RowImpl update = new PrimaryRow(row.getTable(), 0, (OpenJPAStateManager)null);
      if (row.getAction() == 1) {
         if (row.getPrimaryKey() == null) {
            throw new InternalException(_loc.get("ref-cycle"));
         }

         update.wherePrimaryKey(row.getPrimaryKey());
      } else {
         row.copyInto(update, true);
      }

      if (edge.getUserObject() instanceof ForeignKey) {
         ForeignKey fk = (ForeignKey)edge.getUserObject();
         update.setForeignKey(fk, row.getForeignKeyIO(fk), row.getForeignKeySet(fk));
         row.clearForeignKey(fk);
      } else {
         Column col = (Column)edge.getUserObject();
         update.setRelationId(col, row.getRelationIdSet(col), row.getRelationIdCallback(col));
         row.clearRelationId(col);
      }

      insertUpdates.add(update);
   }

   private Edge findBreakableLink(List cycle) {
      Edge breakableLink = null;
      Iterator iter = cycle.iterator();

      while(iter.hasNext()) {
         Edge edge = (Edge)iter.next();
         Object userObject = edge.getUserObject();
         if (userObject instanceof ForeignKey) {
            if (!((ForeignKey)userObject).hasNotNullColumns()) {
               breakableLink = edge;
               break;
            }
         } else if (userObject instanceof Column && !((Column)userObject).isNotNull()) {
            breakableLink = edge;
            break;
         }
      }

      return breakableLink;
   }

   private DepthFirstAnalysis recalculateDepthFirstAnalysis(Graph graph, boolean autoAssign) {
      graph.clearTraversal();
      DepthFirstAnalysis dfa = this.newDepthFirstAnalysis(graph, autoAssign);

      assert dfa.hasNoCycles() : _loc.get("graph-not-cycle-free");

      return dfa;
   }

   private boolean resolveCycles(Graph graph, Collection edges, Collection deleteUpdates, Collection insertUpdates) throws SQLException {
      boolean recalculate = false;
      Iterator itr = edges.iterator();

      while(itr.hasNext()) {
         Edge edge = (Edge)itr.next();
         List cycle = edge.getCycle();
         if (cycle != null) {
            Edge breakableLink = this.findBreakableLink(cycle);
            if (breakableLink == null) {
               throw new UserException(_loc.get("no-nullable-fk"));
            }

            if (edge != breakableLink) {
               recalculate = true;
            }

            if (!breakableLink.isRemovedFromGraph()) {
               PrimaryRow row = (PrimaryRow)breakableLink.getFrom();
               if (row.getAction() == 2) {
                  this.addDeleteUpdate(breakableLink, deleteUpdates);
               } else {
                  this.addInsertUpdate(row, breakableLink, insertUpdates);
               }

               graph.removeEdge(breakableLink);
            }
         }
      }

      return recalculate;
   }

   protected DepthFirstAnalysis newDepthFirstAnalysis(Graph graph, boolean autoAssign) {
      return new DepthFirstAnalysis(graph);
   }

   protected void flush(Collection rows, PreparedStatementManager psMgr) {
      if (rows.size() != 0) {
         Iterator itr = rows.iterator();

         while(itr.hasNext()) {
            RowImpl row = (RowImpl)itr.next();
            if (row.isValid() && !row.isDependent()) {
               psMgr.flush(row);
            }
         }

      }
   }
}

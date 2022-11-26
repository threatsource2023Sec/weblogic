package com.ziclix.python.sql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.python.core.Py;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyTuple;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;

public abstract class Fetch implements Traverseproc {
   protected int rowcount = -1;
   protected int rownumber = -1;
   private DataHandler datahandler;
   protected PyObject description;
   private List listeners;

   protected Fetch(DataHandler datahandler) {
      this.description = Py.None;
      this.datahandler = datahandler;
      this.listeners = new ArrayList(3);
   }

   public static Fetch newFetch(DataHandler datahandler, boolean dynamic) {
      return (Fetch)(dynamic ? new DynamicFetch(datahandler) : new StaticFetch(datahandler));
   }

   public int getRowCount() {
      return this.rowcount;
   }

   public PyObject getDescription() {
      return this.description;
   }

   public abstract void add(ResultSet var1);

   public abstract void add(ResultSet var1, Set var2);

   public abstract void add(CallableStatement var1, Procedure var2, PyObject var3);

   public PyObject fetchone() {
      PyObject sequence = this.fetchmany(1);
      return sequence.__len__() == 1 ? sequence.__getitem__(0) : Py.None;
   }

   public abstract PyObject fetchall();

   public abstract PyObject fetchmany(int var1);

   public abstract PyObject nextset();

   public abstract void scroll(int var1, String var2);

   public void close() throws SQLException {
      this.listeners.clear();
   }

   protected PyObject createDescription(ResultSetMetaData meta) throws SQLException {
      PyObject metadata = new PyList();

      for(int i = 1; i <= meta.getColumnCount(); ++i) {
         PyObject[] a = new PyObject[]{Py.newUnicode(meta.getColumnLabel(i)), Py.newInteger(meta.getColumnType(i)), Py.newInteger(meta.getColumnDisplaySize(i)), Py.None, null, null, null};
         switch (meta.getColumnType(i)) {
            case -7:
            case -6:
            case -5:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
               a[4] = Py.newInteger(meta.getPrecision(i));
               a[5] = Py.newInteger(meta.getScale(i));
               break;
            case -4:
            case -3:
            case -2:
            case -1:
            case 0:
            case 1:
            case 7:
            default:
               a[4] = Py.None;
               a[5] = Py.None;
         }

         a[6] = Py.newInteger(meta.isNullable(i));
         ((PyList)metadata).append(new PyTuple(a));
      }

      return metadata;
   }

   protected PyObject createDescription(Procedure procedure) throws SQLException {
      PyObject metadata = new PyList();
      int i = 0;
      int len = procedure.columns.__len__();

      while(i < len) {
         PyObject column = procedure.columns.__getitem__(i);
         int colType = column.__getitem__(4).asInt();
         switch (colType) {
            case 5:
               PyObject[] a = new PyObject[]{column.__getitem__(3), column.__getitem__(5), Py.newInteger(-1), column.__getitem__(8), null, null, null};
               switch (a[1].asInt()) {
                  case -7:
                  case -5:
                  case 3:
                  case 4:
                  case 5:
                  case 6:
                  case 8:
                     a[4] = column.__getitem__(7);
                     a[5] = column.__getitem__(9);
                     break;
                  case -6:
                  case -4:
                  case -3:
                  case -2:
                  case -1:
                  case 0:
                  case 1:
                  case 2:
                  case 7:
                  default:
                     a[4] = Py.None;
                     a[5] = Py.None;
               }

               int nullable = column.__getitem__(11).asInt();
               a[6] = nullable == 1 ? Py.One : Py.Zero;
               ((PyList)metadata).append(new PyTuple(a));
            default:
               ++i;
         }
      }

      return metadata;
   }

   protected PyObject createResults(CallableStatement callableStatement, Procedure procedure, PyObject params) throws SQLException {
      PyList results = new PyList();
      int i = 0;
      int j = 0;

      for(int len = procedure.columns.__len__(); i < len; ++i) {
         PyObject obj = Py.None;
         PyObject column = procedure.columns.__getitem__(i);
         int colType = column.__getitem__(4).asInt();
         int dataType = column.__getitem__(5).asInt();
         switch (colType) {
            case 1:
               ++j;
               break;
            case 2:
            case 4:
               obj = this.datahandler.getPyObject(callableStatement, i + 1, dataType);
               params.__setitem__(j++, obj);
            case 3:
            default:
               break;
            case 5:
               obj = this.datahandler.getPyObject(callableStatement, i + 1, dataType);
               Object rs = obj.__tojava__(ResultSet.class);
               if (rs == Py.NoConversion) {
                  results.append(obj);
               } else {
                  this.add((ResultSet)rs);
               }
         }
      }

      if (results.__len__() == 0) {
         return results;
      } else {
         PyList ret = new PyList();
         ret.append(PyTuple.fromIterable(results));
         return ret;
      }
   }

   protected PyList createResults(ResultSet set, Set skipCols, PyObject metaData) throws SQLException {
      PyList res = new PyList();

      while(set.next()) {
         PyObject tuple = this.createResult(set, skipCols, metaData);
         res.append(tuple);
      }

      return res;
   }

   protected PyTuple createResult(ResultSet set, Set skipCols, PyObject metaData) throws SQLException {
      int descriptionLength = metaData.__len__();
      PyObject[] row = new PyObject[descriptionLength];

      for(int i = 0; i < descriptionLength; ++i) {
         if (skipCols != null && skipCols.contains(i + 1)) {
            row[i] = Py.None;
         } else {
            int type = ((PyInteger)metaData.__getitem__(i).__getitem__(1)).getValue();
            row[i] = this.datahandler.getPyObject(set, i + 1, type);
         }
      }

      SQLWarning warning = set.getWarnings();
      if (warning != null) {
         this.fireWarning(warning);
      }

      return new PyTuple(row);
   }

   protected void fireWarning(SQLWarning warning) {
      WarningEvent event = new WarningEvent(this, warning);
      Iterator var3 = this.listeners.iterator();

      while(var3.hasNext()) {
         WarningListener listener = (WarningListener)var3.next();

         try {
            listener.warning(event);
         } catch (Throwable var6) {
         }
      }

   }

   public void addWarningListener(WarningListener listener) {
      this.listeners.add(listener);
   }

   public boolean removeWarningListener(WarningListener listener) {
      return this.listeners.remove(listener);
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = visit.visit(this.description, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         if (this.listeners != null) {
            Iterator var4 = this.listeners.iterator();

            while(var4.hasNext()) {
               WarningListener obj = (WarningListener)var4.next();
               if (obj != null) {
                  if (obj instanceof PyObject) {
                     retVal = visit.visit((PyObject)obj, arg);
                     if (retVal != 0) {
                        return retVal;
                     }
                  } else if (obj instanceof Traverseproc) {
                     retVal = ((Traverseproc)obj).traverse(visit, arg);
                     if (retVal != 0) {
                        return retVal;
                     }
                  }
               }
            }
         }

         return 0;
      }
   }

   public boolean refersDirectlyTo(PyObject ob) throws UnsupportedOperationException {
      if (ob == null) {
         return false;
      } else if (ob == this.description) {
         return true;
      } else {
         throw new UnsupportedOperationException();
      }
   }
}

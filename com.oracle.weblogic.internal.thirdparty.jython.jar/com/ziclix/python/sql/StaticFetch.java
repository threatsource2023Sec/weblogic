package com.ziclix.python.sql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.Visitproc;

class StaticFetch extends Fetch {
   protected List results = new LinkedList();
   protected List descriptions = new LinkedList();

   public StaticFetch(DataHandler datahandler) {
      super(datahandler);
   }

   public void add(ResultSet resultSet) {
      this.add(resultSet, (Set)null);
   }

   public void add(ResultSet resultSet, Set skipCols) {
      try {
         if (resultSet != null && resultSet.getMetaData() != null) {
            PyObject metadata = this.createDescription(resultSet.getMetaData());
            PyObject result = this.createResults(resultSet, skipCols, metadata);
            this.results.add(result);
            this.descriptions.add(metadata);
            this.rowcount = ((PyObject)this.results.get(0)).__len__();
            this.description = (PyObject)this.descriptions.get(0);
            this.rownumber = 0;
         }
      } catch (PyException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw zxJDBC.makeException(var14);
      } finally {
         try {
            resultSet.close();
         } catch (Throwable var12) {
         }

      }

   }

   public void add(CallableStatement callableStatement, Procedure procedure, PyObject params) {
      try {
         PyObject result = this.createResults(callableStatement, procedure, params);
         if (result.__len__() > 0) {
            this.results.add(result);
            this.descriptions.add(this.createDescription(procedure));
            this.rowcount = ((PyObject)this.results.get(0)).__len__();
            this.description = (PyObject)this.descriptions.get(0);
            this.rownumber = 0;
         }

      } catch (PyException var5) {
         throw var5;
      } catch (Throwable var6) {
         throw zxJDBC.makeException(var6);
      }
   }

   public PyObject fetchall() {
      return this.fetchmany(this.rowcount);
   }

   public PyObject fetchmany(int size) {
      if (this.results != null && this.results.size() != 0) {
         PyObject res = new PyList();
         PyObject current = (PyObject)this.results.get(0);
         if (size <= 0) {
            size = this.rowcount;
         }

         if (this.rownumber < this.rowcount) {
            res = current.__getslice__(Py.newInteger(this.rownumber), Py.newInteger(this.rownumber + size), Py.One);
            this.rownumber += size;
         }

         return (PyObject)res;
      } else {
         throw zxJDBC.makeException(zxJDBC.DatabaseError, "no results");
      }
   }

   public void scroll(int value, String mode) {
      int pos;
      if ("relative".equals(mode)) {
         pos = this.rownumber + value;
      } else {
         if (!"absolute".equals(mode)) {
            throw zxJDBC.makeException(zxJDBC.ProgrammingError, "invalid cursor scroll mode [" + mode + "]");
         }

         pos = value;
      }

      if (pos >= 0 && pos < this.rowcount) {
         this.rownumber = pos;
      } else {
         throw zxJDBC.makeException(Py.IndexError, "cursor index [" + pos + "] out of range");
      }
   }

   public PyObject nextset() {
      PyObject next = Py.None;
      if (this.results != null && this.results.size() > 1) {
         this.results.remove(0);
         this.descriptions.remove(0);
         next = (PyObject)this.results.get(0);
         this.description = (PyObject)this.descriptions.get(0);
         this.rowcount = next.__len__();
         this.rownumber = 0;
      }

      return (PyObject)(next == Py.None ? Py.None : Py.One);
   }

   public void close() throws SQLException {
      super.close();
      this.rownumber = -1;
      this.results.clear();
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = super.traverse(visit, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         Iterator var4;
         PyObject obj;
         if (this.results != null) {
            var4 = this.results.iterator();

            while(var4.hasNext()) {
               obj = (PyObject)var4.next();
               if (obj != null) {
                  retVal = visit.visit(obj, arg);
                  if (retVal != 0) {
                     return retVal;
                  }
               }
            }
         }

         if (this.descriptions != null) {
            var4 = this.descriptions.iterator();

            while(var4.hasNext()) {
               obj = (PyObject)var4.next();
               if (obj != null) {
                  retVal = visit.visit(obj, arg);
                  if (retVal != 0) {
                     return retVal;
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
      } else if (this.results != null && this.results.contains(ob)) {
         return true;
      } else {
         return this.descriptions != null && this.descriptions.contains(ob) ? true : super.refersDirectlyTo(ob);
      }
   }
}

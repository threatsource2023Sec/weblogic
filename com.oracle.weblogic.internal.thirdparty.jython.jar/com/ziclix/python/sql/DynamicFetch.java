package com.ziclix.python.sql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyTuple;

class DynamicFetch extends Fetch {
   protected Set skipCols;
   protected ResultSet resultSet;

   public DynamicFetch(DataHandler datahandler) {
      super(datahandler);
   }

   public void add(ResultSet resultSet) {
      this.add(resultSet, (Set)null);
   }

   public void add(ResultSet resultSet, Set skipCols) {
      if (this.resultSet != null) {
         throw zxJDBC.makeException(zxJDBC.getString("onlyOneResultSet"));
      } else {
         try {
            if (resultSet != null && resultSet.getMetaData() != null) {
               if (this.description == Py.None) {
                  this.description = this.createDescription(resultSet.getMetaData());
               }

               this.resultSet = resultSet;
               this.skipCols = skipCols;
               this.rowcount = 0;
               this.rownumber = 0;
            }

         } catch (PyException var4) {
            throw var4;
         } catch (Throwable var5) {
            throw zxJDBC.makeException(var5);
         }
      }
   }

   public void add(CallableStatement callableStatement, Procedure procedure, PyObject params) {
      throw zxJDBC.makeException(zxJDBC.NotSupportedError, zxJDBC.getString("nocallprocsupport"));
   }

   public PyObject fetchall() {
      return this.fetch(0, true);
   }

   public PyObject fetchmany(int size) {
      return this.fetch(size, false);
   }

   private PyObject fetch(int size, boolean all) {
      PyList res = new PyList();
      if (this.resultSet == null) {
         throw zxJDBC.makeException(zxJDBC.DatabaseError, "no results");
      } else {
         try {
            for(all = size < 0 ? true : all; (size-- > 0 || all) && this.resultSet.next(); this.rownumber = this.resultSet.getRow()) {
               PyTuple tuple = this.createResult(this.resultSet, this.skipCols, this.description);
               res.append(tuple);
               ++this.rowcount;
            }

            return res;
         } catch (AbstractMethodError var5) {
            throw zxJDBC.makeException(zxJDBC.NotSupportedError, zxJDBC.getString("nodynamiccursors"));
         } catch (PyException var6) {
            throw var6;
         } catch (Throwable var7) {
            throw zxJDBC.makeException(var7);
         }
      }
   }

   public PyObject nextset() {
      return Py.None;
   }

   public void scroll(int value, String mode) {
      try {
         int type = this.resultSet.getType();
         if (type == 1003 && value <= 0) {
            String msg = "dynamic result set of type [" + type + "] does not support scrolling";
            throw zxJDBC.makeException(zxJDBC.NotSupportedError, msg);
         } else {
            if ("relative".equals(mode)) {
               if (value < 0) {
                  value = Math.abs(this.rownumber + value);
               } else if (value > 0) {
                  value = this.rownumber + value + 1;
               }
            } else {
               if (!"absolute".equals(mode)) {
                  throw zxJDBC.makeException(zxJDBC.ProgrammingError, "invalid cursor scroll mode [" + mode + "]");
               }

               if (value < 0) {
                  throw zxJDBC.makeException(Py.IndexError, "cursor index [" + value + "] out of range");
               }
            }

            if (value == 0) {
               this.resultSet.beforeFirst();
            } else if (!this.resultSet.absolute(value)) {
               throw zxJDBC.makeException(Py.IndexError, "cursor index [" + value + "] out of range");
            }

            this.rownumber = this.resultSet.getRow();
         }
      } catch (AbstractMethodError var5) {
         throw zxJDBC.makeException(zxJDBC.NotSupportedError, zxJDBC.getString("nodynamiccursors"));
      } catch (SQLException var6) {
         throw zxJDBC.makeException((Throwable)var6);
      } catch (Throwable var7) {
         throw zxJDBC.makeException(var7);
      }
   }

   public void close() throws SQLException {
      super.close();
      if (this.resultSet != null) {
         this.rownumber = -1;

         try {
            this.resultSet.close();
         } finally {
            this.resultSet = null;
         }

      }
   }
}

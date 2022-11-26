package com.ziclix.python.sql.pipe.db;

import com.ziclix.python.sql.PyConnection;
import com.ziclix.python.sql.pipe.Source;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyTuple;

public class DBSource extends BaseDB implements Source {
   protected String sql;
   protected boolean sentHeader;
   protected PyObject params;
   protected PyObject include;

   public DBSource(PyConnection connection, Class dataHandler, String tableName, String where, PyObject include, PyObject params) {
      super(connection, dataHandler, tableName);
      this.params = params;
      this.include = include;
      this.sentHeader = false;
      this.sql = this.createSql(where);
   }

   protected String createSql(String where) {
      StringBuffer sb = new StringBuffer("select ");
      if (this.include != Py.None && this.include.__len__() != 0) {
         for(int i = 1; i < this.include.__len__(); ++i) {
            sb.append(this.include.__getitem__(i)).append(",");
         }

         sb.append(this.include.__getitem__(this.include.__len__() - 1));
      } else {
         sb.append("*");
      }

      sb.append(" from ").append(this.tableName);
      sb.append(" where ").append(where == null ? "(1=1)" : where);
      String sql = sb.toString();
      return sql;
   }

   public PyObject next() {
      if (this.sentHeader) {
         return this.cursor.fetchone();
      } else {
         this.cursor.execute(Py.newString(this.sql), this.params, Py.None, Py.None);
         PyObject description = this.cursor.__findattr__("description");
         if (description != Py.None && description.__len__() != 0) {
            int len = description.__len__();
            PyObject[] columns = new PyObject[len];

            for(int i = 0; i < len; ++i) {
               PyObject[] colInfo = new PyObject[]{description.__getitem__(i).__getitem__(0), description.__getitem__(i).__getitem__(1)};
               columns[i] = new PyTuple(colInfo);
            }

            PyObject row = new PyTuple(columns);
            Py.writeDebug("db-source", row.toString());
            this.sentHeader = true;
            return row;
         } else {
            return Py.None;
         }
      }
   }

   public void start() {
   }

   public void end() {
      if (this.cursor != null) {
         this.cursor.close();
      }

   }
}

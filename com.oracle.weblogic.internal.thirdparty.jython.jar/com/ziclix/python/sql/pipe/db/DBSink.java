package com.ziclix.python.sql.pipe.db;

import com.ziclix.python.sql.PyConnection;
import com.ziclix.python.sql.zxJDBC;
import com.ziclix.python.sql.pipe.Sink;
import java.util.HashSet;
import java.util.Set;
import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;

public class DBSink extends BaseDB implements Sink {
   protected PyObject sql;
   protected Set exclude;
   protected PyList rows;
   protected int batchsize;
   protected PyObject bindings;
   protected PyDictionary indexedBindings;

   public DBSink(PyConnection connection, Class dataHandler, String tableName, PyObject exclude, PyObject bindings, int batchsize) {
      super(connection, dataHandler, tableName);
      this.sql = Py.None;
      this.rows = new PyList();
      this.bindings = bindings;
      this.batchsize = batchsize;
      this.exclude = new HashSet();
      this.indexedBindings = new PyDictionary();
      if (exclude != Py.None) {
         for(int i = 0; i < exclude.__len__(); ++i) {
            PyObject lowered = Py.newString(((PyString)exclude.__getitem__(i)).lower());
            this.exclude.add(lowered);
         }
      }

   }

   protected boolean excluded(PyObject key) {
      PyObject lowered = Py.newString(((PyString)key).lower());
      return this.exclude.contains(lowered);
   }

   protected void createSql(PyObject row) {
      if (row != Py.None && row.__len__() != 0) {
         int index = 0;
         int len = row.__len__();
         PyObject entry = Py.None;
         PyObject col = Py.None;
         PyObject pyIndex = Py.None;
         StringBuffer sb = (new StringBuffer("insert into ")).append(this.tableName).append(" (");

         int i;
         PyInteger pyIndex;
         for(i = 0; i < len - 1; ++i) {
            entry = row.__getitem__(i);
            col = entry.__getitem__(0);
            if (!this.excluded(col)) {
               sb.append(col).append(",");
               pyIndex = Py.newInteger(index++);

               try {
                  this.indexedBindings.__setitem__(pyIndex, this.bindings.__getitem__(col));
               } catch (Exception var11) {
                  this.indexedBindings.__setitem__(pyIndex, entry.__getitem__(1));
               }
            }
         }

         entry = row.__getitem__(len - 1);
         col = entry.__getitem__(0);
         if (!this.excluded(col)) {
            sb.append(col);
            pyIndex = Py.newInteger(index++);

            try {
               this.indexedBindings.__setitem__(pyIndex, this.bindings.__getitem__(col));
            } catch (Exception var10) {
               this.indexedBindings.__setitem__(pyIndex, entry.__getitem__(1));
            }
         }

         sb.append(") values (");

         for(i = 1; i < len; ++i) {
            sb.append("?,");
         }

         sb.append("?)");
         if (index == 0) {
            throw zxJDBC.makeException(zxJDBC.ProgrammingError, zxJDBC.getString("excludedAllCols"));
         } else {
            this.sql = Py.newString(sb.toString());
         }
      } else {
         throw zxJDBC.makeException(zxJDBC.getString("noColInfo"));
      }
   }

   public void row(PyObject row) {
      if (this.sql != Py.None) {
         if (this.batchsize <= 0) {
            this.cursor.execute(this.sql, row, this.indexedBindings, Py.None);
            this.connection.commit();
         } else {
            this.rows.append(row);
            int len = this.rows.__len__();
            if (len % this.batchsize == 0) {
               this.cursor.execute(this.sql, this.rows, this.indexedBindings, Py.None);
               this.connection.commit();
               this.rows = new PyList();
            }
         }
      } else {
         this.createSql(row);
      }

   }

   public void start() {
   }

   public void end() {
      try {
         int len = this.rows.__len__();
         if (len > 0) {
            this.cursor.execute(this.sql, this.rows, this.indexedBindings, Py.None);
            this.connection.commit();
         }
      } finally {
         this.cursor.close();
      }

   }
}

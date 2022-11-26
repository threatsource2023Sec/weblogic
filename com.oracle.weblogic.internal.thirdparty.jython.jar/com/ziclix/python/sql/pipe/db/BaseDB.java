package com.ziclix.python.sql.pipe.db;

import com.ziclix.python.sql.DataHandler;
import com.ziclix.python.sql.PyConnection;
import com.ziclix.python.sql.PyCursor;
import com.ziclix.python.sql.zxJDBC;
import java.lang.reflect.Constructor;
import org.python.core.Py;

public abstract class BaseDB {
   protected PyCursor cursor;
   protected Class dataHandler;
   protected String tableName;
   protected PyConnection connection;

   public BaseDB(PyConnection connection, Class dataHandler, String tableName) {
      this.tableName = tableName;
      this.dataHandler = dataHandler;
      this.connection = connection;
      this.cursor = this.cursor();
   }

   protected PyCursor cursor() {
      PyCursor cursor = this.connection.cursor(true);
      DataHandler origDataHandler = cursor.getDataHandler();
      DataHandler newDataHandler = null;
      if (origDataHandler != null && this.dataHandler != null) {
         Constructor cons = null;

         try {
            Class[] args = new Class[]{DataHandler.class};
            cons = this.dataHandler.getConstructor(args);
         } catch (Exception var7) {
            return cursor;
         }

         if (cons == null) {
            String msg = zxJDBC.getString("invalidCons", new Object[]{this.dataHandler.getName()});
            throw zxJDBC.makeException(msg);
         }

         try {
            Object[] args = new Object[]{origDataHandler};
            newDataHandler = (DataHandler)cons.newInstance(args);
         } catch (Exception var6) {
            return cursor;
         }

         if (newDataHandler != null) {
            cursor.__setattr__("datahandler", Py.java2py(newDataHandler));
         }
      }

      return cursor;
   }
}

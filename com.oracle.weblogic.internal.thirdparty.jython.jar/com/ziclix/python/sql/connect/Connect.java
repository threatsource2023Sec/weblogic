package com.ziclix.python.sql.connect;

import com.ziclix.python.sql.PyConnection;
import com.ziclix.python.sql.zxJDBC;
import com.ziclix.python.sql.util.PyArgParser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.Untraversable;

@Untraversable
public class Connect extends PyObject {
   private static final PyString _doc = new PyString("establish a connection through java.sql.DriverManager");

   public PyObject __findattr_ex__(String name) {
      return (PyObject)("__doc__".equals(name) ? _doc : super.__findattr_ex__(name));
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      Connection c = null;
      PyArgParser parser = new PyArgParser(args, keywords);
      Object arg = parser.arg(0).__tojava__(Connection.class);
      if (arg == Py.NoConversion) {
         Properties props = new Properties();
         String url = (String)parser.arg(0).__tojava__(String.class);
         String user = (String)parser.arg(1).__tojava__(String.class);
         String password = (String)parser.arg(2).__tojava__(String.class);
         String driver = (String)parser.arg(3).__tojava__(String.class);
         if (url == null) {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, "no url specified");
         }

         if (driver == null) {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, "no driver specified");
         }

         props.put("user", user == null ? "" : user);
         props.put("password", password == null ? "" : password);
         String[] kws = parser.kws();

         for(int i = 0; i < kws.length; ++i) {
            Object value = parser.kw(kws[i]).__tojava__(Object.class);
            props.put(kws[i], value);
         }

         try {
            Class.forName(driver);
         } catch (Throwable var16) {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, "driver [" + driver + "] not found");
         }

         try {
            c = DriverManager.getConnection(url, props);
         } catch (SQLException var15) {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var15);
         }
      } else {
         c = (Connection)arg;
      }

      try {
         if (c != null && !c.isClosed()) {
            return new PyConnection(c);
         } else {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, "unable to establish connection");
         }
      } catch (SQLException var14) {
         throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var14);
      }
   }

   public String toString() {
      return "<connect object instance at " + Py.id(this) + ">";
   }
}

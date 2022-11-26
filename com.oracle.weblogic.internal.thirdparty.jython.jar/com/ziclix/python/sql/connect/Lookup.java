package com.ziclix.python.sql.connect;

import com.ziclix.python.sql.PyConnection;
import com.ziclix.python.sql.zxJDBC;
import com.ziclix.python.sql.util.PyArgParser;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.Untraversable;

@Untraversable
public class Lookup extends PyObject {
   private static final PyString _doc = new PyString("establish a connection through a JNDI lookup");

   public PyObject __findattr_ex__(String name) {
      return (PyObject)("__doc__".equals(name) ? _doc : super.__findattr_ex__(name));
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      Object ref = null;
      Connection connection = null;
      Hashtable env = new Hashtable();
      PyArgParser parser = new PyArgParser(args, keywords);
      Object jndiName = parser.arg(0).__tojava__(String.class);
      if (jndiName != null && jndiName != Py.NoConversion) {
         String[] kws = parser.kws();

         for(int i = 0; i < kws.length; ++i) {
            String keyword = kws[i];
            String fieldname = null;
            Object value = parser.kw(keyword).__tojava__(Object.class);

            try {
               Field field = Context.class.getField(keyword);
               fieldname = (String)field.get(Context.class);
            } catch (IllegalAccessException var28) {
               throw zxJDBC.makeException(zxJDBC.ProgrammingError, (Throwable)var28);
            } catch (NoSuchFieldException var29) {
               fieldname = keyword;
            }

            env.put(fieldname, value);
         }

         InitialContext context = null;

         try {
            context = new InitialContext(env);
            ref = context.lookup((String)jndiName);
         } catch (NamingException var27) {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var27);
         } finally {
            if (context != null) {
               try {
                  context.close();
               } catch (NamingException var24) {
               }
            }

         }

         if (ref == null) {
            throw zxJDBC.makeException(zxJDBC.ProgrammingError, "object [" + jndiName + "] not found in JNDI");
         } else {
            try {
               if (ref instanceof String) {
                  connection = DriverManager.getConnection((String)ref);
               } else if (ref instanceof Connection) {
                  connection = (Connection)ref;
               } else if (ref instanceof DataSource) {
                  connection = ((DataSource)ref).getConnection();
               } else if (ref instanceof ConnectionPoolDataSource) {
                  connection = ((ConnectionPoolDataSource)ref).getPooledConnection().getConnection();
               }
            } catch (SQLException var26) {
               throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var26);
            }

            try {
               if (connection != null && !connection.isClosed()) {
                  return new PyConnection(connection);
               } else {
                  throw zxJDBC.makeException(zxJDBC.DatabaseError, "unable to establish connection");
               }
            } catch (SQLException var25) {
               throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var25);
            }
         }
      } else {
         throw zxJDBC.makeException(zxJDBC.DatabaseError, "lookup name is null");
      }
   }

   public String toString() {
      return String.format("<lookup object at %s>", Py.idstr(this));
   }
}

package com.ziclix.python.sql.connect;

import com.ziclix.python.sql.PyConnection;
import com.ziclix.python.sql.zxJDBC;
import com.ziclix.python.sql.util.PyArgParser;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.Untraversable;

@Untraversable
public class Connectx extends PyObject {
   private static final String SET = "set";
   private static final PyString _doc = new PyString("establish a connection through a javax.sql.DataSource or javax.sql.ConnectionPooledDataSource");

   public PyObject __findattr_ex__(String name) {
      return (PyObject)("__doc__".equals(name) ? _doc : super.__findattr_ex__(name));
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      Connection c = null;
      PyConnection pc = null;
      Object datasource = null;
      PyArgParser parser = new PyArgParser(args, keywords);

      try {
         String klass = (String)parser.arg(0).__tojava__(String.class);
         datasource = Class.forName(klass).newInstance();
      } catch (Exception var13) {
         throw zxJDBC.makeException(zxJDBC.DatabaseError, "unable to instantiate datasource");
      }

      String[] kws = parser.kws();

      for(int i = 0; i < kws.length; ++i) {
         String methodName = kws[i];
         if (methodName != null) {
            Object value = parser.kw(kws[i]).__tojava__(Object.class);
            if (methodName.length() > "set".length()) {
               if (!"set".equals(methodName.substring(0, "set".length()))) {
                  this.invoke(datasource, "set" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1), value);
               } else {
                  this.invoke(datasource, methodName, value);
               }
            } else {
               this.invoke(datasource, "set" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1), value);
            }
         }
      }

      try {
         if (datasource instanceof ConnectionPoolDataSource) {
            c = ((ConnectionPoolDataSource)datasource).getPooledConnection().getConnection();
         } else if (datasource instanceof DataSource) {
            c = ((DataSource)datasource).getConnection();
         }
      } catch (SQLException var12) {
         throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var12);
      }

      try {
         if (c != null && !c.isClosed()) {
            pc = new PyConnection(c);
            return pc;
         } else {
            throw zxJDBC.makeException(zxJDBC.DatabaseError, "unable to establish connection");
         }
      } catch (SQLException var11) {
         throw zxJDBC.makeException(zxJDBC.DatabaseError, (Throwable)var11);
      }
   }

   public String toString() {
      return String.format("<connectx object at %s>", Py.id(this));
   }

   protected void invoke(Object src, String methodName, Object value) {
      Method method = null;
      StringBuffer exceptionMsg = new StringBuffer("method [");
      exceptionMsg.append(methodName).append("] using arg type [");
      exceptionMsg.append(value.getClass()).append("], value [");
      exceptionMsg.append(value.toString()).append("]");

      try {
         method = this.getMethod(src.getClass(), methodName, value.getClass());
         if (method == null) {
            throw zxJDBC.makeException("no such " + exceptionMsg);
         } else {
            method.invoke(src, value);
         }
      } catch (IllegalAccessException var7) {
         throw zxJDBC.makeException("illegal access for " + exceptionMsg);
      } catch (InvocationTargetException var8) {
         throw zxJDBC.makeException("invocation target exception for " + exceptionMsg);
      }
   }

   protected Method getMethod(Class srcClass, String methodName, Class valueClass) {
      Method method = null;

      try {
         method = srcClass.getMethod(methodName, valueClass);
      } catch (NoSuchMethodException var11) {
         Class primitive = null;

         try {
            Field f = valueClass.getField("TYPE");
            primitive = (Class)f.get(valueClass);
         } catch (NoSuchFieldException var8) {
         } catch (IllegalAccessException var9) {
         } catch (ClassCastException var10) {
         }

         if (primitive != null && primitive.isPrimitive()) {
            return this.getMethod(srcClass, methodName, primitive);
         }
      }

      return method;
   }
}

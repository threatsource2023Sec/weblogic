package com.ziclix.python.sql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.python.core.PyObject;
import org.python.core.PySystemState;

public class DataHandlerTest extends TestCase {
   private DataHandler _handler;

   protected void setUp() throws Exception {
      PySystemState.initialize();
      this._handler = new DataHandler();
   }

   public void testGetPyObjectResultSetIntInt() throws Exception {
      ResultSet rs = (ResultSet)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{ResultSet.class}, new DefaultReturnHandler());
      List unsupportedTypes = Arrays.asList("ARRAY", "DATALINK", "DISTINCT", "REF", "REF_CURSOR", "ROWID", "STRUCT", "TIME_WITH_TIMEZONE", "TIMESTAMP_WITH_TIMEZONE");
      Field[] var3 = Types.class.getDeclaredFields();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field field = var3[var5];
         String typeName = field.getName();
         int type = field.getInt((Object)null);
         if (unsupportedTypes.contains(typeName)) {
            try {
               this._handler.getPyObject((ResultSet)rs, 1, type);
               fail("SQLException expected: " + typeName);
            } catch (SQLException var10) {
            }
         } else {
            try {
               PyObject pyobj = this._handler.getPyObject((ResultSet)rs, 1, type);
               assertNotNull(typeName + " should return None", pyobj);
            } catch (SQLException var11) {
               fail("unexpected SQLException: " + typeName);
            }
         }
      }

   }

   static class DefaultReturnHandler implements InvocationHandler {
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         Class returnType = method.getReturnType();
         if (!returnType.equals(Boolean.class) && !returnType.equals(Boolean.TYPE)) {
            if (Character.TYPE.equals(returnType)) {
               return '0';
            } else if (Byte.TYPE.equals(returnType)) {
               return 0;
            } else if (Short.TYPE.equals(returnType)) {
               return Short.valueOf((short)0);
            } else if (Integer.TYPE.equals(returnType)) {
               return 0;
            } else if (Long.TYPE.equals(returnType)) {
               return 0L;
            } else if (Float.TYPE.equals(returnType)) {
               return 0.0F;
            } else if (Double.TYPE.equals(returnType)) {
               return 0.0;
            } else if (returnType.isPrimitive()) {
               throw new RuntimeException("unhandled primitve type " + returnType);
            } else if (returnType.isAssignableFrom(BigInteger.class)) {
               return BigInteger.ZERO;
            } else if (returnType.isAssignableFrom(BigDecimal.class)) {
               return BigDecimal.ZERO;
            } else {
               return returnType.isAssignableFrom(Number.class) ? 0 : null;
            }
         } else {
            return Boolean.FALSE;
         }
      }
   }
}

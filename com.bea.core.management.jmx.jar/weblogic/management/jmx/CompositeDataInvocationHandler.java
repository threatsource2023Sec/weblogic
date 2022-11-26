package weblogic.management.jmx;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.ArrayType;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;

public class CompositeDataInvocationHandler implements InvocationHandler {
   protected MBeanServerConnection connection = null;
   protected CompositeData data;
   private static final Class[] ARGS_STRING = new Class[]{String.class};

   private CompositeDataInvocationHandler(MBeanServerConnection connection, CompositeData data) {
      this.connection = connection;
      this.data = data;
   }

   public static Object newProxyInstance(MBeanServerConnection connection, CompositeData data) {
      String compositeTypeName = data.getCompositeType().getTypeName();
      if (compositeTypeName.equals(CompositeTypeThrowable.THROWABLE_TYPE_NAME)) {
         return CompositeTypeThrowable.reconstitute(data);
      } else if (compositeTypeName.equals(CompositeTypeProperties.TYPE_NAME)) {
         return CompositeTypeProperties.reconstitute(data);
      } else {
         Class interfaceClass = null;

         try {
            interfaceClass = Class.forName(compositeTypeName);
         } catch (ClassNotFoundException var8) {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();

            try {
               interfaceClass = cl.loadClass(compositeTypeName);
            } catch (ClassNotFoundException var7) {
               throw new RuntimeException(var7);
            }
         }

         InvocationHandler handler = new CompositeDataInvocationHandler(connection, data);
         return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, handler);
      }
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      String methodName = method.getName();
      Class[] paramTypes = method.getParameterTypes();
      Class returnType = method.getReturnType();
      if (methodName.equals("toString") && paramTypes.length == 0) {
         return this.data.toString();
      } else {
         int offset = -1;
         if (methodName.startsWith("get")) {
            offset = 3;
         } else if (methodName.startsWith("is")) {
            offset = 2;
         }

         if (offset != -1 && paramTypes.length == 0) {
            String propertyName = methodName.substring(offset);
            Object propertyValue = this.data.get(propertyName);
            if (!(propertyValue instanceof CompositeData)) {
               return propertyValue instanceof ObjectName && !returnType.isAssignableFrom(ObjectName.class) ? MBeanServerInvocationHandler.newProxyInstance(this.connection, (ObjectName)propertyValue) : propertyValue;
            } else {
               CompositeData propertyData = (CompositeData)propertyValue;
               CompositeType propertyType = propertyData.getCompositeType();
               if (!propertyType.getTypeName().equals(CompositeTypeAny.ANY_TYPE_NAME)) {
                  return newProxyInstance(this.connection, propertyData);
               } else {
                  String typeAsString = (String)propertyData.get("OpenTypeName");
                  if (typeAsString.equals("null")) {
                     return null;
                  } else if (!typeAsString.equals(ArrayType.class.getName())) {
                     Class resultClass = Class.forName(typeAsString);
                     String valueAsString = (String)propertyData.get("ValueAsString");
                     Constructor constructor = resultClass.getConstructor(ARGS_STRING);
                     return constructor.newInstance((Object[])(new String[]{valueAsString}));
                  } else {
                     String typeFromValue = (String)propertyData.get("ValueAsString");
                     Class resultClass = Class.forName(typeFromValue).getComponentType();
                     String[] propertyValues = (String[])((String[])propertyData.get("ValueAsStringArray"));
                     if (resultClass.isPrimitive()) {
                        return convertStringArrayToPrimitives(resultClass, propertyValues);
                     } else {
                        Object[] resultArray = (Object[])((Object[])Array.newInstance(resultClass, propertyValues.length));
                        Constructor constructor = resultClass.getConstructor(ARGS_STRING);

                        for(int i = 0; i < propertyValues.length; ++i) {
                           resultArray[i] = constructor.newInstance((Object[])(new String[]{propertyValues[i]}));
                        }

                        return resultArray;
                     }
                  }
               }
            }
         } else {
            throw new UnsupportedOperationException("Only get is currently support for proxied CompositeData Proxies");
         }
      }
   }

   private static Object convertStringArrayToPrimitives(Class resultClass, String[] propertyValues) {
      int i;
      if (resultClass == Byte.TYPE) {
         byte[] results = new byte[propertyValues.length];

         for(i = 0; i < propertyValues.length; ++i) {
            results[i] = Byte.parseByte(propertyValues[i]);
         }

         return results;
      } else if (resultClass == Boolean.TYPE) {
         boolean[] results = new boolean[propertyValues.length];

         for(i = 0; i < propertyValues.length; ++i) {
            results[i] = Boolean.parseBoolean(propertyValues[i]);
         }

         return results;
      } else if (resultClass == Character.TYPE) {
         char[] results = new char[propertyValues.length];

         for(i = 0; i < propertyValues.length; ++i) {
            propertyValues[i].getChars(0, 1, results, i);
         }

         return results;
      } else if (resultClass == Short.TYPE) {
         short[] results = new short[propertyValues.length];

         for(i = 0; i < propertyValues.length; ++i) {
            results[i] = Short.parseShort(propertyValues[i]);
         }

         return results;
      } else if (resultClass == Integer.TYPE) {
         int[] results = new int[propertyValues.length];

         for(i = 0; i < propertyValues.length; ++i) {
            results[i] = Integer.parseInt(propertyValues[i]);
         }

         return results;
      } else if (resultClass == Long.TYPE) {
         long[] results = new long[propertyValues.length];

         for(i = 0; i < propertyValues.length; ++i) {
            results[i] = Long.parseLong(propertyValues[i]);
         }

         return results;
      } else if (resultClass == Float.TYPE) {
         float[] results = new float[propertyValues.length];

         for(i = 0; i < propertyValues.length; ++i) {
            results[i] = Float.parseFloat(propertyValues[i]);
         }

         return results;
      } else if (resultClass != Double.TYPE) {
         throw new AssertionError("Unable to convert " + resultClass.getName());
      } else {
         double[] results = new double[propertyValues.length];

         for(i = 0; i < propertyValues.length; ++i) {
            results[i] = Double.parseDouble(propertyValues[i]);
         }

         return results;
      }
   }
}

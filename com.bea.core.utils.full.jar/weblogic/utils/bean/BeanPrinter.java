package weblogic.utils.bean;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import weblogic.utils.ArrayUtils;
import weblogic.utils.PlatformConstants;

public class BeanPrinter {
   private BeanPrinter() {
   }

   public static String toString(Object bean) {
      StringBuffer buffer = new StringBuffer(bean.getClass().toString());
      buffer.append(':');
      buffer.append(PlatformConstants.EOL);

      try {
         PropertyDescriptor[] descs = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();

         for(int i = 0; i < descs.length; ++i) {
            PropertyDescriptor property = descs[i];
            Method m = property.getReadMethod();
            Object value = null;

            try {
               value = m.invoke(bean, (Object[])null);
            } catch (IllegalAccessException var8) {
               value = "Illegal access exception";
            } catch (IllegalArgumentException var9) {
               value = "Illegal argument exception";
            } catch (InvocationTargetException var10) {
               value = "Invocation target exception";
            }

            buffer.append('\t');
            buffer.append(property.getName());
            buffer.append(": '");
            if (value instanceof Object[]) {
               buffer.append(ArrayUtils.toString((Object[])((Object[])value)));
            } else {
               buffer.append(value);
            }

            buffer.append("'");
            buffer.append(PlatformConstants.EOL);
         }

         return buffer.toString();
      } catch (IntrospectionException var11) {
         return null;
      }
   }
}

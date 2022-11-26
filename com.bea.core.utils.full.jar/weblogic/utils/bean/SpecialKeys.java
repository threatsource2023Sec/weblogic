package weblogic.utils.bean;

import java.beans.PropertyDescriptor;
import weblogic.utils.collections.AggregateKey;

public class SpecialKeys {
   private static final boolean debug = true;
   private static final boolean verbose = false;

   public static Object getKey(Object o) {
      if (o instanceof PropertyDescriptor) {
         PropertyDescriptor pd = (PropertyDescriptor)o;
         String name = pd.getName();
         Class clazz = pd.getPropertyType();
         return new AggregateKey(name, clazz);
      } else {
         return o;
      }
   }
}

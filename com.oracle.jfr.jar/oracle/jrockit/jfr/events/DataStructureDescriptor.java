package oracle.jrockit.jfr.events;

import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.InvalidValueException;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class DataStructureDescriptor {
   private final ValueDescriptor[] values;
   private final HashMap idToIndexMap = new HashMap();

   public DataStructureDescriptor(Class c) throws InvalidValueException {
      ArrayList values = new ArrayList();

      while(true) {
         Field[] fields = c.getDeclaredFields();
         Field[] arr$ = fields;
         int len$ = fields.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Field f = arr$[i$];
            ValueDefinition d = (ValueDefinition)f.getAnnotation(ValueDefinition.class);
            UseConstantPool p = (UseConstantPool)f.getAnnotation(UseConstantPool.class);
            if (d != null) {
               values.add(new ValueDescriptor(d, p, f));
            }
         }

         Class s = c.getSuperclass();
         if (!InstantEvent.class.isAssignableFrom(s)) {
            Collections.reverse(values);
            this.values = (ValueDescriptor[])values.toArray(new ValueDescriptor[values.size()]);
            int i = 0;
            Iterator i$ = values.iterator();

            while(i$.hasNext()) {
               ValueDescriptor d = (ValueDescriptor)i$.next();
               this.idToIndexMap.put(d.getId(), i++);
            }

            return;
         }

         c = s.asSubclass(InstantEvent.class);
      }
   }

   public DataStructureDescriptor(ValueDescriptor... values) {
      this.values = values;
      int i = 0;
      ValueDescriptor[] arr$ = values;
      int len$ = values.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ValueDescriptor d = arr$[i$];
         this.idToIndexMap.put(d.getId(), i++);
      }

   }

   public ValueDescriptor[] getValues() {
      return this.values;
   }

   public int valueIndex(String valueId) {
      Integer i = (Integer)this.idToIndexMap.get(valueId);
      if (i != null) {
         return i;
      } else {
         throw new IllegalArgumentException("No value field with id " + valueId);
      }
   }

   public String toString() {
      return Arrays.toString(this.values);
   }
}

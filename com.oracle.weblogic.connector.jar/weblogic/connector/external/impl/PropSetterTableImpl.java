package weblogic.connector.external.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.connector.external.PropSetterTable;
import weblogic.connector.utils.ObjectPair;
import weblogic.connector.utils.PropertyNameNormalizer;

public class PropSetterTableImpl implements PropSetterTable {
   private final Map injectors = new HashMap();
   private PropertyNameNormalizer propertyNameNormalizer;

   public PropSetterTableImpl(PropertyNameNormalizer propertyNameNormalizer) {
      this.propertyNameNormalizer = propertyNameNormalizer;
   }

   public void registerSetterProperty(String name, String type, Method method) {
      this.injectors.put(this.generateKey(name, type), new MethodInjector(method, name, type));
   }

   public PropSetterTable.PropertyInjector getInjectorByName(String name) {
      String normalizedName = this.propertyNameNormalizer.normalize(name);
      Set keySet = this.injectors.keySet();
      Iterator var4 = keySet.iterator();

      ObjectPair key;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         key = (ObjectPair)var4.next();
      } while(!((String)key.getFirst()).equals(normalizedName));

      return (PropSetterTable.PropertyInjector)this.injectors.get(key);
   }

   public boolean haveInjectorWithName(String name) {
      return this.getInjectorByName(name) != null;
   }

   public PropSetterTable.PropertyInjector getInjector(String propName, String propType) {
      return (PropSetterTable.PropertyInjector)this.injectors.get(this.generateKey(propName, propType));
   }

   private ObjectPair generateKey(String name, String type) {
      return new ObjectPair(this.propertyNameNormalizer.normalize(name), type);
   }

   private static class MethodInjector implements PropSetterTable.PropertyInjector {
      private final Method method;
      private final String name;
      private final String type;

      MethodInjector(Method method, String name, String type) {
         this.method = method;
         this.name = name;
         this.type = type;
      }

      public String getName() {
         return this.name;
      }

      public String getType() {
         return this.type;
      }

      public void inject(Object target, Object value) throws IllegalAccessException, InvocationTargetException {
         this.method.invoke(target, value);
      }
   }
}

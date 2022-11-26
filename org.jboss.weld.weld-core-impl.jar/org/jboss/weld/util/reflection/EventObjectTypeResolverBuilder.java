package org.jboss.weld.util.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EventObjectTypeResolverBuilder {
   private final Map selectedTypeVariables;
   private final Map eventTypeVariables;
   private final Map resolvedTypes;

   public EventObjectTypeResolverBuilder(Map selectedTypeVariables, Map eventTypeVariables) {
      this.selectedTypeVariables = selectedTypeVariables;
      this.eventTypeVariables = eventTypeVariables;
      this.resolvedTypes = new HashMap();
   }

   public TypeResolver build() {
      this.resolveTypeVariables();
      Map mergedVariables = new HashMap(this.eventTypeVariables);
      mergedVariables.putAll(this.selectedTypeVariables);
      mergedVariables.putAll(this.resolvedTypes);
      return new TypeResolver(mergedVariables);
   }

   protected void resolveTypeVariables() {
      Iterator var1 = this.eventTypeVariables.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry entry = (Map.Entry)var1.next();
         TypeVariable key = (TypeVariable)entry.getKey();
         Type typeWithTypeVariables = (Type)entry.getValue();
         Type value = (Type)this.selectedTypeVariables.get(key);
         if (value != null) {
            this.resolveTypeVariables(typeWithTypeVariables, value);
         }
      }

   }

   protected void resolveTypeVariables(Type type1, Type type2) {
      if (type1 instanceof TypeVariable) {
         this.resolveTypeVariables((TypeVariable)type1, type2);
      }

      if (type1 instanceof ParameterizedType) {
         this.resolveTypeVariables((ParameterizedType)type1, type2);
      }

   }

   protected void resolveTypeVariables(TypeVariable type1, Type type2) {
      if (!(type2 instanceof TypeVariable)) {
         this.resolvedTypes.put(type1, type2);
      }
   }

   protected void resolveTypeVariables(ParameterizedType type1, Type type2) {
      if (type2 instanceof ParameterizedType) {
         Type[] type1Arguments = type1.getActualTypeArguments();
         Type[] type2Arguments = ((ParameterizedType)type2).getActualTypeArguments();
         if (type1Arguments.length == type2Arguments.length) {
            for(int i = 0; i < type1Arguments.length; ++i) {
               this.resolveTypeVariables(type1Arguments[i], type2Arguments[i]);
            }
         }
      }

   }

   public Map getResolvedTypes() {
      return this.resolvedTypes;
   }
}

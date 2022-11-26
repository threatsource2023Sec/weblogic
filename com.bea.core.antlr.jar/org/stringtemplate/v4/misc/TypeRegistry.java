package org.stringtemplate.v4.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TypeRegistry implements Map {
   private final Map backingStore = new HashMap();
   private final Map cache = new HashMap();

   public int size() {
      return this.backingStore.size();
   }

   public boolean isEmpty() {
      return this.backingStore.isEmpty();
   }

   public boolean containsKey(Object key) {
      if (this.cache.containsKey(key)) {
         return true;
      } else if (!(key instanceof Class)) {
         return false;
      } else {
         return this.get((Class)key) != null;
      }
   }

   public boolean containsValue(Object value) {
      return this.values().contains(value);
   }

   public Object get(Object key) {
      Object value = this.backingStore.get(key);
      if (value != null) {
         return value;
      } else {
         Class redirect = (Class)this.cache.get(key);
         if (redirect != null) {
            return redirect == Void.TYPE ? null : this.backingStore.get(redirect);
         } else if (!(key instanceof Class)) {
            return null;
         } else {
            Class keyClass = (Class)key;
            List candidates = new ArrayList();
            Iterator var6 = this.backingStore.keySet().iterator();

            while(var6.hasNext()) {
               Class clazz = (Class)var6.next();
               if (clazz.isAssignableFrom(keyClass)) {
                  candidates.add(clazz);
               }
            }

            if (candidates.isEmpty()) {
               this.cache.put(keyClass, Void.TYPE);
               return null;
            } else if (candidates.size() == 1) {
               this.cache.put(keyClass, candidates.get(0));
               return this.backingStore.get(candidates.get(0));
            } else {
               int j;
               int j;
               for(j = 0; j < candidates.size() - 1; ++j) {
                  if (candidates.get(j) != null) {
                     for(j = j + 1; j < candidates.size(); ++j) {
                        if (((Class)candidates.get(j)).isAssignableFrom((Class)candidates.get(j))) {
                           candidates.set(j, (Object)null);
                           break;
                        }

                        if (((Class)candidates.get(j)).isAssignableFrom((Class)candidates.get(j))) {
                           candidates.set(j, (Object)null);
                        }
                     }
                  }
               }

               j = 0;

               for(j = 0; j < candidates.size(); ++j) {
                  Class current = (Class)candidates.get(j);
                  if (current != null) {
                     if (j != j) {
                        candidates.set(j, current);
                     }

                     ++j;
                  }
               }

               assert j > 0;

               if (j == 1) {
                  this.cache.put(keyClass, candidates.get(0));
                  return this.backingStore.get(candidates.get(0));
               } else {
                  StringBuilder builder = new StringBuilder();
                  builder.append(String.format("The class '%s' does not match a single item in the registry. The %d ambiguous matches are:", keyClass.getName(), j));

                  for(int i = 0; i < j; ++i) {
                     builder.append(String.format("%n    %s", ((Class)candidates.get(j)).getName()));
                  }

                  throw new AmbiguousMatchException(builder.toString());
               }
            }
         }
      }
   }

   public Object put(Class key, Object value) {
      Object result = this.get(key);
      this.backingStore.put(key, value);
      this.handleAlteration(key);
      return result;
   }

   public Object remove(Object key) {
      if (!(key instanceof Class)) {
         return null;
      } else {
         Class clazz = (Class)key;
         Object previous = this.get(clazz);
         if (this.backingStore.remove(clazz) != null) {
            this.handleAlteration(clazz);
         }

         return previous;
      }
   }

   public void putAll(Map m) {
      Iterator var2 = m.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.put((Class)entry.getKey(), entry.getValue());
      }

   }

   public void clear() {
      this.backingStore.clear();
      this.cache.clear();
   }

   public Set keySet() {
      return Collections.unmodifiableSet(this.backingStore.keySet());
   }

   public Collection values() {
      return Collections.unmodifiableCollection(this.backingStore.values());
   }

   public Set entrySet() {
      return Collections.unmodifiableSet(this.backingStore.entrySet());
   }

   protected void handleAlteration(Class clazz) {
      Iterator var2 = this.cache.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         if (clazz.isAssignableFrom((Class)entry.getKey())) {
            entry.setValue((Object)null);
         }
      }

   }
}

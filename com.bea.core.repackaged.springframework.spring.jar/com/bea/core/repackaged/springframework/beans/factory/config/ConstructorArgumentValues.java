package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeanMetadataElement;
import com.bea.core.repackaged.springframework.beans.Mergeable;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConstructorArgumentValues {
   private final Map indexedArgumentValues = new LinkedHashMap();
   private final List genericArgumentValues = new ArrayList();

   public ConstructorArgumentValues() {
   }

   public ConstructorArgumentValues(ConstructorArgumentValues original) {
      this.addArgumentValues(original);
   }

   public void addArgumentValues(@Nullable ConstructorArgumentValues other) {
      if (other != null) {
         other.indexedArgumentValues.forEach((index, argValue) -> {
            this.addOrMergeIndexedArgumentValue(index, argValue.copy());
         });
         other.genericArgumentValues.stream().filter((valueHolder) -> {
            return !this.genericArgumentValues.contains(valueHolder);
         }).forEach((valueHolder) -> {
            this.addOrMergeGenericArgumentValue(valueHolder.copy());
         });
      }

   }

   public void addIndexedArgumentValue(int index, @Nullable Object value) {
      this.addIndexedArgumentValue(index, new ValueHolder(value));
   }

   public void addIndexedArgumentValue(int index, @Nullable Object value, String type) {
      this.addIndexedArgumentValue(index, new ValueHolder(value, type));
   }

   public void addIndexedArgumentValue(int index, ValueHolder newValue) {
      Assert.isTrue(index >= 0, "Index must not be negative");
      Assert.notNull(newValue, (String)"ValueHolder must not be null");
      this.addOrMergeIndexedArgumentValue(index, newValue);
   }

   private void addOrMergeIndexedArgumentValue(Integer key, ValueHolder newValue) {
      ValueHolder currentValue = (ValueHolder)this.indexedArgumentValues.get(key);
      if (currentValue != null && newValue.getValue() instanceof Mergeable) {
         Mergeable mergeable = (Mergeable)newValue.getValue();
         if (mergeable.isMergeEnabled()) {
            newValue.setValue(mergeable.merge(currentValue.getValue()));
         }
      }

      this.indexedArgumentValues.put(key, newValue);
   }

   public boolean hasIndexedArgumentValue(int index) {
      return this.indexedArgumentValues.containsKey(index);
   }

   @Nullable
   public ValueHolder getIndexedArgumentValue(int index, @Nullable Class requiredType) {
      return this.getIndexedArgumentValue(index, requiredType, (String)null);
   }

   @Nullable
   public ValueHolder getIndexedArgumentValue(int index, @Nullable Class requiredType, @Nullable String requiredName) {
      Assert.isTrue(index >= 0, "Index must not be negative");
      ValueHolder valueHolder = (ValueHolder)this.indexedArgumentValues.get(index);
      return valueHolder == null || valueHolder.getType() != null && (requiredType == null || !ClassUtils.matchesTypeName(requiredType, valueHolder.getType())) || valueHolder.getName() != null && !"".equals(requiredName) && (requiredName == null || !requiredName.equals(valueHolder.getName())) ? null : valueHolder;
   }

   public Map getIndexedArgumentValues() {
      return Collections.unmodifiableMap(this.indexedArgumentValues);
   }

   public void addGenericArgumentValue(Object value) {
      this.genericArgumentValues.add(new ValueHolder(value));
   }

   public void addGenericArgumentValue(Object value, String type) {
      this.genericArgumentValues.add(new ValueHolder(value, type));
   }

   public void addGenericArgumentValue(ValueHolder newValue) {
      Assert.notNull(newValue, (String)"ValueHolder must not be null");
      if (!this.genericArgumentValues.contains(newValue)) {
         this.addOrMergeGenericArgumentValue(newValue);
      }

   }

   private void addOrMergeGenericArgumentValue(ValueHolder newValue) {
      if (newValue.getName() != null) {
         Iterator it = this.genericArgumentValues.iterator();

         while(it.hasNext()) {
            ValueHolder currentValue = (ValueHolder)it.next();
            if (newValue.getName().equals(currentValue.getName())) {
               if (newValue.getValue() instanceof Mergeable) {
                  Mergeable mergeable = (Mergeable)newValue.getValue();
                  if (mergeable.isMergeEnabled()) {
                     newValue.setValue(mergeable.merge(currentValue.getValue()));
                  }
               }

               it.remove();
            }
         }
      }

      this.genericArgumentValues.add(newValue);
   }

   @Nullable
   public ValueHolder getGenericArgumentValue(Class requiredType) {
      return this.getGenericArgumentValue(requiredType, (String)null, (Set)null);
   }

   @Nullable
   public ValueHolder getGenericArgumentValue(Class requiredType, String requiredName) {
      return this.getGenericArgumentValue(requiredType, requiredName, (Set)null);
   }

   @Nullable
   public ValueHolder getGenericArgumentValue(@Nullable Class requiredType, @Nullable String requiredName, @Nullable Set usedValueHolders) {
      Iterator var4 = this.genericArgumentValues.iterator();

      ValueHolder valueHolder;
      do {
         while(true) {
            do {
               do {
                  if (!var4.hasNext()) {
                     return null;
                  }

                  valueHolder = (ValueHolder)var4.next();
               } while(usedValueHolders != null && usedValueHolders.contains(valueHolder));
            } while(valueHolder.getName() != null && !"".equals(requiredName) && (requiredName == null || !valueHolder.getName().equals(requiredName)));

            if (valueHolder.getType() == null || requiredType != null && ClassUtils.matchesTypeName(requiredType, valueHolder.getType())) {
               break;
            }
         }
      } while(requiredType != null && valueHolder.getType() == null && valueHolder.getName() == null && !ClassUtils.isAssignableValue(requiredType, valueHolder.getValue()));

      return valueHolder;
   }

   public List getGenericArgumentValues() {
      return Collections.unmodifiableList(this.genericArgumentValues);
   }

   @Nullable
   public ValueHolder getArgumentValue(int index, Class requiredType) {
      return this.getArgumentValue(index, requiredType, (String)null, (Set)null);
   }

   @Nullable
   public ValueHolder getArgumentValue(int index, Class requiredType, String requiredName) {
      return this.getArgumentValue(index, requiredType, requiredName, (Set)null);
   }

   @Nullable
   public ValueHolder getArgumentValue(int index, @Nullable Class requiredType, @Nullable String requiredName, @Nullable Set usedValueHolders) {
      Assert.isTrue(index >= 0, "Index must not be negative");
      ValueHolder valueHolder = this.getIndexedArgumentValue(index, requiredType, requiredName);
      if (valueHolder == null) {
         valueHolder = this.getGenericArgumentValue(requiredType, requiredName, usedValueHolders);
      }

      return valueHolder;
   }

   public int getArgumentCount() {
      return this.indexedArgumentValues.size() + this.genericArgumentValues.size();
   }

   public boolean isEmpty() {
      return this.indexedArgumentValues.isEmpty() && this.genericArgumentValues.isEmpty();
   }

   public void clear() {
      this.indexedArgumentValues.clear();
      this.genericArgumentValues.clear();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof ConstructorArgumentValues)) {
         return false;
      } else {
         ConstructorArgumentValues that = (ConstructorArgumentValues)other;
         if (this.genericArgumentValues.size() == that.genericArgumentValues.size() && this.indexedArgumentValues.size() == that.indexedArgumentValues.size()) {
            Iterator it1 = this.genericArgumentValues.iterator();
            Iterator it2 = that.genericArgumentValues.iterator();

            while(it1.hasNext() && it2.hasNext()) {
               ValueHolder vh1 = (ValueHolder)it1.next();
               ValueHolder vh2 = (ValueHolder)it2.next();
               if (!vh1.contentEquals(vh2)) {
                  return false;
               }
            }

            Iterator var9 = this.indexedArgumentValues.entrySet().iterator();

            ValueHolder vh1;
            ValueHolder vh2;
            do {
               if (!var9.hasNext()) {
                  return true;
               }

               Map.Entry entry = (Map.Entry)var9.next();
               vh1 = (ValueHolder)entry.getValue();
               vh2 = (ValueHolder)that.indexedArgumentValues.get(entry.getKey());
            } while(vh2 != null && vh1.contentEquals(vh2));

            return false;
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      int hashCode = 7;

      Iterator var2;
      ValueHolder valueHolder;
      for(var2 = this.genericArgumentValues.iterator(); var2.hasNext(); hashCode = 31 * hashCode + valueHolder.contentHashCode()) {
         valueHolder = (ValueHolder)var2.next();
      }

      hashCode = 29 * hashCode;

      Map.Entry entry;
      for(var2 = this.indexedArgumentValues.entrySet().iterator(); var2.hasNext(); hashCode = 31 * hashCode + (((ValueHolder)entry.getValue()).contentHashCode() ^ ((Integer)entry.getKey()).hashCode())) {
         entry = (Map.Entry)var2.next();
      }

      return hashCode;
   }

   public static class ValueHolder implements BeanMetadataElement {
      @Nullable
      private Object value;
      @Nullable
      private String type;
      @Nullable
      private String name;
      @Nullable
      private Object source;
      private boolean converted = false;
      @Nullable
      private Object convertedValue;

      public ValueHolder(@Nullable Object value) {
         this.value = value;
      }

      public ValueHolder(@Nullable Object value, @Nullable String type) {
         this.value = value;
         this.type = type;
      }

      public ValueHolder(@Nullable Object value, @Nullable String type, @Nullable String name) {
         this.value = value;
         this.type = type;
         this.name = name;
      }

      public void setValue(@Nullable Object value) {
         this.value = value;
      }

      @Nullable
      public Object getValue() {
         return this.value;
      }

      public void setType(@Nullable String type) {
         this.type = type;
      }

      @Nullable
      public String getType() {
         return this.type;
      }

      public void setName(@Nullable String name) {
         this.name = name;
      }

      @Nullable
      public String getName() {
         return this.name;
      }

      public void setSource(@Nullable Object source) {
         this.source = source;
      }

      @Nullable
      public Object getSource() {
         return this.source;
      }

      public synchronized boolean isConverted() {
         return this.converted;
      }

      public synchronized void setConvertedValue(@Nullable Object value) {
         this.converted = value != null;
         this.convertedValue = value;
      }

      @Nullable
      public synchronized Object getConvertedValue() {
         return this.convertedValue;
      }

      private boolean contentEquals(ValueHolder other) {
         return this == other || ObjectUtils.nullSafeEquals(this.value, other.value) && ObjectUtils.nullSafeEquals(this.type, other.type);
      }

      private int contentHashCode() {
         return ObjectUtils.nullSafeHashCode(this.value) * 29 + ObjectUtils.nullSafeHashCode((Object)this.type);
      }

      public ValueHolder copy() {
         ValueHolder copy = new ValueHolder(this.value, this.type, this.name);
         copy.setSource(this.source);
         return copy;
      }
   }
}

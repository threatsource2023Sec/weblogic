package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;

public class MutablePropertyValues implements PropertyValues, Serializable {
   private final List propertyValueList;
   @Nullable
   private Set processedProperties;
   private volatile boolean converted = false;

   public MutablePropertyValues() {
      this.propertyValueList = new ArrayList(0);
   }

   public MutablePropertyValues(@Nullable PropertyValues original) {
      if (original != null) {
         PropertyValue[] pvs = original.getPropertyValues();
         this.propertyValueList = new ArrayList(pvs.length);
         PropertyValue[] var3 = pvs;
         int var4 = pvs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PropertyValue pv = var3[var5];
            this.propertyValueList.add(new PropertyValue(pv));
         }
      } else {
         this.propertyValueList = new ArrayList(0);
      }

   }

   public MutablePropertyValues(@Nullable Map original) {
      if (original != null) {
         this.propertyValueList = new ArrayList(original.size());
         original.forEach((attrName, attrValue) -> {
            this.propertyValueList.add(new PropertyValue(attrName.toString(), attrValue));
         });
      } else {
         this.propertyValueList = new ArrayList(0);
      }

   }

   public MutablePropertyValues(@Nullable List propertyValueList) {
      this.propertyValueList = (List)(propertyValueList != null ? propertyValueList : new ArrayList());
   }

   public List getPropertyValueList() {
      return this.propertyValueList;
   }

   public int size() {
      return this.propertyValueList.size();
   }

   public MutablePropertyValues addPropertyValues(@Nullable PropertyValues other) {
      if (other != null) {
         PropertyValue[] pvs = other.getPropertyValues();
         PropertyValue[] var3 = pvs;
         int var4 = pvs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PropertyValue pv = var3[var5];
            this.addPropertyValue(new PropertyValue(pv));
         }
      }

      return this;
   }

   public MutablePropertyValues addPropertyValues(@Nullable Map other) {
      if (other != null) {
         other.forEach((attrName, attrValue) -> {
            this.addPropertyValue(new PropertyValue(attrName.toString(), attrValue));
         });
      }

      return this;
   }

   public MutablePropertyValues addPropertyValue(PropertyValue pv) {
      for(int i = 0; i < this.propertyValueList.size(); ++i) {
         PropertyValue currentPv = (PropertyValue)this.propertyValueList.get(i);
         if (currentPv.getName().equals(pv.getName())) {
            pv = this.mergeIfRequired(pv, currentPv);
            this.setPropertyValueAt(pv, i);
            return this;
         }
      }

      this.propertyValueList.add(pv);
      return this;
   }

   public void addPropertyValue(String propertyName, Object propertyValue) {
      this.addPropertyValue(new PropertyValue(propertyName, propertyValue));
   }

   public MutablePropertyValues add(String propertyName, @Nullable Object propertyValue) {
      this.addPropertyValue(new PropertyValue(propertyName, propertyValue));
      return this;
   }

   public void setPropertyValueAt(PropertyValue pv, int i) {
      this.propertyValueList.set(i, pv);
   }

   private PropertyValue mergeIfRequired(PropertyValue newPv, PropertyValue currentPv) {
      Object value = newPv.getValue();
      if (value instanceof Mergeable) {
         Mergeable mergeable = (Mergeable)value;
         if (mergeable.isMergeEnabled()) {
            Object merged = mergeable.merge(currentPv.getValue());
            return new PropertyValue(newPv.getName(), merged);
         }
      }

      return newPv;
   }

   public void removePropertyValue(PropertyValue pv) {
      this.propertyValueList.remove(pv);
   }

   public void removePropertyValue(String propertyName) {
      this.propertyValueList.remove(this.getPropertyValue(propertyName));
   }

   public Iterator iterator() {
      return Collections.unmodifiableList(this.propertyValueList).iterator();
   }

   public Spliterator spliterator() {
      return Spliterators.spliterator(this.propertyValueList, 0);
   }

   public Stream stream() {
      return this.propertyValueList.stream();
   }

   public PropertyValue[] getPropertyValues() {
      return (PropertyValue[])this.propertyValueList.toArray(new PropertyValue[0]);
   }

   @Nullable
   public PropertyValue getPropertyValue(String propertyName) {
      Iterator var2 = this.propertyValueList.iterator();

      PropertyValue pv;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         pv = (PropertyValue)var2.next();
      } while(!pv.getName().equals(propertyName));

      return pv;
   }

   @Nullable
   public Object get(String propertyName) {
      PropertyValue pv = this.getPropertyValue(propertyName);
      return pv != null ? pv.getValue() : null;
   }

   public PropertyValues changesSince(PropertyValues old) {
      MutablePropertyValues changes = new MutablePropertyValues();
      if (old == this) {
         return changes;
      } else {
         Iterator var3 = this.propertyValueList.iterator();

         while(true) {
            PropertyValue newPv;
            PropertyValue pvOld;
            do {
               if (!var3.hasNext()) {
                  return changes;
               }

               newPv = (PropertyValue)var3.next();
               pvOld = old.getPropertyValue(newPv.getName());
            } while(pvOld != null && pvOld.equals(newPv));

            changes.addPropertyValue(newPv);
         }
      }
   }

   public boolean contains(String propertyName) {
      return this.getPropertyValue(propertyName) != null || this.processedProperties != null && this.processedProperties.contains(propertyName);
   }

   public boolean isEmpty() {
      return this.propertyValueList.isEmpty();
   }

   public void registerProcessedProperty(String propertyName) {
      if (this.processedProperties == null) {
         this.processedProperties = new HashSet(4);
      }

      this.processedProperties.add(propertyName);
   }

   public void clearProcessedProperty(String propertyName) {
      if (this.processedProperties != null) {
         this.processedProperties.remove(propertyName);
      }

   }

   public void setConverted() {
      this.converted = true;
   }

   public boolean isConverted() {
      return this.converted;
   }

   public boolean equals(Object other) {
      return this == other || other instanceof MutablePropertyValues && this.propertyValueList.equals(((MutablePropertyValues)other).propertyValueList);
   }

   public int hashCode() {
      return this.propertyValueList.hashCode();
   }

   public String toString() {
      PropertyValue[] pvs = this.getPropertyValues();
      StringBuilder sb = (new StringBuilder("PropertyValues: length=")).append(pvs.length);
      if (pvs.length > 0) {
         sb.append("; ").append(StringUtils.arrayToDelimitedString(pvs, "; "));
      }

      return sb.toString();
   }
}

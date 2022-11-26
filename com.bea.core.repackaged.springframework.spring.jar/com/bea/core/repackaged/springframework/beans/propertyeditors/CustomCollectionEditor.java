package com.bea.core.repackaged.springframework.beans.propertyeditors;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class CustomCollectionEditor extends PropertyEditorSupport {
   private final Class collectionType;
   private final boolean nullAsEmptyCollection;

   public CustomCollectionEditor(Class collectionType) {
      this(collectionType, false);
   }

   public CustomCollectionEditor(Class collectionType, boolean nullAsEmptyCollection) {
      Assert.notNull(collectionType, (String)"Collection type is required");
      if (!Collection.class.isAssignableFrom(collectionType)) {
         throw new IllegalArgumentException("Collection type [" + collectionType.getName() + "] does not implement [java.util.Collection]");
      } else {
         this.collectionType = collectionType;
         this.nullAsEmptyCollection = nullAsEmptyCollection;
      }
   }

   public void setAsText(String text) throws IllegalArgumentException {
      this.setValue(text);
   }

   public void setValue(@Nullable Object value) {
      if (value == null && this.nullAsEmptyCollection) {
         super.setValue(this.createCollection(this.collectionType, 0));
      } else if (value == null || this.collectionType.isInstance(value) && !this.alwaysCreateNewCollection()) {
         super.setValue(value);
      } else {
         Collection target;
         Collection target;
         if (value instanceof Collection) {
            target = (Collection)value;
            target = this.createCollection(this.collectionType, target.size());
            Iterator var4 = target.iterator();

            while(var4.hasNext()) {
               Object elem = var4.next();
               target.add(this.convertElement(elem));
            }

            super.setValue(target);
         } else if (value.getClass().isArray()) {
            int length = Array.getLength(value);
            target = this.createCollection(this.collectionType, length);

            for(int i = 0; i < length; ++i) {
               target.add(this.convertElement(Array.get(value, i)));
            }

            super.setValue(target);
         } else {
            target = this.createCollection(this.collectionType, 1);
            target.add(this.convertElement(value));
            super.setValue(target);
         }
      }

   }

   protected Collection createCollection(Class collectionType, int initialCapacity) {
      if (!collectionType.isInterface()) {
         try {
            return (Collection)ReflectionUtils.accessibleConstructor(collectionType).newInstance();
         } catch (Throwable var4) {
            throw new IllegalArgumentException("Could not instantiate collection class: " + collectionType.getName(), var4);
         }
      } else if (List.class == collectionType) {
         return new ArrayList(initialCapacity);
      } else {
         return (Collection)(SortedSet.class == collectionType ? new TreeSet() : new LinkedHashSet(initialCapacity));
      }
   }

   protected boolean alwaysCreateNewCollection() {
      return false;
   }

   protected Object convertElement(Object element) {
      return element;
   }

   @Nullable
   public String getAsText() {
      return null;
   }
}

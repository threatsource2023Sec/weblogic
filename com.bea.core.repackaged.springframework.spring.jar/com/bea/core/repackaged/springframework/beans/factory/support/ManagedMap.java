package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.BeanMetadataElement;
import com.bea.core.repackaged.springframework.beans.Mergeable;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ManagedMap extends LinkedHashMap implements Mergeable, BeanMetadataElement {
   @Nullable
   private Object source;
   @Nullable
   private String keyTypeName;
   @Nullable
   private String valueTypeName;
   private boolean mergeEnabled;

   public ManagedMap() {
   }

   public ManagedMap(int initialCapacity) {
      super(initialCapacity);
   }

   public void setSource(@Nullable Object source) {
      this.source = source;
   }

   @Nullable
   public Object getSource() {
      return this.source;
   }

   public void setKeyTypeName(@Nullable String keyTypeName) {
      this.keyTypeName = keyTypeName;
   }

   @Nullable
   public String getKeyTypeName() {
      return this.keyTypeName;
   }

   public void setValueTypeName(@Nullable String valueTypeName) {
      this.valueTypeName = valueTypeName;
   }

   @Nullable
   public String getValueTypeName() {
      return this.valueTypeName;
   }

   public void setMergeEnabled(boolean mergeEnabled) {
      this.mergeEnabled = mergeEnabled;
   }

   public boolean isMergeEnabled() {
      return this.mergeEnabled;
   }

   public Object merge(@Nullable Object parent) {
      if (!this.mergeEnabled) {
         throw new IllegalStateException("Not allowed to merge when the 'mergeEnabled' property is set to 'false'");
      } else if (parent == null) {
         return this;
      } else if (!(parent instanceof Map)) {
         throw new IllegalArgumentException("Cannot merge with object of type [" + parent.getClass() + "]");
      } else {
         Map merged = new ManagedMap();
         merged.putAll((Map)parent);
         merged.putAll(this);
         return merged;
      }
   }
}

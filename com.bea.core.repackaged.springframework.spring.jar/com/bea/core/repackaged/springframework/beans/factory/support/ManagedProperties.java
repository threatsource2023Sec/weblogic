package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.BeanMetadataElement;
import com.bea.core.repackaged.springframework.beans.Mergeable;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Properties;

public class ManagedProperties extends Properties implements Mergeable, BeanMetadataElement {
   @Nullable
   private Object source;
   private boolean mergeEnabled;

   public void setSource(@Nullable Object source) {
      this.source = source;
   }

   @Nullable
   public Object getSource() {
      return this.source;
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
      } else if (!(parent instanceof Properties)) {
         throw new IllegalArgumentException("Cannot merge with object of type [" + parent.getClass() + "]");
      } else {
         Properties merged = new ManagedProperties();
         merged.putAll((Properties)parent);
         merged.putAll(this);
         return merged;
      }
   }
}

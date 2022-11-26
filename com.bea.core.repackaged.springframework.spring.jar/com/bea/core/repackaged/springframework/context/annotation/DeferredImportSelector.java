package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface DeferredImportSelector extends ImportSelector {
   @Nullable
   default Class getImportGroup() {
      return null;
   }

   public interface Group {
      void process(AnnotationMetadata var1, DeferredImportSelector var2);

      Iterable selectImports();

      public static class Entry {
         private final AnnotationMetadata metadata;
         private final String importClassName;

         public Entry(AnnotationMetadata metadata, String importClassName) {
            this.metadata = metadata;
            this.importClassName = importClassName;
         }

         public AnnotationMetadata getMetadata() {
            return this.metadata;
         }

         public String getImportClassName() {
            return this.importClassName;
         }

         public boolean equals(@Nullable Object other) {
            if (this == other) {
               return true;
            } else if (other != null && this.getClass() == other.getClass()) {
               Entry entry = (Entry)other;
               return this.metadata.equals(entry.metadata) && this.importClassName.equals(entry.importClassName);
            } else {
               return false;
            }
         }

         public int hashCode() {
            return this.metadata.hashCode() * 31 + this.importClassName.hashCode();
         }

         public String toString() {
            return this.importClassName;
         }
      }
   }
}

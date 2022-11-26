package com.googlecode.cqengine.index.compound.support;

import java.util.Collections;
import java.util.List;

public class CompoundValueTuple {
   private final List attributeValues;
   private final int hashCode;

   public CompoundValueTuple(List attributeValues) {
      this.attributeValues = attributeValues;
      this.hashCode = attributeValues.hashCode();
   }

   public Iterable getAttributeValues() {
      return Collections.unmodifiableList(this.attributeValues);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof CompoundValueTuple)) {
         return false;
      } else {
         CompoundValueTuple that = (CompoundValueTuple)o;
         if (this.hashCode != that.hashCode) {
            return false;
         } else {
            return this.attributeValues.equals(that.attributeValues);
         }
      }
   }

   public int hashCode() {
      return this.hashCode;
   }

   public String toString() {
      return "CompoundValueTuple{attributeValues=" + this.attributeValues + ", hashCode=" + this.hashCode + '}';
   }
}

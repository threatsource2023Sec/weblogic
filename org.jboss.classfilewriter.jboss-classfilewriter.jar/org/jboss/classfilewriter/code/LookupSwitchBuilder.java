package org.jboss.classfilewriter.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class LookupSwitchBuilder {
   private final CodeLocation defaultLocation;
   private final AtomicReference defaultBranchEnd;
   private final List values = new ArrayList();

   public LookupSwitchBuilder() {
      this.defaultBranchEnd = new AtomicReference();
      this.defaultLocation = null;
   }

   public LookupSwitchBuilder(CodeLocation defaultLocation) {
      this.defaultLocation = defaultLocation;
      this.defaultBranchEnd = null;
   }

   public AtomicReference add(int value) {
      AtomicReference end = new AtomicReference();
      ValuePair vp = new ValuePair(value, end);
      this.values.add(vp);
      return end;
   }

   public LookupSwitchBuilder add(int value, CodeLocation location) {
      this.values.add(new ValuePair(value, location));
      return this;
   }

   public CodeLocation getDefaultLocation() {
      return this.defaultLocation;
   }

   public AtomicReference getDefaultBranchEnd() {
      return this.defaultBranchEnd;
   }

   public List getValues() {
      return Collections.unmodifiableList(this.values);
   }

   public static class ValuePair implements Comparable {
      private final int value;
      private final CodeLocation location;
      private final AtomicReference branchEnd;

      public ValuePair(int value, AtomicReference branchEnd) {
         this.value = value;
         this.location = null;
         this.branchEnd = branchEnd;
      }

      public ValuePair(int value, CodeLocation location) {
         this.value = value;
         this.location = location;
         this.branchEnd = null;
      }

      public int compareTo(ValuePair o) {
         return Integer.valueOf(this.value).compareTo(o.value);
      }

      public int getValue() {
         return this.value;
      }

      public CodeLocation getLocation() {
         return this.location;
      }

      public AtomicReference getBranchEnd() {
         return this.branchEnd;
      }
   }
}

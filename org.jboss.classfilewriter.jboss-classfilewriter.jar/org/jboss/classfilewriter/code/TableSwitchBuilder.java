package org.jboss.classfilewriter.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TableSwitchBuilder {
   private final CodeLocation defaultLocation;
   private final AtomicReference defaultBranchEnd;
   private final List values = new ArrayList();
   private final int low;
   private final int high;

   public TableSwitchBuilder(int low, int high) {
      this.low = low;
      this.high = high;
      this.defaultBranchEnd = new AtomicReference();
      this.defaultLocation = null;
   }

   public TableSwitchBuilder(CodeLocation defaultLocation, int low, int high) {
      this.defaultLocation = defaultLocation;
      this.low = low;
      this.high = high;
      this.defaultBranchEnd = null;
   }

   public AtomicReference add() {
      AtomicReference end = new AtomicReference();
      ValuePair vp = new ValuePair(end);
      this.values.add(vp);
      return end;
   }

   public TableSwitchBuilder add(CodeLocation location) {
      this.values.add(new ValuePair(location));
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

   public int getLow() {
      return this.low;
   }

   public int getHigh() {
      return this.high;
   }

   public static class ValuePair {
      private final CodeLocation location;
      private final AtomicReference branchEnd;

      public ValuePair(AtomicReference branchEnd) {
         this.location = null;
         this.branchEnd = branchEnd;
      }

      public ValuePair(CodeLocation location) {
         this.location = location;
         this.branchEnd = null;
      }

      public CodeLocation getLocation() {
         return this.location;
      }

      public AtomicReference getBranchEnd() {
         return this.branchEnd;
      }
   }
}

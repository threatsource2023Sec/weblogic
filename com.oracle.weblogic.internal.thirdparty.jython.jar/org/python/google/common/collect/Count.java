package org.python.google.common.collect;

import java.io.Serializable;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
final class Count implements Serializable {
   private int value;

   Count(int value) {
      this.value = value;
   }

   public int get() {
      return this.value;
   }

   public void add(int delta) {
      this.value += delta;
   }

   public int addAndGet(int delta) {
      return this.value += delta;
   }

   public void set(int newValue) {
      this.value = newValue;
   }

   public int getAndSet(int newValue) {
      int result = this.value;
      this.value = newValue;
      return result;
   }

   public int hashCode() {
      return this.value;
   }

   public boolean equals(@Nullable Object obj) {
      return obj instanceof Count && ((Count)obj).value == this.value;
   }

   public String toString() {
      return Integer.toString(this.value);
   }
}

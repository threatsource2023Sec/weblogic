package weblogic.management.deploy.internal.parallel;

import java.util.Deque;

public final class Bucket {
   public final boolean isParallel;
   public final Deque contents;

   public Bucket(boolean isParallel, Deque contents) {
      this.isParallel = isParallel;
      this.contents = contents;
   }

   public String toString() {
      return "[isParallel=" + this.isParallel + ", Contents: " + this.contents + "]";
   }
}

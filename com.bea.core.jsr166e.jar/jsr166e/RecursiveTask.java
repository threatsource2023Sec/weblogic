package jsr166e;

public abstract class RecursiveTask extends ForkJoinTask {
   private static final long serialVersionUID = 5232453952276485270L;
   Object result;

   protected abstract Object compute();

   public final Object getRawResult() {
      return this.result;
   }

   protected final void setRawResult(Object value) {
      this.result = value;
   }

   protected final boolean exec() {
      this.result = this.compute();
      return true;
   }
}

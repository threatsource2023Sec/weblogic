package jnr.ffi;

public abstract class Union extends Struct {
   protected Union(Runtime runtime) {
      super(runtime, true);
   }
}

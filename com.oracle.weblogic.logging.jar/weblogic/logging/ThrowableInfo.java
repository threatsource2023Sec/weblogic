package weblogic.logging;

import com.bea.logging.ThrowableWrapper;

public final class ThrowableInfo extends ThrowableWrapper {
   public ThrowableInfo(Throwable th) {
      super(th);
   }
}

package org.python.google.common.io;

import java.io.IOException;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public interface LineProcessor {
   @CanIgnoreReturnValue
   boolean processLine(String var1) throws IOException;

   Object getResult();
}

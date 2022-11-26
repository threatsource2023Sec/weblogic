package org.python.google.common.io;

import java.io.IOException;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public interface ByteProcessor {
   @CanIgnoreReturnValue
   boolean processBytes(byte[] var1, int var2, int var3) throws IOException;

   Object getResult();
}

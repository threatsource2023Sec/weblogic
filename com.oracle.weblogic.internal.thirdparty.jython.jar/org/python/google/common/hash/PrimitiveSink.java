package org.python.google.common.hash;

import java.nio.charset.Charset;
import org.python.google.common.annotations.Beta;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@CanIgnoreReturnValue
public interface PrimitiveSink {
   PrimitiveSink putByte(byte var1);

   PrimitiveSink putBytes(byte[] var1);

   PrimitiveSink putBytes(byte[] var1, int var2, int var3);

   PrimitiveSink putShort(short var1);

   PrimitiveSink putInt(int var1);

   PrimitiveSink putLong(long var1);

   PrimitiveSink putFloat(float var1);

   PrimitiveSink putDouble(double var1);

   PrimitiveSink putBoolean(boolean var1);

   PrimitiveSink putChar(char var1);

   PrimitiveSink putUnencodedChars(CharSequence var1);

   PrimitiveSink putString(CharSequence var1, Charset var2);
}

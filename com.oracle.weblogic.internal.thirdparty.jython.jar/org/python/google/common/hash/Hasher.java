package org.python.google.common.hash;

import java.nio.charset.Charset;
import org.python.google.common.annotations.Beta;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@CanIgnoreReturnValue
public interface Hasher extends PrimitiveSink {
   Hasher putByte(byte var1);

   Hasher putBytes(byte[] var1);

   Hasher putBytes(byte[] var1, int var2, int var3);

   Hasher putShort(short var1);

   Hasher putInt(int var1);

   Hasher putLong(long var1);

   Hasher putFloat(float var1);

   Hasher putDouble(double var1);

   Hasher putBoolean(boolean var1);

   Hasher putChar(char var1);

   Hasher putUnencodedChars(CharSequence var1);

   Hasher putString(CharSequence var1, Charset var2);

   Hasher putObject(Object var1, Funnel var2);

   HashCode hash();

   /** @deprecated */
   @Deprecated
   int hashCode();
}

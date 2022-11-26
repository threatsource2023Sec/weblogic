package org.python.google.common.hash;

import java.nio.charset.Charset;
import org.python.google.common.annotations.Beta;

@Beta
public interface HashFunction {
   Hasher newHasher();

   Hasher newHasher(int var1);

   HashCode hashInt(int var1);

   HashCode hashLong(long var1);

   HashCode hashBytes(byte[] var1);

   HashCode hashBytes(byte[] var1, int var2, int var3);

   HashCode hashUnencodedChars(CharSequence var1);

   HashCode hashString(CharSequence var1, Charset var2);

   HashCode hashObject(Object var1, Funnel var2);

   int bits();
}

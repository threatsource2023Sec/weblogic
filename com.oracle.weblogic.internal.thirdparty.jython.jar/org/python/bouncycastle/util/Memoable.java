package org.python.bouncycastle.util;

public interface Memoable {
   Memoable copy();

   void reset(Memoable var1);
}

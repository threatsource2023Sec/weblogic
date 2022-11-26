package com.ning.compress;

import java.io.IOException;

public abstract class Uncompressor {
   public abstract boolean feedCompressedData(byte[] var1, int var2, int var3) throws IOException;

   public abstract void complete() throws IOException;
}

package com.ning.compress;

import java.io.IOException;

public interface DataHandler {
   boolean handleData(byte[] var1, int var2, int var3) throws IOException;

   void allDataHandled() throws IOException;
}

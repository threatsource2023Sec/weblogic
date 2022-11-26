package com.sun.faces.application;

import java.io.IOException;
import java.io.Writer;

/** @deprecated */
public interface InterweavingResponse {
   void flushContentToWrappedResponse() throws IOException;

   void flushToWriter(Writer var1, String var2) throws IOException;

   void resetBuffers() throws IOException;

   boolean isBytes();

   boolean isChars();

   char[] getChars();

   byte[] getBytes();

   int getStatus();
}

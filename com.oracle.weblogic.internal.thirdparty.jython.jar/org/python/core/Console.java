package org.python.core;

import java.io.IOException;
import java.nio.charset.Charset;

public interface Console {
   void install() throws IOException;

   void uninstall() throws UnsupportedOperationException;

   String getEncoding();

   Charset getEncodingCharset();
}

package org.python.bouncycastle.est;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Source {
   InputStream getInputStream() throws IOException;

   OutputStream getOutputStream() throws IOException;

   Object getSession();

   void close() throws IOException;
}

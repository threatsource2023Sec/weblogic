package org.python.bouncycastle.crypto.tls;

import java.io.IOException;

public interface DatagramTransport {
   int getReceiveLimit() throws IOException;

   int getSendLimit() throws IOException;

   int receive(byte[] var1, int var2, int var3, int var4) throws IOException;

   void send(byte[] var1, int var2, int var3) throws IOException;

   void close() throws IOException;
}

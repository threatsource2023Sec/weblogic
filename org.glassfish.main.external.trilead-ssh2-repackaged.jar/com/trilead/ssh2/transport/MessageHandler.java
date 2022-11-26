package com.trilead.ssh2.transport;

import java.io.IOException;

public interface MessageHandler {
   void handleMessage(byte[] var1, int var2) throws IOException;
}

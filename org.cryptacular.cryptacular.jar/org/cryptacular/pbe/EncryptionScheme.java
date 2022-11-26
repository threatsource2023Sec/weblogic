package org.cryptacular.pbe;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface EncryptionScheme {
   byte[] encrypt(byte[] var1);

   void encrypt(InputStream var1, OutputStream var2) throws IOException;

   byte[] decrypt(byte[] var1);

   void decrypt(InputStream var1, OutputStream var2) throws IOException;
}

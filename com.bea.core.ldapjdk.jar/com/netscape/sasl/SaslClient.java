package com.netscape.sasl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SaslClient {
   String getMechanismName();

   byte[] createInitialResponse() throws SaslException;

   byte[] evaluateChallenge(byte[] var1) throws SaslException;

   boolean isComplete();

   InputStream getInputStream(InputStream var1) throws IOException;

   OutputStream getOutputStream(OutputStream var1) throws IOException;
}

package com.netscape.sasl.mechanisms;

import com.netscape.sasl.SaslClient;
import com.netscape.sasl.SaslException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SaslExternal implements SaslClient {
   private static final String MECHANISM_NAME = "EXTERNAL";

   public byte[] createInitialResponse() throws SaslException {
      return null;
   }

   public byte[] evaluateChallenge(byte[] var1) throws SaslException {
      return null;
   }

   public String getMechanismName() {
      return "EXTERNAL";
   }

   public boolean isComplete() {
      return true;
   }

   public InputStream getInputStream(InputStream var1) throws IOException {
      return var1;
   }

   public OutputStream getOutputStream(OutputStream var1) throws IOException {
      return var1;
   }
}

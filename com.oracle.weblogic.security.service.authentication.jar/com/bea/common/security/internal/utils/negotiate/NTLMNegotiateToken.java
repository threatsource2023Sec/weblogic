package com.bea.common.security.internal.utils.negotiate;

import com.bea.common.logger.spi.LoggerSpi;

public class NTLMNegotiateToken extends NegotiateToken {
   private static final Boolean DISCRIMINATE_TRUE;
   private static final byte[] NTLM_BYTES;

   private NTLMNegotiateToken() {
   }

   protected NTLMNegotiateToken(byte[] theRawBytes, String theBase64EncodedBytes, Object discriminateState, LoggerSpi log) throws NegotiateTokenException {
      super(2, theBase64EncodedBytes, theRawBytes, log);
   }

   public static Object discriminate(byte[] theRawBytes) {
      if (theRawBytes == null) {
         return null;
      } else {
         return theRawBytes.length >= NTLM_BYTES.length && theRawBytes[0] == NTLM_BYTES[0] && theRawBytes[1] == NTLM_BYTES[1] && theRawBytes[2] == NTLM_BYTES[2] && theRawBytes[3] == NTLM_BYTES[3] ? DISCRIMINATE_TRUE : null;
      }
   }

   static {
      DISCRIMINATE_TRUE = Boolean.TRUE;
      NTLM_BYTES = "NTLM".getBytes();
   }
}

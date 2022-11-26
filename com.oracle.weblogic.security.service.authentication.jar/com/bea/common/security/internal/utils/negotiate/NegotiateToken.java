package com.bea.common.security.internal.utils.negotiate;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.security.utils.negotiate.NegotiateTokenUtils;

public class NegotiateToken {
   public static final int TOKEN_TYPE_UNKNOWN = 0;
   public static final int TOKEN_TYPE_SPNEGO = 1;
   public static final int TOKEN_TYPE_NTLM = 2;
   public static final String TOKEN_NAME_UNKNOWN = "UNKNOWN";
   public static final String TOKEN_NAME_SPNEGO = "SPNEGO";
   public static final String TOKEN_NAME_NTLM = "NTLM";
   protected int tokenType = 0;
   protected String base64EncodedBytes = null;
   protected byte[] rawBytes = null;
   protected LoggerSpi log = null;

   public static NegotiateToken getInstance(String theBase64EncodedBytes, LoggerSpi log) {
      byte[] theRawBytes = NegotiateTokenUtils.base64Decode(theBase64EncodedBytes, log);
      return theRawBytes == null ? null : discriminateAndCreate(theRawBytes, theBase64EncodedBytes, log);
   }

   public String getTokenTypeName() {
      switch (this.tokenType) {
         case 0:
            return "UNKNOWN";
         case 1:
            return "SPNEGO";
         case 2:
            return "NTLM";
         default:
            return "UNKNOWN";
      }
   }

   protected NegotiateToken(int tokenType, String theBase64EncodedBytes, byte[] theRawBytes, LoggerSpi logger) {
      this.log = logger;
      this.tokenType = tokenType;
      this.setBase64EncodedAndRaw(theBase64EncodedBytes, theRawBytes);
   }

   protected NegotiateToken() {
      this.tokenType = 0;
   }

   public void setBase64TokenValue(String theBase64EncodedBytes) {
      this.setBase64Encoded(theBase64EncodedBytes);
   }

   public String getBase64Encoded() {
      return this.base64EncodedBytes;
   }

   public byte[] getRawBytes() {
      return this.rawBytes;
   }

   public int getTokenType() {
      return this.tokenType;
   }

   private void setBase64EncodedAndRaw(String theBase64EncodedBytes, byte[] theRawBytes) throws NegotiateTokenException {
      this.base64EncodedBytes = theBase64EncodedBytes;
      this.rawBytes = theRawBytes;
      this.validate();
   }

   protected void setBase64Encoded(String theBase64EncodedBytes) throws NegotiateTokenException {
      this.base64EncodedBytes = theBase64EncodedBytes;
      this.rawBytes = NegotiateTokenUtils.base64Decode(this.base64EncodedBytes, this.log);
      this.validate();
   }

   protected void setBase64Encoded(byte[] theRawBytes) throws NegotiateTokenException {
      this.rawBytes = theRawBytes;
      this.base64EncodedBytes = NegotiateTokenUtils.base64Encode(theRawBytes);
      this.validate();
   }

   protected void validate() throws NegotiateTokenException {
      if (this.rawBytes == null && this.base64EncodedBytes != null || this.rawBytes != null && this.base64EncodedBytes == null) {
         throw new NegotiateTokenException(ServiceLogger.getInconsistentTokenState());
      }
   }

   private static NegotiateToken discriminateAndCreate(byte[] theRawBytes, String theBase64EncodedBytes, LoggerSpi log) {
      try {
         Object discriminateState = null;
         discriminateState = NegotiateTokenUtils.discriminate(theRawBytes, log);
         if (discriminateState != null) {
            return new SPNEGONegotiateToken(theRawBytes, theBase64EncodedBytes, discriminateState, log);
         }

         discriminateState = NTLMNegotiateToken.discriminate(theRawBytes);
         if (discriminateState != null) {
            return new NTLMNegotiateToken(theRawBytes, theBase64EncodedBytes, discriminateState, log);
         }
      } catch (Throwable var4) {
         if (log.isDebugEnabled()) {
            log.debug("Unexpected error occured attempting to discriminate the token", var4);
         }
      }

      return null;
   }
}

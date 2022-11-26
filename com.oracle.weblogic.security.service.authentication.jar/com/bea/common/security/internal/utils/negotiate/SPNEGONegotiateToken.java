package com.bea.common.security.internal.utils.negotiate;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.security.utils.kerberos.KerberosException;
import com.bea.security.utils.kerberos.KerberosTokenHandler;
import com.bea.security.utils.negotiate.CredentialObject;
import com.bea.security.utils.negotiate.NegotiateTokenUtils;

public class SPNEGONegotiateToken extends NegotiateToken {
   private NegotiateTokenUtils.NegTokenInitInfo parseInfo;
   KerberosTokenHandler handler;

   private SPNEGONegotiateToken() {
   }

   protected SPNEGONegotiateToken(byte[] theRawBytes, String theBase64EncodedBytes, Object theDiscriminateState, LoggerSpi log) throws NegotiateTokenException {
      super(1, theBase64EncodedBytes, theRawBytes, log);
      if (theDiscriminateState != null && theDiscriminateState instanceof NegotiateTokenUtils.NegTokenInitInfo) {
         this.parseInfo = (NegotiateTokenUtils.NegTokenInitInfo)theDiscriminateState;
         this.handler = new KerberosTokenHandler(log);
      } else {
         throw new NegotiateTokenException(ServiceLogger.getInvalidSPNEGOParseInfo());
      }
   }

   public String getUsername() throws KerberosException {
      this.handler.acceptGssInitContextToken(this.parseInfo);
      return this.handler.getUsername();
   }

   public boolean getRequiresMore() {
      return this.handler.isMoreRequired();
   }

   public boolean getContextFlagMutual() {
      return this.parseInfo.contextFlagMutual;
   }

   public String getOutputToken() {
      byte[] gssToken = this.handler.getOutputToken();
      return NegotiateTokenUtils.encodeNegTokenTarg(gssToken, this.handler.isAcceptCompleted(), this.log);
   }

   public CredentialObject getDelegatedCredential() {
      return this.handler.getDelegatedCredential();
   }
}

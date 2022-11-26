package weblogic.connector.deploy;

import weblogic.connector.exception.RAException;
import weblogic.connector.external.SecurityIdentityInfo;
import weblogic.connector.security.work.CallbackHandlerFactoryImpl;
import weblogic.connector.security.work.SecurityContextPrincipalMapperImpl;
import weblogic.security.container.jca.jaspic.ConnectorCallbackHandler;

public class SecurityContextChangePackage implements ChangePackage {
   private SecurityIdentityInfo newSecurityIdentityInfo;
   CallbackHandlerFactoryImpl callbackHandlerFactory;
   ConnectorCallbackHandler.EISPrincipalMapper origMapper;

   public SecurityContextChangePackage(CallbackHandlerFactoryImpl callbackHandlerFactory, SecurityIdentityInfo newSecurityIdentityInfo) {
      this.newSecurityIdentityInfo = newSecurityIdentityInfo;
      this.callbackHandlerFactory = callbackHandlerFactory;
      this.origMapper = callbackHandlerFactory.getMapper();
   }

   public void prepare() throws RAException {
   }

   public void activate() throws RAException {
      ConnectorCallbackHandler.EISPrincipalMapper newMapper = null;
      if (this.newSecurityIdentityInfo != null && this.newSecurityIdentityInfo.isInboundMappingRequired()) {
         newMapper = new SecurityContextPrincipalMapperImpl(this.newSecurityIdentityInfo.getDefaultCallerPrincipalMapped(), this.newSecurityIdentityInfo.getInboundCallerPrincipalMapping(), this.newSecurityIdentityInfo.getDefaultGroupMappedPrincipal(), this.newSecurityIdentityInfo.getInboundGroupPrincipalMapping());
      }

      this.callbackHandlerFactory.setMapper(newMapper);
   }

   public void rollback() throws RAException {
      this.callbackHandlerFactory.setMapper(this.origMapper);
   }
}

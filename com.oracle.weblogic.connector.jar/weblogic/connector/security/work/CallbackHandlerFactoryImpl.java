package weblogic.connector.security.work;

import javax.security.auth.callback.CallbackHandler;

public class CallbackHandlerFactoryImpl implements CallbackHandlerFactory {
   private weblogic.security.container.jca.jaspic.ConnectorCallbackHandler.EISPrincipalMapper mapper;
   private boolean virtual;

   public CallbackHandlerFactoryImpl(weblogic.security.container.jca.jaspic.ConnectorCallbackHandler.EISPrincipalMapper mapper, boolean virtual) {
      this.mapper = mapper;
      this.virtual = virtual;
   }

   public weblogic.security.container.jca.jaspic.ConnectorCallbackHandler.EISPrincipalMapper getMapper() {
      return this.mapper;
   }

   public void setMapper(weblogic.security.container.jca.jaspic.ConnectorCallbackHandler.EISPrincipalMapper mapper) {
      this.mapper = mapper;
   }

   public boolean isVirtual() {
      return this.virtual;
   }

   public void setVirtual(boolean virtual) {
      this.virtual = virtual;
   }

   public CallbackHandler getCallBackHandler() {
      return new weblogic.security.container.jca.jaspic.ConnectorCallbackHandler(this.mapper, this.virtual);
   }
}

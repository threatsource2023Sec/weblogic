package weblogic.rmi;

public interface RmiContextConstants {
   String PROVIDER_CHANNEL = "weblogic.jndi.provider.channel";
   String RESPONSE_READ_TIMEOUT = "weblogic.jndi.responseReadTimeout";
   String CONNECT_TIMEOUT = "weblogic.jndi.connectTimeout";
   /** @deprecated */
   @Deprecated
   String REQUEST_TIMEOUT = "weblogic.jndi.requestTimeout";
   /** @deprecated */
   @Deprecated
   String RMI_TIMEOUT = "weblogic.rmi.clientTimeout";
}

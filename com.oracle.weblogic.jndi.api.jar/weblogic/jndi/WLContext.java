package weblogic.jndi;

import java.rmi.RemoteException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.rmi.RmiContextConstants;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface WLContext extends Context, RmiContextConstants {
   String PARTITION_NAME = "weblogic.partitionName";
   String PARTITION_ID = "weblogic.partitionId";
   String CREATE_INTERMEDIATE_CONTEXTS = "weblogic.jndi.createIntermediateContexts";
   String DELEGATE_ENVIRONMENT = "weblogic.jndi.delegate.environment";
   String PROVIDER_RJVM = "weblogic.jndi.provider.rjvm";
   String REPLICATE_BINDINGS = "weblogic.jndi.replicateBindings";
   String PIN_TO_PRIMARY_SERVER = "weblogic.jndi.pinToPrimaryServer";
   String SSL_ROOT_CA_FINGERPRINTS = "weblogic.jndi.ssl.root.ca.fingerprints";
   String SSL_SERVER_NAME = "weblogic.jndi.ssl.server.name";
   String SSL_CLIENT_CERTIFICATE = "weblogic.jndi.ssl.client.certificate";
   String SSL_CLIENT_KEY_PASSWORD = "weblogic.jndi.ssl.client.key_password";
   String ENABLE_CALL_BY_REFERENCE = "weblogic.jndi.enable.call_by_reference";
   String ENABLE_SERVER_AFFINITY = "weblogic.jndi.enableServerAffinity";
   String RELAX_VERSION_LOOKUP = "weblogic.jndi.relaxVersionLookup";
   String ALLOW_EXTERNAL_APP_LOOKUP = "weblogic.jndi.allowExternalAppLookup";
   String ALLOW_GLOBAL_RESOURCE_LOOKUP = "weblogic.jndi.allowGlobalResourceLookup";
   String VERSION_LOOKUP_APPLICATION_ID = "weblogic.jndi.lookupApplicationId";
   String FORCE_RESOLVE_DNS_NAME = "weblogic.jndi.forceResolveDNSName";
   String ENABLE_DEFAULT_USER = "weblogic.jndi.enableDefaultUser";
   String DISABLE_LOGGING_WARNING_MSG = "weblogic.jndi.disableLoggingOfWarningMsg";
   String IDENTITY_DOMAIN = "weblogic.jndi.identityDomain";
   String CREATE_CONTEXT_RETRY_TIME = "weblogic.jndi.createContextRetry.time";
   String CREATE_CONTEXT_RETRY_INTERVAL = "weblogic.jndi.createContextRetry.interval";

   String getNameInNamespace(String var1) throws NamingException;

   void unbind(Name var1, Object var2) throws NamingException;

   void unbind(String var1, Object var2) throws NamingException;

   void rebind(String var1, Object var2, Object var3) throws NamingException;

   Object lookup(String var1, AuthenticatedSubject var2) throws NamingException, RemoteException;

   void bind(String var1, Object var2, boolean var3) throws NamingException;

   void bind(Name var1, Object var2, boolean var3) throws NamingException;
}

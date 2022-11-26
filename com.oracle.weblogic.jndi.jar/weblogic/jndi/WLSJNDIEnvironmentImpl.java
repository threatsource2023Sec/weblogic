package weblogic.jndi;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.List;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.common.internal.ReplacerObjectInputStream;
import weblogic.common.internal.ReplacerObjectOutputStream;
import weblogic.jndi.internal.BuiltinTransportableObjectFactory;
import weblogic.jndi.internal.JNDIEnvironment;
import weblogic.jndi.internal.JNDIHelper;
import weblogic.jndi.internal.RemoteContextFactory;
import weblogic.jndi.internal.RemoteContextFactoryImpl;
import weblogic.jndi.internal.ThreadEnvironment;
import weblogic.jndi.internal.SSL.SSLProxy;
import weblogic.jndi.internal.SSL.WLSSSLProxyImpl;
import weblogic.kernel.Kernel;
import weblogic.management.EncryptionHelper;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.acl.internal.Security;
import weblogic.security.internal.encryption.EncryptionServiceException;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SubjectManagerImpl;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.internal.TransactionHelperImpl;
import weblogic.utils.io.Resolver;

public class WLSJNDIEnvironmentImpl extends JNDIEnvironment {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public SSLProxy getSSLProxy() {
      return new WLSSSLProxyImpl();
   }

   public Object copyObjectViaSerialization(Object obj) throws IOException, ClassNotFoundException {
      return JNDIHelper.copyObject(obj);
   }

   public void prepareKernel() {
      Kernel.ensureInitialized();
   }

   public void nullSSLClientCertificate() {
      Security.setSSLClientCertificate((InputStream[])null);
   }

   public void loadTransportableFactories(List transportableFactories) throws ConfigurationException {
      String[] factoryList = ManagementService.getRuntimeAccess(kernelId).getServer().getJNDITransportableObjectFactoryList();
      Object builtinFactory = new BuiltinTransportableObjectFactory();
      synchronized(transportableFactories) {
         if (factoryList != null) {
            for(int i = 0; i < factoryList.length; ++i) {
               String factoryName = factoryList[i];

               ConfigurationException ce;
               try {
                  transportableFactories.add(Class.forName(factoryName).newInstance());
               } catch (ClassNotFoundException var10) {
                  ce = new ConfigurationException("Failed to find class \"" + factoryName);
                  ce.setRootCause(var10);
                  throw ce;
               } catch (InstantiationException var11) {
                  ce = new ConfigurationException("Failed to instantiate \"" + factoryName + ".  Make sure it has a public default constructor.");
                  ce.setRootCause(var11);
                  throw ce;
               } catch (IllegalAccessException var12) {
                  ce = new ConfigurationException("Failed to instantiate \"" + factoryName + " because the default constuctor is not public.");
                  ce.setRootCause(var12);
                  throw ce;
               }
            }
         }

         if (builtinFactory != null) {
            transportableFactories.add(builtinFactory);
         }

      }
   }

   public Context getDelegateContext(ServerIdentity hostVM, Environment env, String subCtxName) throws RemoteException, NamingException {
      RemoteContextFactory stub = (RemoteContextFactory)StubFactory.getStub(RemoteContextFactoryImpl.class, hostVM);
      return stub.getContext(env.getRemoteProperties(), subCtxName);
   }

   public void prepareSubjectManager() {
      SubjectManagerImpl.ensureInitialized();
   }

   public void activateTransactionHelper() {
      TransactionHelper.pushTransactionHelper(new TransactionHelperImpl());
   }

   public void deactivateTransactionHelper() {
      TransactionHelper.popTransactionHelper();
   }

   public void pushThreadEnvironment(Environment env) {
      ThreadEnvironment.push(env);
   }

   public Environment popThreadEnvironment() {
      return ThreadEnvironment.pop();
   }

   public void pushSubject(AuthenticatedSubject kernelIdentity, AuthenticatedSubject userIdentity) {
      SecurityServiceManager.pushSubject(kernelIdentity, userIdentity);
   }

   public void popSubject(AuthenticatedSubject kernelIdentity) {
      SecurityServiceManager.popSubject(kernelIdentity);
   }

   public AuthenticatedSubject getCurrentSubject(AuthenticatedSubject kernelIdentity) {
      return SecurityServiceManager.getCurrentSubject(kernelIdentity);
   }

   public AuthenticatedSubject getASFromAU(AuthenticatedUser au) {
      return SecurityServiceManager.getASFromAU(au);
   }

   public ObjectOutput getReplacerObjectOutputStream(ObjectOutput out) throws IOException {
      ReplacerObjectOutputStream replOut = new ReplacerObjectOutputStream((OutputStream)out, RemoteObjectReplacer.getReplacer());
      replOut.setServerChannel(ServerChannelManager.findDefaultLocalServerChannel());
      return replOut;
   }

   public ObjectInput getReplacerObjectInputStream(ObjectInput in) throws IOException {
      return new ReplacerObjectInputStream((InputStream)in, RemoteObjectReplacer.getReplacer(), (Resolver)null);
   }

   public byte[] encryptionHelperDecrypt(byte[] credential, AuthenticatedSubject id) throws EncryptionServiceException {
      return EncryptionHelper.decrypt(credential, id);
   }

   public byte[] encryptionHelperClear(byte[] credential) {
      return EncryptionHelper.clear(credential);
   }
}

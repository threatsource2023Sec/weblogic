package weblogic.jndi;

import com.bea.core.security.managers.ManagerFactory;
import com.bea.core.security.managers.SubjectManagerFactory;
import com.bea.core.security.managers.internal.ManagerFactoryImpl;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.net.ssl.SSLContext;
import weblogic.jndi.internal.JNDIEnvironment;
import weblogic.jndi.internal.RemoteContextFactory;
import weblogic.jndi.internal.ThreadEnvironment;
import weblogic.jndi.internal.SSL.ClientSSLProxyImpl;
import weblogic.jndi.internal.SSL.SSLProxy;
import weblogic.protocol.ServerIdentity;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.extensions.server.CBVInputStream;
import weblogic.rmi.extensions.server.CBVOutputStream;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.internal.encryption.EncryptionServiceException;
import weblogic.security.subject.SubjectManager;

public class WLSClientJNDIEnvironmentImpl extends JNDIEnvironment {
   public void nullSSLClientCertificate() {
   }

   public SSLProxy getSSLProxy() {
      return new ClientSSLProxyImpl();
   }

   public Object copyObjectViaSerialization(Object object) throws IOException, ClassNotFoundException {
      CBVOutputStream out = new CBVOutputStream();
      out.writeObject(object);
      out.flush();
      CBVInputStream in = out.makeCBVInputStream();
      return in.readObject();
   }

   public void prepareKernel() {
   }

   public void loadTransportableFactories(List transportableFactories) throws ConfigurationException {
   }

   public Context getDelegateContext(ServerIdentity hostVM, Environment env, String subCtxName) throws RemoteException, NamingException {
      if (hostVM.isLocal()) {
         throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
      } else {
         RemoteContextFactory stub = (RemoteContextFactory)StubFactory.getStub(RemoteContextFactory.class, hostVM);
         return stub.getContext(env.getRemoteProperties(), subCtxName);
      }
   }

   public void prepareSubjectManager() {
   }

   public void activateTransactionHelper() {
   }

   public void deactivateTransactionHelper() {
   }

   public void pushThreadEnvironment(Environment env) {
      if (env != null && env.getSecurityCredentials() != null && env.getSecurityCredentials() instanceof SSLContext) {
         RJVMEnvironment.getEnvironment().setSSLContext((SSLContext)env.getSecurityCredentials());
         env.setSecurityCredentials((Object)null);
         ThreadEnvironment.push(env);
      } else {
         ThreadEnvironment.push(env);
      }
   }

   public Environment popThreadEnvironment() {
      Environment env = ThreadEnvironment.pop();
      if (env == null) {
         return env;
      } else {
         if (RJVMEnvironment.getEnvironment().getSSLContext() != null) {
            env.setSecurityCredentials(RJVMEnvironment.getEnvironment().getSSLContext());
         }

         RJVMEnvironment.getEnvironment().setSSLContext((Object)null);
         return env;
      }
   }

   public void pushSubject(AuthenticatedSubject kernelIdentity, AuthenticatedSubject userIdentity) {
      SubjectManager.getSubjectManager().pushSubject(kernelIdentity, userIdentity);
   }

   public void popSubject(AuthenticatedSubject kernelIdentity) {
      SubjectManager.getSubjectManager().popSubject(kernelIdentity);
   }

   public AuthenticatedSubject getCurrentSubject(AuthenticatedSubject kernelIdentity) {
      return (AuthenticatedSubject)SubjectManager.getSubjectManager().getCurrentSubject(kernelIdentity);
   }

   public AuthenticatedSubject getASFromAU(AuthenticatedUser au) {
      return (AuthenticatedSubject)au;
   }

   public ObjectOutput getReplacerObjectOutputStream(ObjectOutput out) throws IOException {
      return out;
   }

   public ObjectInput getReplacerObjectInputStream(ObjectInput in) throws IOException {
      return in;
   }

   public byte[] encryptionHelperDecrypt(byte[] credential, AuthenticatedSubject id) throws EncryptionServiceException {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   public byte[] encryptionHelperClear(byte[] credential) {
      throw new UnsupportedOperationException("This method is not supported on the standalone WebLogic client");
   }

   static {
      ManagerFactory.setInstance(new ManagerFactoryImpl());
      AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            SubjectManager.installCESubjectManager(SubjectManagerFactory.getInstance().getSubjectManager());
            return null;
         }
      });
   }
}

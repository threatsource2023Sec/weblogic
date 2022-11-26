package weblogic.security.service;

import java.util.Vector;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.Resource;

public interface CredentialManager {
   Object[] getCredentials(AuthenticatedSubject var1, AuthenticatedSubject var2, Resource var3, ContextHandler var4, String var5);

   Object[] getCredentials(AuthenticatedSubject var1, String var2, Resource var3, ContextHandler var4, String var5);

   /** @deprecated */
   @Deprecated
   Vector getCredentials(AuthenticatedSubject var1, AuthenticatedSubject var2, Resource var3, String[] var4) throws NotYetInitializedException, InvalidParameterException;

   /** @deprecated */
   @Deprecated
   Vector getCredentials(AuthenticatedSubject var1, String var2, Resource var3, String[] var4);

   boolean isVersionableApplicationSupported();

   void createApplicationVersion(String var1, String var2) throws ApplicationVersionCreationException;

   void deleteApplicationVersion(String var1) throws ApplicationVersionRemovalException;

   void deleteApplication(String var1) throws ApplicationRemovalException;
}

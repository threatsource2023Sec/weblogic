package weblogic.cacheprovider.coherence;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.x500.X500Principal;
import javax.security.auth.x500.X500PrivateCredential;
import weblogic.coherence.api.internal.CoherenceException;
import weblogic.coherence.descriptor.CoherenceKeyStoreBean;
import weblogic.coherence.descriptor.wl.CoherenceKeystoreParamsBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.pki.keystore.WLSKeyStoreFactory;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.utils.KeyStoreConfigurationHelper;
import weblogic.security.utils.KeyStoreInfo;
import weblogic.security.utils.MBeanKeyStoreConfiguration;

public class SecurityHelper {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static boolean isInitialized = false;
   private static Exception PendingException = null;
   private static String signatureAlgorithm = "MD5withRSA";
   private static final KeyStore keystore = getWLSKeyStore();

   public static Subject getCoherenceKernel(AuthenticatedSubject kernel) throws Exception {
      assertIfNotKernel(kernel);
      if (PendingException != null) {
         throw PendingException;
      } else {
         Subject cohKernel = SecurityHelper.SingletonMaker.COH_KERNEL_ID;
         if (cohKernel == null) {
            throw PendingException;
         } else {
            isInitialized = true;
            return cohKernel;
         }
      }
   }

   public static void createCoherenceKernel(AuthenticatedSubject kernel) throws Exception {
      if (!isInitialized) {
         getCoherenceKernel(kernel);
      }

   }

   public static String getSignatureAlgorithm(AuthenticatedSubject kernel) throws Exception {
      createCoherenceKernel(kernel);
      return signatureAlgorithm;
   }

   private static KeyStore getWLSKeyStore() {
      KeyStoreConfigurationHelper helper = new KeyStoreConfigurationHelper(MBeanKeyStoreConfiguration.getInstance());
      KeyStoreInfo info = helper.getIdentityKeyStore();
      String ksType = info.getType();
      char[] ksPwd = info.getPassPhrase();
      KeyStore ks = null;

      try {
         String keyStoreSource;
         if (WLSKeyStoreFactory.isFileBasedKeyStore(ksType)) {
            File ksFile = getFile(info.getFileName());
            keyStoreSource = ksFile.getAbsolutePath();
         } else {
            keyStoreSource = info.getFileName();
         }

         ks = WLSKeyStoreFactory.getKeyStoreInstance(KERNEL_ID, ksType, keyStoreSource, ksPwd);
      } catch (Exception var8) {
         PendingException = new CoherenceException("unable to obtain keystore: " + var8);
      }

      return ks;
   }

   private static void assertIfNotKernel(AuthenticatedSubject as) {
      if (as != KERNEL_ID) {
         throw new AssertionError("The internal method that you have invoked is not available unless you are running as Kernel!\nYou are running as: " + as);
      }
   }

   public static CoherenceKeyStoreBean getIdentityKeyStoreBean(ServerMBean server, KeyStoreConfigurationHelper helper) throws CoherenceException {
      KeyStoreInfo identityKeyStoreInfo = helper.getIdentityKeyStore();
      if (identityKeyStoreInfo == null) {
         throw new CoherenceException("Null Identity KeyStore. Invalid SSL configuration.");
      } else {
         CoherenceKeyStoreBean identityBean = new CoherenceKeyStoreBean();
         identityBean.setKeyStoreFile(getFile(identityKeyStoreInfo.getFileName()));
         identityBean.setKeyStoreType(identityKeyStoreInfo.getType());
         identityBean.setPassPhrase(identityKeyStoreInfo.getPassPhrase());
         identityBean.setIdentityPassPhrase(helper.getIdentityPrivateKeyPassPhrase());
         identityBean.setKeyStore(keystore);
         return identityBean;
      }
   }

   public static CoherenceKeyStoreBean[] getTrustKeyStoreBeans(ServerMBean server, KeyStoreConfigurationHelper helper) throws CoherenceException {
      KeyStoreInfo[] trustKeyStoreInfo = helper.getTrustKeyStores();
      if (trustKeyStoreInfo != null && trustKeyStoreInfo.length != 0) {
         CoherenceKeyStoreBean[] trustBeans = new CoherenceKeyStoreBean[trustKeyStoreInfo.length];
         int i = 0;
         KeyStoreInfo[] var5 = trustKeyStoreInfo;
         int var6 = trustKeyStoreInfo.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            KeyStoreInfo trustKeyStore = var5[var7];
            CoherenceKeyStoreBean trustBean = new CoherenceKeyStoreBean();
            trustBean.setKeyStoreFile(getFile(trustKeyStore.getFileName()));
            trustBean.setKeyStoreType(trustKeyStore.getType());
            trustBean.setPassPhrase(trustKeyStore.getPassPhrase());
            trustBeans[i++] = trustBean;
         }

         return trustBeans;
      } else {
         throw new CoherenceException("Null Trust KeyStore. Invalid SSL configuration.");
      }
   }

   private static File getFile(String fileName) throws CoherenceException {
      File file = new File(fileName);
      if (!file.exists()) {
         throw new CoherenceException(fileName + " does not exist.");
      } else {
         return file;
      }
   }

   private static class SingletonMaker {
      private static final Subject COH_KERNEL_ID = createCoherenceKernel();

      private static Subject createCoherenceKernel() {
         try {
            ServerMBean server = CoherenceClusterManager.getServerMBean();
            CoherenceKeystoreParamsBean ksParamsBean = null;
            String cohAlias = null;
            if (server != null) {
               ClusterMBean cluster = server.getCluster();
               CoherenceClusterSystemResourceMBean cohSR = cluster != null && cluster.getCoherenceClusterSystemResource() != null ? cluster.getCoherenceClusterSystemResource() : server.getCoherenceClusterSystemResource();
               ksParamsBean = cohSR.getCoherenceClusterResource().getCoherenceClusterParams().getCoherenceKeystoreParams();
               cohAlias = ksParamsBean.getCoherenceIdentityAlias();
            }

            KeyStoreConfigurationHelper helper = new KeyStoreConfigurationHelper(MBeanKeyStoreConfiguration.getInstance());
            KeyStore ks = SecurityHelper.keystore;
            Subject kernelSubject;
            char[] keyPwd;
            if (cohAlias != null && !cohAlias.isEmpty()) {
               keyPwd = ksParamsBean.getCoherencePrivateKeyPassPhrase().toCharArray();
               verifyPrivateKeyPassPhrase(keyPwd);
               kernelSubject = createSubject(cohAlias, keyPwd, ks);
            } else {
               cohAlias = helper.getIdentityAlias();
               keyPwd = helper.getIdentityPrivateKeyPassPhrase();
               verifyPrivateKeyPassPhrase(keyPwd);
               kernelSubject = createSubject(cohAlias, keyPwd, ks);
            }

            return kernelSubject;
         } catch (Exception var7) {
            SecurityHelper.PendingException = var7;
            return null;
         }
      }

      private static void verifyPrivateKeyPassPhrase(char[] keyPwd) throws CoherenceException {
         if (keyPwd == null) {
            throw new CoherenceException("Unable to obtain private key passphrase: must configure private key passphrase for Coherence Security Framework");
         }
      }

      private static Subject createSubject(String alias, char[] passPhrase, KeyStore ks) throws GeneralSecurityException, CoherenceException, IOException {
         PrivateKey privateKey = (PrivateKey)ks.getKey(alias, passPhrase);
         if (privateKey == null) {
            throw new GeneralSecurityException("Invalid alias name: " + alias);
         } else {
            Certificate[] acert = ks.getCertificateChain(alias);
            Set principals = new HashSet();
            Set publicCreds = new HashSet();
            Set privateCreds = new HashSet();
            if (acert != null && acert.length > 0) {
               Certificate cert = acert[0];
               if (cert instanceof X509Certificate) {
                  X509Certificate certX509 = (X509Certificate)cert;
                  SecurityHelper.signatureAlgorithm = certX509.getSigAlgName();
                  X500Principal principal = new X500Principal(certX509.getIssuerDN().getName());
                  CertificateFactory factory = CertificateFactory.getInstance("X.509");
                  CertPath certPath = factory.generateCertPath(Arrays.asList(acert));
                  principals.add(principal);
                  publicCreds.add(certPath);
                  privateCreds.add(new X500PrivateCredential(certX509, privateKey, alias));
               } else {
                  publicCreds.add(cert);
                  privateCreds.add(privateKey);
                  String algorithm = privateKey.getAlgorithm();
                  if (algorithm.equals("DSA")) {
                     SecurityHelper.signatureAlgorithm = "SHA1withDSA";
                  } else if (algorithm.equals("RSA")) {
                     SecurityHelper.signatureAlgorithm = "MD5withRSA";
                  } else {
                     SecurityHelper.signatureAlgorithm = algorithm;
                  }
               }
            }

            return new Subject(true, principals, publicCreds, privateCreds);
         }
      }
   }
}

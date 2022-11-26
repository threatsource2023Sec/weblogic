package weblogic.security.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Properties;
import org.jvnet.hk2.annotations.Service;
import utils.CertGen;
import weblogic.management.bootstrap.BootStrap;
import weblogic.security.internal.KeyStoreInstallationService;

public final class KeyStoreInstall {
   public static void initDefaultKeyStore() throws Exception {
      initDefaultKeyStore((String)null);
   }

   public static void initDefaultKeyStore(String cn) throws Exception {
      if (cn == null) {
         cn = InetAddress.getLocalHost().getHostName();
      }

      Properties nameProps = new Properties();
      nameProps.put("x500name.commonname", cn);
      initDefaultKeyStore("DemoIdentity.jks", "DemoIdentityKeyStorePassPhrase", "DemoIdentity", "DemoIdentityPassPhrase", nameProps);
   }

   public static void initDefaultKeyStore(String ksFileName, String ksPwd, String keyAlias, String keyPwd, Properties nameProps) throws Exception {
      boolean exportKeyStrengthUsed = false;
      CertGen cg = new CertGen(exportKeyStrengthUsed);
      cg.generateCertificate(nameProps);
      Certificate[] certChain = new Certificate[]{cg.getSubjectCertificate()};
      PrivateKey key = cg.getSubjectPrivateKey();
      KeyStore ks = KeyStore.getInstance("jks");
      ks.load((InputStream)null, ksPwd.toCharArray());
      ks.setKeyEntry(keyAlias, key, keyPwd.toCharArray(), certChain);
      File ksFile = new File(new File(BootStrap.getWebLogicHome(), "lib"), ksFileName);
      FileOutputStream ksOut = new FileOutputStream(ksFile);
      ks.store(ksOut, ksPwd.toCharArray());
      ksOut.close();
   }

   public static void main(String[] args) throws Exception {
      String cn = args.length > 0 ? args[0] : null;
      initDefaultKeyStore(cn);
   }

   @Service
   private static class KeyStoreInstallationServiceImpl implements KeyStoreInstallationService {
      public void initDefaultKeyStore() throws Exception {
         KeyStoreInstall.initDefaultKeyStore();
      }
   }
}

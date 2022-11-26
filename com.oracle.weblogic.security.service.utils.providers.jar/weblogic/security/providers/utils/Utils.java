package weblogic.security.providers.utils;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceNotFoundException;
import com.bea.common.engine.Services;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.legacy.ExtendedSecurityServices;
import com.bea.common.security.legacy.spi.LegacyConfigInfoSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.service.PasswordValidationService;
import com.bea.common.security.service.SAMLKeyService;
import com.bea.common.security.utils.CommonUtils;
import com.bea.common.store.bootstrap.BootStrapService;
import com.bea.common.store.service.StoreService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.security.auth.Subject;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
import weblogic.security.principal.IdentityDomainPrincipal;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.SecurityServices;

public final class Utils {
   private static boolean DUMP_CTX = false;
   private static boolean DUMP_CTX_IS_SET = false;
   private static final char[] regChars = new char[]{'.', '[', ']', '^', '{', '}', '$', '?', '+', '|', '(', ')'};
   private static final int REQUIRED_PADDED_BYTE_LENGTH = 32;

   public static File getSecurityDir(String domainRootDir) {
      return new File(domainRootDir, "security");
   }

   public static File getServerDir(String domainRootDir, String serverName) {
      return serverName == null ? new File(domainRootDir) : new File(domainRootDir + File.separator + "servers", serverName);
   }

   public static final boolean logContextHandlerEnabled() {
      if (!DUMP_CTX_IS_SET) {
         DUMP_CTX = Boolean.getBoolean("weblogic.security.DumpContextHandler");
         DUMP_CTX_IS_SET = true;
      }

      return DUMP_CTX;
   }

   public static void logContextHandler(String identifier, LoggerSpi log, ContextHandler handler) {
      if (log != null && log.isDebugEnabled()) {
         if (handler == null) {
            log.debug("ContextHandler for " + identifier + "is null.");
         } else {
            log.debug("Logging ContextHandler for " + identifier);
            String[] names = handler.getNames();

            for(int i = 0; i < names.length; ++i) {
               log.debug("\t" + names[i] + "=" + handler.getValue(names[i]).toString());
            }
         }
      }

   }

   public static String displaySubject(Subject subject) {
      StringBuffer buf = new StringBuffer("Subject: ");
      if (subject != null) {
         Set principals = subject.getPrincipals();
         buf.append(principals.size()).append("\n");

         for(Iterator pIter = principals.iterator(); pIter.hasNext(); buf.append("\")\n")) {
            Principal p = (Principal)pIter.next();
            buf.append("\tPrincipal = ").append(p.getClass().getName());
            buf.append("(\"");
            String userName = null;
            if (p instanceof IdentityDomainPrincipal) {
               userName = p.toString();
            } else {
               userName = p.getName();
            }

            if (userName != null) {
               buf.append(userName);
            }
         }
      } else {
         buf.append("null");
      }

      return buf.toString();
   }

   public static String getDomainName(SecurityServices secServices) {
      LegacyConfigInfoSpi legacyConfigInfoSpi = ((ExtendedSecurityServices)secServices).getLegacyConfig();
      return legacyConfigInfoSpi.getDomainName();
   }

   public static String getRootDirectory(SecurityServices secServices) {
      LegacyConfigInfoSpi legacyConfigInfoSpi = ((ExtendedSecurityServices)secServices).getLegacyConfig();
      return legacyConfigInfoSpi.getRootDirectory();
   }

   public static String getServerName(SecurityServices secServices) {
      LegacyConfigInfoSpi legacyConfigInfoSpi = ((ExtendedSecurityServices)secServices).getLegacyConfig();
      return legacyConfigInfoSpi.getServerName();
   }

   public static StoreService getStoreService(SecurityServices secServices) {
      ExtendedSecurityServices xSecurityService = (ExtendedSecurityServices)secServices;
      LegacyConfigInfoSpi legacyConfigInfoSpi = xSecurityService.getLegacyConfig();
      Services services = xSecurityService.getServices();

      try {
         return (StoreService)services.getService(legacyConfigInfoSpi.getStoreServiceName());
      } catch (ServiceNotFoundException var5) {
      } catch (ServiceInitializationException var6) {
      }

      return null;
   }

   public static BootStrapService getBootStrapService(SecurityServices secServices) {
      ExtendedSecurityServices xSecurityService = (ExtendedSecurityServices)secServices;
      LegacyConfigInfoSpi legacyConfigInfoSpi = xSecurityService.getLegacyConfig();
      Services services = xSecurityService.getServices();

      try {
         return (BootStrapService)services.getService(legacyConfigInfoSpi.getBootStrapServiceName());
      } catch (ServiceNotFoundException var5) {
      } catch (ServiceInitializationException var6) {
      }

      return null;
   }

   public static LegacyEncryptorSpi getLegacyEncryptorSpi(SecurityServices secServices) {
      ExtendedSecurityServices xSecurityService = (ExtendedSecurityServices)secServices;
      LegacyConfigInfoSpi legacyConfigInfoSpi = xSecurityService.getLegacyConfig();
      return legacyConfigInfoSpi.getLegacyEncryptor();
   }

   public static SAMLKeyService getSAMLKeyService(SecurityServices secServices) {
      ExtendedSecurityServices xSecurityService = (ExtendedSecurityServices)secServices;
      LegacyConfigInfoSpi legacyConfigInfoSpi = xSecurityService.getLegacyConfig();
      Services services = xSecurityService.getServices();

      try {
         return (SAMLKeyService)services.getService(legacyConfigInfoSpi.getSAMLKeyServiceName());
      } catch (ServiceNotFoundException var5) {
      } catch (ServiceInitializationException var6) {
      }

      return null;
   }

   public static PasswordValidationService getPasswordValidationService(SecurityServices secServices) throws ServiceInitializationException {
      ExtendedSecurityServices xSecurityService = (ExtendedSecurityServices)secServices;
      LegacyConfigInfoSpi legacyConfigInfoSpi = xSecurityService.getLegacyConfig();
      Services services = xSecurityService.getServices();
      return (PasswordValidationService)services.getService(legacyConfigInfoSpi.getPasswordValidationServiceName());
   }

   public static void persistBusinessObject(StoreService storeService, Object businessObjectOrList) throws Exception {
      PersistenceManager pm = storeService.getPersistenceManager();

      try {
         Transaction t = pm.currentTransaction();
         t.begin();

         try {
            if (businessObjectOrList instanceof List) {
               pm.makePersistentAll((List)businessObjectOrList);
            } else {
               pm.makePersistent(businessObjectOrList);
            }
         } catch (Exception var8) {
            t.rollback();
            throw var8;
         }

         t.commit();
      } finally {
         pm.close();
      }

   }

   public static void deleteBusinessObject(StoreService storeService, Object businessObjectOrList) throws Exception {
      PersistenceManager pm = storeService.getPersistenceManager();

      try {
         Transaction t = pm.currentTransaction();
         t.begin();

         try {
            if (businessObjectOrList instanceof List) {
               pm.deletePersistentAll((List)businessObjectOrList);
            } else {
               pm.deletePersistent(businessObjectOrList);
            }
         } catch (Exception var8) {
            t.rollback();
            throw var8;
         }

         t.commit();
      } finally {
         pm.close();
      }

   }

   public static void deleteSingleBusinessObject(StoreService storeService, Class businessObjectClass, String filter, String declarations, Object[] parameters) throws Throwable {
      PersistenceManager pm = storeService.getPersistenceManager();
      Query q = null;

      try {
         Transaction t = pm.currentTransaction();
         t.begin();

         try {
            q = pm.newQuery(businessObjectClass);
            q.setFilter(filter);
            Collection results;
            if (declarations != null) {
               q.declareParameters(declarations);
               results = (Collection)q.executeWithArray(parameters);
            } else {
               results = (Collection)q.execute();
            }

            if (results.size() == 0) {
               throw new Exception("No business Object found for delete");
            }

            if (results.size() > 1) {
               throw new Exception("More than one business Object found for single delete");
            }

            pm.deletePersistentAll(results);
         } catch (Throwable var13) {
            t.rollback();
            throw var13;
         }

         t.commit();
      } finally {
         if (q != null) {
            q.closeAll();
         }

         pm.close();
      }

   }

   public static void deleteBusinessObject(StoreService storeService, Class businessObjectClass, String filter, String declarations, Object[] parameters) throws Throwable {
      PersistenceManager pm = storeService.getPersistenceManager();
      Query q = null;

      try {
         Transaction t = pm.currentTransaction();
         t.begin();

         try {
            q = pm.newQuery(businessObjectClass);
            q.setFilter(filter);
            Collection results;
            if (declarations != null) {
               q.declareParameters(declarations);
               results = (Collection)q.executeWithArray(parameters);
            } else {
               results = (Collection)q.execute();
            }

            if (results.size() == 0) {
               throw new Exception("No business Object found for delete");
            }

            pm.deletePersistentAll(results);
         } catch (Throwable var13) {
            t.rollback();
            throw var13;
         }

         t.commit();
      } finally {
         if (q != null) {
            q.closeAll();
         }

         pm.close();
      }

   }

   public static Collection queryBusinessObjects(StoreService storeService, Class businessObjectClass, String filter, String declarations, Object[] parameters, int maxSize) throws Throwable {
      PersistenceManager pm = storeService.getPersistenceManager();
      Collection results = null;
      Query q = null;

      try {
         Transaction t = pm.currentTransaction();
         t.begin();

         try {
            q = pm.newQuery(businessObjectClass);
            if (maxSize > 0) {
               q.setRange(0L, (long)maxSize);
            }

            q.setFilter(filter);
            Collection tmpResults = null;
            if (declarations != null) {
               q.declareParameters(declarations);
               tmpResults = (Collection)q.executeWithArray(parameters);
            } else {
               tmpResults = (Collection)q.execute();
            }

            if (tmpResults != null && tmpResults.size() > 0) {
               results = pm.detachCopyAll(tmpResults);
            }
         } catch (Throwable var14) {
            t.rollback();
            throw var14;
         }

         t.commit();
      } finally {
         if (q != null) {
            q.closeAll();
         }

         pm.close();
      }

      return results;
   }

   public static String convertLDAPPatternForJDO(String pattern) {
      return convertLDAPPatternForJDO(pattern, (StoreService)null);
   }

   public static String convertLDAPPatternForJDO(String pattern, StoreService storeService) {
      return CommonUtils.convertLDAPPatternForJDO(pattern, storeService != null && storeService.isMySQLUsed());
   }

   public static String escapeRegChar(String string) {
      if (string != null && !string.equals("*")) {
         int length = string.length();
         StringBuffer buf = new StringBuffer(length + 5);

         for(int i = 0; i < length; ++i) {
            char c = string.charAt(i);
            if (c == '\\') {
               ++i;
               if (i >= length) {
                  throw new IllegalArgumentException("Unexpected escape character in the pattern: " + string);
               }

               char escapedChar = string.charAt(i);
               if (escapedChar == '\\') {
                  buf.append(c).append(escapedChar);
               } else {
                  if (escapedChar != '*') {
                     throw new IllegalArgumentException("Unexpected character '" + escapedChar + "' escaped in pattern: " + string);
                  }

                  buf.append(escapedChar);
               }
            } else if (c == '*') {
               buf.append(".*");
            } else if (checkSpecialChar(c)) {
               buf.append('\\').append(c);
            } else {
               buf.append(c);
            }
         }

         return buf.toString();
      } else {
         return ".*";
      }
   }

   public static boolean checkSpecialChar(char c) {
      for(int i = 0; i < regChars.length; ++i) {
         if (c == regChars[i]) {
            return true;
         }
      }

      return false;
   }

   public static AppConfigurationEntry.LoginModuleControlFlag getLoginModuleControlFlag(String flag) {
      if (flag != null && !flag.equalsIgnoreCase("REQUIRED")) {
         if (flag.equalsIgnoreCase("OPTIONAL")) {
            return LoginModuleControlFlag.OPTIONAL;
         } else if (flag.equalsIgnoreCase("REQUISITE")) {
            return LoginModuleControlFlag.REQUISITE;
         } else if (flag.equalsIgnoreCase("SUFFICIENT")) {
            return LoginModuleControlFlag.SUFFICIENT;
         } else {
            throw new IllegalArgumentException(SecurityLogger.getInvalidFlagValue(flag));
         }
      } else {
         return LoginModuleControlFlag.REQUIRED;
      }
   }

   public static File getFile(String location, String serverDirectory, String rootDirectory) {
      if (location != null && location.length() != 0) {
         File file = new File(location);
         if (file.exists()) {
            return file;
         } else {
            file = new File(serverDirectory, location);
            if (file.exists()) {
               return file;
            } else {
               file = new File(rootDirectory, location);
               return file.exists() ? file : null;
            }
         }
      } else {
         throw new IllegalArgumentException(SecurityLogger.getLocationNullOrEmpty());
      }
   }

   public static KeyStore load(File file, String passphrase, String type, LoggerSpi log) {
      char[] password = passphrase != null && passphrase.length() != 0 ? passphrase.toCharArray() : null;
      return load(file, password, type, log);
   }

   public static KeyStore load(File file, char[] password, String type, LoggerSpi log) {
      if (file == null) {
         throw new IllegalArgumentException(SecurityLogger.getNullFile());
      } else {
         KeyStore keystore = null;

         try {
            keystore = KeyStore.getInstance(type);
         } catch (KeyStoreException var24) {
            SecurityLogger.logLoadKeyStoreKeyStoreException(log, type, var24.toString());
            return null;
         }

         FileInputStream is = null;

         try {
            is = new FileInputStream(file);
         } catch (FileNotFoundException var23) {
            SecurityLogger.logLoadKeyStoreFileNotFoundException(log, file.getAbsolutePath(), var23.toString());
            return null;
         }

         Object var7;
         try {
            keystore.load(is, password);
            return keystore;
         } catch (CertificateException var25) {
            SecurityLogger.logLoadKeyStoreCertificateException(log, file.getAbsolutePath(), type, var25.toString());
            var7 = null;
            return (KeyStore)var7;
         } catch (NoSuchAlgorithmException var26) {
            SecurityLogger.logLoadKeyStoreNoSuchAlgorithmException(log, file.getAbsolutePath(), type, var26.toString());
            var7 = null;
         } catch (IOException var27) {
            SecurityLogger.logLoadKeyStoreIOException(log, file.getAbsolutePath(), type, var27.toString());
            var7 = null;
            return (KeyStore)var7;
         } finally {
            try {
               is.close();
            } catch (IOException var22) {
            }

         }

         return (KeyStore)var7;
      }
   }

   public static KeyStore load(File file, String passphrase, String type, String provider, LoggerSpi log) {
      char[] password = passphrase != null && passphrase.length() != 0 ? passphrase.toCharArray() : null;
      return load(file, password, type, provider, log);
   }

   public static KeyStore load(File file, char[] password, String type, String provider, LoggerSpi log) {
      if (file == null) {
         throw new IllegalArgumentException(SecurityLogger.getNullFile());
      } else {
         KeyStore keystore = null;

         try {
            keystore = KeyStore.getInstance(type, provider);
         } catch (NoSuchProviderException var26) {
            SecurityLogger.logLoadKeyStoreException(log, type, provider, var26.toString());
            return null;
         } catch (KeyStoreException var27) {
            SecurityLogger.logLoadKeyStoreException(log, type, provider, var27.toString());
            return null;
         }

         FileInputStream is = null;

         try {
            is = new FileInputStream(file);
         } catch (FileNotFoundException var25) {
            SecurityLogger.logLoadKeyStoreFileNotFoundException(log, file.getAbsolutePath(), var25.toString());
            return null;
         }

         Object var8;
         try {
            keystore.load(is, password);
            return keystore;
         } catch (CertificateException var28) {
            SecurityLogger.logLoadKeyStoreCertificateException(log, file.getAbsolutePath(), type, var28.toString());
            var8 = null;
            return (KeyStore)var8;
         } catch (NoSuchAlgorithmException var29) {
            SecurityLogger.logLoadKeyStoreNoSuchAlgorithmException(log, file.getAbsolutePath(), type, var29.toString());
            var8 = null;
            return (KeyStore)var8;
         } catch (IOException var30) {
            SecurityLogger.logLoadKeyStoreIOException(log, file.getAbsolutePath(), type, var30.toString());
            var8 = null;
         } finally {
            try {
               is.close();
            } catch (IOException var24) {
            }

         }

         return (KeyStore)var8;
      }
   }

   public static String escapeFormValue(String value) {
      if (value != null && value.length() != 0) {
         StringBuilder sb = new StringBuilder();

         for(int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            switch (c) {
               case '"':
                  sb.append("&quot;");
                  break;
               case '&':
                  sb.append("&amp;");
                  break;
               case '\'':
                  sb.append("&apos;");
                  break;
               case '<':
                  sb.append("&lt;");
                  break;
               case '>':
                  sb.append("&gt;");
                  break;
               default:
                  sb.append(c);
            }
         }

         return sb.toString();
      } else {
         return value;
      }
   }

   public static byte[] getPaddedBytes(byte[] value) {
      if (value != null && value.length != 0) {
         int neededBytes = 32 - value.length;
         if (neededBytes <= 0) {
            return value;
         } else {
            byte[] result = new byte[32];
            System.arraycopy(value, 0, result, 0, value.length);
            if (value.length >= neededBytes) {
               System.arraycopy(value, 0, result, value.length, neededBytes);
            } else {
               int remaining = neededBytes;

               int copyBytes;
               for(int copiedLength = value.length; remaining > 0; copiedLength += copyBytes) {
                  copyBytes = value.length >= remaining ? remaining : value.length;
                  System.arraycopy(value, 0, result, copiedLength, copyBytes);
                  remaining -= copyBytes;
               }
            }

            return result;
         }
      } else {
         return value;
      }
   }
}

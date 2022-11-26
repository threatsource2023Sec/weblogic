package weblogic.security.providers.utils;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.ProvidersLogger;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.legacy.ExtendedSecurityServices;
import com.bea.common.security.store.data.WLSCertRegEntryId;
import com.bea.common.security.utils.encoders.BASE64Encoder;
import com.bea.common.store.service.RemoteCommitEvent;
import com.bea.common.store.service.RemoteCommitListener;
import com.bea.common.store.service.StoreService;
import com.bea.security.utils.wss.WSSThumbprint;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.SubjectKeyID;
import com.rsa.certj.cert.extensions.X509V3Extension;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javax.jdo.PersistenceManager;
import javax.security.auth.x500.X500Principal;
import netscape.ldap.LDAPDN;
import weblogic.management.security.ProviderMBean;
import weblogic.management.utils.AlreadyExistsException;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;
import weblogic.security.spi.AuditMgmtEvent;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.AuditorService;
import weblogic.security.spi.SecurityServices;
import weblogic.security.utils.AuditBaseEventImpl;

public abstract class CertRegLDAPDelegate {
   protected AuditorService auditor;
   private static final String SUBJECT_KEY_IDENTIFIER_OID = "2.5.29.14";
   public static final String JKS_KEYSTORE_FORMAT = "JKS KeyStore";
   public static final String GROUP_JKS_KEYSTORE_FORMAT = "Group JKS KeyStore";
   private static final char[] KEYSTORE_PASSWORD = "changeme".toCharArray();
   private static final String CERT_HEADER = "-----BEGIN CERTIFICATE-----\n";
   private static final String CERT_FOOTER = "-----END CERTIFICATE-----\n";
   private static final int LINE_LENGTH = 76;
   protected static final String RESERVED_CERT_REG_GROUP = normalizeAlias("ReservedCertRegGroup");
   protected static final String SAMLCertificateRegistry = "SAMLCertificateRegistry";
   protected static final String SAMLCertReg = "SAMLCertReg";
   private static final String TRUST_GROUP_CONSTRAINTS_SEPERATOR = ",";
   protected LoggerSpi log = null;
   protected StoreService storeService = null;
   protected CertRegStore certStore;
   protected String realmName;
   protected String domainName;
   private BusinessObjectListerManager listerManager = new BusinessObjectListerManager();
   private Map thumbprintMapKeyMap = new HashMap();
   private static String allowExpiredCerts = "com.bea.common.security.saml.allowExpiredCerts";

   boolean isDebug() {
      return this.log == null ? false : this.log.isDebugEnabled();
   }

   private void debug(String method, String info) {
      if (this.log != null) {
         String msg = "CertRegLDAPDelegate." + method + ": " + info;
         if (this.log.isDebugEnabled()) {
            this.log.debug(msg);
         }

      }
   }

   private static void handleUnexpectedException(Throwable e) {
      throw new RuntimeException(e);
   }

   private void validateFormat(String format) throws InvalidParameterException {
      if (!"JKS KeyStore".equals(format)) {
         throw new InvalidParameterException(SecurityLogger.getInvalidFormat(format));
      }
   }

   private void validateConstraints(Properties constraints) throws InvalidParameterException {
      if (constraints != null && constraints.size() > 0) {
         throw new InvalidParameterException(SecurityLogger.getInvalidConstraints());
      }
   }

   private String getFullPathName(File file) {
      try {
         return file.getCanonicalPath();
      } catch (IOException var3) {
         return file.getAbsolutePath();
      }
   }

   private String getFullPathName(String fileName) {
      return this.getFullPathName(new File(fileName));
   }

   private static void closeStream(InputStream is) {
      try {
         is.close();
      } catch (IOException var2) {
      }

   }

   private static void closeStream(OutputStream os) {
      try {
         os.close();
      } catch (IOException var2) {
      }

   }

   public static String getCertificateDERFormat(X509Certificate cert) {
      try {
         return (new BASE64Encoder()).encodeBuffer(cert.getEncoded());
      } catch (CertificateEncodingException var2) {
         return null;
      }
   }

   private static String DER2PEM(byte[] der) {
      if (der != null && der.length >= 1) {
         String base64 = (new BASE64Encoder()).encodeBuffer(der);
         StringBuffer pem = new StringBuffer();
         pem.append("-----BEGIN CERTIFICATE-----\n");
         int len = base64.length();

         for(int start = 0; start < len; start += 76) {
            int end = start + 76;
            if (end > len) {
               end = len;
            }

            pem.append(base64.substring(start, end));
            pem.append('\n');
         }

         pem.append("-----END CERTIFICATE-----\n");
         return pem.toString();
      } else {
         return null;
      }
   }

   private byte[] getDER(X509Certificate cert) {
      try {
         return cert.getEncoded();
      } catch (CertificateEncodingException var3) {
         handleUnexpectedException(var3);
         return null;
      }
   }

   private String getPEM(X509Certificate cert) {
      return DER2PEM(this.getDER(cert));
   }

   private static String normalizeDN(String dn) {
      String normalizedDN = null;

      try {
         X500Principal p = new X500Principal(dn);
         String dn2 = p.getName();
         String[] rdns = LDAPDN.explodeDN(dn2, false);
         TreeSet set = new TreeSet();

         for(int i = 0; i < rdns.length; ++i) {
            set.add(rdns[i]);
         }

         StringBuffer buf = new StringBuffer();
         Iterator i = set.iterator();

         while(i.hasNext()) {
            if (buf.length() > 0) {
               buf.append(',');
            }

            String val = (String)i.next();
            buf.append(val);
         }

         normalizedDN = buf.toString();
      } catch (IllegalArgumentException var9) {
         normalizedDN = dn;
      }

      return normalizedDN;
   }

   private byte[] getSubjectKeyIdentifier(X509Certificate cert) {
      try {
         com.rsa.certj.cert.X509Certificate certjcert = new com.rsa.certj.cert.X509Certificate(cert.getEncoded(), 0, 0);
         X509V3Extensions extensions = certjcert.getExtensions();
         if (extensions != null) {
            X509V3Extension extension = extensions.getExtensionByType(14);
            if (extension != null) {
               SubjectKeyID skid = (SubjectKeyID)extension;
               return skid.getKeyID();
            }
         }

         return null;
      } catch (Exception var6) {
         handleUnexpectedException(var6);
         return null;
      }
   }

   private void validateAlias(String alias) throws InvalidParameterException {
      if (alias == null || alias.length() < 1) {
         throw new InvalidParameterException(SecurityLogger.getEmptyOrNullCertificateAlias());
      }
   }

   private void validateFileName(String filename) throws InvalidParameterException {
      if (filename == null || filename.length() < 1) {
         throw new InvalidParameterException(SecurityLogger.getEmptyOrNullFileName());
      }
   }

   private static X509Certificate readCertificateFromStream(InputStream is) throws CertificateException {
      CertificateFactory factory = null;

      try {
         factory = CertificateFactory.getInstance("X509");
      } catch (CertificateException var3) {
         handleUnexpectedException(var3);
      }

      return (X509Certificate)factory.generateCertificate(is);
   }

   private X509Certificate readCertificateFromFile(String filename) throws InvalidParameterException {
      try {
         FileInputStream is = new FileInputStream(filename);

         X509Certificate var3;
         try {
            var3 = readCertificateFromStream(is);
         } finally {
            closeStream((InputStream)is);
         }

         return var3;
      } catch (FileNotFoundException var9) {
         throw new InvalidParameterException(SecurityLogger.getUnableToReadFileError(this.getFullPathName(filename)), var9);
      } catch (CertificateException var10) {
         throw new InvalidParameterException(SecurityLogger.getUnableToReadCertificateFromPEMorDERError(this.getFullPathName(filename)), var10);
      }
   }

   public static X509Certificate readCertificateFromEncoded(byte[] encoded) {
      ByteArrayInputStream is = new ByteArrayInputStream(encoded);

      try {
         return readCertificateFromStream(is);
      } catch (CertificateException var3) {
         handleUnexpectedException(var3);
         return null;
      }
   }

   private X509Certificate getCertificate(WLSCertRegEntry businessObject) {
      if (businessObject != null && !RESERVED_CERT_REG_GROUP.equalsIgnoreCase(getAlias(businessObject))) {
         byte[] encoded = businessObject.getUserCertificate();
         return encoded != null ? readCertificateFromEncoded(encoded) : null;
      } else {
         return null;
      }
   }

   private static String getAlias(WLSCertRegEntry businessObject) {
      return businessObject == null ? null : businessObject.getCn();
   }

   private static String normalizeAlias(String alias) {
      return normalize(alias);
   }

   private static String normalize(String toBeNormalized) {
      return toBeNormalized.toUpperCase().toLowerCase();
   }

   private String normalizeGroup(String group) {
      return this.getRegistryDNName().equalsIgnoreCase(group) ? this.getRegistryDNName() : normalize(group);
   }

   private void checkTrustGroupNameReserved(String trustGroup) throws InvalidParameterException {
      if ("SAMLCertReg".equalsIgnoreCase(trustGroup) || "SAMLCertificateRegistry".equalsIgnoreCase(trustGroup) || this.getRegistryDNName().equalsIgnoreCase(trustGroup)) {
         throw new InvalidParameterException(ProvidersLogger.getTrustGroupNameReserved(trustGroup));
      }
   }

   private boolean aliasExistsInternal(String group, String alias) {
      return this.certStore.getRegEntryByAlias(group, alias) != null;
   }

   private void checkAliasExists(String group, String alias) throws InvalidParameterException, NotFoundException {
      String method = "checkAliasExists";
      if (!this.aliasExistsInternal(group, alias)) {
         if (this.isDebug()) {
            this.debug(method, "alias " + alias + " does not exist");
         }

         throw new NotFoundException(SecurityLogger.getCertificateAliasNotFound(alias));
      }
   }

   private String checkSubjectDNExistsInternal(String group, String subjectDN) {
      return getAlias(this.certStore.getRegEntryBySubjectDN(group, subjectDN));
   }

   private String checkIssuerDNExistsInternal(String group, String issuerDN, String serialNumber) {
      return getAlias(this.certStore.getRegEntryByIssuerDN(group, issuerDN, serialNumber));
   }

   private String checkSubjectKeyIdentifierExistsInternal(String group, String subjectKeyIdentifier) {
      return getAlias(this.certStore.getRegEntryBySubjectKeyId(group, subjectKeyIdentifier));
   }

   private void checkAliasDoesNotExist(String group, String alias) throws InvalidParameterException, AlreadyExistsException {
      String method = "checkAliasDoesNotExist";
      if (this.aliasExistsInternal(group, alias)) {
         if (this.isDebug()) {
            this.debug(method, "alias " + alias + " already exists in trust group: " + group);
         }

         String errMsg = this.getRegistryDNName().equalsIgnoreCase(group) ? SecurityLogger.getCertificateAliasAlreadyExists(alias) : ProvidersLogger.getCertificateAliasAlreadyExistsInTrustGroup(group, alias);
         throw new AlreadyExistsException(errMsg);
      }
   }

   private WLSCertRegEntry prepareForRegistery(String group, String alias, X509Certificate cert) throws AlreadyExistsException, InvalidParameterException, Exception {
      String method = "registerCertificate";
      if (this.isDebug()) {
         this.debug(method, "alias=" + alias + ",  group=" + group + ",cert=" + cert);
      }

      String normalizedAlias = normalizeAlias(alias);
      String normalizedGroup = this.normalizeGroup(group);
      X500Principal subjectX500Principal = cert.getSubjectX500Principal();
      String subjectDNString = null;
      if (subjectX500Principal != null) {
         subjectDNString = subjectX500Principal.getName();
      }

      X500Principal issuerX500Principal = cert.getIssuerX500Principal();
      String issuerDNString = null;
      if (issuerX500Principal != null) {
         issuerDNString = issuerX500Principal.getName();
      }

      String subjectDN = normalizeDN(subjectDNString);
      String issuerDN = normalizeDN(issuerDNString);
      BigInteger serialNumber = cert.getSerialNumber();
      byte[] skid = this.getSubjectKeyIdentifier(cert);
      String skidString = getSubjectKeyIdentifierString(skid);
      this.checkGroupExists(normalizedGroup);
      this.checkAliasDoesNotExist(normalizedGroup, normalizedAlias);
      String priorAlias = this.checkSubjectDNExistsInternal(normalizedGroup, subjectDN);
      String errMsg;
      if (priorAlias != null) {
         if (this.isDebug()) {
            this.debug(method, "subjectDN " + subjectDN + " already exists under the alias " + priorAlias + " and group " + group);
         }

         errMsg = this.getRegistryDNName().equalsIgnoreCase(group) ? SecurityLogger.getSubjectDNAlreadyExists(subjectDN, priorAlias) : ProvidersLogger.getSubjectDNAlreadyExists(group, subjectDN, priorAlias);
         throw new AlreadyExistsException(errMsg);
      } else {
         priorAlias = this.checkIssuerDNExistsInternal(normalizedGroup, issuerDN, serialNumber.toString());
         if (priorAlias != null) {
            if (this.isDebug()) {
               this.debug(method, "issuerDN " + issuerDN + " and serial number " + serialNumber + " already exists under the alias " + priorAlias + " and group " + group);
            }

            errMsg = this.getRegistryDNName().equalsIgnoreCase(group) ? SecurityLogger.getIssuerDNAndSerialNumberAlreadyExists(issuerDN, serialNumber.toString(), priorAlias) : ProvidersLogger.getIssuerDNAndSerialNumberAlreadyExists(group, issuerDN, serialNumber.toString(), priorAlias);
            throw new AlreadyExistsException(errMsg);
         } else {
            if (skidString != null) {
               priorAlias = this.checkSubjectKeyIdentifierExistsInternal(normalizedGroup, skidString);
               if (priorAlias != null) {
                  if (this.isDebug()) {
                     this.debug(method, "SubjectKeyIdentifier " + skidString + " already exists under the alias " + priorAlias + " and group " + group);
                  }

                  errMsg = this.getRegistryDNName().equalsIgnoreCase(group) ? SecurityLogger.getSubjectKeyIdentifierAlreadyExists(skidString, priorAlias) : ProvidersLogger.getSubjectKeyIdentifierAlreadyExists(group, skidString, priorAlias);
                  throw new AlreadyExistsException(errMsg);
               }
            }

            WLSCertRegEntry businessObject = new WLSCertRegEntry();
            businessObject.setCn(normalizedAlias);
            businessObject.setRegistryName(normalizedGroup);
            businessObject.setWlsCertRegSubjectDN(subjectDN);
            businessObject.setWlsCertRegIssuerDN(issuerDN);
            businessObject.setWlsCertRegSerialNumber(serialNumber.toString());
            businessObject.setWlsCertRegSubjectKeyIdentifier(skidString);
            byte[] encodedCert = this.getDER(cert);
            businessObject.setUserCertificate(encodedCert);
            return businessObject;
         }
      }
   }

   private void registerCertificateInternal(String group, String alias, X509Certificate cert) throws AlreadyExistsException, InvalidParameterException, Exception {
      String method = "registerCertificate";
      WLSCertRegEntry businessObject = this.prepareForRegistery(group, alias, cert);

      try {
         ThumbprintMap thumbprintAliases = CertRegLDAPDelegate.ThumbprintMapFactory.getThumbprintMap(this.getThumbprintMapKey(businessObject.getRegistryName()));
         synchronized(thumbprintAliases) {
            this.certStore.registerCertificate(businessObject);
            if (thumbprintAliases.isInitialized()) {
               this.putToThumbprintMap(thumbprintAliases, businessObject.getUserCertificate(), businessObject.getCn());
            }
         }
      } catch (Exception var10) {
         if (this.isDebug()) {
            this.debug(method, "group " + group + ", alias " + alias + " and certificate " + cert + " have not been added to the registry, exception: " + var10.getMessage());
         }

         throw var10;
      }

      if (this.isDebug()) {
         this.debug(method, "group " + group + ", alias " + alias + " and certificate " + cert + " have been added to the registry");
      }

   }

   private void validateGroup(String group) throws InvalidParameterException {
      if (group == null || group.length() < 1) {
         throw new InvalidParameterException(ProvidersLogger.getEmptyOrNullTrustGroup());
      }
   }

   private void validateGroups(String[] groups) throws InvalidParameterException {
      if (groups != null && groups.length >= 1) {
         for(int i = 0; i < groups.length; ++i) {
            this.checkTrustGroupNameReserved(groups[i]);
         }

      } else {
         throw new InvalidParameterException(ProvidersLogger.getEmptyOrNullTrustGroups());
      }
   }

   private void registerGroupNoAudit(String group) throws AlreadyExistsException, InvalidParameterException {
      this.validateGroup(group);

      try {
         this.registerGroupInternal(this.normalizeGroup(group));
      } catch (AlreadyExistsException var3) {
         throw var3;
      } catch (Exception var4) {
         handleUnexpectedException(var4);
      }

   }

   private void checkGroupDoesNotExist(String group) throws InvalidParameterException, AlreadyExistsException {
      String method = "checkGroupDoesNotExist";
      if (this.aliasExistsInternal(group, RESERVED_CERT_REG_GROUP)) {
         if (this.isDebug()) {
            this.debug(method, "group " + group + " already exists");
         }

         throw new AlreadyExistsException(ProvidersLogger.getTrustGroupAlreadyExists(group));
      }
   }

   private void registerGroupInternal(String group) throws AlreadyExistsException, InvalidParameterException, Exception {
      this.checkGroupDoesNotExist(group);
      WLSCertRegEntry businessObject = new WLSCertRegEntry();
      businessObject.setCn(RESERVED_CERT_REG_GROUP);
      businessObject.setRegistryName(group);
      businessObject.setUserCertificate(new byte[]{0});
      businessObject.setWlsCertRegIssuerDN("FAKEIssuerDN");
      businessObject.setWlsCertRegSerialNumber("0");
      businessObject.setWlsCertRegSubjectDN("FAKESubjectDN");

      try {
         this.certStore.registerCertificate(businessObject);
      } catch (Exception var4) {
         if (this.isDebug()) {
            this.debug("registerGroupInternal", "alias " + RESERVED_CERT_REG_GROUP + " and group " + group + " have not been added to the registry, exception: " + var4.getMessage());
         }

         throw var4;
      }

      if (this.isDebug()) {
         this.debug("registerGroupInternal", "alias " + RESERVED_CERT_REG_GROUP + " and group " + group + " have been added to the registry");
      }

   }

   public boolean aliasExists(String group, String alias) throws InvalidParameterException {
      this.validateGroup(group);
      this.validateAlias(alias);
      this.checkTrustGroupNameReserved(group);

      try {
         return this.aliasExistsInternal(this.normalizeGroup(group), normalizeAlias(alias));
      } catch (Exception var4) {
         handleUnexpectedException(var4);
         return false;
      }
   }

   public X509Certificate getCertificateFromAlias(String group, String alias) throws NotFoundException, InvalidParameterException {
      this.validateGroup(group);
      this.checkTrustGroupNameReserved(group);
      String normalizedGroup = this.normalizeGroup(group);
      this.checkGroupFound(normalizedGroup);
      return this.getCertificateFromAliasInternal(normalizedGroup, alias);
   }

   private X509Certificate getCertificateFromAliasInternal(String group, String alias) throws NotFoundException, InvalidParameterException {
      this.validateAlias(alias);

      try {
         X509Certificate cert = this.getCertificate(this.certStore.getRegEntryByAlias(group, normalizeAlias(alias)));
         if (cert == null) {
            throw new NotFoundException(SecurityLogger.getCertificateAliasNotFound(alias));
         } else {
            return cert;
         }
      } catch (NotFoundException var4) {
         throw var4;
      } catch (Exception var5) {
         handleUnexpectedException(var5);
         return null;
      }
   }

   private void registerCertificateNoAudit(String method, String group, String alias, String filename) throws AlreadyExistsException, InvalidParameterException {
      this.validateAlias(alias);
      this.validateGroup(group);
      this.validateFileName(filename);
      X509Certificate cert = this.readCertificateFromFile(filename);
      boolean allowInvalid = Boolean.getBoolean(allowExpiredCerts);

      try {
         cert.checkValidity();
      } catch (CertificateExpiredException var11) {
         if (this.isDebug()) {
            this.debug(method, "Expired certificate being registered as alias: " + alias);
         }

         if (!allowInvalid) {
            throw new InvalidParameterException("Expired certificate being registered:" + var11.getMessage());
         }
      } catch (CertificateNotYetValidException var12) {
         if (this.isDebug()) {
            this.debug(method, "Not yet valid certificate being registered as alias: " + alias);
         }

         if (!allowInvalid) {
            throw new InvalidParameterException("Certificate that is not yet valid is being registered: " + var12.getMessage());
         }
      } catch (Exception var13) {
         if (this.isDebug()) {
            this.debug(method, "Exception when certificate being registered as alias: " + alias);
         }

         if (!allowInvalid) {
            throw new InvalidParameterException("Exception when certificate is being registered: " + var13.getMessage());
         }
      }

      try {
         this.registerCertificateInternal(group, alias, cert);
      } catch (AlreadyExistsException var8) {
         throw var8;
      } catch (InvalidParameterException var9) {
         throw var9;
      } catch (Exception var10) {
         handleUnexpectedException(var10);
      }

   }

   private void registerCertificate(String group, String alias, String filename) throws AlreadyExistsException, InvalidParameterException {
      boolean doAudit = true;
      RuntimeException auditException = null;
      boolean var15 = false;

      try {
         var15 = true;
         this.registerCertificateNoAudit("registerCertificate", group, alias, filename);
         var15 = false;
      } catch (AlreadyExistsException var16) {
         doAudit = false;
         throw var16;
      } catch (InvalidParameterException var17) {
         doAudit = false;
         throw var17;
      } catch (RuntimeException var18) {
         auditException = var18;
         throw var18;
      } finally {
         if (var15) {
            if (doAudit && this.auditor != null) {
               String eventType = "registerCertificate";
               String eventData = "<Trust group = " + group + "> <Alias = " + alias + "> <File Name = " + filename + ">";
               this.auditMgmtEvent(eventType, eventData, auditException);
            }

         }
      }

      if (doAudit && this.auditor != null) {
         String eventType = "registerCertificate";
         String eventData = "<Trust group = " + group + "> <Alias = " + alias + "> <File Name = " + filename + ">";
         this.auditMgmtEvent(eventType, eventData, auditException);
      }

   }

   private static String arrayToString(String[] a) {
      if (a == null) {
         return "null";
      } else {
         int iMax = a.length - 1;
         if (iMax == -1) {
            return "[]";
         } else {
            StringBuilder b = new StringBuilder();
            b.append('[');
            int i = 0;

            while(true) {
               b.append(a[i]);
               if (i == iMax) {
                  return b.append(']').toString();
               }

               b.append(", ");
               ++i;
            }
         }
      }
   }

   public void registerCertificate(String[] groups, String alias, String filename) throws InvalidParameterException, ErrorCollectionException {
      String method = "registerCertificatefromFile";
      this.validateAlias(alias);
      this.validateFileName(filename);
      this.validateGroups(groups);
      ErrorCollectionException errorCollectionException = new ErrorCollectionException(ProvidersLogger.getErrorsRegisterCertificate(arrayToString(groups)));

      for(int i = 0; i < groups.length; ++i) {
         if (this.isDebug()) {
            this.debug(method, " alias=" + alias + " filename=" + filename + " groups=" + arrayToString(groups));
         }

         try {
            this.registerCertificate(groups[i], alias, filename);
            if (this.isDebug()) {
               this.debug(method, " registered for group:" + groups[i]);
            }
         } catch (AlreadyExistsException var8) {
            errorCollectionException.add(var8);
         } catch (InvalidParameterException var9) {
            errorCollectionException.add(var9);
         }
      }

      if (!errorCollectionException.isEmpty()) {
         throw errorCollectionException;
      }
   }

   public String[] getTrustGroups(String alias) throws InvalidParameterException {
      this.validateAlias(alias);
      List nameList = this.getTrustGroupList(alias);
      nameList.remove(this.getRegistryDNName());
      return (String[])((String[])nameList.toArray(new String[0]));
   }

   private List getTrustGroupList(String alias) {
      Collection businessObjects = this.certStore.getRegEntriesByRegistryPattern("*", normalizeAlias(alias), 0);
      List nameList = this.generateGroupNameList(businessObjects);
      nameList.remove(this.getRegistryDNName());
      return nameList;
   }

   public void registerCertificate(String[] trustGroups, String alias) throws NotFoundException, InvalidParameterException, ErrorCollectionException {
      String method = "registerCertificate";
      this.validateAlias(alias);
      this.validateGroups(trustGroups);
      Collection businessObjects = this.certStore.getRegEntriesByRegistryPattern("*", normalizeAlias(alias), 0);
      List groupsList = this.generateGroupNameList(businessObjects);
      X509Certificate cert = null;
      if (groupsList.size() == 0) {
         throw new NotFoundException(SecurityLogger.getCertificateAliasNotFound(alias));
      } else {
         Iterator entries = businessObjects.iterator();
         cert = this.getCertificate((WLSCertRegEntry)entries.next());
         ErrorCollectionException errorCollectionException = new ErrorCollectionException(ProvidersLogger.getErrorsRegisterCertificate(arrayToString(trustGroups)));

         for(int i = 0; i < trustGroups.length; ++i) {
            if (this.isDebug()) {
               this.debug(method, "registering exist certificate:" + cert + " with alias:" + alias + " for " + arrayToString(trustGroups));
            }

            try {
               this.validateGroup(trustGroups[i]);
            } catch (InvalidParameterException var11) {
               errorCollectionException.add(var11);
               continue;
            }

            if (groupsList.contains(this.normalizeGroup(trustGroups[i]))) {
               errorCollectionException.add(new AlreadyExistsException(ProvidersLogger.getCertificateAliasAlreadyExistsInTrustGroup(trustGroups[i], alias)));
            } else {
               try {
                  this.registerCertificateAudit(trustGroups[i], normalizeAlias(alias), cert);
                  if (this.isDebug()) {
                     this.debug(method, "registered cert with alias:" + alias + " for " + trustGroups[i]);
                  }
               } catch (Exception var10) {
                  errorCollectionException.add(var10);
               }
            }
         }

         if (!errorCollectionException.isEmpty()) {
            throw errorCollectionException;
         }
      }
   }

   private void registerCertificateAudit(String group, String alias, X509Certificate cert) throws Exception {
      boolean doAudit = true;
      RuntimeException auditException = null;
      boolean var15 = false;

      try {
         var15 = true;
         this.registerCertificateInternal(group, alias, cert);
         var15 = false;
      } catch (AlreadyExistsException var16) {
         doAudit = false;
         throw var16;
      } catch (InvalidParameterException var17) {
         doAudit = false;
         throw var17;
      } catch (RuntimeException var18) {
         auditException = var18;
         throw var18;
      } finally {
         if (var15) {
            if (doAudit && this.auditor != null) {
               String eventType = "registerCertificate";
               String eventData = "<Trust group = " + group + "><Alias = " + alias + ">";
               this.auditMgmtEvent(eventType, eventData, auditException);
            }

         }
      }

      if (doAudit && this.auditor != null) {
         String eventType = "registerCertificate";
         String eventData = "<Trust group = " + group + "><Alias = " + alias + ">";
         this.auditMgmtEvent(eventType, eventData, auditException);
      }

   }

   public boolean isAliasRegistered(String alias) throws InvalidParameterException {
      this.validateAlias(alias);
      Collection businessObjects = this.certStore.getRegEntriesByRegistryPattern("*", normalizeAlias(alias), 3);
      int size = businessObjects == null ? 0 : businessObjects.size();
      if (this.isDebug()) {
         this.debug("isAliasRegistered", "alias " + alias + " registered at least: " + size + "times.");
      }

      return this.generateGroupNameList(businessObjects).size() > 0;
   }

   public void removeCertificate(String alias) throws NotFoundException, InvalidParameterException {
      String method = "removeCertificate";
      this.validateAlias(alias);
      String normalizedAlias = normalizeAlias(alias);
      if (this.isDebug()) {
         this.debug(method, "alias = " + alias);
      }

      List nameList = this.getTrustGroupList(normalizedAlias);
      if (nameList.size() == 0) {
         throw new NotFoundException(SecurityLogger.getCertificateAliasNotFound(alias));
      } else {
         Iterator groupIterator = nameList.iterator();

         while(groupIterator.hasNext()) {
            String trustGroup = (String)groupIterator.next();
            if (this.isDebug()) {
               this.debug(method, "unregistering trust group = " + trustGroup + "...");
            }

            try {
               this.unregisterCertificate(trustGroup, normalizedAlias);
            } catch (NotFoundException var8) {
               if (this.isDebug()) {
                  this.debug(method, "alias= " + alias + " not found in trust group: " + trustGroup);
               }
            } catch (Exception var9) {
               handleUnexpectedException(var9);
            }
         }

      }
   }

   private void unregisterCertificateNoAudit(String group, String alias) throws NotFoundException, InvalidParameterException {
      String method = "unregisterCertificate";
      if (this.isDebug()) {
         this.debug(method, "alias=" + alias);
      }

      this.validateAlias(alias);
      String normalizedAlias = normalizeAlias(alias);

      try {
         this.checkAliasExists(group, normalizedAlias);
         ThumbprintMap thumbprintAliases = CertRegLDAPDelegate.ThumbprintMapFactory.getThumbprintMap(this.getThumbprintMapKey(group));
         synchronized(thumbprintAliases) {
            this.certStore.unregisterCertificate(group, normalizedAlias);
            if (thumbprintAliases.isInitialized()) {
               this.removeFromThumbprintMap(thumbprintAliases, normalizedAlias);
            }
         }

         if (this.isDebug()) {
            this.debug(method, "alias " + normalizedAlias + " and its certificate have been removed from the registry");
         }
      } catch (NotFoundException var9) {
         throw var9;
      } catch (InvalidParameterException var10) {
         throw var10;
      } catch (Throwable var11) {
         handleUnexpectedException(var11);
      }

   }

   public void unregisterCertificate(String[] groups, String alias) throws InvalidParameterException, ErrorCollectionException {
      String method = "unregisterCertificate";
      this.validateAlias(alias);
      this.validateGroups(groups);
      String normalizedAlias = normalizeAlias(alias);
      ErrorCollectionException errorCollectionException = new ErrorCollectionException();

      int i;
      String group;
      for(i = 0; i < groups.length; ++i) {
         try {
            this.validateGroup(groups[i]);
            group = this.normalizeGroup(groups[i]);
            this.checkGroupFound(group);
            this.checkAliasExists(group, normalizedAlias);
         } catch (NotFoundException var9) {
            errorCollectionException.add(var9);
         } catch (InvalidParameterException var10) {
            errorCollectionException.add(var10);
         }
      }

      if (!errorCollectionException.isEmpty()) {
         throw errorCollectionException;
      } else {
         if (this.isDebug()) {
            this.debug(method, "alias=" + alias + "groups=" + arrayToString(groups));
         }

         for(i = 0; i < groups.length; ++i) {
            group = groups[i];

            try {
               this.unregisterCertificate(group, normalizedAlias);
            } catch (NotFoundException var11) {
               if (this.isDebug()) {
                  this.debug(method, "alias=" + alias + " not found in trust group=" + group);
               }
            }
         }

      }
   }

   private void unregisterCertificate(String group, String alias) throws NotFoundException, InvalidParameterException {
      this.validateGroup(group);
      String normalizedGroup = this.normalizeGroup(group);
      this.checkGroupFound(normalizedGroup);
      boolean doAudit = true;
      RuntimeException auditException = null;
      boolean var15 = false;

      try {
         var15 = true;
         this.unregisterCertificateNoAudit(this.normalizeGroup(normalizedGroup), alias);
         var15 = false;
      } catch (NotFoundException var16) {
         doAudit = false;
         throw var16;
      } catch (InvalidParameterException var17) {
         doAudit = false;
         throw var17;
      } catch (RuntimeException var18) {
         auditException = var18;
         throw var18;
      } finally {
         if (var15) {
            if (doAudit && this.auditor != null) {
               String eventType = "unregisterCertificate";
               String eventData = "";
               if (this.getRegistryDNName().equalsIgnoreCase(group)) {
                  eventData = "<Alias = " + alias + ">";
               } else {
                  eventData = "<Trust group = " + group + " <Alias = " + alias + ">";
               }

               this.auditMgmtEvent(eventType, eventData, auditException);
            }

         }
      }

      if (doAudit && this.auditor != null) {
         String eventType = "unregisterCertificate";
         String eventData = "";
         if (this.getRegistryDNName().equalsIgnoreCase(group)) {
            eventData = "<Alias = " + alias + ">";
         } else {
            eventData = "<Trust group = " + group + " <Alias = " + alias + ">";
         }

         this.auditMgmtEvent(eventType, eventData, auditException);
      }

   }

   public void exportData(String format, String filename, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      String method = "exportData";
      if (this.isDebug()) {
         this.debug(method, "format=" + format + ", filename=" + filename + ", constraints=" + constraints);
      }

      if ("Group JKS KeyStore".equals(format)) {
         this.exportDataGroupJKS(format, filename, constraints);
      } else {
         if (!"JKS KeyStore".equals(format)) {
            throw new InvalidParameterException(SecurityLogger.getInvalidFormat(format));
         }

         this.exportDataJKS(this.getRegistryDNName(), format, filename, constraints);
      }

   }

   private void exportDataGroupJKS(String format, String filename, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      this.validateFileName(filename);
      File file = new File(filename);
      FileOutputStream os = null;

      try {
         os = new FileOutputStream(file);
         this.exportDataToOutputStream(os, constraints);
      } catch (FileNotFoundException var10) {
         throw new InvalidParameterException(SecurityLogger.getExportFileError(), var10);
      } finally {
         closeStream((OutputStream)os);
      }

   }

   private void exportDataToOutputStream(FileOutputStream os, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      String method = "exportGroupJKS";
      ErrorCollectionException errCollectionexception = new ErrorCollectionException();
      List toBeExportedGroupList = null;
      String[] constraintsGroups = this.parseTrustGroupsConstraints(constraints);
      if (constraintsGroups != null && constraintsGroups.length > 0) {
         toBeExportedGroupList = new ArrayList();

         for(int i = 0; i < constraintsGroups.length; ++i) {
            if (!this.groupExists(constraintsGroups[i])) {
               errCollectionexception.add(new NotFoundException(ProvidersLogger.getTrustGroupNotFound(constraintsGroups[i])));
            } else {
               ((List)toBeExportedGroupList).add(constraintsGroups[i]);
            }
         }

         if (((List)toBeExportedGroupList).isEmpty()) {
            if (this.isDebug()) {
               this.debug(method, "User wants to export: " + arrayToString(constraintsGroups) + " but none of them exits.");
            }

            throw errCollectionexception;
         }
      } else {
         if (this.isDebug()) {
            this.debug(method, "Null or empty group list, going to export all data.");
         }

         Collection businessObjects = this.certStore.getRegEntriesByRegistryPattern("*", RESERVED_CERT_REG_GROUP, 0);
         toBeExportedGroupList = this.generateAllGroupNameList(businessObjects);
      }

      Iterator it = ((List)toBeExportedGroupList).iterator();
      ZipOutputStream zipOStream = null;

      try {
         zipOStream = new ZipOutputStream(os);

         while(it.hasNext()) {
            try {
               String groupToBeExported = (String)it.next();
               if (this.isDebug()) {
                  this.debug(method, "exporting group: " + groupToBeExported);
               }

               this.appendZipEntry(zipOStream, groupToBeExported);
            } catch (IOException var17) {
               errCollectionexception.add(var17);
            }
         }
      } finally {
         try {
            zipOStream.flush();
            zipOStream.finish();
            zipOStream.close();
         } catch (Exception var16) {
            errCollectionexception.add(var16);
         }

      }

      if (!errCollectionexception.isEmpty()) {
         throw errCollectionexception;
      }
   }

   private List generateGroupNameList(Collection businessObjects) {
      ArrayList nameList = new ArrayList();
      if (businessObjects != null) {
         Iterator entries = businessObjects.iterator();

         while(true) {
            while(entries.hasNext()) {
               String group = ((WLSCertRegEntry)entries.next()).getRegistryName();
               if (!"SAMLCertReg".equalsIgnoreCase(group) && !"SAMLCertificateRegistry".equalsIgnoreCase(group)) {
                  nameList.add(group);
               } else if (this.isDebug()) {
                  this.debug("generateGroupNameList", "ignore certificate found in " + group);
               }
            }

            return nameList;
         }
      } else {
         return nameList;
      }
   }

   private List generateAllGroupNameList(Collection businessObjects) {
      List nameList = this.generateGroupNameList(businessObjects);
      nameList.add(this.getRegistryDNName());
      return nameList;
   }

   private void appendZipEntry(ZipOutputStream zo, String group) throws InvalidParameterException, ErrorCollectionException, IOException {
      ZipEntry entry = new ZipEntry(group);
      zo.putNextEntry(entry);
      ByteArrayOutputStream bo = new ByteArrayOutputStream();
      this.exportData(group, bo);
      zo.write(bo.toByteArray());
   }

   private void exportDataJKS(String group, String format, String filename, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      this.validateFormat(format);
      this.validateConstraints(constraints);
      this.validateFileName(filename);
      File file = new File(filename);

      try {
         FileOutputStream os = new FileOutputStream(file);
         this.exportData(group, os);
      } catch (FileNotFoundException var7) {
         throw new InvalidParameterException(SecurityLogger.getExportFileError(), var7);
      }
   }

   private void exportData(String group, OutputStream ostream) throws InvalidParameterException, ErrorCollectionException {
      String method = "exportData";
      this.validateGroup(group);
      String normalizedGroup = this.normalizeGroup(group);
      this.checkGroupExists(normalizedGroup);
      KeyStore ks = null;

      try {
         ks = KeyStore.getInstance("JKS");
      } catch (Exception var24) {
         handleUnexpectedException(var24);
      }

      try {
         ks.load((InputStream)null, (char[])null);
      } catch (Exception var23) {
         handleUnexpectedException(var23);
      }

      ErrorCollectionException errors = new ErrorCollectionException(SecurityLogger.getExportErrors());

      try {
         Collection businessObjects = this.certStore.getRegEntriesByAliasPattern(normalizedGroup, "*", 0);
         if (businessObjects != null) {
            Iterator entries = businessObjects.iterator();

            while(entries.hasNext()) {
               WLSCertRegEntry businessObject = (WLSCertRegEntry)entries.next();
               String alias = getAlias(businessObject);
               X509Certificate cert = null;
               if (alias != null) {
                  cert = this.getCertificate(businessObject);
               }

               if (this.isDebug()) {
                  this.debug(method, "export trust group=" + group + " alias=" + alias + " cert=" + cert);
               }

               if (alias != null && cert != null) {
                  try {
                     ks.setCertificateEntry(alias, cert);
                     if (this.isDebug()) {
                        this.debug(method, "exported  alias=" + alias);
                     }
                  } catch (KeyStoreException var22) {
                     errors.add(var22);
                  }
               }
            }
         }
      } catch (Throwable var25) {
         handleUnexpectedException(var25);
      }

      try {
         ks.store(ostream, KEYSTORE_PASSWORD);
      } catch (Exception var20) {
         handleUnexpectedException(var20);
      } finally {
         closeStream(ostream);
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   public void registerCertificate(String group, String alias, X509Certificate cert) throws InvalidParameterException, ErrorCollectionException {
      if (alias != null && alias.length() != 0 && cert != null) {
         ErrorCollectionException errors = new ErrorCollectionException(SecurityLogger.getImportErrors());

         try {
            try {
               this.registerCertificateInternal(group, alias, cert);
            } catch (AlreadyExistsException var6) {
               errors.add(var6);
            } catch (InvalidParameterException var7) {
               errors.add(var7);
            }
         } catch (Exception var8) {
            handleUnexpectedException(var8);
         }

         if (!errors.isEmpty()) {
            throw errors;
         }
      } else {
         throw new InvalidParameterException("Import Certificate Invalid Parameter Exception, either alias or certificate is null");
      }
   }

   private void importDataNoAudit(String group, InputStream is) throws InvalidParameterException, ErrorCollectionException {
      String method = "importDataNoAudit";
      KeyStore ks = null;

      try {
         ks = KeyStore.getInstance("JKS");
      } catch (Exception var22) {
         handleUnexpectedException(var22);
      }

      try {
         ks.load(is, (char[])null);
      } catch (Exception var20) {
         handleUnexpectedException(var20);
      } finally {
         closeStream(is);
      }

      ErrorCollectionException errorCollectionException = new ErrorCollectionException(SecurityLogger.getImportErrors());

      try {
         Collection businessObjects = new ArrayList();
         Enumeration enum_ = ks.aliases();

         while(enum_.hasMoreElements()) {
            String alias = (String)enum_.nextElement();
            X509Certificate cert = (X509Certificate)ks.getCertificate(alias);
            if (this.isDebug()) {
               this.debug(method, " trust group=" + group + " alias=" + alias + " cert=" + cert);
            }

            if (cert != null) {
               try {
                  businessObjects.add(this.prepareForRegistery(group, alias, cert));
                  if (this.isDebug()) {
                     this.debug(method, " imported alias: " + alias);
                  }
               } catch (Exception var19) {
                  errorCollectionException.add(var19);
               }
            }
         }

         Collection results = this.certStore.registerCertificate(group, businessObjects, errorCollectionException);
         ThumbprintMap thumbprintAliases = CertRegLDAPDelegate.ThumbprintMapFactory.getThumbprintMap(this.getThumbprintMapKey(group));
         synchronized(thumbprintAliases) {
            if (thumbprintAliases.isInitialized()) {
               Iterator var10 = results.iterator();

               while(var10.hasNext()) {
                  WLSCertRegEntry businessObject = (WLSCertRegEntry)var10.next();
                  this.putToThumbprintMap(thumbprintAliases, businessObject.getUserCertificate(), businessObject.getCn());
               }
            }
         }
      } catch (KeyStoreException var24) {
         handleUnexpectedException(var24);
      }

      if (!errorCollectionException.isEmpty()) {
         throw errorCollectionException;
      }
   }

   public void importData(String format, String filename, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      String method = "importData";
      if (this.isDebug()) {
         this.debug(method, "format=" + format + ", filename=" + filename + ", constraints=" + constraints);
      }

      boolean doAudit = true;
      Object auditException = null;
      boolean var16 = false;

      try {
         var16 = true;
         if ("Group JKS KeyStore".equals(format)) {
            this.importDataGroupJKS(filename, constraints);
            var16 = false;
         } else {
            if (!"JKS KeyStore".equals(format)) {
               throw new InvalidParameterException(SecurityLogger.getInvalidFormat(format));
            }

            this.importDataJKS(this.getRegistryDNName(), filename, constraints);
            var16 = false;
         }
      } catch (ErrorCollectionException var17) {
         auditException = var17;
         throw var17;
      } catch (InvalidParameterException var18) {
         doAudit = false;
         throw var18;
      } catch (RuntimeException var19) {
         auditException = var19;
         throw var19;
      } finally {
         if (var16) {
            if (doAudit && this.auditor != null) {
               String eventType = "importData";
               String eventData = "<Format = " + format + "><File Name = " + filename + "><Constraints = " + constraints + ">";
               this.auditMgmtEvent(eventType, eventData, (Exception)auditException);
            }

         }
      }

      if (doAudit && this.auditor != null) {
         String eventType = "importData";
         String eventData = "<Format = " + format + "><File Name = " + filename + "><Constraints = " + constraints + ">";
         this.auditMgmtEvent(eventType, eventData, (Exception)auditException);
      }

   }

   private boolean importGroup(ZipFile zipFile, String group) throws Exception {
      ZipEntry entry = zipFile.getEntry(group);
      if (entry != null) {
         InputStream is = zipFile.getInputStream(entry);
         if (!this.getRegistryDNName().equals(group) && !this.groupExists(group)) {
            this.createTrustGroup(group);
         }

         this.importDataNoAudit(group, is);
         return true;
      } else {
         return false;
      }
   }

   private List getGroupsFromZipFile(ZipFile zipFile) throws InvalidParameterException {
      Enumeration entryEnum = zipFile.entries();
      if (entryEnum == null) {
         throw new InvalidParameterException(SecurityLogger.getImportFileError());
      } else {
         List groupList = new ArrayList();
         if (this.isDebug()) {
            this.debug("getGroupsFromZipFile", "Groups in the given import file: ");
         }

         String group;
         for(; entryEnum.hasMoreElements(); groupList.add(group)) {
            group = ((ZipEntry)entryEnum.nextElement()).getName();
            if (this.isDebug()) {
               this.debug("getGroupsFromZipFile", group);
            }
         }

         if (groupList.isEmpty()) {
            throw new InvalidParameterException(SecurityLogger.getImportFileError());
         } else {
            return groupList;
         }
      }
   }

   private String[] parseTrustGroupsConstraints(Properties constraints) throws InvalidParameterException {
      String[] trustGroups = null;
      if (constraints != null && !constraints.isEmpty()) {
         if (!constraints.containsKey("com.bea.contextelement.security.CertificateGroup")) {
            throw new InvalidParameterException(SecurityLogger.getInvalidConstraints());
         }

         String toBeExportedString = constraints.getProperty("com.bea.contextelement.security.CertificateGroup");
         if (toBeExportedString != null) {
            trustGroups = toBeExportedString.split(",");
         }

         if (trustGroups != null) {
            for(int i = 0; i < trustGroups.length; ++i) {
               try {
                  this.validateGroup(trustGroups[i]);
               } catch (InvalidParameterException var6) {
                  throw new InvalidParameterException(SecurityLogger.getInvalidConstraints());
               }
            }
         }
      }

      return trustGroups;
   }

   private void importDataGroupJKS(String filename, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      this.validateFileName(filename);
      String[] groups = this.parseTrustGroupsConstraints(constraints);
      ZipFile zipFile = null;

      try {
         zipFile = new ZipFile(filename);
         this.importDataFromZipFile(zipFile, groups);
      } catch (IOException var13) {
         throw new InvalidParameterException(SecurityLogger.getImportFileError(), var13);
      } finally {
         if (zipFile != null) {
            try {
               zipFile.close();
            } catch (IOException var12) {
            }
         }

      }

   }

   private void importDataFromZipFile(ZipFile zipFile, String[] groups) throws InvalidParameterException, ErrorCollectionException {
      String method = "importDataFromZipFile";
      ErrorCollectionException errCollectionexception = new ErrorCollectionException(SecurityLogger.getImportErrors());
      List toBeImportedGroupList = null;
      List zipFileGroupList = this.getGroupsFromZipFile(zipFile);
      if (groups != null && groups.length != 0) {
         toBeImportedGroupList = new ArrayList();

         for(int i = 0; i < groups.length; ++i) {
            if (!zipFileGroupList.contains(groups[i])) {
               if (this.isDebug()) {
                  this.debug(method, "User wants to import group:" + groups[i] + " but it does not exist in the given file.");
               }

               errCollectionexception.add(new InvalidParameterException(ProvidersLogger.getGroupNotInImportFileError(groups[i], zipFile.getName())));
            } else {
               ((List)toBeImportedGroupList).add(groups[i]);
            }
         }

         if (((List)toBeImportedGroupList).isEmpty()) {
            throw errCollectionexception;
         }
      } else {
         if (this.isDebug()) {
            this.debug(method, "Null or empty group list, going to import all data.");
         }

         toBeImportedGroupList = zipFileGroupList;
      }

      Iterator groupIt = ((List)toBeImportedGroupList).iterator();

      while(groupIt.hasNext()) {
         String group = (String)groupIt.next();
         if (group == null || group.trim().length() == 0) {
            errCollectionexception.add(new InvalidParameterException(ProvidersLogger.getEmptyOrNullTrustGroup()));
         }

         try {
            if (this.isDebug()) {
               this.debug(method, "importing group: " + group);
            }

            if (!this.importGroup(zipFile, group)) {
               errCollectionexception.add(new NotFoundException(ProvidersLogger.getGroupNotInImportFileError(group, zipFile.getName())));
            }
         } catch (ErrorCollectionException var12) {
            Collection exceptions = var12.getExceptions();
            if (exceptions != null && exceptions.size() > 0) {
               Iterator itr = exceptions.iterator();

               while(itr.hasNext()) {
                  errCollectionexception.add((Throwable)itr.next());
               }
            }
         } catch (Exception var13) {
            errCollectionexception.add(var13);
         }
      }

      if (!errCollectionexception.isEmpty()) {
         throw errCollectionexception;
      }
   }

   private void importDataJKS(String group, String filename, Properties constraints) throws InvalidParameterException, ErrorCollectionException {
      this.validateConstraints(constraints);
      this.validateFileName(filename);
      File file = new File(filename);

      try {
         FileInputStream is = new FileInputStream(file);

         try {
            this.importDataNoAudit(group, is);
         } finally {
            closeStream((InputStream)is);
         }

      } catch (FileNotFoundException var10) {
         throw new InvalidParameterException(SecurityLogger.getUnableToReadFile(this.getFullPathName(file)));
      }
   }

   public void copyToPEM(String group, String alias, String filename) throws InvalidParameterException, NotFoundException {
      this.validateGroup(group);
      this.checkTrustGroupNameReserved(group);
      this.copyToPEMInternal(this.normalizeGroup(group), alias, filename);
   }

   private void copyToPEMInternal(String group, String alias, String filename) throws InvalidParameterException, NotFoundException {
      String method = "copyToPEM";
      if (this.isDebug()) {
         this.debug(method, "alias=" + alias + ", filename=" + filename);
      }

      this.validateFileName(filename);
      X509Certificate cert = this.getCertificateFromAliasInternal(group, alias);
      if (cert == null) {
         throw new NotFoundException(SecurityLogger.getCertificateAliasNotFound(alias));
      } else {
         File file = new File(filename);

         try {
            FileOutputStream os = new FileOutputStream(file);
            PrintStream ps = new PrintStream(os);

            try {
               ps.print(this.getPEM(cert));
            } finally {
               closeStream((OutputStream)ps);
            }

         } catch (FileNotFoundException var13) {
            throw new InvalidParameterException(SecurityLogger.getUnableToWriteFileError(this.getFullPathName(file)), var13);
         }
      }
   }

   public void copyToDER(String group, String alias, String filename) throws InvalidParameterException, NotFoundException {
      this.validateGroup(group);
      this.checkTrustGroupNameReserved(group);
      this.copyToDERInternal(this.normalizeGroup(group), alias, filename);
   }

   private void copyToDERInternal(String group, String alias, String filename) throws InvalidParameterException, NotFoundException {
      String method = "copyToDER";
      if (this.isDebug()) {
         this.debug(method, "alias=" + alias + ", filename=" + filename);
      }

      this.validateFileName(filename);
      X509Certificate cert = this.getCertificateFromAliasInternal(group, alias);
      if (cert == null) {
         throw new NotFoundException(SecurityLogger.getCertificateAliasNotFound(alias));
      } else {
         File file = new File(filename);

         try {
            FileOutputStream os = new FileOutputStream(file);

            try {
               os.write(this.getDER(cert));
            } catch (IOException var13) {
               handleUnexpectedException(var13);
            } finally {
               closeStream((OutputStream)os);
            }

         } catch (FileNotFoundException var15) {
            throw new InvalidParameterException(SecurityLogger.getUnableToWriteFileError(this.getFullPathName(file)), var15);
         }
      }
   }

   public String listAliasesByGroup(String group, String aliasWildcard, int maxToReturn) throws InvalidParameterException, InvalidCursorException {
      this.validateGroup(group);
      this.checkTrustGroupNameReserved(group);
      String method = "listAliasesByGroup";
      if (this.isDebug()) {
         this.debug(method, "aliasWildcard=" + aliasWildcard + ", maxToReturn=" + maxToReturn);
      }

      String rtn = this.listAliases(this.listerManager, this.normalizeGroup(group), aliasWildcard, maxToReturn);
      if (this.isDebug()) {
         this.debug(method, "returning " + rtn);
      }

      return rtn;
   }

   protected List searchCertificatesByAliasFilter(String group, String aliasWildCard, int maxToReturn) {
      Collection businessObjects = this.certStore.getRegEntriesByAliasPattern(group, aliasWildCard, maxToReturn);
      return businessObjects != null && businessObjects.size() > 0 ? new ArrayList(businessObjects) : null;
   }

   protected List searchCertificatesByAliasFilter(String aliasWildCard, int maxToReturn) {
      return this.searchCertificatesByAliasFilter(this.getRegistryDNName(), aliasWildCard, maxToReturn);
   }

   protected String listAliases(BusinessObjectListerManager lm, String aliasWildcard, int maxToReturn) throws InvalidParameterException, InvalidCursorException {
      return this.listAliases(lm, this.getRegistryDNName(), aliasWildcard, maxToReturn);
   }

   protected String listAliases(BusinessObjectListerManager lm, String group, String aliasWildcard, int maxToReturn) throws InvalidParameterException, InvalidCursorException {
      if (aliasWildcard != null && aliasWildcard.length() >= 1) {
         if (maxToReturn < 0) {
            throw new InvalidParameterException(SecurityLogger.getMaximumToReturnCanNotBeLessThanZero());
         } else {
            Collection businessObjects = this.certStore.getRegEntriesByAliasPattern(group, aliasWildcard, maxToReturn);
            List nameList = generateNameList(businessObjects);
            return lm.addLister(nameList, maxToReturn);
         }
      } else {
         throw new InvalidParameterException(SecurityLogger.getEmptyOrNullCertificateAliasWildcard());
      }
   }

   public boolean haveCurrent(String cursor) throws InvalidCursorException {
      String method = "";
      if (this.isDebug()) {
         this.debug(method, "cursor=" + cursor);
      }

      boolean rtn = ListerManager.haveCurrent(cursor);
      if (this.isDebug()) {
         this.debug(method, "returning " + rtn);
      }

      return rtn;
   }

   public String getCurrentName(String cursor) throws InvalidCursorException {
      String method = "getCurrentName";
      if (this.isDebug()) {
         this.debug(method, "cursor=" + cursor);
      }

      String rtn = (String)this.listerManager.getCurrentBusinessObject(cursor);
      if (this.isDebug()) {
         this.debug(method, "returning " + rtn);
      }

      return rtn;
   }

   public void advance(String cursor) throws InvalidCursorException {
      String method = "";
      if (this.isDebug()) {
         this.debug(method, "cursor=" + cursor);
      }

      ListerManager.advance(cursor);
   }

   public void close(String cursor) throws InvalidCursorException {
      String method = "close";
      if (this.isDebug()) {
         this.debug(method, "cursor=" + cursor);
      }

      ListerManager.close(cursor);
   }

   private static List generateNameList(Collection businessObjects) {
      ArrayList nameList = new ArrayList();
      if (businessObjects != null) {
         Iterator entries = businessObjects.iterator();

         while(entries.hasNext()) {
            String alias = ((WLSCertRegEntry)entries.next()).getCn();
            if (!RESERVED_CERT_REG_GROUP.equalsIgnoreCase(alias)) {
               nameList.add(alias);
            }
         }
      }

      return nameList;
   }

   public static String getSubjectKeyIdentifierString(byte[] subjectKeyIdentifier) {
      return subjectKeyIdentifier != null && subjectKeyIdentifier.length >= 1 ? (new BASE64Encoder()).encodeBuffer(subjectKeyIdentifier) : null;
   }

   private String normalizeOrDefaultGroup(String group) {
      if (group == null) {
         return "";
      } else {
         return this.getRegistryDNName().equalsIgnoreCase(group) ? this.getRegistryDNName() : this.normalizeGroup(group);
      }
   }

   public X509Certificate getCertificateFromSubjectDN(String group, String subjectDN) {
      try {
         return this.getCertificate(this.certStore.getRegEntryBySubjectDN(this.normalizeOrDefaultGroup(group), normalizeDN(subjectDN)));
      } catch (Exception var4) {
         handleUnexpectedException(var4);
         return null;
      }
   }

   public X509Certificate getCertificateFromIssuerDNAndSerialNumber(String group, String issuerDN, BigInteger serialNumber) {
      if (group == null) {
         group = this.getRegistryDNName();
      } else {
         group = this.normalizeGroup(group);
      }

      try {
         return this.getCertificate(this.certStore.getRegEntryByIssuerDN(this.normalizeOrDefaultGroup(group), normalizeDN(issuerDN), serialNumber.toString()));
      } catch (Exception var5) {
         handleUnexpectedException(var5);
         return null;
      }
   }

   public X509Certificate getCertificateFromSubjectKeyIdentifierString(String group, String skidString) {
      try {
         return this.getCertificate(this.certStore.getRegEntryBySubjectKeyId(this.normalizeOrDefaultGroup(group), skidString));
      } catch (Exception var4) {
         handleUnexpectedException(var4);
         return null;
      }
   }

   public X509Certificate getCertificateFromThumbprint(String thumbprint) {
      return this.getCertificateFromThumbprint(this.getRegistryDNName(), thumbprint);
   }

   public X509Certificate getCertificateFromThumbprint(String group, String thumbprint) {
      String normalizedGroup = this.normalizeOrDefaultGroup(group);
      this.initializeThumbprintMap(normalizedGroup);
      String alias = this.getFromThumbprintMap(normalizedGroup, thumbprint);
      if (this.isDebug()) {
         this.debug("getCertificateFromThumbprint", " trust group=" + group + " thumbprint= " + thumbprint + " alias=" + alias);
      }

      if (alias == null) {
         return null;
      } else {
         X509Certificate returnCert = null;

         try {
            returnCert = this.getCertificate(this.certStore.getRegEntryByAlias(normalizedGroup, alias));
         } catch (Exception var7) {
            handleUnexpectedException(var7);
         }

         return returnCert;
      }
   }

   protected abstract String getRegistryDNName();

   protected abstract String getBaseAuditEventType();

   protected abstract String getDebugLogName();

   protected boolean ignoreCertPathValidators() {
      return false;
   }

   private void auditMgmtEvent(String eventType, String eventData, Exception exception) {
      if (this.auditor != null) {
         this.auditor.providerAuditWriteEvent(new CertRegAuditMgmtEvent(this.constructEventType(eventType), eventData, exception));
      }
   }

   private String constructEventType(String eventType) {
      return this.getBaseAuditEventType() + " " + eventType;
   }

   protected CertRegLDAPDelegate() {
   }

   public void initialize(ProviderMBean mbean, SecurityServices services) {
   }

   protected CertRegLDAPDelegate(ProviderMBean mbean, SecurityServices services) {
      String method = "constructor";
      this.log = ((ExtendedSecurityServices)services).getLogger(this.getDebugLogName());
      this.realmName = mbean.getRealm().getName();
      this.domainName = Utils.getDomainName(services);
      this.storeService = Utils.getStoreService(services);
      this.certStore = new StoreServiceBasedCertRegStore(this.domainName, this.realmName, this.storeService, this.log);
      this.auditor = services.getAuditorService();
      if (this.isDebug()) {
         this.debug(method, "succeeded.  Delegate = " + this);
      }

   }

   public void shutdown() {
      this.clearThumbprintMap();
   }

   private ThumbprintMapKey getThumbprintMapKey(String group) {
      synchronized(this.thumbprintMapKeyMap) {
         ThumbprintMapKey key = (ThumbprintMapKey)this.thumbprintMapKeyMap.get(group);
         if (key == null) {
            key = new ThumbprintMapKey(this.domainName, this.realmName, group);
            this.thumbprintMapKeyMap.put(group, key);
         }

         return key;
      }
   }

   private void initializeThumbprintMap(String group) {
      ThumbprintMap thumbprintAliases = CertRegLDAPDelegate.ThumbprintMapFactory.getThumbprintMap(this.getThumbprintMapKey(group));
      synchronized(thumbprintAliases) {
         if (!thumbprintAliases.isInitialized()) {
            this.log.info("initializing Thumbprint Map...");
            Date curTime = new Date();
            Collection businessObjects = this.certStore.getRegEntriesByAliasPattern(group, "*", 0);
            if (this.isDebug()) {
               this.log.debug("Time to retrieve " + (businessObjects == null ? 0 : businessObjects.size()) + " certificates from the store: " + ((new Date()).getTime() - curTime.getTime()));
            }

            thumbprintAliases.setInitialized(true);
            if (this.storeService != null) {
               this.storeService.addRemoteCommitListener(com.bea.common.security.store.data.WLSCertRegEntry.class, new ThumbprintRemoteCommitListener());
            }

            if (businessObjects != null && !businessObjects.isEmpty()) {
               curTime = new Date();
               Iterator entries = businessObjects.iterator();

               while(entries.hasNext()) {
                  WLSCertRegEntry businessObject = (WLSCertRegEntry)entries.next();
                  this.putToThumbprintMap(thumbprintAliases, businessObject);
               }

               if (this.isDebug()) {
                  this.log.debug("Time to generate " + businessObjects.size() + " thumbprints: " + ((new Date()).getTime() - curTime.getTime()));
               }

               if (this.log.isDebugEnabled()) {
                  this.log.debug("Thumbprint Map for trust group " + group + " initialized -#count:" + thumbprintAliases.size());
               }

               this.log.info("Thumbprint Map initialized -#count:" + thumbprintAliases.size());
            } else {
               if (this.log.isDebugEnabled()) {
                  this.log.debug("Thumbprint Map for trust group " + group + " initialized -#count: 0");
               }

               this.log.info("Thumbprint Map initialized -#count: 0");
            }
         }
      }
   }

   private void clearThumbprintMap() {
      this.log.info("clearing Thumbprint Map...");
      CertRegLDAPDelegate.ThumbprintMapFactory.clearThumbprintMaps();
      this.log.info("Thumbprint Map cleared.");
   }

   private boolean putToThumbprintMap(HashMap thumbprintAliases, WLSCertRegEntry businessObject) {
      boolean returnValue = false;
      if (businessObject == null) {
         return returnValue;
      } else {
         try {
            String alias = getAlias(businessObject);
            X509Certificate cert = this.getCertificate(businessObject);
            if (alias != null && cert != null) {
               String base64ThumbprintString = WSSThumbprint.generateThumbprint(cert);
               thumbprintAliases.put(base64ThumbprintString, alias);
               returnValue = true;
            } else {
               this.log.error("alias or certificate retrieved from WLSCertRegEntry is null");
            }
         } catch (Throwable var7) {
            this.log.error("Failed to generate thumbprint from WLSCertRegEntry: " + var7.getMessage(), var7);
         }

         return returnValue;
      }
   }

   private boolean putToThumbprintMap(HashMap thumbprintAliases, byte[] derEncodedCert, String alias) {
      boolean returnValue = false;

      try {
         String thumbprint = WSSThumbprint.generateThumbprint(derEncodedCert);
         thumbprintAliases.put(thumbprint, alias);
         returnValue = true;
         if (this.isDebug()) {
            this.log.debug("Put to Thumbprint Map - alias: " + alias + " # map count: " + thumbprintAliases.size());
         }
      } catch (Exception var6) {
         this.log.error("Failed to generate thumbprint for certificate with alias: " + alias, var6);
      }

      return returnValue;
   }

   private boolean removeFromThumbprintMap(HashMap thumbprintAliases, String alias) {
      Collection aliases = thumbprintAliases.values();
      boolean removed = aliases.remove(alias);
      if (this.isDebug()) {
         this.log.debug((removed ? "Removed from" : "Not found in") + " the Thumbprint Map - alias: " + alias + " #map count: " + thumbprintAliases.size());
      }

      return removed;
   }

   private String getFromThumbprintMap(String group, String thumbprint) {
      String returnValue = null;
      ThumbprintMap thumbprintAliases = CertRegLDAPDelegate.ThumbprintMapFactory.getThumbprintMap(this.getThumbprintMapKey(group));
      synchronized(thumbprintAliases) {
         if (thumbprintAliases.isInitialized()) {
            returnValue = (String)thumbprintAliases.get(thumbprint);
         }

         return returnValue;
      }
   }

   public void registerCertificate(String alias, String certificateFile) throws AlreadyExistsException, InvalidParameterException {
      this.registerCertificate(this.getRegistryDNName(), alias, certificateFile);
   }

   public void registerCertificate(String alias, X509Certificate endcert) throws InvalidParameterException, ErrorCollectionException {
      this.registerCertificate(this.getRegistryDNName(), alias, endcert);
   }

   public void unregisterCertificate(String alias) throws NotFoundException, InvalidParameterException {
      this.unregisterCertificate(this.getRegistryDNName(), alias);
   }

   public String listAliases(String aliasWildcard, int maxToReturn) throws InvalidParameterException, InvalidCursorException {
      String method = "listAliases";
      if (this.isDebug()) {
         this.debug(method, "aliasWildcard=" + aliasWildcard + ", maxToReturn=" + maxToReturn);
      }

      String rtn = this.listAliases(this.listerManager, this.getRegistryDNName(), aliasWildcard, maxToReturn);
      if (this.isDebug()) {
         this.debug(method, "returning " + rtn);
      }

      return rtn;
   }

   public X509Certificate getCertificateFromAlias(String alias) throws NotFoundException, InvalidParameterException {
      return this.getCertificateFromAliasInternal(this.getRegistryDNName(), alias);
   }

   public boolean aliasExists(String alias) throws InvalidParameterException {
      this.validateAlias(alias);
      boolean exists = false;

      try {
         exists = this.aliasExistsInternal(this.getRegistryDNName(), normalizeAlias(alias));
      } catch (Exception var4) {
         handleUnexpectedException(var4);
      }

      return exists;
   }

   public void copyToPEM(String alias, String certificateFile) throws InvalidParameterException, NotFoundException {
      this.copyToPEMInternal(this.getRegistryDNName(), alias, certificateFile);
   }

   public void copyToDER(String alias, String certificateFile) throws InvalidParameterException, NotFoundException {
      this.copyToDERInternal(this.getRegistryDNName(), alias, certificateFile);
   }

   public X509Certificate getCertificateFromSubjectDN(String subjectDN) {
      return this.getCertificateFromSubjectDN(this.getRegistryDNName(), subjectDN);
   }

   public X509Certificate getCertificateFromIssuerDNAndSerialNumber(String issuerDN, BigInteger serialNumber) {
      return this.getCertificateFromIssuerDNAndSerialNumber(this.getRegistryDNName(), issuerDN, serialNumber);
   }

   public X509Certificate getCertificateFromSubjectKeyIdentifierString(String skidString) {
      return this.getCertificateFromSubjectKeyIdentifierString(this.getRegistryDNName(), skidString);
   }

   public void createTrustGroup(String group) throws AlreadyExistsException, InvalidParameterException {
      this.checkTrustGroupNameReserved(group);
      boolean doAudit = true;
      RuntimeException auditException = null;
      boolean var13 = false;

      try {
         var13 = true;
         this.registerGroupNoAudit(group);
         var13 = false;
      } catch (AlreadyExistsException var14) {
         doAudit = false;
         throw var14;
      } catch (InvalidParameterException var15) {
         doAudit = false;
         throw var15;
      } catch (RuntimeException var16) {
         auditException = var16;
         throw var16;
      } finally {
         if (var13) {
            if (doAudit && this.auditor != null) {
               String eventType = "registerGroup";
               String eventData = "<Trust group = " + group + ">";
               this.auditMgmtEvent(eventType, eventData, auditException);
            }

         }
      }

      if (doAudit && this.auditor != null) {
         String eventType = "registerGroup";
         String eventData = "<Trust group = " + group + ">";
         this.auditMgmtEvent(eventType, eventData, auditException);
      }

   }

   public String listGroups(String groupWildcard, int maxToReturn) throws InvalidCursorException, InvalidParameterException {
      String method = "listGroups";
      if (this.isDebug()) {
         this.debug(method, "aliasWildcard=" + groupWildcard + ", maxToReturn=" + maxToReturn);
      }

      String rtn = this.listGroups(this.listerManager, groupWildcard, maxToReturn);
      if (this.isDebug()) {
         this.debug(method, "returning " + rtn);
      }

      return rtn;
   }

   protected String listGroups(BusinessObjectListerManager lm, String groupWildcard, int maxToReturn) throws InvalidParameterException, InvalidCursorException {
      if (groupWildcard != null && groupWildcard.length() >= 1) {
         if (maxToReturn < 0) {
            throw new InvalidParameterException(SecurityLogger.getMaximumToReturnCanNotBeLessThanZero());
         } else {
            Collection businessObjects = this.certStore.getRegEntriesByRegistryPattern(groupWildcard, RESERVED_CERT_REG_GROUP, maxToReturn);
            List nameList = this.generateGroupNameList(businessObjects);
            nameList.remove(this.getRegistryDNName());
            return lm.addLister(nameList, maxToReturn);
         }
      } else {
         throw new InvalidParameterException(ProvidersLogger.getEmptyOrNullTrustGroupWildcard());
      }
   }

   public boolean groupExists(String group) throws InvalidParameterException {
      return this.aliasExists(group, RESERVED_CERT_REG_GROUP);
   }

   public void removeTrustGroup(String group) throws NotFoundException, InvalidParameterException {
      this.checkTrustGroupNameReserved(group);
      boolean doAudit = true;
      RuntimeException auditException = null;
      boolean var13 = false;

      try {
         var13 = true;
         this.unregisterGroupNoAudit(group);
         var13 = false;
      } catch (NotFoundException var14) {
         doAudit = false;
         throw var14;
      } catch (InvalidParameterException var15) {
         doAudit = false;
         throw var15;
      } catch (RuntimeException var16) {
         auditException = var16;
         throw var16;
      } finally {
         if (var13) {
            if (doAudit && this.auditor != null) {
               String eventType = "unRegisterGroup";
               String eventData = "<Trust group = " + group + ">";
               this.auditMgmtEvent(eventType, eventData, auditException);
            }

         }
      }

      if (doAudit && this.auditor != null) {
         String eventType = "unRegisterGroup";
         String eventData = "<Trust group = " + group + ">";
         this.auditMgmtEvent(eventType, eventData, auditException);
      }

   }

   private void checkGroupExists(String group) throws InvalidParameterException {
      if (!this.getRegistryDNName().equalsIgnoreCase(group)) {
         if (!this.aliasExistsInternal(group, RESERVED_CERT_REG_GROUP)) {
            if (this.isDebug()) {
               this.debug("checkGroupExists", "group " + group + " already exists");
            }

            throw new InvalidParameterException(ProvidersLogger.getTrustGroupNotFound(group));
         }
      }
   }

   private void checkGroupFound(String group) throws NotFoundException {
      if (!this.getRegistryDNName().equalsIgnoreCase(group)) {
         String method = "checkGroupFound";
         if (!this.aliasExistsInternal(group, RESERVED_CERT_REG_GROUP)) {
            if (this.isDebug()) {
               this.debug(method, "group " + group + " already exists");
            }

            throw new NotFoundException(ProvidersLogger.getTrustGroupNotFound(group));
         }
      }
   }

   private void unregisterGroupNoAudit(String group) throws NotFoundException, InvalidParameterException {
      String method = "unRegisterGroup";
      if (this.isDebug()) {
         this.debug(method, "group=" + group);
      }

      this.validateGroup(group);
      String normalizedGroup = this.normalizeGroup(group);

      try {
         this.checkGroupFound(normalizedGroup);
         this.certStore.unregisterGroup(normalizedGroup);
         if (this.isDebug()) {
            this.debug(method, "group " + normalizedGroup + " and its certificates have been removed from the registry");
         }
      } catch (NotFoundException var5) {
         throw var5;
      } catch (InvalidParameterException var6) {
         throw var6;
      } catch (Throwable var7) {
         handleUnexpectedException(var7);
      }

   }

   public void copyFromJKS(String group, String JKSFile) throws NotFoundException, InvalidParameterException, ErrorCollectionException {
      this.validateGroup(group);
      this.checkTrustGroupNameReserved(group);
      this.validateFileName(JKSFile);
      this.checkGroupFound(this.normalizeGroup(group));
      this.importDataJKS(this.normalizeGroup(group), JKSFile, (Properties)null);
   }

   public void copyToJKS(String group, String JKSFile) throws NotFoundException, InvalidParameterException, ErrorCollectionException {
      this.validateGroup(group);
      this.checkTrustGroupNameReserved(group);
      this.validateFileName(JKSFile);
      this.checkGroupFound(this.normalizeGroup(group));
      this.exportDataJKS(this.normalizeGroup(group), "JKS KeyStore", JKSFile, (Properties)null);
   }

   private class ThumbprintRemoteCommitListener implements RemoteCommitListener {
      private ThumbprintRemoteCommitListener() {
      }

      public void afterCommit(RemoteCommitEvent event) {
         CertRegLDAPDelegate.this.log.info("calling afterCommit() ...");
         Collection addedObjectIds = event.getAddedObjectIds();
         Collection deletedObjectIds = event.getDeletedObjectIds();
         this.handleCertificateAdded(addedObjectIds);
         this.handleCertificateDeleted(deletedObjectIds);
         CertRegLDAPDelegate.this.log.info("afterCommit() ends.");
      }

      private void handleCertificateAdded(Collection objectIds) {
         if (objectIds != null && !objectIds.isEmpty()) {
            int count = 0;
            Iterator i = objectIds.iterator();

            while(i.hasNext()) {
               Object objectId = i.next();
               WLSCertRegEntryId certRegEntryId = this.convertToCertRegEntryId(objectId);
               ThumbprintMap thumbprintAliases = CertRegLDAPDelegate.ThumbprintMapFactory.getThumbprintMap(CertRegLDAPDelegate.this.getThumbprintMapKey(certRegEntryId.getRegistryName()));
               synchronized(thumbprintAliases) {
                  if (thumbprintAliases.isInitialized()) {
                     WLSCertRegEntry certRegEntry = StoreServiceBasedCertRegStore.convert2StoreEntry(this.getCertRegEntry(objectId));
                     if (CertRegLDAPDelegate.this.putToThumbprintMap(thumbprintAliases, certRegEntry)) {
                        ++count;
                     }
                  }
               }
            }

            CertRegLDAPDelegate.this.log.info("handleCertificateAdded - #objectIds: " + objectIds.size() + " #mapping entries added: " + count);
         } else {
            if (CertRegLDAPDelegate.this.isDebug()) {
               CertRegLDAPDelegate.this.log.debug("handleCertificateAdded - objectIds=null || empty");
            }

         }
      }

      private void handleCertificateDeleted(Collection objectIds) {
         if (objectIds != null && !objectIds.isEmpty()) {
            int count = 0;
            Iterator i = objectIds.iterator();

            while(i.hasNext()) {
               Object objectId = i.next();
               WLSCertRegEntryId certRegEntryId = this.convertToCertRegEntryId(objectId);
               ThumbprintMap thumbprintAliases = CertRegLDAPDelegate.ThumbprintMapFactory.getThumbprintMap(CertRegLDAPDelegate.this.getThumbprintMapKey(certRegEntryId.getRegistryName()));
               synchronized(thumbprintAliases) {
                  if (thumbprintAliases.isInitialized()) {
                     String alias = certRegEntryId.getCn();
                     if (CertRegLDAPDelegate.this.removeFromThumbprintMap(thumbprintAliases, alias)) {
                        ++count;
                     }
                  }
               }
            }

            CertRegLDAPDelegate.this.log.info("handleCertificateDeleted - #objectIds: " + objectIds.size() + " #mapping entries removed: " + count);
         } else {
            if (CertRegLDAPDelegate.this.isDebug()) {
               CertRegLDAPDelegate.this.log.debug("handleCertificateDeleted - objectIds=null || empty");
            }

         }
      }

      private com.bea.common.security.store.data.WLSCertRegEntry getCertRegEntry(Object objectId) {
         com.bea.common.security.store.data.WLSCertRegEntry certRegEntry = null;
         WLSCertRegEntryId certRegEntryId = this.convertToCertRegEntryId(objectId);
         if (certRegEntryId != null) {
            PersistenceManager pm = CertRegLDAPDelegate.this.storeService.getPersistenceManager();

            try {
               certRegEntry = (com.bea.common.security.store.data.WLSCertRegEntry)certRegEntryId.getObject(pm, true);
            } finally {
               pm.close();
            }
         }

         return certRegEntry;
      }

      private WLSCertRegEntryId convertToCertRegEntryId(Object objectId) {
         WLSCertRegEntryId returnValue = null;
         if (objectId instanceof WLSCertRegEntryId) {
            WLSCertRegEntryId certRegEntryId = (WLSCertRegEntryId)objectId;
            String domainName = certRegEntryId.getDomainName();
            String realmName = certRegEntryId.getRealmName();
            String registryDNName = certRegEntryId.getRegistryName();
            if (CertRegLDAPDelegate.this.getThumbprintMapKey(registryDNName).equals(domainName, realmName, registryDNName)) {
               returnValue = certRegEntryId;
            }
         }

         return returnValue;
      }

      // $FF: synthetic method
      ThumbprintRemoteCommitListener(Object x1) {
         this();
      }
   }

   private static class ThumbprintMap extends HashMap {
      private boolean initialized;

      private ThumbprintMap() {
         this.initialized = false;
      }

      boolean isInitialized() {
         return this.initialized;
      }

      void setInitialized(boolean initialized) {
         this.initialized = initialized;
      }

      // $FF: synthetic method
      ThumbprintMap(Object x0) {
         this();
      }
   }

   private static class ThumbprintMapFactory {
      private static Map thumbprintMaps = new HashMap();

      static synchronized ThumbprintMap getThumbprintMap(ThumbprintMapKey key) {
         ThumbprintMap map = (ThumbprintMap)thumbprintMaps.get(key);
         if (map == null) {
            map = new ThumbprintMap();
            thumbprintMaps.put(key, map);
         }

         return map;
      }

      static synchronized void clearThumbprintMaps() {
         Collection values = thumbprintMaps.values();
         Iterator iterator = values.iterator();

         while(iterator.hasNext()) {
            ThumbprintMap map = (ThumbprintMap)iterator.next();
            synchronized(map) {
               map.clear();
            }
         }

         thumbprintMaps.clear();
      }
   }

   private static class ThumbprintMapKey {
      private String domainName;
      private String realmName;
      private String registryDNName;

      ThumbprintMapKey(String aDomainName, String aRealmName, String aRegistryDNName) {
         this.domainName = aDomainName;
         this.realmName = aRealmName;
         this.registryDNName = aRegistryDNName;
      }

      public boolean equals(Object anObject) {
         if (this == anObject) {
            return true;
         } else {
            boolean returnValue = false;
            if (anObject instanceof ThumbprintMapKey) {
               ThumbprintMapKey aKey = (ThumbprintMapKey)anObject;
               returnValue = this.equals(aKey.domainName, aKey.realmName, aKey.registryDNName);
            }

            return returnValue;
         }
      }

      public int hashCode() {
         return this.domainName.hashCode() ^ this.realmName.hashCode() ^ this.registryDNName.hashCode();
      }

      boolean equals(String aDomainName, String aRealmName, String aRegistryDNName) {
         return this.domainName.equals(aDomainName) && this.realmName.equals(aRealmName) && this.registryDNName.equals(aRegistryDNName);
      }
   }

   private static final class CertRegAuditMgmtEvent extends AuditBaseEventImpl implements AuditMgmtEvent {
      private String eventData;

      public CertRegAuditMgmtEvent(String eventType, String eventData, Exception exception) {
         super(exception == null ? AuditSeverity.INFORMATION : AuditSeverity.FAILURE, eventType, exception);
         this.eventData = eventData;
      }

      protected void writeAttributes(StringBuffer buf) {
         super.writeAttributes(buf);
         buf.append(this.eventData);
      }
   }
}

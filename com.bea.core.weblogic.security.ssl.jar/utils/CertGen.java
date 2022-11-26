package utils;

import com.rsa.certj.cert.AttributeValueAssertion;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.RDN;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.AuthorityKeyID;
import com.rsa.certj.cert.extensions.BasicConstraints;
import com.rsa.certj.cert.extensions.GeneralName;
import com.rsa.certj.cert.extensions.KeyUsage;
import com.rsa.certj.cert.extensions.SubjectAltName;
import com.rsa.certj.cert.extensions.SubjectKeyID;
import com.rsa.jsafe.CryptoJ;
import com.rsa.jsafe.JSAFE_InvalidUseException;
import com.rsa.jsafe.JSAFE_KeyPair;
import com.rsa.jsafe.JSAFE_Parameters;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import com.rsa.jsafe.JSAFE_UnimplementedException;
import com.rsa.jsafe.cert.KeyIdentifier;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import weblogic.management.bootstrap.WeblogicHome;
import weblogic.security.SSL.jsseadapter.RSAPKFactory;
import weblogic.security.internal.encryption.JSafeEncryptionServiceImpl;
import weblogic.utils.encoders.BASE64Decoder;

public class CertGen {
   public static final String COUNTRY_PROPERTY = "x500name.country";
   public static final String STATE_PROPERTY = "x500name.state";
   public static final String LOCALITY_PROPERTY = "x500name.town";
   public static final String ORGANIZATION_PROPERTY = "x500name.organization";
   public static final String ORG_UNIT_PROPERTY = "x500name.orgunit";
   public static final String COMMON_NAME_PROPERTY = "x500name.commonname";
   public static final String EMAIL_PROPERTY = "x500name.email";
   private static final String DEFAULT_COUNTRY = "US";
   private static final String DEFAULT_STATE = "MyState";
   private static final String DEFAULT_LOCALITY = "MyTown";
   private static final String DEFAULT_ORGANIZATION = "MyOrganization";
   private static final String DEFAULT_ORG_UNIT = "FOR TESTING ONLY";
   private static final String DEFAULT_COMMON_NAME = "localhost";
   private static final String DEFAULT_CA_CERT = "CertGenCA.der";
   private static final String DEFAULT_CA_KEY = "CertGenCAKey.der";
   private static final String DEFAULT_CA_PWD = "password";
   private static final int CERT_YEARS_VALID = 15;
   private static final int CA_YEARS_VALID = 20;
   private static final int EXPORT_KEY_STRENGTH = 512;
   private static final int DOMESTIC_KEY_STRENGTH = 2048;
   private static final String DIGEST_ALGORITHM = "SHA256";
   private static final String[] KEY_USAGE_NAMES = new String[]{"digitalSignature", "nonRepudiation", "keyEncipherment", "dataEncipherment", "keyAgreement", "keyCertSign", "cRLSign", "encipherOnly", "decipherOnly"};
   private static final int[] KEY_USAGE_BITS = new int[]{Integer.MIN_VALUE, 1073741824, 536870912, 268435456, 134217728, 67108864, 33554432, 16777216, 8388608};
   private JSAFE_SecureRandom random;
   private JSAFE_PrivateKey issuerPrivateKey;
   private X500Name issuerName;
   private SubjectKeyID issuerSubjectKeyID;
   private JSAFE_PrivateKey subjectPrivateKey;
   private JSAFE_PublicKey subjectPublicKey;
   private X500Name subjectName;
   private X509Certificate subjectCert;
   private int keyStrength;
   private String digestAlgorithm;
   private boolean noSkid;
   private KeyUsage keyUsage;
   private SubjectKeyID subjectKeyIdentifier;
   private String sanStr;
   private boolean noSanHostDns;

   public CertGen(boolean export) throws Exception {
      this(export ? 512 : 2048);
   }

   public CertGen(int keyStrength) throws Exception {
      this.keyStrength = 2048;
      this.digestAlgorithm = "SHA256";
      this.noSkid = false;
      this.keyUsage = null;
      this.subjectKeyIdentifier = null;
      this.sanStr = null;
      this.noSanHostDns = false;
      this.random = (JSAFE_SecureRandom)JSAFE_SecureRandom.getInstance("HMACDRBG", "Java");
      this.random.seed(generateSeed());
      this.keyStrength = keyStrength;
   }

   public void setSubjectKeyIdentifier(byte[] skid) {
      this.subjectKeyIdentifier = skid != null ? new SubjectKeyID(skid, 0, skid.length, false) : null;
   }

   public void setKeyUsage(int keyUsageBits, boolean critical) {
      this.keyUsage = new KeyUsage(keyUsageBits, critical);
   }

   public void setDigestAlgorithm(String digAlg) {
      this.digestAlgorithm = digAlg;
   }

   public void setNoSkid(boolean noSkid) {
      this.noSkid = noSkid;
   }

   public void setSanStr(String sanStr) {
      this.sanStr = sanStr;
   }

   public void setNoSanHostDns(boolean noSanHostDns) {
      this.noSanHostDns = noSanHostDns;
   }

   public PrivateKey getSubjectPrivateKey() throws Exception {
      return convert(this.subjectPrivateKey);
   }

   public Certificate getSubjectCertificate() throws Exception {
      return convert(this.subjectCert);
   }

   private static byte[] generateSeed() {
      StringBuffer seed = new StringBuffer();
      seed.append("IDH").append(System.identityHashCode(seed));
      seed.append("FM").append(Runtime.getRuntime().freeMemory());
      seed.append("CT").append(System.currentTimeMillis());
      Enumeration e = System.getProperties().elements();

      while(e.hasMoreElements()) {
         seed.append(e.nextElement());
      }

      seed.append("VHC").append(seed.hashCode());
      return seed.toString().getBytes();
   }

   private void generateKeys() throws Exception {
      JSAFE_KeyPair keyPairGenerator = JSAFE_KeyPair.getInstance("RSA", "Java");

      try {
         int[] parameters = new int[]{this.keyStrength, 65537};
         keyPairGenerator.generateInit((JSAFE_Parameters)null, parameters, this.random);
         keyPairGenerator.generate();
         this.subjectPublicKey = keyPairGenerator.getPublicKey();
         this.subjectPrivateKey = keyPairGenerator.getPrivateKey();
      } finally {
         if (keyPairGenerator != null) {
            keyPairGenerator.clearSensitiveData();
         }

      }

   }

   private void generateSubject(Properties nameAttr) throws Exception {
      this.generateKeys();
      this.subjectName = createX500Name(nameAttr);
   }

   public void generateCACertificate(Properties nameAttr) throws Exception {
      this.generateSubject(nameAttr);
      this.issuerPrivateKey = this.subjectPrivateKey;
      this.issuerName = this.subjectName;
      checkForConflictingKeyUsage(this.keyUsage);
      this.generateCertificate(true);
   }

   public void generateCertificate(Properties nameAttr) throws Exception {
      this.generateCertificate(nameAttr, findFile("CertGenCA.der"), findFile("CertGenCAKey.der"), "password");
   }

   public void generateCertificate(Properties nameAttr, String issuerCertFile, String issuerPKFile, String pkPassword) throws Exception {
      X509Certificate issuerCert = loadX509Certificate(issuerCertFile);
      JSAFE_PrivateKey issuerPK = loadPKCS8PrivateKey(issuerPKFile, pkPassword);
      this.generateCertificate(nameAttr, issuerCert, issuerPK);
   }

   public void generateCertificate(Properties nameAttr, X509Certificate cert, JSAFE_PrivateKey key) throws Exception {
      this.generateSubject(nameAttr);
      this.issuerName = cert.getSubjectName();
      this.issuerPrivateKey = key;
      this.issuerSubjectKeyID = (SubjectKeyID)cert.getExtensions().getExtensionByType(14);
      checkCAKeyUsage(cert);
      checkForConflictingKeyUsage(this.keyUsage);
      this.generateCertificate(false);
   }

   private void generateCertificate(boolean caCert) throws Exception {
      X509Certificate certificate = new X509Certificate();
      certificate.setVersion(2);
      byte[] serialNumber = new byte[16];
      this.random.generateRandomBytes(serialNumber, 0, serialNumber.length);
      certificate.setSerialNumber(serialNumber, 0, serialNumber.length);
      X509V3Extensions extensions = new X509V3Extensions(1);
      if (this.keyUsage != null) {
         extensions.addV3Extension(this.keyUsage);
      } else if (caCert) {
         extensions.addV3Extension(new KeyUsage(67108864, true));
      }

      if (caCert) {
         extensions.addV3Extension(new BasicConstraints(true, 1, true));
      } else {
         AuthorityKeyID akid = this.getAuthorityKeyID();
         if (akid != null) {
            extensions.addV3Extension(akid);
         }
      }

      if (!this.noSkid) {
         if (this.subjectKeyIdentifier != null) {
            extensions.addV3Extension(this.subjectKeyIdentifier);
         } else {
            extensions.addV3Extension(this.getSubjectKeyID());
         }
      }

      if (!this.noSanHostDns || this.sanStr != null) {
         SubjectAltName subAltName = this.addSan(this.noSanHostDns, this.sanStr);
         extensions.addV3Extension(subAltName);
      }

      certificate.setExtensions(extensions);
      Calendar cal = Calendar.getInstance();
      cal.add(5, -1);
      Date notBefore = cal.getTime();
      cal.add(5, 1);
      cal.set(1, cal.get(1) + (caCert ? 20 : 15));
      Date notAfter = cal.getTime();
      certificate.setValidity(notBefore, notAfter);
      certificate.setSubjectName(this.subjectName);
      certificate.setSubjectPublicKey(this.subjectPublicKey);
      certificate.setIssuerName(this.issuerName);
      if (CryptoJ.getMode() == 0) {
         CryptoJ.setMode(2);
      }

      certificate.signCertificate(this.digestAlgorithm + "/RSA/PKCS1Block01Pad", "Java", this.issuerPrivateKey, this.random);
      this.subjectCert = certificate;
   }

   private SubjectAltName addSan(boolean noSanHostDns, String sanStr) throws Exception {
      SubjectAltName subAltName = new SubjectAltName();
      if (!noSanHostDns) {
         GeneralName generalName = new GeneralName();
         generalName.setGeneralName(InetAddress.getLocalHost().getCanonicalHostName(), 3);
         subAltName.addGeneralName(generalName);
      }

      if (sanStr != null) {
         List sanArrlist = this.getSanList(sanStr);

         for(int i = 0; i < sanArrlist.size(); i += 2) {
            GeneralName generalName = new GeneralName();
            if (((String)sanArrlist.get(i)).equalsIgnoreCase("IP")) {
               InetAddress inetAddress = InetAddress.getByName((String)sanArrlist.get(i + 1));
               generalName.setGeneralName(inetAddress, 8);
            } else if (((String)sanArrlist.get(i)).equalsIgnoreCase("DNS")) {
               generalName.setGeneralName(sanArrlist.get(i + 1), 3);
            }

            subAltName.addGeneralName(generalName);
         }
      }

      return subAltName;
   }

   private List getSanList(String sanStr) {
      String[] sanArr = sanStr.split(",");
      String[] sanArrTemp = null;
      List sanArrlist = new ArrayList();
      String[] sanItems = new String[]{"DNS", "IP"};
      String[] var6 = sanArr;
      int var7 = sanArr.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String san = var6[var8];
         sanArrTemp = san.split(":", 2);
         if (!this.checkDNSOrIP(sanArrTemp[0], sanItems)) {
            throw new IllegalArgumentException("DNS or IP are only supported for the argument -a.");
         }

         sanArrlist.add(sanArrTemp[0]);
         sanArrlist.add(sanArrTemp[1]);
      }

      return sanArrlist;
   }

   public boolean checkDNSOrIP(String san, String[] items) {
      for(int i = 0; i < items.length; ++i) {
         if (san.contains(items[i])) {
            return true;
         }
      }

      return false;
   }

   private SubjectKeyID getSubjectKeyID() throws JSAFE_UnimplementedException, NoSuchAlgorithmException, InvalidKeySpecException {
      byte[][] keyData = this.subjectPublicKey.getKeyData("RSAPublicKey");
      KeySpec ks = new RSAPublicKeySpec(new BigInteger(1, keyData[0]), new BigInteger(keyData[1]));
      PublicKey pubKey = KeyFactory.getInstance("RSA").generatePublic(ks);
      byte[] keyID = KeyIdentifier.generateKeyIdentifier(pubKey);
      SubjectKeyID skid = new SubjectKeyID(keyID, 0, keyID.length, false);
      return skid;
   }

   private AuthorityKeyID getAuthorityKeyID() {
      AuthorityKeyID akID = null;
      if (this.issuerSubjectKeyID != null) {
         akID = new AuthorityKeyID();
         byte[] keyID = this.issuerSubjectKeyID.getKeyID();
         akID.setKeyID(keyID, 0, keyID.length);
      }

      return akID;
   }

   private static boolean checkCAKeyUsage(X509Certificate issuerCertificate) throws CertificateException {
      X509V3Extensions extensions = issuerCertificate.getExtensions();
      if (extensions == null) {
         return true;
      } else {
         KeyUsage keyUsageExtension = (KeyUsage)extensions.getExtensionByType(15);
         if (keyUsageExtension == null) {
            return true;
         } else {
            int keyUsageInt = keyUsageExtension.getKeyUsage();
            if (keyUsageInt != 0 && (keyUsageInt & 67108864) != 0 && (keyUsageInt & 16777216) == 0 && (keyUsageInt & 8388608) == 0) {
               return true;
            } else {
               String ss = getKeyUsageSetting(keyUsageInt);
               throw new KeyUsageException("The CA with subject name of \"" + issuerCertificate.getSubjectName() + "\" has invalid keyusage setting of [" + ss + "]");
            }
         }
      }
   }

   private static boolean checkForConflictingKeyUsage(KeyUsage keyUsageExtension) throws CertificateException {
      if (keyUsageExtension == null) {
         return true;
      } else {
         int keyUsageInt = keyUsageExtension.getKeyUsage();
         if (keyUsageInt != 0) {
            if ((keyUsageInt & 16777216) != 0) {
               if ((keyUsageInt & 8388608) != 0) {
                  throw new KeyUsageException("Conflicting keyusage setting between 'encipherOnly' and 'decipherOnly'");
               }

               if ((keyUsageInt & 67108864) != 0) {
                  throw new KeyUsageException("Conflicting keyusage setting between 'encipherOnly' and 'keyCertSign'");
               }
            }

            if ((keyUsageInt & 8388608) != 0) {
               if ((keyUsageInt & 16777216) != 0) {
                  throw new KeyUsageException("Conflicting keyusage setting between 'encipherOnly' and 'decipherOnly'");
               }

               if ((keyUsageInt & 67108864) != 0) {
                  throw new KeyUsageException("Conflicting keyusage setting between 'decipherOnly' and 'keyCertSign'");
               }
            }
         }

         return false;
      }
   }

   private static String getKeyUsageSetting(int keyUsageInt) {
      StringBuffer keyusage = new StringBuffer();
      String comm = ", ";
      if ((keyUsageInt & Integer.MIN_VALUE) != 0) {
         keyusage.append(KEY_USAGE_NAMES[0]).append(comm);
      }

      if ((keyUsageInt & 1073741824) != 0) {
         keyusage.append(KEY_USAGE_NAMES[1]).append(comm);
      }

      if ((keyUsageInt & 536870912) != 0) {
         keyusage.append(KEY_USAGE_NAMES[2]).append(comm);
      }

      if ((keyUsageInt & 268435456) != 0) {
         keyusage.append(KEY_USAGE_NAMES[3]).append(comm);
      }

      if ((keyUsageInt & 134217728) != 0) {
         keyusage.append(KEY_USAGE_NAMES[4]).append(comm);
      }

      if ((keyUsageInt & 67108864) != 0) {
         keyusage.append(KEY_USAGE_NAMES[5]).append(comm);
      }

      if ((keyUsageInt & 33554432) != 0) {
         keyusage.append(KEY_USAGE_NAMES[6]).append(comm);
      }

      if ((keyUsageInt & 16777216) != 0) {
         keyusage.append(KEY_USAGE_NAMES[7]).append(comm);
      }

      if ((keyUsageInt & 8388608) != 0) {
         keyusage.append(KEY_USAGE_NAMES[8]).append(comm);
      }

      String ss = keyusage.toString();
      int last = ss.lastIndexOf(comm);
      if (last != -1) {
         ss = ss.substring(0, last);
      }

      return ss;
   }

   public static byte[] getPrivateKeyData(JSAFE_PrivateKey privateKey, char[] pwd) throws Exception {
      byte[] salt = new byte[]{0, 17, 34, 51, 68, 85, 102, 119};
      JSAFE_SymmetricCipher encrypter = JSafeEncryptionServiceImpl.getSymmetricCipher("PBE/MD5/DES/CBC/PKCS5PBE-5-56", "Java");
      encrypter.setSalt(salt, 0, salt.length);
      JSAFE_SecretKey key = encrypter.getBlankKey();
      key.setPassword(pwd, 0, pwd.length);
      encrypter.encryptInit(key, (SecureRandom)null);
      return encrypter.wrapPrivateKey(privateKey, true);
   }

   public static void writePKCS8PrivateKey(JSAFE_PrivateKey privateKey, String password, String fileName) throws Exception {
      byte[] privateKeyData = getPrivateKeyData(privateKey, password.toCharArray());
      String derFileName = fileName + ".der";
      FileOutputStream outputStream = new FileOutputStream(derFileName);
      outputStream.write(privateKeyData);
      outputStream.close();
      String pemFileName = fileName + ".pem";
      InputStream derStream = new ByteArrayInputStream(privateKeyData);
      OutputStream pemStream = new FileOutputStream(pemFileName);
      der2pem.convertEncryptedKey(derStream, pemStream);
      derStream.close();
      pemStream.close();
   }

   private static void writeX509Certificate(X509Certificate certificate, String fileName) throws Exception {
      byte[] certificateData = new byte[certificate.getDERLen(0)];
      certificate.getDEREncoding(certificateData, 0, 0);
      String derFileName = fileName + ".der";
      FileOutputStream outputStream = new FileOutputStream(derFileName);
      outputStream.write(certificateData);
      outputStream.close();
      String pemFileName = fileName + ".pem";
      InputStream derStream = new ByteArrayInputStream(certificateData);
      OutputStream pemStream = new FileOutputStream(pemFileName);
      der2pem.convertCertificate(derStream, pemStream);
      derStream.close();
      pemStream.close();
   }

   private static JSAFE_PrivateKey loadPKCS8PrivateKey(String fileName, String password) throws Exception {
      byte[] keyData = readFile(fileName);
      JSAFE_SymmetricCipher decrypter = JSafeEncryptionServiceImpl.getNonFIPS140Ctx() == null ? JSAFE_SymmetricCipher.getInstance(keyData, 0, "Java") : JSAFE_SymmetricCipher.getInstance(keyData, 0, "Java", JSafeEncryptionServiceImpl.getNonFIPS140Ctx());
      JSAFE_SecretKey key = decrypter.getBlankKey();
      key.setPassword(password.toCharArray(), 0, password.length());
      decrypter.decryptInit(key, (SecureRandom)null);
      return decrypter.unwrapPrivateKey(keyData, 0, keyData.length, true);
   }

   private static X509Certificate loadX509Certificate(String fileName) throws Exception {
      byte[] certificateData = readFile(fileName);
      return new X509Certificate(certificateData, 0, 0);
   }

   private static byte[] readFile(String fileName) throws IOException {
      FileInputStream in = new FileInputStream(fileName);
      ByteArrayOutputStream bs = new ByteArrayOutputStream(in.available());

      int b;
      while((b = in.read()) != -1) {
         bs.write(b);
      }

      in.close();
      return bs.toByteArray();
   }

   private static X500Name createX500Name(Properties nameAttr) throws NameException {
      if (nameAttr == null) {
         nameAttr = new Properties();
      }

      AttributeValueAssertion[] attributes = new AttributeValueAssertion[]{new AttributeValueAssertion(1, AttributeValueAssertion.COUNTRY_NAME_OID, 4864, nameAttr.getProperty("x500name.country", "US")), new AttributeValueAssertion(3, AttributeValueAssertion.STATE_NAME_OID, 3072, nameAttr.getProperty("x500name.state", "MyState")), new AttributeValueAssertion(2, AttributeValueAssertion.LOCALITY_NAME_OID, 3072, nameAttr.getProperty("x500name.town", "MyTown")), new AttributeValueAssertion(4, AttributeValueAssertion.ORGANIZATION_NAME_OID, 3072, nameAttr.getProperty("x500name.organization", "MyOrganization")), new AttributeValueAssertion(5, AttributeValueAssertion.ORGANIZATIONAL_UNIT_NAME_OID, 3072, nameAttr.getProperty("x500name.orgunit", "FOR TESTING ONLY")), new AttributeValueAssertion(0, AttributeValueAssertion.COMMON_NAME_OID, 3072, nameAttr.getProperty("x500name.commonname", "localhost"))};
      X500Name name = new X500Name();
      AttributeValueAssertion[] var3 = attributes;
      int var4 = attributes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         AttributeValueAssertion attribute = var3[var5];
         RDN rdn = new RDN();
         rdn.addNameAVA(attribute);
         name.addRDN(rdn);
      }

      String emailAddress = nameAttr.getProperty("x500name.email");
      if (emailAddress != null) {
         RDN rdn = new RDN();
         rdn.addNameAVA(new AttributeValueAssertion(7, AttributeValueAssertion.EMAIL_ADDRESS_OID, 5632, emailAddress));
         name.addRDN(rdn);
      }

      return name;
   }

   private static String findFile(String fileName) throws FileNotFoundException {
      String foundName = fileName;
      if (!(new File(fileName)).exists()) {
         File f = new File(new File(WeblogicHome.getWebLogicHome(), "lib"), fileName);
         if (!f.exists()) {
            throw new FileNotFoundException("Cannot find file: " + fileName);
         }

         foundName = f.getAbsolutePath();
      }

      return foundName;
   }

   public static Certificate convert(X509Certificate cert) throws Exception {
      if (cert == null) {
         return null;
      } else {
         byte[] certData = new byte[cert.getDERLen(0)];
         cert.getDEREncoding(certData, 0, 0);
         InputStream in = new ByteArrayInputStream(certData);
         CertificateFactory factory = CertificateFactory.getInstance("X.509");
         return factory.generateCertificate(in);
      }
   }

   public static PrivateKey convert(JSAFE_PrivateKey key) throws Exception {
      if (key == null) {
         return null;
      } else {
         String pwd = " ";
         byte[] keyData = getPrivateKeyData(key, pwd.toCharArray());
         return getPKCS8EncodedPrivateKey(keyData, pwd.toCharArray());
      }
   }

   private static PrivateKey getPKCS8EncodedPrivateKey(byte[] keyData, char[] pwd) throws Exception {
      return RSAPKFactory.getPrivateKey(new ByteArrayInputStream(keyData), pwd);
   }

   public static int getKeyUsageBitForName(String keyUsageName) throws IllegalArgumentException {
      for(int i = 0; i < KEY_USAGE_NAMES.length; ++i) {
         if (keyUsageName.equalsIgnoreCase(KEY_USAGE_NAMES[i])) {
            return KEY_USAGE_BITS[i];
         }
      }

      throw new IllegalArgumentException(keyUsageName);
   }

   private static String[] parseList(String csvStr, String delim) {
      StringTokenizer st = new StringTokenizer(csvStr, delim);
      String[] tokens = new String[st.countTokens()];

      for(int i = 0; st.hasMoreTokens(); ++i) {
         tokens[i] = st.nextToken().trim();
      }

      return tokens;
   }

   private static void printError(String message) {
      printUsage(message, true);
   }

   private static void printUsage(String message) {
      printUsage(message, false);
   }

   private static void printUsage(String message, boolean brief) {
      if (message != null) {
         System.out.println();
         System.out.println(message);
      }

      System.out.println("\nUsage: java utils.CertGen\n\t-certfile <cert_file> -keyfile <private_key_file>\n\t-keyfilepass <private_key_password>\n\t[-cacert <ca_cert_file>][-cakey <ca_key_file>]\n\t[-cakeypass <ca_key_password>]\n\t[-selfsigned][-strength <key_strength>]\n\t[-digestalgorithm <message digest algorithm such as MD5, SHA1, or SHA256>]\n\t[-e <email_address>][-cn <common_name>]\n\t[-ou <org_unit>][-o <organization>]\n\t[-l <locality>][-s <state>][-c <country_code>]\n\t[-keyusage [digitalSignature,nonRepudiation,keyEncipherment,\n\t\tdataEncipherment,keyAgreement,keyCertSign,\n\t\tcRLSign,encipherOnly,decipherOnly]]\n\t[-keyusagecritical true|false]\n\t[-noskid]\n\t[-subjectkeyid <subject_key_identifier>]\n\t[-subjectkeyidformat UTF-8|BASE64]\n\t[-nosanhostdns]\n\t[-a DNS:<hostname>,DNS:<localhost>,IP:<ip address>,IP:<127.0.0.1>\n\t[-help]\n");
      if (!brief) {
         System.out.println("Where:\n-cacert, -cakey, -cakeypass\n\tpublic certificate, private key file names, and private key\n\tpassword of the CA to be used as an issuer of the generated\n\tcertificate. When these options are not specified Demo CA files:\n\tCertGenCA.der, CertGenCAKey.der from the current working directory\n\tor from WebLogic lib directory will be used.\n\n-selfsigned\n\tgenerate a self-signed certificate. CA options will be ignored\n\twhen this option is specified\n\n-digestalgorithm\n\tThe message digest algorithm used with the signature algorithm to sign the certificate.\n\n-certfile, -keyfile\n\toutput file names without extensions for the generated public\n\tcertificate and private key. Appropriate extensions are appended\n\twhen the pem and der files are created.\n\n-keyfilepass\n\tpassword of the generated private key.\n\n-strength\n\tsize of the generated keys. The default is 2048 bits.\n\n-e, -cn, -ou, -o, -l, -s, -c\n\tSubject DN attributes of the generated public certificate.\n\n-keyusage\n\tgenerate certificate with keyusage extension, and with bits set\n\taccording to the comma-separated list of bit names.\n\tExtension will be marked as critical by default.\n\tUse [-keyusagecritical false] to generate certificate with\n\tnon-critical extension.\n\n-noskid\n\tdo not include subject key identifier extension in the certificate.\n\t-subjectkeyid and -subjectkeyidformat will be ignored if -noskid is present.\n\n-subjectkeyid\n\tgenerate certificate with the specified subject key identifier\n\tUse [-subjectkeyidformat UTF-8|BASE64] to indicate the format of\n\tthe specified subject key identifier string. The default is UTF-8.\n\n-nosanhostdns\n\tdo not add the hostname DNS to the SAN\n\n-a\n\tadd SANs of type DNSName, IPAddress.\n");
      }
   }

   public static void main(String[] args) {
      boolean generated = false;

      try {
         generated = generateCertificate(args);
      } catch (Throwable var3) {
         var3.printStackTrace();
         printError("Unexpected exception while generating certificate");
      }

      System.exit(generated ? 0 : 1);
   }

   public static boolean generateCertificate(String[] args) throws Exception {
      if (args.length == 0) {
         printUsage((String)null);
         return false;
      } else {
         String caCert = null;
         String caKey = null;
         String caKeyPwd = null;
         boolean ca = false;
         String digestAlgorithm = null;
         String certFileName = null;
         String keyFileName = null;
         String keyFilePwd = null;
         int keyStrength = 2048;
         String[] keyUsage = null;
         boolean keyUsageCritical = true;
         String subjectKeyIdStr = null;
         String subjectKeyIdFormat = "UTF-8";
         Properties nameAttr = new Properties();
         boolean noSkid = false;
         String sanStr = null;
         boolean noSanHostDns = false;
         int i = 0;

         try {
            for(; i < args.length; ++i) {
               if (args[i].equals("-help")) {
                  printUsage((String)null);
                  return false;
               }

               if (args[i].equals("-cacert")) {
                  ++i;
                  caCert = args[i];
               } else if (args[i].equals("-cakey")) {
                  ++i;
                  caKey = args[i];
               } else if (args[i].equals("-cakeypass")) {
                  ++i;
                  caKeyPwd = args[i];
               } else if (args[i].equals("-selfsigned")) {
                  ca = true;
               } else if (args[i].equals("-digestalgorithm")) {
                  ++i;
                  digestAlgorithm = args[i].toUpperCase();
               } else if (args[i].equals("-certfile")) {
                  ++i;
                  certFileName = args[i];
               } else if (args[i].equals("-keyfile")) {
                  ++i;
                  keyFileName = args[i];
               } else if (args[i].equals("-keyfilepass")) {
                  ++i;
                  keyFilePwd = args[i];
               } else if (args[i].equals("-strength")) {
                  ++i;
                  keyStrength = Integer.parseInt(args[i]);
               } else if (args[i].equals("-e")) {
                  ++i;
                  nameAttr.setProperty("x500name.email", args[i]);
               } else if (args[i].equals("-cn")) {
                  ++i;
                  nameAttr.setProperty("x500name.commonname", args[i]);
               } else if (args[i].equals("-ou")) {
                  ++i;
                  nameAttr.setProperty("x500name.orgunit", args[i]);
               } else if (args[i].equals("-o")) {
                  ++i;
                  nameAttr.setProperty("x500name.organization", args[i]);
               } else if (args[i].equals("-l")) {
                  ++i;
                  nameAttr.setProperty("x500name.town", args[i]);
               } else if (args[i].equals("-s")) {
                  ++i;
                  nameAttr.setProperty("x500name.state", args[i]);
               } else if (args[i].equals("-c")) {
                  ++i;
                  nameAttr.setProperty("x500name.country", args[i]);
               } else if (args[i].equals("-keyusage")) {
                  String[] var10000;
                  if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                     ++i;
                     var10000 = parseList(args[i], ",");
                  } else {
                     var10000 = new String[0];
                  }

                  keyUsage = var10000;
               } else if (args[i].equals("-keyusagecritical")) {
                  ++i;
                  keyUsageCritical = Boolean.parseBoolean(args[i]);
               } else if (args[i].equals("-noskid")) {
                  noSkid = true;
               } else if (args[i].equals("-subjectkeyid")) {
                  ++i;
                  subjectKeyIdStr = args[i];
               } else if (args[i].equals("-subjectkeyidformat")) {
                  ++i;
                  subjectKeyIdFormat = args[i];
               } else if (args[i].equals("-nosanhostdns")) {
                  noSanHostDns = true;
               } else {
                  if (!args[i].equals("-a")) {
                     if (i == 0 && args.length >= 3) {
                        return oldCertGen(args);
                     }

                     printError("Unknown option: " + args[i]);
                     return false;
                  }

                  ++i;
                  sanStr = args[i];
               }
            }
         } catch (ArrayIndexOutOfBoundsException var35) {
            printError("Please specify a value for " + args[args.length - 1]);
            return false;
         } catch (NumberFormatException var36) {
            printError("Please specify a valid integer value for " + args[i - 1]);
            return false;
         }

         if (keyFileName == null) {
            printError("Please specify an output file name for the generated private key");
            return false;
         } else if (keyFilePwd == null) {
            printError("Please specify a password for the generated private key");
            return false;
         } else if (certFileName == null) {
            printError("Please specify an output file name for the generated certificate");
            return false;
         } else {
            int keyUsageBits = 0;
            if (keyUsage != null) {
               String[] var20 = keyUsage;
               int var21 = keyUsage.length;

               for(int var22 = 0; var22 < var21; ++var22) {
                  String aKeyUsage = var20[var22];

                  try {
                     keyUsageBits |= getKeyUsageBitForName((String)aKeyUsage);
                  } catch (IllegalArgumentException var28) {
                     printError("Unexpected key usage name: " + aKeyUsage);
                     return false;
                  }
               }
            }

            byte[] subjectKeyId = null;
            if (!noSkid && subjectKeyIdStr != null) {
               if (subjectKeyIdFormat.equalsIgnoreCase("UTF-8")) {
                  subjectKeyId = subjectKeyIdStr.getBytes("UTF-8");
               } else {
                  if (!subjectKeyIdFormat.equalsIgnoreCase("BASE64")) {
                     printError("The subjectkeyidformat must be 'UTF-8' or 'BASE64'.");
                     return false;
                  }

                  try {
                     subjectKeyId = (new BASE64Decoder()).decodeBuffer(subjectKeyIdStr);
                  } catch (IOException var27) {
                     System.out.println("Could not base64 decode the subject key identifier.");
                     System.out.println("Either specify a base64 encoded subject key identifier or ");
                     System.out.println("specify '-subjectkeyidentifierformat UTF-8' if the subject key identifier is an ASCII string");
                     System.out.println(var27);
                     return false;
                  }
               }
            }

            CertGen generator = new CertGen(keyStrength);
            if (keyUsage != null) {
               generator.setKeyUsage(keyUsageBits, keyUsageCritical);
            }

            if (subjectKeyId != null) {
               generator.setSubjectKeyIdentifier(subjectKeyId);
            }

            if (digestAlgorithm != null) {
               generator.setDigestAlgorithm(digestAlgorithm);
            }

            if (noSkid) {
               generator.setNoSkid(noSkid);
            }

            if (noSanHostDns) {
               generator.setNoSanHostDns(noSanHostDns);
            }

            if (sanStr != null) {
               generator.setSanStr(sanStr);
            }

            if (!nameAttr.containsKey("x500name.commonname")) {
               nameAttr.setProperty("x500name.commonname", InetAddress.getLocalHost().getHostName());
            }

            try {
               if (ca) {
                  System.out.println("Generating a self signed certificate with common name " + nameAttr.getProperty("x500name.commonname") + " and key strength " + keyStrength);
                  generator.generateCACertificate(nameAttr);
               } else {
                  if (caCert == null) {
                     caCert = findFile("CertGenCA.der");
                  }

                  if (caKey == null) {
                     caKey = findFile("CertGenCAKey.der");
                     if (caKeyPwd == null) {
                        caKeyPwd = "password";
                     }
                  } else if (caKeyPwd == null) {
                     System.out.println("Please specify password for the key from " + caKey + " file");
                     return false;
                  }

                  System.out.println("Generating a certificate with common name " + nameAttr.getProperty("x500name.commonname") + " and key strength " + keyStrength + "\nissued by CA with certificate from " + caCert + " file and key from " + caKey + " file");
                  if (!noSkid && subjectKeyIdStr != null) {
                     System.out.println("with subject key identifier " + subjectKeyIdStr);
                  }

                  generator.generateCertificate(nameAttr, caCert, caKey, caKeyPwd);
               }
            } catch (NameException var29) {
               System.out.println("Failed to generate the certificate:\n" + var29.getMessage());
               if (nameAttr.getProperty("x500name.country", "US").length() != 2) {
                  System.out.println("The country code must consist of two printable characters.");
               } else {
                  System.out.println("Make sure the values of the subject name fields are valid.");
               }

               return false;
            } catch (KeyUsageException var30) {
               System.out.println("Failed to generate the certificate:\n" + var30.getMessage());
               return false;
            } catch (CertificateException var31) {
               System.out.println("Failed to generate the certificate:\n" + var31.getMessage());
               System.out.println("Make sure the country code contains only printable characters, and values of the other subject name fields are valid.");
               return false;
            } catch (JSAFE_InvalidUseException var32) {
               System.out.println("Failed to generate the certificate:\n" + var32.getMessage());
               if (keyStrength != 2048) {
                  System.out.println("Make sure the key strength value is valid.");
               }

               if (!ca) {
                  System.out.println("Make sure the CA key password is valid.");
               }

               return false;
            } catch (IOException var33) {
               if (ca) {
                  System.out.println("Failed to generate the certificate:");
               } else {
                  System.out.println("Failed to read one of the CA files: " + caCert + ", or " + caKey);
               }

               System.out.println(var33.getMessage());
               return false;
            } catch (Exception var34) {
               System.out.println("Failed to generate the certificate:\n" + var34.getMessage());
               if (!ca) {
                  System.out.println("Make sure the CA files are in DER format.");
               }

               return false;
            }

            try {
               writePKCS8PrivateKey(generator.subjectPrivateKey, keyFilePwd, keyFileName);
            } catch (Exception var26) {
               System.out.println("Failed to write generated private key to " + keyFileName);
               System.out.println(var26.getMessage());
               return false;
            }

            try {
               writeX509Certificate(generator.subjectCert, certFileName);
               return true;
            } catch (Exception var25) {
               System.out.println("Failed to write generated certificate to " + certFileName);
               System.out.println(var25.getMessage());
               return false;
            }
         }
      }
   }

   /** @deprecated */
   @Deprecated
   private static boolean oldCertGen(String[] args) throws Exception {
      String password = args[0];
      String certFileName = args[1];
      String keyFileName = args[2];
      boolean export = args.length > 3 && args[3].equalsIgnoreCase("EXPORT");
      String hostName = args.length > 4 ? args[4] : InetAddress.getLocalHost().getHostName();
      boolean generateNewCA = args.length > 5 && args[5].equalsIgnoreCase("GENCA");
      System.out.println("......  Will generate " + (generateNewCA ? "new CA certificate (self signed)" : "certificate signed by CA from CertGenCA.der file"));
      System.out.println("......  With " + (export ? "Export" : "Domestic") + " Key Strength");
      System.out.println("......  Common Name will have Hostname " + hostName);
      Properties nameAttr = new Properties();
      nameAttr.setProperty("x500name.commonname", hostName);
      CertGen generator = new CertGen(export);

      try {
         if (generateNewCA) {
            generator.generateCACertificate(nameAttr);
         } else {
            generator.generateCertificate(nameAttr);
         }
      } catch (Exception var11) {
         System.out.println("Failed to generate the certificate\n" + var11.getMessage());
         return false;
      }

      System.out.println("......  Issuer CA name is " + generator.issuerName);

      try {
         writePKCS8PrivateKey(generator.subjectPrivateKey, password, keyFileName);
         writeX509Certificate(generator.subjectCert, certFileName);
         return true;
      } catch (Exception var10) {
         System.out.println("Failed to write to file\n" + var10.getMessage());
         return false;
      }
   }

   private static class KeyUsageException extends CertificateException {
      public KeyUsageException(String s) {
         super(s);
      }
   }
}

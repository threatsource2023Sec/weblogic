package net.shibboleth.utilities.java.support.security;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.BaseConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.Positive;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelfSignedCertificateGenerator {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(SelfSignedCertificateGenerator.class);
   @Nonnull
   private final CommandLineArgs args = new CommandLineArgs();

   public void setKeyType(@Nonnull @NotEmpty String type) {
      this.args.keyType = (String)Constraint.isNotNull(StringSupport.trimOrNull(type), "Key type cannot be null or empty");
   }

   public void setKeySize(@Positive int size) {
      Constraint.isGreaterThan(0L, (long)size, "Key size must be greater than 0");
      this.args.keySize = size;
   }

   public void setCertificateLifetime(@Positive int lifetime) {
      Constraint.isGreaterThan(0L, (long)lifetime, "Certificate lifetime must be greater than 0");
      this.args.certificateLifetime = lifetime;
   }

   public void setCertificateAlg(@Nonnull @NotEmpty String alg) {
      this.args.certAlg = (String)Constraint.isNotNull(StringSupport.trimOrNull(alg), "Algorithm cannot be null or empty");
   }

   public void setHostName(@Nonnull @NotEmpty String name) {
      this.args.hostname = (String)Constraint.isNotNull(StringSupport.trimOrNull(name), "Hostname cannot be null or empty");
   }

   public void setPrivateKeyFile(@Nullable File file) {
      this.args.privateKeyFile = file;
   }

   public void setCertificateFile(@Nullable File file) {
      this.args.certificateFile = file;
   }

   public void setKeystoreType(@Nonnull @NotEmpty String type) {
      this.args.keystoreType = (String)Constraint.isNotNull(StringSupport.trimOrNull(type), "Keystore type cannot be null or empty");
   }

   public void setKeystoreFile(@Nullable File file) {
      this.args.keystoreFile = file;
   }

   public void setKeystorePassword(@Nullable String password) {
      this.args.keystorePassword = password;
   }

   public void setDNSSubjectAltNames(@Nonnull @NonnullElements Collection altNames) {
      this.args.dnsSubjectAltNames = new ArrayList(StringSupport.normalizeStringCollection(altNames));
   }

   public void setURISubjectAltNames(@Nonnull @NonnullElements Collection altNames) {
      this.args.uriSubjectAltNames = new ArrayList(StringSupport.normalizeStringCollection(altNames));
   }

   public void generate() throws Exception {
      this.validate();
      if (this.args.privateKeyFile != null && !this.args.privateKeyFile.createNewFile()) {
         throw new IOException("Private key file exists: " + this.args.privateKeyFile.getAbsolutePath());
      } else if (this.args.certificateFile != null && !this.args.certificateFile.createNewFile()) {
         throw new IOException("Certificate file exists: " + this.args.certificateFile.getAbsolutePath());
      } else if (this.args.keystoreFile != null && !this.args.keystoreFile.createNewFile()) {
         throw new IOException("KeyStore file exists: " + this.args.keystoreFile.getAbsolutePath());
      } else {
         KeyPair keypair = this.generateKeyPair();
         X509Certificate certificate = this.generateCertificate(keypair);
         JcaPEMWriter certOut;
         Throwable var4;
         if (this.args.privateKeyFile != null) {
            certOut = new JcaPEMWriter(new FileWriter(this.args.privateKeyFile));
            var4 = null;

            try {
               certOut.writeObject(keypair.getPrivate());
               certOut.flush();
            } catch (Throwable var50) {
               var4 = var50;
               throw var50;
            } finally {
               if (certOut != null) {
                  if (var4 != null) {
                     try {
                        certOut.close();
                     } catch (Throwable var45) {
                        var4.addSuppressed(var45);
                     }
                  } else {
                     certOut.close();
                  }
               }

            }
         }

         if (this.args.certificateFile != null) {
            certOut = new JcaPEMWriter(new FileWriter(this.args.certificateFile));
            var4 = null;

            try {
               certOut.writeObject(certificate);
               certOut.flush();
            } catch (Throwable var49) {
               var4 = var49;
               throw var49;
            } finally {
               if (certOut != null) {
                  if (var4 != null) {
                     try {
                        certOut.close();
                     } catch (Throwable var47) {
                        var4.addSuppressed(var47);
                     }
                  } else {
                     certOut.close();
                  }
               }

            }
         }

         if (this.args.keystoreFile != null) {
            KeyStore store = KeyStore.getInstance(this.args.keystoreType);
            store.load((InputStream)null, (char[])null);
            store.setKeyEntry(this.args.hostname, keypair.getPrivate(), this.args.keystorePassword.toCharArray(), new X509Certificate[]{certificate});
            FileOutputStream keystoreOut = new FileOutputStream(this.args.keystoreFile);
            Throwable var5 = null;

            try {
               store.store(keystoreOut, this.args.keystorePassword.toCharArray());
               keystoreOut.flush();
            } catch (Throwable var48) {
               var5 = var48;
               throw var48;
            } finally {
               if (keystoreOut != null) {
                  if (var5 != null) {
                     try {
                        keystoreOut.close();
                     } catch (Throwable var46) {
                        var5.addSuppressed(var46);
                     }
                  } else {
                     keystoreOut.close();
                  }
               }

            }
         }

      }
   }

   protected void validate() {
      if (this.args.keySize > 2048) {
         this.log.warn("Key size is greater than 2048, this may cause problems with some JVMs");
      }

      if (this.args.hostname != null && this.args.hostname.length() != 0) {
         if (this.args.keystoreFile != null && (this.args.keystorePassword == null || this.args.keystorePassword.length() == 0)) {
            throw new IllegalArgumentException("Keystore password cannot be null if a keystore file is given");
         }
      } else {
         throw new IllegalArgumentException("A non-empty hostname is required");
      }
   }

   @Nonnull
   protected KeyPair generateKeyPair() throws NoSuchAlgorithmException {
      try {
         KeyPairGenerator generator = KeyPairGenerator.getInstance(this.args.keyType);
         generator.initialize(this.args.keySize);
         return generator.generateKeyPair();
      } catch (NoSuchAlgorithmException var2) {
         this.log.error("The {} key type is not supported by this JVM", this.args.keyType);
         throw var2;
      }
   }

   @Nonnull
   protected X509Certificate generateCertificate(@Nonnull KeyPair keypair) throws Exception {
      X500Name dn = new X500Name("CN=" + this.args.hostname);
      GregorianCalendar notBefore = new GregorianCalendar();
      GregorianCalendar notOnOrAfter = new GregorianCalendar();
      notOnOrAfter.set(1, notOnOrAfter.get(1) + this.args.certificateLifetime);
      X509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(dn, new BigInteger(160, new SecureRandom()), notBefore.getTime(), notOnOrAfter.getTime(), dn, keypair.getPublic());
      JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();
      builder.addExtension(Extension.subjectKeyIdentifier, false, extUtils.createSubjectKeyIdentifier(keypair.getPublic()));
      builder.addExtension(Extension.subjectAlternativeName, false, GeneralNames.getInstance(new DERSequence(this.buildSubjectAltNames())));
      X509CertificateHolder certHldr = builder.build((new JcaContentSignerBuilder(this.args.certAlg)).build(keypair.getPrivate()));
      X509Certificate cert = (new JcaX509CertificateConverter()).getCertificate(certHldr);
      cert.checkValidity(new Date());
      cert.verify(keypair.getPublic());
      return cert;
   }

   @Nonnull
   @NonnullElements
   protected ASN1Encodable[] buildSubjectAltNames() {
      ArrayList subjectAltNames = new ArrayList();
      subjectAltNames.add(new GeneralName(2, this.args.hostname));
      Iterator i$;
      String subjectAltName;
      if (this.args.dnsSubjectAltNames != null) {
         i$ = this.args.dnsSubjectAltNames.iterator();

         while(i$.hasNext()) {
            subjectAltName = (String)i$.next();
            subjectAltNames.add(new GeneralName(2, subjectAltName));
         }
      }

      if (this.args.uriSubjectAltNames != null) {
         i$ = this.args.uriSubjectAltNames.iterator();

         while(i$.hasNext()) {
            subjectAltName = (String)i$.next();
            subjectAltNames.add(new GeneralName(6, subjectAltName));
         }
      }

      return (ASN1Encodable[])subjectAltNames.toArray(new ASN1Encodable[0]);
   }

   public static void main(@Nonnull String[] args) throws Exception {
      SelfSignedCertificateGenerator generator = new SelfSignedCertificateGenerator();
      JCommander jc = new JCommander(generator.args, args);
      if (generator.args.help) {
         jc.setProgramName("SelfSignedCertificateGenerator");
         jc.usage();
      } else {
         generator.generate();
      }
   }

   private static class CommandLineArgs {
      @Nonnull
      @NotEmpty
      public static final String HELP = "--help";
      @Nonnull
      @NotEmpty
      public static final String KEY_TYPE = "--type";
      @Nonnull
      @NotEmpty
      public static final String KEY_SIZE = "--size";
      @Nonnull
      @NotEmpty
      public static final String CERT_LIFETIME = "--lifetime";
      @Nonnull
      @NotEmpty
      public static final String CERT_ALG = "--certAlg";
      @Nonnull
      @NotEmpty
      public static final String HOSTNAME = "--hostname";
      @Nonnull
      @NotEmpty
      public static final String DNS_ALTNAMES = "--dnsAltName";
      @Nonnull
      @NotEmpty
      public static final String URI_ALTNAMES = "--uriAltName";
      @Nonnull
      @NotEmpty
      public static final String KEY_FILE = "--keyfile";
      @Nonnull
      @NotEmpty
      public static final String CERT_FILE = "--certfile";
      @Nonnull
      @NotEmpty
      public static final String STORE_TYPE = "--storetype";
      @Nonnull
      @NotEmpty
      public static final String STORE_FILE = "--storefile";
      @Nonnull
      @NotEmpty
      public static final String STORE_PASS = "--storepass";
      @Parameter(
         names = {"--help"},
         description = "Display program usage",
         help = true
      )
      private boolean help;
      @Parameter(
         names = {"--type"},
         description = "Type of key to generate (default: RSA)"
      )
      @Nonnull
      @NotEmpty
      private String keyType;
      @Parameter(
         names = {"--size"},
         description = "Size of key to generate (default: 3072)"
      )
      @Positive
      private int keySize;
      @Parameter(
         names = {"--lifetime"},
         description = "Certificate lifetime in years (default: 20)"
      )
      @Positive
      private int certificateLifetime;
      @Parameter(
         names = {"--certAlg"},
         description = "Certificate algorithm (default: SHA256withRSA)"
      )
      @Nonnull
      @NotEmpty
      private String certAlg;
      @Parameter(
         names = {"--hostname"},
         required = true,
         description = "Hostname for certificate subject"
      )
      @Nonnull
      @NotEmpty
      private String hostname;
      @Parameter(
         names = {"--dnsAltName"},
         description = "DNS subjectAltNames for certificate"
      )
      @Nullable
      private List dnsSubjectAltNames;
      @Parameter(
         names = {"--uriAltName"},
         description = "URI subjectAltNames for certificate"
      )
      @Nullable
      private List uriSubjectAltNames;
      @Parameter(
         names = {"--keyfile"},
         converter = FileConverter.class,
         description = "Path to private key file"
      )
      @Nullable
      private File privateKeyFile;
      @Parameter(
         names = {"--certfile"},
         converter = FileConverter.class,
         description = "Path to certificate file"
      )
      @Nullable
      private File certificateFile;
      @Parameter(
         names = {"--storetype"},
         description = "Type of keystore to generate (default: PKCS12)"
      )
      @Nonnull
      @NotEmpty
      private String keystoreType;
      @Parameter(
         names = {"--storefile"},
         converter = FileConverter.class,
         description = "Path to keystore"
      )
      @Nullable
      private File keystoreFile;
      @Parameter(
         names = {"--storepass"},
         description = "Password for keystore"
      )
      @Nullable
      private String keystorePassword;

      private CommandLineArgs() {
         this.keyType = "RSA";
         this.keySize = 3072;
         this.certificateLifetime = 20;
         this.certAlg = "SHA256withRSA";
         this.keystoreType = "PKCS12";
      }

      // $FF: synthetic method
      CommandLineArgs(Object x0) {
         this();
      }
   }

   public static class FileConverter extends BaseConverter {
      public FileConverter(String optionName) {
         super(optionName);
      }

      public File convert(String value) {
         return new File(value);
      }
   }
}

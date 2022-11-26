package net.shibboleth.utilities.java.support.security;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.BaseConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Properties;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.Positive;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicKeystoreKeyStrategyTool {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(BasicKeystoreKeyStrategyTool.class);
   @Nonnull
   private final CommandLineArgs args = new CommandLineArgs();

   public void setKeyType(@Nonnull @NotEmpty String type) {
      this.args.keyType = (String)Constraint.isNotNull(StringSupport.trimOrNull(type), "Key type cannot be null or empty");
   }

   public void setKeySize(@Positive int size) {
      Constraint.isGreaterThan(0L, (long)size, "Key size must be greater than 0");
      this.args.keySize = size;
   }

   public void setKeyAlias(@Nonnull @NotEmpty String alias) {
      this.args.keyAlias = (String)Constraint.isNotNull(StringSupport.trimOrNull(alias), "Key alias base cannot be null or empty");
   }

   public void setKeyCount(@Positive int count) {
      Constraint.isGreaterThan(0L, (long)count, "Key count must be greater than 0");
      this.args.keyCount = count;
   }

   public void setKeystoreType(@Nonnull @NotEmpty String type) {
      this.args.keystoreType = (String)Constraint.isNotNull(StringSupport.trimOrNull(type), "Keystore type cannot be null or empty");
   }

   public void setKeystoreFile(@Nonnull File file) {
      this.args.keystoreFile = (File)Constraint.isNotNull(file, "Keystore file cannot be null");
   }

   public void setKeystorePassword(@Nullable String password) {
      this.args.keystorePassword = password;
   }

   public void setVersionFile(@Nonnull File file) {
      this.args.versionFile = (File)Constraint.isNotNull(file, "Key versioning file cannot be null");
   }

   public void changeKey() throws Exception {
      KeyStore ks = KeyStore.getInstance(this.args.keystoreType);
      FileInputStream ksIn = this.args.keystoreFile.exists() ? new FileInputStream(this.args.keystoreFile) : null;
      Throwable var3 = null;

      try {
         ks.load(ksIn, this.args.keystorePassword.toCharArray());
      } catch (Throwable var80) {
         var3 = var80;
         throw var80;
      } finally {
         if (ksIn != null) {
            if (var3 != null) {
               try {
                  ksIn.close();
               } catch (Throwable var73) {
                  var3.addSuppressed(var73);
               }
            } else {
               ksIn.close();
            }
         }

      }

      Properties versionInfo = new Properties();
      if (this.args.versionFile.exists()) {
         FileInputStream versionIn = new FileInputStream(this.args.versionFile);
         Throwable var4 = null;

         try {
            versionInfo.load(versionIn);
         } catch (Throwable var79) {
            var4 = var79;
            throw var79;
         } finally {
            if (versionIn != null) {
               if (var4 != null) {
                  try {
                     versionIn.close();
                  } catch (Throwable var74) {
                     var4.addSuppressed(var74);
                  }
               } else {
                  versionIn.close();
               }
            }

         }
      }

      int currentVersion = Integer.parseInt(versionInfo.getProperty("CurrentVersion", "0"));
      if (currentVersion == 0) {
         this.log.info("No existing versioning property, initializing...");
      } else {
         this.log.info("Incrementing key version from {} to {}", currentVersion, currentVersion + 1);
      }

      ++currentVersion;
      String newKeyAlias = this.args.keyAlias + Integer.toString(currentVersion);
      if (ks.containsAlias(newKeyAlias)) {
         this.log.error("Keystore already contains an entry named {}, exiting", newKeyAlias);
         throw new KeyException("Entry for new key already exists");
      } else {
         KeyGenerator keyGenerator = KeyGenerator.getInstance(this.args.keyType);
         keyGenerator.init(this.args.keySize);
         SecretKey newKey = keyGenerator.generateKey();
         ks.setKeyEntry(newKeyAlias, newKey, this.args.keystorePassword.toCharArray(), (Certificate[])null);

         for(int oldVersion = currentVersion - this.args.keyCount; oldVersion > 0; --oldVersion) {
            String oldAlias = this.args.keyAlias + Integer.toString(oldVersion);
            if (!ks.containsAlias(oldAlias)) {
               break;
            }

            this.log.info("Deleting old key: {}", oldAlias);
            ks.deleteEntry(oldAlias);
         }

         FileOutputStream versionOut = new FileOutputStream(this.args.keystoreFile);
         Throwable var9 = null;

         try {
            ks.store(versionOut, this.args.keystorePassword.toCharArray());
         } catch (Throwable var78) {
            var9 = var78;
            throw var78;
         } finally {
            if (versionOut != null) {
               if (var9 != null) {
                  try {
                     versionOut.close();
                  } catch (Throwable var76) {
                     var9.addSuppressed(var76);
                  }
               } else {
                  versionOut.close();
               }
            }

         }

         versionOut = new FileOutputStream(this.args.versionFile);
         var9 = null;

         try {
            versionInfo.setProperty("CurrentVersion", Integer.toString(currentVersion));
            versionInfo.store(versionOut, (String)null);
         } catch (Throwable var77) {
            var9 = var77;
            throw var77;
         } finally {
            if (versionOut != null) {
               if (var9 != null) {
                  try {
                     versionOut.close();
                  } catch (Throwable var75) {
                     var9.addSuppressed(var75);
                  }
               } else {
                  versionOut.close();
               }
            }

         }

      }
   }

   public static void main(@Nonnull String[] args) throws Exception {
      BasicKeystoreKeyStrategyTool tool = new BasicKeystoreKeyStrategyTool();
      JCommander jc = new JCommander(tool.args, args);
      if (tool.args.help) {
         jc.setProgramName("BasicKeystoreKeyStrategyTool");
         jc.usage();
      } else {
         tool.changeKey();
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
      public static final String KEY_ALIAS = "--alias";
      @Nonnull
      @NotEmpty
      public static final String KEY_COUNT = "--count";
      @Nonnull
      @NotEmpty
      public static final String STORE_TYPE = "--storetype";
      @Nonnull
      @NotEmpty
      public static final String STORE_FILE = "--storefile";
      @Nonnull
      @NotEmpty
      public static final String STORE_PASS = "--storepass";
      @Nonnull
      @NotEmpty
      public static final String VERSION_FILE = "--versionfile";
      @Parameter(
         names = {"--help"},
         description = "Display program usage",
         help = true
      )
      private boolean help;
      @Parameter(
         names = {"--type"},
         description = "Type of key to generate (default: AES)"
      )
      @Nonnull
      @NotEmpty
      private String keyType;
      @Parameter(
         names = {"--size"},
         description = "Size of key to generate (default: 128)"
      )
      @Positive
      private int keySize;
      @Parameter(
         names = {"--alias"},
         required = true,
         description = "Base name of key alias"
      )
      @Nullable
      private String keyAlias;
      @Parameter(
         names = {"--count"},
         description = "Number of keys to maintain (default: 30)"
      )
      @Positive
      private int keyCount;
      @Parameter(
         names = {"--storetype"},
         description = "Type of keystore to generate (default: JCEKS)"
      )
      @Nonnull
      @NotEmpty
      private String keystoreType;
      @Parameter(
         names = {"--storefile"},
         required = true,
         converter = FileConverter.class,
         description = "Path to keystore"
      )
      @Nullable
      private File keystoreFile;
      @Parameter(
         names = {"--storepass"},
         required = true,
         description = "Password for keystore"
      )
      @Nullable
      private String keystorePassword;
      @Parameter(
         names = {"--versionfile"},
         required = true,
         converter = FileConverter.class,
         description = "Path to key versioning file"
      )
      @Nullable
      private File versionFile;

      private CommandLineArgs() {
         this.keyType = "AES";
         this.keySize = 128;
         this.keyCount = 30;
         this.keystoreType = "JCEKS";
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

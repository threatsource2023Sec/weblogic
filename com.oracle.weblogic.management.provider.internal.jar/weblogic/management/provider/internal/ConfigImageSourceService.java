package weblogic.management.provider.internal;

import com.bea.security.utils.random.SecureRandomData;
import java.lang.annotation.Annotation;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.image.ImageSource;
import weblogic.management.ManagementLogger;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class ConfigImageSourceService extends AbstractServerService {
   @Inject
   private RuntimeAccess runtimeAccess;
   public static String[] PROTECTED = new String[]{"password", "passphrase", "credential", "pass", "pwd"};
   static final String LINE_SEP = "line.separator";

   public void start() throws ServiceFailureException {
      try {
         this.logSystemProperties();
         ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
         ImageSource imageSource = new ConfigImageSource(this.runtimeAccess);
         imageManager.registerImageSource("configuration", imageSource);
      } catch (Throwable var3) {
         throw new ServiceFailureException(var3);
      }
   }

   private void logSystemProperties() {
      this.logEntropyConfiguration();

      try {
         StringBuffer buffer = new StringBuffer();
         String value = null;
         buffer.append(System.getProperty("line.separator"));
         Iterator it = null;

         while(it == null) {
            try {
               it = (new TreeSet(System.getProperties().keySet())).iterator();
            } catch (ConcurrentModificationException var5) {
            }
         }

         while(it.hasNext()) {
            String key = (String)it.next();
            if (!key.equals("line.separator")) {
               value = System.getProperty(key);
               if (value != null && value.length() != 0) {
                  buffer.append(key);
                  buffer.append(" = ");
                  buffer.append(this.hideIfProtected(key, value));
                  buffer.append(System.getProperty("line.separator"));
               }
            }
         }

         ManagementLogger.logJavaSystemProperties(buffer.toString());
      } catch (IllegalArgumentException var6) {
         ManagementLogger.logInvalidSystemProperty(var6);
      }

   }

   private void logEntropyConfiguration() {
      ManagementLogger.logJavaEntropyConfig(SecureRandomData.getJavaEntropyConfiguration());
      if (SecureRandomData.isJavaEntropyBlocking()) {
         ManagementLogger.logJavaEntropyConfigIsBlocking();
      } else {
         ManagementLogger.logJavaEntropyConfigIsNonBlocking();
      }

   }

   private String hideIfProtected(String key, String value) {
      for(int i = 0; i < PROTECTED.length; ++i) {
         if (key.toLowerCase(Locale.US).indexOf(PROTECTED[i]) >= 0) {
            return "********";
         }
      }

      return value;
   }
}

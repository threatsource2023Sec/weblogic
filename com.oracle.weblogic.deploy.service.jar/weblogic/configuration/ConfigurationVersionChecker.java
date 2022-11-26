package weblogic.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import weblogic.callout.spi.WebLogicCallout;
import weblogic.management.configuration.DomainMBean;

public class ConfigurationVersionChecker implements WebLogicCallout {
   File versionFile = null;

   public String callout(String hookPoint, String location, Map values) {
      if ("HOOK_POINT_CHANGE_RECEIVED".equals(hookPoint)) {
         String versionFromFile = this.getVersionFromFile();
         String pendingVersion = this.getVersionFromDomainMBean(values.get("pending"));
         if (versionFromFile != null && pendingVersion != null) {
            return versionFromFile.equals(pendingVersion) ? "accept" : "ignore";
         }
      }

      return "continue";
   }

   public void init(String argument) {
      if (argument != null) {
         this.versionFile = new File(argument);
      }

   }

   String getVersionFromFile() {
      if (this.versionFile != null && this.versionFile.exists()) {
         try {
            FileReader fileReader = new FileReader(this.versionFile);
            Throwable var2 = null;

            try {
               BufferedReader bufferedReader = new BufferedReader(fileReader);
               StringBuffer stringBuffer = new StringBuffer();

               String readLine;
               while((readLine = bufferedReader.readLine()) != null) {
                  stringBuffer.append(readLine);
               }

               String var6 = stringBuffer.toString();
               return var6;
            } catch (Throwable var17) {
               var2 = var17;
               throw var17;
            } finally {
               if (fileReader != null) {
                  if (var2 != null) {
                     try {
                        fileReader.close();
                     } catch (Throwable var16) {
                        var2.addSuppressed(var16);
                     }
                  } else {
                     fileReader.close();
                  }
               }

            }
         } catch (FileNotFoundException var19) {
         } catch (IOException var20) {
         }
      }

      return null;
   }

   String getVersionFromDomainMBean(Object domainMBean) {
      return domainMBean != null && domainMBean instanceof DomainMBean ? ((DomainMBean)domainMBean).getInstalledSoftwareVersion() : null;
   }
}

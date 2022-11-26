package weblogic.apache.org.apache.velocity.texen.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;
import weblogic.apache.org.apache.velocity.texen.Generator;

public class PropertiesUtil {
   public Properties load(String propertiesFile) {
      new Properties();
      String templatePath = Generator.getInstance().getTemplatePath();
      Properties properties;
      if (templatePath != null) {
         properties = this.loadFromTemplatePath(propertiesFile);
      } else {
         properties = this.loadFromClassPath(propertiesFile);
      }

      return properties;
   }

   protected Properties loadFromTemplatePath(String propertiesFile) {
      Properties properties = new Properties();
      String templatePath = Generator.getInstance().getTemplatePath();
      StringTokenizer st = new StringTokenizer(templatePath, ",");

      while(st.hasMoreTokens()) {
         String templateDir = st.nextToken();

         try {
            String fullPath = propertiesFile;
            if (!propertiesFile.startsWith(templateDir)) {
               fullPath = templateDir + "/" + propertiesFile;
            }

            properties.load(new FileInputStream(fullPath));
            break;
         } catch (Exception var7) {
         }
      }

      return properties;
   }

   protected Properties loadFromClassPath(String propertiesFile) {
      Properties properties = new Properties();
      ClassLoader classLoader = this.getClass().getClassLoader();

      try {
         if (propertiesFile.startsWith("$generator")) {
            propertiesFile = propertiesFile.substring("$generator.templatePath/".length());
         }

         InputStream inputStream = classLoader.getResourceAsStream(propertiesFile);
         properties.load(inputStream);
      } catch (IOException var5) {
      }

      return properties;
   }
}

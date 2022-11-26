package weblogic.spring.monitoring.instrumentation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class SpringVersionLoader {
   private static final String PROPERTIES_FILE = "/weblogic/spring/monitoring/instrumentation/SpringVersion.properties";
   private static final SpringVersionLoader INSTANCE = new SpringVersionLoader();
   private final Properties props = new Properties();

   private SpringVersionLoader() {
      try {
         InputStream inStream = SpringVersionLoader.class.getResourceAsStream("/weblogic/spring/monitoring/instrumentation/SpringVersion.properties");
         this.props.load(inStream);
         inStream.close();
      } catch (IOException var2) {
         throw new RuntimeException("Cannot load SpringVersion properties.", var2);
      }
   }

   public static SpringVersionLoader getInstance() {
      return INSTANCE;
   }

   public String getSpringInstrumentorEngineCreatorImplClassName(String springVersion) {
      return this.props.containsKey(springVersion) ? this.props.getProperty(springVersion) : null;
   }

   public String[] getSupportedSpringVersions() {
      List result = new ArrayList();
      Enumeration it = this.props.propertyNames();

      while(it.hasMoreElements()) {
         String version = (String)it.nextElement();
         result.add(version);
      }

      return (String[])result.toArray(new String[result.size()]);
   }
}

package weblogic.nodemanager.common;

import java.util.Properties;
import weblogic.nodemanager.NodeManagerTextTextFormatter;

public class Config {
   protected final Properties props;

   public Config(Properties props) {
      this.props = props;
   }

   public String getProperty(String name) {
      return this.trim(this.props.getProperty(name));
   }

   public String getProperty(String name, String defaultValue) {
      return this.trim(this.props.getProperty(name, defaultValue));
   }

   public boolean getBooleanProperty(String name) {
      return "true".equalsIgnoreCase(this.trim(this.props.getProperty(name)));
   }

   public boolean getBooleanProperty(String name, boolean defaultValue) {
      String s = this.trim(this.getProperty(name));
      return s != null ? "true".equalsIgnoreCase(s) : defaultValue;
   }

   public int getIntProperty(String name, int defaultValue) throws ConfigException {
      String s = this.trim(this.props.getProperty(name));

      try {
         return s != null ? Integer.parseInt(s) : defaultValue;
      } catch (NumberFormatException var5) {
         throw new ConfigException(NodeManagerTextTextFormatter.getInstance().getInvalidIntProperty(name));
      }
   }

   public long getLongProperty(String name, long defaultValue) throws ConfigException {
      String s = this.getProperty(name);
      if (s != null) {
         try {
            return Long.parseLong(s);
         } catch (IllegalArgumentException var6) {
            throw new ConfigException(NodeManagerTextTextFormatter.getInstance().getInvalidLongProperty(name));
         }
      } else {
         return defaultValue;
      }
   }

   private String trim(String s) {
      if (s != null) {
         s = s.trim();
         if (s.length() == 0) {
            s = null;
         }
      }

      return s;
   }
}

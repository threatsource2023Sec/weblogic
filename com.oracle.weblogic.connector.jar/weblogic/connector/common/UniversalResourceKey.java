package weblogic.connector.common;

import java.io.Serializable;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.utils.PartitionUtils;

public class UniversalResourceKey implements Serializable {
   private static final long serialVersionUID = 7927049438879097404L;
   private static final String VERSION_NONE = "NONE";
   private static final String SPLIT_STR = "|";
   private final String jndi;
   private final String version;
   private final String partitionName;
   private final String defApp;
   private final String defModule;
   private final String defComp;
   private final boolean definedInRA;
   private int hash;
   private String internalString;
   private String toString;

   public UniversalResourceKey(String jndi, String version) {
      this(PartitionUtils.getPartitionName(), (String)null, (String)null, (String)null, jndi, version);
   }

   public UniversalResourceKey(String defApp, String defModule, String defComp, String jndi, String version) {
      this(PartitionUtils.getPartitionName(), defApp, defModule, defComp, jndi, version);
   }

   UniversalResourceKey(String partitionName, String defApp, String defModule, String defComp, String jndi, String version) {
      this.hash = 0;
      this.internalString = null;
      this.toString = null;
      if (jndi != null && jndi.trim().length() != 0) {
         this.jndi = jndi.trim();
         if (version != null && version.trim().length() != 0) {
            this.version = version.trim();
         } else {
            this.version = "NONE";
         }

         this.partitionName = partitionName;
         String msg;
         if (defApp != null && defApp.trim().length() != 0) {
            this.definedInRA = false;
            this.defApp = defApp.trim();
            if (defModule != null && defModule.trim().length() != 0) {
               this.defModule = defModule.trim();
            } else {
               this.defModule = "";
            }

            if (defComp != null && defComp.trim().length() != 0) {
               this.defComp = defComp.trim();
            } else {
               this.defComp = "";
            }

            if (!isInJavaGlobalNS(jndi) && !isInJavaAppNS(jndi)) {
               if (isInJavaModuleNS(jndi)) {
                  if (this.defModule.length() == 0) {
                     msg = ConnectorLogger.getAppInfoNSNotMatch(jndi, defApp, defModule, defComp);
                     throw new IllegalArgumentException(msg);
                  }
               } else {
                  if (!isInJavaCompNS(jndi)) {
                     msg = ConnectorLogger.getWrongNameSpaceInJNDIName(jndi, defApp, defModule, defComp);
                     throw new IllegalArgumentException(msg);
                  }

                  if (this.defModule.length() == 0 || this.defComp.length() == 0) {
                     msg = ConnectorLogger.getAppInfoNSNotMatch(jndi, defApp, defModule, defComp);
                     throw new IllegalArgumentException(msg);
                  }
               }
            }
         } else {
            this.definedInRA = true;
            this.defApp = "";
            this.defModule = "";
            this.defComp = "";
            if (isInJavaNS(jndi)) {
               msg = ConnectorLogger.getRedundantJavaNameSpce(jndi);
               throw new IllegalArgumentException(msg);
            }
         }

      } else {
         throw new IllegalArgumentException("JNDI name should not be empty");
      }
   }

   public int hashCode() {
      if (this.hash == 0) {
         this.hash = this.toInternalString().hashCode();
      }

      return this.hash;
   }

   public boolean equals(Object obj) {
      if (obj != null && obj instanceof UniversalResourceKey) {
         UniversalResourceKey that = (UniversalResourceKey)obj;
         return this.toInternalString().equals(that.toInternalString());
      } else {
         return false;
      }
   }

   String toInternalString() {
      if (this.internalString == null) {
         StringBuilder sb = new StringBuilder();
         sb.append(this.partitionName).append("|");
         if (!this.isDefinedInRA()) {
            if (isInJavaGlobalNS(this.jndi)) {
               sb.append("global").append("|");
            } else if (isInJavaAppNS(this.jndi)) {
               sb.append(this.defApp).append("|");
            } else if (isInJavaModuleNS(this.jndi)) {
               sb.append(this.defApp).append('#').append(this.defModule).append("|");
            } else {
               if (!isInJavaCompNS(this.jndi)) {
                  String msg = ConnectorLogger.getWrongNameSpaceInJNDIName(this.jndi, this.defApp, this.defModule, this.defComp);
                  throw new IllegalArgumentException(msg);
               }

               sb.append(this.defApp).append('#').append(this.defModule).append('#').append(this.defComp).append("|");
            }
         }

         sb.append(this.jndi).append("|").append(this.version);
         this.internalString = sb.toString();
      }

      return this.internalString;
   }

   private static boolean isInJavaNS(String jndi) {
      return jndi.startsWith("java:");
   }

   private static boolean isInJavaGlobalNS(String jndi) {
      return jndi.startsWith("java:global");
   }

   private static boolean isInJavaAppNS(String jndi) {
      return jndi.startsWith("java:app");
   }

   private static boolean isInJavaModuleNS(String jndi) {
      return jndi.startsWith("java:module");
   }

   private static boolean isInJavaCompNS(String jndi) {
      return jndi.startsWith("java:comp");
   }

   public String toString() {
      if (this.toString == null) {
         StringBuilder sb = new StringBuilder();
         sb.append(this.getPartitionName()).append("|");
         if (!this.isDefinedInRA()) {
            sb.append(this.defApp);
            if (this.defModule.length() > 0) {
               sb.append('#').append(this.defModule);
               if (this.defComp.length() > 0) {
                  sb.append('#').append(this.defComp);
               }
            }

            sb.append("|");
         }

         sb.append(this.jndi).append("|").append(this.version);
         this.toString = sb.toString();
      }

      return this.toString;
   }

   public static UniversalResourceKey fromString(String text) {
      if (text != null && text.trim().length() != 0) {
         String[] parts = text.split("\\|");
         String partitionName;
         if (parts.length >= 3 && parts.length <= 4) {
            partitionName = null;
            String defApp = null;
            String defModule = null;
            String defComp = null;
            String jndi = null;
            String version = null;
            partitionName = parts[0];
            jndi = parts[parts.length - 2];
            version = parts[parts.length - 1];
            if (parts.length == 4) {
               String[] defAppNames = parts[1].split("#");
               switch (defAppNames.length) {
                  case 1:
                     defApp = defAppNames[0];
                     break;
                  case 2:
                     defApp = defAppNames[0];
                     defModule = defAppNames[1];
                     break;
                  case 3:
                     defApp = defAppNames[0];
                     defModule = defAppNames[1];
                     defComp = defAppNames[2];
                     break;
                  default:
                     String msg = ConnectorLogger.getInvalidDefiningAppInfoforURK(text);
                     throw new IllegalArgumentException(msg);
               }
            }

            return new UniversalResourceKey(partitionName, defApp, defModule, defComp, jndi, version);
         } else {
            partitionName = ConnectorLogger.getInvalidURKString(text);
            throw new IllegalArgumentException(partitionName);
         }
      } else {
         String msg = ConnectorLogger.getInvalidURKString(text);
         throw new IllegalArgumentException(msg);
      }
   }

   public String getJndi() {
      return this.jndi;
   }

   public String getVersion() {
      return this.version;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public String getDefApp() {
      return this.defApp;
   }

   public String getDefModule() {
      return this.defModule;
   }

   public String getDefComp() {
      return this.defComp;
   }

   public boolean isDefinedInRA() {
      return this.definedInRA;
   }

   public static String toKeyString(String jndi, String appName, String moduleName, String compName) {
      if (jndi.startsWith("java:app")) {
         moduleName = null;
         compName = null;
      } else if (jndi.startsWith("java:module")) {
         compName = null;
      } else if (!jndi.startsWith("java:comp")) {
         return jndi;
      }

      if (appName != null) {
         if (moduleName != null) {
            return compName != null ? appName + "@" + moduleName + "@" + compName + "@" + jndi : appName + "@" + moduleName + "@" + jndi;
         } else {
            return appName + "@" + jndi;
         }
      } else {
         return jndi;
      }
   }

   public String toKeyString() {
      return toKeyString(this.jndi, this.defApp, this.defModule, this.defComp);
   }
}

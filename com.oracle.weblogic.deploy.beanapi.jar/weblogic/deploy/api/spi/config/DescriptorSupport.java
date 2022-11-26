package weblogic.deploy.api.spi.config;

import javax.enterprise.deploy.shared.ModuleType;

public class DescriptorSupport {
   private ModuleType module;
   private String baseTag;
   private String configTag;
   private String baseNameSpace;
   private String configNameSpace;
   private String baseURI;
   private String configURI;
   private String stdClassName;
   private String dConfigClassName;
   private String configClassName;
   private boolean primary;
   private boolean flush = false;

   public DescriptorSupport(ModuleType module, String baseTag, String configTag, String baseNameSpace, String configNameSpace, String baseURI, String configURI, String stdClassName, String configClassName, String dConfigClassName, boolean primary) {
      this.module = module;
      this.baseTag = baseTag;
      this.configTag = configTag;
      this.baseURI = baseURI;
      this.configURI = configURI;
      this.stdClassName = stdClassName;
      this.dConfigClassName = dConfigClassName;
      this.configClassName = configClassName;
      this.primary = primary;
      this.baseNameSpace = baseNameSpace;
      this.configNameSpace = configNameSpace;
   }

   public ModuleType getModuleType() {
      return this.module;
   }

   public String getBaseTag() {
      return this.baseTag;
   }

   public String getConfigTag() {
      return this.configTag;
   }

   public void setConfigTag(String s) {
      this.configTag = s;
   }

   public String getBaseNameSpace() {
      return this.baseNameSpace;
   }

   public void setBaseNameSpace(String s) {
      this.baseNameSpace = s;
   }

   public String getConfigNameSpace() {
      return this.configNameSpace;
   }

   public void setConfigNameSpace(String s) {
      this.configNameSpace = s;
   }

   public String getBaseURI() {
      return this.baseURI;
   }

   public void setBaseURI(String s) {
      this.baseURI = s;
   }

   public String getConfigURI() {
      return this.configURI;
   }

   public void setConfigURI(String s) {
      this.configURI = s;
   }

   public String getStandardClassName() {
      return this.stdClassName;
   }

   public void setStandardClassName(String s) {
      this.stdClassName = s;
   }

   public String getDConfigClassName() {
      return this.dConfigClassName;
   }

   public void setDConfigClassName(String s) {
      this.dConfigClassName = s;
   }

   public String getConfigClassName() {
      return this.configClassName;
   }

   public void setConfigClassName(String s) {
      this.configClassName = s;
   }

   public boolean isPrimary() {
      return this.primary;
   }

   public boolean supportsConfigModules() {
      if (this.getModuleType().getValue() == ModuleType.EAR.getValue()) {
         return true;
      } else {
         return this.getModuleType().getValue() == ModuleType.WAR.getValue();
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.getModuleType().toString());
      sb.append(":");
      sb.append(this.getBaseTag());
      sb.append(":");
      sb.append(this.getConfigTag());
      sb.append(":");
      sb.append(this.getBaseURI());
      sb.append(":");
      sb.append(this.getConfigURI());
      return sb.toString();
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (!(o instanceof DescriptorSupport)) {
         return false;
      } else if (this == o) {
         return true;
      } else {
         return this.hashCode() == o.hashCode();
      }
   }

   boolean isFlush() {
      return this.flush;
   }

   void setFlush(boolean b) {
      this.flush = b;
   }
}

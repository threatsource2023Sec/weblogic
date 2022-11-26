package weblogic.descriptor.beangen;

import weblogic.descriptor.codegen.CodeGenOptions;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.utils.Getopt2;

public class BeanGenOptions extends CodeGenOptions implements BeanGenOptionsBean {
   public static final String BASE_CLASS_NAME = "baseclass";
   public static final String BASE_INTERFACE_NAME = "baseinterface";
   public static final String NO_LOCAL_VALIDATION = "nolocalvalidation";
   public static final String TEMPLATE_EXTENSION = "templateextension";
   public static final String NO_SYNTHETICS = "nosynthetics";
   public static final String TARGET_NAMESPACE = "targetNamespace";
   public static final String SCHEMA_LOCATION = "schemaLocation";
   private String baseClassName;
   private String baseInterfaceName;
   private boolean noLocalValidation;
   private boolean noSynthetics;
   private String templateExtension;
   private String targetNamespace;
   private String schemaLocation;

   public static void addOptionsToGetOpts(Getopt2 opts) {
      CodeGenOptions.addOptionsToGetOpts(opts);
      opts.setUsageArgs("[sourcefiles]");
      opts.addOption("baseinterface", "interface name", "Specifies base interface of all bean interfaces (Optional)");
      opts.addOption("baseclass", "class name", "Specifies base class of all generated implementations (Required)");
      opts.addFlag("nolocalvalidation", "Specifies that local validators should not be generated in setters");
      opts.addFlag("nosynthetics", "Specifies that no synthetic methods should be generated.  This is a hack to support generating beans that are merely wrappers aroundcustom implementations");
      opts.addOption("templateextension", "template extension", "Specifies the template extension file");
      opts.addOption("targetNamespace", "targetNamespace", "Specifies the target Namespace");
      opts.addOption("schemaLocation", "schemaLocation", "Specifies the schema location");
   }

   public void readOptions(Getopt2 opts, String defaultTemplate) throws BeanGenerationException {
      super.readOptions(opts, defaultTemplate);
      this.setBaseInterfaceName(opts.getOption("baseinterface", (String)null));
      this.setBaseClassName(opts.getOption("baseclass", (String)null));
      this.setNoLocalValidation(opts.getBooleanOption("nolocalvalidation", false));
      this.setNoSynthetics(opts.getBooleanOption("nosynthetics", false));
      this.setTemplateExtension(opts.getOption("templateextension", (String)null));
      this.setTargetNamespace(opts.getOption("targetNamespace", (String)null));
      this.setSchemaLocation(opts.getOption("schemaLocation", (String)null));
   }

   public String getBaseClassName() {
      return this.baseClassName != null ? this.baseClassName : AbstractDescriptorBean.class.getName();
   }

   public void setBaseClassName(String baseClassName) {
      this.baseClassName = baseClassName;
   }

   public String getBaseInterfaceName() {
      return this.baseInterfaceName;
   }

   public void setBaseInterfaceName(String baseInterfaceName) {
      if (baseInterfaceName != null && baseInterfaceName.length() == 0) {
         baseInterfaceName = null;
      }

      this.baseInterfaceName = baseInterfaceName;
   }

   public boolean isNoLocalValidation() {
      return this.noLocalValidation;
   }

   public void setNoLocalValidation(boolean noLocalValidation) {
      this.noLocalValidation = noLocalValidation;
   }

   public void setNoSynthetics(boolean flag) {
      this.noSynthetics = flag;
   }

   public boolean getNoSynthetics() {
      return this.noSynthetics;
   }

   public void setTemplateExtension(String template) {
      this.templateExtension = template;
   }

   public String getTemplateExtension() {
      return this.templateExtension;
   }

   public String getTargetNamespace() {
      return this.targetNamespace;
   }

   public void setTargetNamespace(String namespace) {
      this.targetNamespace = namespace;
   }

   public String getSchemaLocation() {
      return this.schemaLocation;
   }

   public void setSchemaLocation(String location) {
      this.schemaLocation = location;
   }

   public String toString() {
      return "BeanGenOptions{baseClassName='" + this.baseClassName + '\'' + ", baseInterfaceName='" + this.baseInterfaceName + '\'' + ", noLocalValidation=" + this.noLocalValidation + ", noSynthetics=" + this.noSynthetics + ", templateExtension='" + this.templateExtension + '\'' + ", targetNamespace='" + this.targetNamespace + '\'' + ", schemaLocation='" + this.schemaLocation + '\'' + " extends " + super.toString() + '}';
   }
}

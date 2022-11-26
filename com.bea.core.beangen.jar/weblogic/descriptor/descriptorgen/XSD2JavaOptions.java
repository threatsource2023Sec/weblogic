package weblogic.descriptor.descriptorgen;

import weblogic.descriptor.codegen.CodeGenOptions;
import weblogic.utils.Getopt2;

public class XSD2JavaOptions extends CodeGenOptions {
   static String MBEAN_SPEC = "mbeanspec";
   static String MBEAN_PACKAGE = "mbeanpackage";
   static String ORIGINALS_PATH = "originalspath";
   static String ORIGINALS_PACKAGE = "originalspackage";
   protected String mBeanSpec;
   protected String mBeanPackages;
   protected String originalsPaths;
   protected String originalsPackages;

   protected XSD2JavaOptions() {
   }

   public static void adOptionsToGetOpts(Getopt2 opts) {
      CodeGenOptions.addOptionsToGetOpts(opts);
      opts.addOption(MBEAN_SPEC, "string", "The file spec for mbean interfaces to merge.");
      opts.addOption(MBEAN_PACKAGE, "string", "Package directory to seach for MBean interfaces to merge.");
      opts.addOption(ORIGINALS_PATH, "string", "Root directory to seach for original interfaces to merge.");
      opts.addOption(ORIGINALS_PACKAGE, "string", "Package directory to seach for original interfaces to merge.");
   }

   public void readOptions(Getopt2 opts, String defaultTemplate) {
      super.readOptions(opts, defaultTemplate);
      this.setOriginalsPath(opts.getOption(ORIGINALS_PATH, "."));
      this.setOriginalsPackages(opts.getOption(ORIGINALS_PACKAGE, "."));
      this.setMBeanPackages(opts.getOption(MBEAN_PACKAGE, "weblogic/management/configuration/"));
      if (opts.hasOption(MBEAN_SPEC)) {
         this.setMBeanSpec(opts.getOption(MBEAN_SPEC, "."));
      }

      this.setSuffix("");
   }

   public String getOriginalsPath() {
      return this.originalsPaths;
   }

   public void setOriginalsPath(String s) {
      this.originalsPaths = s;
   }

   public String getOriginalsPackages() {
      return this.originalsPackages;
   }

   public void setOriginalsPackages(String s) {
      this.originalsPackages = s;
   }

   public String getMBeanPackages() {
      return this.mBeanPackages;
   }

   public void setMBeanPackages(String s) {
      this.mBeanPackages = s;
   }

   public String getMBeanSpec() {
      return this.mBeanSpec;
   }

   public void setMBeanSpec(String s) {
      this.mBeanSpec = s;
   }
}

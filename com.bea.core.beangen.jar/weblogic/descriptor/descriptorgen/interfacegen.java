package weblogic.descriptor.descriptorgen;

import weblogic.utils.compiler.Tool;

public class interfacegen extends Tool {
   protected interfacegen(String[] args) {
      super(args);
   }

   public void prepare() {
      XSD2JavaOptions.adOptionsToGetOpts(this.opts);
      this.opts.setUsageArgs("[sourcefiles]");
   }

   public void runBody() throws Exception {
      XSD2JavaOptions options = new XSD2JavaOptions();
      options.readOptions(this.opts, "weblogic/descriptor/descriptorgen/XSD2Java.template");
      XSD2Java generator = new XSD2Java(options);
      generator.generate();
   }

   public static void main(String[] args) throws Exception {
      try {
         (new interfacegen(args)).run();
      } catch (Throwable var2) {
         var2.printStackTrace();
      }

   }
}

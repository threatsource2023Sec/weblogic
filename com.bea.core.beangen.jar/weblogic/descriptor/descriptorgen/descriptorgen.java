package weblogic.descriptor.descriptorgen;

import java.util.Arrays;
import weblogic.descriptor.codegen.CodeGenOptions;
import weblogic.utils.compiler.Tool;

public class descriptorgen extends Tool {
   private DeploymentDescriptorGenerator generator;

   protected descriptorgen(String[] args) {
      super(args);
   }

   public void prepare() {
      CodeGenOptions.addOptionsToGetOpts(this.opts);
      this.opts.setUsageArgs("[sourcefiles]");
   }

   public void runBody() throws Exception {
      CodeGenOptions options = new CodeGenOptions();
      options.readOptions(this.opts, "weblogic/utils/descriptorgen/descriptor.template");
      this.generator = new DeploymentDescriptorGenerator(options);
      this.generator.generate();
   }

   public static void main(String[] args) throws Exception {
      try {
         (new descriptorgen(args)).run();
      } catch (Throwable var2) {
         var2.printStackTrace();
         if (!Arrays.asList(args).contains("-noexit")) {
            System.exit(1);
         }
      }

   }
}

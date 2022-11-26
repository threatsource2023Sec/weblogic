package weblogic.descriptor.beangen;

import weblogic.utils.compiler.Tool;

public class beangen extends Tool {
   private beangen(String[] args) {
      super(args);
   }

   public void prepare() {
      BeanGenOptions.addOptionsToGetOpts(this.opts);
   }

   public void runBody() throws Exception {
      BeanGenOptions beangenOptions = new BeanGenOptions();
      beangenOptions.readOptions(this.opts, "weblogic/descriptor/beangen/DescriptorBean.template");
      BeanGenerator beanGenerator = new BeanGenerator(beangenOptions);
      beanGenerator.generate();
   }

   public static void main(String[] args) throws Exception {
      try {
         (new beangen(args)).run();
      } catch (Throwable var2) {
         var2.printStackTrace();
         System.exit(-1);
      }

   }
}

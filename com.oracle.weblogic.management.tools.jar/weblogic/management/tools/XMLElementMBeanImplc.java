package weblogic.management.tools;

import weblogic.utils.compiler.CodeGenerator;
import weblogic.utils.compiler.CompilerInvoker;
import weblogic.utils.compiler.Tool;

public class XMLElementMBeanImplc extends Tool {
   private CodeGenerator generator;
   private CompilerInvoker compiler;

   private XMLElementMBeanImplc(String[] args) {
      super(args);
   }

   public static void main(String[] args) throws Exception {
      (new XMLElementMBeanImplc(args)).run();
   }

   public void prepare() {
      this.generator = new XMLElementMBeanImplGenerator(this.opts);
      this.compiler = new CompilerInvoker(this.opts);
   }

   public void runBody() throws Exception {
      String[] sources = this.generator.generate(this.opts.args());
   }
}

package weblogic.xml.process;

import weblogic.utils.compiler.CompilerInvoker;
import weblogic.utils.compiler.Tool;
import weblogic.utils.compiler.ToolFailureException;

public class xmlproc extends Tool {
   private static final boolean debug = false;

   public xmlproc(String[] args) {
      super(args);
   }

   public void prepare() {
      this.opts.addFlag("generatemain", "Generate a main method to invoke the processing");
      new CompilerInvoker(this.opts);
   }

   public void runBody() throws ToolFailureException {
      Compiler.compile(this.opts);
   }

   public static void main(String[] args) throws Exception {
      (new xmlproc(args)).run();
   }
}

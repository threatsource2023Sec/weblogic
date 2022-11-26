package weblogic;

import java.lang.reflect.Constructor;
import weblogic.rmi.utils.Utilities;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.CodeGenerator;
import weblogic.utils.compiler.Tool;

public class j2idl extends Tool {
   private CodeGenerator idl;
   private ClassLoader classLoader = null;

   public void prepare() {
      Utilities.setClassLoader(this.classLoader);

      try {
         Class ncClass = Utilities.classForName("weblogic.corba.rmic.IDLGenerator", this.getClass());
         Class[] paramTypes = new Class[]{Getopt2.class};
         Constructor c = ncClass.getConstructor(paramTypes);
         Object[] params = new Object[]{this.opts};
         this.idl = (CodeGenerator)c.newInstance(params);
      } catch (Throwable var5) {
      }

      this.opts.setUsageArgs("<classes>...");
   }

   public void runBody() throws Exception {
      if (this.idl != null) {
         this.idl.generate(this.opts.args());
      }

   }

   private j2idl(String[] args) {
      super(args);
   }

   private j2idl(String[] args, ClassLoader cl) {
      super(args);
      this.classLoader = cl;
   }

   public static void main(String[] args, ClassLoader cl) throws Exception {
      (new j2idl(args, cl)).run();
   }

   public static void main(String[] args) throws Exception {
      (new j2idl(args)).run();
   }
}

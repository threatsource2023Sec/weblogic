package weblogic;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Vector;
import weblogic.rmi.rmic.Remote2Java;
import weblogic.rmi.utils.Utilities;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.CodeGenerator;
import weblogic.utils.compiler.CompilerInvoker;
import weblogic.utils.compiler.Tool;
import weblogic.utils.compiler.ToolFailureException;

public class rmic extends Tool {
   private CodeGenerator m_rmi;
   private CodeGenerator m_idl;
   private CodeGenerator m_iiop;
   private CompilerInvoker m_compiler;
   private ClassLoader m_classLoader = null;
   private Collection m_rmicMethodDescriptors = null;
   private static Vector ss = null;
   private static boolean no_compile = false;

   public void prepare() {
      Utilities.setClassLoader(this.m_classLoader);
      this.m_rmi = new Remote2Java(this.opts, this.m_classLoader, this.m_rmicMethodDescriptors);
      this.m_idl = this.createCodeGenerator("weblogic.corba.rmic.IDLGenerator");
      this.m_iiop = this.createCodeGenerator("weblogic.corba.rmic.Remote2Corba");
      this.m_compiler = new CompilerInvoker(this.opts);
      this.opts.setUsageArgs("<classes>...");
   }

   private void validateToolInputs() throws ToolFailureException {
      if (this.opts.args().length < 1) {
         this.opts.usageError("weblogic.rmic");
         throw new ToolFailureException("ERROR: incorrect command-line.");
      }
   }

   public void runBody() throws Exception {
      this.validateToolInputs();

      try {
         String[] sources = this.m_rmi.generate(this.opts.args());
         if (no_compile && sources != null) {
            for(int i = 0; i < sources.length; ++i) {
               ss.addElement(sources[i]);
            }
         }

         if (this.opts.hasOption("idl") && this.m_idl != null) {
            this.m_idl.generate(this.opts.args());
         }

         if (this.opts.hasOption("iiop") || this.opts.hasOption("iiopTie") || this.opts.hasOption("iiopSun")) {
            this.m_iiop.generate(this.opts.args());
         }
      } catch (ClassNotFoundException var6) {
         System.err.println("Class not found : " + var6.getMessage());
         throw var6;
      } finally {
         Utilities.setClassLoader((ClassLoader)null);
      }

   }

   private rmic(String[] args) {
      super(args);
   }

   private rmic(String[] args, ClassLoader cl, Collection mds) {
      super(args);
      this.m_classLoader = cl;
      if (mds == null) {
         this.m_rmicMethodDescriptors = null;
      } else {
         this.m_rmicMethodDescriptors = new TreeSet(mds);
      }

   }

   public static void main(String[] args, ClassLoader cl) throws Exception {
      (new rmic(args, cl, (Collection)null)).run();
   }

   public static synchronized String[] main_nocompile(String[] args, ClassLoader cl, Collection rmicMethodDescriptors) throws Exception {
      ss = new Vector();
      no_compile = true;
      (new rmic(args, cl, rmicMethodDescriptors)).run();
      String[] ssa = new String[ss.size()];
      ss.copyInto(ssa);
      return ssa;
   }

   private final CodeGenerator createCodeGenerator(String name) {
      try {
         Class ncClass = Utilities.classForName(name, this.getClass());
         Class[] paramTypes = new Class[]{Getopt2.class};
         Constructor c = ncClass.getConstructor(paramTypes);
         Object[] params = new Object[]{this.opts};
         return (CodeGenerator)c.newInstance(params);
      } catch (Throwable var6) {
         return null;
      }
   }

   public static void main(String[] args) throws Exception {
      (new rmic(args)).run();
   }
}

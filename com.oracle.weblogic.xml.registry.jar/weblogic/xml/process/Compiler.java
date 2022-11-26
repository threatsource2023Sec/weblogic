package weblogic.xml.process;

import java.io.IOException;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.CodeGenerator;
import weblogic.utils.compiler.CompilerInvoker;
import weblogic.utils.compiler.ToolFailureException;

public class Compiler {
   private static final boolean debug = true;
   private static final boolean verbose = true;

   public static void compile(Getopt2 opts) throws ToolFailureException {
      String[] args = opts.args();
      String inputFilePath = args[0];
      Object instrux = null;
      CodeGenerator codeGen = null;

      try {
         ProcessorFactory factory = new ProcessorFactory();
         XMLProcessor loader = factory.getProcessor((String)inputFilePath, (String[])null);
         Debug.assertion(loader != null);
         loader.process(inputFilePath);
         if (loader instanceof PILoader) {
            instrux = ((PILoader)loader).getProcessingInstructions();
            codeGen = new ProcessorCompiler(opts);
         } else {
            if (!(loader instanceof GILoaderBase)) {
               throw new AssertionError("unknown loader type");
            }

            instrux = ((GILoaderBase)loader).getGeneratingInstructions();
            codeGen = new GeneratorCompiler(opts);
         }
      } catch (ProcessorFactoryException var9) {
         var9.printStackTrace();
         throw new ToolFailureException("ERROR: in obtaining a processor: ", var9);
      } catch (XMLParsingException var10) {
         var10.printStackTrace();
         throw new ToolFailureException("ERROR: in parsing processing instructions: ", var10);
      } catch (XMLProcessingException var11) {
         var11.printStackTrace();
         throw new ToolFailureException("ERROR: in loading processing instructions: ", var11);
      } catch (IOException var12) {
         var12.printStackTrace();
         throw new ToolFailureException("ERROR: in reading processing instructions: ", var12);
      }

      try {
         Debug.assertion(instrux != null);
         String[] generated = ((CodeGenerator)codeGen).generate(new Object[]{instrux});
         if (!opts.hasOption("nowrite")) {
            CompilerInvoker compiler = new CompilerInvoker(opts);
            compiler.compile(generated);
         }
      } catch (IOException var7) {
         var7.printStackTrace();
         throw new ToolFailureException("ERROR: during code compilation", var7);
      } catch (Exception var8) {
         var8.printStackTrace();
         throw new ToolFailureException("ERROR: during code generation", var8);
      }
   }
}

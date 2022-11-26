package weblogic.spring.tools;

import weblogic.spring.tools.internal.InstrumentFlagChecker;
import weblogic.spring.tools.internal.InstrumentFlagCheckerImpl;
import weblogic.spring.tools.internal.InstrumentFlagWriter;
import weblogic.spring.tools.internal.InstrumentFlagWriterImpl;
import weblogic.spring.tools.internal.InstrumentorImpl;
import weblogic.spring.tools.internal.JarFileCreator;
import weblogic.spring.tools.internal.JarFileCreatorImpl;
import weblogic.spring.tools.internal.JarFileExtractor;
import weblogic.spring.tools.internal.JarFileExtractorImpl;
import weblogic.spring.tools.internal.TemporaryDirectoryManagerFactory;
import weblogic.spring.tools.internal.TemporaryDirectoryManagerFactoryImpl;
import weblogic.utils.compiler.Tool;

public class SpringInstrumentor extends Tool {
   private static final String OPTION_INPUT = "input";
   private static final String OPTION_OUTPUT = "output";

   public SpringInstrumentor(String[] args) {
      super(args);
   }

   public void prepare() throws Exception {
      this.setRequireExtraArgs(false);
      this.opts.addOption("input", "file", "Specify spring jar file to be intrumented");
      this.opts.addOption("output", "file", "Specify where to put instrumented spring jar file");
   }

   public void runBody() throws Exception {
      if (!this.opts.hasOption("input")) {
         System.out.println("Option <input> is required.");
         System.exit(-1);
      }

      if (!this.opts.hasOption("output")) {
         System.out.println("Option <output> is required.");
         System.exit(-1);
      }

      String input = this.opts.getOption("input");
      String output = this.opts.getOption("output");
      System.out.println("Trying to instrument [" + input + "], output file will be [" + output + "].");
      Instrumentor instrumentor = this.createInstrumentor();
      instrumentor.instrument(input, output);
   }

   public static void main(String[] args) throws Exception {
      (new SpringInstrumentor(args)).run();
   }

   private Instrumentor createInstrumentor() {
      TemporaryDirectoryManagerFactory tdm = new TemporaryDirectoryManagerFactoryImpl();
      JarFileExtractor jfe = new JarFileExtractorImpl();
      InstrumentFlagChecker ifc = new InstrumentFlagCheckerImpl();
      InstrumentFlagWriter ifw = new InstrumentFlagWriterImpl();
      JarFileCreator jfc = new JarFileCreatorImpl();
      InstrumentorImpl impl = new InstrumentorImpl();
      impl.setTemporaryDirectoryManagerFactory(tdm);
      impl.setJarFileExtractor(jfe);
      impl.setInstrumentFlagChecker(ifc);
      impl.setInstrumentFlagWriter(ifw);
      impl.setJarFileCreator(jfc);
      return impl;
   }
}

package weblogic.spring.tools.internal;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import org.apache.commons.lang.StringUtils;
import weblogic.spring.monitoring.instrumentation.NoSupportedSpringInstrumentorEngineCreatorFoundException;
import weblogic.spring.monitoring.instrumentation.SpringInstrumentorEngineCreator;
import weblogic.spring.monitoring.instrumentation.SpringInstrumentorEngineCreatorFactory;
import weblogic.spring.monitoring.instrumentation.SpringInstrumentorEngineCreatorFactoryImpl;
import weblogic.spring.monitoring.instrumentation.SpringVersionLoader;
import weblogic.spring.tools.Instrumentor;
import weblogic.spring.tools.SpringInstrumentException;

public class InstrumentorImpl implements Instrumentor {
   private TemporaryDirectoryManagerFactory temporaryDirectoryManagerFactory;
   private JarFileExtractor jarFileExtractor;
   private InstrumentFlagChecker instrumentFlagChecker;
   private InstrumentFlagWriter instrumentFlagWriter;
   private JarFileCreator jarFileCreator;

   public void setTemporaryDirectoryManagerFactory(TemporaryDirectoryManagerFactory temporaryDirectoryManagerFactory) {
      this.temporaryDirectoryManagerFactory = temporaryDirectoryManagerFactory;
   }

   public void setJarFileExtractor(JarFileExtractor jarFileExtractor) {
      this.jarFileExtractor = jarFileExtractor;
   }

   public void setInstrumentFlagChecker(InstrumentFlagChecker instrumentFlagChecker) {
      this.instrumentFlagChecker = instrumentFlagChecker;
   }

   public void setInstrumentFlagWriter(InstrumentFlagWriter instrumentFlagWriter) {
      this.instrumentFlagWriter = instrumentFlagWriter;
   }

   public void setJarFileCreator(JarFileCreator jarFileCreator) {
      this.jarFileCreator = jarFileCreator;
   }

   public void instrument(String input, String output) throws SpringInstrumentException {
      if (StringUtils.isEmpty(input)) {
         throw new IllegalArgumentException("Input cannot be empty.");
      } else if (StringUtils.isEmpty(output)) {
         throw new IllegalArgumentException("Output cannot be empty.");
      } else {
         this.doCheckFileExists(input);
         String springVersion = this.getSpringVersion(input);
         System.out.println("Spring version is [" + springVersion + "].");
         SpringInstrumentorEngineCreatorFactory factory = SpringInstrumentorEngineCreatorFactoryImpl.getInstance();
         SpringInstrumentorEngineCreator creator = factory.build(springVersion);
         if (creator == null) {
            String[] supportedVersions = SpringVersionLoader.getInstance().getSupportedSpringVersions();
            StringBuilder sbuf = new StringBuilder();

            for(int j = 0; j < supportedVersions.length; ++j) {
               sbuf.append(supportedVersions[j]);
               if (j != supportedVersions.length - 1) {
                  sbuf.append(", ");
               }
            }

            System.out.println("Supported spring versions are [" + sbuf.toString() + "].");
            throw new NoSupportedSpringInstrumentorEngineCreatorFoundException(springVersion);
         } else {
            ClassInstrumentor classInstrumentor = new ClassInstrumentorWLDFImpl(creator.createSpringInstrumentorEngine());
            TemporaryDirectoryManager tdm = this.temporaryDirectoryManagerFactory.buildInstance();
            File tmpDir = new File(tdm.getTemporaryDirectory());
            System.out.println("Temporary directory [" + tmpDir + "] created.");

            try {
               this.doInstrument(classInstrumentor, new File(input), output, tmpDir);
            } catch (Exception var13) {
               if (var13 instanceof SpringInstrumentException) {
                  throw (SpringInstrumentException)var13;
               }

               throw new SpringInstrumentException(var13.getMessage(), var13);
            } finally {
               tdm.cleanup();
               System.out.println("Temporary directory removed.");
            }

         }
      }
   }

   void doCheckFileExists(String file) throws SpringInstrumentException {
      File inputFile = new File(file);
      if (!inputFile.exists() && !inputFile.isFile()) {
         throw new SpringInstrumentException("[" + file + "] doesn't exist.");
      }
   }

   private String getSpringVersion(String input) {
      try {
         JarFile jarFile = new JarFile(new File(input));
         Manifest mf = jarFile.getManifest();
         Attributes attrs = mf.getMainAttributes();
         return attrs.getValue("Implementation-Version");
      } catch (IOException var5) {
         throw new SpringInstrumentException("Cannot determine spring version.");
      }
   }

   private void doInstrument(ClassInstrumentor classInstrumentor, File input, String output, File tmpDir) throws Exception {
      this.jarFileExtractor.extract(input, tmpDir);
      System.out.println("[" + input + "] has been extracted into temporary directory.");
      if (this.instrumentFlagChecker.check(tmpDir.getAbsolutePath())) {
         throw new SpringInstrumentException("The jar file [" + input + "] already instrumented, ignore.");
      } else {
         String[] fileToInstrumentList = new String[]{"/org/springframework/context/support/AbstractApplicationContext.class", "/org/springframework/beans/factory/support/AbstractBeanFactory.class", "/org/springframework/transaction/support/AbstractPlatformTransactionManager.class", "/org/springframework/beans/factory/support/DefaultListableBeanFactory.class"};
         String[] var6 = fileToInstrumentList;
         int var7 = fileToInstrumentList.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String each = var6[var8];
            File fileToInstrument = new File(tmpDir.getAbsolutePath() + each);
            this.doCheckFileExists(fileToInstrument.getAbsolutePath());
            System.out.println("File [" + each + "] found.");
            String className = StringUtils.substringAfter(each, "/");
            className = StringUtils.substringBeforeLast(className, ".class");
            className = StringUtils.replace(className, "/", ".");
            classInstrumentor.instrument(fileToInstrument.getAbsolutePath(), className);
         }

         this.instrumentFlagWriter.instrumented(tmpDir.getAbsolutePath());
         this.jarFileCreator.create(tmpDir, output);
         System.out.println("Jar file [" + output + "] builded from temporary directory.");
      }
   }
}

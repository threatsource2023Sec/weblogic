package com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch;

import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.batch.Main;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilation;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import javax.annotation.processing.Processor;
import javax.lang.model.SourceVersion;
import javax.tools.JavaFileManager;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;

public class BatchAnnotationProcessorManager extends BaseAnnotationProcessorManager {
   private List _setProcessors = null;
   private Iterator _setProcessorIter = null;
   private List _commandLineProcessors;
   private Iterator _commandLineProcessorIter = null;
   private ServiceLoader _serviceLoader = null;
   private Iterator _serviceLoaderIter;
   private ClassLoader _procLoader;
   private static final boolean VERBOSE_PROCESSOR_DISCOVERY = true;
   private boolean _printProcessorDiscovery = false;

   public void configure(Object batchCompiler, String[] commandLineArguments) {
      if (this._processingEnv != null) {
         throw new IllegalStateException("Calling configure() more than once on an AnnotationProcessorManager is not supported");
      } else {
         BatchProcessingEnvImpl processingEnv = new BatchProcessingEnvImpl(this, (Main)batchCompiler, commandLineArguments);
         this._processingEnv = processingEnv;
         JavaFileManager fileManager = processingEnv.getFileManager();
         if (fileManager instanceof StandardJavaFileManager) {
            Iterable location = null;
            if (SourceVersion.latest().compareTo(SourceVersion.RELEASE_8) > 0) {
               location = ((StandardJavaFileManager)fileManager).getLocation(StandardLocation.ANNOTATION_PROCESSOR_MODULE_PATH);
            }

            if (location != null) {
               this._procLoader = fileManager.getClassLoader(StandardLocation.ANNOTATION_PROCESSOR_MODULE_PATH);
            } else {
               this._procLoader = fileManager.getClassLoader(StandardLocation.ANNOTATION_PROCESSOR_PATH);
            }
         } else {
            this._procLoader = fileManager.getClassLoader(StandardLocation.ANNOTATION_PROCESSOR_PATH);
         }

         this.parseCommandLine(commandLineArguments);
         this._round = 0;
      }
   }

   private void parseCommandLine(String[] commandLineArguments) {
      List commandLineProcessors = null;

      label33:
      for(int i = 0; i < commandLineArguments.length; ++i) {
         String option = commandLineArguments[i];
         if ("-XprintProcessorInfo".equals(option)) {
            this._printProcessorInfo = true;
            this._printProcessorDiscovery = true;
         } else if ("-XprintRounds".equals(option)) {
            this._printRounds = true;
         } else if ("-processor".equals(option)) {
            commandLineProcessors = new ArrayList();
            ++i;
            String procs = commandLineArguments[i];
            String[] var9;
            int var8 = (var9 = procs.split(",")).length;
            int var7 = 0;

            while(true) {
               if (var7 >= var8) {
                  break label33;
               }

               String proc = var9[var7];
               commandLineProcessors.add(proc);
               ++var7;
            }
         }
      }

      this._commandLineProcessors = commandLineProcessors;
      if (this._commandLineProcessors != null) {
         this._commandLineProcessorIter = this._commandLineProcessors.iterator();
      }

   }

   public ProcessorInfo discoverNextProcessor() {
      Processor p;
      ProcessorInfo pi;
      if (this._setProcessors != null) {
         if (this._setProcessorIter.hasNext()) {
            p = (Processor)this._setProcessorIter.next();
            p.init(this._processingEnv);
            pi = new ProcessorInfo(p);
            this._processors.add(pi);
            if (this._printProcessorDiscovery && this._out != null) {
               this._out.println("API specified processor: " + pi);
            }

            return pi;
         } else {
            return null;
         }
      } else if (this._commandLineProcessors != null) {
         if (this._commandLineProcessorIter.hasNext()) {
            String proc = (String)this._commandLineProcessorIter.next();

            try {
               Class clazz = this._procLoader.loadClass(proc);
               Object o = clazz.newInstance();
               Processor p = (Processor)o;
               p.init(this._processingEnv);
               ProcessorInfo pi = new ProcessorInfo(p);
               this._processors.add(pi);
               if (this._printProcessorDiscovery && this._out != null) {
                  this._out.println("Command line specified processor: " + pi);
               }

               return pi;
            } catch (Exception var6) {
               throw new AbortCompilation((CompilationResult)null, var6);
            }
         } else {
            return null;
         }
      } else {
         if (this._serviceLoader == null) {
            this._serviceLoader = ServiceLoader.load(Processor.class, this._procLoader);
            this._serviceLoaderIter = this._serviceLoader.iterator();
         }

         try {
            if (this._serviceLoaderIter.hasNext()) {
               p = (Processor)this._serviceLoaderIter.next();
               p.init(this._processingEnv);
               pi = new ProcessorInfo(p);
               this._processors.add(pi);
               if (this._printProcessorDiscovery && this._out != null) {
                  StringBuilder sb = new StringBuilder();
                  sb.append("Discovered processor service ");
                  sb.append(pi);
                  sb.append("\n  supporting ");
                  sb.append(pi.getSupportedAnnotationTypesAsString());
                  sb.append("\n  in ");
                  sb.append(this.getProcessorLocation(p));
                  this._out.println(sb.toString());
               }

               return pi;
            } else {
               return null;
            }
         } catch (ServiceConfigurationError var7) {
            throw new AbortCompilation((CompilationResult)null, var7);
         }
      }
   }

   private String getProcessorLocation(Processor p) {
      boolean isMember = false;
      Class outerClass = p.getClass();

      StringBuilder innerName;
      for(innerName = new StringBuilder(); outerClass.isMemberClass(); outerClass = outerClass.getEnclosingClass()) {
         innerName.insert(0, outerClass.getSimpleName());
         innerName.insert(0, '$');
         isMember = true;
      }

      String path = outerClass.getName();
      path = path.replace('.', '/');
      if (isMember) {
         path = path + innerName;
      }

      path = path + ".class";
      String location = this._procLoader.getResource(path).toString();
      if (location.endsWith(path)) {
         location = location.substring(0, location.length() - path.length());
      }

      return location;
   }

   public void reportProcessorException(Processor p, Exception e) {
      throw new AbortCompilation((CompilationResult)null, e);
   }

   public void setProcessors(Object[] processors) {
      if (!this._isFirstRound) {
         throw new IllegalStateException("setProcessors() cannot be called after processing has begun");
      } else {
         this._setProcessors = new ArrayList(processors.length);
         Object[] var5 = processors;
         int var4 = processors.length;

         for(int var3 = 0; var3 < var4; ++var3) {
            Object o = var5[var3];
            Processor p = (Processor)o;
            this._setProcessors.add(p);
         }

         this._setProcessorIter = this._setProcessors.iterator();
         this._commandLineProcessors = null;
         this._commandLineProcessorIter = null;
      }
   }

   public void reset() {
      super.reset();
      if (this._procLoader instanceof URLClassLoader) {
         try {
            ((URLClassLoader)this._procLoader).close();
         } catch (IOException var2) {
            var2.printStackTrace();
         }
      }

   }
}

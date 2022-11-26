package com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch;

import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.apt.util.EclipseFileManager;
import com.bea.core.repackaged.jdt.internal.compiler.batch.Main;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilation;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.tools.JavaFileManager;

public class BatchProcessingEnvImpl extends BaseProcessingEnvImpl {
   protected final BaseAnnotationProcessorManager _dispatchManager;
   protected final JavaFileManager _fileManager;
   protected final Main _compilerOwner;

   public BatchProcessingEnvImpl(BaseAnnotationProcessorManager dispatchManager, Main batchCompiler, String[] commandLineArguments) {
      this._compilerOwner = batchCompiler;
      this._compiler = batchCompiler.batchCompiler;
      this._dispatchManager = dispatchManager;
      Class c = null;

      try {
         c = Class.forName("com.bea.core.repackaged.jdt.internal.compiler.tool.EclipseCompilerImpl");
      } catch (ClassNotFoundException var20) {
      }

      Field field = null;
      JavaFileManager javaFileManager = null;
      if (c != null) {
         try {
            field = c.getField("fileManager");
         } catch (SecurityException var17) {
         } catch (IllegalArgumentException var18) {
         } catch (NoSuchFieldException var19) {
         }
      }

      if (field != null) {
         try {
            javaFileManager = (JavaFileManager)field.get(batchCompiler);
         } catch (IllegalArgumentException var15) {
         } catch (IllegalAccessException var16) {
         }
      }

      if (javaFileManager != null) {
         this._fileManager = javaFileManager;
      } else {
         String encoding = (String)batchCompiler.options.get("com.bea.core.repackaged.jdt.core.encoding");
         Charset charset = encoding != null ? Charset.forName(encoding) : null;
         JavaFileManager manager = new EclipseFileManager(batchCompiler.compilerLocale, charset);
         ArrayList options = new ArrayList();
         String[] var14 = commandLineArguments;
         int var13 = commandLineArguments.length;

         for(int var12 = 0; var12 < var13; ++var12) {
            String argument = var14[var12];
            options.add(argument);
         }

         Iterator iterator = options.iterator();

         while(iterator.hasNext()) {
            manager.handleOption((String)iterator.next(), iterator);
         }

         this._fileManager = manager;
      }

      this._processorOptions = Collections.unmodifiableMap(this.parseProcessorOptions(commandLineArguments));
      this._filer = new BatchFilerImpl(this._dispatchManager, this);
      this._messager = new BatchMessagerImpl(this, this._compilerOwner);
   }

   private Map parseProcessorOptions(String[] args) {
      Map options = new LinkedHashMap();
      String[] var6 = args;
      int var5 = args.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         String arg = var6[var4];
         if (arg.startsWith("-A")) {
            int equals = arg.indexOf(61);
            if (equals == 2) {
               Exception e = new IllegalArgumentException("-A option must have a key before the equals sign");
               throw new AbortCompilation((CompilationResult)null, e);
            }

            if (equals == arg.length() - 1) {
               options.put(arg.substring(2, equals), (Object)null);
            } else if (equals == -1) {
               options.put(arg.substring(2), (Object)null);
            } else {
               options.put(arg.substring(2, equals), arg.substring(equals + 1));
            }
         }
      }

      return options;
   }

   public JavaFileManager getFileManager() {
      return this._fileManager;
   }

   public Locale getLocale() {
      return this._compilerOwner.compilerLocale;
   }
}

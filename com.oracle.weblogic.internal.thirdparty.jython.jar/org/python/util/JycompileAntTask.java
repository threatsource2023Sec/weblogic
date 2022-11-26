package org.python.util;

import java.io.File;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import org.python.apache.tools.ant.BuildException;
import org.python.core.PyException;
import org.python.core.PySystemState;
import org.python.core.imp;
import org.python.modules._py_compile;

public class JycompileAntTask extends GlobMatchingTask {
   public void process(Set toCompile) throws BuildException {
      if (toCompile.size() != 0) {
         if (toCompile.size() > 1) {
            this.log("Compiling " + toCompile.size() + " files");
         } else if (toCompile.size() == 1) {
            this.log("Compiling 1 file");
         }

         Properties props = new Properties();
         props.setProperty("python.cachedir.skip", "true");
         PySystemState.initialize(System.getProperties(), props);
         Iterator var3 = toCompile.iterator();

         while(var3.hasNext()) {
            File src = (File)var3.next();

            try {
               String name = _py_compile.getModuleName(src);
               String compiledFilePath = name.replace('.', '/');
               if (src.getName().endsWith("__init__.py")) {
                  compiledFilePath = compiledFilePath + "/__init__.py";
               } else {
                  compiledFilePath = compiledFilePath + ".py";
               }

               File compiled = new File(this.destDir, imp.makeCompiledFilename(compiledFilePath));
               this.compile(src, compiled, name);
            } catch (RuntimeException var8) {
               this.log("Could not compile " + src);
               throw var8;
            }
         }

      }
   }

   protected void compile(File src, File compiled, String moduleName) {
      byte[] bytes;
      try {
         bytes = imp.compileSource(moduleName, src);
      } catch (PyException var6) {
         var6.printStackTrace();
         throw new BuildException("Compile failed; see the compiler error output for details.");
      }

      File dir = compiled.getParentFile();
      if (!dir.exists() && !compiled.getParentFile().mkdirs()) {
         throw new BuildException("Unable to make directory for compiled file: " + compiled);
      } else {
         imp.cacheCompiledSource(src.getAbsolutePath(), compiled.getAbsolutePath(), bytes);
      }
   }

   protected String getFrom() {
      return "*.py";
   }

   protected String getTo() {
      return imp.makeCompiledFilename(this.getFrom());
   }
}

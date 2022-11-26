package com.bea.staxb.buildtime;

import com.bea.staxb.buildtime.internal.logger.MessageSink;
import com.bea.staxb.buildtime.internal.logger.SimpleMessageSink;
import java.io.File;
import java.io.FileWriter;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;

public abstract class BindingCompilerTask extends MatchingTask {
   public BindingCompilerProcessor processor;

   protected BindingCompilerTask(BindingCompilerProcessor processor) {
      this.processor = processor;
      this.processor.setLogger(new AntBuildtimeLogger());
   }

   public void setTypeSystemName(String typeSystemName) {
      this.processor.setTypeSystemName(typeSystemName);
   }

   protected BindingCompilerProcessor getProcessor() {
      return this.processor;
   }

   protected abstract void populateProcessor();

   public void setDestDir(File dir) {
      try {
         this.processor.setDestinationDirectory(dir);
      } catch (DuplicateDestinationException var3) {
         throw new BuildException("You can set only one of destjar and destdir", this.getLocation());
      }
   }

   public void setDestJar(File jar) throws BuildException {
      try {
         this.processor.setDestinationJar(jar);
      } catch (DuplicateDestinationException var3) {
         throw new BuildException("You can set only one of destjar and destdir", this.getLocation());
      }
   }

   public void setVerbose(boolean v) {
      this.processor.setVerbose(v);
   }

   public void setIgnoreErrors(boolean v) {
      this.processor.setIgnoreErrors(v);
   }

   public final void execute() throws BuildException {
      try {
         this.populateProcessor();
         if (this.processor.isVerbose()) {
            FileWriter out = new FileWriter(File.createTempFile("BindingCompilerOptions", ".txt"));
            Throwable var2 = null;

            try {
               out.write(this.processor.toString());
            } catch (Throwable var13) {
               var2 = var13;
               throw var13;
            } finally {
               if (out != null) {
                  if (var2 != null) {
                     try {
                        out.close();
                     } catch (Throwable var12) {
                        var2.addSuppressed(var12);
                     }
                  } else {
                     out.close();
                  }
               }

            }
         }

         this.processor.process(this.createMessageSink(), new AntBuildtimeLogger());
      } catch (BindingCompilerNoDestinationException var15) {
         throw new BuildException("must specify destdir or destjar");
      } catch (Exception var16) {
         throw new BuildException(var16);
      }
   }

   private MessageSink createMessageSink() {
      return new SimpleMessageSink();
   }

   class AntBuildtimeLogger implements BuildtimeLogger {
      public void logDebug(String message) {
         BindingCompilerTask.this.getProject().log(message, 4);
      }

      public boolean isVerbose() {
         return true;
      }

      public void logInfo(String message) {
         BindingCompilerTask.this.getProject().log(message, 2);
      }

      public void logVerbose(String message) {
         BindingCompilerTask.this.getProject().log(message, 3);
      }
   }
}

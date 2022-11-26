package com.bea.core.repackaged.jdt.internal.compiler;

import com.bea.core.repackaged.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.util.Messages;

public class ProcessTaskManager implements Runnable {
   Compiler compiler;
   private int unitIndex;
   private Thread processingThread;
   CompilationUnitDeclaration unitToProcess;
   private Throwable caughtException;
   volatile int currentIndex;
   volatile int availableIndex;
   volatile int size;
   volatile int sleepCount;
   CompilationUnitDeclaration[] units;
   public static final int PROCESSED_QUEUE_SIZE = 12;

   public ProcessTaskManager(Compiler compiler, int startingIndex) {
      this.compiler = compiler;
      this.unitIndex = startingIndex;
      this.currentIndex = 0;
      this.availableIndex = 0;
      this.size = 12;
      this.sleepCount = 0;
      this.units = new CompilationUnitDeclaration[this.size];
      synchronized(this) {
         this.processingThread = new Thread(this, "Compiler Processing Task");
         this.processingThread.setDaemon(true);
         this.processingThread.start();
      }
   }

   private synchronized void addNextUnit(CompilationUnitDeclaration newElement) {
      for(; this.units[this.availableIndex] != null; this.sleepCount = 0) {
         this.sleepCount = 1;

         try {
            this.wait(250L);
         } catch (InterruptedException var2) {
         }
      }

      this.units[this.availableIndex++] = newElement;
      if (this.availableIndex >= this.size) {
         this.availableIndex = 0;
      }

      if (this.sleepCount <= -1) {
         this.notify();
      }

   }

   public CompilationUnitDeclaration removeNextUnit() throws Error {
      CompilationUnitDeclaration next = null;
      boolean yield = false;
      synchronized(this) {
         next = this.units[this.currentIndex];
         if (next == null || this.caughtException != null) {
            do {
               if (this.processingThread == null) {
                  if (this.caughtException != null) {
                     if (this.caughtException instanceof Error) {
                        throw (Error)this.caughtException;
                     }

                     throw (RuntimeException)this.caughtException;
                  }

                  return null;
               }

               this.sleepCount = -1;

               try {
                  this.wait(100L);
               } catch (InterruptedException var4) {
               }

               this.sleepCount = 0;
               next = this.units[this.currentIndex];
            } while(next == null);
         }

         this.units[this.currentIndex++] = null;
         if (this.currentIndex >= this.size) {
            this.currentIndex = 0;
         }

         if (this.sleepCount >= 1 && ++this.sleepCount > 4) {
            this.notify();
            yield = this.sleepCount > 8;
         }
      }

      if (yield) {
         Thread.yield();
      }

      return next;
   }

   public void run() {
      boolean noAnnotations = this.compiler.annotationProcessorManager == null;

      while(this.processingThread != null) {
         this.unitToProcess = null;
         int index = true;
         boolean cleanup = noAnnotations || this.compiler.shouldCleanup(this.unitIndex);

         try {
            int index;
            synchronized(this) {
               if (this.processingThread == null) {
                  return;
               }

               this.unitToProcess = this.compiler.getUnitToProcess(this.unitIndex);
               if (this.unitToProcess == null) {
                  this.processingThread = null;
                  return;
               }

               index = this.unitIndex++;
               if (this.unitToProcess.compilationResult.hasBeenAccepted) {
                  continue;
               }
            }

            try {
               this.compiler.reportProgress(Messages.bind(Messages.compilation_processing, (Object)(new String(this.unitToProcess.getFileName()))));
               if (this.compiler.options.verbose) {
                  this.compiler.out.println(Messages.bind(Messages.compilation_process, (Object[])(new String[]{String.valueOf(index + 1), String.valueOf(this.compiler.totalUnits), new String(this.unitToProcess.getFileName())})));
               }

               this.compiler.process(this.unitToProcess, index);
            } finally {
               if (this.unitToProcess != null && cleanup) {
                  this.unitToProcess.cleanUp();
               }

            }

            this.addNextUnit(this.unitToProcess);
         } catch (Error var16) {
            Error e = var16;
            synchronized(this) {
               this.processingThread = null;
               this.caughtException = e;
               return;
            }
         } catch (RuntimeException var17) {
            RuntimeException e = var17;
            synchronized(this) {
               this.processingThread = null;
               this.caughtException = e;
               return;
            }
         }
      }

   }

   public void shutdown() {
      try {
         Thread t = null;
         synchronized(this) {
            if (this.processingThread != null) {
               t = this.processingThread;
               this.processingThread = null;
               this.notifyAll();
            }
         }

         if (t != null) {
            t.join(250L);
         }
      } catch (InterruptedException var4) {
      }

   }
}

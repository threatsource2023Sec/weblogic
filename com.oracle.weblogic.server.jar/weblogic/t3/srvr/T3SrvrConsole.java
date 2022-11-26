package weblogic.t3.srvr;

import java.io.DataInputStream;
import java.io.IOException;
import weblogic.kernel.T3SrvrLogger;

final class T3SrvrConsole {
   public void processCommands() {
      Runtime rt = Runtime.getRuntime();
      DataInputStream cmds = new DataInputStream(System.in);
      String line = null;

      while(true) {
         while(true) {
            while(true) {
               while(true) {
                  while(true) {
                     do {
                        do {
                           try {
                              line = cmds.readLine();
                           } catch (IOException var5) {
                              T3SrvrLogger.logReadCommandIOException(var5);
                           }
                        } while(line == null);
                     } while(line.trim().length() == 0);

                     T3SrvrLogger.logReadWhichCommand(line);
                     if (!line.equalsIgnoreCase("GC")) {
                        if (!line.equalsIgnoreCase("PROFOFF")) {
                           if (!line.equalsIgnoreCase("PROFON")) {
                              if (line.equalsIgnoreCase("SHUT")) {
                                 try {
                                    T3Srvr.getT3Srvr().requestShutdownFromConsole();
                                    return;
                                 } catch (SecurityException var6) {
                                    T3SrvrLogger.logConsoleShutSecurityException(var6);
                                 }
                              } else if (line.equalsIgnoreCase("KILL")) {
                                 rt.exit(0);
                              } else {
                                 T3SrvrLogger.logConsoleNoSuchCommand(line);
                              }
                           } else {
                              T3SrvrLogger.logConsoleProfilingEnabled();
                           }
                        } else {
                           T3SrvrLogger.logConsoleProfilingDisabled();
                        }
                     } else {
                        this.doGarbageCollection();
                     }
                  }
               }
            }
         }
      }
   }

   private void doGarbageCollection() {
      Runtime rt = Runtime.getRuntime();
      long totMem = rt.totalMemory();
      long freeMem = rt.freeMemory();
      T3SrvrLogger.logConsoleGCBefore(freeMem, totMem, 100L * freeMem / totMem);
      rt.gc();
      rt.runFinalization();
      totMem = rt.totalMemory();
      freeMem = rt.freeMemory();
      T3SrvrLogger.logConsoleGCAfter(freeMem, totMem, 100L * freeMem / totMem);
   }
}

package jnr.posix.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import jnr.posix.POSIXHandler;

public class Java5ProcessMaker implements ProcessMaker {
   private final ProcessBuilder builder;
   private final POSIXHandler handler;

   public Java5ProcessMaker(POSIXHandler handler, String... command) {
      this.handler = handler;
      this.builder = new ProcessBuilder(command);
   }

   public Java5ProcessMaker(POSIXHandler handler) {
      this.handler = handler;
      this.builder = new ProcessBuilder(new String[0]);
   }

   public List command() {
      return this.builder.command();
   }

   public ProcessMaker command(List command) {
      this.builder.command(command);
      return this;
   }

   public ProcessMaker command(String... command) {
      this.builder.command(command);
      return this;
   }

   public File directory() {
      return this.builder.directory();
   }

   public ProcessMaker directory(File dir) {
      this.builder.directory(dir);
      return this;
   }

   public Map environment() {
      return this.builder.environment();
   }

   public ProcessMaker environment(String[] envLines) {
      envIntoProcessBuilder(this.builder, envLines);
      return this;
   }

   public ProcessMaker inheritIO() {
      this.handler.unimplementedError("inheritIO");
      return this;
   }

   public ProcessMaker.Redirect redirectError() {
      return ProcessMaker.Redirect.PIPE;
   }

   public ProcessMaker redirectError(File file) {
      this.handler.unimplementedError("redirectError");
      return this;
   }

   public ProcessMaker redirectError(ProcessMaker.Redirect destination) {
      this.handler.unimplementedError("redirectError");
      return this;
   }

   public boolean redirectErrorStream() {
      return false;
   }

   public ProcessMaker redirectErrorStream(boolean redirectErrorStream) {
      this.handler.unimplementedError("redirectErrorStream");
      return this;
   }

   public ProcessMaker.Redirect redirectInput() {
      return ProcessMaker.Redirect.PIPE;
   }

   public ProcessMaker redirectInput(File file) {
      this.handler.unimplementedError("redirectInput");
      return this;
   }

   public ProcessMaker redirectInput(ProcessMaker.Redirect source) {
      this.handler.unimplementedError("redirectInput");
      return this;
   }

   public ProcessMaker.Redirect redirectOutput() {
      return ProcessMaker.Redirect.PIPE;
   }

   public ProcessMaker redirectOutput(File file) {
      this.handler.unimplementedError("redirectOutput");
      return this;
   }

   public ProcessMaker redirectOutput(ProcessMaker.Redirect destination) {
      this.handler.unimplementedError("redirectOutput");
      return this;
   }

   public Process start() throws IOException {
      return this.builder.start();
   }

   private static void envIntoProcessBuilder(ProcessBuilder pb, String[] env) {
      if (env != null) {
         pb.environment().clear();
         String[] var2 = env;
         int var3 = env.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String envLine = var2[var4];
            if (envLine.indexOf(0) != -1) {
               envLine = envLine.replaceFirst("\u0000.*", "");
            }

            int index = envLine.indexOf(61);
            if (index != -1) {
               pb.environment().put(envLine.substring(0, index), envLine.substring(index + 1));
            }
         }

      }
   }
}

package jnr.posix.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProcessMaker {
   List command();

   ProcessMaker command(List var1);

   ProcessMaker command(String... var1);

   File directory();

   ProcessMaker directory(File var1);

   Map environment();

   ProcessMaker environment(String[] var1);

   ProcessMaker inheritIO();

   Redirect redirectError();

   ProcessMaker redirectError(File var1);

   ProcessMaker redirectError(Redirect var1);

   boolean redirectErrorStream();

   ProcessMaker redirectErrorStream(boolean var1);

   Redirect redirectInput();

   ProcessMaker redirectInput(File var1);

   ProcessMaker redirectInput(Redirect var1);

   Redirect redirectOutput();

   ProcessMaker redirectOutput(File var1);

   ProcessMaker redirectOutput(Redirect var1);

   Process start() throws IOException;

   public static class Redirect {
      public static final Redirect INHERIT;
      public static final Redirect PIPE;
      private final Type type;
      private final File file;

      private Redirect(Type type) {
         this(type, (File)null);
      }

      private Redirect(Type type, File file) {
         this.type = type;
         this.file = file;
      }

      public static Redirect appendTo(File file) {
         return new Redirect(ProcessMaker.Redirect.Type.APPEND, file);
      }

      public static Redirect from(File file) {
         return new Redirect(ProcessMaker.Redirect.Type.READ, file);
      }

      public static Redirect to(File file) {
         return new Redirect(ProcessMaker.Redirect.Type.WRITE, file);
      }

      public File file() {
         return this.file;
      }

      public Type type() {
         return this.type;
      }

      static {
         INHERIT = new Redirect(ProcessMaker.Redirect.Type.INHERIT);
         PIPE = new Redirect(ProcessMaker.Redirect.Type.PIPE);
      }

      private static enum Type {
         APPEND,
         INHERIT,
         PIPE,
         READ,
         WRITE;
      }
   }
}

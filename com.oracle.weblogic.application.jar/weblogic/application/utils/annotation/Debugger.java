package weblogic.application.utils.annotation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import weblogic.utils.compiler.Tool;

public class Debugger extends Tool {
   protected Debugger(String[] args) {
      super(args);
   }

   public static Object readObject(File cacheFile) throws IOException, ClassNotFoundException {
      ObjectInputStream ois = null;

      Object var2;
      try {
         ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(cacheFile)));
         var2 = ois.readObject();
      } finally {
         if (ois != null) {
            ois.close();
         }

      }

      return var2;
   }

   public void prepare() throws Exception {
      this.setRequireExtraArgs(true);
      this.opts.setUsageArgs("<path to the cache file>");
   }

   public void runBody() throws Exception {
      File file = new File(this.opts.args()[0]);
      ClassfinderClassInfos cfci = (ClassfinderClassInfos)readObject(file);
      StringBuilder output = new StringBuilder();
      cfci.debugScanData(output);
      System.out.println(output);
   }

   public static void main(String[] args) throws Exception {
      (new Debugger(args)).run();
   }
}

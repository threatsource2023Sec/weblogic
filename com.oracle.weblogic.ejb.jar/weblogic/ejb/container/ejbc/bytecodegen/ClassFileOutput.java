package weblogic.ejb.container.ejbc.bytecodegen;

import java.io.File;

class ClassFileOutput implements Generator.Output {
   private final String name;
   private final byte[] bytes;

   ClassFileOutput(String clsBinName, byte[] bytes) {
      this.name = clsBinName.replace('/', File.separatorChar) + ".class";
      this.bytes = bytes;
   }

   public String relativeFilePath() {
      return this.name;
   }

   public byte[] bytes() {
      return this.bytes;
   }
}

package weblogic.utils.classfile.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.security.CodeSource;
import weblogic.utils.AssertionError;
import weblogic.utils.classfile.BadBytecodesException;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public abstract class CodeGenerator {
   private static URL codebaseURL;
   private static final GenericClassLoader AUG_CLASSLOADER = AugmentableClassLoaderManager.getAugmentableSystemClassLoader();

   public abstract String getClassName();

   public abstract int write(OutputStream var1) throws IOException, BadBytecodesException;

   public Class generateClass(ClassLoader cl) {
      String name = this.getClassName();

      try {
         byte[] bytes = this.getClassBytes();
         GenericClassLoader gcl = AugmentableClassLoaderManager.getAugmentableClassLoader(cl);
         return gcl.defineCodeGenClass(name, bytes, codebaseURL);
      } catch (ClassNotFoundException var5) {
         throw new AssertionError("Could not find dynamically generated class: '" + name + "'", var5);
      }
   }

   private byte[] getClassBytes() {
      try {
         UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream(8192);
         this.write(baos);
         return baos.toByteArray();
      } catch (IOException var2) {
         throw new AssertionError(var2);
      } catch (BadBytecodesException var3) {
         throw new AssertionError("Could not generate class", var3);
      }
   }

   static {
      CodeSource cs = CodeGenerator.class.getProtectionDomain().getCodeSource();
      if (cs != null) {
         codebaseURL = cs.getLocation();
      } else {
         File lib = new File("lib");

         try {
            codebaseURL = lib.toURL();
         } catch (Exception var4) {
            codebaseURL = null;
         }
      }

      try {
         codebaseURL = new URL(codebaseURL, "CodeGenerator.class");
      } catch (Exception var3) {
      }

   }
}

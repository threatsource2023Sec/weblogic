package com.sun.faces.application;

import com.sun.faces.util.FacesLogger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConverterPropertyEditorFactory {
   private static final Logger LOGGER;
   private static final Pattern UNDERSCORE_PATTERN;
   private static final Pattern SingleUnderscorePattern;
   private static final Pattern MultipleUnderscorePattern;
   private static ConverterPropertyEditorFactory defaultInstance;
   private ClassTemplateInfo templateInfo;
   private Map classLoaderCache;
   private static final Map PRIM_MAP;

   public ConverterPropertyEditorFactory() {
      this.templateInfo = new ClassTemplateInfo();
   }

   public ConverterPropertyEditorFactory(Class templateClass) {
      this.templateInfo = new ClassTemplateInfo(templateClass);
   }

   public static synchronized ConverterPropertyEditorFactory getDefaultInstance() {
      if (defaultInstance == null) {
         defaultInstance = new ConverterPropertyEditorFactory();
      }

      return defaultInstance;
   }

   private ClassTemplateInfo getTemplateInfo() {
      return this.templateInfo;
   }

   public Class definePropertyEditorClassFor(final Class targetClass) {
      try {
         String className = this.getTemplateInfo().generateClassNameFor(targetClass, false);
         if (this.classLoaderCache == null) {
            this.classLoaderCache = new WeakHashMap();
         }

         WeakReference loaderRef = (WeakReference)this.classLoaderCache.get(targetClass.getClassLoader());
         DisposableClassLoader loader;
         if (loaderRef == null || (loader = (DisposableClassLoader)loaderRef.get()) == null) {
            loader = (DisposableClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  return ConverterPropertyEditorFactory.this.new DisposableClassLoader(targetClass.getClassLoader());
               }
            });
            if (loader == null) {
               return null;
            }

            this.classLoaderCache.put(targetClass.getClassLoader(), new WeakReference(loader));
         }

         return loader.loadClass(className);
      } catch (ClassNotFoundException var5) {
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "definePropertyEditorClassFor: ClassNotFoundException: " + var5.getMessage(), var5);
         }

         return null;
      }
   }

   private static String getVMClassName(Class c) {
      return c.getName().replace('.', '/');
   }

   private static byte[] getUtf8InfoBytes(String text) {
      byte[] utf8;
      try {
         utf8 = text.getBytes("UTF-8");
      } catch (UnsupportedEncodingException var3) {
         utf8 = text.getBytes();
      }

      byte[] info = new byte[utf8.length + 3];
      info[0] = 1;
      info[1] = (byte)(utf8.length >> 8 & 255);
      info[2] = (byte)(utf8.length & 255);
      System.arraycopy(utf8, 0, info, 3, utf8.length);
      return info;
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      UNDERSCORE_PATTERN = Pattern.compile("_+");
      SingleUnderscorePattern = Pattern.compile("([^_])_([^_])");
      MultipleUnderscorePattern = Pattern.compile("_(_+)");
      PRIM_MAP = new HashMap(8, 1.0F);
      PRIM_MAP.put('B', "byte");
      PRIM_MAP.put('C', "char");
      PRIM_MAP.put('S', "short");
      PRIM_MAP.put('I', "int");
      PRIM_MAP.put('F', "float");
      PRIM_MAP.put('J', "long");
      PRIM_MAP.put('D', "double");
      PRIM_MAP.put('Z', "boolean");
   }

   private class DisposableClassLoader extends ClassLoader {
      private ClassLoader targetLoader;
      private ClassLoader myLoader;

      public DisposableClassLoader(ClassLoader targetLoader) {
         super(targetLoader);
         this.targetLoader = targetLoader;
         this.myLoader = ConverterPropertyEditorBase.class.getClassLoader();
      }

      protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
         Class clazz = this.findLoadedClass(name);
         if (clazz == null && this.myLoader != null && this.myLoader != this.targetLoader) {
            try {
               clazz = this.myLoader.loadClass(name);
            } catch (ClassNotFoundException var5) {
               if (ConverterPropertyEditorFactory.LOGGER.isLoggable(Level.FINEST)) {
                  ConverterPropertyEditorFactory.LOGGER.log(Level.FINEST, "Ignoring ClassNotFoundException, continuing with parent ClassLoader.", var5);
               }
            }
         }

         if (clazz == null) {
            clazz = super.loadClass(name, false);
         }

         if (resolve) {
            this.resolveClass(clazz);
         }

         return clazz;
      }

      protected Class findClass(String className) throws ClassNotFoundException {
         String targetClassName = ConverterPropertyEditorFactory.this.getTemplateInfo().getTargetClassName(className);
         if (targetClassName != null) {
            byte[] classBytes = ConverterPropertyEditorFactory.this.getTemplateInfo().generateClassBytesFor(className.replace('.', '/'), targetClassName.replace('.', '/'));
            Class editorClass = this.defineClass(className, classBytes, 0, classBytes.length);
            if (ConverterPropertyEditorFactory.LOGGER.isLoggable(Level.FINE)) {
               ConverterPropertyEditorFactory.LOGGER.fine("Defined editorClass " + editorClass);
            }

            return editorClass;
         } else {
            return super.findClass(className);
         }
      }
   }

   private static class ClassTemplateInfo {
      private Class templateClass;
      private byte[] templateBytes;
      private int constant_pool_count;
      private Utf8InfoRef classNameConstant;
      private Utf8InfoRef classNameRefConstant;
      private Utf8InfoRef targetClassConstant;

      public ClassTemplateInfo() {
         this(ConverterPropertyEditorFor_XXXX.class);
      }

      public ClassTemplateInfo(Class templateClass) {
         this.templateClass = templateClass;

         try {
            ConverterPropertyEditorBase tc = (ConverterPropertyEditorBase)templateClass.newInstance();
            Class templateTargetClass = tc.getTargetClass();
            this.loadTemplateBytes();
            this.classNameConstant = this.findConstant(ConverterPropertyEditorFactory.getVMClassName(templateClass));
            this.classNameRefConstant = this.findConstant((new StringBuilder(64)).append('L').append(ConverterPropertyEditorFactory.getVMClassName(templateClass)).append(';').toString());
            this.targetClassConstant = this.findConstant(ConverterPropertyEditorFactory.getVMClassName(templateTargetClass));
         } catch (IllegalAccessException | IOException | InstantiationException var4) {
            if (ConverterPropertyEditorFactory.LOGGER.isLoggable(Level.FINE)) {
               ConverterPropertyEditorFactory.LOGGER.log(Level.FINE, "Unexected exception ClassTemplateInfo", var4);
            }
         }

      }

      private boolean matchAtIndex(byte[] targetBytes, int index) {
         if (index >= 0 && index + targetBytes.length <= this.templateBytes.length) {
            for(int i = 0; i < targetBytes.length; ++i) {
               if (targetBytes[i] != this.templateBytes[index + i]) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }

      private Utf8InfoRef findConstant(String text) {
         byte[] utf8InfoBytes = ConverterPropertyEditorFactory.getUtf8InfoBytes(text);

         assert utf8InfoBytes[0] == 1;

         int off = 10;

         for(int i = 1; i < this.constant_pool_count && off < this.templateBytes.length; ++i) {
            if (this.matchAtIndex(utf8InfoBytes, off)) {
               return new Utf8InfoRef(off, utf8InfoBytes.length);
            }

            switch (this.templateBytes[off]) {
               case 1:
                  int len = (this.templateBytes[off + 1] & '\uff00') + (this.templateBytes[off + 2] & 255);
                  off += 3 + len;
                  break;
               case 2:
               default:
                  throw new IllegalArgumentException("Unrecognized class file constant pool tag " + this.templateBytes[off]);
               case 3:
               case 4:
               case 9:
               case 10:
               case 11:
               case 12:
                  off += 5;
                  break;
               case 5:
               case 6:
                  off += 9;
                  break;
               case 7:
               case 8:
                  off += 3;
            }
         }

         return null;
      }

      private void loadTemplateBytes() throws IOException {
         String resourceName = '/' + this.templateClass.getName().replace('.', '/') + ".class";
         InputStream in = ConverterPropertyEditorFactory.class.getResourceAsStream(resourceName);
         Throwable var3 = null;

         try {
            if (in != null) {
               ByteArrayOutputStream baos = new ByteArrayOutputStream();
               byte[] buff = new byte[1024];

               int more;
               while((more = in.read(buff)) > 0) {
                  baos.write(buff, 0, more);
               }

               this.templateBytes = baos.toByteArray();

               assert this.templateBytes.length > 9;

               assert this.templateBytes[0] == -54;

               assert this.templateBytes[1] == -2;

               assert this.templateBytes[2] == -70;

               assert this.templateBytes[3] == -66;

               this.constant_pool_count = ((this.templateBytes[8] & 255) << 8) + (this.templateBytes[9] & 255);
            }
         } catch (Throwable var14) {
            var3 = var14;
            throw var14;
         } finally {
            if (in != null) {
               if (var3 != null) {
                  try {
                     in.close();
                  } catch (Throwable var13) {
                     var3.addSuppressed(var13);
                  }
               } else {
                  in.close();
               }
            }

         }

      }

      public String generateClassNameFor(Class targetClass, boolean vmFormat) {
         String name = targetClass.getName();
         if (targetClass.isArray()) {
            int idx = name.lastIndexOf(91);
            int bracketCount = idx + 1;
            int semiIdx = name.indexOf(59);
            if (semiIdx == -1) {
               name = (String)ConverterPropertyEditorFactory.PRIM_MAP.get(name.charAt(idx + 1));
            } else {
               name = name.substring(idx + 2, semiIdx);
            }

            name = name + "Array" + bracketCount + 'd';
         }

         Matcher m = ConverterPropertyEditorFactory.UNDERSCORE_PATTERN.matcher(name);
         name = m.replaceAll("$0_");
         name = name.replace('.', '_');
         return vmFormat ? ConverterPropertyEditorFactory.getVMClassName(this.templateClass).replace("XXXX", name) : this.templateClass.getName().replace("XXXX", name);
      }

      public String getTargetClassName(String className) {
         String prefix = this.templateClass.getName().replace("XXXX", "");
         if (className.startsWith(prefix)) {
            String name = className.substring(prefix.length());
            name = ConverterPropertyEditorFactory.SingleUnderscorePattern.matcher(name).replaceAll("$1.$2");
            name = ConverterPropertyEditorFactory.MultipleUnderscorePattern.matcher(name).replaceAll("$1");
            return name;
         } else {
            return null;
         }
      }

      private byte[] replaceInTemplate(Utf8InfoReplacement... replacements) {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         TreeSet sorted = new TreeSet();
         Utf8InfoReplacement[] var4 = replacements;
         int var5 = replacements.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Utf8InfoReplacement r = var4[var6];
            if (r.ref != null && r.replacement != null) {
               sorted.add(r);
            }
         }

         int from = 0;
         Iterator var9 = sorted.iterator();

         while(var9.hasNext()) {
            Utf8InfoReplacement r = (Utf8InfoReplacement)var9.next();
            baos.write(this.templateBytes, from, r.ref.index - from);
            from = r.ref.index + r.ref.length;
            baos.write(r.replacement, 0, r.replacement.length);
         }

         baos.write(this.templateBytes, from, this.templateBytes.length - from);
         return baos.toByteArray();
      }

      public byte[] generateClassBytesFor(String newClassName, String targetClassName) {
         return this.replaceInTemplate(new Utf8InfoReplacement(this.classNameConstant, newClassName), new Utf8InfoReplacement(this.classNameRefConstant, (new StringBuilder(32)).append('L').append(newClassName).append(';').toString()), new Utf8InfoReplacement(this.targetClassConstant, targetClassName));
      }

      private static class Utf8InfoReplacement implements Comparable {
         Utf8InfoRef ref;
         byte[] replacement;

         public Utf8InfoReplacement(Utf8InfoRef ref, String replacement) {
            this.ref = ref;
            this.replacement = ConverterPropertyEditorFactory.getUtf8InfoBytes(replacement);
         }

         public int compareTo(Utf8InfoReplacement rhs) {
            return this.ref.index - rhs.ref.index;
         }
      }

      private static class Utf8InfoRef {
         int index;
         int length;

         public Utf8InfoRef(int index, int length) {
            this.index = index;
            this.length = length;
         }
      }
   }
}

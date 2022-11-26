package weblogic.ejb.container.deployer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashMap;
import java.util.Map;
import weblogic.utils.classloaders.GenericClassLoader;

/** @deprecated */
@Deprecated
public final class RemoteBizIntfClassLoader extends GenericClassLoader {
   private GenericClassLoader ctxCl;
   private RemoteBizIntfClassPreProcessor rbzcpp = null;
   private String remoteBiName = null;
   private String biName = null;
   private Class oldBiClass = null;
   private boolean isStubLoaded = false;
   private Map enhancedClasses = new HashMap();
   private static boolean debug = Boolean.getBoolean("weblogic.ejb.enhancement.debug");

   public RemoteBizIntfClassLoader(String biName, String remoteBiName, GenericClassLoader ctxCl) {
      super(ctxCl);
      this.ctxCl = ctxCl;
      this.remoteBiName = remoteBiName;
      this.biName = biName;
      this.rbzcpp = new RemoteBizIntfClassPreProcessor(remoteBiName, biName);
      this.addInstanceClassPreProcessor(this.rbzcpp);
   }

   public Class loadClass(String name) throws ClassNotFoundException {
      Class c;
      Class clss;
      if (name.indexOf("_WLStub") > 0) {
         c = (Class)this.enhancedClasses.get(name);
         if (c != null) {
            return c;
         } else if (!this.isStubLoaded) {
            this.isStubLoaded = true;
            return this.ctxCl.loadClass(name);
         } else {
            clss = this.findClass(name);
            this.enhancedClasses.put(name, clss);
            this.isStubLoaded = false;
            return clss;
         }
      } else {
         c = this.ctxCl.loadClass(name);
         if (c.getName().equals(this.remoteBiName)) {
            this.debug(c + "is " + this.remoteBiName + ", return it");
            return c;
         } else if (!Remote.class.isAssignableFrom(c) && !Object.class.equals(c)) {
            if (name != null && name.startsWith("java.")) {
               return c;
            } else if (!c.isInterface()) {
               return c;
            } else {
               if (name.equals(this.biName)) {
                  this.oldBiClass = c;
                  this.enhancedClasses.put(this.biName, this.oldBiClass);
               }

               if (this.oldBiClass != null && c.isAssignableFrom(this.oldBiClass)) {
                  clss = (Class)this.enhancedClasses.get(this.remoteBiName);
                  if (clss != null) {
                     this.debug("got " + clss + " from " + this.enhancedClasses);
                     return clss;
                  } else {
                     InputStream stream = null;

                     byte[] bytes;
                     try {
                        stream = this.ctxCl.getResourceAsStream(name.replace('.', '/') + ".class");
                        bytes = this.getClassBytes(stream, name);
                     } finally {
                        try {
                           if (stream != null) {
                              stream.close();
                           }
                        } catch (IOException var13) {
                        }

                     }

                     Class clz;
                     if (name.equals(this.biName)) {
                        try {
                           clz = this.ctxCl.loadClass(this.remoteBiName);
                           this.debug("loaded " + this.remoteBiName + " on " + this.ctxCl);
                           return clz;
                        } catch (ClassNotFoundException var15) {
                           clz = this.enhanceClass(this.biName, this.remoteBiName, bytes);
                        }
                     } else {
                        clz = this.enhanceClass(name, name, bytes);
                     }

                     return clz;
                  }
               } else {
                  this.debug(c + "is not super class of " + this.oldBiClass + ", return it");
                  return c;
               }
            }
         } else {
            return c;
         }
      }
   }

   private synchronized Class enhanceClass(String oldClassName, String newClassName, byte[] bytes) throws ClassNotFoundException {
      Class clz;
      byte[] enBytes;
      try {
         enBytes = this.doPreProcess(bytes, oldClassName);
         if (!oldClassName.equals(newClassName)) {
            clz = (Class)this.enhancedClasses.get("_pseudo_" + newClassName);
            if (clz == null) {
               clz = this.defineClass("_pseudo_" + newClassName, enBytes, 0, enBytes.length);
               this.enhancedClasses.put("_pseudo_" + newClassName, clz);
            }
         } else {
            clz = (Class)this.enhancedClasses.get(newClassName);
            if (clz == null) {
               clz = this.defineClass(newClassName, enBytes, 0, enBytes.length);
               this.enhancedClasses.put(newClassName, clz);
            }
         }

         enBytes = this.rbzcpp.postProcess();
      } catch (Throwable var9) {
         if (debug) {
            var9.printStackTrace();
         }

         throw new ClassNotFoundException(newClassName);
      }

      try {
         if (!oldClassName.equals(newClassName)) {
            synchronized(this.ctxCl) {
               Class var10000;
               try {
                  Class clss = this.ctxCl.loadClass(newClassName);
                  this.debug("loaded " + newClassName + " on " + this.ctxCl);
                  var10000 = clss;
               } catch (ClassNotFoundException var10) {
                  clz = this.ctxCl.defineCodeGenClass(newClassName, enBytes, (URL)null);
                  this.debug("defined " + newClassName + " on " + this.ctxCl);
                  this.enhancedClasses.put(newClassName, clz);
                  return clz;
               }

               return var10000;
            }
         } else {
            return clz;
         }
      } catch (Throwable var12) {
         if (debug) {
            var12.printStackTrace();
         }

         throw new ClassNotFoundException(newClassName);
      }
   }

   private byte[] getClassBytes(InputStream stream, String name) throws ClassNotFoundException {
      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         byte[] buffer = new byte[4096];

         int count;
         while((count = stream.read(buffer)) != -1) {
            baos.write(buffer, 0, count);
         }

         baos.close();
         byte[] array = baos.toByteArray();
         return array;
      } catch (Throwable var6) {
         if (debug) {
            var6.printStackTrace();
         }

         throw new ClassNotFoundException(name);
      }
   }

   private void debug(String log) {
      if (debug) {
         System.out.println("[" + this.getClass().getSimpleName() + "]---" + Thread.currentThread() + "---\n\t" + log);
      }

   }
}

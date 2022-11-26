package org.apache.openjpa.enhance;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Set;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.GeneralException;
import serp.bytecode.Project;
import serp.bytecode.lowlevel.ConstantPoolTable;

public class PCClassFileTransformer implements ClassFileTransformer {
   private static final Localizer _loc = Localizer.forPackage(PCClassFileTransformer.class);
   private final MetaDataRepository _repos;
   private final PCEnhancer.Flags _flags;
   private final ClassLoader _tmpLoader;
   private final Log _log;
   private final Set _names;
   private boolean _transforming;

   public PCClassFileTransformer(MetaDataRepository repos, Options opts, ClassLoader loader) {
      this(repos, toFlags(opts), loader, opts.removeBooleanProperty("scanDevPath", "ScanDevPath", false));
   }

   private static PCEnhancer.Flags toFlags(Options opts) {
      PCEnhancer.Flags flags = new PCEnhancer.Flags();
      flags.addDefaultConstructor = opts.removeBooleanProperty("addDefaultConstructor", "AddDefaultConstructor", flags.addDefaultConstructor);
      flags.enforcePropertyRestrictions = opts.removeBooleanProperty("enforcePropertyRestrictions", "EnforcePropertyRestrictions", flags.enforcePropertyRestrictions);
      return flags;
   }

   public PCClassFileTransformer(MetaDataRepository repos, PCEnhancer.Flags flags, ClassLoader tmpLoader, boolean devscan) {
      this._transforming = false;
      this._repos = repos;
      this._tmpLoader = tmpLoader;
      this._log = repos.getConfiguration().getLog("openjpa.Enhance");
      this._flags = flags;
      this._names = repos.getPersistentTypeNames(devscan, tmpLoader);
      if (this._names == null && this._log.isInfoEnabled()) {
         this._log.info(_loc.get("runtime-enhance-pcclasses"));
      }

   }

   public byte[] transform(ClassLoader loader, String className, Class redef, ProtectionDomain domain, byte[] bytes) throws IllegalClassFormatException {
      if (loader == this._tmpLoader) {
         return null;
      } else if (this._transforming) {
         return null;
      } else {
         this._transforming = true;
         return this.transform0(className, redef, bytes);
      }
   }

   private byte[] transform0(String className, Class redef, byte[] bytes) throws IllegalClassFormatException {
      byte[] var6;
      try {
         Boolean enhance = this.needsEnhance(className, redef, bytes);
         if (enhance != null && this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("needs-runtime-enhance", className, enhance));
         }

         PCEnhancer enhancer;
         if (enhance != Boolean.TRUE) {
            enhancer = null;
            return (byte[])enhancer;
         }

         enhancer = new PCEnhancer(this._repos.getConfiguration(), (new Project()).loadClass(new ByteArrayInputStream(bytes), this._tmpLoader), this._repos);
         enhancer.setAddDefaultConstructor(this._flags.addDefaultConstructor);
         enhancer.setEnforcePropertyRestrictions(this._flags.enforcePropertyRestrictions);
         if (enhancer.run() == 0) {
            Object var13 = null;
            return (byte[])var13;
         }

         var6 = enhancer.getPCBytecode().toByteArray();
      } catch (Throwable var11) {
         this._log.warn(_loc.get("cft-exception-thrown", (Object)className), var11);
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         }

         if (var11 instanceof IllegalClassFormatException) {
            throw (IllegalClassFormatException)var11;
         }

         throw new GeneralException(var11);
      } finally {
         this._transforming = false;
      }

      return var6;
   }

   private Boolean needsEnhance(String clsName, Class redef, byte[] bytes) {
      if (redef != null) {
         Class[] intfs = redef.getInterfaces();

         for(int i = 0; i < intfs.length; ++i) {
            if (PersistenceCapable.class.getName().equals(intfs[i].getName())) {
               return !isEnhanced(bytes);
            }
         }

         return null;
      } else if (this._names != null) {
         return this._names.contains(clsName.replace('/', '.')) ? !isEnhanced(bytes) : null;
      } else if (!clsName.startsWith("java/") && !clsName.startsWith("javax/")) {
         if (isEnhanced(bytes)) {
            return Boolean.FALSE;
         } else {
            try {
               Class c = Class.forName(clsName.replace('/', '.'), false, this._tmpLoader);
               return this._repos.getMetaData((Class)c, (ClassLoader)null, false) != null ? Boolean.TRUE : null;
            } catch (ClassNotFoundException var6) {
               return Boolean.FALSE;
            } catch (LinkageError var7) {
               return Boolean.FALSE;
            } catch (RuntimeException var8) {
               throw var8;
            } catch (Throwable var9) {
               throw new GeneralException(var9);
            }
         }
      } else {
         return null;
      }
   }

   private static boolean isEnhanced(byte[] b) {
      ConstantPoolTable table = new ConstantPoolTable(b);
      int idx = table.getEndIndex();
      idx += 6;
      int ifaces = table.readUnsignedShort(idx);

      for(int i = 0; i < ifaces; ++i) {
         idx += 2;
         int clsEntry = table.readUnsignedShort(idx);
         int utfEntry = table.readUnsignedShort(table.get(clsEntry));
         String name = table.readString(table.get(utfEntry));
         if ("org/apache/openjpa/enhance/PersistenceCapable".equals(name)) {
            return true;
         }
      }

      return false;
   }
}

package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import java.util.Arrays;

public class ModuleInfo extends ClassFileStruct implements IBinaryModule {
   protected int flags;
   protected int requiresCount;
   protected int exportsCount;
   protected int usesCount;
   protected int providesCount;
   protected int opensCount;
   protected char[] name;
   protected char[] version;
   protected ModuleReferenceInfo[] requires;
   protected PackageExportInfo[] exports;
   protected PackageExportInfo[] opens;
   char[][] uses;
   IModule.IService[] provides;
   protected AnnotationInfo[] annotations;
   private long tagBits;

   public boolean isOpen() {
      return (this.flags & 32) != 0;
   }

   public int requiresCount() {
      return this.requiresCount;
   }

   public int exportsCount() {
      return this.exportsCount;
   }

   public int usesCount() {
      return this.usesCount;
   }

   public int providesCount() {
      return this.providesCount;
   }

   public char[] name() {
      return this.name;
   }

   public void setName(char[] name) {
      this.name = name;
   }

   public IModule.IModuleReference[] requires() {
      return this.requires;
   }

   public IModule.IPackageExport[] exports() {
      return this.exports;
   }

   public char[][] uses() {
      return this.uses;
   }

   public IModule.IService[] provides() {
      return this.provides;
   }

   public IModule.IPackageExport[] opens() {
      return this.opens;
   }

   public IBinaryAnnotation[] getAnnotations() {
      return this.annotations;
   }

   public long getTagBits() {
      return this.tagBits;
   }

   protected ModuleInfo(byte[] classFileBytes, int[] offsets, int offset) {
      super(classFileBytes, offsets, offset);
   }

   public static ModuleInfo createModule(byte[] classFileBytes, int[] offsets, int offset) {
      ModuleInfo module = new ModuleInfo(classFileBytes, offsets, 0);
      module.readModuleAttribute(offset + 6);
      return module;
   }

   private void readModuleAttribute(int moduleOffset) {
      int name_index = this.constantPoolOffsets[this.u2At(moduleOffset)];
      int utf8Offset = this.constantPoolOffsets[this.u2At(name_index + 1)];
      this.name = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
      CharOperation.replace(this.name, '/', '.');
      moduleOffset += 2;
      this.flags = this.u2At(moduleOffset);
      moduleOffset += 2;
      int version_index = this.u2At(moduleOffset);
      if (version_index > 0) {
         utf8Offset = this.constantPoolOffsets[version_index];
         this.version = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
      }

      moduleOffset += 2;
      int count = this.u2At(moduleOffset);
      this.requiresCount = count;
      this.requires = new ModuleReferenceInfo[count];
      moduleOffset += 2;

      int i;
      char[] exported;
      for(i = 0; i < count; ++i) {
         name_index = this.constantPoolOffsets[this.u2At(moduleOffset)];
         utf8Offset = this.constantPoolOffsets[this.u2At(name_index + 1)];
         exported = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
         this.requires[i] = new ModuleReferenceInfo();
         CharOperation.replace(exported, '/', '.');
         this.requires[i].refName = exported;
         moduleOffset += 2;
         int modifiers = this.u2At(moduleOffset);
         this.requires[i].modifiers = modifiers;
         this.requires[i].isTransitive = (32 & modifiers) != 0;
         moduleOffset += 2;
         version_index = this.u2At(moduleOffset);
         if (version_index > 0) {
            utf8Offset = this.constantPoolOffsets[version_index];
            this.requires[i].required_version = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
         }

         moduleOffset += 2;
      }

      count = this.u2At(moduleOffset);
      moduleOffset += 2;
      this.exportsCount = count;
      this.exports = new PackageExportInfo[count];

      int exportedtoCount;
      int implCount;
      char[] exportedToName;
      PackageExportInfo pack;
      for(i = 0; i < count; ++i) {
         name_index = this.constantPoolOffsets[this.u2At(moduleOffset)];
         utf8Offset = this.constantPoolOffsets[this.u2At(name_index + 1)];
         exported = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
         CharOperation.replace(exported, '/', '.');
         pack = new PackageExportInfo();
         this.exports[i] = pack;
         pack.packageName = exported;
         moduleOffset += 2;
         pack.modifiers = this.u2At(moduleOffset);
         moduleOffset += 2;
         exportedtoCount = this.u2At(moduleOffset);
         moduleOffset += 2;
         if (exportedtoCount > 0) {
            pack.exportedTo = new char[exportedtoCount][];
            pack.exportedToCount = exportedtoCount;

            for(implCount = 0; implCount < exportedtoCount; ++implCount) {
               name_index = this.constantPoolOffsets[this.u2At(moduleOffset)];
               utf8Offset = this.constantPoolOffsets[this.u2At(name_index + 1)];
               exportedToName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
               CharOperation.replace(exportedToName, '/', '.');
               pack.exportedTo[implCount] = exportedToName;
               moduleOffset += 2;
            }
         }
      }

      count = this.u2At(moduleOffset);
      moduleOffset += 2;
      this.opensCount = count;
      this.opens = new PackageExportInfo[count];

      for(i = 0; i < count; ++i) {
         name_index = this.constantPoolOffsets[this.u2At(moduleOffset)];
         utf8Offset = this.constantPoolOffsets[this.u2At(name_index + 1)];
         exported = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
         CharOperation.replace(exported, '/', '.');
         pack = new PackageExportInfo();
         this.opens[i] = pack;
         pack.packageName = exported;
         moduleOffset += 2;
         pack.modifiers = this.u2At(moduleOffset);
         moduleOffset += 2;
         exportedtoCount = this.u2At(moduleOffset);
         moduleOffset += 2;
         if (exportedtoCount > 0) {
            pack.exportedTo = new char[exportedtoCount][];
            pack.exportedToCount = exportedtoCount;

            for(implCount = 0; implCount < exportedtoCount; ++implCount) {
               name_index = this.constantPoolOffsets[this.u2At(moduleOffset)];
               utf8Offset = this.constantPoolOffsets[this.u2At(name_index + 1)];
               exportedToName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
               CharOperation.replace(exportedToName, '/', '.');
               pack.exportedTo[implCount] = exportedToName;
               moduleOffset += 2;
            }
         }
      }

      count = this.u2At(moduleOffset);
      moduleOffset += 2;
      this.usesCount = count;
      this.uses = new char[count][];

      int classIndex;
      char[] inf;
      for(i = 0; i < count; ++i) {
         classIndex = this.constantPoolOffsets[this.u2At(moduleOffset)];
         utf8Offset = this.constantPoolOffsets[this.u2At(classIndex + 1)];
         inf = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
         CharOperation.replace(inf, '/', '.');
         this.uses[i] = inf;
         moduleOffset += 2;
      }

      count = this.u2At(moduleOffset);
      moduleOffset += 2;
      this.providesCount = count;
      this.provides = new ServiceInfo[count];

      for(i = 0; i < count; ++i) {
         classIndex = this.constantPoolOffsets[this.u2At(moduleOffset)];
         utf8Offset = this.constantPoolOffsets[this.u2At(classIndex + 1)];
         inf = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
         CharOperation.replace(inf, '/', '.');
         ServiceInfo service = new ServiceInfo();
         this.provides[i] = service;
         service.serviceName = inf;
         moduleOffset += 2;
         implCount = this.u2At(moduleOffset);
         moduleOffset += 2;
         service.with = new char[implCount][];
         if (implCount > 0) {
            service.with = new char[implCount][];

            for(int k = 0; k < implCount; ++k) {
               classIndex = this.constantPoolOffsets[this.u2At(moduleOffset)];
               utf8Offset = this.constantPoolOffsets[this.u2At(classIndex + 1)];
               char[] implName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
               CharOperation.replace(implName, '/', '.');
               service.with[k] = implName;
               moduleOffset += 2;
            }
         }
      }

   }

   void setAnnotations(AnnotationInfo[] annotationInfos, long tagBits, boolean fullyInitialize) {
      this.annotations = annotationInfos;
      this.tagBits = tagBits;
      if (fullyInitialize) {
         int i = 0;

         for(int max = annotationInfos.length; i < max; ++i) {
            annotationInfos[i].initialize();
         }
      }

   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof IModule)) {
         return false;
      } else {
         IModule mod = (IModule)o;
         return !CharOperation.equals(this.name, mod.name()) ? false : Arrays.equals(this.requires, mod.requires());
      }
   }

   public int hashCode() {
      int result = 17;
      int c = CharOperation.hashCode(this.name);
      result = 31 * result + c;
      c = Arrays.hashCode(this.requires);
      result = 31 * result + c;
      return result;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer(this.getClass().getName());
      this.toStringContent(buffer);
      return buffer.toString();
   }

   protected void toStringContent(StringBuffer buffer) {
      buffer.append("\nmodule ");
      buffer.append(this.name).append(' ');
      buffer.append('{').append('\n');
      int i;
      if (this.requiresCount > 0) {
         for(i = 0; i < this.requiresCount; ++i) {
            buffer.append("\trequires ");
            if (this.requires[i].isTransitive) {
               buffer.append(" public ");
            }

            buffer.append(this.requires[i].refName);
            buffer.append(';').append('\n');
         }
      }

      if (this.exportsCount > 0) {
         buffer.append('\n');

         for(i = 0; i < this.exportsCount; ++i) {
            buffer.append("\texports ");
            buffer.append(this.exports[i].toString());
         }
      }

      buffer.append('\n').append('}').toString();
   }

   class ModuleReferenceInfo implements IModule.IModuleReference {
      char[] refName;
      boolean isTransitive = false;
      int modifiers;
      char[] required_version;

      public char[] name() {
         return this.refName;
      }

      public boolean isTransitive() {
         return this.isTransitive;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof IModule.IModuleReference)) {
            return false;
         } else {
            IModule.IModuleReference mod = (IModule.IModuleReference)o;
            return this.modifiers != mod.getModifiers() ? false : CharOperation.equals(this.refName, mod.name(), false);
         }
      }

      public int hashCode() {
         return CharOperation.hashCode(this.refName);
      }

      public int getModifiers() {
         return this.modifiers;
      }
   }

   class PackageExportInfo implements IModule.IPackageExport {
      char[] packageName;
      char[][] exportedTo;
      int exportedToCount;
      int modifiers;

      public char[] name() {
         return this.packageName;
      }

      public char[][] targets() {
         return this.exportedTo;
      }

      public String toString() {
         StringBuffer buffer = new StringBuffer();
         this.toStringContent(buffer);
         return buffer.toString();
      }

      protected void toStringContent(StringBuffer buffer) {
         buffer.append(this.packageName);
         if (this.exportedToCount > 0) {
            buffer.append(" to ");

            for(int i = 0; i < this.exportedToCount; ++i) {
               buffer.append(this.exportedTo[i]);
               buffer.append(',').append(' ');
            }
         }

         buffer.append(';').append('\n');
      }
   }

   class ServiceInfo implements IModule.IService {
      char[] serviceName;
      char[][] with;

      public char[] name() {
         return this.serviceName;
      }

      public char[][] with() {
         return this.with;
      }
   }
}

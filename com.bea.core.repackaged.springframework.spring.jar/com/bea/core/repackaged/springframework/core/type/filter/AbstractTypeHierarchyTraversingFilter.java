package com.bea.core.repackaged.springframework.core.type.filter;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.type.ClassMetadata;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReader;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReaderFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.IOException;

public abstract class AbstractTypeHierarchyTraversingFilter implements TypeFilter {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private final boolean considerInherited;
   private final boolean considerInterfaces;

   protected AbstractTypeHierarchyTraversingFilter(boolean considerInherited, boolean considerInterfaces) {
      this.considerInherited = considerInherited;
      this.considerInterfaces = considerInterfaces;
   }

   public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
      if (this.matchSelf(metadataReader)) {
         return true;
      } else {
         ClassMetadata metadata = metadataReader.getClassMetadata();
         if (this.matchClassName(metadata.getClassName())) {
            return true;
         } else {
            if (this.considerInherited) {
               String superClassName = metadata.getSuperClassName();
               if (superClassName != null) {
                  Boolean superClassMatch = this.matchSuperClass(superClassName);
                  if (superClassMatch != null) {
                     if (superClassMatch) {
                        return true;
                     }
                  } else {
                     try {
                        if (this.match(metadata.getSuperClassName(), metadataReaderFactory)) {
                           return true;
                        }
                     } catch (IOException var11) {
                        if (this.logger.isDebugEnabled()) {
                           this.logger.debug("Could not read super class [" + metadata.getSuperClassName() + "] of type-filtered class [" + metadata.getClassName() + "]");
                        }
                     }
                  }
               }
            }

            if (this.considerInterfaces) {
               String[] var12 = metadata.getInterfaceNames();
               int var13 = var12.length;

               for(int var6 = 0; var6 < var13; ++var6) {
                  String ifc = var12[var6];
                  Boolean interfaceMatch = this.matchInterface(ifc);
                  if (interfaceMatch != null) {
                     if (interfaceMatch) {
                        return true;
                     }
                  } else {
                     try {
                        if (this.match(ifc, metadataReaderFactory)) {
                           return true;
                        }
                     } catch (IOException var10) {
                        if (this.logger.isDebugEnabled()) {
                           this.logger.debug("Could not read interface [" + ifc + "] for type-filtered class [" + metadata.getClassName() + "]");
                        }
                     }
                  }
               }
            }

            return false;
         }
      }
   }

   private boolean match(String className, MetadataReaderFactory metadataReaderFactory) throws IOException {
      return this.match(metadataReaderFactory.getMetadataReader(className), metadataReaderFactory);
   }

   protected boolean matchSelf(MetadataReader metadataReader) {
      return false;
   }

   protected boolean matchClassName(String className) {
      return false;
   }

   @Nullable
   protected Boolean matchSuperClass(String superClassName) {
      return null;
   }

   @Nullable
   protected Boolean matchInterface(String interfaceName) {
      return null;
   }
}

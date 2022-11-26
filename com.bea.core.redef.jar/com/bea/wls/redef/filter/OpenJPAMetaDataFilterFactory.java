package com.bea.wls.redef.filter;

import serp.bytecode.lowlevel.ConstantPoolTable;

public class OpenJPAMetaDataFilterFactory implements MetaDataFilterFactory {
   public static final OpenJPAMetaDataFilterFactory OPENJPA_FACTORY = new OpenJPAMetaDataFilterFactory();
   private static final String PERSISTENCE_CAPABLE = "org.apache.openjpa.enhance.PersistenceCapable";
   private static final String PERSISTENCE_CAPABLE_ENTRY = "org.apache.openjpa.enhance.PersistenceCapable".replace('.', '/');
   private static final String[] ENTITY_ANNOTATIONS = new String[]{"Ljavax/persistence/Entity;", "Ljavax/persistence/Embeddable;", "Ljavax/persistence/MappedSuperclass;"};

   private OpenJPAMetaDataFilterFactory() {
   }

   public MetaDataFilter newFilter(String clsName, Class prevCls, MetaDataFilter prev, ConstantPoolTable table, byte[] bytes) {
      int i;
      if (prevCls != null) {
         Class[] ifaces = prevCls.getInterfaces();

         for(i = 0; i < ifaces.length; ++i) {
            if (ifaces[i].getName().equals("org.apache.openjpa.enhance.PersistenceCapable")) {
               return null;
            }
         }
      } else {
         try {
            int idx = table.getEndIndex();
            idx += 2;
            idx += 2;
            idx += 2;
            i = table.readUnsignedShort(idx);
            idx += 2;

            for(int i = 0; i < i; idx += 2) {
               int clsEntry = table.readUnsignedShort(idx);
               int utfEntry = table.readUnsignedShort(table.get(clsEntry));
               if (table.readString(table.get(utfEntry)).equals(PERSISTENCE_CAPABLE_ENTRY)) {
                  return null;
               }

               ++i;
            }

            if (this.isAnnotatedEntity(table, idx)) {
               return null;
            }
         } catch (RuntimeException var11) {
            throw (Error)(new ClassFormatError()).initCause(var11);
         }
      }

      return (MetaDataFilter)(prev != null ? prev : NullMetaDataFilter.NULL_FILTER);
   }

   private boolean isAnnotatedEntity(ConstantPoolTable table, int idx) {
      int fields = table.readUnsignedShort(idx);
      idx += 2;

      int methods;
      for(methods = 0; methods < fields; ++methods) {
         idx += skipFieldOrMethod(table, idx);
      }

      methods = table.readUnsignedShort(idx);
      idx += 2;

      int attrs;
      for(attrs = 0; attrs < methods; ++attrs) {
         idx += skipFieldOrMethod(table, idx);
      }

      attrs = table.readUnsignedShort(idx);
      idx += 2;

      for(int i = 0; i < attrs; ++i) {
         int name = table.readUnsignedShort(idx);
         idx += 2;
         if ("RuntimeVisibleAnnotations".equals(table.readString(table.get(name)))) {
            return this.matchAnnotations(table, idx + 4);
         }

         idx += 4 + table.readInt(idx);
      }

      return false;
   }

   private boolean matchAnnotations(ConstantPoolTable table, int idx) {
      int annos = table.readUnsignedShort(idx);
      idx += 2;

      for(int i = 0; i < annos; ++i) {
         int type = table.readUnsignedShort(idx);
         idx += 2;
         if (this.matchAnnotation(table.readString(table.get(type)))) {
            return true;
         }

         int props = table.readUnsignedShort(idx);
         idx += 2;

         for(int j = 0; j < props; ++j) {
            idx += 2;
            idx += skipAnnotationPropertyValue(table, idx);
         }
      }

      return false;
   }

   private boolean matchAnnotation(String name) {
      for(int i = 0; i < ENTITY_ANNOTATIONS.length; ++i) {
         if (ENTITY_ANNOTATIONS[i].equals(name)) {
            return true;
         }
      }

      return false;
   }

   private static int skipAnnotationPropertyValue(ConstantPoolTable table, int idx) {
      int skipped = 0;
      int i;
      switch (table.readByte(idx + skipped++)) {
         case 64:
            skipped += 2;
            i = table.readUnsignedShort(idx + skipped);
            skipped += 2;

            for(int j = 0; j < i; ++j) {
               skipped += 2;
               skipped += skipAnnotationPropertyValue(table, idx + skipped);
            }
         case 65:
         case 69:
         case 71:
         case 72:
         case 75:
         case 76:
         case 77:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 100:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         default:
            break;
         case 66:
         case 67:
         case 68:
         case 70:
         case 73:
         case 74:
         case 83:
         case 90:
         case 99:
         case 115:
            skipped += 2;
            break;
         case 91:
            int size = table.readUnsignedShort(idx + skipped);
            skipped += 2;

            for(i = 0; i < size; ++i) {
               skipped += skipAnnotationPropertyValue(table, idx + skipped);
            }

            return skipped;
         case 101:
            skipped += 4;
      }

      return skipped;
   }

   private static int skipFieldOrMethod(ConstantPoolTable table, int idx) {
      int attrs = table.readUnsignedShort(idx + 6);
      int skipped = 8;

      for(int i = 0; i < attrs; ++i) {
         int len = table.readInt(idx + skipped + 2);
         skipped += 6 + len;
      }

      return skipped;
   }
}

package weblogic.application.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import serp.bytecode.lowlevel.ConstantPoolTable;

public class ClassAnnotationMetaDataFilter implements MetaDataFilter {
   private final String[] _annos;
   private final boolean multipleMatches;
   private final Map reverseMap;

   public ClassAnnotationMetaDataFilter(Class[] annos) {
      this(annos, false);
   }

   public ClassAnnotationMetaDataFilter(Class[] annos, boolean multipleMatches) {
      this.reverseMap = new HashMap();
      this._annos = new String[annos.length];

      for(int i = 0; i < annos.length; ++i) {
         this._annos[i] = "L" + annos[i].getName().replace('.', '/') + ";";
         this.reverseMap.put(this._annos[i], annos[i]);
      }

      this.multipleMatches = multipleMatches;
   }

   public List matches(MetaDataFilter.Resource rsrc) throws IOException {
      if (this._annos.length != 0 && rsrc.getName().endsWith(".class")) {
         try {
            ConstantPoolTable table = new ConstantPoolTable(rsrc.getContent());
            int idx = table.getEndIndex();
            idx += 2;
            int clsEntry = table.readUnsignedShort(idx);
            int utfEntry = table.readUnsignedShort(table.get(clsEntry));
            String clsName = table.readString(table.get(utfEntry));
            if (!clsName.concat(".class").equals(rsrc.getName().replace("\\", "/"))) {
               return null;
            }

            idx += 4;
            int interfaces = table.readUnsignedShort(idx);
            idx += 2 + interfaces * 2;
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
         } catch (ArrayIndexOutOfBoundsException var13) {
            Error cfe = new ClassFormatError(rsrc.getName());
            cfe.initCause(var13);
         }

         return null;
      } else {
         return null;
      }
   }

   private List matchAnnotations(ConstantPoolTable table, int idx) {
      int annos = table.readUnsignedShort(idx);
      idx += 2;
      List matchList = new ArrayList();

      for(int i = 0; i < annos; ++i) {
         int type = table.readUnsignedShort(idx);
         idx += 2;
         Class match = this.matchAnnotation(table.readString(table.get(type)));
         if (match != null) {
            matchList.add(match);
            if (!this.multipleMatches) {
               return matchList;
            }
         }

         int props = table.readUnsignedShort(idx);
         idx += 2;

         for(int j = 0; j < props; ++j) {
            idx += 2;
            idx += skipAnnotationPropertyValue(table, idx);
         }
      }

      if (this.multipleMatches && !matchList.isEmpty()) {
         return matchList;
      } else {
         return null;
      }
   }

   private Class matchAnnotation(String name) {
      for(int i = 0; i < this._annos.length; ++i) {
         if (name.equals(this._annos[i])) {
            return (Class)this.reverseMap.get(this._annos[i]);
         }
      }

      return null;
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

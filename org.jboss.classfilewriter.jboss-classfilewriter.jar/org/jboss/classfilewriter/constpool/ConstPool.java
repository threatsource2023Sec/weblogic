package org.jboss.classfilewriter.constpool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jboss.classfilewriter.WritableEntry;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class ConstPool implements WritableEntry {
   private final LinkedHashMap entries = new LinkedHashMap();
   private final Map utf8Locations = new HashMap();
   private final Map classLocations = new HashMap();
   private final Map stringLocations = new HashMap();
   private final Map nameAndTypeLocations = new HashMap();
   private final Map fieldLocations = new HashMap();
   private final Map methodLocations = new HashMap();
   private final Map interfaceMethodLocations = new HashMap();
   private final Map integerLocations = new HashMap();
   private final Map floatLocations = new HashMap();
   private final Map longLocations = new HashMap();
   private final Map doubleLocations = new HashMap();
   private int count = 1;
   private Integer constPoolSize = 1;

   public Integer addUtf8Entry(String entry) {
      if (this.utf8Locations.containsKey(entry)) {
         return (Integer)this.utf8Locations.get(entry);
      } else {
         int index = this.count++;
         Integer var3 = this.constPoolSize;
         Integer var4 = this.constPoolSize = this.constPoolSize + 1;
         this.entries.put(index, new Utf8Entry(entry));
         this.utf8Locations.put(entry, index);
         return index;
      }
   }

   public Integer addClassEntry(String className) {
      className = className.replace('.', '/');
      if (this.classLocations.containsKey(className)) {
         return (Integer)this.classLocations.get(className);
      } else {
         Integer utf8Location = this.addUtf8Entry(className);
         Integer index = this.count++;
         Integer var4 = this.constPoolSize;
         Integer var5 = this.constPoolSize = this.constPoolSize + 1;
         this.entries.put(index, new ClassEntry(utf8Location));
         this.classLocations.put(className, index);
         return index;
      }
   }

   public Integer addStringEntry(String string) {
      if (this.stringLocations.containsKey(string)) {
         return (Integer)this.stringLocations.get(string);
      } else {
         Integer utf8Location = this.addUtf8Entry(string);
         Integer index = this.count++;
         Integer var4 = this.constPoolSize;
         Integer var5 = this.constPoolSize = this.constPoolSize + 1;
         this.entries.put(index, new StringEntry(utf8Location));
         this.stringLocations.put(string, index);
         return index;
      }
   }

   public Integer addIntegerEntry(int entry) {
      if (this.integerLocations.containsKey(entry)) {
         return (Integer)this.integerLocations.get(entry);
      } else {
         Integer index = this.count++;
         Integer var3 = this.constPoolSize;
         Integer var4 = this.constPoolSize = this.constPoolSize + 1;
         this.entries.put(index, new IntegerEntry(entry));
         this.integerLocations.put(entry, index);
         return index;
      }
   }

   public Integer addFloatEntry(float entry) {
      if (this.floatLocations.containsKey(entry)) {
         return (Integer)this.floatLocations.get(entry);
      } else {
         Integer index = this.count++;
         Integer var3 = this.constPoolSize;
         Integer var4 = this.constPoolSize = this.constPoolSize + 1;
         this.entries.put(index, new FloatEntry(entry));
         this.floatLocations.put(entry, index);
         return index;
      }
   }

   public Integer addLongEntry(long entry) {
      if (this.longLocations.containsKey(entry)) {
         return (Integer)this.longLocations.get(entry);
      } else {
         Integer index = this.count++;
         ++this.count;
         this.constPoolSize = this.constPoolSize + 2;
         this.entries.put(index, new LongEntry(entry));
         this.longLocations.put(entry, index);
         return index;
      }
   }

   public Integer addDoubleEntry(double entry) {
      if (this.doubleLocations.containsKey(entry)) {
         return (Integer)this.doubleLocations.get(entry);
      } else {
         Integer index = this.count++;
         ++this.count;
         this.constPoolSize = this.constPoolSize + 2;
         this.entries.put(index, new DoubleEntry(entry));
         this.doubleLocations.put(entry, index);
         return index;
      }
   }

   public Integer addNameAndTypeEntry(String name, String type) {
      NameAndType typeInfo = new NameAndType(name, type);
      if (this.nameAndTypeLocations.containsKey(typeInfo)) {
         return (Integer)this.nameAndTypeLocations.get(typeInfo);
      } else {
         Integer nameIndex = this.addUtf8Entry(name);
         Integer typeIndex = this.addUtf8Entry(type);
         Integer index = this.count++;
         Integer var7 = this.constPoolSize;
         Integer var8 = this.constPoolSize = this.constPoolSize + 1;
         this.entries.put(index, new NameAndTypeEntry(nameIndex, typeIndex));
         this.nameAndTypeLocations.put(typeInfo, index);
         return index;
      }
   }

   public Integer addFieldEntry(String className, String fieldName, String fieldType) {
      NameAndType nameAndType = new NameAndType(fieldName, fieldType);
      MemberInfo field = new MemberInfo(className, nameAndType);
      if (this.fieldLocations.containsKey(field)) {
         return (Integer)this.fieldLocations.get(field);
      } else {
         Integer nameAndTypeIndex = this.addNameAndTypeEntry(fieldName, fieldType);
         Integer classIndex = this.addClassEntry(className);
         Integer index = this.count++;
         Integer var9 = this.constPoolSize;
         Integer var10 = this.constPoolSize = this.constPoolSize + 1;
         this.entries.put(index, new FieldRefEntry(classIndex, nameAndTypeIndex));
         this.fieldLocations.put(field, index);
         return index;
      }
   }

   public Integer addMethodEntry(String className, String methodName, String descriptor) {
      NameAndType nameAndType = new NameAndType(methodName, descriptor);
      MemberInfo method = new MemberInfo(className, nameAndType);
      if (this.methodLocations.containsKey(method)) {
         return (Integer)this.methodLocations.get(method);
      } else {
         Integer nameAndTypeIndex = this.addNameAndTypeEntry(methodName, descriptor);
         Integer classIndex = this.addClassEntry(className);
         Integer index = this.count++;
         Integer var9 = this.constPoolSize;
         Integer var10 = this.constPoolSize = this.constPoolSize + 1;
         this.entries.put(index, new MethodRefEntry(classIndex, nameAndTypeIndex));
         this.methodLocations.put(method, index);
         return index;
      }
   }

   public Integer addInterfaceMethodEntry(String className, String methodName, String descriptor) {
      NameAndType nameAndType = new NameAndType(methodName, descriptor);
      MemberInfo method = new MemberInfo(className, nameAndType);
      if (this.interfaceMethodLocations.containsKey(method)) {
         return (Integer)this.interfaceMethodLocations.get(method);
      } else {
         Integer nameAndTypeIndex = this.addNameAndTypeEntry(methodName, descriptor);
         Integer classIndex = this.addClassEntry(className);
         Integer index = this.count++;
         Integer var9 = this.constPoolSize;
         Integer var10 = this.constPoolSize = this.constPoolSize + 1;
         this.entries.put(index, new InterfaceMethodRefEntry(classIndex, nameAndTypeIndex));
         this.interfaceMethodLocations.put(method, index);
         return index;
      }
   }

   public void write(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.constPoolSize);
      Iterator var2 = this.entries.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         ((ConstPoolEntry)entry.getValue()).write(stream);
      }

   }

   private static class MemberInfo {
      private final String className;
      private final NameAndType nameAndType;

      public MemberInfo(String className, NameAndType nameAndType) {
         this.className = className;
         this.nameAndType = nameAndType;
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + (this.className == null ? 0 : this.className.hashCode());
         result = 31 * result + (this.nameAndType == null ? 0 : this.nameAndType.hashCode());
         return result;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            MemberInfo other = (MemberInfo)obj;
            if (this.className == null) {
               if (other.className != null) {
                  return false;
               }
            } else if (!this.className.equals(other.className)) {
               return false;
            }

            if (this.nameAndType == null) {
               if (other.nameAndType != null) {
                  return false;
               }
            } else if (!this.nameAndType.equals(other.nameAndType)) {
               return false;
            }

            return true;
         }
      }
   }

   private static final class NameAndType {
      private final String name;
      private final String type;

      public NameAndType(String name, String type) {
         this.name = name;
         this.type = type;
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
         result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
         return result;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            NameAndType other = (NameAndType)obj;
            if (this.name == null) {
               if (other.name != null) {
                  return false;
               }
            } else if (!this.name.equals(other.name)) {
               return false;
            }

            if (this.type == null) {
               if (other.type != null) {
                  return false;
               }
            } else if (!this.type.equals(other.type)) {
               return false;
            }

            return true;
         }
      }
   }
}

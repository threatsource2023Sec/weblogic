package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Field;
import serp.bytecode.lowlevel.ComplexEntry;
import serp.util.Strings;

public abstract class FieldInstruction extends Instruction {
   private int _index = 0;

   FieldInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   int getLength() {
      return super.getLength() + 2;
   }

   public int getFieldIndex() {
      return this._index;
   }

   public FieldInstruction setFieldIndex(int index) {
      this._index = index;
      return this;
   }

   public BCField getField() {
      String dec = this.getFieldDeclarerName();
      if (dec == null) {
         return null;
      } else {
         BCClass bc = this.getProject().loadClass(dec, this.getClassLoader());
         BCField[] fields = bc.getFields(this.getFieldName());
         return fields.length == 0 ? null : fields[0];
      }
   }

   public FieldInstruction setField(BCField field) {
      return field == null ? this.setFieldIndex(0) : this.setField(field.getDeclarer().getName(), field.getName(), field.getTypeName());
   }

   public FieldInstruction setField(Field field) {
      return field == null ? this.setFieldIndex(0) : this.setField(field.getDeclaringClass(), field.getName(), field.getType());
   }

   public FieldInstruction setField(String dec, String name, String type) {
      if (dec == null && name == null && type == null) {
         return this.setFieldIndex(0);
      } else {
         if (dec == null) {
            dec = "";
         }

         if (name == null) {
            name = "";
         }

         if (type == null) {
            type = "";
         }

         dec = this.getProject().getNameCache().getInternalForm(dec, false);
         type = this.getProject().getNameCache().getInternalForm(type, true);
         return this.setFieldIndex(this.getPool().findFieldEntry(dec, name, type, true));
      }
   }

   public FieldInstruction setField(String name, String type) {
      BCClass owner = this.getCode().getMethod().getDeclarer();
      return this.setField(owner.getName(), name, type);
   }

   public FieldInstruction setField(Class dec, String name, Class type) {
      String decName = dec == null ? null : dec.getName();
      String typeName = type == null ? null : type.getName();
      return this.setField(decName, name, typeName);
   }

   public FieldInstruction setField(String name, Class type) {
      BCClass owner = this.getCode().getMethod().getDeclarer();
      String typeName = type == null ? null : type.getName();
      return this.setField(owner.getName(), name, typeName);
   }

   public FieldInstruction setField(BCClass dec, String name, BCClass type) {
      String decName = dec == null ? null : dec.getName();
      String typeName = type == null ? null : type.getName();
      return this.setField(decName, name, typeName);
   }

   public FieldInstruction setField(String name, BCClass type) {
      BCClass owner = this.getCode().getMethod().getDeclarer();
      String typeName = type == null ? null : type.getName();
      return this.setField(owner.getName(), name, typeName);
   }

   public String getFieldName() {
      int index = this.getFieldIndex();
      if (index == 0) {
         return null;
      } else {
         ComplexEntry entry = (ComplexEntry)this.getPool().getEntry(index);
         String name = entry.getNameAndTypeEntry().getNameEntry().getValue();
         return name.length() == 0 ? null : name;
      }
   }

   public FieldInstruction setFieldName(String name) {
      return this.setField(this.getFieldDeclarerName(), name, this.getFieldTypeName());
   }

   public String getFieldTypeName() {
      int index = this.getFieldIndex();
      if (index == 0) {
         return null;
      } else {
         ComplexEntry entry = (ComplexEntry)this.getPool().getEntry(index);
         String name = this.getProject().getNameCache().getExternalForm(entry.getNameAndTypeEntry().getDescriptorEntry().getValue(), false);
         return name.length() == 0 ? null : name;
      }
   }

   public Class getFieldType() {
      String type = this.getFieldTypeName();
      return type == null ? null : Strings.toClass(type, this.getClassLoader());
   }

   public BCClass getFieldTypeBC() {
      String type = this.getFieldTypeName();
      return type == null ? null : this.getProject().loadClass(type, this.getClassLoader());
   }

   public FieldInstruction setFieldType(String type) {
      return this.setField(this.getFieldDeclarerName(), this.getFieldName(), type);
   }

   public FieldInstruction setFieldType(Class type) {
      String name = null;
      if (type != null) {
         name = type.getName();
      }

      return this.setFieldType(name);
   }

   public FieldInstruction setFieldType(BCClass type) {
      String name = null;
      if (type != null) {
         name = type.getName();
      }

      return this.setFieldType(name);
   }

   public String getFieldDeclarerName() {
      int index = this.getFieldIndex();
      if (index == 0) {
         return null;
      } else {
         ComplexEntry entry = (ComplexEntry)this.getPool().getEntry(index);
         String name = this.getProject().getNameCache().getExternalForm(entry.getClassEntry().getNameEntry().getValue(), false);
         return name.length() == 0 ? null : name;
      }
   }

   public Class getFieldDeclarerType() {
      String type = this.getFieldDeclarerName();
      return type == null ? null : Strings.toClass(type, this.getClassLoader());
   }

   public BCClass getFieldDeclarerBC() {
      String type = this.getFieldDeclarerName();
      return type == null ? null : this.getProject().loadClass(type, this.getClassLoader());
   }

   public FieldInstruction setFieldDeclarer(String type) {
      return this.setField(type, this.getFieldName(), this.getFieldTypeName());
   }

   public FieldInstruction setFieldDeclarer(Class type) {
      String name = null;
      if (type != null) {
         name = type.getName();
      }

      return this.setFieldDeclarer(name);
   }

   public FieldInstruction setFieldDeclarer(BCClass type) {
      String name = null;
      if (type != null) {
         name = type.getName();
      }

      return this.setFieldDeclarer(name);
   }

   public boolean equalsInstruction(Instruction other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof FieldInstruction)) {
         return false;
      } else if (!super.equalsInstruction(other)) {
         return false;
      } else {
         FieldInstruction ins = (FieldInstruction)other;
         String s1 = this.getFieldName();
         String s2 = ins.getFieldName();
         if (s1 != null && s2 != null && !s1.equals(s2)) {
            return false;
         } else {
            s1 = this.getFieldTypeName();
            s2 = ins.getFieldTypeName();
            if (s1 != null && s2 != null && !s1.equals(s2)) {
               return false;
            } else {
               s1 = this.getFieldDeclarerName();
               s2 = ins.getFieldDeclarerName();
               return s1 == null || s2 == null || s1.equals(s2);
            }
         }
      }
   }

   void read(Instruction orig) {
      super.read(orig);
      FieldInstruction ins = (FieldInstruction)orig;
      this.setField(ins.getFieldDeclarerName(), ins.getFieldName(), ins.getFieldTypeName());
   }

   void read(DataInput in) throws IOException {
      super.read(in);
      this.setFieldIndex(in.readUnsignedShort());
   }

   void write(DataOutput out) throws IOException {
      super.write(out);
      out.writeShort(this.getFieldIndex());
   }
}

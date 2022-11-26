package weblogic.utils.classfile;

public class FieldInfo extends ClassMember {
   public FieldInfo(ClassFile classFile) {
      super(classFile);
   }

   public FieldInfo(ClassFile classFile, String fieldName, String fieldDescriptor, int modifiers) {
      super(classFile, fieldName, fieldDescriptor, modifiers);
   }

   public String getType() {
      return "field";
   }
}

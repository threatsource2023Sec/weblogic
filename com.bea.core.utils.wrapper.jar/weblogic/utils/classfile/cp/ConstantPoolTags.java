package weblogic.utils.classfile.cp;

public interface ConstantPoolTags {
   int CONSTANT_Class = 7;
   int CONSTANT_Fieldref = 9;
   int CONSTANT_Methodref = 10;
   int CONSTANT_InterfaceMethodref = 11;
   int CONSTANT_String = 8;
   int CONSTANT_Integer = 3;
   int CONSTANT_Float = 4;
   int CONSTANT_Long = 5;
   int CONSTANT_Double = 6;
   int CONSTANT_NameAndType = 12;
   int CONSTANT_Utf8 = 1;
   String[] name = new String[]{"", "CONSTANT_Utf8", "", "CONSTANT_Integer", "CONSTANT_Float", "CONSTANT_Long", "CONSTANT_Double", "CONSTANT_Class", "CONSTANT_String", "CONSTANT_Fieldref", "CONSTANT_Methodref", "CONSTANT_InterfaceMethodref", "CONSTANT_NameAndType"};
}

package weblogic.corba.rmic;

public interface TypeTraits {
   Class getValidClass(Class var1, Class var2);

   IDLType createType(Class var1, Class var2);
}

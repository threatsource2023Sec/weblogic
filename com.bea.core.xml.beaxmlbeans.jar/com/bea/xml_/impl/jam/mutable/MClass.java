package com.bea.xml_.impl.jam.mutable;

import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.JMethod;
import com.bea.xml_.impl.jam.JProperty;

public interface MClass extends MMember, JClass {
   void setIsInterface(boolean var1);

   void setIsAnnotationType(boolean var1);

   void setIsEnumType(boolean var1);

   void setSuperclass(String var1);

   void setSuperclassUnqualified(String var1);

   void setSuperclass(JClass var1);

   void addInterface(String var1);

   void addInterfaceUnqualified(String var1);

   void addInterface(JClass var1);

   void removeInterface(String var1);

   void removeInterface(JClass var1);

   MConstructor addNewConstructor();

   void removeConstructor(MConstructor var1);

   MConstructor[] getMutableConstructors();

   MField addNewField();

   void removeField(MField var1);

   MField[] getMutableFields();

   MMethod addNewMethod();

   void removeMethod(MMethod var1);

   MMethod[] getMutableMethods();

   JProperty addNewProperty(String var1, JMethod var2, JMethod var3);

   void removeProperty(JProperty var1);

   JProperty addNewDeclaredProperty(String var1, JMethod var2, JMethod var3);

   void removeDeclaredProperty(JProperty var1);

   MClass addNewInnerClass(String var1);

   void removeInnerClass(MClass var1);
}

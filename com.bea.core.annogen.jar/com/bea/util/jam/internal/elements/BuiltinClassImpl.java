package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JConstructor;
import com.bea.util.jam.JField;
import com.bea.util.jam.JImport;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JPackage;
import com.bea.util.jam.JProperty;
import com.bea.util.jam.JSourcePosition;
import com.bea.util.jam.mutable.MClass;
import com.bea.util.jam.mutable.MConstructor;
import com.bea.util.jam.mutable.MField;
import com.bea.util.jam.mutable.MMethod;
import com.bea.util.jam.visitor.JVisitor;
import com.bea.util.jam.visitor.MVisitor;

public abstract class BuiltinClassImpl extends AnnotatedElementImpl implements MClass {
   protected BuiltinClassImpl(ElementContext ctx) {
      super(ctx);
   }

   public void accept(MVisitor visitor) {
      visitor.visit((MClass)this);
   }

   public void accept(JVisitor visitor) {
      visitor.visit((JClass)this);
   }

   public String getQualifiedName() {
      return this.mSimpleName;
   }

   public String getFieldDescriptor() {
      return this.mSimpleName;
   }

   public int getModifiers() {
      return Object.class.getModifiers();
   }

   public boolean isPublic() {
      return true;
   }

   public boolean isPackagePrivate() {
      return false;
   }

   public boolean isProtected() {
      return false;
   }

   public boolean isPrivate() {
      return false;
   }

   public JSourcePosition getSourcePosition() {
      return null;
   }

   public JClass getContainingClass() {
      return null;
   }

   public JClass forName(String fd) {
      return this.getClassLoader().loadClass(fd);
   }

   public JClass getArrayComponentType() {
      return null;
   }

   public int getArrayDimensions() {
      return 0;
   }

   public JClass getSuperclass() {
      return null;
   }

   public JClass[] getInterfaces() {
      return NO_CLASS;
   }

   public JField[] getFields() {
      return NO_FIELD;
   }

   public JField[] getDeclaredFields() {
      return NO_FIELD;
   }

   public JConstructor[] getConstructors() {
      return NO_CONSTRUCTOR;
   }

   public JMethod[] getMethods() {
      return NO_METHOD;
   }

   public JMethod[] getDeclaredMethods() {
      return NO_METHOD;
   }

   public JPackage getContainingPackage() {
      return null;
   }

   public boolean isInterface() {
      return false;
   }

   public boolean isArrayType() {
      return false;
   }

   public boolean isAnnotationType() {
      return false;
   }

   public boolean isPrimitiveType() {
      return false;
   }

   public boolean isBuiltinType() {
      return true;
   }

   public boolean isUnresolvedType() {
      return false;
   }

   public boolean isObjectType() {
      return false;
   }

   public boolean isVoidType() {
      return false;
   }

   public boolean isEnumType() {
      return false;
   }

   public Class getPrimitiveClass() {
      return null;
   }

   public boolean isAbstract() {
      return false;
   }

   public boolean isFinal() {
      return false;
   }

   public boolean isStatic() {
      return false;
   }

   public JClass[] getClasses() {
      return NO_CLASS;
   }

   public JProperty[] getProperties() {
      return NO_PROPERTY;
   }

   public JProperty[] getDeclaredProperties() {
      return NO_PROPERTY;
   }

   public JPackage[] getImportedPackages() {
      return NO_PACKAGE;
   }

   public JPackage[] getImportedStarPackages() {
      return NO_PACKAGE;
   }

   public JClass[] getImportedClasses() {
      return NO_CLASS;
   }

   public JImport[] getImports() {
      return NO_IMPORT;
   }

   public MField[] getMutableFields() {
      return NO_FIELD;
   }

   public MConstructor[] getMutableConstructors() {
      return NO_CONSTRUCTOR;
   }

   public MMethod[] getMutableMethods() {
      return NO_METHOD;
   }

   public void setSimpleName(String s) {
      this.nocando();
   }

   public void setIsAnnotationType(boolean b) {
      this.nocando();
   }

   public void setIsInterface(boolean b) {
      this.nocando();
   }

   public void setIsUnresolvedType(boolean b) {
      this.nocando();
   }

   public void setIsEnumType(boolean b) {
      this.nocando();
   }

   public void setSuperclass(String qualifiedClassName) {
      this.nocando();
   }

   public void setSuperclassUnqualified(String unqualifiedClassName) {
      this.nocando();
   }

   public void setSuperclass(JClass clazz) {
      this.nocando();
   }

   public void addInterface(String className) {
      this.nocando();
   }

   public void addInterfaceUnqualified(String unqualifiedClassName) {
      this.nocando();
   }

   public void addInterface(JClass interf) {
      this.nocando();
   }

   public void removeInterface(String className) {
      this.nocando();
   }

   public void removeInterface(JClass interf) {
      this.nocando();
   }

   public MConstructor addNewConstructor() {
      this.nocando();
      return null;
   }

   public void removeConstructor(MConstructor constr) {
      this.nocando();
   }

   public MField addNewField() {
      this.nocando();
      return null;
   }

   public void removeField(MField field) {
      this.nocando();
   }

   public MMethod addNewMethod() {
      this.nocando();
      return null;
   }

   public void removeMethod(MMethod method) {
      this.nocando();
   }

   public void setModifiers(int modifiers) {
      this.nocando();
   }

   public MClass addNewInnerClass(String named) {
      this.nocando();
      return null;
   }

   public void removeInnerClass(MClass inner) {
      this.nocando();
   }

   public JProperty addNewProperty(String name, JMethod m, JMethod x) {
      this.nocando();
      return null;
   }

   public void removeProperty(JProperty prop) {
      this.nocando();
   }

   public JProperty addNewDeclaredProperty(String name, JMethod m, JMethod x) {
      this.nocando();
      return null;
   }

   public void removeDeclaredProperty(JProperty prop) {
      this.nocando();
   }

   public void setSourceAvailable(boolean b) {
      this.nocando();
   }

   public boolean isSourceAvailable() {
      return false;
   }

   public boolean equals(Object o) {
      return o instanceof JClass ? ((JClass)o).getFieldDescriptor().equals(this.getFieldDescriptor()) : false;
   }

   public int hashCode() {
      return this.getFieldDescriptor().hashCode();
   }

   protected void reallySetSimpleName(String name) {
      super.setSimpleName(name);
   }

   private void nocando() {
      throw new UnsupportedOperationException("Cannot alter builtin types");
   }
}

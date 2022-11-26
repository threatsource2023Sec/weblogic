package weblogic.corba.rmic;

import java.io.Externalizable;
import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.Hashtable;
import weblogic.corba.utils.ClassInfo;
import weblogic.iiop.IDLUtils;
import weblogic.utils.compiler.CodeGenerationException;

public class IDLTypeValueType extends IDLType {
   private boolean opaque = false;
   private boolean custom = false;
   public static final TypeTraits TRAITS = new TypeTraits() {
      public Class getValidClass(Class c, Class ec) {
         return IDLUtils.isConcreteValueType(c) ? c : null;
      }

      public IDLType createType(Class c, Class ec) {
         return new IDLTypeValueType(c, ec);
      }
   };

   public IDLTypeValueType(Class c, Class ec) {
      super(c, ec);
      if (Externalizable.class.isAssignableFrom(c)) {
         this.opaque = true;
      } else {
         this.custom = ClassInfo.findClassInfo(c).getDescriptor().isCustomMarshaled();
      }

   }

   public String getIncludeDeclaration() throws CodeGenerationException {
      String result = "";
      if (!IDLUtils.isException(this.getJavaClass()) || this.getEnclosingClass() != null && this.getEnclosingClass().isArray()) {
         result = IDLUtils.generateInclude(this.getDirectory(), this.getJavaClass());
      }

      return result;
   }

   public String getForwardDeclaration() throws CodeGenerationException {
      return this.getForwardDeclaration(true);
   }

   public String getForwardDeclaration(boolean guard) throws CodeGenerationException {
      StringBuilder result = new StringBuilder();
      Class c = this.getJavaClass();
      if (IDLUtils.isException(c) && (this.getEnclosingClass() == null || !this.getEnclosingClass().isArray())) {
         return IDLUtils.generateInclude(this.getDirectory(), c);
      } else {
         if (this.isRequired()) {
            if (guard) {
               result.append(IDLUtils.generateGuard(c, "#ifndef"));
            }

            result.append(IDLUtils.openModule(c));
            result.append("  ").append(this.getOpeningKeyword(true)).append(" ").append(IDLUtils.stripPackage(IDLUtils.getIDLType(this.m_class, "."))).append(";\n");
            result.append(IDLUtils.closeModule(c));
            if (guard) {
               result.append(IDLUtils.generateGuard(c, "#endif //"));
            }
         }

         return result.toString();
      }
   }

   private String getOpeningKeyword(boolean forward) {
      return (this.opaque || this.custom) && !forward ? "custom valuetype" : "valuetype";
   }

   public String getOpeningDeclaration() throws CodeGenerationException {
      Class ec = this.getEnclosingClass();
      return IDLType.getOpeningDecl(this, ec, this.getInheritedClasses(), this.getOpeningKeyword(false), TRAITS);
   }

   public void getReferences(Hashtable hResult) {
      Class c = this.getJavaClass();
      getAll(c, hResult, false, false);
   }

   public Hashtable getAttributes() {
      Hashtable attr = super.getAttributes();
      if (this.opaque) {
         Enumeration e = attr.elements();

         while(e.hasMoreElements()) {
            IDLField a = (IDLField)e.nextElement();
            if (!a.isPublic()) {
               attr.remove(a.getMangledName());
            }
         }
      }

      return attr;
   }

   public Hashtable getMethods() {
      Hashtable result = new Hashtable();
      if (IDLOptions.getFactories()) {
         Constructor[] ctors = this.getJavaClass().getConstructors();
         if (ctors.length <= 1) {
            Class[] params = ctors.length == 0 ? new Class[0] : ctors[0].getParameterTypes();
            IDLMethod m = new IDLMethod(this.getJavaClass(), this.getJavaClass().getName(), "factory create", (Class)null, params, new Class[0]);
            result.put(m.getMangledName(), m);
         } else {
            Constructor[] var9 = ctors;
            int var10 = ctors.length;

            for(int var5 = 0; var5 < var10; ++var5) {
               Constructor ctor = var9[var5];
               String methodName = "factory " + IDLMangler.convertOverloadedName("create", ctor.getParameterTypes());
               IDLMethod m = new IDLMethod(this.getJavaClass(), this.getJavaClass().getName(), methodName, (Class)null, ctor.getParameterTypes(), new Class[0]);
               result.put(m.getMangledName(), m);
            }
         }
      }

      return result;
   }
}

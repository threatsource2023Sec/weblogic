package weblogic.corba.rmic;

import java.util.Hashtable;
import weblogic.corba.utils.CorbaUtils;
import weblogic.iiop.IDLUtils;
import weblogic.utils.compiler.CodeGenerationException;

public class IDLTypeRemote extends IDLType {
   public static final TypeTraits TRAITS = new TypeTraits() {
      public Class getValidClass(Class c, Class ec) {
         Class result = null;
         if (CorbaUtils.isARemote(c)) {
            result = c;
         }

         return result;
      }

      public IDLType createType(Class c, Class ec) {
         return new IDLTypeRemote(c, ec);
      }
   };
   public static final String OPENING_DECLARATION = "interface";

   public String getOpeningDeclarationString() {
      return "interface";
   }

   public IDLTypeRemote(Class c, Class ec) {
      super(c, ec);
   }

   public String getIncludeDeclaration() throws CodeGenerationException {
      return IDLUtils.generateInclude(this.getDirectory(), this.getJavaClass());
   }

   public String getForwardDeclaration() throws CodeGenerationException {
      StringBuilder result = new StringBuilder();
      Class c = this.getJavaClass();
      result.append(IDLUtils.openModule(c));
      result.append(this.getOpeningDeclarationString()).append(" ").append(IDLUtils.stripPackage(IDLUtils.getIDLType(this.m_class, "."))).append(";\n");
      result.append(IDLUtils.closeModule(c));
      return result.toString();
   }

   public String getOpeningDeclaration() throws CodeGenerationException {
      Class ec = this.getEnclosingClass();
      return IDLType.getOpeningDecl(this, ec, this.getInheritedClasses(), "interface", TRAITS);
   }

   public void getReferences(Hashtable hResult) {
      Class c = this.getJavaClass();
      getAll(c, hResult, false);
   }
}

package weblogic.corba.rmic;

import java.util.Hashtable;
import weblogic.iiop.IDLUtils;
import weblogic.utils.compiler.CodeGenerationException;

public class IDLTypeNonConformant extends IDLType {
   public static final String OPENING_DECLARATION = "abstract valuetype";
   public static final TypeTraits TRAITS = new TypeTraits() {
      public Class getValidClass(Class c, Class ec) {
         return IDLUtils.getNonConformantType(c);
      }

      public IDLType createType(Class c, Class ec) {
         return new IDLTypeNonConformant(c, ec);
      }
   };

   public IDLTypeNonConformant(Class c, Class ec) {
      super(c, ec);
   }

   public String getIncludeDeclaration() throws CodeGenerationException {
      return IDLUtils.generateInclude(this.getDirectory(), this.getJavaClass());
   }

   public String getForwardDeclaration() throws CodeGenerationException {
      StringBuffer result = new StringBuffer("");
      if (!IDLOptions.getNoValueTypes()) {
         Class c = this.getJavaClass();
         result.append(IDLUtils.generateGuard(c, "#ifndef"));
         result.append(IDLUtils.openModule(c));
         result.append("  abstract valuetype " + IDLUtils.stripPackage(IDLUtils.getIDLType(c, "."))).append(";\n");
         result.append(IDLUtils.closeModule(c));
         result.append(IDLUtils.generateGuard(c, "#endif //"));
      }

      return result.toString();
   }

   public String getOpeningDeclaration() throws CodeGenerationException {
      Class ec = this.getEnclosingClass();
      String result = IDLType.getOpeningDecl(this, ec, this.getInheritedClasses(), "abstract valuetype", TRAITS);
      return result;
   }

   public Hashtable getMethods() {
      return new Hashtable();
   }

   public Hashtable getAttributes() {
      return new Hashtable();
   }

   public void getReferences(Hashtable hResult) {
      Class c = this.getJavaClass();
      getAll(c, hResult, false, false);
   }
}

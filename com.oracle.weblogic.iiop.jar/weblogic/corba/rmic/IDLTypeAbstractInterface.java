package weblogic.corba.rmic;

import weblogic.corba.utils.CorbaUtils;
import weblogic.utils.compiler.CodeGenerationException;

public class IDLTypeAbstractInterface extends IDLTypeRemote {
   public static final String OPENING_DECLARATION = "abstract interface";
   public static final TypeTraits TRAITS = new TypeTraits() {
      public Class getValidClass(Class c, Class ec) {
         return CorbaUtils.isAbstractInterface(c) ? c : null;
      }

      public IDLType createType(Class c, Class ec) {
         return new IDLTypeAbstractInterface(c, ec);
      }
   };

   public String getOpeningDeclarationString() {
      return "abstract interface";
   }

   public IDLTypeAbstractInterface(Class c, Class ec) {
      super(c, ec);
   }

   public String getIncludeDeclaration() throws CodeGenerationException {
      return !IDLOptions.getNoAbstract() ? super.getIncludeDeclaration() : "// abstract interface " + this.getClassName() + " omitted.\n";
   }

   public String getForwardDeclaration() throws CodeGenerationException {
      return !IDLOptions.getNoAbstract() ? super.getForwardDeclaration() : "// abstract interface " + this.getClassName() + " omitted.\n";
   }

   public String getInheritKeyword(IDLType subtype) {
      return IDLTypeValueType.class.isAssignableFrom(subtype.getClass()) ? "supports" : super.getInheritKeyword(subtype);
   }

   public String getOpeningDeclaration() throws CodeGenerationException {
      String result = "";
      Class ec = this.getEnclosingClass();
      result = IDLType.getOpeningDecl(this, ec, this.getInheritedClasses(), "abstract interface", TRAITS);
      return result;
   }
}

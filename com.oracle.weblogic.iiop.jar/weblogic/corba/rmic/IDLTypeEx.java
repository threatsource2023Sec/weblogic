package weblogic.corba.rmic;

import java.util.Hashtable;
import weblogic.corba.utils.CorbaUtils;
import weblogic.iiop.IDLUtils;
import weblogic.utils.compiler.CodeGenerationException;

public class IDLTypeEx extends IDLType {
   String m_exName = null;
   String m_exceptionName = null;
   Class m_class = null;
   String m_guard = null;
   IDLTypeValueType m_enclosed;
   public static final TypeTraits TRAITS = new TypeTraits() {
      public Class getValidClass(Class c, Class ec) {
         Class result = null;
         if (CorbaUtils.isValid(c) && IDLUtils.isException(c)) {
            result = c;
         }

         return result;
      }

      public IDLType createType(Class c, Class ec) {
         IDLTypeValueType enclosed = new IDLTypeValueType(c, ec);
         IDLType.m_usedTypes.put(enclosed.getFileName(), enclosed);
         return new IDLTypeEx(c, ec, enclosed);
      }
   };
   private static final String IDL_EXT = ".idl";

   public IDLTypeEx(Class c, Class ec, IDLTypeValueType enclosed) {
      super(c, ec);
      this.m_class = this.getJavaClass();
      this.m_exceptionName = IDLUtils.stripPackage(IDLUtils.getIDLType(this.m_class), "::");
      this.m_exName = IDLUtils.exceptionToEx(this.m_exceptionName);
      this.m_guard = IDLUtils.exceptionToEx(IDLUtils.getIDLType(this.m_class, "_"));
      this.m_enclosed = enclosed;
   }

   public String getIncludeDeclaration() throws CodeGenerationException {
      return "";
   }

   public String getForwardDeclaration() throws CodeGenerationException {
      StringBuffer result = new StringBuffer();
      String fileName = IDLUtils.javaTypeToPath((String)null, this.m_class);
      fileName = fileName.substring(0, fileName.length() - ".idl".length());
      fileName = IDLUtils.exceptionToEx(fileName) + ".idl";
      result.append("#include \"" + fileName + "\"\n");
      return result.toString();
   }

   public String beforeMainDeclaration() {
      StringBuffer result = new StringBuffer();
      if (!IDLOptions.getNoValueTypes()) {
         result.append(IDLUtils.generateGuard(this.m_class, "#ifndef"));
         result.append(IDLUtils.openModule(this.m_class));
         result.append("  valuetype ").append(this.m_exceptionName).append(";\n");
         result.append(IDLUtils.closeModule(this.m_class));
         result.append(IDLUtils.generateGuard(this.m_class, "#endif //"));
      }

      return result.toString();
   }

   public IDLType getEnclosed() {
      return this.m_enclosed;
   }

   public String afterMainDeclaration() {
      String result = new String();
      if (!IDLOptions.getNoValueTypes()) {
         result = IDLUtils.generateInclude((String)null, this.m_class);
      }

      return result;
   }

   public String getOpeningDeclaration() throws CodeGenerationException {
      StringBuffer result = (new StringBuffer("exception ")).append(this.m_exName).append(" ");
      return result.toString();
   }

   public Hashtable getMethods() {
      return new Hashtable();
   }

   public Hashtable getAttributes() {
      return new Hashtable();
   }

   public Hashtable getExtraLines() {
      Hashtable result = new Hashtable();
      String name = "value";
      String exType = IDLOptions.getNoValueTypes() ? "::CORBA::WStringValue" : this.m_exceptionName;
      IDLExtraLine s = new IDLExtraLine(name, exType + " " + name + ";\n");
      result.put(s.getMangledName(), s);
      return result;
   }

   public String getGuardName(String preprocessor) {
      String result = IDLUtils.generateGuard(this.m_guard, preprocessor);
      return result;
   }

   public void getReferences(Hashtable hResult) {
   }

   public String getFileName() {
      String fn = IDLUtils.javaTypeToPath(this.getDirectory(), this.getJavaClass());
      fn = fn.substring(0, fn.length() - ".idl".length());
      return IDLUtils.exceptionToEx(fn) + ".idl";
   }

   public String getPragmaID() {
      return "";
   }

   public boolean canHaveSubtype(IDLType t) {
      return false;
   }
}

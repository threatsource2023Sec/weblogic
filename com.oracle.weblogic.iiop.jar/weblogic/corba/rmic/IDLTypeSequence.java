package weblogic.corba.rmic;

import java.util.Hashtable;
import weblogic.corba.utils.CorbaUtils;
import weblogic.iiop.IDLUtils;
import weblogic.utils.Debug;
import weblogic.utils.compiler.CodeGenerationException;

public class IDLTypeSequence extends IDLType {
   public static final String OPENING_DECLARATION = "valuetype";
   Class m_componentType;
   IDLType idlComponent;
   public static final TypeTraits TRAITS = new TypeTraits() {
      public Class getValidClass(Class c, Class ec) {
         Class result = null;
         if (CorbaUtils.isValid(c) && null != c.getComponentType()) {
            result = c;
         }

         return result;
      }

      public IDLType createType(Class c, Class ec) {
         return new IDLTypeSequence(c, ec);
      }
   };

   public IDLTypeSequence(Class c, Class ec) {
      super(c, ec);
      this.m_componentType = c.getComponentType();
      Debug.assertion(null != this.m_componentType);
      if (CorbaUtils.isValid(this.m_componentType)) {
         for(this.idlComponent = IDLType.createType(this.m_componentType, this.getJavaClass()); this.idlComponent instanceof IDLTypeEx; this.idlComponent = ((IDLTypeEx)this.idlComponent).getEnclosed()) {
         }
      }

   }

   public String getIncludeDeclaration() throws CodeGenerationException {
      return "";
   }

   public String getOpeningDeclaration() throws CodeGenerationException {
      Class c = this.getJavaClass();
      Debug.assertion(null != this.m_componentType);
      String type = IDLUtils.stripPackage(IDLUtils.getIDLType(c, "."));
      return "typedef sequence<" + IDLUtils.getIDLType(this.m_componentType) + "> " + type + "_t ;\n" + "valuetype" + " " + type + " " + type + "_t ;\n";
   }

   public String getForwardDeclaration() throws CodeGenerationException {
      StringBuffer sb = new StringBuffer();
      if (this.idlComponent instanceof IDLTypeValueType) {
         sb.append(((IDLTypeValueType)this.idlComponent).getForwardDeclaration(false));
      } else if (this.m_componentType.equals(this.getEnclosingClass())) {
         sb.append(this.idlComponent.getForwardDeclaration());
      }

      sb.append(IDLUtils.generateInclude(this.getDirectory(), this.getJavaClass()));
      return sb.toString();
   }

   public String getOpenBrace() {
      return "";
   }

   public String getCloseBrace() {
      return "";
   }

   public void getReferences(Hashtable hResult) {
      if (this.idlComponent != null) {
         hResult.put(this.idlComponent.getClassName(), this.idlComponent);
      }

   }

   public String getPragmaID() {
      return IDLUtils.getPragmaID(this.getJavaClass());
   }

   public boolean canHaveSubtype(IDLType t) {
      return false;
   }
}

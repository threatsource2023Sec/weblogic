package weblogic.corba.rmic;

import java.lang.reflect.Field;
import weblogic.iiop.IDLUtils;
import weblogic.iiop.Utils;

public final class IDLAttribute extends IDLField {
   public static final int READONLY = 1;
   public static final int READWRITE = 2;
   public static final int CONST = 4;
   public static final int WRITEONLY = 8;
   int m_modifier;

   public IDLAttribute(String name, int modifier, Object value, Class type) {
      super((Class)null, (Field)null);
      this.m_mangledName = name;
      this.m_modifier = modifier;
      this.m_value = value;
      this.m_type = type;
   }

   public boolean isReadOnly() {
      return 1 == this.m_modifier;
   }

   public void setModifier(int mod) {
      this.m_modifier = mod;
   }

   public int getModifier() {
      return this.m_modifier;
   }

   public String getName() {
      return this.m_mangledName;
   }

   public String toIDL() {
      String idlType;
      try {
         idlType = IDLUtils.getIDLType(this.m_type);
      } catch (Exception var4) {
         return "// error generating the type for " + this.m_mangledName;
      }

      StringBuffer result = new StringBuffer();
      if (IDLUtils.isValueType(this.m_type) && IDLOptions.getNoValueTypes() || Utils.isAbstractInterface(this.m_type) && IDLOptions.getNoAbstract() || IDLOptions.getOrbix() && idlType.endsWith(this.m_mangledName)) {
         result.append("// ");
      }

      if (this.isReadOnly()) {
         result.append("readonly ");
      }

      result.append("attribute ");
      result.append(idlType).append(" ").append(this.m_mangledName);
      if (null != this.m_value) {
         result.append(" = ");
         String value = this.m_value.toString();
         if (String.class.equals(this.m_value.getClass())) {
            value = '"' + value + '"';
         }

         result.append(value);
      }

      result.append(";\n");
      return result.toString();
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }
}

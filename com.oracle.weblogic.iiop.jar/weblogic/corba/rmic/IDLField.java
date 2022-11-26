package weblogic.corba.rmic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import weblogic.iiop.IDLUtils;

public class IDLField implements Cloneable {
   protected String m_mangledName = null;
   protected Class m_type = null;
   protected boolean m_isConst = false;
   protected Object m_value = null;
   boolean m_isPublic = false;
   Field m_field = null;
   Class m_class = null;

   public IDLField(Class c, Field f) {
      this.init(c, f);
   }

   void init(Class c, Field f) {
      this.m_class = c;
      this.m_field = f;
      if (null != this.m_field) {
         int mod = this.m_field.getModifiers();
         if (Modifier.isPublic(mod)) {
            this.m_isPublic = true;
         }

         this.m_isConst = Modifier.isPublic(mod) && Modifier.isStatic(mod) && Modifier.isFinal(mod);
         this.m_mangledName = IDLUtils.mangleAttributeName(this.m_class, f);
         if (this.m_isConst) {
            try {
               this.m_value = f.get((Object)null);
            } catch (IllegalAccessException var5) {
            }
         }

         this.m_type = f.getType();
      }

   }

   public Field getField() {
      return this.m_field;
   }

   public Object getValue() {
      return this.m_value;
   }

   public Class getType() {
      return this.m_type;
   }

   public String getMangledName() {
      return this.m_mangledName;
   }

   public void setMangledName(String mn) {
      this.m_mangledName = mn;
   }

   public boolean isConst() {
      return this.m_isConst;
   }

   public boolean isPublic() {
      return this.m_isPublic;
   }

   public boolean isPrimitive() {
      return this.m_type.isPrimitive();
   }

   public String getName() {
      return this.m_field.getName();
   }

   public String toIDL() {
      StringBuilder result = new StringBuilder();

      String idlType;
      try {
         idlType = IDLUtils.getIDLType(this.m_type);
      } catch (Exception var6) {
         return "// error generating the type for " + this.m_mangledName;
      }

      if (IDLOptions.getOrbix() && idlType.endsWith(this.m_mangledName)) {
         result.append("// ");
      }

      if (this.isConst()) {
         result.append("const ");
      } else if (this.isPublic()) {
         result.append("public ");
      } else {
         result.append("private ");
      }

      result.append(idlType).append(" ").append(this.m_mangledName);
      if (null != this.m_value && IDLUtils.isValueType(this.m_value.getClass())) {
         result.append(" = ");
         String value = this.m_value.toString();
         if (String.class.equals(this.m_value.getClass())) {
            value = '"' + value + '"';
         } else if (Character.class.equals(this.m_value.getClass())) {
            char ch = (Character)this.m_value;
            int n = Character.getNumericValue(ch) & '\uffff';
            value = "'\\u" + Integer.toHexString(n) + "'";
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

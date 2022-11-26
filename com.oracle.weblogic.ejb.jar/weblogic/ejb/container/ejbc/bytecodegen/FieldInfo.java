package weblogic.ejb.container.ejbc.bytecodegen;

class FieldInfo {
   private final String name;
   private final Class type;
   private final String binName;
   private final String fieldDesc;

   FieldInfo(String fieldName, Class type) {
      this.name = fieldName;
      this.type = type;
      this.binName = BCUtil.binName(type);
      this.fieldDesc = BCUtil.fieldDesc(type);
   }

   String fieldName() {
      return this.name;
   }

   Class fieldType() {
      return this.type;
   }

   String binName() {
      return this.binName;
   }

   String fieldDesc() {
      return this.fieldDesc;
   }
}

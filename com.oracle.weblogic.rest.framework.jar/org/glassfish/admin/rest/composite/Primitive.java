package org.glassfish.admin.rest.composite;

enum Primitive {
   DOUBLE("D", 175, 24),
   FLOAT("F", 174, 23),
   LONG("J", 173, 22),
   SHORT("S", 172, 21),
   INT("I", 172, 21),
   BYTE("B", 172, 21),
   BOOLEAN("Z", 172, 21);

   private final int returnOpcode;
   private final int setOpcode;
   private final String internalType;

   private Primitive(String type, int returnOpcode, int setOpcode) {
      this.internalType = type;
      this.returnOpcode = returnOpcode;
      this.setOpcode = setOpcode;
   }

   public int getReturnOpcode() {
      return this.returnOpcode;
   }

   public int getSetOpCode() {
      return this.setOpcode;
   }

   public String getInternalType() {
      return this.internalType;
   }

   static Primitive getPrimitive(String type) {
      if (!"S".equals(type) && !"short".equals(type)) {
         if (!"J".equals(type) && !"long".equals(type)) {
            if (!"I".equals(type) && !"int".equals(type)) {
               if (!"F".equals(type) && !"float".equals(type)) {
                  if (!"D".equals(type) && !"double".equals(type)) {
                     if (!"B".equals(type) && !"byte".equals(type)) {
                        if (!"Z".equals(type) && !"boolean".equals(type)) {
                           throw new RuntimeException("Unknown primitive type: " + type);
                        } else {
                           return BOOLEAN;
                        }
                     } else {
                        return BYTE;
                     }
                  } else {
                     return DOUBLE;
                  }
               } else {
                  return FLOAT;
               }
            } else {
               return INT;
            }
         } else {
            return LONG;
         }
      } else {
         return SHORT;
      }
   }
}

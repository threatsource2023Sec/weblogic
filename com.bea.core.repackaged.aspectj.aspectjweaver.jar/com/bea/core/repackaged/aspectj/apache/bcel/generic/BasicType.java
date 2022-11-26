package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;

public final class BasicType extends Type {
   BasicType(byte type) {
      super(type, Constants.SHORT_TYPE_NAMES[type]);
      if (type < 4 || type > 12) {
         throw new ClassGenException("Invalid type: " + type);
      }
   }

   public static final BasicType getType(byte type) {
      switch (type) {
         case 4:
            return BOOLEAN;
         case 5:
            return CHAR;
         case 6:
            return FLOAT;
         case 7:
            return DOUBLE;
         case 8:
            return BYTE;
         case 9:
            return SHORT;
         case 10:
            return INT;
         case 11:
            return LONG;
         case 12:
            return VOID;
         default:
            throw new ClassGenException("Invalid type: " + type);
      }
   }

   public boolean equals(Object type) {
      return type instanceof BasicType ? ((BasicType)type).type == this.type : false;
   }
}

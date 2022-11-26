package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;
import serp.util.Strings;

public class ConvertInstruction extends TypedInstruction {
   private static final Class[][] _mappings;
   private static final Class[][] _fromMappings;
   String _toType = null;
   String _fromType = null;

   ConvertInstruction(Code owner) {
      super(owner);
   }

   ConvertInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   public int getLogicalStackChange() {
      return 0;
   }

   public int getStackChange() {
      switch (this.getOpcode()) {
         case 133:
         case 135:
         case 140:
         case 141:
            return 1;
         case 134:
         case 138:
         case 139:
         case 143:
         default:
            return 0;
         case 136:
         case 137:
         case 142:
         case 144:
            return -1;
      }
   }

   public String getTypeName() {
      switch (this.getOpcode()) {
         case 133:
         case 140:
         case 143:
            return Long.TYPE.getName();
         case 134:
         case 137:
         case 144:
            return Float.TYPE.getName();
         case 135:
         case 138:
         case 141:
            return Double.TYPE.getName();
         case 136:
         case 139:
         case 142:
            return Integer.TYPE.getName();
         case 145:
            return Byte.TYPE.getName();
         case 146:
            return Character.TYPE.getName();
         case 147:
            return Short.TYPE.getName();
         default:
            return this._toType;
      }
   }

   public TypedInstruction setType(String type) {
      String toType = this.mapType(type, _mappings, true);
      String fromType = this.getFromTypeName();
      if (toType != null && fromType != null && !toType.equals(fromType)) {
         this._toType = null;
         this._fromType = null;
         char to = toType.charAt(0);
         char from = fromType.charAt(0);
         switch (to) {
            case 'i':
               switch (from) {
                  case 'd':
                     return (TypedInstruction)this.setOpcode(142);
                  case 'f':
                     return (TypedInstruction)this.setOpcode(139);
                  case 'l':
                     return (TypedInstruction)this.setOpcode(136);
               }
            case 'l':
               switch (from) {
                  case 'd':
                     return (TypedInstruction)this.setOpcode(143);
                  case 'f':
                     return (TypedInstruction)this.setOpcode(140);
                  case 'i':
                     return (TypedInstruction)this.setOpcode(133);
               }
            case 'f':
               switch (from) {
                  case 'd':
                     return (TypedInstruction)this.setOpcode(144);
                  case 'i':
                     return (TypedInstruction)this.setOpcode(134);
                  case 'l':
                     return (TypedInstruction)this.setOpcode(137);
               }
            case 'd':
               switch (from) {
                  case 'f':
                     return (TypedInstruction)this.setOpcode(141);
                  case 'i':
                     return (TypedInstruction)this.setOpcode(135);
                  case 'l':
                     return (TypedInstruction)this.setOpcode(138);
               }
            case 'b':
               if (from == 'i') {
                  return (TypedInstruction)this.setOpcode(145);
               }
            case 'C':
               if (from == 'i') {
                  return (TypedInstruction)this.setOpcode(146);
               }
            case 'S':
               if (from == 'i') {
                  return (TypedInstruction)this.setOpcode(147);
               }
            default:
               throw new IllegalStateException();
         }
      } else {
         this._toType = toType;
         this._fromType = fromType;
         return (TypedInstruction)this.setOpcode(0);
      }
   }

   public String getFromTypeName() {
      switch (this.getOpcode()) {
         case 133:
         case 134:
         case 135:
         case 145:
         case 146:
         case 147:
            return Integer.TYPE.getName();
         case 136:
         case 137:
         case 138:
            return Long.TYPE.getName();
         case 139:
         case 140:
         case 141:
            return Float.TYPE.getName();
         case 142:
         case 143:
         case 144:
            return Double.TYPE.getName();
         default:
            return this._fromType;
      }
   }

   public Class getFromType() {
      String type = this.getFromTypeName();
      return type == null ? null : Strings.toClass(type, this.getClassLoader());
   }

   public BCClass getFromTypeBC() {
      String type = this.getFromTypeName();
      return type == null ? null : this.getProject().loadClass(type, this.getClassLoader());
   }

   public ConvertInstruction setFromType(String type) {
      String fromType = this.mapType(type, _fromMappings, true);
      String toType = this.getTypeName();
      if (toType != null && fromType != null && !toType.equals(fromType)) {
         this._toType = null;
         this._fromType = null;
         char to = toType.charAt(0);
         char from = fromType.charAt(0);
         switch (from) {
            case 'i':
               switch (to) {
                  case 'b':
                     return (ConvertInstruction)this.setOpcode(145);
                  case 'c':
                     return (ConvertInstruction)this.setOpcode(146);
                  case 'd':
                     return (ConvertInstruction)this.setOpcode(135);
                  case 'e':
                  case 'g':
                  case 'h':
                  case 'i':
                  case 'j':
                  case 'k':
                  case 'm':
                  case 'n':
                  case 'o':
                  case 'p':
                  case 'q':
                  case 'r':
                  default:
                     break;
                  case 'f':
                     return (ConvertInstruction)this.setOpcode(134);
                  case 'l':
                     return (ConvertInstruction)this.setOpcode(133);
                  case 's':
                     return (ConvertInstruction)this.setOpcode(147);
               }
            case 'l':
               switch (to) {
                  case 'd':
                     return (ConvertInstruction)this.setOpcode(138);
                  case 'f':
                     return (ConvertInstruction)this.setOpcode(137);
                  case 'i':
                     return (ConvertInstruction)this.setOpcode(136);
               }
            case 'f':
               switch (to) {
                  case 'd':
                     return (ConvertInstruction)this.setOpcode(141);
                  case 'i':
                     return (ConvertInstruction)this.setOpcode(139);
                  case 'l':
                     return (ConvertInstruction)this.setOpcode(140);
               }
            case 'd':
               switch (to) {
                  case 'f':
                     return (ConvertInstruction)this.setOpcode(144);
                  case 'i':
                     return (ConvertInstruction)this.setOpcode(142);
                  case 'l':
                     return (ConvertInstruction)this.setOpcode(143);
               }
            case 'e':
            case 'g':
            case 'h':
            case 'j':
            case 'k':
            default:
               throw new IllegalStateException();
         }
      } else {
         this._toType = toType;
         this._fromType = fromType;
         return (ConvertInstruction)this.setOpcode(0);
      }
   }

   public ConvertInstruction setFromType(Class type) {
      return type == null ? this.setFromType((String)null) : this.setFromType(type.getName());
   }

   public ConvertInstruction setFromType(BCClass type) {
      return type == null ? this.setFromType((String)null) : this.setFromType(type.getName());
   }

   public boolean equalsInstruction(Instruction other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof ConvertInstruction)) {
         return false;
      } else {
         ConvertInstruction ins = (ConvertInstruction)other;
         if (this.getOpcode() != 0 && this.getOpcode() == ins.getOpcode()) {
            return true;
         } else {
            String type = this.getTypeName();
            String otherType = ins.getTypeName();
            if (type != null && otherType != null && !type.equals(otherType)) {
               return false;
            } else {
               type = this.getFromTypeName();
               otherType = ins.getFromTypeName();
               return type == null || otherType == null || type.equals(otherType);
            }
         }
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterConvertInstruction(this);
      visit.exitConvertInstruction(this);
   }

   void read(Instruction orig) {
      super.read(orig);
      ConvertInstruction ins = (ConvertInstruction)orig;
      this._toType = ins._toType;
      this._fromType = ins._fromType;
   }

   static {
      _mappings = new Class[][]{{Boolean.TYPE, Integer.TYPE}, {Void.TYPE, Integer.TYPE}, {Object.class, Integer.TYPE}};
      _fromMappings = new Class[][]{{Boolean.TYPE, Integer.TYPE}, {Void.TYPE, Integer.TYPE}, {Object.class, Integer.TYPE}, {Byte.TYPE, Integer.TYPE}, {Character.TYPE, Integer.TYPE}, {Short.TYPE, Integer.TYPE}};
   }
}

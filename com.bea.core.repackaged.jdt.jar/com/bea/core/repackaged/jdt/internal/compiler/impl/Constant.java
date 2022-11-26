package com.bea.core.repackaged.jdt.internal.compiler.impl;

import com.bea.core.repackaged.jdt.internal.compiler.ast.OperatorIds;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeIds;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ShouldNotImplement;
import com.bea.core.repackaged.jdt.internal.compiler.util.Messages;

public abstract class Constant implements TypeIds, OperatorIds {
   public static final Constant NotAConstant = DoubleConstant.fromValue(Double.NaN);
   public static final Constant[] NotAConstantList = new Constant[]{DoubleConstant.fromValue(Double.NaN)};

   public boolean booleanValue() {
      throw new ShouldNotImplement(Messages.bind(Messages.constant_cannotCastedInto, (Object[])(new String[]{this.typeName(), "boolean"})));
   }

   public byte byteValue() {
      throw new ShouldNotImplement(Messages.bind(Messages.constant_cannotCastedInto, (Object[])(new String[]{this.typeName(), "byte"})));
   }

   public final Constant castTo(int conversionToTargetType) {
      if (this == NotAConstant) {
         return NotAConstant;
      } else {
         switch (conversionToTargetType) {
            case 0:
               return this;
            case 34:
               return this;
            case 35:
               return CharConstant.fromValue((char)this.byteValue());
            case 36:
               return CharConstant.fromValue((char)this.shortValue());
            case 39:
               return CharConstant.fromValue((char)((int)this.longValue()));
            case 40:
               return CharConstant.fromValue((char)((int)this.doubleValue()));
            case 41:
               return CharConstant.fromValue((char)((int)this.floatValue()));
            case 42:
               return CharConstant.fromValue((char)this.intValue());
            case 50:
               return ByteConstant.fromValue((byte)this.charValue());
            case 51:
               return this;
            case 52:
               return ByteConstant.fromValue((byte)this.shortValue());
            case 55:
               return ByteConstant.fromValue((byte)((int)this.longValue()));
            case 56:
               return ByteConstant.fromValue((byte)((int)this.doubleValue()));
            case 57:
               return ByteConstant.fromValue((byte)((int)this.floatValue()));
            case 58:
               return ByteConstant.fromValue((byte)this.intValue());
            case 66:
               return ShortConstant.fromValue((short)this.charValue());
            case 67:
               return ShortConstant.fromValue(this.byteValue());
            case 68:
               return this;
            case 71:
               return ShortConstant.fromValue((short)((int)this.longValue()));
            case 72:
               return ShortConstant.fromValue((short)((int)this.doubleValue()));
            case 73:
               return ShortConstant.fromValue((short)((int)this.floatValue()));
            case 74:
               return ShortConstant.fromValue((short)this.intValue());
            case 85:
               return this;
            case 114:
               return LongConstant.fromValue((long)this.charValue());
            case 115:
               return LongConstant.fromValue((long)this.byteValue());
            case 116:
               return LongConstant.fromValue((long)this.shortValue());
            case 119:
               return this;
            case 120:
               return LongConstant.fromValue((long)this.doubleValue());
            case 121:
               return LongConstant.fromValue((long)this.floatValue());
            case 122:
               return LongConstant.fromValue((long)this.intValue());
            case 130:
               return DoubleConstant.fromValue((double)this.charValue());
            case 131:
               return DoubleConstant.fromValue((double)this.byteValue());
            case 132:
               return DoubleConstant.fromValue((double)this.shortValue());
            case 135:
               return DoubleConstant.fromValue((double)this.longValue());
            case 136:
               return this;
            case 137:
               return DoubleConstant.fromValue((double)this.floatValue());
            case 138:
               return DoubleConstant.fromValue((double)this.intValue());
            case 146:
               return FloatConstant.fromValue((float)this.charValue());
            case 147:
               return FloatConstant.fromValue((float)this.byteValue());
            case 148:
               return FloatConstant.fromValue((float)this.shortValue());
            case 151:
               return FloatConstant.fromValue((float)this.longValue());
            case 152:
               return FloatConstant.fromValue((float)this.doubleValue());
            case 153:
               return this;
            case 154:
               return FloatConstant.fromValue((float)this.intValue());
            case 162:
               return IntConstant.fromValue(this.charValue());
            case 163:
               return IntConstant.fromValue(this.byteValue());
            case 164:
               return IntConstant.fromValue(this.shortValue());
            case 167:
               return IntConstant.fromValue((int)this.longValue());
            case 168:
               return IntConstant.fromValue((int)this.doubleValue());
            case 169:
               return IntConstant.fromValue((int)this.floatValue());
            case 170:
               return this;
            case 187:
               return this;
            default:
               return NotAConstant;
         }
      }
   }

   public char charValue() {
      throw new ShouldNotImplement(Messages.bind(Messages.constant_cannotCastedInto, (Object[])(new String[]{this.typeName(), "char"})));
   }

   public static final Constant computeConstantOperation(Constant cst, int id, int operator) {
      switch (operator) {
         case 11:
            return BooleanConstant.fromValue(!cst.booleanValue());
         case 12:
            switch (id) {
               case 2:
                  return IntConstant.fromValue(~cst.charValue());
               case 3:
                  return IntConstant.fromValue(~cst.byteValue());
               case 4:
                  return IntConstant.fromValue(~cst.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue(~cst.longValue());
               case 10:
                  return IntConstant.fromValue(~cst.intValue());
            }
         case 13:
            switch (id) {
               case 8:
                  double d;
                  if ((d = cst.doubleValue()) == 0.0) {
                     if (Double.doubleToLongBits(d) == 0L) {
                        return DoubleConstant.fromValue(-0.0);
                     }

                     return DoubleConstant.fromValue(0.0);
                  }
                  break;
               case 9:
                  float f;
                  if ((f = cst.floatValue()) == 0.0F) {
                     if (Float.floatToIntBits(f) == 0) {
                        return FloatConstant.fromValue(-0.0F);
                     }

                     return FloatConstant.fromValue(0.0F);
                  }
            }

            return computeConstantOperationMINUS(IntConstant.fromValue(0), 10, cst, id);
         case 14:
            return computeConstantOperationPLUS(IntConstant.fromValue(0), 10, cst, id);
         default:
            return NotAConstant;
      }
   }

   public static final Constant computeConstantOperation(Constant left, int leftId, int operator, Constant right, int rightId) {
      switch (operator) {
         case 0:
            return computeConstantOperationAND_AND(left, leftId, right, rightId);
         case 1:
            return computeConstantOperationOR_OR(left, leftId, right, rightId);
         case 2:
            return computeConstantOperationAND(left, leftId, right, rightId);
         case 3:
            return computeConstantOperationOR(left, leftId, right, rightId);
         case 4:
            return computeConstantOperationLESS(left, leftId, right, rightId);
         case 5:
            return computeConstantOperationLESS_EQUAL(left, leftId, right, rightId);
         case 6:
            return computeConstantOperationGREATER(left, leftId, right, rightId);
         case 7:
            return computeConstantOperationGREATER_EQUAL(left, leftId, right, rightId);
         case 8:
            return computeConstantOperationXOR(left, leftId, right, rightId);
         case 9:
            return computeConstantOperationDIVIDE(left, leftId, right, rightId);
         case 10:
            return computeConstantOperationLEFT_SHIFT(left, leftId, right, rightId);
         case 11:
         case 12:
         case 18:
         default:
            return NotAConstant;
         case 13:
            return computeConstantOperationMINUS(left, leftId, right, rightId);
         case 14:
            return computeConstantOperationPLUS(left, leftId, right, rightId);
         case 15:
            return computeConstantOperationMULTIPLY(left, leftId, right, rightId);
         case 16:
            return computeConstantOperationREMAINDER(left, leftId, right, rightId);
         case 17:
            return computeConstantOperationRIGHT_SHIFT(left, leftId, right, rightId);
         case 19:
            return computeConstantOperationUNSIGNED_RIGHT_SHIFT(left, leftId, right, rightId);
      }
   }

   public static final Constant computeConstantOperationAND(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.charValue() & right.charValue());
               case 3:
                  return IntConstant.fromValue(left.charValue() & right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.charValue() & right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.charValue() & right.longValue());
               case 10:
                  return IntConstant.fromValue(left.charValue() & right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.byteValue() & right.charValue());
               case 3:
                  return IntConstant.fromValue(left.byteValue() & right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.byteValue() & right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.byteValue() & right.longValue());
               case 10:
                  return IntConstant.fromValue(left.byteValue() & right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.shortValue() & right.charValue());
               case 3:
                  return IntConstant.fromValue(left.shortValue() & right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.shortValue() & right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.shortValue() & right.longValue());
               case 10:
                  return IntConstant.fromValue(left.shortValue() & right.intValue());
            }
         case 5:
            return BooleanConstant.fromValue(left.booleanValue() & right.booleanValue());
         case 6:
         case 8:
         case 9:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return LongConstant.fromValue(left.longValue() & (long)right.charValue());
               case 3:
                  return LongConstant.fromValue(left.longValue() & (long)right.byteValue());
               case 4:
                  return LongConstant.fromValue(left.longValue() & (long)right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue(left.longValue() & right.longValue());
               case 10:
                  return LongConstant.fromValue(left.longValue() & (long)right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.intValue() & right.charValue());
               case 3:
                  return IntConstant.fromValue(left.intValue() & right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.intValue() & right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  break;
               case 7:
                  return LongConstant.fromValue((long)left.intValue() & right.longValue());
               case 10:
                  return IntConstant.fromValue(left.intValue() & right.intValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationAND_AND(Constant left, int leftId, Constant right, int rightId) {
      return BooleanConstant.fromValue(left.booleanValue() && right.booleanValue());
   }

   public static final Constant computeConstantOperationDIVIDE(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.charValue() / right.charValue());
               case 3:
                  return IntConstant.fromValue(left.charValue() / right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.charValue() / right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.charValue() / right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.charValue() / right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.charValue() / right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.charValue() / right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.byteValue() / right.charValue());
               case 3:
                  return IntConstant.fromValue(left.byteValue() / right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.byteValue() / right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.byteValue() / right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.byteValue() / right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.byteValue() / right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.byteValue() / right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.shortValue() / right.charValue());
               case 3:
                  return IntConstant.fromValue(left.shortValue() / right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.shortValue() / right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return LongConstant.fromValue((long)left.shortValue() / right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.shortValue() / right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.shortValue() / right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.shortValue() / right.intValue());
            }
         case 5:
         case 6:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return LongConstant.fromValue(left.longValue() / (long)right.charValue());
               case 3:
                  return LongConstant.fromValue(left.longValue() / (long)right.byteValue());
               case 4:
                  return LongConstant.fromValue(left.longValue() / (long)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue(left.longValue() / right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.longValue() / right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.longValue() / right.floatValue());
               case 10:
                  return LongConstant.fromValue(left.longValue() / (long)right.intValue());
            }
         case 8:
            switch (rightId) {
               case 2:
                  return DoubleConstant.fromValue(left.doubleValue() / (double)right.charValue());
               case 3:
                  return DoubleConstant.fromValue(left.doubleValue() / (double)right.byteValue());
               case 4:
                  return DoubleConstant.fromValue(left.doubleValue() / (double)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return DoubleConstant.fromValue(left.doubleValue() / (double)right.longValue());
               case 8:
                  return DoubleConstant.fromValue(left.doubleValue() / right.doubleValue());
               case 9:
                  return DoubleConstant.fromValue(left.doubleValue() / (double)right.floatValue());
               case 10:
                  return DoubleConstant.fromValue(left.doubleValue() / (double)right.intValue());
            }
         case 9:
            switch (rightId) {
               case 2:
                  return FloatConstant.fromValue(left.floatValue() / (float)right.charValue());
               case 3:
                  return FloatConstant.fromValue(left.floatValue() / (float)right.byteValue());
               case 4:
                  return FloatConstant.fromValue(left.floatValue() / (float)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return FloatConstant.fromValue(left.floatValue() / (float)right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.floatValue() / right.doubleValue());
               case 9:
                  return FloatConstant.fromValue(left.floatValue() / right.floatValue());
               case 10:
                  return FloatConstant.fromValue(left.floatValue() / (float)right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.intValue() / right.charValue());
               case 3:
                  return IntConstant.fromValue(left.intValue() / right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.intValue() / right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return LongConstant.fromValue((long)left.intValue() / right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.intValue() / right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.intValue() / right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.intValue() / right.intValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationEQUAL_EQUAL(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.charValue() == right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.charValue() == right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.charValue() == right.shortValue());
               case 5:
               case 6:
               default:
                  return BooleanConstant.fromValue(false);
               case 7:
                  return BooleanConstant.fromValue((long)left.charValue() == right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.charValue() == right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.charValue() == right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.charValue() == right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.byteValue() == right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.byteValue() == right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.byteValue() == right.shortValue());
               case 5:
               case 6:
               default:
                  return BooleanConstant.fromValue(false);
               case 7:
                  return BooleanConstant.fromValue((long)left.byteValue() == right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.byteValue() == right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.byteValue() == right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.byteValue() == right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.shortValue() == right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.shortValue() == right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.shortValue() == right.shortValue());
               case 5:
               case 6:
               default:
                  return BooleanConstant.fromValue(false);
               case 7:
                  return BooleanConstant.fromValue((long)left.shortValue() == right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.shortValue() == right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.shortValue() == right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.shortValue() == right.intValue());
            }
         case 5:
            if (rightId == 5) {
               return BooleanConstant.fromValue(left.booleanValue() == right.booleanValue());
            }
         case 6:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.longValue() == (long)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.longValue() == (long)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.longValue() == (long)right.shortValue());
               case 5:
               case 6:
               default:
                  return BooleanConstant.fromValue(false);
               case 7:
                  return BooleanConstant.fromValue(left.longValue() == right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.longValue() == right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.longValue() == right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.longValue() == (long)right.intValue());
            }
         case 8:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.doubleValue() == (double)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.doubleValue() == (double)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.doubleValue() == (double)right.shortValue());
               case 5:
               case 6:
               default:
                  return BooleanConstant.fromValue(false);
               case 7:
                  return BooleanConstant.fromValue(left.doubleValue() == (double)right.longValue());
               case 8:
                  return BooleanConstant.fromValue(left.doubleValue() == right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue(left.doubleValue() == (double)right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.doubleValue() == (double)right.intValue());
            }
         case 9:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.floatValue() == (float)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.floatValue() == (float)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.floatValue() == (float)right.shortValue());
               case 5:
               case 6:
               default:
                  return BooleanConstant.fromValue(false);
               case 7:
                  return BooleanConstant.fromValue(left.floatValue() == (float)right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.floatValue() == right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue(left.floatValue() == right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.floatValue() == (float)right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.intValue() == right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.intValue() == right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.intValue() == right.shortValue());
               case 5:
               case 6:
               default:
                  return BooleanConstant.fromValue(false);
               case 7:
                  return BooleanConstant.fromValue((long)left.intValue() == right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.intValue() == right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.intValue() == right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.intValue() == right.intValue());
            }
         case 11:
            if (rightId == 11) {
               return BooleanConstant.fromValue(((StringConstant)left).hasSameValue(right));
            }
            break;
         case 12:
            if (rightId == 11) {
               return BooleanConstant.fromValue(false);
            }

            if (rightId == 12) {
               return BooleanConstant.fromValue(true);
            }
      }

      return BooleanConstant.fromValue(false);
   }

   public static final Constant computeConstantOperationGREATER(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.charValue() > right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.charValue() > right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.charValue() > right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue((long)left.charValue() > right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.charValue() > right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.charValue() > right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.charValue() > right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.byteValue() > right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.byteValue() > right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.byteValue() > right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue((long)left.byteValue() > right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.byteValue() > right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.byteValue() > right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.byteValue() > right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.shortValue() > right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.shortValue() > right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.shortValue() > right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return BooleanConstant.fromValue((long)left.shortValue() > right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.shortValue() > right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.shortValue() > right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.shortValue() > right.intValue());
            }
         case 5:
         case 6:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.longValue() > (long)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.longValue() > (long)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.longValue() > (long)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue(left.longValue() > right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.longValue() > right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.longValue() > right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.longValue() > (long)right.intValue());
            }
         case 8:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.doubleValue() > (double)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.doubleValue() > (double)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.doubleValue() > (double)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue(left.doubleValue() > (double)right.longValue());
               case 8:
                  return BooleanConstant.fromValue(left.doubleValue() > right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue(left.doubleValue() > (double)right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.doubleValue() > (double)right.intValue());
            }
         case 9:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.floatValue() > (float)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.floatValue() > (float)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.floatValue() > (float)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue(left.floatValue() > (float)right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.floatValue() > right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue(left.floatValue() > right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.floatValue() > (float)right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.intValue() > right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.intValue() > right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.intValue() > right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return BooleanConstant.fromValue((long)left.intValue() > right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.intValue() > right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.intValue() > right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.intValue() > right.intValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationGREATER_EQUAL(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.charValue() >= right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.charValue() >= right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.charValue() >= right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue((long)left.charValue() >= right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.charValue() >= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.charValue() >= right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.charValue() >= right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.byteValue() >= right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.byteValue() >= right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.byteValue() >= right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue((long)left.byteValue() >= right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.byteValue() >= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.byteValue() >= right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.byteValue() >= right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.shortValue() >= right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.shortValue() >= right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.shortValue() >= right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return BooleanConstant.fromValue((long)left.shortValue() >= right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.shortValue() >= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.shortValue() >= right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.shortValue() >= right.intValue());
            }
         case 5:
         case 6:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.longValue() >= (long)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.longValue() >= (long)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.longValue() >= (long)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue(left.longValue() >= right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.longValue() >= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.longValue() >= right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.longValue() >= (long)right.intValue());
            }
         case 8:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.doubleValue() >= (double)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.doubleValue() >= (double)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.doubleValue() >= (double)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue(left.doubleValue() >= (double)right.longValue());
               case 8:
                  return BooleanConstant.fromValue(left.doubleValue() >= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue(left.doubleValue() >= (double)right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.doubleValue() >= (double)right.intValue());
            }
         case 9:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.floatValue() >= (float)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.floatValue() >= (float)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.floatValue() >= (float)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue(left.floatValue() >= (float)right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.floatValue() >= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue(left.floatValue() >= right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.floatValue() >= (float)right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.intValue() >= right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.intValue() >= right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.intValue() >= right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return BooleanConstant.fromValue((long)left.intValue() >= right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.intValue() >= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.intValue() >= right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.intValue() >= right.intValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationLEFT_SHIFT(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.charValue() << right.charValue());
               case 3:
                  return IntConstant.fromValue(left.charValue() << right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.charValue() << right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return IntConstant.fromValue(left.charValue() << (int)right.longValue());
               case 10:
                  return IntConstant.fromValue(left.charValue() << right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.byteValue() << right.charValue());
               case 3:
                  return IntConstant.fromValue(left.byteValue() << right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.byteValue() << right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return IntConstant.fromValue(left.byteValue() << (int)right.longValue());
               case 10:
                  return IntConstant.fromValue(left.byteValue() << right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.shortValue() << right.charValue());
               case 3:
                  return IntConstant.fromValue(left.shortValue() << right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.shortValue() << right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  break;
               case 7:
                  return IntConstant.fromValue(left.shortValue() << (int)right.longValue());
               case 10:
                  return IntConstant.fromValue(left.shortValue() << right.intValue());
            }
         case 5:
         case 6:
         case 8:
         case 9:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return LongConstant.fromValue(left.longValue() << right.charValue());
               case 3:
                  return LongConstant.fromValue(left.longValue() << right.byteValue());
               case 4:
                  return LongConstant.fromValue(left.longValue() << right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue(left.longValue() << (int)right.longValue());
               case 10:
                  return LongConstant.fromValue(left.longValue() << right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.intValue() << right.charValue());
               case 3:
                  return IntConstant.fromValue(left.intValue() << right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.intValue() << right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  break;
               case 7:
                  return IntConstant.fromValue(left.intValue() << (int)right.longValue());
               case 10:
                  return IntConstant.fromValue(left.intValue() << right.intValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationLESS(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.charValue() < right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.charValue() < right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.charValue() < right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue((long)left.charValue() < right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.charValue() < right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.charValue() < right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.charValue() < right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.byteValue() < right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.byteValue() < right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.byteValue() < right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue((long)left.byteValue() < right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.byteValue() < right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.byteValue() < right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.byteValue() < right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.shortValue() < right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.shortValue() < right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.shortValue() < right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return BooleanConstant.fromValue((long)left.shortValue() < right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.shortValue() < right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.shortValue() < right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.shortValue() < right.intValue());
            }
         case 5:
         case 6:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.longValue() < (long)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.longValue() < (long)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.longValue() < (long)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue(left.longValue() < right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.longValue() < right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.longValue() < right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.longValue() < (long)right.intValue());
            }
         case 8:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.doubleValue() < (double)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.doubleValue() < (double)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.doubleValue() < (double)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue(left.doubleValue() < (double)right.longValue());
               case 8:
                  return BooleanConstant.fromValue(left.doubleValue() < right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue(left.doubleValue() < (double)right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.doubleValue() < (double)right.intValue());
            }
         case 9:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.floatValue() < (float)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.floatValue() < (float)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.floatValue() < (float)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue(left.floatValue() < (float)right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.floatValue() < right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue(left.floatValue() < right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.floatValue() < (float)right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.intValue() < right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.intValue() < right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.intValue() < right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return BooleanConstant.fromValue((long)left.intValue() < right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.intValue() < right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.intValue() < right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.intValue() < right.intValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationLESS_EQUAL(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.charValue() <= right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.charValue() <= right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.charValue() <= right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue((long)left.charValue() <= right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.charValue() <= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.charValue() <= right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.charValue() <= right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.byteValue() <= right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.byteValue() <= right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.byteValue() <= right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue((long)left.byteValue() <= right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.byteValue() <= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.byteValue() <= right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.byteValue() <= right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.shortValue() <= right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.shortValue() <= right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.shortValue() <= right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return BooleanConstant.fromValue((long)left.shortValue() <= right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.shortValue() <= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.shortValue() <= right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.shortValue() <= right.intValue());
            }
         case 5:
         case 6:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.longValue() <= (long)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.longValue() <= (long)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.longValue() <= (long)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue(left.longValue() <= right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.longValue() <= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.longValue() <= right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.longValue() <= (long)right.intValue());
            }
         case 8:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.doubleValue() <= (double)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.doubleValue() <= (double)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.doubleValue() <= (double)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue(left.doubleValue() <= (double)right.longValue());
               case 8:
                  return BooleanConstant.fromValue(left.doubleValue() <= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue(left.doubleValue() <= (double)right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.doubleValue() <= (double)right.intValue());
            }
         case 9:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.floatValue() <= (float)right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.floatValue() <= (float)right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.floatValue() <= (float)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return BooleanConstant.fromValue(left.floatValue() <= (float)right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.floatValue() <= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue(left.floatValue() <= right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.floatValue() <= (float)right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return BooleanConstant.fromValue(left.intValue() <= right.charValue());
               case 3:
                  return BooleanConstant.fromValue(left.intValue() <= right.byteValue());
               case 4:
                  return BooleanConstant.fromValue(left.intValue() <= right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return BooleanConstant.fromValue((long)left.intValue() <= right.longValue());
               case 8:
                  return BooleanConstant.fromValue((double)left.intValue() <= right.doubleValue());
               case 9:
                  return BooleanConstant.fromValue((float)left.intValue() <= right.floatValue());
               case 10:
                  return BooleanConstant.fromValue(left.intValue() <= right.intValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationMINUS(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.charValue() - right.charValue());
               case 3:
                  return IntConstant.fromValue(left.charValue() - right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.charValue() - right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.charValue() - right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.charValue() - right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.charValue() - right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.charValue() - right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.byteValue() - right.charValue());
               case 3:
                  return IntConstant.fromValue(left.byteValue() - right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.byteValue() - right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.byteValue() - right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.byteValue() - right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.byteValue() - right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.byteValue() - right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.shortValue() - right.charValue());
               case 3:
                  return IntConstant.fromValue(left.shortValue() - right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.shortValue() - right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return LongConstant.fromValue((long)left.shortValue() - right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.shortValue() - right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.shortValue() - right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.shortValue() - right.intValue());
            }
         case 5:
         case 6:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return LongConstant.fromValue(left.longValue() - (long)right.charValue());
               case 3:
                  return LongConstant.fromValue(left.longValue() - (long)right.byteValue());
               case 4:
                  return LongConstant.fromValue(left.longValue() - (long)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue(left.longValue() - right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.longValue() - right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.longValue() - right.floatValue());
               case 10:
                  return LongConstant.fromValue(left.longValue() - (long)right.intValue());
            }
         case 8:
            switch (rightId) {
               case 2:
                  return DoubleConstant.fromValue(left.doubleValue() - (double)right.charValue());
               case 3:
                  return DoubleConstant.fromValue(left.doubleValue() - (double)right.byteValue());
               case 4:
                  return DoubleConstant.fromValue(left.doubleValue() - (double)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return DoubleConstant.fromValue(left.doubleValue() - (double)right.longValue());
               case 8:
                  return DoubleConstant.fromValue(left.doubleValue() - right.doubleValue());
               case 9:
                  return DoubleConstant.fromValue(left.doubleValue() - (double)right.floatValue());
               case 10:
                  return DoubleConstant.fromValue(left.doubleValue() - (double)right.intValue());
            }
         case 9:
            switch (rightId) {
               case 2:
                  return FloatConstant.fromValue(left.floatValue() - (float)right.charValue());
               case 3:
                  return FloatConstant.fromValue(left.floatValue() - (float)right.byteValue());
               case 4:
                  return FloatConstant.fromValue(left.floatValue() - (float)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return FloatConstant.fromValue(left.floatValue() - (float)right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.floatValue() - right.doubleValue());
               case 9:
                  return FloatConstant.fromValue(left.floatValue() - right.floatValue());
               case 10:
                  return FloatConstant.fromValue(left.floatValue() - (float)right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.intValue() - right.charValue());
               case 3:
                  return IntConstant.fromValue(left.intValue() - right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.intValue() - right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return LongConstant.fromValue((long)left.intValue() - right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.intValue() - right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.intValue() - right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.intValue() - right.intValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationMULTIPLY(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.charValue() * right.charValue());
               case 3:
                  return IntConstant.fromValue(left.charValue() * right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.charValue() * right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.charValue() * right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.charValue() * right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.charValue() * right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.charValue() * right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.byteValue() * right.charValue());
               case 3:
                  return IntConstant.fromValue(left.byteValue() * right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.byteValue() * right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.byteValue() * right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.byteValue() * right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.byteValue() * right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.byteValue() * right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.shortValue() * right.charValue());
               case 3:
                  return IntConstant.fromValue(left.shortValue() * right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.shortValue() * right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return LongConstant.fromValue((long)left.shortValue() * right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.shortValue() * right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.shortValue() * right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.shortValue() * right.intValue());
            }
         case 5:
         case 6:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return LongConstant.fromValue(left.longValue() * (long)right.charValue());
               case 3:
                  return LongConstant.fromValue(left.longValue() * (long)right.byteValue());
               case 4:
                  return LongConstant.fromValue(left.longValue() * (long)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue(left.longValue() * right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.longValue() * right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.longValue() * right.floatValue());
               case 10:
                  return LongConstant.fromValue(left.longValue() * (long)right.intValue());
            }
         case 8:
            switch (rightId) {
               case 2:
                  return DoubleConstant.fromValue(left.doubleValue() * (double)right.charValue());
               case 3:
                  return DoubleConstant.fromValue(left.doubleValue() * (double)right.byteValue());
               case 4:
                  return DoubleConstant.fromValue(left.doubleValue() * (double)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return DoubleConstant.fromValue(left.doubleValue() * (double)right.longValue());
               case 8:
                  return DoubleConstant.fromValue(left.doubleValue() * right.doubleValue());
               case 9:
                  return DoubleConstant.fromValue(left.doubleValue() * (double)right.floatValue());
               case 10:
                  return DoubleConstant.fromValue(left.doubleValue() * (double)right.intValue());
            }
         case 9:
            switch (rightId) {
               case 2:
                  return FloatConstant.fromValue(left.floatValue() * (float)right.charValue());
               case 3:
                  return FloatConstant.fromValue(left.floatValue() * (float)right.byteValue());
               case 4:
                  return FloatConstant.fromValue(left.floatValue() * (float)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return FloatConstant.fromValue(left.floatValue() * (float)right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.floatValue() * right.doubleValue());
               case 9:
                  return FloatConstant.fromValue(left.floatValue() * right.floatValue());
               case 10:
                  return FloatConstant.fromValue(left.floatValue() * (float)right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.intValue() * right.charValue());
               case 3:
                  return IntConstant.fromValue(left.intValue() * right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.intValue() * right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return LongConstant.fromValue((long)left.intValue() * right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.intValue() * right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.intValue() * right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.intValue() * right.intValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationOR(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.charValue() | right.charValue());
               case 3:
                  return IntConstant.fromValue(left.charValue() | right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.charValue() | right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.charValue() | right.longValue());
               case 10:
                  return IntConstant.fromValue(left.charValue() | right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.byteValue() | right.charValue());
               case 3:
                  return IntConstant.fromValue(left.byteValue() | right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.byteValue() | right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.byteValue() | right.longValue());
               case 10:
                  return IntConstant.fromValue(left.byteValue() | right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.shortValue() | right.charValue());
               case 3:
                  return IntConstant.fromValue(left.shortValue() | right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.shortValue() | right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.shortValue() | right.longValue());
               case 10:
                  return IntConstant.fromValue(left.shortValue() | right.intValue());
            }
         case 5:
            return BooleanConstant.fromValue(left.booleanValue() | right.booleanValue());
         case 6:
         case 8:
         case 9:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return LongConstant.fromValue(left.longValue() | (long)right.charValue());
               case 3:
                  return LongConstant.fromValue(left.longValue() | (long)right.byteValue());
               case 4:
                  return LongConstant.fromValue(left.longValue() | (long)right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue(left.longValue() | right.longValue());
               case 10:
                  return LongConstant.fromValue(left.longValue() | (long)right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.intValue() | right.charValue());
               case 3:
                  return IntConstant.fromValue(left.intValue() | right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.intValue() | right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  break;
               case 7:
                  return LongConstant.fromValue((long)left.intValue() | right.longValue());
               case 10:
                  return IntConstant.fromValue(left.intValue() | right.intValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationOR_OR(Constant left, int leftId, Constant right, int rightId) {
      return BooleanConstant.fromValue(left.booleanValue() || right.booleanValue());
   }

   public static final Constant computeConstantOperationPLUS(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 1:
            if (rightId == 11) {
               return StringConstant.fromValue(left.stringValue() + right.stringValue());
            }
            break;
         case 2:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.charValue() + right.charValue());
               case 3:
                  return IntConstant.fromValue(left.charValue() + right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.charValue() + right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.charValue() + right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.charValue() + right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.charValue() + right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.charValue() + right.intValue());
               case 11:
                  return StringConstant.fromValue(left.stringValue() + right.stringValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.byteValue() + right.charValue());
               case 3:
                  return IntConstant.fromValue(left.byteValue() + right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.byteValue() + right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.byteValue() + right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.byteValue() + right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.byteValue() + right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.byteValue() + right.intValue());
               case 11:
                  return StringConstant.fromValue(left.stringValue() + right.stringValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.shortValue() + right.charValue());
               case 3:
                  return IntConstant.fromValue(left.shortValue() + right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.shortValue() + right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.shortValue() + right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.shortValue() + right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.shortValue() + right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.shortValue() + right.intValue());
               case 11:
                  return StringConstant.fromValue(left.stringValue() + right.stringValue());
            }
         case 5:
            if (rightId == 11) {
               return StringConstant.fromValue(left.stringValue() + right.stringValue());
            }
         case 6:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return LongConstant.fromValue(left.longValue() + (long)right.charValue());
               case 3:
                  return LongConstant.fromValue(left.longValue() + (long)right.byteValue());
               case 4:
                  return LongConstant.fromValue(left.longValue() + (long)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue(left.longValue() + right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.longValue() + right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.longValue() + right.floatValue());
               case 10:
                  return LongConstant.fromValue(left.longValue() + (long)right.intValue());
               case 11:
                  return StringConstant.fromValue(left.stringValue() + right.stringValue());
            }
         case 8:
            switch (rightId) {
               case 2:
                  return DoubleConstant.fromValue(left.doubleValue() + (double)right.charValue());
               case 3:
                  return DoubleConstant.fromValue(left.doubleValue() + (double)right.byteValue());
               case 4:
                  return DoubleConstant.fromValue(left.doubleValue() + (double)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return DoubleConstant.fromValue(left.doubleValue() + (double)right.longValue());
               case 8:
                  return DoubleConstant.fromValue(left.doubleValue() + right.doubleValue());
               case 9:
                  return DoubleConstant.fromValue(left.doubleValue() + (double)right.floatValue());
               case 10:
                  return DoubleConstant.fromValue(left.doubleValue() + (double)right.intValue());
               case 11:
                  return StringConstant.fromValue(left.stringValue() + right.stringValue());
            }
         case 9:
            switch (rightId) {
               case 2:
                  return FloatConstant.fromValue(left.floatValue() + (float)right.charValue());
               case 3:
                  return FloatConstant.fromValue(left.floatValue() + (float)right.byteValue());
               case 4:
                  return FloatConstant.fromValue(left.floatValue() + (float)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return FloatConstant.fromValue(left.floatValue() + (float)right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.floatValue() + right.doubleValue());
               case 9:
                  return FloatConstant.fromValue(left.floatValue() + right.floatValue());
               case 10:
                  return FloatConstant.fromValue(left.floatValue() + (float)right.intValue());
               case 11:
                  return StringConstant.fromValue(left.stringValue() + right.stringValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.intValue() + right.charValue());
               case 3:
                  return IntConstant.fromValue(left.intValue() + right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.intValue() + right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.intValue() + right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.intValue() + right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.intValue() + right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.intValue() + right.intValue());
               case 11:
                  return StringConstant.fromValue(left.stringValue() + right.stringValue());
            }
         case 11:
            switch (rightId) {
               case 2:
                  return StringConstant.fromValue(left.stringValue() + String.valueOf(right.charValue()));
               case 3:
                  return StringConstant.fromValue(left.stringValue() + String.valueOf(right.byteValue()));
               case 4:
                  return StringConstant.fromValue(left.stringValue() + String.valueOf(right.shortValue()));
               case 5:
                  return StringConstant.fromValue(left.stringValue() + right.booleanValue());
               case 6:
               default:
                  break;
               case 7:
                  return StringConstant.fromValue(left.stringValue() + String.valueOf(right.longValue()));
               case 8:
                  return StringConstant.fromValue(left.stringValue() + String.valueOf(right.doubleValue()));
               case 9:
                  return StringConstant.fromValue(left.stringValue() + String.valueOf(right.floatValue()));
               case 10:
                  return StringConstant.fromValue(left.stringValue() + String.valueOf(right.intValue()));
               case 11:
                  return StringConstant.fromValue(left.stringValue() + right.stringValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationREMAINDER(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.charValue() % right.charValue());
               case 3:
                  return IntConstant.fromValue(left.charValue() % right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.charValue() % right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.charValue() % right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.charValue() % right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.charValue() % right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.charValue() % right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.byteValue() % right.charValue());
               case 3:
                  return IntConstant.fromValue(left.byteValue() % right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.byteValue() % right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.byteValue() % right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.byteValue() % right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.byteValue() % right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.byteValue() % right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.shortValue() % right.charValue());
               case 3:
                  return IntConstant.fromValue(left.shortValue() % right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.shortValue() % right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return LongConstant.fromValue((long)left.shortValue() % right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.shortValue() % right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.shortValue() % right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.shortValue() % right.intValue());
            }
         case 5:
         case 6:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return LongConstant.fromValue(left.longValue() % (long)right.charValue());
               case 3:
                  return LongConstant.fromValue(left.longValue() % (long)right.byteValue());
               case 4:
                  return LongConstant.fromValue(left.longValue() % (long)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue(left.longValue() % right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.longValue() % right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.longValue() % right.floatValue());
               case 10:
                  return LongConstant.fromValue(left.longValue() % (long)right.intValue());
            }
         case 8:
            switch (rightId) {
               case 2:
                  return DoubleConstant.fromValue(left.doubleValue() % (double)right.charValue());
               case 3:
                  return DoubleConstant.fromValue(left.doubleValue() % (double)right.byteValue());
               case 4:
                  return DoubleConstant.fromValue(left.doubleValue() % (double)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return DoubleConstant.fromValue(left.doubleValue() % (double)right.longValue());
               case 8:
                  return DoubleConstant.fromValue(left.doubleValue() % right.doubleValue());
               case 9:
                  return DoubleConstant.fromValue(left.doubleValue() % (double)right.floatValue());
               case 10:
                  return DoubleConstant.fromValue(left.doubleValue() % (double)right.intValue());
            }
         case 9:
            switch (rightId) {
               case 2:
                  return FloatConstant.fromValue(left.floatValue() % (float)right.charValue());
               case 3:
                  return FloatConstant.fromValue(left.floatValue() % (float)right.byteValue());
               case 4:
                  return FloatConstant.fromValue(left.floatValue() % (float)right.shortValue());
               case 5:
               case 6:
               default:
                  return NotAConstant;
               case 7:
                  return FloatConstant.fromValue(left.floatValue() % (float)right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.floatValue() % right.doubleValue());
               case 9:
                  return FloatConstant.fromValue(left.floatValue() % right.floatValue());
               case 10:
                  return FloatConstant.fromValue(left.floatValue() % (float)right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.intValue() % right.charValue());
               case 3:
                  return IntConstant.fromValue(left.intValue() % right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.intValue() % right.shortValue());
               case 5:
               case 6:
               default:
                  break;
               case 7:
                  return LongConstant.fromValue((long)left.intValue() % right.longValue());
               case 8:
                  return DoubleConstant.fromValue((double)left.intValue() % right.doubleValue());
               case 9:
                  return FloatConstant.fromValue((float)left.intValue() % right.floatValue());
               case 10:
                  return IntConstant.fromValue(left.intValue() % right.intValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationRIGHT_SHIFT(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.charValue() >> right.charValue());
               case 3:
                  return IntConstant.fromValue(left.charValue() >> right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.charValue() >> right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return IntConstant.fromValue(left.charValue() >> (int)right.longValue());
               case 10:
                  return IntConstant.fromValue(left.charValue() >> right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.byteValue() >> right.charValue());
               case 3:
                  return IntConstant.fromValue(left.byteValue() >> right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.byteValue() >> right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return IntConstant.fromValue(left.byteValue() >> (int)right.longValue());
               case 10:
                  return IntConstant.fromValue(left.byteValue() >> right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.shortValue() >> right.charValue());
               case 3:
                  return IntConstant.fromValue(left.shortValue() >> right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.shortValue() >> right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  break;
               case 7:
                  return IntConstant.fromValue(left.shortValue() >> (int)right.longValue());
               case 10:
                  return IntConstant.fromValue(left.shortValue() >> right.intValue());
            }
         case 5:
         case 6:
         case 8:
         case 9:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return LongConstant.fromValue(left.longValue() >> right.charValue());
               case 3:
                  return LongConstant.fromValue(left.longValue() >> right.byteValue());
               case 4:
                  return LongConstant.fromValue(left.longValue() >> right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue(left.longValue() >> (int)right.longValue());
               case 10:
                  return LongConstant.fromValue(left.longValue() >> right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.intValue() >> right.charValue());
               case 3:
                  return IntConstant.fromValue(left.intValue() >> right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.intValue() >> right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  break;
               case 7:
                  return IntConstant.fromValue(left.intValue() >> (int)right.longValue());
               case 10:
                  return IntConstant.fromValue(left.intValue() >> right.intValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationUNSIGNED_RIGHT_SHIFT(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.charValue() >>> right.charValue());
               case 3:
                  return IntConstant.fromValue(left.charValue() >>> right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.charValue() >>> right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return IntConstant.fromValue(left.charValue() >>> (int)right.longValue());
               case 10:
                  return IntConstant.fromValue(left.charValue() >>> right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.byteValue() >>> right.charValue());
               case 3:
                  return IntConstant.fromValue(left.byteValue() >>> right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.byteValue() >>> right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return IntConstant.fromValue(left.byteValue() >>> (int)right.longValue());
               case 10:
                  return IntConstant.fromValue(left.byteValue() >>> right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.shortValue() >>> right.charValue());
               case 3:
                  return IntConstant.fromValue(left.shortValue() >>> right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.shortValue() >>> right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  break;
               case 7:
                  return IntConstant.fromValue(left.shortValue() >>> (int)right.longValue());
               case 10:
                  return IntConstant.fromValue(left.shortValue() >>> right.intValue());
            }
         case 5:
         case 6:
         case 8:
         case 9:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return LongConstant.fromValue(left.longValue() >>> right.charValue());
               case 3:
                  return LongConstant.fromValue(left.longValue() >>> right.byteValue());
               case 4:
                  return LongConstant.fromValue(left.longValue() >>> right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue(left.longValue() >>> (int)right.longValue());
               case 10:
                  return LongConstant.fromValue(left.longValue() >>> right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.intValue() >>> right.charValue());
               case 3:
                  return IntConstant.fromValue(left.intValue() >>> right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.intValue() >>> right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  break;
               case 7:
                  return IntConstant.fromValue(left.intValue() >>> (int)right.longValue());
               case 10:
                  return IntConstant.fromValue(left.intValue() >>> right.intValue());
            }
      }

      return NotAConstant;
   }

   public static final Constant computeConstantOperationXOR(Constant left, int leftId, Constant right, int rightId) {
      switch (leftId) {
         case 2:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.charValue() ^ right.charValue());
               case 3:
                  return IntConstant.fromValue(left.charValue() ^ right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.charValue() ^ right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.charValue() ^ right.longValue());
               case 10:
                  return IntConstant.fromValue(left.charValue() ^ right.intValue());
            }
         case 3:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.byteValue() ^ right.charValue());
               case 3:
                  return IntConstant.fromValue(left.byteValue() ^ right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.byteValue() ^ right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.byteValue() ^ right.longValue());
               case 10:
                  return IntConstant.fromValue(left.byteValue() ^ right.intValue());
            }
         case 4:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.shortValue() ^ right.charValue());
               case 3:
                  return IntConstant.fromValue(left.shortValue() ^ right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.shortValue() ^ right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue((long)left.shortValue() ^ right.longValue());
               case 10:
                  return IntConstant.fromValue(left.shortValue() ^ right.intValue());
            }
         case 5:
            return BooleanConstant.fromValue(left.booleanValue() ^ right.booleanValue());
         case 6:
         case 8:
         case 9:
         default:
            break;
         case 7:
            switch (rightId) {
               case 2:
                  return LongConstant.fromValue(left.longValue() ^ (long)right.charValue());
               case 3:
                  return LongConstant.fromValue(left.longValue() ^ (long)right.byteValue());
               case 4:
                  return LongConstant.fromValue(left.longValue() ^ (long)right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  return NotAConstant;
               case 7:
                  return LongConstant.fromValue(left.longValue() ^ right.longValue());
               case 10:
                  return LongConstant.fromValue(left.longValue() ^ (long)right.intValue());
            }
         case 10:
            switch (rightId) {
               case 2:
                  return IntConstant.fromValue(left.intValue() ^ right.charValue());
               case 3:
                  return IntConstant.fromValue(left.intValue() ^ right.byteValue());
               case 4:
                  return IntConstant.fromValue(left.intValue() ^ right.shortValue());
               case 5:
               case 6:
               case 8:
               case 9:
               default:
                  break;
               case 7:
                  return LongConstant.fromValue((long)left.intValue() ^ right.longValue());
               case 10:
                  return IntConstant.fromValue(left.intValue() ^ right.intValue());
            }
      }

      return NotAConstant;
   }

   public double doubleValue() {
      throw new ShouldNotImplement(Messages.bind(Messages.constant_cannotCastedInto, (Object[])(new String[]{this.typeName(), "double"})));
   }

   public float floatValue() {
      throw new ShouldNotImplement(Messages.bind(Messages.constant_cannotCastedInto, (Object[])(new String[]{this.typeName(), "float"})));
   }

   public boolean hasSameValue(Constant otherConstant) {
      if (this == otherConstant) {
         return true;
      } else {
         int typeID;
         if ((typeID = this.typeID()) != otherConstant.typeID()) {
            return false;
         } else {
            switch (typeID) {
               case 2:
                  if (this.charValue() == otherConstant.charValue()) {
                     return true;
                  }

                  return false;
               case 3:
                  if (this.byteValue() == otherConstant.byteValue()) {
                     return true;
                  }

                  return false;
               case 4:
                  if (this.shortValue() == otherConstant.shortValue()) {
                     return true;
                  }

                  return false;
               case 5:
                  if (this.booleanValue() == otherConstant.booleanValue()) {
                     return true;
                  }

                  return false;
               case 6:
               default:
                  return false;
               case 7:
                  if (this.longValue() == otherConstant.longValue()) {
                     return true;
                  }

                  return false;
               case 8:
                  if (this.doubleValue() == otherConstant.doubleValue()) {
                     return true;
                  }

                  return false;
               case 9:
                  if (this.floatValue() == otherConstant.floatValue()) {
                     return true;
                  }

                  return false;
               case 10:
                  if (this.intValue() == otherConstant.intValue()) {
                     return true;
                  }

                  return false;
               case 11:
                  String value = this.stringValue();
                  return value == null ? otherConstant.stringValue() == null : value.equals(otherConstant.stringValue());
            }
         }
      }
   }

   public int intValue() {
      throw new ShouldNotImplement(Messages.bind(Messages.constant_cannotCastedInto, (Object[])(new String[]{this.typeName(), "int"})));
   }

   public long longValue() {
      throw new ShouldNotImplement(Messages.bind(Messages.constant_cannotCastedInto, (Object[])(new String[]{this.typeName(), "long"})));
   }

   public short shortValue() {
      throw new ShouldNotImplement(Messages.bind(Messages.constant_cannotConvertedTo, (Object[])(new String[]{this.typeName(), "short"})));
   }

   public String stringValue() {
      throw new ShouldNotImplement(Messages.bind(Messages.constant_cannotConvertedTo, (Object[])(new String[]{this.typeName(), "String"})));
   }

   public String toString() {
      return this == NotAConstant ? "(Constant) NotAConstant" : super.toString();
   }

   public abstract int typeID();

   public String typeName() {
      switch (this.typeID()) {
         case 2:
            return "char";
         case 3:
            return "byte";
         case 4:
            return "short";
         case 5:
            return "boolean";
         case 6:
         default:
            return "unknown";
         case 7:
            return "long";
         case 8:
            return "double";
         case 9:
            return "float";
         case 10:
            return "int";
         case 11:
            return "java.lang.String";
      }
   }
}

package org.python.icu.text;

import java.text.FieldPosition;
import java.text.Format;

/** @deprecated */
@Deprecated
public class UFieldPosition extends FieldPosition {
   private int countVisibleFractionDigits = -1;
   private long fractionDigits = 0L;

   /** @deprecated */
   @Deprecated
   public UFieldPosition() {
      super(-1);
   }

   /** @deprecated */
   @Deprecated
   public UFieldPosition(int field) {
      super(field);
   }

   /** @deprecated */
   @Deprecated
   public UFieldPosition(Format.Field attribute, int fieldID) {
      super(attribute, fieldID);
   }

   /** @deprecated */
   @Deprecated
   public UFieldPosition(Format.Field attribute) {
      super(attribute);
   }

   /** @deprecated */
   @Deprecated
   public void setFractionDigits(int countVisibleFractionDigits, long fractionDigits) {
      this.countVisibleFractionDigits = countVisibleFractionDigits;
      this.fractionDigits = fractionDigits;
   }

   /** @deprecated */
   @Deprecated
   public int getCountVisibleFractionDigits() {
      return this.countVisibleFractionDigits;
   }

   /** @deprecated */
   @Deprecated
   public long getFractionDigits() {
      return this.fractionDigits;
   }
}

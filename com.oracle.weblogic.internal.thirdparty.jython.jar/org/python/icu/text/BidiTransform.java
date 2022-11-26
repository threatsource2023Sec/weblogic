package org.python.icu.text;

import org.python.icu.lang.UCharacter;

public class BidiTransform {
   private Bidi bidi;
   private String text;
   private int reorderingOptions;
   private int shapingOptions;

   public String transform(CharSequence text, byte inParaLevel, Order inOrder, byte outParaLevel, Order outOrder, Mirroring doMirroring, int shapingOptions) {
      if (text != null && inOrder != null && outOrder != null && doMirroring != null) {
         this.text = text.toString();
         byte[] levels = new byte[]{inParaLevel, outParaLevel};
         this.resolveBaseDirection(levels);
         ReorderingScheme currentScheme = this.findMatchingScheme(levels[0], inOrder, levels[1], outOrder);
         if (currentScheme != null) {
            this.bidi = new Bidi();
            this.reorderingOptions = BidiTransform.Mirroring.ON.equals(doMirroring) ? 2 : 0;
            this.shapingOptions = shapingOptions & -5;
            currentScheme.doTransform(this);
         }

         return this.text;
      } else {
         throw new IllegalArgumentException();
      }
   }

   private void resolveBaseDirection(byte[] levels) {
      if (Bidi.IsDefaultLevel(levels[0])) {
         byte level = Bidi.getBaseDirection(this.text);
         levels[0] = (byte)(level != 3 ? level : (levels[0] == 127 ? 1 : 0));
      } else {
         levels[0] = (byte)(levels[0] & 1);
      }

      if (Bidi.IsDefaultLevel(levels[1])) {
         levels[1] = levels[0];
      } else {
         levels[1] = (byte)(levels[1] & 1);
      }

   }

   private ReorderingScheme findMatchingScheme(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
      ReorderingScheme[] var5 = BidiTransform.ReorderingScheme.values();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ReorderingScheme scheme = var5[var7];
         if (scheme.matches(inLevel, inOrder, outLevel, outOrder)) {
            return scheme;
         }
      }

      return null;
   }

   private void resolve(byte level, int options) {
      this.bidi.setInverse((options & 5) != 0);
      this.bidi.setReorderingMode(options);
      this.bidi.setPara((String)this.text, level, (byte[])null);
   }

   private void reorder() {
      this.text = this.bidi.writeReordered(this.reorderingOptions);
      this.reorderingOptions = 0;
   }

   private void reverse() {
      this.text = Bidi.writeReverse(this.text, 0);
   }

   private void mirror() {
      if ((this.reorderingOptions & 2) != 0) {
         StringBuffer sb = new StringBuffer(this.text);
         byte[] levels = this.bidi.getLevels();
         int i = 0;

         int ch;
         for(int n = levels.length; i < n; i += UTF16.getCharCount(ch)) {
            ch = UTF16.charAt(sb, i);
            if ((levels[i] & 1) != 0) {
               UTF16.setCharAt(sb, i, UCharacter.getMirror(ch));
            }
         }

         this.text = sb.toString();
         this.reorderingOptions &= -3;
      }
   }

   private void shapeArabic(int digitsDir, int lettersDir) {
      if (digitsDir == lettersDir) {
         this.shapeArabic(this.shapingOptions | digitsDir);
      } else {
         this.shapeArabic(this.shapingOptions & -25 | digitsDir);
         this.shapeArabic(this.shapingOptions & -225 | lettersDir);
      }

   }

   private void shapeArabic(int options) {
      if (options != 0) {
         ArabicShaping shaper = new ArabicShaping(options);

         try {
            this.text = shaper.shape(this.text);
         } catch (ArabicShapingException var4) {
         }
      }

   }

   private static boolean IsLTR(byte level) {
      return (level & 1) == 0;
   }

   private static boolean IsRTL(byte level) {
      return (level & 1) == 1;
   }

   private static boolean IsLogical(Order order) {
      return BidiTransform.Order.LOGICAL.equals(order);
   }

   private static boolean IsVisual(Order order) {
      return BidiTransform.Order.VISUAL.equals(order);
   }

   private static enum ReorderingScheme {
      LOG_LTR_TO_VIS_LTR {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsLTR(inLevel) && BidiTransform.IsLogical(inOrder) && BidiTransform.IsLTR(outLevel) && BidiTransform.IsVisual(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.shapeArabic(0, 0);
            transform.resolve((byte)0, 0);
            transform.reorder();
         }
      },
      LOG_RTL_TO_VIS_LTR {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsRTL(inLevel) && BidiTransform.IsLogical(inOrder) && BidiTransform.IsLTR(outLevel) && BidiTransform.IsVisual(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.resolve((byte)1, 0);
            transform.reorder();
            transform.shapeArabic(0, 4);
         }
      },
      LOG_LTR_TO_VIS_RTL {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsLTR(inLevel) && BidiTransform.IsLogical(inOrder) && BidiTransform.IsRTL(outLevel) && BidiTransform.IsVisual(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.shapeArabic(0, 0);
            transform.resolve((byte)0, 0);
            transform.reorder();
            transform.reverse();
         }
      },
      LOG_RTL_TO_VIS_RTL {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsRTL(inLevel) && BidiTransform.IsLogical(inOrder) && BidiTransform.IsRTL(outLevel) && BidiTransform.IsVisual(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.resolve((byte)1, 0);
            transform.reorder();
            transform.shapeArabic(0, 4);
            transform.reverse();
         }
      },
      VIS_LTR_TO_LOG_RTL {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsLTR(inLevel) && BidiTransform.IsVisual(inOrder) && BidiTransform.IsRTL(outLevel) && BidiTransform.IsLogical(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.shapeArabic(0, 4);
            transform.resolve((byte)1, 5);
            transform.reorder();
         }
      },
      VIS_RTL_TO_LOG_RTL {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsRTL(inLevel) && BidiTransform.IsVisual(inOrder) && BidiTransform.IsRTL(outLevel) && BidiTransform.IsLogical(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.reverse();
            transform.shapeArabic(0, 4);
            transform.resolve((byte)1, 5);
            transform.reorder();
         }
      },
      VIS_LTR_TO_LOG_LTR {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsLTR(inLevel) && BidiTransform.IsVisual(inOrder) && BidiTransform.IsLTR(outLevel) && BidiTransform.IsLogical(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.resolve((byte)0, 5);
            transform.reorder();
            transform.shapeArabic(0, 0);
         }
      },
      VIS_RTL_TO_LOG_LTR {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsRTL(inLevel) && BidiTransform.IsVisual(inOrder) && BidiTransform.IsLTR(outLevel) && BidiTransform.IsLogical(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.reverse();
            transform.resolve((byte)0, 5);
            transform.reorder();
            transform.shapeArabic(0, 0);
         }
      },
      LOG_LTR_TO_LOG_RTL {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsLTR(inLevel) && BidiTransform.IsLogical(inOrder) && BidiTransform.IsRTL(outLevel) && BidiTransform.IsLogical(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.shapeArabic(0, 0);
            transform.resolve((byte)0, 0);
            transform.mirror();
            transform.resolve((byte)0, 3);
            transform.reorder();
         }
      },
      LOG_RTL_TO_LOG_LTR {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsRTL(inLevel) && BidiTransform.IsLogical(inOrder) && BidiTransform.IsLTR(outLevel) && BidiTransform.IsLogical(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.resolve((byte)1, 0);
            transform.mirror();
            transform.resolve((byte)1, 3);
            transform.reorder();
            transform.shapeArabic(0, 0);
         }
      },
      VIS_LTR_TO_VIS_RTL {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsLTR(inLevel) && BidiTransform.IsVisual(inOrder) && BidiTransform.IsRTL(outLevel) && BidiTransform.IsVisual(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.resolve((byte)0, 0);
            transform.mirror();
            transform.shapeArabic(0, 4);
            transform.reverse();
         }
      },
      VIS_RTL_TO_VIS_LTR {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsRTL(inLevel) && BidiTransform.IsVisual(inOrder) && BidiTransform.IsLTR(outLevel) && BidiTransform.IsVisual(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.reverse();
            transform.resolve((byte)0, 0);
            transform.mirror();
            transform.shapeArabic(0, 4);
         }
      },
      LOG_LTR_TO_LOG_LTR {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsLTR(inLevel) && BidiTransform.IsLogical(inOrder) && BidiTransform.IsLTR(outLevel) && BidiTransform.IsLogical(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.resolve((byte)0, 0);
            transform.mirror();
            transform.shapeArabic(0, 0);
         }
      },
      LOG_RTL_TO_LOG_RTL {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsRTL(inLevel) && BidiTransform.IsLogical(inOrder) && BidiTransform.IsRTL(outLevel) && BidiTransform.IsLogical(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.resolve((byte)1, 0);
            transform.mirror();
            transform.shapeArabic(4, 0);
         }
      },
      VIS_LTR_TO_VIS_LTR {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsLTR(inLevel) && BidiTransform.IsVisual(inOrder) && BidiTransform.IsLTR(outLevel) && BidiTransform.IsVisual(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.resolve((byte)0, 0);
            transform.mirror();
            transform.shapeArabic(0, 4);
         }
      },
      VIS_RTL_TO_VIS_RTL {
         boolean matches(byte inLevel, Order inOrder, byte outLevel, Order outOrder) {
            return BidiTransform.IsRTL(inLevel) && BidiTransform.IsVisual(inOrder) && BidiTransform.IsRTL(outLevel) && BidiTransform.IsVisual(outOrder);
         }

         void doTransform(BidiTransform transform) {
            transform.reverse();
            transform.resolve((byte)0, 0);
            transform.mirror();
            transform.shapeArabic(0, 4);
            transform.reverse();
         }
      };

      private ReorderingScheme() {
      }

      abstract boolean matches(byte var1, Order var2, byte var3, Order var4);

      abstract void doTransform(BidiTransform var1);

      // $FF: synthetic method
      ReorderingScheme(Object x2) {
         this();
      }
   }

   public static enum Mirroring {
      OFF,
      ON;
   }

   public static enum Order {
      LOGICAL,
      VISUAL;
   }
}

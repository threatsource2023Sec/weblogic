package org.python.icu.impl.number;

import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import org.python.icu.text.PluralRules;

public abstract class Format {
   protected static final ThreadLocal threadLocalStringBuilder = new ThreadLocal() {
      protected NumberStringBuilder initialValue() {
         return new NumberStringBuilder();
      }
   };
   protected static final ThreadLocal threadLocalModifierHolder = new ThreadLocal() {
      protected ModifierHolder initialValue() {
         return new ModifierHolder();
      }
   };

   public String format(FormatQuantity... inputs) {
      Deque inputDeque = new ArrayDeque();
      inputDeque.addAll(Arrays.asList(inputs));
      ModifierHolder modDeque = ((ModifierHolder)threadLocalModifierHolder.get()).clear();
      NumberStringBuilder sb = ((NumberStringBuilder)threadLocalStringBuilder.get()).clear();
      int length = this.process(inputDeque, modDeque, sb, 0);
      modDeque.applyAll(sb, 0, length);
      return sb.toString();
   }

   public abstract int process(Deque var1, ModifierHolder var2, NumberStringBuilder var3, int var4);

   public interface AfterFormat extends Exportable {
      int after(ModifierHolder var1, NumberStringBuilder var2, int var3, int var4);
   }

   public interface TargetFormat extends Exportable {
      int target(FormatQuantity var1, NumberStringBuilder var2, int var3);
   }

   public abstract static class BeforeFormat implements Exportable {
      protected abstract void before(FormatQuantity var1, ModifierHolder var2);

      public void before(FormatQuantity input, ModifierHolder mods, PluralRules rules) {
         this.before(input, mods);
      }
   }

   public static class PositiveNegativeRounderTargetFormat extends SingularFormat {
      private final Modifier.PositiveNegativeModifier positiveNegative;
      private final Rounder rounder;
      private final TargetFormat target;

      public PositiveNegativeRounderTargetFormat(Modifier.PositiveNegativeModifier positiveNegative, Rounder rounder, TargetFormat target) {
         this.positiveNegative = positiveNegative;
         this.rounder = rounder;
         this.target = target;
      }

      public String format(FormatQuantity input) {
         NumberStringBuilder sb = ((NumberStringBuilder)threadLocalStringBuilder.get()).clear();
         this.process(input, (ModifierHolder)null, sb, 0);
         return sb.toString();
      }

      public int process(FormatQuantity input, ModifierHolder mods, NumberStringBuilder string, int startIndex) {
         Modifier mod = null;
         this.rounder.apply(input);
         if (!input.isNaN() && this.positiveNegative != null) {
            mod = this.positiveNegative.getModifier(input.isNegative());
         }

         int length = this.target.target(input, string, startIndex);
         if (mod != null) {
            length += mod.apply(string, 0, length);
         }

         return length;
      }

      public void export(Properties properties) {
         this.rounder.export(properties);
         this.positiveNegative.export(properties);
         this.target.export(properties);
      }
   }

   public static class BeforeTargetAfterFormat extends SingularFormat {
      private BeforeFormat before1 = null;
      private BeforeFormat before2 = null;
      private BeforeFormat before3 = null;
      private TargetFormat target = null;
      private AfterFormat after1 = null;
      private AfterFormat after2 = null;
      private AfterFormat after3 = null;
      private final PluralRules rules;

      public BeforeTargetAfterFormat(PluralRules rules) {
         this.rules = rules;
      }

      public void addBeforeFormat(BeforeFormat before) {
         if (this.before1 == null) {
            this.before1 = before;
         } else if (this.before2 == null) {
            this.before2 = before;
         } else {
            if (this.before3 != null) {
               throw new IllegalArgumentException("Only three BeforeFormats are allowed at a time");
            }

            this.before3 = before;
         }

      }

      public void setTargetFormat(TargetFormat target) {
         this.target = target;
      }

      public void addAfterFormat(AfterFormat after) {
         if (this.after1 == null) {
            this.after1 = after;
         } else if (this.after2 == null) {
            this.after2 = after;
         } else {
            if (this.after3 != null) {
               throw new IllegalArgumentException("Only three AfterFormats are allowed at a time");
            }

            this.after3 = after;
         }

      }

      public String format(FormatQuantity input) {
         ModifierHolder mods = ((ModifierHolder)threadLocalModifierHolder.get()).clear();
         NumberStringBuilder sb = ((NumberStringBuilder)threadLocalStringBuilder.get()).clear();
         int length = this.process(input, mods, sb, 0);
         mods.applyAll(sb, 0, length);
         return sb.toString();
      }

      public int process(FormatQuantity input, ModifierHolder mods, NumberStringBuilder string, int startIndex) {
         int length = false;
         if (!input.isNaN()) {
            if (this.before1 != null) {
               this.before1.before(input, mods, this.rules);
            }

            if (this.before2 != null) {
               this.before2.before(input, mods, this.rules);
            }

            if (this.before3 != null) {
               this.before3.before(input, mods, this.rules);
            }
         }

         int length = this.target.target(input, string, startIndex);
         length += mods.applyStrong(string, startIndex, startIndex + length);
         if (this.after1 != null) {
            length += this.after1.after(mods, string, startIndex, startIndex + length);
         }

         if (this.after2 != null) {
            length += this.after2.after(mods, string, startIndex, startIndex + length);
         }

         if (this.after3 != null) {
            length += this.after3.after(mods, string, startIndex, startIndex + length);
         }

         return length;
      }

      public void export(Properties properties) {
         if (this.before1 != null) {
            this.before1.export(properties);
         }

         if (this.before2 != null) {
            this.before2.export(properties);
         }

         if (this.before3 != null) {
            this.before3.export(properties);
         }

         this.target.export(properties);
         if (this.after1 != null) {
            this.after1.export(properties);
         }

         if (this.after2 != null) {
            this.after2.export(properties);
         }

         if (this.after3 != null) {
            this.after3.export(properties);
         }

      }
   }

   public abstract static class SingularFormat extends Format implements Exportable {
      public String format(FormatQuantity input) {
         NumberStringBuilder sb = this.formatToStringBuilder(input);
         return sb.toString();
      }

      public void format(FormatQuantity input, StringBuffer output) {
         NumberStringBuilder sb = this.formatToStringBuilder(input);
         output.append(sb);
      }

      public String format(FormatQuantity input, FieldPosition fp) {
         NumberStringBuilder sb = this.formatToStringBuilder(input);
         sb.populateFieldPosition(fp, 0);
         return sb.toString();
      }

      public void format(FormatQuantity input, StringBuffer output, FieldPosition fp) {
         NumberStringBuilder sb = this.formatToStringBuilder(input);
         sb.populateFieldPosition(fp, output.length());
         output.append(sb);
      }

      public AttributedCharacterIterator formatToCharacterIterator(FormatQuantity input) {
         NumberStringBuilder sb = this.formatToStringBuilder(input);
         return sb.getIterator();
      }

      private NumberStringBuilder formatToStringBuilder(FormatQuantity input) {
         ModifierHolder modDeque = ((ModifierHolder)threadLocalModifierHolder.get()).clear();
         NumberStringBuilder sb = ((NumberStringBuilder)threadLocalStringBuilder.get()).clear();
         int length = this.process((FormatQuantity)input, modDeque, sb, 0);
         length += modDeque.applyAll(sb, 0, length);
         return sb;
      }

      public int process(Deque input, ModifierHolder mods, NumberStringBuilder string, int startIndex) {
         return this.process((FormatQuantity)input.removeFirst(), mods, string, startIndex);
      }

      public abstract int process(FormatQuantity var1, ModifierHolder var2, NumberStringBuilder var3, int var4);
   }
}

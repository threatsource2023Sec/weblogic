package org.mozilla.javascript.tools.idswitch;

import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.tools.ToolErrorReporter;

public class SwitchGenerator {
   String v_switch_label = "L0";
   String v_label = "L";
   String v_s = "s";
   String v_c = "c";
   String v_guess = "X";
   String v_id = "id";
   String v_length_suffix = "_length";
   int use_if_threshold = 3;
   int char_tail_test_threshold = 2;
   private IdValuePair[] pairs;
   private String default_value;
   private int[] columns;
   private boolean c_was_defined;
   private CodePrinter P;
   private ToolErrorReporter R;
   private String source_file;

   private static boolean bigger(IdValuePair var0, IdValuePair var1, int var2) {
      if (var2 < 0) {
         int var3 = var0.idLength - var1.idLength;
         if (var3 != 0) {
            return var3 > 0;
         } else {
            return var0.id.compareTo(var1.id) > 0;
         }
      } else {
         return var0.id.charAt(var2) > var1.id.charAt(var2);
      }
   }

   private void check_all_is_different(int var1, int var2) {
      if (var1 != var2) {
         IdValuePair var3 = this.pairs[var1];

         while(true) {
            ++var1;
            if (var1 == var2) {
               break;
            }

            IdValuePair var4 = this.pairs[var1];
            if (var3.id.equals(var4.id)) {
               throw this.on_same_pair_fail(var3, var4);
            }

            var3 = var4;
         }
      }

   }

   private int count_different_chars(int var1, int var2, int var3) {
      int var4 = 0;

      for(int var5 = -1; var1 != var2; ++var1) {
         char var6 = this.pairs[var1].id.charAt(var3);
         if (var6 != var5) {
            ++var4;
            var5 = var6;
         }
      }

      return var4;
   }

   private int count_different_lengths(int var1, int var2) {
      int var3 = 0;

      for(int var4 = -1; var1 != var2; ++var1) {
         int var5 = this.pairs[var1].idLength;
         if (var4 != var5) {
            ++var3;
            var4 = var5;
         }
      }

      return var3;
   }

   private int find_max_different_column(int var1, int var2, int var3) {
      int var4 = 0;
      int var5 = 0;

      for(int var6 = 0; var6 != var3; ++var6) {
         int var7 = this.columns[var6];
         this.sort_pairs(var1, var2, var7);
         int var8 = this.count_different_chars(var1, var2, var7);
         if (var8 == var2 - var1) {
            return var6;
         }

         if (var4 < var8) {
            var4 = var8;
            var5 = var6;
         }
      }

      if (var5 != var3 - 1) {
         this.sort_pairs(var1, var2, this.columns[var5]);
      }

      return var5;
   }

   public void generateSwitch(String[] var1, String var2) {
      int var3 = var1.length / 2;
      IdValuePair[] var4 = new IdValuePair[var3];

      for(int var5 = 0; var5 != var3; ++var5) {
         var4[var5] = new IdValuePair(var1[2 * var5], var1[2 * var5 + 1]);
      }

      this.generateSwitch(var4, var2);
   }

   public void generateSwitch(IdValuePair[] var1, String var2) {
      byte var3 = 0;
      int var4 = var1.length;
      if (var3 != var4) {
         this.pairs = var1;
         this.default_value = var2;
         this.generate_body(var3, var4, 2);
      }
   }

   private void generate_body(int var1, int var2, int var3) {
      this.P.indent(var3);
      this.P.p(this.v_switch_label);
      this.P.p(": { ");
      this.P.p(this.v_id);
      this.P.p(" = ");
      this.P.p(this.default_value);
      this.P.p("; String ");
      this.P.p(this.v_guess);
      this.P.p(" = null;");
      this.c_was_defined = false;
      int var4 = this.P.getOffset();
      this.P.p(" int ");
      this.P.p(this.v_c);
      this.P.p(';');
      int var5 = this.P.getOffset();
      this.P.nl();
      this.generate_length_switch(var1, var2, var3 + 1);
      if (!this.c_was_defined) {
         this.P.erase(var4, var5);
      }

      this.P.indent(var3 + 1);
      this.P.p("if (");
      this.P.p(this.v_guess);
      this.P.p("!=null && ");
      this.P.p(this.v_guess);
      this.P.p("!=");
      this.P.p(this.v_s);
      this.P.p(" && !");
      this.P.p(this.v_guess);
      this.P.p(".equals(");
      this.P.p(this.v_s);
      this.P.p(")) ");
      this.P.p(this.v_id);
      this.P.p(" = ");
      this.P.p(this.default_value);
      this.P.p(";");
      this.P.nl();
      this.P.line(var3, "}");
   }

   private void generate_length_switch(int var1, int var2, int var3) {
      this.sort_pairs(var1, var2, -1);
      this.check_all_is_different(var1, var2);
      int var4 = this.count_different_lengths(var1, var2);
      this.columns = new int[this.pairs[var2 - 1].idLength];
      boolean var5;
      if (var4 <= this.use_if_threshold) {
         var5 = true;
         if (var4 != 1) {
            this.P.indent(var3);
            this.P.p("int ");
            this.P.p(this.v_s);
            this.P.p(this.v_length_suffix);
            this.P.p(" = ");
            this.P.p(this.v_s);
            this.P.p(".length();");
            this.P.nl();
         }
      } else {
         var5 = false;
         this.P.indent(var3);
         this.P.p(this.v_label);
         this.P.p(": switch (");
         this.P.p(this.v_s);
         this.P.p(".length()) {");
         this.P.nl();
      }

      int var6 = var1;
      int var7 = this.pairs[var1].idLength;
      int var8 = 0;
      int var9 = var1;

      while(true) {
         do {
            ++var9;
         } while(var9 != var2 && (var8 = this.pairs[var9].idLength) == var7);

         int var10;
         if (var5) {
            this.P.indent(var3);
            if (var6 != var1) {
               this.P.p("else ");
            }

            this.P.p("if (");
            if (var4 == 1) {
               this.P.p(this.v_s);
               this.P.p(".length()==");
            } else {
               this.P.p(this.v_s);
               this.P.p(this.v_length_suffix);
               this.P.p("==");
            }

            this.P.p(var7);
            this.P.p(") {");
            var10 = var3 + 1;
         } else {
            this.P.indent(var3);
            this.P.p("case ");
            this.P.p(var7);
            this.P.p(":");
            var10 = var3 + 1;
         }

         this.generate_letter_switch(var6, var9, var10, var5 ^ true, var5);
         if (var5) {
            this.P.p("}");
            this.P.nl();
         } else {
            this.P.p("break ");
            this.P.p(this.v_label);
            this.P.p(";");
            this.P.nl();
         }

         if (var9 == var2) {
            if (!var5) {
               this.P.indent(var3);
               this.P.p("}");
               this.P.nl();
            }

            return;
         }

         var6 = var9;
         var7 = var8;
      }
   }

   private void generate_letter_switch(int var1, int var2, int var3, boolean var4, boolean var5) {
      int var6 = this.pairs[var1].idLength;

      for(int var7 = 0; var7 != var6; this.columns[var7] = var7++) {
      }

      this.generate_letter_switch_r(var1, var2, var6, var3, var4, var5);
   }

   private boolean generate_letter_switch_r(int var1, int var2, int var3, int var4, boolean var5, boolean var6) {
      boolean var7 = false;
      int var9;
      int var10;
      if (var1 + 1 == var2) {
         this.P.p(' ');
         IdValuePair var18 = this.pairs[var1];
         if (var3 > this.char_tail_test_threshold) {
            this.P.p(this.v_guess);
            this.P.p("=");
            this.P.qstring(var18.id);
            this.P.p(";");
            this.P.p(this.v_id);
            this.P.p("=");
            this.P.p(var18.value);
            this.P.p(";");
         } else if (var3 == 0) {
            var7 = true;
            this.P.p(this.v_id);
            this.P.p("=");
            this.P.p(var18.value);
            this.P.p("; break ");
            this.P.p(this.v_switch_label);
            this.P.p(";");
         } else {
            this.P.p("if (");
            var9 = this.columns[0];
            this.P.p(this.v_s);
            this.P.p(".charAt(");
            this.P.p(var9);
            this.P.p(")==");
            this.P.qchar(var18.id.charAt(var9));

            for(var10 = 1; var10 != var3; ++var10) {
               this.P.p(" && ");
               var9 = this.columns[var10];
               this.P.p(this.v_s);
               this.P.p(".charAt(");
               this.P.p(var9);
               this.P.p(")==");
               this.P.qchar(var18.id.charAt(var9));
            }

            this.P.p(") {");
            this.P.p(this.v_id);
            this.P.p("=");
            this.P.p(var18.value);
            this.P.p("; break ");
            this.P.p(this.v_switch_label);
            this.P.p(";}");
         }

         this.P.p(' ');
         return var7;
      } else {
         int var8 = this.find_max_different_column(var1, var2, var3);
         var9 = this.columns[var8];
         var10 = this.count_different_chars(var1, var2, var9);
         this.columns[var8] = this.columns[var3 - 1];
         if (var6) {
            this.P.nl();
            this.P.indent(var4);
         } else {
            this.P.p(' ');
         }

         boolean var11;
         if (var10 <= this.use_if_threshold) {
            var11 = true;
            this.c_was_defined = true;
            this.P.p(this.v_c);
            this.P.p("=");
            this.P.p(this.v_s);
            this.P.p(".charAt(");
            this.P.p(var9);
            this.P.p(");");
         } else {
            var11 = false;
            if (!var5) {
               var5 = true;
               this.P.p(this.v_label);
               this.P.p(": ");
            }

            this.P.p("switch (");
            this.P.p(this.v_s);
            this.P.p(".charAt(");
            this.P.p(var9);
            this.P.p(")) {");
         }

         int var12 = var1;
         char var13 = this.pairs[var1].id.charAt(var9);
         char var14 = 0;
         int var15 = var1;

         while(true) {
            do {
               ++var15;
            } while(var15 != var2 && (var14 = this.pairs[var15].id.charAt(var9)) == var13);

            int var16;
            if (var11) {
               this.P.nl();
               this.P.indent(var4);
               if (var12 != var1) {
                  this.P.p("else ");
               }

               this.P.p("if (");
               this.P.p(this.v_c);
               this.P.p("==");
               this.P.qchar(var13);
               this.P.p(") {");
               var16 = var4 + 1;
            } else {
               this.P.nl();
               this.P.indent(var4);
               this.P.p("case ");
               this.P.qchar(var13);
               this.P.p(":");
               var16 = var4 + 1;
            }

            boolean var17 = this.generate_letter_switch_r(var12, var15, var3 - 1, var16, var5, var11);
            if (var11) {
               this.P.p("}");
            } else if (!var17) {
               this.P.p("break ");
               this.P.p(this.v_label);
               this.P.p(";");
            }

            if (var15 == var2) {
               if (var11) {
                  this.P.nl();
                  if (var6) {
                     this.P.indent(var4 - 1);
                  } else {
                     this.P.indent(var4);
                  }
               } else {
                  this.P.nl();
                  this.P.indent(var4);
                  this.P.p("}");
                  if (var6) {
                     this.P.nl();
                     this.P.indent(var4 - 1);
                  } else {
                     this.P.p(' ');
                  }
               }

               this.columns[var8] = var9;
               return var7;
            }

            var12 = var15;
            var13 = var14;
         }
      }
   }

   public CodePrinter getCodePrinter() {
      return this.P;
   }

   public ToolErrorReporter getReporter() {
      return this.R;
   }

   public String getSourceFileName() {
      return this.source_file;
   }

   private static void heap4Sort(IdValuePair[] var0, int var1, int var2, int var3) {
      if (var2 > 1) {
         makeHeap4(var0, var1, var2, var3);

         while(var2 > 1) {
            --var2;
            IdValuePair var4 = var0[var1 + var2];
            IdValuePair var5 = var0[var1];
            var0[var1 + var2] = var5;
            var0[var1] = var4;
            heapify4(var0, var1, var2, 0, var3);
         }

      }
   }

   private static void heapify4(IdValuePair[] var0, int var1, int var2, int var3, int var4) {
      IdValuePair var8 = var0[var1 + var3];

      while(true) {
         int var9 = var3 << 2;
         int var5 = var9 | 1;
         int var6 = var9 | 2;
         int var7 = var9 | 3;
         int var10 = var9 + 4;
         IdValuePair var11;
         if (var10 >= var2) {
            if (var5 < var2) {
               IdValuePair var15 = var0[var1 + var5];
               if (var6 != var2) {
                  IdValuePair var16 = var0[var1 + var6];
                  if (bigger(var16, var15, var4)) {
                     var15 = var16;
                     var5 = var6;
                  }

                  if (var7 != var2) {
                     var11 = var0[var1 + var7];
                     if (bigger(var11, var15, var4)) {
                        var15 = var11;
                        var5 = var7;
                     }
                  }
               }

               if (bigger(var15, var8, var4)) {
                  var0[var1 + var3] = var15;
                  var0[var1 + var5] = var8;
               }
            }

            return;
         }

         var11 = var0[var1 + var5];
         IdValuePair var12 = var0[var1 + var6];
         IdValuePair var13 = var0[var1 + var7];
         IdValuePair var14 = var0[var1 + var10];
         if (bigger(var12, var11, var4)) {
            var11 = var12;
            var5 = var6;
         }

         if (bigger(var14, var13, var4)) {
            var13 = var14;
            var7 = var10;
         }

         if (bigger(var13, var11, var4)) {
            var11 = var13;
            var5 = var7;
         }

         if (bigger(var8, var11, var4)) {
            return;
         }

         var0[var1 + var3] = var11;
         var0[var1 + var5] = var8;
         var3 = var5;
      }
   }

   private static void makeHeap4(IdValuePair[] var0, int var1, int var2, int var3) {
      int var4 = var2 + 2 >> 2;

      while(var4 != 0) {
         --var4;
         heapify4(var0, var1, var2, var4, var3);
      }

   }

   private EvaluatorException on_same_pair_fail(IdValuePair var1, IdValuePair var2) {
      new StringBuffer();
      int var4 = var1.getLineNumber();
      int var5 = var2.getLineNumber();
      if (var5 > var4) {
         int var6 = var4;
         var4 = var5;
         var5 = var6;
      }

      String var7 = ToolErrorReporter.getMessage("msg.idswitch.same_string", var1.id, new Integer(var5));
      return this.R.runtimeError(var7, this.source_file, var4, (String)null, 0);
   }

   public void setCodePrinter(CodePrinter var1) {
      this.P = var1;
   }

   public void setReporter(ToolErrorReporter var1) {
      this.R = var1;
   }

   public void setSourceFileName(String var1) {
      this.source_file = var1;
   }

   private void sort_pairs(int var1, int var2, int var3) {
      heap4Sort(this.pairs, var1, var2 - var1, var3);
   }
}

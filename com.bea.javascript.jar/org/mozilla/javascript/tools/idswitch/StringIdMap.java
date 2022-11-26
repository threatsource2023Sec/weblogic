package org.mozilla.javascript.tools.idswitch;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.tools.ToolErrorReporter;

public class StringIdMap {
   private static final String PROGRAM_NAME = "StringIdMap";
   private static final String SWITCH_TAG_STR = "string_id_map";
   private static final String GENERATED_TAG_STR = "generated";
   private static final String STRING_TAG_STR = "string";
   private static final int NORMAL_LINE = 0;
   private static final int SWITCH_TAG = 1;
   private static final int GENERATED_TAG = 2;
   private static final int STRING_TAG = 3;
   private final Vector all_pairs = new Vector();
   private ToolErrorReporter R;
   private CodePrinter P;
   private FileBody body;
   private String source_file;
   private int tag_definition_end;
   private int tag_value_start;
   private int tag_value_end;

   private void add_id(char[] var1, int var2, int var3, int var4, int var5) {
      String var6 = new String(var1, var4, var5 - var4);
      String var7 = new String(var1, var2, var3 - var2);
      IdValuePair var8 = new IdValuePair(var6, var7);
      var8.setLineNumber(this.body.getLineNumber());
      this.all_pairs.addElement(var8);
   }

   private static boolean equals(String var0, char[] var1, int var2, int var3) {
      if (var0.length() != var3 - var2) {
         return false;
      } else {
         int var4 = var2;

         for(int var5 = 0; var4 != var3; ++var5) {
            if (var1[var4] != var0.charAt(var5)) {
               return false;
            }

            ++var4;
         }

         return true;
      }
   }

   private int exec(String[] var1) {
      this.R = new ToolErrorReporter(true, System.err);
      int var2 = this.process_options(var1);
      if (var2 == 0) {
         this.option_error(ToolErrorReporter.getMessage("msg.idswitch.no_file_argument"));
         return -1;
      } else if (var2 > 1) {
         this.option_error(ToolErrorReporter.getMessage("msg.idswitch.too_many_arguments"));
         return -1;
      } else {
         this.P = new CodePrinter();
         this.P.setIndentStep(4);
         this.P.setIndentTabSize(0);

         try {
            this.process_file(var1[0]);
            return 0;
         } catch (IOException var4) {
            this.print_error(ToolErrorReporter.getMessage("msg.idswitch.io_error", var4.toString()));
            return -1;
         } catch (EvaluatorException var5) {
            return -1;
         }
      }
   }

   private int extract_line_tag_id(char[] var1, int var2, int var3) {
      int var4 = 0;
      var2 = skip_white_space(var1, var2, var3);
      int var5 = var2;
      var2 = this.look_for_slash_slash(var1, var2, var3);
      if (var2 != var3) {
         boolean var6 = var5 + 2 == var2;
         var2 = skip_white_space(var1, var2, var3);
         if (var2 != var3 && var1[var2] == '#') {
            ++var2;
            boolean var7 = false;
            if (var2 != var3 && var1[var2] == '/') {
               ++var2;
               var7 = true;
            }

            int var8;
            int var9;
            for(var8 = var2; var2 != var3; ++var2) {
               var9 = var1[var2];
               if (var9 == 35 || var9 == 61 || is_white_space(var9)) {
                  break;
               }
            }

            if (var2 != var3) {
               var9 = var2;
               var2 = skip_white_space(var1, var2, var3);
               if (var2 != var3) {
                  char var10 = var1[var2];
                  if (var10 == '=' || var10 == '#') {
                     var4 = this.get_tag_id(var1, var8, var9, var6);
                     if (var4 != 0) {
                        String var11 = null;
                        if (var10 == '#') {
                           if (var7) {
                              var4 = -var4;
                              if (is_value_type(var4)) {
                                 var11 = "msg.idswitch.no_end_usage";
                              }
                           }

                           this.tag_definition_end = var2 + 1;
                        } else {
                           if (var7) {
                              var11 = "msg.idswitch.no_end_with_value";
                           } else if (!is_value_type(var4)) {
                              var11 = "msg.idswitch.no_value_allowed";
                           }

                           var4 = this.extract_tag_value(var1, var2 + 1, var3, var4);
                        }

                        if (var11 != null) {
                           String var12 = ToolErrorReporter.getMessage(var11, tag_name(var4));
                           throw this.R.runtimeError(var12, this.source_file, this.body.getLineNumber(), (String)null, 0);
                        }
                     }
                  }
               }
            }
         }
      }

      return var4;
   }

   private int extract_tag_value(char[] var1, int var2, int var3, int var4) {
      boolean var5 = false;
      var2 = skip_white_space(var1, var2, var3);
      if (var2 != var3) {
         int var6 = var2;
         int var7 = var2;

         while(var2 != var3) {
            char var8 = var1[var2];
            if (is_white_space(var8)) {
               int var9 = skip_white_space(var1, var2 + 1, var3);
               if (var9 != var3 && var1[var9] == '#') {
                  var7 = var2;
                  var2 = var9;
                  break;
               }

               var2 = var9 + 1;
            } else {
               if (var8 == '#') {
                  var7 = var2;
                  break;
               }

               ++var2;
            }
         }

         if (var2 != var3) {
            var5 = true;
            this.tag_value_start = var6;
            this.tag_value_end = var7;
            this.tag_definition_end = var2 + 1;
         }
      }

      return var5 ? var4 : 0;
   }

   private void generate_java_code() {
      this.P.clear();
      IdValuePair[] var1 = new IdValuePair[this.all_pairs.size()];
      this.all_pairs.copyInto(var1);
      SwitchGenerator var2 = new SwitchGenerator();
      var2.char_tail_test_threshold = 2;
      var2.setReporter(this.R);
      var2.setCodePrinter(this.P);
      var2.generateSwitch(var1, "0");
   }

   private int get_tag_id(char[] var1, int var2, int var3, boolean var4) {
      if (var4) {
         if (equals("string_id_map", var1, var2, var3)) {
            return 1;
         }

         if (equals("generated", var1, var2, var3)) {
            return 2;
         }
      }

      return equals("string", var1, var2, var3) ? 3 : 0;
   }

   private String get_time_stamp() {
      SimpleDateFormat var1 = new SimpleDateFormat(" 'Last update:' yyyy-MM-dd HH:mm:ss z");
      var1.format(new Date());
      return var1.format(new Date());
   }

   private static boolean is_value_type(int var0) {
      return var0 == 3;
   }

   private static boolean is_white_space(int var0) {
      return var0 == 32 || var0 == 9;
   }

   private void look_for_id_definitions(char[] var1, int var2, int var3, boolean var4) {
      int var5 = skip_white_space(var1, var2, var3);
      int var6 = var5;
      int var7 = skip_matched_prefix("Id_", var1, var5, var3);
      if (var7 >= 0) {
         var5 = skip_name_char(var1, var7, var3);
         int var8 = var5;
         if (var7 != var5) {
            var5 = skip_white_space(var1, var5, var3);
            if (var5 != var3 && var1[var5] == '=') {
               if (var4) {
                  var7 = this.tag_value_start;
                  var8 = this.tag_value_end;
               }

               this.add_id(var1, var6, var8, var7, var8);
            }
         }
      }

   }

   private int look_for_slash_slash(char[] var1, int var2, int var3) {
      while(var2 + 2 <= var3) {
         char var4 = var1[var2++];
         if (var4 == '/') {
            var4 = var1[var2++];
            if (var4 == '/') {
               return var2;
            }
         }
      }

      return var3;
   }

   public static void main(String[] var0) {
      StringIdMap var1 = new StringIdMap();
      int var2 = var1.exec(var0);
      System.exit(var2);
   }

   private void option_error(String var1) {
      this.print_error(ToolErrorReporter.getMessage("msg.idswitch.bad_invocation", var1));
   }

   private void print_error(String var1) {
      System.err.println(var1);
   }

   private void process_file() throws IOException {
      byte var1 = 0;
      char[] var2 = this.body.getBuffer();
      int var3 = -1;
      int var4 = -1;
      int var5 = -1;
      int var6 = -1;
      this.body.startLineLoop();

      int var9;
      boolean var10;
      String var11;
      do {
         if (!this.body.nextLine()) {
            if (var1 != 0) {
               String var14 = ToolErrorReporter.getMessage("msg.idswitch.file_end_in_switch", tag_name(var1));
               throw this.R.runtimeError(var14, this.source_file, this.body.getLineNumber(), (String)null, 0);
            }

            return;
         }

         int var7 = this.body.getLineBegin();
         int var8 = this.body.getLineEnd();
         var9 = this.extract_line_tag_id(var2, var7, var8);
         var10 = false;
         switch (var1) {
            case 0:
               if (var9 == 1) {
                  var1 = 1;
                  this.all_pairs.removeAllElements();
                  var3 = -1;
               } else if (var9 == -1) {
                  var10 = true;
               }
               break;
            case 1:
               if (var9 == 0) {
                  this.look_for_id_definitions(var2, var7, var8, false);
               } else if (var9 == 3) {
                  this.look_for_id_definitions(var2, var7, var8, true);
               } else if (var9 == 2) {
                  if (var3 >= 0) {
                     var10 = true;
                  } else {
                     var1 = 2;
                     var5 = this.tag_definition_end;
                     var6 = var8;
                  }
               } else if (var9 == -1) {
                  var1 = 0;
                  if (var3 >= 0 && !this.all_pairs.isEmpty()) {
                     this.generate_java_code();
                     var11 = this.P.toString();
                     boolean var12 = this.body.setReplacement(var3, var4, var11);
                     if (var12) {
                        String var13 = this.get_time_stamp();
                        this.body.setReplacement(var5, var6, var13);
                     }
                  }
               } else {
                  var10 = true;
               }
               break;
            case 2:
               if (var9 == 0) {
                  if (var3 < 0) {
                     var3 = var7;
                  }
               } else if (var9 == -2) {
                  if (var3 < 0) {
                     var3 = var7;
                  }

                  var1 = 1;
                  var4 = var7;
               } else {
                  var10 = true;
               }
         }
      } while(!var10);

      var11 = ToolErrorReporter.getMessage("msg.idswitch.bad_tag_order", tag_name(var9));
      throw this.R.runtimeError(var11, this.source_file, this.body.getLineNumber(), (String)null, 0);
   }

   void process_file(String var1) throws IOException {
      this.source_file = var1;
      this.body = new FileBody();
      Object var2;
      if (var1.equals("-")) {
         var2 = System.in;
      } else {
         var2 = new FileInputStream(var1);
      }

      try {
         InputStreamReader var5 = new InputStreamReader((InputStream)var2, "ASCII");
         this.body.readData(var5);
      } finally {
         ((InputStream)var2).close();
      }

      this.process_file();
      if (this.body.wasModified()) {
         Object var3;
         if (var1.equals("-")) {
            var3 = System.out;
         } else {
            var3 = new FileOutputStream(var1);
         }

         try {
            OutputStreamWriter var6 = new OutputStreamWriter((OutputStream)var3);
            this.body.writeData(var6);
            var6.flush();
         } finally {
            ((OutputStream)var3).close();
         }
      }

   }

   private int process_options(String[] var1) {
      byte var2 = 1;
      boolean var3 = false;
      boolean var4 = false;
      int var5 = var1.length;

      label59:
      for(int var6 = 0; var6 != var5; ++var6) {
         String var7 = var1[var6];
         int var8 = var7.length();
         if (var8 >= 2 && var7.charAt(0) == '-') {
            if (var7.charAt(1) == '-') {
               if (var8 == 2) {
                  var1[var6] = null;
                  break;
               }

               if (var7.equals("--help")) {
                  var3 = true;
               } else {
                  if (!var7.equals("--version")) {
                     this.option_error(ToolErrorReporter.getMessage("msg.idswitch.bad_option", var7));
                     var2 = -1;
                     break;
                  }

                  var4 = true;
               }
            } else {
               int var9 = 1;

               while(var9 != var8) {
                  char var10 = var7.charAt(var9);
                  switch (var10) {
                     case 'h':
                        var3 = true;
                        ++var9;
                        break;
                     default:
                        this.option_error(ToolErrorReporter.getMessage("msg.idswitch.bad_option_char", String.valueOf(var10)));
                        var2 = -1;
                        break label59;
                  }
               }
            }

            var1[var6] = null;
         }
      }

      if (var2 == 1) {
         if (var3) {
            this.show_usage();
            var2 = 0;
         }

         if (var4) {
            this.show_version();
            var2 = 0;
         }
      }

      if (var2 != 1) {
         System.exit(var2);
      }

      return this.remove_nulls(var1);
   }

   private int remove_nulls(String[] var1) {
      int var2 = var1.length;

      int var3;
      for(var3 = 0; var3 != var2 && var1[var3] != null; ++var3) {
      }

      int var4 = var3;
      if (var3 != var2) {
         ++var3;

         for(; var3 != var2; ++var3) {
            String var5 = var1[var3];
            if (var5 != null) {
               var1[var4] = var5;
               ++var4;
            }
         }
      }

      return var4;
   }

   private void show_usage() {
      System.out.println(ToolErrorReporter.getMessage("msg.idswitch.usage"));
      System.out.println();
   }

   private void show_version() {
      System.out.println(ToolErrorReporter.getMessage("msg.idswitch.version"));
   }

   private static int skip_matched_prefix(String var0, char[] var1, int var2, int var3) {
      int var4 = -1;
      int var5 = var0.length();
      if (var5 <= var3 - var2) {
         var4 = var2;

         for(int var6 = 0; var6 != var5; ++var4) {
            if (var0.charAt(var6) != var1[var4]) {
               var4 = -1;
               break;
            }

            ++var6;
         }
      }

      return var4;
   }

   private static int skip_name_char(char[] var0, int var1, int var2) {
      int var3;
      for(var3 = var1; var3 != var2; ++var3) {
         char var4 = var0[var3];
         if ((var4 < 'a' || var4 > 'z') && (var4 < 'A' || var4 > 'Z') && (var4 < '0' || var4 > '9') && var4 != '_') {
            break;
         }
      }

      return var3;
   }

   private static int skip_white_space(char[] var0, int var1, int var2) {
      int var3;
      for(var3 = var1; var3 != var2; ++var3) {
         char var4 = var0[var3];
         if (!is_white_space(var4)) {
            break;
         }
      }

      return var3;
   }

   private static String tag_name(int var0) {
      switch (var0) {
         case -2:
            return "/generated";
         case -1:
            return "/string_id_map";
         case 0:
         default:
            return "";
         case 1:
            return "string_id_map";
         case 2:
            return "generated";
      }
   }
}

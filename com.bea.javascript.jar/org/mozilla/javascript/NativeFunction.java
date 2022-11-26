package org.mozilla.javascript;

public class NativeFunction extends BaseFunction {
   private static final int OFFSET = 4;
   private static final int SETBACK = 2;
   private static final boolean printSource = false;
   protected String[] argNames;
   protected short argCount;
   protected short version;
   protected String source;
   public NativeFunction[] nestedFunctions;
   public int debug_level = -1;
   public String debug_srcName;

   private void decompile(int var1, boolean var2, boolean var3, StringBuffer var4) {
      if (this.source == null) {
         if (!var3) {
            var4.append("function ");
            var4.append(this.getFunctionName());
            var4.append("() {\n\t");
         }

         var4.append("[native code]\n");
         if (!var3) {
            var4.append("}\n");
         }

      } else {
         int var5 = 0;
         int var6;
         if (this.source.length() > 0) {
            if (var2) {
               if (!var3) {
                  var4.append('\n');
               }

               for(var6 = 0; var6 < var1; ++var6) {
                  var4.append(' ');
               }
            }

            if (this.source.charAt(0) == 'n' && this.source.length() > 1 && (this.source.charAt(1) == ',' || this.source.charAt(1) == '^')) {
               if (!var3) {
                  var4.append("function ");
                  if (this.nextIs(var5, 94) && this.version != 120 && super.functionName != null && super.functionName.equals("anonymous")) {
                     var4.append("anonymous");
                  }

                  ++var5;
               } else {
                  while(var5 < this.source.length() && (this.source.charAt(var5) != 1 || var5 > 0 && this.source.charAt(var5 - 1) == ',')) {
                     ++var5;
                  }

                  ++var5;
               }
            }
         }

         for(; var5 < this.source.length(); ++var5) {
            int var9;
            switch (this.source.charAt(var5)) {
               case '\u0001':
                  var4.append('\n');
                  if (var5 + 1 < this.source.length()) {
                     var9 = 0;
                     if (!this.nextIs(var5, 116) && !this.nextIs(var5, 117)) {
                        if (this.nextIs(var5, 93)) {
                           var9 = 4;
                        } else if (this.nextIs(var5, 44)) {
                           char var12 = this.source.charAt(var5 + 2);
                           if (this.source.charAt(var5 + var12 + 3) == 'c') {
                              var9 = 4;
                           }
                        }
                     } else {
                        var9 = 2;
                     }

                     while(var9 < var1) {
                        var4.append(' ');
                        ++var9;
                     }
                  }
                  break;
               case '\u0002':
               case '\u0003':
               case '\u0004':
               case '\u0006':
               case '\u0007':
               case '\b':
               case '\t':
               case '\n':
               case '\u000e':
               case '\u000f':
               case '\u0010':
               case '\u0011':
               case '\u0012':
               case '\u0013':
               case '\u0014':
               case '\u0015':
               case '\u0016':
               case '\u001c':
               case '\u001d':
               case ' ':
               case '!':
               case '"':
               case '#':
               case '$':
               case '%':
               case '&':
               case '\'':
               case '(':
               case ')':
               case '*':
               case '+':
               case '/':
               case '0':
               case '1':
               case '2':
               case '3':
               case '4':
               case '5':
               case '6':
               case '7':
               case '9':
               case ':':
               case ';':
               case '<':
               case '=':
               case '@':
               case 'A':
               case 'B':
               case 'C':
               case 'D':
               case 'E':
               case 'F':
               case 'G':
               case 'H':
               case 'I':
               case 'J':
               case 'L':
               case 'M':
               case 'N':
               case 'O':
               case 'P':
               case 'Q':
               case 'R':
               case 'S':
               case 'T':
               case 'U':
               case 'V':
               case 'W':
               case 'X':
               case 'o':
               case 'p':
               case '\u007f':
               case '\u0080':
               case '\u0081':
               case '\u0082':
               case '\u0083':
               case '\u0084':
               case '\u0085':
               case '\u0086':
               default:
                  throw new RuntimeException("Unknown token " + this.source.charAt(var5));
               case '\u0005':
                  if (this.nextIs(var5, 89)) {
                     var4.append("return");
                  } else {
                     var4.append("return ");
                  }
                  break;
               case '\u000b':
                  var4.append(" | ");
                  break;
               case '\f':
                  var4.append(" ^ ");
                  break;
               case '\r':
                  var4.append(" & ");
                  break;
               case '\u0017':
                  var4.append(" + ");
                  break;
               case '\u0018':
                  var4.append(" - ");
                  break;
               case '\u0019':
                  var4.append(" * ");
                  break;
               case '\u001a':
                  var4.append(" / ");
                  break;
               case '\u001b':
                  var4.append(" % ");
                  break;
               case '\u001e':
                  var4.append("new ");
                  break;
               case '\u001f':
                  var4.append("delete ");
                  break;
               case ',':
               case '8':
                  ++var5;
                  var6 = var5 + this.source.charAt(var5);
                  var4.append(this.source.substring(var5 + 1, var6 + 1));
                  var5 = var6;
                  break;
               case '-':
                  ++var5;
                  long var7 = 0L;
                  switch (this.source.charAt(var5)) {
                     case 'D':
                        ++var5;
                        var7 |= (long)this.source.charAt(var5++) << 48;
                        var7 |= (long)this.source.charAt(var5++) << 32;
                        var7 |= (long)this.source.charAt(var5++) << 16;
                        var7 |= (long)this.source.charAt(var5);
                        double var11 = Double.longBitsToDouble(var7);
                        var4.append(ScriptRuntime.numberToString(var11, 10));
                        continue;
                     case 'J':
                        ++var5;
                        var7 |= (long)this.source.charAt(var5++) << 48;
                        var7 |= (long)this.source.charAt(var5++) << 32;
                        var7 |= (long)this.source.charAt(var5++) << 16;
                        var7 |= (long)this.source.charAt(var5);
                        var4.append(var7);
                        continue;
                     case 'S':
                        ++var5;
                        var4.append(this.source.charAt(var5));
                     default:
                        continue;
                  }
               case '.':
                  ++var5;
                  var6 = var5 + this.source.charAt(var5);
                  var4.append('"');
                  var4.append(ScriptRuntime.escapeString(this.source.substring(var5 + 1, var6 + 1)));
                  var4.append('"');
                  var5 = var6;
                  break;
               case '>':
                  var4.append("throw ");
                  break;
               case '?':
                  var4.append(" in ");
                  break;
               case 'K':
                  var4.append("try ");
                  break;
               case 'Y':
                  if (this.nextIs(var5, 1)) {
                     var4.append(';');
                  } else {
                     var4.append("; ");
                  }
                  break;
               case 'Z':
                  var4.append('[');
                  break;
               case '[':
                  var4.append(']');
                  break;
               case '\\':
                  if (this.nextIs(var5, 1)) {
                     var1 += 4;
                  }

                  var4.append('{');
                  break;
               case ']':
                  if (!var3 || !var2 || var5 + 1 != this.source.length()) {
                     if (this.nextIs(var5, 1)) {
                        var1 -= 4;
                     }

                     if (!this.nextIs(var5, 118) && !this.nextIs(var5, 114)) {
                        var4.append('}');
                     } else {
                        var1 -= 4;
                        var4.append("} ");
                     }
                  }
                  break;
               case '^':
                  var4.append('(');
                  break;
               case '_':
                  if (this.nextIs(var5, 92)) {
                     var4.append(") ");
                  } else {
                     var4.append(')');
                  }
                  break;
               case '`':
                  var4.append(", ");
                  break;
               case 'a':
                  ++var5;
                  switch (this.source.charAt(var5)) {
                     case '\u000b':
                        var4.append(" |= ");
                        continue;
                     case '\f':
                        var4.append(" ^= ");
                        continue;
                     case '\r':
                        var4.append(" &= ");
                        continue;
                     case '\u0014':
                        var4.append(" <<= ");
                        continue;
                     case '\u0015':
                        var4.append(" >>= ");
                        continue;
                     case '\u0016':
                        var4.append(" >>>= ");
                        continue;
                     case '\u0017':
                        var4.append(" += ");
                        continue;
                     case '\u0018':
                        var4.append(" -= ");
                        continue;
                     case '\u0019':
                        var4.append(" *= ");
                        continue;
                     case '\u001a':
                        var4.append(" /= ");
                        continue;
                     case '\u001b':
                        var4.append(" %= ");
                        continue;
                     case '\u0080':
                        var4.append(" = ");
                     default:
                        continue;
                  }
               case 'b':
                  var4.append(" ? ");
                  break;
               case 'c':
                  if (this.nextIs(var5, 1)) {
                     var4.append(':');
                  } else {
                     var4.append(" : ");
                  }
                  break;
               case 'd':
                  var4.append(" || ");
                  break;
               case 'e':
                  var4.append(" && ");
                  break;
               case 'f':
                  ++var5;
                  switch (this.source.charAt(var5)) {
                     case '\u000e':
                        var4.append(" == ");
                        continue;
                     case '\u000f':
                        var4.append(" != ");
                        continue;
                     case '5':
                        var4.append(this.version == 120 ? " == " : " === ");
                        continue;
                     case '6':
                        var4.append(this.version == 120 ? " != " : " !== ");
                     default:
                        continue;
                  }
               case 'g':
                  ++var5;
                  switch (this.source.charAt(var5)) {
                     case '\u0010':
                        var4.append(" < ");
                        continue;
                     case '\u0011':
                        var4.append(" <= ");
                        continue;
                     case '\u0012':
                        var4.append(" > ");
                        continue;
                     case '\u0013':
                        var4.append(" >= ");
                        continue;
                     case '@':
                        var4.append(" instanceof ");
                     default:
                        continue;
                  }
               case 'h':
                  ++var5;
                  switch (this.source.charAt(var5)) {
                     case '\u0014':
                        var4.append(" << ");
                        continue;
                     case '\u0015':
                        var4.append(" >> ");
                        continue;
                     case '\u0016':
                        var4.append(" >>> ");
                     default:
                        continue;
                  }
               case 'i':
                  ++var5;
                  switch (this.source.charAt(var5)) {
                     case '\u0017':
                        var4.append('+');
                        continue;
                     case '\u0018':
                        var4.append('-');
                        continue;
                     case '\u001c':
                        var4.append('~');
                        continue;
                     case ' ':
                        var4.append("typeof ");
                        continue;
                     case '\u0081':
                        var4.append('!');
                        continue;
                     case '\u0084':
                        var4.append("void ");
                     default:
                        continue;
                  }
               case 'j':
                  var4.append("++");
                  break;
               case 'k':
                  var4.append("--");
                  break;
               case 'l':
                  var4.append('.');
                  break;
               case 'm':
                  ++var5;
                  switch (this.source.charAt(var5)) {
                     case ' ':
                        var4.append("typeof");
                        continue;
                     case '1':
                        var4.append("null");
                        continue;
                     case '2':
                        var4.append("this");
                        continue;
                     case '3':
                        var4.append("false");
                        continue;
                     case '4':
                        var4.append("true");
                        continue;
                     case 'J':
                        var4.append("undefined");
                        continue;
                     case '\u0084':
                        var4.append("void");
                     default:
                        continue;
                  }
               case 'n':
                  ++var5;
                  var9 = this.source.charAt(var5);
                  if (this.nestedFunctions == null || var9 > this.nestedFunctions.length) {
                     String var10;
                     if (super.functionName != null && super.functionName.length() > 0) {
                        var10 = Context.getMessage2("msg.no.function.ref.found.in", new Integer(this.source.charAt(var5)), super.functionName);
                     } else {
                        var10 = Context.getMessage1("msg.no.function.ref.found", new Integer(this.source.charAt(var5)));
                     }

                     throw Context.reportRuntimeError(var10);
                  }

                  this.nestedFunctions[var9].decompile(var1, false, false, var4);
                  break;
               case 'q':
                  var4.append("if ");
                  break;
               case 'r':
                  var4.append("else ");
                  break;
               case 's':
                  var4.append("switch ");
                  break;
               case 't':
                  var4.append("case ");
                  break;
               case 'u':
                  var4.append("default");
                  break;
               case 'v':
                  var4.append("while ");
                  break;
               case 'w':
                  var4.append("do ");
                  break;
               case 'x':
                  var4.append("for ");
                  break;
               case 'y':
                  if (this.nextIs(var5, 44)) {
                     var4.append("break ");
                  } else {
                     var4.append("break");
                  }
                  break;
               case 'z':
                  if (this.nextIs(var5, 44)) {
                     var4.append("continue ");
                  } else {
                     var4.append("continue");
                  }
                  break;
               case '{':
                  var4.append("var ");
                  break;
               case '|':
                  var4.append("with ");
                  break;
               case '}':
                  var4.append("catch ");
                  break;
               case '~':
                  var4.append("finally ");
                  break;
               case '\u0087':
                  var4.append(':');
            }
         }

         if (var2 && !var3) {
            var4.append('\n');
         }

      }
   }

   public String decompile(Context var1, int var2, boolean var3) {
      StringBuffer var4 = new StringBuffer();
      this.decompile(var2, true, var3, var4);
      return var4.toString();
   }

   public int getArity() {
      return this.argCount;
   }

   public String getFunctionName() {
      if (super.functionName == null) {
         return "";
      } else {
         if (super.functionName.equals("anonymous")) {
            Context var1 = Context.getCurrentContext();
            if (var1 != null && var1.getLanguageVersion() == 120) {
               return "";
            }
         }

         return super.functionName;
      }
   }

   public int getLength() {
      Context var1 = Context.getContext();
      if (var1 != null && var1.getLanguageVersion() != 120) {
         return this.argCount;
      } else {
         NativeCall var2 = this.getActivation(var1);
         return var2 == null ? this.argCount : var2.getOriginalArguments().length;
      }
   }

   public String jsGet_name() {
      return this.getFunctionName();
   }

   private boolean nextIs(int var1, int var2) {
      if (var1 + 1 < this.source.length()) {
         return this.source.charAt(var1 + 1) == var2;
      } else {
         return false;
      }
   }
}

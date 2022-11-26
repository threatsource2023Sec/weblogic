package weblogic.wtc.jatmi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class viewjCompiler {
   private boolean modifyStrings;
   private boolean beanNames;
   private boolean associatedFieldHandling = false;
   private boolean isView32;
   private boolean buildWith64Bit = false;
   private int currspot;
   private int input_string_length;
   private String input_string;

   private void write_error(int lineno, String explain) {
      System.out.println("Error at line " + lineno + ". " + explain + ".");
   }

   private void writeAccessorComment(BufferedWriter output_stream, ViewMember theMember, boolean isGetter, boolean isIndexed) throws IOException {
      try {
         if (isGetter) {
            output_stream.write("  /**\n");
            output_stream.write("   * Gets the value of the " + theMember.cname + " element of this view\n");
            if (isIndexed) {
               output_stream.write("   * @param index The element in the array to return\n");
            }

            output_stream.write("   * @return The value which this element has\n");
            if (isIndexed) {
               output_stream.write("   * @throws ArrayIndexOutOfBounds if the array index is out of bounds\n");
            }

            output_stream.write("   */\n");
         } else {
            output_stream.write("  /**\n");
            output_stream.write("   * Sets the value of the " + theMember.cname + " element of this view\n");
            output_stream.write("   * @param value The value to set the element to\n");
            if (isIndexed) {
               output_stream.write("   * @param index The element in the array to set\n");
               output_stream.write("   * @throws ArrayIndexOutOfBounds if the array index is out of bounds\n");
            } else if (theMember.count > 1) {
               output_stream.write("   * @throws ArrayIndexOutOfBounds if the array given is too large\n");
            }

            if (theMember.type == 5 || theMember.type == 6) {
               if (!isIndexed && theMember.count != 1) {
                  output_stream.write("   * @throws IllegalArgumentException if any of the values given are too long\n");
               } else {
                  output_stream.write("   * @throws IllegalArgumentException if the value is too long\n");
               }
            }

            output_stream.write("   */\n");
         }

      } catch (IOException var6) {
         throw var6;
      }
   }

   private void writeBoundsCheck(BufferedWriter output_stream, ViewMember theMember, boolean isIndexed) throws IOException {
      String lstr;
      if (theMember.type == 5) {
         lstr = "()";
      } else {
         lstr = "";
      }

      try {
         if (theMember.count != 1 && !isIndexed) {
            output_stream.write("    if (value.length > " + theMember.count + ")\n");
            output_stream.write("        throw new ArrayIndexOutOfBoundsException(\"Array too large for " + theMember.cname + "\");\n");
            if (theMember.type == 6 || theMember.type == 5) {
               output_stream.write("    for(int i = 0; i < value.length; i++)\n");
               output_stream.write("       if (value[i].length" + lstr + " > " + theMember.size + ")\n");
               output_stream.write("          throw new IllegalArgumentException(\"Data too long, value[\" + i + \"]\");\n");
            }
         } else {
            output_stream.write("    if (value.length" + lstr + " > " + theMember.size + ")\n");
            output_stream.write("         throw new IllegalArgumentException(\"Data too large for " + theMember.cname + "\");\n");
         }

      } catch (IOException var6) {
         throw var6;
      }
   }

   private void writeSetterArrayCopy(BufferedWriter output_stream, ViewMember theMember) throws IOException {
      try {
         if (18 == theMember.type || 16 == theMember.type || 15 == theMember.type) {
            output_stream.write("    for(int i = 0; i < value.length; i++) {\n");
            if (18 == theMember.type) {
               output_stream.write("      if (value[i]<0L || value[i]>4294967295L) {\n");
               output_stream.write("        throw new IllegalArgumentException(\"the value should be >=0 and <=4294967295L\");\n");
               output_stream.write("      }\n");
            } else if (16 == theMember.type) {
               output_stream.write("      if (value[i]< -128 || value[i]>127) {\n");
               output_stream.write("        throw new IllegalArgumentException(\"the value should be >=-128 and <=127\");\n");
               output_stream.write("      }\n");
            } else if (15 == theMember.type) {
               output_stream.write("      if (value[i]< 0 || value[i] >255) {\n");
               output_stream.write("        throw new IllegalArgumentException(\"the value should be >=0 and <=255\");\n");
               output_stream.write("      }\n");
            }

            output_stream.write("    }\n");
         }

         output_stream.write("    if (value.length < " + theMember.cname + ".length)\n    {\n");
         output_stream.write("      for(int i = 0; i < value.length; i++)\n");
         output_stream.write("        " + theMember.cname + "[i] = value[i];\n");
         output_stream.write("    }\n    else\n");
         output_stream.write("      this." + theMember.cname + " = value;\n  }\n");
      } catch (IOException var4) {
         throw var4;
      }
   }

   private void writeSetterNestedViewArrayCopy(BufferedWriter output_stream, ViewMember theMember) throws IOException {
      try {
         output_stream.write("    if (value.length < " + theMember.fbname + ".length)\n    {\n");
         output_stream.write("      for(int i = 0; i < value.length; i++)\n");
         output_stream.write("        " + theMember.fbname + "[i] = (" + theMember.cname + ")(value[i].doClone());\n");
         output_stream.write("    }\n    else\n");
         output_stream.write("      this." + theMember.fbname + " = value;\n  }\n");
      } catch (IOException var4) {
         throw var4;
      }
   }

   private void writeSetterFieldHandling(BufferedWriter output_stream, ViewMember theMember, boolean isIndexed) throws IOException {
      String lstr = "";
      if (theMember.type == 5) {
         lstr = "()";
      }

      try {
         if (theMember.count == 1) {
            if (theMember.hasLength) {
               output_stream.write("    if (_associatedFieldHandling)\n");
               output_stream.write("      L_" + theMember.cname + " = value.length" + lstr + ";\n");
            }
         } else if (isIndexed) {
            if (theMember.hasCount || theMember.hasLength) {
               output_stream.write("    if (_associatedFieldHandling)\n    {\n");
               if (theMember.hasCount) {
                  output_stream.write("      if (index >= C_" + theMember.cname + ")\n");
                  output_stream.write("        C_" + theMember.cname + " = index + 1;\n");
               }

               if (theMember.hasLength) {
                  output_stream.write("      L_" + theMember.cname + "[index] = value.length" + lstr + ";\n");
               }

               output_stream.write("    }\n");
            }
         } else if (theMember.hasCount || theMember.hasLength) {
            output_stream.write("    if (_associatedFieldHandling)\n    {\n");
            if (theMember.hasCount) {
               output_stream.write("      C_" + theMember.cname + " = value.length;\n");
            }

            if (theMember.hasLength) {
               output_stream.write("      for(int i = 0; i < value.length; i++)\n");
               output_stream.write("         L_" + theMember.cname + "[i] = value[i].length" + lstr + ";\n");
            }

            output_stream.write("    }\n");
         }

      } catch (IOException var6) {
         throw var6;
      }
   }

   private void writeFieldTruncate(BufferedWriter output_stream, ViewMember theMember, boolean isString, boolean isIndexed) throws IOException {
      String lstr = "";
      String istr = "";
      String varName = theMember.cname;
      if (isString) {
         lstr = "()";
      }

      if (theMember.count > 1) {
         if (isIndexed) {
            istr = "[index]";
         } else {
            istr = "[i]";
            varName = "ret";
         }
      }

      try {
         if (isString) {
            output_stream.write("          String rData = " + varName + istr + ".substring(0, L_" + theMember.cname + istr + ");\n");
         } else {
            output_stream.write("          byte[] rData = new byte[L_" + theMember.cname + istr + "];\n");
            output_stream.write("          for(int j = 0; j < L_" + theMember.cname + istr + "; j++)\n");
            output_stream.write("            rData[j] = " + varName + istr + "[j];\n");
         }

         if (theMember.count != 1 && !isIndexed) {
            output_stream.write("          ret[i] = rData;\n");
         } else {
            output_stream.write("          return rData;\n");
         }

      } catch (IOException var9) {
         throw var9;
      }
   }

   private void writeGetterFieldHandling(BufferedWriter output_stream, ViewMember theMember, String type, boolean isIndexed) throws IOException {
      try {
         if (theMember.hasCount && !isIndexed || theMember.hasLength) {
            output_stream.write("    if (_associatedFieldHandling)\n    {\n");
            if (theMember.count > 1 && !isIndexed) {
               output_stream.write("      " + type + "[] ret = null;\n");
               if (theMember.hasCount) {
                  output_stream.write("      if (C_" + theMember.cname + " == 0)\n");
                  output_stream.write("        return null;\n");
                  output_stream.write("      else if (C_" + theMember.cname + " > 0 && C_" + theMember.cname + " < " + theMember.cname + ".length)\n      {\n");
                  if (theMember.type == 6) {
                     output_stream.write("        ret = new byte[C_" + theMember.cname + "][];\n");
                  } else {
                     output_stream.write("        ret = new " + type + "[C_" + theMember.cname + "];\n");
                  }

                  output_stream.write("        for(int i = 0; i < C_" + theMember.cname + "; i++)\n");
                  output_stream.write("          ret[i] = " + theMember.cname + "[i];\n");
                  if (theMember.hasLength) {
                     output_stream.write("      }\n      else\n      {\n");
                     if (theMember.type == 6) {
                        output_stream.write("        ret = new byte[" + theMember.cname + ".length][];\n");
                     } else {
                        output_stream.write("        ret = new " + type + "[" + theMember.cname + ".length];\n");
                     }

                     output_stream.write("        for(int i = 0; i < " + theMember.cname + ".length; i++)\n");
                     output_stream.write("          ret[i] = " + theMember.cname + "[i];\n      }\n");
                  } else {
                     output_stream.write("        return ret;\n      }\n");
                  }
               }
            }

            if (theMember.hasLength) {
               boolean isString;
               String lstr;
               if (theMember.type == 5) {
                  isString = true;
                  lstr = "()";
               } else {
                  isString = false;
                  lstr = "";
               }

               String varName;
               if (theMember.hasCount && theMember.count > 1 && !isIndexed) {
                  varName = "ret";
               } else {
                  varName = theMember.cname;
               }

               String istr = "";
               if (theMember.count > 1) {
                  if (!isIndexed) {
                     istr = "[i]";
                     if (!theMember.hasCount) {
                        if (theMember.type == 6) {
                           output_stream.write("      ret = new byte[" + theMember.cname + ".length][];\n");
                        } else {
                           output_stream.write("      ret = new " + type + "[" + theMember.cname + ".length];\n");
                        }

                        output_stream.write("      for(int i = 0; i < " + theMember.cname + ".length; i++)\n");
                        output_stream.write("        ret[i] = " + theMember.cname + "[i];\n");
                     }

                     output_stream.write("      for(int i = 0; i < " + varName + ".length; i++)\n      {\n");
                  } else {
                     istr = "[index]";
                  }
               }

               output_stream.write("        if (L_" + theMember.cname + istr + " == 0)\n");
               if (theMember.count > 1 && !isIndexed) {
                  output_stream.write("          ret[i] = null;\n");
               } else {
                  output_stream.write("          return null;\n");
               }

               output_stream.write("        else if (L_" + theMember.cname + istr + " > 0 && L_" + theMember.cname + istr + " < " + varName + istr + ".length" + lstr + ")\n        {\n");
               this.writeFieldTruncate(output_stream, theMember, isString, isIndexed);
               output_stream.write("         }\n");
               if (theMember.count > 1 && !isIndexed) {
                  output_stream.write("      }\n      return ret;\n");
               }
            }

            output_stream.write("    }\n");
         }

      } catch (IOException var9) {
         throw var9;
      }
   }

   private void writePrimitiveNonArrayAccessors(ViewMember theMember, String memberName, BufferedWriter output_stream) throws IOException {
      try {
         this.writeAccessorComment(output_stream, theMember, true, false);
         output_stream.write("  public " + theMember.typeName + " get" + memberName + "()\n  {\n");
         output_stream.write("    return(this." + theMember.cname + ");\n  }\n");
         this.writeAccessorComment(output_stream, theMember, false, false);
         output_stream.write("  public void set" + memberName + "(" + theMember.typeName + " value) throws IllegalArgumentException \n  {\n");
         if (18 == theMember.type) {
            output_stream.write("    if (value<0L || value>4294967295L) {\n");
            output_stream.write("      throw new IllegalArgumentException(\"the value should be >=0 and <=4294967295L\");\n");
            output_stream.write("    }\n");
         } else if (16 == theMember.type) {
            output_stream.write("    if (value< -128 || value>127) {\n");
            output_stream.write("      throw new IllegalArgumentException(\"the value should be >=-128 and <=127\");\n");
            output_stream.write("    }\n");
         } else if (15 == theMember.type) {
            output_stream.write("    if (value< 0 || value>255) {\n");
            output_stream.write("      throw new IllegalArgumentException(\"the value should be >=0 and <=255\");\n");
            output_stream.write("    }\n");
         }

         output_stream.write("    this." + theMember.cname + " = value;\n  }\n");
      } catch (IOException var5) {
         throw var5;
      }
   }

   private void writePrimitiveArrayAccessors(ViewMember theMember, String memberName, BufferedWriter output_stream) throws IOException {
      try {
         String type = theMember.typeName;
         this.writeAccessorComment(output_stream, theMember, true, false);
         output_stream.write("  public " + type + "[] get" + memberName + "()\n  {\n");
         this.writeGetterFieldHandling(output_stream, theMember, type, false);
         output_stream.write("    return(this." + theMember.cname + ");\n  }\n");
         this.writeAccessorComment(output_stream, theMember, true, true);
         output_stream.write("  public " + type + " get" + memberName + "(int index)\n  {\n");
         this.writeGetterFieldHandling(output_stream, theMember, type, true);
         output_stream.write("    return(this." + theMember.cname + "[index]);\n  }\n");
         this.writeAccessorComment(output_stream, theMember, false, false);
         output_stream.write("  public void set" + memberName + "(" + type + "[] value) throws IllegalArgumentException\n  {\n");
         this.writeBoundsCheck(output_stream, theMember, false);
         this.writeSetterFieldHandling(output_stream, theMember, false);
         this.writeSetterArrayCopy(output_stream, theMember);
         this.writeAccessorComment(output_stream, theMember, false, true);
         output_stream.write("  public void set" + memberName);
         if (this.beanNames) {
            output_stream.write("(int index, " + type + " value) throws IllegalArgumentException\n  {\n");
         } else {
            output_stream.write("(" + type + " value, int index)\n  {\n");
         }

         this.writeSetterFieldHandling(output_stream, theMember, true);
         if (18 == theMember.type) {
            output_stream.write("    if (value<0L || value>4294967295L) {\n");
            output_stream.write("      throw new IllegalArgumentException(\"the value should be >=0 and <=4294967295L\");\n");
            output_stream.write("    }\n");
         } else if (16 == theMember.type) {
            output_stream.write("    if (value<-128 || value>127) {\n");
            output_stream.write("      throw new IllegalArgumentException(\"the value should be >=-128 and <=127\");\n");
            output_stream.write("    }\n");
         } else if (15 == theMember.type) {
            output_stream.write("    if (value<0 || value>255) {\n");
            output_stream.write("      throw new IllegalArgumentException(\"the value should be >=0 and <=255\");\n");
            output_stream.write("    }\n");
         }

         output_stream.write("    this." + theMember.cname + "[index] = value;\n  }\n");
      } catch (IOException var5) {
         throw var5;
      }
   }

   private void writeNonPrimitiveNonArrayAccessors(ViewMember theMember, String memberName, BufferedWriter output_stream) throws IOException {
      try {
         String type = theMember.typeName;
         this.writeAccessorComment(output_stream, theMember, true, false);
         output_stream.write("  public " + type + " get" + memberName + "()\n  {\n");
         this.writeGetterFieldHandling(output_stream, theMember, type, false);
         output_stream.write("    return(this." + theMember.cname + ");\n  }\n");
         this.writeAccessorComment(output_stream, theMember, false, false);
         output_stream.write("  public void set" + memberName + "(" + type + " value)\n  {\n");
         this.writeBoundsCheck(output_stream, theMember, false);
         this.writeSetterFieldHandling(output_stream, theMember, false);
         output_stream.write("    this." + theMember.cname + " = value;\n  }\n");
      } catch (IOException var5) {
         throw var5;
      }
   }

   private void writeNonPrimitiveArrayAccessors(ViewMember theMember, String memberName, BufferedWriter output_stream) throws IOException {
      try {
         String type = theMember.typeName;
         this.writeAccessorComment(output_stream, theMember, true, false);
         output_stream.write("  public " + type + "[] get" + memberName + "()\n  {\n");
         this.writeGetterFieldHandling(output_stream, theMember, type, false);
         output_stream.write("    return(this." + theMember.cname + ");\n  }\n");
         this.writeAccessorComment(output_stream, theMember, true, true);
         output_stream.write("  public " + type + " get" + memberName + "(int index)\n  {\n");
         this.writeGetterFieldHandling(output_stream, theMember, type, true);
         output_stream.write("    return(this." + theMember.cname + "[index]);\n  }\n");
         this.writeAccessorComment(output_stream, theMember, false, false);
         output_stream.write("  public void set" + memberName + "(" + type + "[] value)\n  {\n");
         this.writeBoundsCheck(output_stream, theMember, false);
         this.writeSetterFieldHandling(output_stream, theMember, false);
         this.writeSetterArrayCopy(output_stream, theMember);
         this.writeAccessorComment(output_stream, theMember, false, true);
         output_stream.write("  public void set" + memberName);
         if (this.beanNames) {
            output_stream.write("(int index, " + type + " value)\n  {\n");
         } else {
            output_stream.write("(" + type + " value, int index)\n  {\n");
         }

         this.writeBoundsCheck(output_stream, theMember, true);
         this.writeSetterFieldHandling(output_stream, theMember, true);
         output_stream.write("    this." + theMember.cname + "[index] = value;\n  }\n");
      } catch (IOException var5) {
         throw var5;
      }
   }

   private void writeNestedViewNonArrayAccessors(ViewMember theMember, String memberName, BufferedWriter output_stream) throws IOException {
      try {
         this.writeAccessorComment(output_stream, theMember, true, false);
         output_stream.write("  public " + theMember.cname + " get" + memberName + "()\n  {\n");
         output_stream.write("    return(this." + theMember.fbname + ");\n  }\n");
         this.writeAccessorComment(output_stream, theMember, false, false);
         output_stream.write("  public void set" + memberName + "(" + theMember.cname + " value)\n  {\n");
         output_stream.write("    this." + theMember.fbname + " = (" + theMember.cname + ")(value.doClone());\n  }\n");
      } catch (IOException var5) {
         throw var5;
      }
   }

   private void writeNestedViewArrayAccessors(ViewMember theMember, String memberName, BufferedWriter output_stream) throws IOException {
      try {
         String type = theMember.cname;
         this.writeAccessorComment(output_stream, theMember, true, false);
         output_stream.write("  public " + type + "[] get" + memberName + "()\n  {\n");
         this.writeGetterFieldHandling(output_stream, theMember, type, false);
         output_stream.write("    return(this." + theMember.fbname + ");\n  }\n");
         this.writeAccessorComment(output_stream, theMember, true, true);
         output_stream.write("  public " + type + " get" + memberName + "(int index)\n  {\n");
         this.writeGetterFieldHandling(output_stream, theMember, type, true);
         output_stream.write("    return(this." + theMember.fbname + "[index]);\n  }\n");
         this.writeAccessorComment(output_stream, theMember, false, false);
         output_stream.write("  public void set" + memberName + "(" + type + "[] value)\n  {\n");
         this.writeBoundsCheck(output_stream, theMember, false);
         this.writeSetterFieldHandling(output_stream, theMember, false);
         this.writeSetterNestedViewArrayCopy(output_stream, theMember);
         this.writeAccessorComment(output_stream, theMember, false, true);
         output_stream.write("  public void set" + memberName);
         if (this.beanNames) {
            output_stream.write("(int index, " + type + " value)\n  {\n");
         } else {
            output_stream.write("(" + type + " value, int index)\n  {\n");
         }

         this.writeSetterFieldHandling(output_stream, theMember, true);
         output_stream.write("    this." + theMember.fbname + "[index] = (" + theMember.cname + ")(value.doClone());\n  }\n");
      } catch (IOException var5) {
         throw var5;
      }
   }

   private boolean write_view(LinkedList viewList, BufferedWriter output_stream, String viewName) {
      int nummethods = false;

      try {
         output_stream.newLine();
         ViewMember[] myList = (ViewMember[])((ViewMember[])viewList.toArray(new ViewMember[0]));
         int myList_size = myList.length;

         ViewMember theMember;
         int lcv;
         for(lcv = 0; lcv < myList_size; ++lcv) {
            theMember = myList[lcv];
            if (11 == theMember.type) {
               if (theMember.count > 1) {
                  output_stream.write("  private " + theMember.cname + "[] " + theMember.fbname + " = null;\n");
               } else {
                  output_stream.write("  private " + theMember.cname + " " + theMember.fbname + " = " + theMember.nullVal + ";\n");
               }
            } else {
               if (theMember.hasLength) {
                  if (theMember.count > 1) {
                     output_stream.write("  private int[] L_" + theMember.cname + " = null;\n");
                  } else {
                     output_stream.write("  private int L_" + theMember.cname + " = 0;\n");
                  }
               }

               if (theMember.hasCount) {
                  output_stream.write("  private int C_" + theMember.cname + " = 0;\n");
               }

               if (theMember.count > 1) {
                  output_stream.write("  private " + theMember.typeName + "[] " + theMember.cname + " = null;\n");
               } else {
                  output_stream.write("  private " + theMember.typeName + " " + theMember.cname + " = " + theMember.nullVal + ";\n");
               }
            }
         }

         output_stream.write("  private boolean _associatedFieldHandling = " + this.associatedFieldHandling + ";\n");
         output_stream.write("\n  public ");
         output_stream.write(viewName + "() {\n");
         output_stream.write("    super(\"" + viewName + "\", " + this.buildWith64Bit + ");\n");

         for(lcv = 0; lcv < myList_size; ++lcv) {
            theMember = myList[lcv];
            if (11 == theMember.type) {
               if (theMember.count > 1) {
                  output_stream.write("    this." + theMember.fbname + " = new " + theMember.cname + "[" + theMember.count + "];\n");
                  output_stream.write("    for (int i = 0; i < " + theMember.count + "; i++) {\n");
                  output_stream.write("      " + theMember.fbname + "[i] = new " + theMember.cname + "();\n");
                  output_stream.write("    }\n");
               } else {
                  output_stream.write("    this." + theMember.fbname + " = new " + theMember.cname + "();\n");
               }
            } else {
               if (theMember.hasLength && theMember.count > 1) {
                  output_stream.write("    this.L_" + theMember.cname + " = new int[" + theMember.count + "];\n");
               }

               if (theMember.type == 6) {
                  if (theMember.count > 1) {
                     output_stream.write("    this." + theMember.cname + " = new byte[" + theMember.count + "][" + theMember.size + "];\n");
                  } else {
                     output_stream.write("    this." + theMember.cname + " = new byte[" + theMember.size + "];\n");
                  }
               } else if (theMember.count > 1) {
                  output_stream.write("    this." + theMember.cname + " = new " + theMember.typeName + "[" + theMember.count + "];\n");
               }
            }
         }

         output_stream.write("    return;\n  }\n");
         output_stream.write("  /**\n");
         output_stream.write("   * Gets the current state of associated field handling.\n");
         output_stream.write("   * @return the current state (true=on, false=off)\n");
         output_stream.write("   */\n");
         output_stream.write("  public boolean getAssociatedFieldHandling()\n");
         output_stream.write("  {\n    return _associatedFieldHandling;\n  }\n");
         output_stream.write("  /**\n");
         output_stream.write("   * Sets the state of associated field handling.\n");
         output_stream.write("   * @param state the desired state (true=on, false=off)\n");
         output_stream.write("   */\n");
         output_stream.write("  public void setAssociatedFieldHandling(boolean state)\n");
         output_stream.write("  {\n    _associatedFieldHandling = state;\n  }\n");
         output_stream.write("  /**\n");
         output_stream.write("   * Check if current VIEW is or contains VIEW class\n");
         output_stream.write("   * build with -32bit option.\n");
         output_stream.write("   */\n");
         output_stream.write("  public boolean containsOldView() {\n");
         if (!this.buildWith64Bit) {
            output_stream.write("    return true;\n");
         } else {
            for(lcv = 0; lcv < myList_size; ++lcv) {
               theMember = myList[lcv];
               if (11 == theMember.type) {
                  output_stream.write("    if (" + theMember.fbname + ".containsOldView() == true) {\n");
                  output_stream.write("      return true;\n");
                  output_stream.write("    }\n");
               }
            }

            output_stream.write("    return false;\n");
         }

         output_stream.write("  }");

         for(lcv = 0; lcv < myList_size; ++lcv) {
            theMember = myList[lcv];
            output_stream.newLine();
            String memberName;
            char firstChar;
            if (11 == theMember.type) {
               if (this.beanNames) {
                  if (theMember.fbname.length() > 1) {
                     firstChar = theMember.fbname.charAt(0);
                     memberName = Character.toUpperCase(firstChar) + theMember.fbname.substring(1);
                  } else {
                     memberName = theMember.fbname.toUpperCase();
                  }
               } else {
                  memberName = theMember.fbname;
               }

               if (theMember.count > 1) {
                  this.writeNestedViewArrayAccessors(theMember, memberName, output_stream);
               } else {
                  this.writeNestedViewNonArrayAccessors(theMember, memberName, output_stream);
               }
            } else {
               if (this.beanNames) {
                  if (theMember.cname.length() > 1) {
                     firstChar = theMember.cname.charAt(0);
                     memberName = Character.toUpperCase(firstChar) + theMember.cname.substring(1);
                  } else {
                     memberName = theMember.cname.toUpperCase();
                  }
               } else {
                  memberName = theMember.cname;
               }

               if (theMember.hasCount) {
                  output_stream.write("  /**\n");
                  output_stream.write("   * Gets the value of the C_" + theMember.cname + " count element of this view\n");
                  output_stream.write("   * @return The count which this element has\n");
                  output_stream.write("   */\n");
                  output_stream.write("  public int ");
                  output_stream.write("getC_" + theMember.cname + "()\n");
                  output_stream.write("  {\n");
                  output_stream.write("    return(this.C_" + theMember.cname + ");\n");
                  output_stream.write("  }\n");
               }

               if (theMember.hasLength) {
                  output_stream.write("  /**\n");
                  output_stream.write("   * Gets the value of the L_" + theMember.cname + " length element of this view\n");
                  output_stream.write("   * @return The length which this element has\n");
                  output_stream.write("   */\n");
                  output_stream.write("  public int");
                  if (theMember.count > 1) {
                     output_stream.write("[]");
                  }

                  output_stream.write(" getL_" + theMember.cname + "()\n");
                  output_stream.write("  {\n");
                  output_stream.write("    return(this.L_" + theMember.cname + ");\n");
                  output_stream.write("  }\n");
                  if (theMember.count > 1) {
                     output_stream.write("  /**\n");
                     output_stream.write("   * Gets the value of the L_" + theMember.cname + " length element of this view\n");
                     output_stream.write("   * @param index The element in the array to get.\n");
                     output_stream.write("   * @return The length which this element has\n");
                     output_stream.write("   */\n");
                     output_stream.write("  public int getL_" + theMember.cname + "(int index)\n");
                     output_stream.write("  {\n");
                     output_stream.write("    return(this.L_" + theMember.cname + "[index]);\n");
                     output_stream.write("  }\n");
                  }
               }

               if (theMember.hasCount) {
                  output_stream.write("  /**\n");
                  output_stream.write("   * Sets the value of the C_" + theMember.cname + " count element of this view\n");
                  output_stream.write("   * @return The count which this element has\n");
                  output_stream.write("   */\n");
                  output_stream.write("  public void");
                  output_stream.write(" setC_" + theMember.cname + "(");
                  output_stream.write("int numCount)\n");
                  output_stream.write("  {\n");
                  output_stream.write("    this.C_" + theMember.cname + " = numCount;\n");
                  output_stream.write("  }\n");
               }

               if (theMember.hasLength) {
                  output_stream.write("  /**\n");
                  output_stream.write("   * Sets the value of the L_" + theMember.cname + " length element of this view\n");
                  output_stream.write("   * @param numLength The length which this element has\n");
                  output_stream.write("   */\n");
                  output_stream.write("  public void ");
                  output_stream.write("setL_" + theMember.cname + "(int");
                  if (theMember.count > 1) {
                     output_stream.write("[]");
                  }

                  output_stream.write(" numLength)\n");
                  output_stream.write("  {\n");
                  output_stream.write("    this.L_" + theMember.cname + " = numLength;\n");
                  output_stream.write("  }\n");
                  if (theMember.count > 1) {
                     output_stream.write("  /**\n");
                     output_stream.write("   * Sets the value of the L_" + theMember.cname + " length element of this view\n");
                     output_stream.write("   * @param index The element in the array to set.\n");
                     output_stream.write("   * @param numLength The length which this element has\n");
                     output_stream.write("   */\n");
                     output_stream.write("  public void ");
                     output_stream.write("setL_" + theMember.cname);
                     output_stream.write("(int index, int numLength)\n");
                     output_stream.write("  {\n");
                     output_stream.write("    this.L_" + theMember.cname + "[index] = numLength;\n");
                     output_stream.write("  }\n");
                  }
               }

               if (theMember.type != 5 && theMember.type != 6) {
                  if (theMember.count > 1) {
                     this.writePrimitiveArrayAccessors(theMember, memberName, output_stream);
                  } else {
                     this.writePrimitiveNonArrayAccessors(theMember, memberName, output_stream);
                  }
               } else if (theMember.count > 1) {
                  this.writeNonPrimitiveArrayAccessors(theMember, memberName, output_stream);
               } else {
                  this.writeNonPrimitiveNonArrayAccessors(theMember, memberName, output_stream);
               }
            }
         }

         output_stream.newLine();
         this.write_tmpresend(myList, output_stream, viewName);
         output_stream.newLine();
         this.write_tmpostrecv(myList, output_stream, viewName);
         output_stream.write("}\n");
         return true;
      } catch (IOException var12) {
         System.out.println("Error writing view file: " + var12);
         return false;
      }
   }

   private void write_tmpresend(ViewMember[] myList, BufferedWriter output_stream, String viewName) throws IOException {
      try {
         output_stream.write("  public void _tmpresend(DataOutputStream encoder)");
         output_stream.newLine();
         output_stream.write("    throws TPException, IOException {\n");
         output_stream.write("    int lcv;\n");
         if (this.modifyStrings) {
            output_stream.write("    boolean addNull;\n");
         }

         output_stream.write("    try {\n");

         for(int lcv = 0; lcv < myList.length; ++lcv) {
            ViewMember theMember = myList[lcv];
            if (11 == theMember.type) {
               if (theMember.count > 1) {
                  output_stream.write("      for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                  if (this.buildWith64Bit) {
                     output_stream.write("       // set if need to support 64-bit FLD_LONG for embedded VIEW\n");
                     output_stream.write("       " + theMember.fbname + "[lcv].setUse64BitsLong(getUse64BitsLong());\n");
                  }

                  output_stream.write("       " + theMember.fbname + "[lcv]._tmpresend(encoder);\n");
                  output_stream.write("      }\n");
               } else {
                  if (this.buildWith64Bit) {
                     output_stream.write("      // set if need to support 64-bit FLD_LONG for embedded VIEW\n");
                     output_stream.write("      " + theMember.fbname + ".setUse64BitsLong(getUse64BitsLong());\n");
                  }

                  output_stream.write("      " + theMember.fbname + "._tmpresend(encoder);\n");
               }
            } else if (18 == theMember.type) {
               if (theMember.count > 1) {
                  output_stream.write("      for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                  output_stream.write("        Long __lVale = " + theMember.cname + "[lcv] & 0xffffffffL;\n");
                  output_stream.write("        int __iValue = __lVale.intValue();\n");
                  output_stream.write("        encoder.writeInt(__iValue);\n");
                  output_stream.write("      }\n");
               } else {
                  output_stream.write("      {\n");
                  output_stream.write("        Long __lVale = " + theMember.cname + "& 0xffffffffL;\n");
                  output_stream.write("        int __iValue = __lVale.intValue();\n");
                  output_stream.write("        encoder.writeInt(__iValue);\n");
                  output_stream.write("      }\n");
               }
            } else if (19 == theMember.type) {
               if (theMember.count > 1) {
                  output_stream.write("      for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                  output_stream.write("        int[] __iValue = new int[4];\n");
                  output_stream.write("        Long __lValue;\n");
                  output_stream.write("        int __i;\n");
                  output_stream.write("        for (__i=0; __i<=3; __i++) {\n");
                  output_stream.write("          __lValue = " + theMember.cname + "[lcv] & 0xffffffffL;\n");
                  output_stream.write("          __iValue[__i] = __lValue.intValue();\n");
                  output_stream.write("          " + theMember.cname + "[lcv] = " + theMember.cname + "[lcv] >> 32;\n");
                  output_stream.write("        }\n");
                  output_stream.write("        for (__i=0; __i<=3; __i++) {\n");
                  output_stream.write("          encoder.writeInt(__iValue[__i]);\n");
                  output_stream.write("        }\n");
                  output_stream.write("      }\n");
               } else {
                  output_stream.write("      {\n");
                  output_stream.write("        int[] __iValue = new int[4];\n");
                  output_stream.write("        Long __lValue;\n");
                  output_stream.write("        int __i;\n");
                  output_stream.write("        for (__i=0; __i<=3; __i++) {\n");
                  output_stream.write("          __lValue = " + theMember.cname + " & 0xffffffffL;\n");
                  output_stream.write("          __iValue[__i] = __lValue.intValue();\n");
                  output_stream.write("          " + theMember.cname + " = " + theMember.cname + " >> 32;\n");
                  output_stream.write("        }\n");
                  output_stream.write("        for (__i=0; __i<=3; __i++) {\n");
                  output_stream.write("          encoder.writeInt(__iValue[__i]);\n");
                  output_stream.write("        }\n");
                  output_stream.write("      }\n");
               }
            } else {
               if (theMember.hasLength) {
                  if (theMember.count > 1) {
                     output_stream.write("      for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                     output_stream.write("        encoder.writeInt(L_" + theMember.cname + "[lcv]);\n");
                     output_stream.write("      }\n");
                  } else {
                     output_stream.write("      encoder.writeInt(L_" + theMember.cname + ");\n");
                  }
               }

               if (theMember.hasCount) {
                  output_stream.write("      encoder.writeInt(C_" + theMember.cname + ");\n");
               }

               if (theMember.type == 5) {
                  if (theMember.count > 1) {
                     output_stream.write("      for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                     if (this.modifyStrings) {
                        output_stream.write("        if (" + theMember.cname + "[lcv] != null)\n");
                        output_stream.write("          addNull = (" + theMember.cname + "[lcv].length() < " + theMember.size + ");\n");
                        output_stream.write("        else\n");
                        output_stream.write("          addNull = true;\n");
                     }

                     output_stream.write("        Utilities.xdr_encode_string_length(encoder," + theMember.cname + "[lcv]," + theMember.size);
                  } else {
                     if (this.modifyStrings) {
                        output_stream.write("      if (" + theMember.cname + " != null)\n");
                        output_stream.write("        addNull = (" + theMember.cname + ".length() < " + theMember.size + ");\n");
                        output_stream.write("      else\n");
                        output_stream.write("        addNull = true;\n");
                     }

                     output_stream.write("      Utilities.xdr_encode_string_length(encoder," + theMember.cname + ", " + theMember.size);
                  }

                  if (this.modifyStrings) {
                     output_stream.write(",addNull);\n");
                  } else {
                     output_stream.write(");\n");
                  }

                  if (theMember.count > 1) {
                     output_stream.write("      }\n");
                  }
               } else if (theMember.type == 1) {
                  if (this.buildWith64Bit) {
                     if (theMember.count > 1) {
                        output_stream.write("      for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                        output_stream.write("        if (getUse64BitsLong() == true) {\n");
                        output_stream.write("          encoder.writeLong(" + theMember.cname + "[lcv]);\n");
                        output_stream.write("        } else {\n");
                        output_stream.write("          if (" + theMember.cname + "[lcv] > Integer.MAX_VALUE) {\n");
                        output_stream.write("            throw new TPException(TPException.TPEINVAL, \"64-bit Long is not supported\");\n");
                        output_stream.write("          }\n");
                        output_stream.write("          encoder.writeInt((new Long(" + theMember.cname + "[lcv])).intValue());\n");
                        output_stream.write("        }\n");
                        output_stream.write("      }\n");
                     } else {
                        output_stream.write("      if (getUse64BitsLong() == true) {\n");
                        output_stream.write("        encoder.writeLong(" + theMember.cname + ");\n");
                        output_stream.write("      } else {\n");
                        output_stream.write("        if (" + theMember.cname + " > Integer.MAX_VALUE) {\n");
                        output_stream.write("          throw new TPException(TPException.TPEINVAL, \"64-bit Long is not supported\");\n");
                        output_stream.write("        }\n");
                        output_stream.write("        encoder.writeInt((new Long(" + theMember.cname + ")).intValue());\n");
                        output_stream.write("      }\n");
                     }
                  } else if (theMember.count > 1) {
                     output_stream.write("      for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                     output_stream.write("        " + theMember.encoder + theMember.cname + "[lcv]);\n");
                     output_stream.write("      }\n");
                  } else {
                     output_stream.write("      " + theMember.encoder + theMember.cname + ");\n");
                  }
               } else if (theMember.count > 1) {
                  output_stream.write("      for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                  if (14 == theMember.type) {
                     output_stream.write("        " + theMember.encoder + theMember.cname + "[lcv] ?1:0);\n");
                  } else {
                     output_stream.write("        " + theMember.encoder + theMember.cname + "[lcv]);\n");
                  }

                  output_stream.write("      }\n");
               } else if (14 == theMember.type) {
                  output_stream.write("      " + theMember.encoder + theMember.cname + "?1:0);\n");
               } else {
                  output_stream.write("      " + theMember.encoder + theMember.cname + ");\n");
               }
            }
         }

         output_stream.write("    }\n    catch (IOException ie) {\n");
         output_stream.write("      System.out.println(\"Error encoding view buffer: \" + ie);\n");
         output_stream.write("    }\n");
         output_stream.write("    return;\n");
         output_stream.write("  }\n");
      } catch (IOException var7) {
         System.out.println("Error writing view file: " + var7);
      }
   }

   private void write_tmpostrecv(ViewMember[] myList, BufferedWriter output_stream, String viewName) throws IOException {
      try {
         output_stream.newLine();
         output_stream.write("  public void _tmpostrecv(DataInputStream decoder, int recv_size)\n");
         output_stream.write("    throws TPException, IOException {\n");
         output_stream.write("    int lcv;\n");

         for(int lcv = 0; lcv < myList.length; ++lcv) {
            ViewMember theMember = myList[lcv];
            if (11 == theMember.type) {
               if (theMember.count > 1) {
                  output_stream.write("    for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                  if (this.buildWith64Bit) {
                     output_stream.write("       // set if need to support 64-bit FLD_LONG for embedded VIEW\n");
                     output_stream.write("       " + theMember.fbname + "[lcv].setUse64BitsLong(getUse64BitsLong());\n");
                  }

                  output_stream.write("      " + theMember.fbname + "[lcv]._tmpostrecv(decoder, recv_size);\n");
                  output_stream.write("    }\n");
               } else {
                  if (this.buildWith64Bit) {
                     output_stream.write("    // set if need to support 64-bit FLD_LONG for embedded VIEW\n");
                     output_stream.write("    " + theMember.fbname + ".setUse64BitsLong(getUse64BitsLong());\n");
                  }

                  output_stream.write("    " + theMember.fbname + "._tmpostrecv(decoder, recv_size);\n");
               }
            } else if (18 == theMember.type) {
               if (theMember.count > 1) {
                  output_stream.write("    for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                  output_stream.write("      int __iValue = decoder.readInt();\n");
                  output_stream.write("      Long __lValue = new Long(__iValue);\n");
                  output_stream.write("      " + theMember.cname + "[lcv] = __lValue & 0xffffffffL;\n");
                  output_stream.write("    }\n");
               } else {
                  output_stream.write("    {\n");
                  output_stream.write("      int __iValue = decoder.readInt();\n");
                  output_stream.write("      Long __lValue = new Long(__iValue);\n");
                  output_stream.write("      " + theMember.cname + " = __lValue & 0xffffffffL;\n");
                  output_stream.write("    }\n");
               }
            } else if (19 == theMember.type) {
               if (theMember.count > 1) {
                  output_stream.write("    for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                  output_stream.write("      int[] __iValue = new int[4];\n");
                  output_stream.write("      Long __lValue;\n");
                  output_stream.write("      int __i;\n");
                  output_stream.write("      for (__i=0; __i<=3; __i++) {\n");
                  output_stream.write("        __iValue[__i] = decoder.readInt();\n");
                  output_stream.write("      }\n");
                  output_stream.write("      " + theMember.cname + "[lcv] = 0L;\n");
                  output_stream.write("      for (__i=3; __i>=0; __i--) {\n");
                  output_stream.write("        " + theMember.cname + "[lcv] = " + theMember.cname + "[lcv] << 32;\n");
                  output_stream.write("        __lValue = new Long(__iValue[__i]);\n");
                  output_stream.write("        __lValue = __lValue & 0xffffffffL;\n");
                  output_stream.write("        " + theMember.cname + "[lcv] |= __lValue;\n");
                  output_stream.write("      }\n");
                  output_stream.write("    }\n");
               } else {
                  output_stream.write("    {\n");
                  output_stream.write("      int[] __iValue = new int[4];\n");
                  output_stream.write("      Long __lValue;\n");
                  output_stream.write("      int __i;\n");
                  output_stream.write("      for (__i=0; __i<=3; __i++) {\n");
                  output_stream.write("        __iValue[__i] = decoder.readInt();\n");
                  output_stream.write("      }\n");
                  output_stream.write("      " + theMember.cname + " = 0L;\n");
                  output_stream.write("      for (__i=3; __i>=0; __i--) {\n");
                  output_stream.write("        " + theMember.cname + " = " + theMember.cname + " << 32;\n");
                  output_stream.write("        __lValue = new Long(__iValue[__i]);\n");
                  output_stream.write("        __lValue = __lValue & 0xffffffffL;\n");
                  output_stream.write("        " + theMember.cname + " |= __lValue;\n");
                  output_stream.write("      }\n");
                  output_stream.write("    }\n");
               }
            } else {
               if (theMember.hasLength) {
                  if (theMember.count > 1) {
                     output_stream.write("    for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                     output_stream.write("      L_" + theMember.cname + "[lcv] = decoder.readInt();\n");
                     output_stream.write("    }\n");
                  } else {
                     output_stream.write("    L_" + theMember.cname + " = decoder.readInt();\n");
                  }
               }

               if (theMember.hasCount) {
                  output_stream.write("    C_" + theMember.cname + " = decoder.readInt();\n");
               }

               if (theMember.type == 5) {
                  if (theMember.count > 1) {
                     output_stream.write("    for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                     output_stream.write("      " + theMember.cname + "[lcv] = Utilities.xdr_decode_string(decoder, null");
                  } else {
                     output_stream.write("    " + theMember.cname + " = Utilities.xdr_decode_string(decoder, null");
                  }

                  if (this.modifyStrings) {
                     output_stream.write(", true);\n");
                  } else {
                     output_stream.write(");\n");
                  }

                  if (theMember.count > 1) {
                     output_stream.write("    }\n");
                  }
               } else if (theMember.type == 1) {
                  if (this.buildWith64Bit) {
                     if (theMember.count > 1) {
                        output_stream.write("    for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                        output_stream.write("      if (getUse64BitsLong() == true) {\n");
                        output_stream.write("        " + theMember.cname + "[lcv] = decoder.readLong();\n");
                        output_stream.write("      } else {\n");
                        output_stream.write("        " + theMember.cname + "[lcv] = (new Long(decoder.readInt())).longValue();\n");
                        output_stream.write("      }\n");
                        output_stream.write("    }\n");
                     } else {
                        output_stream.write("    if (getUse64BitsLong() == true) {\n");
                        output_stream.write("      " + theMember.cname + " = decoder.readLong();\n");
                        output_stream.write("    } else {\n");
                        output_stream.write("      " + theMember.cname + " = (new Long(decoder.readInt())).longValue();\n");
                        output_stream.write("    }\n");
                     }
                  } else if (theMember.count > 1) {
                     output_stream.write("    for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                     output_stream.write("      " + theMember.cname + "[lcv] = " + theMember.decoder + ";\n");
                     output_stream.write("    }\n");
                  } else {
                     output_stream.write("    " + theMember.cname + " = " + theMember.decoder + ";\n");
                  }
               } else if (theMember.count > 1) {
                  output_stream.write("    for (lcv = 0; lcv < " + theMember.count + "; lcv++) {\n");
                  output_stream.write("      " + theMember.cname + "[lcv] = " + theMember.decoder + ";\n");
                  output_stream.write("    }\n");
               } else {
                  output_stream.write("    " + theMember.cname + " = " + theMember.decoder + ";\n");
               }
            }
         }

         output_stream.write("    return;\n");
         output_stream.write("  }\n");
      } catch (IOException var7) {
         System.out.println("Error writing view file: " + var7);
      }
   }

   private void usage() {
      if (this.isView32) {
         System.out.println("Usage: viewj32 [options] [package] view");
      } else {
         System.out.println("Usage: viewj [options] [package] view");
      }

      System.out.println("Options:");
      System.out.println("  -associated_fields Use associated count and length");
      System.out.println("                     fields in generated setters and getters");
      System.out.println("  -compat_names      Use original naming conventions for generated");
      System.out.println("                     setters and getters: set or get prefix plus field name.");
      System.out.println("  -bean_names        Use JavaBean naming conventions for generated");
      System.out.println("                     setters and getters: convert first letter of field name to");
      System.out.println("                     upper case and add set or get prefix.");
      System.out.println("  -modify_strings    Add null to strings before sending to Tuxedo and");
      System.out.println("                     truncate strings received from Tuxedo at first null.");
      System.out.println("  -xcommon           Generate output class as extending TypedXCommon");
      System.out.println("                     instead of TypedView.");
      System.out.println("  -xctype            Generate output class as extending TypedXCType");
      System.out.println("                     instead of TypedView.");
      System.out.println("  -64bits            Use Long internally for long/FLD_LONG field defined in the view");
   }

   viewjCompiler(boolean is32Bits) {
      this.isView32 = is32Bits;
      this.beanNames = false;
      this.modifyStrings = false;
   }

   protected void compileView(String[] args) {
      String outputfilename = null;
      BufferedWriter output_stream = null;
      BufferedReader input_stream = null;
      int written_classheader = false;
      int current_base = false;
      int lineno = 0;
      LinkedList viewList = null;
      int fldno = false;
      int fldid = false;
      int fldtype = false;
      int firsttime = true;
      String viewName = null;
      HashMap nameMap = new HashMap();
      this.beanNames = false;
      this.modifyStrings = false;
      String baseClass;
      if (this.isView32) {
         baseClass = "TypedView32";
      } else {
         baseClass = "TypedView";
      }

      int i;
      for(i = 0; i < args.length && args[i].charAt(0) == '-'; ++i) {
         if (args[i].equals("-compat_names")) {
            this.beanNames = false;
         } else if (args[i].equals("-bean_names")) {
            this.beanNames = true;
         } else if (args[i].equals("-modify_strings")) {
            this.modifyStrings = true;
         } else if (args[i].equals("-associated_fields")) {
            this.associatedFieldHandling = true;
         } else if (args[i].equals("-xctype")) {
            baseClass = "TypedXCType";
         } else if (args[i].equals("-xcommon")) {
            baseClass = "TypedXCommon";
         } else {
            if (!args[i].equals("-64bits")) {
               System.out.println("Illegal option: " + args[i]);
               this.usage();
               return;
            }

            this.buildWith64Bit = true;
         }
      }

      int argsLeft = args.length - i;
      if (argsLeft != 0 && argsLeft <= 2) {
         String fpackage;
         String input_file_name;
         if (argsLeft == 1) {
            fpackage = null;
            input_file_name = args[i];
         } else {
            fpackage = args[i];
            input_file_name = args[i + 1];
         }

         File input_file = new File(input_file_name);

         try {
            input_stream = new BufferedReader(new FileReader(input_file));
         } catch (FileNotFoundException var34) {
            System.out.println("Could not find file " + input_file_name);
            return;
         }

         while(true) {
            int line_len;
            label406: {
               try {
                  if ((this.input_string = input_stream.readLine()) != null) {
                     this.input_string = this.input_string.trim();
                     ++lineno;
                     line_len = this.input_string.length();
                     this.currspot = 0;
                     break label406;
                  }
               } catch (IOException var37) {
                  System.out.println("Finished! " + var37);
               }

               return;
            }

            this.input_string_length = this.input_string.length();

            try {
               if (this.input_string_length != 0 && !this.input_string.startsWith("#")) {
                  if (this.input_string.startsWith("$")) {
                     if (output_stream != null) {
                        output_stream.write("//" + this.input_string.substring(1));
                        output_stream.newLine();
                     }
                  } else if (this.input_string.startsWith("VIEW") && Character.isWhitespace(this.input_string.charAt(4)) && viewName == null) {
                     nameMap.clear();
                     this.currspot = 5;
                     this.skipWhitespace("New view does not have a name");
                     viewName = this.input_string.substring(this.currspot);
                     outputfilename = new String(viewName + ".java");
                     File output_file = new File(outputfilename);

                     try {
                        output_stream = new BufferedWriter(new FileWriter(output_file));
                     } catch (FileNotFoundException var32) {
                        System.out.println("Could not create file " + outputfilename + ". " + var32);
                        return;
                     } catch (IOException var33) {
                        System.out.println("Error creating file " + outputfilename + ". " + var33);
                        return;
                     }

                     viewList = new LinkedList();
                     if (fpackage != null) {
                        output_stream.write("package " + fpackage + ";");
                        output_stream.newLine();
                        output_stream.newLine();
                     }

                     output_stream.write("import java.io.*;");
                     output_stream.newLine();
                     output_stream.write("import java.lang.*;");
                     output_stream.newLine();
                     output_stream.write("import weblogic.wtc.jatmi.*;");
                     output_stream.newLine();
                     output_stream.write("import com.bea.core.jatmi.common.Utilities;");
                     output_stream.newLine();
                     output_stream.newLine();
                     output_stream.write("public class " + viewName);
                     output_stream.write(" extends " + baseClass + " implements Serializable {");
                  } else if (this.input_string.startsWith("END") && (this.input_string_length == 3 || Character.isWhitespace(this.input_string.charAt(3)))) {
                     if (!this.write_view(viewList, output_stream, viewName)) {
                        System.out.println("Error writing view file " + outputfilename);
                        return;
                     }

                     viewName = null;
                     viewList = new LinkedList();
                     output_stream.close();
                     output_stream = null;
                  } else {
                     ViewMember aMember;
                     if (this.input_string.startsWith("int") && Character.isWhitespace(this.input_string.charAt(3))) {
                        aMember = new ViewMember(7);
                        this.currspot = 3;
                     } else if (this.input_string.startsWith("short") && Character.isWhitespace(this.input_string.charAt(5))) {
                        aMember = new ViewMember(0);
                        this.currspot = 5;
                     } else if (this.input_string.startsWith("long") && Character.isWhitespace(this.input_string.charAt(4))) {
                        aMember = new ViewMember(1);
                        if (this.buildWith64Bit) {
                           aMember.typeName = "long";
                        }

                        this.currspot = 4;
                     } else if (this.input_string.startsWith("char") && Character.isWhitespace(this.input_string.charAt(4))) {
                        aMember = new ViewMember(2);
                        this.currspot = 4;
                     } else if (this.input_string.startsWith("float") && Character.isWhitespace(this.input_string.charAt(5))) {
                        aMember = new ViewMember(3);
                        this.currspot = 5;
                     } else if (this.input_string.startsWith("double") && Character.isWhitespace(this.input_string.charAt(6))) {
                        aMember = new ViewMember(4);
                        this.currspot = 6;
                     } else if (this.input_string.startsWith("string") && Character.isWhitespace(this.input_string.charAt(6))) {
                        aMember = new ViewMember(5);
                        this.currspot = 6;
                     } else if (this.input_string.startsWith("carray") && Character.isWhitespace(this.input_string.charAt(6))) {
                        aMember = new ViewMember(6);
                        this.currspot = 6;
                     } else if (this.input_string.startsWith("dec_t") && Character.isWhitespace(this.input_string.charAt(5))) {
                        aMember = new ViewMember(8);
                        this.currspot = 5;
                     } else if (this.input_string.startsWith("struct") && Character.isWhitespace(this.input_string.charAt(6))) {
                        if (!this.isView32) {
                           this.write_error(lineno, "nested view is not supported for viewj, please use viewj32");
                           return;
                        }

                        aMember = new ViewMember(11);
                        this.currspot = 6;
                     } else if (this.input_string.startsWith("bool") && Character.isWhitespace(this.input_string.charAt(4))) {
                        aMember = new ViewMember(14);
                        this.currspot = 4;
                     } else if (this.input_string.startsWith("signedchar") && Character.isWhitespace(this.input_string.charAt(10))) {
                        aMember = new ViewMember(16);
                        this.currspot = 11;
                     } else if (this.input_string.startsWith("unsignedint") && Character.isWhitespace(this.input_string.charAt(11))) {
                        aMember = new ViewMember(18);
                        this.currspot = 12;
                     } else if (this.input_string.startsWith("longlong") && Character.isWhitespace(this.input_string.charAt(8))) {
                        aMember = new ViewMember(19);
                        this.currspot = 9;
                     } else {
                        if (!this.input_string.startsWith("longdouble") || !Character.isWhitespace(this.input_string.charAt(10))) {
                           if (this.input_string.startsWith("-") && Character.isWhitespace(this.input_string.charAt(1))) {
                              this.write_error(lineno, "FML view mapping is not yet supported, but view type is -");
                              return;
                           }

                           this.write_error(lineno, "Found unknown type");
                           return;
                        }

                        aMember = new ViewMember(22);
                        this.currspot = 11;
                     }

                     this.skipWhitespace((String)null);

                     int first_character;
                     for(first_character = this.currspot++; this.currspot < this.input_string_length && !Character.isWhitespace(this.input_string.charAt(this.currspot)); ++this.currspot) {
                     }

                     aMember.cname = this.input_string.substring(first_character, this.currspot);
                     this.skipWhitespace((String)null);

                     for(first_character = this.currspot++; this.currspot < this.input_string_length && !Character.isWhitespace(this.input_string.charAt(this.currspot)); ++this.currspot) {
                     }

                     aMember.fbname = this.input_string.substring(first_character, this.currspot);
                     if (this.beanNames) {
                        String newName;
                        if (aMember.cname.length() > 1) {
                           char firstChar = aMember.cname.charAt(0);
                           newName = Character.toUpperCase(firstChar) + aMember.cname.substring(1);
                        } else {
                           newName = aMember.cname.toUpperCase();
                        }

                        if (nameMap.containsKey(newName + aMember.fbname)) {
                           String prevName = (String)nameMap.get(newName + aMember.fbname);
                           this.write_error(lineno, "Name '" + aMember.cname + "' will clash with name '" + prevName + "' when converted to JavaBean style accessor. Try changing field names or use the -compat_names option.");
                           return;
                        }

                        nameMap.put(newName + aMember.fbname, aMember.cname);
                     } else if ((String)nameMap.put(aMember.cname + aMember.fbname, aMember.cname) != null) {
                        this.write_error(lineno, "Name '" + aMember.cname + "' will clash with previously defined field name  when compiled by Javac compiler. Try changing field names so they won't clash.");
                        return;
                     }

                     this.skipWhitespace((String)null);

                     for(first_character = this.currspot++; this.currspot < this.input_string_length && !Character.isWhitespace(this.input_string.charAt(this.currspot)); ++this.currspot) {
                        if (!Character.isDigit(this.input_string.charAt(this.currspot))) {
                           this.write_error(lineno, "Invalid count field - not digits");
                           return;
                        }
                     }

                     aMember.count = Integer.parseInt(this.input_string.substring(first_character, this.currspot));
                     this.skipWhitespace((String)null);

                     for(first_character = this.currspot; this.currspot < this.input_string_length && !Character.isWhitespace(this.input_string.charAt(this.currspot)); ++this.currspot) {
                        if (this.input_string.charAt(this.currspot) != '-') {
                           if (this.input_string.charAt(this.currspot) == 'C') {
                              aMember.hasCount = true;
                           } else if (this.input_string.charAt(this.currspot) == 'L') {
                              if (aMember.type != 5 && aMember.type != 6) {
                                 this.write_error(lineno, "Only STRING and CARRAY should have a length field");
                                 return;
                              }

                              aMember.hasLength = true;
                           } else if (this.input_string.charAt(this.currspot) != 'F' && this.input_string.charAt(this.currspot) != 'N' && this.input_string.charAt(this.currspot) != 'P' && this.input_string.charAt(this.currspot) != 'S' && this.input_string.charAt(this.currspot) != ',') {
                              this.write_error(lineno, "Invalid flag value: " + this.input_string.charAt(this.currspot));
                              return;
                           }
                        }
                     }

                     this.skipWhitespace((String)null);
                     first_character = this.currspot++;
                     boolean commaSeen = false;

                     while(this.currspot < this.input_string_length && !Character.isWhitespace(this.input_string.charAt(this.currspot))) {
                        if (!Character.isDigit(this.input_string.charAt(this.currspot))) {
                           if (this.input_string.charAt(this.currspot) != ',' || aMember.type != 8 || commaSeen) {
                              this.write_error(lineno, "Invalid size field - not digits");
                              return;
                           }

                           commaSeen = true;
                           ++this.currspot;
                        } else {
                           ++this.currspot;
                        }
                     }

                     if (this.input_string.charAt(first_character) != '-' && aMember.type != 8) {
                        aMember.size = Integer.parseInt(this.input_string.substring(first_character, this.currspot));
                     }

                     if (this.currspot < line_len) {
                        this.skipWhitespace((String)null);
                        aMember.nullValue = this.input_string.substring(this.currspot);
                     }

                     viewList.add(aMember);
                  }
               }
            } catch (IOException var35) {
               System.out.println("Unable to write output file " + var35);
            } catch (ViewParseException var36) {
               this.write_error(lineno, var36.toString());
               return;
            }
         }
      } else {
         this.usage();
      }
   }

   private void skipWhitespace(String error_message) throws ViewParseException {
      while(this.currspot < this.input_string_length && Character.isWhitespace(this.input_string.charAt(this.currspot))) {
         ++this.currspot;
      }

      if (this.currspot >= this.input_string_length) {
         if (error_message != null) {
            throw new ViewParseException(error_message);
         } else {
            throw new ViewParseException("Badly formed view description");
         }
      }
   }
}

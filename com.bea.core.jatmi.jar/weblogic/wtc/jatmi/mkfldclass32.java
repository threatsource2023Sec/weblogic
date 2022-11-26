package weblogic.wtc.jatmi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public final class mkfldclass32 extends Task {
   private String packageName;
   private String outputDirectory;
   private String fieldTable;
   private static final int FLD_SHORT = 0;
   private static final int FLD_LONG = 1;
   private static final int FLD_CHAR = 2;
   private static final int FLD_FLOAT = 3;
   private static final int FLD_DOUBLE = 4;
   private static final int FLD_STRING = 5;
   private static final int FLD_CARRAY = 6;
   private static final int FLD_INT = 7;
   private static final int FLD_DECIMAL = 8;
   private static final int FLD_PTR = 9;
   private static final int FLD_FML32 = 10;
   private static final int FLD_VIEW32 = 11;
   private static final int FLD_MBSTRING = 12;
   private static final int FNMASK32 = 33554431;
   private static final int FTMASK32 = 63;
   private static final int FTSHIFT32 = 25;

   private static void write_error(int lineno, String explain) {
      System.out.println("Error at line " + lineno + ". " + explain + ".");
      throw new BuildException();
   }

   public void execute() {
      int l = false;
      String[] args = new String[]{this.packageName, this.outputDirectory, this.fieldTable};
      doIt(args);
   }

   public void setPackage(String p) {
      this.packageName = p;
   }

   public void setOutputDirectory(String o) {
      this.outputDirectory = o;
   }

   public void setFieldTable(String f) {
      this.fieldTable = f;
   }

   public static void generateDynamicCode(String path, String fpackage, String outdirectory, File classfilename) {
      String outputfilename = null;
      BufferedWriter output_stream = null;
      outputfilename = new String(classfilename.getName() + ".java");
      if (outdirectory != null) {
         outputfilename = outdirectory + File.separatorChar + outputfilename;
      }

      File output_file = new File(outputfilename);

      try {
         output_stream = new BufferedWriter(new FileWriter(output_file));
      } catch (FileNotFoundException var9) {
         System.out.println("Could not create file " + outputfilename + ". " + var9);
         return;
      } catch (IOException var10) {
         System.out.println("Error creating file " + outputfilename + ". " + var10);
         return;
      }

      try {
         if (fpackage != null) {
            output_stream.write("package " + fpackage + ";");
            output_stream.newLine();
            output_stream.newLine();
         }

         output_stream.newLine();
         output_stream.write("import weblogic.wtc.jatmi.DynamicFldTbl;");
         output_stream.newLine();
         output_stream.newLine();
         output_stream.write("/**");
         output_stream.newLine();
         output_stream.write(" * @author Copyright (c) 2003 by BEA Systems, Inc. All Rights Reserved.");
         output_stream.newLine();
         output_stream.write(" */");
         output_stream.newLine();
         output_stream.newLine();
         output_stream.write("/**");
         output_stream.newLine();
         output_stream.write(" * This class reads a FML32 Field Table during boot time");
         output_stream.newLine();
         output_stream.write(" * which implements the FldTbl interface.  ");
         output_stream.newLine();
         output_stream.write(" * @see weblogic.wtc.jatmi.FldTbl");
         output_stream.newLine();
         output_stream.write(" * @see weblogic.wtc.jatmi.FML");
         output_stream.newLine();
         output_stream.write(" * @author BEA Systems, Inc.");
         output_stream.newLine();
         output_stream.write(" */");
         output_stream.newLine();
         output_stream.write("final public class " + classfilename);
         output_stream.newLine();
         output_stream.write("\textends weblogic.wtc.jatmi.DynamicFldTbl");
         output_stream.newLine();
         output_stream.write("{");
         output_stream.newLine();
         output_stream.newLine();
         output_stream.write("\t// Users need to modify this to where the field file is during boot time.");
         output_stream.newLine();
         output_stream.write("\t// The string may be an absolute pathname, a path relative to the root directory");
         output_stream.newLine();
         output_stream.write("\t// from which Java is started, or a relative path that can be found as a resource file");
         output_stream.newLine();
         output_stream.write("\t// by the class loader.");
         output_stream.newLine();
         output_stream.write("\tstatic final String USERFMLTABLEFILE=\"" + path + "\";");
         output_stream.newLine();
         output_stream.newLine();
         output_stream.write("\t/**");
         output_stream.newLine();
         output_stream.write("\t * The constructor.  ");
         output_stream.newLine();
         output_stream.write("\t */");
         output_stream.newLine();
         output_stream.write("\tpublic " + classfilename + "() {");
         output_stream.newLine();
         output_stream.write("\t\t// the 2nd parameter, true for FML32, false for FML");
         output_stream.newLine();
         output_stream.write("\t\t// Default initial capacity is 101 and load factor is 0.75f");
         output_stream.newLine();
         output_stream.write("\t\tsuper(USERFMLTABLEFILE, true);");
         output_stream.newLine();
         output_stream.newLine();
         output_stream.write("\t\t// super(USERFMLTABLEFILE, true, 1000, 0.5f);");
         output_stream.newLine();
         output_stream.write("\t\t// the 3rd parameter, Initial Capacity");
         output_stream.newLine();
         output_stream.write("\t\t// the 4th parameter, Load Factor");
         output_stream.newLine();
         output_stream.write("\t}");
         output_stream.newLine();
         output_stream.newLine();
         output_stream.write("\tpublic static void main(String[] args) {");
         output_stream.newLine();
         output_stream.write("\t\t" + classfilename + " bf = new " + classfilename + "();");
         output_stream.newLine();
         output_stream.write("\t\tString[] list = bf.getFldNames();");
         output_stream.newLine();
         output_stream.write("\t\tfor (int i = 0; i < list.length; i++ ) {");
         output_stream.newLine();
         output_stream.write("\t\t\tSystem.out.println(\"field name = [\" + list[i] + \"]\t\t\tfield name from field id = [\" + bf.Fldid_to_name(bf.name_to_Fldid(list[i]))+ \"]\");");
         output_stream.newLine();
         output_stream.write("\t\t}");
         output_stream.newLine();
         output_stream.write("\t\tSystem.out.println(\"Total entries = \" + list.length);");
         output_stream.newLine();
         output_stream.write("\t}");
         output_stream.newLine();
         output_stream.write("\t");
         output_stream.newLine();
         output_stream.write("}");
         output_stream.newLine();
         output_stream.flush();
         output_stream.close();
      } catch (IOException var8) {
         System.out.println("Unable to write output file " + var8);
      }

   }

   public static void main(String[] args) {
      try {
         doIt(args);
      } catch (BuildException var2) {
      }

   }

   private static void doIt(String[] args) throws BuildException {
      BufferedWriter output_stream = null;
      BufferedReader input_stream = null;
      int written_classheader = false;
      int current_base = 0;
      int lineno = 0;
      int fldno = false;
      int fldid = false;
      int fldtype = 0;
      int numFields = 0;
      int firsttime = true;
      boolean written_package = false;
      if (args.length >= 1 && args.length <= 3) {
         File input_file;
         String fpackage;
         String outdirectory;
         if (args.length == 1) {
            fpackage = null;
            outdirectory = null;
            input_file = new File(args[0]);
         } else if (args.length == 2) {
            outdirectory = null;
            fpackage = args[0];
            input_file = new File(args[1]);
         } else {
            fpackage = args[0];
            if (fpackage.length() == 0) {
               fpackage = null;
            }

            outdirectory = args[1];
            if (outdirectory.length() == 0) {
               outdirectory = null;
            }

            input_file = new File(args[2]);
         }

         String dynrdhdr = System.getProperty("DynRdHdr");
         if (dynrdhdr != null) {
            generateDynamicCode(dynrdhdr, fpackage, outdirectory, input_file);
         } else {
            try {
               input_stream = new BufferedReader(new FileReader(input_file));
            } catch (FileNotFoundException var31) {
               System.out.println("Could not find file " + args[0]);
               throw new BuildException();
            }

            String outputfilename = new String(input_file.getName() + ".java");
            if (outdirectory != null) {
               outputfilename = outdirectory + File.separatorChar + outputfilename;
            }

            File output_file = new File(outputfilename);

            try {
               output_stream = new BufferedWriter(new FileWriter(output_file));
            } catch (FileNotFoundException var29) {
               System.out.println("Could not create file " + outputfilename + ". " + var29);
               throw new BuildException();
            } catch (IOException var30) {
               System.out.println("Error creating file " + outputfilename + ". " + var30);
               throw new BuildException();
            }

            LinkedList fldlist = new LinkedList();

            while(true) {
               String input_string;
               int currspot;
               try {
                  if ((input_string = input_stream.readLine()) == null) {
                     break;
                  }

                  input_string = input_string.trim();
                  ++lineno;
                  currspot = 0;
               } catch (IOException var33) {
                  System.out.println("Finished! " + var33);
                  break;
               }

               int input_string_length = input_string.length();

               try {
                  if (!written_package && fpackage != null) {
                     output_stream.write("package " + fpackage + ";");
                     output_stream.newLine();
                     output_stream.newLine();
                  }

                  written_package = true;
                  if (input_string_length != 0 && !input_string.startsWith("#")) {
                     if (input_string.startsWith("$")) {
                        output_stream.write("//" + input_string.substring(1));
                        output_stream.newLine();
                     } else {
                        int first_digit;
                        if (input_string.startsWith("*base")) {
                           if (!Character.isWhitespace(input_string.charAt(5))) {
                              write_error(lineno, "*base must be followed by white space");
                           }

                           for(currspot = 6; Character.isWhitespace(input_string.charAt(currspot)); ++currspot) {
                           }

                           if (!Character.isDigit(input_string.charAt(currspot))) {
                              write_error(lineno, "*base must be followed by a numeric value");
                           }

                           for(first_digit = currspot++; currspot < input_string.length() && Character.isDigit(input_string.charAt(currspot)); ++currspot) {
                           }

                           current_base = Integer.parseInt(input_string.substring(first_digit, currspot));
                        } else {
                           if (!written_classheader) {
                              written_classheader = true;
                              output_stream.write("import java.io.*;");
                              output_stream.newLine();
                              output_stream.write("import java.lang.*;");
                              output_stream.newLine();
                              output_stream.write("import java.util.*;");
                              output_stream.newLine();
                              output_stream.write("import weblogic.wtc.jatmi.*;");
                              output_stream.newLine();
                              output_stream.newLine();
                              output_stream.write("public final class " + input_file.getName());
                              output_stream.newLine();
                              output_stream.write("\timplements weblogic.wtc.jatmi.FldTbl");
                              output_stream.newLine();
                              output_stream.write("{");
                              output_stream.newLine();
                              output_stream.write("\tHashtable nametofieldHashTable;");
                              output_stream.newLine();
                              output_stream.write("\tHashtable fieldtonameHashTable;");
                              output_stream.newLine();
                           }

                           while(!Character.isWhitespace(input_string.charAt(currspot))) {
                              ++currspot;
                           }

                           String fldname;
                           for(fldname = input_string.substring(0, currspot); Character.isWhitespace(input_string.charAt(currspot)); ++currspot) {
                           }

                           if (!Character.isDigit(input_string.charAt(currspot))) {
                              write_error(lineno, "fieldname " + fldname + " must be followed by a numeric value");
                           }

                           for(first_digit = currspot++; Character.isDigit(input_string.charAt(currspot)); ++currspot) {
                           }

                           int fldno = Integer.parseInt(input_string.substring(first_digit, currspot)) + current_base;
                           if (fldno > 33554431) {
                              write_error(lineno, "fieldnumber " + fldno + " must be less than or equal to 33554431");
                           }

                           while(Character.isWhitespace(input_string.charAt(currspot))) {
                              ++currspot;
                           }

                           first_digit = currspot;

                           while(!Character.isWhitespace(input_string.charAt(currspot)) && input_string.charAt(currspot) != '\n') {
                              ++currspot;
                              if (currspot >= input_string_length) {
                                 currspot = input_string_length;
                                 break;
                              }
                           }

                           String typestring = input_string.substring(first_digit, currspot);
                           if (typestring.equals("char")) {
                              fldtype = 2;
                           } else if (typestring.equals("string")) {
                              fldtype = 5;
                           } else if (typestring.equals("short")) {
                              fldtype = 0;
                           } else if (typestring.equals("long")) {
                              fldtype = 1;
                           } else if (typestring.equals("float")) {
                              fldtype = 3;
                           } else if (typestring.equals("double")) {
                              fldtype = 4;
                           } else if (typestring.equals("carray")) {
                              fldtype = 6;
                           } else if (typestring.equals("ptr")) {
                              fldtype = 9;
                           } else if (typestring.equals("fml32")) {
                              fldtype = 10;
                           } else if (typestring.equals("view32")) {
                              fldtype = 11;
                           } else if (typestring.equals("mbstring")) {
                              fldtype = 12;
                           } else {
                              write_error(lineno, "fieldtype " + typestring + " is invalid");
                           }

                           int fldid = (fldtype & 63) << 25 | fldno & 33554431;
                           output_stream.write("\t/** number: " + fldno + "  type: " + typestring + " */");
                           output_stream.newLine();
                           output_stream.write("\tpublic final static int " + fldname + " = " + fldid + ";");
                           output_stream.newLine();
                           fldlist.add(fldname);
                           ++numFields;
                        }
                     }
                  }
               } catch (IOException var34) {
                  System.out.println("Unable to write output file " + var34);
               }
            }

            if (!written_classheader) {
               System.out.println("Did not find any field entries");
               throw new BuildException();
            } else {
               if (numFields > 3000) {
                  System.out.println("WARNING: Field tables with large numbers of fields can cause run-time");
                  System.out.println("problems in the Java Virtual Machine.  Consider creating a dynamically");
                  System.out.println("parsed field table by setting the DynRdHdr Java property to the run-time");
                  System.out.println("path name of the field definition file.");
               }

               try {
                  output_stream.flush();
                  output_stream.write("\n\tpublic String Fldid_to_name(int fldid)");
                  output_stream.newLine();
                  output_stream.write("\t{");
                  output_stream.newLine();
                  output_stream.write("\t\tif ( fieldtonameHashTable == null ) {");
                  output_stream.newLine();
                  output_stream.write("\t\t\tfieldtonameHashTable = new Hashtable();");
                  output_stream.newLine();
                  Iterator it = fldlist.listIterator();

                  String element;
                  while(it.hasNext()) {
                     element = (String)it.next();
                     output_stream.write("\t\t\t");
                     output_stream.write("fieldtonameHashTable.put(new Integer(" + element + "), \"" + element + "\");");
                     output_stream.newLine();
                  }

                  output_stream.write("\t\t}");
                  output_stream.newLine();
                  output_stream.newLine();
                  output_stream.write("\t\treturn ((String)fieldtonameHashTable.get(new Integer(fldid)));");
                  output_stream.newLine();
                  output_stream.write("\t}");
                  output_stream.newLine();
                  output_stream.newLine();
                  output_stream.write("\tpublic int name_to_Fldid(String name)");
                  output_stream.newLine();
                  output_stream.write("\t{");
                  output_stream.newLine();
                  output_stream.write("\t\tif ( nametofieldHashTable == null ) {");
                  output_stream.newLine();
                  output_stream.write("\t\t\tnametofieldHashTable = new Hashtable();");
                  output_stream.newLine();
                  it = fldlist.listIterator();

                  while(it.hasNext()) {
                     element = (String)it.next();
                     output_stream.write("\t\t\t");
                     output_stream.write("nametofieldHashTable.put(\"" + element + "\", new Integer(" + element + "));");
                     output_stream.newLine();
                  }

                  output_stream.write("\t\t}");
                  output_stream.newLine();
                  output_stream.newLine();
                  output_stream.write("\t\tInteger fld = (Integer)nametofieldHashTable.get(name);");
                  output_stream.newLine();
                  output_stream.write("\t\tif (fld == null) {");
                  output_stream.newLine();
                  output_stream.write("\t\t\treturn (-1);");
                  output_stream.newLine();
                  output_stream.write("\t\t} else {");
                  output_stream.newLine();
                  output_stream.write("\t\t\treturn (fld.intValue());");
                  output_stream.newLine();
                  output_stream.write("\t\t}");
                  output_stream.newLine();
                  output_stream.write("\t}");
                  output_stream.newLine();
                  output_stream.newLine();
                  output_stream.write("\tpublic String[] getFldNames()");
                  output_stream.newLine();
                  output_stream.write("\t{");
                  output_stream.newLine();
                  output_stream.write("\t\tString retval[] = new String[" + fldlist.size() + "];");
                  output_stream.newLine();
                  int i = 0;

                  for(it = fldlist.listIterator(); it.hasNext(); ++i) {
                     element = (String)it.next();
                     output_stream.write("\t\tretval[" + i + "] = new String(\"" + element + "\");");
                     output_stream.newLine();
                  }

                  output_stream.write("\t\treturn retval;");
                  output_stream.newLine();
                  output_stream.write("\t}");
                  output_stream.newLine();
                  output_stream.write("}");
                  output_stream.newLine();
                  output_stream.flush();
                  output_stream.close();
               } catch (IOException var32) {
                  System.out.println("Unable to write output file " + var32);
                  throw new BuildException();
               }
            }
         }
      } else {
         System.out.println("Usage: mkfldclass32 [package] [outputdirectory] fldtbl");
         throw new BuildException();
      }
   }
}

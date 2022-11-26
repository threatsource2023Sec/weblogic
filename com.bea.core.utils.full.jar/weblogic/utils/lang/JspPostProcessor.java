package weblogic.utils.lang;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class JspPostProcessor {
   private File classFile;
   private File javaFile;
   private String jspFileName;
   private static boolean debug = false;

   public static void main(String[] args) throws IOException {
      for(int i = 0; i < args.length; ++i) {
         if (args[i].endsWith(".java")) {
            (new JspPostProcessor(args[i])).run();
         }
      }

   }

   public static void callPostProcessor(String[] args) throws IOException {
      String sourceFileName = null;

      for(int i = 0; i < args.length; ++i) {
         if (args[i].endsWith(".java")) {
            sourceFileName = args[i];
            if (debug) {
               System.err.println("calling Preprocessor on : " + sourceFileName);
            }

            (new JspPostProcessor(args[i])).run();
         }
      }

   }

   public JspPostProcessor(String javaFileName) throws IOException {
      this.javaFile = new File(javaFileName);
      if (!this.javaFile.exists()) {
         throw new IOException("Cannot find java file: " + javaFileName);
      } else {
         String classFileName = javaFileName.substring(0, javaFileName.length() - 4) + "class";
         this.classFile = new File(classFileName);
         if (!this.classFile.exists()) {
            throw new IOException("Cannot find class file: " + classFileName);
         }
      }
   }

   public void run() throws IOException {
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(this.classFile));
      WLClass clazz = (new WLClass()).read(bis);
      bis.close();
      BufferedReader br = new BufferedReader(new FileReader(this.javaFile));
      Map lines = this.createSourceList(br);
      br.close();
      this.modifyServiceMethod(clazz, lines);
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(this.classFile));
      clazz.write(bos);
      bos.close();
   }

   public Map createSourceList(BufferedReader br) throws IOException {
      Map list = new HashMap();
      int lineno = 0;

      String line;
      while((line = br.readLine()) != null) {
         ++lineno;
         int position = line.lastIndexOf("//[ ");
         if (position != -1) {
            line = line.substring(position + 4);
            StringTokenizer st = new StringTokenizer(line);

            try {
               this.jspFileName = st.nextToken(";");
               st.nextToken(":");

               try {
                  int jspLine = Integer.parseInt(st.nextToken(": ]"));
                  list.put(new Integer(lineno), new Integer(jspLine));
               } catch (NoSuchElementException var8) {
               }
            } catch (NoSuchElementException var9) {
            }
         }
      }

      return list;
   }

   public void modifyServiceMethod(WLClass clazz, Map lines) throws IOException {
      WLMethod _jspService = null;

      String filename;
      for(int i = 0; i < clazz.methods_count; ++i) {
         WLMethod method = clazz.methods[i];
         String name = new String(((Utf8Info)clazz.constant_pool[method.name_index - 1].info).bytes);
         if (name.equals("_jspService")) {
            filename = new String(((Utf8Info)clazz.constant_pool[method.descriptor_index - 1].info).bytes);
            if (filename.equals("(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V")) {
               _jspService = method;
            }
         }
      }

      if (_jspService == null) {
         if (debug) {
            System.err.println("No _jspService(HttpServletRequest, HttpServletResponse) method found in class");
         }

      } else {
         WLLineNumberTable lineNumbers = null;
         WLCodeAttribute code = null;

         for(int i = 0; i < _jspService.attributes_count; ++i) {
            WLAttribute attribute = _jspService.attributes[i];
            String name = new String(((Utf8Info)clazz.constant_pool[attribute.attribute_name_index - 1].info).bytes);
            if (name.equals("Code")) {
               code = new WLCodeAttribute(attribute.attribute_name_index, attribute.attribute_length);
               code.read(new ByteArrayInputStream(attribute.info));

               for(int j = 0; j < code.attributes_count; ++j) {
                  attribute = code.attributes[j];
                  name = new String(((Utf8Info)clazz.constant_pool[attribute.attribute_name_index - 1].info).bytes);
                  if (name.equals("LineNumberTable")) {
                     lineNumbers = new WLLineNumberTable(attribute.attribute_name_index, attribute.attribute_length);
                     lineNumbers.read(new ByteArrayInputStream(attribute.info));
                     _jspService.attributes[i] = code;
                     code.attributes[j] = lineNumbers;
                  }
               }
            }
         }

         if (lineNumbers == null) {
            if (debug) {
               System.err.println("No line number table present for _jspService method");
            }

         } else {
            WLSourceFile sourceFile = null;

            for(int i = 0; i < clazz.attributes_count; ++i) {
               WLAttribute attribute = clazz.attributes[i];
               String name = new String(((Utf8Info)clazz.constant_pool[attribute.attribute_name_index - 1].info).bytes);
               if (name.equals("SourceFile")) {
                  sourceFile = new WLSourceFile(attribute.attribute_name_index, attribute.attribute_length);
                  sourceFile.read(new ByteArrayInputStream(attribute.info));
               }
            }

            if (sourceFile == null) {
               if (debug) {
                  System.err.println("No source file attribute");
               }

            } else if (lines.size() == 0) {
               if (debug) {
                  System.err.println("No line numbers present in the generated _jspService method");
               }

            } else {
               this.jspFileName = this.jspFileName.substring(this.jspFileName.lastIndexOf("/") + 1);
               filename = new String(((Utf8Info)clazz.constant_pool[sourceFile.sourcefile_index - 1].info).bytes);
               if (filename.equals(this.jspFileName)) {
                  if (debug) {
                     System.err.println("Already converted.");
                  }

               } else {
                  byte[] jspFileNameBytes = this.jspFileName.getBytes("utf-8");
                  Utf8Info sourceFileName = (Utf8Info)clazz.constant_pool[sourceFile.sourcefile_index - 1].info;
                  sourceFileName.length = (short)jspFileNameBytes.length;
                  sourceFileName.bytes = jspFileNameBytes;
                  short j = 0;
                  short lastline = 0;

                  int i;
                  int difference;
                  for(i = 0; i < lineNumbers.line_number_table_length; ++i) {
                     difference = lineNumbers.line_number_table[i][1];
                     Integer jspLineNoInteger = (Integer)lines.get(new Integer(difference));
                     if (jspLineNoInteger != null) {
                        int jspLineNo = jspLineNoInteger;
                        lineNumbers.line_number_table[j][0] = lineNumbers.line_number_table[i][0];
                        lineNumbers.line_number_table[j][1] = (short)jspLineNo;
                        lastline = (short)jspLineNo;
                        ++j;
                     } else if (i == lineNumbers.line_number_table_length - 1) {
                        lineNumbers.line_number_table[j][0] = lineNumbers.line_number_table[i][0];
                        lineNumbers.line_number_table[j][1] = lastline;
                        ++j;
                     }
                  }

                  i = j * 4 + 2;
                  difference = lineNumbers.attribute_length - i;
                  lineNumbers.attribute_length -= difference;
                  code.attribute_length -= difference;
                  lineNumbers.line_number_table_length = j;
               }
            }
         }
      }
   }

   public static void cleanUpFiles(String[] sources) {
      for(int i = 0; i < sources.length; ++i) {
         File f = new File(sources[i]);
         if (f.exists()) {
            f.delete();
         }
      }

   }
}

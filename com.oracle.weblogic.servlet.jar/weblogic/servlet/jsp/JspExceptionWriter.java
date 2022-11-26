package weblogic.servlet.jsp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.ServletContext;
import weblogic.servlet.internal.WebAppServletContext;

public final class JspExceptionWriter extends PrintWriter {
   private String packagePrefix;
   private ServletContext sc;
   private boolean transformed = false;

   public JspExceptionWriter(String packagePrefix, Writer out, ServletContext sc) {
      super(out, true);
      this.packagePrefix = packagePrefix;
      this.sc = sc;
   }

   public void println(String line) {
      if (line.startsWith("\tat " + this.packagePrefix + ".")) {
         try {
            line = line.substring(4);
            StringTokenizer st = new StringTokenizer(line, "()");
            String class_method = st.nextToken();
            String classname = class_method.substring(0, class_method.lastIndexOf("."));
            String method = class_method.substring(class_method.lastIndexOf(".") + 1);
            String source_lineno = st.nextToken();
            if (source_lineno.equals("Native Method")) {
               super.println("\tat " + line + " [native method]");
            } else {
               st = new StringTokenizer(source_lineno, ":");
               String filename = st.nextToken();
               int codeLine = Integer.parseInt(st.nextToken());
               String resname = classname.replace('.', '/') + ".java";
               ClassLoader cl = ((WebAppServletContext)this.sc).getServletClassLoader();
               InputStream is = cl.getResourceAsStream(resname);
               if (is == null) {
                  super.println("\tat " + line + " [no source]");
               } else {
                  Vector jspLine = new Vector();
                  BufferedReader br = new BufferedReader(new InputStreamReader(is));
                  int lineno = 0;

                  while((line = br.readLine()) != null) {
                     ++lineno;
                     if (lineno == codeLine) {
                        int position = line.lastIndexOf("//[ ");
                        if (position != -1) {
                           line = line.substring(position + 4);
                           st = new StringTokenizer(line);

                           try {
                              jspLine.addElement(st.nextToken(";"));
                              st.nextToken(":");

                              try {
                                 jspLine.addElement(st.nextToken(": ]"));
                                 br.close();
                                 is = this.sc.getResourceAsStream((String)jspLine.elementAt(0));
                                 if (is != null) {
                                    br = new BufferedReader(new InputStreamReader(is));
                                    lineno = 1;
                                    int lineNumber = Integer.parseInt((String)jspLine.elementAt(1));

                                    while((line = br.readLine()) != null) {
                                       if (lineno++ == lineNumber) {
                                          jspLine.addElement(line);
                                          break;
                                       }
                                    }

                                    br.close();
                                 }
                              } catch (NoSuchElementException var18) {
                                 jspLine = null;
                              }
                           } catch (NoSuchElementException var19) {
                              jspLine = null;
                           }
                           break;
                        }
                     }
                  }

                  if (jspLine == null) {
                     super.println("\tat " + line + " [could not parse]");
                  } else {
                     String file = (String)jspLine.elementAt(0);
                     super.println("\tat " + classname + (method.equals("_jspService") ? "" : "." + method) + "(" + file + ":" + jspLine.elementAt(1) + ")");
                     this.transformed = true;
                  }
               }
            }
         } catch (NoSuchElementException var20) {
            super.println("\tat " + line + " [could not parse]");
         } catch (NumberFormatException var21) {
            super.println("\tat " + line + " [no line number]");
         } catch (IOException var22) {
            super.println("\tat " + line);
         }
      } else {
         if (!this.transformed) {
            super.println(line);
         }

      }
   }

   public void println(Object i) {
      this.println(i.toString());
   }

   public void println(char[] i) {
      this.println(new String(i));
   }
}

package weblogicx.jsp.tags;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class FormAnchorTag extends BodyTagSupport {
   int formCount;
   char[] buf = new char[6];

   public int doAfterBody() throws JspException {
      try {
         return this.doit();
      } catch (IOException var2) {
         return 0;
      }
   }

   public int doit() throws IOException {
      Reader r = this.getBodyContent().getReader();
      Writer w = this.getPreviousOut();

      int i;
      while((i = r.read()) > 0) {
         if (i != 60) {
            w.write(i);
         } else {
            int buflen = 0;
            this.buf[buflen++] = (char)i;
            if ((i = r.read()) < 0) {
               w.write(this.buf, 0, buflen);
               break;
            }

            this.buf[buflen++] = (char)i;
            if (i != 102 && i != 70) {
               w.write(this.buf, 0, buflen);
            } else {
               if ((i = r.read()) < 0) {
                  w.write(this.buf, 0, buflen);
                  break;
               }

               this.buf[buflen++] = (char)i;
               if (i != 111 && i != 79) {
                  w.write(this.buf, 0, buflen);
               } else {
                  if ((i = r.read()) < 0) {
                     w.write(this.buf, 0, buflen);
                     break;
                  }

                  this.buf[buflen++] = (char)i;
                  if (i != 114 && i != 82) {
                     w.write(this.buf, 0, buflen);
                  } else {
                     if ((i = r.read()) < 0) {
                        w.write(this.buf, 0, buflen);
                        break;
                     }

                     this.buf[buflen++] = (char)i;
                     if (i != 109 && i != 77) {
                        w.write(this.buf, 0, buflen);
                     } else {
                        if ((i = r.read()) < 0) {
                           w.write(this.buf, 0, buflen);
                           break;
                        }

                        this.buf[buflen++] = (char)i;
                        if (i != 32 && i != 13 && i != 10 && i != 9) {
                           w.write(this.buf, 0, buflen);
                        } else {
                           w.write("<A NAME=\"FORM-" + this.formCount + "\"></A>\n");
                           w.write(this.buf, 0, buflen);

                           while((i = r.read()) > 0) {
                              if (i == -1) {
                                 return 0;
                              }

                              w.write(i);
                              if (i == 62) {
                                 w.write("<input type=\"hidden\" name=\"return-to-anchor\" value=\"FORM-" + this.formCount + "\">");
                                 break;
                              }
                           }

                           ++this.formCount;
                        }
                     }
                  }
               }
            }
         }
      }

      return 0;
   }

   public void release() {
      super.release();
      this.formCount = 0;
   }
}

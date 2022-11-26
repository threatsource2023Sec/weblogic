package org.apache.taglibs.standard.tag.common.core;

import java.io.IOException;
import java.io.Reader;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class OutSupport extends BodyTagSupport {
   protected Object value;
   protected String def;
   protected boolean escapeXml;
   private boolean needBody;

   public OutSupport() {
      this.init();
   }

   private void init() {
      this.value = this.def = null;
      this.escapeXml = true;
      this.needBody = false;
   }

   public void release() {
      super.release();
      this.init();
   }

   public int doStartTag() throws JspException {
      this.needBody = false;
      this.bodyContent = null;

      try {
         if (this.value != null) {
            out(this.pageContext, this.escapeXml, this.value);
            return 0;
         } else if (this.def == null) {
            this.needBody = true;
            return 2;
         } else {
            if (this.def != null) {
               out(this.pageContext, this.escapeXml, this.def);
            }

            return 0;
         }
      } catch (IOException var2) {
         throw new JspException(var2.toString(), var2);
      }
   }

   public int doEndTag() throws JspException {
      try {
         if (!this.needBody) {
            return 6;
         } else {
            if (this.bodyContent != null && this.bodyContent.getString() != null) {
               out(this.pageContext, this.escapeXml, this.bodyContent.getString().trim());
            }

            return 6;
         }
      } catch (IOException var2) {
         throw new JspException(var2.toString(), var2);
      }
   }

   public static void out(PageContext pageContext, boolean escapeXml, Object obj) throws IOException {
      JspWriter w = pageContext.getOut();
      Reader reader;
      char[] buf;
      int count;
      if (!escapeXml) {
         if (obj instanceof Reader) {
            reader = (Reader)obj;
            buf = new char[4096];

            while((count = reader.read(buf, 0, 4096)) != -1) {
               w.write(buf, 0, count);
            }
         } else {
            w.write(obj.toString());
         }
      } else if (obj instanceof Reader) {
         reader = (Reader)obj;
         buf = new char[4096];

         while((count = reader.read(buf, 0, 4096)) != -1) {
            writeEscapedXml(buf, count, w);
         }
      } else {
         String text = obj.toString();
         writeEscapedXml(text.toCharArray(), text.length(), w);
      }

   }

   private static void writeEscapedXml(char[] buffer, int length, JspWriter w) throws IOException {
      int start = 0;

      for(int i = 0; i < length; ++i) {
         char c = buffer[i];
         if (c <= '>') {
            char[] escaped = Util.specialCharactersRepresentation[c];
            if (escaped != null) {
               if (start < i) {
                  w.write(buffer, start, i - start);
               }

               w.write(escaped);
               start = i + 1;
            }
         }
      }

      if (start < length) {
         w.write(buffer, start, length - start);
      }

   }
}

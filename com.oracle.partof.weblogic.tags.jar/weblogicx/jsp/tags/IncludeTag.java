package weblogicx.jsp.tags;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class IncludeTag extends BodyTagSupport {
   private String url;
   private String contentType;

   public void setUrl(String url) {
      this.url = url;
   }

   public String getUrl() {
      return this.url;
   }

   public void setContentType(String contentType) {
      this.contentType = contentType;
   }

   public String getContentType() {
      return this.contentType;
   }

   public void release() {
      this.url = null;
      this.contentType = null;
   }

   public int doEndTag() throws JspException {
      JspWriter out = this.pageContext.getOut();

      try {
         URL url = new URL(this.url);
         URLConnection urlc = url.openConnection();
         urlc.setDoInput(true);
         char[] chars = new char[8192];
         BodyContent bc = this.getBodyContent();
         if (bc != null && bc.getString().length() != 0) {
            urlc.setDoOutput(true);
            if (this.contentType != null) {
               urlc.setRequestProperty("Content-Type", this.contentType);
            }

            OutputStreamWriter osw = new OutputStreamWriter(urlc.getOutputStream());
            bc.writeOut(osw);
            osw.flush();
         } else {
            urlc.setDoOutput(false);
         }

         Reader is = new BufferedReader(new InputStreamReader(urlc.getInputStream()));

         int length;
         while((length = is.read(chars, 0, chars.length)) != -1) {
            try {
               out.write(chars, 0, length);
            } catch (IOException var9) {
            }
         }

         is.close();
         return 6;
      } catch (Exception var10) {
         throw new JspException("Failed to read URL: " + var10);
      }
   }
}

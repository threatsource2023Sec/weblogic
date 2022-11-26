package weblogic.xml.saaj.mime4j.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import weblogic.xml.saaj.mime4j.util.CharsetUtil;
import weblogic.xml.saaj.mime4j.util.TempFile;
import weblogic.xml.saaj.mime4j.util.TempPath;
import weblogic.xml.saaj.mime4j.util.TempStorage;
import weblogic.xml.saaj.util.CopyUtils;

class TempFileTextBody extends AbstractBody implements TextBody {
   private String mimeCharset;
   private TempFile tempFile;

   public TempFileTextBody(InputStream is) throws IOException {
      this(is, (String)null);
   }

   public TempFileTextBody(InputStream is, String mimeCharset) throws IOException {
      this.mimeCharset = null;
      this.tempFile = null;
      this.mimeCharset = mimeCharset;
      TempPath tempPath = TempStorage.getInstance().getRootTempPath();
      this.tempFile = tempPath.createTempFile("attachment", ".txt");
      OutputStream out = this.tempFile.getOutputStream();
      CopyUtils.copy(is, out);
      out.close();
   }

   public Reader getReader() throws UnsupportedEncodingException, IOException {
      String javaCharset = null;
      if (this.mimeCharset != null) {
         javaCharset = CharsetUtil.toJavaCharset(this.mimeCharset);
      }

      if (javaCharset == null) {
         javaCharset = "ISO8859-1";
      }

      return new InputStreamReader(this.tempFile.getInputStream(), javaCharset);
   }

   public void writeTo(OutputStream out) throws IOException {
   }
}

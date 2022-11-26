package weblogic.xml.saaj.mime4j.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import weblogic.xml.saaj.mime4j.util.TempFile;
import weblogic.xml.saaj.mime4j.util.TempPath;
import weblogic.xml.saaj.mime4j.util.TempStorage;
import weblogic.xml.saaj.util.CopyUtils;

class TempFileBinaryBody extends AbstractBody implements BinaryBody {
   private Entity parent = null;
   private TempFile tempFile = null;

   public TempFileBinaryBody(InputStream is) throws IOException {
      TempPath tempPath = TempStorage.getInstance().getRootTempPath();
      this.tempFile = tempPath.createTempFile("attachment", ".bin");
      OutputStream out = this.tempFile.getOutputStream();
      CopyUtils.copy(is, out);
      out.close();
   }

   public Entity getParent() {
      return this.parent;
   }

   public void setParent(Entity parent) {
      this.parent = parent;
   }

   public InputStream getInputStream() throws IOException {
      return this.tempFile.getInputStream();
   }

   public void writeTo(OutputStream out) throws IOException {
   }
}

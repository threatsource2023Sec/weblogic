package weblogic.xml.saaj.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;

public class GifDataContentHandler implements DataContentHandler {
   private static ActivationDataFlavor myDF = new ActivationDataFlavor(Image.class, "image/gif", "GIF Image");

   protected ActivationDataFlavor getDF() {
      return myDF;
   }

   public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[]{this.getDF()};
   }

   public Object getTransferData(DataFlavor df, DataSource ds) throws IOException {
      return this.getDF().equals(df) ? this.getContent(ds) : null;
   }

   public Object getContent(DataSource ds) throws IOException {
      InputStream is = ds.getInputStream();
      int pos = 0;
      int chunk_size = true;

      int count;
      byte[] buf;
      byte[] tbuf;
      for(buf = new byte[1024]; (count = is.read(buf, pos, 1024)) != -1; buf = tbuf) {
         pos += count;
         tbuf = new byte[pos + 1024];
         System.arraycopy(buf, 0, tbuf, 0, pos);
      }

      Toolkit tk = Toolkit.getDefaultToolkit();
      return tk.createImage(buf, 0, pos);
   }

   public void writeTo(Object obj, String type, OutputStream os) throws IOException {
      if (!(obj instanceof Image)) {
         throw new IOException("\"" + this.getDF().getMimeType() + "\" DataContentHandler requires Image object, was given object of type " + obj.getClass().toString());
      } else {
         throw new IOException("GIF encoding not supported");
      }
   }
}

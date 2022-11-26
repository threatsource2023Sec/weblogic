package weblogic.xml.saaj.util;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.imageio.ImageIO;

public class JpegDataContentHandler extends Component implements DataContentHandler {
   public final String STR_SRC = "java.awt.Image";

   public DataFlavor[] getTransferDataFlavors() {
      DataFlavor[] flavors = new DataFlavor[1];

      try {
         flavors[0] = new ActivationDataFlavor(Class.forName("java.awt.Image"), "image/jpeg", "JPEG");
      } catch (Exception var3) {
         System.out.println(var3);
      }

      return flavors;
   }

   public Object getTransferData(DataFlavor df, DataSource ds) {
      if (df.getMimeType().startsWith("image/jpeg") && df.getRepresentationClass().getName().equals("java.awt.Image")) {
         InputStream inputStream = null;
         BufferedImage jpegLoadImage = null;

         try {
            inputStream = ds.getInputStream();
            jpegLoadImage = ImageIO.read(inputStream);
         } catch (Exception var6) {
            System.out.println(var6);
         }

         return jpegLoadImage;
      } else {
         return null;
      }
   }

   public Object getContent(DataSource ds) {
      InputStream inputStream = null;
      BufferedImage jpegLoadImage = null;

      try {
         inputStream = ds.getInputStream();
         jpegLoadImage = ImageIO.read(inputStream);
      } catch (Exception var5) {
      }

      return jpegLoadImage;
   }

   public void writeTo(Object obj, String mimeType, OutputStream os) throws IOException {
      if (!mimeType.equals("image/jpeg")) {
         throw new IOException("Invalid content type \"" + mimeType + "\" for ImageContentHandler");
      } else if (obj == null) {
         throw new IOException("Null object for ImageContentHandler");
      } else {
         try {
            BufferedImage bufImage = null;
            if (obj instanceof BufferedImage) {
               bufImage = (BufferedImage)obj;
            } else {
               Image img = (Image)obj;
               MediaTracker tracker = new MediaTracker(this);
               tracker.addImage(img, 0);
               tracker.waitForAll();
               if (tracker.isErrorAny()) {
                  throw new IOException("Error while loading image");
               }

               bufImage = new BufferedImage(img.getWidth((ImageObserver)null), img.getHeight((ImageObserver)null), 1);
               Graphics g = bufImage.createGraphics();
               g.drawImage(img, 0, 0, (ImageObserver)null);
            }

            ImageIO.write(bufImage, "jpeg", os);
         } catch (Exception var8) {
            throw new IOException("Unable to run the JPEG Encoder on a stream " + var8.getMessage());
         }
      }
   }
}

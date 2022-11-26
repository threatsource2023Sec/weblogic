package utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class der2pem {
   private static final String usage = "\tUsage: java utils.der2pem derFile [headerFile] [footerFile]\n\n\tWrites file with the same name but with .pem extension\n\tto the same directory as the .der file. The header and footer files\n\tshould contain appropriate pem headers and footers\n";
   private static final String CERT_HEADER = "-----BEGIN CERTIFICATE-----\n";
   private static final String CERT_FOOTER = "-----END CERTIFICATE-----\n";
   private static final String ENCR_KEY_HEADER = "-----BEGIN ENCRYPTED PRIVATE KEY-----\n";
   private static final String ENCR_KEY_FOOTER = "-----END ENCRYPTED PRIVATE KEY-----\n";
   private static final int BYTES_PER_LINE = 60;
   private static final int EOF = -1;
   private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

   private static Object openFile(String fileName, boolean toRead) {
      Object stream = null;

      try {
         if (toRead) {
            stream = new FileInputStream(fileName);
         } else {
            stream = new FileOutputStream(fileName);
         }
      } catch (FileNotFoundException var4) {
         System.out.println("\n\tCannot open file " + fileName + ".\n\t" + var4.getMessage() + "\n\n" + "\tUsage: java utils.der2pem derFile [headerFile] [footerFile]\n\n\tWrites file with the same name but with .pem extension\n\tto the same directory as the .der file. The header and footer files\n\tshould contain appropriate pem headers and footers\n");
         System.exit(1);
      }

      return stream;
   }

   private static byte[] readFile(String fileName) throws IOException {
      FileInputStream in = (FileInputStream)openFile(fileName, true);
      ByteArrayOutputStream bs = new ByteArrayOutputStream(in.available());
      int b = false;

      int b;
      while((b = in.read()) != -1) {
         bs.write(b);
      }

      in.close();
      return bs.toByteArray();
   }

   public static void main(String[] argv) throws Exception {
      if (argv.length < 1) {
         System.out.println("\tUsage: java utils.der2pem derFile [headerFile] [footerFile]\n\n\tWrites file with the same name but with .pem extension\n\tto the same directory as the .der file. The header and footer files\n\tshould contain appropriate pem headers and footers\n");
         System.exit(1);
      }

      String orgFileName = argv[0];
      int index = orgFileName.lastIndexOf(".");
      if (index < 0 || !orgFileName.substring(index + 1).equalsIgnoreCase("der")) {
         System.out.println("\nDer input file with extention der is expected\n\n\tUsage: java utils.der2pem derFile [headerFile] [footerFile]\n\n\tWrites file with the same name but with .pem extension\n\tto the same directory as the .der file. The header and footer files\n\tshould contain appropriate pem headers and footers\n");
         System.exit(1);
      }

      String newFileName = orgFileName.substring(0, index) + ".pem";
      InputStream in = (InputStream)openFile(orgFileName, true);
      OutputStream out = (OutputStream)openFile(newFileName, false);
      byte[] header = argv.length > 1 ? readFile(argv[1]) : null;
      byte[] footer = argv.length > 2 ? readFile(argv[2]) : null;
      if (header == null) {
         header = "-----BEGIN CERTIFICATE-----\n".getBytes();
      }

      if (footer == null) {
         footer = "-----END CERTIFICATE-----\n".getBytes();
      }

      convert(in, out, header, footer);
      in.close();
      out.close();
   }

   public static void convertCertificate(InputStream in, OutputStream out) throws IOException {
      convert(in, out, "-----BEGIN CERTIFICATE-----\n".getBytes(), "-----END CERTIFICATE-----\n".getBytes());
   }

   public static void convertEncryptedKey(InputStream in, OutputStream out) throws IOException {
      convert(in, out, "-----BEGIN ENCRYPTED PRIVATE KEY-----\n".getBytes(), "-----END ENCRYPTED PRIVATE KEY-----\n".getBytes());
   }

   public static void convert(InputStream in, OutputStream out, byte[] header, byte[] footer) throws IOException {
      out.write(header);
      byte[] atom = new byte[3];
      int count = 0;
      int result = false;
      boolean need_newline = false;

      int result;
      while((result = in.read(atom)) != -1) {
         if (need_newline) {
            out.write(10);
            need_newline = false;
         }

         encodeAtom(out, atom, 0, result);
         if (result < 3) {
            break;
         }

         count += 4;
         if (count > 60) {
            need_newline = true;
            count = 0;
         }
      }

      out.write(10);
      out.write(footer);
   }

   public static void encodeAtom(OutputStream outStream, byte[] data, int offset, int len) throws IOException {
      byte a;
      if (len == 1) {
         a = data[offset];
         byte b = 0;
         byte c = false;
         outStream.write(pem_array[a >>> 2 & 63]);
         outStream.write(pem_array[(a << 4 & 48) + (b >>> 4 & 15)]);
         outStream.write(61);
         outStream.write(61);
      } else {
         byte b;
         if (len == 2) {
            a = data[offset];
            b = data[offset + 1];
            byte c = 0;
            outStream.write(pem_array[a >>> 2 & 63]);
            outStream.write(pem_array[(a << 4 & 48) + (b >>> 4 & 15)]);
            outStream.write(pem_array[(b << 2 & 60) + (c >>> 6 & 3)]);
            outStream.write(61);
         } else {
            a = data[offset];
            b = data[offset + 1];
            byte c = data[offset + 2];
            outStream.write(pem_array[a >>> 2 & 63]);
            outStream.write(pem_array[(a << 4 & 48) + (b >>> 4 & 15)]);
            outStream.write(pem_array[(b << 2 & 60) + (c >>> 6 & 3)]);
            outStream.write(pem_array[c & 63]);
         }
      }

   }
}

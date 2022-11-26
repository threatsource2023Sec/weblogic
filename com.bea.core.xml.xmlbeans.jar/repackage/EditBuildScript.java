package repackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class EditBuildScript {
   public static void main(String[] args) throws Exception {
      if (args.length != 3) {
         throw new IllegalArgumentException("Wrong number of arguments");
      } else {
         args[0] = args[0].replace('/', File.separatorChar);
         File buildFile = new File(args[0]);
         StringBuffer sb = readFile(buildFile);
         String tokenStr = "<property name=\"" + args[1] + "\" value=\"";
         int i = sb.indexOf(tokenStr);
         if (i < 0) {
            throw new IllegalArgumentException("Can't find token: " + tokenStr);
         } else {
            int j;
            for(j = i + tokenStr.length(); sb.charAt(j) != '"'; ++j) {
            }

            sb.replace(i + tokenStr.length(), j, args[2]);
            writeFile(buildFile, sb);
         }
      }
   }

   static StringBuffer readFile(File f) throws IOException {
      InputStream in = new FileInputStream(f);
      Reader r = new InputStreamReader(in);
      StringWriter w = new StringWriter();
      copy(r, w);
      w.close();
      r.close();
      in.close();
      return w.getBuffer();
   }

   static void writeFile(File f, StringBuffer chars) throws IOException {
      OutputStream out = new FileOutputStream(f);
      Writer w = new OutputStreamWriter(out);
      Reader r = new StringReader(chars.toString());
      copy(r, w);
      r.close();
      w.close();
      out.close();
   }

   static void copy(Reader r, Writer w) throws IOException {
      char[] buffer = new char[16384];

      while(true) {
         int n = r.read(buffer, 0, buffer.length);
         if (n < 0) {
            return;
         }

         w.write(buffer, 0, n);
      }
   }
}

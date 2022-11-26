package weblogic.utils.jars;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import weblogic.utils.collections.ArrayMap;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public final class ManifestEntry {
   private static char SPACE = ' ';
   private static String NEWLINE = System.getProperty("line.separator");
   private static byte[] NEWLINE_BYTES;
   private File root;
   private File file;
   private String name;
   private final ArrayMap attributes;
   private final ArrayList keys;

   ManifestEntry() {
      this.attributes = new ArrayMap();
      this.keys = new ArrayList();
   }

   public ManifestEntry(File root, File file) {
      this.attributes = new ArrayMap();
      this.keys = new ArrayList();
      if (root == null) {
         root = new File("/");
      }

      if (file.isDirectory()) {
         throw new IllegalArgumentException("bad file: is a directory: " + file);
      } else if (!root.isDirectory()) {
         throw new IllegalArgumentException("bad root: not a directory: " + root);
      } else {
         this.file = file;
         this.root = root;
      }
   }

   public ManifestEntry(File file, File root, Map attributes) {
      this(file, root);
      Iterator i = attributes.values().iterator();

      while(i.hasNext()) {
         String key = (String)i.next();
         this.addHeader(key, (String)attributes.get(key));
      }

   }

   public Iterator getHeaders() {
      return this.keys.iterator();
   }

   public void addHeader(String key, String value) {
      String lcKey = key.toLowerCase();
      if (this.attributes.containsKey(lcKey)) {
         this.removeFromKeys(key);
      }

      this.keys.add(key);
      this.attributes.put(lcKey, value);
   }

   public String getHeader(String key) {
      return (String)this.attributes.get(key.toLowerCase());
   }

   public boolean removeHeader(String key) {
      return this.removeFromKeys(key) && this.attributes.remove(this.getHeader(key)) != null;
   }

   private boolean removeFromKeys(String key) {
      Iterator i = this.keys.iterator();

      String existingKey;
      do {
         if (!i.hasNext()) {
            return false;
         }

         existingKey = (String)i.next();
      } while(!existingKey.equalsIgnoreCase(key));

      this.keys.remove(existingKey);
      return true;
   }

   public File getFile() {
      return this.file;
   }

   public String getName() {
      String rootPath = null;

      try {
         if (this.name == null) {
            if (this.root == null) {
               return this.getHeader("Name");
            }

            rootPath = this.root.getCanonicalPath();
            String path = this.file.getCanonicalPath();
            this.name = path.substring(rootPath.length() + 1);
            this.name = this.name.replace(File.separatorChar, '/');
         }
      } catch (IOException var4) {
         File problemF = this.file;
         if (rootPath == null) {
            problemF = this.root;
         }

         throw new InternalError("IO error while trying to compute name from: " + problemF + "\t" + var4);
      }

      return this.name;
   }

   public boolean stream(InputStream in) throws IOException {
      DataInputStream din = new DataInputStream(in);

      while(true) {
         String line = din.readLine();
         if (line == null) {
            return false;
         }

         if (line.length() == 0) {
            return true;
         }

         int sepCharPos = line.indexOf(58);
         if (sepCharPos > 0) {
            String key = line.substring(0, sepCharPos);
            String value = line.substring(sepCharPos + 2, line.length());
            this.addHeader(key, value);
         }
      }
   }

   public void stream(OutputStream out) throws IOException {
      if (this.name != null) {
         String header = "Name: " + this.name;
         out.write(header.getBytes(), 0, header.length());
         out.write(NEWLINE_BYTES);
      }

      int count = this.keys.size();
      Iterator i = this.keys.iterator();

      while(i.hasNext()) {
         String key = (String)i.next();
         String value = this.getHeader(key);
         this.breakUpAndWriteItOutAsNecessary(key, value, out);
         if (count-- != 0) {
            out.write(NEWLINE_BYTES);
         }
      }

   }

   private void breakUpAndWriteItOutAsNecessary(String key, String value, OutputStream out) throws IOException {
      int valueLen = value.length();
      String header = key + ": ";
      int headerLen = header.length();
      int excess = headerLen + valueLen - 72;
      if (excess <= 0) {
         header = header + value;
         out.write(header.getBytes(), 0, header.length());
      } else {
         int index = 72 - headerLen;
         int spacesToAdd = excess / 72;
         boolean prependSpace = false;
         if (excess % 72 > 0) {
            ++spacesToAdd;
         }

         for(int i = 0; i <= spacesToAdd; ++i) {
            if (prependSpace) {
               out.write(SPACE);
               if (valueLen > 71) {
                  out.write(value.getBytes(), index, 71);
                  valueLen -= 71;
                  index += 71;
                  out.write(NEWLINE_BYTES);
               } else {
                  out.write(value.getBytes(), index, valueLen);
               }
            } else {
               prependSpace = true;
               out.write(header.getBytes());
               out.write(value.getBytes(), 0, index);
               valueLen -= index;
               out.write(NEWLINE_BYTES);
            }
         }

      }
   }

   public String toString() {
      try {
         UnsyncByteArrayOutputStream out = new UnsyncByteArrayOutputStream();
         this.stream((OutputStream)out);
         return new String(out.toByteArray());
      } catch (IOException var2) {
         return "error trying to print manifest entry " + var2;
      }
   }

   static {
      NEWLINE_BYTES = NEWLINE.getBytes();
   }
}

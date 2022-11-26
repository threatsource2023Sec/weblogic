package weblogic.utils.jars;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import weblogic.utils.collections.ArrayMap;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public final class Manifest {
   private static final String MANIFEST_VERSION = "1.0";
   private static String LINE_SEPARATOR = System.getProperty("line.separator");
   private static byte[] ENTRY_SEPARATOR;
   private ManifestEntry headers;
   private final Map entries;

   public Manifest(Map entries) {
      this.entries = entries;
   }

   public Manifest() {
      this.entries = new ArrayMap();
      this.headers = new ManifestEntry();
      this.headers.addHeader("Manifest-Version", "1.0");
   }

   public Map getEntries() {
      return this.entries;
   }

   public ManifestEntry getEntry(String name) {
      return (ManifestEntry)this.entries.get(name);
   }

   public void addEntry(ManifestEntry entry) {
      this.entries.put(entry.getName(), entry);
   }

   public Object removeEntry(String name) {
      return this.entries.remove(this.getEntry(name));
   }

   public ManifestEntry getHeaders() {
      return this.headers;
   }

   public void stream(InputStream in) throws IOException {
      if (this.headers.stream(in)) {
         while(true) {
            ManifestEntry entry = new ManifestEntry();
            if (!entry.stream(in)) {
               break;
            }

            this.addEntry(entry);
         }
      }

   }

   public void stream(OutputStream out) throws IOException {
      this.headers.stream(out);
      out.write(ENTRY_SEPARATOR);
      int count = this.entries.size();
      Iterator i = this.entries.values().iterator();

      while(i.hasNext()) {
         ManifestEntry entry = (ManifestEntry)i.next();
         entry.stream(out);
         if (count-- != 0) {
            out.write(ENTRY_SEPARATOR);
         }
      }

   }

   public String toString() {
      try {
         UnsyncByteArrayOutputStream out = new UnsyncByteArrayOutputStream();
         this.stream((OutputStream)out);
         return new String(out.toByteArray());
      } catch (IOException var2) {
         return "error trying to print manifest " + var2;
      }
   }

   static {
      ENTRY_SEPARATOR = LINE_SEPARATOR.getBytes();
   }
}

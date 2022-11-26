package org.python.jline.console.history;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Iterator;
import org.python.jline.internal.Log;
import org.python.jline.internal.Preconditions;

public class FileHistory extends MemoryHistory implements PersistentHistory, Flushable {
   private final File file;

   public FileHistory(File file) throws IOException {
      this.file = ((File)Preconditions.checkNotNull(file)).getAbsoluteFile();
      this.load(file);
   }

   public File getFile() {
      return this.file;
   }

   public void load(File file) throws IOException {
      Preconditions.checkNotNull(file);
      if (file.exists()) {
         Log.trace("Loading history from: ", file);
         FileReader reader = null;

         try {
            reader = new FileReader(file);
            this.load((Reader)reader);
         } finally {
            if (reader != null) {
               reader.close();
            }

         }
      }

   }

   public void load(InputStream input) throws IOException {
      Preconditions.checkNotNull(input);
      this.load((Reader)(new InputStreamReader(input)));
   }

   public void load(Reader reader) throws IOException {
      Preconditions.checkNotNull(reader);
      BufferedReader input = new BufferedReader(reader);

      String item;
      while((item = input.readLine()) != null) {
         this.internalAdd(item);
      }

   }

   public void flush() throws IOException {
      Log.trace("Flushing history");
      if (!this.file.exists()) {
         File dir = this.file.getParentFile();
         if (!dir.exists() && !dir.mkdirs()) {
            Log.warn("Failed to create directory: ", dir);
         }

         if (!this.file.createNewFile()) {
            Log.warn("Failed to create file: ", this.file);
         }
      }

      PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(this.file)));

      try {
         Iterator var2 = this.iterator();

         while(var2.hasNext()) {
            History.Entry entry = (History.Entry)var2.next();
            out.println(entry.value());
         }
      } finally {
         out.close();
      }

   }

   public void purge() throws IOException {
      Log.trace("Purging history");
      this.clear();
      if (!this.file.delete()) {
         Log.warn("Failed to delete history file: ", this.file);
      }

   }
}

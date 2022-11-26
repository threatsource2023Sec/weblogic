package jnr.ffi.provider.jffi;

import com.kenai.jffi.Library;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jnr.ffi.Platform;

public class NativeLibrary {
   private final List libraryNames;
   private final List searchPaths;
   private volatile List nativeLibraries = Collections.emptyList();
   private static final Pattern BAD_ELF = Pattern.compile("(.*): (invalid ELF header|file too short|invalid file format)");
   private static final Pattern ELF_GROUP = Pattern.compile("GROUP\\s*\\(\\s*(\\S*).*\\)");

   NativeLibrary(Collection libraryNames, Collection searchPaths) {
      this.libraryNames = Collections.unmodifiableList(new ArrayList(libraryNames));
      this.searchPaths = Collections.unmodifiableList(new ArrayList(searchPaths));
   }

   private String locateLibrary(String libraryName) {
      return (new File(libraryName)).isAbsolute() ? libraryName : Platform.getNativePlatform().locateLibrary(libraryName, this.searchPaths);
   }

   long getSymbolAddress(String name) {
      Iterator var2 = this.getNativeLibraries().iterator();

      long address;
      do {
         if (!var2.hasNext()) {
            return 0L;
         }

         Library l = (Library)var2.next();
         address = l.getSymbolAddress(name);
      } while(address == 0L);

      return address;
   }

   long findSymbolAddress(String name) {
      long address = this.getSymbolAddress(name);
      if (address == 0L) {
         throw new SymbolNotFoundError(Library.getLastError());
      } else {
         return address;
      }
   }

   private synchronized List getNativeLibraries() {
      return !this.nativeLibraries.isEmpty() ? this.nativeLibraries : (this.nativeLibraries = this.loadNativeLibraries());
   }

   private synchronized List loadNativeLibraries() {
      List libs = new ArrayList();
      Iterator var2 = this.libraryNames.iterator();

      while(var2.hasNext()) {
         String libraryName = (String)var2.next();
         Library lib = openLibrary(libraryName);
         String path;
         if (lib == null && libraryName != null && (path = this.locateLibrary(libraryName)) != null && !libraryName.equals(path)) {
            lib = openLibrary(path);
         }

         if (lib == null) {
            throw new UnsatisfiedLinkError(Library.getLastError());
         }

         libs.add(lib);
      }

      return Collections.unmodifiableList(libs);
   }

   private static Library openLibrary(String path) {
      Library lib = Library.getCachedInstance(path, 9);
      if (lib != null) {
         return lib;
      } else {
         Matcher badElf = BAD_ELF.matcher(Library.getLastError());
         if (badElf.lookingAt()) {
            File f = new File(badElf.group(1));
            if (f.isFile() && f.length() < 4096L) {
               Matcher sharedObject = ELF_GROUP.matcher(readAll(f));
               if (sharedObject.find()) {
                  return Library.getCachedInstance(sharedObject.group(1), 9);
               }
            }
         }

         return null;
      }
   }

   private static String readAll(File f) {
      BufferedReader br = null;

      try {
         br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
         StringBuilder sb = new StringBuilder();

         String line;
         while((line = br.readLine()) != null) {
            sb.append(line);
         }

         String var4 = sb.toString();
         return var4;
      } catch (FileNotFoundException var14) {
         throw new RuntimeException(var14);
      } catch (IOException var15) {
         throw new RuntimeException(var15);
      } finally {
         if (br != null) {
            try {
               br.close();
            } catch (IOException var13) {
               throw new RuntimeException(var13);
            }
         }

      }
   }
}

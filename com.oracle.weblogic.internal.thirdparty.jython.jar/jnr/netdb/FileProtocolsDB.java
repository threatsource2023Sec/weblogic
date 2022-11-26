package jnr.netdb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jnr.ffi.Platform;

class FileProtocolsDB implements ProtocolsDB {
   private final File protocolsFile;

   public static final FileProtocolsDB getInstance() {
      return FileProtocolsDB.SingletonHolder.INSTANCE;
   }

   FileProtocolsDB(File protocolsFile) {
      this.protocolsFile = protocolsFile;
   }

   private static final File locateProtocolsFile() {
      if (Platform.getNativePlatform().getOS().equals(Platform.OS.WINDOWS)) {
         String systemRoot;
         try {
            systemRoot = System.getProperty("SystemRoot", "C:\\windows");
         } catch (SecurityException var2) {
            systemRoot = "C:\\windows";
         }

         return new File(systemRoot + "\\system32\\drivers\\etc\\protocol");
      } else {
         return new File("/etc/protocols");
      }
   }

   private static FileProtocolsDB load() {
      try {
         File protocolsFile = locateProtocolsFile();
         NetDBParser parser = new NetDBParser(new FileReader(protocolsFile));

         try {
            parser.iterator().next();
         } finally {
            parser.close();
         }

         return new FileProtocolsDB(protocolsFile);
      } catch (Throwable var6) {
         return null;
      }
   }

   public Protocol getProtocolByName(final String name) {
      return this.parse(new Filter() {
         public boolean filter(Protocol p) {
            if (p.getName().equals(name)) {
               return true;
            } else {
               Iterator i$ = p.getAliases().iterator();

               String alias;
               do {
                  if (!i$.hasNext()) {
                     return false;
                  }

                  alias = (String)i$.next();
               } while(!alias.equals(name));

               return true;
            }
         }
      });
   }

   public Protocol getProtocolByNumber(final Integer proto) {
      return this.parse(new Filter() {
         public boolean filter(Protocol p) {
            return p.getProto() == proto;
         }
      });
   }

   public Collection getAllProtocols() {
      final List allProtocols = new LinkedList();
      this.parse(new Filter() {
         public boolean filter(Protocol s) {
            allProtocols.add(s);
            return false;
         }
      });
      return Collections.unmodifiableList(allProtocols);
   }

   private final NetDBParser loadProtocolsFile() {
      try {
         return new NetDBParser(new FileReader(this.protocolsFile));
      } catch (FileNotFoundException var2) {
         return new NetDBParser(new StringReader(""));
      }
   }

   private final Protocol parse(Filter filter) {
      NetDBParser parser = this.loadProtocolsFile();

      try {
         Iterator i$ = parser.iterator();

         while(i$.hasNext()) {
            NetDBEntry e = (NetDBEntry)i$.next();

            try {
               Protocol p = new Protocol(e.name, Integer.parseInt(e.data, 10), e.aliases);
               if (filter.filter(p)) {
                  Protocol var6 = p;
                  return var6;
               }
            } catch (NumberFormatException var16) {
            }
         }

         return null;
      } finally {
         try {
            parser.close();
         } catch (IOException var15) {
            throw new RuntimeException(var15);
         }
      }
   }

   private interface Filter {
      boolean filter(Protocol var1);
   }

   private static final class SingletonHolder {
      public static final FileProtocolsDB INSTANCE = FileProtocolsDB.load();
   }
}

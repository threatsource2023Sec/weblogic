package com.octetstring.vde.backend;

import com.octetstring.ldapv3.Filter;
import com.octetstring.nls.Messages;
import com.octetstring.vde.Entry;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public class Mapper {
   Hashtable maps = null;
   private File mapFilename = new File("conf/mapping.cfg");
   private Hashtable reverseDNMap = null;

   public Mapper() {
      this.maps = new Hashtable();
      this.reverseDNMap = new Hashtable();
      BufferedReader br = null;
      String filename = null;
      String ihome = System.getProperty("vde.home");
      if (!this.mapFilename.isAbsolute() && ihome != null) {
         filename = ihome + "/" + this.mapFilename.toString();
      } else {
         filename = this.mapFilename.toString();
      }

      try {
         br = new BufferedReader(new FileReader(filename));
      } catch (FileNotFoundException var12) {
         Logger.getInstance().log(0, this, Messages.getString("Error_opening_mapping_file___4") + filename);
         return;
      }

      boolean hasMore = true;
      DirectoryString currentDN = null;
      int linecount = 0;

      while(hasMore) {
         try {
            String line = br.readLine();
            ++linecount;
            if (line != null) {
               if (line.length() > 0 && line.charAt(0) != '#') {
                  if (line.startsWith("dn:")) {
                     StringTokenizer st = new StringTokenizer(line, ":");
                     if (st.hasMoreTokens()) {
                        String junk = st.nextToken();
                        if (st.hasMoreTokens()) {
                           currentDN = new DirectoryString(st.nextToken().trim());
                        } else {
                           Logger.getInstance().log(0, this, "No DN Specified at line: " + linecount);
                        }
                     } else {
                        Logger.getInstance().log(0, this, "No DN Specified at line: " + linecount);
                     }
                  } else {
                     try {
                        Mapping oneMapping = new Mapping(line);
                        Vector cmaps = (Vector)this.maps.get(currentDN);
                        if (cmaps == null) {
                           cmaps = new Vector();
                           cmaps.addElement(oneMapping);
                           this.maps.put(currentDN, cmaps);
                        } else {
                           cmaps.addElement(oneMapping);
                        }
                     } catch (MappingException var10) {
                        Logger.getInstance().log(0, this, "Error at line#" + linecount + ": " + var10.getMessage());
                     }
                  }
               }
            } else {
               hasMore = false;
            }
         } catch (IOException var11) {
            Logger.getInstance().log(0, this, Messages.getString("IO_error_reading_mapping_file._7"));
            hasMore = false;
         }
      }

   }

   public Entry map(Entry original) {
      if (original != null && original.getName() != null) {
         Enumeration maplocs = this.maps.keys();
         Entry newentry = null;

         while(true) {
            DirectoryString loc;
            do {
               if (!maplocs.hasMoreElements()) {
                  if (newentry != null) {
                     if (!newentry.getName().equals(original.getName())) {
                        this.reverseDNMap.put(newentry.getName(), original.getName());
                     }

                     return newentry;
                  }

                  return original;
               }

               loc = (DirectoryString)maplocs.nextElement();
            } while(!original.getName().endsWith(loc));

            if (newentry == null) {
               newentry = (Entry)original.clone();
            }

            Vector cmaps = (Vector)this.maps.get(loc);

            Mapping oneMapping;
            for(Enumeration mapenum = cmaps.elements(); mapenum.hasMoreElements(); newentry = oneMapping.transform(newentry)) {
               oneMapping = (Mapping)mapenum.nextElement();
            }
         }
      } else {
         return original;
      }
   }

   public Filter mapfilter(DirectoryString base, Filter original) {
      Enumeration maplocs = this.maps.keys();

      while(true) {
         DirectoryString loc;
         do {
            if (!maplocs.hasMoreElements()) {
               return original;
            }

            loc = (DirectoryString)maplocs.nextElement();
         } while(!base.endsWith(loc));

         Vector cmaps = (Vector)this.maps.get(loc);

         Mapping oneMapping;
         for(Enumeration mapenum = cmaps.elements(); mapenum.hasMoreElements(); original = oneMapping.updateFilter(original)) {
            oneMapping = (Mapping)mapenum.nextElement();
         }
      }
   }

   public DirectoryString mapbase(DirectoryString base) {
      DirectoryString origdn = (DirectoryString)this.reverseDNMap.get(base);
      return origdn == null ? base : origdn;
   }
}

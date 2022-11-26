package org.python.icu.impl.duration.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import org.python.icu.impl.ICUData;
import org.python.icu.util.ICUUncheckedIOException;

public class ResourceBasedPeriodFormatterDataService extends PeriodFormatterDataService {
   private Collection availableLocales;
   private PeriodFormatterData lastData = null;
   private String lastLocale = null;
   private Map cache = new HashMap();
   private static final String PATH = "data/";
   private static final ResourceBasedPeriodFormatterDataService singleton = new ResourceBasedPeriodFormatterDataService();

   public static ResourceBasedPeriodFormatterDataService getInstance() {
      return singleton;
   }

   private ResourceBasedPeriodFormatterDataService() {
      List localeNames = new ArrayList();
      InputStream is = ICUData.getRequiredStream(this.getClass(), "data/index.txt");

      try {
         BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
         String string = null;

         while(true) {
            if (null == (string = br.readLine())) {
               br.close();
               break;
            }

            string = string.trim();
            if (!string.startsWith("#") && string.length() != 0) {
               localeNames.add(string);
            }
         }
      } catch (IOException var12) {
         throw new IllegalStateException("IO Error reading data/index.txt: " + var12.toString());
      } finally {
         try {
            is.close();
         } catch (IOException var11) {
         }

      }

      this.availableLocales = Collections.unmodifiableList(localeNames);
   }

   public PeriodFormatterData get(String localeName) {
      int x = localeName.indexOf(64);
      if (x != -1) {
         localeName = localeName.substring(0, x);
      }

      synchronized(this) {
         if (this.lastLocale != null && this.lastLocale.equals(localeName)) {
            return this.lastData;
         } else {
            PeriodFormatterData ld = (PeriodFormatterData)this.cache.get(localeName);
            if (ld == null) {
               String ln = localeName;

               while(!this.availableLocales.contains(ln)) {
                  int ix = ln.lastIndexOf("_");
                  if (ix > -1) {
                     ln = ln.substring(0, ix);
                  } else {
                     if ("test".equals(ln)) {
                        ln = null;
                        break;
                     }

                     ln = "test";
                  }
               }

               if (ln == null) {
                  throw new MissingResourceException("Duration data not found for  " + localeName, "data/", localeName);
               }

               String name = "data/pfd_" + ln + ".xml";

               try {
                  InputStreamReader reader = new InputStreamReader(ICUData.getRequiredStream(this.getClass(), name), "UTF-8");
                  DataRecord dr = DataRecord.read(ln, new XMLRecordReader(reader));
                  reader.close();
                  if (dr != null) {
                     ld = new PeriodFormatterData(localeName, dr);
                  }
               } catch (UnsupportedEncodingException var10) {
                  throw new MissingResourceException("Unhandled encoding for resource " + name, name, "");
               } catch (IOException var11) {
                  throw new ICUUncheckedIOException("Failed to close() resource " + name, var11);
               }

               this.cache.put(localeName, ld);
            }

            this.lastData = ld;
            this.lastLocale = localeName;
            return ld;
         }
      }
   }

   public Collection getAvailableLocales() {
      return this.availableLocales;
   }
}

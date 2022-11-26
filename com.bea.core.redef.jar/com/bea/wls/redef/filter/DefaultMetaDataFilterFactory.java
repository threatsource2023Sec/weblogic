package com.bea.wls.redef.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import serp.bytecode.lowlevel.ConstantPoolTable;
import weblogic.diagnostics.debug.DebugLogger;

public class DefaultMetaDataFilterFactory implements MetaDataFilterFactory {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugClassRedef");
   private static final String FASTSWAP_PROPERTIES = "fastswap.properties";
   private static final String[] DEFAULT_EXCLUDE_CLASSES = new String[]{"*WLSkel*", "*WLStub*"};
   private static final String[] DEFAULT_EXCLUDE_EXTENDS = new String[]{"java.rmi.Remote", "weblogic.ejb.container.interfaces.WLEnterpriseBean"};
   private static final Set DEFAULT_EXCLUDE_CLASSES_SET;
   private static final Set DEFAULT_EXCLUDE_EXTENDS_SET;
   private boolean excludeAllInterfaces;
   private Set inclusionPatterns;
   private Set exclusionPatterns;
   private Set excludeExtends;

   public DefaultMetaDataFilterFactory() {
      this(false, (Set)null, DEFAULT_EXCLUDE_CLASSES_SET, DEFAULT_EXCLUDE_EXTENDS_SET);
   }

   public DefaultMetaDataFilterFactory(boolean excludeAllInterfaces, Set inclusionPats, Set exclusionPats, Set excludeExtendPats) {
      this.excludeAllInterfaces = excludeAllInterfaces;
      this.inclusionPatterns = makePatterns(inclusionPats);
      this.exclusionPatterns = makePatterns(exclusionPats);
      this.excludeExtends = makePatterns(excludeExtendPats);
      FileInputStream in = null;

      try {
         String fastSwapProps = System.getProperty("fastswap.properties");
         if (fastSwapProps != null) {
            File propFile = new File(fastSwapProps);
            if (propFile.exists()) {
               in = new FileInputStream(propFile);
               Properties props = new Properties();
               props.load(in);
               String val = props.getProperty("excludeAllInterfaces");
               if (val != null) {
                  this.excludeAllInterfaces = "true".equals(val);
               }

               this.inclusionPatterns = addPatterns(this.inclusionPatterns, props.getProperty("includeClasses"));
               this.exclusionPatterns = addPatterns(this.exclusionPatterns, props.getProperty("excludeClasses"));
               this.excludeExtends = addPatterns(this.excludeExtends, props.getProperty("excludeExtends"));
            }
         }
      } catch (IOException var18) {
         var18.printStackTrace();
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (Exception var17) {
            }
         }

      }

   }

   public MetaDataFilter newFilter(String clsName, Class prevCls, MetaDataFilter prev, ConstantPoolTable table, byte[] bytes) {
      int idx = table.getEndIndex();
      int access = table.readUnsignedShort(idx);
      idx += 2;
      if (this.excludeAllInterfaces && (access & 512) == 512) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Interface " + clsName + " excluded from enhancement");
         }

         return null;
      } else {
         Iterator var8;
         Pattern pat;
         if (this.exclusionPatterns != null) {
            var8 = this.exclusionPatterns.iterator();

            while(var8.hasNext()) {
               pat = (Pattern)var8.next();
               if (pat.matcher(clsName).matches()) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("Class " + clsName + " excluded from enhancement, matches " + pat);
                  }

                  return null;
               }
            }
         }

         if (this.excludeExtends != null) {
            idx += 2;
            String sup = getClassEntry(table, idx);
            idx += 2;
            int count = table.readUnsignedShort(idx);
            idx += 2;
            String[] interfaces = new String[count];

            for(int i = 0; i < count; ++i) {
               interfaces[i] = getClassEntry(table, idx + 2 * i);
            }

            Iterator var19 = this.excludeExtends.iterator();

            while(var19.hasNext()) {
               Pattern pat = (Pattern)var19.next();
               if (sup != null && pat.matcher(sup).matches()) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("Class " + clsName + " excluded from enhancement, its super matches " + pat);
                  }

                  return null;
               }

               String[] var13 = interfaces;
               int var14 = interfaces.length;

               for(int var15 = 0; var15 < var14; ++var15) {
                  String iface = var13[var15];
                  if (pat.matcher(iface).matches()) {
                     if (DEBUG.isDebugEnabled()) {
                        DEBUG.debug("Class " + clsName + " excluded from enhancement, implements/extends matches " + pat);
                     }

                     return null;
                  }
               }
            }
         }

         if (this.inclusionPatterns != null) {
            var8 = this.inclusionPatterns.iterator();

            do {
               if (!var8.hasNext()) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("Class " + clsName + " excluded from enhancement, does not match inclusion patterns");
                  }

                  return null;
               }

               pat = (Pattern)var8.next();
            } while(!pat.matcher(clsName).matches());

            return (MetaDataFilter)(prev != null ? prev : NullMetaDataFilter.NULL_FILTER);
         } else {
            return (MetaDataFilter)(prev != null ? prev : NullMetaDataFilter.NULL_FILTER);
         }
      }
   }

   private static String getClassEntry(ConstantPoolTable table, int idx) {
      int clsEntry = table.readUnsignedShort(idx);
      int utfEntry = table.readUnsignedShort(table.get(clsEntry));
      return table.readString(table.get(utfEntry)).replace('/', '.');
   }

   private static Set addPatterns(Set patSet, String pats) {
      Set newPats = makePatterns(pats);
      if (newPats == null) {
         return patSet;
      } else {
         if (patSet != null) {
            newPats.addAll(patSet);
         }

         return newPats;
      }
   }

   private static Set makePatterns(String patterns) {
      if (patterns == null) {
         return null;
      } else {
         Set set = arr2set(patterns.trim().split(","));
         return makePatterns(set);
      }
   }

   private static Set arr2set(String[] arr) {
      HashSet set = new HashSet();
      set.addAll(Arrays.asList(arr));
      return set;
   }

   private static Set makePatterns(Set patterns) {
      if (patterns != null && patterns.size() != 0) {
         Set patSet = null;
         Iterator var2 = patterns.iterator();

         while(var2.hasNext()) {
            String pat = (String)var2.next();
            pat = pat.trim();
            if (pat.length() != 0) {
               pat = pat.replaceAll("\\.", "\\\\\\.");
               pat = pat.replaceAll("\\$", "\\\\\\$");
               pat = pat.replaceAll("\\*", "(.*)");
               pat = "^" + pat + "$";

               try {
                  Pattern p = Pattern.compile(pat);
                  if (patSet == null) {
                     patSet = new HashSet();
                  }

                  patSet.add(p);
               } catch (Exception var5) {
                  var5.printStackTrace();
               }
            }
         }

         return patSet;
      } else {
         return null;
      }
   }

   static {
      DEFAULT_EXCLUDE_CLASSES_SET = arr2set(DEFAULT_EXCLUDE_CLASSES);
      DEFAULT_EXCLUDE_EXTENDS_SET = arr2set(DEFAULT_EXCLUDE_EXTENDS);
   }
}

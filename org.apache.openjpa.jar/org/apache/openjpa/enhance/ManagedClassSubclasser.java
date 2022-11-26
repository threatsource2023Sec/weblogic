package org.apache.openjpa.enhance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.BytecodeWriter;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.JavaVersions;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.GeneratedClasses;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UserException;
import serp.bytecode.BCClass;

public class ManagedClassSubclasser {
   private static final Localizer _loc = Localizer.forPackage(ManagedClassSubclasser.class);

   public static List prepareUnenhancedClasses(OpenJPAConfiguration conf, Collection classes, ClassLoader envLoader) {
      if (classes == null) {
         return null;
      } else if (classes.size() == 0) {
         return Collections.EMPTY_LIST;
      } else {
         Log log = conf.getLog("openjpa.Enhance");
         if (conf.getRuntimeUnenhancedClassesConstant() != 0) {
            Collection unenhanced = new ArrayList();
            Iterator i$ = classes.iterator();

            while(i$.hasNext()) {
               Class cls = (Class)i$.next();
               if (!PersistenceCapable.class.isAssignableFrom(cls)) {
                  unenhanced.add(cls);
               }
            }

            if (unenhanced.size() > 0) {
               Localizer.Message msg = _loc.get("runtime-optimization-disabled", (Object)unenhanced);
               if (conf.getRuntimeUnenhancedClassesConstant() != 2) {
                  throw new UserException(msg);
               }

               log.warn(msg);
            }

            return null;
         } else {
            boolean redefine = ClassRedefiner.canRedefineClasses();
            if (redefine) {
               log.info(_loc.get("enhance-and-subclass-and-redef-start", (Object)classes));
            } else {
               log.info(_loc.get("enhance-and-subclass-no-redef-start", (Object)classes));
            }

            final Map map = new HashMap();
            final List subs = new ArrayList(classes.size());
            final List ints = new ArrayList(classes.size());
            Set unspecified = null;
            Iterator i$ = classes.iterator();

            final Class cls;
            while(i$.hasNext()) {
               cls = (Class)i$.next();
               final PCEnhancer enhancer = new PCEnhancer(conf, cls);
               enhancer.setBytecodeWriter(new BytecodeWriter() {
                  public void write(BCClass bc) throws IOException {
                     ManagedClassSubclasser.write(bc, enhancer, map, cls, subs, ints);
                  }
               });
               if (redefine) {
                  enhancer.setRedefine(true);
               }

               enhancer.setCreateSubclass(true);
               enhancer.setAddDefaultConstructor(true);
               configureMetaData(enhancer.getMetaData(), conf, redefine, false);
               unspecified = collectRelatedUnspecifiedTypes(enhancer.getMetaData(), classes, unspecified);
               int runResult = enhancer.run();
               if (runResult == 8) {
                  try {
                     enhancer.record();
                  } catch (IOException var14) {
                     throw new InternalException(var14);
                  }
               }
            }

            if (unspecified != null && !unspecified.isEmpty()) {
               throw new UserException(_loc.get("unspecified-unenhanced-types", classes, unspecified));
            } else {
               ClassRedefiner.redefineClasses(conf, map);
               i$ = map.keySet().iterator();

               while(i$.hasNext()) {
                  cls = (Class)i$.next();
                  setIntercepting(conf, envLoader, cls);
                  configureMetaData(conf, envLoader, cls, redefine);
               }

               i$ = subs.iterator();

               while(i$.hasNext()) {
                  cls = (Class)i$.next();
                  configureMetaData(conf, envLoader, cls, redefine);
               }

               i$ = ints.iterator();

               while(i$.hasNext()) {
                  cls = (Class)i$.next();
                  setIntercepting(conf, envLoader, cls);
               }

               return subs;
            }
         }
      }
   }

   private static Set collectRelatedUnspecifiedTypes(ClassMetaData meta, Collection classes, Set unspecified) {
      unspecified = collectUnspecifiedType(meta.getPCSuperclass(), classes, unspecified);
      FieldMetaData[] arr$ = meta.getFields();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         FieldMetaData fmd = arr$[i$];
         if (!fmd.isTransient()) {
            if (fmd.isTypePC()) {
               unspecified = collectUnspecifiedType(fmd.getType(), classes, unspecified);
            }

            if (fmd.getElement() != null && fmd.getElement().isTypePC()) {
               unspecified = collectUnspecifiedType(fmd.getElement().getType(), classes, unspecified);
            }

            if (fmd.getKey() != null && fmd.getKey().isTypePC()) {
               unspecified = collectUnspecifiedType(fmd.getKey().getType(), classes, unspecified);
            }

            if (fmd.getValue() != null && fmd.getValue().isTypePC()) {
               unspecified = collectUnspecifiedType(fmd.getValue().getType(), classes, unspecified);
            }
         }
      }

      return unspecified;
   }

   private static Set collectUnspecifiedType(Class cls, Collection classes, Set unspecified) {
      if (cls != null && !classes.contains(cls) && !ImplHelper.isManagedType((OpenJPAConfiguration)null, cls)) {
         if (unspecified == null) {
            unspecified = new HashSet();
         }

         ((Set)unspecified).add(cls);
      }

      return (Set)unspecified;
   }

   private static void configureMetaData(OpenJPAConfiguration conf, ClassLoader envLoader, Class cls, boolean redefineAvailable) {
      ClassMetaData meta = conf.getMetaDataRepositoryInstance().getMetaData(cls, envLoader, true);
      configureMetaData(meta, conf, redefineAvailable, true);
   }

   private static void configureMetaData(ClassMetaData meta, OpenJPAConfiguration conf, boolean redefineAvailable, boolean warn) {
      setDetachedState(meta);
      if (warn && meta.getAccessType() == 2 && !redefineAvailable) {
         FieldMetaData[] arr$ = meta.getDeclaredFields();
         int len$ = arr$.length;
         int i$ = 0;

         while(i$ < len$) {
            FieldMetaData fmd = arr$[i$];
            switch (fmd.getTypeCode()) {
               default:
                  if (!fmd.isInDefaultFetchGroup() && !fmd.isVersion() && !fmd.isPrimaryKey()) {
                     Log log = conf.getLog("openjpa.Enhance");
                     log.warn(_loc.get("subclasser-fetch-group-override", meta.getDescribedType().getName(), fmd.getName()));
                     fmd.setInDefaultFetchGroup(true);
                  }
               case 12:
               case 13:
                  ++i$;
            }
         }
      }

   }

   private static void write(BCClass bc, PCEnhancer enhancer, Map map, Class cls, List subs, List ints) throws IOException {
      if (bc == enhancer.getManagedTypeBytecode()) {
         if (enhancer.isAlreadyRedefined()) {
            ints.add(bc.getType());
         } else if (JavaVersions.VERSION >= 5) {
            map.put(bc.getType(), bc.toByteArray());
            debugBytecodes(bc);
         }
      } else if (!enhancer.isAlreadySubclassed()) {
         debugBytecodes(bc);
         ClassLoader loader = GeneratedClasses.getMostDerivedLoader(cls, PersistenceCapable.class);
         subs.add(GeneratedClasses.loadBCClass(bc, loader));
      }

   }

   private static void debugBytecodes(BCClass bc) throws IOException {
      if ("true".equals(System.getProperty(ManagedClassSubclasser.class.getName() + ".dumpBytecodes"))) {
         File tmp = new File(System.getProperty("java.io.tmpdir"));
         File dir = new File(tmp, "openjpa");
         dir = new File(dir, "pcsubclasses");
         dir.mkdirs();
         dir = Files.getPackageFile(dir, bc.getPackageName(), true);
         File f = new File(dir, bc.getClassName() + ".class");
         System.err.println("Writing to " + f);
         bc.write(f);
      }

   }

   private static void setIntercepting(OpenJPAConfiguration conf, ClassLoader envLoader, Class cls) {
      ClassMetaData meta = conf.getMetaDataRepositoryInstance().getMetaData(cls, envLoader, true);
      meta.setIntercepting(true);
   }

   private static void setDetachedState(ClassMetaData meta) {
      if ("`syn".equals(meta.getDetachedState())) {
         meta.setDetachedState((String)null);
      }

   }
}

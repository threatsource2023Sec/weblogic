package com.bea.util.annogen.view.internal;

import com.bea.util.annogen.generate.internal.PropfileUtils;
import com.bea.util.annogen.override.AnnoBean;
import com.bea.util.annogen.override.AnnoContext;
import com.bea.util.annogen.override.AnnoOverrider;
import com.bea.util.annogen.override.internal.AnnoBeanBase;
import com.bea.util.annogen.view.AnnoViewerParams;
import com.bea.util.jam.internal.JamLoggerImpl;
import com.bea.util.jam.provider.JamLogger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AnnoViewerParamsImpl implements AnnoViewerParams, AnnoContext {
   private LinkedList mPopulators = new LinkedList();
   private JamLogger mLogger = new JamLoggerImpl();
   private ClassLoader mClassLoader = ClassLoader.getSystemClassLoader();
   private Map mMappingCache = new HashMap();

   public void addXmlOverrides(File file) throws FileNotFoundException {
      this.addXmlOverrides((Reader)(new FileReader(file)));
   }

   public void addXmlOverrides(Reader in) {
      throw new IllegalArgumentException("NYI");
   }

   public void addOverrider(AnnoOverrider over) {
      if (over == null) {
         throw new IllegalArgumentException("null overrider");
      } else {
         this.mPopulators.addFirst(over);
      }
   }

   public void setClassLoader(ClassLoader c) {
      this.mClassLoader = c;
   }

   public void setLogger(JamLogger logger) {
      this.mLogger = logger;
   }

   public void setVerbose(Class c) {
      this.mLogger.setVerbose(c);
   }

   public void appendAnnoOverrider(AnnoOverrider over) {
      if (over == null) {
         throw new IllegalArgumentException("null overrider");
      } else {
         this.mPopulators.addLast(over);
      }
   }

   public AnnoOverrider[] getOverriders() {
      AnnoOverrider[] out = new AnnoOverrider[this.mPopulators.size()];
      this.mPopulators.toArray(out);
      return out;
   }

   public JamLogger getLogger() {
      return this.mLogger;
   }

   public Class getAnnobeanClassFor(Class clazz) throws ClassNotFoundException {
      Class out = (Class)this.mMappingCache.get(clazz);
      if (out == null) {
         this.mMappingCache.put(clazz, out = this.getAnnobeanClassForNocache(clazz));
      }

      return out;
   }

   public Class getJsr175ClassForAnnobeanClass(Class beanClass) throws ClassNotFoundException {
      if (!AnnoBean.class.isAssignableFrom(beanClass)) {
         throw new IllegalArgumentException(beanClass.getName() + " is not a AnnoBean");
      } else {
         Field f;
         try {
            f = beanClass.getField("ANNOBEAN_FOR");
         } catch (NoSuchFieldException var8) {
            throw new IllegalArgumentException(beanClass.getName() + " is an AnnoBean but does not have a " + "ANNOBEAN_FOR" + " field");
         }

         try {
            String declaredTypeName = (String)f.get((Object)null);
            return beanClass.getClass().getClassLoader().loadClass(declaredTypeName);
         } catch (IllegalAccessException var7) {
            IllegalAccessException e = var7;

            try {
               throw (new ClassNotFoundException()).initCause(e);
            } catch (Throwable var6) {
               var6.printStackTrace();
               throw new IllegalStateException();
            }
         }
      }
   }

   public ClassLoader getClassLoader() {
      return this.mClassLoader;
   }

   public AnnoBean createAnnoBeanFor(Class beanClass) {
      try {
         AnnoBeanBase out = (AnnoBeanBase)beanClass.newInstance();
         out.init(this);
         return out;
      } catch (InstantiationException var3) {
         this.mLogger.error((Throwable)var3);
      } catch (IllegalAccessException var4) {
         this.mLogger.error((Throwable)var4);
      }

      return null;
   }

   private Class getAnnobeanClassForNocache(Class requestedClass) throws ClassNotFoundException {
      if (AnnoBean.class.isAssignableFrom(requestedClass)) {
         return requestedClass;
      } else {
         try {
            return PropfileUtils.getInstance().getAnnobeanTypeFor(requestedClass, this.mClassLoader);
         } catch (IOException var3) {
            throw new ClassNotFoundException("IO Error looking up bean class for " + requestedClass.getName(), var3);
         }
      }
   }
}

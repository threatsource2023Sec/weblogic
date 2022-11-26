package org.apache.xmlbeans.impl.jam.provider;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import org.apache.xmlbeans.impl.jam.JClass;
import org.apache.xmlbeans.impl.jam.JamClassLoader;
import org.apache.xmlbeans.impl.jam.JamService;
import org.apache.xmlbeans.impl.jam.JamServiceFactory;
import org.apache.xmlbeans.impl.jam.JamServiceParams;
import org.apache.xmlbeans.impl.jam.internal.JamClassLoaderImpl;
import org.apache.xmlbeans.impl.jam.internal.JamServiceContextImpl;
import org.apache.xmlbeans.impl.jam.internal.JamServiceImpl;
import org.apache.xmlbeans.impl.jam.internal.elements.ElementContext;
import org.apache.xmlbeans.impl.jam.internal.javadoc.JavadocClassBuilder;
import org.apache.xmlbeans.impl.jam.internal.parser.ParserClassBuilder;
import org.apache.xmlbeans.impl.jam.internal.reflect.ReflectClassBuilder;
import org.apache.xmlbeans.impl.jam.visitor.MVisitor;

public class JamServiceFactoryImpl extends JamServiceFactory {
   public static final String USE_NEW_PARSER = "JamServiceFactoryImpl.use-new-parser";
   private static final String PREFIX = "[JamServiceFactoryImpl]";

   public JamServiceParams createServiceParams() {
      return new JamServiceContextImpl();
   }

   public JamService createService(JamServiceParams jsps) throws IOException {
      if (!(jsps instanceof JamServiceContextImpl)) {
         throw new IllegalArgumentException("JamServiceParams must be instantiated by this JamServiceFactory.");
      } else {
         JamClassLoader clToUse = this.createClassLoader((JamServiceContextImpl)jsps);
         ((JamServiceContextImpl)jsps).setClassLoader(clToUse);
         return new JamServiceImpl((ElementContext)jsps, this.getSpecifiedClasses((JamServiceContextImpl)jsps));
      }
   }

   public JamClassLoader createSystemJamClassLoader() {
      JamServiceParams params = this.createServiceParams();
      params.setUseSystemClasspath(true);

      try {
         JamService service = this.createService(params);
         return service.getClassLoader();
      } catch (IOException var3) {
         var3.printStackTrace();
         throw new IllegalStateException(var3.getMessage());
      }
   }

   public JamClassLoader createJamClassLoader(ClassLoader cl) {
      JamServiceParams params = this.createServiceParams();
      params.setUseSystemClasspath(false);
      params.setPropertyInitializer((MVisitor)null);
      params.addClassLoader(cl);

      try {
         JamService service = this.createService(params);
         return service.getClassLoader();
      } catch (IOException var4) {
         var4.printStackTrace();
         throw new IllegalStateException(var4.getMessage());
      }
   }

   protected String[] getSpecifiedClasses(JamServiceContext params) throws IOException {
      return params.getAllClassnames();
   }

   protected JamClassLoader createClassLoader(JamServiceContext ctx) throws IOException {
      JamClassBuilder builder = this.createBuilder(ctx);
      return new JamClassLoaderImpl((ElementContext)ctx, builder, ctx.getInitializer());
   }

   protected JamClassBuilder createBuilder(JamServiceContext ctx) throws IOException {
      JamLogger log = ctx.getLogger();
      List builders = new ArrayList();
      JamClassBuilder b = ctx.getBaseBuilder();
      if (b != null) {
         builders.add(b);
      }

      b = this.createSourceBuilder(ctx);
      if (log.isVerbose((Object)this)) {
         log.verbose("added classbuilder for sources");
      }

      if (b != null) {
         builders.add(b);
      }

      b = this.createClassfileBuilder(ctx);
      if (log.isVerbose((Object)this)) {
         log.verbose("added classbuilder for custom classpath");
      }

      if (b != null) {
         builders.add(b);
      }

      ClassLoader[] cls = ctx.getReflectionClassLoaders();

      for(int i = 0; i < cls.length; ++i) {
         if (log.isVerbose((Object)this)) {
            log.verbose("added classbuilder for classloader " + cls[i].getClass());
         }

         builders.add(new ReflectClassBuilder(cls[i]));
      }

      JamClassBuilder[] barray = new JamClassBuilder[builders.size()];
      builders.toArray(barray);
      JamClassBuilder out = new CompositeJamClassBuilder(barray);
      out.init((ElementContext)ctx);
      if (log.isVerbose((Object)this)) {
         log.verbose("returning a composite of " + barray.length + " class builders.");
         JClass c = out.build("java.lang", "Object");
         c = out.build("javax.ejb", "SessionBean");
      }

      return out;
   }

   protected JamClassBuilder createSourceBuilder(JamServiceContext ctx) throws IOException {
      File[] sources = ctx.getSourceFiles();
      if (sources != null && sources.length != 0) {
         return (JamClassBuilder)(ctx.getProperty("JamServiceFactoryImpl.use-new-parser") == null ? new JavadocClassBuilder() : new ParserClassBuilder(ctx));
      } else {
         if (ctx.isVerbose(this)) {
            ctx.verbose("[JamServiceFactoryImpl]no source files present, skipping source ClassBuilder");
         }

         return null;
      }
   }

   protected JamClassBuilder createClassfileBuilder(JamServiceContext jp) throws IOException {
      ResourcePath cp = jp.getInputClasspath();
      if (cp == null) {
         return null;
      } else {
         URL[] urls = cp.toUrlPath();
         ClassLoader cl = new URLClassLoader(urls);
         return new ReflectClassBuilder(cl);
      }
   }
}

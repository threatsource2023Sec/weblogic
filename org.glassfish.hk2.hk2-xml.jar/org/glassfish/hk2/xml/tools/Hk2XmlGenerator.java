package org.glassfish.hk2.xml.tools;

import java.io.DataOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.Set;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import javax.tools.Diagnostic.Kind;
import org.glassfish.hk2.xml.internal.Generator;
import org.glassfish.hk2.xml.internal.alt.papi.TypeElementAltClassImpl;

@SupportedAnnotationTypes({"org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate"})
public class Hk2XmlGenerator extends AbstractProcessor {
   private volatile boolean initialized;
   private ClassPool defaultClassPool;
   private CtClass superClazz;

   public SourceVersion getSupportedSourceVersion() {
      return SourceVersion.latest();
   }

   private void initializeHk2XmlGenerator() {
      if (!this.initialized) {
         synchronized(this) {
            if (!this.initialized) {
               this.defaultClassPool = new ClassPool(true);
               ClassLoader localLoader = this.getClass().getClassLoader();
               if (!(localLoader instanceof URLClassLoader)) {
                  throw new RuntimeException("Unknown classloader: " + localLoader);
               } else {
                  URLClassLoader urlLoader = (URLClassLoader)localLoader;
                  URL[] var4 = urlLoader.getURLs();
                  int var5 = var4.length;

                  for(int var6 = 0; var6 < var5; ++var6) {
                     URL url = var4[var6];

                     URI uri;
                     try {
                        uri = url.toURI();
                     } catch (URISyntaxException var14) {
                        throw new RuntimeException(var14);
                     }

                     File asFile = new File(uri);
                     if (asFile.exists() && asFile.canRead()) {
                        try {
                           this.defaultClassPool.appendClassPath(asFile.getAbsolutePath());
                        } catch (NotFoundException var13) {
                           throw new RuntimeException(var13);
                        }
                     }
                  }

                  try {
                     this.superClazz = this.defaultClassPool.get("org.glassfish.hk2.xml.jaxb.internal.BaseHK2JAXBBean");
                  } catch (NotFoundException var12) {
                     throw new RuntimeException(var12);
                  }

               }
            }
         }
      }
   }

   public boolean process(Set annotations, RoundEnvironment roundEnv) {
      Filer filer = this.processingEnv.getFiler();
      Iterator var4 = annotations.iterator();

      label104:
      while(var4.hasNext()) {
         TypeElement annotation = (TypeElement)var4.next();
         Set clazzes = roundEnv.getElementsAnnotatedWith(annotation);
         Iterator var7 = clazzes.iterator();

         while(true) {
            Element clazzElement;
            do {
               if (!var7.hasNext()) {
                  continue label104;
               }

               clazzElement = (Element)var7.next();
            } while(!(clazzElement instanceof TypeElement));

            this.initializeHk2XmlGenerator();
            TypeElement clazz = (TypeElement)clazzElement;
            TypeElementAltClassImpl altClass = new TypeElementAltClassImpl(clazz, this.processingEnv);

            String msg;
            try {
               CtClass ctClass = Generator.generate(altClass, this.superClazz, this.defaultClassPool);
               msg = ctClass.getName();
               JavaFileObject jfo = filer.createClassFile(msg, new Element[]{clazzElement});
               OutputStream outputStream = jfo.openOutputStream();
               DataOutputStream dataOutputStream = null;

               try {
                  dataOutputStream = new DataOutputStream(outputStream);
                  ctClass.toBytecode(dataOutputStream);
               } finally {
                  if (dataOutputStream != null) {
                     dataOutputStream.close();
                  }

                  outputStream.close();
               }
            } catch (Throwable var20) {
               msg = var20.getMessage();
               if (msg == null) {
                  msg = "Exception of type " + var20.getClass().getName();
               }

               this.processingEnv.getMessager().printMessage(Kind.ERROR, "While processing class: " + clazz.getQualifiedName() + " got exeption: " + msg);
               var20.printStackTrace();
            }
         }
      }

      return true;
   }
}

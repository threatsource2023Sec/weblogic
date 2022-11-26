package com.bea.util.jam.internal;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JPackage;
import com.bea.util.jam.JamClassLoader;
import com.bea.util.jam.internal.elements.ArrayClassImpl;
import com.bea.util.jam.internal.elements.ClassImpl;
import com.bea.util.jam.internal.elements.ElementContext;
import com.bea.util.jam.internal.elements.PackageImpl;
import com.bea.util.jam.internal.elements.PrimitiveClassImpl;
import com.bea.util.jam.internal.elements.UnresolvedClassImpl;
import com.bea.util.jam.internal.elements.VoidClassImpl;
import com.bea.util.jam.mutable.MClass;
import com.bea.util.jam.provider.JamClassBuilder;
import com.bea.util.jam.visitor.MVisitor;
import com.bea.util.jam.visitor.TraversingMVisitor;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.apache.tools.ant.taskdefs.Javac;

public class JamClassLoaderImpl implements JamClassLoader {
   private Map mName2Package = new HashMap();
   private Map mFd2ClassCache = new HashMap();
   private JamClassBuilder mBuilder;
   private MVisitor mInitializer = null;
   private ElementContext mContext;
   private Stack mInitializeStack = new Stack();
   private boolean mAlreadyInitializing = false;

   public JamClassLoaderImpl(ElementContext context, JamClassBuilder builder, MVisitor initializerOrNull) {
      if (builder == null) {
         throw new IllegalArgumentException("null builder");
      } else if (context == null) {
         throw new IllegalArgumentException("null builder");
      } else {
         this.mBuilder = builder;
         this.mInitializer = initializerOrNull == null ? null : new TraversingMVisitor(initializerOrNull);
         this.mContext = context;
         this.initCache();
      }
   }

   public final JClass loadClass(String fd) {
      return this.loadClass((Javac)null, fd);
   }

   public final JClass loadClass(Javac javacTask, String fd) {
      fd = fd.trim();
      JClass out = this.cacheGet(fd);
      if (out != null) {
         return out;
      } else if (fd.indexOf(91) != -1) {
         String normalFd = ArrayClassImpl.normalizeArrayName(fd);
         out = this.cacheGet(normalFd);
         if (out == null) {
            out = ArrayClassImpl.createClassForFD(normalFd, this);
            this.cachePut(out, normalFd);
         }

         this.cachePut(out, fd);
         return out;
      } else {
         int dot = fd.indexOf(36);
         String pkg;
         if (dot != -1) {
            pkg = fd.substring(0, dot);
            ((ClassImpl)this.loadClass(pkg)).ensureLoaded();
            JClass out = this.cacheGet(fd);
            int dot = fd.lastIndexOf(46);
            if (out == null) {
               String pkg;
               String name;
               if (dot == -1) {
                  pkg = "";
                  name = fd;
               } else {
                  pkg = fd.substring(0, dot);
                  name = fd.substring(dot + 1);
               }

               out = new UnresolvedClassImpl(pkg, name, this.mContext);
               this.mContext.getLogger().warning("failed to resolve class " + fd);
               this.cachePut((JClass)out);
            }

            return (JClass)out;
         } else {
            dot = fd.lastIndexOf(46);
            String name;
            if (dot == -1) {
               pkg = "";
               name = fd;
            } else {
               pkg = fd.substring(0, dot);
               name = fd.substring(dot + 1);
            }

            JClass out = this.mBuilder.build(javacTask, pkg, name);
            if (this.mContext.getLogger().isVerbose()) {
               this.mContext.getLogger().verbose("used mBuilder = " + this.mBuilder.getClass().getName() + " to build pkg=" + pkg + ", name=" + name + ", out=" + out);
            }

            if (out == null) {
               JClass out = new UnresolvedClassImpl(pkg, name, this.mContext);
               this.mContext.getLogger().warning("failed to resolve class " + fd);
               this.cachePut(out);
               return out;
            } else {
               this.cachePut(out);
               return out;
            }
         }
      }
   }

   public JPackage getPackage(String named) {
      JPackage out = (JPackage)this.mName2Package.get(named);
      if (out == null) {
         out = new PackageImpl(this.mContext, named);
         this.mName2Package.put(named, out);
      }

      return (JPackage)out;
   }

   private void initCache() {
      PrimitiveClassImpl.mapNameToPrimitive(this.mContext, this.mFd2ClassCache);
      this.mFd2ClassCache.put("void", new VoidClassImpl(this.mContext));
   }

   private void cachePut(JClass clazz) {
      this.mFd2ClassCache.put(clazz.getFieldDescriptor().trim(), new WeakReference(clazz));
   }

   private void cachePut(JClass clazz, String cachedName) {
      this.mFd2ClassCache.put(cachedName, new WeakReference(clazz));
   }

   private JClass cacheGet(String fd) {
      Object out = this.mFd2ClassCache.get(fd.trim());
      if (out == null) {
         return null;
      } else if (out instanceof JClass) {
         return (JClass)out;
      } else if (out instanceof WeakReference) {
         out = ((WeakReference)out).get();
         if (out == null) {
            this.mFd2ClassCache.remove(fd.trim());
            return null;
         } else {
            return (JClass)out;
         }
      } else {
         throw new IllegalStateException();
      }
   }

   public void initialize(ClassImpl out) {
      if (this.mInitializer != null) {
         if (this.mAlreadyInitializing) {
            this.mInitializeStack.push(out);
         } else {
            out.accept(this.mInitializer);

            while(!this.mInitializeStack.isEmpty()) {
               ClassImpl initme = (ClassImpl)this.mInitializeStack.pop();
               initme.accept(this.mInitializer);
            }

            this.mAlreadyInitializing = false;
         }
      }

   }

   public Collection getResolvedClasses() {
      return Collections.unmodifiableCollection(this.mFd2ClassCache.values());
   }

   public void addToCache(JClass c) {
      this.cachePut((MClass)c);
   }
}

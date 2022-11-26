package org.apache.openjpa.meta;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.enhance.PCEnhancer;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import serp.bytecode.BCClass;
import serp.bytecode.BCClassLoader;
import serp.bytecode.BCField;
import serp.bytecode.BCMethod;
import serp.bytecode.Code;
import serp.bytecode.Project;

class InterfaceImplGenerator {
   private static final Localizer _loc = Localizer.forPackage(InterfaceImplGenerator.class);
   private static final String POSTFIX = "openjpaimpl";
   private final MetaDataRepository _repos;
   private final Map _impls = new WeakHashMap();
   private final Project _project = new Project();
   private final Project _enhProject = new Project();

   public InterfaceImplGenerator(MetaDataRepository repos) {
      this._repos = repos;
   }

   public synchronized Class createImpl(ClassMetaData meta) {
      Class iface = meta.getDescribedType();
      Class impl = (Class)this._impls.get(iface);
      if (impl != null) {
         return impl;
      } else {
         ClassLoader parentLoader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(iface));
         BCClassLoader loader = (BCClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newBCClassLoaderAction(this._project, parentLoader));
         BCClassLoader enhLoader = (BCClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newBCClassLoaderAction(this._enhProject, parentLoader));
         BCClass bc = this._project.loadClass(this.getClassName(meta));
         bc.declareInterface(iface);
         ClassMetaData sup = meta.getPCSuperclassMetaData();
         if (sup != null) {
            bc.setSuperclass(sup.getInterfaceImpl());
            enhLoader = (BCClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newBCClassLoaderAction(this._enhProject, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(sup.getInterfaceImpl()))));
         }

         FieldMetaData[] fields = meta.getDeclaredFields();
         Set methods = new HashSet();

         for(int i = 0; i < fields.length; ++i) {
            this.addField(bc, iface, fields[i], methods);
         }

         this.invalidateNonBeanMethods(bc, iface, methods);

         try {
            meta.setInterfaceImpl(Class.forName(bc.getName(), true, loader));
         } catch (Throwable var15) {
            throw (new InternalException(_loc.get("interface-load", iface, loader), var15)).setFatal(true);
         }

         bc = this._enhProject.loadClass(new ByteArrayInputStream(bc.toByteArray()), loader);
         PCEnhancer enhancer = new PCEnhancer(this._repos, bc, meta);
         int result = enhancer.run();
         if (result != 8) {
            throw (new InternalException(_loc.get("interface-badenhance", (Object)iface))).setFatal(true);
         } else {
            try {
               impl = Class.forName(bc.getName(), true, enhLoader);
            } catch (Throwable var14) {
               throw (new InternalException(_loc.get("interface-load2", iface, enhLoader), var14)).setFatal(true);
            }

            this._impls.put(iface, impl);
            return impl;
         }
      }
   }

   private void addField(BCClass bc, Class iface, FieldMetaData fmd, Set methods) {
      String name = fmd.getName();
      Class type = fmd.getDeclaredType();
      BCField field = bc.declareField(name, type);
      field.setAccessFlags(2);
      name = StringUtils.capitalize(name);
      String prefix = isGetter(iface, fmd) ? "get" : "is";
      BCMethod meth = bc.declareMethod(prefix + name, type, (Class[])null);
      meth.makePublic();
      Code code = meth.getCode(true);
      code.aload().setThis();
      code.getfield().setField(field);
      code.xreturn().setType(type);
      code.calculateMaxStack();
      code.calculateMaxLocals();
      methods.add(getMethodSafe(iface, meth.getName(), (Class)null));
      meth = bc.declareMethod("set" + name, Void.TYPE, new Class[]{type});
      meth.makePublic();
      code = meth.getCode(true);
      code.aload().setThis();
      code.xload().setParam(0).setType(type);
      code.putfield().setField(field);
      code.vreturn();
      code.calculateMaxStack();
      code.calculateMaxLocals();
      methods.add(getMethodSafe(iface, meth.getName(), type));
   }

   private void invalidateNonBeanMethods(BCClass bc, Class iface, Set methods) {
      Method[] meths = (Method[])((Method[])AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodsAction(iface)));
      Class type = this._repos.getMetaDataFactory().getDefaults().getUnimplementedExceptionType();

      for(int i = 0; i < meths.length; ++i) {
         if (!methods.contains(meths[i])) {
            BCMethod meth = bc.declareMethod(meths[i].getName(), meths[i].getReturnType(), meths[i].getParameterTypes());
            meth.makePublic();
            Code code = meth.getCode(true);
            code.anew().setType(type);
            code.dup();
            code.invokespecial().setMethod(type, "<init>", Void.TYPE, (Class[])null);
            code.athrow();
            code.calculateMaxLocals();
            code.calculateMaxStack();
         }
      }

   }

   protected final String getClassName(ClassMetaData meta) {
      Class iface = meta.getDescribedType();
      return iface.getName() + "$" + System.identityHashCode(iface) + "openjpaimpl";
   }

   private static Method getMethodSafe(Class iface, String name, Class arg) {
      try {
         return (Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(iface, name, arg == null ? null : new Class[]{arg}));
      } catch (PrivilegedActionException var4) {
         throw new InternalException(_loc.get("interface-mismatch", (Object)name));
      }
   }

   private static boolean isGetter(Class iface, FieldMetaData fmd) {
      if (fmd.getType() != Boolean.TYPE && fmd.getType() != Boolean.class) {
         return true;
      } else {
         try {
            Method meth = (Method)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodAction(iface, "is" + StringUtils.capitalize(fmd.getName()), (Class[])null));
            return meth == null;
         } catch (PrivilegedActionException var3) {
            return true;
         }
      }
   }

   boolean isImplType(Class cls) {
      return cls.getName().endsWith("openjpaimpl") && cls.getName().indexOf(36) != -1;
   }

   public Class toManagedInterface(Class cls) {
      Class[] ifaces = cls.getInterfaces();

      for(int i = 0; i < ifaces.length; ++i) {
         if (this._impls.get(ifaces[i]) == cls) {
            return ifaces[i];
         }
      }

      throw new IllegalArgumentException(cls.getName());
   }
}

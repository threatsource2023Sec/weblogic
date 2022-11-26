package org.apache.openjpa.enhance;

import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.Map;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.JavaVersions;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UserException;

public class ClassRedefiner {
   private static final Localizer _loc = Localizer.forPackage(ClassRedefiner.class);
   private static Boolean _canRedefine = null;

   public static void redefineClasses(OpenJPAConfiguration conf, final Map classes) {
      if (classes != null && classes.size() != 0 && canRedefineClasses()) {
         Log log = conf.getLog("openjpa.Enhance");
         Instrumentation inst = null;
         ClassFileTransformer t = null;

         try {
            inst = InstrumentationFactory.getInstrumentation();
            Class[] array = (Class[])classes.keySet().toArray(new Class[classes.size()]);
            if (JavaVersions.VERSION >= 6) {
               log.trace(_loc.get("retransform-types", (Object)classes.keySet()));
               t = new ClassFileTransformer() {
                  public byte[] transform(ClassLoader loader, String clsName, Class classBeingRedefined, ProtectionDomain pd, byte[] classfileBuffer) {
                     return (byte[])classes.get(classBeingRedefined);
                  }
               };
               Method meth = inst.getClass().getMethod("addTransformer", ClassFileTransformer.class, Boolean.TYPE);
               meth.invoke(inst, t, true);
               meth = inst.getClass().getMethod("retransformClasses", array.getClass());
               meth.invoke(inst, array);
            } else {
               log.trace(_loc.get("redefine-types", (Object)classes.keySet()));
               ClassDefinition[] defs = new ClassDefinition[array.length];

               for(int i = 0; i < defs.length; ++i) {
                  defs[i] = new ClassDefinition(array[i], (byte[])classes.get(array[i]));
               }

               inst.redefineClasses(defs);
            }
         } catch (NoSuchMethodException var17) {
            throw new InternalException(var17);
         } catch (IllegalAccessException var18) {
            throw new InternalException(var18);
         } catch (InvocationTargetException var19) {
            throw new UserException(var19.getCause());
         } catch (IOException var20) {
            throw new InternalException(var20);
         } catch (ClassNotFoundException var21) {
            throw new InternalException(var21);
         } catch (UnmodifiableClassException var22) {
            throw new InternalException(var22);
         } finally {
            if (inst != null && t != null) {
               inst.removeTransformer(t);
            }

         }

      }
   }

   public static boolean canRedefineClasses() {
      if (_canRedefine == null) {
         try {
            Instrumentation inst = InstrumentationFactory.getInstrumentation();
            if (inst == null) {
               _canRedefine = Boolean.FALSE;
            } else if (JavaVersions.VERSION == 5) {
               _canRedefine = Boolean.TRUE;
            } else {
               _canRedefine = (Boolean)Instrumentation.class.getMethod("isRetransformClassesSupported").invoke(inst);
            }
         } catch (Exception var1) {
            _canRedefine = Boolean.FALSE;
         }
      }

      return _canRedefine;
   }
}

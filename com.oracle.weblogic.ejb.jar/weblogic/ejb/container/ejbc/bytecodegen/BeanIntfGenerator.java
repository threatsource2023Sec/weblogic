package weblogic.ejb.container.ejbc.bytecodegen;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.objectweb.asm.ClassWriter;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.utils.annotation.BeaSynthetic.Helper;

class BeanIntfGenerator implements Generator {
   private final SessionBeanInfo sbi;
   private final String clsName;

   BeanIntfGenerator(NamingConvention nc, SessionBeanInfo sbi) {
      this.sbi = sbi;
      this.clsName = BCUtil.binName(nc.getGeneratedBeanInterfaceName());
   }

   public Generator.Output generate() {
      ClassWriter cw = new ClassWriter(0);
      cw.visit(49, 1537, this.clsName, (String)null, "java/lang/Object", new String[]{BCUtil.binName(WLEnterpriseBean.class)});
      int baseMods = 1025;

      Method m;
      int mods;
      for(Iterator var3 = this.getMethods(this.sbi.getBeanClass()).iterator(); var3.hasNext(); cw.visitMethod(mods, m.getName(), BCUtil.methodDesc(m), (String)null, BCUtil.exceptionsDesc(m.getExceptionTypes())).visitEnd()) {
         m = (Method)var3.next();
         mods = baseMods;
         if (m.isVarArgs()) {
            mods = baseMods + 128;
         }
      }

      cw.visitEnd();
      return new ClassFileOutput(this.clsName, cw.toByteArray());
   }

   private Iterable getMethods(Class c) {
      Map methodMap = new TreeMap();
      Method[] var4 = c.getMethods();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method m = var4[var6];
         if (!Modifier.isAbstract(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()) && (!Helper.isBeaSyntheticMethod(m) || this.isSyntheticServiceMethod(m)) && m.getDeclaringClass() != Object.class) {
            String methodDesc = m.getName() + ":" + BCUtil.methodDesc(m);
            methodMap.put(methodDesc, m);
         }
      }

      return methodMap.values();
   }

   private boolean isSyntheticServiceMethod(Method method) {
      Class beanClass = method.getDeclaringClass();

      for(Class c = beanClass.getSuperclass(); c != Object.class; c = c.getSuperclass()) {
         try {
            Method superMethod = c.getMethod(method.getName(), method.getParameterTypes());
            if (!Helper.isBeaSyntheticMethod(superMethod)) {
               return true;
            }
         } catch (NoSuchMethodException var5) {
            return false;
         }
      }

      return false;
   }
}

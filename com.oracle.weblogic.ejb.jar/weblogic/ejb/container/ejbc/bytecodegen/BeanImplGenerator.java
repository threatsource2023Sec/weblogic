package weblogic.ejb.container.ejbc.bytecodegen;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJBContext;
import javax.transaction.Transaction;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.EJBCreateInvoker;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.interfaces.WLSessionBean;
import weblogic.ejb.container.interfaces.WLSessionSynchronization;
import weblogic.ejb.container.internal.WLEnterpriseBeanUtils;
import weblogic.ejb.container.utils.EJBMethodsUtil;
import weblogic.kernel.ResettableThreadLocal;

class BeanImplGenerator implements Generator {
   private final NamingConvention nc;
   private final SessionBeanInfo sbi;
   private final String clsName;
   private final String superClsName;
   private ClassWriter cw;
   private MethodVisitor clInitMV;
   private final List initMVs = new LinkedList();

   BeanImplGenerator(NamingConvention nc, SessionBeanInfo sbi) {
      this.nc = nc;
      this.sbi = sbi;
      this.clsName = BCUtil.binName(nc.getGeneratedBeanClassName());
      this.superClsName = BCUtil.binName(nc.getBeanClassName());
   }

   public Generator.Output generate() {
      this.cw = new ClassWriter(1);
      this.cw.visit(49, 33, this.clsName, (String)null, this.superClsName, this.getInterfaces());
      Constructor[] var1 = this.sbi.getBeanClass().getConstructors();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Constructor ctr = var1[var3];
         String methodDesc = BCUtil.methodDesc(Void.TYPE, ctr.getParameterTypes());
         MethodVisitor initMV = this.cw.visitMethod(1, "<init>", methodDesc, (String)null, BCUtil.exceptionsDesc(ctr.getExceptionTypes()));
         this.initMVs.add(initMV);
         initMV.visitCode();
         initMV.visitVarInsn(25, 0);
         BCUtil.loadArgs(initMV, ctr.getParameterTypes(), false);
         initMV.visitMethodInsn(183, this.superClsName, "<init>", methodDesc, false);
      }

      this.addMethodStateMembers();
      this.addMembers(new FieldInfo("__WL_EJBContext", EJBContext.class), "__WL_getEJBContext", "__WL_setEJBContext");
      Iterator var8;
      MethodVisitor initMV;
      if (this.sbi.isStateful()) {
         this.addMembers(new FieldInfo("__WL_busy", Boolean.TYPE), "__WL_isBusy", "__WL_setBusy");
         this.addMembers(new FieldInfo("__WL_needsRemove", Boolean.TYPE), "__WL_needsRemove", "__WL_setNeedsRemove");
         this.addMembers(new FieldInfo("__WL_bmtx", Transaction.class), "__WL_getBeanManagedTransaction", "__WL_setBeanManagedTransaction");
         this.addMembers(new FieldInfo("__WL_needsSessionSync", Boolean.TYPE), "__WL_needsSessionSynchronization", "__WL_setNeedsSessionSynchronization");
         var8 = this.initMVs.iterator();

         while(var8.hasNext()) {
            initMV = (MethodVisitor)var8.next();
            initMV.visitVarInsn(25, 0);
            initMV.visitInsn(4);
            initMV.visitFieldInsn(181, this.clsName, "__WL_needsSessionSync", "Z");
         }

         StatefulSessionBeanInfo ssbi = (StatefulSessionBeanInfo)this.sbi;
         FieldInfo xpcsField = null;
         if (ssbi.containsExtendedPersistenceContextRefs()) {
            xpcsField = new FieldInfo("__WL_persistenceContexts", Set.class);
            BCUtil.addInstanceField(this.cw, xpcsField, false);
            Iterator var12 = this.initMVs.iterator();

            while(var12.hasNext()) {
               MethodVisitor initMV = (MethodVisitor)var12.next();
               initMV.visitVarInsn(25, 0);
               initMV.visitTypeInsn(187, "java/util/HashSet");
               initMV.visitInsn(89);
               initMV.visitMethodInsn(183, "java/util/HashSet", "<init>", "()V", false);
               initMV.visitFieldInsn(181, this.clsName, xpcsField.fieldName(), xpcsField.fieldDesc());
            }
         }

         this.addGetExtendedPersistenceContexts(xpcsField);
         if (this.sbi.isEJB30() && !Serializable.class.isAssignableFrom(this.sbi.getBeanClass())) {
            BCUtil.addSerializationSupport(this.clsName, this.cw, this.getClassInit());
         }

         if (ssbi.implementsSessionSynchronization()) {
            Map map = ssbi.getSessionSyncMethodMapping();
            Method[] var15 = WLSessionSynchronization.class.getMethods();
            int var16 = var15.length;

            for(int var17 = 0; var17 < var16; ++var17) {
               Method intfMethod = var15[var17];
               if (map.containsKey(intfMethod)) {
                  this.invokeSuper(intfMethod, (Method)map.get(intfMethod));
               } else {
                  BCUtil.addDummyImplementation(this.cw, intfMethod);
               }
            }
         }
      }

      var8 = this.initMVs.iterator();

      while(var8.hasNext()) {
         initMV = (MethodVisitor)var8.next();
         initMV.visitInsn(177);
         initMV.visitMaxs(0, 0);
         initMV.visitEnd();
      }

      if (this.clInitMV != null) {
         this.clInitMV.visitInsn(177);
         this.clInitMV.visitMaxs(0, 0);
         this.clInitMV.visitEnd();
      }

      this.cw.visitEnd();
      return new ClassFileOutput(this.clsName, this.cw.toByteArray());
   }

   private String[] getInterfaces() {
      List intfs = new ArrayList(4);
      intfs.add(BCUtil.binName(this.nc.getGeneratedBeanInterfaceName()));
      if (this.sbi.isStateful()) {
         intfs.add(BCUtil.binName(WLSessionBean.class));
         if (this.sbi.isEJB30() && !Serializable.class.isAssignableFrom(this.sbi.getBeanClass())) {
            intfs.add(BCUtil.binName(Serializable.class));
         }

         if (((StatefulSessionBeanInfo)this.sbi).implementsSessionSynchronization()) {
            intfs.add(BCUtil.binName(WLSessionSynchronization.class));
         }
      } else {
         intfs.add(BCUtil.binName(WLEnterpriseBean.class));
         if (this.sbi.isStateless() && !this.sbi.isEJB30()) {
            intfs.add(BCUtil.binName(EJBCreateInvoker.class));
         }
      }

      return (String[])intfs.toArray(new String[intfs.size()]);
   }

   private MethodVisitor getClassInit() {
      if (this.clInitMV == null) {
         this.clInitMV = this.cw.visitMethod(8, "<clinit>", "()V", (String)null, (String[])null);
         this.clInitMV.visitCode();
      }

      return this.clInitMV;
   }

   private void addMethodStateMembers() {
      if (this.sbi.isSingleton()) {
         FieldInfo stateField = new FieldInfo("__WL_method_state", ResettableThreadLocal.class);
         BCUtil.addInstanceField(this.cw, stateField, true);
         Iterator var2 = this.initMVs.iterator();

         MethodVisitor setMV;
         while(var2.hasNext()) {
            setMV = (MethodVisitor)var2.next();
            setMV.visitVarInsn(25, 0);
            setMV.visitMethodInsn(184, BCUtil.binName(WLEnterpriseBeanUtils.class), "newSingletonBeanState", "()" + stateField.fieldDesc(), false);
            setMV.visitFieldInsn(181, this.clsName, stateField.fieldName(), stateField.fieldDesc());
         }

         MethodVisitor getMV = this.cw.visitMethod(1, "__WL_getMethodState", "()I", (String)null, (String[])null);
         getMV.visitCode();
         getMV.visitVarInsn(25, 0);
         getMV.visitFieldInsn(180, this.clsName, stateField.fieldName(), stateField.fieldDesc());
         getMV.visitMethodInsn(182, stateField.binName(), "get", "()Ljava/lang/Object;", false);
         getMV.visitTypeInsn(192, "java/lang/Integer");
         getMV.visitMethodInsn(182, "java/lang/Integer", "intValue", "()I", false);
         getMV.visitInsn(172);
         getMV.visitMaxs(1, 1);
         getMV.visitEnd();
         setMV = this.cw.visitMethod(1, "__WL_setMethodState", "(I)V", (String)null, (String[])null);
         setMV.visitCode();
         setMV.visitVarInsn(25, 0);
         setMV.visitFieldInsn(180, this.clsName, stateField.fieldName(), stateField.fieldDesc());
         setMV.visitVarInsn(21, 1);
         setMV.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
         setMV.visitMethodInsn(182, stateField.binName(), "set", "(Ljava/lang/Object;)V", false);
         setMV.visitInsn(177);
         setMV.visitMaxs(2, 2);
         setMV.visitEnd();
      } else {
         this.addMembers(new FieldInfo("__WL_method_state", Integer.TYPE), "__WL_getMethodState", "__WL_setMethodState");
      }

   }

   private void invokeSuper(Method intfMethod, Method supersMethod) {
      MethodVisitor mv = this.cw.visitMethod(1, intfMethod.getName(), BCUtil.methodDesc(intfMethod), (String)null, BCUtil.exceptionsDesc(intfMethod.getExceptionTypes()));
      mv.visitCode();
      if (this.isAccessible(supersMethod)) {
         mv.visitVarInsn(25, 0);
         BCUtil.loadArgs(mv, supersMethod);
         mv.visitMethodInsn(183, this.superClsName, supersMethod.getName(), BCUtil.methodDesc(supersMethod), false);
         mv.visitInsn(BCUtil.returnOpcode(supersMethod.getReturnType()));
      } else {
         String fieldName = "__WL_METHOD" + intfMethod.getName();
         this.cw.visitField(26, fieldName, BCUtil.METHOD_FIELD_DESC, (String)null, (Object)null).visitEnd();
         MethodVisitor clInit = this.getClassInit();
         clInit.visitLdcInsn(Type.getType("L" + this.superClsName + ";"));
         clInit.visitLdcInsn(supersMethod.getName());
         Class[] params = supersMethod.getParameterTypes();
         BCUtil.pushInsn(clInit, params.length);
         clInit.visitTypeInsn(189, "java/lang/Class");

         for(int i = 0; i < params.length; ++i) {
            clInit.visitInsn(89);
            BCUtil.pushInsn(clInit, i);
            if (params[i].isPrimitive()) {
               clInit.visitFieldInsn(178, BCUtil.getBoxedClassBinName(params[i]), "TYPE", "Ljava/lang/Class;");
            } else {
               clInit.visitLdcInsn(Type.getType(BCUtil.fieldDesc(params[i])));
            }

            clInit.visitInsn(83);
         }

         clInit.visitMethodInsn(184, BCUtil.binName(EJBMethodsUtil.class), "getInvokableMethod", "(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
         clInit.visitFieldInsn(179, this.clsName, fieldName, BCUtil.METHOD_FIELD_DESC);
         mv.visitFieldInsn(178, this.clsName, fieldName, BCUtil.METHOD_FIELD_DESC);
         mv.visitVarInsn(25, 0);
         BCUtil.boxArgs(mv, supersMethod);
         mv.visitMethodInsn(182, "java/lang/reflect/Method", "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", false);
         BCUtil.unboxReturn(mv, supersMethod.getReturnType());
      }

      mv.visitMaxs(0, 0);
      mv.visitEnd();
   }

   private boolean isAccessible(Method m) {
      int mods = m.getModifiers();
      return !Modifier.isPrivate(mods) && (Modifier.isPublic(mods) || m.getDeclaringClass().getPackage().equals(this.sbi.getBeanClass().getPackage()));
   }

   private void addGetExtendedPersistenceContexts(FieldInfo xpcsField) {
      MethodVisitor mv = this.cw.visitMethod(1, "__WL_getExtendedPersistenceContexts", "()Ljava/util/Set;", (String)null, (String[])null);
      mv.visitCode();
      if (xpcsField != null) {
         mv.visitVarInsn(25, 0);
         mv.visitFieldInsn(180, this.clsName, xpcsField.fieldName(), xpcsField.fieldDesc());
      } else {
         mv.visitInsn(1);
      }

      mv.visitInsn(176);
      mv.visitMaxs(1, 1);
      mv.visitEnd();
   }

   private void addMembers(FieldInfo fi, String getter, String setter) {
      BCUtil.addInstanceField(this.cw, fi, false);
      BCUtil.addGetter(this.cw, this.clsName, getter, fi);
      BCUtil.addSetter(this.cw, this.clsName, setter, fi);
   }
}

package com.bea.core.repackaged.springframework.cglib.core;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.asm.FieldVisitor;
import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.transform.ClassTransformer;
import java.util.HashMap;
import java.util.Map;

public class ClassEmitter extends ClassTransformer {
   private ClassInfo classInfo;
   private Map fieldInfo;
   private static int hookCounter;
   private MethodVisitor rawStaticInit;
   private CodeEmitter staticInit;
   private CodeEmitter staticHook;
   private Signature staticHookSig;

   public ClassEmitter(ClassVisitor cv) {
      this.setTarget(cv);
   }

   public ClassEmitter() {
      super(Constants.ASM_API);
   }

   public void setTarget(ClassVisitor cv) {
      this.cv = cv;
      this.fieldInfo = new HashMap();
      this.staticInit = this.staticHook = null;
      this.staticHookSig = null;
   }

   private static synchronized int getNextHook() {
      return ++hookCounter;
   }

   public ClassInfo getClassInfo() {
      return this.classInfo;
   }

   public void begin_class(int version, final int access, String className, final Type superType, final Type[] interfaces, String source) {
      final Type classType = Type.getType("L" + className.replace('.', '/') + ";");
      this.classInfo = new ClassInfo() {
         public Type getType() {
            return classType;
         }

         public Type getSuperType() {
            return superType != null ? superType : Constants.TYPE_OBJECT;
         }

         public Type[] getInterfaces() {
            return interfaces;
         }

         public int getModifiers() {
            return access;
         }
      };
      this.cv.visit(version, access, this.classInfo.getType().getInternalName(), (String)null, this.classInfo.getSuperType().getInternalName(), TypeUtils.toInternalNames(interfaces));
      if (source != null) {
         this.cv.visitSource(source, (String)null);
      }

      this.init();
   }

   public CodeEmitter getStaticHook() {
      if (TypeUtils.isInterface(this.getAccess())) {
         throw new IllegalStateException("static hook is invalid for this class");
      } else {
         if (this.staticHook == null) {
            this.staticHookSig = new Signature("CGLIB$STATICHOOK" + getNextHook(), "()V");
            this.staticHook = this.begin_method(8, this.staticHookSig, (Type[])null);
            if (this.staticInit != null) {
               this.staticInit.invoke_static_this(this.staticHookSig);
            }
         }

         return this.staticHook;
      }
   }

   protected void init() {
   }

   public int getAccess() {
      return this.classInfo.getModifiers();
   }

   public Type getClassType() {
      return this.classInfo.getType();
   }

   public Type getSuperType() {
      return this.classInfo.getSuperType();
   }

   public void end_class() {
      if (this.staticHook != null && this.staticInit == null) {
         this.begin_static();
      }

      if (this.staticInit != null) {
         this.staticHook.return_value();
         this.staticHook.end_method();
         this.rawStaticInit.visitInsn(177);
         this.rawStaticInit.visitMaxs(0, 0);
         this.staticInit = this.staticHook = null;
         this.staticHookSig = null;
      }

      this.cv.visitEnd();
   }

   public CodeEmitter begin_method(int access, Signature sig, Type[] exceptions) {
      if (this.classInfo == null) {
         throw new IllegalStateException("classInfo is null! " + this);
      } else {
         MethodVisitor v = this.cv.visitMethod(access, sig.getName(), sig.getDescriptor(), (String)null, TypeUtils.toInternalNames(exceptions));
         if (sig.equals(Constants.SIG_STATIC) && !TypeUtils.isInterface(this.getAccess())) {
            this.rawStaticInit = v;
            MethodVisitor wrapped = new MethodVisitor(Constants.ASM_API, v) {
               public void visitMaxs(int maxStack, int maxLocals) {
               }

               public void visitInsn(int insn) {
                  if (insn != 177) {
                     super.visitInsn(insn);
                  }

               }
            };
            this.staticInit = new CodeEmitter(this, wrapped, access, sig, exceptions);
            if (this.staticHook == null) {
               this.getStaticHook();
            } else {
               this.staticInit.invoke_static_this(this.staticHookSig);
            }

            return this.staticInit;
         } else {
            return sig.equals(this.staticHookSig) ? new CodeEmitter(this, v, access, sig, exceptions) {
               public boolean isStaticHook() {
                  return true;
               }
            } : new CodeEmitter(this, v, access, sig, exceptions);
         }
      }
   }

   public CodeEmitter begin_static() {
      return this.begin_method(8, Constants.SIG_STATIC, (Type[])null);
   }

   public void declare_field(int access, String name, Type type, Object value) {
      FieldInfo existing = (FieldInfo)this.fieldInfo.get(name);
      FieldInfo info = new FieldInfo(access, name, type, value);
      if (existing != null) {
         if (!info.equals(existing)) {
            throw new IllegalArgumentException("Field \"" + name + "\" has been declared differently");
         }
      } else {
         this.fieldInfo.put(name, info);
         this.cv.visitField(access, name, type.getDescriptor(), (String)null, value);
      }

   }

   boolean isFieldDeclared(String name) {
      return this.fieldInfo.get(name) != null;
   }

   FieldInfo getFieldInfo(String name) {
      FieldInfo field = (FieldInfo)this.fieldInfo.get(name);
      if (field == null) {
         throw new IllegalArgumentException("Field " + name + " is not declared in " + this.getClassType().getClassName());
      } else {
         return field;
      }
   }

   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      this.begin_class(version, access, name.replace('/', '.'), TypeUtils.fromInternalName(superName), TypeUtils.fromInternalNames(interfaces), (String)null);
   }

   public void visitEnd() {
      this.end_class();
   }

   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
      this.declare_field(access, name, Type.getType(desc), value);
      return null;
   }

   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
      return this.begin_method(access, new Signature(name, desc), TypeUtils.fromInternalNames(exceptions));
   }

   static class FieldInfo {
      int access;
      String name;
      Type type;
      Object value;

      public FieldInfo(int access, String name, Type type, Object value) {
         this.access = access;
         this.name = name;
         this.type = type;
         this.value = value;
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else if (!(o instanceof FieldInfo)) {
            return false;
         } else {
            FieldInfo other = (FieldInfo)o;
            if (this.access == other.access && this.name.equals(other.name) && this.type.equals(other.type)) {
               if (this.value == null ^ other.value == null) {
                  return false;
               } else {
                  return this.value == null || this.value.equals(other.value);
               }
            } else {
               return false;
            }
         }
      }

      public int hashCode() {
         return this.access ^ this.name.hashCode() ^ this.type.hashCode() ^ (this.value == null ? 0 : this.value.hashCode());
      }
   }
}

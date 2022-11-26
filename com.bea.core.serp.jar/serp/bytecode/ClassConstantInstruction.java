package serp.bytecode;

import java.util.HashMap;
import java.util.Map;

public class ClassConstantInstruction {
   private static final Class[] _params = new Class[]{String.class};
   private static final Map _wrappers = new HashMap();
   private Instruction _ins = null;
   private Code _code = null;
   private BCClass _class = null;
   private boolean _invalid = false;

   ClassConstantInstruction(BCClass bc, Code code, Instruction nop) {
      this._class = bc;
      this._code = code;
      this._ins = nop;
   }

   public Instruction setClass(String name) {
      name = this._class.getProject().getNameCache().getExternalForm(name, false);
      this.setClassName(name, getWrapperClass(name));
      return this._ins;
   }

   public Instruction setClass(Class type) {
      return this.setClass(type.getName());
   }

   public Instruction setClass(BCClass type) {
      return this.setClass(type.getName());
   }

   private void setClassName(String name, Class wrapper) {
      if (this._invalid) {
         throw new IllegalStateException();
      } else {
         Instruction before = this._code.hasNext() ? this._code.next() : null;
         this._code.before(this._ins);
         this._code.next();
         if (wrapper != null) {
            this._code.getstatic().setField(wrapper, "TYPE", Class.class);
         } else {
            this.setObject(name);
         }

         if (before != null) {
            this._code.before(before);
         } else {
            this._code.afterLast();
         }

         this._invalid = true;
      }
   }

   private void setObject(String name) {
      BCField field = this.addClassField(name);
      BCMethod method = this.addClassLoadMethod();
      this._code.getstatic().setField(field);
      JumpInstruction ifnull = this._code.ifnull();
      this._code.getstatic().setField(field);
      JumpInstruction go2 = this._code.go2();
      ifnull.setTarget(this._code.constant().setValue(name));
      this._code.invokestatic().setMethod(method);
      this._code.dup();
      this._code.putstatic().setField(field);
      go2.setTarget(this._code.nop());
   }

   private BCField addClassField(String name) {
      String fieldName = "class$L" + name.replace('.', '$').replace('[', '$').replace(';', '$');
      BCField field = this._class.getDeclaredField(fieldName);
      if (field == null) {
         field = this._class.declareField(fieldName, Class.class);
         field.makePackage();
         field.setStatic(true);
         field.setSynthetic(true);
      }

      return field;
   }

   private BCMethod addClassLoadMethod() {
      BCMethod method = this._class.getDeclaredMethod("class$", _params);
      if (method != null) {
         return method;
      } else {
         method = this._class.declareMethod("class$", Class.class, _params);
         method.setStatic(true);
         method.makePackage();
         method.setSynthetic(true);
         Code code = method.getCode(true);
         code.setMaxStack(3);
         code.setMaxLocals(2);
         Instruction tryStart = code.aload().setLocal(0);
         code.invokestatic().setMethod(Class.class, "forName", Class.class, _params);
         Instruction tryEnd = code.areturn();
         Instruction handlerStart = code.astore().setLocal(1);
         code.anew().setType(NoClassDefFoundError.class);
         code.dup();
         code.aload().setLocal(1);
         code.invokevirtual().setMethod((Class)Throwable.class, "getMessage", (Class)String.class, (Class[])null);
         code.invokespecial().setMethod(NoClassDefFoundError.class, "<init>", Void.TYPE, _params);
         code.athrow();
         code.addExceptionHandler(tryStart, tryEnd, handlerStart, (Class)ClassNotFoundException.class);
         return method;
      }
   }

   private static Class getWrapperClass(String name) {
      return name == null ? null : (Class)_wrappers.get(name);
   }

   static {
      _wrappers.put(Byte.TYPE.getName(), Byte.class);
      _wrappers.put(Boolean.TYPE.getName(), Boolean.class);
      _wrappers.put(Character.TYPE.getName(), Character.class);
      _wrappers.put(Double.TYPE.getName(), Double.class);
      _wrappers.put(Float.TYPE.getName(), Float.class);
      _wrappers.put(Integer.TYPE.getName(), Integer.class);
      _wrappers.put(Long.TYPE.getName(), Long.class);
      _wrappers.put(Short.TYPE.getName(), Short.class);
   }
}

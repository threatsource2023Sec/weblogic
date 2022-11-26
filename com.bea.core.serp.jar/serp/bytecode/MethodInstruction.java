package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import serp.bytecode.lowlevel.ComplexEntry;
import serp.bytecode.lowlevel.InvokeDynamicEntry;
import serp.bytecode.visitor.BCVisitor;
import serp.util.Strings;

public class MethodInstruction extends Instruction {
   private int _index = 0;

   MethodInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   int getLength() {
      if (this.getOpcode() == 185) {
         return super.getLength() + 4;
      } else {
         return this.getOpcode() == 186 ? super.getLength() + 4 : super.getLength() + 2;
      }
   }

   public int getLogicalStackChange() {
      String ret = this.getMethodReturnName();
      if (ret == null) {
         return 0;
      } else {
         int stack = 0;
         if (this.getOpcode() != 184) {
            --stack;
         }

         String[] params = this.getMethodParamNames();

         for(int i = 0; i < params.length; ++i) {
            --stack;
         }

         if (!Void.TYPE.getName().equals(ret)) {
            ++stack;
         }

         return stack;
      }
   }

   public int getStackChange() {
      String ret = this.getMethodReturnName();
      if (ret == null) {
         return 0;
      } else {
         int stack = 0;
         if (this.getOpcode() != 184) {
            --stack;
         }

         String[] params = this.getMethodParamNames();

         for(int i = 0; i < params.length; --stack) {
            if (Long.TYPE.getName().equals(params[i]) || Double.TYPE.getName().equals(params[i])) {
               --stack;
            }

            ++i;
         }

         if (!Void.TYPE.getName().equals(ret)) {
            ++stack;
         }

         if (Long.TYPE.getName().equals(ret) || Double.TYPE.getName().equals(ret)) {
            ++stack;
         }

         return stack;
      }
   }

   public int getMethodIndex() {
      return this._index;
   }

   public MethodInstruction setMethodIndex(int index) {
      this._index = index;
      return this;
   }

   public BCMethod getMethod() {
      String dec = this.getMethodDeclarerName();
      if (dec == null) {
         return null;
      } else {
         BCClass bc = this.getProject().loadClass(dec, this.getClassLoader());
         BCMethod[] meths = bc.getMethods(this.getMethodName(), this.getMethodParamNames());
         return meths.length == 0 ? null : meths[0];
      }
   }

   public MethodInstruction setMethod(BCMethod method) {
      return method == null ? this.setMethodIndex(0) : this.setMethod(method.getDeclarer().getName(), method.getName(), method.getReturnName(), method.getParamNames(), false);
   }

   public MethodInstruction setMethod(Method method) {
      return method == null ? this.setMethodIndex(0) : this.setMethod(method.getDeclaringClass(), method.getName(), method.getReturnType(), method.getParameterTypes());
   }

   public MethodInstruction setMethod(Constructor method) {
      if (method == null) {
         return this.setMethodIndex(0);
      } else {
         this.setOpcode(183);
         return this.setMethod(method.getDeclaringClass(), "<init>", Void.TYPE, method.getParameterTypes());
      }
   }

   public MethodInstruction setMethod(String dec, String name, String returnType, String[] params) {
      return this.setMethod(dec, name, returnType, params, true);
   }

   private MethodInstruction setMethod(String dec, String name, String returnType, String[] params, boolean copy) {
      if (name != null || returnType != null || dec != null || params != null && params.length != 0) {
         if (dec == null) {
            dec = "";
         }

         if (name == null) {
            name = "";
         }

         if (returnType == null) {
            returnType = "";
         }

         if (params == null) {
            params = new String[0];
         } else if (copy) {
            String[] pcopy = new String[params.length];
            System.arraycopy(params, 0, pcopy, 0, params.length);
            params = pcopy;
         }

         NameCache cache = this.getProject().getNameCache();
         returnType = cache.getInternalForm(returnType, true);
         dec = cache.getInternalForm(dec, false);

         for(int i = 0; i < params.length; ++i) {
            params[i] = cache.getInternalForm(params[i], true);
         }

         String desc = cache.getDescriptor(returnType, params);
         if (this.getOpcode() == 185) {
            return this.setMethodIndex(this.getPool().findInterfaceMethodEntry(dec, name, desc, true));
         } else if (this.getOpcode() == 186) {
            int bootstrapindex = Integer.parseInt(dec);
            return this.setMethodIndex(this.getPool().findInvokeDynamicEntry(bootstrapindex, name, desc, true));
         } else {
            return this.setMethodIndex(this.getPool().findMethodEntry(dec, name, desc, true));
         }
      } else {
         return this.setMethodIndex(0);
      }
   }

   public MethodInstruction setMethod(String name, String returnType, String[] params) {
      BCClass owner = this.getCode().getMethod().getDeclarer();
      return this.setMethod(owner.getName(), name, returnType, params);
   }

   public MethodInstruction setMethod(Class dec, String name, Class returnType, Class[] params) {
      String decName = dec == null ? null : dec.getName();
      String returnName = returnType == null ? null : returnType.getName();
      String[] paramNames = null;
      if (params != null) {
         paramNames = new String[params.length];

         for(int i = 0; i < params.length; ++i) {
            paramNames[i] = params[i].getName();
         }
      }

      return this.setMethod(decName, name, returnName, paramNames, false);
   }

   public MethodInstruction setMethod(String name, Class returnType, Class[] params) {
      BCClass owner = this.getCode().getMethod().getDeclarer();
      String returnName = returnType == null ? null : returnType.getName();
      String[] paramNames = null;
      if (params != null) {
         paramNames = new String[params.length];

         for(int i = 0; i < params.length; ++i) {
            paramNames[i] = params[i].getName();
         }
      }

      return this.setMethod(owner.getName(), name, returnName, paramNames, false);
   }

   public MethodInstruction setMethod(BCClass dec, String name, BCClass returnType, BCClass[] params) {
      String decName = dec == null ? null : dec.getName();
      String returnName = returnType == null ? null : returnType.getName();
      String[] paramNames = null;
      if (params != null) {
         paramNames = new String[params.length];

         for(int i = 0; i < params.length; ++i) {
            paramNames[i] = params[i].getName();
         }
      }

      return this.setMethod(decName, name, returnName, paramNames, false);
   }

   public MethodInstruction setMethod(String name, BCClass returnType, BCClass[] params) {
      BCClass owner = this.getCode().getMethod().getDeclarer();
      String returnName = returnType == null ? null : returnType.getName();
      String[] paramNames = null;
      if (params != null) {
         paramNames = new String[params.length];

         for(int i = 0; i < params.length; ++i) {
            paramNames[i] = params[i].getName();
         }
      }

      return this.setMethod(owner.getName(), name, returnName, paramNames, false);
   }

   public String getMethodName() {
      if (this._index == 0) {
         return null;
      } else {
         String name = null;
         if (this.getOpcode() == 186) {
            InvokeDynamicEntry ide = (InvokeDynamicEntry)this.getPool().getEntry(this._index);
            name = ide.getNameAndTypeEntry().getNameEntry().getValue();
         } else {
            ComplexEntry entry = (ComplexEntry)this.getPool().getEntry(this._index);
            name = entry.getNameAndTypeEntry().getNameEntry().getValue();
         }

         if (name.length() == 0) {
            name = null;
         }

         return name;
      }
   }

   public MethodInstruction setMethodName(String name) {
      return this.setMethod(this.getMethodDeclarerName(), name, this.getMethodReturnName(), this.getMethodParamNames());
   }

   public String getMethodReturnName() {
      if (this._index == 0) {
         return null;
      } else {
         String desc = null;
         if (this.getOpcode() == 186) {
            InvokeDynamicEntry ide = (InvokeDynamicEntry)this.getPool().getEntry(this._index);
            desc = ide.getNameAndTypeEntry().getDescriptorEntry().getValue();
         } else {
            ComplexEntry entry = (ComplexEntry)this.getPool().getEntry(this._index);
            desc = entry.getNameAndTypeEntry().getDescriptorEntry().getValue();
         }

         NameCache cache = this.getProject().getNameCache();
         String name = cache.getExternalForm(cache.getDescriptorReturnName(desc), false);
         return name.length() == 0 ? null : name;
      }
   }

   public Class getMethodReturnType() {
      String type = this.getMethodReturnName();
      return type == null ? null : Strings.toClass(type, this.getClassLoader());
   }

   public BCClass getMethodReturnBC() {
      String type = this.getMethodReturnName();
      return type == null ? null : this.getProject().loadClass(type, this.getClassLoader());
   }

   public MethodInstruction setMethodReturn(String type) {
      return this.setMethod(this.getMethodDeclarerName(), this.getMethodName(), type, this.getMethodParamNames());
   }

   public MethodInstruction setMethodReturn(Class type) {
      String name = null;
      if (type != null) {
         name = type.getName();
      }

      return this.setMethodReturn(name);
   }

   public MethodInstruction setMethodReturn(BCClass type) {
      String name = null;
      if (type != null) {
         name = type.getName();
      }

      return this.setMethodReturn(name);
   }

   public String[] getMethodParamNames() {
      if (this._index == 0) {
         return new String[0];
      } else {
         String desc = null;
         if (this.getOpcode() == 186) {
            InvokeDynamicEntry ide = (InvokeDynamicEntry)this.getPool().getEntry(this._index);
            desc = ide.getNameAndTypeEntry().getDescriptorEntry().getValue();
         } else {
            ComplexEntry entry = (ComplexEntry)this.getPool().getEntry(this._index);
            desc = entry.getNameAndTypeEntry().getDescriptorEntry().getValue();
         }

         NameCache cache = this.getProject().getNameCache();
         String[] names = cache.getDescriptorParamNames(desc);

         for(int i = 0; i < names.length; ++i) {
            names[i] = cache.getExternalForm(names[i], false);
         }

         return names;
      }
   }

   public Class[] getMethodParamTypes() {
      String[] paramNames = this.getMethodParamNames();
      Class[] params = new Class[paramNames.length];

      for(int i = 0; i < paramNames.length; ++i) {
         params[i] = Strings.toClass(paramNames[i], this.getClassLoader());
      }

      return params;
   }

   public BCClass[] getMethodParamBCs() {
      String[] paramNames = this.getMethodParamNames();
      BCClass[] params = new BCClass[paramNames.length];

      for(int i = 0; i < paramNames.length; ++i) {
         params[i] = this.getProject().loadClass(paramNames[i], this.getClassLoader());
      }

      return params;
   }

   public MethodInstruction setMethodParams(String[] types) {
      return this.setMethod(this.getMethodDeclarerName(), this.getMethodName(), this.getMethodReturnName(), types);
   }

   public void setMethodParams(Class[] types) {
      if (types == null) {
         this.setMethodParams((String[])null);
      } else {
         String[] names = new String[types.length];

         for(int i = 0; i < types.length; ++i) {
            names[i] = types[i].getName();
         }

         this.setMethodParams(names);
      }

   }

   public void setMethodParams(BCClass[] types) {
      if (types == null) {
         this.setMethodParams((String[])null);
      } else {
         String[] names = new String[types.length];

         for(int i = 0; i < types.length; ++i) {
            names[i] = types[i].getName();
         }

         this.setMethodParams(names);
      }

   }

   public String getMethodDeclarerName() {
      if (this._index == 0) {
         return null;
      } else {
         String name = null;
         if (this.getOpcode() == 186) {
            InvokeDynamicEntry ide = (InvokeDynamicEntry)this.getPool().getEntry(this._index);
            name = String.valueOf(ide.getBootstrapMethodAttrIndex());
         } else {
            ComplexEntry entry = (ComplexEntry)this.getPool().getEntry(this._index);
            name = this.getProject().getNameCache().getExternalForm(entry.getClassEntry().getNameEntry().getValue(), false);
         }

         return name.length() == 0 ? null : name;
      }
   }

   public Class getMethodDeclarerType() {
      String type = this.getMethodDeclarerName();
      return type == null ? null : Strings.toClass(type, this.getClassLoader());
   }

   public BCClass getMethodDeclarerBC() {
      String type = this.getMethodDeclarerName();
      return type == null ? null : this.getProject().loadClass(type, this.getClassLoader());
   }

   public MethodInstruction setMethodDeclarer(String type) {
      return this.setMethod(type, this.getMethodName(), this.getMethodReturnName(), this.getMethodParamNames());
   }

   public MethodInstruction setMethodDeclarer(Class type) {
      String name = null;
      if (type != null) {
         name = type.getName();
      }

      return this.setMethodDeclarer(name);
   }

   public MethodInstruction setMethodDeclarer(BCClass type) {
      String name = null;
      if (type != null) {
         name = type.getName();
      }

      return this.setMethodDeclarer(name);
   }

   public boolean equalsInstruction(Instruction other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof MethodInstruction)) {
         return false;
      } else if (!super.equalsInstruction(other)) {
         return false;
      } else {
         MethodInstruction ins = (MethodInstruction)other;
         String s1 = this.getMethodName();
         String s2 = ins.getMethodName();
         if (s1 != null && s2 != null && !s1.equals(s2)) {
            return false;
         } else {
            s1 = this.getMethodReturnName();
            s2 = ins.getMethodReturnName();
            if (s1 != null && s2 != null && !s1.equals(s2)) {
               return false;
            } else {
               s1 = this.getMethodDeclarerName();
               s2 = ins.getMethodDeclarerName();
               if (s1 != null && s2 != null && !s1.equals(s2)) {
                  return false;
               } else {
                  String[] p1 = this.getMethodParamNames();
                  String[] p2 = ins.getMethodParamNames();
                  if (p1.length != 0 && p2.length != 0 && p1.length != p2.length) {
                     return false;
                  } else {
                     for(int i = 0; i < p1.length; ++i) {
                        if (p1[i] != null && p2[i] != null && !p1[i].equals(p2[i])) {
                           return false;
                        }
                     }

                     return true;
                  }
               }
            }
         }
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterMethodInstruction(this);
      visit.exitMethodInstruction(this);
   }

   void read(Instruction orig) {
      super.read(orig);
      MethodInstruction ins = (MethodInstruction)orig;
      this.setMethod(ins.getMethodDeclarerName(), ins.getMethodName(), ins.getMethodReturnName(), ins.getMethodParamNames());
   }

   void read(DataInput in) throws IOException {
      super.read(in);
      this._index = in.readUnsignedShort();
      if (this.getOpcode() == 185 || this.getOpcode() == 186) {
         in.readByte();
         in.readByte();
      }

   }

   void write(DataOutput out) throws IOException {
      super.write(out);
      out.writeShort(this._index);
      if (this.getOpcode() == 185) {
         String[] args = this.getMethodParamNames();
         int count = 1;

         for(int i = 0; i < args.length; ++count) {
            if (Long.TYPE.getName().equals(args[i]) || Double.TYPE.getName().equals(args[i])) {
               ++count;
            }

            ++i;
         }

         out.writeByte(count);
         out.writeByte(0);
      } else if (this.getOpcode() == 186) {
         out.writeByte(0);
         out.writeByte(0);
      }

   }
}

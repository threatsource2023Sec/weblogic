package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;
import serp.bytecode.visitor.VisitAcceptor;
import serp.util.Strings;

public class BCMethod extends BCMember implements VisitAcceptor {
   BCMethod(BCClass owner) {
      super(owner);
   }

   public boolean isSynchronized() {
      return (this.getAccessFlags() & 32) > 0;
   }

   public void setSynchronized(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 32);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -33);
      }

   }

   public boolean isNative() {
      return (this.getAccessFlags() & 256) > 0;
   }

   public void setNative(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 256);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -257);
      }

   }

   public boolean isAbstract() {
      return (this.getAccessFlags() & 1024) > 0;
   }

   public void setAbstract(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 1024);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -1025);
      }

   }

   public boolean isStrict() {
      return (this.getAccessFlags() & 2048) > 0;
   }

   public void setStrict(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 2048);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -2049);
      }

   }

   public boolean isVarArgs() {
      return (this.getAccessFlags() & 128) > 0;
   }

   public void setVarArgs(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 128);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -129);
      }

   }

   public boolean isBridge() {
      return (this.getAccessFlags() & 64) > 0;
   }

   public void setBridge(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 64);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -65);
      }

   }

   public String getReturnName() {
      return this.getProject().getNameCache().getExternalForm(this.getProject().getNameCache().getDescriptorReturnName(this.getDescriptor()), false);
   }

   public Class getReturnType() {
      return Strings.toClass(this.getReturnName(), this.getClassLoader());
   }

   public BCClass getReturnBC() {
      return this.getProject().loadClass(this.getReturnName(), this.getClassLoader());
   }

   public void setReturn(String name) {
      this.setDescriptor(this.getProject().getNameCache().getDescriptor(name, this.getParamNames()));
   }

   public void setReturn(Class type) {
      this.setReturn(type.getName());
   }

   public void setReturn(BCClass type) {
      this.setReturn(type.getName());
   }

   public String[] getParamNames() {
      String[] params = this.getProject().getNameCache().getDescriptorParamNames(this.getDescriptor());

      for(int i = 0; i < params.length; ++i) {
         params[i] = this.getProject().getNameCache().getExternalForm(params[i], false);
      }

      return params;
   }

   public Class[] getParamTypes() {
      String[] paramNames = this.getParamNames();
      Class[] params = new Class[paramNames.length];

      for(int i = 0; i < paramNames.length; ++i) {
         params[i] = Strings.toClass(paramNames[i], this.getClassLoader());
      }

      return params;
   }

   public BCClass[] getParamBCs() {
      String[] paramNames = this.getParamNames();
      BCClass[] params = new BCClass[paramNames.length];

      for(int i = 0; i < paramNames.length; ++i) {
         params[i] = this.getProject().loadClass(paramNames[i], this.getClassLoader());
      }

      return params;
   }

   public void setParams(String[] names) {
      if (names == null) {
         names = new String[0];
      }

      this.setDescriptor(this.getProject().getNameCache().getDescriptor(this.getReturnName(), names));
   }

   public void setParams(Class[] types) {
      if (types == null) {
         this.setParams((String[])null);
      } else {
         String[] names = new String[types.length];

         for(int i = 0; i < types.length; ++i) {
            names[i] = types[i].getName();
         }

         this.setParams(names);
      }

   }

   public void setParams(BCClass[] types) {
      if (types == null) {
         this.setParams((String[])null);
      } else {
         String[] names = new String[types.length];

         for(int i = 0; i < types.length; ++i) {
            names[i] = types[i].getName();
         }

         this.setParams(names);
      }

   }

   public void addParam(String type) {
      String[] origParams = this.getParamNames();
      String[] params = new String[origParams.length + 1];

      for(int i = 0; i < origParams.length; ++i) {
         params[i] = origParams[i];
      }

      params[origParams.length] = type;
      this.setParams(params);
   }

   public void addParam(Class type) {
      this.addParam(type.getName());
   }

   public void addParam(BCClass type) {
      this.addParam(type.getName());
   }

   public void addParam(int pos, String type) {
      String[] origParams = this.getParamNames();
      if (pos >= 0 && pos < origParams.length) {
         String[] params = new String[origParams.length + 1];
         int i = 0;

         for(int index = 0; i < params.length; ++i) {
            if (i == pos) {
               params[i] = type;
            } else {
               params[i] = origParams[index++];
            }
         }

         this.setParams(params);
      } else {
         throw new IndexOutOfBoundsException("pos = " + pos);
      }
   }

   public void addParam(int pos, Class type) {
      this.addParam(pos, type.getName());
   }

   public void addParam(int pos, BCClass type) {
      this.addParam(pos, type.getName());
   }

   public void setParam(int pos, String type) {
      String[] origParams = this.getParamNames();
      if (pos >= 0 && pos < origParams.length) {
         String[] params = new String[origParams.length];

         for(int i = 0; i < params.length; ++i) {
            if (i == pos) {
               params[i] = type;
            } else {
               params[i] = origParams[i];
            }
         }

         this.setParams(params);
      } else {
         throw new IndexOutOfBoundsException("pos = " + pos);
      }
   }

   public void setParam(int pos, Class type) {
      this.setParam(pos, type.getName());
   }

   public void setParam(int pos, BCClass type) {
      this.setParam(pos, type.getName());
   }

   public void clearParams() {
      this.setParams((String[])null);
   }

   public void removeParam(int pos) {
      String[] origParams = this.getParamNames();
      if (pos >= 0 && pos < origParams.length) {
         String[] params = new String[origParams.length - 1];
         int i = 0;

         for(int index = 0; i < origParams.length; ++i) {
            if (i != pos) {
               params[index++] = origParams[i];
            }
         }

         this.setParams(params);
      } else {
         throw new IndexOutOfBoundsException("pos = " + pos);
      }
   }

   public Exceptions getExceptions(boolean add) {
      Exceptions exceptions = (Exceptions)this.getAttribute("Exceptions");
      if (add && exceptions == null) {
         if (exceptions == null) {
            exceptions = (Exceptions)this.addAttribute("Exceptions");
         }

         return exceptions;
      } else {
         return exceptions;
      }
   }

   public boolean removeExceptions() {
      return this.removeAttribute("Exceptions");
   }

   public Code getCode(boolean add) {
      Code code = (Code)this.getAttribute("Code");
      if (code != null) {
         code.beforeFirst();
         return code;
      } else {
         return !add ? null : (Code)this.addAttribute("Code");
      }
   }

   public boolean removeCode() {
      return this.removeAttribute("Code");
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterBCMethod(this);
      this.visitAttributes(visit);
      visit.exitBCMethod(this);
   }

   void initialize(String name, String descriptor) {
      super.initialize(name, descriptor);
      this.makePublic();
   }
}

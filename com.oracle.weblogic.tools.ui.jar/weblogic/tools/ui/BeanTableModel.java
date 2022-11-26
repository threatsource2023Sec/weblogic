package weblogic.tools.ui;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.table.AbstractTableModel;
import weblogic.servlet.jsp.BeanUtils;

public class BeanTableModel extends AbstractTableModel {
   Class beanClass;
   BeanInfo beanInfo;
   PropertyDescriptor[] propertyDescriptors;
   Object[] beans;
   String[] props;
   boolean[] editable;
   Object parentBean;
   Method parentSetter;
   Method parentAdder;
   private static final Object[] NO_ARGS = new Object[0];

   public BeanTableModel(Class bc, BeanInfo bi, PropertyDescriptor[] pds, Object[] beans, String[] props, String[] titles) {
      this.beanClass = bc;
      this.beanInfo = bi;
      this.propertyDescriptors = pds;
      this.beans = beans;
      this.props = props;
      this.editable = new boolean[props.length];

      for(int i = 0; i < props.length; ++i) {
         if (!BeanUtils.isStringConvertible(pds[i].getPropertyType())) {
            this.editable[i] = false;
         } else {
            this.editable[i] = true;
         }

         this.editable[i] = true;
      }

   }

   public void setEditable(boolean b) {
      for(int i = 0; i < this.editable.length; ++i) {
         this.editable[i] = b;
      }

   }

   static void p(String s) {
      System.err.println("[BGModel]: " + s);
   }

   private void setParentInfo(Object parent, String parentProp, boolean useAdder) {
      if (parent == null) {
         this.parentSetter = null;
         this.parentAdder = null;
         this.parentBean = null;
      } else {
         RuntimeException re = null;
         Class c = parent.getClass();

         while(c != Object.class) {
            try {
               if (useAdder) {
                  this.parentAdder = this.findAdder(c, parentProp);
               } else {
                  this.parentAdder = null;
               }

               this.parentSetter = this.findSetter(c, parentProp);
               this.parentBean = parent;
               return;
            } catch (RuntimeException var7) {
               if (re == null) {
                  re = var7;
               }

               c = c.getSuperclass();
            }
         }

         if (re == null) {
            re = new RuntimeException("Couldn't find " + c.getClass() + " .set" + parentProp + "(" + this.beans.getClass().getComponentType() + ")");
         }

         throw re;
      }
   }

   public void setParentInfo(Object parent, String parentProp) {
      this.setParentInfo(parent, parentProp, false);
   }

   public void setParentAdder(Object parent, String parentProp) {
      this.setParentInfo(parent, parentProp, true);
   }

   private Method findAdder(Class parent, String parentProp) {
      Method result = null;
      int size = parentProp.length();
      if (null != parentProp && 's' == parentProp.charAt(size - 1)) {
         parentProp = parentProp.substring(0, size - 1);
      }

      String methodName = "add" + parentProp;

      try {
         Method[] methods = parent.getDeclaredMethods();

         for(int i = 0; methods != null && i < methods.length; ++i) {
            if (methods[i].getName().equalsIgnoreCase(methodName)) {
               Method m = methods[i];
               int mods = m.getModifiers();
               if ((mods & 1) != 0) {
                  Class[] args = m.getParameterTypes();
                  if (args != null && args.length == 1) {
                     Class param = args[0];
                     if (param.isAssignableFrom(this.beanClass)) {
                        result = m;
                     }
                  }
               }
            }
         }

         String var13 = "Couldn't find " + parent.getName() + "." + methodName + "(" + this.beanClass.getName() + "[])";
         return result;
      } catch (Exception var12) {
         if (var12 instanceof RuntimeException) {
            throw (RuntimeException)var12;
         } else {
            throw new RuntimeException("nested: " + var12);
         }
      }
   }

   private Method findSetter(Class parent, String parentProp) {
      String methodName = "set" + parentProp;

      try {
         Method[] methods = parent.getDeclaredMethods();

         for(int i = 0; methods != null && i < methods.length; ++i) {
            if (methods[i].getName().equalsIgnoreCase(methodName)) {
               Method m = methods[i];
               int mods = m.getModifiers();
               if ((mods & 1) != 0) {
                  Class[] args = m.getParameterTypes();
                  if (args != null && args.length == 1 && args[0].isArray()) {
                     Class param = args[0].getComponentType();
                     if (param.isAssignableFrom(this.beanClass)) {
                        return m;
                     }
                  }
               }
            }
         }

         String msg = "Couldn't find " + parent.getName() + "." + methodName + "(" + this.beanClass.getName() + "[])";
         throw new RuntimeException(msg);
      } catch (Exception var10) {
         if (var10 instanceof RuntimeException) {
            throw (RuntimeException)var10;
         } else {
            throw new RuntimeException("nested: " + var10);
         }
      }
   }

   public Object[] getBeans() {
      return this.beans;
   }

   public void setBeans(Object[] o) {
      this.beans = o;
   }

   private PropertyDescriptor getPD(String name) {
      int i;
      for(i = 0; i < this.propertyDescriptors.length; ++i) {
         if (this.propertyDescriptors[i].getName().equals(name)) {
            return this.propertyDescriptors[i];
         }
      }

      for(i = 0; i < this.propertyDescriptors.length; ++i) {
         if (this.propertyDescriptors[i].getName().equalsIgnoreCase(name)) {
            return this.propertyDescriptors[i];
         }
      }

      return null;
   }

   public Class getColumnClass(int col) {
      PropertyDescriptor pd = this.getPD(this.props[col]);
      if (pd == null) {
         return Object.class;
      } else {
         Class c = pd.getPropertyType();
         if (c != Boolean.class && c != Boolean.TYPE) {
            return !c.isPrimitive() && !Number.class.isAssignableFrom(c) ? Object.class : Number.class;
         } else {
            return Boolean.class;
         }
      }
   }

   public void setEditable(int col, boolean b) {
      if (b) {
         PropertyDescriptor pd = this.getPD(this.props[col]);
         if (pd.getWriteMethod() == null) {
            String msg = "property " + this.props[col] + " on " + this.beanClass.getName() + " cannot be editable, it has no write method";
            throw new RuntimeException(msg);
         }
      }

      this.editable[col] = b;
   }

   private static void handleITE(InvocationTargetException ite) {
      Throwable t = ite.getTargetException();
      if (t instanceof RuntimeException) {
         throw (RuntimeException)t;
      } else if (t instanceof Error) {
         throw (Error)t;
      } else {
         throw new RuntimeException(t.toString());
      }
   }

   public boolean isCellEditable(int row, int col) {
      return this.editable[col];
   }

   public int getColumnCount() {
      return this.props.length;
   }

   public void setValueAt(Object val, int row, int col) {
      if (null != val) {
         p("setValueAt(" + row + "," + col + ")->\"" + val + "\"(" + val.getClass().getName() + ")");
         PropertyDescriptor pd = this.getPD(this.props[col]);
         Method writer = pd.getWriteMethod();
         if (null == writer) {
            throw new RuntimeException("Couldn't find a setter for " + pd.getReadMethod().getName());
         } else {
            Object arg = null;
            if (!BeanUtils.isStringConvertible(pd.getPropertyType())) {
               arg = val;
            } else {
               String s = val.toString();
               arg = this.convertArg(s, pd.getPropertyType());
            }

            try {
               Object[] args = new Object[]{arg};
               writer.invoke(this.beans[row], args);
            } catch (InvocationTargetException var8) {
               handleITE(var8);
            } catch (IllegalAccessException var9) {
               var9.printStackTrace();
               throw new RuntimeException(var9.toString());
            }

         }
      }
   }

   public Object getValueAt(int row, int col) {
      if (row >= this.getRowCount()) {
         return null;
      } else if (col >= this.props.length) {
         return null;
      } else {
         try {
            PropertyDescriptor pd = this.getPD(this.props[col]);
            if (pd == null) {
               String msg = "bean class " + this.beanClass.getName() + " has no property " + this.props[col];
               throw new RuntimeException(msg);
            } else {
               Method reader = pd.getReadMethod();
               Object ret = reader.invoke(this.beans[row], NO_ARGS);
               if (pd.getPropertyType().isArray() && pd.getPropertyType().getComponentType() == String.class) {
                  ret = this.makeStringList((String[])((String[])ret));
               }

               return ret;
            }
         } catch (InvocationTargetException var6) {
            handleITE(var6);
            return null;
         } catch (IllegalAccessException var7) {
            throw new RuntimeException(var7.toString());
         }
      }
   }

   public int getRowCount() {
      return this.beans.length;
   }

   public void removeRow(int r) {
      Class ctype = this.beans.getClass().getComponentType();
      Object[] newbeans = (Object[])((Object[])Array.newInstance(ctype, this.beans.length - 1));
      System.arraycopy(this.beans, 0, newbeans, 0, r);
      System.arraycopy(this.beans, r + 1, newbeans, r, this.beans.length - r - 1);
      this.beans = newbeans;
      this.invokeParentSetter();
      this.fireTableRowsDeleted(r, r);
   }

   public void addRow(Object newobj) {
      Class ctype = this.beans.getClass().getComponentType();
      Object[] newbeans = (Object[])((Object[])Array.newInstance(ctype, this.beans.length + 1));

      for(int i = 0; i < this.beans.length; ++i) {
         newbeans[i] = this.beans[i];
      }

      try {
         newbeans[this.beans.length] = newobj;
      } catch (ArrayStoreException var5) {
         throw new ArrayStoreException("Make sure the beans stored in the table and the type  returned by createNewBean() match.");
      }

      this.beans = newbeans;
      if (this.parentAdder != null) {
         this.invokeParentAdder(newobj);
      } else {
         this.invokeParentSetter();
      }

      this.fireTableRowsInserted(this.beans.length - 1, this.beans.length - 1);
   }

   private String makeStringList(String[] x) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; x != null && i < x.length; ++i) {
         if (x[i] != null) {
            sb.append(x[i]);
         }

         if (i < x.length - 1) {
            sb.append(',');
         }
      }

      return sb.toString();
   }

   private void invokeParentSetter() {
      Object[] args = new Object[]{this.beans};
      this.invokeMethod(this.parentSetter, args);
   }

   private void invokeParentAdder(Object newobj) {
      Object[] args = new Object[]{newobj};
      if (this.parentAdder != null) {
         this.invokeMethod(this.parentAdder, args);
      }

   }

   private void invokeMethod(Method method, Object[] args) {
      if (this.parentBean != null) {
         try {
            method.invoke(this.parentBean, args);
         } catch (IllegalAccessException var4) {
            throw new RuntimeException(var4.toString());
         } catch (InvocationTargetException var5) {
            handleITE(var5);
         }

      }
   }

   private Object convertArg(String s, Class c) {
      if (c == String.class) {
         return s;
      } else if (c != Boolean.class && c != Boolean.TYPE) {
         if (c != Byte.class && c != Byte.TYPE) {
            if (c != Double.class && c != Double.TYPE) {
               if (c != Integer.class && c != Integer.TYPE) {
                  if (c != Float.class && c != Float.TYPE) {
                     if (c != Long.class && c != Long.TYPE) {
                        if (c != Character.class && c != Character.TYPE) {
                           throw new IllegalArgumentException("cannot convert String to " + c.getName());
                        } else {
                           return new Character(s.charAt(0));
                        }
                     } else {
                        return Long.valueOf(s.trim());
                     }
                  } else {
                     return Float.valueOf(s.trim());
                  }
               } else {
                  return Integer.valueOf(s.trim());
               }
            } else {
               return Double.valueOf(s.trim());
            }
         } else {
            return Byte.valueOf(s.trim());
         }
      } else {
         return Boolean.valueOf(s);
      }
   }

   private static void ppp(String s) {
      System.out.println("[BeanTableModel] " + s);
   }
}

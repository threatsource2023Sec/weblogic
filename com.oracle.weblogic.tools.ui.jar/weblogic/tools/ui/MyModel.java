package weblogic.tools.ui;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import javax.swing.table.AbstractTableModel;
import weblogic.utils.Debug;

public class MyModel extends AbstractTableModel {
   private boolean targetIsString;
   private HashMap m_map = null;
   Object bean;
   Class bc;
   Method reader;
   Method writer;
   Class propType;
   boolean autoCommit = true;
   Object[] tmpModel;
   private static final Object[] NO_ARGS = new Object[0];

   private static void p(String s) {
      System.err.println("[ListModel]: " + s);
   }

   public MyModel(Class bc, Object parent, String propName) {
      this.bc = bc;
      this.bean = parent;

      try {
         this.reader = this.getMethod("get" + propName);
         this.writer = this.getMethod("set" + propName);
         Class[] args = this.writer.getParameterTypes();
         this.propType = args[0].getComponentType();
         this.targetIsString = this.propType == String.class;
         if (!this.targetIsString) {
            this.m_map = new HashMap();
         }

      } catch (RuntimeException var5) {
         throw var5;
      } catch (Exception var6) {
         throw new RuntimeException(var6.toString());
      }
   }

   public boolean isAutoCommit() {
      return this.autoCommit;
   }

   public void setAutoCommit(boolean b) {
      if (!b) {
         this.tmpModel = this.getStrings();
      } else {
         this.tmpModel = null;
      }

      this.autoCommit = b;
   }

   public void modelToUI() {
      this.fireTableDataChanged();
   }

   public void uiToModel() {
      if (!this.isAutoCommit()) {
         this.invokeSetter(this.tmpModel);
      }

   }

   public Object getValueAt(int row, int col) {
      return col == 0 ? this.getElementAt(row) : null;
   }

   public boolean isCellEditable(int row, int col) {
      return this.targetIsString;
   }

   public void setValueAt(Object o, int row, int col) {
      if (col == 0) {
         Object[] x = this.getStrings();
         if (row >= 0 && row < x.length) {
            x[row] = o;
            this.setStrings(x);
         }

      }
   }

   public Class getColumnClass(int i) {
      return String.class;
   }

   public Object getElementAt(int i) {
      Object[] x = this.getStrings();
      return i >= 0 && i < x.length ? x[i] : null;
   }

   public int getColumnCount() {
      return 1;
   }

   public int getRowCount() {
      return this.getStrings().length;
   }

   public int getSize() {
      return this.getStrings().length;
   }

   public void swap(int i) {
      Object[] s = this.getStrings();
      Object tmp = s[i - 1];
      s[i - 1] = s[i];
      s[i] = tmp;
      this.setStrings(s);
      this.fireTableRowsUpdated(i - 1, i);
   }

   public void remove(int i) {
      Object[] r = this.getStrings();
      Object[] s = new String[r.length - 1];
      if (i != 0) {
         System.arraycopy(r, 0, s, 0, i);
      }

      if (i != s.length) {
         System.arraycopy(r, i + 1, s, i, s.length - i);
      }

      this.setStrings(s);
      this.fireTableRowsDeleted(i, i);
      this.fireTableStructureChanged();
   }

   public void add(Object n) {
      Object[] r = this.getStrings();
      Object[] s = new Object[r.length + 1];
      System.arraycopy(r, 0, s, 0, r.length);
      s[r.length] = n;
      if (!this.targetIsString) {
         this.m_map.put(n.toString(), n);
      }

      this.setStrings(s);
      this.fireTableRowsUpdated(r.length, r.length);
   }

   public void setBean(Object o) {
      int size = this.getSize();
      this.fireTableRowsDeleted(0, size);
      this.bean = o;
      if (!this.isAutoCommit()) {
         this.tmpModel = this.invokeGetter();
      }

      size = this.getSize();
      this.fireTableRowsInserted(0, size);
   }

   void setStrings(Object[] x) {
      if (x == null) {
         x = (Object[])((Object[])Array.newInstance(this.propType, 0));
      }

      if (!this.isAutoCommit()) {
         this.tmpModel = (Object[])((Object[])x.clone());
      } else {
         this.invokeSetter(x);
      }
   }

   private void invokeSetter(Object[] x) {
      if (x == null) {
         x = (Object[])((Object[])Array.newInstance(this.propType, 0));
      }

      Object[] args = new Object[1];
      Class targetClass = null;
      if (this.targetIsString) {
         targetClass = String.class;
      } else {
         if (x.length > 0) {
            targetClass = this.m_map.get(x[0].toString()).getClass();
         }

         if (targetClass == null) {
            targetClass = this.propType;
         }
      }

      Object newArray = Array.newInstance(targetClass, x.length);
      int i;
      if (this.targetIsString) {
         String[] realArgs = (String[])((String[])newArray);

         for(i = 0; i < x.length; ++i) {
            realArgs[i] = (String)x[i];
         }

         args[0] = realArgs;
      } else {
         Object[] realArgs = (Object[])((Object[])newArray);

         for(i = 0; i < x.length; ++i) {
            realArgs[i] = this.m_map.get(x[i].toString());
            Debug.assertion(null != realArgs[i], "Couldn't find object " + x[i]);
         }

         args[0] = realArgs;
      }

      try {
         this.writer.invoke(this.bean, args);
      } catch (InvocationTargetException var7) {
         handleITE(var7);
      } catch (IllegalAccessException var8) {
         throw new RuntimeException(var8.toString());
      }

   }

   Object[] getStrings() {
      if (!this.isAutoCommit()) {
         return (Object[])((Object[])this.tmpModel.clone());
      } else {
         return (Object[])(this.bean == null ? new String[0] : this.invokeGetter());
      }
   }

   private Object[] invokeGetter() {
      try {
         Object o = this.reader.invoke(this.bean, NO_ARGS);
         if (o == null) {
            return new String[0];
         } else if (this.targetIsString) {
            String[] x = (String[])((String[])o);
            return (String[])((String[])x.clone());
         } else {
            Object[] objects = (Object[])((Object[])o);
            String[] ret = new String[objects.length];

            for(int i = 0; i < objects.length; ++i) {
               ret[i] = objects[i].toString();
               this.m_map.put(ret[i].toString(), objects[i]);
            }

            return ret;
         }
      } catch (InvocationTargetException var5) {
         handleITE(var5);
         return null;
      } catch (IllegalAccessException var6) {
         throw new RuntimeException(var6.toString());
      }
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

   private Method getMethod(String mname) throws NoSuchMethodException {
      return getMethod(this.bc, mname);
   }

   public static Method getMethod(Class bc, String mname) throws NoSuchMethodException {
      Method[] methods = bc.getDeclaredMethods();
      Class[] args = null;

      for(int i = 0; methods != null && i < methods.length; ++i) {
         if (methods[i].getName().equalsIgnoreCase(mname)) {
            Method m = methods[i];
            int mods = m.getModifiers();
            if ((mods & 1) != 0) {
               args = m.getParameterTypes();
               if (mname.startsWith("set")) {
                  if (args != null && args.length == 1 && args[0].isArray()) {
                     return m;
                  }
               } else if (args == null || args.length == 0 || args.length == 1 && args[0] == Void.TYPE) {
                  return m;
               }
            }
         }
      }

      String msg = "cannot find appropriate method " + bc.getName() + "." + mname + "(" + (null != args ? args.getClass().toString() : ") ");
      throw new IllegalArgumentException(msg);
   }
}

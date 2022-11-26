package weblogic.tools.ui;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JLabel;
import weblogic.utils.Debug;

public abstract class Property implements FocusListener {
   protected boolean autoCommit;
   protected Object bean;
   protected PropertyDescriptor pd;
   protected JLabel label;
   private boolean required;
   protected Component component;
   private static final Object[] NO_ARGS = new Object[0];

   private static void p(String s) {
      System.err.println("[Property]: " + s);
   }

   public Property(Object b, PropertyDescriptor p, String l) {
      this(b, p, l, false);
   }

   public Property(Object b, PropertyDescriptor p, String l, boolean required) {
      this(b, p, l, required, (Component)null);
   }

   public Property(Object b, PropertyDescriptor p, String l, boolean required, Component comp) {
      this.autoCommit = true;
      this.bean = b;
      this.pd = p;
      this.component = comp;
      if (l == null) {
         l = this.pd.getName();
      }

      this.required = required;
      this.label = required ? UIFactory.getMandatoryLabel(l) : UIFactory.getLabel(l);
      if (comp != null) {
         comp.addFocusListener(this);
      }

   }

   public abstract Component getComponent();

   protected abstract Object getCurrentUIValue();

   protected abstract void setCurrentUIValue(Object var1);

   public boolean isAutoCommit() {
      return this.autoCommit;
   }

   public void setAutoCommit(boolean b) {
      this.autoCommit = b;
   }

   public void focusGained(FocusEvent e) {
   }

   public void focusLost(FocusEvent e) {
      if (this.isAutoCommit() && e.getSource() == this.getComponent()) {
         this.uiToModel();
      }

   }

   public boolean hasSeparateLabel() {
      return true;
   }

   public Object getBean() {
      return this.bean;
   }

   public void setBean(Object o) {
      this.bean = o;
   }

   public void setTooltip(String s) {
      if (s != null) {
         Component c = this.getComponent();
         if (c != null && c instanceof JComponent) {
            JComponent j = (JComponent)c;
            j.setToolTipText(s);
         }

      }
   }

   public String getHelpAnchor() {
      String ret = null;
      Component c = this.getComponent();
      if (c != null && c instanceof JComponent) {
         JComponent j = (JComponent)c;
         Object o = j.getClientProperty("wl.helpanchor");
         if (o != null) {
            ret = o.toString();
         }
      }

      return ret;
   }

   public void setHelpAnchor(String h) {
      Component c = this.getComponent();
      if (c != null && c instanceof JComponent) {
         JComponent j = (JComponent)c;
         j.putClientProperty("wl.helpanchor", h);
      }

   }

   public JLabel getLabel() {
      return this.label;
   }

   public void modelToUI() {
      this.setCurrentUIValue(this.invokeGetter());
   }

   public boolean isUIEmpty() {
      return false;
   }

   public void uiToModel() {
      this.invokeSetter(this.getCurrentUIValue());
   }

   protected static void handleITE(InvocationTargetException ite) {
      Throwable e = ite.getTargetException();
      if (e instanceof RuntimeException) {
         throw (RuntimeException)e;
      } else if (e instanceof Error) {
         throw (Error)e;
      } else {
         throw new RuntimeException(e.toString());
      }
   }

   public boolean isRequired() {
      return this.required;
   }

   protected Object invokeGetter() {
      Debug.assertion(this.bean != null);

      try {
         return this.pd.getReadMethod().invoke(this.bean, NO_ARGS);
      } catch (InvocationTargetException var2) {
         handleITE(var2);
         return null;
      } catch (IllegalAccessException var3) {
         throw new RuntimeException(var3.toString());
      }
   }

   protected void invokeSetter(Object val) {
      Debug.assertion(this.bean != null);

      try {
         Object[] args = new Object[1];
         if (this.pd.getPropertyType() == Integer.TYPE) {
            val = new Integer(Integer.parseInt(val.toString()));
         }

         args[0] = val;
         Debug.assertion(null != this.pd.getWriteMethod(), "Couldn't find a write method for " + this.pd.getReadMethod());
         this.pd.getWriteMethod().invoke(this.bean, args);
      } catch (InvocationTargetException var3) {
         handleITE(var3);
      } catch (IllegalAccessException var4) {
         throw new RuntimeException(var4.toString());
      }

   }

   public static Property parseSpec(Class bc, Object bean, BeanInfo bi, PropertyDescriptor[] pds, Object[] data) {
      if (bi == null) {
         try {
            bi = Introspector.getBeanInfo(bc);
         } catch (Exception var14) {
            var14.printStackTrace();
            throw new RuntimeException(var14.toString());
         }
      }

      if (pds == null) {
         pds = bi.getPropertyDescriptors();
      }

      String propName = (String)data[0];
      String label = (String)data[1];
      String tt = (String)data[2];
      PropertyDescriptor pd = getPD(pds, propName, bc);
      Class c = pd.getPropertyType();
      Property p = null;
      Object lastObj = data[data.length - 1];
      boolean required = lastObj == Boolean.TRUE;
      if (data.length > 3 && data[3] != null && !(data[3] instanceof Boolean)) {
         if (data[3] instanceof String[]) {
            String[] constrained = (String[])((String[])data[3]);
            p = new ListProperty(bean, pd, label, constrained, required);
         } else if (data[3] instanceof Object[]) {
            Object[] constrained = (Object[])((Object[])data[3]);
            p = new ObjectProperty(bean, pd, label, constrained, required);
         }
      } else if (c != Boolean.TYPE && c != Boolean.class) {
         if (!c.isPrimitive() && !Number.class.isAssignableFrom(c)) {
            p = new StringProperty(bean, pd, label, required);
         } else {
            p = new NumberProperty(bean, pd, label);
         }
      } else {
         p = new BooleanProperty(bean, pd, label);
      }

      if (data.length > 4 && data[4] != null) {
         String help = data[4].toString();
         ((Property)p).setHelpAnchor(help);
      }

      if (p == null) {
         throw new RuntimeException("bad data for prop: '" + propName + "' of " + bc.getName());
      } else {
         if (tt != null) {
            ((Property)p).setTooltip(tt);
         }

         return (Property)p;
      }
   }

   private static Class[] getAllInterfaces(Class bc) {
      ArrayList ifaces = new ArrayList();

      for(Class currentClass = bc; currentClass != null; currentClass = currentClass.getSuperclass()) {
         ifaces.add(currentClass);
         Class[] tmp = currentClass.getInterfaces();

         for(int j = 0; j < tmp.length; ++j) {
            ifaces.add(tmp[j]);
         }
      }

      return (Class[])((Class[])ifaces.toArray(new Class[ifaces.size()]));
   }

   static PropertyDescriptor getPD(PropertyDescriptor[] pds, String pname, Class bc) {
      PropertyDescriptor result = null;
      Class[] allIfaces = getAllInterfaces(bc);

      for(int j = 0; j < allIfaces.length && result == null; ++j) {
         for(int i = 0; i < pds.length; ++i) {
            if (pname.equalsIgnoreCase(pds[i].getName())) {
               result = pds[i];
            }
         }

         try {
            BeanInfo bi = Introspector.getBeanInfo(allIfaces[j]);
            pds = bi.getPropertyDescriptors();
         } catch (Exception var8) {
            pds = new PropertyDescriptor[0];
         }
      }

      if (result == null) {
         throw new RuntimeException("cannot find property '" + pname + "' for class " + bc.getName());
      } else {
         return result;
      }
   }
}

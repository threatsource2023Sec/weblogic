package weblogic.tools.ui;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PropertySet {
   protected Object bean;
   protected Property[] props;
   protected Class bc;

   public PropertySet(Class bc, Object[][] propdata) {
      this((Object)null, bc, propdata);
   }

   public PropertySet(Class bc, PropertyInfo[] info) {
      this.bc = bc;
      this.props = this.parseInfo(bc, info);
   }

   public PropertySet(Object bean, Class bc, Object[][] propdata) {
      this.bean = bean;
      this.bc = bc;
      this.props = this.parseData(bc, bean, propdata);
   }

   public PropertySet(Class bc, Property[] props) {
      this.bc = bc;
      this.props = (Property[])((Property[])props.clone());
   }

   public void setAutoCommit(boolean b) {
      for(int i = 0; i < this.props.length; ++i) {
         this.props[i].setAutoCommit(b);
      }

   }

   public StringProperty findString(String prop) {
      Property p = this.findPropByName(prop);
      if (p == null) {
         throw new RuntimeException("no such property '" + prop + "' on " + this.bc.getName());
      } else {
         return (StringProperty)p;
      }
   }

   public Property findPropByName(String prop) {
      for(int i = 0; this.props != null && i < this.props.length; ++i) {
         if (prop.equalsIgnoreCase(this.props[i].pd.getName())) {
            return this.props[i];
         }
      }

      return null;
   }

   public Property[] getProps() {
      return this.props;
   }

   public void setBean(Object o) {
      this.bean = o;
      if (this.bean != null) {
         for(int i = 0; i < this.props.length; ++i) {
            this.props[i].setBean(o);
         }

         this.modelToUI();
      }

   }

   public void modelToUI() {
      for(int i = 0; i < this.props.length; ++i) {
         this.props[i].modelToUI();
      }

   }

   public Object createNewBean() {
      if (this.bc.isInterface()) {
         throw new RuntimeException("cannot instantiate " + this.bc.getName() + " (it is an interface)");
      } else {
         int mods = this.bc.getModifiers();
         if ((mods & 1) == 0) {
            throw new RuntimeException("cannot instantiate " + this.bc.getName() + " (not public class)");
         } else if ((mods & 1024) != 0) {
            throw new RuntimeException("cannot instantiate " + this.bc.getName() + " (it is abstract class)");
         } else {
            try {
               Class[] voidargs = new Class[0];
               Constructor ctor = this.bc.getDeclaredConstructor(voidargs);
               return ctor != null ? ctor.newInstance() : null;
            } catch (InvocationTargetException var4) {
               Property.handleITE(var4);
               return null;
            } catch (Exception var5) {
               var5.printStackTrace();
               throw new RuntimeException("cannot instantiate " + this.bc.getName() + "(it has no public default constructor)");
            }
         }
      }
   }

   public void uiToModel() {
      for(int i = 0; i < this.props.length; ++i) {
         this.props[i].uiToModel();
      }

   }

   private Property[] parseInfo(Class bc, PropertyInfo[] info) {
      BeanInfo bi = null;
      PropertyDescriptor[] pds = null;

      try {
         bi = Introspector.getBeanInfo(bc);
         pds = bi.getPropertyDescriptors();
      } catch (RuntimeException var15) {
         throw var15;
      } catch (Exception var16) {
         throw new RuntimeException(var16.toString());
      }

      Property[] props = new Property[info.length];

      for(int i = 0; i < info.length; ++i) {
         PropertyInfo pi = info[i];
         String name = pi.getName();
         String label = pi.getLabel();
         String tip = pi.getTooltip();
         boolean required = pi.isRequired();
         PropertyDescriptor pd = Property.getPD(pds, name, bc);
         Class ptype = pd.getPropertyType();
         if (pi.getConstrainedObjects() != null) {
            ObjectProperty p = new ObjectProperty(pd, label, pi.getConstrainedObjects(), required);
            p.setAllowNull(pi.getAllowNullObject());
            props[i] = p;
         } else if (pi.getConstrainedStrings() != null) {
            ListProperty p = new ListProperty(pd, label, pi.getConstrainedStrings(), required);
            p.setAllowEditing(pi.getAllowListEditing());
            p.setSelectFirstElement(pi.getSelectFirstListElement());
            props[i] = p;
         } else if (ptype != Boolean.TYPE && ptype != Boolean.class) {
            if (!ptype.isPrimitive() && !Number.class.isAssignableFrom(ptype)) {
               StringProperty sp = new StringProperty(pd, label, required);
               sp.setEmptyIsNull(pi.isEmptyStringNull());
               props[i] = sp;
            } else {
               NumberProperty np = new NumberProperty(pd, label);
               np.setMin(pi.getNumberMin());
               np.setMax(pi.getNumberMax());
               np.setIncrement(pi.getNumberIncrement());
               props[i] = np;
            }
         } else {
            props[i] = new BooleanProperty(pd, label);
         }
      }

      return props;
   }

   private Property[] parseData(Class bc, Object bean, Object[][] propdata) {
      PropertyInfo[] info = new PropertyInfo[propdata.length];

      for(int i = 0; i < info.length; ++i) {
         info[i] = PropertyInfo.fromArray(propdata[i]);
      }

      return this.parseInfo(bc, info);
   }
}

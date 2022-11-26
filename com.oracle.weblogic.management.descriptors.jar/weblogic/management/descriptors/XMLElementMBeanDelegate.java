package weblogic.management.descriptors;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import weblogic.management.ManagementException;
import weblogic.utils.Debug;

public class XMLElementMBeanDelegate {
   protected static final String CDATA_BEGIN = "<![CDATA[";
   protected static final String CDATA_END = "]]>";
   protected PropertyChangeSupport changeSupport = null;
   private String name;

   public XMLElementMBeanDelegate() {
   }

   public XMLElementMBeanDelegate(String name) {
      this.name = name;
   }

   protected static String cdata(String s) {
      if (s == null) {
         return s;
      } else {
         return s.indexOf(62) < 0 ? s : "<![CDATA[" + s + "]]>";
      }
   }

   protected static boolean comp(String x, String y) {
      if (x == y) {
         return true;
      } else if ((x == null || x.length() == 0) && (y == null || y.length() == 0)) {
         return true;
      } else {
         return x != null && y != null ? x.equals(y) : false;
      }
   }

   protected static boolean comp(Object x, Object y) {
      if (x == y) {
         return true;
      } else if (x != null && y != null) {
         if (x.equals(y)) {
            return true;
         } else if (x.getClass().isArray() && y.getClass().isArray()) {
            Object[] a = (Object[])((Object[])x);
            Object[] b = (Object[])((Object[])y);
            if (a.length != b.length) {
               return false;
            } else {
               for(int i = 0; i < a.length; ++i) {
                  if (a[i] != b[i]) {
                     if (a[i] == null || b[i] == null) {
                        return false;
                     }

                     if (!a[i].equals(b[i])) {
                        return false;
                     }
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void register() throws ManagementException {
   }

   public void unregister() throws ManagementException {
   }

   public String toXML(int indentLevel) {
      Debug.say("WARNING:  toXML(int indentLevel) not found on class " + this.getClass().getName());
      return "";
   }

   public void addPropertyChangeListener(PropertyChangeListener listener) {
      this.getChangeSupport().addPropertyChangeListener(listener);
   }

   public void removePropertyChangeListener(PropertyChangeListener listener) {
      if (this.changeSupport != null) {
         this.getChangeSupport().removePropertyChangeListener(listener);
      }

   }

   public void firePropertyChange(String prop, Object oldVal, Object newVal) {
      if (this.changeSupport != null) {
         this.changeSupport.firePropertyChange(prop, oldVal, newVal);
      }

   }

   protected synchronized PropertyChangeSupport getChangeSupport() {
      if (this.changeSupport == null) {
         this.changeSupport = new PropertyChangeSupport(this);
      }

      return this.changeSupport;
   }

   protected void checkChange(String prop, String oldVal, String newVal) {
      if (!comp(oldVal, newVal)) {
         this.firePropertyChange(prop, oldVal, newVal);
      }

   }

   protected void checkChange(String prop, Object oldVal, Object newVal) {
      if (!comp(oldVal, newVal)) {
         this.firePropertyChange(prop, oldVal, newVal);
      }

   }

   protected void checkChange(String prop, boolean ov, boolean nv) {
      if (ov != nv) {
         this.firePropertyChange(prop, new Boolean(ov), new Boolean(nv));
      }

   }

   protected void checkChange(String prop, int ov, int nv) {
      if (ov != nv) {
         this.firePropertyChange(prop, new Integer(ov), new Integer(nv));
      }

   }
}

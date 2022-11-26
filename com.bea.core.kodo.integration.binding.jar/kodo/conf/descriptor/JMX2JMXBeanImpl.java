package kodo.conf.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class JMX2JMXBeanImpl extends LocalJMXBeanImpl implements JMX2JMXBean, Serializable {
   private String _NamingImpl;
   private String _ServiceURL;
   private static SchemaHelper2 _schemaHelper;

   public JMX2JMXBeanImpl() {
      this._initializeProperty(-1);
   }

   public JMX2JMXBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JMX2JMXBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getNamingImpl() {
      return this._NamingImpl;
   }

   public boolean isNamingImplInherited() {
      return false;
   }

   public boolean isNamingImplSet() {
      return this._isSet(9);
   }

   public void setNamingImpl(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NamingImpl;
      this._NamingImpl = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getServiceURL() {
      return this._ServiceURL;
   }

   public boolean isServiceURLInherited() {
      return false;
   }

   public boolean isServiceURLSet() {
      return this._isSet(10);
   }

   public void setServiceURL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceURL;
      this._ServiceURL = param0;
      this._postSet(10, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 9;
      }

      try {
         switch (idx) {
            case 9:
               this._NamingImpl = "mx4j.tools.naming.NamingService";
               if (initOne) {
                  break;
               }
            case 10:
               this._ServiceURL = "service:jmx:rmi://localhost/jndi/jmxservice";
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends LocalJMXBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 10:
               if (s.equals("NamingImpl")) {
                  return 9;
               } else if (s.equals("ServiceURL")) {
                  return 10;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new NoneJMXBeanImpl.SchemaHelper2();
            case 1:
               return new LocalJMXBeanImpl.SchemaHelper2();
            case 2:
               return new GUIJMXBeanImpl.SchemaHelper2();
            case 3:
               return new SchemaHelper2();
            case 4:
               return new MX4J1JMXBeanImpl.SchemaHelper2();
            case 5:
               return new WLS81JMXBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 9:
               return "NamingImpl";
            case 10:
               return "ServiceURL";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends LocalJMXBeanImpl.Helper {
      private JMX2JMXBeanImpl bean;

      protected Helper(JMX2JMXBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 9:
               return "NamingImpl";
            case 10:
               return "ServiceURL";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("NamingImpl")) {
            return 9;
         } else {
            return propName.equals("ServiceURL") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getGUIJMX() != null) {
            iterators.add(new ArrayIterator(new GUIJMXBean[]{this.bean.getGUIJMX()}));
         }

         if (this.bean.getJMX2JMX() != null) {
            iterators.add(new ArrayIterator(new JMX2JMXBean[]{this.bean.getJMX2JMX()}));
         }

         if (this.bean.getLocalJMX() != null) {
            iterators.add(new ArrayIterator(new LocalJMXBean[]{this.bean.getLocalJMX()}));
         }

         if (this.bean.getMX4J1JMX() != null) {
            iterators.add(new ArrayIterator(new MX4J1JMXBean[]{this.bean.getMX4J1JMX()}));
         }

         if (this.bean.getNoneJMX() != null) {
            iterators.add(new ArrayIterator(new NoneJMXBean[]{this.bean.getNoneJMX()}));
         }

         if (this.bean.getWLS81JMX() != null) {
            iterators.add(new ArrayIterator(new WLS81JMXBean[]{this.bean.getWLS81JMX()}));
         }

         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isNamingImplSet()) {
               buf.append("NamingImpl");
               buf.append(String.valueOf(this.bean.getNamingImpl()));
            }

            if (this.bean.isServiceURLSet()) {
               buf.append("ServiceURL");
               buf.append(String.valueOf(this.bean.getServiceURL()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            JMX2JMXBeanImpl otherTyped = (JMX2JMXBeanImpl)other;
            this.computeDiff("NamingImpl", this.bean.getNamingImpl(), otherTyped.getNamingImpl(), false);
            this.computeDiff("ServiceURL", this.bean.getServiceURL(), otherTyped.getServiceURL(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMX2JMXBeanImpl original = (JMX2JMXBeanImpl)event.getSourceBean();
            JMX2JMXBeanImpl proposed = (JMX2JMXBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("NamingImpl")) {
                  original.setNamingImpl(proposed.getNamingImpl());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("ServiceURL")) {
                  original.setServiceURL(proposed.getServiceURL());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else {
                  super.applyPropertyUpdate(event, update);
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            JMX2JMXBeanImpl copy = (JMX2JMXBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("NamingImpl")) && this.bean.isNamingImplSet()) {
               copy.setNamingImpl(this.bean.getNamingImpl());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceURL")) && this.bean.isServiceURLSet()) {
               copy.setServiceURL(this.bean.getServiceURL());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
      }
   }
}

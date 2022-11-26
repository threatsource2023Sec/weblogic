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

public class LocalJMXBeanImpl extends JMXBeanImpl implements LocalJMXBean, Serializable {
   private boolean _EnableLogMBean;
   private boolean _EnableRuntimeMBean;
   private String _MBeanServerStrategy;
   private static SchemaHelper2 _schemaHelper;

   public LocalJMXBeanImpl() {
      this._initializeProperty(-1);
   }

   public LocalJMXBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LocalJMXBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getMBeanServerStrategy() {
      return this._MBeanServerStrategy;
   }

   public boolean isMBeanServerStrategyInherited() {
      return false;
   }

   public boolean isMBeanServerStrategySet() {
      return this._isSet(6);
   }

   public void setMBeanServerStrategy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MBeanServerStrategy;
      this._MBeanServerStrategy = param0;
      this._postSet(6, _oldVal, param0);
   }

   public boolean getEnableLogMBean() {
      return this._EnableLogMBean;
   }

   public boolean isEnableLogMBeanInherited() {
      return false;
   }

   public boolean isEnableLogMBeanSet() {
      return this._isSet(7);
   }

   public void setEnableLogMBean(boolean param0) {
      boolean _oldVal = this._EnableLogMBean;
      this._EnableLogMBean = param0;
      this._postSet(7, _oldVal, param0);
   }

   public boolean getEnableRuntimeMBean() {
      return this._EnableRuntimeMBean;
   }

   public boolean isEnableRuntimeMBeanInherited() {
      return false;
   }

   public boolean isEnableRuntimeMBeanSet() {
      return this._isSet(8);
   }

   public void setEnableRuntimeMBean(boolean param0) {
      boolean _oldVal = this._EnableRuntimeMBean;
      this._EnableRuntimeMBean = param0;
      this._postSet(8, _oldVal, param0);
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
         idx = 7;
      }

      try {
         switch (idx) {
            case 7:
               this._EnableLogMBean = false;
               if (initOne) {
                  break;
               }
            case 8:
               this._EnableRuntimeMBean = false;
               if (initOne) {
                  break;
               }
            case 6:
               this._MBeanServerStrategy = null;
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

   public static class SchemaHelper2 extends JMXBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 14:
               if (s.equals("EnableLogMBean")) {
                  return 7;
               }
               break;
            case 18:
               if (s.equals("EnableRuntimeMBean")) {
                  return 8;
               }
               break;
            case 19:
               if (s.equals("MBeanServerStrategy")) {
                  return 6;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new NoneJMXBeanImpl.SchemaHelper2();
            case 1:
               return new SchemaHelper2();
            case 2:
               return new GUIJMXBeanImpl.SchemaHelper2();
            case 3:
               return new JMX2JMXBeanImpl.SchemaHelper2();
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
            case 6:
               return "MBeanServerStrategy";
            case 7:
               return "EnableLogMBean";
            case 8:
               return "EnableRuntimeMBean";
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends JMXBeanImpl.Helper {
      private LocalJMXBeanImpl bean;

      protected Helper(LocalJMXBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 6:
               return "MBeanServerStrategy";
            case 7:
               return "EnableLogMBean";
            case 8:
               return "EnableRuntimeMBean";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EnableLogMBean")) {
            return 7;
         } else if (propName.equals("EnableRuntimeMBean")) {
            return 8;
         } else {
            return propName.equals("MBeanServerStrategy") ? 6 : super.getPropertyIndex(propName);
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
            if (this.bean.isEnableLogMBeanSet()) {
               buf.append("EnableLogMBean");
               buf.append(String.valueOf(this.bean.getEnableLogMBean()));
            }

            if (this.bean.isEnableRuntimeMBeanSet()) {
               buf.append("EnableRuntimeMBean");
               buf.append(String.valueOf(this.bean.getEnableRuntimeMBean()));
            }

            if (this.bean.isMBeanServerStrategySet()) {
               buf.append("MBeanServerStrategy");
               buf.append(String.valueOf(this.bean.getMBeanServerStrategy()));
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
            LocalJMXBeanImpl otherTyped = (LocalJMXBeanImpl)other;
            this.computeDiff("EnableLogMBean", this.bean.getEnableLogMBean(), otherTyped.getEnableLogMBean(), false);
            this.computeDiff("EnableRuntimeMBean", this.bean.getEnableRuntimeMBean(), otherTyped.getEnableRuntimeMBean(), false);
            this.computeDiff("MBeanServerStrategy", this.bean.getMBeanServerStrategy(), otherTyped.getMBeanServerStrategy(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LocalJMXBeanImpl original = (LocalJMXBeanImpl)event.getSourceBean();
            LocalJMXBeanImpl proposed = (LocalJMXBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("EnableLogMBean")) {
                  original.setEnableLogMBean(proposed.getEnableLogMBean());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("EnableRuntimeMBean")) {
                  original.setEnableRuntimeMBean(proposed.getEnableRuntimeMBean());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("MBeanServerStrategy")) {
                  original.setMBeanServerStrategy(proposed.getMBeanServerStrategy());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
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
            LocalJMXBeanImpl copy = (LocalJMXBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EnableLogMBean")) && this.bean.isEnableLogMBeanSet()) {
               copy.setEnableLogMBean(this.bean.getEnableLogMBean());
            }

            if ((excludeProps == null || !excludeProps.contains("EnableRuntimeMBean")) && this.bean.isEnableRuntimeMBeanSet()) {
               copy.setEnableRuntimeMBean(this.bean.getEnableRuntimeMBean());
            }

            if ((excludeProps == null || !excludeProps.contains("MBeanServerStrategy")) && this.bean.isMBeanServerStrategySet()) {
               copy.setMBeanServerStrategy(this.bean.getMBeanServerStrategy());
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

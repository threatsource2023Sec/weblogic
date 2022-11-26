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

public class MX4J1JMXBeanImpl extends LocalJMXBeanImpl implements MX4J1JMXBean, Serializable {
   private String _Host;
   private String _JNDIName;
   private String _Port;
   private static SchemaHelper2 _schemaHelper;

   public MX4J1JMXBeanImpl() {
      this._initializeProperty(-1);
   }

   public MX4J1JMXBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MX4J1JMXBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getHost() {
      return this._Host;
   }

   public boolean isHostInherited() {
      return false;
   }

   public boolean isHostSet() {
      return this._isSet(9);
   }

   public void setHost(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Host;
      this._Host = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getPort() {
      return this._Port;
   }

   public boolean isPortInherited() {
      return false;
   }

   public boolean isPortSet() {
      return this._isSet(10);
   }

   public void setPort(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Port;
      this._Port = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getJNDIName() {
      return this._JNDIName;
   }

   public boolean isJNDINameInherited() {
      return false;
   }

   public boolean isJNDINameSet() {
      return this._isSet(11);
   }

   public void setJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JNDIName;
      this._JNDIName = param0;
      this._postSet(11, _oldVal, param0);
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
               this._Host = "localhost";
               if (initOne) {
                  break;
               }
            case 11:
               this._JNDIName = "jrmp";
               if (initOne) {
                  break;
               }
            case 10:
               this._Port = "1099";
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
            case 4:
               if (s.equals("Host")) {
                  return 9;
               }

               if (s.equals("Port")) {
                  return 10;
               }
               break;
            case 8:
               if (s.equals("JNDIName")) {
                  return 11;
               }
         }

         return super.getPropertyIndex(s);
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
               return new JMX2JMXBeanImpl.SchemaHelper2();
            case 4:
               return new SchemaHelper2();
            case 5:
               return new WLS81JMXBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 9:
               return "Host";
            case 10:
               return "Port";
            case 11:
               return "JNDIName";
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
            case 11:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends LocalJMXBeanImpl.Helper {
      private MX4J1JMXBeanImpl bean;

      protected Helper(MX4J1JMXBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 9:
               return "Host";
            case 10:
               return "Port";
            case 11:
               return "JNDIName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Host")) {
            return 9;
         } else if (propName.equals("JNDIName")) {
            return 11;
         } else {
            return propName.equals("Port") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isHostSet()) {
               buf.append("Host");
               buf.append(String.valueOf(this.bean.getHost()));
            }

            if (this.bean.isJNDINameSet()) {
               buf.append("JNDIName");
               buf.append(String.valueOf(this.bean.getJNDIName()));
            }

            if (this.bean.isPortSet()) {
               buf.append("Port");
               buf.append(String.valueOf(this.bean.getPort()));
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
            MX4J1JMXBeanImpl otherTyped = (MX4J1JMXBeanImpl)other;
            this.computeDiff("Host", this.bean.getHost(), otherTyped.getHost(), false);
            this.computeDiff("JNDIName", this.bean.getJNDIName(), otherTyped.getJNDIName(), false);
            this.computeDiff("Port", this.bean.getPort(), otherTyped.getPort(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MX4J1JMXBeanImpl original = (MX4J1JMXBeanImpl)event.getSourceBean();
            MX4J1JMXBeanImpl proposed = (MX4J1JMXBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Host")) {
                  original.setHost(proposed.getHost());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("JNDIName")) {
                  original.setJNDIName(proposed.getJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Port")) {
                  original.setPort(proposed.getPort());
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
            MX4J1JMXBeanImpl copy = (MX4J1JMXBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Host")) && this.bean.isHostSet()) {
               copy.setHost(this.bean.getHost());
            }

            if ((excludeProps == null || !excludeProps.contains("JNDIName")) && this.bean.isJNDINameSet()) {
               copy.setJNDIName(this.bean.getJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("Port")) && this.bean.isPortSet()) {
               copy.setPort(this.bean.getPort());
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

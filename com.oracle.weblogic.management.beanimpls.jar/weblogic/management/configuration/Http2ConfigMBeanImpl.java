package weblogic.management.configuration;

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
import weblogic.utils.collections.CombinedIterator;

public class Http2ConfigMBeanImpl extends ConfigurationMBeanImpl implements Http2ConfigMBean, Serializable {
   private int _HeaderTableSize;
   private int _InitialWindowSize;
   private int _MaxConcurrentStreams;
   private int _MaxFrameSize;
   private int _MaxHeaderListSize;
   private static SchemaHelper2 _schemaHelper;

   public Http2ConfigMBeanImpl() {
      this._initializeProperty(-1);
   }

   public Http2ConfigMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public Http2ConfigMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getHeaderTableSize() {
      return this._HeaderTableSize;
   }

   public boolean isHeaderTableSizeInherited() {
      return false;
   }

   public boolean isHeaderTableSizeSet() {
      return this._isSet(10);
   }

   public void setHeaderTableSize(int param0) {
      int _oldVal = this._HeaderTableSize;
      this._HeaderTableSize = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getMaxConcurrentStreams() {
      return this._MaxConcurrentStreams;
   }

   public boolean isMaxConcurrentStreamsInherited() {
      return false;
   }

   public boolean isMaxConcurrentStreamsSet() {
      return this._isSet(11);
   }

   public void setMaxConcurrentStreams(int param0) {
      int _oldVal = this._MaxConcurrentStreams;
      this._MaxConcurrentStreams = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getInitialWindowSize() {
      return this._InitialWindowSize;
   }

   public boolean isInitialWindowSizeInherited() {
      return false;
   }

   public boolean isInitialWindowSizeSet() {
      return this._isSet(12);
   }

   public void setInitialWindowSize(int param0) {
      int _oldVal = this._InitialWindowSize;
      this._InitialWindowSize = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getMaxFrameSize() {
      return this._MaxFrameSize;
   }

   public boolean isMaxFrameSizeInherited() {
      return false;
   }

   public boolean isMaxFrameSizeSet() {
      return this._isSet(13);
   }

   public void setMaxFrameSize(int param0) {
      int _oldVal = this._MaxFrameSize;
      this._MaxFrameSize = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getMaxHeaderListSize() {
      return this._MaxHeaderListSize;
   }

   public boolean isMaxHeaderListSizeInherited() {
      return false;
   }

   public boolean isMaxHeaderListSizeSet() {
      return this._isSet(14);
   }

   public void setMaxHeaderListSize(int param0) {
      int _oldVal = this._MaxHeaderListSize;
      this._MaxHeaderListSize = param0;
      this._postSet(14, _oldVal, param0);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._HeaderTableSize = 4096;
               if (initOne) {
                  break;
               }
            case 12:
               this._InitialWindowSize = 65535;
               if (initOne) {
                  break;
               }
            case 11:
               this._MaxConcurrentStreams = 300;
               if (initOne) {
                  break;
               }
            case 13:
               this._MaxFrameSize = 16384;
               if (initOne) {
                  break;
               }
            case 14:
               this._MaxHeaderListSize = Integer.MAX_VALUE;
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

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "Http2Config";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("HeaderTableSize")) {
         oldVal = this._HeaderTableSize;
         this._HeaderTableSize = (Integer)v;
         this._postSet(10, oldVal, this._HeaderTableSize);
      } else if (name.equals("InitialWindowSize")) {
         oldVal = this._InitialWindowSize;
         this._InitialWindowSize = (Integer)v;
         this._postSet(12, oldVal, this._InitialWindowSize);
      } else if (name.equals("MaxConcurrentStreams")) {
         oldVal = this._MaxConcurrentStreams;
         this._MaxConcurrentStreams = (Integer)v;
         this._postSet(11, oldVal, this._MaxConcurrentStreams);
      } else if (name.equals("MaxFrameSize")) {
         oldVal = this._MaxFrameSize;
         this._MaxFrameSize = (Integer)v;
         this._postSet(13, oldVal, this._MaxFrameSize);
      } else if (name.equals("MaxHeaderListSize")) {
         oldVal = this._MaxHeaderListSize;
         this._MaxHeaderListSize = (Integer)v;
         this._postSet(14, oldVal, this._MaxHeaderListSize);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("HeaderTableSize")) {
         return new Integer(this._HeaderTableSize);
      } else if (name.equals("InitialWindowSize")) {
         return new Integer(this._InitialWindowSize);
      } else if (name.equals("MaxConcurrentStreams")) {
         return new Integer(this._MaxConcurrentStreams);
      } else if (name.equals("MaxFrameSize")) {
         return new Integer(this._MaxFrameSize);
      } else {
         return name.equals("MaxHeaderListSize") ? new Integer(this._MaxHeaderListSize) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 14:
               if (s.equals("max-frame-size")) {
                  return 13;
               }
            case 15:
            case 16:
            case 18:
            case 21:
            default:
               break;
            case 17:
               if (s.equals("header-table-size")) {
                  return 10;
               }
               break;
            case 19:
               if (s.equals("initial-window-size")) {
                  return 12;
               }
               break;
            case 20:
               if (s.equals("max-header-list-size")) {
                  return 14;
               }
               break;
            case 22:
               if (s.equals("max-concurrent-streams")) {
                  return 11;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "header-table-size";
            case 11:
               return "max-concurrent-streams";
            case 12:
               return "initial-window-size";
            case 13:
               return "max-frame-size";
            case 14:
               return "max-header-list-size";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private Http2ConfigMBeanImpl bean;

      protected Helper(Http2ConfigMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "HeaderTableSize";
            case 11:
               return "MaxConcurrentStreams";
            case 12:
               return "InitialWindowSize";
            case 13:
               return "MaxFrameSize";
            case 14:
               return "MaxHeaderListSize";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("HeaderTableSize")) {
            return 10;
         } else if (propName.equals("InitialWindowSize")) {
            return 12;
         } else if (propName.equals("MaxConcurrentStreams")) {
            return 11;
         } else if (propName.equals("MaxFrameSize")) {
            return 13;
         } else {
            return propName.equals("MaxHeaderListSize") ? 14 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
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
            if (this.bean.isHeaderTableSizeSet()) {
               buf.append("HeaderTableSize");
               buf.append(String.valueOf(this.bean.getHeaderTableSize()));
            }

            if (this.bean.isInitialWindowSizeSet()) {
               buf.append("InitialWindowSize");
               buf.append(String.valueOf(this.bean.getInitialWindowSize()));
            }

            if (this.bean.isMaxConcurrentStreamsSet()) {
               buf.append("MaxConcurrentStreams");
               buf.append(String.valueOf(this.bean.getMaxConcurrentStreams()));
            }

            if (this.bean.isMaxFrameSizeSet()) {
               buf.append("MaxFrameSize");
               buf.append(String.valueOf(this.bean.getMaxFrameSize()));
            }

            if (this.bean.isMaxHeaderListSizeSet()) {
               buf.append("MaxHeaderListSize");
               buf.append(String.valueOf(this.bean.getMaxHeaderListSize()));
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
            Http2ConfigMBeanImpl otherTyped = (Http2ConfigMBeanImpl)other;
            this.computeDiff("HeaderTableSize", this.bean.getHeaderTableSize(), otherTyped.getHeaderTableSize(), true);
            this.computeDiff("InitialWindowSize", this.bean.getInitialWindowSize(), otherTyped.getInitialWindowSize(), true);
            this.computeDiff("MaxConcurrentStreams", this.bean.getMaxConcurrentStreams(), otherTyped.getMaxConcurrentStreams(), true);
            this.computeDiff("MaxFrameSize", this.bean.getMaxFrameSize(), otherTyped.getMaxFrameSize(), true);
            this.computeDiff("MaxHeaderListSize", this.bean.getMaxHeaderListSize(), otherTyped.getMaxHeaderListSize(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            Http2ConfigMBeanImpl original = (Http2ConfigMBeanImpl)event.getSourceBean();
            Http2ConfigMBeanImpl proposed = (Http2ConfigMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("HeaderTableSize")) {
                  original.setHeaderTableSize(proposed.getHeaderTableSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("InitialWindowSize")) {
                  original.setInitialWindowSize(proposed.getInitialWindowSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("MaxConcurrentStreams")) {
                  original.setMaxConcurrentStreams(proposed.getMaxConcurrentStreams());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("MaxFrameSize")) {
                  original.setMaxFrameSize(proposed.getMaxFrameSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("MaxHeaderListSize")) {
                  original.setMaxHeaderListSize(proposed.getMaxHeaderListSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
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
            Http2ConfigMBeanImpl copy = (Http2ConfigMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("HeaderTableSize")) && this.bean.isHeaderTableSizeSet()) {
               copy.setHeaderTableSize(this.bean.getHeaderTableSize());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialWindowSize")) && this.bean.isInitialWindowSizeSet()) {
               copy.setInitialWindowSize(this.bean.getInitialWindowSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxConcurrentStreams")) && this.bean.isMaxConcurrentStreamsSet()) {
               copy.setMaxConcurrentStreams(this.bean.getMaxConcurrentStreams());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxFrameSize")) && this.bean.isMaxFrameSizeSet()) {
               copy.setMaxFrameSize(this.bean.getMaxFrameSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxHeaderListSize")) && this.bean.isMaxHeaderListSizeSet()) {
               copy.setMaxHeaderListSize(this.bean.getMaxHeaderListSize());
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

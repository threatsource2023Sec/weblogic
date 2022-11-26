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

public class CacheExpirationMBeanImpl extends ConfigurationMBeanImpl implements CacheExpirationMBean, Serializable {
   private long _IdleTime;
   private long _TTL;
   private static SchemaHelper2 _schemaHelper;

   public CacheExpirationMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CacheExpirationMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CacheExpirationMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public long getTTL() {
      return this._TTL;
   }

   public boolean isTTLInherited() {
      return false;
   }

   public boolean isTTLSet() {
      return this._isSet(10);
   }

   public void setTTL(long param0) {
      long _oldVal = this._TTL;
      this._TTL = param0;
      this._postSet(10, _oldVal, param0);
   }

   public long getIdleTime() {
      return this._IdleTime;
   }

   public boolean isIdleTimeInherited() {
      return false;
   }

   public boolean isIdleTimeSet() {
      return this._isSet(11);
   }

   public void setIdleTime(long param0) {
      long _oldVal = this._IdleTime;
      this._IdleTime = param0;
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
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._IdleTime = 0L;
               if (initOne) {
                  break;
               }
            case 10:
               this._TTL = 0L;
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
      return "CacheExpiration";
   }

   public void putValue(String name, Object v) {
      long oldVal;
      if (name.equals("IdleTime")) {
         oldVal = this._IdleTime;
         this._IdleTime = (Long)v;
         this._postSet(11, oldVal, this._IdleTime);
      } else if (name.equals("TTL")) {
         oldVal = this._TTL;
         this._TTL = (Long)v;
         this._postSet(10, oldVal, this._TTL);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("IdleTime")) {
         return new Long(this._IdleTime);
      } else {
         return name.equals("TTL") ? new Long(this._TTL) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("ttl")) {
                  return 10;
               }
               break;
            case 9:
               if (s.equals("idle-time")) {
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
               return "ttl";
            case 11:
               return "idle-time";
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
      private CacheExpirationMBeanImpl bean;

      protected Helper(CacheExpirationMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "TTL";
            case 11:
               return "IdleTime";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("IdleTime")) {
            return 11;
         } else {
            return propName.equals("TTL") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isIdleTimeSet()) {
               buf.append("IdleTime");
               buf.append(String.valueOf(this.bean.getIdleTime()));
            }

            if (this.bean.isTTLSet()) {
               buf.append("TTL");
               buf.append(String.valueOf(this.bean.getTTL()));
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
            CacheExpirationMBeanImpl otherTyped = (CacheExpirationMBeanImpl)other;
            this.computeDiff("IdleTime", this.bean.getIdleTime(), otherTyped.getIdleTime(), true);
            this.computeDiff("TTL", this.bean.getTTL(), otherTyped.getTTL(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CacheExpirationMBeanImpl original = (CacheExpirationMBeanImpl)event.getSourceBean();
            CacheExpirationMBeanImpl proposed = (CacheExpirationMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("IdleTime")) {
                  original.setIdleTime(proposed.getIdleTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("TTL")) {
                  original.setTTL(proposed.getTTL());
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
            CacheExpirationMBeanImpl copy = (CacheExpirationMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("IdleTime")) && this.bean.isIdleTimeSet()) {
               copy.setIdleTime(this.bean.getIdleTime());
            }

            if ((excludeProps == null || !excludeProps.contains("TTL")) && this.bean.isTTLSet()) {
               copy.setTTL(this.bean.getTTL());
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

package weblogic.management.security.credentials;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.commo.AbstractCommoConfigurationBean;
import weblogic.utils.collections.CombinedIterator;

public class CredentialCacheMBeanImpl extends AbstractCommoConfigurationBean implements CredentialCacheMBean, Serializable {
   private int _CredentialCacheTTL;
   private boolean _CredentialCachingEnabled;
   private int _CredentialsCacheSize;
   private static SchemaHelper2 _schemaHelper;

   public CredentialCacheMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CredentialCacheMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CredentialCacheMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isCredentialCachingEnabled() {
      return this._CredentialCachingEnabled;
   }

   public boolean isCredentialCachingEnabledInherited() {
      return false;
   }

   public boolean isCredentialCachingEnabledSet() {
      return this._isSet(2);
   }

   public void setCredentialCachingEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._CredentialCachingEnabled;
      this._CredentialCachingEnabled = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getCredentialsCacheSize() {
      return this._CredentialsCacheSize;
   }

   public boolean isCredentialsCacheSizeInherited() {
      return false;
   }

   public boolean isCredentialsCacheSizeSet() {
      return this._isSet(3);
   }

   public void setCredentialsCacheSize(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._CredentialsCacheSize;
      this._CredentialsCacheSize = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getCredentialCacheTTL() {
      return this._CredentialCacheTTL;
   }

   public boolean isCredentialCacheTTLInherited() {
      return false;
   }

   public boolean isCredentialCacheTTLSet() {
      return this._isSet(4);
   }

   public void setCredentialCacheTTL(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._CredentialCacheTTL;
      this._CredentialCacheTTL = param0;
      this._postSet(4, _oldVal, param0);
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._CredentialCacheTTL = 600;
               if (initOne) {
                  break;
               }
            case 3:
               this._CredentialsCacheSize = 100;
               if (initOne) {
                  break;
               }
            case 2:
               this._CredentialCachingEnabled = false;
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
      return "http://xmlns.oracle.com/weblogic/1.0/security.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/security";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String wls_getInterfaceClassName() {
      return "weblogic.management.security.credentials.CredentialCacheMBean";
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 19:
               if (s.equals("credential-cachettl")) {
                  return 4;
               }
               break;
            case 22:
               if (s.equals("credentials-cache-size")) {
                  return 3;
               }
               break;
            case 26:
               if (s.equals("credential-caching-enabled")) {
                  return 2;
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
            case 2:
               return "credential-caching-enabled";
            case 3:
               return "credentials-cache-size";
            case 4:
               return "credential-cachettl";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractCommoConfigurationBean.Helper {
      private CredentialCacheMBeanImpl bean;

      protected Helper(CredentialCacheMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "CredentialCachingEnabled";
            case 3:
               return "CredentialsCacheSize";
            case 4:
               return "CredentialCacheTTL";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CredentialCacheTTL")) {
            return 4;
         } else if (propName.equals("CredentialsCacheSize")) {
            return 3;
         } else {
            return propName.equals("CredentialCachingEnabled") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isCredentialCacheTTLSet()) {
               buf.append("CredentialCacheTTL");
               buf.append(String.valueOf(this.bean.getCredentialCacheTTL()));
            }

            if (this.bean.isCredentialsCacheSizeSet()) {
               buf.append("CredentialsCacheSize");
               buf.append(String.valueOf(this.bean.getCredentialsCacheSize()));
            }

            if (this.bean.isCredentialCachingEnabledSet()) {
               buf.append("CredentialCachingEnabled");
               buf.append(String.valueOf(this.bean.isCredentialCachingEnabled()));
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
            CredentialCacheMBeanImpl otherTyped = (CredentialCacheMBeanImpl)other;
            this.computeDiff("CredentialCacheTTL", this.bean.getCredentialCacheTTL(), otherTyped.getCredentialCacheTTL(), false);
            this.computeDiff("CredentialsCacheSize", this.bean.getCredentialsCacheSize(), otherTyped.getCredentialsCacheSize(), false);
            this.computeDiff("CredentialCachingEnabled", this.bean.isCredentialCachingEnabled(), otherTyped.isCredentialCachingEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CredentialCacheMBeanImpl original = (CredentialCacheMBeanImpl)event.getSourceBean();
            CredentialCacheMBeanImpl proposed = (CredentialCacheMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CredentialCacheTTL")) {
                  original.setCredentialCacheTTL(proposed.getCredentialCacheTTL());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("CredentialsCacheSize")) {
                  original.setCredentialsCacheSize(proposed.getCredentialsCacheSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("CredentialCachingEnabled")) {
                  original.setCredentialCachingEnabled(proposed.isCredentialCachingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            CredentialCacheMBeanImpl copy = (CredentialCacheMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CredentialCacheTTL")) && this.bean.isCredentialCacheTTLSet()) {
               copy.setCredentialCacheTTL(this.bean.getCredentialCacheTTL());
            }

            if ((excludeProps == null || !excludeProps.contains("CredentialsCacheSize")) && this.bean.isCredentialsCacheSizeSet()) {
               copy.setCredentialsCacheSize(this.bean.getCredentialsCacheSize());
            }

            if ((excludeProps == null || !excludeProps.contains("CredentialCachingEnabled")) && this.bean.isCredentialCachingEnabledSet()) {
               copy.setCredentialCachingEnabled(this.bean.isCredentialCachingEnabled());
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

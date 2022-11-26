package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class ForeignJNDILinkOverrideMBeanImpl extends ConfigurationMBeanImpl implements ForeignJNDILinkOverrideMBean, Serializable {
   private String _LocalJNDIName;
   private String _RemoteJNDIName;
   private static SchemaHelper2 _schemaHelper;

   public ForeignJNDILinkOverrideMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ForeignJNDILinkOverrideMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ForeignJNDILinkOverrideMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getLocalJNDIName() {
      return this._LocalJNDIName;
   }

   public boolean isLocalJNDINameInherited() {
      return false;
   }

   public boolean isLocalJNDINameSet() {
      return this._isSet(10);
   }

   public void setLocalJNDIName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("LocalJNDIName", param0);
      String _oldVal = this._LocalJNDIName;
      this._LocalJNDIName = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getRemoteJNDIName() {
      return this._RemoteJNDIName;
   }

   public boolean isRemoteJNDINameInherited() {
      return false;
   }

   public boolean isRemoteJNDINameSet() {
      return this._isSet(11);
   }

   public void setRemoteJNDIName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("RemoteJNDIName", param0);
      String _oldVal = this._RemoteJNDIName;
      this._RemoteJNDIName = param0;
      this._postSet(11, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("LocalJNDIName", this.isLocalJNDINameSet());
      LegalChecks.checkIsSet("RemoteJNDIName", this.isRemoteJNDINameSet());
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
               this._LocalJNDIName = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._RemoteJNDIName = null;
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
      return "ForeignJNDILinkOverride";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("LocalJNDIName")) {
         oldVal = this._LocalJNDIName;
         this._LocalJNDIName = (String)v;
         this._postSet(10, oldVal, this._LocalJNDIName);
      } else if (name.equals("RemoteJNDIName")) {
         oldVal = this._RemoteJNDIName;
         this._RemoteJNDIName = (String)v;
         this._postSet(11, oldVal, this._RemoteJNDIName);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("LocalJNDIName")) {
         return this._LocalJNDIName;
      } else {
         return name.equals("RemoteJNDIName") ? this._RemoteJNDIName : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 15:
               if (s.equals("local-jndi-name")) {
                  return 10;
               }
               break;
            case 16:
               if (s.equals("remote-jndi-name")) {
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
               return "local-jndi-name";
            case 11:
               return "remote-jndi-name";
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
      private ForeignJNDILinkOverrideMBeanImpl bean;

      protected Helper(ForeignJNDILinkOverrideMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "LocalJNDIName";
            case 11:
               return "RemoteJNDIName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("LocalJNDIName")) {
            return 10;
         } else {
            return propName.equals("RemoteJNDIName") ? 11 : super.getPropertyIndex(propName);
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
            if (this.bean.isLocalJNDINameSet()) {
               buf.append("LocalJNDIName");
               buf.append(String.valueOf(this.bean.getLocalJNDIName()));
            }

            if (this.bean.isRemoteJNDINameSet()) {
               buf.append("RemoteJNDIName");
               buf.append(String.valueOf(this.bean.getRemoteJNDIName()));
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
            ForeignJNDILinkOverrideMBeanImpl otherTyped = (ForeignJNDILinkOverrideMBeanImpl)other;
            this.computeDiff("LocalJNDIName", this.bean.getLocalJNDIName(), otherTyped.getLocalJNDIName(), true);
            this.computeDiff("RemoteJNDIName", this.bean.getRemoteJNDIName(), otherTyped.getRemoteJNDIName(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ForeignJNDILinkOverrideMBeanImpl original = (ForeignJNDILinkOverrideMBeanImpl)event.getSourceBean();
            ForeignJNDILinkOverrideMBeanImpl proposed = (ForeignJNDILinkOverrideMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("LocalJNDIName")) {
                  original.setLocalJNDIName(proposed.getLocalJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("RemoteJNDIName")) {
                  original.setRemoteJNDIName(proposed.getRemoteJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            ForeignJNDILinkOverrideMBeanImpl copy = (ForeignJNDILinkOverrideMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("LocalJNDIName")) && this.bean.isLocalJNDINameSet()) {
               copy.setLocalJNDIName(this.bean.getLocalJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteJNDIName")) && this.bean.isRemoteJNDINameSet()) {
               copy.setRemoteJNDIName(this.bean.getRemoteJNDIName());
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

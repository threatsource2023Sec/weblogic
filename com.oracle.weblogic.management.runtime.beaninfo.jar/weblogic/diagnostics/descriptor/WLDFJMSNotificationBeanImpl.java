package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class WLDFJMSNotificationBeanImpl extends WLDFNotificationBeanImpl implements WLDFJMSNotificationBean, Serializable {
   private String _ConnectionFactoryJNDIName;
   private String _DestinationJNDIName;
   private static SchemaHelper2 _schemaHelper;

   public WLDFJMSNotificationBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFJMSNotificationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFJMSNotificationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDestinationJNDIName() {
      return this._DestinationJNDIName;
   }

   public boolean isDestinationJNDINameInherited() {
      return false;
   }

   public boolean isDestinationJNDINameSet() {
      return this._isSet(4);
   }

   public void setDestinationJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("DestinationJNDIName", param0);
      LegalChecks.checkNonNull("DestinationJNDIName", param0);
      String _oldVal = this._DestinationJNDIName;
      this._DestinationJNDIName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getConnectionFactoryJNDIName() {
      return this._ConnectionFactoryJNDIName;
   }

   public boolean isConnectionFactoryJNDINameInherited() {
      return false;
   }

   public boolean isConnectionFactoryJNDINameSet() {
      return this._isSet(5);
   }

   public void setConnectionFactoryJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("ConnectionFactoryJNDIName", param0);
      LegalChecks.checkNonNull("ConnectionFactoryJNDIName", param0);
      String _oldVal = this._ConnectionFactoryJNDIName;
      this._ConnectionFactoryJNDIName = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("DestinationJNDIName", this.isDestinationJNDINameSet());
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
         idx = 5;
      }

      try {
         switch (idx) {
            case 5:
               this._ConnectionFactoryJNDIName = "weblogic.jms.ConnectionFactory";
               if (initOne) {
                  break;
               }
            case 4:
               this._DestinationJNDIName = null;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/1.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("ConnectionFactoryJNDIName", "weblogic.jms.ConnectionFactory");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property ConnectionFactoryJNDIName in WLDFJMSNotificationBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonEmptyString("ConnectionFactoryJNDIName", "weblogic.jms.ConnectionFactory");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is zero-length. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-zero-length value on @default annotation. Refer annotation legalZeroLength on property ConnectionFactoryJNDIName in WLDFJMSNotificationBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends WLDFNotificationBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 21:
               if (s.equals("destination-jndi-name")) {
                  return 4;
               }
               break;
            case 28:
               if (s.equals("connection-factory-jndi-name")) {
                  return 5;
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
            case 4:
               return "destination-jndi-name";
            case 5:
               return "connection-factory-jndi-name";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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

   protected static class Helper extends WLDFNotificationBeanImpl.Helper {
      private WLDFJMSNotificationBeanImpl bean;

      protected Helper(WLDFJMSNotificationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "DestinationJNDIName";
            case 5:
               return "ConnectionFactoryJNDIName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionFactoryJNDIName")) {
            return 5;
         } else {
            return propName.equals("DestinationJNDIName") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isConnectionFactoryJNDINameSet()) {
               buf.append("ConnectionFactoryJNDIName");
               buf.append(String.valueOf(this.bean.getConnectionFactoryJNDIName()));
            }

            if (this.bean.isDestinationJNDINameSet()) {
               buf.append("DestinationJNDIName");
               buf.append(String.valueOf(this.bean.getDestinationJNDIName()));
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
            WLDFJMSNotificationBeanImpl otherTyped = (WLDFJMSNotificationBeanImpl)other;
            this.computeDiff("ConnectionFactoryJNDIName", this.bean.getConnectionFactoryJNDIName(), otherTyped.getConnectionFactoryJNDIName(), true);
            this.computeDiff("DestinationJNDIName", this.bean.getDestinationJNDIName(), otherTyped.getDestinationJNDIName(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFJMSNotificationBeanImpl original = (WLDFJMSNotificationBeanImpl)event.getSourceBean();
            WLDFJMSNotificationBeanImpl proposed = (WLDFJMSNotificationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionFactoryJNDIName")) {
                  original.setConnectionFactoryJNDIName(proposed.getConnectionFactoryJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("DestinationJNDIName")) {
                  original.setDestinationJNDIName(proposed.getDestinationJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            WLDFJMSNotificationBeanImpl copy = (WLDFJMSNotificationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryJNDIName")) && this.bean.isConnectionFactoryJNDINameSet()) {
               copy.setConnectionFactoryJNDIName(this.bean.getConnectionFactoryJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("DestinationJNDIName")) && this.bean.isDestinationJNDINameSet()) {
               copy.setDestinationJNDIName(this.bean.getDestinationJNDIName());
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

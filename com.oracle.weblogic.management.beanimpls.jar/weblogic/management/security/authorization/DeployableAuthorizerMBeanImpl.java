package weblogic.management.security.authorization;

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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class DeployableAuthorizerMBeanImpl extends AuthorizerMBeanImpl implements DeployableAuthorizerMBean, Serializable {
   private boolean _PolicyDeploymentEnabled;
   private static SchemaHelper2 _schemaHelper;

   public DeployableAuthorizerMBeanImpl() {
      this._initializeProperty(-1);
   }

   public DeployableAuthorizerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DeployableAuthorizerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isPolicyDeploymentEnabled() {
      return this._PolicyDeploymentEnabled;
   }

   public boolean isPolicyDeploymentEnabledInherited() {
      return false;
   }

   public boolean isPolicyDeploymentEnabledSet() {
      return this._isSet(8);
   }

   public void setPolicyDeploymentEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._PolicyDeploymentEnabled;
      this._PolicyDeploymentEnabled = param0;
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
         idx = 8;
      }

      try {
         switch (idx) {
            case 8:
               this._PolicyDeploymentEnabled = true;
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
      return "weblogic.management.security.authorization.DeployableAuthorizerMBean";
   }

   public static class SchemaHelper2 extends AuthorizerMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 25:
               if (s.equals("policy-deployment-enabled")) {
                  return 8;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 8:
               return "policy-deployment-enabled";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AuthorizerMBeanImpl.Helper {
      private DeployableAuthorizerMBeanImpl bean;

      protected Helper(DeployableAuthorizerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 8:
               return "PolicyDeploymentEnabled";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("PolicyDeploymentEnabled") ? 8 : super.getPropertyIndex(propName);
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
            if (this.bean.isPolicyDeploymentEnabledSet()) {
               buf.append("PolicyDeploymentEnabled");
               buf.append(String.valueOf(this.bean.isPolicyDeploymentEnabled()));
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
            DeployableAuthorizerMBeanImpl otherTyped = (DeployableAuthorizerMBeanImpl)other;
            this.computeDiff("PolicyDeploymentEnabled", this.bean.isPolicyDeploymentEnabled(), otherTyped.isPolicyDeploymentEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DeployableAuthorizerMBeanImpl original = (DeployableAuthorizerMBeanImpl)event.getSourceBean();
            DeployableAuthorizerMBeanImpl proposed = (DeployableAuthorizerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("PolicyDeploymentEnabled")) {
                  original.setPolicyDeploymentEnabled(proposed.isPolicyDeploymentEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
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
            DeployableAuthorizerMBeanImpl copy = (DeployableAuthorizerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("PolicyDeploymentEnabled")) && this.bean.isPolicyDeploymentEnabledSet()) {
               copy.setPolicyDeploymentEnabled(this.bean.isPolicyDeploymentEnabled());
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

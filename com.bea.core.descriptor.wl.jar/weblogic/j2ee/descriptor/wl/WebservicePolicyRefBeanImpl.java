package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WebservicePolicyRefBeanImpl extends AbstractDescriptorBean implements WebservicePolicyRefBean, Serializable {
   private OperationPolicyBean[] _OperationPolicy;
   private PortPolicyBean[] _PortPolicy;
   private String _RefName;
   private String _Version;
   private static SchemaHelper2 _schemaHelper;

   public WebservicePolicyRefBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WebservicePolicyRefBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WebservicePolicyRefBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void setRefName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RefName;
      this._RefName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getRefName() {
      return this._RefName;
   }

   public boolean isRefNameInherited() {
      return false;
   }

   public boolean isRefNameSet() {
      return this._isSet(0);
   }

   public void addPortPolicy(PortPolicyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         PortPolicyBean[] _new;
         if (this._isSet(1)) {
            _new = (PortPolicyBean[])((PortPolicyBean[])this._getHelper()._extendArray(this.getPortPolicy(), PortPolicyBean.class, param0));
         } else {
            _new = new PortPolicyBean[]{param0};
         }

         try {
            this.setPortPolicy(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PortPolicyBean[] getPortPolicy() {
      return this._PortPolicy;
   }

   public boolean isPortPolicyInherited() {
      return false;
   }

   public boolean isPortPolicySet() {
      return this._isSet(1);
   }

   public void removePortPolicy(PortPolicyBean param0) {
      this.destroyPortPolicy(param0);
   }

   public void setPortPolicy(PortPolicyBean[] param0) throws InvalidAttributeValueException {
      PortPolicyBean[] param0 = param0 == null ? new PortPolicyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PortPolicyBean[] _oldVal = this._PortPolicy;
      this._PortPolicy = (PortPolicyBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public PortPolicyBean createPortPolicy() {
      PortPolicyBeanImpl _val = new PortPolicyBeanImpl(this, -1);

      try {
         this.addPortPolicy(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPortPolicy(PortPolicyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         PortPolicyBean[] _old = this.getPortPolicy();
         PortPolicyBean[] _new = (PortPolicyBean[])((PortPolicyBean[])this._getHelper()._removeElement(_old, PortPolicyBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPortPolicy(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addOperationPolicy(OperationPolicyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         OperationPolicyBean[] _new;
         if (this._isSet(2)) {
            _new = (OperationPolicyBean[])((OperationPolicyBean[])this._getHelper()._extendArray(this.getOperationPolicy(), OperationPolicyBean.class, param0));
         } else {
            _new = new OperationPolicyBean[]{param0};
         }

         try {
            this.setOperationPolicy(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public OperationPolicyBean[] getOperationPolicy() {
      return this._OperationPolicy;
   }

   public boolean isOperationPolicyInherited() {
      return false;
   }

   public boolean isOperationPolicySet() {
      return this._isSet(2);
   }

   public void removeOperationPolicy(OperationPolicyBean param0) {
      this.destroyOperationPolicy(param0);
   }

   public void setOperationPolicy(OperationPolicyBean[] param0) throws InvalidAttributeValueException {
      OperationPolicyBean[] param0 = param0 == null ? new OperationPolicyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      OperationPolicyBean[] _oldVal = this._OperationPolicy;
      this._OperationPolicy = (OperationPolicyBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public OperationPolicyBean createOperationPolicy() {
      OperationPolicyBeanImpl _val = new OperationPolicyBeanImpl(this, -1);

      try {
         this.addOperationPolicy(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyOperationPolicy(OperationPolicyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         OperationPolicyBean[] _old = this.getOperationPolicy();
         OperationPolicyBean[] _new = (OperationPolicyBean[])((OperationPolicyBean[])this._getHelper()._removeElement(_old, OperationPolicyBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setOperationPolicy(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(3);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(3, _oldVal, param0);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._OperationPolicy = new OperationPolicyBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._PortPolicy = new PortPolicyBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._RefName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._Version = null;
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
      return "http://xmlns.oracle.com/weblogic/webservice-policy-ref/1.1/webservice-policy-ref.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/webservice-policy-ref";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("version")) {
                  return 3;
               }
               break;
            case 8:
               if (s.equals("ref-name")) {
                  return 0;
               }
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               break;
            case 11:
               if (s.equals("port-policy")) {
                  return 1;
               }
               break;
            case 16:
               if (s.equals("operation-policy")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new PortPolicyBeanImpl.SchemaHelper2();
            case 2:
               return new OperationPolicyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "webservice-policy-ref";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ref-name";
            case 1:
               return "port-policy";
            case 2:
               return "operation-policy";
            case 3:
               return "version";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WebservicePolicyRefBeanImpl bean;

      protected Helper(WebservicePolicyRefBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "RefName";
            case 1:
               return "PortPolicy";
            case 2:
               return "OperationPolicy";
            case 3:
               return "Version";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("OperationPolicy")) {
            return 2;
         } else if (propName.equals("PortPolicy")) {
            return 1;
         } else if (propName.equals("RefName")) {
            return 0;
         } else {
            return propName.equals("Version") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getOperationPolicy()));
         iterators.add(new ArrayIterator(this.bean.getPortPolicy()));
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
            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getOperationPolicy().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getOperationPolicy()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPortPolicy().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPortPolicy()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isRefNameSet()) {
               buf.append("RefName");
               buf.append(String.valueOf(this.bean.getRefName()));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            WebservicePolicyRefBeanImpl otherTyped = (WebservicePolicyRefBeanImpl)other;
            this.computeChildDiff("OperationPolicy", this.bean.getOperationPolicy(), otherTyped.getOperationPolicy(), false);
            this.computeChildDiff("PortPolicy", this.bean.getPortPolicy(), otherTyped.getPortPolicy(), false);
            this.computeDiff("RefName", this.bean.getRefName(), otherTyped.getRefName(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebservicePolicyRefBeanImpl original = (WebservicePolicyRefBeanImpl)event.getSourceBean();
            WebservicePolicyRefBeanImpl proposed = (WebservicePolicyRefBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("OperationPolicy")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addOperationPolicy((OperationPolicyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeOperationPolicy((OperationPolicyBean)update.getRemovedObject());
                  }

                  if (original.getOperationPolicy() == null || original.getOperationPolicy().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("PortPolicy")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPortPolicy((PortPolicyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePortPolicy((PortPolicyBean)update.getRemovedObject());
                  }

                  if (original.getPortPolicy() == null || original.getPortPolicy().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("RefName")) {
                  original.setRefName(proposed.getRefName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            WebservicePolicyRefBeanImpl copy = (WebservicePolicyRefBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("OperationPolicy")) && this.bean.isOperationPolicySet() && !copy._isSet(2)) {
               OperationPolicyBean[] oldOperationPolicy = this.bean.getOperationPolicy();
               OperationPolicyBean[] newOperationPolicy = new OperationPolicyBean[oldOperationPolicy.length];

               for(i = 0; i < newOperationPolicy.length; ++i) {
                  newOperationPolicy[i] = (OperationPolicyBean)((OperationPolicyBean)this.createCopy((AbstractDescriptorBean)oldOperationPolicy[i], includeObsolete));
               }

               copy.setOperationPolicy(newOperationPolicy);
            }

            if ((excludeProps == null || !excludeProps.contains("PortPolicy")) && this.bean.isPortPolicySet() && !copy._isSet(1)) {
               PortPolicyBean[] oldPortPolicy = this.bean.getPortPolicy();
               PortPolicyBean[] newPortPolicy = new PortPolicyBean[oldPortPolicy.length];

               for(i = 0; i < newPortPolicy.length; ++i) {
                  newPortPolicy[i] = (PortPolicyBean)((PortPolicyBean)this.createCopy((AbstractDescriptorBean)oldPortPolicy[i], includeObsolete));
               }

               copy.setPortPolicy(newPortPolicy);
            }

            if ((excludeProps == null || !excludeProps.contains("RefName")) && this.bean.isRefNameSet()) {
               copy.setRefName(this.bean.getRefName());
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getOperationPolicy(), clazz, annotation);
         this.inferSubTree(this.bean.getPortPolicy(), clazz, annotation);
      }
   }
}

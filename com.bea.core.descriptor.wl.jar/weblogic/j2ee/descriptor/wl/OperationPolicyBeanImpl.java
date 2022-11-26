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
import weblogic.descriptor.internal.CompoundKey;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class OperationPolicyBeanImpl extends AbstractDescriptorBean implements OperationPolicyBean, Serializable {
   private String _OperationName;
   private String _ServiceLink;
   private WsPolicyBean[] _WsPolicy;
   private static SchemaHelper2 _schemaHelper;

   public OperationPolicyBeanImpl() {
      this._initializeProperty(-1);
   }

   public OperationPolicyBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public OperationPolicyBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setOperationName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._OperationName;
      this._OperationName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getOperationName() {
      return this._OperationName;
   }

   public boolean isOperationNameInherited() {
      return false;
   }

   public boolean isOperationNameSet() {
      return this._isSet(0);
   }

   public String getServiceLink() {
      return this._ServiceLink;
   }

   public boolean isServiceLinkInherited() {
      return false;
   }

   public boolean isServiceLinkSet() {
      return this._isSet(1);
   }

   public void setServiceLink(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceLink;
      this._ServiceLink = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addWsPolicy(WsPolicyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         WsPolicyBean[] _new;
         if (this._isSet(2)) {
            _new = (WsPolicyBean[])((WsPolicyBean[])this._getHelper()._extendArray(this.getWsPolicy(), WsPolicyBean.class, param0));
         } else {
            _new = new WsPolicyBean[]{param0};
         }

         try {
            this.setWsPolicy(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WsPolicyBean[] getWsPolicy() {
      return this._WsPolicy;
   }

   public boolean isWsPolicyInherited() {
      return false;
   }

   public boolean isWsPolicySet() {
      return this._isSet(2);
   }

   public void removeWsPolicy(WsPolicyBean param0) {
      this.destroyWsPolicy(param0);
   }

   public void setWsPolicy(WsPolicyBean[] param0) throws InvalidAttributeValueException {
      WsPolicyBean[] param0 = param0 == null ? new WsPolicyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WsPolicyBean[] _oldVal = this._WsPolicy;
      this._WsPolicy = (WsPolicyBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public WsPolicyBean createWsPolicy() {
      WsPolicyBeanImpl _val = new WsPolicyBeanImpl(this, -1);

      try {
         this.addWsPolicy(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWsPolicy(WsPolicyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         WsPolicyBean[] _old = this.getWsPolicy();
         WsPolicyBean[] _new = (WsPolicyBean[])((WsPolicyBean[])this._getHelper()._removeElement(_old, WsPolicyBean.class, param0));
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
               this.setWsPolicy(_new);
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

   public Object _getKey() {
      return new CompoundKey(new Object[]{this.getOperationName(), this.getServiceLink()});
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._OperationName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ServiceLink = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._WsPolicy = new WsPolicyBean[0];
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

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("ws-policy")) {
                  return 2;
               }
               break;
            case 12:
               if (s.equals("service-link")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("operation-name")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new WsPolicyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "operation-name";
            case 1:
               return "service-link";
            case 2:
               return "ws-policy";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKeyComponent(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isKeyComponent(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("operation-name");
         indices.add("service-link");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private OperationPolicyBeanImpl bean;

      protected Helper(OperationPolicyBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "OperationName";
            case 1:
               return "ServiceLink";
            case 2:
               return "WsPolicy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("OperationName")) {
            return 0;
         } else if (propName.equals("ServiceLink")) {
            return 1;
         } else {
            return propName.equals("WsPolicy") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getWsPolicy()));
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
            if (this.bean.isOperationNameSet()) {
               buf.append("OperationName");
               buf.append(String.valueOf(this.bean.getOperationName()));
            }

            if (this.bean.isServiceLinkSet()) {
               buf.append("ServiceLink");
               buf.append(String.valueOf(this.bean.getServiceLink()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getWsPolicy().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWsPolicy()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            OperationPolicyBeanImpl otherTyped = (OperationPolicyBeanImpl)other;
            this.computeDiff("OperationName", this.bean.getOperationName(), otherTyped.getOperationName(), false);
            this.computeDiff("ServiceLink", this.bean.getServiceLink(), otherTyped.getServiceLink(), false);
            this.computeChildDiff("WsPolicy", this.bean.getWsPolicy(), otherTyped.getWsPolicy(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            OperationPolicyBeanImpl original = (OperationPolicyBeanImpl)event.getSourceBean();
            OperationPolicyBeanImpl proposed = (OperationPolicyBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("OperationName")) {
                  original.setOperationName(proposed.getOperationName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ServiceLink")) {
                  original.setServiceLink(proposed.getServiceLink());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("WsPolicy")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWsPolicy((WsPolicyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWsPolicy((WsPolicyBean)update.getRemovedObject());
                  }

                  if (original.getWsPolicy() == null || original.getWsPolicy().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
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
            OperationPolicyBeanImpl copy = (OperationPolicyBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("OperationName")) && this.bean.isOperationNameSet()) {
               copy.setOperationName(this.bean.getOperationName());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceLink")) && this.bean.isServiceLinkSet()) {
               copy.setServiceLink(this.bean.getServiceLink());
            }

            if ((excludeProps == null || !excludeProps.contains("WsPolicy")) && this.bean.isWsPolicySet() && !copy._isSet(2)) {
               WsPolicyBean[] oldWsPolicy = this.bean.getWsPolicy();
               WsPolicyBean[] newWsPolicy = new WsPolicyBean[oldWsPolicy.length];

               for(int i = 0; i < newWsPolicy.length; ++i) {
                  newWsPolicy[i] = (WsPolicyBean)((WsPolicyBean)this.createCopy((AbstractDescriptorBean)oldWsPolicy[i], includeObsolete));
               }

               copy.setWsPolicy(newWsPolicy);
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
         this.inferSubTree(this.bean.getWsPolicy(), clazz, annotation);
      }
   }
}

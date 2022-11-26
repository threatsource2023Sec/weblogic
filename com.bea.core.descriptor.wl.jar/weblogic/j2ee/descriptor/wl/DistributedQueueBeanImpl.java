package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class DistributedQueueBeanImpl extends DistributedDestinationBeanImpl implements DistributedQueueBean, Serializable {
   private DistributedDestinationMemberBean[] _DistributedQueueMembers;
   private int _ForwardDelay;
   private boolean _ResetDeliveryCountOnForward;
   private static SchemaHelper2 _schemaHelper;

   public DistributedQueueBeanImpl() {
      this._initializeProperty(-1);
   }

   public DistributedQueueBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DistributedQueueBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addDistributedQueueMember(DistributedDestinationMemberBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 7)) {
         DistributedDestinationMemberBean[] _new;
         if (this._isSet(7)) {
            _new = (DistributedDestinationMemberBean[])((DistributedDestinationMemberBean[])this._getHelper()._extendArray(this.getDistributedQueueMembers(), DistributedDestinationMemberBean.class, param0));
         } else {
            _new = new DistributedDestinationMemberBean[]{param0};
         }

         try {
            this.setDistributedQueueMembers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public DistributedDestinationMemberBean[] getDistributedQueueMembers() {
      return this._DistributedQueueMembers;
   }

   public boolean isDistributedQueueMembersInherited() {
      return false;
   }

   public boolean isDistributedQueueMembersSet() {
      return this._isSet(7);
   }

   public void removeDistributedQueueMember(DistributedDestinationMemberBean param0) {
      this.destroyDistributedQueueMember(param0);
   }

   public void setDistributedQueueMembers(DistributedDestinationMemberBean[] param0) throws InvalidAttributeValueException {
      DistributedDestinationMemberBean[] param0 = param0 == null ? new DistributedDestinationMemberBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 7)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      DistributedDestinationMemberBean[] _oldVal = this._DistributedQueueMembers;
      this._DistributedQueueMembers = (DistributedDestinationMemberBean[])param0;
      this._postSet(7, _oldVal, param0);
   }

   public DistributedDestinationMemberBean createDistributedQueueMember(String param0) {
      DistributedDestinationMemberBeanImpl lookup = (DistributedDestinationMemberBeanImpl)this.lookupDistributedQueueMember(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         DistributedDestinationMemberBeanImpl _val = new DistributedDestinationMemberBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addDistributedQueueMember(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyDistributedQueueMember(DistributedDestinationMemberBean param0) {
      try {
         this._checkIsPotentialChild(param0, 7);
         DistributedDestinationMemberBean[] _old = this.getDistributedQueueMembers();
         DistributedDestinationMemberBean[] _new = (DistributedDestinationMemberBean[])((DistributedDestinationMemberBean[])this._getHelper()._removeElement(_old, DistributedDestinationMemberBean.class, param0));
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
               this.setDistributedQueueMembers(_new);
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

   public DistributedDestinationMemberBean lookupDistributedQueueMember(String param0) {
      Object[] aary = (Object[])this._DistributedQueueMembers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      DistributedDestinationMemberBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (DistributedDestinationMemberBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public int getForwardDelay() {
      return this._ForwardDelay;
   }

   public boolean isForwardDelayInherited() {
      return false;
   }

   public boolean isForwardDelaySet() {
      return this._isSet(8);
   }

   public void setForwardDelay(int param0) throws IllegalArgumentException {
      int _oldVal = this._ForwardDelay;
      this._ForwardDelay = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean getResetDeliveryCountOnForward() {
      return this._ResetDeliveryCountOnForward;
   }

   public boolean isResetDeliveryCountOnForwardInherited() {
      return false;
   }

   public boolean isResetDeliveryCountOnForwardSet() {
      return this._isSet(9);
   }

   public void setResetDeliveryCountOnForward(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._ResetDeliveryCountOnForward;
      this._ResetDeliveryCountOnForward = param0;
      this._postSet(9, _oldVal, param0);
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
               this._DistributedQueueMembers = new DistributedDestinationMemberBean[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._ForwardDelay = -1;
               if (initOne) {
                  break;
               }
            case 9:
               this._ResetDeliveryCountOnForward = true;
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

   public static class SchemaHelper2 extends DistributedDestinationBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 13:
               if (s.equals("forward-delay")) {
                  return 8;
               }
               break;
            case 24:
               if (s.equals("distributed-queue-member")) {
                  return 7;
               }
               break;
            case 31:
               if (s.equals("reset-delivery-count-on-forward")) {
                  return 9;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 7:
               return new DistributedDestinationMemberBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 7:
               return "distributed-queue-member";
            case 8:
               return "forward-delay";
            case 9:
               return "reset-delivery-count-on-forward";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 7:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 7:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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

   protected static class Helper extends DistributedDestinationBeanImpl.Helper {
      private DistributedQueueBeanImpl bean;

      protected Helper(DistributedQueueBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 7:
               return "DistributedQueueMembers";
            case 8:
               return "ForwardDelay";
            case 9:
               return "ResetDeliveryCountOnForward";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DistributedQueueMembers")) {
            return 7;
         } else if (propName.equals("ForwardDelay")) {
            return 8;
         } else {
            return propName.equals("ResetDeliveryCountOnForward") ? 9 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getDistributedQueueMembers()));
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

            for(int i = 0; i < this.bean.getDistributedQueueMembers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getDistributedQueueMembers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isForwardDelaySet()) {
               buf.append("ForwardDelay");
               buf.append(String.valueOf(this.bean.getForwardDelay()));
            }

            if (this.bean.isResetDeliveryCountOnForwardSet()) {
               buf.append("ResetDeliveryCountOnForward");
               buf.append(String.valueOf(this.bean.getResetDeliveryCountOnForward()));
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
            DistributedQueueBeanImpl otherTyped = (DistributedQueueBeanImpl)other;
            this.computeChildDiff("DistributedQueueMembers", this.bean.getDistributedQueueMembers(), otherTyped.getDistributedQueueMembers(), true);
            this.computeDiff("ForwardDelay", this.bean.getForwardDelay(), otherTyped.getForwardDelay(), true);
            this.computeDiff("ResetDeliveryCountOnForward", this.bean.getResetDeliveryCountOnForward(), otherTyped.getResetDeliveryCountOnForward(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DistributedQueueBeanImpl original = (DistributedQueueBeanImpl)event.getSourceBean();
            DistributedQueueBeanImpl proposed = (DistributedQueueBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DistributedQueueMembers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addDistributedQueueMember((DistributedDestinationMemberBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDistributedQueueMember((DistributedDestinationMemberBean)update.getRemovedObject());
                  }

                  if (original.getDistributedQueueMembers() == null || original.getDistributedQueueMembers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
                  }
               } else if (prop.equals("ForwardDelay")) {
                  original.setForwardDelay(proposed.getForwardDelay());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("ResetDeliveryCountOnForward")) {
                  original.setResetDeliveryCountOnForward(proposed.getResetDeliveryCountOnForward());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
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
            DistributedQueueBeanImpl copy = (DistributedQueueBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DistributedQueueMembers")) && this.bean.isDistributedQueueMembersSet() && !copy._isSet(7)) {
               DistributedDestinationMemberBean[] oldDistributedQueueMembers = this.bean.getDistributedQueueMembers();
               DistributedDestinationMemberBean[] newDistributedQueueMembers = new DistributedDestinationMemberBean[oldDistributedQueueMembers.length];

               for(int i = 0; i < newDistributedQueueMembers.length; ++i) {
                  newDistributedQueueMembers[i] = (DistributedDestinationMemberBean)((DistributedDestinationMemberBean)this.createCopy((AbstractDescriptorBean)oldDistributedQueueMembers[i], includeObsolete));
               }

               copy.setDistributedQueueMembers(newDistributedQueueMembers);
            }

            if ((excludeProps == null || !excludeProps.contains("ForwardDelay")) && this.bean.isForwardDelaySet()) {
               copy.setForwardDelay(this.bean.getForwardDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("ResetDeliveryCountOnForward")) && this.bean.isResetDeliveryCountOnForwardSet()) {
               copy.setResetDeliveryCountOnForward(this.bean.getResetDeliveryCountOnForward());
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
         this.inferSubTree(this.bean.getDistributedQueueMembers(), clazz, annotation);
      }
   }
}

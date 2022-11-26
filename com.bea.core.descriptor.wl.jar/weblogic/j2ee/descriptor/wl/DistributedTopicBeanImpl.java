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

public class DistributedTopicBeanImpl extends DistributedDestinationBeanImpl implements DistributedTopicBean, Serializable {
   private DistributedDestinationMemberBean[] _DistributedTopicMembers;
   private static SchemaHelper2 _schemaHelper;

   public DistributedTopicBeanImpl() {
      this._initializeProperty(-1);
   }

   public DistributedTopicBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DistributedTopicBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addDistributedTopicMember(DistributedDestinationMemberBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 7)) {
         DistributedDestinationMemberBean[] _new;
         if (this._isSet(7)) {
            _new = (DistributedDestinationMemberBean[])((DistributedDestinationMemberBean[])this._getHelper()._extendArray(this.getDistributedTopicMembers(), DistributedDestinationMemberBean.class, param0));
         } else {
            _new = new DistributedDestinationMemberBean[]{param0};
         }

         try {
            this.setDistributedTopicMembers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public DistributedDestinationMemberBean[] getDistributedTopicMembers() {
      return this._DistributedTopicMembers;
   }

   public boolean isDistributedTopicMembersInherited() {
      return false;
   }

   public boolean isDistributedTopicMembersSet() {
      return this._isSet(7);
   }

   public void removeDistributedTopicMember(DistributedDestinationMemberBean param0) {
      this.destroyDistributedTopicMember(param0);
   }

   public void setDistributedTopicMembers(DistributedDestinationMemberBean[] param0) throws InvalidAttributeValueException {
      DistributedDestinationMemberBean[] param0 = param0 == null ? new DistributedDestinationMemberBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 7)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      DistributedDestinationMemberBean[] _oldVal = this._DistributedTopicMembers;
      this._DistributedTopicMembers = (DistributedDestinationMemberBean[])param0;
      this._postSet(7, _oldVal, param0);
   }

   public DistributedDestinationMemberBean createDistributedTopicMember(String param0) {
      DistributedDestinationMemberBeanImpl lookup = (DistributedDestinationMemberBeanImpl)this.lookupDistributedTopicMember(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         DistributedDestinationMemberBeanImpl _val = new DistributedDestinationMemberBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addDistributedTopicMember(_val);
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

   public void destroyDistributedTopicMember(DistributedDestinationMemberBean param0) {
      try {
         this._checkIsPotentialChild(param0, 7);
         DistributedDestinationMemberBean[] _old = this.getDistributedTopicMembers();
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
               this.setDistributedTopicMembers(_new);
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

   public DistributedDestinationMemberBean lookupDistributedTopicMember(String param0) {
      Object[] aary = (Object[])this._DistributedTopicMembers;
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
               this._DistributedTopicMembers = new DistributedDestinationMemberBean[0];
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
            case 24:
               if (s.equals("distributed-topic-member")) {
                  return 7;
               }
            default:
               return super.getPropertyIndex(s);
         }
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
               return "distributed-topic-member";
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
      private DistributedTopicBeanImpl bean;

      protected Helper(DistributedTopicBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 7:
               return "DistributedTopicMembers";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("DistributedTopicMembers") ? 7 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getDistributedTopicMembers()));
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

            for(int i = 0; i < this.bean.getDistributedTopicMembers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getDistributedTopicMembers()[i]);
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
            DistributedTopicBeanImpl otherTyped = (DistributedTopicBeanImpl)other;
            this.computeChildDiff("DistributedTopicMembers", this.bean.getDistributedTopicMembers(), otherTyped.getDistributedTopicMembers(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DistributedTopicBeanImpl original = (DistributedTopicBeanImpl)event.getSourceBean();
            DistributedTopicBeanImpl proposed = (DistributedTopicBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DistributedTopicMembers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addDistributedTopicMember((DistributedDestinationMemberBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDistributedTopicMember((DistributedDestinationMemberBean)update.getRemovedObject());
                  }

                  if (original.getDistributedTopicMembers() == null || original.getDistributedTopicMembers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
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
            DistributedTopicBeanImpl copy = (DistributedTopicBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DistributedTopicMembers")) && this.bean.isDistributedTopicMembersSet() && !copy._isSet(7)) {
               DistributedDestinationMemberBean[] oldDistributedTopicMembers = this.bean.getDistributedTopicMembers();
               DistributedDestinationMemberBean[] newDistributedTopicMembers = new DistributedDestinationMemberBean[oldDistributedTopicMembers.length];

               for(int i = 0; i < newDistributedTopicMembers.length; ++i) {
                  newDistributedTopicMembers[i] = (DistributedDestinationMemberBean)((DistributedDestinationMemberBean)this.createCopy((AbstractDescriptorBean)oldDistributedTopicMembers[i], includeObsolete));
               }

               copy.setDistributedTopicMembers(newDistributedTopicMembers);
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
         this.inferSubTree(this.bean.getDistributedTopicMembers(), clazz, annotation);
      }
   }
}

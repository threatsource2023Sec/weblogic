package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.ejb.MessageDriven;
import javax.ejb.Stateless;
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

public class EnterpriseBeansBeanImpl extends AbstractDescriptorBean implements EnterpriseBeansBean, Serializable {
   private EntityBeanBean[] _Entities;
   private String _Id;
   private MessageDrivenBeanBean[] _MessageDrivens;
   private SessionBeanBean[] _Sessions;
   private static SchemaHelper2 _schemaHelper;

   public EnterpriseBeansBeanImpl() {
      this._initializeProperty(-1);
   }

   public EnterpriseBeansBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public EnterpriseBeansBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addSession(SessionBeanBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         SessionBeanBean[] _new;
         if (this._isSet(0)) {
            _new = (SessionBeanBean[])((SessionBeanBean[])this._getHelper()._extendArray(this.getSessions(), SessionBeanBean.class, param0));
         } else {
            _new = new SessionBeanBean[]{param0};
         }

         try {
            this.setSessions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SessionBeanBean[] getSessions() {
      return this._Sessions;
   }

   public boolean isSessionsInherited() {
      return false;
   }

   public boolean isSessionsSet() {
      return this._isSet(0);
   }

   public void removeSession(SessionBeanBean param0) {
      this.destroySession(param0);
   }

   public void setSessions(SessionBeanBean[] param0) throws InvalidAttributeValueException {
      SessionBeanBean[] param0 = param0 == null ? new SessionBeanBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SessionBeanBean[] _oldVal = this._Sessions;
      this._Sessions = (SessionBeanBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public SessionBeanBean createSession() {
      SessionBeanBeanImpl _val = new SessionBeanBeanImpl(this, -1);

      try {
         this.addSession(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public SessionBeanBean createSession(String param0) {
      SessionBeanBeanImpl _val = new SessionBeanBeanImpl(this, -1);

      try {
         _val.setEjbName(param0);
         this.addSession(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroySession(SessionBeanBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         SessionBeanBean[] _old = this.getSessions();
         SessionBeanBean[] _new = (SessionBeanBean[])((SessionBeanBean[])this._getHelper()._removeElement(_old, SessionBeanBean.class, param0));
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
               this.setSessions(_new);
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

   public SessionBeanBean lookupSession(String param0) {
      Object[] aary = (Object[])this._Sessions;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SessionBeanBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SessionBeanBeanImpl)it.previous();
      } while(!bean.getEjbName().equals(param0));

      return bean;
   }

   public void addEntity(EntityBeanBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         EntityBeanBean[] _new;
         if (this._isSet(1)) {
            _new = (EntityBeanBean[])((EntityBeanBean[])this._getHelper()._extendArray(this.getEntities(), EntityBeanBean.class, param0));
         } else {
            _new = new EntityBeanBean[]{param0};
         }

         try {
            this.setEntities(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EntityBeanBean[] getEntities() {
      return this._Entities;
   }

   public boolean isEntitiesInherited() {
      return false;
   }

   public boolean isEntitiesSet() {
      return this._isSet(1);
   }

   public void removeEntity(EntityBeanBean param0) {
      this.destroyEntity(param0);
   }

   public void setEntities(EntityBeanBean[] param0) throws InvalidAttributeValueException {
      EntityBeanBean[] param0 = param0 == null ? new EntityBeanBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EntityBeanBean[] _oldVal = this._Entities;
      this._Entities = (EntityBeanBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public EntityBeanBean createEntity() {
      EntityBeanBeanImpl _val = new EntityBeanBeanImpl(this, -1);

      try {
         this.addEntity(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEntity(EntityBeanBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         EntityBeanBean[] _old = this.getEntities();
         EntityBeanBean[] _new = (EntityBeanBean[])((EntityBeanBean[])this._getHelper()._removeElement(_old, EntityBeanBean.class, param0));
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
               this.setEntities(_new);
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

   public void addMessageDriven(MessageDrivenBeanBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         MessageDrivenBeanBean[] _new;
         if (this._isSet(2)) {
            _new = (MessageDrivenBeanBean[])((MessageDrivenBeanBean[])this._getHelper()._extendArray(this.getMessageDrivens(), MessageDrivenBeanBean.class, param0));
         } else {
            _new = new MessageDrivenBeanBean[]{param0};
         }

         try {
            this.setMessageDrivens(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MessageDrivenBeanBean[] getMessageDrivens() {
      return this._MessageDrivens;
   }

   public boolean isMessageDrivensInherited() {
      return false;
   }

   public boolean isMessageDrivensSet() {
      return this._isSet(2);
   }

   public void removeMessageDriven(MessageDrivenBeanBean param0) {
      this.destroyMessageDriven(param0);
   }

   public void setMessageDrivens(MessageDrivenBeanBean[] param0) throws InvalidAttributeValueException {
      MessageDrivenBeanBean[] param0 = param0 == null ? new MessageDrivenBeanBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MessageDrivenBeanBean[] _oldVal = this._MessageDrivens;
      this._MessageDrivens = (MessageDrivenBeanBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public MessageDrivenBeanBean createMessageDriven() {
      MessageDrivenBeanBeanImpl _val = new MessageDrivenBeanBeanImpl(this, -1);

      try {
         this.addMessageDriven(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public MessageDrivenBeanBean createMessageDriven(String param0) {
      MessageDrivenBeanBeanImpl _val = new MessageDrivenBeanBeanImpl(this, -1);

      try {
         _val.setEjbName(param0);
         this.addMessageDriven(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyMessageDriven(MessageDrivenBeanBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         MessageDrivenBeanBean[] _old = this.getMessageDrivens();
         MessageDrivenBeanBean[] _new = (MessageDrivenBeanBean[])((MessageDrivenBeanBean[])this._getHelper()._removeElement(_old, MessageDrivenBeanBean.class, param0));
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
               this.setMessageDrivens(_new);
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

   public MessageDrivenBeanBean lookupMessageDriven(String param0) {
      Object[] aary = (Object[])this._MessageDrivens;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      MessageDrivenBeanBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (MessageDrivenBeanBeanImpl)it.previous();
      } while(!bean.getEjbName().equals(param0));

      return bean;
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(3);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._Entities = new EntityBeanBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._MessageDrivens = new MessageDrivenBeanBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._Sessions = new SessionBeanBean[0];
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
            case 2:
               if (s.equals("id")) {
                  return 3;
               }
               break;
            case 6:
               if (s.equals("entity")) {
                  return 1;
               }
               break;
            case 7:
               if (s.equals("session")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("message-driven")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new SessionBeanBeanImpl.SchemaHelper2();
            case 1:
               return new EntityBeanBeanImpl.SchemaHelper2();
            case 2:
               return new MessageDrivenBeanBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "session";
            case 1:
               return "entity";
            case 2:
               return "message-driven";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
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
            case 0:
               return true;
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
      private EnterpriseBeansBeanImpl bean;

      protected Helper(EnterpriseBeansBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Sessions";
            case 1:
               return "Entities";
            case 2:
               return "MessageDrivens";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Entities")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("MessageDrivens")) {
            return 2;
         } else {
            return propName.equals("Sessions") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getEntities()));
         iterators.add(new ArrayIterator(this.bean.getMessageDrivens()));
         iterators.add(new ArrayIterator(this.bean.getSessions()));
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
            for(i = 0; i < this.bean.getEntities().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEntities()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMessageDrivens().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMessageDrivens()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSessions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSessions()[i]);
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
            EnterpriseBeansBeanImpl otherTyped = (EnterpriseBeansBeanImpl)other;
            this.computeChildDiff("Entities", this.bean.getEntities(), otherTyped.getEntities(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("MessageDrivens", this.bean.getMessageDrivens(), otherTyped.getMessageDrivens(), false);
            this.computeChildDiff("Sessions", this.bean.getSessions(), otherTyped.getSessions(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EnterpriseBeansBeanImpl original = (EnterpriseBeansBeanImpl)event.getSourceBean();
            EnterpriseBeansBeanImpl proposed = (EnterpriseBeansBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Entities")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEntity((EntityBeanBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEntity((EntityBeanBean)update.getRemovedObject());
                  }

                  if (original.getEntities() == null || original.getEntities().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("MessageDrivens")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMessageDriven((MessageDrivenBeanBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMessageDriven((MessageDrivenBeanBean)update.getRemovedObject());
                  }

                  if (original.getMessageDrivens() == null || original.getMessageDrivens().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("Sessions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSession((SessionBeanBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSession((SessionBeanBean)update.getRemovedObject());
                  }

                  if (original.getSessions() == null || original.getSessions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            EnterpriseBeansBeanImpl copy = (EnterpriseBeansBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("Entities")) && this.bean.isEntitiesSet() && !copy._isSet(1)) {
               EntityBeanBean[] oldEntities = this.bean.getEntities();
               EntityBeanBean[] newEntities = new EntityBeanBean[oldEntities.length];

               for(i = 0; i < newEntities.length; ++i) {
                  newEntities[i] = (EntityBeanBean)((EntityBeanBean)this.createCopy((AbstractDescriptorBean)oldEntities[i], includeObsolete));
               }

               copy.setEntities(newEntities);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDrivens")) && this.bean.isMessageDrivensSet() && !copy._isSet(2)) {
               MessageDrivenBeanBean[] oldMessageDrivens = this.bean.getMessageDrivens();
               MessageDrivenBeanBean[] newMessageDrivens = new MessageDrivenBeanBean[oldMessageDrivens.length];

               for(i = 0; i < newMessageDrivens.length; ++i) {
                  newMessageDrivens[i] = (MessageDrivenBeanBean)((MessageDrivenBeanBean)this.createCopy((AbstractDescriptorBean)oldMessageDrivens[i], includeObsolete));
               }

               copy.setMessageDrivens(newMessageDrivens);
            }

            if ((excludeProps == null || !excludeProps.contains("Sessions")) && this.bean.isSessionsSet() && !copy._isSet(0)) {
               SessionBeanBean[] oldSessions = this.bean.getSessions();
               SessionBeanBean[] newSessions = new SessionBeanBean[oldSessions.length];

               for(i = 0; i < newSessions.length; ++i) {
                  newSessions[i] = (SessionBeanBean)((SessionBeanBean)this.createCopy((AbstractDescriptorBean)oldSessions[i], includeObsolete));
               }

               copy.setSessions(newSessions);
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
         this.inferSubTree(this.bean.getEntities(), clazz, annotation);
         if (clazz.isAnnotationPresent(MessageDriven.class)) {
            MessageDriven MessageDrivensAnno = (MessageDriven)clazz.getAnnotation(MessageDriven.class);
            if (this.bean.lookupMessageDriven(MessageDrivensAnno.name().equals("") ? clazz.getSimpleName() : MessageDrivensAnno.name()) == null) {
               this.bean.createMessageDriven(MessageDrivensAnno.name().equals("") ? clazz.getSimpleName() : MessageDrivensAnno.name());
            }

            Object property = this.bean.lookupMessageDriven(MessageDrivensAnno.name().equals("") ? clazz.getSimpleName() : MessageDrivensAnno.name());
            this.inferSubTree(property, clazz, MessageDrivensAnno);
         }

         if (clazz.isAnnotationPresent(Stateless.class)) {
            Stateless SessionsAnno = (Stateless)clazz.getAnnotation(Stateless.class);
            if (this.bean.lookupSession(SessionsAnno.name().equals("") ? clazz.getSimpleName() : SessionsAnno.name()) == null) {
               this.bean.createSession(SessionsAnno.name().equals("") ? clazz.getSimpleName() : SessionsAnno.name());
            }

            Object property = this.bean.lookupSession(SessionsAnno.name().equals("") ? clazz.getSimpleName() : SessionsAnno.name());
            this.inferSubTree(property, clazz, SessionsAnno);
         }

      }
   }
}

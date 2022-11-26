package weblogic.j2ee.descriptor.wl60;

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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicRdbmsBeanBeanImpl extends BaseWeblogicRdbmsBeanBeanImpl implements WeblogicRdbmsBeanBean, Serializable {
   private String _DataSourceJndiName;
   private String _EjbName;
   private boolean _EnableTunedUpdates;
   private FieldMapBean[] _FieldMaps;
   private FinderBean[] _Finders;
   private String _Id;
   private String _PoolName;
   private String _TableName;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicRdbmsBeanBeanImpl() {
      this._initializeProperty(-1);
   }

   public WeblogicRdbmsBeanBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WeblogicRdbmsBeanBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getEjbName() {
      return this._EjbName;
   }

   public boolean isEjbNameInherited() {
      return false;
   }

   public boolean isEjbNameSet() {
      return this._isSet(0);
   }

   public void setEjbName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbName;
      this._EjbName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDataSourceJndiName() {
      return this._DataSourceJndiName;
   }

   public String getPoolName() {
      return this._PoolName;
   }

   public boolean isDataSourceJndiNameInherited() {
      return false;
   }

   public boolean isDataSourceJndiNameSet() {
      return this._isSet(1);
   }

   public boolean isPoolNameInherited() {
      return false;
   }

   public boolean isPoolNameSet() {
      return this._isSet(2);
   }

   public void setDataSourceJndiName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DataSourceJndiName;
      this._DataSourceJndiName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void setPoolName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PoolName;
      this._PoolName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getTableName() {
      return this._TableName;
   }

   public boolean isTableNameInherited() {
      return false;
   }

   public boolean isTableNameSet() {
      return this._isSet(3);
   }

   public void setTableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TableName;
      this._TableName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public void addFieldMap(FieldMapBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         FieldMapBean[] _new;
         if (this._isSet(4)) {
            _new = (FieldMapBean[])((FieldMapBean[])this._getHelper()._extendArray(this.getFieldMaps(), FieldMapBean.class, param0));
         } else {
            _new = new FieldMapBean[]{param0};
         }

         try {
            this.setFieldMaps(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public FieldMapBean[] getFieldMaps() {
      return this._FieldMaps;
   }

   public boolean isFieldMapsInherited() {
      return false;
   }

   public boolean isFieldMapsSet() {
      return this._isSet(4);
   }

   public void removeFieldMap(FieldMapBean param0) {
      this.destroyFieldMap(param0);
   }

   public void setFieldMaps(FieldMapBean[] param0) throws InvalidAttributeValueException {
      FieldMapBean[] param0 = param0 == null ? new FieldMapBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      FieldMapBean[] _oldVal = this._FieldMaps;
      this._FieldMaps = (FieldMapBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public FieldMapBean createFieldMap() {
      FieldMapBeanImpl _val = new FieldMapBeanImpl(this, -1);

      try {
         this.addFieldMap(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyFieldMap(FieldMapBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         FieldMapBean[] _old = this.getFieldMaps();
         FieldMapBean[] _new = (FieldMapBean[])((FieldMapBean[])this._getHelper()._removeElement(_old, FieldMapBean.class, param0));
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
               this.setFieldMaps(_new);
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

   public void addFinder(FinderBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         FinderBean[] _new;
         if (this._isSet(5)) {
            _new = (FinderBean[])((FinderBean[])this._getHelper()._extendArray(this.getFinders(), FinderBean.class, param0));
         } else {
            _new = new FinderBean[]{param0};
         }

         try {
            this.setFinders(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public FinderBean[] getFinders() {
      return this._Finders;
   }

   public boolean isFindersInherited() {
      return false;
   }

   public boolean isFindersSet() {
      return this._isSet(5);
   }

   public void removeFinder(FinderBean param0) {
      this.destroyFinder(param0);
   }

   public void setFinders(FinderBean[] param0) throws InvalidAttributeValueException {
      FinderBean[] param0 = param0 == null ? new FinderBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      FinderBean[] _oldVal = this._Finders;
      this._Finders = (FinderBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public FinderBean createFinder() {
      FinderBeanImpl _val = new FinderBeanImpl(this, -1);

      try {
         this.addFinder(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyFinder(FinderBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         FinderBean[] _old = this.getFinders();
         FinderBean[] _new = (FinderBean[])((FinderBean[])this._getHelper()._removeElement(_old, FinderBean.class, param0));
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
               this.setFinders(_new);
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

   public boolean isEnableTunedUpdates() {
      return this._EnableTunedUpdates;
   }

   public boolean isEnableTunedUpdatesInherited() {
      return false;
   }

   public boolean isEnableTunedUpdatesSet() {
      return this._isSet(6);
   }

   public void setEnableTunedUpdates(boolean param0) {
      boolean _oldVal = this._EnableTunedUpdates;
      this._EnableTunedUpdates = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(7);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(7, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getEjbName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 8:
            if (s.equals("ejb-name")) {
               return info.compareXpaths(this._getPropertyXpath("ejb-name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
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
               this._DataSourceJndiName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._EjbName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._FieldMaps = new FieldMapBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._Finders = new FinderBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._PoolName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._TableName = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._EnableTunedUpdates = true;
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

   public static class SchemaHelper2 extends BaseWeblogicRdbmsBeanBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 7;
               }
            case 3:
            case 4:
            case 5:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            default:
               break;
            case 6:
               if (s.equals("finder")) {
                  return 5;
               }
               break;
            case 8:
               if (s.equals("ejb-name")) {
                  return 0;
               }
               break;
            case 9:
               if (s.equals("field-map")) {
                  return 4;
               }

               if (s.equals("pool-name")) {
                  return 2;
               }
               break;
            case 10:
               if (s.equals("table-name")) {
                  return 3;
               }
               break;
            case 20:
               if (s.equals("enable-tuned-updates")) {
                  return 6;
               }
               break;
            case 21:
               if (s.equals("data-source-jndi-name")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 4:
               return new FieldMapBeanImpl.SchemaHelper2();
            case 5:
               return new FinderBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ejb-name";
            case 1:
               return "data-source-jndi-name";
            case 2:
               return "pool-name";
            case 3:
               return "table-name";
            case 4:
               return "field-map";
            case 5:
               return "finder";
            case 6:
               return "enable-tuned-updates";
            case 7:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isBean(propIndex);
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("ejb-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends BaseWeblogicRdbmsBeanBeanImpl.Helper {
      private WeblogicRdbmsBeanBeanImpl bean;

      protected Helper(WeblogicRdbmsBeanBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "EjbName";
            case 1:
               return "DataSourceJndiName";
            case 2:
               return "PoolName";
            case 3:
               return "TableName";
            case 4:
               return "FieldMaps";
            case 5:
               return "Finders";
            case 6:
               return "EnableTunedUpdates";
            case 7:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DataSourceJndiName")) {
            return 1;
         } else if (propName.equals("EjbName")) {
            return 0;
         } else if (propName.equals("FieldMaps")) {
            return 4;
         } else if (propName.equals("Finders")) {
            return 5;
         } else if (propName.equals("Id")) {
            return 7;
         } else if (propName.equals("PoolName")) {
            return 2;
         } else if (propName.equals("TableName")) {
            return 3;
         } else {
            return propName.equals("EnableTunedUpdates") ? 6 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getFieldMaps()));
         iterators.add(new ArrayIterator(this.bean.getFinders()));
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
            if (this.bean.isDataSourceJndiNameSet()) {
               buf.append("DataSourceJndiName");
               buf.append(String.valueOf(this.bean.getDataSourceJndiName()));
            }

            if (this.bean.isEjbNameSet()) {
               buf.append("EjbName");
               buf.append(String.valueOf(this.bean.getEjbName()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getFieldMaps().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getFieldMaps()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getFinders().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getFinders()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isPoolNameSet()) {
               buf.append("PoolName");
               buf.append(String.valueOf(this.bean.getPoolName()));
            }

            if (this.bean.isTableNameSet()) {
               buf.append("TableName");
               buf.append(String.valueOf(this.bean.getTableName()));
            }

            if (this.bean.isEnableTunedUpdatesSet()) {
               buf.append("EnableTunedUpdates");
               buf.append(String.valueOf(this.bean.isEnableTunedUpdates()));
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
            WeblogicRdbmsBeanBeanImpl otherTyped = (WeblogicRdbmsBeanBeanImpl)other;
            this.computeDiff("DataSourceJndiName", this.bean.getDataSourceJndiName(), otherTyped.getDataSourceJndiName(), false);
            this.computeDiff("EjbName", this.bean.getEjbName(), otherTyped.getEjbName(), false);
            this.computeChildDiff("FieldMaps", this.bean.getFieldMaps(), otherTyped.getFieldMaps(), false);
            this.computeChildDiff("Finders", this.bean.getFinders(), otherTyped.getFinders(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("PoolName", this.bean.getPoolName(), otherTyped.getPoolName(), false);
            this.computeDiff("TableName", this.bean.getTableName(), otherTyped.getTableName(), false);
            this.computeDiff("EnableTunedUpdates", this.bean.isEnableTunedUpdates(), otherTyped.isEnableTunedUpdates(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicRdbmsBeanBeanImpl original = (WeblogicRdbmsBeanBeanImpl)event.getSourceBean();
            WeblogicRdbmsBeanBeanImpl proposed = (WeblogicRdbmsBeanBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DataSourceJndiName")) {
                  original.setDataSourceJndiName(proposed.getDataSourceJndiName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("EjbName")) {
                  original.setEjbName(proposed.getEjbName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("FieldMaps")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addFieldMap((FieldMapBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeFieldMap((FieldMapBean)update.getRemovedObject());
                  }

                  if (original.getFieldMaps() == null || original.getFieldMaps().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("Finders")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addFinder((FinderBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeFinder((FinderBean)update.getRemovedObject());
                  }

                  if (original.getFinders() == null || original.getFinders().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("PoolName")) {
                  original.setPoolName(proposed.getPoolName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("TableName")) {
                  original.setTableName(proposed.getTableName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("EnableTunedUpdates")) {
                  original.setEnableTunedUpdates(proposed.isEnableTunedUpdates());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
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
            WeblogicRdbmsBeanBeanImpl copy = (WeblogicRdbmsBeanBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DataSourceJndiName")) && this.bean.isDataSourceJndiNameSet()) {
               copy.setDataSourceJndiName(this.bean.getDataSourceJndiName());
            }

            if ((excludeProps == null || !excludeProps.contains("EjbName")) && this.bean.isEjbNameSet()) {
               copy.setEjbName(this.bean.getEjbName());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("FieldMaps")) && this.bean.isFieldMapsSet() && !copy._isSet(4)) {
               FieldMapBean[] oldFieldMaps = this.bean.getFieldMaps();
               FieldMapBean[] newFieldMaps = new FieldMapBean[oldFieldMaps.length];

               for(i = 0; i < newFieldMaps.length; ++i) {
                  newFieldMaps[i] = (FieldMapBean)((FieldMapBean)this.createCopy((AbstractDescriptorBean)oldFieldMaps[i], includeObsolete));
               }

               copy.setFieldMaps(newFieldMaps);
            }

            if ((excludeProps == null || !excludeProps.contains("Finders")) && this.bean.isFindersSet() && !copy._isSet(5)) {
               FinderBean[] oldFinders = this.bean.getFinders();
               FinderBean[] newFinders = new FinderBean[oldFinders.length];

               for(i = 0; i < newFinders.length; ++i) {
                  newFinders[i] = (FinderBean)((FinderBean)this.createCopy((AbstractDescriptorBean)oldFinders[i], includeObsolete));
               }

               copy.setFinders(newFinders);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("PoolName")) && this.bean.isPoolNameSet()) {
               copy.setPoolName(this.bean.getPoolName());
            }

            if ((excludeProps == null || !excludeProps.contains("TableName")) && this.bean.isTableNameSet()) {
               copy.setTableName(this.bean.getTableName());
            }

            if ((excludeProps == null || !excludeProps.contains("EnableTunedUpdates")) && this.bean.isEnableTunedUpdatesSet()) {
               copy.setEnableTunedUpdates(this.bean.isEnableTunedUpdates());
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
         this.inferSubTree(this.bean.getFieldMaps(), clazz, annotation);
         this.inferSubTree(this.bean.getFinders(), clazz, annotation);
      }
   }
}

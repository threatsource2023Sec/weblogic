package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.utils.ArrayUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SubDeploymentMBeanImpl extends TargetInfoMBeanImpl implements SubDeploymentMBean, Serializable {
   private String _Name;
   private SubDeploymentMBean[] _SubDeployments;
   private boolean _Untargeted;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private SubDeploymentMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(SubDeploymentMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(SubDeploymentMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public SubDeploymentMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(SubDeploymentMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      SubDeploymentMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public SubDeploymentMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SubDeploymentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SubDeploymentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      if (!this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2)) {
         return this._performMacroSubstitution(this._getDelegateBean().getName(), this);
      } else {
         if (!this._isSet(2)) {
            try {
               return ((ConfigurationMBean)this.getParent()).getName();
            } catch (NullPointerException var2) {
            }
         }

         return this._Name;
      }
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void addSubDeployment(SubDeploymentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         SubDeploymentMBean[] _new;
         if (this._isSet(13)) {
            _new = (SubDeploymentMBean[])((SubDeploymentMBean[])this._getHelper()._extendArray(this.getSubDeployments(), SubDeploymentMBean.class, param0));
         } else {
            _new = new SubDeploymentMBean[]{param0};
         }

         try {
            this.setSubDeployments(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SubDeploymentMBean[] getSubDeployments() {
      SubDeploymentMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(13)) {
         delegateArray = this._getDelegateBean().getSubDeployments();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._SubDeployments.length; ++j) {
               if (delegateArray[i].getName().equals(this._SubDeployments[j].getName())) {
                  ((SubDeploymentMBeanImpl)this._SubDeployments[j])._setDelegateBean((SubDeploymentMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  SubDeploymentMBeanImpl mbean = new SubDeploymentMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 13);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((SubDeploymentMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(13)) {
                     this.setSubDeployments((SubDeploymentMBean[])((SubDeploymentMBean[])this._getHelper()._extendArray(this._SubDeployments, SubDeploymentMBean.class, mbean)));
                  } else {
                     this.setSubDeployments(new SubDeploymentMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new SubDeploymentMBean[0];
      }

      if (this._SubDeployments != null) {
         List removeList = new ArrayList();
         SubDeploymentMBean[] var18 = this._SubDeployments;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            SubDeploymentMBean bn = var18[var5];
            SubDeploymentMBeanImpl bni = (SubDeploymentMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               SubDeploymentMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  SubDeploymentMBean delegateTo = var10[var12];
                  if (nameToSearch.equals(delegateTo.getName())) {
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  removeList.add(bn);
               }
            }
         }

         Iterator var19 = removeList.iterator();

         while(var19.hasNext()) {
            SubDeploymentMBean removeIt = (SubDeploymentMBean)var19.next();
            SubDeploymentMBeanImpl removeItImpl = (SubDeploymentMBeanImpl)removeIt;
            SubDeploymentMBean[] _new = (SubDeploymentMBean[])((SubDeploymentMBean[])this._getHelper()._removeElement(this._SubDeployments, SubDeploymentMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setSubDeployments(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._SubDeployments;
   }

   public boolean isSubDeploymentsInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(13)) {
         SubDeploymentMBean[] elements = this.getSubDeployments();
         SubDeploymentMBean[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            if (o instanceof AbstractDescriptorBean) {
               AbstractDescriptorBean adBean = (AbstractDescriptorBean)o;
               if (!adBean._isTransient() || !adBean._isSynthetic()) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isSubDeploymentsSet() {
      return this._isSet(13);
   }

   public void removeSubDeployment(SubDeploymentMBean param0) {
      this.destroySubDeployment(param0);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SubDeploymentMBeanImpl source = (SubDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setSubDeployments(SubDeploymentMBean[] param0) throws InvalidAttributeValueException {
      SubDeploymentMBean[] param0 = param0 == null ? new SubDeploymentMBeanImpl[0] : param0;
      SubDeploymentMBeanImpl source;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._SubDeployments, (Object[])param0, handler, new Comparator() {
            public int compare(SubDeploymentMBean o1, SubDeploymentMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            SubDeploymentMBean bean = (SubDeploymentMBean)var3.next();
            source = (SubDeploymentMBeanImpl)bean;
            if (!source._isTransient() && source._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(13);
      SubDeploymentMBean[] _oldVal = this._SubDeployments;
      this._SubDeployments = (SubDeploymentMBean[])param0;
      this._postSet(13, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         source = (SubDeploymentMBeanImpl)var11.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public SubDeploymentMBean createSubDeployment(String param0) {
      SubDeploymentMBeanImpl lookup = (SubDeploymentMBeanImpl)this.lookupSubDeployment(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SubDeploymentMBeanImpl _val = new SubDeploymentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSubDeployment(_val);
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

   public SubDeploymentMBean lookupSubDeployment(String param0) {
      Object[] aary = (Object[])this.getSubDeployments();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SubDeploymentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SubDeploymentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroySubDeployment(SubDeploymentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 13);
         SubDeploymentMBean[] _old = this.getSubDeployments();
         SubDeploymentMBean[] _new = (SubDeploymentMBean[])((SubDeploymentMBean[])this._getHelper()._removeElement(_old, SubDeploymentMBean.class, param0));
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
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  SubDeploymentMBeanImpl source = (SubDeploymentMBeanImpl)var6.next();
                  SubDeploymentMBeanImpl childImpl = (SubDeploymentMBeanImpl)_child;
                  SubDeploymentMBeanImpl lookup = (SubDeploymentMBeanImpl)source.lookupSubDeployment(childImpl.getName());
                  if (lookup != null) {
                     source.destroySubDeployment(lookup);
                  }
               }

               this.setSubDeployments(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
         }
      }
   }

   public boolean isUntargeted() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().isUntargeted() : this._Untargeted;
   }

   public boolean isUntargetedInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isUntargetedSet() {
      return this._isSet(14);
   }

   public void setUntargeted(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      boolean _oldVal = this._Untargeted;
      this._Untargeted = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SubDeploymentMBeanImpl source = (SubDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      JMSLegalHelper.validateSubDeploymentTargets(this);
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._SubDeployments = new SubDeploymentMBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._Untargeted = false;
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
      return "SubDeployment";
   }

   public void putValue(String name, Object v) {
      if (name.equals("Name")) {
         String oldVal = this._Name;
         this._Name = (String)v;
         this._postSet(2, oldVal, this._Name);
      } else if (name.equals("SubDeployments")) {
         SubDeploymentMBean[] oldVal = this._SubDeployments;
         this._SubDeployments = (SubDeploymentMBean[])((SubDeploymentMBean[])v);
         this._postSet(13, oldVal, this._SubDeployments);
      } else if (name.equals("Untargeted")) {
         boolean oldVal = this._Untargeted;
         this._Untargeted = (Boolean)v;
         this._postSet(14, oldVal, this._Untargeted);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("SubDeployments")) {
         return this._SubDeployments;
      } else {
         return name.equals("Untargeted") ? new Boolean(this._Untargeted) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends TargetInfoMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
               break;
            case 10:
               if (s.equals("untargeted")) {
                  return 14;
               }
               break;
            case 14:
               if (s.equals("sub-deployment")) {
                  return 13;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 13:
               return new SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "name";
            case 13:
               return "sub-deployment";
            case 14:
               return "untargeted";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 11:
            case 12:
            default:
               return super.isArray(propIndex);
            case 13:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 13:
               return true;
            default:
               return super.isBean(propIndex);
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends TargetInfoMBeanImpl.Helper {
      private SubDeploymentMBeanImpl bean;

      protected Helper(SubDeploymentMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 13:
               return "SubDeployments";
            case 14:
               return "Untargeted";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("SubDeployments")) {
            return 13;
         } else {
            return propName.equals("Untargeted") ? 14 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getSubDeployments()));
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
            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getSubDeployments().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSubDeployments()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isUntargetedSet()) {
               buf.append("Untargeted");
               buf.append(String.valueOf(this.bean.isUntargeted()));
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
            SubDeploymentMBeanImpl otherTyped = (SubDeploymentMBeanImpl)other;
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeChildDiff("SubDeployments", this.bean.getSubDeployments(), otherTyped.getSubDeployments(), false);
            this.computeDiff("Untargeted", this.bean.isUntargeted(), otherTyped.isUntargeted(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SubDeploymentMBeanImpl original = (SubDeploymentMBeanImpl)event.getSourceBean();
            SubDeploymentMBeanImpl proposed = (SubDeploymentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("SubDeployments")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSubDeployment((SubDeploymentMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSubDeployment((SubDeploymentMBean)update.getRemovedObject());
                  }

                  if (original.getSubDeployments() == null || original.getSubDeployments().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  }
               } else if (prop.equals("Untargeted")) {
                  original.setUntargeted(proposed.isUntargeted());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
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
            SubDeploymentMBeanImpl copy = (SubDeploymentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("SubDeployments")) && this.bean.isSubDeploymentsSet() && !copy._isSet(13)) {
               SubDeploymentMBean[] oldSubDeployments = this.bean.getSubDeployments();
               SubDeploymentMBean[] newSubDeployments = new SubDeploymentMBean[oldSubDeployments.length];

               for(int i = 0; i < newSubDeployments.length; ++i) {
                  newSubDeployments[i] = (SubDeploymentMBean)((SubDeploymentMBean)this.createCopy((AbstractDescriptorBean)oldSubDeployments[i], includeObsolete));
               }

               copy.setSubDeployments(newSubDeployments);
            }

            if ((excludeProps == null || !excludeProps.contains("Untargeted")) && this.bean.isUntargetedSet()) {
               copy.setUntargeted(this.bean.isUntargeted());
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
         this.inferSubTree(this.bean.getSubDeployments(), clazz, annotation);
      }
   }
}

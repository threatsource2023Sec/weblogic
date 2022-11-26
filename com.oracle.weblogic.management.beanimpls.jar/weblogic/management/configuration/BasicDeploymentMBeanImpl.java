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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.mbeans.custom.AppDeployment;
import weblogic.utils.ArrayUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class BasicDeploymentMBeanImpl extends TargetInfoMBeanImpl implements BasicDeploymentMBean, Serializable {
   private int _DeploymentOrder;
   private String _DeploymentPrincipalName;
   private String _PartitionName;
   private String _SourcePath;
   private SubDeploymentMBean[] _SubDeployments;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private BasicDeploymentMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(BasicDeploymentMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(BasicDeploymentMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public BasicDeploymentMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(BasicDeploymentMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      BasicDeploymentMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public BasicDeploymentMBeanImpl() {
      this._initializeProperty(-1);
   }

   public BasicDeploymentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public BasicDeploymentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getSourcePath() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getSourcePath(), this) : this._SourcePath;
   }

   public boolean isSourcePathInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isSourcePathSet() {
      return this._isSet(13);
   }

   public void setSourcePath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      String _oldVal = this._SourcePath;
      this._SourcePath = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         BasicDeploymentMBeanImpl source = (BasicDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public SubDeploymentMBean[] getSubDeployments() {
      SubDeploymentMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(14)) {
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
                  this._setParent(mbean, this, 14);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((SubDeploymentMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(14)) {
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
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(14)) {
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
      return this._isSet(14);
   }

   public void removeSubDeployment(SubDeploymentMBean param0) {
      this.destroySubDeployment(param0);
   }

   public void setSubDeployments(SubDeploymentMBean[] param0) throws InvalidAttributeValueException {
      SubDeploymentMBean[] param0 = param0 == null ? new SubDeploymentMBeanImpl[0] : param0;
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
            SubDeploymentMBeanImpl beanImpl = (SubDeploymentMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(14);
      SubDeploymentMBean[] _oldVal = this._SubDeployments;
      this._SubDeployments = (SubDeploymentMBean[])param0;
      this._postSet(14, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         BasicDeploymentMBeanImpl source = (BasicDeploymentMBeanImpl)var11.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 14);
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
                  BasicDeploymentMBeanImpl source = (BasicDeploymentMBeanImpl)var6.next();
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

   public void addSubDeployment(SubDeploymentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         SubDeploymentMBean[] _new;
         if (this._isSet(14)) {
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

   public int getDeploymentOrder() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getDeploymentOrder() : this._DeploymentOrder;
   }

   public boolean isDeploymentOrderInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isDeploymentOrderSet() {
      return this._isSet(15);
   }

   public void setDeploymentOrder(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      int _oldVal = this._DeploymentOrder;
      this._DeploymentOrder = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         BasicDeploymentMBeanImpl source = (BasicDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDeploymentPrincipalName() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._performMacroSubstitution(this._getDelegateBean().getDeploymentPrincipalName(), this) : this._DeploymentPrincipalName;
   }

   public boolean isDeploymentPrincipalNameInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isDeploymentPrincipalNameSet() {
      return this._isSet(16);
   }

   public void setDeploymentPrincipalName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      SecurityLegalHelper.validatePrincipalName(param0);
      boolean wasSet = this._isSet(16);
      String _oldVal = this._DeploymentPrincipalName;
      this._DeploymentPrincipalName = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         BasicDeploymentMBeanImpl source = (BasicDeploymentMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPartitionName() {
      if (!this._isSet(17)) {
         try {
            return AppDeployment.derivePartitionName(this.getParent(), this.getName());
         } catch (NullPointerException var2) {
         }
      }

      return this._PartitionName;
   }

   public boolean isPartitionNameInherited() {
      return false;
   }

   public boolean isPartitionNameSet() {
      return this._isSet(17);
   }

   public void setPartitionName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._PartitionName = param0;
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
         idx = 15;
      }

      try {
         switch (idx) {
            case 15:
               this._DeploymentOrder = 100;
               if (initOne) {
                  break;
               }
            case 16:
               this._DeploymentPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._PartitionName = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._SourcePath = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._SubDeployments = new SubDeploymentMBean[0];
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
      return "BasicDeployment";
   }

   public void putValue(String name, Object v) {
      if (name.equals("DeploymentOrder")) {
         int oldVal = this._DeploymentOrder;
         this._DeploymentOrder = (Integer)v;
         this._postSet(15, oldVal, this._DeploymentOrder);
      } else {
         String oldVal;
         if (name.equals("DeploymentPrincipalName")) {
            oldVal = this._DeploymentPrincipalName;
            this._DeploymentPrincipalName = (String)v;
            this._postSet(16, oldVal, this._DeploymentPrincipalName);
         } else if (name.equals("PartitionName")) {
            oldVal = this._PartitionName;
            this._PartitionName = (String)v;
            this._postSet(17, oldVal, this._PartitionName);
         } else if (name.equals("SourcePath")) {
            oldVal = this._SourcePath;
            this._SourcePath = (String)v;
            this._postSet(13, oldVal, this._SourcePath);
         } else if (name.equals("SubDeployments")) {
            SubDeploymentMBean[] oldVal = this._SubDeployments;
            this._SubDeployments = (SubDeploymentMBean[])((SubDeploymentMBean[])v);
            this._postSet(14, oldVal, this._SubDeployments);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DeploymentOrder")) {
         return new Integer(this._DeploymentOrder);
      } else if (name.equals("DeploymentPrincipalName")) {
         return this._DeploymentPrincipalName;
      } else if (name.equals("PartitionName")) {
         return this._PartitionName;
      } else if (name.equals("SourcePath")) {
         return this._SourcePath;
      } else {
         return name.equals("SubDeployments") ? this._SubDeployments : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends TargetInfoMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("source-path")) {
                  return 13;
               }
               break;
            case 14:
               if (s.equals("partition-name")) {
                  return 17;
               }

               if (s.equals("sub-deployment")) {
                  return 14;
               }
               break;
            case 16:
               if (s.equals("deployment-order")) {
                  return 15;
               }
               break;
            case 25:
               if (s.equals("deployment-principal-name")) {
                  return 16;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 14:
               return new SubDeploymentMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 13:
               return "source-path";
            case 14:
               return "sub-deployment";
            case 15:
               return "deployment-order";
            case 16:
               return "deployment-principal-name";
            case 17:
               return "partition-name";
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
            case 14:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 14:
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends TargetInfoMBeanImpl.Helper {
      private BasicDeploymentMBeanImpl bean;

      protected Helper(BasicDeploymentMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 13:
               return "SourcePath";
            case 14:
               return "SubDeployments";
            case 15:
               return "DeploymentOrder";
            case 16:
               return "DeploymentPrincipalName";
            case 17:
               return "PartitionName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DeploymentOrder")) {
            return 15;
         } else if (propName.equals("DeploymentPrincipalName")) {
            return 16;
         } else if (propName.equals("PartitionName")) {
            return 17;
         } else if (propName.equals("SourcePath")) {
            return 13;
         } else {
            return propName.equals("SubDeployments") ? 14 : super.getPropertyIndex(propName);
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
            if (this.bean.isDeploymentOrderSet()) {
               buf.append("DeploymentOrder");
               buf.append(String.valueOf(this.bean.getDeploymentOrder()));
            }

            if (this.bean.isDeploymentPrincipalNameSet()) {
               buf.append("DeploymentPrincipalName");
               buf.append(String.valueOf(this.bean.getDeploymentPrincipalName()));
            }

            if (this.bean.isPartitionNameSet()) {
               buf.append("PartitionName");
               buf.append(String.valueOf(this.bean.getPartitionName()));
            }

            if (this.bean.isSourcePathSet()) {
               buf.append("SourcePath");
               buf.append(String.valueOf(this.bean.getSourcePath()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getSubDeployments().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSubDeployments()[i]);
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
            BasicDeploymentMBeanImpl otherTyped = (BasicDeploymentMBeanImpl)other;
            this.computeDiff("DeploymentOrder", this.bean.getDeploymentOrder(), otherTyped.getDeploymentOrder(), false);
            this.computeDiff("DeploymentPrincipalName", this.bean.getDeploymentPrincipalName(), otherTyped.getDeploymentPrincipalName(), false);
            this.computeDiff("SourcePath", this.bean.getSourcePath(), otherTyped.getSourcePath(), false);
            this.computeChildDiff("SubDeployments", this.bean.getSubDeployments(), otherTyped.getSubDeployments(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            BasicDeploymentMBeanImpl original = (BasicDeploymentMBeanImpl)event.getSourceBean();
            BasicDeploymentMBeanImpl proposed = (BasicDeploymentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DeploymentOrder")) {
                  original.setDeploymentOrder(proposed.getDeploymentOrder());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("DeploymentPrincipalName")) {
                  original.setDeploymentPrincipalName(proposed.getDeploymentPrincipalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (!prop.equals("PartitionName")) {
                  if (prop.equals("SourcePath")) {
                     original.setSourcePath(proposed.getSourcePath());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
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
                        original._conditionalUnset(update.isUnsetUpdate(), 14);
                     }
                  } else {
                     super.applyPropertyUpdate(event, update);
                  }
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
            BasicDeploymentMBeanImpl copy = (BasicDeploymentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DeploymentOrder")) && this.bean.isDeploymentOrderSet()) {
               copy.setDeploymentOrder(this.bean.getDeploymentOrder());
            }

            if ((excludeProps == null || !excludeProps.contains("DeploymentPrincipalName")) && this.bean.isDeploymentPrincipalNameSet()) {
               copy.setDeploymentPrincipalName(this.bean.getDeploymentPrincipalName());
            }

            if ((excludeProps == null || !excludeProps.contains("SourcePath")) && this.bean.isSourcePathSet()) {
               copy.setSourcePath(this.bean.getSourcePath());
            }

            if ((excludeProps == null || !excludeProps.contains("SubDeployments")) && this.bean.isSubDeploymentsSet() && !copy._isSet(14)) {
               SubDeploymentMBean[] oldSubDeployments = this.bean.getSubDeployments();
               SubDeploymentMBean[] newSubDeployments = new SubDeploymentMBean[oldSubDeployments.length];

               for(int i = 0; i < newSubDeployments.length; ++i) {
                  newSubDeployments[i] = (SubDeploymentMBean)((SubDeploymentMBean)this.createCopy((AbstractDescriptorBean)oldSubDeployments[i], includeObsolete));
               }

               copy.setSubDeployments(newSubDeployments);
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

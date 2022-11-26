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
import weblogic.utils.ArrayUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WebServicePersistenceMBeanImpl extends ConfigurationMBeanImpl implements WebServicePersistenceMBean, Serializable {
   private String _DefaultLogicalStoreName;
   private WebServiceLogicalStoreMBean[] _WebServiceLogicalStores;
   private WebServicePhysicalStoreMBean[] _WebServicePhysicalStores;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private WebServicePersistenceMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(WebServicePersistenceMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(WebServicePersistenceMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public WebServicePersistenceMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(WebServicePersistenceMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      WebServicePersistenceMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public WebServicePersistenceMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebServicePersistenceMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebServicePersistenceMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDefaultLogicalStoreName() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getDefaultLogicalStoreName(), this) : this._DefaultLogicalStoreName;
   }

   public boolean isDefaultLogicalStoreNameInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isDefaultLogicalStoreNameSet() {
      return this._isSet(10);
   }

   public void setDefaultLogicalStoreName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LogicalStoreNameValidator.validateDefaultLogicalStoreName(this, param0);
      boolean wasSet = this._isSet(10);
      String _oldVal = this._DefaultLogicalStoreName;
      this._DefaultLogicalStoreName = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServicePersistenceMBeanImpl source = (WebServicePersistenceMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public WebServiceLogicalStoreMBean createWebServiceLogicalStore(String param0) {
      WebServiceLogicalStoreMBeanImpl lookup = (WebServiceLogicalStoreMBeanImpl)this.lookupWebServiceLogicalStore(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WebServiceLogicalStoreMBeanImpl _val = new WebServiceLogicalStoreMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWebServiceLogicalStore(_val);
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

   public void destroyWebServiceLogicalStore(WebServiceLogicalStoreMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         WebServiceLogicalStoreMBean[] _old = this.getWebServiceLogicalStores();
         WebServiceLogicalStoreMBean[] _new = (WebServiceLogicalStoreMBean[])((WebServiceLogicalStoreMBean[])this._getHelper()._removeElement(_old, WebServiceLogicalStoreMBean.class, param0));
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
                  WebServicePersistenceMBeanImpl source = (WebServicePersistenceMBeanImpl)var6.next();
                  WebServiceLogicalStoreMBeanImpl childImpl = (WebServiceLogicalStoreMBeanImpl)_child;
                  WebServiceLogicalStoreMBeanImpl lookup = (WebServiceLogicalStoreMBeanImpl)source.lookupWebServiceLogicalStore(childImpl.getName());
                  if (lookup != null) {
                     source.destroyWebServiceLogicalStore(lookup);
                  }
               }

               this.setWebServiceLogicalStores(_new);
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

   public void addWebServiceLogicalStore(WebServiceLogicalStoreMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         WebServiceLogicalStoreMBean[] _new;
         if (this._isSet(11)) {
            _new = (WebServiceLogicalStoreMBean[])((WebServiceLogicalStoreMBean[])this._getHelper()._extendArray(this.getWebServiceLogicalStores(), WebServiceLogicalStoreMBean.class, param0));
         } else {
            _new = new WebServiceLogicalStoreMBean[]{param0};
         }

         try {
            this.setWebServiceLogicalStores(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WebServiceLogicalStoreMBean[] getWebServiceLogicalStores() {
      WebServiceLogicalStoreMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(11)) {
         delegateArray = this._getDelegateBean().getWebServiceLogicalStores();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._WebServiceLogicalStores.length; ++j) {
               if (delegateArray[i].getName().equals(this._WebServiceLogicalStores[j].getName())) {
                  ((WebServiceLogicalStoreMBeanImpl)this._WebServiceLogicalStores[j])._setDelegateBean((WebServiceLogicalStoreMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  WebServiceLogicalStoreMBeanImpl mbean = new WebServiceLogicalStoreMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 11);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((WebServiceLogicalStoreMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(11)) {
                     this.setWebServiceLogicalStores((WebServiceLogicalStoreMBean[])((WebServiceLogicalStoreMBean[])this._getHelper()._extendArray(this._WebServiceLogicalStores, WebServiceLogicalStoreMBean.class, mbean)));
                  } else {
                     this.setWebServiceLogicalStores(new WebServiceLogicalStoreMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new WebServiceLogicalStoreMBean[0];
      }

      if (this._WebServiceLogicalStores != null) {
         List removeList = new ArrayList();
         WebServiceLogicalStoreMBean[] var18 = this._WebServiceLogicalStores;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            WebServiceLogicalStoreMBean bn = var18[var5];
            WebServiceLogicalStoreMBeanImpl bni = (WebServiceLogicalStoreMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               WebServiceLogicalStoreMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  WebServiceLogicalStoreMBean delegateTo = var10[var12];
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
            WebServiceLogicalStoreMBean removeIt = (WebServiceLogicalStoreMBean)var19.next();
            WebServiceLogicalStoreMBeanImpl removeItImpl = (WebServiceLogicalStoreMBeanImpl)removeIt;
            WebServiceLogicalStoreMBean[] _new = (WebServiceLogicalStoreMBean[])((WebServiceLogicalStoreMBean[])this._getHelper()._removeElement(this._WebServiceLogicalStores, WebServiceLogicalStoreMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setWebServiceLogicalStores(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._WebServiceLogicalStores;
   }

   public boolean isWebServiceLogicalStoresInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(11)) {
         WebServiceLogicalStoreMBean[] elements = this.getWebServiceLogicalStores();
         WebServiceLogicalStoreMBean[] var2 = elements;
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

   public boolean isWebServiceLogicalStoresSet() {
      return this._isSet(11);
   }

   public void removeWebServiceLogicalStore(WebServiceLogicalStoreMBean param0) {
      this.destroyWebServiceLogicalStore(param0);
   }

   public void setWebServiceLogicalStores(WebServiceLogicalStoreMBean[] param0) throws InvalidAttributeValueException {
      WebServiceLogicalStoreMBean[] param0 = param0 == null ? new WebServiceLogicalStoreMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._WebServiceLogicalStores, (Object[])param0, handler, new Comparator() {
            public int compare(WebServiceLogicalStoreMBean o1, WebServiceLogicalStoreMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            WebServiceLogicalStoreMBean bean = (WebServiceLogicalStoreMBean)var3.next();
            WebServiceLogicalStoreMBeanImpl beanImpl = (WebServiceLogicalStoreMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(11);
      WebServiceLogicalStoreMBean[] _oldVal = this._WebServiceLogicalStores;
      this._WebServiceLogicalStores = (WebServiceLogicalStoreMBean[])param0;
      this._postSet(11, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         WebServicePersistenceMBeanImpl source = (WebServicePersistenceMBeanImpl)var11.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public WebServiceLogicalStoreMBean lookupWebServiceLogicalStore(String param0) {
      Object[] aary = (Object[])this.getWebServiceLogicalStores();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WebServiceLogicalStoreMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WebServiceLogicalStoreMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public WebServicePhysicalStoreMBean createWebServicePhysicalStore(String param0) {
      WebServicePhysicalStoreMBeanImpl lookup = (WebServicePhysicalStoreMBeanImpl)this.lookupWebServicePhysicalStore(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WebServicePhysicalStoreMBeanImpl _val = new WebServicePhysicalStoreMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWebServicePhysicalStore(_val);
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

   public void destroyWebServicePhysicalStore(WebServicePhysicalStoreMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         WebServicePhysicalStoreMBean[] _old = this.getWebServicePhysicalStores();
         WebServicePhysicalStoreMBean[] _new = (WebServicePhysicalStoreMBean[])((WebServicePhysicalStoreMBean[])this._getHelper()._removeElement(_old, WebServicePhysicalStoreMBean.class, param0));
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
                  WebServicePersistenceMBeanImpl source = (WebServicePersistenceMBeanImpl)var6.next();
                  WebServicePhysicalStoreMBeanImpl childImpl = (WebServicePhysicalStoreMBeanImpl)_child;
                  WebServicePhysicalStoreMBeanImpl lookup = (WebServicePhysicalStoreMBeanImpl)source.lookupWebServicePhysicalStore(childImpl.getName());
                  if (lookup != null) {
                     source.destroyWebServicePhysicalStore(lookup);
                  }
               }

               this.setWebServicePhysicalStores(_new);
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

   public void addWebServicePhysicalStore(WebServicePhysicalStoreMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         WebServicePhysicalStoreMBean[] _new;
         if (this._isSet(12)) {
            _new = (WebServicePhysicalStoreMBean[])((WebServicePhysicalStoreMBean[])this._getHelper()._extendArray(this.getWebServicePhysicalStores(), WebServicePhysicalStoreMBean.class, param0));
         } else {
            _new = new WebServicePhysicalStoreMBean[]{param0};
         }

         try {
            this.setWebServicePhysicalStores(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WebServicePhysicalStoreMBean[] getWebServicePhysicalStores() {
      WebServicePhysicalStoreMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(12)) {
         delegateArray = this._getDelegateBean().getWebServicePhysicalStores();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._WebServicePhysicalStores.length; ++j) {
               if (delegateArray[i].getName().equals(this._WebServicePhysicalStores[j].getName())) {
                  ((WebServicePhysicalStoreMBeanImpl)this._WebServicePhysicalStores[j])._setDelegateBean((WebServicePhysicalStoreMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  WebServicePhysicalStoreMBeanImpl mbean = new WebServicePhysicalStoreMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 12);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((WebServicePhysicalStoreMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(12)) {
                     this.setWebServicePhysicalStores((WebServicePhysicalStoreMBean[])((WebServicePhysicalStoreMBean[])this._getHelper()._extendArray(this._WebServicePhysicalStores, WebServicePhysicalStoreMBean.class, mbean)));
                  } else {
                     this.setWebServicePhysicalStores(new WebServicePhysicalStoreMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new WebServicePhysicalStoreMBean[0];
      }

      if (this._WebServicePhysicalStores != null) {
         List removeList = new ArrayList();
         WebServicePhysicalStoreMBean[] var18 = this._WebServicePhysicalStores;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            WebServicePhysicalStoreMBean bn = var18[var5];
            WebServicePhysicalStoreMBeanImpl bni = (WebServicePhysicalStoreMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               WebServicePhysicalStoreMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  WebServicePhysicalStoreMBean delegateTo = var10[var12];
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
            WebServicePhysicalStoreMBean removeIt = (WebServicePhysicalStoreMBean)var19.next();
            WebServicePhysicalStoreMBeanImpl removeItImpl = (WebServicePhysicalStoreMBeanImpl)removeIt;
            WebServicePhysicalStoreMBean[] _new = (WebServicePhysicalStoreMBean[])((WebServicePhysicalStoreMBean[])this._getHelper()._removeElement(this._WebServicePhysicalStores, WebServicePhysicalStoreMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setWebServicePhysicalStores(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._WebServicePhysicalStores;
   }

   public boolean isWebServicePhysicalStoresInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(12)) {
         WebServicePhysicalStoreMBean[] elements = this.getWebServicePhysicalStores();
         WebServicePhysicalStoreMBean[] var2 = elements;
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

   public boolean isWebServicePhysicalStoresSet() {
      return this._isSet(12);
   }

   public void removeWebServicePhysicalStore(WebServicePhysicalStoreMBean param0) {
      this.destroyWebServicePhysicalStore(param0);
   }

   public void setWebServicePhysicalStores(WebServicePhysicalStoreMBean[] param0) throws InvalidAttributeValueException {
      WebServicePhysicalStoreMBean[] param0 = param0 == null ? new WebServicePhysicalStoreMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._WebServicePhysicalStores, (Object[])param0, handler, new Comparator() {
            public int compare(WebServicePhysicalStoreMBean o1, WebServicePhysicalStoreMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            WebServicePhysicalStoreMBean bean = (WebServicePhysicalStoreMBean)var3.next();
            WebServicePhysicalStoreMBeanImpl beanImpl = (WebServicePhysicalStoreMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(12);
      WebServicePhysicalStoreMBean[] _oldVal = this._WebServicePhysicalStores;
      this._WebServicePhysicalStores = (WebServicePhysicalStoreMBean[])param0;
      this._postSet(12, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         WebServicePersistenceMBeanImpl source = (WebServicePersistenceMBeanImpl)var11.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public WebServicePhysicalStoreMBean lookupWebServicePhysicalStore(String param0) {
      Object[] aary = (Object[])this.getWebServicePhysicalStores();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WebServicePhysicalStoreMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WebServicePhysicalStoreMBeanImpl)it.previous();
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._DefaultLogicalStoreName = "WseeStore";
               if (initOne) {
                  break;
               }
            case 11:
               this._WebServiceLogicalStores = new WebServiceLogicalStoreMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._WebServicePhysicalStores = new WebServicePhysicalStoreMBean[0];
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
      return "WebServicePersistence";
   }

   public void putValue(String name, Object v) {
      if (name.equals("DefaultLogicalStoreName")) {
         String oldVal = this._DefaultLogicalStoreName;
         this._DefaultLogicalStoreName = (String)v;
         this._postSet(10, oldVal, this._DefaultLogicalStoreName);
      } else if (name.equals("WebServiceLogicalStores")) {
         WebServiceLogicalStoreMBean[] oldVal = this._WebServiceLogicalStores;
         this._WebServiceLogicalStores = (WebServiceLogicalStoreMBean[])((WebServiceLogicalStoreMBean[])v);
         this._postSet(11, oldVal, this._WebServiceLogicalStores);
      } else if (name.equals("WebServicePhysicalStores")) {
         WebServicePhysicalStoreMBean[] oldVal = this._WebServicePhysicalStores;
         this._WebServicePhysicalStores = (WebServicePhysicalStoreMBean[])((WebServicePhysicalStoreMBean[])v);
         this._postSet(12, oldVal, this._WebServicePhysicalStores);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("DefaultLogicalStoreName")) {
         return this._DefaultLogicalStoreName;
      } else if (name.equals("WebServiceLogicalStores")) {
         return this._WebServiceLogicalStores;
      } else {
         return name.equals("WebServicePhysicalStores") ? this._WebServicePhysicalStores : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 25:
               if (s.equals("web-service-logical-store")) {
                  return 11;
               }
               break;
            case 26:
               if (s.equals("default-logical-store-name")) {
                  return 10;
               }

               if (s.equals("web-service-physical-store")) {
                  return 12;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 11:
               return new WebServiceLogicalStoreMBeanImpl.SchemaHelper2();
            case 12:
               return new WebServicePhysicalStoreMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "default-logical-store-name";
            case 11:
               return "web-service-logical-store";
            case 12:
               return "web-service-physical-store";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
            default:
               return super.isArray(propIndex);
            case 11:
               return true;
            case 12:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 11:
               return true;
            case 12:
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private WebServicePersistenceMBeanImpl bean;

      protected Helper(WebServicePersistenceMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "DefaultLogicalStoreName";
            case 11:
               return "WebServiceLogicalStores";
            case 12:
               return "WebServicePhysicalStores";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DefaultLogicalStoreName")) {
            return 10;
         } else if (propName.equals("WebServiceLogicalStores")) {
            return 11;
         } else {
            return propName.equals("WebServicePhysicalStores") ? 12 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getWebServiceLogicalStores()));
         iterators.add(new ArrayIterator(this.bean.getWebServicePhysicalStores()));
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
            if (this.bean.isDefaultLogicalStoreNameSet()) {
               buf.append("DefaultLogicalStoreName");
               buf.append(String.valueOf(this.bean.getDefaultLogicalStoreName()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getWebServiceLogicalStores().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWebServiceLogicalStores()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWebServicePhysicalStores().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWebServicePhysicalStores()[i]);
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
            WebServicePersistenceMBeanImpl otherTyped = (WebServicePersistenceMBeanImpl)other;
            this.computeDiff("DefaultLogicalStoreName", this.bean.getDefaultLogicalStoreName(), otherTyped.getDefaultLogicalStoreName(), true);
            this.computeChildDiff("WebServiceLogicalStores", this.bean.getWebServiceLogicalStores(), otherTyped.getWebServiceLogicalStores(), true);
            this.computeChildDiff("WebServicePhysicalStores", this.bean.getWebServicePhysicalStores(), otherTyped.getWebServicePhysicalStores(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebServicePersistenceMBeanImpl original = (WebServicePersistenceMBeanImpl)event.getSourceBean();
            WebServicePersistenceMBeanImpl proposed = (WebServicePersistenceMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DefaultLogicalStoreName")) {
                  original.setDefaultLogicalStoreName(proposed.getDefaultLogicalStoreName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("WebServiceLogicalStores")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWebServiceLogicalStore((WebServiceLogicalStoreMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWebServiceLogicalStore((WebServiceLogicalStoreMBean)update.getRemovedObject());
                  }

                  if (original.getWebServiceLogicalStores() == null || original.getWebServiceLogicalStores().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("WebServicePhysicalStores")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWebServicePhysicalStore((WebServicePhysicalStoreMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWebServicePhysicalStore((WebServicePhysicalStoreMBean)update.getRemovedObject());
                  }

                  if (original.getWebServicePhysicalStores() == null || original.getWebServicePhysicalStores().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            WebServicePersistenceMBeanImpl copy = (WebServicePersistenceMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DefaultLogicalStoreName")) && this.bean.isDefaultLogicalStoreNameSet()) {
               copy.setDefaultLogicalStoreName(this.bean.getDefaultLogicalStoreName());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("WebServiceLogicalStores")) && this.bean.isWebServiceLogicalStoresSet() && !copy._isSet(11)) {
               WebServiceLogicalStoreMBean[] oldWebServiceLogicalStores = this.bean.getWebServiceLogicalStores();
               WebServiceLogicalStoreMBean[] newWebServiceLogicalStores = new WebServiceLogicalStoreMBean[oldWebServiceLogicalStores.length];

               for(i = 0; i < newWebServiceLogicalStores.length; ++i) {
                  newWebServiceLogicalStores[i] = (WebServiceLogicalStoreMBean)((WebServiceLogicalStoreMBean)this.createCopy((AbstractDescriptorBean)oldWebServiceLogicalStores[i], includeObsolete));
               }

               copy.setWebServiceLogicalStores(newWebServiceLogicalStores);
            }

            if ((excludeProps == null || !excludeProps.contains("WebServicePhysicalStores")) && this.bean.isWebServicePhysicalStoresSet() && !copy._isSet(12)) {
               WebServicePhysicalStoreMBean[] oldWebServicePhysicalStores = this.bean.getWebServicePhysicalStores();
               WebServicePhysicalStoreMBean[] newWebServicePhysicalStores = new WebServicePhysicalStoreMBean[oldWebServicePhysicalStores.length];

               for(i = 0; i < newWebServicePhysicalStores.length; ++i) {
                  newWebServicePhysicalStores[i] = (WebServicePhysicalStoreMBean)((WebServicePhysicalStoreMBean)this.createCopy((AbstractDescriptorBean)oldWebServicePhysicalStores[i], includeObsolete));
               }

               copy.setWebServicePhysicalStores(newWebServicePhysicalStores);
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
         this.inferSubTree(this.bean.getWebServiceLogicalStores(), clazz, annotation);
         this.inferSubTree(this.bean.getWebServicePhysicalStores(), clazz, annotation);
      }
   }
}

package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WebAppBaseBeanImpl extends AbstractDescriptorBean implements WebAppBaseBean, Serializable {
   private AdministeredObjectBean[] _AdministeredObjects;
   private ConnectionFactoryResourceBean[] _ConnectionFactories;
   private ParamValueBean[] _ContextParams;
   private DataSourceBean[] _DataSources;
   private String[] _Descriptions;
   private String[] _DisplayNames;
   private EmptyBean[] _Distributables;
   private EjbLocalRefBean[] _EjbLocalRefs;
   private EjbRefBean[] _EjbRefs;
   private EnvEntryBean[] _EnvEntries;
   private ErrorPageBean[] _ErrorPages;
   private FilterMappingBean[] _FilterMappings;
   private FilterBean[] _Filters;
   private IconBean[] _Icons;
   private String _Id;
   private JmsConnectionFactoryBean[] _JmsConnectionFactories;
   private JmsDestinationBean[] _JmsDestinations;
   private JspConfigBean[] _JspConfigs;
   private ListenerBean[] _Listeners;
   private LocaleEncodingMappingListBean[] _LocaleEncodingMappingLists;
   private LoginConfigBean[] _LoginConfigs;
   private MailSessionBean[] _MailSessions;
   private MessageDestinationRefBean[] _MessageDestinationRefs;
   private MessageDestinationBean[] _MessageDestinations;
   private boolean _MetadataComplete;
   private MimeMappingBean[] _MimeMappings;
   private PersistenceContextRefBean[] _PersistenceContextRefs;
   private PersistenceUnitRefBean[] _PersistenceUnitRefs;
   private LifecycleCallbackBean[] _PostConstructs;
   private LifecycleCallbackBean[] _PreDestroys;
   private ResourceEnvRefBean[] _ResourceEnvRefs;
   private ResourceRefBean[] _ResourceRefs;
   private SecurityConstraintBean[] _SecurityConstraints;
   private SecurityRoleBean[] _SecurityRoles;
   private ServiceRefBean[] _ServiceRefs;
   private ServletMappingBean[] _ServletMappings;
   private ServletBean[] _Servlets;
   private SessionConfigBean[] _SessionConfigs;
   private String _Version;
   private WelcomeFileListBean[] _WelcomeFileLists;
   private static SchemaHelper2 _schemaHelper;

   public WebAppBaseBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebAppBaseBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebAppBaseBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String[] getDescriptions() {
      return this._Descriptions;
   }

   public boolean isDescriptionsInherited() {
      return false;
   }

   public boolean isDescriptionsSet() {
      return this._isSet(0);
   }

   public void addDescription(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(0)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDescriptions(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDescriptions(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDescription(String param0) {
      String[] _old = this.getDescriptions();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDescriptions(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Descriptions;
      this._Descriptions = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String[] getDisplayNames() {
      return this._DisplayNames;
   }

   public boolean isDisplayNamesInherited() {
      return false;
   }

   public boolean isDisplayNamesSet() {
      return this._isSet(1);
   }

   public void addDisplayName(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(1)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDisplayNames(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDisplayNames(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDisplayName(String param0) {
      String[] _old = this.getDisplayNames();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDisplayNames(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDisplayNames(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._DisplayNames;
      this._DisplayNames = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addIcon(IconBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         IconBean[] _new;
         if (this._isSet(2)) {
            _new = (IconBean[])((IconBean[])this._getHelper()._extendArray(this.getIcons(), IconBean.class, param0));
         } else {
            _new = new IconBean[]{param0};
         }

         try {
            this.setIcons(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public IconBean[] getIcons() {
      return this._Icons;
   }

   public boolean isIconsInherited() {
      return false;
   }

   public boolean isIconsSet() {
      return this._isSet(2);
   }

   public void removeIcon(IconBean param0) {
      this.destroyIcon(param0);
   }

   public void setIcons(IconBean[] param0) throws InvalidAttributeValueException {
      IconBean[] param0 = param0 == null ? new IconBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      IconBean[] _oldVal = this._Icons;
      this._Icons = (IconBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public IconBean createIcon() {
      IconBeanImpl _val = new IconBeanImpl(this, -1);

      try {
         this.addIcon(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyIcon(IconBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         IconBean[] _old = this.getIcons();
         IconBean[] _new = (IconBean[])((IconBean[])this._getHelper()._removeElement(_old, IconBean.class, param0));
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
               this.setIcons(_new);
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

   public void addDistributable(EmptyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         EmptyBean[] _new;
         if (this._isSet(3)) {
            _new = (EmptyBean[])((EmptyBean[])this._getHelper()._extendArray(this.getDistributables(), EmptyBean.class, param0));
         } else {
            _new = new EmptyBean[]{param0};
         }

         try {
            this.setDistributables(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EmptyBean[] getDistributables() {
      return this._Distributables;
   }

   public boolean isDistributablesInherited() {
      return false;
   }

   public boolean isDistributablesSet() {
      return this._isSet(3);
   }

   public void removeDistributable(EmptyBean param0) {
      this.destroyDistributable(param0);
   }

   public void setDistributables(EmptyBean[] param0) throws InvalidAttributeValueException {
      EmptyBean[] param0 = param0 == null ? new EmptyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      EmptyBean[] _oldVal = this._Distributables;
      this._Distributables = (EmptyBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public EmptyBean createDistributable() {
      EmptyBeanImpl _val = new EmptyBeanImpl(this, -1);

      try {
         this.addDistributable(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDistributable(EmptyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         EmptyBean[] _old = this.getDistributables();
         EmptyBean[] _new = (EmptyBean[])((EmptyBean[])this._getHelper()._removeElement(_old, EmptyBean.class, param0));
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
               this.setDistributables(_new);
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

   public void addContextParam(ParamValueBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         ParamValueBean[] _new;
         if (this._isSet(4)) {
            _new = (ParamValueBean[])((ParamValueBean[])this._getHelper()._extendArray(this.getContextParams(), ParamValueBean.class, param0));
         } else {
            _new = new ParamValueBean[]{param0};
         }

         try {
            this.setContextParams(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ParamValueBean[] getContextParams() {
      return this._ContextParams;
   }

   public boolean isContextParamsInherited() {
      return false;
   }

   public boolean isContextParamsSet() {
      return this._isSet(4);
   }

   public void removeContextParam(ParamValueBean param0) {
      this.destroyContextParam(param0);
   }

   public void setContextParams(ParamValueBean[] param0) throws InvalidAttributeValueException {
      ParamValueBean[] param0 = param0 == null ? new ParamValueBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ParamValueBean[] _oldVal = this._ContextParams;
      this._ContextParams = (ParamValueBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public ParamValueBean createContextParam() {
      ParamValueBeanImpl _val = new ParamValueBeanImpl(this, -1);

      try {
         this.addContextParam(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyContextParam(ParamValueBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         ParamValueBean[] _old = this.getContextParams();
         ParamValueBean[] _new = (ParamValueBean[])((ParamValueBean[])this._getHelper()._removeElement(_old, ParamValueBean.class, param0));
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
               this.setContextParams(_new);
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

   public void addFilter(FilterBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         FilterBean[] _new;
         if (this._isSet(5)) {
            _new = (FilterBean[])((FilterBean[])this._getHelper()._extendArray(this.getFilters(), FilterBean.class, param0));
         } else {
            _new = new FilterBean[]{param0};
         }

         try {
            this.setFilters(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public FilterBean[] getFilters() {
      return this._Filters;
   }

   public boolean isFiltersInherited() {
      return false;
   }

   public boolean isFiltersSet() {
      return this._isSet(5);
   }

   public void removeFilter(FilterBean param0) {
      this.destroyFilter(param0);
   }

   public void setFilters(FilterBean[] param0) throws InvalidAttributeValueException {
      FilterBean[] param0 = param0 == null ? new FilterBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      FilterBean[] _oldVal = this._Filters;
      this._Filters = (FilterBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public FilterBean createFilter() {
      FilterBeanImpl _val = new FilterBeanImpl(this, -1);

      try {
         this.addFilter(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyFilter(FilterBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         FilterBean[] _old = this.getFilters();
         FilterBean[] _new = (FilterBean[])((FilterBean[])this._getHelper()._removeElement(_old, FilterBean.class, param0));
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
               this.setFilters(_new);
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

   public FilterBean lookupFilter(String param0) {
      Object[] aary = (Object[])this._Filters;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      FilterBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (FilterBeanImpl)it.previous();
      } while(!bean.getFilterName().equals(param0));

      return bean;
   }

   public FilterBean createFilter(String param0) {
      FilterBeanImpl _val = new FilterBeanImpl(this, -1);

      try {
         _val.setFilterName(param0);
         this.addFilter(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void addFilterMapping(FilterMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         FilterMappingBean[] _new;
         if (this._isSet(6)) {
            _new = (FilterMappingBean[])((FilterMappingBean[])this._getHelper()._extendArray(this.getFilterMappings(), FilterMappingBean.class, param0));
         } else {
            _new = new FilterMappingBean[]{param0};
         }

         try {
            this.setFilterMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public FilterMappingBean[] getFilterMappings() {
      return this._FilterMappings;
   }

   public boolean isFilterMappingsInherited() {
      return false;
   }

   public boolean isFilterMappingsSet() {
      return this._isSet(6);
   }

   public void removeFilterMapping(FilterMappingBean param0) {
      this.destroyFilterMapping(param0);
   }

   public void setFilterMappings(FilterMappingBean[] param0) throws InvalidAttributeValueException {
      FilterMappingBean[] param0 = param0 == null ? new FilterMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      FilterMappingBean[] _oldVal = this._FilterMappings;
      this._FilterMappings = (FilterMappingBean[])param0;
      this._postSet(6, _oldVal, param0);
   }

   public FilterMappingBean createFilterMapping() {
      FilterMappingBeanImpl _val = new FilterMappingBeanImpl(this, -1);

      try {
         this.addFilterMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyFilterMapping(FilterMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 6);
         FilterMappingBean[] _old = this.getFilterMappings();
         FilterMappingBean[] _new = (FilterMappingBean[])((FilterMappingBean[])this._getHelper()._removeElement(_old, FilterMappingBean.class, param0));
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
               this.setFilterMappings(_new);
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

   public void addListener(ListenerBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 7)) {
         ListenerBean[] _new;
         if (this._isSet(7)) {
            _new = (ListenerBean[])((ListenerBean[])this._getHelper()._extendArray(this.getListeners(), ListenerBean.class, param0));
         } else {
            _new = new ListenerBean[]{param0};
         }

         try {
            this.setListeners(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ListenerBean[] getListeners() {
      return this._Listeners;
   }

   public boolean isListenersInherited() {
      return false;
   }

   public boolean isListenersSet() {
      return this._isSet(7);
   }

   public void removeListener(ListenerBean param0) {
      this.destroyListener(param0);
   }

   public void setListeners(ListenerBean[] param0) throws InvalidAttributeValueException {
      ListenerBean[] param0 = param0 == null ? new ListenerBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 7)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ListenerBean[] _oldVal = this._Listeners;
      this._Listeners = (ListenerBean[])param0;
      this._postSet(7, _oldVal, param0);
   }

   public ListenerBean createListener() {
      ListenerBeanImpl _val = new ListenerBeanImpl(this, -1);

      try {
         this.addListener(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyListener(ListenerBean param0) {
      try {
         this._checkIsPotentialChild(param0, 7);
         ListenerBean[] _old = this.getListeners();
         ListenerBean[] _new = (ListenerBean[])((ListenerBean[])this._getHelper()._removeElement(_old, ListenerBean.class, param0));
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
               this.setListeners(_new);
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

   public void addServlet(ServletBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 8)) {
         ServletBean[] _new;
         if (this._isSet(8)) {
            _new = (ServletBean[])((ServletBean[])this._getHelper()._extendArray(this.getServlets(), ServletBean.class, param0));
         } else {
            _new = new ServletBean[]{param0};
         }

         try {
            this.setServlets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServletBean[] getServlets() {
      return this._Servlets;
   }

   public boolean isServletsInherited() {
      return false;
   }

   public boolean isServletsSet() {
      return this._isSet(8);
   }

   public void removeServlet(ServletBean param0) {
      this.destroyServlet(param0);
   }

   public void setServlets(ServletBean[] param0) throws InvalidAttributeValueException {
      ServletBean[] param0 = param0 == null ? new ServletBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 8)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServletBean[] _oldVal = this._Servlets;
      this._Servlets = (ServletBean[])param0;
      this._postSet(8, _oldVal, param0);
   }

   public ServletBean createServlet() {
      ServletBeanImpl _val = new ServletBeanImpl(this, -1);

      try {
         this.addServlet(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyServlet(ServletBean param0) {
      try {
         this._checkIsPotentialChild(param0, 8);
         ServletBean[] _old = this.getServlets();
         ServletBean[] _new = (ServletBean[])((ServletBean[])this._getHelper()._removeElement(_old, ServletBean.class, param0));
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
               this.setServlets(_new);
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

   public ServletBean lookupServlet(String param0) {
      Object[] aary = (Object[])this._Servlets;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ServletBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ServletBeanImpl)it.previous();
      } while(!bean.getServletName().equals(param0));

      return bean;
   }

   public ServletBean createServlet(String param0) {
      ServletBeanImpl _val = new ServletBeanImpl(this, -1);

      try {
         _val.setServletName(param0);
         this.addServlet(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void addServletMapping(ServletMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
         ServletMappingBean[] _new;
         if (this._isSet(9)) {
            _new = (ServletMappingBean[])((ServletMappingBean[])this._getHelper()._extendArray(this.getServletMappings(), ServletMappingBean.class, param0));
         } else {
            _new = new ServletMappingBean[]{param0};
         }

         try {
            this.setServletMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServletMappingBean[] getServletMappings() {
      return this._ServletMappings;
   }

   public boolean isServletMappingsInherited() {
      return false;
   }

   public boolean isServletMappingsSet() {
      return this._isSet(9);
   }

   public void removeServletMapping(ServletMappingBean param0) {
      this.destroyServletMapping(param0);
   }

   public void setServletMappings(ServletMappingBean[] param0) throws InvalidAttributeValueException {
      ServletMappingBean[] param0 = param0 == null ? new ServletMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 9)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServletMappingBean[] _oldVal = this._ServletMappings;
      this._ServletMappings = (ServletMappingBean[])param0;
      this._postSet(9, _oldVal, param0);
   }

   public ServletMappingBean createServletMapping() {
      ServletMappingBeanImpl _val = new ServletMappingBeanImpl(this, -1);

      try {
         this.addServletMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyServletMapping(ServletMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 9);
         ServletMappingBean[] _old = this.getServletMappings();
         ServletMappingBean[] _new = (ServletMappingBean[])((ServletMappingBean[])this._getHelper()._removeElement(_old, ServletMappingBean.class, param0));
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
               this.setServletMappings(_new);
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

   public void addSessionConfig(SessionConfigBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         SessionConfigBean[] _new;
         if (this._isSet(10)) {
            _new = (SessionConfigBean[])((SessionConfigBean[])this._getHelper()._extendArray(this.getSessionConfigs(), SessionConfigBean.class, param0));
         } else {
            _new = new SessionConfigBean[]{param0};
         }

         try {
            this.setSessionConfigs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SessionConfigBean[] getSessionConfigs() {
      return this._SessionConfigs;
   }

   public boolean isSessionConfigsInherited() {
      return false;
   }

   public boolean isSessionConfigsSet() {
      return this._isSet(10);
   }

   public void removeSessionConfig(SessionConfigBean param0) {
      this.destroySessionConfig(param0);
   }

   public void setSessionConfigs(SessionConfigBean[] param0) throws InvalidAttributeValueException {
      SessionConfigBean[] param0 = param0 == null ? new SessionConfigBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SessionConfigBean[] _oldVal = this._SessionConfigs;
      this._SessionConfigs = (SessionConfigBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public SessionConfigBean createSessionConfig() {
      SessionConfigBeanImpl _val = new SessionConfigBeanImpl(this, -1);

      try {
         this.addSessionConfig(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySessionConfig(SessionConfigBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         SessionConfigBean[] _old = this.getSessionConfigs();
         SessionConfigBean[] _new = (SessionConfigBean[])((SessionConfigBean[])this._getHelper()._removeElement(_old, SessionConfigBean.class, param0));
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
               this.setSessionConfigs(_new);
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

   public void addMimeMapping(MimeMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         MimeMappingBean[] _new;
         if (this._isSet(11)) {
            _new = (MimeMappingBean[])((MimeMappingBean[])this._getHelper()._extendArray(this.getMimeMappings(), MimeMappingBean.class, param0));
         } else {
            _new = new MimeMappingBean[]{param0};
         }

         try {
            this.setMimeMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MimeMappingBean[] getMimeMappings() {
      return this._MimeMappings;
   }

   public boolean isMimeMappingsInherited() {
      return false;
   }

   public boolean isMimeMappingsSet() {
      return this._isSet(11);
   }

   public void removeMimeMapping(MimeMappingBean param0) {
      this.destroyMimeMapping(param0);
   }

   public void setMimeMappings(MimeMappingBean[] param0) throws InvalidAttributeValueException {
      MimeMappingBean[] param0 = param0 == null ? new MimeMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MimeMappingBean[] _oldVal = this._MimeMappings;
      this._MimeMappings = (MimeMappingBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public MimeMappingBean createMimeMapping() {
      MimeMappingBeanImpl _val = new MimeMappingBeanImpl(this, -1);

      try {
         this.addMimeMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMimeMapping(MimeMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         MimeMappingBean[] _old = this.getMimeMappings();
         MimeMappingBean[] _new = (MimeMappingBean[])((MimeMappingBean[])this._getHelper()._removeElement(_old, MimeMappingBean.class, param0));
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
               this.setMimeMappings(_new);
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

   public void addWelcomeFileList(WelcomeFileListBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         WelcomeFileListBean[] _new;
         if (this._isSet(12)) {
            _new = (WelcomeFileListBean[])((WelcomeFileListBean[])this._getHelper()._extendArray(this.getWelcomeFileLists(), WelcomeFileListBean.class, param0));
         } else {
            _new = new WelcomeFileListBean[]{param0};
         }

         try {
            this.setWelcomeFileLists(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WelcomeFileListBean[] getWelcomeFileLists() {
      return this._WelcomeFileLists;
   }

   public boolean isWelcomeFileListsInherited() {
      return false;
   }

   public boolean isWelcomeFileListsSet() {
      return this._isSet(12);
   }

   public void removeWelcomeFileList(WelcomeFileListBean param0) {
      this.destroyWelcomeFileList(param0);
   }

   public void setWelcomeFileLists(WelcomeFileListBean[] param0) throws InvalidAttributeValueException {
      WelcomeFileListBean[] param0 = param0 == null ? new WelcomeFileListBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WelcomeFileListBean[] _oldVal = this._WelcomeFileLists;
      this._WelcomeFileLists = (WelcomeFileListBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public WelcomeFileListBean createWelcomeFileList() {
      WelcomeFileListBeanImpl _val = new WelcomeFileListBeanImpl(this, -1);

      try {
         this.addWelcomeFileList(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWelcomeFileList(WelcomeFileListBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         WelcomeFileListBean[] _old = this.getWelcomeFileLists();
         WelcomeFileListBean[] _new = (WelcomeFileListBean[])((WelcomeFileListBean[])this._getHelper()._removeElement(_old, WelcomeFileListBean.class, param0));
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
               this.setWelcomeFileLists(_new);
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

   public void addErrorPage(ErrorPageBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         ErrorPageBean[] _new;
         if (this._isSet(13)) {
            _new = (ErrorPageBean[])((ErrorPageBean[])this._getHelper()._extendArray(this.getErrorPages(), ErrorPageBean.class, param0));
         } else {
            _new = new ErrorPageBean[]{param0};
         }

         try {
            this.setErrorPages(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ErrorPageBean[] getErrorPages() {
      return this._ErrorPages;
   }

   public boolean isErrorPagesInherited() {
      return false;
   }

   public boolean isErrorPagesSet() {
      return this._isSet(13);
   }

   public void removeErrorPage(ErrorPageBean param0) {
      this.destroyErrorPage(param0);
   }

   public void setErrorPages(ErrorPageBean[] param0) throws InvalidAttributeValueException {
      ErrorPageBean[] param0 = param0 == null ? new ErrorPageBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ErrorPageBean[] _oldVal = this._ErrorPages;
      this._ErrorPages = (ErrorPageBean[])param0;
      this._postSet(13, _oldVal, param0);
   }

   public ErrorPageBean createErrorPage() {
      ErrorPageBeanImpl _val = new ErrorPageBeanImpl(this, -1);

      try {
         this.addErrorPage(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyErrorPage(ErrorPageBean param0) {
      try {
         this._checkIsPotentialChild(param0, 13);
         ErrorPageBean[] _old = this.getErrorPages();
         ErrorPageBean[] _new = (ErrorPageBean[])((ErrorPageBean[])this._getHelper()._removeElement(_old, ErrorPageBean.class, param0));
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
               this.setErrorPages(_new);
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

   public void addJspConfig(JspConfigBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         JspConfigBean[] _new;
         if (this._isSet(14)) {
            _new = (JspConfigBean[])((JspConfigBean[])this._getHelper()._extendArray(this.getJspConfigs(), JspConfigBean.class, param0));
         } else {
            _new = new JspConfigBean[]{param0};
         }

         try {
            this.setJspConfigs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JspConfigBean[] getJspConfigs() {
      return this._JspConfigs;
   }

   public boolean isJspConfigsInherited() {
      return false;
   }

   public boolean isJspConfigsSet() {
      return this._isSet(14);
   }

   public void removeJspConfig(JspConfigBean param0) {
      this.destroyJspConfig(param0);
   }

   public void setJspConfigs(JspConfigBean[] param0) throws InvalidAttributeValueException {
      JspConfigBean[] param0 = param0 == null ? new JspConfigBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JspConfigBean[] _oldVal = this._JspConfigs;
      this._JspConfigs = (JspConfigBean[])param0;
      this._postSet(14, _oldVal, param0);
   }

   public JspConfigBean createJspConfig() {
      JspConfigBeanImpl _val = new JspConfigBeanImpl(this, -1);

      try {
         this.addJspConfig(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJspConfig(JspConfigBean param0) {
      try {
         this._checkIsPotentialChild(param0, 14);
         JspConfigBean[] _old = this.getJspConfigs();
         JspConfigBean[] _new = (JspConfigBean[])((JspConfigBean[])this._getHelper()._removeElement(_old, JspConfigBean.class, param0));
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
               this.setJspConfigs(_new);
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

   public void addSecurityConstraint(SecurityConstraintBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         SecurityConstraintBean[] _new;
         if (this._isSet(15)) {
            _new = (SecurityConstraintBean[])((SecurityConstraintBean[])this._getHelper()._extendArray(this.getSecurityConstraints(), SecurityConstraintBean.class, param0));
         } else {
            _new = new SecurityConstraintBean[]{param0};
         }

         try {
            this.setSecurityConstraints(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SecurityConstraintBean[] getSecurityConstraints() {
      return this._SecurityConstraints;
   }

   public boolean isSecurityConstraintsInherited() {
      return false;
   }

   public boolean isSecurityConstraintsSet() {
      return this._isSet(15);
   }

   public void removeSecurityConstraint(SecurityConstraintBean param0) {
      this.destroySecurityConstraint(param0);
   }

   public void setSecurityConstraints(SecurityConstraintBean[] param0) throws InvalidAttributeValueException {
      SecurityConstraintBean[] param0 = param0 == null ? new SecurityConstraintBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 15)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SecurityConstraintBean[] _oldVal = this._SecurityConstraints;
      this._SecurityConstraints = (SecurityConstraintBean[])param0;
      this._postSet(15, _oldVal, param0);
   }

   public SecurityConstraintBean createSecurityConstraint() {
      SecurityConstraintBeanImpl _val = new SecurityConstraintBeanImpl(this, -1);

      try {
         this.addSecurityConstraint(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySecurityConstraint(SecurityConstraintBean param0) {
      try {
         this._checkIsPotentialChild(param0, 15);
         SecurityConstraintBean[] _old = this.getSecurityConstraints();
         SecurityConstraintBean[] _new = (SecurityConstraintBean[])((SecurityConstraintBean[])this._getHelper()._removeElement(_old, SecurityConstraintBean.class, param0));
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
               this.setSecurityConstraints(_new);
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

   public void addLoginConfig(LoginConfigBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 16)) {
         LoginConfigBean[] _new;
         if (this._isSet(16)) {
            _new = (LoginConfigBean[])((LoginConfigBean[])this._getHelper()._extendArray(this.getLoginConfigs(), LoginConfigBean.class, param0));
         } else {
            _new = new LoginConfigBean[]{param0};
         }

         try {
            this.setLoginConfigs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LoginConfigBean[] getLoginConfigs() {
      return this._LoginConfigs;
   }

   public boolean isLoginConfigsInherited() {
      return false;
   }

   public boolean isLoginConfigsSet() {
      return this._isSet(16);
   }

   public void removeLoginConfig(LoginConfigBean param0) {
      this.destroyLoginConfig(param0);
   }

   public void setLoginConfigs(LoginConfigBean[] param0) throws InvalidAttributeValueException {
      LoginConfigBean[] param0 = param0 == null ? new LoginConfigBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 16)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LoginConfigBean[] _oldVal = this._LoginConfigs;
      this._LoginConfigs = (LoginConfigBean[])param0;
      this._postSet(16, _oldVal, param0);
   }

   public LoginConfigBean createLoginConfig() {
      LoginConfigBeanImpl _val = new LoginConfigBeanImpl(this, -1);

      try {
         this.addLoginConfig(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLoginConfig(LoginConfigBean param0) {
      try {
         this._checkIsPotentialChild(param0, 16);
         LoginConfigBean[] _old = this.getLoginConfigs();
         LoginConfigBean[] _new = (LoginConfigBean[])((LoginConfigBean[])this._getHelper()._removeElement(_old, LoginConfigBean.class, param0));
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
               this.setLoginConfigs(_new);
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

   public void addSecurityRole(SecurityRoleBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         SecurityRoleBean[] _new;
         if (this._isSet(17)) {
            _new = (SecurityRoleBean[])((SecurityRoleBean[])this._getHelper()._extendArray(this.getSecurityRoles(), SecurityRoleBean.class, param0));
         } else {
            _new = new SecurityRoleBean[]{param0};
         }

         try {
            this.setSecurityRoles(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SecurityRoleBean[] getSecurityRoles() {
      return this._SecurityRoles;
   }

   public boolean isSecurityRolesInherited() {
      return false;
   }

   public boolean isSecurityRolesSet() {
      return this._isSet(17);
   }

   public void removeSecurityRole(SecurityRoleBean param0) {
      this.destroySecurityRole(param0);
   }

   public void setSecurityRoles(SecurityRoleBean[] param0) throws InvalidAttributeValueException {
      SecurityRoleBean[] param0 = param0 == null ? new SecurityRoleBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 17)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SecurityRoleBean[] _oldVal = this._SecurityRoles;
      this._SecurityRoles = (SecurityRoleBean[])param0;
      this._postSet(17, _oldVal, param0);
   }

   public SecurityRoleBean createSecurityRole() {
      SecurityRoleBeanImpl _val = new SecurityRoleBeanImpl(this, -1);

      try {
         this.addSecurityRole(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySecurityRole(SecurityRoleBean param0) {
      try {
         this._checkIsPotentialChild(param0, 17);
         SecurityRoleBean[] _old = this.getSecurityRoles();
         SecurityRoleBean[] _new = (SecurityRoleBean[])((SecurityRoleBean[])this._getHelper()._removeElement(_old, SecurityRoleBean.class, param0));
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
               this.setSecurityRoles(_new);
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

   public void addEnvEntry(EnvEntryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 18)) {
         EnvEntryBean[] _new;
         if (this._isSet(18)) {
            _new = (EnvEntryBean[])((EnvEntryBean[])this._getHelper()._extendArray(this.getEnvEntries(), EnvEntryBean.class, param0));
         } else {
            _new = new EnvEntryBean[]{param0};
         }

         try {
            this.setEnvEntries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EnvEntryBean[] getEnvEntries() {
      return this._EnvEntries;
   }

   public boolean isEnvEntriesInherited() {
      return false;
   }

   public boolean isEnvEntriesSet() {
      return this._isSet(18);
   }

   public void removeEnvEntry(EnvEntryBean param0) {
      this.destroyEnvEntry(param0);
   }

   public void setEnvEntries(EnvEntryBean[] param0) throws InvalidAttributeValueException {
      EnvEntryBean[] param0 = param0 == null ? new EnvEntryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 18)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EnvEntryBean[] _oldVal = this._EnvEntries;
      this._EnvEntries = (EnvEntryBean[])param0;
      this._postSet(18, _oldVal, param0);
   }

   public EnvEntryBean createEnvEntry() {
      EnvEntryBeanImpl _val = new EnvEntryBeanImpl(this, -1);

      try {
         this.addEnvEntry(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEnvEntry(EnvEntryBean param0) {
      try {
         this._checkIsPotentialChild(param0, 18);
         EnvEntryBean[] _old = this.getEnvEntries();
         EnvEntryBean[] _new = (EnvEntryBean[])((EnvEntryBean[])this._getHelper()._removeElement(_old, EnvEntryBean.class, param0));
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
               this.setEnvEntries(_new);
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

   public void addEjbRef(EjbRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 19)) {
         EjbRefBean[] _new;
         if (this._isSet(19)) {
            _new = (EjbRefBean[])((EjbRefBean[])this._getHelper()._extendArray(this.getEjbRefs(), EjbRefBean.class, param0));
         } else {
            _new = new EjbRefBean[]{param0};
         }

         try {
            this.setEjbRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EjbRefBean[] getEjbRefs() {
      return this._EjbRefs;
   }

   public boolean isEjbRefsInherited() {
      return false;
   }

   public boolean isEjbRefsSet() {
      return this._isSet(19);
   }

   public void removeEjbRef(EjbRefBean param0) {
      this.destroyEjbRef(param0);
   }

   public void setEjbRefs(EjbRefBean[] param0) throws InvalidAttributeValueException {
      EjbRefBean[] param0 = param0 == null ? new EjbRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 19)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EjbRefBean[] _oldVal = this._EjbRefs;
      this._EjbRefs = (EjbRefBean[])param0;
      this._postSet(19, _oldVal, param0);
   }

   public EjbRefBean createEjbRef() {
      EjbRefBeanImpl _val = new EjbRefBeanImpl(this, -1);

      try {
         this.addEjbRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEjbRef(EjbRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 19);
         EjbRefBean[] _old = this.getEjbRefs();
         EjbRefBean[] _new = (EjbRefBean[])((EjbRefBean[])this._getHelper()._removeElement(_old, EjbRefBean.class, param0));
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
               this.setEjbRefs(_new);
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

   public void addEjbLocalRef(EjbLocalRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 20)) {
         EjbLocalRefBean[] _new;
         if (this._isSet(20)) {
            _new = (EjbLocalRefBean[])((EjbLocalRefBean[])this._getHelper()._extendArray(this.getEjbLocalRefs(), EjbLocalRefBean.class, param0));
         } else {
            _new = new EjbLocalRefBean[]{param0};
         }

         try {
            this.setEjbLocalRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EjbLocalRefBean[] getEjbLocalRefs() {
      return this._EjbLocalRefs;
   }

   public boolean isEjbLocalRefsInherited() {
      return false;
   }

   public boolean isEjbLocalRefsSet() {
      return this._isSet(20);
   }

   public void removeEjbLocalRef(EjbLocalRefBean param0) {
      this.destroyEjbLocalRef(param0);
   }

   public void setEjbLocalRefs(EjbLocalRefBean[] param0) throws InvalidAttributeValueException {
      EjbLocalRefBean[] param0 = param0 == null ? new EjbLocalRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 20)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EjbLocalRefBean[] _oldVal = this._EjbLocalRefs;
      this._EjbLocalRefs = (EjbLocalRefBean[])param0;
      this._postSet(20, _oldVal, param0);
   }

   public EjbLocalRefBean createEjbLocalRef() {
      EjbLocalRefBeanImpl _val = new EjbLocalRefBeanImpl(this, -1);

      try {
         this.addEjbLocalRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEjbLocalRef(EjbLocalRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 20);
         EjbLocalRefBean[] _old = this.getEjbLocalRefs();
         EjbLocalRefBean[] _new = (EjbLocalRefBean[])((EjbLocalRefBean[])this._getHelper()._removeElement(_old, EjbLocalRefBean.class, param0));
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
               this.setEjbLocalRefs(_new);
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

   public void addServiceRef(ServiceRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 21)) {
         ServiceRefBean[] _new;
         if (this._isSet(21)) {
            _new = (ServiceRefBean[])((ServiceRefBean[])this._getHelper()._extendArray(this.getServiceRefs(), ServiceRefBean.class, param0));
         } else {
            _new = new ServiceRefBean[]{param0};
         }

         try {
            this.setServiceRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServiceRefBean[] getServiceRefs() {
      return this._ServiceRefs;
   }

   public boolean isServiceRefsInherited() {
      return false;
   }

   public boolean isServiceRefsSet() {
      return this._isSet(21);
   }

   public void removeServiceRef(ServiceRefBean param0) {
      this.destroyServiceRef(param0);
   }

   public void setServiceRefs(ServiceRefBean[] param0) throws InvalidAttributeValueException {
      ServiceRefBean[] param0 = param0 == null ? new ServiceRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 21)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServiceRefBean[] _oldVal = this._ServiceRefs;
      this._ServiceRefs = (ServiceRefBean[])param0;
      this._postSet(21, _oldVal, param0);
   }

   public ServiceRefBean createServiceRef() {
      ServiceRefBeanImpl _val = new ServiceRefBeanImpl(this, -1);

      try {
         this.addServiceRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyServiceRef(ServiceRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 21);
         ServiceRefBean[] _old = this.getServiceRefs();
         ServiceRefBean[] _new = (ServiceRefBean[])((ServiceRefBean[])this._getHelper()._removeElement(_old, ServiceRefBean.class, param0));
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
               this.setServiceRefs(_new);
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

   public void addResourceRef(ResourceRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 22)) {
         ResourceRefBean[] _new;
         if (this._isSet(22)) {
            _new = (ResourceRefBean[])((ResourceRefBean[])this._getHelper()._extendArray(this.getResourceRefs(), ResourceRefBean.class, param0));
         } else {
            _new = new ResourceRefBean[]{param0};
         }

         try {
            this.setResourceRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ResourceRefBean[] getResourceRefs() {
      return this._ResourceRefs;
   }

   public boolean isResourceRefsInherited() {
      return false;
   }

   public boolean isResourceRefsSet() {
      return this._isSet(22);
   }

   public void removeResourceRef(ResourceRefBean param0) {
      this.destroyResourceRef(param0);
   }

   public void setResourceRefs(ResourceRefBean[] param0) throws InvalidAttributeValueException {
      ResourceRefBean[] param0 = param0 == null ? new ResourceRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 22)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceRefBean[] _oldVal = this._ResourceRefs;
      this._ResourceRefs = (ResourceRefBean[])param0;
      this._postSet(22, _oldVal, param0);
   }

   public ResourceRefBean createResourceRef() {
      ResourceRefBeanImpl _val = new ResourceRefBeanImpl(this, -1);

      try {
         this.addResourceRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyResourceRef(ResourceRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 22);
         ResourceRefBean[] _old = this.getResourceRefs();
         ResourceRefBean[] _new = (ResourceRefBean[])((ResourceRefBean[])this._getHelper()._removeElement(_old, ResourceRefBean.class, param0));
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
               this.setResourceRefs(_new);
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

   public void addResourceEnvRef(ResourceEnvRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 23)) {
         ResourceEnvRefBean[] _new;
         if (this._isSet(23)) {
            _new = (ResourceEnvRefBean[])((ResourceEnvRefBean[])this._getHelper()._extendArray(this.getResourceEnvRefs(), ResourceEnvRefBean.class, param0));
         } else {
            _new = new ResourceEnvRefBean[]{param0};
         }

         try {
            this.setResourceEnvRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ResourceEnvRefBean[] getResourceEnvRefs() {
      return this._ResourceEnvRefs;
   }

   public boolean isResourceEnvRefsInherited() {
      return false;
   }

   public boolean isResourceEnvRefsSet() {
      return this._isSet(23);
   }

   public void removeResourceEnvRef(ResourceEnvRefBean param0) {
      this.destroyResourceEnvRef(param0);
   }

   public void setResourceEnvRefs(ResourceEnvRefBean[] param0) throws InvalidAttributeValueException {
      ResourceEnvRefBean[] param0 = param0 == null ? new ResourceEnvRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 23)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceEnvRefBean[] _oldVal = this._ResourceEnvRefs;
      this._ResourceEnvRefs = (ResourceEnvRefBean[])param0;
      this._postSet(23, _oldVal, param0);
   }

   public ResourceEnvRefBean createResourceEnvRef() {
      ResourceEnvRefBeanImpl _val = new ResourceEnvRefBeanImpl(this, -1);

      try {
         this.addResourceEnvRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyResourceEnvRef(ResourceEnvRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 23);
         ResourceEnvRefBean[] _old = this.getResourceEnvRefs();
         ResourceEnvRefBean[] _new = (ResourceEnvRefBean[])((ResourceEnvRefBean[])this._getHelper()._removeElement(_old, ResourceEnvRefBean.class, param0));
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
               this.setResourceEnvRefs(_new);
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

   public void addMessageDestinationRef(MessageDestinationRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 24)) {
         MessageDestinationRefBean[] _new;
         if (this._isSet(24)) {
            _new = (MessageDestinationRefBean[])((MessageDestinationRefBean[])this._getHelper()._extendArray(this.getMessageDestinationRefs(), MessageDestinationRefBean.class, param0));
         } else {
            _new = new MessageDestinationRefBean[]{param0};
         }

         try {
            this.setMessageDestinationRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MessageDestinationRefBean[] getMessageDestinationRefs() {
      return this._MessageDestinationRefs;
   }

   public boolean isMessageDestinationRefsInherited() {
      return false;
   }

   public boolean isMessageDestinationRefsSet() {
      return this._isSet(24);
   }

   public void removeMessageDestinationRef(MessageDestinationRefBean param0) {
      this.destroyMessageDestinationRef(param0);
   }

   public void setMessageDestinationRefs(MessageDestinationRefBean[] param0) throws InvalidAttributeValueException {
      MessageDestinationRefBean[] param0 = param0 == null ? new MessageDestinationRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 24)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MessageDestinationRefBean[] _oldVal = this._MessageDestinationRefs;
      this._MessageDestinationRefs = (MessageDestinationRefBean[])param0;
      this._postSet(24, _oldVal, param0);
   }

   public MessageDestinationRefBean createMessageDestinationRef() {
      MessageDestinationRefBeanImpl _val = new MessageDestinationRefBeanImpl(this, -1);

      try {
         this.addMessageDestinationRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMessageDestinationRef(MessageDestinationRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 24);
         MessageDestinationRefBean[] _old = this.getMessageDestinationRefs();
         MessageDestinationRefBean[] _new = (MessageDestinationRefBean[])((MessageDestinationRefBean[])this._getHelper()._removeElement(_old, MessageDestinationRefBean.class, param0));
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
               this.setMessageDestinationRefs(_new);
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

   public void addPersistenceContextRef(PersistenceContextRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 25)) {
         PersistenceContextRefBean[] _new;
         if (this._isSet(25)) {
            _new = (PersistenceContextRefBean[])((PersistenceContextRefBean[])this._getHelper()._extendArray(this.getPersistenceContextRefs(), PersistenceContextRefBean.class, param0));
         } else {
            _new = new PersistenceContextRefBean[]{param0};
         }

         try {
            this.setPersistenceContextRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PersistenceContextRefBean[] getPersistenceContextRefs() {
      return this._PersistenceContextRefs;
   }

   public boolean isPersistenceContextRefsInherited() {
      return false;
   }

   public boolean isPersistenceContextRefsSet() {
      return this._isSet(25);
   }

   public void removePersistenceContextRef(PersistenceContextRefBean param0) {
      this.destroyPersistenceContextRef(param0);
   }

   public void setPersistenceContextRefs(PersistenceContextRefBean[] param0) throws InvalidAttributeValueException {
      PersistenceContextRefBean[] param0 = param0 == null ? new PersistenceContextRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 25)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PersistenceContextRefBean[] _oldVal = this._PersistenceContextRefs;
      this._PersistenceContextRefs = (PersistenceContextRefBean[])param0;
      this._postSet(25, _oldVal, param0);
   }

   public PersistenceContextRefBean createPersistenceContextRef() {
      PersistenceContextRefBeanImpl _val = new PersistenceContextRefBeanImpl(this, -1);

      try {
         this.addPersistenceContextRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPersistenceContextRef(PersistenceContextRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 25);
         PersistenceContextRefBean[] _old = this.getPersistenceContextRefs();
         PersistenceContextRefBean[] _new = (PersistenceContextRefBean[])((PersistenceContextRefBean[])this._getHelper()._removeElement(_old, PersistenceContextRefBean.class, param0));
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
               this.setPersistenceContextRefs(_new);
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

   public void addPersistenceUnitRef(PersistenceUnitRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 26)) {
         PersistenceUnitRefBean[] _new;
         if (this._isSet(26)) {
            _new = (PersistenceUnitRefBean[])((PersistenceUnitRefBean[])this._getHelper()._extendArray(this.getPersistenceUnitRefs(), PersistenceUnitRefBean.class, param0));
         } else {
            _new = new PersistenceUnitRefBean[]{param0};
         }

         try {
            this.setPersistenceUnitRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PersistenceUnitRefBean[] getPersistenceUnitRefs() {
      return this._PersistenceUnitRefs;
   }

   public boolean isPersistenceUnitRefsInherited() {
      return false;
   }

   public boolean isPersistenceUnitRefsSet() {
      return this._isSet(26);
   }

   public void removePersistenceUnitRef(PersistenceUnitRefBean param0) {
      this.destroyPersistenceUnitRef(param0);
   }

   public void setPersistenceUnitRefs(PersistenceUnitRefBean[] param0) throws InvalidAttributeValueException {
      PersistenceUnitRefBean[] param0 = param0 == null ? new PersistenceUnitRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 26)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PersistenceUnitRefBean[] _oldVal = this._PersistenceUnitRefs;
      this._PersistenceUnitRefs = (PersistenceUnitRefBean[])param0;
      this._postSet(26, _oldVal, param0);
   }

   public PersistenceUnitRefBean createPersistenceUnitRef() {
      PersistenceUnitRefBeanImpl _val = new PersistenceUnitRefBeanImpl(this, -1);

      try {
         this.addPersistenceUnitRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPersistenceUnitRef(PersistenceUnitRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 26);
         PersistenceUnitRefBean[] _old = this.getPersistenceUnitRefs();
         PersistenceUnitRefBean[] _new = (PersistenceUnitRefBean[])((PersistenceUnitRefBean[])this._getHelper()._removeElement(_old, PersistenceUnitRefBean.class, param0));
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
               this.setPersistenceUnitRefs(_new);
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

   public void addPostConstruct(LifecycleCallbackBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 27)) {
         LifecycleCallbackBean[] _new;
         if (this._isSet(27)) {
            _new = (LifecycleCallbackBean[])((LifecycleCallbackBean[])this._getHelper()._extendArray(this.getPostConstructs(), LifecycleCallbackBean.class, param0));
         } else {
            _new = new LifecycleCallbackBean[]{param0};
         }

         try {
            this.setPostConstructs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LifecycleCallbackBean[] getPostConstructs() {
      return this._PostConstructs;
   }

   public boolean isPostConstructsInherited() {
      return false;
   }

   public boolean isPostConstructsSet() {
      return this._isSet(27);
   }

   public void removePostConstruct(LifecycleCallbackBean param0) {
      this.destroyPostConstruct(param0);
   }

   public void setPostConstructs(LifecycleCallbackBean[] param0) throws InvalidAttributeValueException {
      LifecycleCallbackBean[] param0 = param0 == null ? new LifecycleCallbackBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 27)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LifecycleCallbackBean[] _oldVal = this._PostConstructs;
      this._PostConstructs = (LifecycleCallbackBean[])param0;
      this._postSet(27, _oldVal, param0);
   }

   public LifecycleCallbackBean createPostConstruct() {
      LifecycleCallbackBeanImpl _val = new LifecycleCallbackBeanImpl(this, -1);

      try {
         this.addPostConstruct(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPostConstruct(LifecycleCallbackBean param0) {
      try {
         this._checkIsPotentialChild(param0, 27);
         LifecycleCallbackBean[] _old = this.getPostConstructs();
         LifecycleCallbackBean[] _new = (LifecycleCallbackBean[])((LifecycleCallbackBean[])this._getHelper()._removeElement(_old, LifecycleCallbackBean.class, param0));
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
               this.setPostConstructs(_new);
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

   public void addPreDestroy(LifecycleCallbackBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 28)) {
         LifecycleCallbackBean[] _new;
         if (this._isSet(28)) {
            _new = (LifecycleCallbackBean[])((LifecycleCallbackBean[])this._getHelper()._extendArray(this.getPreDestroys(), LifecycleCallbackBean.class, param0));
         } else {
            _new = new LifecycleCallbackBean[]{param0};
         }

         try {
            this.setPreDestroys(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LifecycleCallbackBean[] getPreDestroys() {
      return this._PreDestroys;
   }

   public boolean isPreDestroysInherited() {
      return false;
   }

   public boolean isPreDestroysSet() {
      return this._isSet(28);
   }

   public void removePreDestroy(LifecycleCallbackBean param0) {
      this.destroyPreDestroy(param0);
   }

   public void setPreDestroys(LifecycleCallbackBean[] param0) throws InvalidAttributeValueException {
      LifecycleCallbackBean[] param0 = param0 == null ? new LifecycleCallbackBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 28)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LifecycleCallbackBean[] _oldVal = this._PreDestroys;
      this._PreDestroys = (LifecycleCallbackBean[])param0;
      this._postSet(28, _oldVal, param0);
   }

   public LifecycleCallbackBean createPreDestroy() {
      LifecycleCallbackBeanImpl _val = new LifecycleCallbackBeanImpl(this, -1);

      try {
         this.addPreDestroy(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPreDestroy(LifecycleCallbackBean param0) {
      try {
         this._checkIsPotentialChild(param0, 28);
         LifecycleCallbackBean[] _old = this.getPreDestroys();
         LifecycleCallbackBean[] _new = (LifecycleCallbackBean[])((LifecycleCallbackBean[])this._getHelper()._removeElement(_old, LifecycleCallbackBean.class, param0));
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
               this.setPreDestroys(_new);
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

   public void addDataSource(DataSourceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 29)) {
         DataSourceBean[] _new;
         if (this._isSet(29)) {
            _new = (DataSourceBean[])((DataSourceBean[])this._getHelper()._extendArray(this.getDataSources(), DataSourceBean.class, param0));
         } else {
            _new = new DataSourceBean[]{param0};
         }

         try {
            this.setDataSources(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public DataSourceBean[] getDataSources() {
      return this._DataSources;
   }

   public boolean isDataSourcesInherited() {
      return false;
   }

   public boolean isDataSourcesSet() {
      return this._isSet(29);
   }

   public void removeDataSource(DataSourceBean param0) {
      this.destroyDataSource(param0);
   }

   public void setDataSources(DataSourceBean[] param0) throws InvalidAttributeValueException {
      DataSourceBean[] param0 = param0 == null ? new DataSourceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 29)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      DataSourceBean[] _oldVal = this._DataSources;
      this._DataSources = (DataSourceBean[])param0;
      this._postSet(29, _oldVal, param0);
   }

   public DataSourceBean createDataSource() {
      DataSourceBeanImpl _val = new DataSourceBeanImpl(this, -1);

      try {
         this.addDataSource(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDataSource(DataSourceBean param0) {
      try {
         this._checkIsPotentialChild(param0, 29);
         DataSourceBean[] _old = this.getDataSources();
         DataSourceBean[] _new = (DataSourceBean[])((DataSourceBean[])this._getHelper()._removeElement(_old, DataSourceBean.class, param0));
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
               this.setDataSources(_new);
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

   public void addMessageDestination(MessageDestinationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 30)) {
         MessageDestinationBean[] _new;
         if (this._isSet(30)) {
            _new = (MessageDestinationBean[])((MessageDestinationBean[])this._getHelper()._extendArray(this.getMessageDestinations(), MessageDestinationBean.class, param0));
         } else {
            _new = new MessageDestinationBean[]{param0};
         }

         try {
            this.setMessageDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MessageDestinationBean[] getMessageDestinations() {
      return this._MessageDestinations;
   }

   public boolean isMessageDestinationsInherited() {
      return false;
   }

   public boolean isMessageDestinationsSet() {
      return this._isSet(30);
   }

   public void removeMessageDestination(MessageDestinationBean param0) {
      this.destroyMessageDestination(param0);
   }

   public void setMessageDestinations(MessageDestinationBean[] param0) throws InvalidAttributeValueException {
      MessageDestinationBean[] param0 = param0 == null ? new MessageDestinationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 30)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MessageDestinationBean[] _oldVal = this._MessageDestinations;
      this._MessageDestinations = (MessageDestinationBean[])param0;
      this._postSet(30, _oldVal, param0);
   }

   public MessageDestinationBean createMessageDestination() {
      MessageDestinationBeanImpl _val = new MessageDestinationBeanImpl(this, -1);

      try {
         this.addMessageDestination(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMessageDestination(MessageDestinationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 30);
         MessageDestinationBean[] _old = this.getMessageDestinations();
         MessageDestinationBean[] _new = (MessageDestinationBean[])((MessageDestinationBean[])this._getHelper()._removeElement(_old, MessageDestinationBean.class, param0));
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
               this.setMessageDestinations(_new);
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

   public void addLocaleEncodingMappingList(LocaleEncodingMappingListBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 31)) {
         LocaleEncodingMappingListBean[] _new;
         if (this._isSet(31)) {
            _new = (LocaleEncodingMappingListBean[])((LocaleEncodingMappingListBean[])this._getHelper()._extendArray(this.getLocaleEncodingMappingLists(), LocaleEncodingMappingListBean.class, param0));
         } else {
            _new = new LocaleEncodingMappingListBean[]{param0};
         }

         try {
            this.setLocaleEncodingMappingLists(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LocaleEncodingMappingListBean[] getLocaleEncodingMappingLists() {
      return this._LocaleEncodingMappingLists;
   }

   public boolean isLocaleEncodingMappingListsInherited() {
      return false;
   }

   public boolean isLocaleEncodingMappingListsSet() {
      return this._isSet(31);
   }

   public void removeLocaleEncodingMappingList(LocaleEncodingMappingListBean param0) {
      this.destroyLocaleEncodingMappingList(param0);
   }

   public void setLocaleEncodingMappingLists(LocaleEncodingMappingListBean[] param0) throws InvalidAttributeValueException {
      LocaleEncodingMappingListBean[] param0 = param0 == null ? new LocaleEncodingMappingListBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 31)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LocaleEncodingMappingListBean[] _oldVal = this._LocaleEncodingMappingLists;
      this._LocaleEncodingMappingLists = (LocaleEncodingMappingListBean[])param0;
      this._postSet(31, _oldVal, param0);
   }

   public LocaleEncodingMappingListBean createLocaleEncodingMappingList() {
      LocaleEncodingMappingListBeanImpl _val = new LocaleEncodingMappingListBeanImpl(this, -1);

      try {
         this.addLocaleEncodingMappingList(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLocaleEncodingMappingList(LocaleEncodingMappingListBean param0) {
      try {
         this._checkIsPotentialChild(param0, 31);
         LocaleEncodingMappingListBean[] _old = this.getLocaleEncodingMappingLists();
         LocaleEncodingMappingListBean[] _new = (LocaleEncodingMappingListBean[])((LocaleEncodingMappingListBean[])this._getHelper()._removeElement(_old, LocaleEncodingMappingListBean.class, param0));
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
               this.setLocaleEncodingMappingLists(_new);
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
      return this._isSet(32);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(32, _oldVal, param0);
   }

   public boolean isMetadataComplete() {
      return this._MetadataComplete;
   }

   public boolean isMetadataCompleteInherited() {
      return false;
   }

   public boolean isMetadataCompleteSet() {
      return this._isSet(33);
   }

   public void setMetadataComplete(boolean param0) {
      boolean _oldVal = this._MetadataComplete;
      this._MetadataComplete = param0;
      this._postSet(33, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(34);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(34, _oldVal, param0);
   }

   public void addJmsConnectionFactory(JmsConnectionFactoryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 35)) {
         JmsConnectionFactoryBean[] _new;
         if (this._isSet(35)) {
            _new = (JmsConnectionFactoryBean[])((JmsConnectionFactoryBean[])this._getHelper()._extendArray(this.getJmsConnectionFactories(), JmsConnectionFactoryBean.class, param0));
         } else {
            _new = new JmsConnectionFactoryBean[]{param0};
         }

         try {
            this.setJmsConnectionFactories(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JmsConnectionFactoryBean[] getJmsConnectionFactories() {
      return this._JmsConnectionFactories;
   }

   public boolean isJmsConnectionFactoriesInherited() {
      return false;
   }

   public boolean isJmsConnectionFactoriesSet() {
      return this._isSet(35);
   }

   public void removeJmsConnectionFactory(JmsConnectionFactoryBean param0) {
      this.destroyJmsConnectionFactory(param0);
   }

   public void setJmsConnectionFactories(JmsConnectionFactoryBean[] param0) throws InvalidAttributeValueException {
      JmsConnectionFactoryBean[] param0 = param0 == null ? new JmsConnectionFactoryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 35)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JmsConnectionFactoryBean[] _oldVal = this._JmsConnectionFactories;
      this._JmsConnectionFactories = (JmsConnectionFactoryBean[])param0;
      this._postSet(35, _oldVal, param0);
   }

   public JmsConnectionFactoryBean createJmsConnectionFactory() {
      JmsConnectionFactoryBeanImpl _val = new JmsConnectionFactoryBeanImpl(this, -1);

      try {
         this.addJmsConnectionFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJmsConnectionFactory(JmsConnectionFactoryBean param0) {
      try {
         this._checkIsPotentialChild(param0, 35);
         JmsConnectionFactoryBean[] _old = this.getJmsConnectionFactories();
         JmsConnectionFactoryBean[] _new = (JmsConnectionFactoryBean[])((JmsConnectionFactoryBean[])this._getHelper()._removeElement(_old, JmsConnectionFactoryBean.class, param0));
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
               this.setJmsConnectionFactories(_new);
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

   public void addJmsDestination(JmsDestinationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 36)) {
         JmsDestinationBean[] _new;
         if (this._isSet(36)) {
            _new = (JmsDestinationBean[])((JmsDestinationBean[])this._getHelper()._extendArray(this.getJmsDestinations(), JmsDestinationBean.class, param0));
         } else {
            _new = new JmsDestinationBean[]{param0};
         }

         try {
            this.setJmsDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JmsDestinationBean[] getJmsDestinations() {
      return this._JmsDestinations;
   }

   public boolean isJmsDestinationsInherited() {
      return false;
   }

   public boolean isJmsDestinationsSet() {
      return this._isSet(36);
   }

   public void removeJmsDestination(JmsDestinationBean param0) {
      this.destroyJmsDestination(param0);
   }

   public void setJmsDestinations(JmsDestinationBean[] param0) throws InvalidAttributeValueException {
      JmsDestinationBean[] param0 = param0 == null ? new JmsDestinationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 36)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JmsDestinationBean[] _oldVal = this._JmsDestinations;
      this._JmsDestinations = (JmsDestinationBean[])param0;
      this._postSet(36, _oldVal, param0);
   }

   public JmsDestinationBean createJmsDestination() {
      JmsDestinationBeanImpl _val = new JmsDestinationBeanImpl(this, -1);

      try {
         this.addJmsDestination(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJmsDestination(JmsDestinationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 36);
         JmsDestinationBean[] _old = this.getJmsDestinations();
         JmsDestinationBean[] _new = (JmsDestinationBean[])((JmsDestinationBean[])this._getHelper()._removeElement(_old, JmsDestinationBean.class, param0));
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
               this.setJmsDestinations(_new);
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

   public void addMailSession(MailSessionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 37)) {
         MailSessionBean[] _new;
         if (this._isSet(37)) {
            _new = (MailSessionBean[])((MailSessionBean[])this._getHelper()._extendArray(this.getMailSessions(), MailSessionBean.class, param0));
         } else {
            _new = new MailSessionBean[]{param0};
         }

         try {
            this.setMailSessions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MailSessionBean[] getMailSessions() {
      return this._MailSessions;
   }

   public boolean isMailSessionsInherited() {
      return false;
   }

   public boolean isMailSessionsSet() {
      return this._isSet(37);
   }

   public void removeMailSession(MailSessionBean param0) {
      this.destroyMailSession(param0);
   }

   public void setMailSessions(MailSessionBean[] param0) throws InvalidAttributeValueException {
      MailSessionBean[] param0 = param0 == null ? new MailSessionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 37)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MailSessionBean[] _oldVal = this._MailSessions;
      this._MailSessions = (MailSessionBean[])param0;
      this._postSet(37, _oldVal, param0);
   }

   public MailSessionBean createMailSession() {
      MailSessionBeanImpl _val = new MailSessionBeanImpl(this, -1);

      try {
         this.addMailSession(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMailSession(MailSessionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 37);
         MailSessionBean[] _old = this.getMailSessions();
         MailSessionBean[] _new = (MailSessionBean[])((MailSessionBean[])this._getHelper()._removeElement(_old, MailSessionBean.class, param0));
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
               this.setMailSessions(_new);
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

   public void addConnectionFactory(ConnectionFactoryResourceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 38)) {
         ConnectionFactoryResourceBean[] _new;
         if (this._isSet(38)) {
            _new = (ConnectionFactoryResourceBean[])((ConnectionFactoryResourceBean[])this._getHelper()._extendArray(this.getConnectionFactories(), ConnectionFactoryResourceBean.class, param0));
         } else {
            _new = new ConnectionFactoryResourceBean[]{param0};
         }

         try {
            this.setConnectionFactories(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ConnectionFactoryResourceBean[] getConnectionFactories() {
      return this._ConnectionFactories;
   }

   public boolean isConnectionFactoriesInherited() {
      return false;
   }

   public boolean isConnectionFactoriesSet() {
      return this._isSet(38);
   }

   public void removeConnectionFactory(ConnectionFactoryResourceBean param0) {
      this.destroyConnectionFactory(param0);
   }

   public void setConnectionFactories(ConnectionFactoryResourceBean[] param0) throws InvalidAttributeValueException {
      ConnectionFactoryResourceBean[] param0 = param0 == null ? new ConnectionFactoryResourceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 38)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ConnectionFactoryResourceBean[] _oldVal = this._ConnectionFactories;
      this._ConnectionFactories = (ConnectionFactoryResourceBean[])param0;
      this._postSet(38, _oldVal, param0);
   }

   public ConnectionFactoryResourceBean createConnectionFactoryResourceBean() {
      ConnectionFactoryResourceBeanImpl _val = new ConnectionFactoryResourceBeanImpl(this, -1);

      try {
         this.addConnectionFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConnectionFactory(ConnectionFactoryResourceBean param0) {
      try {
         this._checkIsPotentialChild(param0, 38);
         ConnectionFactoryResourceBean[] _old = this.getConnectionFactories();
         ConnectionFactoryResourceBean[] _new = (ConnectionFactoryResourceBean[])((ConnectionFactoryResourceBean[])this._getHelper()._removeElement(_old, ConnectionFactoryResourceBean.class, param0));
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
               this.setConnectionFactories(_new);
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

   public void addAdministeredObject(AdministeredObjectBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 39)) {
         AdministeredObjectBean[] _new;
         if (this._isSet(39)) {
            _new = (AdministeredObjectBean[])((AdministeredObjectBean[])this._getHelper()._extendArray(this.getAdministeredObjects(), AdministeredObjectBean.class, param0));
         } else {
            _new = new AdministeredObjectBean[]{param0};
         }

         try {
            this.setAdministeredObjects(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AdministeredObjectBean[] getAdministeredObjects() {
      return this._AdministeredObjects;
   }

   public boolean isAdministeredObjectsInherited() {
      return false;
   }

   public boolean isAdministeredObjectsSet() {
      return this._isSet(39);
   }

   public void removeAdministeredObject(AdministeredObjectBean param0) {
      this.destroyAdministeredObject(param0);
   }

   public void setAdministeredObjects(AdministeredObjectBean[] param0) throws InvalidAttributeValueException {
      AdministeredObjectBean[] param0 = param0 == null ? new AdministeredObjectBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 39)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AdministeredObjectBean[] _oldVal = this._AdministeredObjects;
      this._AdministeredObjects = (AdministeredObjectBean[])param0;
      this._postSet(39, _oldVal, param0);
   }

   public AdministeredObjectBean createAdministeredObjectBean() {
      AdministeredObjectBeanImpl _val = new AdministeredObjectBeanImpl(this, -1);

      try {
         this.addAdministeredObject(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAdministeredObject(AdministeredObjectBean param0) {
      try {
         this._checkIsPotentialChild(param0, 39);
         AdministeredObjectBean[] _old = this.getAdministeredObjects();
         AdministeredObjectBean[] _new = (AdministeredObjectBean[])((AdministeredObjectBean[])this._getHelper()._removeElement(_old, AdministeredObjectBean.class, param0));
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
               this.setAdministeredObjects(_new);
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
         idx = 39;
      }

      try {
         switch (idx) {
            case 39:
               this._AdministeredObjects = new AdministeredObjectBean[0];
               if (initOne) {
                  break;
               }
            case 38:
               this._ConnectionFactories = new ConnectionFactoryResourceBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._ContextParams = new ParamValueBean[0];
               if (initOne) {
                  break;
               }
            case 29:
               this._DataSources = new DataSourceBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._DisplayNames = new String[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._Distributables = new EmptyBean[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._EjbLocalRefs = new EjbLocalRefBean[0];
               if (initOne) {
                  break;
               }
            case 19:
               this._EjbRefs = new EjbRefBean[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._EnvEntries = new EnvEntryBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._ErrorPages = new ErrorPageBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._FilterMappings = new FilterMappingBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._Filters = new FilterBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._Icons = new IconBean[0];
               if (initOne) {
                  break;
               }
            case 34:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 35:
               this._JmsConnectionFactories = new JmsConnectionFactoryBean[0];
               if (initOne) {
                  break;
               }
            case 36:
               this._JmsDestinations = new JmsDestinationBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._JspConfigs = new JspConfigBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._Listeners = new ListenerBean[0];
               if (initOne) {
                  break;
               }
            case 31:
               this._LocaleEncodingMappingLists = new LocaleEncodingMappingListBean[0];
               if (initOne) {
                  break;
               }
            case 16:
               this._LoginConfigs = new LoginConfigBean[0];
               if (initOne) {
                  break;
               }
            case 37:
               this._MailSessions = new MailSessionBean[0];
               if (initOne) {
                  break;
               }
            case 24:
               this._MessageDestinationRefs = new MessageDestinationRefBean[0];
               if (initOne) {
                  break;
               }
            case 30:
               this._MessageDestinations = new MessageDestinationBean[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._MimeMappings = new MimeMappingBean[0];
               if (initOne) {
                  break;
               }
            case 25:
               this._PersistenceContextRefs = new PersistenceContextRefBean[0];
               if (initOne) {
                  break;
               }
            case 26:
               this._PersistenceUnitRefs = new PersistenceUnitRefBean[0];
               if (initOne) {
                  break;
               }
            case 27:
               this._PostConstructs = new LifecycleCallbackBean[0];
               if (initOne) {
                  break;
               }
            case 28:
               this._PreDestroys = new LifecycleCallbackBean[0];
               if (initOne) {
                  break;
               }
            case 23:
               this._ResourceEnvRefs = new ResourceEnvRefBean[0];
               if (initOne) {
                  break;
               }
            case 22:
               this._ResourceRefs = new ResourceRefBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._SecurityConstraints = new SecurityConstraintBean[0];
               if (initOne) {
                  break;
               }
            case 17:
               this._SecurityRoles = new SecurityRoleBean[0];
               if (initOne) {
                  break;
               }
            case 21:
               this._ServiceRefs = new ServiceRefBean[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._ServletMappings = new ServletMappingBean[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._Servlets = new ServletBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._SessionConfigs = new SessionConfigBean[0];
               if (initOne) {
                  break;
               }
            case 32:
               this._Version = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._WelcomeFileLists = new WelcomeFileListBean[0];
               if (initOne) {
                  break;
               }
            case 33:
               this._MetadataComplete = false;
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
                  return 34;
               }
            case 3:
            case 5:
            case 21:
            case 24:
            case 25:
            case 26:
            case 27:
            default:
               break;
            case 4:
               if (s.equals("icon")) {
                  return 2;
               }
               break;
            case 6:
               if (s.equals("filter")) {
                  return 5;
               }
               break;
            case 7:
               if (s.equals("ejb-ref")) {
                  return 19;
               }

               if (s.equals("servlet")) {
                  return 8;
               }

               if (s.equals("version")) {
                  return 32;
               }
               break;
            case 8:
               if (s.equals("listener")) {
                  return 7;
               }
               break;
            case 9:
               if (s.equals("env-entry")) {
                  return 18;
               }
               break;
            case 10:
               if (s.equals("error-page")) {
                  return 13;
               }

               if (s.equals("jsp-config")) {
                  return 14;
               }
               break;
            case 11:
               if (s.equals("data-source")) {
                  return 29;
               }

               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("pre-destroy")) {
                  return 28;
               }

               if (s.equals("service-ref")) {
                  return 21;
               }
               break;
            case 12:
               if (s.equals("display-name")) {
                  return 1;
               }

               if (s.equals("login-config")) {
                  return 16;
               }

               if (s.equals("mail-session")) {
                  return 37;
               }

               if (s.equals("mime-mapping")) {
                  return 11;
               }

               if (s.equals("resource-ref")) {
                  return 22;
               }
               break;
            case 13:
               if (s.equals("context-param")) {
                  return 4;
               }

               if (s.equals("distributable")) {
                  return 3;
               }

               if (s.equals("ejb-local-ref")) {
                  return 20;
               }

               if (s.equals("security-role")) {
                  return 17;
               }
               break;
            case 14:
               if (s.equals("filter-mapping")) {
                  return 6;
               }

               if (s.equals("post-construct")) {
                  return 27;
               }

               if (s.equals("session-config")) {
                  return 10;
               }
               break;
            case 15:
               if (s.equals("jms-destination")) {
                  return 36;
               }

               if (s.equals("servlet-mapping")) {
                  return 9;
               }
               break;
            case 16:
               if (s.equals("resource-env-ref")) {
                  return 23;
               }
               break;
            case 17:
               if (s.equals("welcome-file-list")) {
                  return 12;
               }

               if (s.equals("metadata-complete")) {
                  return 33;
               }
               break;
            case 18:
               if (s.equals("connection-factory")) {
                  return 38;
               }
               break;
            case 19:
               if (s.equals("administered-object")) {
                  return 39;
               }

               if (s.equals("message-destination")) {
                  return 30;
               }

               if (s.equals("security-constraint")) {
                  return 15;
               }
               break;
            case 20:
               if (s.equals("persistence-unit-ref")) {
                  return 26;
               }
               break;
            case 22:
               if (s.equals("jms-connection-factory")) {
                  return 35;
               }
               break;
            case 23:
               if (s.equals("message-destination-ref")) {
                  return 24;
               }

               if (s.equals("persistence-context-ref")) {
                  return 25;
               }
               break;
            case 28:
               if (s.equals("locale-encoding-mapping-list")) {
                  return 31;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new IconBeanImpl.SchemaHelper2();
            case 3:
               return new EmptyBeanImpl.SchemaHelper2();
            case 4:
               return new ParamValueBeanImpl.SchemaHelper2();
            case 5:
               return new FilterBeanImpl.SchemaHelper2();
            case 6:
               return new FilterMappingBeanImpl.SchemaHelper2();
            case 7:
               return new ListenerBeanImpl.SchemaHelper2();
            case 8:
               return new ServletBeanImpl.SchemaHelper2();
            case 9:
               return new ServletMappingBeanImpl.SchemaHelper2();
            case 10:
               return new SessionConfigBeanImpl.SchemaHelper2();
            case 11:
               return new MimeMappingBeanImpl.SchemaHelper2();
            case 12:
               return new WelcomeFileListBeanImpl.SchemaHelper2();
            case 13:
               return new ErrorPageBeanImpl.SchemaHelper2();
            case 14:
               return new JspConfigBeanImpl.SchemaHelper2();
            case 15:
               return new SecurityConstraintBeanImpl.SchemaHelper2();
            case 16:
               return new LoginConfigBeanImpl.SchemaHelper2();
            case 17:
               return new SecurityRoleBeanImpl.SchemaHelper2();
            case 18:
               return new EnvEntryBeanImpl.SchemaHelper2();
            case 19:
               return new EjbRefBeanImpl.SchemaHelper2();
            case 20:
               return new EjbLocalRefBeanImpl.SchemaHelper2();
            case 21:
               return new ServiceRefBeanImpl.SchemaHelper2();
            case 22:
               return new ResourceRefBeanImpl.SchemaHelper2();
            case 23:
               return new ResourceEnvRefBeanImpl.SchemaHelper2();
            case 24:
               return new MessageDestinationRefBeanImpl.SchemaHelper2();
            case 25:
               return new PersistenceContextRefBeanImpl.SchemaHelper2();
            case 26:
               return new PersistenceUnitRefBeanImpl.SchemaHelper2();
            case 27:
               return new LifecycleCallbackBeanImpl.SchemaHelper2();
            case 28:
               return new LifecycleCallbackBeanImpl.SchemaHelper2();
            case 29:
               return new DataSourceBeanImpl.SchemaHelper2();
            case 30:
               return new MessageDestinationBeanImpl.SchemaHelper2();
            case 31:
               return new LocaleEncodingMappingListBeanImpl.SchemaHelper2();
            case 32:
            case 33:
            case 34:
            default:
               return super.getSchemaHelper(propIndex);
            case 35:
               return new JmsConnectionFactoryBeanImpl.SchemaHelper2();
            case 36:
               return new JmsDestinationBeanImpl.SchemaHelper2();
            case 37:
               return new MailSessionBeanImpl.SchemaHelper2();
            case 38:
               return new ConnectionFactoryResourceBeanImpl.SchemaHelper2();
            case 39:
               return new AdministeredObjectBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "display-name";
            case 2:
               return "icon";
            case 3:
               return "distributable";
            case 4:
               return "context-param";
            case 5:
               return "filter";
            case 6:
               return "filter-mapping";
            case 7:
               return "listener";
            case 8:
               return "servlet";
            case 9:
               return "servlet-mapping";
            case 10:
               return "session-config";
            case 11:
               return "mime-mapping";
            case 12:
               return "welcome-file-list";
            case 13:
               return "error-page";
            case 14:
               return "jsp-config";
            case 15:
               return "security-constraint";
            case 16:
               return "login-config";
            case 17:
               return "security-role";
            case 18:
               return "env-entry";
            case 19:
               return "ejb-ref";
            case 20:
               return "ejb-local-ref";
            case 21:
               return "service-ref";
            case 22:
               return "resource-ref";
            case 23:
               return "resource-env-ref";
            case 24:
               return "message-destination-ref";
            case 25:
               return "persistence-context-ref";
            case 26:
               return "persistence-unit-ref";
            case 27:
               return "post-construct";
            case 28:
               return "pre-destroy";
            case 29:
               return "data-source";
            case 30:
               return "message-destination";
            case 31:
               return "locale-encoding-mapping-list";
            case 32:
               return "version";
            case 33:
               return "metadata-complete";
            case 34:
               return "id";
            case 35:
               return "jms-connection-factory";
            case 36:
               return "jms-destination";
            case 37:
               return "mail-session";
            case 38:
               return "connection-factory";
            case 39:
               return "administered-object";
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
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            case 28:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            case 32:
            case 33:
            case 34:
            default:
               return super.isArray(propIndex);
            case 35:
               return true;
            case 36:
               return true;
            case 37:
               return true;
            case 38:
               return true;
            case 39:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            case 28:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            case 32:
            case 33:
            case 34:
            default:
               return super.isBean(propIndex);
            case 35:
               return true;
            case 36:
               return true;
            case 37:
               return true;
            case 38:
               return true;
            case 39:
               return true;
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

      public boolean isMergeRulePrependDefined(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            case 6:
               return true;
            default:
               return super.isMergeRulePrependDefined(propIndex);
         }
      }

      public boolean isMergeRuleIgnoreTargetDefined(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 13:
            case 15:
            default:
               return super.isMergeRuleIgnoreTargetDefined(propIndex);
            case 4:
               return true;
            case 10:
               return true;
            case 12:
               return true;
            case 14:
               return true;
            case 16:
               return true;
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WebAppBaseBeanImpl bean;

      protected Helper(WebAppBaseBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Descriptions";
            case 1:
               return "DisplayNames";
            case 2:
               return "Icons";
            case 3:
               return "Distributables";
            case 4:
               return "ContextParams";
            case 5:
               return "Filters";
            case 6:
               return "FilterMappings";
            case 7:
               return "Listeners";
            case 8:
               return "Servlets";
            case 9:
               return "ServletMappings";
            case 10:
               return "SessionConfigs";
            case 11:
               return "MimeMappings";
            case 12:
               return "WelcomeFileLists";
            case 13:
               return "ErrorPages";
            case 14:
               return "JspConfigs";
            case 15:
               return "SecurityConstraints";
            case 16:
               return "LoginConfigs";
            case 17:
               return "SecurityRoles";
            case 18:
               return "EnvEntries";
            case 19:
               return "EjbRefs";
            case 20:
               return "EjbLocalRefs";
            case 21:
               return "ServiceRefs";
            case 22:
               return "ResourceRefs";
            case 23:
               return "ResourceEnvRefs";
            case 24:
               return "MessageDestinationRefs";
            case 25:
               return "PersistenceContextRefs";
            case 26:
               return "PersistenceUnitRefs";
            case 27:
               return "PostConstructs";
            case 28:
               return "PreDestroys";
            case 29:
               return "DataSources";
            case 30:
               return "MessageDestinations";
            case 31:
               return "LocaleEncodingMappingLists";
            case 32:
               return "Version";
            case 33:
               return "MetadataComplete";
            case 34:
               return "Id";
            case 35:
               return "JmsConnectionFactories";
            case 36:
               return "JmsDestinations";
            case 37:
               return "MailSessions";
            case 38:
               return "ConnectionFactories";
            case 39:
               return "AdministeredObjects";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdministeredObjects")) {
            return 39;
         } else if (propName.equals("ConnectionFactories")) {
            return 38;
         } else if (propName.equals("ContextParams")) {
            return 4;
         } else if (propName.equals("DataSources")) {
            return 29;
         } else if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("DisplayNames")) {
            return 1;
         } else if (propName.equals("Distributables")) {
            return 3;
         } else if (propName.equals("EjbLocalRefs")) {
            return 20;
         } else if (propName.equals("EjbRefs")) {
            return 19;
         } else if (propName.equals("EnvEntries")) {
            return 18;
         } else if (propName.equals("ErrorPages")) {
            return 13;
         } else if (propName.equals("FilterMappings")) {
            return 6;
         } else if (propName.equals("Filters")) {
            return 5;
         } else if (propName.equals("Icons")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 34;
         } else if (propName.equals("JmsConnectionFactories")) {
            return 35;
         } else if (propName.equals("JmsDestinations")) {
            return 36;
         } else if (propName.equals("JspConfigs")) {
            return 14;
         } else if (propName.equals("Listeners")) {
            return 7;
         } else if (propName.equals("LocaleEncodingMappingLists")) {
            return 31;
         } else if (propName.equals("LoginConfigs")) {
            return 16;
         } else if (propName.equals("MailSessions")) {
            return 37;
         } else if (propName.equals("MessageDestinationRefs")) {
            return 24;
         } else if (propName.equals("MessageDestinations")) {
            return 30;
         } else if (propName.equals("MimeMappings")) {
            return 11;
         } else if (propName.equals("PersistenceContextRefs")) {
            return 25;
         } else if (propName.equals("PersistenceUnitRefs")) {
            return 26;
         } else if (propName.equals("PostConstructs")) {
            return 27;
         } else if (propName.equals("PreDestroys")) {
            return 28;
         } else if (propName.equals("ResourceEnvRefs")) {
            return 23;
         } else if (propName.equals("ResourceRefs")) {
            return 22;
         } else if (propName.equals("SecurityConstraints")) {
            return 15;
         } else if (propName.equals("SecurityRoles")) {
            return 17;
         } else if (propName.equals("ServiceRefs")) {
            return 21;
         } else if (propName.equals("ServletMappings")) {
            return 9;
         } else if (propName.equals("Servlets")) {
            return 8;
         } else if (propName.equals("SessionConfigs")) {
            return 10;
         } else if (propName.equals("Version")) {
            return 32;
         } else if (propName.equals("WelcomeFileLists")) {
            return 12;
         } else {
            return propName.equals("MetadataComplete") ? 33 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAdministeredObjects()));
         iterators.add(new ArrayIterator(this.bean.getConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getContextParams()));
         iterators.add(new ArrayIterator(this.bean.getDataSources()));
         iterators.add(new ArrayIterator(this.bean.getDistributables()));
         iterators.add(new ArrayIterator(this.bean.getEjbLocalRefs()));
         iterators.add(new ArrayIterator(this.bean.getEjbRefs()));
         iterators.add(new ArrayIterator(this.bean.getEnvEntries()));
         iterators.add(new ArrayIterator(this.bean.getErrorPages()));
         iterators.add(new ArrayIterator(this.bean.getFilterMappings()));
         iterators.add(new ArrayIterator(this.bean.getFilters()));
         iterators.add(new ArrayIterator(this.bean.getIcons()));
         iterators.add(new ArrayIterator(this.bean.getJmsConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getJmsDestinations()));
         iterators.add(new ArrayIterator(this.bean.getJspConfigs()));
         iterators.add(new ArrayIterator(this.bean.getListeners()));
         iterators.add(new ArrayIterator(this.bean.getLocaleEncodingMappingLists()));
         iterators.add(new ArrayIterator(this.bean.getLoginConfigs()));
         iterators.add(new ArrayIterator(this.bean.getMailSessions()));
         iterators.add(new ArrayIterator(this.bean.getMessageDestinationRefs()));
         iterators.add(new ArrayIterator(this.bean.getMessageDestinations()));
         iterators.add(new ArrayIterator(this.bean.getMimeMappings()));
         iterators.add(new ArrayIterator(this.bean.getPersistenceContextRefs()));
         iterators.add(new ArrayIterator(this.bean.getPersistenceUnitRefs()));
         iterators.add(new ArrayIterator(this.bean.getPostConstructs()));
         iterators.add(new ArrayIterator(this.bean.getPreDestroys()));
         iterators.add(new ArrayIterator(this.bean.getResourceEnvRefs()));
         iterators.add(new ArrayIterator(this.bean.getResourceRefs()));
         iterators.add(new ArrayIterator(this.bean.getSecurityConstraints()));
         iterators.add(new ArrayIterator(this.bean.getSecurityRoles()));
         iterators.add(new ArrayIterator(this.bean.getServiceRefs()));
         iterators.add(new ArrayIterator(this.bean.getServletMappings()));
         iterators.add(new ArrayIterator(this.bean.getServlets()));
         iterators.add(new ArrayIterator(this.bean.getSessionConfigs()));
         iterators.add(new ArrayIterator(this.bean.getWelcomeFileLists()));
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
            for(i = 0; i < this.bean.getAdministeredObjects().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAdministeredObjects()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getConnectionFactories().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConnectionFactories()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getContextParams().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getContextParams()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getDataSources().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getDataSources()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
            }

            if (this.bean.isDisplayNamesSet()) {
               buf.append("DisplayNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDisplayNames())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getDistributables().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getDistributables()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getEjbLocalRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEjbLocalRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getEjbRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEjbRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getEnvEntries().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEnvEntries()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getErrorPages().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getErrorPages()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getFilterMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getFilterMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getFilters().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getFilters()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getIcons().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getIcons()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJmsConnectionFactories().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJmsConnectionFactories()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJmsDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJmsDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJspConfigs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJspConfigs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getListeners().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getListeners()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getLocaleEncodingMappingLists().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLocaleEncodingMappingLists()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getLoginConfigs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLoginConfigs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMailSessions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMailSessions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMessageDestinationRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMessageDestinationRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMessageDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMessageDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMimeMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMimeMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPersistenceContextRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPersistenceContextRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPersistenceUnitRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPersistenceUnitRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPostConstructs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPostConstructs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPreDestroys().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPreDestroys()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getResourceEnvRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResourceEnvRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getResourceRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResourceRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSecurityConstraints().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSecurityConstraints()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSecurityRoles().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSecurityRoles()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServiceRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServiceRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServletMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServletMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServlets().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServlets()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSessionConfigs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSessionConfigs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWelcomeFileLists().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWelcomeFileLists()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMetadataCompleteSet()) {
               buf.append("MetadataComplete");
               buf.append(String.valueOf(this.bean.isMetadataComplete()));
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
            WebAppBaseBeanImpl otherTyped = (WebAppBaseBeanImpl)other;
            this.computeChildDiff("AdministeredObjects", this.bean.getAdministeredObjects(), otherTyped.getAdministeredObjects(), false);
            this.computeChildDiff("ConnectionFactories", this.bean.getConnectionFactories(), otherTyped.getConnectionFactories(), false);
            this.computeChildDiff("ContextParams", this.bean.getContextParams(), otherTyped.getContextParams(), false);
            this.computeChildDiff("DataSources", this.bean.getDataSources(), otherTyped.getDataSources(), false);
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("DisplayNames", this.bean.getDisplayNames(), otherTyped.getDisplayNames(), false);
            this.computeChildDiff("Distributables", this.bean.getDistributables(), otherTyped.getDistributables(), false);
            this.computeChildDiff("EjbLocalRefs", this.bean.getEjbLocalRefs(), otherTyped.getEjbLocalRefs(), false);
            this.computeChildDiff("EjbRefs", this.bean.getEjbRefs(), otherTyped.getEjbRefs(), false);
            this.computeChildDiff("EnvEntries", this.bean.getEnvEntries(), otherTyped.getEnvEntries(), false);
            this.computeChildDiff("ErrorPages", this.bean.getErrorPages(), otherTyped.getErrorPages(), false);
            this.computeChildDiff("FilterMappings", this.bean.getFilterMappings(), otherTyped.getFilterMappings(), false);
            this.computeChildDiff("Filters", this.bean.getFilters(), otherTyped.getFilters(), false);
            this.computeChildDiff("Icons", this.bean.getIcons(), otherTyped.getIcons(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("JmsConnectionFactories", this.bean.getJmsConnectionFactories(), otherTyped.getJmsConnectionFactories(), false);
            this.computeChildDiff("JmsDestinations", this.bean.getJmsDestinations(), otherTyped.getJmsDestinations(), false);
            this.computeChildDiff("JspConfigs", this.bean.getJspConfigs(), otherTyped.getJspConfigs(), false);
            this.computeChildDiff("Listeners", this.bean.getListeners(), otherTyped.getListeners(), false);
            this.computeChildDiff("LocaleEncodingMappingLists", this.bean.getLocaleEncodingMappingLists(), otherTyped.getLocaleEncodingMappingLists(), false);
            this.computeChildDiff("LoginConfigs", this.bean.getLoginConfigs(), otherTyped.getLoginConfigs(), false);
            this.computeChildDiff("MailSessions", this.bean.getMailSessions(), otherTyped.getMailSessions(), false);
            this.computeChildDiff("MessageDestinationRefs", this.bean.getMessageDestinationRefs(), otherTyped.getMessageDestinationRefs(), false);
            this.computeChildDiff("MessageDestinations", this.bean.getMessageDestinations(), otherTyped.getMessageDestinations(), false);
            this.computeChildDiff("MimeMappings", this.bean.getMimeMappings(), otherTyped.getMimeMappings(), false);
            this.computeChildDiff("PersistenceContextRefs", this.bean.getPersistenceContextRefs(), otherTyped.getPersistenceContextRefs(), false);
            this.computeChildDiff("PersistenceUnitRefs", this.bean.getPersistenceUnitRefs(), otherTyped.getPersistenceUnitRefs(), false);
            this.computeChildDiff("PostConstructs", this.bean.getPostConstructs(), otherTyped.getPostConstructs(), false);
            this.computeChildDiff("PreDestroys", this.bean.getPreDestroys(), otherTyped.getPreDestroys(), false);
            this.computeChildDiff("ResourceEnvRefs", this.bean.getResourceEnvRefs(), otherTyped.getResourceEnvRefs(), false);
            this.computeChildDiff("ResourceRefs", this.bean.getResourceRefs(), otherTyped.getResourceRefs(), false);
            this.computeChildDiff("SecurityConstraints", this.bean.getSecurityConstraints(), otherTyped.getSecurityConstraints(), false);
            this.computeChildDiff("SecurityRoles", this.bean.getSecurityRoles(), otherTyped.getSecurityRoles(), false);
            this.computeChildDiff("ServiceRefs", this.bean.getServiceRefs(), otherTyped.getServiceRefs(), false);
            this.computeChildDiff("ServletMappings", this.bean.getServletMappings(), otherTyped.getServletMappings(), false);
            this.computeChildDiff("Servlets", this.bean.getServlets(), otherTyped.getServlets(), false);
            this.computeChildDiff("SessionConfigs", this.bean.getSessionConfigs(), otherTyped.getSessionConfigs(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
            this.computeChildDiff("WelcomeFileLists", this.bean.getWelcomeFileLists(), otherTyped.getWelcomeFileLists(), true);
            this.computeDiff("MetadataComplete", this.bean.isMetadataComplete(), otherTyped.isMetadataComplete(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebAppBaseBeanImpl original = (WebAppBaseBeanImpl)event.getSourceBean();
            WebAppBaseBeanImpl proposed = (WebAppBaseBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AdministeredObjects")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAdministeredObject((AdministeredObjectBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAdministeredObject((AdministeredObjectBean)update.getRemovedObject());
                  }

                  if (original.getAdministeredObjects() == null || original.getAdministeredObjects().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 39);
                  }
               } else if (prop.equals("ConnectionFactories")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConnectionFactory((ConnectionFactoryResourceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConnectionFactory((ConnectionFactoryResourceBean)update.getRemovedObject());
                  }

                  if (original.getConnectionFactories() == null || original.getConnectionFactories().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 38);
                  }
               } else if (prop.equals("ContextParams")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addContextParam((ParamValueBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeContextParam((ParamValueBean)update.getRemovedObject());
                  }

                  if (original.getContextParams() == null || original.getContextParams().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("DataSources")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addDataSource((DataSourceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDataSource((DataSourceBean)update.getRemovedObject());
                  }

                  if (original.getDataSources() == null || original.getDataSources().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 29);
                  }
               } else if (prop.equals("Descriptions")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDescription((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDescription((String)update.getRemovedObject());
                  }

                  if (original.getDescriptions() == null || original.getDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("DisplayNames")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDisplayName((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDisplayName((String)update.getRemovedObject());
                  }

                  if (original.getDisplayNames() == null || original.getDisplayNames().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("Distributables")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addDistributable((EmptyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDistributable((EmptyBean)update.getRemovedObject());
                  }

                  if (original.getDistributables() == null || original.getDistributables().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("EjbLocalRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEjbLocalRef((EjbLocalRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEjbLocalRef((EjbLocalRefBean)update.getRemovedObject());
                  }

                  if (original.getEjbLocalRefs() == null || original.getEjbLocalRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  }
               } else if (prop.equals("EjbRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEjbRef((EjbRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEjbRef((EjbRefBean)update.getRemovedObject());
                  }

                  if (original.getEjbRefs() == null || original.getEjbRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  }
               } else if (prop.equals("EnvEntries")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEnvEntry((EnvEntryBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEnvEntry((EnvEntryBean)update.getRemovedObject());
                  }

                  if (original.getEnvEntries() == null || original.getEnvEntries().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  }
               } else if (prop.equals("ErrorPages")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addErrorPage((ErrorPageBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeErrorPage((ErrorPageBean)update.getRemovedObject());
                  }

                  if (original.getErrorPages() == null || original.getErrorPages().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  }
               } else if (prop.equals("FilterMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addFilterMapping((FilterMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeFilterMapping((FilterMappingBean)update.getRemovedObject());
                  }

                  if (original.getFilterMappings() == null || original.getFilterMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("Filters")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addFilter((FilterBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeFilter((FilterBean)update.getRemovedObject());
                  }

                  if (original.getFilters() == null || original.getFilters().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("Icons")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addIcon((IconBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeIcon((IconBean)update.getRemovedObject());
                  }

                  if (original.getIcons() == null || original.getIcons().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("JmsConnectionFactories")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJmsConnectionFactory((JmsConnectionFactoryBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJmsConnectionFactory((JmsConnectionFactoryBean)update.getRemovedObject());
                  }

                  if (original.getJmsConnectionFactories() == null || original.getJmsConnectionFactories().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 35);
                  }
               } else if (prop.equals("JmsDestinations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJmsDestination((JmsDestinationBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJmsDestination((JmsDestinationBean)update.getRemovedObject());
                  }

                  if (original.getJmsDestinations() == null || original.getJmsDestinations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 36);
                  }
               } else if (prop.equals("JspConfigs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJspConfig((JspConfigBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJspConfig((JspConfigBean)update.getRemovedObject());
                  }

                  if (original.getJspConfigs() == null || original.getJspConfigs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  }
               } else if (prop.equals("Listeners")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addListener((ListenerBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeListener((ListenerBean)update.getRemovedObject());
                  }

                  if (original.getListeners() == null || original.getListeners().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
                  }
               } else if (prop.equals("LocaleEncodingMappingLists")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addLocaleEncodingMappingList((LocaleEncodingMappingListBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeLocaleEncodingMappingList((LocaleEncodingMappingListBean)update.getRemovedObject());
                  }

                  if (original.getLocaleEncodingMappingLists() == null || original.getLocaleEncodingMappingLists().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 31);
                  }
               } else if (prop.equals("LoginConfigs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addLoginConfig((LoginConfigBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeLoginConfig((LoginConfigBean)update.getRemovedObject());
                  }

                  if (original.getLoginConfigs() == null || original.getLoginConfigs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  }
               } else if (prop.equals("MailSessions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMailSession((MailSessionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMailSession((MailSessionBean)update.getRemovedObject());
                  }

                  if (original.getMailSessions() == null || original.getMailSessions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 37);
                  }
               } else if (prop.equals("MessageDestinationRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMessageDestinationRef((MessageDestinationRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMessageDestinationRef((MessageDestinationRefBean)update.getRemovedObject());
                  }

                  if (original.getMessageDestinationRefs() == null || original.getMessageDestinationRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
                  }
               } else if (prop.equals("MessageDestinations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMessageDestination((MessageDestinationBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMessageDestination((MessageDestinationBean)update.getRemovedObject());
                  }

                  if (original.getMessageDestinations() == null || original.getMessageDestinations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 30);
                  }
               } else if (prop.equals("MimeMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMimeMapping((MimeMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMimeMapping((MimeMappingBean)update.getRemovedObject());
                  }

                  if (original.getMimeMappings() == null || original.getMimeMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("PersistenceContextRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPersistenceContextRef((PersistenceContextRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePersistenceContextRef((PersistenceContextRefBean)update.getRemovedObject());
                  }

                  if (original.getPersistenceContextRefs() == null || original.getPersistenceContextRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 25);
                  }
               } else if (prop.equals("PersistenceUnitRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPersistenceUnitRef((PersistenceUnitRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePersistenceUnitRef((PersistenceUnitRefBean)update.getRemovedObject());
                  }

                  if (original.getPersistenceUnitRefs() == null || original.getPersistenceUnitRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 26);
                  }
               } else if (prop.equals("PostConstructs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPostConstruct((LifecycleCallbackBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePostConstruct((LifecycleCallbackBean)update.getRemovedObject());
                  }

                  if (original.getPostConstructs() == null || original.getPostConstructs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  }
               } else if (prop.equals("PreDestroys")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPreDestroy((LifecycleCallbackBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePreDestroy((LifecycleCallbackBean)update.getRemovedObject());
                  }

                  if (original.getPreDestroys() == null || original.getPreDestroys().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 28);
                  }
               } else if (prop.equals("ResourceEnvRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addResourceEnvRef((ResourceEnvRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeResourceEnvRef((ResourceEnvRefBean)update.getRemovedObject());
                  }

                  if (original.getResourceEnvRefs() == null || original.getResourceEnvRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
                  }
               } else if (prop.equals("ResourceRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addResourceRef((ResourceRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeResourceRef((ResourceRefBean)update.getRemovedObject());
                  }

                  if (original.getResourceRefs() == null || original.getResourceRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  }
               } else if (prop.equals("SecurityConstraints")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSecurityConstraint((SecurityConstraintBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSecurityConstraint((SecurityConstraintBean)update.getRemovedObject());
                  }

                  if (original.getSecurityConstraints() == null || original.getSecurityConstraints().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  }
               } else if (prop.equals("SecurityRoles")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSecurityRole((SecurityRoleBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSecurityRole((SecurityRoleBean)update.getRemovedObject());
                  }

                  if (original.getSecurityRoles() == null || original.getSecurityRoles().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  }
               } else if (prop.equals("ServiceRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addServiceRef((ServiceRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeServiceRef((ServiceRefBean)update.getRemovedObject());
                  }

                  if (original.getServiceRefs() == null || original.getServiceRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  }
               } else if (prop.equals("ServletMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addServletMapping((ServletMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeServletMapping((ServletMappingBean)update.getRemovedObject());
                  }

                  if (original.getServletMappings() == null || original.getServletMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("Servlets")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addServlet((ServletBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeServlet((ServletBean)update.getRemovedObject());
                  }

                  if (original.getServlets() == null || original.getServlets().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  }
               } else if (prop.equals("SessionConfigs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSessionConfig((SessionConfigBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSessionConfig((SessionConfigBean)update.getRemovedObject());
                  }

                  if (original.getSessionConfigs() == null || original.getSessionConfigs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("WelcomeFileLists")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWelcomeFileList((WelcomeFileListBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWelcomeFileList((WelcomeFileListBean)update.getRemovedObject());
                  }

                  if (original.getWelcomeFileLists() == null || original.getWelcomeFileLists().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  }
               } else if (prop.equals("MetadataComplete")) {
                  original.setMetadataComplete(proposed.isMetadataComplete());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
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
            WebAppBaseBeanImpl copy = (WebAppBaseBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("AdministeredObjects")) && this.bean.isAdministeredObjectsSet() && !copy._isSet(39)) {
               AdministeredObjectBean[] oldAdministeredObjects = this.bean.getAdministeredObjects();
               AdministeredObjectBean[] newAdministeredObjects = new AdministeredObjectBean[oldAdministeredObjects.length];

               for(i = 0; i < newAdministeredObjects.length; ++i) {
                  newAdministeredObjects[i] = (AdministeredObjectBean)((AdministeredObjectBean)this.createCopy((AbstractDescriptorBean)oldAdministeredObjects[i], includeObsolete));
               }

               copy.setAdministeredObjects(newAdministeredObjects);
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactories")) && this.bean.isConnectionFactoriesSet() && !copy._isSet(38)) {
               ConnectionFactoryResourceBean[] oldConnectionFactories = this.bean.getConnectionFactories();
               ConnectionFactoryResourceBean[] newConnectionFactories = new ConnectionFactoryResourceBean[oldConnectionFactories.length];

               for(i = 0; i < newConnectionFactories.length; ++i) {
                  newConnectionFactories[i] = (ConnectionFactoryResourceBean)((ConnectionFactoryResourceBean)this.createCopy((AbstractDescriptorBean)oldConnectionFactories[i], includeObsolete));
               }

               copy.setConnectionFactories(newConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("ContextParams")) && this.bean.isContextParamsSet() && !copy._isSet(4)) {
               ParamValueBean[] oldContextParams = this.bean.getContextParams();
               ParamValueBean[] newContextParams = new ParamValueBean[oldContextParams.length];

               for(i = 0; i < newContextParams.length; ++i) {
                  newContextParams[i] = (ParamValueBean)((ParamValueBean)this.createCopy((AbstractDescriptorBean)oldContextParams[i], includeObsolete));
               }

               copy.setContextParams(newContextParams);
            }

            if ((excludeProps == null || !excludeProps.contains("DataSources")) && this.bean.isDataSourcesSet() && !copy._isSet(29)) {
               DataSourceBean[] oldDataSources = this.bean.getDataSources();
               DataSourceBean[] newDataSources = new DataSourceBean[oldDataSources.length];

               for(i = 0; i < newDataSources.length; ++i) {
                  newDataSources[i] = (DataSourceBean)((DataSourceBean)this.createCopy((AbstractDescriptorBean)oldDataSources[i], includeObsolete));
               }

               copy.setDataSources(newDataSources);
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("DisplayNames")) && this.bean.isDisplayNamesSet()) {
               o = this.bean.getDisplayNames();
               copy.setDisplayNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Distributables")) && this.bean.isDistributablesSet() && !copy._isSet(3)) {
               EmptyBean[] oldDistributables = this.bean.getDistributables();
               EmptyBean[] newDistributables = new EmptyBean[oldDistributables.length];

               for(i = 0; i < newDistributables.length; ++i) {
                  newDistributables[i] = (EmptyBean)((EmptyBean)this.createCopy((AbstractDescriptorBean)oldDistributables[i], includeObsolete));
               }

               copy.setDistributables(newDistributables);
            }

            if ((excludeProps == null || !excludeProps.contains("EjbLocalRefs")) && this.bean.isEjbLocalRefsSet() && !copy._isSet(20)) {
               EjbLocalRefBean[] oldEjbLocalRefs = this.bean.getEjbLocalRefs();
               EjbLocalRefBean[] newEjbLocalRefs = new EjbLocalRefBean[oldEjbLocalRefs.length];

               for(i = 0; i < newEjbLocalRefs.length; ++i) {
                  newEjbLocalRefs[i] = (EjbLocalRefBean)((EjbLocalRefBean)this.createCopy((AbstractDescriptorBean)oldEjbLocalRefs[i], includeObsolete));
               }

               copy.setEjbLocalRefs(newEjbLocalRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("EjbRefs")) && this.bean.isEjbRefsSet() && !copy._isSet(19)) {
               EjbRefBean[] oldEjbRefs = this.bean.getEjbRefs();
               EjbRefBean[] newEjbRefs = new EjbRefBean[oldEjbRefs.length];

               for(i = 0; i < newEjbRefs.length; ++i) {
                  newEjbRefs[i] = (EjbRefBean)((EjbRefBean)this.createCopy((AbstractDescriptorBean)oldEjbRefs[i], includeObsolete));
               }

               copy.setEjbRefs(newEjbRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("EnvEntries")) && this.bean.isEnvEntriesSet() && !copy._isSet(18)) {
               EnvEntryBean[] oldEnvEntries = this.bean.getEnvEntries();
               EnvEntryBean[] newEnvEntries = new EnvEntryBean[oldEnvEntries.length];

               for(i = 0; i < newEnvEntries.length; ++i) {
                  newEnvEntries[i] = (EnvEntryBean)((EnvEntryBean)this.createCopy((AbstractDescriptorBean)oldEnvEntries[i], includeObsolete));
               }

               copy.setEnvEntries(newEnvEntries);
            }

            if ((excludeProps == null || !excludeProps.contains("ErrorPages")) && this.bean.isErrorPagesSet() && !copy._isSet(13)) {
               ErrorPageBean[] oldErrorPages = this.bean.getErrorPages();
               ErrorPageBean[] newErrorPages = new ErrorPageBean[oldErrorPages.length];

               for(i = 0; i < newErrorPages.length; ++i) {
                  newErrorPages[i] = (ErrorPageBean)((ErrorPageBean)this.createCopy((AbstractDescriptorBean)oldErrorPages[i], includeObsolete));
               }

               copy.setErrorPages(newErrorPages);
            }

            if ((excludeProps == null || !excludeProps.contains("FilterMappings")) && this.bean.isFilterMappingsSet() && !copy._isSet(6)) {
               FilterMappingBean[] oldFilterMappings = this.bean.getFilterMappings();
               FilterMappingBean[] newFilterMappings = new FilterMappingBean[oldFilterMappings.length];

               for(i = 0; i < newFilterMappings.length; ++i) {
                  newFilterMappings[i] = (FilterMappingBean)((FilterMappingBean)this.createCopy((AbstractDescriptorBean)oldFilterMappings[i], includeObsolete));
               }

               copy.setFilterMappings(newFilterMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("Filters")) && this.bean.isFiltersSet() && !copy._isSet(5)) {
               FilterBean[] oldFilters = this.bean.getFilters();
               FilterBean[] newFilters = new FilterBean[oldFilters.length];

               for(i = 0; i < newFilters.length; ++i) {
                  newFilters[i] = (FilterBean)((FilterBean)this.createCopy((AbstractDescriptorBean)oldFilters[i], includeObsolete));
               }

               copy.setFilters(newFilters);
            }

            if ((excludeProps == null || !excludeProps.contains("Icons")) && this.bean.isIconsSet() && !copy._isSet(2)) {
               IconBean[] oldIcons = this.bean.getIcons();
               IconBean[] newIcons = new IconBean[oldIcons.length];

               for(i = 0; i < newIcons.length; ++i) {
                  newIcons[i] = (IconBean)((IconBean)this.createCopy((AbstractDescriptorBean)oldIcons[i], includeObsolete));
               }

               copy.setIcons(newIcons);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("JmsConnectionFactories")) && this.bean.isJmsConnectionFactoriesSet() && !copy._isSet(35)) {
               JmsConnectionFactoryBean[] oldJmsConnectionFactories = this.bean.getJmsConnectionFactories();
               JmsConnectionFactoryBean[] newJmsConnectionFactories = new JmsConnectionFactoryBean[oldJmsConnectionFactories.length];

               for(i = 0; i < newJmsConnectionFactories.length; ++i) {
                  newJmsConnectionFactories[i] = (JmsConnectionFactoryBean)((JmsConnectionFactoryBean)this.createCopy((AbstractDescriptorBean)oldJmsConnectionFactories[i], includeObsolete));
               }

               copy.setJmsConnectionFactories(newJmsConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("JmsDestinations")) && this.bean.isJmsDestinationsSet() && !copy._isSet(36)) {
               JmsDestinationBean[] oldJmsDestinations = this.bean.getJmsDestinations();
               JmsDestinationBean[] newJmsDestinations = new JmsDestinationBean[oldJmsDestinations.length];

               for(i = 0; i < newJmsDestinations.length; ++i) {
                  newJmsDestinations[i] = (JmsDestinationBean)((JmsDestinationBean)this.createCopy((AbstractDescriptorBean)oldJmsDestinations[i], includeObsolete));
               }

               copy.setJmsDestinations(newJmsDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("JspConfigs")) && this.bean.isJspConfigsSet() && !copy._isSet(14)) {
               JspConfigBean[] oldJspConfigs = this.bean.getJspConfigs();
               JspConfigBean[] newJspConfigs = new JspConfigBean[oldJspConfigs.length];

               for(i = 0; i < newJspConfigs.length; ++i) {
                  newJspConfigs[i] = (JspConfigBean)((JspConfigBean)this.createCopy((AbstractDescriptorBean)oldJspConfigs[i], includeObsolete));
               }

               copy.setJspConfigs(newJspConfigs);
            }

            if ((excludeProps == null || !excludeProps.contains("Listeners")) && this.bean.isListenersSet() && !copy._isSet(7)) {
               ListenerBean[] oldListeners = this.bean.getListeners();
               ListenerBean[] newListeners = new ListenerBean[oldListeners.length];

               for(i = 0; i < newListeners.length; ++i) {
                  newListeners[i] = (ListenerBean)((ListenerBean)this.createCopy((AbstractDescriptorBean)oldListeners[i], includeObsolete));
               }

               copy.setListeners(newListeners);
            }

            if ((excludeProps == null || !excludeProps.contains("LocaleEncodingMappingLists")) && this.bean.isLocaleEncodingMappingListsSet() && !copy._isSet(31)) {
               LocaleEncodingMappingListBean[] oldLocaleEncodingMappingLists = this.bean.getLocaleEncodingMappingLists();
               LocaleEncodingMappingListBean[] newLocaleEncodingMappingLists = new LocaleEncodingMappingListBean[oldLocaleEncodingMappingLists.length];

               for(i = 0; i < newLocaleEncodingMappingLists.length; ++i) {
                  newLocaleEncodingMappingLists[i] = (LocaleEncodingMappingListBean)((LocaleEncodingMappingListBean)this.createCopy((AbstractDescriptorBean)oldLocaleEncodingMappingLists[i], includeObsolete));
               }

               copy.setLocaleEncodingMappingLists(newLocaleEncodingMappingLists);
            }

            if ((excludeProps == null || !excludeProps.contains("LoginConfigs")) && this.bean.isLoginConfigsSet() && !copy._isSet(16)) {
               LoginConfigBean[] oldLoginConfigs = this.bean.getLoginConfigs();
               LoginConfigBean[] newLoginConfigs = new LoginConfigBean[oldLoginConfigs.length];

               for(i = 0; i < newLoginConfigs.length; ++i) {
                  newLoginConfigs[i] = (LoginConfigBean)((LoginConfigBean)this.createCopy((AbstractDescriptorBean)oldLoginConfigs[i], includeObsolete));
               }

               copy.setLoginConfigs(newLoginConfigs);
            }

            if ((excludeProps == null || !excludeProps.contains("MailSessions")) && this.bean.isMailSessionsSet() && !copy._isSet(37)) {
               MailSessionBean[] oldMailSessions = this.bean.getMailSessions();
               MailSessionBean[] newMailSessions = new MailSessionBean[oldMailSessions.length];

               for(i = 0; i < newMailSessions.length; ++i) {
                  newMailSessions[i] = (MailSessionBean)((MailSessionBean)this.createCopy((AbstractDescriptorBean)oldMailSessions[i], includeObsolete));
               }

               copy.setMailSessions(newMailSessions);
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDestinationRefs")) && this.bean.isMessageDestinationRefsSet() && !copy._isSet(24)) {
               MessageDestinationRefBean[] oldMessageDestinationRefs = this.bean.getMessageDestinationRefs();
               MessageDestinationRefBean[] newMessageDestinationRefs = new MessageDestinationRefBean[oldMessageDestinationRefs.length];

               for(i = 0; i < newMessageDestinationRefs.length; ++i) {
                  newMessageDestinationRefs[i] = (MessageDestinationRefBean)((MessageDestinationRefBean)this.createCopy((AbstractDescriptorBean)oldMessageDestinationRefs[i], includeObsolete));
               }

               copy.setMessageDestinationRefs(newMessageDestinationRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDestinations")) && this.bean.isMessageDestinationsSet() && !copy._isSet(30)) {
               MessageDestinationBean[] oldMessageDestinations = this.bean.getMessageDestinations();
               MessageDestinationBean[] newMessageDestinations = new MessageDestinationBean[oldMessageDestinations.length];

               for(i = 0; i < newMessageDestinations.length; ++i) {
                  newMessageDestinations[i] = (MessageDestinationBean)((MessageDestinationBean)this.createCopy((AbstractDescriptorBean)oldMessageDestinations[i], includeObsolete));
               }

               copy.setMessageDestinations(newMessageDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("MimeMappings")) && this.bean.isMimeMappingsSet() && !copy._isSet(11)) {
               MimeMappingBean[] oldMimeMappings = this.bean.getMimeMappings();
               MimeMappingBean[] newMimeMappings = new MimeMappingBean[oldMimeMappings.length];

               for(i = 0; i < newMimeMappings.length; ++i) {
                  newMimeMappings[i] = (MimeMappingBean)((MimeMappingBean)this.createCopy((AbstractDescriptorBean)oldMimeMappings[i], includeObsolete));
               }

               copy.setMimeMappings(newMimeMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceContextRefs")) && this.bean.isPersistenceContextRefsSet() && !copy._isSet(25)) {
               PersistenceContextRefBean[] oldPersistenceContextRefs = this.bean.getPersistenceContextRefs();
               PersistenceContextRefBean[] newPersistenceContextRefs = new PersistenceContextRefBean[oldPersistenceContextRefs.length];

               for(i = 0; i < newPersistenceContextRefs.length; ++i) {
                  newPersistenceContextRefs[i] = (PersistenceContextRefBean)((PersistenceContextRefBean)this.createCopy((AbstractDescriptorBean)oldPersistenceContextRefs[i], includeObsolete));
               }

               copy.setPersistenceContextRefs(newPersistenceContextRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceUnitRefs")) && this.bean.isPersistenceUnitRefsSet() && !copy._isSet(26)) {
               PersistenceUnitRefBean[] oldPersistenceUnitRefs = this.bean.getPersistenceUnitRefs();
               PersistenceUnitRefBean[] newPersistenceUnitRefs = new PersistenceUnitRefBean[oldPersistenceUnitRefs.length];

               for(i = 0; i < newPersistenceUnitRefs.length; ++i) {
                  newPersistenceUnitRefs[i] = (PersistenceUnitRefBean)((PersistenceUnitRefBean)this.createCopy((AbstractDescriptorBean)oldPersistenceUnitRefs[i], includeObsolete));
               }

               copy.setPersistenceUnitRefs(newPersistenceUnitRefs);
            }

            LifecycleCallbackBean[] oldPreDestroys;
            LifecycleCallbackBean[] newPreDestroys;
            if ((excludeProps == null || !excludeProps.contains("PostConstructs")) && this.bean.isPostConstructsSet() && !copy._isSet(27)) {
               oldPreDestroys = this.bean.getPostConstructs();
               newPreDestroys = new LifecycleCallbackBean[oldPreDestroys.length];

               for(i = 0; i < newPreDestroys.length; ++i) {
                  newPreDestroys[i] = (LifecycleCallbackBean)((LifecycleCallbackBean)this.createCopy((AbstractDescriptorBean)oldPreDestroys[i], includeObsolete));
               }

               copy.setPostConstructs(newPreDestroys);
            }

            if ((excludeProps == null || !excludeProps.contains("PreDestroys")) && this.bean.isPreDestroysSet() && !copy._isSet(28)) {
               oldPreDestroys = this.bean.getPreDestroys();
               newPreDestroys = new LifecycleCallbackBean[oldPreDestroys.length];

               for(i = 0; i < newPreDestroys.length; ++i) {
                  newPreDestroys[i] = (LifecycleCallbackBean)((LifecycleCallbackBean)this.createCopy((AbstractDescriptorBean)oldPreDestroys[i], includeObsolete));
               }

               copy.setPreDestroys(newPreDestroys);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceEnvRefs")) && this.bean.isResourceEnvRefsSet() && !copy._isSet(23)) {
               ResourceEnvRefBean[] oldResourceEnvRefs = this.bean.getResourceEnvRefs();
               ResourceEnvRefBean[] newResourceEnvRefs = new ResourceEnvRefBean[oldResourceEnvRefs.length];

               for(i = 0; i < newResourceEnvRefs.length; ++i) {
                  newResourceEnvRefs[i] = (ResourceEnvRefBean)((ResourceEnvRefBean)this.createCopy((AbstractDescriptorBean)oldResourceEnvRefs[i], includeObsolete));
               }

               copy.setResourceEnvRefs(newResourceEnvRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceRefs")) && this.bean.isResourceRefsSet() && !copy._isSet(22)) {
               ResourceRefBean[] oldResourceRefs = this.bean.getResourceRefs();
               ResourceRefBean[] newResourceRefs = new ResourceRefBean[oldResourceRefs.length];

               for(i = 0; i < newResourceRefs.length; ++i) {
                  newResourceRefs[i] = (ResourceRefBean)((ResourceRefBean)this.createCopy((AbstractDescriptorBean)oldResourceRefs[i], includeObsolete));
               }

               copy.setResourceRefs(newResourceRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityConstraints")) && this.bean.isSecurityConstraintsSet() && !copy._isSet(15)) {
               SecurityConstraintBean[] oldSecurityConstraints = this.bean.getSecurityConstraints();
               SecurityConstraintBean[] newSecurityConstraints = new SecurityConstraintBean[oldSecurityConstraints.length];

               for(i = 0; i < newSecurityConstraints.length; ++i) {
                  newSecurityConstraints[i] = (SecurityConstraintBean)((SecurityConstraintBean)this.createCopy((AbstractDescriptorBean)oldSecurityConstraints[i], includeObsolete));
               }

               copy.setSecurityConstraints(newSecurityConstraints);
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityRoles")) && this.bean.isSecurityRolesSet() && !copy._isSet(17)) {
               SecurityRoleBean[] oldSecurityRoles = this.bean.getSecurityRoles();
               SecurityRoleBean[] newSecurityRoles = new SecurityRoleBean[oldSecurityRoles.length];

               for(i = 0; i < newSecurityRoles.length; ++i) {
                  newSecurityRoles[i] = (SecurityRoleBean)((SecurityRoleBean)this.createCopy((AbstractDescriptorBean)oldSecurityRoles[i], includeObsolete));
               }

               copy.setSecurityRoles(newSecurityRoles);
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceRefs")) && this.bean.isServiceRefsSet() && !copy._isSet(21)) {
               ServiceRefBean[] oldServiceRefs = this.bean.getServiceRefs();
               ServiceRefBean[] newServiceRefs = new ServiceRefBean[oldServiceRefs.length];

               for(i = 0; i < newServiceRefs.length; ++i) {
                  newServiceRefs[i] = (ServiceRefBean)((ServiceRefBean)this.createCopy((AbstractDescriptorBean)oldServiceRefs[i], includeObsolete));
               }

               copy.setServiceRefs(newServiceRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("ServletMappings")) && this.bean.isServletMappingsSet() && !copy._isSet(9)) {
               ServletMappingBean[] oldServletMappings = this.bean.getServletMappings();
               ServletMappingBean[] newServletMappings = new ServletMappingBean[oldServletMappings.length];

               for(i = 0; i < newServletMappings.length; ++i) {
                  newServletMappings[i] = (ServletMappingBean)((ServletMappingBean)this.createCopy((AbstractDescriptorBean)oldServletMappings[i], includeObsolete));
               }

               copy.setServletMappings(newServletMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("Servlets")) && this.bean.isServletsSet() && !copy._isSet(8)) {
               ServletBean[] oldServlets = this.bean.getServlets();
               ServletBean[] newServlets = new ServletBean[oldServlets.length];

               for(i = 0; i < newServlets.length; ++i) {
                  newServlets[i] = (ServletBean)((ServletBean)this.createCopy((AbstractDescriptorBean)oldServlets[i], includeObsolete));
               }

               copy.setServlets(newServlets);
            }

            if ((excludeProps == null || !excludeProps.contains("SessionConfigs")) && this.bean.isSessionConfigsSet() && !copy._isSet(10)) {
               SessionConfigBean[] oldSessionConfigs = this.bean.getSessionConfigs();
               SessionConfigBean[] newSessionConfigs = new SessionConfigBean[oldSessionConfigs.length];

               for(i = 0; i < newSessionConfigs.length; ++i) {
                  newSessionConfigs[i] = (SessionConfigBean)((SessionConfigBean)this.createCopy((AbstractDescriptorBean)oldSessionConfigs[i], includeObsolete));
               }

               copy.setSessionConfigs(newSessionConfigs);
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("WelcomeFileLists")) && this.bean.isWelcomeFileListsSet() && !copy._isSet(12)) {
               WelcomeFileListBean[] oldWelcomeFileLists = this.bean.getWelcomeFileLists();
               WelcomeFileListBean[] newWelcomeFileLists = new WelcomeFileListBean[oldWelcomeFileLists.length];

               for(i = 0; i < newWelcomeFileLists.length; ++i) {
                  newWelcomeFileLists[i] = (WelcomeFileListBean)((WelcomeFileListBean)this.createCopy((AbstractDescriptorBean)oldWelcomeFileLists[i], includeObsolete));
               }

               copy.setWelcomeFileLists(newWelcomeFileLists);
            }

            if ((excludeProps == null || !excludeProps.contains("MetadataComplete")) && this.bean.isMetadataCompleteSet()) {
               copy.setMetadataComplete(this.bean.isMetadataComplete());
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
         this.inferSubTree(this.bean.getAdministeredObjects(), clazz, annotation);
         this.inferSubTree(this.bean.getConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getContextParams(), clazz, annotation);
         this.inferSubTree(this.bean.getDataSources(), clazz, annotation);
         this.inferSubTree(this.bean.getDistributables(), clazz, annotation);
         this.inferSubTree(this.bean.getEjbLocalRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getEjbRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getEnvEntries(), clazz, annotation);
         this.inferSubTree(this.bean.getErrorPages(), clazz, annotation);
         this.inferSubTree(this.bean.getFilterMappings(), clazz, annotation);
         this.inferSubTree(this.bean.getFilters(), clazz, annotation);
         this.inferSubTree(this.bean.getIcons(), clazz, annotation);
         this.inferSubTree(this.bean.getJmsConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getJmsDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getJspConfigs(), clazz, annotation);
         this.inferSubTree(this.bean.getListeners(), clazz, annotation);
         this.inferSubTree(this.bean.getLocaleEncodingMappingLists(), clazz, annotation);
         this.inferSubTree(this.bean.getLoginConfigs(), clazz, annotation);
         this.inferSubTree(this.bean.getMailSessions(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageDestinationRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getMimeMappings(), clazz, annotation);
         this.inferSubTree(this.bean.getPersistenceContextRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getPersistenceUnitRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getPostConstructs(), clazz, annotation);
         this.inferSubTree(this.bean.getPreDestroys(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceEnvRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityConstraints(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityRoles(), clazz, annotation);
         this.inferSubTree(this.bean.getServiceRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getServletMappings(), clazz, annotation);
         this.inferSubTree(this.bean.getServlets(), clazz, annotation);
         this.inferSubTree(this.bean.getSessionConfigs(), clazz, annotation);
         this.inferSubTree(this.bean.getWelcomeFileLists(), clazz, annotation);
      }
   }
}

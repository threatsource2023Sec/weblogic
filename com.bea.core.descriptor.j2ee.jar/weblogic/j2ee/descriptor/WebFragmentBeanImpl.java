package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WebFragmentBeanImpl extends WebAppBaseBeanImpl implements WebFragmentBean, Serializable {
   private String[] _Names;
   private OrderingBean[] _Orderings;
   private String _Version;
   private static SchemaHelper2 _schemaHelper;

   public WebFragmentBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebFragmentBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebFragmentBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String[] getNames() {
      return this._Names;
   }

   public boolean isNamesInherited() {
      return false;
   }

   public boolean isNamesSet() {
      return this._isSet(40);
   }

   public void setNames(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Names;
      this._Names = param0;
      this._postSet(40, _oldVal, param0);
   }

   public void addOrdering(OrderingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 41)) {
         OrderingBean[] _new;
         if (this._isSet(41)) {
            _new = (OrderingBean[])((OrderingBean[])this._getHelper()._extendArray(this.getOrderings(), OrderingBean.class, param0));
         } else {
            _new = new OrderingBean[]{param0};
         }

         try {
            this.setOrderings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public OrderingBean[] getOrderings() {
      return this._Orderings;
   }

   public boolean isOrderingsInherited() {
      return false;
   }

   public boolean isOrderingsSet() {
      return this._isSet(41);
   }

   public void removeOrdering(OrderingBean param0) {
      this.destroyOrdering(param0);
   }

   public void setOrderings(OrderingBean[] param0) throws InvalidAttributeValueException {
      OrderingBean[] param0 = param0 == null ? new OrderingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 41)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      OrderingBean[] _oldVal = this._Orderings;
      this._Orderings = (OrderingBean[])param0;
      this._postSet(41, _oldVal, param0);
   }

   public OrderingBean createOrdering() {
      OrderingBeanImpl _val = new OrderingBeanImpl(this, -1);

      try {
         this.addOrdering(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyOrdering(OrderingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 41);
         OrderingBean[] _old = this.getOrderings();
         OrderingBean[] _new = (OrderingBean[])((OrderingBean[])this._getHelper()._removeElement(_old, OrderingBean.class, param0));
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
               this.setOrderings(_new);
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
         idx = 40;
      }

      try {
         switch (idx) {
            case 40:
               this._Names = new String[0];
               if (initOne) {
                  break;
               }
            case 41:
               this._Orderings = new OrderingBean[0];
               if (initOne) {
                  break;
               }
            case 32:
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends WebAppBaseBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 40;
               }
            case 5:
            case 6:
            default:
               break;
            case 7:
               if (s.equals("version")) {
                  return 32;
               }
               break;
            case 8:
               if (s.equals("ordering")) {
                  return 41;
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
            case 40:
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
            case 41:
               return new OrderingBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 32:
               return "version";
            case 40:
               return "name";
            case 41:
               return "ordering";
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
            case 40:
               return true;
            case 41:
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
            case 40:
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
            case 41:
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

   protected static class Helper extends WebAppBaseBeanImpl.Helper {
      private WebFragmentBeanImpl bean;

      protected Helper(WebFragmentBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 32:
               return "Version";
            case 40:
               return "Names";
            case 41:
               return "Orderings";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Names")) {
            return 40;
         } else if (propName.equals("Orderings")) {
            return 41;
         } else {
            return propName.equals("Version") ? 32 : super.getPropertyIndex(propName);
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
         iterators.add(new ArrayIterator(this.bean.getOrderings()));
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
            if (this.bean.isNamesSet()) {
               buf.append("Names");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getNames())));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getOrderings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getOrderings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            WebFragmentBeanImpl otherTyped = (WebFragmentBeanImpl)other;
            this.computeDiff("Names", this.bean.getNames(), otherTyped.getNames(), false);
            this.computeChildDiff("Orderings", this.bean.getOrderings(), otherTyped.getOrderings(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebFragmentBeanImpl original = (WebFragmentBeanImpl)event.getSourceBean();
            WebFragmentBeanImpl proposed = (WebFragmentBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Names")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 40);
               } else if (prop.equals("Orderings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addOrdering((OrderingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeOrdering((OrderingBean)update.getRemovedObject());
                  }

                  if (original.getOrderings() == null || original.getOrderings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 41);
                  }
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
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
            WebFragmentBeanImpl copy = (WebFragmentBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Names")) && this.bean.isNamesSet()) {
               Object o = this.bean.getNames();
               copy.setNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Orderings")) && this.bean.isOrderingsSet() && !copy._isSet(41)) {
               OrderingBean[] oldOrderings = this.bean.getOrderings();
               OrderingBean[] newOrderings = new OrderingBean[oldOrderings.length];

               for(int i = 0; i < newOrderings.length; ++i) {
                  newOrderings[i] = (OrderingBean)((OrderingBean)this.createCopy((AbstractDescriptorBean)oldOrderings[i], includeObsolete));
               }

               copy.setOrderings(newOrderings);
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
         this.inferSubTree(this.bean.getOrderings(), clazz, annotation);
      }
   }
}

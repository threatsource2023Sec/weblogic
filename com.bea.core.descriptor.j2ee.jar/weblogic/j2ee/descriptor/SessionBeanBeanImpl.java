package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.ejb.LocalHome;
import javax.ejb.RemoteHome;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SessionBeanBeanImpl extends AbstractDescriptorBean implements SessionBeanBean, Serializable {
   private AdministeredObjectBean[] _AdministeredObjects;
   private NamedMethodBean _AfterBeginMethod;
   private NamedMethodBean _AfterCompletionMethod;
   private AroundInvokeBean[] _AroundInvokes;
   private AroundTimeoutBean[] _AroundTimeouts;
   private AsyncMethodBean[] _AsyncMethods;
   private NamedMethodBean _BeforeCompletionMethod;
   private String[] _BusinessLocals;
   private String[] _BusinessRemotes;
   private String _ConcurrencyManagement;
   private ConcurrentMethodBean[] _ConcurrentMethods;
   private ConnectionFactoryResourceBean[] _ConnectionFactories;
   private DataSourceBean[] _DataSources;
   private DependsOnBean _DependsOn;
   private String[] _Descriptions;
   private String[] _DisplayNames;
   private String _EjbClass;
   private EjbLocalRefBean[] _EjbLocalRefs;
   private String _EjbName;
   private EjbRefBean[] _EjbRefs;
   private EnvEntryBean[] _EnvEntries;
   private String _Home;
   private IconBean[] _Icons;
   private String _Id;
   private InitMethodBean[] _InitMethods;
   private boolean _InitOnStartup;
   private JmsConnectionFactoryBean[] _JmsConnectionFactories;
   private JmsDestinationBean[] _JmsDestinations;
   private String _Local;
   private EmptyBean _LocalBean;
   private String _LocalHome;
   private MailSessionBean[] _MailSessions;
   private String _MappedName;
   private MessageDestinationRefBean[] _MessageDestinationRefs;
   private boolean _PassivationCapable;
   private PersistenceContextRefBean[] _PersistenceContextRefs;
   private PersistenceUnitRefBean[] _PersistenceUnitRefs;
   private LifecycleCallbackBean[] _PostActivates;
   private LifecycleCallbackBean[] _PostConstructs;
   private LifecycleCallbackBean[] _PreDestroys;
   private LifecycleCallbackBean[] _PrePassivates;
   private String _Remote;
   private RemoveMethodBean[] _RemoveMethods;
   private ResourceEnvRefBean[] _ResourceEnvRefs;
   private ResourceRefBean[] _ResourceRefs;
   private SecurityIdentityBean _SecurityIdentity;
   private SecurityRoleRefBean[] _SecurityRoleRefs;
   private String _ServiceEndpoint;
   private ServiceRefBean[] _ServiceRefs;
   private String _SessionType;
   private StatefulTimeoutBean _StatefulTimeout;
   private NamedMethodBean _TimeoutMethod;
   private TimerBean[] _Timers;
   private String _TransactionType;
   private static SchemaHelper2 _schemaHelper;

   public SessionBeanBeanImpl() {
      this._initializeProperty(-1);
   }

   public SessionBeanBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SessionBeanBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String getEjbName() {
      return this._EjbName;
   }

   public boolean isEjbNameInherited() {
      return false;
   }

   public boolean isEjbNameSet() {
      return this._isSet(3);
   }

   public void setEjbName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbName;
      this._EjbName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getMappedName() {
      return this._MappedName;
   }

   public boolean isMappedNameInherited() {
      return false;
   }

   public boolean isMappedNameSet() {
      return this._isSet(4);
   }

   public void setMappedName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MappedName;
      this._MappedName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getHome() {
      return this._Home;
   }

   public boolean isHomeInherited() {
      return false;
   }

   public boolean isHomeSet() {
      return this._isSet(5);
   }

   public void setHome(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Home;
      this._Home = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getRemote() {
      return this._Remote;
   }

   public boolean isRemoteInherited() {
      return false;
   }

   public boolean isRemoteSet() {
      return this._isSet(6);
   }

   public void setRemote(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Remote;
      this._Remote = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getLocalHome() {
      return this._LocalHome;
   }

   public boolean isLocalHomeInherited() {
      return false;
   }

   public boolean isLocalHomeSet() {
      return this._isSet(7);
   }

   public void setLocalHome(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LocalHome;
      this._LocalHome = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getLocal() {
      return this._Local;
   }

   public boolean isLocalInherited() {
      return false;
   }

   public boolean isLocalSet() {
      return this._isSet(8);
   }

   public void setLocal(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Local;
      this._Local = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String[] getBusinessLocals() {
      return this._BusinessLocals;
   }

   public boolean isBusinessLocalsInherited() {
      return false;
   }

   public boolean isBusinessLocalsSet() {
      return this._isSet(9);
   }

   public void addBusinessLocal(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(9)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getBusinessLocals(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setBusinessLocals(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeBusinessLocal(String param0) {
      String[] _old = this.getBusinessLocals();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setBusinessLocals(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setBusinessLocals(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._BusinessLocals;
      this._BusinessLocals = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String[] getBusinessRemotes() {
      return this._BusinessRemotes;
   }

   public boolean isBusinessRemotesInherited() {
      return false;
   }

   public boolean isBusinessRemotesSet() {
      return this._isSet(10);
   }

   public void addBusinessRemote(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(10)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getBusinessRemotes(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setBusinessRemotes(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeBusinessRemote(String param0) {
      String[] _old = this.getBusinessRemotes();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setBusinessRemotes(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public EmptyBean getLocalBean() {
      return this._LocalBean;
   }

   public boolean isLocalBeanInherited() {
      return false;
   }

   public boolean isLocalBeanSet() {
      return this._isSet(11);
   }

   public void setLocalBean(EmptyBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getLocalBean() != null && param0 != this.getLocalBean()) {
         throw new BeanAlreadyExistsException(this.getLocalBean() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 11)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         EmptyBean _oldVal = this._LocalBean;
         this._LocalBean = param0;
         this._postSet(11, _oldVal, param0);
      }
   }

   public EmptyBean createLocalBean() {
      EmptyBeanImpl _val = new EmptyBeanImpl(this, -1);

      try {
         this.setLocalBean(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLocalBean(EmptyBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._LocalBean;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLocalBean((EmptyBean)null);
               this._unSet(11);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void setBusinessRemotes(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._BusinessRemotes;
      this._BusinessRemotes = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getServiceEndpoint() {
      return this._ServiceEndpoint;
   }

   public boolean isServiceEndpointInherited() {
      return false;
   }

   public boolean isServiceEndpointSet() {
      return this._isSet(12);
   }

   public void setServiceEndpoint(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceEndpoint;
      this._ServiceEndpoint = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getEjbClass() {
      return this._EjbClass;
   }

   public boolean isEjbClassInherited() {
      return false;
   }

   public boolean isEjbClassSet() {
      return this._isSet(13);
   }

   public void setEjbClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbClass;
      this._EjbClass = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getSessionType() {
      return !this._isSet(14) ? null : this._SessionType;
   }

   public boolean isSessionTypeInherited() {
      return false;
   }

   public boolean isSessionTypeSet() {
      return this._isSet(14);
   }

   public void setSessionType(String param0) {
      if (param0 == null) {
         this._unSet(14);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"Singleton", "Stateful", "Stateless"};
         param0 = LegalChecks.checkInEnum("SessionType", param0, _set);
         String _oldVal = this._SessionType;
         this._SessionType = param0;
         this._postSet(14, _oldVal, param0);
      }
   }

   public StatefulTimeoutBean getStatefulTimeout() {
      return this._StatefulTimeout;
   }

   public boolean isStatefulTimeoutInherited() {
      return false;
   }

   public boolean isStatefulTimeoutSet() {
      return this._isSet(15);
   }

   public void setStatefulTimeout(StatefulTimeoutBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getStatefulTimeout() != null && param0 != this.getStatefulTimeout()) {
         throw new BeanAlreadyExistsException(this.getStatefulTimeout() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 15)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         StatefulTimeoutBean _oldVal = this._StatefulTimeout;
         this._StatefulTimeout = param0;
         this._postSet(15, _oldVal, param0);
      }
   }

   public StatefulTimeoutBean createStatefulTimeout() {
      StatefulTimeoutBeanImpl _val = new StatefulTimeoutBeanImpl(this, -1);

      try {
         this.setStatefulTimeout(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyStatefulTimeout(StatefulTimeoutBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._StatefulTimeout;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setStatefulTimeout((StatefulTimeoutBean)null);
               this._unSet(15);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public NamedMethodBean getTimeoutMethod() {
      return this._TimeoutMethod;
   }

   public boolean isTimeoutMethodInherited() {
      return false;
   }

   public boolean isTimeoutMethodSet() {
      return this._isSet(16);
   }

   public void setTimeoutMethod(NamedMethodBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getTimeoutMethod() != null && param0 != this.getTimeoutMethod()) {
         throw new BeanAlreadyExistsException(this.getTimeoutMethod() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 16)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NamedMethodBean _oldVal = this._TimeoutMethod;
         this._TimeoutMethod = param0;
         this._postSet(16, _oldVal, param0);
      }
   }

   public NamedMethodBean createTimeoutMethod() {
      NamedMethodBeanImpl _val = new NamedMethodBeanImpl(this, -1);

      try {
         this.setTimeoutMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTimeoutMethod(NamedMethodBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._TimeoutMethod;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTimeoutMethod((NamedMethodBean)null);
               this._unSet(16);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void addTimer(TimerBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         TimerBean[] _new;
         if (this._isSet(17)) {
            _new = (TimerBean[])((TimerBean[])this._getHelper()._extendArray(this.getTimers(), TimerBean.class, param0));
         } else {
            _new = new TimerBean[]{param0};
         }

         try {
            this.setTimers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public TimerBean[] getTimers() {
      return this._Timers;
   }

   public boolean isTimersInherited() {
      return false;
   }

   public boolean isTimersSet() {
      return this._isSet(17);
   }

   public void removeTimer(TimerBean param0) {
      this.destroyTimer(param0);
   }

   public void setTimers(TimerBean[] param0) throws InvalidAttributeValueException {
      TimerBean[] param0 = param0 == null ? new TimerBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 17)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      TimerBean[] _oldVal = this._Timers;
      this._Timers = (TimerBean[])param0;
      this._postSet(17, _oldVal, param0);
   }

   public TimerBean createTimer() {
      TimerBeanImpl _val = new TimerBeanImpl(this, -1);

      try {
         this.addTimer(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTimer(TimerBean param0) {
      try {
         this._checkIsPotentialChild(param0, 17);
         TimerBean[] _old = this.getTimers();
         TimerBean[] _new = (TimerBean[])((TimerBean[])this._getHelper()._removeElement(_old, TimerBean.class, param0));
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
               this.setTimers(_new);
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

   public boolean isInitOnStartup() {
      return this._InitOnStartup;
   }

   public boolean isInitOnStartupInherited() {
      return false;
   }

   public boolean isInitOnStartupSet() {
      return this._isSet(18);
   }

   public void setInitOnStartup(boolean param0) {
      boolean _oldVal = this._InitOnStartup;
      this._InitOnStartup = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getConcurrencyManagement() {
      return !this._isSet(19) ? null : this._ConcurrencyManagement;
   }

   public boolean isConcurrencyManagementInherited() {
      return false;
   }

   public boolean isConcurrencyManagementSet() {
      return this._isSet(19);
   }

   public void setConcurrencyManagement(String param0) {
      if (param0 == null) {
         this._unSet(19);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"Bean", "Container"};
         param0 = LegalChecks.checkInEnum("ConcurrencyManagement", param0, _set);
         String _oldVal = this._ConcurrencyManagement;
         this._ConcurrencyManagement = param0;
         this._postSet(19, _oldVal, param0);
      }
   }

   public void addConcurrentMethod(ConcurrentMethodBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 20)) {
         ConcurrentMethodBean[] _new;
         if (this._isSet(20)) {
            _new = (ConcurrentMethodBean[])((ConcurrentMethodBean[])this._getHelper()._extendArray(this.getConcurrentMethods(), ConcurrentMethodBean.class, param0));
         } else {
            _new = new ConcurrentMethodBean[]{param0};
         }

         try {
            this.setConcurrentMethods(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ConcurrentMethodBean[] getConcurrentMethods() {
      return this._ConcurrentMethods;
   }

   public boolean isConcurrentMethodsInherited() {
      return false;
   }

   public boolean isConcurrentMethodsSet() {
      return this._isSet(20);
   }

   public void removeConcurrentMethod(ConcurrentMethodBean param0) {
      this.destroyConcurrentMethod(param0);
   }

   public void setConcurrentMethods(ConcurrentMethodBean[] param0) throws InvalidAttributeValueException {
      ConcurrentMethodBean[] param0 = param0 == null ? new ConcurrentMethodBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 20)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ConcurrentMethodBean[] _oldVal = this._ConcurrentMethods;
      this._ConcurrentMethods = (ConcurrentMethodBean[])param0;
      this._postSet(20, _oldVal, param0);
   }

   public ConcurrentMethodBean createConcurrentMethod() {
      ConcurrentMethodBeanImpl _val = new ConcurrentMethodBeanImpl(this, -1);

      try {
         this.addConcurrentMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConcurrentMethod(ConcurrentMethodBean param0) {
      try {
         this._checkIsPotentialChild(param0, 20);
         ConcurrentMethodBean[] _old = this.getConcurrentMethods();
         ConcurrentMethodBean[] _new = (ConcurrentMethodBean[])((ConcurrentMethodBean[])this._getHelper()._removeElement(_old, ConcurrentMethodBean.class, param0));
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
               this.setConcurrentMethods(_new);
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

   public DependsOnBean getDependsOn() {
      return this._DependsOn;
   }

   public boolean isDependsOnInherited() {
      return false;
   }

   public boolean isDependsOnSet() {
      return this._isSet(21);
   }

   public void setDependsOn(DependsOnBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDependsOn() != null && param0 != this.getDependsOn()) {
         throw new BeanAlreadyExistsException(this.getDependsOn() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 21)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DependsOnBean _oldVal = this._DependsOn;
         this._DependsOn = param0;
         this._postSet(21, _oldVal, param0);
      }
   }

   public DependsOnBean createDependsOn() {
      DependsOnBeanImpl _val = new DependsOnBeanImpl(this, -1);

      try {
         this.setDependsOn(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDependsOn(DependsOnBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DependsOn;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDependsOn((DependsOnBean)null);
               this._unSet(21);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void addInitMethod(InitMethodBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 22)) {
         InitMethodBean[] _new;
         if (this._isSet(22)) {
            _new = (InitMethodBean[])((InitMethodBean[])this._getHelper()._extendArray(this.getInitMethods(), InitMethodBean.class, param0));
         } else {
            _new = new InitMethodBean[]{param0};
         }

         try {
            this.setInitMethods(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public InitMethodBean[] getInitMethods() {
      return this._InitMethods;
   }

   public boolean isInitMethodsInherited() {
      return false;
   }

   public boolean isInitMethodsSet() {
      return this._isSet(22);
   }

   public void removeInitMethod(InitMethodBean param0) {
      this.destroyInitMethod(param0);
   }

   public void setInitMethods(InitMethodBean[] param0) throws InvalidAttributeValueException {
      InitMethodBean[] param0 = param0 == null ? new InitMethodBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 22)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      InitMethodBean[] _oldVal = this._InitMethods;
      this._InitMethods = (InitMethodBean[])param0;
      this._postSet(22, _oldVal, param0);
   }

   public InitMethodBean createInitMethod() {
      InitMethodBeanImpl _val = new InitMethodBeanImpl(this, -1);

      try {
         this.addInitMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInitMethod(InitMethodBean param0) {
      try {
         this._checkIsPotentialChild(param0, 22);
         InitMethodBean[] _old = this.getInitMethods();
         InitMethodBean[] _new = (InitMethodBean[])((InitMethodBean[])this._getHelper()._removeElement(_old, InitMethodBean.class, param0));
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
               this.setInitMethods(_new);
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

   public void addRemoveMethod(RemoveMethodBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 23)) {
         RemoveMethodBean[] _new;
         if (this._isSet(23)) {
            _new = (RemoveMethodBean[])((RemoveMethodBean[])this._getHelper()._extendArray(this.getRemoveMethods(), RemoveMethodBean.class, param0));
         } else {
            _new = new RemoveMethodBean[]{param0};
         }

         try {
            this.setRemoveMethods(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public RemoveMethodBean[] getRemoveMethods() {
      return this._RemoveMethods;
   }

   public boolean isRemoveMethodsInherited() {
      return false;
   }

   public boolean isRemoveMethodsSet() {
      return this._isSet(23);
   }

   public void removeRemoveMethod(RemoveMethodBean param0) {
      this.destroyRemoveMethod(param0);
   }

   public void setRemoveMethods(RemoveMethodBean[] param0) throws InvalidAttributeValueException {
      RemoveMethodBean[] param0 = param0 == null ? new RemoveMethodBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 23)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      RemoveMethodBean[] _oldVal = this._RemoveMethods;
      this._RemoveMethods = (RemoveMethodBean[])param0;
      this._postSet(23, _oldVal, param0);
   }

   public RemoveMethodBean createRemoveMethod() {
      RemoveMethodBeanImpl _val = new RemoveMethodBeanImpl(this, -1);

      try {
         this.addRemoveMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyRemoveMethod(RemoveMethodBean param0) {
      try {
         this._checkIsPotentialChild(param0, 23);
         RemoveMethodBean[] _old = this.getRemoveMethods();
         RemoveMethodBean[] _new = (RemoveMethodBean[])((RemoveMethodBean[])this._getHelper()._removeElement(_old, RemoveMethodBean.class, param0));
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
               this.setRemoveMethods(_new);
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

   public void addAsyncMethod(AsyncMethodBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 24)) {
         AsyncMethodBean[] _new;
         if (this._isSet(24)) {
            _new = (AsyncMethodBean[])((AsyncMethodBean[])this._getHelper()._extendArray(this.getAsyncMethods(), AsyncMethodBean.class, param0));
         } else {
            _new = new AsyncMethodBean[]{param0};
         }

         try {
            this.setAsyncMethods(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AsyncMethodBean[] getAsyncMethods() {
      return this._AsyncMethods;
   }

   public boolean isAsyncMethodsInherited() {
      return false;
   }

   public boolean isAsyncMethodsSet() {
      return this._isSet(24);
   }

   public void removeAsyncMethod(AsyncMethodBean param0) {
      this.destroyAsyncMethod(param0);
   }

   public void setAsyncMethods(AsyncMethodBean[] param0) throws InvalidAttributeValueException {
      AsyncMethodBean[] param0 = param0 == null ? new AsyncMethodBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 24)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AsyncMethodBean[] _oldVal = this._AsyncMethods;
      this._AsyncMethods = (AsyncMethodBean[])param0;
      this._postSet(24, _oldVal, param0);
   }

   public AsyncMethodBean createAsyncMethod() {
      AsyncMethodBeanImpl _val = new AsyncMethodBeanImpl(this, -1);

      try {
         this.addAsyncMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAsyncMethod(AsyncMethodBean param0) {
      try {
         this._checkIsPotentialChild(param0, 24);
         AsyncMethodBean[] _old = this.getAsyncMethods();
         AsyncMethodBean[] _new = (AsyncMethodBean[])((AsyncMethodBean[])this._getHelper()._removeElement(_old, AsyncMethodBean.class, param0));
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
               this.setAsyncMethods(_new);
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

   public String getTransactionType() {
      return !this._isSet(25) ? null : this._TransactionType;
   }

   public boolean isTransactionTypeInherited() {
      return false;
   }

   public boolean isTransactionTypeSet() {
      return this._isSet(25);
   }

   public void setTransactionType(String param0) {
      if (param0 == null) {
         this._unSet(25);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"Bean", "Container"};
         param0 = LegalChecks.checkInEnum("TransactionType", param0, _set);
         String _oldVal = this._TransactionType;
         this._TransactionType = param0;
         this._postSet(25, _oldVal, param0);
      }
   }

   public NamedMethodBean getAfterBeginMethod() {
      return this._AfterBeginMethod;
   }

   public boolean isAfterBeginMethodInherited() {
      return false;
   }

   public boolean isAfterBeginMethodSet() {
      return this._isSet(26);
   }

   public void setAfterBeginMethod(NamedMethodBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAfterBeginMethod() != null && param0 != this.getAfterBeginMethod()) {
         throw new BeanAlreadyExistsException(this.getAfterBeginMethod() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 26)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NamedMethodBean _oldVal = this._AfterBeginMethod;
         this._AfterBeginMethod = param0;
         this._postSet(26, _oldVal, param0);
      }
   }

   public NamedMethodBean createAfterBeginMethod() {
      NamedMethodBeanImpl _val = new NamedMethodBeanImpl(this, -1);

      try {
         this.setAfterBeginMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAfterBeginMethod(NamedMethodBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._AfterBeginMethod;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAfterBeginMethod((NamedMethodBean)null);
               this._unSet(26);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public NamedMethodBean getBeforeCompletionMethod() {
      return this._BeforeCompletionMethod;
   }

   public boolean isBeforeCompletionMethodInherited() {
      return false;
   }

   public boolean isBeforeCompletionMethodSet() {
      return this._isSet(27);
   }

   public void setBeforeCompletionMethod(NamedMethodBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getBeforeCompletionMethod() != null && param0 != this.getBeforeCompletionMethod()) {
         throw new BeanAlreadyExistsException(this.getBeforeCompletionMethod() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 27)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NamedMethodBean _oldVal = this._BeforeCompletionMethod;
         this._BeforeCompletionMethod = param0;
         this._postSet(27, _oldVal, param0);
      }
   }

   public NamedMethodBean createBeforeCompletionMethod() {
      NamedMethodBeanImpl _val = new NamedMethodBeanImpl(this, -1);

      try {
         this.setBeforeCompletionMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyBeforeCompletionMethod(NamedMethodBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._BeforeCompletionMethod;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setBeforeCompletionMethod((NamedMethodBean)null);
               this._unSet(27);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public NamedMethodBean getAfterCompletionMethod() {
      return this._AfterCompletionMethod;
   }

   public boolean isAfterCompletionMethodInherited() {
      return false;
   }

   public boolean isAfterCompletionMethodSet() {
      return this._isSet(28);
   }

   public void setAfterCompletionMethod(NamedMethodBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAfterCompletionMethod() != null && param0 != this.getAfterCompletionMethod()) {
         throw new BeanAlreadyExistsException(this.getAfterCompletionMethod() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 28)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NamedMethodBean _oldVal = this._AfterCompletionMethod;
         this._AfterCompletionMethod = param0;
         this._postSet(28, _oldVal, param0);
      }
   }

   public NamedMethodBean createAfterCompletionMethod() {
      NamedMethodBeanImpl _val = new NamedMethodBeanImpl(this, -1);

      try {
         this.setAfterCompletionMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAfterCompletionMethod(NamedMethodBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._AfterCompletionMethod;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAfterCompletionMethod((NamedMethodBean)null);
               this._unSet(28);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void addAroundInvoke(AroundInvokeBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 29)) {
         AroundInvokeBean[] _new;
         if (this._isSet(29)) {
            _new = (AroundInvokeBean[])((AroundInvokeBean[])this._getHelper()._extendArray(this.getAroundInvokes(), AroundInvokeBean.class, param0));
         } else {
            _new = new AroundInvokeBean[]{param0};
         }

         try {
            this.setAroundInvokes(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AroundInvokeBean[] getAroundInvokes() {
      return this._AroundInvokes;
   }

   public boolean isAroundInvokesInherited() {
      return false;
   }

   public boolean isAroundInvokesSet() {
      return this._isSet(29);
   }

   public void removeAroundInvoke(AroundInvokeBean param0) {
      this.destroyAroundInvoke(param0);
   }

   public void setAroundInvokes(AroundInvokeBean[] param0) throws InvalidAttributeValueException {
      AroundInvokeBean[] param0 = param0 == null ? new AroundInvokeBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 29)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AroundInvokeBean[] _oldVal = this._AroundInvokes;
      this._AroundInvokes = (AroundInvokeBean[])param0;
      this._postSet(29, _oldVal, param0);
   }

   public AroundInvokeBean createAroundInvoke() {
      AroundInvokeBeanImpl _val = new AroundInvokeBeanImpl(this, -1);

      try {
         this.addAroundInvoke(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAroundInvoke(AroundInvokeBean param0) {
      try {
         this._checkIsPotentialChild(param0, 29);
         AroundInvokeBean[] _old = this.getAroundInvokes();
         AroundInvokeBean[] _new = (AroundInvokeBean[])((AroundInvokeBean[])this._getHelper()._removeElement(_old, AroundInvokeBean.class, param0));
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
               this.setAroundInvokes(_new);
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

   public void addAroundTimeout(AroundTimeoutBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 30)) {
         AroundTimeoutBean[] _new;
         if (this._isSet(30)) {
            _new = (AroundTimeoutBean[])((AroundTimeoutBean[])this._getHelper()._extendArray(this.getAroundTimeouts(), AroundTimeoutBean.class, param0));
         } else {
            _new = new AroundTimeoutBean[]{param0};
         }

         try {
            this.setAroundTimeouts(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AroundTimeoutBean[] getAroundTimeouts() {
      return this._AroundTimeouts;
   }

   public boolean isAroundTimeoutsInherited() {
      return false;
   }

   public boolean isAroundTimeoutsSet() {
      return this._isSet(30);
   }

   public void removeAroundTimeout(AroundTimeoutBean param0) {
      this.destroyAroundTimeout(param0);
   }

   public void setAroundTimeouts(AroundTimeoutBean[] param0) throws InvalidAttributeValueException {
      AroundTimeoutBean[] param0 = param0 == null ? new AroundTimeoutBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 30)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AroundTimeoutBean[] _oldVal = this._AroundTimeouts;
      this._AroundTimeouts = (AroundTimeoutBean[])param0;
      this._postSet(30, _oldVal, param0);
   }

   public AroundTimeoutBean createAroundTimeout() {
      AroundTimeoutBeanImpl _val = new AroundTimeoutBeanImpl(this, -1);

      try {
         this.addAroundTimeout(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAroundTimeout(AroundTimeoutBean param0) {
      try {
         this._checkIsPotentialChild(param0, 30);
         AroundTimeoutBean[] _old = this.getAroundTimeouts();
         AroundTimeoutBean[] _new = (AroundTimeoutBean[])((AroundTimeoutBean[])this._getHelper()._removeElement(_old, AroundTimeoutBean.class, param0));
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
               this.setAroundTimeouts(_new);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 31)) {
         EnvEntryBean[] _new;
         if (this._isSet(31)) {
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
      return this._isSet(31);
   }

   public void removeEnvEntry(EnvEntryBean param0) {
      this.destroyEnvEntry(param0);
   }

   public void setEnvEntries(EnvEntryBean[] param0) throws InvalidAttributeValueException {
      EnvEntryBean[] param0 = param0 == null ? new EnvEntryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 31)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EnvEntryBean[] _oldVal = this._EnvEntries;
      this._EnvEntries = (EnvEntryBean[])param0;
      this._postSet(31, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 31);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 32)) {
         EjbRefBean[] _new;
         if (this._isSet(32)) {
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
      return this._isSet(32);
   }

   public void removeEjbRef(EjbRefBean param0) {
      this.destroyEjbRef(param0);
   }

   public void setEjbRefs(EjbRefBean[] param0) throws InvalidAttributeValueException {
      EjbRefBean[] param0 = param0 == null ? new EjbRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 32)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EjbRefBean[] _oldVal = this._EjbRefs;
      this._EjbRefs = (EjbRefBean[])param0;
      this._postSet(32, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 32);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 33)) {
         EjbLocalRefBean[] _new;
         if (this._isSet(33)) {
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
      return this._isSet(33);
   }

   public void removeEjbLocalRef(EjbLocalRefBean param0) {
      this.destroyEjbLocalRef(param0);
   }

   public void setEjbLocalRefs(EjbLocalRefBean[] param0) throws InvalidAttributeValueException {
      EjbLocalRefBean[] param0 = param0 == null ? new EjbLocalRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 33)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EjbLocalRefBean[] _oldVal = this._EjbLocalRefs;
      this._EjbLocalRefs = (EjbLocalRefBean[])param0;
      this._postSet(33, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 33);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 34)) {
         ServiceRefBean[] _new;
         if (this._isSet(34)) {
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
      return this._isSet(34);
   }

   public void removeServiceRef(ServiceRefBean param0) {
      this.destroyServiceRef(param0);
   }

   public void setServiceRefs(ServiceRefBean[] param0) throws InvalidAttributeValueException {
      ServiceRefBean[] param0 = param0 == null ? new ServiceRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 34)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServiceRefBean[] _oldVal = this._ServiceRefs;
      this._ServiceRefs = (ServiceRefBean[])param0;
      this._postSet(34, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 34);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 35)) {
         ResourceRefBean[] _new;
         if (this._isSet(35)) {
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
      return this._isSet(35);
   }

   public void removeResourceRef(ResourceRefBean param0) {
      this.destroyResourceRef(param0);
   }

   public void setResourceRefs(ResourceRefBean[] param0) throws InvalidAttributeValueException {
      ResourceRefBean[] param0 = param0 == null ? new ResourceRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 35)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceRefBean[] _oldVal = this._ResourceRefs;
      this._ResourceRefs = (ResourceRefBean[])param0;
      this._postSet(35, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 35);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 36)) {
         ResourceEnvRefBean[] _new;
         if (this._isSet(36)) {
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
      return this._isSet(36);
   }

   public void removeResourceEnvRef(ResourceEnvRefBean param0) {
      this.destroyResourceEnvRef(param0);
   }

   public void setResourceEnvRefs(ResourceEnvRefBean[] param0) throws InvalidAttributeValueException {
      ResourceEnvRefBean[] param0 = param0 == null ? new ResourceEnvRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 36)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResourceEnvRefBean[] _oldVal = this._ResourceEnvRefs;
      this._ResourceEnvRefs = (ResourceEnvRefBean[])param0;
      this._postSet(36, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 36);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 37)) {
         MessageDestinationRefBean[] _new;
         if (this._isSet(37)) {
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
      return this._isSet(37);
   }

   public void removeMessageDestinationRef(MessageDestinationRefBean param0) {
      this.destroyMessageDestinationRef(param0);
   }

   public void setMessageDestinationRefs(MessageDestinationRefBean[] param0) throws InvalidAttributeValueException {
      MessageDestinationRefBean[] param0 = param0 == null ? new MessageDestinationRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 37)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MessageDestinationRefBean[] _oldVal = this._MessageDestinationRefs;
      this._MessageDestinationRefs = (MessageDestinationRefBean[])param0;
      this._postSet(37, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 37);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 38)) {
         PersistenceContextRefBean[] _new;
         if (this._isSet(38)) {
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
      return this._isSet(38);
   }

   public void removePersistenceContextRef(PersistenceContextRefBean param0) {
      this.destroyPersistenceContextRef(param0);
   }

   public void setPersistenceContextRefs(PersistenceContextRefBean[] param0) throws InvalidAttributeValueException {
      PersistenceContextRefBean[] param0 = param0 == null ? new PersistenceContextRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 38)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PersistenceContextRefBean[] _oldVal = this._PersistenceContextRefs;
      this._PersistenceContextRefs = (PersistenceContextRefBean[])param0;
      this._postSet(38, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 38);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 39)) {
         PersistenceUnitRefBean[] _new;
         if (this._isSet(39)) {
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
      return this._isSet(39);
   }

   public void removePersistenceUnitRef(PersistenceUnitRefBean param0) {
      this.destroyPersistenceUnitRef(param0);
   }

   public void setPersistenceUnitRefs(PersistenceUnitRefBean[] param0) throws InvalidAttributeValueException {
      PersistenceUnitRefBean[] param0 = param0 == null ? new PersistenceUnitRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 39)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PersistenceUnitRefBean[] _oldVal = this._PersistenceUnitRefs;
      this._PersistenceUnitRefs = (PersistenceUnitRefBean[])param0;
      this._postSet(39, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 39);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 40)) {
         LifecycleCallbackBean[] _new;
         if (this._isSet(40)) {
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
      return this._isSet(40);
   }

   public void removePostConstruct(LifecycleCallbackBean param0) {
      this.destroyPostConstruct(param0);
   }

   public void setPostConstructs(LifecycleCallbackBean[] param0) throws InvalidAttributeValueException {
      LifecycleCallbackBean[] param0 = param0 == null ? new LifecycleCallbackBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 40)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LifecycleCallbackBean[] _oldVal = this._PostConstructs;
      this._PostConstructs = (LifecycleCallbackBean[])param0;
      this._postSet(40, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 40);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 41)) {
         LifecycleCallbackBean[] _new;
         if (this._isSet(41)) {
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
      return this._isSet(41);
   }

   public void removePreDestroy(LifecycleCallbackBean param0) {
      this.destroyPreDestroy(param0);
   }

   public void setPreDestroys(LifecycleCallbackBean[] param0) throws InvalidAttributeValueException {
      LifecycleCallbackBean[] param0 = param0 == null ? new LifecycleCallbackBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 41)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LifecycleCallbackBean[] _oldVal = this._PreDestroys;
      this._PreDestroys = (LifecycleCallbackBean[])param0;
      this._postSet(41, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 41);
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

   public void addPostActivate(LifecycleCallbackBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 42)) {
         LifecycleCallbackBean[] _new;
         if (this._isSet(42)) {
            _new = (LifecycleCallbackBean[])((LifecycleCallbackBean[])this._getHelper()._extendArray(this.getPostActivates(), LifecycleCallbackBean.class, param0));
         } else {
            _new = new LifecycleCallbackBean[]{param0};
         }

         try {
            this.setPostActivates(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LifecycleCallbackBean[] getPostActivates() {
      return this._PostActivates;
   }

   public boolean isPostActivatesInherited() {
      return false;
   }

   public boolean isPostActivatesSet() {
      return this._isSet(42);
   }

   public void removePostActivate(LifecycleCallbackBean param0) {
      this.destroyPostActivate(param0);
   }

   public void setPostActivates(LifecycleCallbackBean[] param0) throws InvalidAttributeValueException {
      LifecycleCallbackBean[] param0 = param0 == null ? new LifecycleCallbackBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 42)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LifecycleCallbackBean[] _oldVal = this._PostActivates;
      this._PostActivates = (LifecycleCallbackBean[])param0;
      this._postSet(42, _oldVal, param0);
   }

   public LifecycleCallbackBean createPostActivate() {
      LifecycleCallbackBeanImpl _val = new LifecycleCallbackBeanImpl(this, -1);

      try {
         this.addPostActivate(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPostActivate(LifecycleCallbackBean param0) {
      try {
         this._checkIsPotentialChild(param0, 42);
         LifecycleCallbackBean[] _old = this.getPostActivates();
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
               this.setPostActivates(_new);
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

   public void addPrePassivate(LifecycleCallbackBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 43)) {
         LifecycleCallbackBean[] _new;
         if (this._isSet(43)) {
            _new = (LifecycleCallbackBean[])((LifecycleCallbackBean[])this._getHelper()._extendArray(this.getPrePassivates(), LifecycleCallbackBean.class, param0));
         } else {
            _new = new LifecycleCallbackBean[]{param0};
         }

         try {
            this.setPrePassivates(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LifecycleCallbackBean[] getPrePassivates() {
      return this._PrePassivates;
   }

   public boolean isPrePassivatesInherited() {
      return false;
   }

   public boolean isPrePassivatesSet() {
      return this._isSet(43);
   }

   public void removePrePassivate(LifecycleCallbackBean param0) {
      this.destroyPrePassivate(param0);
   }

   public void setPrePassivates(LifecycleCallbackBean[] param0) throws InvalidAttributeValueException {
      LifecycleCallbackBean[] param0 = param0 == null ? new LifecycleCallbackBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 43)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LifecycleCallbackBean[] _oldVal = this._PrePassivates;
      this._PrePassivates = (LifecycleCallbackBean[])param0;
      this._postSet(43, _oldVal, param0);
   }

   public LifecycleCallbackBean createPrePassivate() {
      LifecycleCallbackBeanImpl _val = new LifecycleCallbackBeanImpl(this, -1);

      try {
         this.addPrePassivate(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPrePassivate(LifecycleCallbackBean param0) {
      try {
         this._checkIsPotentialChild(param0, 43);
         LifecycleCallbackBean[] _old = this.getPrePassivates();
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
               this.setPrePassivates(_new);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 44)) {
         DataSourceBean[] _new;
         if (this._isSet(44)) {
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
      return this._isSet(44);
   }

   public void removeDataSource(DataSourceBean param0) {
      this.destroyDataSource(param0);
   }

   public void setDataSources(DataSourceBean[] param0) throws InvalidAttributeValueException {
      DataSourceBean[] param0 = param0 == null ? new DataSourceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 44)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      DataSourceBean[] _oldVal = this._DataSources;
      this._DataSources = (DataSourceBean[])param0;
      this._postSet(44, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 44);
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

   public void addJmsConnectionFactory(JmsConnectionFactoryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 45)) {
         JmsConnectionFactoryBean[] _new;
         if (this._isSet(45)) {
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
      return this._isSet(45);
   }

   public void removeJmsConnectionFactory(JmsConnectionFactoryBean param0) {
      this.destroyJmsConnectionFactory(param0);
   }

   public void setJmsConnectionFactories(JmsConnectionFactoryBean[] param0) throws InvalidAttributeValueException {
      JmsConnectionFactoryBean[] param0 = param0 == null ? new JmsConnectionFactoryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 45)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JmsConnectionFactoryBean[] _oldVal = this._JmsConnectionFactories;
      this._JmsConnectionFactories = (JmsConnectionFactoryBean[])param0;
      this._postSet(45, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 45);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 46)) {
         JmsDestinationBean[] _new;
         if (this._isSet(46)) {
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
      return this._isSet(46);
   }

   public void removeJmsDestination(JmsDestinationBean param0) {
      this.destroyJmsDestination(param0);
   }

   public void setJmsDestinations(JmsDestinationBean[] param0) throws InvalidAttributeValueException {
      JmsDestinationBean[] param0 = param0 == null ? new JmsDestinationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 46)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JmsDestinationBean[] _oldVal = this._JmsDestinations;
      this._JmsDestinations = (JmsDestinationBean[])param0;
      this._postSet(46, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 46);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 47)) {
         MailSessionBean[] _new;
         if (this._isSet(47)) {
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
      return this._isSet(47);
   }

   public void removeMailSession(MailSessionBean param0) {
      this.destroyMailSession(param0);
   }

   public void setMailSessions(MailSessionBean[] param0) throws InvalidAttributeValueException {
      MailSessionBean[] param0 = param0 == null ? new MailSessionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 47)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MailSessionBean[] _oldVal = this._MailSessions;
      this._MailSessions = (MailSessionBean[])param0;
      this._postSet(47, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 47);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 48)) {
         ConnectionFactoryResourceBean[] _new;
         if (this._isSet(48)) {
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
      return this._isSet(48);
   }

   public void removeConnectionFactory(ConnectionFactoryResourceBean param0) {
      this.destroyConnectionFactory(param0);
   }

   public void setConnectionFactories(ConnectionFactoryResourceBean[] param0) throws InvalidAttributeValueException {
      ConnectionFactoryResourceBean[] param0 = param0 == null ? new ConnectionFactoryResourceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 48)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ConnectionFactoryResourceBean[] _oldVal = this._ConnectionFactories;
      this._ConnectionFactories = (ConnectionFactoryResourceBean[])param0;
      this._postSet(48, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 48);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 49)) {
         AdministeredObjectBean[] _new;
         if (this._isSet(49)) {
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
      return this._isSet(49);
   }

   public void removeAdministeredObject(AdministeredObjectBean param0) {
      this.destroyAdministeredObject(param0);
   }

   public void setAdministeredObjects(AdministeredObjectBean[] param0) throws InvalidAttributeValueException {
      AdministeredObjectBean[] param0 = param0 == null ? new AdministeredObjectBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 49)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AdministeredObjectBean[] _oldVal = this._AdministeredObjects;
      this._AdministeredObjects = (AdministeredObjectBean[])param0;
      this._postSet(49, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 49);
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

   public void addSecurityRoleRef(SecurityRoleRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 50)) {
         SecurityRoleRefBean[] _new;
         if (this._isSet(50)) {
            _new = (SecurityRoleRefBean[])((SecurityRoleRefBean[])this._getHelper()._extendArray(this.getSecurityRoleRefs(), SecurityRoleRefBean.class, param0));
         } else {
            _new = new SecurityRoleRefBean[]{param0};
         }

         try {
            this.setSecurityRoleRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SecurityRoleRefBean[] getSecurityRoleRefs() {
      return this._SecurityRoleRefs;
   }

   public boolean isSecurityRoleRefsInherited() {
      return false;
   }

   public boolean isSecurityRoleRefsSet() {
      return this._isSet(50);
   }

   public void removeSecurityRoleRef(SecurityRoleRefBean param0) {
      this.destroySecurityRoleRef(param0);
   }

   public void setSecurityRoleRefs(SecurityRoleRefBean[] param0) throws InvalidAttributeValueException {
      SecurityRoleRefBean[] param0 = param0 == null ? new SecurityRoleRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 50)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SecurityRoleRefBean[] _oldVal = this._SecurityRoleRefs;
      this._SecurityRoleRefs = (SecurityRoleRefBean[])param0;
      this._postSet(50, _oldVal, param0);
   }

   public SecurityRoleRefBean createSecurityRoleRef() {
      SecurityRoleRefBeanImpl _val = new SecurityRoleRefBeanImpl(this, -1);

      try {
         this.addSecurityRoleRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySecurityRoleRef(SecurityRoleRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 50);
         SecurityRoleRefBean[] _old = this.getSecurityRoleRefs();
         SecurityRoleRefBean[] _new = (SecurityRoleRefBean[])((SecurityRoleRefBean[])this._getHelper()._removeElement(_old, SecurityRoleRefBean.class, param0));
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
               this.setSecurityRoleRefs(_new);
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

   public SecurityIdentityBean getSecurityIdentity() {
      return this._SecurityIdentity;
   }

   public boolean isSecurityIdentityInherited() {
      return false;
   }

   public boolean isSecurityIdentitySet() {
      return this._isSet(51);
   }

   public void setSecurityIdentity(SecurityIdentityBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSecurityIdentity() != null && param0 != this.getSecurityIdentity()) {
         throw new BeanAlreadyExistsException(this.getSecurityIdentity() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 51)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SecurityIdentityBean _oldVal = this._SecurityIdentity;
         this._SecurityIdentity = param0;
         this._postSet(51, _oldVal, param0);
      }
   }

   public SecurityIdentityBean createSecurityIdentity() {
      SecurityIdentityBeanImpl _val = new SecurityIdentityBeanImpl(this, -1);

      try {
         this.setSecurityIdentity(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySecurityIdentity(SecurityIdentityBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._SecurityIdentity;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSecurityIdentity((SecurityIdentityBean)null);
               this._unSet(51);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean isPassivationCapable() {
      return this._PassivationCapable;
   }

   public boolean isPassivationCapableInherited() {
      return false;
   }

   public boolean isPassivationCapableSet() {
      return this._isSet(52);
   }

   public void setPassivationCapable(boolean param0) {
      boolean _oldVal = this._PassivationCapable;
      this._PassivationCapable = param0;
      this._postSet(52, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(53);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(53, _oldVal, param0);
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
         idx = 49;
      }

      try {
         switch (idx) {
            case 49:
               this._AdministeredObjects = new AdministeredObjectBean[0];
               if (initOne) {
                  break;
               }
            case 26:
               this._AfterBeginMethod = null;
               if (initOne) {
                  break;
               }
            case 28:
               this._AfterCompletionMethod = null;
               if (initOne) {
                  break;
               }
            case 29:
               this._AroundInvokes = new AroundInvokeBean[0];
               if (initOne) {
                  break;
               }
            case 30:
               this._AroundTimeouts = new AroundTimeoutBean[0];
               if (initOne) {
                  break;
               }
            case 24:
               this._AsyncMethods = new AsyncMethodBean[0];
               if (initOne) {
                  break;
               }
            case 27:
               this._BeforeCompletionMethod = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._BusinessLocals = new String[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._BusinessRemotes = new String[0];
               if (initOne) {
                  break;
               }
            case 19:
               this._ConcurrencyManagement = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._ConcurrentMethods = new ConcurrentMethodBean[0];
               if (initOne) {
                  break;
               }
            case 48:
               this._ConnectionFactories = new ConnectionFactoryResourceBean[0];
               if (initOne) {
                  break;
               }
            case 44:
               this._DataSources = new DataSourceBean[0];
               if (initOne) {
                  break;
               }
            case 21:
               this._DependsOn = null;
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
            case 13:
               this._EjbClass = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._EjbLocalRefs = new EjbLocalRefBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._EjbName = null;
               if (initOne) {
                  break;
               }
            case 32:
               this._EjbRefs = new EjbRefBean[0];
               if (initOne) {
                  break;
               }
            case 31:
               this._EnvEntries = new EnvEntryBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._Home = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Icons = new IconBean[0];
               if (initOne) {
                  break;
               }
            case 53:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._InitMethods = new InitMethodBean[0];
               if (initOne) {
                  break;
               }
            case 45:
               this._JmsConnectionFactories = new JmsConnectionFactoryBean[0];
               if (initOne) {
                  break;
               }
            case 46:
               this._JmsDestinations = new JmsDestinationBean[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._Local = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._LocalBean = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._LocalHome = null;
               if (initOne) {
                  break;
               }
            case 47:
               this._MailSessions = new MailSessionBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._MappedName = null;
               if (initOne) {
                  break;
               }
            case 37:
               this._MessageDestinationRefs = new MessageDestinationRefBean[0];
               if (initOne) {
                  break;
               }
            case 38:
               this._PersistenceContextRefs = new PersistenceContextRefBean[0];
               if (initOne) {
                  break;
               }
            case 39:
               this._PersistenceUnitRefs = new PersistenceUnitRefBean[0];
               if (initOne) {
                  break;
               }
            case 42:
               this._PostActivates = new LifecycleCallbackBean[0];
               if (initOne) {
                  break;
               }
            case 40:
               this._PostConstructs = new LifecycleCallbackBean[0];
               if (initOne) {
                  break;
               }
            case 41:
               this._PreDestroys = new LifecycleCallbackBean[0];
               if (initOne) {
                  break;
               }
            case 43:
               this._PrePassivates = new LifecycleCallbackBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._Remote = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._RemoveMethods = new RemoveMethodBean[0];
               if (initOne) {
                  break;
               }
            case 36:
               this._ResourceEnvRefs = new ResourceEnvRefBean[0];
               if (initOne) {
                  break;
               }
            case 35:
               this._ResourceRefs = new ResourceRefBean[0];
               if (initOne) {
                  break;
               }
            case 51:
               this._SecurityIdentity = null;
               if (initOne) {
                  break;
               }
            case 50:
               this._SecurityRoleRefs = new SecurityRoleRefBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._ServiceEndpoint = null;
               if (initOne) {
                  break;
               }
            case 34:
               this._ServiceRefs = new ServiceRefBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._SessionType = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._StatefulTimeout = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._TimeoutMethod = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._Timers = new TimerBean[0];
               if (initOne) {
                  break;
               }
            case 25:
               this._TransactionType = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._InitOnStartup = false;
               if (initOne) {
                  break;
               }
            case 52:
               this._PassivationCapable = true;
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
                  return 53;
               }
            case 3:
            case 21:
            default:
               break;
            case 4:
               if (s.equals("home")) {
                  return 5;
               }

               if (s.equals("icon")) {
                  return 2;
               }
               break;
            case 5:
               if (s.equals("local")) {
                  return 8;
               }

               if (s.equals("timer")) {
                  return 17;
               }
               break;
            case 6:
               if (s.equals("remote")) {
                  return 6;
               }
               break;
            case 7:
               if (s.equals("ejb-ref")) {
                  return 32;
               }
               break;
            case 8:
               if (s.equals("ejb-name")) {
                  return 3;
               }
               break;
            case 9:
               if (s.equals("ejb-class")) {
                  return 13;
               }

               if (s.equals("env-entry")) {
                  return 31;
               }
               break;
            case 10:
               if (s.equals("depends-on")) {
                  return 21;
               }

               if (s.equals("local-bean")) {
                  return 11;
               }

               if (s.equals("local-home")) {
                  return 7;
               }
               break;
            case 11:
               if (s.equals("data-source")) {
                  return 44;
               }

               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("init-method")) {
                  return 22;
               }

               if (s.equals("mapped-name")) {
                  return 4;
               }

               if (s.equals("pre-destroy")) {
                  return 41;
               }

               if (s.equals("service-ref")) {
                  return 34;
               }
               break;
            case 12:
               if (s.equals("async-method")) {
                  return 24;
               }

               if (s.equals("display-name")) {
                  return 1;
               }

               if (s.equals("mail-session")) {
                  return 47;
               }

               if (s.equals("resource-ref")) {
                  return 35;
               }

               if (s.equals("session-type")) {
                  return 14;
               }
               break;
            case 13:
               if (s.equals("around-invoke")) {
                  return 29;
               }

               if (s.equals("ejb-local-ref")) {
                  return 33;
               }

               if (s.equals("post-activate")) {
                  return 42;
               }

               if (s.equals("pre-passivate")) {
                  return 43;
               }

               if (s.equals("remove-method")) {
                  return 23;
               }
               break;
            case 14:
               if (s.equals("around-timeout")) {
                  return 30;
               }

               if (s.equals("business-local")) {
                  return 9;
               }

               if (s.equals("post-construct")) {
                  return 40;
               }

               if (s.equals("timeout-method")) {
                  return 16;
               }
               break;
            case 15:
               if (s.equals("business-remote")) {
                  return 10;
               }

               if (s.equals("jms-destination")) {
                  return 46;
               }

               if (s.equals("init-on-startup")) {
                  return 18;
               }
               break;
            case 16:
               if (s.equals("resource-env-ref")) {
                  return 36;
               }

               if (s.equals("service-endpoint")) {
                  return 12;
               }

               if (s.equals("stateful-timeout")) {
                  return 15;
               }

               if (s.equals("transaction-type")) {
                  return 25;
               }
               break;
            case 17:
               if (s.equals("concurrent-method")) {
                  return 20;
               }

               if (s.equals("security-identity")) {
                  return 51;
               }

               if (s.equals("security-role-ref")) {
                  return 50;
               }
               break;
            case 18:
               if (s.equals("after-begin-method")) {
                  return 26;
               }

               if (s.equals("connection-factory")) {
                  return 48;
               }
               break;
            case 19:
               if (s.equals("administered-object")) {
                  return 49;
               }

               if (s.equals("passivation-capable")) {
                  return 52;
               }
               break;
            case 20:
               if (s.equals("persistence-unit-ref")) {
                  return 39;
               }
               break;
            case 22:
               if (s.equals("concurrency-management")) {
                  return 19;
               }

               if (s.equals("jms-connection-factory")) {
                  return 45;
               }
               break;
            case 23:
               if (s.equals("after-completion-method")) {
                  return 28;
               }

               if (s.equals("message-destination-ref")) {
                  return 37;
               }

               if (s.equals("persistence-context-ref")) {
                  return 38;
               }
               break;
            case 24:
               if (s.equals("before-completion-method")) {
                  return 27;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new IconBeanImpl.SchemaHelper2();
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 18:
            case 19:
            case 25:
            default:
               return super.getSchemaHelper(propIndex);
            case 11:
               return new EmptyBeanImpl.SchemaHelper2();
            case 15:
               return new StatefulTimeoutBeanImpl.SchemaHelper2();
            case 16:
               return new NamedMethodBeanImpl.SchemaHelper2();
            case 17:
               return new TimerBeanImpl.SchemaHelper2();
            case 20:
               return new ConcurrentMethodBeanImpl.SchemaHelper2();
            case 21:
               return new DependsOnBeanImpl.SchemaHelper2();
            case 22:
               return new InitMethodBeanImpl.SchemaHelper2();
            case 23:
               return new RemoveMethodBeanImpl.SchemaHelper2();
            case 24:
               return new AsyncMethodBeanImpl.SchemaHelper2();
            case 26:
               return new NamedMethodBeanImpl.SchemaHelper2();
            case 27:
               return new NamedMethodBeanImpl.SchemaHelper2();
            case 28:
               return new NamedMethodBeanImpl.SchemaHelper2();
            case 29:
               return new AroundInvokeBeanImpl.SchemaHelper2();
            case 30:
               return new AroundTimeoutBeanImpl.SchemaHelper2();
            case 31:
               return new EnvEntryBeanImpl.SchemaHelper2();
            case 32:
               return new EjbRefBeanImpl.SchemaHelper2();
            case 33:
               return new EjbLocalRefBeanImpl.SchemaHelper2();
            case 34:
               return new ServiceRefBeanImpl.SchemaHelper2();
            case 35:
               return new ResourceRefBeanImpl.SchemaHelper2();
            case 36:
               return new ResourceEnvRefBeanImpl.SchemaHelper2();
            case 37:
               return new MessageDestinationRefBeanImpl.SchemaHelper2();
            case 38:
               return new PersistenceContextRefBeanImpl.SchemaHelper2();
            case 39:
               return new PersistenceUnitRefBeanImpl.SchemaHelper2();
            case 40:
               return new LifecycleCallbackBeanImpl.SchemaHelper2();
            case 41:
               return new LifecycleCallbackBeanImpl.SchemaHelper2();
            case 42:
               return new LifecycleCallbackBeanImpl.SchemaHelper2();
            case 43:
               return new LifecycleCallbackBeanImpl.SchemaHelper2();
            case 44:
               return new DataSourceBeanImpl.SchemaHelper2();
            case 45:
               return new JmsConnectionFactoryBeanImpl.SchemaHelper2();
            case 46:
               return new JmsDestinationBeanImpl.SchemaHelper2();
            case 47:
               return new MailSessionBeanImpl.SchemaHelper2();
            case 48:
               return new ConnectionFactoryResourceBeanImpl.SchemaHelper2();
            case 49:
               return new AdministeredObjectBeanImpl.SchemaHelper2();
            case 50:
               return new SecurityRoleRefBeanImpl.SchemaHelper2();
            case 51:
               return new SecurityIdentityBeanImpl.SchemaHelper2();
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
               return "ejb-name";
            case 4:
               return "mapped-name";
            case 5:
               return "home";
            case 6:
               return "remote";
            case 7:
               return "local-home";
            case 8:
               return "local";
            case 9:
               return "business-local";
            case 10:
               return "business-remote";
            case 11:
               return "local-bean";
            case 12:
               return "service-endpoint";
            case 13:
               return "ejb-class";
            case 14:
               return "session-type";
            case 15:
               return "stateful-timeout";
            case 16:
               return "timeout-method";
            case 17:
               return "timer";
            case 18:
               return "init-on-startup";
            case 19:
               return "concurrency-management";
            case 20:
               return "concurrent-method";
            case 21:
               return "depends-on";
            case 22:
               return "init-method";
            case 23:
               return "remove-method";
            case 24:
               return "async-method";
            case 25:
               return "transaction-type";
            case 26:
               return "after-begin-method";
            case 27:
               return "before-completion-method";
            case 28:
               return "after-completion-method";
            case 29:
               return "around-invoke";
            case 30:
               return "around-timeout";
            case 31:
               return "env-entry";
            case 32:
               return "ejb-ref";
            case 33:
               return "ejb-local-ref";
            case 34:
               return "service-ref";
            case 35:
               return "resource-ref";
            case 36:
               return "resource-env-ref";
            case 37:
               return "message-destination-ref";
            case 38:
               return "persistence-context-ref";
            case 39:
               return "persistence-unit-ref";
            case 40:
               return "post-construct";
            case 41:
               return "pre-destroy";
            case 42:
               return "post-activate";
            case 43:
               return "pre-passivate";
            case 44:
               return "data-source";
            case 45:
               return "jms-connection-factory";
            case 46:
               return "jms-destination";
            case 47:
               return "mail-session";
            case 48:
               return "connection-factory";
            case 49:
               return "administered-object";
            case 50:
               return "security-role-ref";
            case 51:
               return "security-identity";
            case 52:
               return "passivation-capable";
            case 53:
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
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 21:
            case 25:
            case 26:
            case 27:
            case 28:
            default:
               return super.isArray(propIndex);
            case 9:
               return true;
            case 10:
               return true;
            case 17:
               return true;
            case 20:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            case 32:
               return true;
            case 33:
               return true;
            case 34:
               return true;
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
            case 42:
               return true;
            case 43:
               return true;
            case 44:
               return true;
            case 45:
               return true;
            case 46:
               return true;
            case 47:
               return true;
            case 48:
               return true;
            case 49:
               return true;
            case 50:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 18:
            case 19:
            case 25:
            default:
               return super.isBean(propIndex);
            case 11:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
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
               return true;
            case 33:
               return true;
            case 34:
               return true;
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
            case 42:
               return true;
            case 43:
               return true;
            case 44:
               return true;
            case 45:
               return true;
            case 46:
               return true;
            case 47:
               return true;
            case 48:
               return true;
            case 49:
               return true;
            case 50:
               return true;
            case 51:
               return true;
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 3:
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

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private SessionBeanBeanImpl bean;

      protected Helper(SessionBeanBeanImpl bean) {
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
               return "EjbName";
            case 4:
               return "MappedName";
            case 5:
               return "Home";
            case 6:
               return "Remote";
            case 7:
               return "LocalHome";
            case 8:
               return "Local";
            case 9:
               return "BusinessLocals";
            case 10:
               return "BusinessRemotes";
            case 11:
               return "LocalBean";
            case 12:
               return "ServiceEndpoint";
            case 13:
               return "EjbClass";
            case 14:
               return "SessionType";
            case 15:
               return "StatefulTimeout";
            case 16:
               return "TimeoutMethod";
            case 17:
               return "Timers";
            case 18:
               return "InitOnStartup";
            case 19:
               return "ConcurrencyManagement";
            case 20:
               return "ConcurrentMethods";
            case 21:
               return "DependsOn";
            case 22:
               return "InitMethods";
            case 23:
               return "RemoveMethods";
            case 24:
               return "AsyncMethods";
            case 25:
               return "TransactionType";
            case 26:
               return "AfterBeginMethod";
            case 27:
               return "BeforeCompletionMethod";
            case 28:
               return "AfterCompletionMethod";
            case 29:
               return "AroundInvokes";
            case 30:
               return "AroundTimeouts";
            case 31:
               return "EnvEntries";
            case 32:
               return "EjbRefs";
            case 33:
               return "EjbLocalRefs";
            case 34:
               return "ServiceRefs";
            case 35:
               return "ResourceRefs";
            case 36:
               return "ResourceEnvRefs";
            case 37:
               return "MessageDestinationRefs";
            case 38:
               return "PersistenceContextRefs";
            case 39:
               return "PersistenceUnitRefs";
            case 40:
               return "PostConstructs";
            case 41:
               return "PreDestroys";
            case 42:
               return "PostActivates";
            case 43:
               return "PrePassivates";
            case 44:
               return "DataSources";
            case 45:
               return "JmsConnectionFactories";
            case 46:
               return "JmsDestinations";
            case 47:
               return "MailSessions";
            case 48:
               return "ConnectionFactories";
            case 49:
               return "AdministeredObjects";
            case 50:
               return "SecurityRoleRefs";
            case 51:
               return "SecurityIdentity";
            case 52:
               return "PassivationCapable";
            case 53:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdministeredObjects")) {
            return 49;
         } else if (propName.equals("AfterBeginMethod")) {
            return 26;
         } else if (propName.equals("AfterCompletionMethod")) {
            return 28;
         } else if (propName.equals("AroundInvokes")) {
            return 29;
         } else if (propName.equals("AroundTimeouts")) {
            return 30;
         } else if (propName.equals("AsyncMethods")) {
            return 24;
         } else if (propName.equals("BeforeCompletionMethod")) {
            return 27;
         } else if (propName.equals("BusinessLocals")) {
            return 9;
         } else if (propName.equals("BusinessRemotes")) {
            return 10;
         } else if (propName.equals("ConcurrencyManagement")) {
            return 19;
         } else if (propName.equals("ConcurrentMethods")) {
            return 20;
         } else if (propName.equals("ConnectionFactories")) {
            return 48;
         } else if (propName.equals("DataSources")) {
            return 44;
         } else if (propName.equals("DependsOn")) {
            return 21;
         } else if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("DisplayNames")) {
            return 1;
         } else if (propName.equals("EjbClass")) {
            return 13;
         } else if (propName.equals("EjbLocalRefs")) {
            return 33;
         } else if (propName.equals("EjbName")) {
            return 3;
         } else if (propName.equals("EjbRefs")) {
            return 32;
         } else if (propName.equals("EnvEntries")) {
            return 31;
         } else if (propName.equals("Home")) {
            return 5;
         } else if (propName.equals("Icons")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 53;
         } else if (propName.equals("InitMethods")) {
            return 22;
         } else if (propName.equals("JmsConnectionFactories")) {
            return 45;
         } else if (propName.equals("JmsDestinations")) {
            return 46;
         } else if (propName.equals("Local")) {
            return 8;
         } else if (propName.equals("LocalBean")) {
            return 11;
         } else if (propName.equals("LocalHome")) {
            return 7;
         } else if (propName.equals("MailSessions")) {
            return 47;
         } else if (propName.equals("MappedName")) {
            return 4;
         } else if (propName.equals("MessageDestinationRefs")) {
            return 37;
         } else if (propName.equals("PersistenceContextRefs")) {
            return 38;
         } else if (propName.equals("PersistenceUnitRefs")) {
            return 39;
         } else if (propName.equals("PostActivates")) {
            return 42;
         } else if (propName.equals("PostConstructs")) {
            return 40;
         } else if (propName.equals("PreDestroys")) {
            return 41;
         } else if (propName.equals("PrePassivates")) {
            return 43;
         } else if (propName.equals("Remote")) {
            return 6;
         } else if (propName.equals("RemoveMethods")) {
            return 23;
         } else if (propName.equals("ResourceEnvRefs")) {
            return 36;
         } else if (propName.equals("ResourceRefs")) {
            return 35;
         } else if (propName.equals("SecurityIdentity")) {
            return 51;
         } else if (propName.equals("SecurityRoleRefs")) {
            return 50;
         } else if (propName.equals("ServiceEndpoint")) {
            return 12;
         } else if (propName.equals("ServiceRefs")) {
            return 34;
         } else if (propName.equals("SessionType")) {
            return 14;
         } else if (propName.equals("StatefulTimeout")) {
            return 15;
         } else if (propName.equals("TimeoutMethod")) {
            return 16;
         } else if (propName.equals("Timers")) {
            return 17;
         } else if (propName.equals("TransactionType")) {
            return 25;
         } else if (propName.equals("InitOnStartup")) {
            return 18;
         } else {
            return propName.equals("PassivationCapable") ? 52 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAdministeredObjects()));
         if (this.bean.getAfterBeginMethod() != null) {
            iterators.add(new ArrayIterator(new NamedMethodBean[]{this.bean.getAfterBeginMethod()}));
         }

         if (this.bean.getAfterCompletionMethod() != null) {
            iterators.add(new ArrayIterator(new NamedMethodBean[]{this.bean.getAfterCompletionMethod()}));
         }

         iterators.add(new ArrayIterator(this.bean.getAroundInvokes()));
         iterators.add(new ArrayIterator(this.bean.getAroundTimeouts()));
         iterators.add(new ArrayIterator(this.bean.getAsyncMethods()));
         if (this.bean.getBeforeCompletionMethod() != null) {
            iterators.add(new ArrayIterator(new NamedMethodBean[]{this.bean.getBeforeCompletionMethod()}));
         }

         iterators.add(new ArrayIterator(this.bean.getConcurrentMethods()));
         iterators.add(new ArrayIterator(this.bean.getConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getDataSources()));
         if (this.bean.getDependsOn() != null) {
            iterators.add(new ArrayIterator(new DependsOnBean[]{this.bean.getDependsOn()}));
         }

         iterators.add(new ArrayIterator(this.bean.getEjbLocalRefs()));
         iterators.add(new ArrayIterator(this.bean.getEjbRefs()));
         iterators.add(new ArrayIterator(this.bean.getEnvEntries()));
         iterators.add(new ArrayIterator(this.bean.getIcons()));
         iterators.add(new ArrayIterator(this.bean.getInitMethods()));
         iterators.add(new ArrayIterator(this.bean.getJmsConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getJmsDestinations()));
         if (this.bean.getLocalBean() != null) {
            iterators.add(new ArrayIterator(new EmptyBean[]{this.bean.getLocalBean()}));
         }

         iterators.add(new ArrayIterator(this.bean.getMailSessions()));
         iterators.add(new ArrayIterator(this.bean.getMessageDestinationRefs()));
         iterators.add(new ArrayIterator(this.bean.getPersistenceContextRefs()));
         iterators.add(new ArrayIterator(this.bean.getPersistenceUnitRefs()));
         iterators.add(new ArrayIterator(this.bean.getPostActivates()));
         iterators.add(new ArrayIterator(this.bean.getPostConstructs()));
         iterators.add(new ArrayIterator(this.bean.getPreDestroys()));
         iterators.add(new ArrayIterator(this.bean.getPrePassivates()));
         iterators.add(new ArrayIterator(this.bean.getRemoveMethods()));
         iterators.add(new ArrayIterator(this.bean.getResourceEnvRefs()));
         iterators.add(new ArrayIterator(this.bean.getResourceRefs()));
         if (this.bean.getSecurityIdentity() != null) {
            iterators.add(new ArrayIterator(new SecurityIdentityBean[]{this.bean.getSecurityIdentity()}));
         }

         iterators.add(new ArrayIterator(this.bean.getSecurityRoleRefs()));
         iterators.add(new ArrayIterator(this.bean.getServiceRefs()));
         if (this.bean.getStatefulTimeout() != null) {
            iterators.add(new ArrayIterator(new StatefulTimeoutBean[]{this.bean.getStatefulTimeout()}));
         }

         if (this.bean.getTimeoutMethod() != null) {
            iterators.add(new ArrayIterator(new NamedMethodBean[]{this.bean.getTimeoutMethod()}));
         }

         iterators.add(new ArrayIterator(this.bean.getTimers()));
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

            childValue = this.computeChildHashValue(this.bean.getAfterBeginMethod());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getAfterCompletionMethod());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getAroundInvokes().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAroundInvokes()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getAroundTimeouts().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAroundTimeouts()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getAsyncMethods().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAsyncMethods()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getBeforeCompletionMethod());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isBusinessLocalsSet()) {
               buf.append("BusinessLocals");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getBusinessLocals())));
            }

            if (this.bean.isBusinessRemotesSet()) {
               buf.append("BusinessRemotes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getBusinessRemotes())));
            }

            if (this.bean.isConcurrencyManagementSet()) {
               buf.append("ConcurrencyManagement");
               buf.append(String.valueOf(this.bean.getConcurrencyManagement()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getConcurrentMethods().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConcurrentMethods()[i]);
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

            for(i = 0; i < this.bean.getDataSources().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getDataSources()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDependsOn());
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

            if (this.bean.isEjbClassSet()) {
               buf.append("EjbClass");
               buf.append(String.valueOf(this.bean.getEjbClass()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getEjbLocalRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEjbLocalRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isEjbNameSet()) {
               buf.append("EjbName");
               buf.append(String.valueOf(this.bean.getEjbName()));
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

            if (this.bean.isHomeSet()) {
               buf.append("Home");
               buf.append(String.valueOf(this.bean.getHome()));
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

            for(i = 0; i < this.bean.getInitMethods().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getInitMethods()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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

            if (this.bean.isLocalSet()) {
               buf.append("Local");
               buf.append(String.valueOf(this.bean.getLocal()));
            }

            childValue = this.computeChildHashValue(this.bean.getLocalBean());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isLocalHomeSet()) {
               buf.append("LocalHome");
               buf.append(String.valueOf(this.bean.getLocalHome()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMailSessions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMailSessions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMappedNameSet()) {
               buf.append("MappedName");
               buf.append(String.valueOf(this.bean.getMappedName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMessageDestinationRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMessageDestinationRefs()[i]);
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

            for(i = 0; i < this.bean.getPostActivates().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPostActivates()[i]);
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

            for(i = 0; i < this.bean.getPrePassivates().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPrePassivates()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isRemoteSet()) {
               buf.append("Remote");
               buf.append(String.valueOf(this.bean.getRemote()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getRemoveMethods().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getRemoveMethods()[i]);
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

            childValue = this.computeChildHashValue(this.bean.getSecurityIdentity());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSecurityRoleRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSecurityRoleRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isServiceEndpointSet()) {
               buf.append("ServiceEndpoint");
               buf.append(String.valueOf(this.bean.getServiceEndpoint()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServiceRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServiceRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSessionTypeSet()) {
               buf.append("SessionType");
               buf.append(String.valueOf(this.bean.getSessionType()));
            }

            childValue = this.computeChildHashValue(this.bean.getStatefulTimeout());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTimeoutMethod());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getTimers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getTimers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isTransactionTypeSet()) {
               buf.append("TransactionType");
               buf.append(String.valueOf(this.bean.getTransactionType()));
            }

            if (this.bean.isInitOnStartupSet()) {
               buf.append("InitOnStartup");
               buf.append(String.valueOf(this.bean.isInitOnStartup()));
            }

            if (this.bean.isPassivationCapableSet()) {
               buf.append("PassivationCapable");
               buf.append(String.valueOf(this.bean.isPassivationCapable()));
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
            SessionBeanBeanImpl otherTyped = (SessionBeanBeanImpl)other;
            this.computeChildDiff("AdministeredObjects", this.bean.getAdministeredObjects(), otherTyped.getAdministeredObjects(), false);
            this.computeChildDiff("AfterBeginMethod", this.bean.getAfterBeginMethod(), otherTyped.getAfterBeginMethod(), false);
            this.computeChildDiff("AfterCompletionMethod", this.bean.getAfterCompletionMethod(), otherTyped.getAfterCompletionMethod(), false);
            this.computeChildDiff("AroundInvokes", this.bean.getAroundInvokes(), otherTyped.getAroundInvokes(), false);
            this.computeChildDiff("AroundTimeouts", this.bean.getAroundTimeouts(), otherTyped.getAroundTimeouts(), false);
            this.computeChildDiff("AsyncMethods", this.bean.getAsyncMethods(), otherTyped.getAsyncMethods(), false);
            this.computeChildDiff("BeforeCompletionMethod", this.bean.getBeforeCompletionMethod(), otherTyped.getBeforeCompletionMethod(), false);
            this.computeDiff("BusinessLocals", this.bean.getBusinessLocals(), otherTyped.getBusinessLocals(), false);
            this.computeDiff("BusinessRemotes", this.bean.getBusinessRemotes(), otherTyped.getBusinessRemotes(), false);
            this.computeDiff("ConcurrencyManagement", this.bean.getConcurrencyManagement(), otherTyped.getConcurrencyManagement(), false);
            this.computeChildDiff("ConcurrentMethods", this.bean.getConcurrentMethods(), otherTyped.getConcurrentMethods(), false);
            this.computeChildDiff("ConnectionFactories", this.bean.getConnectionFactories(), otherTyped.getConnectionFactories(), false);
            this.computeChildDiff("DataSources", this.bean.getDataSources(), otherTyped.getDataSources(), false);
            this.computeChildDiff("DependsOn", this.bean.getDependsOn(), otherTyped.getDependsOn(), false);
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("DisplayNames", this.bean.getDisplayNames(), otherTyped.getDisplayNames(), false);
            this.computeDiff("EjbClass", this.bean.getEjbClass(), otherTyped.getEjbClass(), false);
            this.computeChildDiff("EjbLocalRefs", this.bean.getEjbLocalRefs(), otherTyped.getEjbLocalRefs(), false);
            this.computeDiff("EjbName", this.bean.getEjbName(), otherTyped.getEjbName(), false);
            this.computeChildDiff("EjbRefs", this.bean.getEjbRefs(), otherTyped.getEjbRefs(), false);
            this.computeChildDiff("EnvEntries", this.bean.getEnvEntries(), otherTyped.getEnvEntries(), false);
            this.computeDiff("Home", this.bean.getHome(), otherTyped.getHome(), false);
            this.computeChildDiff("Icons", this.bean.getIcons(), otherTyped.getIcons(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("InitMethods", this.bean.getInitMethods(), otherTyped.getInitMethods(), false);
            this.computeChildDiff("JmsConnectionFactories", this.bean.getJmsConnectionFactories(), otherTyped.getJmsConnectionFactories(), false);
            this.computeChildDiff("JmsDestinations", this.bean.getJmsDestinations(), otherTyped.getJmsDestinations(), false);
            this.computeDiff("Local", this.bean.getLocal(), otherTyped.getLocal(), false);
            this.computeChildDiff("LocalBean", this.bean.getLocalBean(), otherTyped.getLocalBean(), false);
            this.computeDiff("LocalHome", this.bean.getLocalHome(), otherTyped.getLocalHome(), false);
            this.computeChildDiff("MailSessions", this.bean.getMailSessions(), otherTyped.getMailSessions(), false);
            this.computeDiff("MappedName", this.bean.getMappedName(), otherTyped.getMappedName(), false);
            this.computeChildDiff("MessageDestinationRefs", this.bean.getMessageDestinationRefs(), otherTyped.getMessageDestinationRefs(), false);
            this.computeChildDiff("PersistenceContextRefs", this.bean.getPersistenceContextRefs(), otherTyped.getPersistenceContextRefs(), false);
            this.computeChildDiff("PersistenceUnitRefs", this.bean.getPersistenceUnitRefs(), otherTyped.getPersistenceUnitRefs(), false);
            this.computeChildDiff("PostActivates", this.bean.getPostActivates(), otherTyped.getPostActivates(), false);
            this.computeChildDiff("PostConstructs", this.bean.getPostConstructs(), otherTyped.getPostConstructs(), false);
            this.computeChildDiff("PreDestroys", this.bean.getPreDestroys(), otherTyped.getPreDestroys(), false);
            this.computeChildDiff("PrePassivates", this.bean.getPrePassivates(), otherTyped.getPrePassivates(), false);
            this.computeDiff("Remote", this.bean.getRemote(), otherTyped.getRemote(), false);
            this.computeChildDiff("RemoveMethods", this.bean.getRemoveMethods(), otherTyped.getRemoveMethods(), false);
            this.computeChildDiff("ResourceEnvRefs", this.bean.getResourceEnvRefs(), otherTyped.getResourceEnvRefs(), false);
            this.computeChildDiff("ResourceRefs", this.bean.getResourceRefs(), otherTyped.getResourceRefs(), false);
            this.computeChildDiff("SecurityIdentity", this.bean.getSecurityIdentity(), otherTyped.getSecurityIdentity(), false);
            this.computeChildDiff("SecurityRoleRefs", this.bean.getSecurityRoleRefs(), otherTyped.getSecurityRoleRefs(), false);
            this.computeDiff("ServiceEndpoint", this.bean.getServiceEndpoint(), otherTyped.getServiceEndpoint(), false);
            this.computeChildDiff("ServiceRefs", this.bean.getServiceRefs(), otherTyped.getServiceRefs(), false);
            this.computeDiff("SessionType", this.bean.getSessionType(), otherTyped.getSessionType(), false);
            this.computeChildDiff("StatefulTimeout", this.bean.getStatefulTimeout(), otherTyped.getStatefulTimeout(), false);
            this.computeChildDiff("TimeoutMethod", this.bean.getTimeoutMethod(), otherTyped.getTimeoutMethod(), false);
            this.computeChildDiff("Timers", this.bean.getTimers(), otherTyped.getTimers(), false);
            this.computeDiff("TransactionType", this.bean.getTransactionType(), otherTyped.getTransactionType(), false);
            this.computeDiff("InitOnStartup", this.bean.isInitOnStartup(), otherTyped.isInitOnStartup(), false);
            this.computeDiff("PassivationCapable", this.bean.isPassivationCapable(), otherTyped.isPassivationCapable(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SessionBeanBeanImpl original = (SessionBeanBeanImpl)event.getSourceBean();
            SessionBeanBeanImpl proposed = (SessionBeanBeanImpl)event.getProposedBean();
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
                     original._conditionalUnset(update.isUnsetUpdate(), 49);
                  }
               } else if (prop.equals("AfterBeginMethod")) {
                  if (type == 2) {
                     original.setAfterBeginMethod((NamedMethodBean)this.createCopy((AbstractDescriptorBean)proposed.getAfterBeginMethod()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AfterBeginMethod", (DescriptorBean)original.getAfterBeginMethod());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("AfterCompletionMethod")) {
                  if (type == 2) {
                     original.setAfterCompletionMethod((NamedMethodBean)this.createCopy((AbstractDescriptorBean)proposed.getAfterCompletionMethod()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AfterCompletionMethod", (DescriptorBean)original.getAfterCompletionMethod());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("AroundInvokes")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAroundInvoke((AroundInvokeBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAroundInvoke((AroundInvokeBean)update.getRemovedObject());
                  }

                  if (original.getAroundInvokes() == null || original.getAroundInvokes().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 29);
                  }
               } else if (prop.equals("AroundTimeouts")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAroundTimeout((AroundTimeoutBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAroundTimeout((AroundTimeoutBean)update.getRemovedObject());
                  }

                  if (original.getAroundTimeouts() == null || original.getAroundTimeouts().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 30);
                  }
               } else if (prop.equals("AsyncMethods")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAsyncMethod((AsyncMethodBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAsyncMethod((AsyncMethodBean)update.getRemovedObject());
                  }

                  if (original.getAsyncMethods() == null || original.getAsyncMethods().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
                  }
               } else if (prop.equals("BeforeCompletionMethod")) {
                  if (type == 2) {
                     original.setBeforeCompletionMethod((NamedMethodBean)this.createCopy((AbstractDescriptorBean)proposed.getBeforeCompletionMethod()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("BeforeCompletionMethod", (DescriptorBean)original.getBeforeCompletionMethod());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("BusinessLocals")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addBusinessLocal((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeBusinessLocal((String)update.getRemovedObject());
                  }

                  if (original.getBusinessLocals() == null || original.getBusinessLocals().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("BusinessRemotes")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addBusinessRemote((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeBusinessRemote((String)update.getRemovedObject());
                  }

                  if (original.getBusinessRemotes() == null || original.getBusinessRemotes().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
               } else if (prop.equals("ConcurrencyManagement")) {
                  original.setConcurrencyManagement(proposed.getConcurrencyManagement());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("ConcurrentMethods")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConcurrentMethod((ConcurrentMethodBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConcurrentMethod((ConcurrentMethodBean)update.getRemovedObject());
                  }

                  if (original.getConcurrentMethods() == null || original.getConcurrentMethods().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 48);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 44);
                  }
               } else if (prop.equals("DependsOn")) {
                  if (type == 2) {
                     original.setDependsOn((DependsOnBean)this.createCopy((AbstractDescriptorBean)proposed.getDependsOn()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DependsOn", (DescriptorBean)original.getDependsOn());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 21);
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
               } else if (prop.equals("EjbClass")) {
                  original.setEjbClass(proposed.getEjbClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 33);
                  }
               } else if (prop.equals("EjbName")) {
                  original.setEjbName(proposed.getEjbName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 32);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 31);
                  }
               } else if (prop.equals("Home")) {
                  original.setHome(proposed.getHome());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
                  original._conditionalUnset(update.isUnsetUpdate(), 53);
               } else if (prop.equals("InitMethods")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addInitMethod((InitMethodBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeInitMethod((InitMethodBean)update.getRemovedObject());
                  }

                  if (original.getInitMethods() == null || original.getInitMethods().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  }
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
                     original._conditionalUnset(update.isUnsetUpdate(), 45);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 46);
                  }
               } else if (prop.equals("Local")) {
                  original.setLocal(proposed.getLocal());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("LocalBean")) {
                  if (type == 2) {
                     original.setLocalBean((EmptyBean)this.createCopy((AbstractDescriptorBean)proposed.getLocalBean()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("LocalBean", (DescriptorBean)original.getLocalBean());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("LocalHome")) {
                  original.setLocalHome(proposed.getLocalHome());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 47);
                  }
               } else if (prop.equals("MappedName")) {
                  original.setMappedName(proposed.getMappedName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 37);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 38);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 39);
                  }
               } else if (prop.equals("PostActivates")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPostActivate((LifecycleCallbackBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePostActivate((LifecycleCallbackBean)update.getRemovedObject());
                  }

                  if (original.getPostActivates() == null || original.getPostActivates().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 42);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 40);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 41);
                  }
               } else if (prop.equals("PrePassivates")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPrePassivate((LifecycleCallbackBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePrePassivate((LifecycleCallbackBean)update.getRemovedObject());
                  }

                  if (original.getPrePassivates() == null || original.getPrePassivates().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 43);
                  }
               } else if (prop.equals("Remote")) {
                  original.setRemote(proposed.getRemote());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("RemoveMethods")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addRemoveMethod((RemoveMethodBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeRemoveMethod((RemoveMethodBean)update.getRemovedObject());
                  }

                  if (original.getRemoveMethods() == null || original.getRemoveMethods().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 36);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 35);
                  }
               } else if (prop.equals("SecurityIdentity")) {
                  if (type == 2) {
                     original.setSecurityIdentity((SecurityIdentityBean)this.createCopy((AbstractDescriptorBean)proposed.getSecurityIdentity()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SecurityIdentity", (DescriptorBean)original.getSecurityIdentity());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 51);
               } else if (prop.equals("SecurityRoleRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSecurityRoleRef((SecurityRoleRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSecurityRoleRef((SecurityRoleRefBean)update.getRemovedObject());
                  }

                  if (original.getSecurityRoleRefs() == null || original.getSecurityRoleRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 50);
                  }
               } else if (prop.equals("ServiceEndpoint")) {
                  original.setServiceEndpoint(proposed.getServiceEndpoint());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 34);
                  }
               } else if (prop.equals("SessionType")) {
                  original.setSessionType(proposed.getSessionType());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("StatefulTimeout")) {
                  if (type == 2) {
                     original.setStatefulTimeout((StatefulTimeoutBean)this.createCopy((AbstractDescriptorBean)proposed.getStatefulTimeout()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("StatefulTimeout", (DescriptorBean)original.getStatefulTimeout());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("TimeoutMethod")) {
                  if (type == 2) {
                     original.setTimeoutMethod((NamedMethodBean)this.createCopy((AbstractDescriptorBean)proposed.getTimeoutMethod()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("TimeoutMethod", (DescriptorBean)original.getTimeoutMethod());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("Timers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addTimer((TimerBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTimer((TimerBean)update.getRemovedObject());
                  }

                  if (original.getTimers() == null || original.getTimers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  }
               } else if (prop.equals("TransactionType")) {
                  original.setTransactionType(proposed.getTransactionType());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("InitOnStartup")) {
                  original.setInitOnStartup(proposed.isInitOnStartup());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("PassivationCapable")) {
                  original.setPassivationCapable(proposed.isPassivationCapable());
                  original._conditionalUnset(update.isUnsetUpdate(), 52);
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
            SessionBeanBeanImpl copy = (SessionBeanBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("AdministeredObjects")) && this.bean.isAdministeredObjectsSet() && !copy._isSet(49)) {
               AdministeredObjectBean[] oldAdministeredObjects = this.bean.getAdministeredObjects();
               AdministeredObjectBean[] newAdministeredObjects = new AdministeredObjectBean[oldAdministeredObjects.length];

               for(i = 0; i < newAdministeredObjects.length; ++i) {
                  newAdministeredObjects[i] = (AdministeredObjectBean)((AdministeredObjectBean)this.createCopy((AbstractDescriptorBean)oldAdministeredObjects[i], includeObsolete));
               }

               copy.setAdministeredObjects(newAdministeredObjects);
            }

            NamedMethodBean o;
            if ((excludeProps == null || !excludeProps.contains("AfterBeginMethod")) && this.bean.isAfterBeginMethodSet() && !copy._isSet(26)) {
               o = this.bean.getAfterBeginMethod();
               copy.setAfterBeginMethod((NamedMethodBean)null);
               copy.setAfterBeginMethod(o == null ? null : (NamedMethodBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("AfterCompletionMethod")) && this.bean.isAfterCompletionMethodSet() && !copy._isSet(28)) {
               o = this.bean.getAfterCompletionMethod();
               copy.setAfterCompletionMethod((NamedMethodBean)null);
               copy.setAfterCompletionMethod(o == null ? null : (NamedMethodBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("AroundInvokes")) && this.bean.isAroundInvokesSet() && !copy._isSet(29)) {
               AroundInvokeBean[] oldAroundInvokes = this.bean.getAroundInvokes();
               AroundInvokeBean[] newAroundInvokes = new AroundInvokeBean[oldAroundInvokes.length];

               for(i = 0; i < newAroundInvokes.length; ++i) {
                  newAroundInvokes[i] = (AroundInvokeBean)((AroundInvokeBean)this.createCopy((AbstractDescriptorBean)oldAroundInvokes[i], includeObsolete));
               }

               copy.setAroundInvokes(newAroundInvokes);
            }

            if ((excludeProps == null || !excludeProps.contains("AroundTimeouts")) && this.bean.isAroundTimeoutsSet() && !copy._isSet(30)) {
               AroundTimeoutBean[] oldAroundTimeouts = this.bean.getAroundTimeouts();
               AroundTimeoutBean[] newAroundTimeouts = new AroundTimeoutBean[oldAroundTimeouts.length];

               for(i = 0; i < newAroundTimeouts.length; ++i) {
                  newAroundTimeouts[i] = (AroundTimeoutBean)((AroundTimeoutBean)this.createCopy((AbstractDescriptorBean)oldAroundTimeouts[i], includeObsolete));
               }

               copy.setAroundTimeouts(newAroundTimeouts);
            }

            if ((excludeProps == null || !excludeProps.contains("AsyncMethods")) && this.bean.isAsyncMethodsSet() && !copy._isSet(24)) {
               AsyncMethodBean[] oldAsyncMethods = this.bean.getAsyncMethods();
               AsyncMethodBean[] newAsyncMethods = new AsyncMethodBean[oldAsyncMethods.length];

               for(i = 0; i < newAsyncMethods.length; ++i) {
                  newAsyncMethods[i] = (AsyncMethodBean)((AsyncMethodBean)this.createCopy((AbstractDescriptorBean)oldAsyncMethods[i], includeObsolete));
               }

               copy.setAsyncMethods(newAsyncMethods);
            }

            if ((excludeProps == null || !excludeProps.contains("BeforeCompletionMethod")) && this.bean.isBeforeCompletionMethodSet() && !copy._isSet(27)) {
               o = this.bean.getBeforeCompletionMethod();
               copy.setBeforeCompletionMethod((NamedMethodBean)null);
               copy.setBeforeCompletionMethod(o == null ? null : (NamedMethodBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("BusinessLocals")) && this.bean.isBusinessLocalsSet()) {
               o = this.bean.getBusinessLocals();
               copy.setBusinessLocals(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("BusinessRemotes")) && this.bean.isBusinessRemotesSet()) {
               o = this.bean.getBusinessRemotes();
               copy.setBusinessRemotes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ConcurrencyManagement")) && this.bean.isConcurrencyManagementSet()) {
               copy.setConcurrencyManagement(this.bean.getConcurrencyManagement());
            }

            if ((excludeProps == null || !excludeProps.contains("ConcurrentMethods")) && this.bean.isConcurrentMethodsSet() && !copy._isSet(20)) {
               ConcurrentMethodBean[] oldConcurrentMethods = this.bean.getConcurrentMethods();
               ConcurrentMethodBean[] newConcurrentMethods = new ConcurrentMethodBean[oldConcurrentMethods.length];

               for(i = 0; i < newConcurrentMethods.length; ++i) {
                  newConcurrentMethods[i] = (ConcurrentMethodBean)((ConcurrentMethodBean)this.createCopy((AbstractDescriptorBean)oldConcurrentMethods[i], includeObsolete));
               }

               copy.setConcurrentMethods(newConcurrentMethods);
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactories")) && this.bean.isConnectionFactoriesSet() && !copy._isSet(48)) {
               ConnectionFactoryResourceBean[] oldConnectionFactories = this.bean.getConnectionFactories();
               ConnectionFactoryResourceBean[] newConnectionFactories = new ConnectionFactoryResourceBean[oldConnectionFactories.length];

               for(i = 0; i < newConnectionFactories.length; ++i) {
                  newConnectionFactories[i] = (ConnectionFactoryResourceBean)((ConnectionFactoryResourceBean)this.createCopy((AbstractDescriptorBean)oldConnectionFactories[i], includeObsolete));
               }

               copy.setConnectionFactories(newConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("DataSources")) && this.bean.isDataSourcesSet() && !copy._isSet(44)) {
               DataSourceBean[] oldDataSources = this.bean.getDataSources();
               DataSourceBean[] newDataSources = new DataSourceBean[oldDataSources.length];

               for(i = 0; i < newDataSources.length; ++i) {
                  newDataSources[i] = (DataSourceBean)((DataSourceBean)this.createCopy((AbstractDescriptorBean)oldDataSources[i], includeObsolete));
               }

               copy.setDataSources(newDataSources);
            }

            if ((excludeProps == null || !excludeProps.contains("DependsOn")) && this.bean.isDependsOnSet() && !copy._isSet(21)) {
               Object o = this.bean.getDependsOn();
               copy.setDependsOn((DependsOnBean)null);
               copy.setDependsOn(o == null ? null : (DependsOnBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("DisplayNames")) && this.bean.isDisplayNamesSet()) {
               o = this.bean.getDisplayNames();
               copy.setDisplayNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("EjbClass")) && this.bean.isEjbClassSet()) {
               copy.setEjbClass(this.bean.getEjbClass());
            }

            if ((excludeProps == null || !excludeProps.contains("EjbLocalRefs")) && this.bean.isEjbLocalRefsSet() && !copy._isSet(33)) {
               EjbLocalRefBean[] oldEjbLocalRefs = this.bean.getEjbLocalRefs();
               EjbLocalRefBean[] newEjbLocalRefs = new EjbLocalRefBean[oldEjbLocalRefs.length];

               for(i = 0; i < newEjbLocalRefs.length; ++i) {
                  newEjbLocalRefs[i] = (EjbLocalRefBean)((EjbLocalRefBean)this.createCopy((AbstractDescriptorBean)oldEjbLocalRefs[i], includeObsolete));
               }

               copy.setEjbLocalRefs(newEjbLocalRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("EjbName")) && this.bean.isEjbNameSet()) {
               copy.setEjbName(this.bean.getEjbName());
            }

            if ((excludeProps == null || !excludeProps.contains("EjbRefs")) && this.bean.isEjbRefsSet() && !copy._isSet(32)) {
               EjbRefBean[] oldEjbRefs = this.bean.getEjbRefs();
               EjbRefBean[] newEjbRefs = new EjbRefBean[oldEjbRefs.length];

               for(i = 0; i < newEjbRefs.length; ++i) {
                  newEjbRefs[i] = (EjbRefBean)((EjbRefBean)this.createCopy((AbstractDescriptorBean)oldEjbRefs[i], includeObsolete));
               }

               copy.setEjbRefs(newEjbRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("EnvEntries")) && this.bean.isEnvEntriesSet() && !copy._isSet(31)) {
               EnvEntryBean[] oldEnvEntries = this.bean.getEnvEntries();
               EnvEntryBean[] newEnvEntries = new EnvEntryBean[oldEnvEntries.length];

               for(i = 0; i < newEnvEntries.length; ++i) {
                  newEnvEntries[i] = (EnvEntryBean)((EnvEntryBean)this.createCopy((AbstractDescriptorBean)oldEnvEntries[i], includeObsolete));
               }

               copy.setEnvEntries(newEnvEntries);
            }

            if ((excludeProps == null || !excludeProps.contains("Home")) && this.bean.isHomeSet()) {
               copy.setHome(this.bean.getHome());
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

            if ((excludeProps == null || !excludeProps.contains("InitMethods")) && this.bean.isInitMethodsSet() && !copy._isSet(22)) {
               InitMethodBean[] oldInitMethods = this.bean.getInitMethods();
               InitMethodBean[] newInitMethods = new InitMethodBean[oldInitMethods.length];

               for(i = 0; i < newInitMethods.length; ++i) {
                  newInitMethods[i] = (InitMethodBean)((InitMethodBean)this.createCopy((AbstractDescriptorBean)oldInitMethods[i], includeObsolete));
               }

               copy.setInitMethods(newInitMethods);
            }

            if ((excludeProps == null || !excludeProps.contains("JmsConnectionFactories")) && this.bean.isJmsConnectionFactoriesSet() && !copy._isSet(45)) {
               JmsConnectionFactoryBean[] oldJmsConnectionFactories = this.bean.getJmsConnectionFactories();
               JmsConnectionFactoryBean[] newJmsConnectionFactories = new JmsConnectionFactoryBean[oldJmsConnectionFactories.length];

               for(i = 0; i < newJmsConnectionFactories.length; ++i) {
                  newJmsConnectionFactories[i] = (JmsConnectionFactoryBean)((JmsConnectionFactoryBean)this.createCopy((AbstractDescriptorBean)oldJmsConnectionFactories[i], includeObsolete));
               }

               copy.setJmsConnectionFactories(newJmsConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("JmsDestinations")) && this.bean.isJmsDestinationsSet() && !copy._isSet(46)) {
               JmsDestinationBean[] oldJmsDestinations = this.bean.getJmsDestinations();
               JmsDestinationBean[] newJmsDestinations = new JmsDestinationBean[oldJmsDestinations.length];

               for(i = 0; i < newJmsDestinations.length; ++i) {
                  newJmsDestinations[i] = (JmsDestinationBean)((JmsDestinationBean)this.createCopy((AbstractDescriptorBean)oldJmsDestinations[i], includeObsolete));
               }

               copy.setJmsDestinations(newJmsDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("Local")) && this.bean.isLocalSet()) {
               copy.setLocal(this.bean.getLocal());
            }

            if ((excludeProps == null || !excludeProps.contains("LocalBean")) && this.bean.isLocalBeanSet() && !copy._isSet(11)) {
               Object o = this.bean.getLocalBean();
               copy.setLocalBean((EmptyBean)null);
               copy.setLocalBean(o == null ? null : (EmptyBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LocalHome")) && this.bean.isLocalHomeSet()) {
               copy.setLocalHome(this.bean.getLocalHome());
            }

            if ((excludeProps == null || !excludeProps.contains("MailSessions")) && this.bean.isMailSessionsSet() && !copy._isSet(47)) {
               MailSessionBean[] oldMailSessions = this.bean.getMailSessions();
               MailSessionBean[] newMailSessions = new MailSessionBean[oldMailSessions.length];

               for(i = 0; i < newMailSessions.length; ++i) {
                  newMailSessions[i] = (MailSessionBean)((MailSessionBean)this.createCopy((AbstractDescriptorBean)oldMailSessions[i], includeObsolete));
               }

               copy.setMailSessions(newMailSessions);
            }

            if ((excludeProps == null || !excludeProps.contains("MappedName")) && this.bean.isMappedNameSet()) {
               copy.setMappedName(this.bean.getMappedName());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDestinationRefs")) && this.bean.isMessageDestinationRefsSet() && !copy._isSet(37)) {
               MessageDestinationRefBean[] oldMessageDestinationRefs = this.bean.getMessageDestinationRefs();
               MessageDestinationRefBean[] newMessageDestinationRefs = new MessageDestinationRefBean[oldMessageDestinationRefs.length];

               for(i = 0; i < newMessageDestinationRefs.length; ++i) {
                  newMessageDestinationRefs[i] = (MessageDestinationRefBean)((MessageDestinationRefBean)this.createCopy((AbstractDescriptorBean)oldMessageDestinationRefs[i], includeObsolete));
               }

               copy.setMessageDestinationRefs(newMessageDestinationRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceContextRefs")) && this.bean.isPersistenceContextRefsSet() && !copy._isSet(38)) {
               PersistenceContextRefBean[] oldPersistenceContextRefs = this.bean.getPersistenceContextRefs();
               PersistenceContextRefBean[] newPersistenceContextRefs = new PersistenceContextRefBean[oldPersistenceContextRefs.length];

               for(i = 0; i < newPersistenceContextRefs.length; ++i) {
                  newPersistenceContextRefs[i] = (PersistenceContextRefBean)((PersistenceContextRefBean)this.createCopy((AbstractDescriptorBean)oldPersistenceContextRefs[i], includeObsolete));
               }

               copy.setPersistenceContextRefs(newPersistenceContextRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceUnitRefs")) && this.bean.isPersistenceUnitRefsSet() && !copy._isSet(39)) {
               PersistenceUnitRefBean[] oldPersistenceUnitRefs = this.bean.getPersistenceUnitRefs();
               PersistenceUnitRefBean[] newPersistenceUnitRefs = new PersistenceUnitRefBean[oldPersistenceUnitRefs.length];

               for(i = 0; i < newPersistenceUnitRefs.length; ++i) {
                  newPersistenceUnitRefs[i] = (PersistenceUnitRefBean)((PersistenceUnitRefBean)this.createCopy((AbstractDescriptorBean)oldPersistenceUnitRefs[i], includeObsolete));
               }

               copy.setPersistenceUnitRefs(newPersistenceUnitRefs);
            }

            LifecycleCallbackBean[] oldPrePassivates;
            LifecycleCallbackBean[] newPrePassivates;
            if ((excludeProps == null || !excludeProps.contains("PostActivates")) && this.bean.isPostActivatesSet() && !copy._isSet(42)) {
               oldPrePassivates = this.bean.getPostActivates();
               newPrePassivates = new LifecycleCallbackBean[oldPrePassivates.length];

               for(i = 0; i < newPrePassivates.length; ++i) {
                  newPrePassivates[i] = (LifecycleCallbackBean)((LifecycleCallbackBean)this.createCopy((AbstractDescriptorBean)oldPrePassivates[i], includeObsolete));
               }

               copy.setPostActivates(newPrePassivates);
            }

            if ((excludeProps == null || !excludeProps.contains("PostConstructs")) && this.bean.isPostConstructsSet() && !copy._isSet(40)) {
               oldPrePassivates = this.bean.getPostConstructs();
               newPrePassivates = new LifecycleCallbackBean[oldPrePassivates.length];

               for(i = 0; i < newPrePassivates.length; ++i) {
                  newPrePassivates[i] = (LifecycleCallbackBean)((LifecycleCallbackBean)this.createCopy((AbstractDescriptorBean)oldPrePassivates[i], includeObsolete));
               }

               copy.setPostConstructs(newPrePassivates);
            }

            if ((excludeProps == null || !excludeProps.contains("PreDestroys")) && this.bean.isPreDestroysSet() && !copy._isSet(41)) {
               oldPrePassivates = this.bean.getPreDestroys();
               newPrePassivates = new LifecycleCallbackBean[oldPrePassivates.length];

               for(i = 0; i < newPrePassivates.length; ++i) {
                  newPrePassivates[i] = (LifecycleCallbackBean)((LifecycleCallbackBean)this.createCopy((AbstractDescriptorBean)oldPrePassivates[i], includeObsolete));
               }

               copy.setPreDestroys(newPrePassivates);
            }

            if ((excludeProps == null || !excludeProps.contains("PrePassivates")) && this.bean.isPrePassivatesSet() && !copy._isSet(43)) {
               oldPrePassivates = this.bean.getPrePassivates();
               newPrePassivates = new LifecycleCallbackBean[oldPrePassivates.length];

               for(i = 0; i < newPrePassivates.length; ++i) {
                  newPrePassivates[i] = (LifecycleCallbackBean)((LifecycleCallbackBean)this.createCopy((AbstractDescriptorBean)oldPrePassivates[i], includeObsolete));
               }

               copy.setPrePassivates(newPrePassivates);
            }

            if ((excludeProps == null || !excludeProps.contains("Remote")) && this.bean.isRemoteSet()) {
               copy.setRemote(this.bean.getRemote());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoveMethods")) && this.bean.isRemoveMethodsSet() && !copy._isSet(23)) {
               RemoveMethodBean[] oldRemoveMethods = this.bean.getRemoveMethods();
               RemoveMethodBean[] newRemoveMethods = new RemoveMethodBean[oldRemoveMethods.length];

               for(i = 0; i < newRemoveMethods.length; ++i) {
                  newRemoveMethods[i] = (RemoveMethodBean)((RemoveMethodBean)this.createCopy((AbstractDescriptorBean)oldRemoveMethods[i], includeObsolete));
               }

               copy.setRemoveMethods(newRemoveMethods);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceEnvRefs")) && this.bean.isResourceEnvRefsSet() && !copy._isSet(36)) {
               ResourceEnvRefBean[] oldResourceEnvRefs = this.bean.getResourceEnvRefs();
               ResourceEnvRefBean[] newResourceEnvRefs = new ResourceEnvRefBean[oldResourceEnvRefs.length];

               for(i = 0; i < newResourceEnvRefs.length; ++i) {
                  newResourceEnvRefs[i] = (ResourceEnvRefBean)((ResourceEnvRefBean)this.createCopy((AbstractDescriptorBean)oldResourceEnvRefs[i], includeObsolete));
               }

               copy.setResourceEnvRefs(newResourceEnvRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceRefs")) && this.bean.isResourceRefsSet() && !copy._isSet(35)) {
               ResourceRefBean[] oldResourceRefs = this.bean.getResourceRefs();
               ResourceRefBean[] newResourceRefs = new ResourceRefBean[oldResourceRefs.length];

               for(i = 0; i < newResourceRefs.length; ++i) {
                  newResourceRefs[i] = (ResourceRefBean)((ResourceRefBean)this.createCopy((AbstractDescriptorBean)oldResourceRefs[i], includeObsolete));
               }

               copy.setResourceRefs(newResourceRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityIdentity")) && this.bean.isSecurityIdentitySet() && !copy._isSet(51)) {
               Object o = this.bean.getSecurityIdentity();
               copy.setSecurityIdentity((SecurityIdentityBean)null);
               copy.setSecurityIdentity(o == null ? null : (SecurityIdentityBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityRoleRefs")) && this.bean.isSecurityRoleRefsSet() && !copy._isSet(50)) {
               SecurityRoleRefBean[] oldSecurityRoleRefs = this.bean.getSecurityRoleRefs();
               SecurityRoleRefBean[] newSecurityRoleRefs = new SecurityRoleRefBean[oldSecurityRoleRefs.length];

               for(i = 0; i < newSecurityRoleRefs.length; ++i) {
                  newSecurityRoleRefs[i] = (SecurityRoleRefBean)((SecurityRoleRefBean)this.createCopy((AbstractDescriptorBean)oldSecurityRoleRefs[i], includeObsolete));
               }

               copy.setSecurityRoleRefs(newSecurityRoleRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceEndpoint")) && this.bean.isServiceEndpointSet()) {
               copy.setServiceEndpoint(this.bean.getServiceEndpoint());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceRefs")) && this.bean.isServiceRefsSet() && !copy._isSet(34)) {
               ServiceRefBean[] oldServiceRefs = this.bean.getServiceRefs();
               ServiceRefBean[] newServiceRefs = new ServiceRefBean[oldServiceRefs.length];

               for(i = 0; i < newServiceRefs.length; ++i) {
                  newServiceRefs[i] = (ServiceRefBean)((ServiceRefBean)this.createCopy((AbstractDescriptorBean)oldServiceRefs[i], includeObsolete));
               }

               copy.setServiceRefs(newServiceRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("SessionType")) && this.bean.isSessionTypeSet()) {
               copy.setSessionType(this.bean.getSessionType());
            }

            if ((excludeProps == null || !excludeProps.contains("StatefulTimeout")) && this.bean.isStatefulTimeoutSet() && !copy._isSet(15)) {
               Object o = this.bean.getStatefulTimeout();
               copy.setStatefulTimeout((StatefulTimeoutBean)null);
               copy.setStatefulTimeout(o == null ? null : (StatefulTimeoutBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TimeoutMethod")) && this.bean.isTimeoutMethodSet() && !copy._isSet(16)) {
               o = this.bean.getTimeoutMethod();
               copy.setTimeoutMethod((NamedMethodBean)null);
               copy.setTimeoutMethod(o == null ? null : (NamedMethodBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Timers")) && this.bean.isTimersSet() && !copy._isSet(17)) {
               TimerBean[] oldTimers = this.bean.getTimers();
               TimerBean[] newTimers = new TimerBean[oldTimers.length];

               for(i = 0; i < newTimers.length; ++i) {
                  newTimers[i] = (TimerBean)((TimerBean)this.createCopy((AbstractDescriptorBean)oldTimers[i], includeObsolete));
               }

               copy.setTimers(newTimers);
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionType")) && this.bean.isTransactionTypeSet()) {
               copy.setTransactionType(this.bean.getTransactionType());
            }

            if ((excludeProps == null || !excludeProps.contains("InitOnStartup")) && this.bean.isInitOnStartupSet()) {
               copy.setInitOnStartup(this.bean.isInitOnStartup());
            }

            if ((excludeProps == null || !excludeProps.contains("PassivationCapable")) && this.bean.isPassivationCapableSet()) {
               copy.setPassivationCapable(this.bean.isPassivationCapable());
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
         this.inferSubTree(this.bean.getAfterBeginMethod(), clazz, annotation);
         this.inferSubTree(this.bean.getAfterCompletionMethod(), clazz, annotation);
         this.inferSubTree(this.bean.getAroundInvokes(), clazz, annotation);
         this.inferSubTree(this.bean.getAroundTimeouts(), clazz, annotation);
         this.inferSubTree(this.bean.getAsyncMethods(), clazz, annotation);
         this.inferSubTree(this.bean.getBeforeCompletionMethod(), clazz, annotation);
         this.inferSubTree(this.bean.getConcurrentMethods(), clazz, annotation);
         this.inferSubTree(this.bean.getConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getDataSources(), clazz, annotation);
         this.inferSubTree(this.bean.getDependsOn(), clazz, annotation);
         Annotation EjbClassAnno = (Annotation)annotation;
         if (!this.bean.isEjbClassSet()) {
            this.bean.setEjbClass(clazz.getName());
         }

         this.inferSubTree(this.bean.getEjbLocalRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getEjbRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getEnvEntries(), clazz, annotation);
         if (clazz.isAnnotationPresent(RemoteHome.class)) {
            RemoteHome HomeAnno = (RemoteHome)clazz.getAnnotation(RemoteHome.class);
            if (!this.bean.isHomeSet()) {
               this.bean.setHome(HomeAnno.value().getName());
            }
         }

         this.inferSubTree(this.bean.getIcons(), clazz, annotation);
         this.inferSubTree(this.bean.getInitMethods(), clazz, annotation);
         this.inferSubTree(this.bean.getJmsConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getJmsDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getLocalBean(), clazz, annotation);
         if (clazz.isAnnotationPresent(LocalHome.class)) {
            LocalHome LocalHomeAnno = (LocalHome)clazz.getAnnotation(LocalHome.class);
            if (!this.bean.isLocalHomeSet()) {
               this.bean.setLocalHome(LocalHomeAnno.value().getName());
            }
         }

         this.inferSubTree(this.bean.getMailSessions(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageDestinationRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getPersistenceContextRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getPersistenceUnitRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getPostActivates(), clazz, annotation);
         this.inferSubTree(this.bean.getPostConstructs(), clazz, annotation);
         this.inferSubTree(this.bean.getPreDestroys(), clazz, annotation);
         this.inferSubTree(this.bean.getPrePassivates(), clazz, annotation);
         this.inferSubTree(this.bean.getRemoveMethods(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceEnvRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityIdentity(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityRoleRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getServiceRefs(), clazz, annotation);
         Annotation SessionTypeAnno = (Annotation)annotation;
         if (!this.bean.isSessionTypeSet()) {
            this.bean.setSessionType(SessionTypeAnno.annotationType().getSimpleName());
         }

         this.inferSubTree(this.bean.getStatefulTimeout(), clazz, annotation);
         this.inferSubTree(this.bean.getTimeoutMethod(), clazz, annotation);
         this.inferSubTree(this.bean.getTimers(), clazz, annotation);
         if (clazz.isAnnotationPresent(TransactionManagement.class)) {
            TransactionManagement TransactionTypeAnno = (TransactionManagement)clazz.getAnnotation(TransactionManagement.class);
            if (!this.bean.isTransactionTypeSet()) {
               this.bean.setTransactionType(TransactionTypeAnno.value() == TransactionManagementType.CONTAINER ? "Container" : "Bean");
            }
         }

      }
   }
}

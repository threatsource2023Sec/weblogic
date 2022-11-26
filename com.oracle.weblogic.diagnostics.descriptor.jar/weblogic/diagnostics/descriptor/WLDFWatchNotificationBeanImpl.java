package weblogic.diagnostics.descriptor;

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
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.diagnostics.descriptor.validation.WatchNotificationValidators;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WLDFWatchNotificationBeanImpl extends WLDFBeanImpl implements WLDFWatchNotificationBean, Serializable {
   private WLDFActionBean[] _Actions;
   private boolean _Enabled;
   private WLDFHeapDumpActionBean[] _HeapDumpActions;
   private WLDFImageNotificationBean[] _ImageNotifications;
   private WLDFJMSNotificationBean[] _JMSNotifications;
   private WLDFJMXNotificationBean[] _JMXNotifications;
   private WLDFLogActionBean[] _LogActions;
   private String _LogWatchSeverity;
   private WLDFNotificationBean[] _Notifications;
   private WLDFRESTNotificationBean[] _RESTNotifications;
   private WLDFSMTPNotificationBean[] _SMTPNotifications;
   private WLDFSNMPNotificationBean[] _SNMPNotifications;
   private WLDFScaleDownActionBean[] _ScaleDownActions;
   private WLDFScaleUpActionBean[] _ScaleUpActions;
   private WLDFScriptActionBean[] _ScriptActions;
   private String _Severity;
   private WLDFThreadDumpActionBean[] _ThreadDumpActions;
   private WLDFWatchBean[] _Watches;
   private transient WLDFWatchNotificationCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public WLDFWatchNotificationBeanImpl() {
      try {
         this._customizer = new WLDFWatchNotificationCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public WLDFWatchNotificationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new WLDFWatchNotificationCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public WLDFWatchNotificationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new WLDFWatchNotificationCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(2);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getSeverity() {
      return this._Severity;
   }

   public boolean isSeverityInherited() {
      return false;
   }

   public boolean isSeveritySet() {
      return this._isSet(3);
   }

   public void setSeverity(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency"};
      param0 = LegalChecks.checkInEnum("Severity", param0, _set);
      String _oldVal = this._Severity;
      this._Severity = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getLogWatchSeverity() {
      return this._LogWatchSeverity;
   }

   public boolean isLogWatchSeverityInherited() {
      return false;
   }

   public boolean isLogWatchSeveritySet() {
      return this._isSet(4);
   }

   public void setLogWatchSeverity(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency"};
      param0 = LegalChecks.checkInEnum("LogWatchSeverity", param0, _set);
      String _oldVal = this._LogWatchSeverity;
      this._LogWatchSeverity = param0;
      this._postSet(4, _oldVal, param0);
   }

   public void addWatch(WLDFWatchBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         WLDFWatchBean[] _new;
         if (this._isSet(5)) {
            _new = (WLDFWatchBean[])((WLDFWatchBean[])this._getHelper()._extendArray(this.getWatches(), WLDFWatchBean.class, param0));
         } else {
            _new = new WLDFWatchBean[]{param0};
         }

         try {
            this.setWatches(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFWatchBean[] getWatches() {
      return this._Watches;
   }

   public boolean isWatchesInherited() {
      return false;
   }

   public boolean isWatchesSet() {
      return this._isSet(5);
   }

   public void removeWatch(WLDFWatchBean param0) {
      this.destroyWatch(param0);
   }

   public void setWatches(WLDFWatchBean[] param0) throws InvalidAttributeValueException {
      WLDFWatchBean[] param0 = param0 == null ? new WLDFWatchBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WLDFWatchBean[] _oldVal = this._Watches;
      this._Watches = (WLDFWatchBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public WLDFWatchBean createWatch(String param0) {
      WLDFWatchBeanImpl lookup = (WLDFWatchBeanImpl)this.lookupWatch(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFWatchBeanImpl _val = new WLDFWatchBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWatch(_val);
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

   public void destroyWatch(WLDFWatchBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         WLDFWatchBean[] _old = this.getWatches();
         WLDFWatchBean[] _new = (WLDFWatchBean[])((WLDFWatchBean[])this._getHelper()._removeElement(_old, WLDFWatchBean.class, param0));
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
               this.setWatches(_new);
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

   public void addNotification(WLDFNotificationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         WLDFNotificationBean[] _new = (WLDFNotificationBean[])((WLDFNotificationBean[])this._getHelper()._extendArray(this.getNotifications(), WLDFNotificationBean.class, param0));

         try {
            this.setNotifications(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFNotificationBean[] getNotifications() {
      return this._customizer.getNotifications();
   }

   public boolean isNotificationsInherited() {
      return false;
   }

   public boolean isNotificationsSet() {
      return this._isSet(6);
   }

   public void removeNotification(WLDFNotificationBean param0) {
      WLDFNotificationBean[] _old = this.getNotifications();
      WLDFNotificationBean[] _new = (WLDFNotificationBean[])((WLDFNotificationBean[])this._getHelper()._removeElement(_old, WLDFNotificationBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setNotifications(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setNotifications(WLDFNotificationBean[] param0) throws InvalidAttributeValueException {
      WLDFNotificationBean[] param0 = param0 == null ? new WLDFNotificationBeanImpl[0] : param0;
      this._Notifications = (WLDFNotificationBean[])param0;
   }

   public WLDFNotificationBean lookupNotification(String param0) {
      return this._customizer.lookupNotification(param0);
   }

   public WLDFActionBean createAction(String param0, String param1) {
      WLDFActionBeanImpl lookup = (WLDFActionBeanImpl)this.lookupAction(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFActionBeanImpl _val = new WLDFActionBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setType(param1);
            this.addAction(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public void destroyAction(WLDFActionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 7);
         WLDFActionBean[] _old = this.getActions();
         WLDFActionBean[] _new = (WLDFActionBean[])((WLDFActionBean[])this._getHelper()._removeElement(_old, WLDFActionBean.class, param0));
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
               this.setActions(_new);
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

   public void addAction(WLDFActionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 7)) {
         WLDFActionBean[] _new;
         if (this._isSet(7)) {
            _new = (WLDFActionBean[])((WLDFActionBean[])this._getHelper()._extendArray(this.getActions(), WLDFActionBean.class, param0));
         } else {
            _new = new WLDFActionBean[]{param0};
         }

         try {
            this.setActions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFActionBean[] getActions() {
      return this._Actions;
   }

   public boolean isActionsInherited() {
      return false;
   }

   public boolean isActionsSet() {
      return this._isSet(7);
   }

   public void removeAction(WLDFActionBean param0) {
      this.destroyAction(param0);
   }

   public void setActions(WLDFActionBean[] param0) throws InvalidAttributeValueException {
      WLDFActionBean[] param0 = param0 == null ? new WLDFActionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 7)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFActionBean[] _oldVal = this._Actions;
      this._Actions = (WLDFActionBean[])param0;
      this._postSet(7, _oldVal, param0);
   }

   public WLDFActionBean lookupAction(String param0) {
      Object[] aary = (Object[])this._Actions;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFActionBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFActionBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public WLDFActionBean[] lookupActions(String param0) {
      return this._customizer.lookupActions(param0);
   }

   public void addImageNotification(WLDFImageNotificationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 8)) {
         WLDFImageNotificationBean[] _new;
         if (this._isSet(8)) {
            _new = (WLDFImageNotificationBean[])((WLDFImageNotificationBean[])this._getHelper()._extendArray(this.getImageNotifications(), WLDFImageNotificationBean.class, param0));
         } else {
            _new = new WLDFImageNotificationBean[]{param0};
         }

         try {
            this.setImageNotifications(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFImageNotificationBean[] getImageNotifications() {
      return this._ImageNotifications;
   }

   public boolean isImageNotificationsInherited() {
      return false;
   }

   public boolean isImageNotificationsSet() {
      return this._isSet(8);
   }

   public void removeImageNotification(WLDFImageNotificationBean param0) {
      this.destroyImageNotification(param0);
   }

   public void setImageNotifications(WLDFImageNotificationBean[] param0) throws InvalidAttributeValueException {
      WLDFImageNotificationBean[] param0 = param0 == null ? new WLDFImageNotificationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 8)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFImageNotificationBean[] _oldVal = this._ImageNotifications;
      this._ImageNotifications = (WLDFImageNotificationBean[])param0;
      this._postSet(8, _oldVal, param0);
   }

   public WLDFImageNotificationBean createImageNotification(String param0) {
      WLDFImageNotificationBeanImpl lookup = (WLDFImageNotificationBeanImpl)this.lookupImageNotification(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFImageNotificationBeanImpl _val = new WLDFImageNotificationBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addImageNotification(_val);
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

   public void destroyImageNotification(WLDFImageNotificationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 8);
         WLDFImageNotificationBean[] _old = this.getImageNotifications();
         WLDFImageNotificationBean[] _new = (WLDFImageNotificationBean[])((WLDFImageNotificationBean[])this._getHelper()._removeElement(_old, WLDFImageNotificationBean.class, param0));
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
               this.setImageNotifications(_new);
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

   public WLDFImageNotificationBean lookupImageNotification(String param0) {
      Object[] aary = (Object[])this._ImageNotifications;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFImageNotificationBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFImageNotificationBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSNotification(WLDFJMSNotificationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
         WLDFJMSNotificationBean[] _new;
         if (this._isSet(9)) {
            _new = (WLDFJMSNotificationBean[])((WLDFJMSNotificationBean[])this._getHelper()._extendArray(this.getJMSNotifications(), WLDFJMSNotificationBean.class, param0));
         } else {
            _new = new WLDFJMSNotificationBean[]{param0};
         }

         try {
            this.setJMSNotifications(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFJMSNotificationBean[] getJMSNotifications() {
      return this._JMSNotifications;
   }

   public boolean isJMSNotificationsInherited() {
      return false;
   }

   public boolean isJMSNotificationsSet() {
      return this._isSet(9);
   }

   public void removeJMSNotification(WLDFJMSNotificationBean param0) {
      this.destroyJMSNotification(param0);
   }

   public void setJMSNotifications(WLDFJMSNotificationBean[] param0) throws InvalidAttributeValueException {
      WLDFJMSNotificationBean[] param0 = param0 == null ? new WLDFJMSNotificationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 9)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFJMSNotificationBean[] _oldVal = this._JMSNotifications;
      this._JMSNotifications = (WLDFJMSNotificationBean[])param0;
      this._postSet(9, _oldVal, param0);
   }

   public WLDFJMSNotificationBean createJMSNotification(String param0) {
      WLDFJMSNotificationBeanImpl lookup = (WLDFJMSNotificationBeanImpl)this.lookupJMSNotification(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFJMSNotificationBeanImpl _val = new WLDFJMSNotificationBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSNotification(_val);
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

   public void destroyJMSNotification(WLDFJMSNotificationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 9);
         WLDFJMSNotificationBean[] _old = this.getJMSNotifications();
         WLDFJMSNotificationBean[] _new = (WLDFJMSNotificationBean[])((WLDFJMSNotificationBean[])this._getHelper()._removeElement(_old, WLDFJMSNotificationBean.class, param0));
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
               this.setJMSNotifications(_new);
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

   public WLDFJMSNotificationBean lookupJMSNotification(String param0) {
      Object[] aary = (Object[])this._JMSNotifications;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFJMSNotificationBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFJMSNotificationBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addLogAction(WLDFLogActionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         WLDFLogActionBean[] _new;
         if (this._isSet(10)) {
            _new = (WLDFLogActionBean[])((WLDFLogActionBean[])this._getHelper()._extendArray(this.getLogActions(), WLDFLogActionBean.class, param0));
         } else {
            _new = new WLDFLogActionBean[]{param0};
         }

         try {
            this.setLogActions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFLogActionBean[] getLogActions() {
      return this._LogActions;
   }

   public boolean isLogActionsInherited() {
      return false;
   }

   public boolean isLogActionsSet() {
      return this._isSet(10);
   }

   public void removeLogAction(WLDFLogActionBean param0) {
      this.destroyLogAction(param0);
   }

   public void setLogActions(WLDFLogActionBean[] param0) throws InvalidAttributeValueException {
      WLDFLogActionBean[] param0 = param0 == null ? new WLDFLogActionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFLogActionBean[] _oldVal = this._LogActions;
      this._LogActions = (WLDFLogActionBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public WLDFLogActionBean createLogAction(String param0) {
      WLDFLogActionBeanImpl lookup = (WLDFLogActionBeanImpl)this.lookupLogAction(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFLogActionBeanImpl _val = new WLDFLogActionBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addLogAction(_val);
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

   public void destroyLogAction(WLDFLogActionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         WLDFLogActionBean[] _old = this.getLogActions();
         WLDFLogActionBean[] _new = (WLDFLogActionBean[])((WLDFLogActionBean[])this._getHelper()._removeElement(_old, WLDFLogActionBean.class, param0));
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
               this.setLogActions(_new);
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

   public WLDFLogActionBean lookupLogAction(String param0) {
      Object[] aary = (Object[])this._LogActions;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFLogActionBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFLogActionBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMXNotification(WLDFJMXNotificationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         WLDFJMXNotificationBean[] _new;
         if (this._isSet(11)) {
            _new = (WLDFJMXNotificationBean[])((WLDFJMXNotificationBean[])this._getHelper()._extendArray(this.getJMXNotifications(), WLDFJMXNotificationBean.class, param0));
         } else {
            _new = new WLDFJMXNotificationBean[]{param0};
         }

         try {
            this.setJMXNotifications(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFJMXNotificationBean[] getJMXNotifications() {
      return this._JMXNotifications;
   }

   public boolean isJMXNotificationsInherited() {
      return false;
   }

   public boolean isJMXNotificationsSet() {
      return this._isSet(11);
   }

   public void removeJMXNotification(WLDFJMXNotificationBean param0) {
      this.destroyJMXNotification(param0);
   }

   public void setJMXNotifications(WLDFJMXNotificationBean[] param0) throws InvalidAttributeValueException {
      WLDFJMXNotificationBean[] param0 = param0 == null ? new WLDFJMXNotificationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFJMXNotificationBean[] _oldVal = this._JMXNotifications;
      this._JMXNotifications = (WLDFJMXNotificationBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public WLDFJMXNotificationBean createJMXNotification(String param0) {
      WLDFJMXNotificationBeanImpl lookup = (WLDFJMXNotificationBeanImpl)this.lookupJMXNotification(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFJMXNotificationBeanImpl _val = new WLDFJMXNotificationBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMXNotification(_val);
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

   public void destroyJMXNotification(WLDFJMXNotificationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         WLDFJMXNotificationBean[] _old = this.getJMXNotifications();
         WLDFJMXNotificationBean[] _new = (WLDFJMXNotificationBean[])((WLDFJMXNotificationBean[])this._getHelper()._removeElement(_old, WLDFJMXNotificationBean.class, param0));
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
               this.setJMXNotifications(_new);
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

   public WLDFJMXNotificationBean lookupJMXNotification(String param0) {
      Object[] aary = (Object[])this._JMXNotifications;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFJMXNotificationBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFJMXNotificationBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addSMTPNotification(WLDFSMTPNotificationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         WLDFSMTPNotificationBean[] _new;
         if (this._isSet(12)) {
            _new = (WLDFSMTPNotificationBean[])((WLDFSMTPNotificationBean[])this._getHelper()._extendArray(this.getSMTPNotifications(), WLDFSMTPNotificationBean.class, param0));
         } else {
            _new = new WLDFSMTPNotificationBean[]{param0};
         }

         try {
            this.setSMTPNotifications(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFSMTPNotificationBean[] getSMTPNotifications() {
      return this._SMTPNotifications;
   }

   public boolean isSMTPNotificationsInherited() {
      return false;
   }

   public boolean isSMTPNotificationsSet() {
      return this._isSet(12);
   }

   public void removeSMTPNotification(WLDFSMTPNotificationBean param0) {
      this.destroySMTPNotification(param0);
   }

   public void setSMTPNotifications(WLDFSMTPNotificationBean[] param0) throws InvalidAttributeValueException {
      WLDFSMTPNotificationBean[] param0 = param0 == null ? new WLDFSMTPNotificationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFSMTPNotificationBean[] _oldVal = this._SMTPNotifications;
      this._SMTPNotifications = (WLDFSMTPNotificationBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public WLDFSMTPNotificationBean createSMTPNotification(String param0) {
      WLDFSMTPNotificationBeanImpl lookup = (WLDFSMTPNotificationBeanImpl)this.lookupSMTPNotification(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFSMTPNotificationBeanImpl _val = new WLDFSMTPNotificationBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSMTPNotification(_val);
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

   public void destroySMTPNotification(WLDFSMTPNotificationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         WLDFSMTPNotificationBean[] _old = this.getSMTPNotifications();
         WLDFSMTPNotificationBean[] _new = (WLDFSMTPNotificationBean[])((WLDFSMTPNotificationBean[])this._getHelper()._removeElement(_old, WLDFSMTPNotificationBean.class, param0));
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
               this.setSMTPNotifications(_new);
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

   public WLDFSMTPNotificationBean lookupSMTPNotification(String param0) {
      Object[] aary = (Object[])this._SMTPNotifications;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFSMTPNotificationBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFSMTPNotificationBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addSNMPNotification(WLDFSNMPNotificationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         WLDFSNMPNotificationBean[] _new;
         if (this._isSet(13)) {
            _new = (WLDFSNMPNotificationBean[])((WLDFSNMPNotificationBean[])this._getHelper()._extendArray(this.getSNMPNotifications(), WLDFSNMPNotificationBean.class, param0));
         } else {
            _new = new WLDFSNMPNotificationBean[]{param0};
         }

         try {
            this.setSNMPNotifications(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFSNMPNotificationBean[] getSNMPNotifications() {
      return this._SNMPNotifications;
   }

   public boolean isSNMPNotificationsInherited() {
      return false;
   }

   public boolean isSNMPNotificationsSet() {
      return this._isSet(13);
   }

   public void removeSNMPNotification(WLDFSNMPNotificationBean param0) {
      this.destroySNMPNotification(param0);
   }

   public void setSNMPNotifications(WLDFSNMPNotificationBean[] param0) throws InvalidAttributeValueException {
      WLDFSNMPNotificationBean[] param0 = param0 == null ? new WLDFSNMPNotificationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFSNMPNotificationBean[] _oldVal = this._SNMPNotifications;
      this._SNMPNotifications = (WLDFSNMPNotificationBean[])param0;
      this._postSet(13, _oldVal, param0);
   }

   public WLDFSNMPNotificationBean createSNMPNotification(String param0) {
      WLDFSNMPNotificationBeanImpl lookup = (WLDFSNMPNotificationBeanImpl)this.lookupSNMPNotification(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFSNMPNotificationBeanImpl _val = new WLDFSNMPNotificationBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSNMPNotification(_val);
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

   public void destroySNMPNotification(WLDFSNMPNotificationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 13);
         WLDFSNMPNotificationBean[] _old = this.getSNMPNotifications();
         WLDFSNMPNotificationBean[] _new = (WLDFSNMPNotificationBean[])((WLDFSNMPNotificationBean[])this._getHelper()._removeElement(_old, WLDFSNMPNotificationBean.class, param0));
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
               this.setSNMPNotifications(_new);
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

   public WLDFSNMPNotificationBean lookupSNMPNotification(String param0) {
      Object[] aary = (Object[])this._SNMPNotifications;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFSNMPNotificationBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFSNMPNotificationBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addRESTNotification(WLDFRESTNotificationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         WLDFRESTNotificationBean[] _new;
         if (this._isSet(14)) {
            _new = (WLDFRESTNotificationBean[])((WLDFRESTNotificationBean[])this._getHelper()._extendArray(this.getRESTNotifications(), WLDFRESTNotificationBean.class, param0));
         } else {
            _new = new WLDFRESTNotificationBean[]{param0};
         }

         try {
            this.setRESTNotifications(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFRESTNotificationBean[] getRESTNotifications() {
      return this._RESTNotifications;
   }

   public boolean isRESTNotificationsInherited() {
      return false;
   }

   public boolean isRESTNotificationsSet() {
      return this._isSet(14);
   }

   public void removeRESTNotification(WLDFRESTNotificationBean param0) {
      this.destroyRESTNotification(param0);
   }

   public void setRESTNotifications(WLDFRESTNotificationBean[] param0) throws InvalidAttributeValueException {
      WLDFRESTNotificationBean[] param0 = param0 == null ? new WLDFRESTNotificationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFRESTNotificationBean[] _oldVal = this._RESTNotifications;
      this._RESTNotifications = (WLDFRESTNotificationBean[])param0;
      this._postSet(14, _oldVal, param0);
   }

   public WLDFRESTNotificationBean createRESTNotification(String param0) {
      WLDFRESTNotificationBeanImpl lookup = (WLDFRESTNotificationBeanImpl)this.lookupRESTNotification(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFRESTNotificationBeanImpl _val = new WLDFRESTNotificationBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addRESTNotification(_val);
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

   public void destroyRESTNotification(WLDFRESTNotificationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 14);
         WLDFRESTNotificationBean[] _old = this.getRESTNotifications();
         WLDFRESTNotificationBean[] _new = (WLDFRESTNotificationBean[])((WLDFRESTNotificationBean[])this._getHelper()._removeElement(_old, WLDFRESTNotificationBean.class, param0));
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
               this.setRESTNotifications(_new);
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

   public WLDFRESTNotificationBean lookupRESTNotification(String param0) {
      Object[] aary = (Object[])this._RESTNotifications;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFRESTNotificationBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFRESTNotificationBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addScaleUpAction(WLDFScaleUpActionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         WLDFScaleUpActionBean[] _new;
         if (this._isSet(15)) {
            _new = (WLDFScaleUpActionBean[])((WLDFScaleUpActionBean[])this._getHelper()._extendArray(this.getScaleUpActions(), WLDFScaleUpActionBean.class, param0));
         } else {
            _new = new WLDFScaleUpActionBean[]{param0};
         }

         try {
            this.setScaleUpActions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFScaleUpActionBean[] getScaleUpActions() {
      return this._ScaleUpActions;
   }

   public boolean isScaleUpActionsInherited() {
      return false;
   }

   public boolean isScaleUpActionsSet() {
      return this._isSet(15);
   }

   public void removeScaleUpAction(WLDFScaleUpActionBean param0) {
      this.destroyScaleUpAction(param0);
   }

   public void setScaleUpActions(WLDFScaleUpActionBean[] param0) throws InvalidAttributeValueException {
      WLDFScaleUpActionBean[] param0 = param0 == null ? new WLDFScaleUpActionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 15)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFScaleUpActionBean[] _oldVal = this._ScaleUpActions;
      this._ScaleUpActions = (WLDFScaleUpActionBean[])param0;
      this._postSet(15, _oldVal, param0);
   }

   public WLDFScaleUpActionBean createScaleUpAction(String param0) {
      WLDFScaleUpActionBeanImpl lookup = (WLDFScaleUpActionBeanImpl)this.lookupScaleUpAction(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFScaleUpActionBeanImpl _val = new WLDFScaleUpActionBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addScaleUpAction(_val);
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

   public void destroyScaleUpAction(WLDFScaleUpActionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 15);
         WLDFScaleUpActionBean[] _old = this.getScaleUpActions();
         WLDFScaleUpActionBean[] _new = (WLDFScaleUpActionBean[])((WLDFScaleUpActionBean[])this._getHelper()._removeElement(_old, WLDFScaleUpActionBean.class, param0));
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
               this.setScaleUpActions(_new);
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

   public WLDFScaleDownActionBean lookupScaleDownAction(String param0) {
      Object[] aary = (Object[])this._ScaleDownActions;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFScaleDownActionBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFScaleDownActionBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addScaleDownAction(WLDFScaleDownActionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 16)) {
         WLDFScaleDownActionBean[] _new;
         if (this._isSet(16)) {
            _new = (WLDFScaleDownActionBean[])((WLDFScaleDownActionBean[])this._getHelper()._extendArray(this.getScaleDownActions(), WLDFScaleDownActionBean.class, param0));
         } else {
            _new = new WLDFScaleDownActionBean[]{param0};
         }

         try {
            this.setScaleDownActions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFScaleDownActionBean[] getScaleDownActions() {
      return this._ScaleDownActions;
   }

   public boolean isScaleDownActionsInherited() {
      return false;
   }

   public boolean isScaleDownActionsSet() {
      return this._isSet(16);
   }

   public void removeScaleDownAction(WLDFScaleDownActionBean param0) {
      this.destroyScaleDownAction(param0);
   }

   public void setScaleDownActions(WLDFScaleDownActionBean[] param0) throws InvalidAttributeValueException {
      WLDFScaleDownActionBean[] param0 = param0 == null ? new WLDFScaleDownActionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 16)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFScaleDownActionBean[] _oldVal = this._ScaleDownActions;
      this._ScaleDownActions = (WLDFScaleDownActionBean[])param0;
      this._postSet(16, _oldVal, param0);
   }

   public WLDFScaleDownActionBean createScaleDownAction(String param0) {
      WLDFScaleDownActionBeanImpl lookup = (WLDFScaleDownActionBeanImpl)this.lookupScaleDownAction(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFScaleDownActionBeanImpl _val = new WLDFScaleDownActionBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addScaleDownAction(_val);
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

   public void destroyScaleDownAction(WLDFScaleDownActionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 16);
         WLDFScaleDownActionBean[] _old = this.getScaleDownActions();
         WLDFScaleDownActionBean[] _new = (WLDFScaleDownActionBean[])((WLDFScaleDownActionBean[])this._getHelper()._removeElement(_old, WLDFScaleDownActionBean.class, param0));
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
               this.setScaleDownActions(_new);
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

   public WLDFScaleUpActionBean lookupScaleUpAction(String param0) {
      Object[] aary = (Object[])this._ScaleUpActions;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFScaleUpActionBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFScaleUpActionBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addScriptAction(WLDFScriptActionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         WLDFScriptActionBean[] _new;
         if (this._isSet(17)) {
            _new = (WLDFScriptActionBean[])((WLDFScriptActionBean[])this._getHelper()._extendArray(this.getScriptActions(), WLDFScriptActionBean.class, param0));
         } else {
            _new = new WLDFScriptActionBean[]{param0};
         }

         try {
            this.setScriptActions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFScriptActionBean[] getScriptActions() {
      return this._ScriptActions;
   }

   public boolean isScriptActionsInherited() {
      return false;
   }

   public boolean isScriptActionsSet() {
      return this._isSet(17);
   }

   public void removeScriptAction(WLDFScriptActionBean param0) {
      this.destroyScriptAction(param0);
   }

   public void setScriptActions(WLDFScriptActionBean[] param0) throws InvalidAttributeValueException {
      WLDFScriptActionBean[] param0 = param0 == null ? new WLDFScriptActionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 17)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFScriptActionBean[] _oldVal = this._ScriptActions;
      this._ScriptActions = (WLDFScriptActionBean[])param0;
      this._postSet(17, _oldVal, param0);
   }

   public WLDFScriptActionBean createScriptAction(String param0) {
      WLDFScriptActionBeanImpl lookup = (WLDFScriptActionBeanImpl)this.lookupScriptAction(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFScriptActionBeanImpl _val = new WLDFScriptActionBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addScriptAction(_val);
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

   public void destroyScriptAction(WLDFScriptActionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 17);
         WLDFScriptActionBean[] _old = this.getScriptActions();
         WLDFScriptActionBean[] _new = (WLDFScriptActionBean[])((WLDFScriptActionBean[])this._getHelper()._removeElement(_old, WLDFScriptActionBean.class, param0));
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
               this.setScriptActions(_new);
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

   public WLDFScriptActionBean lookupScriptAction(String param0) {
      Object[] aary = (Object[])this._ScriptActions;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFScriptActionBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFScriptActionBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addHeapDumpAction(WLDFHeapDumpActionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 18)) {
         WLDFHeapDumpActionBean[] _new;
         if (this._isSet(18)) {
            _new = (WLDFHeapDumpActionBean[])((WLDFHeapDumpActionBean[])this._getHelper()._extendArray(this.getHeapDumpActions(), WLDFHeapDumpActionBean.class, param0));
         } else {
            _new = new WLDFHeapDumpActionBean[]{param0};
         }

         try {
            this.setHeapDumpActions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFHeapDumpActionBean[] getHeapDumpActions() {
      return this._HeapDumpActions;
   }

   public boolean isHeapDumpActionsInherited() {
      return false;
   }

   public boolean isHeapDumpActionsSet() {
      return this._isSet(18);
   }

   public void removeHeapDumpAction(WLDFHeapDumpActionBean param0) {
      this.destroyHeapDumpAction(param0);
   }

   public void setHeapDumpActions(WLDFHeapDumpActionBean[] param0) throws InvalidAttributeValueException {
      WLDFHeapDumpActionBean[] param0 = param0 == null ? new WLDFHeapDumpActionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 18)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFHeapDumpActionBean[] _oldVal = this._HeapDumpActions;
      this._HeapDumpActions = (WLDFHeapDumpActionBean[])param0;
      this._postSet(18, _oldVal, param0);
   }

   public WLDFHeapDumpActionBean createHeapDumpAction(String param0) {
      WLDFHeapDumpActionBeanImpl lookup = (WLDFHeapDumpActionBeanImpl)this.lookupHeapDumpAction(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFHeapDumpActionBeanImpl _val = new WLDFHeapDumpActionBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addHeapDumpAction(_val);
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

   public void destroyHeapDumpAction(WLDFHeapDumpActionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 18);
         WLDFHeapDumpActionBean[] _old = this.getHeapDumpActions();
         WLDFHeapDumpActionBean[] _new = (WLDFHeapDumpActionBean[])((WLDFHeapDumpActionBean[])this._getHelper()._removeElement(_old, WLDFHeapDumpActionBean.class, param0));
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
               this.setHeapDumpActions(_new);
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

   public WLDFHeapDumpActionBean lookupHeapDumpAction(String param0) {
      Object[] aary = (Object[])this._HeapDumpActions;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFHeapDumpActionBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFHeapDumpActionBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addThreadDumpAction(WLDFThreadDumpActionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 19)) {
         WLDFThreadDumpActionBean[] _new;
         if (this._isSet(19)) {
            _new = (WLDFThreadDumpActionBean[])((WLDFThreadDumpActionBean[])this._getHelper()._extendArray(this.getThreadDumpActions(), WLDFThreadDumpActionBean.class, param0));
         } else {
            _new = new WLDFThreadDumpActionBean[]{param0};
         }

         try {
            this.setThreadDumpActions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFThreadDumpActionBean[] getThreadDumpActions() {
      return this._ThreadDumpActions;
   }

   public boolean isThreadDumpActionsInherited() {
      return false;
   }

   public boolean isThreadDumpActionsSet() {
      return this._isSet(19);
   }

   public void removeThreadDumpAction(WLDFThreadDumpActionBean param0) {
      this.destroyThreadDumpAction(param0);
   }

   public void setThreadDumpActions(WLDFThreadDumpActionBean[] param0) throws InvalidAttributeValueException {
      WLDFThreadDumpActionBean[] param0 = param0 == null ? new WLDFThreadDumpActionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 19)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFThreadDumpActionBean[] _oldVal = this._ThreadDumpActions;
      this._ThreadDumpActions = (WLDFThreadDumpActionBean[])param0;
      this._postSet(19, _oldVal, param0);
   }

   public WLDFThreadDumpActionBean createThreadDumpAction(String param0) {
      WLDFThreadDumpActionBeanImpl lookup = (WLDFThreadDumpActionBeanImpl)this.lookupThreadDumpAction(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFThreadDumpActionBeanImpl _val = new WLDFThreadDumpActionBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addThreadDumpAction(_val);
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

   public void destroyThreadDumpAction(WLDFThreadDumpActionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 19);
         WLDFThreadDumpActionBean[] _old = this.getThreadDumpActions();
         WLDFThreadDumpActionBean[] _new = (WLDFThreadDumpActionBean[])((WLDFThreadDumpActionBean[])this._getHelper()._removeElement(_old, WLDFThreadDumpActionBean.class, param0));
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
               this.setThreadDumpActions(_new);
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

   public WLDFThreadDumpActionBean lookupThreadDumpAction(String param0) {
      Object[] aary = (Object[])this._ThreadDumpActions;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFThreadDumpActionBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFThreadDumpActionBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public WLDFWatchBean lookupWatch(String param0) {
      Object[] aary = (Object[])this._Watches;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFWatchBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFWatchBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      WatchNotificationValidators.validateWatchNotificationBean(this);
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
               this._Actions = new WLDFActionBean[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._HeapDumpActions = new WLDFHeapDumpActionBean[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._ImageNotifications = new WLDFImageNotificationBean[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._JMSNotifications = new WLDFJMSNotificationBean[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._JMXNotifications = new WLDFJMXNotificationBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._LogActions = new WLDFLogActionBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._LogWatchSeverity = "Warning";
               if (initOne) {
                  break;
               }
            case 6:
               this._Notifications = new WLDFNotificationBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._RESTNotifications = new WLDFRESTNotificationBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._SMTPNotifications = new WLDFSMTPNotificationBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._SNMPNotifications = new WLDFSNMPNotificationBean[0];
               if (initOne) {
                  break;
               }
            case 16:
               this._ScaleDownActions = new WLDFScaleDownActionBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._ScaleUpActions = new WLDFScaleUpActionBean[0];
               if (initOne) {
                  break;
               }
            case 17:
               this._ScriptActions = new WLDFScriptActionBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._Severity = "Notice";
               if (initOne) {
                  break;
               }
            case 19:
               this._ThreadDumpActions = new WLDFThreadDumpActionBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._Watches = new WLDFWatchBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._Enabled = true;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/2.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends WLDFBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 6:
               if (s.equals("action")) {
                  return 7;
               }

               if (s.equals("watche")) {
                  return 5;
               }
               break;
            case 7:
               if (s.equals("enabled")) {
                  return 2;
               }
               break;
            case 8:
               if (s.equals("severity")) {
                  return 3;
               }
            case 9:
            case 11:
            case 14:
            default:
               break;
            case 10:
               if (s.equals("log-action")) {
                  return 10;
               }
               break;
            case 12:
               if (s.equals("notification")) {
                  return 6;
               }
               break;
            case 13:
               if (s.equals("script-action")) {
                  return 17;
               }
               break;
            case 15:
               if (s.equals("scale-up-action")) {
                  return 15;
               }
               break;
            case 16:
               if (s.equals("heap-dump-action")) {
                  return 18;
               }

               if (s.equals("jms-notification")) {
                  return 9;
               }

               if (s.equals("jmx-notification")) {
                  return 11;
               }
               break;
            case 17:
               if (s.equals("rest-notification")) {
                  return 14;
               }

               if (s.equals("smtp-notification")) {
                  return 12;
               }

               if (s.equals("snmp-notification")) {
                  return 13;
               }

               if (s.equals("scale-down-action")) {
                  return 16;
               }
               break;
            case 18:
               if (s.equals("image-notification")) {
                  return 8;
               }

               if (s.equals("log-watch-severity")) {
                  return 4;
               }

               if (s.equals("thread-dump-action")) {
                  return 19;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 5:
               return new WLDFWatchBeanImpl.SchemaHelper2();
            case 6:
            default:
               return super.getSchemaHelper(propIndex);
            case 7:
               return new WLDFActionBeanImpl.SchemaHelper2();
            case 8:
               return new WLDFImageNotificationBeanImpl.SchemaHelper2();
            case 9:
               return new WLDFJMSNotificationBeanImpl.SchemaHelper2();
            case 10:
               return new WLDFLogActionBeanImpl.SchemaHelper2();
            case 11:
               return new WLDFJMXNotificationBeanImpl.SchemaHelper2();
            case 12:
               return new WLDFSMTPNotificationBeanImpl.SchemaHelper2();
            case 13:
               return new WLDFSNMPNotificationBeanImpl.SchemaHelper2();
            case 14:
               return new WLDFRESTNotificationBeanImpl.SchemaHelper2();
            case 15:
               return new WLDFScaleUpActionBeanImpl.SchemaHelper2();
            case 16:
               return new WLDFScaleDownActionBeanImpl.SchemaHelper2();
            case 17:
               return new WLDFScriptActionBeanImpl.SchemaHelper2();
            case 18:
               return new WLDFHeapDumpActionBeanImpl.SchemaHelper2();
            case 19:
               return new WLDFThreadDumpActionBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "enabled";
            case 3:
               return "severity";
            case 4:
               return "log-watch-severity";
            case 5:
               return "watche";
            case 6:
               return "notification";
            case 7:
               return "action";
            case 8:
               return "image-notification";
            case 9:
               return "jms-notification";
            case 10:
               return "log-action";
            case 11:
               return "jmx-notification";
            case 12:
               return "smtp-notification";
            case 13:
               return "snmp-notification";
            case 14:
               return "rest-notification";
            case 15:
               return "scale-up-action";
            case 16:
               return "scale-down-action";
            case 17:
               return "script-action";
            case 18:
               return "heap-dump-action";
            case 19:
               return "thread-dump-action";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
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
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            case 6:
            default:
               return super.isBean(propIndex);
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends WLDFBeanImpl.Helper {
      private WLDFWatchNotificationBeanImpl bean;

      protected Helper(WLDFWatchNotificationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Enabled";
            case 3:
               return "Severity";
            case 4:
               return "LogWatchSeverity";
            case 5:
               return "Watches";
            case 6:
               return "Notifications";
            case 7:
               return "Actions";
            case 8:
               return "ImageNotifications";
            case 9:
               return "JMSNotifications";
            case 10:
               return "LogActions";
            case 11:
               return "JMXNotifications";
            case 12:
               return "SMTPNotifications";
            case 13:
               return "SNMPNotifications";
            case 14:
               return "RESTNotifications";
            case 15:
               return "ScaleUpActions";
            case 16:
               return "ScaleDownActions";
            case 17:
               return "ScriptActions";
            case 18:
               return "HeapDumpActions";
            case 19:
               return "ThreadDumpActions";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Actions")) {
            return 7;
         } else if (propName.equals("HeapDumpActions")) {
            return 18;
         } else if (propName.equals("ImageNotifications")) {
            return 8;
         } else if (propName.equals("JMSNotifications")) {
            return 9;
         } else if (propName.equals("JMXNotifications")) {
            return 11;
         } else if (propName.equals("LogActions")) {
            return 10;
         } else if (propName.equals("LogWatchSeverity")) {
            return 4;
         } else if (propName.equals("Notifications")) {
            return 6;
         } else if (propName.equals("RESTNotifications")) {
            return 14;
         } else if (propName.equals("SMTPNotifications")) {
            return 12;
         } else if (propName.equals("SNMPNotifications")) {
            return 13;
         } else if (propName.equals("ScaleDownActions")) {
            return 16;
         } else if (propName.equals("ScaleUpActions")) {
            return 15;
         } else if (propName.equals("ScriptActions")) {
            return 17;
         } else if (propName.equals("Severity")) {
            return 3;
         } else if (propName.equals("ThreadDumpActions")) {
            return 19;
         } else if (propName.equals("Watches")) {
            return 5;
         } else {
            return propName.equals("Enabled") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getActions()));
         iterators.add(new ArrayIterator(this.bean.getHeapDumpActions()));
         iterators.add(new ArrayIterator(this.bean.getImageNotifications()));
         iterators.add(new ArrayIterator(this.bean.getJMSNotifications()));
         iterators.add(new ArrayIterator(this.bean.getJMXNotifications()));
         iterators.add(new ArrayIterator(this.bean.getLogActions()));
         iterators.add(new ArrayIterator(this.bean.getRESTNotifications()));
         iterators.add(new ArrayIterator(this.bean.getSMTPNotifications()));
         iterators.add(new ArrayIterator(this.bean.getSNMPNotifications()));
         iterators.add(new ArrayIterator(this.bean.getScaleDownActions()));
         iterators.add(new ArrayIterator(this.bean.getScaleUpActions()));
         iterators.add(new ArrayIterator(this.bean.getScriptActions()));
         iterators.add(new ArrayIterator(this.bean.getThreadDumpActions()));
         iterators.add(new ArrayIterator(this.bean.getWatches()));
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
            for(i = 0; i < this.bean.getActions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getActions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getHeapDumpActions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getHeapDumpActions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getImageNotifications().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getImageNotifications()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSNotifications().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSNotifications()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMXNotifications().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMXNotifications()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getLogActions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLogActions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isLogWatchSeveritySet()) {
               buf.append("LogWatchSeverity");
               buf.append(String.valueOf(this.bean.getLogWatchSeverity()));
            }

            if (this.bean.isNotificationsSet()) {
               buf.append("Notifications");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getNotifications())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getRESTNotifications().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getRESTNotifications()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSMTPNotifications().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSMTPNotifications()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPNotifications().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPNotifications()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getScaleDownActions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getScaleDownActions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getScaleUpActions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getScaleUpActions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getScriptActions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getScriptActions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSeveritySet()) {
               buf.append("Severity");
               buf.append(String.valueOf(this.bean.getSeverity()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getThreadDumpActions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getThreadDumpActions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWatches().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWatches()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
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
            WLDFWatchNotificationBeanImpl otherTyped = (WLDFWatchNotificationBeanImpl)other;
            this.computeChildDiff("Actions", this.bean.getActions(), otherTyped.getActions(), true);
            this.computeChildDiff("HeapDumpActions", this.bean.getHeapDumpActions(), otherTyped.getHeapDumpActions(), true);
            this.computeChildDiff("ImageNotifications", this.bean.getImageNotifications(), otherTyped.getImageNotifications(), true);
            this.computeChildDiff("JMSNotifications", this.bean.getJMSNotifications(), otherTyped.getJMSNotifications(), true);
            this.computeChildDiff("JMXNotifications", this.bean.getJMXNotifications(), otherTyped.getJMXNotifications(), true);
            this.computeChildDiff("LogActions", this.bean.getLogActions(), otherTyped.getLogActions(), true);
            this.computeDiff("LogWatchSeverity", this.bean.getLogWatchSeverity(), otherTyped.getLogWatchSeverity(), true);
            this.computeChildDiff("RESTNotifications", this.bean.getRESTNotifications(), otherTyped.getRESTNotifications(), true);
            this.computeChildDiff("SMTPNotifications", this.bean.getSMTPNotifications(), otherTyped.getSMTPNotifications(), true);
            this.computeChildDiff("SNMPNotifications", this.bean.getSNMPNotifications(), otherTyped.getSNMPNotifications(), true);
            this.computeChildDiff("ScaleDownActions", this.bean.getScaleDownActions(), otherTyped.getScaleDownActions(), true);
            this.computeChildDiff("ScaleUpActions", this.bean.getScaleUpActions(), otherTyped.getScaleUpActions(), true);
            this.computeChildDiff("ScriptActions", this.bean.getScriptActions(), otherTyped.getScriptActions(), true);
            this.computeDiff("Severity", this.bean.getSeverity(), otherTyped.getSeverity(), true);
            this.computeChildDiff("ThreadDumpActions", this.bean.getThreadDumpActions(), otherTyped.getThreadDumpActions(), true);
            this.computeChildDiff("Watches", this.bean.getWatches(), otherTyped.getWatches(), true);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFWatchNotificationBeanImpl original = (WLDFWatchNotificationBeanImpl)event.getSourceBean();
            WLDFWatchNotificationBeanImpl proposed = (WLDFWatchNotificationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Actions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAction((WLDFActionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAction((WLDFActionBean)update.getRemovedObject());
                  }

                  if (original.getActions() == null || original.getActions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
                  }
               } else if (prop.equals("HeapDumpActions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addHeapDumpAction((WLDFHeapDumpActionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeHeapDumpAction((WLDFHeapDumpActionBean)update.getRemovedObject());
                  }

                  if (original.getHeapDumpActions() == null || original.getHeapDumpActions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  }
               } else if (prop.equals("ImageNotifications")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addImageNotification((WLDFImageNotificationBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeImageNotification((WLDFImageNotificationBean)update.getRemovedObject());
                  }

                  if (original.getImageNotifications() == null || original.getImageNotifications().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  }
               } else if (prop.equals("JMSNotifications")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJMSNotification((WLDFJMSNotificationBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJMSNotification((WLDFJMSNotificationBean)update.getRemovedObject());
                  }

                  if (original.getJMSNotifications() == null || original.getJMSNotifications().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("JMXNotifications")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJMXNotification((WLDFJMXNotificationBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJMXNotification((WLDFJMXNotificationBean)update.getRemovedObject());
                  }

                  if (original.getJMXNotifications() == null || original.getJMXNotifications().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("LogActions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addLogAction((WLDFLogActionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeLogAction((WLDFLogActionBean)update.getRemovedObject());
                  }

                  if (original.getLogActions() == null || original.getLogActions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
               } else if (prop.equals("LogWatchSeverity")) {
                  original.setLogWatchSeverity(proposed.getLogWatchSeverity());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (!prop.equals("Notifications")) {
                  if (prop.equals("RESTNotifications")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addRESTNotification((WLDFRESTNotificationBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeRESTNotification((WLDFRESTNotificationBean)update.getRemovedObject());
                     }

                     if (original.getRESTNotifications() == null || original.getRESTNotifications().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 14);
                     }
                  } else if (prop.equals("SMTPNotifications")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addSMTPNotification((WLDFSMTPNotificationBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeSMTPNotification((WLDFSMTPNotificationBean)update.getRemovedObject());
                     }

                     if (original.getSMTPNotifications() == null || original.getSMTPNotifications().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 12);
                     }
                  } else if (prop.equals("SNMPNotifications")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addSNMPNotification((WLDFSNMPNotificationBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeSNMPNotification((WLDFSNMPNotificationBean)update.getRemovedObject());
                     }

                     if (original.getSNMPNotifications() == null || original.getSNMPNotifications().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 13);
                     }
                  } else if (prop.equals("ScaleDownActions")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addScaleDownAction((WLDFScaleDownActionBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeScaleDownAction((WLDFScaleDownActionBean)update.getRemovedObject());
                     }

                     if (original.getScaleDownActions() == null || original.getScaleDownActions().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 16);
                     }
                  } else if (prop.equals("ScaleUpActions")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addScaleUpAction((WLDFScaleUpActionBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeScaleUpAction((WLDFScaleUpActionBean)update.getRemovedObject());
                     }

                     if (original.getScaleUpActions() == null || original.getScaleUpActions().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 15);
                     }
                  } else if (prop.equals("ScriptActions")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addScriptAction((WLDFScriptActionBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeScriptAction((WLDFScriptActionBean)update.getRemovedObject());
                     }

                     if (original.getScriptActions() == null || original.getScriptActions().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 17);
                     }
                  } else if (prop.equals("Severity")) {
                     original.setSeverity(proposed.getSeverity());
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  } else if (prop.equals("ThreadDumpActions")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addThreadDumpAction((WLDFThreadDumpActionBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeThreadDumpAction((WLDFThreadDumpActionBean)update.getRemovedObject());
                     }

                     if (original.getThreadDumpActions() == null || original.getThreadDumpActions().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 19);
                     }
                  } else if (prop.equals("Watches")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addWatch((WLDFWatchBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeWatch((WLDFWatchBean)update.getRemovedObject());
                     }

                     if (original.getWatches() == null || original.getWatches().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 5);
                     }
                  } else if (prop.equals("Enabled")) {
                     original.setEnabled(proposed.isEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            WLDFWatchNotificationBeanImpl copy = (WLDFWatchNotificationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("Actions")) && this.bean.isActionsSet() && !copy._isSet(7)) {
               WLDFActionBean[] oldActions = this.bean.getActions();
               WLDFActionBean[] newActions = new WLDFActionBean[oldActions.length];

               for(i = 0; i < newActions.length; ++i) {
                  newActions[i] = (WLDFActionBean)((WLDFActionBean)this.createCopy((AbstractDescriptorBean)oldActions[i], includeObsolete));
               }

               copy.setActions(newActions);
            }

            if ((excludeProps == null || !excludeProps.contains("HeapDumpActions")) && this.bean.isHeapDumpActionsSet() && !copy._isSet(18)) {
               WLDFHeapDumpActionBean[] oldHeapDumpActions = this.bean.getHeapDumpActions();
               WLDFHeapDumpActionBean[] newHeapDumpActions = new WLDFHeapDumpActionBean[oldHeapDumpActions.length];

               for(i = 0; i < newHeapDumpActions.length; ++i) {
                  newHeapDumpActions[i] = (WLDFHeapDumpActionBean)((WLDFHeapDumpActionBean)this.createCopy((AbstractDescriptorBean)oldHeapDumpActions[i], includeObsolete));
               }

               copy.setHeapDumpActions(newHeapDumpActions);
            }

            if ((excludeProps == null || !excludeProps.contains("ImageNotifications")) && this.bean.isImageNotificationsSet() && !copy._isSet(8)) {
               WLDFImageNotificationBean[] oldImageNotifications = this.bean.getImageNotifications();
               WLDFImageNotificationBean[] newImageNotifications = new WLDFImageNotificationBean[oldImageNotifications.length];

               for(i = 0; i < newImageNotifications.length; ++i) {
                  newImageNotifications[i] = (WLDFImageNotificationBean)((WLDFImageNotificationBean)this.createCopy((AbstractDescriptorBean)oldImageNotifications[i], includeObsolete));
               }

               copy.setImageNotifications(newImageNotifications);
            }

            if ((excludeProps == null || !excludeProps.contains("JMSNotifications")) && this.bean.isJMSNotificationsSet() && !copy._isSet(9)) {
               WLDFJMSNotificationBean[] oldJMSNotifications = this.bean.getJMSNotifications();
               WLDFJMSNotificationBean[] newJMSNotifications = new WLDFJMSNotificationBean[oldJMSNotifications.length];

               for(i = 0; i < newJMSNotifications.length; ++i) {
                  newJMSNotifications[i] = (WLDFJMSNotificationBean)((WLDFJMSNotificationBean)this.createCopy((AbstractDescriptorBean)oldJMSNotifications[i], includeObsolete));
               }

               copy.setJMSNotifications(newJMSNotifications);
            }

            if ((excludeProps == null || !excludeProps.contains("JMXNotifications")) && this.bean.isJMXNotificationsSet() && !copy._isSet(11)) {
               WLDFJMXNotificationBean[] oldJMXNotifications = this.bean.getJMXNotifications();
               WLDFJMXNotificationBean[] newJMXNotifications = new WLDFJMXNotificationBean[oldJMXNotifications.length];

               for(i = 0; i < newJMXNotifications.length; ++i) {
                  newJMXNotifications[i] = (WLDFJMXNotificationBean)((WLDFJMXNotificationBean)this.createCopy((AbstractDescriptorBean)oldJMXNotifications[i], includeObsolete));
               }

               copy.setJMXNotifications(newJMXNotifications);
            }

            if ((excludeProps == null || !excludeProps.contains("LogActions")) && this.bean.isLogActionsSet() && !copy._isSet(10)) {
               WLDFLogActionBean[] oldLogActions = this.bean.getLogActions();
               WLDFLogActionBean[] newLogActions = new WLDFLogActionBean[oldLogActions.length];

               for(i = 0; i < newLogActions.length; ++i) {
                  newLogActions[i] = (WLDFLogActionBean)((WLDFLogActionBean)this.createCopy((AbstractDescriptorBean)oldLogActions[i], includeObsolete));
               }

               copy.setLogActions(newLogActions);
            }

            if ((excludeProps == null || !excludeProps.contains("LogWatchSeverity")) && this.bean.isLogWatchSeveritySet()) {
               copy.setLogWatchSeverity(this.bean.getLogWatchSeverity());
            }

            if ((excludeProps == null || !excludeProps.contains("RESTNotifications")) && this.bean.isRESTNotificationsSet() && !copy._isSet(14)) {
               WLDFRESTNotificationBean[] oldRESTNotifications = this.bean.getRESTNotifications();
               WLDFRESTNotificationBean[] newRESTNotifications = new WLDFRESTNotificationBean[oldRESTNotifications.length];

               for(i = 0; i < newRESTNotifications.length; ++i) {
                  newRESTNotifications[i] = (WLDFRESTNotificationBean)((WLDFRESTNotificationBean)this.createCopy((AbstractDescriptorBean)oldRESTNotifications[i], includeObsolete));
               }

               copy.setRESTNotifications(newRESTNotifications);
            }

            if ((excludeProps == null || !excludeProps.contains("SMTPNotifications")) && this.bean.isSMTPNotificationsSet() && !copy._isSet(12)) {
               WLDFSMTPNotificationBean[] oldSMTPNotifications = this.bean.getSMTPNotifications();
               WLDFSMTPNotificationBean[] newSMTPNotifications = new WLDFSMTPNotificationBean[oldSMTPNotifications.length];

               for(i = 0; i < newSMTPNotifications.length; ++i) {
                  newSMTPNotifications[i] = (WLDFSMTPNotificationBean)((WLDFSMTPNotificationBean)this.createCopy((AbstractDescriptorBean)oldSMTPNotifications[i], includeObsolete));
               }

               copy.setSMTPNotifications(newSMTPNotifications);
            }

            if ((excludeProps == null || !excludeProps.contains("SNMPNotifications")) && this.bean.isSNMPNotificationsSet() && !copy._isSet(13)) {
               WLDFSNMPNotificationBean[] oldSNMPNotifications = this.bean.getSNMPNotifications();
               WLDFSNMPNotificationBean[] newSNMPNotifications = new WLDFSNMPNotificationBean[oldSNMPNotifications.length];

               for(i = 0; i < newSNMPNotifications.length; ++i) {
                  newSNMPNotifications[i] = (WLDFSNMPNotificationBean)((WLDFSNMPNotificationBean)this.createCopy((AbstractDescriptorBean)oldSNMPNotifications[i], includeObsolete));
               }

               copy.setSNMPNotifications(newSNMPNotifications);
            }

            if ((excludeProps == null || !excludeProps.contains("ScaleDownActions")) && this.bean.isScaleDownActionsSet() && !copy._isSet(16)) {
               WLDFScaleDownActionBean[] oldScaleDownActions = this.bean.getScaleDownActions();
               WLDFScaleDownActionBean[] newScaleDownActions = new WLDFScaleDownActionBean[oldScaleDownActions.length];

               for(i = 0; i < newScaleDownActions.length; ++i) {
                  newScaleDownActions[i] = (WLDFScaleDownActionBean)((WLDFScaleDownActionBean)this.createCopy((AbstractDescriptorBean)oldScaleDownActions[i], includeObsolete));
               }

               copy.setScaleDownActions(newScaleDownActions);
            }

            if ((excludeProps == null || !excludeProps.contains("ScaleUpActions")) && this.bean.isScaleUpActionsSet() && !copy._isSet(15)) {
               WLDFScaleUpActionBean[] oldScaleUpActions = this.bean.getScaleUpActions();
               WLDFScaleUpActionBean[] newScaleUpActions = new WLDFScaleUpActionBean[oldScaleUpActions.length];

               for(i = 0; i < newScaleUpActions.length; ++i) {
                  newScaleUpActions[i] = (WLDFScaleUpActionBean)((WLDFScaleUpActionBean)this.createCopy((AbstractDescriptorBean)oldScaleUpActions[i], includeObsolete));
               }

               copy.setScaleUpActions(newScaleUpActions);
            }

            if ((excludeProps == null || !excludeProps.contains("ScriptActions")) && this.bean.isScriptActionsSet() && !copy._isSet(17)) {
               WLDFScriptActionBean[] oldScriptActions = this.bean.getScriptActions();
               WLDFScriptActionBean[] newScriptActions = new WLDFScriptActionBean[oldScriptActions.length];

               for(i = 0; i < newScriptActions.length; ++i) {
                  newScriptActions[i] = (WLDFScriptActionBean)((WLDFScriptActionBean)this.createCopy((AbstractDescriptorBean)oldScriptActions[i], includeObsolete));
               }

               copy.setScriptActions(newScriptActions);
            }

            if ((excludeProps == null || !excludeProps.contains("Severity")) && this.bean.isSeveritySet()) {
               copy.setSeverity(this.bean.getSeverity());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadDumpActions")) && this.bean.isThreadDumpActionsSet() && !copy._isSet(19)) {
               WLDFThreadDumpActionBean[] oldThreadDumpActions = this.bean.getThreadDumpActions();
               WLDFThreadDumpActionBean[] newThreadDumpActions = new WLDFThreadDumpActionBean[oldThreadDumpActions.length];

               for(i = 0; i < newThreadDumpActions.length; ++i) {
                  newThreadDumpActions[i] = (WLDFThreadDumpActionBean)((WLDFThreadDumpActionBean)this.createCopy((AbstractDescriptorBean)oldThreadDumpActions[i], includeObsolete));
               }

               copy.setThreadDumpActions(newThreadDumpActions);
            }

            if ((excludeProps == null || !excludeProps.contains("Watches")) && this.bean.isWatchesSet() && !copy._isSet(5)) {
               WLDFWatchBean[] oldWatches = this.bean.getWatches();
               WLDFWatchBean[] newWatches = new WLDFWatchBean[oldWatches.length];

               for(i = 0; i < newWatches.length; ++i) {
                  newWatches[i] = (WLDFWatchBean)((WLDFWatchBean)this.createCopy((AbstractDescriptorBean)oldWatches[i], includeObsolete));
               }

               copy.setWatches(newWatches);
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
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
         this.inferSubTree(this.bean.getActions(), clazz, annotation);
         this.inferSubTree(this.bean.getHeapDumpActions(), clazz, annotation);
         this.inferSubTree(this.bean.getImageNotifications(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSNotifications(), clazz, annotation);
         this.inferSubTree(this.bean.getJMXNotifications(), clazz, annotation);
         this.inferSubTree(this.bean.getLogActions(), clazz, annotation);
         this.inferSubTree(this.bean.getNotifications(), clazz, annotation);
         this.inferSubTree(this.bean.getRESTNotifications(), clazz, annotation);
         this.inferSubTree(this.bean.getSMTPNotifications(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPNotifications(), clazz, annotation);
         this.inferSubTree(this.bean.getScaleDownActions(), clazz, annotation);
         this.inferSubTree(this.bean.getScaleUpActions(), clazz, annotation);
         this.inferSubTree(this.bean.getScriptActions(), clazz, annotation);
         this.inferSubTree(this.bean.getThreadDumpActions(), clazz, annotation);
         this.inferSubTree(this.bean.getWatches(), clazz, annotation);
      }
   }
}

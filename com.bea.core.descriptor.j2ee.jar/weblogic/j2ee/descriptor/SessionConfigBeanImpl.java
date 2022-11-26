package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.servlet.internal.WebXmlValidator;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SessionConfigBeanImpl extends AbstractDescriptorBean implements SessionConfigBean, Serializable {
   private CookieConfigBean _CookieConfig;
   private String _Id;
   private int _SessionTimeout;
   private String[] _TrackingModes;
   private static SchemaHelper2 _schemaHelper;

   public SessionConfigBeanImpl() {
      this._initializeProperty(-1);
   }

   public SessionConfigBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SessionConfigBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getSessionTimeout() {
      return this._SessionTimeout;
   }

   public boolean isSessionTimeoutInherited() {
      return false;
   }

   public boolean isSessionTimeoutSet() {
      return this._isSet(0);
   }

   public void setSessionTimeout(int param0) {
      int _oldVal = this._SessionTimeout;
      this._SessionTimeout = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(1);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(1, _oldVal, param0);
   }

   public CookieConfigBean getCookieConfig() {
      return this._CookieConfig;
   }

   public boolean isCookieConfigInherited() {
      return false;
   }

   public boolean isCookieConfigSet() {
      return this._isSet(2);
   }

   public void setCookieConfig(CookieConfigBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCookieConfig() != null && param0 != this.getCookieConfig()) {
         throw new BeanAlreadyExistsException(this.getCookieConfig() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CookieConfigBean _oldVal = this._CookieConfig;
         this._CookieConfig = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public CookieConfigBean createCookieConfig() {
      CookieConfigBeanImpl _val = new CookieConfigBeanImpl(this, -1);

      try {
         this.setCookieConfig(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String[] getTrackingModes() {
      return this._TrackingModes;
   }

   public boolean isTrackingModesInherited() {
      return false;
   }

   public boolean isTrackingModesSet() {
      return this._isSet(3);
   }

   public void addTrackingMode(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(3)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getTrackingModes(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setTrackingModes(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeTrackingMode(String param0) {
      String[] _old = this.getTrackingModes();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTrackingModes(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setTrackingModes(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      WebXmlValidator.validateTrackingModes(param0);
      String[] _oldVal = this._TrackingModes;
      this._TrackingModes = param0;
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._CookieConfig = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._SessionTimeout = 60;
               if (initOne) {
                  break;
               }
            case 3:
               this._TrackingModes = new String[0];
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
                  return 1;
               }
               break;
            case 13:
               if (s.equals("cookie-config")) {
                  return 2;
               }

               if (s.equals("tracking-mode")) {
                  return 3;
               }
               break;
            case 15:
               if (s.equals("session-timeout")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new CookieConfigBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "session-timeout";
            case 1:
               return "id";
            case 2:
               return "cookie-config";
            case 3:
               return "tracking-mode";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private SessionConfigBeanImpl bean;

      protected Helper(SessionConfigBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "SessionTimeout";
            case 1:
               return "Id";
            case 2:
               return "CookieConfig";
            case 3:
               return "TrackingModes";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CookieConfig")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 1;
         } else if (propName.equals("SessionTimeout")) {
            return 0;
         } else {
            return propName.equals("TrackingModes") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCookieConfig() != null) {
            iterators.add(new ArrayIterator(new CookieConfigBean[]{this.bean.getCookieConfig()}));
         }

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
            childValue = this.computeChildHashValue(this.bean.getCookieConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isSessionTimeoutSet()) {
               buf.append("SessionTimeout");
               buf.append(String.valueOf(this.bean.getSessionTimeout()));
            }

            if (this.bean.isTrackingModesSet()) {
               buf.append("TrackingModes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTrackingModes())));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            SessionConfigBeanImpl otherTyped = (SessionConfigBeanImpl)other;
            this.computeChildDiff("CookieConfig", this.bean.getCookieConfig(), otherTyped.getCookieConfig(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("SessionTimeout", this.bean.getSessionTimeout(), otherTyped.getSessionTimeout(), false);
            this.computeDiff("TrackingModes", this.bean.getTrackingModes(), otherTyped.getTrackingModes(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SessionConfigBeanImpl original = (SessionConfigBeanImpl)event.getSourceBean();
            SessionConfigBeanImpl proposed = (SessionConfigBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CookieConfig")) {
                  if (type == 2) {
                     original.setCookieConfig((CookieConfigBean)this.createCopy((AbstractDescriptorBean)proposed.getCookieConfig()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CookieConfig", (DescriptorBean)original.getCookieConfig());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("SessionTimeout")) {
                  original.setSessionTimeout(proposed.getSessionTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("TrackingModes")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addTrackingMode((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTrackingMode((String)update.getRemovedObject());
                  }

                  if (original.getTrackingModes() == null || original.getTrackingModes().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            SessionConfigBeanImpl copy = (SessionConfigBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CookieConfig")) && this.bean.isCookieConfigSet() && !copy._isSet(2)) {
               Object o = this.bean.getCookieConfig();
               copy.setCookieConfig((CookieConfigBean)null);
               copy.setCookieConfig(o == null ? null : (CookieConfigBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionTimeout")) && this.bean.isSessionTimeoutSet()) {
               copy.setSessionTimeout(this.bean.getSessionTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("TrackingModes")) && this.bean.isTrackingModesSet()) {
               Object o = this.bean.getTrackingModes();
               copy.setTrackingModes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getCookieConfig(), clazz, annotation);
      }
   }
}

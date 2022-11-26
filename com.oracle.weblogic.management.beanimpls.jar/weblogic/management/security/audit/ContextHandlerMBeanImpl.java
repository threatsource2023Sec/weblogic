package weblogic.management.security.audit;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.commo.AbstractCommoConfigurationBean;
import weblogic.management.commo.RequiredModelMBeanWrapper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ContextHandlerMBeanImpl extends AbstractCommoConfigurationBean implements ContextHandlerMBean, Serializable {
   private String[] _ActiveContextHandlerEntries;
   private String[] _SupportedContextHandlerEntries;
   private transient ContextHandlerImpl _customizer;
   private static SchemaHelper2 _schemaHelper;

   public ContextHandlerMBeanImpl() {
      try {
         this._customizer = new ContextHandlerImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ContextHandlerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ContextHandlerImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ContextHandlerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ContextHandlerImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String[] getSupportedContextHandlerEntries() {
      return this._SupportedContextHandlerEntries;
   }

   public boolean isSupportedContextHandlerEntriesInherited() {
      return false;
   }

   public boolean isSupportedContextHandlerEntriesSet() {
      return this._isSet(2);
   }

   public void setSupportedContextHandlerEntries(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      this._SupportedContextHandlerEntries = param0;
   }

   public String[] getActiveContextHandlerEntries() {
      return this._ActiveContextHandlerEntries;
   }

   public boolean isActiveContextHandlerEntriesInherited() {
      return false;
   }

   public boolean isActiveContextHandlerEntriesSet() {
      return this._isSet(3);
   }

   public void setActiveContextHandlerEntries(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ActiveContextHandlerEntries;
      this._ActiveContextHandlerEntries = param0;
      this._postSet(3, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();

      try {
         if (!this._customizer.validateActiveContextHandlerEntries(this.getActiveContextHandlerEntries())) {
            throw new IllegalArgumentException("The ContextHandler ActiveContextHandlerEntries attribute was set to an illegal value.");
         }
      } catch (InvalidAttributeValueException var2) {
         throw new IllegalArgumentException(var2.toString());
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._ActiveContextHandlerEntries = new String[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._SupportedContextHandlerEntries = new String[0];
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
      return "http://xmlns.oracle.com/weblogic/1.0/security.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/security";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String wls_getInterfaceClassName() {
      return "weblogic.management.security.audit.ContextHandlerMBean";
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 28:
               if (s.equals("active-context-handler-entry")) {
                  return 3;
               }
               break;
            case 31:
               if (s.equals("supported-context-handler-entry")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "supported-context-handler-entry";
            case 3:
               return "active-context-handler-entry";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractCommoConfigurationBean.Helper {
      private ContextHandlerMBeanImpl bean;

      protected Helper(ContextHandlerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "SupportedContextHandlerEntries";
            case 3:
               return "ActiveContextHandlerEntries";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ActiveContextHandlerEntries")) {
            return 3;
         } else {
            return propName.equals("SupportedContextHandlerEntries") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
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
            if (this.bean.isActiveContextHandlerEntriesSet()) {
               buf.append("ActiveContextHandlerEntries");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getActiveContextHandlerEntries())));
            }

            if (this.bean.isSupportedContextHandlerEntriesSet()) {
               buf.append("SupportedContextHandlerEntries");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSupportedContextHandlerEntries())));
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
            ContextHandlerMBeanImpl otherTyped = (ContextHandlerMBeanImpl)other;
            this.computeDiff("ActiveContextHandlerEntries", this.bean.getActiveContextHandlerEntries(), otherTyped.getActiveContextHandlerEntries(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ContextHandlerMBeanImpl original = (ContextHandlerMBeanImpl)event.getSourceBean();
            ContextHandlerMBeanImpl proposed = (ContextHandlerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ActiveContextHandlerEntries")) {
                  original.setActiveContextHandlerEntries(proposed.getActiveContextHandlerEntries());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (!prop.equals("SupportedContextHandlerEntries")) {
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
            ContextHandlerMBeanImpl copy = (ContextHandlerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ActiveContextHandlerEntries")) && this.bean.isActiveContextHandlerEntriesSet()) {
               Object o = this.bean.getActiveContextHandlerEntries();
               copy.setActiveContextHandlerEntries(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
      }
   }
}

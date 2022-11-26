package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class InterceptorMBeanImpl extends ConfigurationMBeanImpl implements InterceptorMBean, Serializable {
   private String[] _DependsOn;
   private String[] _InterceptedOperationNames;
   private String _InterceptedTargetKey;
   private String _InterceptorTypeName;
   private int _Priority;
   private Properties _Properties;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private InterceptorMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(InterceptorMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(InterceptorMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public InterceptorMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(InterceptorMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      InterceptorMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public InterceptorMBeanImpl() {
      this._initializeProperty(-1);
   }

   public InterceptorMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public InterceptorMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getInterceptorTypeName() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getInterceptorTypeName(), this) : this._InterceptorTypeName;
   }

   public boolean isInterceptorTypeNameInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isInterceptorTypeNameSet() {
      return this._isSet(10);
   }

   public void setInterceptorTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      String _oldVal = this._InterceptorTypeName;
      this._InterceptorTypeName = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         InterceptorMBeanImpl source = (InterceptorMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String getInterceptedTargetKey() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getInterceptedTargetKey(), this) : this._InterceptedTargetKey;
   }

   public boolean isInterceptedTargetKeyInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isInterceptedTargetKeySet() {
      return this._isSet(11);
   }

   public void setInterceptedTargetKey(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      String _oldVal = this._InterceptedTargetKey;
      this._InterceptedTargetKey = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         InterceptorMBeanImpl source = (InterceptorMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getInterceptedOperationNames() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getInterceptedOperationNames() : this._InterceptedOperationNames;
   }

   public boolean isInterceptedOperationNamesInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isInterceptedOperationNamesSet() {
      return this._isSet(12);
   }

   public void setInterceptedOperationNames(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      String[] _oldVal = this._InterceptedOperationNames;
      this._InterceptedOperationNames = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         InterceptorMBeanImpl source = (InterceptorMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public int getPriority() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getPriority() : this._Priority;
   }

   public boolean isPriorityInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isPrioritySet() {
      return this._isSet(13);
   }

   public void setPriority(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      int _oldVal = this._Priority;
      this._Priority = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         InterceptorMBeanImpl source = (InterceptorMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getDependsOn() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().getDependsOn() : this._DependsOn;
   }

   public boolean isDependsOnInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isDependsOnSet() {
      return this._isSet(14);
   }

   public void setDependsOn(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      String[] _oldVal = this._DependsOn;
      this._DependsOn = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         InterceptorMBeanImpl source = (InterceptorMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public Properties getProperties() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getProperties() : this._Properties;
   }

   public String getPropertiesAsString() {
      return StringHelper.objectToString(this.getProperties());
   }

   public boolean isPropertiesInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isPropertiesSet() {
      return this._isSet(15);
   }

   public void setPropertiesAsString(String param0) {
      try {
         this.setProperties(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setProperties(Properties param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      Properties _oldVal = this._Properties;
      this._Properties = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         InterceptorMBeanImpl source = (InterceptorMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
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
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._DependsOn = new String[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._InterceptedOperationNames = new String[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._InterceptedTargetKey = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._InterceptorTypeName = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._Priority = 1073741823;
               if (initOne) {
                  break;
               }
            case 15:
               this._Properties = null;
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
      return "Interceptor";
   }

   public void putValue(String name, Object v) {
      String[] oldVal;
      if (name.equals("DependsOn")) {
         oldVal = this._DependsOn;
         this._DependsOn = (String[])((String[])v);
         this._postSet(14, oldVal, this._DependsOn);
      } else if (name.equals("InterceptedOperationNames")) {
         oldVal = this._InterceptedOperationNames;
         this._InterceptedOperationNames = (String[])((String[])v);
         this._postSet(12, oldVal, this._InterceptedOperationNames);
      } else {
         String oldVal;
         if (name.equals("InterceptedTargetKey")) {
            oldVal = this._InterceptedTargetKey;
            this._InterceptedTargetKey = (String)v;
            this._postSet(11, oldVal, this._InterceptedTargetKey);
         } else if (name.equals("InterceptorTypeName")) {
            oldVal = this._InterceptorTypeName;
            this._InterceptorTypeName = (String)v;
            this._postSet(10, oldVal, this._InterceptorTypeName);
         } else if (name.equals("Priority")) {
            int oldVal = this._Priority;
            this._Priority = (Integer)v;
            this._postSet(13, oldVal, this._Priority);
         } else if (name.equals("Properties")) {
            Properties oldVal = this._Properties;
            this._Properties = (Properties)v;
            this._postSet(15, oldVal, this._Properties);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DependsOn")) {
         return this._DependsOn;
      } else if (name.equals("InterceptedOperationNames")) {
         return this._InterceptedOperationNames;
      } else if (name.equals("InterceptedTargetKey")) {
         return this._InterceptedTargetKey;
      } else if (name.equals("InterceptorTypeName")) {
         return this._InterceptorTypeName;
      } else if (name.equals("Priority")) {
         return new Integer(this._Priority);
      } else {
         return name.equals("Properties") ? this._Properties : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("priority")) {
                  return 13;
               }
               break;
            case 10:
               if (s.equals("depends-on")) {
                  return 14;
               }

               if (s.equals("properties")) {
                  return 15;
               }
               break;
            case 21:
               if (s.equals("interceptor-type-name")) {
                  return 10;
               }
               break;
            case 22:
               if (s.equals("intercepted-target-key")) {
                  return 11;
               }
               break;
            case 26:
               if (s.equals("intercepted-operation-name")) {
                  return 12;
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
            case 10:
               return "interceptor-type-name";
            case 11:
               return "intercepted-target-key";
            case 12:
               return "intercepted-operation-name";
            case 13:
               return "priority";
            case 14:
               return "depends-on";
            case 15:
               return "properties";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 12:
               return true;
            case 14:
               return true;
            default:
               return super.isArray(propIndex);
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
      private InterceptorMBeanImpl bean;

      protected Helper(InterceptorMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "InterceptorTypeName";
            case 11:
               return "InterceptedTargetKey";
            case 12:
               return "InterceptedOperationNames";
            case 13:
               return "Priority";
            case 14:
               return "DependsOn";
            case 15:
               return "Properties";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DependsOn")) {
            return 14;
         } else if (propName.equals("InterceptedOperationNames")) {
            return 12;
         } else if (propName.equals("InterceptedTargetKey")) {
            return 11;
         } else if (propName.equals("InterceptorTypeName")) {
            return 10;
         } else if (propName.equals("Priority")) {
            return 13;
         } else {
            return propName.equals("Properties") ? 15 : super.getPropertyIndex(propName);
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
            if (this.bean.isDependsOnSet()) {
               buf.append("DependsOn");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDependsOn())));
            }

            if (this.bean.isInterceptedOperationNamesSet()) {
               buf.append("InterceptedOperationNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getInterceptedOperationNames())));
            }

            if (this.bean.isInterceptedTargetKeySet()) {
               buf.append("InterceptedTargetKey");
               buf.append(String.valueOf(this.bean.getInterceptedTargetKey()));
            }

            if (this.bean.isInterceptorTypeNameSet()) {
               buf.append("InterceptorTypeName");
               buf.append(String.valueOf(this.bean.getInterceptorTypeName()));
            }

            if (this.bean.isPrioritySet()) {
               buf.append("Priority");
               buf.append(String.valueOf(this.bean.getPriority()));
            }

            if (this.bean.isPropertiesSet()) {
               buf.append("Properties");
               buf.append(String.valueOf(this.bean.getProperties()));
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
            InterceptorMBeanImpl otherTyped = (InterceptorMBeanImpl)other;
            this.computeDiff("DependsOn", this.bean.getDependsOn(), otherTyped.getDependsOn(), false);
            this.computeDiff("InterceptedOperationNames", this.bean.getInterceptedOperationNames(), otherTyped.getInterceptedOperationNames(), true);
            this.computeDiff("InterceptedTargetKey", this.bean.getInterceptedTargetKey(), otherTyped.getInterceptedTargetKey(), true);
            this.computeDiff("InterceptorTypeName", this.bean.getInterceptorTypeName(), otherTyped.getInterceptorTypeName(), true);
            this.computeDiff("Priority", this.bean.getPriority(), otherTyped.getPriority(), true);
            this.computeDiff("Properties", this.bean.getProperties(), otherTyped.getProperties(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            InterceptorMBeanImpl original = (InterceptorMBeanImpl)event.getSourceBean();
            InterceptorMBeanImpl proposed = (InterceptorMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DependsOn")) {
                  original.setDependsOn(proposed.getDependsOn());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("InterceptedOperationNames")) {
                  original.setInterceptedOperationNames(proposed.getInterceptedOperationNames());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("InterceptedTargetKey")) {
                  original.setInterceptedTargetKey(proposed.getInterceptedTargetKey());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("InterceptorTypeName")) {
                  original.setInterceptorTypeName(proposed.getInterceptorTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("Priority")) {
                  original.setPriority(proposed.getPriority());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("Properties")) {
                  original.setProperties(proposed.getProperties() == null ? null : (Properties)proposed.getProperties().clone());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
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
            InterceptorMBeanImpl copy = (InterceptorMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("DependsOn")) && this.bean.isDependsOnSet()) {
               o = this.bean.getDependsOn();
               copy.setDependsOn(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("InterceptedOperationNames")) && this.bean.isInterceptedOperationNamesSet()) {
               o = this.bean.getInterceptedOperationNames();
               copy.setInterceptedOperationNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("InterceptedTargetKey")) && this.bean.isInterceptedTargetKeySet()) {
               copy.setInterceptedTargetKey(this.bean.getInterceptedTargetKey());
            }

            if ((excludeProps == null || !excludeProps.contains("InterceptorTypeName")) && this.bean.isInterceptorTypeNameSet()) {
               copy.setInterceptorTypeName(this.bean.getInterceptorTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("Priority")) && this.bean.isPrioritySet()) {
               copy.setPriority(this.bean.getPriority());
            }

            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet()) {
               copy.setProperties(this.bean.getProperties());
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

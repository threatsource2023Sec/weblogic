package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ScriptInterceptorMBeanImpl extends InterceptorMBeanImpl implements ScriptInterceptorMBean, Serializable {
   private String[] _ApplicableClusterNames;
   private String _InterceptorTypeName;
   private PostProcessorScriptMBean _PostProcessor;
   private PreProcessorScriptMBean _PreProcessor;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ScriptInterceptorMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ScriptInterceptorMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ScriptInterceptorMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ScriptInterceptorMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ScriptInterceptorMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ScriptInterceptorMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._PostProcessor instanceof PostProcessorScriptMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getPostProcessor() != null) {
            this._getReferenceManager().unregisterBean((PostProcessorScriptMBeanImpl)oldDelegate.getPostProcessor());
         }

         if (delegate != null && delegate.getPostProcessor() != null) {
            this._getReferenceManager().registerBean((PostProcessorScriptMBeanImpl)delegate.getPostProcessor(), false);
         }

         ((PostProcessorScriptMBeanImpl)this._PostProcessor)._setDelegateBean((PostProcessorScriptMBeanImpl)((PostProcessorScriptMBeanImpl)(delegate == null ? null : delegate.getPostProcessor())));
      }

      if (this._PreProcessor instanceof PreProcessorScriptMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getPreProcessor() != null) {
            this._getReferenceManager().unregisterBean((PreProcessorScriptMBeanImpl)oldDelegate.getPreProcessor());
         }

         if (delegate != null && delegate.getPreProcessor() != null) {
            this._getReferenceManager().registerBean((PreProcessorScriptMBeanImpl)delegate.getPreProcessor(), false);
         }

         ((PreProcessorScriptMBeanImpl)this._PreProcessor)._setDelegateBean((PreProcessorScriptMBeanImpl)((PreProcessorScriptMBeanImpl)(delegate == null ? null : delegate.getPreProcessor())));
      }

   }

   public ScriptInterceptorMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ScriptInterceptorMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ScriptInterceptorMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String[] getApplicableClusterNames() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getApplicableClusterNames() : this._ApplicableClusterNames;
   }

   public boolean isApplicableClusterNamesInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isApplicableClusterNamesSet() {
      return this._isSet(16);
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
         ScriptInterceptorMBeanImpl source = (ScriptInterceptorMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public void setApplicableClusterNames(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      String[] _oldVal = this._ApplicableClusterNames;
      this._ApplicableClusterNames = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ScriptInterceptorMBeanImpl source = (ScriptInterceptorMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public PreProcessorScriptMBean getPreProcessor() {
      return this._PreProcessor;
   }

   public boolean isPreProcessorInherited() {
      return false;
   }

   public boolean isPreProcessorSet() {
      return this._isSet(17) || this._isAnythingSet((AbstractDescriptorBean)this.getPreProcessor());
   }

   public void setPreProcessor(PreProcessorScriptMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 17)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(17);
      PreProcessorScriptMBean _oldVal = this._PreProcessor;
      this._PreProcessor = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ScriptInterceptorMBeanImpl source = (ScriptInterceptorMBeanImpl)var5.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public PostProcessorScriptMBean getPostProcessor() {
      return this._PostProcessor;
   }

   public boolean isPostProcessorInherited() {
      return false;
   }

   public boolean isPostProcessorSet() {
      return this._isSet(18) || this._isAnythingSet((AbstractDescriptorBean)this.getPostProcessor());
   }

   public void setPostProcessor(PostProcessorScriptMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 18)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(18);
      PostProcessorScriptMBean _oldVal = this._PostProcessor;
      this._PostProcessor = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ScriptInterceptorMBeanImpl source = (ScriptInterceptorMBeanImpl)var5.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      ScriptInterceptorValidator.validateInterceptor(this);
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
      return super._isAnythingSet() || this.isPostProcessorSet() || this.isPreProcessorSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 16;
      }

      try {
         switch (idx) {
            case 16:
               this._ApplicableClusterNames = new String[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._InterceptorTypeName = "ScriptInterceptor";
               if (initOne) {
                  break;
               }
            case 18:
               this._PostProcessor = new PostProcessorScriptMBeanImpl(this, 18);
               this._postCreate((AbstractDescriptorBean)this._PostProcessor);
               if (initOne) {
                  break;
               }
            case 17:
               this._PreProcessor = new PreProcessorScriptMBeanImpl(this, 17);
               this._postCreate((AbstractDescriptorBean)this._PreProcessor);
               if (initOne) {
                  break;
               }
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
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
      return "ScriptInterceptor";
   }

   public void putValue(String name, Object v) {
      if (name.equals("ApplicableClusterNames")) {
         String[] oldVal = this._ApplicableClusterNames;
         this._ApplicableClusterNames = (String[])((String[])v);
         this._postSet(16, oldVal, this._ApplicableClusterNames);
      } else if (name.equals("InterceptorTypeName")) {
         String oldVal = this._InterceptorTypeName;
         this._InterceptorTypeName = (String)v;
         this._postSet(10, oldVal, this._InterceptorTypeName);
      } else if (name.equals("PostProcessor")) {
         PostProcessorScriptMBean oldVal = this._PostProcessor;
         this._PostProcessor = (PostProcessorScriptMBean)v;
         this._postSet(18, oldVal, this._PostProcessor);
      } else if (name.equals("PreProcessor")) {
         PreProcessorScriptMBean oldVal = this._PreProcessor;
         this._PreProcessor = (PreProcessorScriptMBean)v;
         this._postSet(17, oldVal, this._PreProcessor);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ApplicableClusterNames")) {
         return this._ApplicableClusterNames;
      } else if (name.equals("InterceptorTypeName")) {
         return this._InterceptorTypeName;
      } else if (name.equals("PostProcessor")) {
         return this._PostProcessor;
      } else {
         return name.equals("PreProcessor") ? this._PreProcessor : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends InterceptorMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 13:
               if (s.equals("pre-processor")) {
                  return 17;
               }
               break;
            case 14:
               if (s.equals("post-processor")) {
                  return 18;
               }
               break;
            case 21:
               if (s.equals("interceptor-type-name")) {
                  return 10;
               }
               break;
            case 23:
               if (s.equals("applicable-cluster-name")) {
                  return 16;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 17:
               return new PreProcessorScriptMBeanImpl.SchemaHelper2();
            case 18:
               return new PostProcessorScriptMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "interceptor-type-name";
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return super.getElementName(propIndex);
            case 16:
               return "applicable-cluster-name";
            case 17:
               return "pre-processor";
            case 18:
               return "post-processor";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
            case 11:
            case 13:
            case 15:
            default:
               return super.isArray(propIndex);
            case 12:
               return true;
            case 14:
               return true;
            case 16:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 17:
               return true;
            case 18:
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

   protected static class Helper extends InterceptorMBeanImpl.Helper {
      private ScriptInterceptorMBeanImpl bean;

      protected Helper(ScriptInterceptorMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "InterceptorTypeName";
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return super.getPropertyName(propIndex);
            case 16:
               return "ApplicableClusterNames";
            case 17:
               return "PreProcessor";
            case 18:
               return "PostProcessor";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ApplicableClusterNames")) {
            return 16;
         } else if (propName.equals("InterceptorTypeName")) {
            return 10;
         } else if (propName.equals("PostProcessor")) {
            return 18;
         } else {
            return propName.equals("PreProcessor") ? 17 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getPostProcessor() != null) {
            iterators.add(new ArrayIterator(new PostProcessorScriptMBean[]{this.bean.getPostProcessor()}));
         }

         if (this.bean.getPreProcessor() != null) {
            iterators.add(new ArrayIterator(new PreProcessorScriptMBean[]{this.bean.getPreProcessor()}));
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
            if (this.bean.isApplicableClusterNamesSet()) {
               buf.append("ApplicableClusterNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getApplicableClusterNames())));
            }

            if (this.bean.isInterceptorTypeNameSet()) {
               buf.append("InterceptorTypeName");
               buf.append(String.valueOf(this.bean.getInterceptorTypeName()));
            }

            childValue = this.computeChildHashValue(this.bean.getPostProcessor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPreProcessor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            ScriptInterceptorMBeanImpl otherTyped = (ScriptInterceptorMBeanImpl)other;
            this.computeDiff("ApplicableClusterNames", this.bean.getApplicableClusterNames(), otherTyped.getApplicableClusterNames(), true);
            this.computeDiff("InterceptorTypeName", this.bean.getInterceptorTypeName(), otherTyped.getInterceptorTypeName(), false);
            this.computeSubDiff("PostProcessor", this.bean.getPostProcessor(), otherTyped.getPostProcessor());
            this.computeSubDiff("PreProcessor", this.bean.getPreProcessor(), otherTyped.getPreProcessor());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ScriptInterceptorMBeanImpl original = (ScriptInterceptorMBeanImpl)event.getSourceBean();
            ScriptInterceptorMBeanImpl proposed = (ScriptInterceptorMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ApplicableClusterNames")) {
                  original.setApplicableClusterNames(proposed.getApplicableClusterNames());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("InterceptorTypeName")) {
                  original.setInterceptorTypeName(proposed.getInterceptorTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("PostProcessor")) {
                  if (type == 2) {
                     original.setPostProcessor((PostProcessorScriptMBean)this.createCopy((AbstractDescriptorBean)proposed.getPostProcessor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("PostProcessor", original.getPostProcessor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("PreProcessor")) {
                  if (type == 2) {
                     original.setPreProcessor((PreProcessorScriptMBean)this.createCopy((AbstractDescriptorBean)proposed.getPreProcessor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("PreProcessor", original.getPreProcessor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 17);
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
            ScriptInterceptorMBeanImpl copy = (ScriptInterceptorMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ApplicableClusterNames")) && this.bean.isApplicableClusterNamesSet()) {
               Object o = this.bean.getApplicableClusterNames();
               copy.setApplicableClusterNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("InterceptorTypeName")) && this.bean.isInterceptorTypeNameSet()) {
               copy.setInterceptorTypeName(this.bean.getInterceptorTypeName());
            }

            if ((excludeProps == null || !excludeProps.contains("PostProcessor")) && this.bean.isPostProcessorSet() && !copy._isSet(18)) {
               Object o = this.bean.getPostProcessor();
               copy.setPostProcessor((PostProcessorScriptMBean)null);
               copy.setPostProcessor(o == null ? null : (PostProcessorScriptMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PreProcessor")) && this.bean.isPreProcessorSet() && !copy._isSet(17)) {
               Object o = this.bean.getPreProcessor();
               copy.setPreProcessor((PreProcessorScriptMBean)null);
               copy.setPreProcessor(o == null ? null : (PreProcessorScriptMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getPostProcessor(), clazz, annotation);
         this.inferSubTree(this.bean.getPreProcessor(), clazz, annotation);
      }
   }
}

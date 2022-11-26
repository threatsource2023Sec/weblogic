package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.CompoundKey;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.servlet.internal.WebXmlValidator;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class FilterMappingBeanImpl extends AbstractDescriptorBean implements FilterMappingBean, Serializable {
   private String[] _Dispatchers;
   private String _FilterName;
   private String _Id;
   private String[] _ServletNames;
   private String[] _UrlPatterns;
   private static SchemaHelper2 _schemaHelper;

   public FilterMappingBeanImpl() {
      this._initializeProperty(-1);
   }

   public FilterMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public FilterMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getFilterName() {
      return this._FilterName;
   }

   public boolean isFilterNameInherited() {
      return false;
   }

   public boolean isFilterNameSet() {
      return this._isSet(0);
   }

   public void setFilterName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FilterName;
      this._FilterName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String[] getUrlPatterns() {
      return this._UrlPatterns;
   }

   public boolean isUrlPatternsInherited() {
      return false;
   }

   public boolean isUrlPatternsSet() {
      return this._isSet(1);
   }

   public void setUrlPatterns(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      WebXmlValidator.validateURLPatterns(param0);
      String[] _oldVal = this._UrlPatterns;
      this._UrlPatterns = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String[] getServletNames() {
      return this._ServletNames;
   }

   public boolean isServletNamesInherited() {
      return false;
   }

   public boolean isServletNamesSet() {
      return this._isSet(2);
   }

   public void setServletNames(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ServletNames;
      this._ServletNames = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String[] getDispatchers() {
      return this._Dispatchers;
   }

   public boolean isDispatchersInherited() {
      return false;
   }

   public boolean isDispatchersSet() {
      return this._isSet(3);
   }

   public void addDispatcher(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(3)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDispatchers(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDispatchers(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDispatcher(String param0) {
      String[] _old = this.getDispatchers();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDispatchers(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDispatchers(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      WebXmlValidator.validateDispatchers(param0);
      String[] _oldVal = this._Dispatchers;
      this._Dispatchers = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(4);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(4, _oldVal, param0);
   }

   public Object _getKey() {
      return new CompoundKey(new Object[]{this.getFilterName(), new CompoundKey(this.getServletNames()), new CompoundKey(this.getUrlPatterns())});
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
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
               this._Dispatchers = new String[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._FilterName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ServletNames = new String[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._UrlPatterns = new String[0];
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
                  return 4;
               }
               break;
            case 10:
               if (s.equals("dispatcher")) {
                  return 3;
               }
               break;
            case 11:
               if (s.equals("filter-name")) {
                  return 0;
               }

               if (s.equals("url-pattern")) {
                  return 1;
               }
               break;
            case 12:
               if (s.equals("servlet-name")) {
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
            case 0:
               return "filter-name";
            case 1:
               return "url-pattern";
            case 2:
               return "servlet-name";
            case 3:
               return "dispatcher";
            case 4:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isMergeRuleIgnoreSourceDefined(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isMergeRuleIgnoreSourceDefined(propIndex);
         }
      }

      public boolean isMergeRuleIgnoreTargetDefined(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isMergeRuleIgnoreTargetDefined(propIndex);
         }
      }

      public boolean isKeyComponent(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isKeyComponent(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("filter-name");
         indices.add("servlet-name");
         indices.add("url-pattern");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private FilterMappingBeanImpl bean;

      protected Helper(FilterMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "FilterName";
            case 1:
               return "UrlPatterns";
            case 2:
               return "ServletNames";
            case 3:
               return "Dispatchers";
            case 4:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Dispatchers")) {
            return 3;
         } else if (propName.equals("FilterName")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 4;
         } else if (propName.equals("ServletNames")) {
            return 2;
         } else {
            return propName.equals("UrlPatterns") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isDispatchersSet()) {
               buf.append("Dispatchers");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDispatchers())));
            }

            if (this.bean.isFilterNameSet()) {
               buf.append("FilterName");
               buf.append(String.valueOf(this.bean.getFilterName()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isServletNamesSet()) {
               buf.append("ServletNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getServletNames())));
            }

            if (this.bean.isUrlPatternsSet()) {
               buf.append("UrlPatterns");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getUrlPatterns())));
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
            FilterMappingBeanImpl otherTyped = (FilterMappingBeanImpl)other;
            this.computeDiff("Dispatchers", this.bean.getDispatchers(), otherTyped.getDispatchers(), false);
            this.computeDiff("FilterName", this.bean.getFilterName(), otherTyped.getFilterName(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("ServletNames", this.bean.getServletNames(), otherTyped.getServletNames(), false);
            this.computeDiff("UrlPatterns", this.bean.getUrlPatterns(), otherTyped.getUrlPatterns(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            FilterMappingBeanImpl original = (FilterMappingBeanImpl)event.getSourceBean();
            FilterMappingBeanImpl proposed = (FilterMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Dispatchers")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDispatcher((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDispatcher((String)update.getRemovedObject());
                  }

                  if (original.getDispatchers() == null || original.getDispatchers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("FilterName")) {
                  original.setFilterName(proposed.getFilterName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ServletNames")) {
                  original.setServletNames(proposed.getServletNames());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("UrlPatterns")) {
                  original.setUrlPatterns(proposed.getUrlPatterns());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            FilterMappingBeanImpl copy = (FilterMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Dispatchers")) && this.bean.isDispatchersSet()) {
               o = this.bean.getDispatchers();
               copy.setDispatchers(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("FilterName")) && this.bean.isFilterNameSet()) {
               copy.setFilterName(this.bean.getFilterName());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("ServletNames")) && this.bean.isServletNamesSet()) {
               o = this.bean.getServletNames();
               copy.setServletNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UrlPatterns")) && this.bean.isUrlPatternsSet()) {
               o = this.bean.getUrlPatterns();
               copy.setUrlPatterns(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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

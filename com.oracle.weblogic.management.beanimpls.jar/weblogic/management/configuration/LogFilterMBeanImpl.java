package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.LogFilter;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class LogFilterMBeanImpl extends ConfigurationMBeanImpl implements LogFilterMBean, Serializable {
   private boolean _DynamicallyCreated;
   private String _FilterExpression;
   private String _Name;
   private Object _Query;
   private int _SeverityLevel;
   private String[] _SubsystemNames;
   private String[] _Tags;
   private String[] _UserIds;
   private transient LogFilter _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private LogFilterMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(LogFilterMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(LogFilterMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public LogFilterMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(LogFilterMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      LogFilterMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public LogFilterMBeanImpl() {
      try {
         this._customizer = new LogFilter(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public LogFilterMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new LogFilter(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public LogFilterMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new LogFilter(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getName() {
      if (!this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2)) {
         return this._performMacroSubstitution(this._getDelegateBean().getName(), this);
      } else {
         if (!this._isSet(2)) {
            try {
               return ((ConfigurationMBean)this.getParent()).getName();
            } catch (NullPointerException var2) {
            }
         }

         return this._customizer.getName();
      }
   }

   public int getSeverityLevel() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getSeverityLevel() : this._customizer.getSeverityLevel();
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isSeverityLevelInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isSeverityLevelSet() {
      return this._isSet(10);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFilterMBeanImpl source = (LogFilterMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setSeverityLevel(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      int[] _set = new int[]{64, 16, 8, 32, 4, 2, 1};
      param0 = LegalChecks.checkInEnum("SeverityLevel", param0, _set);
      boolean wasSet = this._isSet(10);
      int _oldVal = this.getSeverityLevel();
      this._customizer.setSeverityLevel(param0);
      this._postSet(10, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         LogFilterMBeanImpl source = (LogFilterMBeanImpl)var5.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getSubsystemNames() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._getDelegateBean().getSubsystemNames() : this._customizer.getSubsystemNames();
   }

   public boolean isSubsystemNamesInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isSubsystemNamesSet() {
      return this._isSet(11);
   }

   public void setSubsystemNames(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      String[] _oldVal = this.getSubsystemNames();

      try {
         this._customizer.setSubsystemNames(param0);
      } catch (InvalidAttributeValueException var6) {
         throw new UndeclaredThrowableException(var6);
      }

      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFilterMBeanImpl source = (LogFilterMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getUserIds() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getUserIds() : this._customizer.getUserIds();
   }

   public boolean isUserIdsInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isUserIdsSet() {
      return this._isSet(12);
   }

   public void setUserIds(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      String[] _oldVal = this.getUserIds();

      try {
         this._customizer.setUserIds(param0);
      } catch (InvalidAttributeValueException var6) {
         throw new UndeclaredThrowableException(var6);
      }

      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFilterMBeanImpl source = (LogFilterMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getFilterExpression() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getFilterExpression(), this) : this._customizer.getFilterExpression();
   }

   public boolean isFilterExpressionInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isFilterExpressionSet() {
      return this._isSet(13);
   }

   public void setFilterExpression(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      String _oldVal = this.getFilterExpression();
      this._customizer.setFilterExpression(param0);
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFilterMBeanImpl source = (LogFilterMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public Object getQuery() {
      return this._customizer.getQuery();
   }

   public boolean isQueryInherited() {
      return false;
   }

   public boolean isQuerySet() {
      return this._isSet(14);
   }

   public void setQuery(Object param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._Query = param0;
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(9);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFilterMBeanImpl source = (LogFilterMBeanImpl)var4.next();
         if (source != null && !source._isSet(9)) {
            source._postSetFirePropertyChange(9, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public Object _getKey() {
      return this.getName();
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
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
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
         idx = 13;
      }

      try {
         switch (idx) {
            case 13:
               this._customizer.setFilterExpression((String)null);
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 14:
               this._Query = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._customizer.setSeverityLevel(16);
               if (initOne) {
                  break;
               }
            case 11:
               this._customizer.setSubsystemNames(new String[0]);
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 12:
               this._customizer.setUserIds(new String[0]);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
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
      return "LogFilter";
   }

   public void putValue(String name, Object v) {
      if (name.equals("DynamicallyCreated")) {
         boolean oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else {
         String oldVal;
         if (name.equals("FilterExpression")) {
            oldVal = this._FilterExpression;
            this._FilterExpression = (String)v;
            this._postSet(13, oldVal, this._FilterExpression);
         } else if (name.equals("Name")) {
            oldVal = this._Name;
            this._Name = (String)v;
            this._postSet(2, oldVal, this._Name);
         } else if (name.equals("Query")) {
            Object oldVal = this._Query;
            this._Query = v;
            this._postSet(14, oldVal, this._Query);
         } else if (name.equals("SeverityLevel")) {
            int oldVal = this._SeverityLevel;
            this._SeverityLevel = (Integer)v;
            this._postSet(10, oldVal, this._SeverityLevel);
         } else {
            String[] oldVal;
            if (name.equals("SubsystemNames")) {
               oldVal = this._SubsystemNames;
               this._SubsystemNames = (String[])((String[])v);
               this._postSet(11, oldVal, this._SubsystemNames);
            } else if (name.equals("Tags")) {
               oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
            } else if (name.equals("UserIds")) {
               oldVal = this._UserIds;
               this._UserIds = (String[])((String[])v);
               this._postSet(12, oldVal, this._UserIds);
            } else if (name.equals("customizer")) {
               LogFilter oldVal = this._customizer;
               this._customizer = (LogFilter)v;
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("FilterExpression")) {
         return this._FilterExpression;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Query")) {
         return this._Query;
      } else if (name.equals("SeverityLevel")) {
         return new Integer(this._SeverityLevel);
      } else if (name.equals("SubsystemNames")) {
         return this._SubsystemNames;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("UserIds")) {
         return this._UserIds;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
               break;
            case 5:
               if (s.equals("query")) {
                  return 14;
               }
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 18:
            default:
               break;
            case 7:
               if (s.equals("user-id")) {
                  return 12;
               }
               break;
            case 14:
               if (s.equals("severity-level")) {
                  return 10;
               }

               if (s.equals("subsystem-name")) {
                  return 11;
               }
               break;
            case 17:
               if (s.equals("filter-expression")) {
                  return 13;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
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
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "severity-level";
            case 11:
               return "subsystem-name";
            case 12:
               return "user-id";
            case 13:
               return "filter-expression";
            case 14:
               return "query";
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

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
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
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private LogFilterMBeanImpl bean;

      protected Helper(LogFilterMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "SeverityLevel";
            case 11:
               return "SubsystemNames";
            case 12:
               return "UserIds";
            case 13:
               return "FilterExpression";
            case 14:
               return "Query";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FilterExpression")) {
            return 13;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Query")) {
            return 14;
         } else if (propName.equals("SeverityLevel")) {
            return 10;
         } else if (propName.equals("SubsystemNames")) {
            return 11;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("UserIds")) {
            return 12;
         } else {
            return propName.equals("DynamicallyCreated") ? 7 : super.getPropertyIndex(propName);
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
            if (this.bean.isFilterExpressionSet()) {
               buf.append("FilterExpression");
               buf.append(String.valueOf(this.bean.getFilterExpression()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isQuerySet()) {
               buf.append("Query");
               buf.append(String.valueOf(this.bean.getQuery()));
            }

            if (this.bean.isSeverityLevelSet()) {
               buf.append("SeverityLevel");
               buf.append(String.valueOf(this.bean.getSeverityLevel()));
            }

            if (this.bean.isSubsystemNamesSet()) {
               buf.append("SubsystemNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSubsystemNames())));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isUserIdsSet()) {
               buf.append("UserIds");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getUserIds())));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
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
            LogFilterMBeanImpl otherTyped = (LogFilterMBeanImpl)other;
            this.computeDiff("FilterExpression", this.bean.getFilterExpression(), otherTyped.getFilterExpression(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("SeverityLevel", this.bean.getSeverityLevel(), otherTyped.getSeverityLevel(), true);
            this.computeDiff("SubsystemNames", this.bean.getSubsystemNames(), otherTyped.getSubsystemNames(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("UserIds", this.bean.getUserIds(), otherTyped.getUserIds(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LogFilterMBeanImpl original = (LogFilterMBeanImpl)event.getSourceBean();
            LogFilterMBeanImpl proposed = (LogFilterMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FilterExpression")) {
                  original.setFilterExpression(proposed.getFilterExpression());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (!prop.equals("Query")) {
                  if (prop.equals("SeverityLevel")) {
                     original.setSeverityLevel(proposed.getSeverityLevel());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (prop.equals("SubsystemNames")) {
                     original.setSubsystemNames(proposed.getSubsystemNames());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("Tags")) {
                     if (type == 2) {
                        update.resetAddedObject(update.getAddedObject());
                        original.addTag((String)update.getAddedObject());
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeTag((String)update.getRemovedObject());
                     }

                     if (original.getTags() == null || original.getTags().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 9);
                     }
                  } else if (prop.equals("UserIds")) {
                     original.setUserIds(proposed.getUserIds());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (!prop.equals("DynamicallyCreated")) {
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
            LogFilterMBeanImpl copy = (LogFilterMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FilterExpression")) && this.bean.isFilterExpressionSet()) {
               copy.setFilterExpression(this.bean.getFilterExpression());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("SeverityLevel")) && this.bean.isSeverityLevelSet()) {
               copy.setSeverityLevel(this.bean.getSeverityLevel());
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("SubsystemNames")) && this.bean.isSubsystemNamesSet()) {
               o = this.bean.getSubsystemNames();
               copy.setSubsystemNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UserIds")) && this.bean.isUserIdsSet()) {
               o = this.bean.getUserIds();
               copy.setUserIds(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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

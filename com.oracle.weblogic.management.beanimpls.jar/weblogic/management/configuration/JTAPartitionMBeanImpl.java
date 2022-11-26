package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.JTAPartitionMBeanCustomizer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class JTAPartitionMBeanImpl extends ConfigurationMBeanImpl implements JTAPartitionMBean, Serializable {
   private boolean _DynamicallyCreated;
   private String _Name;
   private String[] _Tags;
   private int _TimeoutSeconds;
   private transient JTAPartitionMBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public JTAPartitionMBeanImpl() {
      try {
         this._customizer = new JTAPartitionMBeanCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public JTAPartitionMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new JTAPartitionMBeanCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public JTAPartitionMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new JTAPartitionMBeanCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getName() {
      if (!this._isSet(2)) {
         try {
            return ((ConfigurationMBean)this.getParent()).getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._customizer.getName();
   }

   public int getTimeoutSeconds() {
      if (!this._isSet(10)) {
         try {
            return ((DomainMBean)this.getParent().getParent()).getJTA().getTimeoutSeconds();
         } catch (NullPointerException var2) {
         }
      }

      return this._TimeoutSeconds;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isTimeoutSecondsInherited() {
      return false;
   }

   public boolean isTimeoutSecondsSet() {
      return this._isSet(10);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public void setTimeoutSeconds(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("TimeoutSeconds", (long)param0, 1L, 2147483647L);
      int _oldVal = this._TimeoutSeconds;
      this._TimeoutSeconds = param0;
      this._postSet(10, _oldVal, param0);
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
      this._DynamicallyCreated = param0;
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 10:
               this._TimeoutSeconds = 0;
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
      return "JTAPartition";
   }

   public void putValue(String name, Object v) {
      if (name.equals("DynamicallyCreated")) {
         boolean oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else if (name.equals("Name")) {
         String oldVal = this._Name;
         this._Name = (String)v;
         this._postSet(2, oldVal, this._Name);
      } else if (name.equals("Tags")) {
         String[] oldVal = this._Tags;
         this._Tags = (String[])((String[])v);
         this._postSet(9, oldVal, this._Tags);
      } else if (name.equals("TimeoutSeconds")) {
         int oldVal = this._TimeoutSeconds;
         this._TimeoutSeconds = (Integer)v;
         this._postSet(10, oldVal, this._TimeoutSeconds);
      } else if (name.equals("customizer")) {
         JTAPartitionMBeanCustomizer oldVal = this._customizer;
         this._customizer = (JTAPartitionMBeanCustomizer)v;
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("TimeoutSeconds")) {
         return new Integer(this._TimeoutSeconds);
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
            case 15:
               if (s.equals("timeout-seconds")) {
                  return 10;
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
               return "timeout-seconds";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            default:
               return super.isConfigurable(propIndex);
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
      private JTAPartitionMBeanImpl bean;

      protected Helper(JTAPartitionMBeanImpl bean) {
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
               return "TimeoutSeconds";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("TimeoutSeconds")) {
            return 10;
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
            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTimeoutSecondsSet()) {
               buf.append("TimeoutSeconds");
               buf.append(String.valueOf(this.bean.getTimeoutSeconds()));
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
            JTAPartitionMBeanImpl otherTyped = (JTAPartitionMBeanImpl)other;
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("TimeoutSeconds", this.bean.getTimeoutSeconds(), otherTyped.getTimeoutSeconds(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JTAPartitionMBeanImpl original = (JTAPartitionMBeanImpl)event.getSourceBean();
            JTAPartitionMBeanImpl proposed = (JTAPartitionMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
               } else if (prop.equals("TimeoutSeconds")) {
                  original.setTimeoutSeconds(proposed.getTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (!prop.equals("DynamicallyCreated")) {
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
            JTAPartitionMBeanImpl copy = (JTAPartitionMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("TimeoutSeconds")) && this.bean.isTimeoutSecondsSet()) {
               copy.setTimeoutSeconds(this.bean.getTimeoutSeconds());
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

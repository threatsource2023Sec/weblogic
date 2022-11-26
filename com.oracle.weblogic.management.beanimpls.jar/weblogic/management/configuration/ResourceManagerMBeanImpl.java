package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ResourceManagerMBeanImpl extends ConfigurationMBeanImpl implements ResourceManagerMBean, Serializable {
   private CpuUtilizationMBean _CpuUtilization;
   private FileOpenMBean _FileOpen;
   private HeapRetainedMBean _HeapRetained;
   private RestartLoopProtectionMBean _RestartLoopProtection;
   private static SchemaHelper2 _schemaHelper;

   public ResourceManagerMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ResourceManagerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ResourceManagerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public FileOpenMBean getFileOpen() {
      return this._FileOpen;
   }

   public boolean isFileOpenInherited() {
      return false;
   }

   public boolean isFileOpenSet() {
      return this._isSet(10);
   }

   public void setFileOpen(FileOpenMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getFileOpen() != null && param0 != this.getFileOpen()) {
         throw new BeanAlreadyExistsException(this.getFileOpen() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 10)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         FileOpenMBean _oldVal = this._FileOpen;
         this._FileOpen = param0;
         this._postSet(10, _oldVal, param0);
      }
   }

   public FileOpenMBean createFileOpen(String param0) {
      FileOpenMBeanImpl _val = new FileOpenMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.setFileOpen(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyFileOpen(FileOpenMBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._FileOpen;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setFileOpen((FileOpenMBean)null);
               this._unSet(10);
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

   public HeapRetainedMBean getHeapRetained() {
      return this._HeapRetained;
   }

   public boolean isHeapRetainedInherited() {
      return false;
   }

   public boolean isHeapRetainedSet() {
      return this._isSet(11);
   }

   public void setHeapRetained(HeapRetainedMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getHeapRetained() != null && param0 != this.getHeapRetained()) {
         throw new BeanAlreadyExistsException(this.getHeapRetained() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 11)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         HeapRetainedMBean _oldVal = this._HeapRetained;
         this._HeapRetained = param0;
         this._postSet(11, _oldVal, param0);
      }
   }

   public HeapRetainedMBean createHeapRetained(String param0) {
      HeapRetainedMBeanImpl _val = new HeapRetainedMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.setHeapRetained(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyHeapRetained(HeapRetainedMBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._HeapRetained;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setHeapRetained((HeapRetainedMBean)null);
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

   public CpuUtilizationMBean getCpuUtilization() {
      return this._CpuUtilization;
   }

   public boolean isCpuUtilizationInherited() {
      return false;
   }

   public boolean isCpuUtilizationSet() {
      return this._isSet(12);
   }

   public void setCpuUtilization(CpuUtilizationMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCpuUtilization() != null && param0 != this.getCpuUtilization()) {
         throw new BeanAlreadyExistsException(this.getCpuUtilization() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 12)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         CpuUtilizationMBean _oldVal = this._CpuUtilization;
         this._CpuUtilization = param0;
         this._postSet(12, _oldVal, param0);
      }
   }

   public CpuUtilizationMBean createCpuUtilization(String param0) {
      CpuUtilizationMBeanImpl _val = new CpuUtilizationMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.setCpuUtilization(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyCpuUtilization(CpuUtilizationMBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CpuUtilization;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCpuUtilization((CpuUtilizationMBean)null);
               this._unSet(12);
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

   public RestartLoopProtectionMBean getRestartLoopProtection() {
      return this._RestartLoopProtection;
   }

   public boolean isRestartLoopProtectionInherited() {
      return false;
   }

   public boolean isRestartLoopProtectionSet() {
      return this._isSet(13) || this._isAnythingSet((AbstractDescriptorBean)this.getRestartLoopProtection());
   }

   public void setRestartLoopProtection(RestartLoopProtectionMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 13)) {
         this._postCreate(_child);
      }

      RestartLoopProtectionMBean _oldVal = this._RestartLoopProtection;
      this._RestartLoopProtection = param0;
      this._postSet(13, _oldVal, param0);
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
      return super._isAnythingSet() || this.isRestartLoopProtectionSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._CpuUtilization = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._FileOpen = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._HeapRetained = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._RestartLoopProtection = new RestartLoopProtectionMBeanImpl(this, 13);
               this._postCreate((AbstractDescriptorBean)this._RestartLoopProtection);
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
      return "ResourceManager";
   }

   public void putValue(String name, Object v) {
      if (name.equals("CpuUtilization")) {
         CpuUtilizationMBean oldVal = this._CpuUtilization;
         this._CpuUtilization = (CpuUtilizationMBean)v;
         this._postSet(12, oldVal, this._CpuUtilization);
      } else if (name.equals("FileOpen")) {
         FileOpenMBean oldVal = this._FileOpen;
         this._FileOpen = (FileOpenMBean)v;
         this._postSet(10, oldVal, this._FileOpen);
      } else if (name.equals("HeapRetained")) {
         HeapRetainedMBean oldVal = this._HeapRetained;
         this._HeapRetained = (HeapRetainedMBean)v;
         this._postSet(11, oldVal, this._HeapRetained);
      } else if (name.equals("RestartLoopProtection")) {
         RestartLoopProtectionMBean oldVal = this._RestartLoopProtection;
         this._RestartLoopProtection = (RestartLoopProtectionMBean)v;
         this._postSet(13, oldVal, this._RestartLoopProtection);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("CpuUtilization")) {
         return this._CpuUtilization;
      } else if (name.equals("FileOpen")) {
         return this._FileOpen;
      } else if (name.equals("HeapRetained")) {
         return this._HeapRetained;
      } else {
         return name.equals("RestartLoopProtection") ? this._RestartLoopProtection : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("file-open")) {
                  return 10;
               }
               break;
            case 13:
               if (s.equals("heap-retained")) {
                  return 11;
               }
               break;
            case 15:
               if (s.equals("cpu-utilization")) {
                  return 12;
               }
               break;
            case 23:
               if (s.equals("restart-loop-protection")) {
                  return 13;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new FileOpenMBeanImpl.SchemaHelper2();
            case 11:
               return new HeapRetainedMBeanImpl.SchemaHelper2();
            case 12:
               return new CpuUtilizationMBeanImpl.SchemaHelper2();
            case 13:
               return new RestartLoopProtectionMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "file-open";
            case 11:
               return "heap-retained";
            case 12:
               return "cpu-utilization";
            case 13:
               return "restart-loop-protection";
            default:
               return super.getElementName(propIndex);
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

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private ResourceManagerMBeanImpl bean;

      protected Helper(ResourceManagerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "FileOpen";
            case 11:
               return "HeapRetained";
            case 12:
               return "CpuUtilization";
            case 13:
               return "RestartLoopProtection";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CpuUtilization")) {
            return 12;
         } else if (propName.equals("FileOpen")) {
            return 10;
         } else if (propName.equals("HeapRetained")) {
            return 11;
         } else {
            return propName.equals("RestartLoopProtection") ? 13 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCpuUtilization() != null) {
            iterators.add(new ArrayIterator(new CpuUtilizationMBean[]{this.bean.getCpuUtilization()}));
         }

         if (this.bean.getFileOpen() != null) {
            iterators.add(new ArrayIterator(new FileOpenMBean[]{this.bean.getFileOpen()}));
         }

         if (this.bean.getHeapRetained() != null) {
            iterators.add(new ArrayIterator(new HeapRetainedMBean[]{this.bean.getHeapRetained()}));
         }

         if (this.bean.getRestartLoopProtection() != null) {
            iterators.add(new ArrayIterator(new RestartLoopProtectionMBean[]{this.bean.getRestartLoopProtection()}));
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
            childValue = this.computeChildHashValue(this.bean.getCpuUtilization());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getFileOpen());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getHeapRetained());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getRestartLoopProtection());
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
            ResourceManagerMBeanImpl otherTyped = (ResourceManagerMBeanImpl)other;
            this.computeChildDiff("CpuUtilization", this.bean.getCpuUtilization(), otherTyped.getCpuUtilization(), true);
            this.computeChildDiff("FileOpen", this.bean.getFileOpen(), otherTyped.getFileOpen(), true);
            this.computeChildDiff("HeapRetained", this.bean.getHeapRetained(), otherTyped.getHeapRetained(), true);
            this.computeSubDiff("RestartLoopProtection", this.bean.getRestartLoopProtection(), otherTyped.getRestartLoopProtection());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ResourceManagerMBeanImpl original = (ResourceManagerMBeanImpl)event.getSourceBean();
            ResourceManagerMBeanImpl proposed = (ResourceManagerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CpuUtilization")) {
                  if (type == 2) {
                     original.setCpuUtilization((CpuUtilizationMBean)this.createCopy((AbstractDescriptorBean)proposed.getCpuUtilization()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CpuUtilization", original.getCpuUtilization());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("FileOpen")) {
                  if (type == 2) {
                     original.setFileOpen((FileOpenMBean)this.createCopy((AbstractDescriptorBean)proposed.getFileOpen()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("FileOpen", original.getFileOpen());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("HeapRetained")) {
                  if (type == 2) {
                     original.setHeapRetained((HeapRetainedMBean)this.createCopy((AbstractDescriptorBean)proposed.getHeapRetained()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("HeapRetained", original.getHeapRetained());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("RestartLoopProtection")) {
                  if (type == 2) {
                     original.setRestartLoopProtection((RestartLoopProtectionMBean)this.createCopy((AbstractDescriptorBean)proposed.getRestartLoopProtection()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("RestartLoopProtection", original.getRestartLoopProtection());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 13);
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
            ResourceManagerMBeanImpl copy = (ResourceManagerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CpuUtilization")) && this.bean.isCpuUtilizationSet() && !copy._isSet(12)) {
               Object o = this.bean.getCpuUtilization();
               copy.setCpuUtilization((CpuUtilizationMBean)null);
               copy.setCpuUtilization(o == null ? null : (CpuUtilizationMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("FileOpen")) && this.bean.isFileOpenSet() && !copy._isSet(10)) {
               Object o = this.bean.getFileOpen();
               copy.setFileOpen((FileOpenMBean)null);
               copy.setFileOpen(o == null ? null : (FileOpenMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("HeapRetained")) && this.bean.isHeapRetainedSet() && !copy._isSet(11)) {
               Object o = this.bean.getHeapRetained();
               copy.setHeapRetained((HeapRetainedMBean)null);
               copy.setHeapRetained(o == null ? null : (HeapRetainedMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("RestartLoopProtection")) && this.bean.isRestartLoopProtectionSet() && !copy._isSet(13)) {
               Object o = this.bean.getRestartLoopProtection();
               copy.setRestartLoopProtection((RestartLoopProtectionMBean)null);
               copy.setRestartLoopProtection(o == null ? null : (RestartLoopProtectionMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getCpuUtilization(), clazz, annotation);
         this.inferSubTree(this.bean.getFileOpen(), clazz, annotation);
         this.inferSubTree(this.bean.getHeapRetained(), clazz, annotation);
         this.inferSubTree(this.bean.getRestartLoopProtection(), clazz, annotation);
      }
   }
}

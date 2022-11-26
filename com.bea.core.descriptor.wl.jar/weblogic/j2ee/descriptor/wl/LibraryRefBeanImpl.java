package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
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
import weblogic.utils.collections.CombinedIterator;

public class LibraryRefBeanImpl extends AbstractDescriptorBean implements LibraryRefBean, Serializable {
   private String _ContextRoot;
   private boolean _ExactMatch;
   private String _ImplementationVersion;
   private String _LibraryName;
   private String _SpecificationVersion;
   private static SchemaHelper2 _schemaHelper;

   public LibraryRefBeanImpl() {
      this._initializeProperty(-1);
   }

   public LibraryRefBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LibraryRefBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getLibraryName() {
      return this._LibraryName;
   }

   public boolean isLibraryNameInherited() {
      return false;
   }

   public boolean isLibraryNameSet() {
      return this._isSet(0);
   }

   public void setLibraryName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LibraryName;
      this._LibraryName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getSpecificationVersion() {
      return this._SpecificationVersion;
   }

   public boolean isSpecificationVersionInherited() {
      return false;
   }

   public boolean isSpecificationVersionSet() {
      return this._isSet(1);
   }

   public void setSpecificationVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SpecificationVersion;
      this._SpecificationVersion = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getImplementationVersion() {
      return this._ImplementationVersion;
   }

   public boolean isImplementationVersionInherited() {
      return false;
   }

   public boolean isImplementationVersionSet() {
      return this._isSet(2);
   }

   public void setImplementationVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ImplementationVersion;
      this._ImplementationVersion = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean getExactMatch() {
      return this._ExactMatch;
   }

   public boolean isExactMatchInherited() {
      return false;
   }

   public boolean isExactMatchSet() {
      return this._isSet(3);
   }

   public void setExactMatch(boolean param0) {
      boolean _oldVal = this._ExactMatch;
      this._ExactMatch = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getContextRoot() {
      return this._ContextRoot;
   }

   public boolean isContextRootInherited() {
      return false;
   }

   public boolean isContextRootSet() {
      return this._isSet(4);
   }

   public void setContextRoot(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ContextRoot;
      this._ContextRoot = param0;
      this._postSet(4, _oldVal, param0);
   }

   public Object _getKey() {
      return new CompoundKey(new Object[]{this.getContextRoot(), this.getLibraryName()});
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._ContextRoot = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ExactMatch = false;
               if (initOne) {
                  break;
               }
            case 2:
               this._ImplementationVersion = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._LibraryName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._SpecificationVersion = null;
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
            case 11:
               if (s.equals("exact-match")) {
                  return 3;
               }
               break;
            case 12:
               if (s.equals("context-root")) {
                  return 4;
               }

               if (s.equals("library-name")) {
                  return 0;
               }
               break;
            case 21:
               if (s.equals("specification-version")) {
                  return 1;
               }
               break;
            case 22:
               if (s.equals("implementation-version")) {
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
               return "library-name";
            case 1:
               return "specification-version";
            case 2:
               return "implementation-version";
            case 3:
               return "exact-match";
            case 4:
               return "context-root";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKeyComponent(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 4:
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
         indices.add("context-root");
         indices.add("library-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private LibraryRefBeanImpl bean;

      protected Helper(LibraryRefBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "LibraryName";
            case 1:
               return "SpecificationVersion";
            case 2:
               return "ImplementationVersion";
            case 3:
               return "ExactMatch";
            case 4:
               return "ContextRoot";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ContextRoot")) {
            return 4;
         } else if (propName.equals("ExactMatch")) {
            return 3;
         } else if (propName.equals("ImplementationVersion")) {
            return 2;
         } else if (propName.equals("LibraryName")) {
            return 0;
         } else {
            return propName.equals("SpecificationVersion") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isContextRootSet()) {
               buf.append("ContextRoot");
               buf.append(String.valueOf(this.bean.getContextRoot()));
            }

            if (this.bean.isExactMatchSet()) {
               buf.append("ExactMatch");
               buf.append(String.valueOf(this.bean.getExactMatch()));
            }

            if (this.bean.isImplementationVersionSet()) {
               buf.append("ImplementationVersion");
               buf.append(String.valueOf(this.bean.getImplementationVersion()));
            }

            if (this.bean.isLibraryNameSet()) {
               buf.append("LibraryName");
               buf.append(String.valueOf(this.bean.getLibraryName()));
            }

            if (this.bean.isSpecificationVersionSet()) {
               buf.append("SpecificationVersion");
               buf.append(String.valueOf(this.bean.getSpecificationVersion()));
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
            LibraryRefBeanImpl otherTyped = (LibraryRefBeanImpl)other;
            this.computeDiff("ContextRoot", this.bean.getContextRoot(), otherTyped.getContextRoot(), false);
            this.computeDiff("ExactMatch", this.bean.getExactMatch(), otherTyped.getExactMatch(), false);
            this.computeDiff("ImplementationVersion", this.bean.getImplementationVersion(), otherTyped.getImplementationVersion(), false);
            this.computeDiff("LibraryName", this.bean.getLibraryName(), otherTyped.getLibraryName(), false);
            this.computeDiff("SpecificationVersion", this.bean.getSpecificationVersion(), otherTyped.getSpecificationVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LibraryRefBeanImpl original = (LibraryRefBeanImpl)event.getSourceBean();
            LibraryRefBeanImpl proposed = (LibraryRefBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ContextRoot")) {
                  original.setContextRoot(proposed.getContextRoot());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ExactMatch")) {
                  original.setExactMatch(proposed.getExactMatch());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ImplementationVersion")) {
                  original.setImplementationVersion(proposed.getImplementationVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("LibraryName")) {
                  original.setLibraryName(proposed.getLibraryName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("SpecificationVersion")) {
                  original.setSpecificationVersion(proposed.getSpecificationVersion());
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
            LibraryRefBeanImpl copy = (LibraryRefBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ContextRoot")) && this.bean.isContextRootSet()) {
               copy.setContextRoot(this.bean.getContextRoot());
            }

            if ((excludeProps == null || !excludeProps.contains("ExactMatch")) && this.bean.isExactMatchSet()) {
               copy.setExactMatch(this.bean.getExactMatch());
            }

            if ((excludeProps == null || !excludeProps.contains("ImplementationVersion")) && this.bean.isImplementationVersionSet()) {
               copy.setImplementationVersion(this.bean.getImplementationVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("LibraryName")) && this.bean.isLibraryNameSet()) {
               copy.setLibraryName(this.bean.getLibraryName());
            }

            if ((excludeProps == null || !excludeProps.contains("SpecificationVersion")) && this.bean.isSpecificationVersionSet()) {
               copy.setSpecificationVersion(this.bean.getSpecificationVersion());
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

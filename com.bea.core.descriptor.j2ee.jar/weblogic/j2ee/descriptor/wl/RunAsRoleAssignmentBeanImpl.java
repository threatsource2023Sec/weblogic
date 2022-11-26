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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class RunAsRoleAssignmentBeanImpl extends AbstractDescriptorBean implements RunAsRoleAssignmentBean, Serializable {
   private String _Id;
   private String _RoleName;
   private String _RunAsPrincipalName;
   private static SchemaHelper2 _schemaHelper;

   public RunAsRoleAssignmentBeanImpl() {
      this._initializeProperty(-1);
   }

   public RunAsRoleAssignmentBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public RunAsRoleAssignmentBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getRoleName() {
      return this._RoleName;
   }

   public boolean isRoleNameInherited() {
      return false;
   }

   public boolean isRoleNameSet() {
      return this._isSet(0);
   }

   public void setRoleName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RoleName;
      this._RoleName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getRunAsPrincipalName() {
      return this._RunAsPrincipalName;
   }

   public boolean isRunAsPrincipalNameInherited() {
      return false;
   }

   public boolean isRunAsPrincipalNameSet() {
      return this._isSet(1);
   }

   public void setRunAsPrincipalName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RunAsPrincipalName;
      this._RunAsPrincipalName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(2);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(2, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getRoleName();
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
         case 9:
            if (s.equals("role-name")) {
               return info.compareXpaths(this._getPropertyXpath("role-name"));
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
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._RoleName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._RunAsPrincipalName = null;
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
                  return 2;
               }
               break;
            case 9:
               if (s.equals("role-name")) {
                  return 0;
               }
               break;
            case 21:
               if (s.equals("run-as-principal-name")) {
                  return 1;
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
               return "role-name";
            case 1:
               return "run-as-principal-name";
            case 2:
               return "id";
            default:
               return super.getElementName(propIndex);
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("role-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private RunAsRoleAssignmentBeanImpl bean;

      protected Helper(RunAsRoleAssignmentBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "RoleName";
            case 1:
               return "RunAsPrincipalName";
            case 2:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 2;
         } else if (propName.equals("RoleName")) {
            return 0;
         } else {
            return propName.equals("RunAsPrincipalName") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isRoleNameSet()) {
               buf.append("RoleName");
               buf.append(String.valueOf(this.bean.getRoleName()));
            }

            if (this.bean.isRunAsPrincipalNameSet()) {
               buf.append("RunAsPrincipalName");
               buf.append(String.valueOf(this.bean.getRunAsPrincipalName()));
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
            RunAsRoleAssignmentBeanImpl otherTyped = (RunAsRoleAssignmentBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("RoleName", this.bean.getRoleName(), otherTyped.getRoleName(), false);
            this.computeDiff("RunAsPrincipalName", this.bean.getRunAsPrincipalName(), otherTyped.getRunAsPrincipalName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            RunAsRoleAssignmentBeanImpl original = (RunAsRoleAssignmentBeanImpl)event.getSourceBean();
            RunAsRoleAssignmentBeanImpl proposed = (RunAsRoleAssignmentBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("RoleName")) {
                  original.setRoleName(proposed.getRoleName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("RunAsPrincipalName")) {
                  original.setRunAsPrincipalName(proposed.getRunAsPrincipalName());
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
            RunAsRoleAssignmentBeanImpl copy = (RunAsRoleAssignmentBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("RoleName")) && this.bean.isRoleNameSet()) {
               copy.setRoleName(this.bean.getRoleName());
            }

            if ((excludeProps == null || !excludeProps.contains("RunAsPrincipalName")) && this.bean.isRunAsPrincipalNameSet()) {
               copy.setRunAsPrincipalName(this.bean.getRunAsPrincipalName());
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

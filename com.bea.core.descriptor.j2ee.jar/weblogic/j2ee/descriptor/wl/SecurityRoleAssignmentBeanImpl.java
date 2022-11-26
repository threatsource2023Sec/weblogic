package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.EmptyBean;
import weblogic.j2ee.descriptor.EmptyBeanImpl;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SecurityRoleAssignmentBeanImpl extends AbstractDescriptorBean implements SecurityRoleAssignmentBean, Serializable {
   private EmptyBean _ExternallyDefined;
   private String _Id;
   private String[] _PrincipalNames;
   private String _RoleName;
   private static SchemaHelper2 _schemaHelper;

   public SecurityRoleAssignmentBeanImpl() {
      this._initializeProperty(-1);
   }

   public SecurityRoleAssignmentBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SecurityRoleAssignmentBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String[] getPrincipalNames() {
      return this._PrincipalNames;
   }

   public boolean isPrincipalNamesInherited() {
      return false;
   }

   public boolean isPrincipalNamesSet() {
      return this._isSet(1);
   }

   public void addPrincipalName(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(1)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getPrincipalNames(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setPrincipalNames(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removePrincipalName(String param0) {
      String[] _old = this.getPrincipalNames();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setPrincipalNames(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setPrincipalNames(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._PrincipalNames;
      this._PrincipalNames = param0;
      this._postSet(1, _oldVal, param0);
   }

   public EmptyBean getExternallyDefined() {
      return this._ExternallyDefined;
   }

   public boolean isExternallyDefinedInherited() {
      return false;
   }

   public boolean isExternallyDefinedSet() {
      return this._isSet(2);
   }

   public void setExternallyDefined(EmptyBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getExternallyDefined() != null && param0 != this.getExternallyDefined()) {
         throw new BeanAlreadyExistsException(this.getExternallyDefined() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         EmptyBean _oldVal = this._ExternallyDefined;
         this._ExternallyDefined = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public EmptyBean createExternallyDefined() {
      EmptyBeanImpl _val = new EmptyBeanImpl(this, -1);

      try {
         this.setExternallyDefined(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyExternallyDefined(EmptyBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ExternallyDefined;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setExternallyDefined((EmptyBean)null);
               this._unSet(2);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(3);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(3, _oldVal, param0);
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
               this._ExternallyDefined = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._PrincipalNames = new String[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._RoleName = null;
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
                  return 3;
               }
               break;
            case 9:
               if (s.equals("role-name")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("principal-name")) {
                  return 1;
               }
               break;
            case 18:
               if (s.equals("externally-defined")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new EmptyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "role-name";
            case 1:
               return "principal-name";
            case 2:
               return "externally-defined";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
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
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isConfigurable(propIndex);
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
      private SecurityRoleAssignmentBeanImpl bean;

      protected Helper(SecurityRoleAssignmentBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "RoleName";
            case 1:
               return "PrincipalNames";
            case 2:
               return "ExternallyDefined";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ExternallyDefined")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("PrincipalNames")) {
            return 1;
         } else {
            return propName.equals("RoleName") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getExternallyDefined() != null) {
            iterators.add(new ArrayIterator(new EmptyBean[]{this.bean.getExternallyDefined()}));
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
            childValue = this.computeChildHashValue(this.bean.getExternallyDefined());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isPrincipalNamesSet()) {
               buf.append("PrincipalNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPrincipalNames())));
            }

            if (this.bean.isRoleNameSet()) {
               buf.append("RoleName");
               buf.append(String.valueOf(this.bean.getRoleName()));
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
            SecurityRoleAssignmentBeanImpl otherTyped = (SecurityRoleAssignmentBeanImpl)other;
            this.computeChildDiff("ExternallyDefined", this.bean.getExternallyDefined(), otherTyped.getExternallyDefined(), true);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("PrincipalNames", this.bean.getPrincipalNames(), otherTyped.getPrincipalNames(), true);
            this.computeDiff("RoleName", this.bean.getRoleName(), otherTyped.getRoleName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SecurityRoleAssignmentBeanImpl original = (SecurityRoleAssignmentBeanImpl)event.getSourceBean();
            SecurityRoleAssignmentBeanImpl proposed = (SecurityRoleAssignmentBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ExternallyDefined")) {
                  if (type == 2) {
                     original.setExternallyDefined((EmptyBean)this.createCopy((AbstractDescriptorBean)proposed.getExternallyDefined()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ExternallyDefined", (DescriptorBean)original.getExternallyDefined());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("PrincipalNames")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addPrincipalName((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePrincipalName((String)update.getRemovedObject());
                  }

                  if (original.getPrincipalNames() == null || original.getPrincipalNames().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("RoleName")) {
                  original.setRoleName(proposed.getRoleName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            SecurityRoleAssignmentBeanImpl copy = (SecurityRoleAssignmentBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ExternallyDefined")) && this.bean.isExternallyDefinedSet() && !copy._isSet(2)) {
               Object o = this.bean.getExternallyDefined();
               copy.setExternallyDefined((EmptyBean)null);
               copy.setExternallyDefined(o == null ? null : (EmptyBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("PrincipalNames")) && this.bean.isPrincipalNamesSet()) {
               Object o = this.bean.getPrincipalNames();
               copy.setPrincipalNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("RoleName")) && this.bean.isRoleNameSet()) {
               copy.setRoleName(this.bean.getRoleName());
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
         this.inferSubTree(this.bean.getExternallyDefined(), clazz, annotation);
      }
   }
}

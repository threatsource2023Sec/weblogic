package weblogic.j2ee.descriptor.wl;

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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ResourceAdapterSecurityBeanImpl extends AbstractDescriptorBean implements ResourceAdapterSecurityBean, Serializable {
   private AnonPrincipalBean _DefaultPrincipalName;
   private String _Id;
   private AnonPrincipalBean _ManageAsPrincipalName;
   private AnonPrincipalCallerBean _RunAsPrincipalName;
   private AnonPrincipalCallerBean _RunWorkAsPrincipalName;
   private SecurityWorkContextBean _SecurityWorkContext;
   private static SchemaHelper2 _schemaHelper;

   public ResourceAdapterSecurityBeanImpl() {
      this._initializeProperty(-1);
   }

   public ResourceAdapterSecurityBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ResourceAdapterSecurityBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public AnonPrincipalBean getDefaultPrincipalName() {
      return this._DefaultPrincipalName;
   }

   public boolean isDefaultPrincipalNameInherited() {
      return false;
   }

   public boolean isDefaultPrincipalNameSet() {
      return this._isSet(0);
   }

   public void setDefaultPrincipalName(AnonPrincipalBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultPrincipalName() != null && param0 != this.getDefaultPrincipalName()) {
         throw new BeanAlreadyExistsException(this.getDefaultPrincipalName() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 0)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AnonPrincipalBean _oldVal = this._DefaultPrincipalName;
         this._DefaultPrincipalName = param0;
         this._postSet(0, _oldVal, param0);
      }
   }

   public AnonPrincipalBean createDefaultPrincipalName() {
      AnonPrincipalBeanImpl _val = new AnonPrincipalBeanImpl(this, -1);

      try {
         this.setDefaultPrincipalName(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultPrincipalName(AnonPrincipalBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultPrincipalName;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultPrincipalName((AnonPrincipalBean)null);
               this._unSet(0);
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

   public AnonPrincipalBean getManageAsPrincipalName() {
      return this._ManageAsPrincipalName;
   }

   public boolean isManageAsPrincipalNameInherited() {
      return false;
   }

   public boolean isManageAsPrincipalNameSet() {
      return this._isSet(1);
   }

   public void setManageAsPrincipalName(AnonPrincipalBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getManageAsPrincipalName() != null && param0 != this.getManageAsPrincipalName()) {
         throw new BeanAlreadyExistsException(this.getManageAsPrincipalName() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AnonPrincipalBean _oldVal = this._ManageAsPrincipalName;
         this._ManageAsPrincipalName = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public AnonPrincipalBean createManageAsPrincipalName() {
      AnonPrincipalBeanImpl _val = new AnonPrincipalBeanImpl(this, -1);

      try {
         this.setManageAsPrincipalName(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyManageAsPrincipalName(AnonPrincipalBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ManageAsPrincipalName;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setManageAsPrincipalName((AnonPrincipalBean)null);
               this._unSet(1);
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

   public AnonPrincipalCallerBean getRunAsPrincipalName() {
      return this._RunAsPrincipalName;
   }

   public boolean isRunAsPrincipalNameInherited() {
      return false;
   }

   public boolean isRunAsPrincipalNameSet() {
      return this._isSet(2);
   }

   public void setRunAsPrincipalName(AnonPrincipalCallerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getRunAsPrincipalName() != null && param0 != this.getRunAsPrincipalName()) {
         throw new BeanAlreadyExistsException(this.getRunAsPrincipalName() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AnonPrincipalCallerBean _oldVal = this._RunAsPrincipalName;
         this._RunAsPrincipalName = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public AnonPrincipalCallerBean createRunAsPrincipalName() {
      AnonPrincipalCallerBeanImpl _val = new AnonPrincipalCallerBeanImpl(this, -1);

      try {
         this.setRunAsPrincipalName(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyRunAsPrincipalName(AnonPrincipalCallerBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._RunAsPrincipalName;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setRunAsPrincipalName((AnonPrincipalCallerBean)null);
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

   public AnonPrincipalCallerBean getRunWorkAsPrincipalName() {
      return this._RunWorkAsPrincipalName;
   }

   public boolean isRunWorkAsPrincipalNameInherited() {
      return false;
   }

   public boolean isRunWorkAsPrincipalNameSet() {
      return this._isSet(3);
   }

   public void setRunWorkAsPrincipalName(AnonPrincipalCallerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getRunWorkAsPrincipalName() != null && param0 != this.getRunWorkAsPrincipalName()) {
         throw new BeanAlreadyExistsException(this.getRunWorkAsPrincipalName() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AnonPrincipalCallerBean _oldVal = this._RunWorkAsPrincipalName;
         this._RunWorkAsPrincipalName = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public AnonPrincipalCallerBean createRunWorkAsPrincipalName() {
      AnonPrincipalCallerBeanImpl _val = new AnonPrincipalCallerBeanImpl(this, -1);

      try {
         this.setRunWorkAsPrincipalName(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyRunWorkAsPrincipalName(AnonPrincipalCallerBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._RunWorkAsPrincipalName;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setRunWorkAsPrincipalName((AnonPrincipalCallerBean)null);
               this._unSet(3);
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

   public SecurityWorkContextBean getSecurityWorkContext() {
      return this._SecurityWorkContext;
   }

   public boolean isSecurityWorkContextInherited() {
      return false;
   }

   public boolean isSecurityWorkContextSet() {
      return this._isSet(4) || this._isAnythingSet((AbstractDescriptorBean)this.getSecurityWorkContext());
   }

   public void setSecurityWorkContext(SecurityWorkContextBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 4)) {
         this._postCreate(_child);
      }

      SecurityWorkContextBean _oldVal = this._SecurityWorkContext;
      this._SecurityWorkContext = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(5);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(5, _oldVal, param0);
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
      return super._isAnythingSet() || this.isSecurityWorkContextSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._DefaultPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ManageAsPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._RunAsPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._RunWorkAsPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._SecurityWorkContext = new SecurityWorkContextBeanImpl(this, 4);
               this._postCreate((AbstractDescriptorBean)this._SecurityWorkContext);
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
                  return 5;
               }
               break;
            case 21:
               if (s.equals("run-as-principal-name")) {
                  return 2;
               }

               if (s.equals("security-work-context")) {
                  return 4;
               }
               break;
            case 22:
               if (s.equals("default-principal-name")) {
                  return 0;
               }
               break;
            case 24:
               if (s.equals("manage-as-principal-name")) {
                  return 1;
               }
               break;
            case 26:
               if (s.equals("run-work-as-principal-name")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new AnonPrincipalBeanImpl.SchemaHelper2();
            case 1:
               return new AnonPrincipalBeanImpl.SchemaHelper2();
            case 2:
               return new AnonPrincipalCallerBeanImpl.SchemaHelper2();
            case 3:
               return new AnonPrincipalCallerBeanImpl.SchemaHelper2();
            case 4:
               return new SecurityWorkContextBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "default-principal-name";
            case 1:
               return "manage-as-principal-name";
            case 2:
               return "run-as-principal-name";
            case 3:
               return "run-work-as-principal-name";
            case 4:
               return "security-work-context";
            case 5:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
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
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ResourceAdapterSecurityBeanImpl bean;

      protected Helper(ResourceAdapterSecurityBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "DefaultPrincipalName";
            case 1:
               return "ManageAsPrincipalName";
            case 2:
               return "RunAsPrincipalName";
            case 3:
               return "RunWorkAsPrincipalName";
            case 4:
               return "SecurityWorkContext";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DefaultPrincipalName")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 5;
         } else if (propName.equals("ManageAsPrincipalName")) {
            return 1;
         } else if (propName.equals("RunAsPrincipalName")) {
            return 2;
         } else if (propName.equals("RunWorkAsPrincipalName")) {
            return 3;
         } else {
            return propName.equals("SecurityWorkContext") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getDefaultPrincipalName() != null) {
            iterators.add(new ArrayIterator(new AnonPrincipalBean[]{this.bean.getDefaultPrincipalName()}));
         }

         if (this.bean.getManageAsPrincipalName() != null) {
            iterators.add(new ArrayIterator(new AnonPrincipalBean[]{this.bean.getManageAsPrincipalName()}));
         }

         if (this.bean.getRunAsPrincipalName() != null) {
            iterators.add(new ArrayIterator(new AnonPrincipalCallerBean[]{this.bean.getRunAsPrincipalName()}));
         }

         if (this.bean.getRunWorkAsPrincipalName() != null) {
            iterators.add(new ArrayIterator(new AnonPrincipalCallerBean[]{this.bean.getRunWorkAsPrincipalName()}));
         }

         if (this.bean.getSecurityWorkContext() != null) {
            iterators.add(new ArrayIterator(new SecurityWorkContextBean[]{this.bean.getSecurityWorkContext()}));
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
            childValue = this.computeChildHashValue(this.bean.getDefaultPrincipalName());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getManageAsPrincipalName());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getRunAsPrincipalName());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getRunWorkAsPrincipalName());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSecurityWorkContext());
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
            ResourceAdapterSecurityBeanImpl otherTyped = (ResourceAdapterSecurityBeanImpl)other;
            this.computeChildDiff("DefaultPrincipalName", this.bean.getDefaultPrincipalName(), otherTyped.getDefaultPrincipalName(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("ManageAsPrincipalName", this.bean.getManageAsPrincipalName(), otherTyped.getManageAsPrincipalName(), false);
            this.computeChildDiff("RunAsPrincipalName", this.bean.getRunAsPrincipalName(), otherTyped.getRunAsPrincipalName(), false);
            this.computeChildDiff("RunWorkAsPrincipalName", this.bean.getRunWorkAsPrincipalName(), otherTyped.getRunWorkAsPrincipalName(), false);
            this.computeSubDiff("SecurityWorkContext", this.bean.getSecurityWorkContext(), otherTyped.getSecurityWorkContext());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ResourceAdapterSecurityBeanImpl original = (ResourceAdapterSecurityBeanImpl)event.getSourceBean();
            ResourceAdapterSecurityBeanImpl proposed = (ResourceAdapterSecurityBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DefaultPrincipalName")) {
                  if (type == 2) {
                     original.setDefaultPrincipalName((AnonPrincipalBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultPrincipalName()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DefaultPrincipalName", (DescriptorBean)original.getDefaultPrincipalName());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("ManageAsPrincipalName")) {
                  if (type == 2) {
                     original.setManageAsPrincipalName((AnonPrincipalBean)this.createCopy((AbstractDescriptorBean)proposed.getManageAsPrincipalName()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ManageAsPrincipalName", (DescriptorBean)original.getManageAsPrincipalName());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("RunAsPrincipalName")) {
                  if (type == 2) {
                     original.setRunAsPrincipalName((AnonPrincipalCallerBean)this.createCopy((AbstractDescriptorBean)proposed.getRunAsPrincipalName()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("RunAsPrincipalName", (DescriptorBean)original.getRunAsPrincipalName());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("RunWorkAsPrincipalName")) {
                  if (type == 2) {
                     original.setRunWorkAsPrincipalName((AnonPrincipalCallerBean)this.createCopy((AbstractDescriptorBean)proposed.getRunWorkAsPrincipalName()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("RunWorkAsPrincipalName", (DescriptorBean)original.getRunWorkAsPrincipalName());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("SecurityWorkContext")) {
                  if (type == 2) {
                     original.setSecurityWorkContext((SecurityWorkContextBean)this.createCopy((AbstractDescriptorBean)proposed.getSecurityWorkContext()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SecurityWorkContext", (DescriptorBean)original.getSecurityWorkContext());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            ResourceAdapterSecurityBeanImpl copy = (ResourceAdapterSecurityBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            AnonPrincipalBean o;
            if ((excludeProps == null || !excludeProps.contains("DefaultPrincipalName")) && this.bean.isDefaultPrincipalNameSet() && !copy._isSet(0)) {
               o = this.bean.getDefaultPrincipalName();
               copy.setDefaultPrincipalName((AnonPrincipalBean)null);
               copy.setDefaultPrincipalName(o == null ? null : (AnonPrincipalBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("ManageAsPrincipalName")) && this.bean.isManageAsPrincipalNameSet() && !copy._isSet(1)) {
               o = this.bean.getManageAsPrincipalName();
               copy.setManageAsPrincipalName((AnonPrincipalBean)null);
               copy.setManageAsPrincipalName(o == null ? null : (AnonPrincipalBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            AnonPrincipalCallerBean o;
            if ((excludeProps == null || !excludeProps.contains("RunAsPrincipalName")) && this.bean.isRunAsPrincipalNameSet() && !copy._isSet(2)) {
               o = this.bean.getRunAsPrincipalName();
               copy.setRunAsPrincipalName((AnonPrincipalCallerBean)null);
               copy.setRunAsPrincipalName(o == null ? null : (AnonPrincipalCallerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("RunWorkAsPrincipalName")) && this.bean.isRunWorkAsPrincipalNameSet() && !copy._isSet(3)) {
               o = this.bean.getRunWorkAsPrincipalName();
               copy.setRunWorkAsPrincipalName((AnonPrincipalCallerBean)null);
               copy.setRunWorkAsPrincipalName(o == null ? null : (AnonPrincipalCallerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityWorkContext")) && this.bean.isSecurityWorkContextSet() && !copy._isSet(4)) {
               Object o = this.bean.getSecurityWorkContext();
               copy.setSecurityWorkContext((SecurityWorkContextBean)null);
               copy.setSecurityWorkContext(o == null ? null : (SecurityWorkContextBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getDefaultPrincipalName(), clazz, annotation);
         this.inferSubTree(this.bean.getManageAsPrincipalName(), clazz, annotation);
         this.inferSubTree(this.bean.getRunAsPrincipalName(), clazz, annotation);
         this.inferSubTree(this.bean.getRunWorkAsPrincipalName(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityWorkContext(), clazz, annotation);
      }
   }
}

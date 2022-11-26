package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class AdministeredObjectBeanImpl extends AbstractDescriptorBean implements AdministeredObjectBean, Serializable {
   private String _ClassName;
   private String _Description;
   private String _Id;
   private String _InterfaceName;
   private String _Name;
   private JavaEEPropertyBean[] _Properties;
   private String _ResourceAdapter;
   private static SchemaHelper2 _schemaHelper;

   public AdministeredObjectBeanImpl() {
      this._initializeProperty(-1);
   }

   public AdministeredObjectBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AdministeredObjectBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(0);
   }

   public void setDescription(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(1);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getInterfaceName() {
      return this._InterfaceName;
   }

   public boolean isInterfaceNameInherited() {
      return false;
   }

   public boolean isInterfaceNameSet() {
      return this._isSet(2);
   }

   public void setInterfaceName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InterfaceName;
      this._InterfaceName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getClassName() {
      return this._ClassName;
   }

   public boolean isClassNameInherited() {
      return false;
   }

   public boolean isClassNameSet() {
      return this._isSet(3);
   }

   public void setClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClassName;
      this._ClassName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getResourceAdapter() {
      return this._ResourceAdapter;
   }

   public boolean isResourceAdapterInherited() {
      return false;
   }

   public boolean isResourceAdapterSet() {
      return this._isSet(4);
   }

   public void setResourceAdapter(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ResourceAdapter;
      this._ResourceAdapter = param0;
      this._postSet(4, _oldVal, param0);
   }

   public void addProperty(JavaEEPropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         JavaEEPropertyBean[] _new;
         if (this._isSet(5)) {
            _new = (JavaEEPropertyBean[])((JavaEEPropertyBean[])this._getHelper()._extendArray(this.getProperties(), JavaEEPropertyBean.class, param0));
         } else {
            _new = new JavaEEPropertyBean[]{param0};
         }

         try {
            this.setProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JavaEEPropertyBean[] getProperties() {
      return this._Properties;
   }

   public boolean isPropertiesInherited() {
      return false;
   }

   public boolean isPropertiesSet() {
      return this._isSet(5);
   }

   public void removeProperty(JavaEEPropertyBean param0) {
      this.destroyProperty(param0);
   }

   public void setProperties(JavaEEPropertyBean[] param0) throws InvalidAttributeValueException {
      JavaEEPropertyBean[] param0 = param0 == null ? new JavaEEPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JavaEEPropertyBean[] _oldVal = this._Properties;
      this._Properties = (JavaEEPropertyBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public JavaEEPropertyBean lookupProperty(String param0) {
      Object[] aary = (Object[])this._Properties;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JavaEEPropertyBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JavaEEPropertyBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public JavaEEPropertyBean createProperty() {
      JavaEEPropertyBeanImpl _val = new JavaEEPropertyBeanImpl(this, -1);

      try {
         this.addProperty(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyProperty(JavaEEPropertyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         JavaEEPropertyBean[] _old = this.getProperties();
         JavaEEPropertyBean[] _new = (JavaEEPropertyBean[])((JavaEEPropertyBean[])this._getHelper()._removeElement(_old, JavaEEPropertyBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setProperties(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
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
      return this._isSet(6);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(6, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("Name", this.isNameSet());
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._ClassName = "";
               if (initOne) {
                  break;
               }
            case 0:
               this._Description = "";
               if (initOne) {
                  break;
               }
            case 6:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._InterfaceName = "";
               if (initOne) {
                  break;
               }
            case 1:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Properties = new JavaEEPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._ResourceAdapter = "";
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
                  return 6;
               }
            case 3:
            case 5:
            case 6:
            case 7:
            case 9:
            case 12:
            case 13:
            case 15:
            default:
               break;
            case 4:
               if (s.equals("name")) {
                  return 1;
               }
               break;
            case 8:
               if (s.equals("property")) {
                  return 5;
               }
               break;
            case 10:
               if (s.equals("class-name")) {
                  return 3;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("interface-name")) {
                  return 2;
               }
               break;
            case 16:
               if (s.equals("resource-adapter")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 5:
               return new JavaEEPropertyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "name";
            case 2:
               return "interface-name";
            case 3:
               return "class-name";
            case 4:
               return "resource-adapter";
            case 5:
               return "property";
            case 6:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 6:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AdministeredObjectBeanImpl bean;

      protected Helper(AdministeredObjectBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "Name";
            case 2:
               return "InterfaceName";
            case 3:
               return "ClassName";
            case 4:
               return "ResourceAdapter";
            case 5:
               return "Properties";
            case 6:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClassName")) {
            return 3;
         } else if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 6;
         } else if (propName.equals("InterfaceName")) {
            return 2;
         } else if (propName.equals("Name")) {
            return 1;
         } else if (propName.equals("Properties")) {
            return 5;
         } else {
            return propName.equals("ResourceAdapter") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getProperties()));
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
            if (this.bean.isClassNameSet()) {
               buf.append("ClassName");
               buf.append(String.valueOf(this.bean.getClassName()));
            }

            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isInterfaceNameSet()) {
               buf.append("InterfaceName");
               buf.append(String.valueOf(this.bean.getInterfaceName()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isResourceAdapterSet()) {
               buf.append("ResourceAdapter");
               buf.append(String.valueOf(this.bean.getResourceAdapter()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            AdministeredObjectBeanImpl otherTyped = (AdministeredObjectBeanImpl)other;
            this.computeDiff("ClassName", this.bean.getClassName(), otherTyped.getClassName(), false);
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("InterfaceName", this.bean.getInterfaceName(), otherTyped.getInterfaceName(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeChildDiff("Properties", this.bean.getProperties(), otherTyped.getProperties(), false);
            this.computeDiff("ResourceAdapter", this.bean.getResourceAdapter(), otherTyped.getResourceAdapter(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AdministeredObjectBeanImpl original = (AdministeredObjectBeanImpl)event.getSourceBean();
            AdministeredObjectBeanImpl proposed = (AdministeredObjectBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClassName")) {
                  original.setClassName(proposed.getClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("InterfaceName")) {
                  original.setInterfaceName(proposed.getInterfaceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Properties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addProperty((JavaEEPropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeProperty((JavaEEPropertyBean)update.getRemovedObject());
                  }

                  if (original.getProperties() == null || original.getProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("ResourceAdapter")) {
                  original.setResourceAdapter(proposed.getResourceAdapter());
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
            AdministeredObjectBeanImpl copy = (AdministeredObjectBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClassName")) && this.bean.isClassNameSet()) {
               copy.setClassName(this.bean.getClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InterfaceName")) && this.bean.isInterfaceNameSet()) {
               copy.setInterfaceName(this.bean.getInterfaceName());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet() && !copy._isSet(5)) {
               JavaEEPropertyBean[] oldProperties = this.bean.getProperties();
               JavaEEPropertyBean[] newProperties = new JavaEEPropertyBean[oldProperties.length];

               for(int i = 0; i < newProperties.length; ++i) {
                  newProperties[i] = (JavaEEPropertyBean)((JavaEEPropertyBean)this.createCopy((AbstractDescriptorBean)oldProperties[i], includeObsolete));
               }

               copy.setProperties(newProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceAdapter")) && this.bean.isResourceAdapterSet()) {
               copy.setResourceAdapter(this.bean.getResourceAdapter());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getProperties(), clazz, annotation);
      }
   }
}

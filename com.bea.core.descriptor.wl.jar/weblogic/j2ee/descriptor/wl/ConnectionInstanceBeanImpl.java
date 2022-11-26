package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ConnectionInstanceBeanImpl extends AbstractDescriptorBean implements ConnectionInstanceBean, Serializable {
   private ConnectionDefinitionPropertiesBean _ConnectionProperties;
   private String _Description;
   private String _Id;
   private String _JNDIName;
   private static SchemaHelper2 _schemaHelper;

   public ConnectionInstanceBeanImpl() {
      this._initializeProperty(-1);
   }

   public ConnectionInstanceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ConnectionInstanceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String getJNDIName() {
      return this._JNDIName;
   }

   public boolean isJNDINameInherited() {
      return false;
   }

   public boolean isJNDINameSet() {
      return this._isSet(1);
   }

   public void setJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JNDIName;
      this._JNDIName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public ConnectionDefinitionPropertiesBean getConnectionProperties() {
      return this._ConnectionProperties;
   }

   public boolean isConnectionPropertiesInherited() {
      return false;
   }

   public boolean isConnectionPropertiesSet() {
      return this._isSet(2) || this._isAnythingSet((AbstractDescriptorBean)this.getConnectionProperties());
   }

   public void setConnectionProperties(ConnectionDefinitionPropertiesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 2)) {
         this._postCreate(_child);
      }

      ConnectionDefinitionPropertiesBean _oldVal = this._ConnectionProperties;
      this._ConnectionProperties = param0;
      this._postSet(2, _oldVal, param0);
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
      return this.getJNDIName();
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
            if (s.equals("jndi-name")) {
               return info.compareXpaths(this._getPropertyXpath("jndi-name"));
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
      return super._isAnythingSet() || this.isConnectionPropertiesSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._ConnectionProperties = new ConnectionDefinitionPropertiesBeanImpl(this, 2);
               this._postCreate((AbstractDescriptorBean)this._ConnectionProperties);
               if (initOne) {
                  break;
               }
            case 0:
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._JNDIName = null;
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
               if (s.equals("jndi-name")) {
                  return 1;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 21:
               if (s.equals("connection-properties")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new ConnectionDefinitionPropertiesBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "jndi-name";
            case 2:
               return "connection-properties";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
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
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isConfigurable(propIndex);
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
         indices.add("jndi-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ConnectionInstanceBeanImpl bean;

      protected Helper(ConnectionInstanceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "JNDIName";
            case 2:
               return "ConnectionProperties";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionProperties")) {
            return 2;
         } else if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 3;
         } else {
            return propName.equals("JNDIName") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getConnectionProperties() != null) {
            iterators.add(new ArrayIterator(new ConnectionDefinitionPropertiesBean[]{this.bean.getConnectionProperties()}));
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
            childValue = this.computeChildHashValue(this.bean.getConnectionProperties());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isJNDINameSet()) {
               buf.append("JNDIName");
               buf.append(String.valueOf(this.bean.getJNDIName()));
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
            ConnectionInstanceBeanImpl otherTyped = (ConnectionInstanceBeanImpl)other;
            this.computeSubDiff("ConnectionProperties", this.bean.getConnectionProperties(), otherTyped.getConnectionProperties());
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("JNDIName", this.bean.getJNDIName(), otherTyped.getJNDIName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConnectionInstanceBeanImpl original = (ConnectionInstanceBeanImpl)event.getSourceBean();
            ConnectionInstanceBeanImpl proposed = (ConnectionInstanceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionProperties")) {
                  if (type == 2) {
                     original.setConnectionProperties((ConnectionDefinitionPropertiesBean)this.createCopy((AbstractDescriptorBean)proposed.getConnectionProperties()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ConnectionProperties", (DescriptorBean)original.getConnectionProperties());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("JNDIName")) {
                  original.setJNDIName(proposed.getJNDIName());
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
            ConnectionInstanceBeanImpl copy = (ConnectionInstanceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionProperties")) && this.bean.isConnectionPropertiesSet() && !copy._isSet(2)) {
               Object o = this.bean.getConnectionProperties();
               copy.setConnectionProperties((ConnectionDefinitionPropertiesBean)null);
               copy.setConnectionProperties(o == null ? null : (ConnectionDefinitionPropertiesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("JNDIName")) && this.bean.isJNDINameSet()) {
               copy.setJNDIName(this.bean.getJNDIName());
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
         this.inferSubTree(this.bean.getConnectionProperties(), clazz, annotation);
      }
   }
}

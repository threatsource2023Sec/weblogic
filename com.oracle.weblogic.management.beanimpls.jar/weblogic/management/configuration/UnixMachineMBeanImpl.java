package weblogic.management.configuration;

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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class UnixMachineMBeanImpl extends MachineMBeanImpl implements UnixMachineMBean, Serializable {
   private String _PostBindGID;
   private boolean _PostBindGIDEnabled;
   private String _PostBindUID;
   private boolean _PostBindUIDEnabled;
   private static SchemaHelper2 _schemaHelper;

   public UnixMachineMBeanImpl() {
      this._initializeProperty(-1);
   }

   public UnixMachineMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public UnixMachineMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isPostBindUIDEnabled() {
      return this._PostBindUIDEnabled;
   }

   public boolean isPostBindUIDEnabledInherited() {
      return false;
   }

   public boolean isPostBindUIDEnabledSet() {
      return this._isSet(12);
   }

   public void setPostBindUIDEnabled(boolean param0) {
      boolean _oldVal = this._PostBindUIDEnabled;
      this._PostBindUIDEnabled = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getPostBindUID() {
      return this._PostBindUID;
   }

   public boolean isPostBindUIDInherited() {
      return false;
   }

   public boolean isPostBindUIDSet() {
      return this._isSet(13);
   }

   public void setPostBindUID(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PostBindUID;
      this._PostBindUID = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isPostBindGIDEnabled() {
      return this._PostBindGIDEnabled;
   }

   public boolean isPostBindGIDEnabledInherited() {
      return false;
   }

   public boolean isPostBindGIDEnabledSet() {
      return this._isSet(14);
   }

   public void setPostBindGIDEnabled(boolean param0) {
      boolean _oldVal = this._PostBindGIDEnabled;
      this._PostBindGIDEnabled = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getPostBindGID() {
      return this._PostBindGID;
   }

   public boolean isPostBindGIDInherited() {
      return false;
   }

   public boolean isPostBindGIDSet() {
      return this._isSet(15);
   }

   public void setPostBindGID(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PostBindGID;
      this._PostBindGID = param0;
      this._postSet(15, _oldVal, param0);
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
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 15;
      }

      try {
         switch (idx) {
            case 15:
               this._PostBindGID = "nobody";
               if (initOne) {
                  break;
               }
            case 13:
               this._PostBindUID = "nobody";
               if (initOne) {
                  break;
               }
            case 14:
               this._PostBindGIDEnabled = false;
               if (initOne) {
                  break;
               }
            case 12:
               this._PostBindUIDEnabled = false;
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
      return "UnixMachine";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("PostBindGID")) {
         oldVal = this._PostBindGID;
         this._PostBindGID = (String)v;
         this._postSet(15, oldVal, this._PostBindGID);
      } else {
         boolean oldVal;
         if (name.equals("PostBindGIDEnabled")) {
            oldVal = this._PostBindGIDEnabled;
            this._PostBindGIDEnabled = (Boolean)v;
            this._postSet(14, oldVal, this._PostBindGIDEnabled);
         } else if (name.equals("PostBindUID")) {
            oldVal = this._PostBindUID;
            this._PostBindUID = (String)v;
            this._postSet(13, oldVal, this._PostBindUID);
         } else if (name.equals("PostBindUIDEnabled")) {
            oldVal = this._PostBindUIDEnabled;
            this._PostBindUIDEnabled = (Boolean)v;
            this._postSet(12, oldVal, this._PostBindUIDEnabled);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("PostBindGID")) {
         return this._PostBindGID;
      } else if (name.equals("PostBindGIDEnabled")) {
         return new Boolean(this._PostBindGIDEnabled);
      } else if (name.equals("PostBindUID")) {
         return this._PostBindUID;
      } else {
         return name.equals("PostBindUIDEnabled") ? new Boolean(this._PostBindUIDEnabled) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends MachineMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 12:
               if (s.equals("post-bindgid")) {
                  return 15;
               }

               if (s.equals("post-binduid")) {
                  return 13;
               }
               break;
            case 20:
               if (s.equals("post-bindgid-enabled")) {
                  return 14;
               }

               if (s.equals("post-binduid-enabled")) {
                  return 12;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 11:
               return new NodeManagerMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "post-binduid-enabled";
            case 13:
               return "post-binduid";
            case 14:
               return "post-bindgid-enabled";
            case 15:
               return "post-bindgid";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 11:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
            default:
               return super.isConfigurable(propIndex);
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends MachineMBeanImpl.Helper {
      private UnixMachineMBeanImpl bean;

      protected Helper(UnixMachineMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "PostBindUIDEnabled";
            case 13:
               return "PostBindUID";
            case 14:
               return "PostBindGIDEnabled";
            case 15:
               return "PostBindGID";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("PostBindGID")) {
            return 15;
         } else if (propName.equals("PostBindUID")) {
            return 13;
         } else if (propName.equals("PostBindGIDEnabled")) {
            return 14;
         } else {
            return propName.equals("PostBindUIDEnabled") ? 12 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getNodeManager() != null) {
            iterators.add(new ArrayIterator(new NodeManagerMBean[]{this.bean.getNodeManager()}));
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
            if (this.bean.isPostBindGIDSet()) {
               buf.append("PostBindGID");
               buf.append(String.valueOf(this.bean.getPostBindGID()));
            }

            if (this.bean.isPostBindUIDSet()) {
               buf.append("PostBindUID");
               buf.append(String.valueOf(this.bean.getPostBindUID()));
            }

            if (this.bean.isPostBindGIDEnabledSet()) {
               buf.append("PostBindGIDEnabled");
               buf.append(String.valueOf(this.bean.isPostBindGIDEnabled()));
            }

            if (this.bean.isPostBindUIDEnabledSet()) {
               buf.append("PostBindUIDEnabled");
               buf.append(String.valueOf(this.bean.isPostBindUIDEnabled()));
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
            UnixMachineMBeanImpl otherTyped = (UnixMachineMBeanImpl)other;
            this.computeDiff("PostBindGID", this.bean.getPostBindGID(), otherTyped.getPostBindGID(), false);
            this.computeDiff("PostBindUID", this.bean.getPostBindUID(), otherTyped.getPostBindUID(), false);
            this.computeDiff("PostBindGIDEnabled", this.bean.isPostBindGIDEnabled(), otherTyped.isPostBindGIDEnabled(), false);
            this.computeDiff("PostBindUIDEnabled", this.bean.isPostBindUIDEnabled(), otherTyped.isPostBindUIDEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            UnixMachineMBeanImpl original = (UnixMachineMBeanImpl)event.getSourceBean();
            UnixMachineMBeanImpl proposed = (UnixMachineMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("PostBindGID")) {
                  original.setPostBindGID(proposed.getPostBindGID());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("PostBindUID")) {
                  original.setPostBindUID(proposed.getPostBindUID());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("PostBindGIDEnabled")) {
                  original.setPostBindGIDEnabled(proposed.isPostBindGIDEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("PostBindUIDEnabled")) {
                  original.setPostBindUIDEnabled(proposed.isPostBindUIDEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            UnixMachineMBeanImpl copy = (UnixMachineMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("PostBindGID")) && this.bean.isPostBindGIDSet()) {
               copy.setPostBindGID(this.bean.getPostBindGID());
            }

            if ((excludeProps == null || !excludeProps.contains("PostBindUID")) && this.bean.isPostBindUIDSet()) {
               copy.setPostBindUID(this.bean.getPostBindUID());
            }

            if ((excludeProps == null || !excludeProps.contains("PostBindGIDEnabled")) && this.bean.isPostBindGIDEnabledSet()) {
               copy.setPostBindGIDEnabled(this.bean.isPostBindGIDEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("PostBindUIDEnabled")) && this.bean.isPostBindUIDEnabledSet()) {
               copy.setPostBindUIDEnabled(this.bean.isPostBindUIDEnabled());
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

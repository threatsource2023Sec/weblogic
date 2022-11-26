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

public class InboundGroupPrincipalMappingBeanImpl extends AbstractDescriptorBean implements InboundGroupPrincipalMappingBean, Serializable {
   private String _EisGroupPrincipal;
   private String _Id;
   private String _MappedGroupPrincipal;
   private static SchemaHelper2 _schemaHelper;

   public InboundGroupPrincipalMappingBeanImpl() {
      this._initializeProperty(-1);
   }

   public InboundGroupPrincipalMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public InboundGroupPrincipalMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getEisGroupPrincipal() {
      return this._EisGroupPrincipal;
   }

   public boolean isEisGroupPrincipalInherited() {
      return false;
   }

   public boolean isEisGroupPrincipalSet() {
      return this._isSet(0);
   }

   public void setEisGroupPrincipal(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EisGroupPrincipal;
      this._EisGroupPrincipal = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getMappedGroupPrincipal() {
      return this._MappedGroupPrincipal;
   }

   public boolean isMappedGroupPrincipalInherited() {
      return false;
   }

   public boolean isMappedGroupPrincipalSet() {
      return this._isSet(1);
   }

   public void setMappedGroupPrincipal(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MappedGroupPrincipal;
      this._MappedGroupPrincipal = param0;
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
      return this.getEisGroupPrincipal();
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
         case 19:
            if (s.equals("eis-group-principal")) {
               return info.compareXpaths(this._getPropertyXpath("eis-group-principal"));
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._EisGroupPrincipal = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._MappedGroupPrincipal = null;
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
            case 19:
               if (s.equals("eis-group-principal")) {
                  return 0;
               }
               break;
            case 22:
               if (s.equals("mapped-group-principal")) {
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
               return "eis-group-principal";
            case 1:
               return "mapped-group-principal";
            case 2:
               return "id";
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
         indices.add("eis-group-principal");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private InboundGroupPrincipalMappingBeanImpl bean;

      protected Helper(InboundGroupPrincipalMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "EisGroupPrincipal";
            case 1:
               return "MappedGroupPrincipal";
            case 2:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EisGroupPrincipal")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 2;
         } else {
            return propName.equals("MappedGroupPrincipal") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isEisGroupPrincipalSet()) {
               buf.append("EisGroupPrincipal");
               buf.append(String.valueOf(this.bean.getEisGroupPrincipal()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isMappedGroupPrincipalSet()) {
               buf.append("MappedGroupPrincipal");
               buf.append(String.valueOf(this.bean.getMappedGroupPrincipal()));
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
            InboundGroupPrincipalMappingBeanImpl otherTyped = (InboundGroupPrincipalMappingBeanImpl)other;
            this.computeDiff("EisGroupPrincipal", this.bean.getEisGroupPrincipal(), otherTyped.getEisGroupPrincipal(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("MappedGroupPrincipal", this.bean.getMappedGroupPrincipal(), otherTyped.getMappedGroupPrincipal(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            InboundGroupPrincipalMappingBeanImpl original = (InboundGroupPrincipalMappingBeanImpl)event.getSourceBean();
            InboundGroupPrincipalMappingBeanImpl proposed = (InboundGroupPrincipalMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("EisGroupPrincipal")) {
                  original.setEisGroupPrincipal(proposed.getEisGroupPrincipal());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MappedGroupPrincipal")) {
                  original.setMappedGroupPrincipal(proposed.getMappedGroupPrincipal());
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
            InboundGroupPrincipalMappingBeanImpl copy = (InboundGroupPrincipalMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EisGroupPrincipal")) && this.bean.isEisGroupPrincipalSet()) {
               copy.setEisGroupPrincipal(this.bean.getEisGroupPrincipal());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("MappedGroupPrincipal")) && this.bean.isMappedGroupPrincipalSet()) {
               copy.setMappedGroupPrincipal(this.bean.getMappedGroupPrincipal());
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

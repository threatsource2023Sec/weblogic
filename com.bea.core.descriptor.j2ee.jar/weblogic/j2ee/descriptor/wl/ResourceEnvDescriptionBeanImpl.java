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

public class ResourceEnvDescriptionBeanImpl extends AbstractDescriptorBean implements ResourceEnvDescriptionBean, Serializable {
   private String _Id;
   private String _JNDIName;
   private String _ResourceEnvRefName;
   private String _ResourceLink;
   private static SchemaHelper2 _schemaHelper;

   public ResourceEnvDescriptionBeanImpl() {
      this._initializeProperty(-1);
   }

   public ResourceEnvDescriptionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ResourceEnvDescriptionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getResourceEnvRefName() {
      return this._ResourceEnvRefName;
   }

   public boolean isResourceEnvRefNameInherited() {
      return false;
   }

   public boolean isResourceEnvRefNameSet() {
      return this._isSet(0);
   }

   public void setResourceEnvRefName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ResourceEnvRefName;
      this._ResourceEnvRefName = param0;
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

   public String getResourceLink() {
      return this._ResourceLink;
   }

   public boolean isResourceLinkInherited() {
      return false;
   }

   public boolean isResourceLinkSet() {
      return this._isSet(2);
   }

   public void setResourceLink(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ResourceLink;
      this._ResourceLink = param0;
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
      return this.getResourceEnvRefName();
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
         case 21:
            if (s.equals("resource-env-ref-name")) {
               return info.compareXpaths(this._getPropertyXpath("resource-env-ref-name"));
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
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._JNDIName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ResourceEnvRefName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ResourceLink = null;
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
            case 13:
               if (s.equals("resource-link")) {
                  return 2;
               }
               break;
            case 21:
               if (s.equals("resource-env-ref-name")) {
                  return 0;
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
               return "resource-env-ref-name";
            case 1:
               return "jndi-name";
            case 2:
               return "resource-link";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
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
         indices.add("resource-env-ref-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ResourceEnvDescriptionBeanImpl bean;

      protected Helper(ResourceEnvDescriptionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ResourceEnvRefName";
            case 1:
               return "JNDIName";
            case 2:
               return "ResourceLink";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("JNDIName")) {
            return 1;
         } else if (propName.equals("ResourceEnvRefName")) {
            return 0;
         } else {
            return propName.equals("ResourceLink") ? 2 : super.getPropertyIndex(propName);
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

            if (this.bean.isJNDINameSet()) {
               buf.append("JNDIName");
               buf.append(String.valueOf(this.bean.getJNDIName()));
            }

            if (this.bean.isResourceEnvRefNameSet()) {
               buf.append("ResourceEnvRefName");
               buf.append(String.valueOf(this.bean.getResourceEnvRefName()));
            }

            if (this.bean.isResourceLinkSet()) {
               buf.append("ResourceLink");
               buf.append(String.valueOf(this.bean.getResourceLink()));
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
            ResourceEnvDescriptionBeanImpl otherTyped = (ResourceEnvDescriptionBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("JNDIName", this.bean.getJNDIName(), otherTyped.getJNDIName(), false);
            this.computeDiff("ResourceEnvRefName", this.bean.getResourceEnvRefName(), otherTyped.getResourceEnvRefName(), false);
            this.computeDiff("ResourceLink", this.bean.getResourceLink(), otherTyped.getResourceLink(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ResourceEnvDescriptionBeanImpl original = (ResourceEnvDescriptionBeanImpl)event.getSourceBean();
            ResourceEnvDescriptionBeanImpl proposed = (ResourceEnvDescriptionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("JNDIName")) {
                  original.setJNDIName(proposed.getJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ResourceEnvRefName")) {
                  original.setResourceEnvRefName(proposed.getResourceEnvRefName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ResourceLink")) {
                  original.setResourceLink(proposed.getResourceLink());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            ResourceEnvDescriptionBeanImpl copy = (ResourceEnvDescriptionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("JNDIName")) && this.bean.isJNDINameSet()) {
               copy.setJNDIName(this.bean.getJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceEnvRefName")) && this.bean.isResourceEnvRefNameSet()) {
               copy.setResourceEnvRefName(this.bean.getResourceEnvRefName());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceLink")) && this.bean.isResourceLinkSet()) {
               copy.setResourceLink(this.bean.getResourceLink());
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

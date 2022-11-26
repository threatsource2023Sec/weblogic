package weblogic.j2ee.descriptor;

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

public class ServiceImplBeanBeanImpl extends AbstractDescriptorBean implements ServiceImplBeanBean, Serializable {
   private String _EjbLink;
   private String _Id;
   private String _ServletLink;
   private static SchemaHelper2 _schemaHelper;

   public ServiceImplBeanBeanImpl() {
      this._initializeProperty(-1);
   }

   public ServiceImplBeanBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ServiceImplBeanBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getEjbLink() {
      return this._EjbLink;
   }

   public boolean isEjbLinkInherited() {
      return false;
   }

   public boolean isEjbLinkSet() {
      return this._isSet(0);
   }

   public void setEjbLink(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbLink;
      this._EjbLink = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getServletLink() {
      return this._ServletLink;
   }

   public boolean isServletLinkInherited() {
      return false;
   }

   public boolean isServletLinkSet() {
      return this._isSet(1);
   }

   public void setServletLink(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServletLink;
      this._ServletLink = param0;
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._EjbLink = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ServletLink = null;
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
            case 8:
               if (s.equals("ejb-link")) {
                  return 0;
               }
               break;
            case 12:
               if (s.equals("servlet-link")) {
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
               return "ejb-link";
            case 1:
               return "servlet-link";
            case 2:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ServiceImplBeanBeanImpl bean;

      protected Helper(ServiceImplBeanBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "EjbLink";
            case 1:
               return "ServletLink";
            case 2:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EjbLink")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 2;
         } else {
            return propName.equals("ServletLink") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isEjbLinkSet()) {
               buf.append("EjbLink");
               buf.append(String.valueOf(this.bean.getEjbLink()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isServletLinkSet()) {
               buf.append("ServletLink");
               buf.append(String.valueOf(this.bean.getServletLink()));
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
            ServiceImplBeanBeanImpl otherTyped = (ServiceImplBeanBeanImpl)other;
            this.computeDiff("EjbLink", this.bean.getEjbLink(), otherTyped.getEjbLink(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("ServletLink", this.bean.getServletLink(), otherTyped.getServletLink(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServiceImplBeanBeanImpl original = (ServiceImplBeanBeanImpl)event.getSourceBean();
            ServiceImplBeanBeanImpl proposed = (ServiceImplBeanBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("EjbLink")) {
                  original.setEjbLink(proposed.getEjbLink());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ServletLink")) {
                  original.setServletLink(proposed.getServletLink());
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
            ServiceImplBeanBeanImpl copy = (ServiceImplBeanBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EjbLink")) && this.bean.isEjbLinkSet()) {
               copy.setEjbLink(this.bean.getEjbLink());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("ServletLink")) && this.bean.isServletLinkSet()) {
               copy.setServletLink(this.bean.getServletLink());
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

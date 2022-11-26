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

public class LinkRefBeanImpl extends AbstractDescriptorBean implements LinkRefBean, Serializable {
   private String _ConnectionFactoryName;
   private String _RaLinkRef;
   private static SchemaHelper2 _schemaHelper;

   public LinkRefBeanImpl() {
      this._initializeProperty(-1);
   }

   public LinkRefBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LinkRefBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getConnectionFactoryName() {
      return this._ConnectionFactoryName;
   }

   public boolean isConnectionFactoryNameInherited() {
      return false;
   }

   public boolean isConnectionFactoryNameSet() {
      return this._isSet(0);
   }

   public void setConnectionFactoryName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionFactoryName;
      this._ConnectionFactoryName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getRaLinkRef() {
      return this._RaLinkRef;
   }

   public boolean isRaLinkRefInherited() {
      return false;
   }

   public boolean isRaLinkRefSet() {
      return this._isSet(1);
   }

   public void setRaLinkRef(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RaLinkRef;
      this._RaLinkRef = param0;
      this._postSet(1, _oldVal, param0);
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
               this._ConnectionFactoryName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._RaLinkRef = null;
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
               if (s.equals("ra-link-ref")) {
                  return 1;
               }
               break;
            case 23:
               if (s.equals("connection-factory-name")) {
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
               return "connection-factory-name";
            case 1:
               return "ra-link-ref";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private LinkRefBeanImpl bean;

      protected Helper(LinkRefBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ConnectionFactoryName";
            case 1:
               return "RaLinkRef";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionFactoryName")) {
            return 0;
         } else {
            return propName.equals("RaLinkRef") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isConnectionFactoryNameSet()) {
               buf.append("ConnectionFactoryName");
               buf.append(String.valueOf(this.bean.getConnectionFactoryName()));
            }

            if (this.bean.isRaLinkRefSet()) {
               buf.append("RaLinkRef");
               buf.append(String.valueOf(this.bean.getRaLinkRef()));
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
            LinkRefBeanImpl otherTyped = (LinkRefBeanImpl)other;
            this.computeDiff("ConnectionFactoryName", this.bean.getConnectionFactoryName(), otherTyped.getConnectionFactoryName(), false);
            this.computeDiff("RaLinkRef", this.bean.getRaLinkRef(), otherTyped.getRaLinkRef(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LinkRefBeanImpl original = (LinkRefBeanImpl)event.getSourceBean();
            LinkRefBeanImpl proposed = (LinkRefBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionFactoryName")) {
                  original.setConnectionFactoryName(proposed.getConnectionFactoryName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("RaLinkRef")) {
                  original.setRaLinkRef(proposed.getRaLinkRef());
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
            LinkRefBeanImpl copy = (LinkRefBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryName")) && this.bean.isConnectionFactoryNameSet()) {
               copy.setConnectionFactoryName(this.bean.getConnectionFactoryName());
            }

            if ((excludeProps == null || !excludeProps.contains("RaLinkRef")) && this.bean.isRaLinkRefSet()) {
               copy.setRaLinkRef(this.bean.getRaLinkRef());
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

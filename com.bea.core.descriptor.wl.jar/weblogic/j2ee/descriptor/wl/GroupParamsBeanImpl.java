package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class GroupParamsBeanImpl extends SettableBeanImpl implements GroupParamsBean, Serializable {
   private DestinationBean _ErrorDestination;
   private String _SubDeploymentName;
   private static SchemaHelper2 _schemaHelper;

   public GroupParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public GroupParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public GroupParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getSubDeploymentName() {
      return this._SubDeploymentName;
   }

   public boolean isSubDeploymentNameInherited() {
      return false;
   }

   public boolean isSubDeploymentNameSet() {
      return this._isSet(0);
   }

   public void setSubDeploymentName(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SubDeploymentName;
      this._SubDeploymentName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public DestinationBean getErrorDestination() {
      return this._ErrorDestination;
   }

   public String getErrorDestinationAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getErrorDestination();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isErrorDestinationInherited() {
      return false;
   }

   public boolean isErrorDestinationSet() {
      return this._isSet(1);
   }

   public void setErrorDestinationAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, DestinationBean.class, new ReferenceManager.Resolver(this, 1) {
            public void resolveReference(Object value) {
               try {
                  GroupParamsBeanImpl.this.setErrorDestination((DestinationBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         DestinationBean _oldVal = this._ErrorDestination;
         this._initializeProperty(1);
         this._postSet(1, _oldVal, this._ErrorDestination);
      }

   }

   public void setErrorDestination(DestinationBean param0) throws IllegalArgumentException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 1, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return GroupParamsBeanImpl.this.getErrorDestination();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      DestinationBean _oldVal = this._ErrorDestination;
      this._ErrorDestination = param0;
      this._postSet(1, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getSubDeploymentName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("SubDeploymentName", this.isSubDeploymentNameSet());
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 19:
            if (s.equals("sub-deployment-name")) {
               return info.compareXpaths(this._getPropertyXpath("sub-deployment-name"));
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._ErrorDestination = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._SubDeploymentName = null;
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 17:
               if (s.equals("error-destination")) {
                  return 1;
               }
               break;
            case 19:
               if (s.equals("sub-deployment-name")) {
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
               return "sub-deployment-name";
            case 1:
               return "error-destination";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 0:
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
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
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
         indices.add("sub-deployment-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private GroupParamsBeanImpl bean;

      protected Helper(GroupParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "SubDeploymentName";
            case 1:
               return "ErrorDestination";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ErrorDestination")) {
            return 1;
         } else {
            return propName.equals("SubDeploymentName") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isErrorDestinationSet()) {
               buf.append("ErrorDestination");
               buf.append(String.valueOf(this.bean.getErrorDestination()));
            }

            if (this.bean.isSubDeploymentNameSet()) {
               buf.append("SubDeploymentName");
               buf.append(String.valueOf(this.bean.getSubDeploymentName()));
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
            GroupParamsBeanImpl otherTyped = (GroupParamsBeanImpl)other;
            this.computeDiff("ErrorDestination", this.bean.getErrorDestination(), otherTyped.getErrorDestination(), true);
            this.computeDiff("SubDeploymentName", this.bean.getSubDeploymentName(), otherTyped.getSubDeploymentName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            GroupParamsBeanImpl original = (GroupParamsBeanImpl)event.getSourceBean();
            GroupParamsBeanImpl proposed = (GroupParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ErrorDestination")) {
                  original.setErrorDestinationAsString(proposed.getErrorDestinationAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("SubDeploymentName")) {
                  original.setSubDeploymentName(proposed.getSubDeploymentName());
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
            GroupParamsBeanImpl copy = (GroupParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ErrorDestination")) && this.bean.isErrorDestinationSet()) {
               copy._unSet(copy, 1);
               copy.setErrorDestinationAsString(this.bean.getErrorDestinationAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("SubDeploymentName")) && this.bean.isSubDeploymentNameSet()) {
               copy.setSubDeploymentName(this.bean.getSubDeploymentName());
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
         this.inferSubTree(this.bean.getErrorDestination(), clazz, annotation);
      }
   }
}

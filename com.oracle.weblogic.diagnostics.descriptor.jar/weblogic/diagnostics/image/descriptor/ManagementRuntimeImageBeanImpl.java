package weblogic.diagnostics.image.descriptor;

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

public class ManagementRuntimeImageBeanImpl extends AbstractDescriptorBean implements ManagementRuntimeImageBean, Serializable {
   private ServerRuntimeStateBean _ServerRuntimeState;
   private ServerRuntimeStatisticsBean _ServerRuntimeStatistics;
   private static SchemaHelper2 _schemaHelper;

   public ManagementRuntimeImageBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public ManagementRuntimeImageBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public ManagementRuntimeImageBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public ServerRuntimeStatisticsBean getServerRuntimeStatistics() {
      return this._ServerRuntimeStatistics;
   }

   public boolean isServerRuntimeStatisticsInherited() {
      return false;
   }

   public boolean isServerRuntimeStatisticsSet() {
      return this._isSet(0) || this._isAnythingSet((AbstractDescriptorBean)this.getServerRuntimeStatistics());
   }

   public void setServerRuntimeStatistics(ServerRuntimeStatisticsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 0)) {
         this._postCreate(_child);
      }

      ServerRuntimeStatisticsBean _oldVal = this._ServerRuntimeStatistics;
      this._ServerRuntimeStatistics = param0;
      this._postSet(0, _oldVal, param0);
   }

   public ServerRuntimeStateBean getServerRuntimeState() {
      return this._ServerRuntimeState;
   }

   public boolean isServerRuntimeStateInherited() {
      return false;
   }

   public boolean isServerRuntimeStateSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getServerRuntimeState());
   }

   public void setServerRuntimeState(ServerRuntimeStateBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      ServerRuntimeStateBean _oldVal = this._ServerRuntimeState;
      this._ServerRuntimeState = param0;
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
      return super._isAnythingSet() || this.isServerRuntimeStateSet() || this.isServerRuntimeStatisticsSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._ServerRuntimeState = new ServerRuntimeStateBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._ServerRuntimeState);
               if (initOne) {
                  break;
               }
            case 0:
               this._ServerRuntimeStatistics = new ServerRuntimeStatisticsBeanImpl(this, 0);
               this._postCreate((AbstractDescriptorBean)this._ServerRuntimeStatistics);
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image/1.0/weblogic-diagnostics-image.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image";
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
            case 20:
               if (s.equals("server-runtime-state")) {
                  return 1;
               }
               break;
            case 25:
               if (s.equals("server-runtime-statistics")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new ServerRuntimeStatisticsBeanImpl.SchemaHelper2();
            case 1:
               return new ServerRuntimeStateBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "management-runtime-image";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "server-runtime-statistics";
            case 1:
               return "server-runtime-state";
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
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ManagementRuntimeImageBeanImpl bean;

      protected Helper(ManagementRuntimeImageBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ServerRuntimeStatistics";
            case 1:
               return "ServerRuntimeState";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ServerRuntimeState")) {
            return 1;
         } else {
            return propName.equals("ServerRuntimeStatistics") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getServerRuntimeState() != null) {
            iterators.add(new ArrayIterator(new ServerRuntimeStateBean[]{this.bean.getServerRuntimeState()}));
         }

         if (this.bean.getServerRuntimeStatistics() != null) {
            iterators.add(new ArrayIterator(new ServerRuntimeStatisticsBean[]{this.bean.getServerRuntimeStatistics()}));
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
            childValue = this.computeChildHashValue(this.bean.getServerRuntimeState());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getServerRuntimeStatistics());
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
            ManagementRuntimeImageBeanImpl otherTyped = (ManagementRuntimeImageBeanImpl)other;
            this.computeSubDiff("ServerRuntimeState", this.bean.getServerRuntimeState(), otherTyped.getServerRuntimeState());
            this.computeSubDiff("ServerRuntimeStatistics", this.bean.getServerRuntimeStatistics(), otherTyped.getServerRuntimeStatistics());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ManagementRuntimeImageBeanImpl original = (ManagementRuntimeImageBeanImpl)event.getSourceBean();
            ManagementRuntimeImageBeanImpl proposed = (ManagementRuntimeImageBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ServerRuntimeState")) {
                  if (type == 2) {
                     original.setServerRuntimeState((ServerRuntimeStateBean)this.createCopy((AbstractDescriptorBean)proposed.getServerRuntimeState()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ServerRuntimeState", (DescriptorBean)original.getServerRuntimeState());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ServerRuntimeStatistics")) {
                  if (type == 2) {
                     original.setServerRuntimeStatistics((ServerRuntimeStatisticsBean)this.createCopy((AbstractDescriptorBean)proposed.getServerRuntimeStatistics()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ServerRuntimeStatistics", (DescriptorBean)original.getServerRuntimeStatistics());
                  }

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
            ManagementRuntimeImageBeanImpl copy = (ManagementRuntimeImageBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ServerRuntimeState")) && this.bean.isServerRuntimeStateSet() && !copy._isSet(1)) {
               Object o = this.bean.getServerRuntimeState();
               copy.setServerRuntimeState((ServerRuntimeStateBean)null);
               copy.setServerRuntimeState(o == null ? null : (ServerRuntimeStateBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ServerRuntimeStatistics")) && this.bean.isServerRuntimeStatisticsSet() && !copy._isSet(0)) {
               Object o = this.bean.getServerRuntimeStatistics();
               copy.setServerRuntimeStatistics((ServerRuntimeStatisticsBean)null);
               copy.setServerRuntimeStatistics(o == null ? null : (ServerRuntimeStatisticsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getServerRuntimeState(), clazz, annotation);
         this.inferSubTree(this.bean.getServerRuntimeStatistics(), clazz, annotation);
      }
   }
}

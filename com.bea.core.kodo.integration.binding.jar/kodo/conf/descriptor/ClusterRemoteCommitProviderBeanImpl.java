package kodo.conf.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class ClusterRemoteCommitProviderBeanImpl extends RemoteCommitProviderBeanImpl implements ClusterRemoteCommitProviderBean, Serializable {
   private int _BufferSize;
   private String _CacheTopics;
   private String _RecoverAction;
   private static SchemaHelper2 _schemaHelper;

   public ClusterRemoteCommitProviderBeanImpl() {
      this._initializeProperty(-1);
   }

   public ClusterRemoteCommitProviderBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ClusterRemoteCommitProviderBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getBufferSize() {
      return this._BufferSize;
   }

   public boolean isBufferSizeInherited() {
      return false;
   }

   public boolean isBufferSizeSet() {
      return this._isSet(1);
   }

   public void setBufferSize(int param0) {
      int _oldVal = this._BufferSize;
      this._BufferSize = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getCacheTopics() {
      return this._CacheTopics;
   }

   public boolean isCacheTopicsInherited() {
      return false;
   }

   public boolean isCacheTopicsSet() {
      return this._isSet(2);
   }

   public void setCacheTopics(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CacheTopics;
      this._CacheTopics = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getRecoverAction() {
      return this._RecoverAction;
   }

   public boolean isRecoverActionInherited() {
      return false;
   }

   public boolean isRecoverActionSet() {
      return this._isSet(3);
   }

   public void setRecoverAction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"none", "clear"};
      param0 = LegalChecks.checkInEnum("RecoverAction", param0, _set);
      String _oldVal = this._RecoverAction;
      this._RecoverAction = param0;
      this._postSet(3, _oldVal, param0);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._BufferSize = 10;
               if (initOne) {
                  break;
               }
            case 2:
               this._CacheTopics = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._RecoverAction = "none";
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

   public static class SchemaHelper2 extends RemoteCommitProviderBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("buffer-size")) {
                  return 1;
               }
               break;
            case 12:
               if (s.equals("cache-topics")) {
                  return 2;
               }
            case 13:
            default:
               break;
            case 14:
               if (s.equals("recover-action")) {
                  return 3;
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
            case 1:
               return "buffer-size";
            case 2:
               return "cache-topics";
            case 3:
               return "recover-action";
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
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends RemoteCommitProviderBeanImpl.Helper {
      private ClusterRemoteCommitProviderBeanImpl bean;

      protected Helper(ClusterRemoteCommitProviderBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 1:
               return "BufferSize";
            case 2:
               return "CacheTopics";
            case 3:
               return "RecoverAction";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BufferSize")) {
            return 1;
         } else if (propName.equals("CacheTopics")) {
            return 2;
         } else {
            return propName.equals("RecoverAction") ? 3 : super.getPropertyIndex(propName);
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
            if (this.bean.isBufferSizeSet()) {
               buf.append("BufferSize");
               buf.append(String.valueOf(this.bean.getBufferSize()));
            }

            if (this.bean.isCacheTopicsSet()) {
               buf.append("CacheTopics");
               buf.append(String.valueOf(this.bean.getCacheTopics()));
            }

            if (this.bean.isRecoverActionSet()) {
               buf.append("RecoverAction");
               buf.append(String.valueOf(this.bean.getRecoverAction()));
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
            ClusterRemoteCommitProviderBeanImpl otherTyped = (ClusterRemoteCommitProviderBeanImpl)other;
            this.computeDiff("BufferSize", this.bean.getBufferSize(), otherTyped.getBufferSize(), false);
            this.computeDiff("CacheTopics", this.bean.getCacheTopics(), otherTyped.getCacheTopics(), false);
            this.computeDiff("RecoverAction", this.bean.getRecoverAction(), otherTyped.getRecoverAction(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ClusterRemoteCommitProviderBeanImpl original = (ClusterRemoteCommitProviderBeanImpl)event.getSourceBean();
            ClusterRemoteCommitProviderBeanImpl proposed = (ClusterRemoteCommitProviderBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BufferSize")) {
                  original.setBufferSize(proposed.getBufferSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("CacheTopics")) {
                  original.setCacheTopics(proposed.getCacheTopics());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("RecoverAction")) {
                  original.setRecoverAction(proposed.getRecoverAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            ClusterRemoteCommitProviderBeanImpl copy = (ClusterRemoteCommitProviderBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BufferSize")) && this.bean.isBufferSizeSet()) {
               copy.setBufferSize(this.bean.getBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheTopics")) && this.bean.isCacheTopicsSet()) {
               copy.setCacheTopics(this.bean.getCacheTopics());
            }

            if ((excludeProps == null || !excludeProps.contains("RecoverAction")) && this.bean.isRecoverActionSet()) {
               copy.setRecoverAction(this.bean.getRecoverAction());
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

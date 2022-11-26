package weblogic.j2ee.descriptor.wl;

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

public class QuotaBeanImpl extends NamedEntityBeanImpl implements QuotaBean, Serializable {
   private long _BytesMaximum;
   private long _MessagesMaximum;
   private String _Policy;
   private boolean _Shared;
   private static SchemaHelper2 _schemaHelper;

   public QuotaBeanImpl() {
      this._initializeProperty(-1);
   }

   public QuotaBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public QuotaBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public long getBytesMaximum() {
      return this._BytesMaximum;
   }

   public boolean isBytesMaximumInherited() {
      return false;
   }

   public boolean isBytesMaximumSet() {
      return this._isSet(3);
   }

   public void setBytesMaximum(long param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("BytesMaximum", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._BytesMaximum;
      this._BytesMaximum = param0;
      this._postSet(3, _oldVal, param0);
   }

   public long getMessagesMaximum() {
      return this._MessagesMaximum;
   }

   public boolean isMessagesMaximumInherited() {
      return false;
   }

   public boolean isMessagesMaximumSet() {
      return this._isSet(4);
   }

   public void setMessagesMaximum(long param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("MessagesMaximum", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._MessagesMaximum;
      this._MessagesMaximum = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getPolicy() {
      return this._Policy;
   }

   public boolean isPolicyInherited() {
      return false;
   }

   public boolean isPolicySet() {
      return this._isSet(5);
   }

   public void setPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"FIFO", "Preemptive"};
      param0 = LegalChecks.checkInEnum("Policy", param0, _set);
      String _oldVal = this._Policy;
      this._Policy = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean isShared() {
      return this._Shared;
   }

   public boolean isSharedInherited() {
      return false;
   }

   public boolean isSharedSet() {
      return this._isSet(6);
   }

   public void setShared(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._Shared;
      this._Shared = param0;
      this._postSet(6, _oldVal, param0);
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._BytesMaximum = Long.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 4:
               this._MessagesMaximum = Long.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 5:
               this._Policy = "FIFO";
               if (initOne) {
                  break;
               }
            case 6:
               this._Shared = true;
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

   public static class SchemaHelper2 extends NamedEntityBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 6:
               if (s.equals("policy")) {
                  return 5;
               }

               if (s.equals("shared")) {
                  return 6;
               }
               break;
            case 13:
               if (s.equals("bytes-maximum")) {
                  return 3;
               }
               break;
            case 16:
               if (s.equals("messages-maximum")) {
                  return 4;
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
            case 3:
               return "bytes-maximum";
            case 4:
               return "messages-maximum";
            case 5:
               return "policy";
            case 6:
               return "shared";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends NamedEntityBeanImpl.Helper {
      private QuotaBeanImpl bean;

      protected Helper(QuotaBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "BytesMaximum";
            case 4:
               return "MessagesMaximum";
            case 5:
               return "Policy";
            case 6:
               return "Shared";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BytesMaximum")) {
            return 3;
         } else if (propName.equals("MessagesMaximum")) {
            return 4;
         } else if (propName.equals("Policy")) {
            return 5;
         } else {
            return propName.equals("Shared") ? 6 : super.getPropertyIndex(propName);
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
            if (this.bean.isBytesMaximumSet()) {
               buf.append("BytesMaximum");
               buf.append(String.valueOf(this.bean.getBytesMaximum()));
            }

            if (this.bean.isMessagesMaximumSet()) {
               buf.append("MessagesMaximum");
               buf.append(String.valueOf(this.bean.getMessagesMaximum()));
            }

            if (this.bean.isPolicySet()) {
               buf.append("Policy");
               buf.append(String.valueOf(this.bean.getPolicy()));
            }

            if (this.bean.isSharedSet()) {
               buf.append("Shared");
               buf.append(String.valueOf(this.bean.isShared()));
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
            QuotaBeanImpl otherTyped = (QuotaBeanImpl)other;
            this.computeDiff("BytesMaximum", this.bean.getBytesMaximum(), otherTyped.getBytesMaximum(), true);
            this.computeDiff("MessagesMaximum", this.bean.getMessagesMaximum(), otherTyped.getMessagesMaximum(), true);
            this.computeDiff("Policy", this.bean.getPolicy(), otherTyped.getPolicy(), true);
            this.computeDiff("Shared", this.bean.isShared(), otherTyped.isShared(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            QuotaBeanImpl original = (QuotaBeanImpl)event.getSourceBean();
            QuotaBeanImpl proposed = (QuotaBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BytesMaximum")) {
                  original.setBytesMaximum(proposed.getBytesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("MessagesMaximum")) {
                  original.setMessagesMaximum(proposed.getMessagesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Policy")) {
                  original.setPolicy(proposed.getPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Shared")) {
                  original.setShared(proposed.isShared());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
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
            QuotaBeanImpl copy = (QuotaBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BytesMaximum")) && this.bean.isBytesMaximumSet()) {
               copy.setBytesMaximum(this.bean.getBytesMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagesMaximum")) && this.bean.isMessagesMaximumSet()) {
               copy.setMessagesMaximum(this.bean.getMessagesMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("Policy")) && this.bean.isPolicySet()) {
               copy.setPolicy(this.bean.getPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("Shared")) && this.bean.isSharedSet()) {
               copy.setShared(this.bean.isShared());
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

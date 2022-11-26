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

public class AddressingBeanImpl extends AbstractDescriptorBean implements AddressingBean, Serializable {
   private boolean _Enabled;
   private boolean _Required;
   private String _Responses;
   private static SchemaHelper2 _schemaHelper;

   public AddressingBeanImpl() {
      this._initializeProperty(-1);
   }

   public AddressingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AddressingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(0);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean isRequired() {
      return this._Required;
   }

   public boolean isRequiredInherited() {
      return false;
   }

   public boolean isRequiredSet() {
      return this._isSet(1);
   }

   public void setRequired(boolean param0) {
      boolean _oldVal = this._Required;
      this._Required = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getResponses() {
      return this._Responses;
   }

   public boolean isResponsesInherited() {
      return false;
   }

   public boolean isResponsesSet() {
      return this._isSet(2);
   }

   public void setResponses(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Responses;
      this._Responses = param0;
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._Responses = "ALL";
               if (initOne) {
                  break;
               }
            case 0:
               this._Enabled = true;
               if (initOne) {
                  break;
               }
            case 1:
               this._Required = false;
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
            case 7:
               if (s.equals("enabled")) {
                  return 0;
               }
               break;
            case 8:
               if (s.equals("required")) {
                  return 1;
               }
               break;
            case 9:
               if (s.equals("responses")) {
                  return 2;
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
               return "enabled";
            case 1:
               return "required";
            case 2:
               return "responses";
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AddressingBeanImpl bean;

      protected Helper(AddressingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Enabled";
            case 1:
               return "Required";
            case 2:
               return "Responses";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Responses")) {
            return 2;
         } else if (propName.equals("Enabled")) {
            return 0;
         } else {
            return propName.equals("Required") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isResponsesSet()) {
               buf.append("Responses");
               buf.append(String.valueOf(this.bean.getResponses()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
            }

            if (this.bean.isRequiredSet()) {
               buf.append("Required");
               buf.append(String.valueOf(this.bean.isRequired()));
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
            AddressingBeanImpl otherTyped = (AddressingBeanImpl)other;
            this.computeDiff("Responses", this.bean.getResponses(), otherTyped.getResponses(), false);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), false);
            this.computeDiff("Required", this.bean.isRequired(), otherTyped.isRequired(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AddressingBeanImpl original = (AddressingBeanImpl)event.getSourceBean();
            AddressingBeanImpl proposed = (AddressingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Responses")) {
                  original.setResponses(proposed.getResponses());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.isEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Required")) {
                  original.setRequired(proposed.isRequired());
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
            AddressingBeanImpl copy = (AddressingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Responses")) && this.bean.isResponsesSet()) {
               copy.setResponses(this.bean.getResponses());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("Required")) && this.bean.isRequiredSet()) {
               copy.setRequired(this.bean.isRequired());
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

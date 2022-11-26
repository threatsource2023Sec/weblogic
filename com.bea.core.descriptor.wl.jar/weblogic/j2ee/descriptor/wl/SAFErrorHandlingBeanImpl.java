package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class SAFErrorHandlingBeanImpl extends NamedEntityBeanImpl implements SAFErrorHandlingBean, Serializable {
   private String _LogFormat;
   private String _Policy;
   private SAFDestinationBean _SAFErrorDestination;
   private static SchemaHelper2 _schemaHelper;

   public SAFErrorHandlingBeanImpl() {
      this._initializeProperty(-1);
   }

   public SAFErrorHandlingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SAFErrorHandlingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getPolicy() {
      return this._Policy;
   }

   public boolean isPolicyInherited() {
      return false;
   }

   public boolean isPolicySet() {
      return this._isSet(3);
   }

   public void setPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Discard", "Log", "Redirect", "Always-Forward"};
      param0 = LegalChecks.checkInEnum("Policy", param0, _set);
      LegalChecks.checkNonNull("Policy", param0);
      String _oldVal = this._Policy;
      this._Policy = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getLogFormat() {
      return this._LogFormat;
   }

   public boolean isLogFormatInherited() {
      return false;
   }

   public boolean isLogFormatSet() {
      return this._isSet(4);
   }

   public void setLogFormat(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LogFormat;
      this._LogFormat = param0;
      this._postSet(4, _oldVal, param0);
   }

   public SAFDestinationBean getSAFErrorDestination() {
      return this._SAFErrorDestination;
   }

   public String getSAFErrorDestinationAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getSAFErrorDestination();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isSAFErrorDestinationInherited() {
      return false;
   }

   public boolean isSAFErrorDestinationSet() {
      return this._isSet(5);
   }

   public void setSAFErrorDestinationAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, SAFDestinationBean.class, new ReferenceManager.Resolver(this, 5) {
            public void resolveReference(Object value) {
               try {
                  SAFErrorHandlingBeanImpl.this.setSAFErrorDestination((SAFDestinationBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         SAFDestinationBean _oldVal = this._SAFErrorDestination;
         this._initializeProperty(5);
         this._postSet(5, _oldVal, this._SAFErrorDestination);
      }

   }

   public void setSAFErrorDestination(SAFDestinationBean param0) throws IllegalArgumentException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 5, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return SAFErrorHandlingBeanImpl.this.getSAFErrorDestination();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      SAFDestinationBean _oldVal = this._SAFErrorDestination;
      this._SAFErrorDestination = param0;
      this._postSet(5, _oldVal, param0);
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._LogFormat = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._Policy = "Discard";
               if (initOne) {
                  break;
               }
            case 5:
               this._SAFErrorDestination = null;
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

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("Policy", "Discard");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property Policy in SAFErrorHandlingBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends NamedEntityBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 6:
               if (s.equals("policy")) {
                  return 3;
               }
               break;
            case 10:
               if (s.equals("log-format")) {
                  return 4;
               }
               break;
            case 21:
               if (s.equals("saf-error-destination")) {
                  return 5;
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
               return "policy";
            case 4:
               return "log-format";
            case 5:
               return "saf-error-destination";
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

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
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
      private SAFErrorHandlingBeanImpl bean;

      protected Helper(SAFErrorHandlingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "Policy";
            case 4:
               return "LogFormat";
            case 5:
               return "SAFErrorDestination";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("LogFormat")) {
            return 4;
         } else if (propName.equals("Policy")) {
            return 3;
         } else {
            return propName.equals("SAFErrorDestination") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isLogFormatSet()) {
               buf.append("LogFormat");
               buf.append(String.valueOf(this.bean.getLogFormat()));
            }

            if (this.bean.isPolicySet()) {
               buf.append("Policy");
               buf.append(String.valueOf(this.bean.getPolicy()));
            }

            if (this.bean.isSAFErrorDestinationSet()) {
               buf.append("SAFErrorDestination");
               buf.append(String.valueOf(this.bean.getSAFErrorDestination()));
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
            SAFErrorHandlingBeanImpl otherTyped = (SAFErrorHandlingBeanImpl)other;
            this.computeDiff("LogFormat", this.bean.getLogFormat(), otherTyped.getLogFormat(), false);
            this.computeDiff("Policy", this.bean.getPolicy(), otherTyped.getPolicy(), false);
            this.computeDiff("SAFErrorDestination", this.bean.getSAFErrorDestination(), otherTyped.getSAFErrorDestination(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SAFErrorHandlingBeanImpl original = (SAFErrorHandlingBeanImpl)event.getSourceBean();
            SAFErrorHandlingBeanImpl proposed = (SAFErrorHandlingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("LogFormat")) {
                  original.setLogFormat(proposed.getLogFormat());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Policy")) {
                  original.setPolicy(proposed.getPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("SAFErrorDestination")) {
                  original.setSAFErrorDestinationAsString(proposed.getSAFErrorDestinationAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
            SAFErrorHandlingBeanImpl copy = (SAFErrorHandlingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("LogFormat")) && this.bean.isLogFormatSet()) {
               copy.setLogFormat(this.bean.getLogFormat());
            }

            if ((excludeProps == null || !excludeProps.contains("Policy")) && this.bean.isPolicySet()) {
               copy.setPolicy(this.bean.getPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("SAFErrorDestination")) && this.bean.isSAFErrorDestinationSet()) {
               copy._unSet(copy, 5);
               copy.setSAFErrorDestinationAsString(this.bean.getSAFErrorDestinationAsString());
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
         this.inferSubTree(this.bean.getSAFErrorDestination(), clazz, annotation);
      }
   }
}

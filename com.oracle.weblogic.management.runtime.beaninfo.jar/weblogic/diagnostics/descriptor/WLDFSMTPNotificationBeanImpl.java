package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
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
import weblogic.diagnostics.descriptor.validation.WatchNotificationValidators;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class WLDFSMTPNotificationBeanImpl extends WLDFNotificationBeanImpl implements WLDFSMTPNotificationBean, Serializable {
   private String _Body;
   private String _MailSessionJNDIName;
   private String[] _Recipients;
   private String _Subject;
   private static SchemaHelper2 _schemaHelper;

   public WLDFSMTPNotificationBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFSMTPNotificationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFSMTPNotificationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getMailSessionJNDIName() {
      return this._MailSessionJNDIName;
   }

   public boolean isMailSessionJNDINameInherited() {
      return false;
   }

   public boolean isMailSessionJNDINameSet() {
      return this._isSet(4);
   }

   public void setMailSessionJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("MailSessionJNDIName", param0);
      LegalChecks.checkNonNull("MailSessionJNDIName", param0);
      String _oldVal = this._MailSessionJNDIName;
      this._MailSessionJNDIName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getSubject() {
      return this._Subject;
   }

   public boolean isSubjectInherited() {
      return false;
   }

   public boolean isSubjectSet() {
      return this._isSet(5);
   }

   public void setSubject(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Subject;
      this._Subject = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getBody() {
      return this._Body;
   }

   public boolean isBodyInherited() {
      return false;
   }

   public boolean isBodySet() {
      return this._isSet(6);
   }

   public void setBody(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Body;
      this._Body = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String[] getRecipients() {
      return this._Recipients;
   }

   public boolean isRecipientsInherited() {
      return false;
   }

   public boolean isRecipientsSet() {
      return this._isSet(7);
   }

   public void setRecipients(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      WatchNotificationValidators.validateRecipients(param0);
      String[] _oldVal = this._Recipients;
      this._Recipients = param0;
      this._postSet(7, _oldVal, param0);
   }

   public void addRecipient(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(7)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getRecipients(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setRecipients(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeRecipient(String param0) {
      String[] _old = this.getRecipients();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setRecipients(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("MailSessionJNDIName", this.isMailSessionJNDINameSet());
      LegalChecks.checkIsSet("Recipients", this.isRecipientsSet());
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
         idx = 6;
      }

      try {
         switch (idx) {
            case 6:
               this._Body = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._MailSessionJNDIName = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Recipients = new String[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._Subject = null;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/1.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends WLDFNotificationBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("body")) {
                  return 6;
               }
               break;
            case 7:
               if (s.equals("subject")) {
                  return 5;
               }
               break;
            case 9:
               if (s.equals("recipient")) {
                  return 7;
               }
               break;
            case 22:
               if (s.equals("mail-session-jndi-name")) {
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
            case 4:
               return "mail-session-jndi-name";
            case 5:
               return "subject";
            case 6:
               return "body";
            case 7:
               return "recipient";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 7:
               return true;
            default:
               return super.isArray(propIndex);
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends WLDFNotificationBeanImpl.Helper {
      private WLDFSMTPNotificationBeanImpl bean;

      protected Helper(WLDFSMTPNotificationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "MailSessionJNDIName";
            case 5:
               return "Subject";
            case 6:
               return "Body";
            case 7:
               return "Recipients";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Body")) {
            return 6;
         } else if (propName.equals("MailSessionJNDIName")) {
            return 4;
         } else if (propName.equals("Recipients")) {
            return 7;
         } else {
            return propName.equals("Subject") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isBodySet()) {
               buf.append("Body");
               buf.append(String.valueOf(this.bean.getBody()));
            }

            if (this.bean.isMailSessionJNDINameSet()) {
               buf.append("MailSessionJNDIName");
               buf.append(String.valueOf(this.bean.getMailSessionJNDIName()));
            }

            if (this.bean.isRecipientsSet()) {
               buf.append("Recipients");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getRecipients())));
            }

            if (this.bean.isSubjectSet()) {
               buf.append("Subject");
               buf.append(String.valueOf(this.bean.getSubject()));
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
            WLDFSMTPNotificationBeanImpl otherTyped = (WLDFSMTPNotificationBeanImpl)other;
            this.computeDiff("Body", this.bean.getBody(), otherTyped.getBody(), true);
            this.computeDiff("MailSessionJNDIName", this.bean.getMailSessionJNDIName(), otherTyped.getMailSessionJNDIName(), true);
            this.computeDiff("Recipients", this.bean.getRecipients(), otherTyped.getRecipients(), true);
            this.computeDiff("Subject", this.bean.getSubject(), otherTyped.getSubject(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFSMTPNotificationBeanImpl original = (WLDFSMTPNotificationBeanImpl)event.getSourceBean();
            WLDFSMTPNotificationBeanImpl proposed = (WLDFSMTPNotificationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Body")) {
                  original.setBody(proposed.getBody());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("MailSessionJNDIName")) {
                  original.setMailSessionJNDIName(proposed.getMailSessionJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Recipients")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addRecipient((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeRecipient((String)update.getRemovedObject());
                  }

                  if (original.getRecipients() == null || original.getRecipients().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
                  }
               } else if (prop.equals("Subject")) {
                  original.setSubject(proposed.getSubject());
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
            WLDFSMTPNotificationBeanImpl copy = (WLDFSMTPNotificationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Body")) && this.bean.isBodySet()) {
               copy.setBody(this.bean.getBody());
            }

            if ((excludeProps == null || !excludeProps.contains("MailSessionJNDIName")) && this.bean.isMailSessionJNDINameSet()) {
               copy.setMailSessionJNDIName(this.bean.getMailSessionJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("Recipients")) && this.bean.isRecipientsSet()) {
               Object o = this.bean.getRecipients();
               copy.setRecipients(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Subject")) && this.bean.isSubjectSet()) {
               copy.setSubject(this.bean.getSubject());
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

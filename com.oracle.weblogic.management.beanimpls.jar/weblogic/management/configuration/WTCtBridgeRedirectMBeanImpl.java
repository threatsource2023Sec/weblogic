package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class WTCtBridgeRedirectMBeanImpl extends ConfigurationMBeanImpl implements WTCtBridgeRedirectMBean, Serializable {
   private String _Direction;
   private String _MetaDataFile;
   private String _ReplyQ;
   private String _SourceAccessPoint;
   private String _SourceName;
   private String _SourceQspace;
   private String _TargetAccessPoint;
   private String _TargetName;
   private String _TargetQspace;
   private String _TranslateFML;
   private static SchemaHelper2 _schemaHelper;

   public WTCtBridgeRedirectMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WTCtBridgeRedirectMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WTCtBridgeRedirectMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setDirection(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"JmsQ2TuxQ", "TuxQ2JmsQ", "JmsQ2TuxS", "JmsQ2JmsQ"};
      param0 = LegalChecks.checkInEnum("Direction", param0, _set);
      LegalChecks.checkNonNull("Direction", param0);
      String _oldVal = this._Direction;
      this._Direction = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getDirection() {
      return this._Direction;
   }

   public boolean isDirectionInherited() {
      return false;
   }

   public boolean isDirectionSet() {
      return this._isSet(10);
   }

   public void setTranslateFML(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"NO", "FLAT", "WLXT"};
      param0 = LegalChecks.checkInEnum("TranslateFML", param0, _set);
      String _oldVal = this._TranslateFML;
      this._TranslateFML = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getTranslateFML() {
      return this._TranslateFML;
   }

   public boolean isTranslateFMLInherited() {
      return false;
   }

   public boolean isTranslateFMLSet() {
      return this._isSet(11);
   }

   public void setMetaDataFile(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MetaDataFile;
      this._MetaDataFile = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getMetaDataFile() {
      return this._MetaDataFile;
   }

   public boolean isMetaDataFileInherited() {
      return false;
   }

   public boolean isMetaDataFileSet() {
      return this._isSet(12);
   }

   public void setReplyQ(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ReplyQ;
      this._ReplyQ = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getReplyQ() {
      return this._ReplyQ;
   }

   public boolean isReplyQInherited() {
      return false;
   }

   public boolean isReplyQSet() {
      return this._isSet(13);
   }

   public void setSourceAccessPoint(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SourceAccessPoint;
      this._SourceAccessPoint = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getSourceAccessPoint() {
      return this._SourceAccessPoint;
   }

   public boolean isSourceAccessPointInherited() {
      return false;
   }

   public boolean isSourceAccessPointSet() {
      return this._isSet(14);
   }

   public void setSourceQspace(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SourceQspace;
      this._SourceQspace = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getSourceQspace() {
      return this._SourceQspace;
   }

   public boolean isSourceQspaceInherited() {
      return false;
   }

   public boolean isSourceQspaceSet() {
      return this._isSet(15);
   }

   public void setSourceName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("SourceName", param0);
      String _oldVal = this._SourceName;
      this._SourceName = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getSourceName() {
      return this._SourceName;
   }

   public boolean isSourceNameInherited() {
      return false;
   }

   public boolean isSourceNameSet() {
      return this._isSet(16);
   }

   public void setTargetAccessPoint(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TargetAccessPoint;
      this._TargetAccessPoint = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getTargetAccessPoint() {
      return this._TargetAccessPoint;
   }

   public boolean isTargetAccessPointInherited() {
      return false;
   }

   public boolean isTargetAccessPointSet() {
      return this._isSet(17);
   }

   public void setTargetQspace(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TargetQspace;
      this._TargetQspace = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getTargetQspace() {
      return this._TargetQspace;
   }

   public boolean isTargetQspaceInherited() {
      return false;
   }

   public boolean isTargetQspaceSet() {
      return this._isSet(18);
   }

   public void setTargetName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("TargetName", param0);
      String _oldVal = this._TargetName;
      this._TargetName = param0;
      this._postSet(19, _oldVal, param0);
   }

   public String getTargetName() {
      return this._TargetName;
   }

   public boolean isTargetNameInherited() {
      return false;
   }

   public boolean isTargetNameSet() {
      return this._isSet(19);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._Direction = "JmsQ2TuxQ";
               if (initOne) {
                  break;
               }
            case 12:
               this._MetaDataFile = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._ReplyQ = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._SourceAccessPoint = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._SourceName = "mySource";
               if (initOne) {
                  break;
               }
            case 15:
               this._SourceQspace = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._TargetAccessPoint = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._TargetName = "myTarget";
               if (initOne) {
                  break;
               }
            case 18:
               this._TargetQspace = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._TranslateFML = "NO";
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
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "WTCtBridgeRedirect";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("Direction")) {
         oldVal = this._Direction;
         this._Direction = (String)v;
         this._postSet(10, oldVal, this._Direction);
      } else if (name.equals("MetaDataFile")) {
         oldVal = this._MetaDataFile;
         this._MetaDataFile = (String)v;
         this._postSet(12, oldVal, this._MetaDataFile);
      } else if (name.equals("ReplyQ")) {
         oldVal = this._ReplyQ;
         this._ReplyQ = (String)v;
         this._postSet(13, oldVal, this._ReplyQ);
      } else if (name.equals("SourceAccessPoint")) {
         oldVal = this._SourceAccessPoint;
         this._SourceAccessPoint = (String)v;
         this._postSet(14, oldVal, this._SourceAccessPoint);
      } else if (name.equals("SourceName")) {
         oldVal = this._SourceName;
         this._SourceName = (String)v;
         this._postSet(16, oldVal, this._SourceName);
      } else if (name.equals("SourceQspace")) {
         oldVal = this._SourceQspace;
         this._SourceQspace = (String)v;
         this._postSet(15, oldVal, this._SourceQspace);
      } else if (name.equals("TargetAccessPoint")) {
         oldVal = this._TargetAccessPoint;
         this._TargetAccessPoint = (String)v;
         this._postSet(17, oldVal, this._TargetAccessPoint);
      } else if (name.equals("TargetName")) {
         oldVal = this._TargetName;
         this._TargetName = (String)v;
         this._postSet(19, oldVal, this._TargetName);
      } else if (name.equals("TargetQspace")) {
         oldVal = this._TargetQspace;
         this._TargetQspace = (String)v;
         this._postSet(18, oldVal, this._TargetQspace);
      } else if (name.equals("TranslateFML")) {
         oldVal = this._TranslateFML;
         this._TranslateFML = (String)v;
         this._postSet(11, oldVal, this._TranslateFML);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Direction")) {
         return this._Direction;
      } else if (name.equals("MetaDataFile")) {
         return this._MetaDataFile;
      } else if (name.equals("ReplyQ")) {
         return this._ReplyQ;
      } else if (name.equals("SourceAccessPoint")) {
         return this._SourceAccessPoint;
      } else if (name.equals("SourceName")) {
         return this._SourceName;
      } else if (name.equals("SourceQspace")) {
         return this._SourceQspace;
      } else if (name.equals("TargetAccessPoint")) {
         return this._TargetAccessPoint;
      } else if (name.equals("TargetName")) {
         return this._TargetName;
      } else if (name.equals("TargetQspace")) {
         return this._TargetQspace;
      } else {
         return name.equals("TranslateFML") ? this._TranslateFML : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("Direction", "JmsQ2TuxQ");
      } catch (IllegalArgumentException var3) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property Direction in WTCtBridgeRedirectMBean" + var3.getMessage());
      }

      try {
         LegalChecks.checkNonNull("SourceName", "mySource");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property SourceName in WTCtBridgeRedirectMBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonNull("TargetName", "myTarget");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property TargetName in WTCtBridgeRedirectMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 6:
               if (s.equals("replyq")) {
                  return 13;
               }
            case 7:
            case 8:
            case 10:
            case 15:
            case 16:
            case 17:
            case 18:
            default:
               break;
            case 9:
               if (s.equals("direction")) {
                  return 10;
               }
               break;
            case 11:
               if (s.equals("source-name")) {
                  return 16;
               }

               if (s.equals("target-name")) {
                  return 19;
               }
               break;
            case 12:
               if (s.equals("translatefml")) {
                  return 11;
               }
               break;
            case 13:
               if (s.equals("source-qspace")) {
                  return 15;
               }

               if (s.equals("target-qspace")) {
                  return 18;
               }
               break;
            case 14:
               if (s.equals("meta-data-file")) {
                  return 12;
               }
               break;
            case 19:
               if (s.equals("source-access-point")) {
                  return 14;
               }

               if (s.equals("target-access-point")) {
                  return 17;
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
            case 10:
               return "direction";
            case 11:
               return "translatefml";
            case 12:
               return "meta-data-file";
            case 13:
               return "replyq";
            case 14:
               return "source-access-point";
            case 15:
               return "source-qspace";
            case 16:
               return "source-name";
            case 17:
               return "target-access-point";
            case 18:
               return "target-qspace";
            case 19:
               return "target-name";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private WTCtBridgeRedirectMBeanImpl bean;

      protected Helper(WTCtBridgeRedirectMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Direction";
            case 11:
               return "TranslateFML";
            case 12:
               return "MetaDataFile";
            case 13:
               return "ReplyQ";
            case 14:
               return "SourceAccessPoint";
            case 15:
               return "SourceQspace";
            case 16:
               return "SourceName";
            case 17:
               return "TargetAccessPoint";
            case 18:
               return "TargetQspace";
            case 19:
               return "TargetName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Direction")) {
            return 10;
         } else if (propName.equals("MetaDataFile")) {
            return 12;
         } else if (propName.equals("ReplyQ")) {
            return 13;
         } else if (propName.equals("SourceAccessPoint")) {
            return 14;
         } else if (propName.equals("SourceName")) {
            return 16;
         } else if (propName.equals("SourceQspace")) {
            return 15;
         } else if (propName.equals("TargetAccessPoint")) {
            return 17;
         } else if (propName.equals("TargetName")) {
            return 19;
         } else if (propName.equals("TargetQspace")) {
            return 18;
         } else {
            return propName.equals("TranslateFML") ? 11 : super.getPropertyIndex(propName);
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
            if (this.bean.isDirectionSet()) {
               buf.append("Direction");
               buf.append(String.valueOf(this.bean.getDirection()));
            }

            if (this.bean.isMetaDataFileSet()) {
               buf.append("MetaDataFile");
               buf.append(String.valueOf(this.bean.getMetaDataFile()));
            }

            if (this.bean.isReplyQSet()) {
               buf.append("ReplyQ");
               buf.append(String.valueOf(this.bean.getReplyQ()));
            }

            if (this.bean.isSourceAccessPointSet()) {
               buf.append("SourceAccessPoint");
               buf.append(String.valueOf(this.bean.getSourceAccessPoint()));
            }

            if (this.bean.isSourceNameSet()) {
               buf.append("SourceName");
               buf.append(String.valueOf(this.bean.getSourceName()));
            }

            if (this.bean.isSourceQspaceSet()) {
               buf.append("SourceQspace");
               buf.append(String.valueOf(this.bean.getSourceQspace()));
            }

            if (this.bean.isTargetAccessPointSet()) {
               buf.append("TargetAccessPoint");
               buf.append(String.valueOf(this.bean.getTargetAccessPoint()));
            }

            if (this.bean.isTargetNameSet()) {
               buf.append("TargetName");
               buf.append(String.valueOf(this.bean.getTargetName()));
            }

            if (this.bean.isTargetQspaceSet()) {
               buf.append("TargetQspace");
               buf.append(String.valueOf(this.bean.getTargetQspace()));
            }

            if (this.bean.isTranslateFMLSet()) {
               buf.append("TranslateFML");
               buf.append(String.valueOf(this.bean.getTranslateFML()));
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
            WTCtBridgeRedirectMBeanImpl otherTyped = (WTCtBridgeRedirectMBeanImpl)other;
            this.computeDiff("Direction", this.bean.getDirection(), otherTyped.getDirection(), true);
            this.computeDiff("MetaDataFile", this.bean.getMetaDataFile(), otherTyped.getMetaDataFile(), true);
            this.computeDiff("ReplyQ", this.bean.getReplyQ(), otherTyped.getReplyQ(), true);
            this.computeDiff("SourceAccessPoint", this.bean.getSourceAccessPoint(), otherTyped.getSourceAccessPoint(), true);
            this.computeDiff("SourceName", this.bean.getSourceName(), otherTyped.getSourceName(), true);
            this.computeDiff("SourceQspace", this.bean.getSourceQspace(), otherTyped.getSourceQspace(), true);
            this.computeDiff("TargetAccessPoint", this.bean.getTargetAccessPoint(), otherTyped.getTargetAccessPoint(), true);
            this.computeDiff("TargetName", this.bean.getTargetName(), otherTyped.getTargetName(), true);
            this.computeDiff("TargetQspace", this.bean.getTargetQspace(), otherTyped.getTargetQspace(), true);
            this.computeDiff("TranslateFML", this.bean.getTranslateFML(), otherTyped.getTranslateFML(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WTCtBridgeRedirectMBeanImpl original = (WTCtBridgeRedirectMBeanImpl)event.getSourceBean();
            WTCtBridgeRedirectMBeanImpl proposed = (WTCtBridgeRedirectMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Direction")) {
                  original.setDirection(proposed.getDirection());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MetaDataFile")) {
                  original.setMetaDataFile(proposed.getMetaDataFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ReplyQ")) {
                  original.setReplyQ(proposed.getReplyQ());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("SourceAccessPoint")) {
                  original.setSourceAccessPoint(proposed.getSourceAccessPoint());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("SourceName")) {
                  original.setSourceName(proposed.getSourceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("SourceQspace")) {
                  original.setSourceQspace(proposed.getSourceQspace());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("TargetAccessPoint")) {
                  original.setTargetAccessPoint(proposed.getTargetAccessPoint());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("TargetName")) {
                  original.setTargetName(proposed.getTargetName());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("TargetQspace")) {
                  original.setTargetQspace(proposed.getTargetQspace());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("TranslateFML")) {
                  original.setTranslateFML(proposed.getTranslateFML());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            WTCtBridgeRedirectMBeanImpl copy = (WTCtBridgeRedirectMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Direction")) && this.bean.isDirectionSet()) {
               copy.setDirection(this.bean.getDirection());
            }

            if ((excludeProps == null || !excludeProps.contains("MetaDataFile")) && this.bean.isMetaDataFileSet()) {
               copy.setMetaDataFile(this.bean.getMetaDataFile());
            }

            if ((excludeProps == null || !excludeProps.contains("ReplyQ")) && this.bean.isReplyQSet()) {
               copy.setReplyQ(this.bean.getReplyQ());
            }

            if ((excludeProps == null || !excludeProps.contains("SourceAccessPoint")) && this.bean.isSourceAccessPointSet()) {
               copy.setSourceAccessPoint(this.bean.getSourceAccessPoint());
            }

            if ((excludeProps == null || !excludeProps.contains("SourceName")) && this.bean.isSourceNameSet()) {
               copy.setSourceName(this.bean.getSourceName());
            }

            if ((excludeProps == null || !excludeProps.contains("SourceQspace")) && this.bean.isSourceQspaceSet()) {
               copy.setSourceQspace(this.bean.getSourceQspace());
            }

            if ((excludeProps == null || !excludeProps.contains("TargetAccessPoint")) && this.bean.isTargetAccessPointSet()) {
               copy.setTargetAccessPoint(this.bean.getTargetAccessPoint());
            }

            if ((excludeProps == null || !excludeProps.contains("TargetName")) && this.bean.isTargetNameSet()) {
               copy.setTargetName(this.bean.getTargetName());
            }

            if ((excludeProps == null || !excludeProps.contains("TargetQspace")) && this.bean.isTargetQspaceSet()) {
               copy.setTargetQspace(this.bean.getTargetQspace());
            }

            if ((excludeProps == null || !excludeProps.contains("TranslateFML")) && this.bean.isTranslateFMLSet()) {
               copy.setTranslateFML(this.bean.getTranslateFML());
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

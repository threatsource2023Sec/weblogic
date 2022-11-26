package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class WTCResourcesMBeanImpl extends ConfigurationMBeanImpl implements WTCResourcesMBean, Serializable {
   private String _AppPassword;
   private String _AppPasswordIV;
   private String[] _FldTbl16Classes;
   private String[] _FldTbl32Classes;
   private String _MBEncodingMapFile;
   private String _RemoteMBEncoding;
   private String _TpUsrFile;
   private String[] _ViewTbl16Classes;
   private String[] _ViewTbl32Classes;
   private static SchemaHelper2 _schemaHelper;

   public WTCResourcesMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WTCResourcesMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WTCResourcesMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setFldTbl16Classes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._FldTbl16Classes;
      this._FldTbl16Classes = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String[] getFldTbl16Classes() {
      return this._FldTbl16Classes;
   }

   public boolean isFldTbl16ClassesInherited() {
      return false;
   }

   public boolean isFldTbl16ClassesSet() {
      return this._isSet(10);
   }

   public void setFldTbl32Classes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._FldTbl32Classes;
      this._FldTbl32Classes = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String[] getFldTbl32Classes() {
      return this._FldTbl32Classes;
   }

   public boolean isFldTbl32ClassesInherited() {
      return false;
   }

   public boolean isFldTbl32ClassesSet() {
      return this._isSet(11);
   }

   public void setViewTbl16Classes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ViewTbl16Classes;
      this._ViewTbl16Classes = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String[] getViewTbl16Classes() {
      return this._ViewTbl16Classes;
   }

   public boolean isViewTbl16ClassesInherited() {
      return false;
   }

   public boolean isViewTbl16ClassesSet() {
      return this._isSet(12);
   }

   public void setViewTbl32Classes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ViewTbl32Classes;
      this._ViewTbl32Classes = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String[] getViewTbl32Classes() {
      return this._ViewTbl32Classes;
   }

   public boolean isViewTbl32ClassesInherited() {
      return false;
   }

   public boolean isViewTbl32ClassesSet() {
      return this._isSet(13);
   }

   public void setAppPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AppPassword;
      this._AppPassword = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getAppPassword() {
      return this._AppPassword;
   }

   public boolean isAppPasswordInherited() {
      return false;
   }

   public boolean isAppPasswordSet() {
      return this._isSet(14);
   }

   public void setAppPasswordIV(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AppPasswordIV;
      this._AppPasswordIV = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getAppPasswordIV() {
      return this._AppPasswordIV;
   }

   public boolean isAppPasswordIVInherited() {
      return false;
   }

   public boolean isAppPasswordIVSet() {
      return this._isSet(15);
   }

   public void setTpUsrFile(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TpUsrFile;
      this._TpUsrFile = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getTpUsrFile() {
      return this._TpUsrFile;
   }

   public boolean isTpUsrFileInherited() {
      return false;
   }

   public boolean isTpUsrFileSet() {
      return this._isSet(16);
   }

   public void setRemoteMBEncoding(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RemoteMBEncoding;
      this._RemoteMBEncoding = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getRemoteMBEncoding() {
      return this._RemoteMBEncoding;
   }

   public boolean isRemoteMBEncodingInherited() {
      return false;
   }

   public boolean isRemoteMBEncodingSet() {
      return this._isSet(17);
   }

   public void setMBEncodingMapFile(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MBEncodingMapFile;
      this._MBEncodingMapFile = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getMBEncodingMapFile() {
      return this._MBEncodingMapFile;
   }

   public boolean isMBEncodingMapFileInherited() {
      return false;
   }

   public boolean isMBEncodingMapFileSet() {
      return this._isSet(18);
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
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._AppPassword = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._AppPasswordIV = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._FldTbl16Classes = new String[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._FldTbl32Classes = new String[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._MBEncodingMapFile = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._RemoteMBEncoding = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._TpUsrFile = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._ViewTbl16Classes = new String[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._ViewTbl32Classes = new String[0];
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
      return "WTCResources";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("AppPassword")) {
         oldVal = this._AppPassword;
         this._AppPassword = (String)v;
         this._postSet(14, oldVal, this._AppPassword);
      } else if (name.equals("AppPasswordIV")) {
         oldVal = this._AppPasswordIV;
         this._AppPasswordIV = (String)v;
         this._postSet(15, oldVal, this._AppPasswordIV);
      } else {
         String[] oldVal;
         if (name.equals("FldTbl16Classes")) {
            oldVal = this._FldTbl16Classes;
            this._FldTbl16Classes = (String[])((String[])v);
            this._postSet(10, oldVal, this._FldTbl16Classes);
         } else if (name.equals("FldTbl32Classes")) {
            oldVal = this._FldTbl32Classes;
            this._FldTbl32Classes = (String[])((String[])v);
            this._postSet(11, oldVal, this._FldTbl32Classes);
         } else if (name.equals("MBEncodingMapFile")) {
            oldVal = this._MBEncodingMapFile;
            this._MBEncodingMapFile = (String)v;
            this._postSet(18, oldVal, this._MBEncodingMapFile);
         } else if (name.equals("RemoteMBEncoding")) {
            oldVal = this._RemoteMBEncoding;
            this._RemoteMBEncoding = (String)v;
            this._postSet(17, oldVal, this._RemoteMBEncoding);
         } else if (name.equals("TpUsrFile")) {
            oldVal = this._TpUsrFile;
            this._TpUsrFile = (String)v;
            this._postSet(16, oldVal, this._TpUsrFile);
         } else if (name.equals("ViewTbl16Classes")) {
            oldVal = this._ViewTbl16Classes;
            this._ViewTbl16Classes = (String[])((String[])v);
            this._postSet(12, oldVal, this._ViewTbl16Classes);
         } else if (name.equals("ViewTbl32Classes")) {
            oldVal = this._ViewTbl32Classes;
            this._ViewTbl32Classes = (String[])((String[])v);
            this._postSet(13, oldVal, this._ViewTbl32Classes);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AppPassword")) {
         return this._AppPassword;
      } else if (name.equals("AppPasswordIV")) {
         return this._AppPasswordIV;
      } else if (name.equals("FldTbl16Classes")) {
         return this._FldTbl16Classes;
      } else if (name.equals("FldTbl32Classes")) {
         return this._FldTbl32Classes;
      } else if (name.equals("MBEncodingMapFile")) {
         return this._MBEncodingMapFile;
      } else if (name.equals("RemoteMBEncoding")) {
         return this._RemoteMBEncoding;
      } else if (name.equals("TpUsrFile")) {
         return this._TpUsrFile;
      } else if (name.equals("ViewTbl16Classes")) {
         return this._ViewTbl16Classes;
      } else {
         return name.equals("ViewTbl32Classes") ? this._ViewTbl32Classes : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("tp-usr-file")) {
                  return 16;
               }
               break;
            case 12:
               if (s.equals("app-password")) {
                  return 14;
               }
            case 13:
            case 18:
            case 19:
            default:
               break;
            case 14:
               if (s.equals("app-passwordiv")) {
                  return 15;
               }
               break;
            case 15:
               if (s.equals("fld-tbl16-class")) {
                  return 10;
               }

               if (s.equals("fld-tbl32-class")) {
                  return 11;
               }
               break;
            case 16:
               if (s.equals("view-tbl16-class")) {
                  return 12;
               }

               if (s.equals("view-tbl32-class")) {
                  return 13;
               }
               break;
            case 17:
               if (s.equals("remotemb-encoding")) {
                  return 17;
               }
               break;
            case 20:
               if (s.equals("mb-encoding-map-file")) {
                  return 18;
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
               return "fld-tbl16-class";
            case 11:
               return "fld-tbl32-class";
            case 12:
               return "view-tbl16-class";
            case 13:
               return "view-tbl32-class";
            case 14:
               return "app-password";
            case 15:
               return "app-passwordiv";
            case 16:
               return "tp-usr-file";
            case 17:
               return "remotemb-encoding";
            case 18:
               return "mb-encoding-map-file";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
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
      private WTCResourcesMBeanImpl bean;

      protected Helper(WTCResourcesMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "FldTbl16Classes";
            case 11:
               return "FldTbl32Classes";
            case 12:
               return "ViewTbl16Classes";
            case 13:
               return "ViewTbl32Classes";
            case 14:
               return "AppPassword";
            case 15:
               return "AppPasswordIV";
            case 16:
               return "TpUsrFile";
            case 17:
               return "RemoteMBEncoding";
            case 18:
               return "MBEncodingMapFile";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AppPassword")) {
            return 14;
         } else if (propName.equals("AppPasswordIV")) {
            return 15;
         } else if (propName.equals("FldTbl16Classes")) {
            return 10;
         } else if (propName.equals("FldTbl32Classes")) {
            return 11;
         } else if (propName.equals("MBEncodingMapFile")) {
            return 18;
         } else if (propName.equals("RemoteMBEncoding")) {
            return 17;
         } else if (propName.equals("TpUsrFile")) {
            return 16;
         } else if (propName.equals("ViewTbl16Classes")) {
            return 12;
         } else {
            return propName.equals("ViewTbl32Classes") ? 13 : super.getPropertyIndex(propName);
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
            if (this.bean.isAppPasswordSet()) {
               buf.append("AppPassword");
               buf.append(String.valueOf(this.bean.getAppPassword()));
            }

            if (this.bean.isAppPasswordIVSet()) {
               buf.append("AppPasswordIV");
               buf.append(String.valueOf(this.bean.getAppPasswordIV()));
            }

            if (this.bean.isFldTbl16ClassesSet()) {
               buf.append("FldTbl16Classes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getFldTbl16Classes())));
            }

            if (this.bean.isFldTbl32ClassesSet()) {
               buf.append("FldTbl32Classes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getFldTbl32Classes())));
            }

            if (this.bean.isMBEncodingMapFileSet()) {
               buf.append("MBEncodingMapFile");
               buf.append(String.valueOf(this.bean.getMBEncodingMapFile()));
            }

            if (this.bean.isRemoteMBEncodingSet()) {
               buf.append("RemoteMBEncoding");
               buf.append(String.valueOf(this.bean.getRemoteMBEncoding()));
            }

            if (this.bean.isTpUsrFileSet()) {
               buf.append("TpUsrFile");
               buf.append(String.valueOf(this.bean.getTpUsrFile()));
            }

            if (this.bean.isViewTbl16ClassesSet()) {
               buf.append("ViewTbl16Classes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getViewTbl16Classes())));
            }

            if (this.bean.isViewTbl32ClassesSet()) {
               buf.append("ViewTbl32Classes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getViewTbl32Classes())));
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
            WTCResourcesMBeanImpl otherTyped = (WTCResourcesMBeanImpl)other;
            this.computeDiff("AppPassword", this.bean.getAppPassword(), otherTyped.getAppPassword(), true);
            this.computeDiff("AppPasswordIV", this.bean.getAppPasswordIV(), otherTyped.getAppPasswordIV(), true);
            this.computeDiff("FldTbl16Classes", this.bean.getFldTbl16Classes(), otherTyped.getFldTbl16Classes(), true);
            this.computeDiff("FldTbl32Classes", this.bean.getFldTbl32Classes(), otherTyped.getFldTbl32Classes(), true);
            this.computeDiff("MBEncodingMapFile", this.bean.getMBEncodingMapFile(), otherTyped.getMBEncodingMapFile(), true);
            this.computeDiff("RemoteMBEncoding", this.bean.getRemoteMBEncoding(), otherTyped.getRemoteMBEncoding(), true);
            this.computeDiff("TpUsrFile", this.bean.getTpUsrFile(), otherTyped.getTpUsrFile(), true);
            this.computeDiff("ViewTbl16Classes", this.bean.getViewTbl16Classes(), otherTyped.getViewTbl16Classes(), true);
            this.computeDiff("ViewTbl32Classes", this.bean.getViewTbl32Classes(), otherTyped.getViewTbl32Classes(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WTCResourcesMBeanImpl original = (WTCResourcesMBeanImpl)event.getSourceBean();
            WTCResourcesMBeanImpl proposed = (WTCResourcesMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AppPassword")) {
                  original.setAppPassword(proposed.getAppPassword());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("AppPasswordIV")) {
                  original.setAppPasswordIV(proposed.getAppPasswordIV());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("FldTbl16Classes")) {
                  original.setFldTbl16Classes(proposed.getFldTbl16Classes());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("FldTbl32Classes")) {
                  original.setFldTbl32Classes(proposed.getFldTbl32Classes());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("MBEncodingMapFile")) {
                  original.setMBEncodingMapFile(proposed.getMBEncodingMapFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("RemoteMBEncoding")) {
                  original.setRemoteMBEncoding(proposed.getRemoteMBEncoding());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("TpUsrFile")) {
                  original.setTpUsrFile(proposed.getTpUsrFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("ViewTbl16Classes")) {
                  original.setViewTbl16Classes(proposed.getViewTbl16Classes());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ViewTbl32Classes")) {
                  original.setViewTbl32Classes(proposed.getViewTbl32Classes());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
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
            WTCResourcesMBeanImpl copy = (WTCResourcesMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AppPassword")) && this.bean.isAppPasswordSet()) {
               copy.setAppPassword(this.bean.getAppPassword());
            }

            if ((excludeProps == null || !excludeProps.contains("AppPasswordIV")) && this.bean.isAppPasswordIVSet()) {
               copy.setAppPasswordIV(this.bean.getAppPasswordIV());
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("FldTbl16Classes")) && this.bean.isFldTbl16ClassesSet()) {
               o = this.bean.getFldTbl16Classes();
               copy.setFldTbl16Classes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("FldTbl32Classes")) && this.bean.isFldTbl32ClassesSet()) {
               o = this.bean.getFldTbl32Classes();
               copy.setFldTbl32Classes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("MBEncodingMapFile")) && this.bean.isMBEncodingMapFileSet()) {
               copy.setMBEncodingMapFile(this.bean.getMBEncodingMapFile());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteMBEncoding")) && this.bean.isRemoteMBEncodingSet()) {
               copy.setRemoteMBEncoding(this.bean.getRemoteMBEncoding());
            }

            if ((excludeProps == null || !excludeProps.contains("TpUsrFile")) && this.bean.isTpUsrFileSet()) {
               copy.setTpUsrFile(this.bean.getTpUsrFile());
            }

            if ((excludeProps == null || !excludeProps.contains("ViewTbl16Classes")) && this.bean.isViewTbl16ClassesSet()) {
               o = this.bean.getViewTbl16Classes();
               copy.setViewTbl16Classes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ViewTbl32Classes")) && this.bean.isViewTbl32ClassesSet()) {
               o = this.bean.getViewTbl32Classes();
               copy.setViewTbl32Classes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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

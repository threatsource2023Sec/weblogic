package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class SCAContainerMBeanImpl extends ConfigurationMBeanImpl implements SCAContainerMBean, Serializable {
   private boolean _AllowsPassByReference;
   private boolean _Autowire;
   private String _MaxAge;
   private String _MaxIdleTime;
   private boolean _Remotable;
   private boolean _SinglePrincipal;
   private int _Timeout;
   private static SchemaHelper2 _schemaHelper;

   public SCAContainerMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SCAContainerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SCAContainerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getTimeout() {
      return this._Timeout;
   }

   public boolean isTimeoutInherited() {
      return false;
   }

   public boolean isTimeoutSet() {
      return this._isSet(10);
   }

   public void setTimeout(int param0) {
      int _oldVal = this._Timeout;
      this._Timeout = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isAutowire() {
      return this._Autowire;
   }

   public boolean isAutowireInherited() {
      return false;
   }

   public boolean isAutowireSet() {
      return this._isSet(11);
   }

   public void setAutowire(boolean param0) {
      boolean _oldVal = this._Autowire;
      this._Autowire = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isAllowsPassByReference() {
      return this._AllowsPassByReference;
   }

   public boolean isAllowsPassByReferenceInherited() {
      return false;
   }

   public boolean isAllowsPassByReferenceSet() {
      return this._isSet(12);
   }

   public void setAllowsPassByReference(boolean param0) {
      boolean _oldVal = this._AllowsPassByReference;
      this._AllowsPassByReference = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean isRemotable() {
      return this._Remotable;
   }

   public boolean isRemotableInherited() {
      return false;
   }

   public boolean isRemotableSet() {
      return this._isSet(13);
   }

   public void setRemotable(boolean param0) {
      boolean _oldVal = this._Remotable;
      this._Remotable = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getMaxIdleTime() {
      return this._MaxIdleTime;
   }

   public boolean isMaxIdleTimeInherited() {
      return false;
   }

   public boolean isMaxIdleTimeSet() {
      return this._isSet(14);
   }

   public void setMaxIdleTime(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MaxIdleTime;
      this._MaxIdleTime = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getMaxAge() {
      return this._MaxAge;
   }

   public boolean isMaxAgeInherited() {
      return false;
   }

   public boolean isMaxAgeSet() {
      return this._isSet(15);
   }

   public void setMaxAge(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MaxAge;
      this._MaxAge = param0;
      this._postSet(15, _oldVal, param0);
   }

   public boolean isSinglePrincipal() {
      return this._SinglePrincipal;
   }

   public boolean isSinglePrincipalInherited() {
      return false;
   }

   public boolean isSinglePrincipalSet() {
      return this._isSet(16);
   }

   public void setSinglePrincipal(boolean param0) {
      boolean _oldVal = this._SinglePrincipal;
      this._SinglePrincipal = param0;
      this._postSet(16, _oldVal, param0);
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
         idx = 15;
      }

      try {
         switch (idx) {
            case 15:
               this._MaxAge = "";
               if (initOne) {
                  break;
               }
            case 14:
               this._MaxIdleTime = "";
               if (initOne) {
                  break;
               }
            case 10:
               this._Timeout = 0;
               if (initOne) {
                  break;
               }
            case 12:
               this._AllowsPassByReference = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._Autowire = false;
               if (initOne) {
                  break;
               }
            case 13:
               this._Remotable = false;
               if (initOne) {
                  break;
               }
            case 16:
               this._SinglePrincipal = false;
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
      return "SCAContainer";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("AllowsPassByReference")) {
         oldVal = this._AllowsPassByReference;
         this._AllowsPassByReference = (Boolean)v;
         this._postSet(12, oldVal, this._AllowsPassByReference);
      } else if (name.equals("Autowire")) {
         oldVal = this._Autowire;
         this._Autowire = (Boolean)v;
         this._postSet(11, oldVal, this._Autowire);
      } else {
         String oldVal;
         if (name.equals("MaxAge")) {
            oldVal = this._MaxAge;
            this._MaxAge = (String)v;
            this._postSet(15, oldVal, this._MaxAge);
         } else if (name.equals("MaxIdleTime")) {
            oldVal = this._MaxIdleTime;
            this._MaxIdleTime = (String)v;
            this._postSet(14, oldVal, this._MaxIdleTime);
         } else if (name.equals("Remotable")) {
            oldVal = this._Remotable;
            this._Remotable = (Boolean)v;
            this._postSet(13, oldVal, this._Remotable);
         } else if (name.equals("SinglePrincipal")) {
            oldVal = this._SinglePrincipal;
            this._SinglePrincipal = (Boolean)v;
            this._postSet(16, oldVal, this._SinglePrincipal);
         } else if (name.equals("Timeout")) {
            int oldVal = this._Timeout;
            this._Timeout = (Integer)v;
            this._postSet(10, oldVal, this._Timeout);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AllowsPassByReference")) {
         return new Boolean(this._AllowsPassByReference);
      } else if (name.equals("Autowire")) {
         return new Boolean(this._Autowire);
      } else if (name.equals("MaxAge")) {
         return this._MaxAge;
      } else if (name.equals("MaxIdleTime")) {
         return this._MaxIdleTime;
      } else if (name.equals("Remotable")) {
         return new Boolean(this._Remotable);
      } else if (name.equals("SinglePrincipal")) {
         return new Boolean(this._SinglePrincipal);
      } else {
         return name.equals("Timeout") ? new Integer(this._Timeout) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("max-age")) {
                  return 15;
               }

               if (s.equals("timeout")) {
                  return 10;
               }
               break;
            case 8:
               if (s.equals("autowire")) {
                  return 11;
               }
               break;
            case 9:
               if (s.equals("remotable")) {
                  return 13;
               }
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            default:
               break;
            case 13:
               if (s.equals("max-idle-time")) {
                  return 14;
               }
               break;
            case 16:
               if (s.equals("single-principal")) {
                  return 16;
               }
               break;
            case 24:
               if (s.equals("allows-pass-by-reference")) {
                  return 12;
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
               return "timeout";
            case 11:
               return "autowire";
            case 12:
               return "allows-pass-by-reference";
            case 13:
               return "remotable";
            case 14:
               return "max-idle-time";
            case 15:
               return "max-age";
            case 16:
               return "single-principal";
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

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            case 12:
            case 13:
            default:
               return super.isConfigurable(propIndex);
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
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
      private SCAContainerMBeanImpl bean;

      protected Helper(SCAContainerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Timeout";
            case 11:
               return "Autowire";
            case 12:
               return "AllowsPassByReference";
            case 13:
               return "Remotable";
            case 14:
               return "MaxIdleTime";
            case 15:
               return "MaxAge";
            case 16:
               return "SinglePrincipal";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("MaxAge")) {
            return 15;
         } else if (propName.equals("MaxIdleTime")) {
            return 14;
         } else if (propName.equals("Timeout")) {
            return 10;
         } else if (propName.equals("AllowsPassByReference")) {
            return 12;
         } else if (propName.equals("Autowire")) {
            return 11;
         } else if (propName.equals("Remotable")) {
            return 13;
         } else {
            return propName.equals("SinglePrincipal") ? 16 : super.getPropertyIndex(propName);
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
            if (this.bean.isMaxAgeSet()) {
               buf.append("MaxAge");
               buf.append(String.valueOf(this.bean.getMaxAge()));
            }

            if (this.bean.isMaxIdleTimeSet()) {
               buf.append("MaxIdleTime");
               buf.append(String.valueOf(this.bean.getMaxIdleTime()));
            }

            if (this.bean.isTimeoutSet()) {
               buf.append("Timeout");
               buf.append(String.valueOf(this.bean.getTimeout()));
            }

            if (this.bean.isAllowsPassByReferenceSet()) {
               buf.append("AllowsPassByReference");
               buf.append(String.valueOf(this.bean.isAllowsPassByReference()));
            }

            if (this.bean.isAutowireSet()) {
               buf.append("Autowire");
               buf.append(String.valueOf(this.bean.isAutowire()));
            }

            if (this.bean.isRemotableSet()) {
               buf.append("Remotable");
               buf.append(String.valueOf(this.bean.isRemotable()));
            }

            if (this.bean.isSinglePrincipalSet()) {
               buf.append("SinglePrincipal");
               buf.append(String.valueOf(this.bean.isSinglePrincipal()));
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
            SCAContainerMBeanImpl otherTyped = (SCAContainerMBeanImpl)other;
            this.computeDiff("MaxAge", this.bean.getMaxAge(), otherTyped.getMaxAge(), false);
            this.computeDiff("MaxIdleTime", this.bean.getMaxIdleTime(), otherTyped.getMaxIdleTime(), false);
            this.computeDiff("Timeout", this.bean.getTimeout(), otherTyped.getTimeout(), false);
            this.computeDiff("AllowsPassByReference", this.bean.isAllowsPassByReference(), otherTyped.isAllowsPassByReference(), false);
            this.computeDiff("Autowire", this.bean.isAutowire(), otherTyped.isAutowire(), false);
            this.computeDiff("Remotable", this.bean.isRemotable(), otherTyped.isRemotable(), false);
            this.computeDiff("SinglePrincipal", this.bean.isSinglePrincipal(), otherTyped.isSinglePrincipal(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SCAContainerMBeanImpl original = (SCAContainerMBeanImpl)event.getSourceBean();
            SCAContainerMBeanImpl proposed = (SCAContainerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("MaxAge")) {
                  original.setMaxAge(proposed.getMaxAge());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("MaxIdleTime")) {
                  original.setMaxIdleTime(proposed.getMaxIdleTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("Timeout")) {
                  original.setTimeout(proposed.getTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("AllowsPassByReference")) {
                  original.setAllowsPassByReference(proposed.isAllowsPassByReference());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Autowire")) {
                  original.setAutowire(proposed.isAutowire());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Remotable")) {
                  original.setRemotable(proposed.isRemotable());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("SinglePrincipal")) {
                  original.setSinglePrincipal(proposed.isSinglePrincipal());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
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
            SCAContainerMBeanImpl copy = (SCAContainerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("MaxAge")) && this.bean.isMaxAgeSet()) {
               copy.setMaxAge(this.bean.getMaxAge());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxIdleTime")) && this.bean.isMaxIdleTimeSet()) {
               copy.setMaxIdleTime(this.bean.getMaxIdleTime());
            }

            if ((excludeProps == null || !excludeProps.contains("Timeout")) && this.bean.isTimeoutSet()) {
               copy.setTimeout(this.bean.getTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("AllowsPassByReference")) && this.bean.isAllowsPassByReferenceSet()) {
               copy.setAllowsPassByReference(this.bean.isAllowsPassByReference());
            }

            if ((excludeProps == null || !excludeProps.contains("Autowire")) && this.bean.isAutowireSet()) {
               copy.setAutowire(this.bean.isAutowire());
            }

            if ((excludeProps == null || !excludeProps.contains("Remotable")) && this.bean.isRemotableSet()) {
               copy.setRemotable(this.bean.isRemotable());
            }

            if ((excludeProps == null || !excludeProps.contains("SinglePrincipal")) && this.bean.isSinglePrincipalSet()) {
               copy.setSinglePrincipal(this.bean.isSinglePrincipal());
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

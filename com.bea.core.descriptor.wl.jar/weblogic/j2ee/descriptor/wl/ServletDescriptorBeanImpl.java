package weblogic.j2ee.descriptor.wl;

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

public class ServletDescriptorBeanImpl extends AbstractDescriptorBean implements ServletDescriptorBean, Serializable {
   private String _DestroyAsPrincipalName;
   private String _DispatchPolicy;
   private String _Id;
   private String _InitAsPrincipalName;
   private String _RunAsPrincipalName;
   private String _ServletName;
   private static SchemaHelper2 _schemaHelper;

   public ServletDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public ServletDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ServletDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getServletName() {
      return this._ServletName;
   }

   public boolean isServletNameInherited() {
      return false;
   }

   public boolean isServletNameSet() {
      return this._isSet(0);
   }

   public void setServletName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServletName;
      this._ServletName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getRunAsPrincipalName() {
      return this._RunAsPrincipalName;
   }

   public boolean isRunAsPrincipalNameInherited() {
      return false;
   }

   public boolean isRunAsPrincipalNameSet() {
      return this._isSet(1);
   }

   public void setRunAsPrincipalName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RunAsPrincipalName;
      this._RunAsPrincipalName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getInitAsPrincipalName() {
      return this._InitAsPrincipalName;
   }

   public boolean isInitAsPrincipalNameInherited() {
      return false;
   }

   public boolean isInitAsPrincipalNameSet() {
      return this._isSet(2);
   }

   public void setInitAsPrincipalName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InitAsPrincipalName;
      this._InitAsPrincipalName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getDestroyAsPrincipalName() {
      return this._DestroyAsPrincipalName;
   }

   public boolean isDestroyAsPrincipalNameInherited() {
      return false;
   }

   public boolean isDestroyAsPrincipalNameSet() {
      return this._isSet(3);
   }

   public void setDestroyAsPrincipalName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DestroyAsPrincipalName;
      this._DestroyAsPrincipalName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getDispatchPolicy() {
      return this._DispatchPolicy;
   }

   public boolean isDispatchPolicyInherited() {
      return false;
   }

   public boolean isDispatchPolicySet() {
      return this._isSet(4);
   }

   public void setDispatchPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DispatchPolicy;
      this._DispatchPolicy = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(5);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getServletName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 12:
            if (s.equals("servlet-name")) {
               return info.compareXpaths(this._getPropertyXpath("servlet-name"));
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._DestroyAsPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._DispatchPolicy = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._InitAsPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._RunAsPrincipalName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ServletName = null;
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
            case 2:
               if (s.equals("id")) {
                  return 5;
               }
               break;
            case 12:
               if (s.equals("servlet-name")) {
                  return 0;
               }
               break;
            case 15:
               if (s.equals("dispatch-policy")) {
                  return 4;
               }
               break;
            case 21:
               if (s.equals("run-as-principal-name")) {
                  return 1;
               }
               break;
            case 22:
               if (s.equals("init-as-principal-name")) {
                  return 2;
               }
               break;
            case 25:
               if (s.equals("destroy-as-principal-name")) {
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
            case 0:
               return "servlet-name";
            case 1:
               return "run-as-principal-name";
            case 2:
               return "init-as-principal-name";
            case 3:
               return "destroy-as-principal-name";
            case 4:
               return "dispatch-policy";
            case 5:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
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
         indices.add("servlet-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ServletDescriptorBeanImpl bean;

      protected Helper(ServletDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ServletName";
            case 1:
               return "RunAsPrincipalName";
            case 2:
               return "InitAsPrincipalName";
            case 3:
               return "DestroyAsPrincipalName";
            case 4:
               return "DispatchPolicy";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DestroyAsPrincipalName")) {
            return 3;
         } else if (propName.equals("DispatchPolicy")) {
            return 4;
         } else if (propName.equals("Id")) {
            return 5;
         } else if (propName.equals("InitAsPrincipalName")) {
            return 2;
         } else if (propName.equals("RunAsPrincipalName")) {
            return 1;
         } else {
            return propName.equals("ServletName") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isDestroyAsPrincipalNameSet()) {
               buf.append("DestroyAsPrincipalName");
               buf.append(String.valueOf(this.bean.getDestroyAsPrincipalName()));
            }

            if (this.bean.isDispatchPolicySet()) {
               buf.append("DispatchPolicy");
               buf.append(String.valueOf(this.bean.getDispatchPolicy()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isInitAsPrincipalNameSet()) {
               buf.append("InitAsPrincipalName");
               buf.append(String.valueOf(this.bean.getInitAsPrincipalName()));
            }

            if (this.bean.isRunAsPrincipalNameSet()) {
               buf.append("RunAsPrincipalName");
               buf.append(String.valueOf(this.bean.getRunAsPrincipalName()));
            }

            if (this.bean.isServletNameSet()) {
               buf.append("ServletName");
               buf.append(String.valueOf(this.bean.getServletName()));
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
            ServletDescriptorBeanImpl otherTyped = (ServletDescriptorBeanImpl)other;
            this.computeDiff("DestroyAsPrincipalName", this.bean.getDestroyAsPrincipalName(), otherTyped.getDestroyAsPrincipalName(), true);
            this.computeDiff("DispatchPolicy", this.bean.getDispatchPolicy(), otherTyped.getDispatchPolicy(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("InitAsPrincipalName", this.bean.getInitAsPrincipalName(), otherTyped.getInitAsPrincipalName(), true);
            this.computeDiff("RunAsPrincipalName", this.bean.getRunAsPrincipalName(), otherTyped.getRunAsPrincipalName(), true);
            this.computeDiff("ServletName", this.bean.getServletName(), otherTyped.getServletName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServletDescriptorBeanImpl original = (ServletDescriptorBeanImpl)event.getSourceBean();
            ServletDescriptorBeanImpl proposed = (ServletDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DestroyAsPrincipalName")) {
                  original.setDestroyAsPrincipalName(proposed.getDestroyAsPrincipalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("DispatchPolicy")) {
                  original.setDispatchPolicy(proposed.getDispatchPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("InitAsPrincipalName")) {
                  original.setInitAsPrincipalName(proposed.getInitAsPrincipalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("RunAsPrincipalName")) {
                  original.setRunAsPrincipalName(proposed.getRunAsPrincipalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ServletName")) {
                  original.setServletName(proposed.getServletName());
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
            ServletDescriptorBeanImpl copy = (ServletDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DestroyAsPrincipalName")) && this.bean.isDestroyAsPrincipalNameSet()) {
               copy.setDestroyAsPrincipalName(this.bean.getDestroyAsPrincipalName());
            }

            if ((excludeProps == null || !excludeProps.contains("DispatchPolicy")) && this.bean.isDispatchPolicySet()) {
               copy.setDispatchPolicy(this.bean.getDispatchPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InitAsPrincipalName")) && this.bean.isInitAsPrincipalNameSet()) {
               copy.setInitAsPrincipalName(this.bean.getInitAsPrincipalName());
            }

            if ((excludeProps == null || !excludeProps.contains("RunAsPrincipalName")) && this.bean.isRunAsPrincipalNameSet()) {
               copy.setRunAsPrincipalName(this.bean.getRunAsPrincipalName());
            }

            if ((excludeProps == null || !excludeProps.contains("ServletName")) && this.bean.isServletNameSet()) {
               copy.setServletName(this.bean.getServletName());
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

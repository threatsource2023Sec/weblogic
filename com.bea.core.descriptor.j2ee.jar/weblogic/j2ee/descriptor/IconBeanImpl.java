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

public class IconBeanImpl extends AbstractDescriptorBean implements IconBean, Serializable {
   private String _Id;
   private String _Lang;
   private String _LargeIcon;
   private String _SmallIcon;
   private static SchemaHelper2 _schemaHelper;

   public IconBeanImpl() {
      this._initializeProperty(-1);
   }

   public IconBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public IconBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getSmallIcon() {
      return this._SmallIcon;
   }

   public boolean isSmallIconInherited() {
      return false;
   }

   public boolean isSmallIconSet() {
      return this._isSet(0);
   }

   public void setSmallIcon(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SmallIcon;
      this._SmallIcon = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getLargeIcon() {
      return this._LargeIcon;
   }

   public boolean isLargeIconInherited() {
      return false;
   }

   public boolean isLargeIconSet() {
      return this._isSet(1);
   }

   public void setLargeIcon(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LargeIcon;
      this._LargeIcon = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getLang() {
      return this._Lang;
   }

   public boolean isLangInherited() {
      return false;
   }

   public boolean isLangSet() {
      return this._isSet(2);
   }

   public void setLang(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Lang;
      this._Lang = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(3);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Lang = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._LargeIcon = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._SmallIcon = null;
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
                  return 3;
               }
               break;
            case 4:
               if (s.equals("lang")) {
                  return 2;
               }
               break;
            case 10:
               if (s.equals("large-icon")) {
                  return 1;
               }

               if (s.equals("small-icon")) {
                  return 0;
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
               return "small-icon";
            case 1:
               return "large-icon";
            case 2:
               return "lang";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private IconBeanImpl bean;

      protected Helper(IconBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "SmallIcon";
            case 1:
               return "LargeIcon";
            case 2:
               return "Lang";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("Lang")) {
            return 2;
         } else if (propName.equals("LargeIcon")) {
            return 1;
         } else {
            return propName.equals("SmallIcon") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isLangSet()) {
               buf.append("Lang");
               buf.append(String.valueOf(this.bean.getLang()));
            }

            if (this.bean.isLargeIconSet()) {
               buf.append("LargeIcon");
               buf.append(String.valueOf(this.bean.getLargeIcon()));
            }

            if (this.bean.isSmallIconSet()) {
               buf.append("SmallIcon");
               buf.append(String.valueOf(this.bean.getSmallIcon()));
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
            IconBeanImpl otherTyped = (IconBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("Lang", this.bean.getLang(), otherTyped.getLang(), false);
            this.computeDiff("LargeIcon", this.bean.getLargeIcon(), otherTyped.getLargeIcon(), false);
            this.computeDiff("SmallIcon", this.bean.getSmallIcon(), otherTyped.getSmallIcon(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            IconBeanImpl original = (IconBeanImpl)event.getSourceBean();
            IconBeanImpl proposed = (IconBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Lang")) {
                  original.setLang(proposed.getLang());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("LargeIcon")) {
                  original.setLargeIcon(proposed.getLargeIcon());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("SmallIcon")) {
                  original.setSmallIcon(proposed.getSmallIcon());
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
            IconBeanImpl copy = (IconBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Lang")) && this.bean.isLangSet()) {
               copy.setLang(this.bean.getLang());
            }

            if ((excludeProps == null || !excludeProps.contains("LargeIcon")) && this.bean.isLargeIconSet()) {
               copy.setLargeIcon(this.bean.getLargeIcon());
            }

            if ((excludeProps == null || !excludeProps.contains("SmallIcon")) && this.bean.isSmallIconSet()) {
               copy.setSmallIcon(this.bean.getSmallIcon());
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

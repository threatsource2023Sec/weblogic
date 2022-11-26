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

public class CookieConfigBeanImpl extends AbstractDescriptorBean implements CookieConfigBean, Serializable {
   private String _Comment;
   private String _Domain;
   private boolean _HttpOnly;
   private String _Id;
   private int _MaxAge;
   private String _Name;
   private String _Path;
   private boolean _Secure;
   private static SchemaHelper2 _schemaHelper;

   public CookieConfigBeanImpl() {
      this._initializeProperty(-1);
   }

   public CookieConfigBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CookieConfigBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDomain() {
      return this._Domain;
   }

   public boolean isDomainInherited() {
      return false;
   }

   public boolean isDomainSet() {
      return this._isSet(1);
   }

   public void setDomain(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Domain;
      this._Domain = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getPath() {
      return this._Path;
   }

   public boolean isPathInherited() {
      return false;
   }

   public boolean isPathSet() {
      return this._isSet(2);
   }

   public void setPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Path;
      this._Path = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getComment() {
      return this._Comment;
   }

   public boolean isCommentInherited() {
      return false;
   }

   public boolean isCommentSet() {
      return this._isSet(3);
   }

   public void setComment(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Comment;
      this._Comment = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isHttpOnly() {
      return this._HttpOnly;
   }

   public boolean isHttpOnlyInherited() {
      return false;
   }

   public boolean isHttpOnlySet() {
      return this._isSet(4);
   }

   public void setHttpOnly(boolean param0) {
      boolean _oldVal = this._HttpOnly;
      this._HttpOnly = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isSecure() {
      return this._Secure;
   }

   public boolean isSecureInherited() {
      return false;
   }

   public boolean isSecureSet() {
      return this._isSet(5);
   }

   public void setSecure(boolean param0) {
      boolean _oldVal = this._Secure;
      this._Secure = param0;
      this._postSet(5, _oldVal, param0);
   }

   public int getMaxAge() {
      return this._MaxAge;
   }

   public boolean isMaxAgeInherited() {
      return false;
   }

   public boolean isMaxAgeSet() {
      return this._isSet(6);
   }

   public void setMaxAge(int param0) {
      int _oldVal = this._MaxAge;
      this._MaxAge = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(7);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(7, _oldVal, param0);
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
               this._Comment = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Domain = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._MaxAge = -1;
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Path = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._HttpOnly = false;
               if (initOne) {
                  break;
               }
            case 5:
               this._Secure = false;
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
                  return 7;
               }
            case 3:
            case 5:
            case 8:
            default:
               break;
            case 4:
               if (s.equals("name")) {
                  return 0;
               }

               if (s.equals("path")) {
                  return 2;
               }
               break;
            case 6:
               if (s.equals("domain")) {
                  return 1;
               }

               if (s.equals("secure")) {
                  return 5;
               }
               break;
            case 7:
               if (s.equals("comment")) {
                  return 3;
               }

               if (s.equals("max-age")) {
                  return 6;
               }
               break;
            case 9:
               if (s.equals("http-only")) {
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
            case 0:
               return "name";
            case 1:
               return "domain";
            case 2:
               return "path";
            case 3:
               return "comment";
            case 4:
               return "http-only";
            case 5:
               return "secure";
            case 6:
               return "max-age";
            case 7:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private CookieConfigBeanImpl bean;

      protected Helper(CookieConfigBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "Domain";
            case 2:
               return "Path";
            case 3:
               return "Comment";
            case 4:
               return "HttpOnly";
            case 5:
               return "Secure";
            case 6:
               return "MaxAge";
            case 7:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Comment")) {
            return 3;
         } else if (propName.equals("Domain")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 7;
         } else if (propName.equals("MaxAge")) {
            return 6;
         } else if (propName.equals("Name")) {
            return 0;
         } else if (propName.equals("Path")) {
            return 2;
         } else if (propName.equals("HttpOnly")) {
            return 4;
         } else {
            return propName.equals("Secure") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isCommentSet()) {
               buf.append("Comment");
               buf.append(String.valueOf(this.bean.getComment()));
            }

            if (this.bean.isDomainSet()) {
               buf.append("Domain");
               buf.append(String.valueOf(this.bean.getDomain()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isMaxAgeSet()) {
               buf.append("MaxAge");
               buf.append(String.valueOf(this.bean.getMaxAge()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPathSet()) {
               buf.append("Path");
               buf.append(String.valueOf(this.bean.getPath()));
            }

            if (this.bean.isHttpOnlySet()) {
               buf.append("HttpOnly");
               buf.append(String.valueOf(this.bean.isHttpOnly()));
            }

            if (this.bean.isSecureSet()) {
               buf.append("Secure");
               buf.append(String.valueOf(this.bean.isSecure()));
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
            CookieConfigBeanImpl otherTyped = (CookieConfigBeanImpl)other;
            this.computeDiff("Comment", this.bean.getComment(), otherTyped.getComment(), false);
            this.computeDiff("Domain", this.bean.getDomain(), otherTyped.getDomain(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("MaxAge", this.bean.getMaxAge(), otherTyped.getMaxAge(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Path", this.bean.getPath(), otherTyped.getPath(), false);
            this.computeDiff("HttpOnly", this.bean.isHttpOnly(), otherTyped.isHttpOnly(), false);
            this.computeDiff("Secure", this.bean.isSecure(), otherTyped.isSecure(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CookieConfigBeanImpl original = (CookieConfigBeanImpl)event.getSourceBean();
            CookieConfigBeanImpl proposed = (CookieConfigBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Comment")) {
                  original.setComment(proposed.getComment());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Domain")) {
                  original.setDomain(proposed.getDomain());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("MaxAge")) {
                  original.setMaxAge(proposed.getMaxAge());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Path")) {
                  original.setPath(proposed.getPath());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("HttpOnly")) {
                  original.setHttpOnly(proposed.isHttpOnly());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Secure")) {
                  original.setSecure(proposed.isSecure());
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
            CookieConfigBeanImpl copy = (CookieConfigBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Comment")) && this.bean.isCommentSet()) {
               copy.setComment(this.bean.getComment());
            }

            if ((excludeProps == null || !excludeProps.contains("Domain")) && this.bean.isDomainSet()) {
               copy.setDomain(this.bean.getDomain());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxAge")) && this.bean.isMaxAgeSet()) {
               copy.setMaxAge(this.bean.getMaxAge());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Path")) && this.bean.isPathSet()) {
               copy.setPath(this.bean.getPath());
            }

            if ((excludeProps == null || !excludeProps.contains("HttpOnly")) && this.bean.isHttpOnlySet()) {
               copy.setHttpOnly(this.bean.isHttpOnly());
            }

            if ((excludeProps == null || !excludeProps.contains("Secure")) && this.bean.isSecureSet()) {
               copy.setSecure(this.bean.isSecure());
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

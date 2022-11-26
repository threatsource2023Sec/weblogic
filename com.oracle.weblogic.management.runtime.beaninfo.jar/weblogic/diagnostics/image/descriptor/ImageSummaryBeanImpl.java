package weblogic.diagnostics.image.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ImageSummaryBeanImpl extends AbstractDescriptorBean implements ImageSummaryBean, Serializable {
   private FailedImageSourceBean[] _FailedImageSource;
   private boolean _ImageCaptureCancelled;
   private String _ImageCreationDate;
   private long _ImageCreationElapsedTime;
   private String _ImageFileName;
   private String _MuxerClass;
   private String _RequestStackTrace;
   private String _RequesterThreadName;
   private String _RequesterUserId;
   private String _ServerName;
   private String _ServerReleaseInfo;
   private SuccessfulImageSourceBean[] _SuccessfulImageSources;
   private SystemPropertyBean[] _SystemProperties;
   private static SchemaHelper2 _schemaHelper;

   public ImageSummaryBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public ImageSummaryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public ImageSummaryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public String getImageCreationDate() {
      return this._ImageCreationDate;
   }

   public boolean isImageCreationDateInherited() {
      return false;
   }

   public boolean isImageCreationDateSet() {
      return this._isSet(0);
   }

   public void setImageCreationDate(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ImageCreationDate;
      this._ImageCreationDate = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void setImageFileName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ImageFileName;
      this._ImageFileName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getImageFileName() {
      return this._ImageFileName;
   }

   public boolean isImageFileNameInherited() {
      return false;
   }

   public boolean isImageFileNameSet() {
      return this._isSet(1);
   }

   public void setImageCreationElapsedTime(long param0) {
      long _oldVal = this._ImageCreationElapsedTime;
      this._ImageCreationElapsedTime = param0;
      this._postSet(2, _oldVal, param0);
   }

   public long getImageCreationElapsedTime() {
      return this._ImageCreationElapsedTime;
   }

   public boolean isImageCreationElapsedTimeInherited() {
      return false;
   }

   public boolean isImageCreationElapsedTimeSet() {
      return this._isSet(2);
   }

   public void setImageCaptureCancelled(boolean param0) {
      boolean _oldVal = this._ImageCaptureCancelled;
      this._ImageCaptureCancelled = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isImageCaptureCancelled() {
      return this._ImageCaptureCancelled;
   }

   public boolean isImageCaptureCancelledInherited() {
      return false;
   }

   public boolean isImageCaptureCancelledSet() {
      return this._isSet(3);
   }

   public void setServerReleaseInfo(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServerReleaseInfo;
      this._ServerReleaseInfo = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getServerReleaseInfo() {
      return this._ServerReleaseInfo;
   }

   public boolean isServerReleaseInfoInherited() {
      return false;
   }

   public boolean isServerReleaseInfoSet() {
      return this._isSet(4);
   }

   public void setServerName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServerName;
      this._ServerName = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getServerName() {
      return this._ServerName;
   }

   public boolean isServerNameInherited() {
      return false;
   }

   public boolean isServerNameSet() {
      return this._isSet(5);
   }

   public void setMuxerClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MuxerClass;
      this._MuxerClass = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getMuxerClass() {
      return this._MuxerClass;
   }

   public boolean isMuxerClassInherited() {
      return false;
   }

   public boolean isMuxerClassSet() {
      return this._isSet(6);
   }

   public void addSystemProperty(SystemPropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 7)) {
         SystemPropertyBean[] _new;
         if (this._isSet(7)) {
            _new = (SystemPropertyBean[])((SystemPropertyBean[])this._getHelper()._extendArray(this.getSystemProperties(), SystemPropertyBean.class, param0));
         } else {
            _new = new SystemPropertyBean[]{param0};
         }

         try {
            this.setSystemProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SystemPropertyBean[] getSystemProperties() {
      return this._SystemProperties;
   }

   public boolean isSystemPropertiesInherited() {
      return false;
   }

   public boolean isSystemPropertiesSet() {
      return this._isSet(7);
   }

   public void removeSystemProperty(SystemPropertyBean param0) {
      SystemPropertyBean[] _old = this.getSystemProperties();
      SystemPropertyBean[] _new = (SystemPropertyBean[])((SystemPropertyBean[])this._getHelper()._removeElement(_old, SystemPropertyBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setSystemProperties(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setSystemProperties(SystemPropertyBean[] param0) throws InvalidAttributeValueException {
      SystemPropertyBean[] param0 = param0 == null ? new SystemPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 7)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SystemPropertyBean[] _oldVal = this._SystemProperties;
      this._SystemProperties = (SystemPropertyBean[])param0;
      this._postSet(7, _oldVal, param0);
   }

   public SystemPropertyBean createSystemProperty() {
      SystemPropertyBeanImpl _val = new SystemPropertyBeanImpl(this, -1);

      try {
         this.addSystemProperty(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public SuccessfulImageSourceBean createSuccessfulImageSource() {
      SuccessfulImageSourceBeanImpl _val = new SuccessfulImageSourceBeanImpl(this, -1);

      try {
         this.addSuccessfulImageSource(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addSuccessfulImageSource(SuccessfulImageSourceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 8)) {
         SuccessfulImageSourceBean[] _new;
         if (this._isSet(8)) {
            _new = (SuccessfulImageSourceBean[])((SuccessfulImageSourceBean[])this._getHelper()._extendArray(this.getSuccessfulImageSources(), SuccessfulImageSourceBean.class, param0));
         } else {
            _new = new SuccessfulImageSourceBean[]{param0};
         }

         try {
            this.setSuccessfulImageSources(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SuccessfulImageSourceBean[] getSuccessfulImageSources() {
      return this._SuccessfulImageSources;
   }

   public boolean isSuccessfulImageSourcesInherited() {
      return false;
   }

   public boolean isSuccessfulImageSourcesSet() {
      return this._isSet(8);
   }

   public void removeSuccessfulImageSource(SuccessfulImageSourceBean param0) {
      SuccessfulImageSourceBean[] _old = this.getSuccessfulImageSources();
      SuccessfulImageSourceBean[] _new = (SuccessfulImageSourceBean[])((SuccessfulImageSourceBean[])this._getHelper()._removeElement(_old, SuccessfulImageSourceBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setSuccessfulImageSources(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setSuccessfulImageSources(SuccessfulImageSourceBean[] param0) throws InvalidAttributeValueException {
      SuccessfulImageSourceBean[] param0 = param0 == null ? new SuccessfulImageSourceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 8)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SuccessfulImageSourceBean[] _oldVal = this._SuccessfulImageSources;
      this._SuccessfulImageSources = (SuccessfulImageSourceBean[])param0;
      this._postSet(8, _oldVal, param0);
   }

   public FailedImageSourceBean createFailedImageSource() {
      FailedImageSourceBeanImpl _val = new FailedImageSourceBeanImpl(this, -1);

      try {
         this.addFailedImageSource(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addFailedImageSource(FailedImageSourceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
         FailedImageSourceBean[] _new;
         if (this._isSet(9)) {
            _new = (FailedImageSourceBean[])((FailedImageSourceBean[])this._getHelper()._extendArray(this.getFailedImageSource(), FailedImageSourceBean.class, param0));
         } else {
            _new = new FailedImageSourceBean[]{param0};
         }

         try {
            this.setFailedImageSource(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public FailedImageSourceBean[] getFailedImageSource() {
      return this._FailedImageSource;
   }

   public boolean isFailedImageSourceInherited() {
      return false;
   }

   public boolean isFailedImageSourceSet() {
      return this._isSet(9);
   }

   public void removeFailedImageSource(FailedImageSourceBean param0) {
      FailedImageSourceBean[] _old = this.getFailedImageSource();
      FailedImageSourceBean[] _new = (FailedImageSourceBean[])((FailedImageSourceBean[])this._getHelper()._removeElement(_old, FailedImageSourceBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setFailedImageSource(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setFailedImageSource(FailedImageSourceBean[] param0) throws InvalidAttributeValueException {
      FailedImageSourceBean[] param0 = param0 == null ? new FailedImageSourceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 9)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      FailedImageSourceBean[] _oldVal = this._FailedImageSource;
      this._FailedImageSource = (FailedImageSourceBean[])param0;
      this._postSet(9, _oldVal, param0);
   }

   public void setRequesterThreadName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RequesterThreadName;
      this._RequesterThreadName = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getRequesterThreadName() {
      return this._RequesterThreadName;
   }

   public boolean isRequesterThreadNameInherited() {
      return false;
   }

   public boolean isRequesterThreadNameSet() {
      return this._isSet(10);
   }

   public void setRequesterUserId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RequesterUserId;
      this._RequesterUserId = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getRequesterUserId() {
      return this._RequesterUserId;
   }

   public boolean isRequesterUserIdInherited() {
      return false;
   }

   public boolean isRequesterUserIdSet() {
      return this._isSet(11);
   }

   public void setRequestStackTrace(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RequestStackTrace;
      this._RequestStackTrace = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getRequestStackTrace() {
      return this._RequestStackTrace;
   }

   public boolean isRequestStackTraceInherited() {
      return false;
   }

   public boolean isRequestStackTraceSet() {
      return this._isSet(12);
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
         idx = 9;
      }

      try {
         switch (idx) {
            case 9:
               this._FailedImageSource = new FailedImageSourceBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._ImageCreationDate = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ImageCreationElapsedTime = 0L;
               if (initOne) {
                  break;
               }
            case 1:
               this._ImageFileName = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._MuxerClass = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._RequestStackTrace = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._RequesterThreadName = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._RequesterUserId = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._ServerName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._ServerReleaseInfo = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._SuccessfulImageSources = new SuccessfulImageSourceBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._SystemProperties = new SystemPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._ImageCaptureCancelled = false;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image/1.0/weblogic-diagnostics-image.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image";
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
            case 11:
               if (s.equals("muxer-class")) {
                  return 6;
               }

               if (s.equals("server-name")) {
                  return 5;
               }
            case 12:
            case 13:
            case 14:
            case 16:
            case 18:
            case 20:
            case 22:
            case 24:
            case 25:
            case 26:
            default:
               break;
            case 15:
               if (s.equals("image-file-name")) {
                  return 1;
               }

               if (s.equals("system-property")) {
                  return 7;
               }
               break;
            case 17:
               if (s.equals("requester-user-id")) {
                  return 11;
               }
               break;
            case 19:
               if (s.equals("failed-image-source")) {
                  return 9;
               }

               if (s.equals("image-creation-date")) {
                  return 0;
               }

               if (s.equals("request-stack-trace")) {
                  return 12;
               }

               if (s.equals("server-release-info")) {
                  return 4;
               }
               break;
            case 21:
               if (s.equals("requester-thread-name")) {
                  return 10;
               }
               break;
            case 23:
               if (s.equals("successful-image-source")) {
                  return 8;
               }

               if (s.equals("image-capture-cancelled")) {
                  return 3;
               }
               break;
            case 27:
               if (s.equals("image-creation-elapsed-time")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 7:
               return new SystemPropertyBeanImpl.SchemaHelper2();
            case 8:
               return new SuccessfulImageSourceBeanImpl.SchemaHelper2();
            case 9:
               return new FailedImageSourceBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "image-summary";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "image-creation-date";
            case 1:
               return "image-file-name";
            case 2:
               return "image-creation-elapsed-time";
            case 3:
               return "image-capture-cancelled";
            case 4:
               return "server-release-info";
            case 5:
               return "server-name";
            case 6:
               return "muxer-class";
            case 7:
               return "system-property";
            case 8:
               return "successful-image-source";
            case 9:
               return "failed-image-source";
            case 10:
               return "requester-thread-name";
            case 11:
               return "requester-user-id";
            case 12:
               return "request-stack-trace";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ImageSummaryBeanImpl bean;

      protected Helper(ImageSummaryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ImageCreationDate";
            case 1:
               return "ImageFileName";
            case 2:
               return "ImageCreationElapsedTime";
            case 3:
               return "ImageCaptureCancelled";
            case 4:
               return "ServerReleaseInfo";
            case 5:
               return "ServerName";
            case 6:
               return "MuxerClass";
            case 7:
               return "SystemProperties";
            case 8:
               return "SuccessfulImageSources";
            case 9:
               return "FailedImageSource";
            case 10:
               return "RequesterThreadName";
            case 11:
               return "RequesterUserId";
            case 12:
               return "RequestStackTrace";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FailedImageSource")) {
            return 9;
         } else if (propName.equals("ImageCreationDate")) {
            return 0;
         } else if (propName.equals("ImageCreationElapsedTime")) {
            return 2;
         } else if (propName.equals("ImageFileName")) {
            return 1;
         } else if (propName.equals("MuxerClass")) {
            return 6;
         } else if (propName.equals("RequestStackTrace")) {
            return 12;
         } else if (propName.equals("RequesterThreadName")) {
            return 10;
         } else if (propName.equals("RequesterUserId")) {
            return 11;
         } else if (propName.equals("ServerName")) {
            return 5;
         } else if (propName.equals("ServerReleaseInfo")) {
            return 4;
         } else if (propName.equals("SuccessfulImageSources")) {
            return 8;
         } else if (propName.equals("SystemProperties")) {
            return 7;
         } else {
            return propName.equals("ImageCaptureCancelled") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getFailedImageSource()));
         iterators.add(new ArrayIterator(this.bean.getSuccessfulImageSources()));
         iterators.add(new ArrayIterator(this.bean.getSystemProperties()));
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
            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getFailedImageSource().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getFailedImageSource()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isImageCreationDateSet()) {
               buf.append("ImageCreationDate");
               buf.append(String.valueOf(this.bean.getImageCreationDate()));
            }

            if (this.bean.isImageCreationElapsedTimeSet()) {
               buf.append("ImageCreationElapsedTime");
               buf.append(String.valueOf(this.bean.getImageCreationElapsedTime()));
            }

            if (this.bean.isImageFileNameSet()) {
               buf.append("ImageFileName");
               buf.append(String.valueOf(this.bean.getImageFileName()));
            }

            if (this.bean.isMuxerClassSet()) {
               buf.append("MuxerClass");
               buf.append(String.valueOf(this.bean.getMuxerClass()));
            }

            if (this.bean.isRequestStackTraceSet()) {
               buf.append("RequestStackTrace");
               buf.append(String.valueOf(this.bean.getRequestStackTrace()));
            }

            if (this.bean.isRequesterThreadNameSet()) {
               buf.append("RequesterThreadName");
               buf.append(String.valueOf(this.bean.getRequesterThreadName()));
            }

            if (this.bean.isRequesterUserIdSet()) {
               buf.append("RequesterUserId");
               buf.append(String.valueOf(this.bean.getRequesterUserId()));
            }

            if (this.bean.isServerNameSet()) {
               buf.append("ServerName");
               buf.append(String.valueOf(this.bean.getServerName()));
            }

            if (this.bean.isServerReleaseInfoSet()) {
               buf.append("ServerReleaseInfo");
               buf.append(String.valueOf(this.bean.getServerReleaseInfo()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSuccessfulImageSources().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSuccessfulImageSources()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSystemProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSystemProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isImageCaptureCancelledSet()) {
               buf.append("ImageCaptureCancelled");
               buf.append(String.valueOf(this.bean.isImageCaptureCancelled()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            ImageSummaryBeanImpl otherTyped = (ImageSummaryBeanImpl)other;
            this.computeChildDiff("FailedImageSource", this.bean.getFailedImageSource(), otherTyped.getFailedImageSource(), false);
            this.computeDiff("ImageCreationDate", this.bean.getImageCreationDate(), otherTyped.getImageCreationDate(), false);
            this.computeDiff("ImageCreationElapsedTime", this.bean.getImageCreationElapsedTime(), otherTyped.getImageCreationElapsedTime(), false);
            this.computeDiff("ImageFileName", this.bean.getImageFileName(), otherTyped.getImageFileName(), false);
            this.computeDiff("MuxerClass", this.bean.getMuxerClass(), otherTyped.getMuxerClass(), false);
            this.computeDiff("RequestStackTrace", this.bean.getRequestStackTrace(), otherTyped.getRequestStackTrace(), false);
            this.computeDiff("RequesterThreadName", this.bean.getRequesterThreadName(), otherTyped.getRequesterThreadName(), false);
            this.computeDiff("RequesterUserId", this.bean.getRequesterUserId(), otherTyped.getRequesterUserId(), false);
            this.computeDiff("ServerName", this.bean.getServerName(), otherTyped.getServerName(), false);
            this.computeDiff("ServerReleaseInfo", this.bean.getServerReleaseInfo(), otherTyped.getServerReleaseInfo(), false);
            this.computeChildDiff("SuccessfulImageSources", this.bean.getSuccessfulImageSources(), otherTyped.getSuccessfulImageSources(), false);
            this.computeChildDiff("SystemProperties", this.bean.getSystemProperties(), otherTyped.getSystemProperties(), false);
            this.computeDiff("ImageCaptureCancelled", this.bean.isImageCaptureCancelled(), otherTyped.isImageCaptureCancelled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ImageSummaryBeanImpl original = (ImageSummaryBeanImpl)event.getSourceBean();
            ImageSummaryBeanImpl proposed = (ImageSummaryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FailedImageSource")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addFailedImageSource((FailedImageSourceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeFailedImageSource((FailedImageSourceBean)update.getRemovedObject());
                  }

                  if (original.getFailedImageSource() == null || original.getFailedImageSource().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("ImageCreationDate")) {
                  original.setImageCreationDate(proposed.getImageCreationDate());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ImageCreationElapsedTime")) {
                  original.setImageCreationElapsedTime(proposed.getImageCreationElapsedTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ImageFileName")) {
                  original.setImageFileName(proposed.getImageFileName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MuxerClass")) {
                  original.setMuxerClass(proposed.getMuxerClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("RequestStackTrace")) {
                  original.setRequestStackTrace(proposed.getRequestStackTrace());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("RequesterThreadName")) {
                  original.setRequesterThreadName(proposed.getRequesterThreadName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("RequesterUserId")) {
                  original.setRequesterUserId(proposed.getRequesterUserId());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("ServerName")) {
                  original.setServerName(proposed.getServerName());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("ServerReleaseInfo")) {
                  original.setServerReleaseInfo(proposed.getServerReleaseInfo());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("SuccessfulImageSources")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSuccessfulImageSource((SuccessfulImageSourceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSuccessfulImageSource((SuccessfulImageSourceBean)update.getRemovedObject());
                  }

                  if (original.getSuccessfulImageSources() == null || original.getSuccessfulImageSources().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  }
               } else if (prop.equals("SystemProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSystemProperty((SystemPropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSystemProperty((SystemPropertyBean)update.getRemovedObject());
                  }

                  if (original.getSystemProperties() == null || original.getSystemProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
                  }
               } else if (prop.equals("ImageCaptureCancelled")) {
                  original.setImageCaptureCancelled(proposed.isImageCaptureCancelled());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            ImageSummaryBeanImpl copy = (ImageSummaryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("FailedImageSource")) && this.bean.isFailedImageSourceSet() && !copy._isSet(9)) {
               FailedImageSourceBean[] oldFailedImageSource = this.bean.getFailedImageSource();
               FailedImageSourceBean[] newFailedImageSource = new FailedImageSourceBean[oldFailedImageSource.length];

               for(i = 0; i < newFailedImageSource.length; ++i) {
                  newFailedImageSource[i] = (FailedImageSourceBean)((FailedImageSourceBean)this.createCopy((AbstractDescriptorBean)oldFailedImageSource[i], includeObsolete));
               }

               copy.setFailedImageSource(newFailedImageSource);
            }

            if ((excludeProps == null || !excludeProps.contains("ImageCreationDate")) && this.bean.isImageCreationDateSet()) {
               copy.setImageCreationDate(this.bean.getImageCreationDate());
            }

            if ((excludeProps == null || !excludeProps.contains("ImageCreationElapsedTime")) && this.bean.isImageCreationElapsedTimeSet()) {
               copy.setImageCreationElapsedTime(this.bean.getImageCreationElapsedTime());
            }

            if ((excludeProps == null || !excludeProps.contains("ImageFileName")) && this.bean.isImageFileNameSet()) {
               copy.setImageFileName(this.bean.getImageFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("MuxerClass")) && this.bean.isMuxerClassSet()) {
               copy.setMuxerClass(this.bean.getMuxerClass());
            }

            if ((excludeProps == null || !excludeProps.contains("RequestStackTrace")) && this.bean.isRequestStackTraceSet()) {
               copy.setRequestStackTrace(this.bean.getRequestStackTrace());
            }

            if ((excludeProps == null || !excludeProps.contains("RequesterThreadName")) && this.bean.isRequesterThreadNameSet()) {
               copy.setRequesterThreadName(this.bean.getRequesterThreadName());
            }

            if ((excludeProps == null || !excludeProps.contains("RequesterUserId")) && this.bean.isRequesterUserIdSet()) {
               copy.setRequesterUserId(this.bean.getRequesterUserId());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerName")) && this.bean.isServerNameSet()) {
               copy.setServerName(this.bean.getServerName());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerReleaseInfo")) && this.bean.isServerReleaseInfoSet()) {
               copy.setServerReleaseInfo(this.bean.getServerReleaseInfo());
            }

            if ((excludeProps == null || !excludeProps.contains("SuccessfulImageSources")) && this.bean.isSuccessfulImageSourcesSet() && !copy._isSet(8)) {
               SuccessfulImageSourceBean[] oldSuccessfulImageSources = this.bean.getSuccessfulImageSources();
               SuccessfulImageSourceBean[] newSuccessfulImageSources = new SuccessfulImageSourceBean[oldSuccessfulImageSources.length];

               for(i = 0; i < newSuccessfulImageSources.length; ++i) {
                  newSuccessfulImageSources[i] = (SuccessfulImageSourceBean)((SuccessfulImageSourceBean)this.createCopy((AbstractDescriptorBean)oldSuccessfulImageSources[i], includeObsolete));
               }

               copy.setSuccessfulImageSources(newSuccessfulImageSources);
            }

            if ((excludeProps == null || !excludeProps.contains("SystemProperties")) && this.bean.isSystemPropertiesSet() && !copy._isSet(7)) {
               SystemPropertyBean[] oldSystemProperties = this.bean.getSystemProperties();
               SystemPropertyBean[] newSystemProperties = new SystemPropertyBean[oldSystemProperties.length];

               for(i = 0; i < newSystemProperties.length; ++i) {
                  newSystemProperties[i] = (SystemPropertyBean)((SystemPropertyBean)this.createCopy((AbstractDescriptorBean)oldSystemProperties[i], includeObsolete));
               }

               copy.setSystemProperties(newSystemProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("ImageCaptureCancelled")) && this.bean.isImageCaptureCancelledSet()) {
               copy.setImageCaptureCancelled(this.bean.isImageCaptureCancelled());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getFailedImageSource(), clazz, annotation);
         this.inferSubTree(this.bean.getSuccessfulImageSources(), clazz, annotation);
         this.inferSubTree(this.bean.getSystemProperties(), clazz, annotation);
      }
   }
}

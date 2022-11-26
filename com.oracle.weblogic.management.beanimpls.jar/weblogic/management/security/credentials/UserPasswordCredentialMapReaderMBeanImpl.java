package weblogic.management.security.credentials;

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
import weblogic.management.utils.InvalidCursorException;
import weblogic.management.utils.ListerMBeanImpl;
import weblogic.management.utils.NotFoundException;
import weblogic.utils.collections.CombinedIterator;

public class UserPasswordCredentialMapReaderMBeanImpl extends ListerMBeanImpl implements UserPasswordCredentialMapReaderMBean, Serializable {
   private static SchemaHelper2 _schemaHelper;

   public UserPasswordCredentialMapReaderMBeanImpl() {
      this._initializeProperty(-1);
   }

   public UserPasswordCredentialMapReaderMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public UserPasswordCredentialMapReaderMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getRemoteUserName(String param0, String param1) throws NotFoundException {
      throw new AssertionError("Method not implemented");
   }

   public String getRemotePassword(String param0, String param1) throws NotFoundException {
      throw new AssertionError("Method not implemented");
   }

   public String listCredentials(String param0) {
      throw new AssertionError("Method not implemented");
   }

   public String getCurrentCredentialRemoteUserName(String param0) throws InvalidCursorException {
      throw new AssertionError("Method not implemented");
   }

   public String getCurrentCredentialRemotePassword(String param0) throws InvalidCursorException {
      throw new AssertionError("Method not implemented");
   }

   public String listMappings(String param0) {
      throw new AssertionError("Method not implemented");
   }

   public String getCurrentMappingWLSUserName(String param0) throws InvalidCursorException {
      throw new AssertionError("Method not implemented");
   }

   public String getCurrentMappingRemoteUserName(String param0) throws InvalidCursorException {
      throw new AssertionError("Method not implemented");
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
         idx = -1;
      }

      try {
         switch (idx) {
            default:
               return !initOne;
         }
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
      return "http://xmlns.oracle.com/weblogic/1.0/security.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/security";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String wls_getInterfaceClassName() {
      return "weblogic.management.security.credentials.UserPasswordCredentialMapReaderMBean";
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends ListerMBeanImpl.Helper {
      private UserPasswordCredentialMapReaderMBeanImpl bean;

      protected Helper(UserPasswordCredentialMapReaderMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return super.getPropertyIndex(propName);
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
            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            UserPasswordCredentialMapReaderMBeanImpl var2 = (UserPasswordCredentialMapReaderMBeanImpl)other;
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            UserPasswordCredentialMapReaderMBeanImpl original = (UserPasswordCredentialMapReaderMBeanImpl)event.getSourceBean();
            UserPasswordCredentialMapReaderMBeanImpl proposed = (UserPasswordCredentialMapReaderMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               super.applyPropertyUpdate(event, update);
            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            UserPasswordCredentialMapReaderMBeanImpl copy = (UserPasswordCredentialMapReaderMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
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

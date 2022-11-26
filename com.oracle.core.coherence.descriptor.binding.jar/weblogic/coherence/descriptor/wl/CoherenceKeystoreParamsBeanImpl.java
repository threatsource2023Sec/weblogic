package weblogic.coherence.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class CoherenceKeystoreParamsBeanImpl extends SettableBeanImpl implements CoherenceKeystoreParamsBean, Serializable {
   private String _CoherenceIdentityAlias;
   private String _CoherencePrivateKeyPassPhrase;
   private byte[] _CoherencePrivateKeyPassPhraseEncrypted;
   private static SchemaHelper2 _schemaHelper;

   public CoherenceKeystoreParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public CoherenceKeystoreParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CoherenceKeystoreParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getCoherenceIdentityAlias() {
      return this._CoherenceIdentityAlias;
   }

   public boolean isCoherenceIdentityAliasInherited() {
      return false;
   }

   public boolean isCoherenceIdentityAliasSet() {
      return this._isSet(0);
   }

   public void setCoherenceIdentityAlias(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CoherenceIdentityAlias;
      this._CoherenceIdentityAlias = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getCoherencePrivateKeyPassPhrase() {
      byte[] bEncrypted = this.getCoherencePrivateKeyPassPhraseEncrypted();
      return bEncrypted == null ? null : this._decrypt("CoherencePrivateKeyPassPhrase", bEncrypted);
   }

   public boolean isCoherencePrivateKeyPassPhraseInherited() {
      return false;
   }

   public boolean isCoherencePrivateKeyPassPhraseSet() {
      return this.isCoherencePrivateKeyPassPhraseEncryptedSet();
   }

   public void setCoherencePrivateKeyPassPhrase(String param0) {
      param0 = param0 == null ? null : param0.trim();
      this.setCoherencePrivateKeyPassPhraseEncrypted(param0 == null ? null : this._encrypt("CoherencePrivateKeyPassPhrase", param0));
   }

   public byte[] getCoherencePrivateKeyPassPhraseEncrypted() {
      return this._getHelper()._cloneArray(this._CoherencePrivateKeyPassPhraseEncrypted);
   }

   public String getCoherencePrivateKeyPassPhraseEncryptedAsString() {
      byte[] obj = this.getCoherencePrivateKeyPassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isCoherencePrivateKeyPassPhraseEncryptedInherited() {
      return false;
   }

   public boolean isCoherencePrivateKeyPassPhraseEncryptedSet() {
      return this._isSet(2);
   }

   public void setCoherencePrivateKeyPassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setCoherencePrivateKeyPassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setCoherencePrivateKeyPassPhraseEncrypted(byte[] param0) {
      byte[] _oldVal = this._CoherencePrivateKeyPassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: CoherencePrivateKeyPassPhraseEncrypted of CoherenceKeystoreParamsBean");
      } else {
         this._getHelper()._clearArray(this._CoherencePrivateKeyPassPhraseEncrypted);
         this._CoherencePrivateKeyPassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(2, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 1) {
            this._markSet(2, false);
         }
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._CoherenceIdentityAlias = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._CoherencePrivateKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._CoherencePrivateKeyPassPhraseEncrypted = null;
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 24:
               if (s.equals("coherence-identity-alias")) {
                  return 0;
               }
               break;
            case 33:
               if (s.equals("coherence-private-key-pass-phrase")) {
                  return 1;
               }
               break;
            case 43:
               if (s.equals("coherence-private-key-pass-phrase-encrypted")) {
                  return 2;
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
               return "coherence-identity-alias";
            case 1:
               return "coherence-private-key-pass-phrase";
            case 2:
               return "coherence-private-key-pass-phrase-encrypted";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private CoherenceKeystoreParamsBeanImpl bean;

      protected Helper(CoherenceKeystoreParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "CoherenceIdentityAlias";
            case 1:
               return "CoherencePrivateKeyPassPhrase";
            case 2:
               return "CoherencePrivateKeyPassPhraseEncrypted";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CoherenceIdentityAlias")) {
            return 0;
         } else if (propName.equals("CoherencePrivateKeyPassPhrase")) {
            return 1;
         } else {
            return propName.equals("CoherencePrivateKeyPassPhraseEncrypted") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isCoherenceIdentityAliasSet()) {
               buf.append("CoherenceIdentityAlias");
               buf.append(String.valueOf(this.bean.getCoherenceIdentityAlias()));
            }

            if (this.bean.isCoherencePrivateKeyPassPhraseSet()) {
               buf.append("CoherencePrivateKeyPassPhrase");
               buf.append(String.valueOf(this.bean.getCoherencePrivateKeyPassPhrase()));
            }

            if (this.bean.isCoherencePrivateKeyPassPhraseEncryptedSet()) {
               buf.append("CoherencePrivateKeyPassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCoherencePrivateKeyPassPhraseEncrypted())));
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
            CoherenceKeystoreParamsBeanImpl otherTyped = (CoherenceKeystoreParamsBeanImpl)other;
            this.computeDiff("CoherenceIdentityAlias", this.bean.getCoherenceIdentityAlias(), otherTyped.getCoherenceIdentityAlias(), false);
            this.computeDiff("CoherencePrivateKeyPassPhraseEncrypted", this.bean.getCoherencePrivateKeyPassPhraseEncrypted(), otherTyped.getCoherencePrivateKeyPassPhraseEncrypted(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceKeystoreParamsBeanImpl original = (CoherenceKeystoreParamsBeanImpl)event.getSourceBean();
            CoherenceKeystoreParamsBeanImpl proposed = (CoherenceKeystoreParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CoherenceIdentityAlias")) {
                  original.setCoherenceIdentityAlias(proposed.getCoherenceIdentityAlias());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (!prop.equals("CoherencePrivateKeyPassPhrase")) {
                  if (prop.equals("CoherencePrivateKeyPassPhraseEncrypted")) {
                     original.setCoherencePrivateKeyPassPhraseEncrypted(proposed.getCoherencePrivateKeyPassPhraseEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else {
                     super.applyPropertyUpdate(event, update);
                  }
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
            CoherenceKeystoreParamsBeanImpl copy = (CoherenceKeystoreParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CoherenceIdentityAlias")) && this.bean.isCoherenceIdentityAliasSet()) {
               copy.setCoherenceIdentityAlias(this.bean.getCoherenceIdentityAlias());
            }

            if ((excludeProps == null || !excludeProps.contains("CoherencePrivateKeyPassPhraseEncrypted")) && this.bean.isCoherencePrivateKeyPassPhraseEncryptedSet()) {
               Object o = this.bean.getCoherencePrivateKeyPassPhraseEncrypted();
               copy.setCoherencePrivateKeyPassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
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

package kodo.conf.descriptor;

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

public class TCPTransportBeanImpl extends PersistenceServerBeanImpl implements TCPTransportBean, Serializable {
   private String _Host;
   private int _Port;
   private int _SoTimeout;
   private static SchemaHelper2 _schemaHelper;

   public TCPTransportBeanImpl() {
      this._initializeProperty(-1);
   }

   public TCPTransportBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TCPTransportBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getSoTimeout() {
      return this._SoTimeout;
   }

   public boolean isSoTimeoutInherited() {
      return false;
   }

   public boolean isSoTimeoutSet() {
      return this._isSet(0);
   }

   public void setSoTimeout(int param0) {
      int _oldVal = this._SoTimeout;
      this._SoTimeout = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getHost() {
      return this._Host;
   }

   public boolean isHostInherited() {
      return false;
   }

   public boolean isHostSet() {
      return this._isSet(1);
   }

   public void setHost(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Host;
      this._Host = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getPort() {
      return this._Port;
   }

   public boolean isPortInherited() {
      return false;
   }

   public boolean isPortSet() {
      return this._isSet(2);
   }

   public void setPort(int param0) {
      int _oldVal = this._Port;
      this._Port = param0;
      this._postSet(2, _oldVal, param0);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._Host = "localhost";
               if (initOne) {
                  break;
               }
            case 2:
               this._Port = 5637;
               if (initOne) {
                  break;
               }
            case 0:
               this._SoTimeout = 0;
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

   public static class SchemaHelper2 extends PersistenceServerBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("host")) {
                  return 1;
               }

               if (s.equals("port")) {
                  return 2;
               }
               break;
            case 10:
               if (s.equals("so-timeout")) {
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
               return "so-timeout";
            case 1:
               return "host";
            case 2:
               return "port";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends PersistenceServerBeanImpl.Helper {
      private TCPTransportBeanImpl bean;

      protected Helper(TCPTransportBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "SoTimeout";
            case 1:
               return "Host";
            case 2:
               return "Port";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Host")) {
            return 1;
         } else if (propName.equals("Port")) {
            return 2;
         } else {
            return propName.equals("SoTimeout") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isHostSet()) {
               buf.append("Host");
               buf.append(String.valueOf(this.bean.getHost()));
            }

            if (this.bean.isPortSet()) {
               buf.append("Port");
               buf.append(String.valueOf(this.bean.getPort()));
            }

            if (this.bean.isSoTimeoutSet()) {
               buf.append("SoTimeout");
               buf.append(String.valueOf(this.bean.getSoTimeout()));
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
            TCPTransportBeanImpl otherTyped = (TCPTransportBeanImpl)other;
            this.computeDiff("Host", this.bean.getHost(), otherTyped.getHost(), false);
            this.computeDiff("Port", this.bean.getPort(), otherTyped.getPort(), false);
            this.computeDiff("SoTimeout", this.bean.getSoTimeout(), otherTyped.getSoTimeout(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TCPTransportBeanImpl original = (TCPTransportBeanImpl)event.getSourceBean();
            TCPTransportBeanImpl proposed = (TCPTransportBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Host")) {
                  original.setHost(proposed.getHost());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Port")) {
                  original.setPort(proposed.getPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("SoTimeout")) {
                  original.setSoTimeout(proposed.getSoTimeout());
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
            TCPTransportBeanImpl copy = (TCPTransportBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Host")) && this.bean.isHostSet()) {
               copy.setHost(this.bean.getHost());
            }

            if ((excludeProps == null || !excludeProps.contains("Port")) && this.bean.isPortSet()) {
               copy.setPort(this.bean.getPort());
            }

            if ((excludeProps == null || !excludeProps.contains("SoTimeout")) && this.bean.isSoTimeoutSet()) {
               copy.setSoTimeout(this.bean.getSoTimeout());
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

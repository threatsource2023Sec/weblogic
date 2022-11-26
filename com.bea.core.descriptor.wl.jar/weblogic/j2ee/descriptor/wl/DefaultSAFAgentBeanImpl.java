package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class DefaultSAFAgentBeanImpl extends SettableBeanImpl implements DefaultSAFAgentBean, Serializable {
   private long _BytesMaximum;
   private long _DefaultRetryDelayBase;
   private long _DefaultRetryDelayMaximum;
   private double _DefaultRetryDelayMultiplier;
   private long _DefaultTimeToLive;
   private boolean _LoggingEnabled;
   private int _MaximumMessageSize;
   private long _MessageBufferSize;
   private long _MessagesMaximum;
   private String _Notes;
   private String _PagingDirectory;
   private long _WindowInterval;
   private int _WindowSize;
   private static SchemaHelper2 _schemaHelper;

   public DefaultSAFAgentBeanImpl() {
      this._initializeProperty(-1);
   }

   public DefaultSAFAgentBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DefaultSAFAgentBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getNotes() {
      return this._Notes;
   }

   public boolean isNotesInherited() {
      return false;
   }

   public boolean isNotesSet() {
      return this._isSet(0);
   }

   public void setNotes(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Notes;
      this._Notes = param0;
      this._postSet(0, _oldVal, param0);
   }

   public long getBytesMaximum() {
      return this._BytesMaximum;
   }

   public boolean isBytesMaximumInherited() {
      return false;
   }

   public boolean isBytesMaximumSet() {
      return this._isSet(1);
   }

   public void setBytesMaximum(long param0) {
      LegalChecks.checkMin("BytesMaximum", param0, -1L);
      long _oldVal = this._BytesMaximum;
      this._BytesMaximum = param0;
      this._postSet(1, _oldVal, param0);
   }

   public long getMessagesMaximum() {
      return this._MessagesMaximum;
   }

   public boolean isMessagesMaximumInherited() {
      return false;
   }

   public boolean isMessagesMaximumSet() {
      return this._isSet(2);
   }

   public void setMessagesMaximum(long param0) {
      LegalChecks.checkMin("MessagesMaximum", param0, -1L);
      long _oldVal = this._MessagesMaximum;
      this._MessagesMaximum = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getMaximumMessageSize() {
      return this._MaximumMessageSize;
   }

   public boolean isMaximumMessageSizeInherited() {
      return false;
   }

   public boolean isMaximumMessageSizeSet() {
      return this._isSet(3);
   }

   public void setMaximumMessageSize(int param0) {
      LegalChecks.checkMin("MaximumMessageSize", param0, 0);
      int _oldVal = this._MaximumMessageSize;
      this._MaximumMessageSize = param0;
      this._postSet(3, _oldVal, param0);
   }

   public long getDefaultRetryDelayBase() {
      return this._DefaultRetryDelayBase;
   }

   public boolean isDefaultRetryDelayBaseInherited() {
      return false;
   }

   public boolean isDefaultRetryDelayBaseSet() {
      return this._isSet(4);
   }

   public void setDefaultRetryDelayBase(long param0) {
      LegalChecks.checkMin("DefaultRetryDelayBase", param0, 1L);
      long _oldVal = this._DefaultRetryDelayBase;
      this._DefaultRetryDelayBase = param0;
      this._postSet(4, _oldVal, param0);
   }

   public long getDefaultRetryDelayMaximum() {
      return this._DefaultRetryDelayMaximum;
   }

   public boolean isDefaultRetryDelayMaximumInherited() {
      return false;
   }

   public boolean isDefaultRetryDelayMaximumSet() {
      return this._isSet(5);
   }

   public void setDefaultRetryDelayMaximum(long param0) {
      LegalChecks.checkMin("DefaultRetryDelayMaximum", param0, 1L);
      long _oldVal = this._DefaultRetryDelayMaximum;
      this._DefaultRetryDelayMaximum = param0;
      this._postSet(5, _oldVal, param0);
   }

   public double getDefaultRetryDelayMultiplier() {
      return this._DefaultRetryDelayMultiplier;
   }

   public boolean isDefaultRetryDelayMultiplierInherited() {
      return false;
   }

   public boolean isDefaultRetryDelayMultiplierSet() {
      return this._isSet(6);
   }

   public void setDefaultRetryDelayMultiplier(double param0) {
      LegalChecks.checkMin("DefaultRetryDelayMultiplier", param0, 1.0);
      double _oldVal = this._DefaultRetryDelayMultiplier;
      this._DefaultRetryDelayMultiplier = param0;
      this._postSet(6, _oldVal, param0);
   }

   public int getWindowSize() {
      return this._WindowSize;
   }

   public boolean isWindowSizeInherited() {
      return false;
   }

   public boolean isWindowSizeSet() {
      return this._isSet(7);
   }

   public void setWindowSize(int param0) {
      LegalChecks.checkMin("WindowSize", param0, 1);
      int _oldVal = this._WindowSize;
      this._WindowSize = param0;
      this._postSet(7, _oldVal, param0);
   }

   public boolean isLoggingEnabled() {
      return this._LoggingEnabled;
   }

   public boolean isLoggingEnabledInherited() {
      return false;
   }

   public boolean isLoggingEnabledSet() {
      return this._isSet(8);
   }

   public void setLoggingEnabled(boolean param0) {
      boolean _oldVal = this._LoggingEnabled;
      this._LoggingEnabled = param0;
      this._postSet(8, _oldVal, param0);
   }

   public long getDefaultTimeToLive() {
      return this._DefaultTimeToLive;
   }

   public boolean isDefaultTimeToLiveInherited() {
      return false;
   }

   public boolean isDefaultTimeToLiveSet() {
      return this._isSet(9);
   }

   public void setDefaultTimeToLive(long param0) {
      LegalChecks.checkMin("DefaultTimeToLive", param0, 0L);
      long _oldVal = this._DefaultTimeToLive;
      this._DefaultTimeToLive = param0;
      this._postSet(9, _oldVal, param0);
   }

   public long getMessageBufferSize() {
      return this._MessageBufferSize;
   }

   public boolean isMessageBufferSizeInherited() {
      return false;
   }

   public boolean isMessageBufferSizeSet() {
      return this._isSet(10);
   }

   public void setMessageBufferSize(long param0) {
      LegalChecks.checkMin("MessageBufferSize", param0, -1L);
      long _oldVal = this._MessageBufferSize;
      this._MessageBufferSize = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getPagingDirectory() {
      return this._PagingDirectory;
   }

   public boolean isPagingDirectoryInherited() {
      return false;
   }

   public boolean isPagingDirectorySet() {
      return this._isSet(11);
   }

   public void setPagingDirectory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PagingDirectory;
      this._PagingDirectory = param0;
      this._postSet(11, _oldVal, param0);
   }

   public long getWindowInterval() {
      return this._WindowInterval;
   }

   public boolean isWindowIntervalInherited() {
      return false;
   }

   public boolean isWindowIntervalSet() {
      return this._isSet(12);
   }

   public void setWindowInterval(long param0) {
      LegalChecks.checkMin("WindowInterval", param0, 0L);
      long _oldVal = this._WindowInterval;
      this._WindowInterval = param0;
      this._postSet(12, _oldVal, param0);
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
               this._BytesMaximum = -1L;
               if (initOne) {
                  break;
               }
            case 4:
               this._DefaultRetryDelayBase = 20000L;
               if (initOne) {
                  break;
               }
            case 5:
               this._DefaultRetryDelayMaximum = 180000L;
               if (initOne) {
                  break;
               }
            case 6:
               this._DefaultRetryDelayMultiplier = 1.0;
               if (initOne) {
                  break;
               }
            case 9:
               this._DefaultTimeToLive = 0L;
               if (initOne) {
                  break;
               }
            case 3:
               this._MaximumMessageSize = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 10:
               this._MessageBufferSize = -1L;
               if (initOne) {
                  break;
               }
            case 2:
               this._MessagesMaximum = -1L;
               if (initOne) {
                  break;
               }
            case 0:
               this._Notes = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._PagingDirectory = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._WindowInterval = 0L;
               if (initOne) {
                  break;
               }
            case 7:
               this._WindowSize = 10;
               if (initOne) {
                  break;
               }
            case 8:
               this._LoggingEnabled = true;
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
            case 5:
               if (s.equals("notes")) {
                  return 0;
               }
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 17:
            case 18:
            case 21:
            case 22:
            case 23:
            case 25:
            case 26:
            case 28:
            case 29:
            default:
               break;
            case 11:
               if (s.equals("window-size")) {
                  return 7;
               }
               break;
            case 13:
               if (s.equals("bytes-maximum")) {
                  return 1;
               }
               break;
            case 15:
               if (s.equals("window-interval")) {
                  return 12;
               }

               if (s.equals("logging-enabled")) {
                  return 8;
               }
               break;
            case 16:
               if (s.equals("messages-maximum")) {
                  return 2;
               }

               if (s.equals("paging-directory")) {
                  return 11;
               }
               break;
            case 19:
               if (s.equals("message-buffer-size")) {
                  return 10;
               }
               break;
            case 20:
               if (s.equals("default-time-to-live")) {
                  return 9;
               }

               if (s.equals("maximum-message-size")) {
                  return 3;
               }
               break;
            case 24:
               if (s.equals("default-retry-delay-base")) {
                  return 4;
               }
               break;
            case 27:
               if (s.equals("default-retry-delay-maximum")) {
                  return 5;
               }
               break;
            case 30:
               if (s.equals("default-retry-delay-multiplier")) {
                  return 6;
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
               return "notes";
            case 1:
               return "bytes-maximum";
            case 2:
               return "messages-maximum";
            case 3:
               return "maximum-message-size";
            case 4:
               return "default-retry-delay-base";
            case 5:
               return "default-retry-delay-maximum";
            case 6:
               return "default-retry-delay-multiplier";
            case 7:
               return "window-size";
            case 8:
               return "logging-enabled";
            case 9:
               return "default-time-to-live";
            case 10:
               return "message-buffer-size";
            case 11:
               return "paging-directory";
            case 12:
               return "window-interval";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private DefaultSAFAgentBeanImpl bean;

      protected Helper(DefaultSAFAgentBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Notes";
            case 1:
               return "BytesMaximum";
            case 2:
               return "MessagesMaximum";
            case 3:
               return "MaximumMessageSize";
            case 4:
               return "DefaultRetryDelayBase";
            case 5:
               return "DefaultRetryDelayMaximum";
            case 6:
               return "DefaultRetryDelayMultiplier";
            case 7:
               return "WindowSize";
            case 8:
               return "LoggingEnabled";
            case 9:
               return "DefaultTimeToLive";
            case 10:
               return "MessageBufferSize";
            case 11:
               return "PagingDirectory";
            case 12:
               return "WindowInterval";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BytesMaximum")) {
            return 1;
         } else if (propName.equals("DefaultRetryDelayBase")) {
            return 4;
         } else if (propName.equals("DefaultRetryDelayMaximum")) {
            return 5;
         } else if (propName.equals("DefaultRetryDelayMultiplier")) {
            return 6;
         } else if (propName.equals("DefaultTimeToLive")) {
            return 9;
         } else if (propName.equals("MaximumMessageSize")) {
            return 3;
         } else if (propName.equals("MessageBufferSize")) {
            return 10;
         } else if (propName.equals("MessagesMaximum")) {
            return 2;
         } else if (propName.equals("Notes")) {
            return 0;
         } else if (propName.equals("PagingDirectory")) {
            return 11;
         } else if (propName.equals("WindowInterval")) {
            return 12;
         } else if (propName.equals("WindowSize")) {
            return 7;
         } else {
            return propName.equals("LoggingEnabled") ? 8 : super.getPropertyIndex(propName);
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
            if (this.bean.isBytesMaximumSet()) {
               buf.append("BytesMaximum");
               buf.append(String.valueOf(this.bean.getBytesMaximum()));
            }

            if (this.bean.isDefaultRetryDelayBaseSet()) {
               buf.append("DefaultRetryDelayBase");
               buf.append(String.valueOf(this.bean.getDefaultRetryDelayBase()));
            }

            if (this.bean.isDefaultRetryDelayMaximumSet()) {
               buf.append("DefaultRetryDelayMaximum");
               buf.append(String.valueOf(this.bean.getDefaultRetryDelayMaximum()));
            }

            if (this.bean.isDefaultRetryDelayMultiplierSet()) {
               buf.append("DefaultRetryDelayMultiplier");
               buf.append(String.valueOf(this.bean.getDefaultRetryDelayMultiplier()));
            }

            if (this.bean.isDefaultTimeToLiveSet()) {
               buf.append("DefaultTimeToLive");
               buf.append(String.valueOf(this.bean.getDefaultTimeToLive()));
            }

            if (this.bean.isMaximumMessageSizeSet()) {
               buf.append("MaximumMessageSize");
               buf.append(String.valueOf(this.bean.getMaximumMessageSize()));
            }

            if (this.bean.isMessageBufferSizeSet()) {
               buf.append("MessageBufferSize");
               buf.append(String.valueOf(this.bean.getMessageBufferSize()));
            }

            if (this.bean.isMessagesMaximumSet()) {
               buf.append("MessagesMaximum");
               buf.append(String.valueOf(this.bean.getMessagesMaximum()));
            }

            if (this.bean.isNotesSet()) {
               buf.append("Notes");
               buf.append(String.valueOf(this.bean.getNotes()));
            }

            if (this.bean.isPagingDirectorySet()) {
               buf.append("PagingDirectory");
               buf.append(String.valueOf(this.bean.getPagingDirectory()));
            }

            if (this.bean.isWindowIntervalSet()) {
               buf.append("WindowInterval");
               buf.append(String.valueOf(this.bean.getWindowInterval()));
            }

            if (this.bean.isWindowSizeSet()) {
               buf.append("WindowSize");
               buf.append(String.valueOf(this.bean.getWindowSize()));
            }

            if (this.bean.isLoggingEnabledSet()) {
               buf.append("LoggingEnabled");
               buf.append(String.valueOf(this.bean.isLoggingEnabled()));
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
            DefaultSAFAgentBeanImpl otherTyped = (DefaultSAFAgentBeanImpl)other;
            this.computeDiff("BytesMaximum", this.bean.getBytesMaximum(), otherTyped.getBytesMaximum(), true);
            this.computeDiff("DefaultRetryDelayBase", this.bean.getDefaultRetryDelayBase(), otherTyped.getDefaultRetryDelayBase(), true);
            this.computeDiff("DefaultRetryDelayMaximum", this.bean.getDefaultRetryDelayMaximum(), otherTyped.getDefaultRetryDelayMaximum(), true);
            this.computeDiff("DefaultRetryDelayMultiplier", this.bean.getDefaultRetryDelayMultiplier(), otherTyped.getDefaultRetryDelayMultiplier(), true);
            this.computeDiff("DefaultTimeToLive", this.bean.getDefaultTimeToLive(), otherTyped.getDefaultTimeToLive(), true);
            this.computeDiff("MaximumMessageSize", this.bean.getMaximumMessageSize(), otherTyped.getMaximumMessageSize(), true);
            this.computeDiff("MessageBufferSize", this.bean.getMessageBufferSize(), otherTyped.getMessageBufferSize(), true);
            this.computeDiff("MessagesMaximum", this.bean.getMessagesMaximum(), otherTyped.getMessagesMaximum(), true);
            this.computeDiff("Notes", this.bean.getNotes(), otherTyped.getNotes(), true);
            this.computeDiff("PagingDirectory", this.bean.getPagingDirectory(), otherTyped.getPagingDirectory(), false);
            this.computeDiff("WindowInterval", this.bean.getWindowInterval(), otherTyped.getWindowInterval(), true);
            this.computeDiff("WindowSize", this.bean.getWindowSize(), otherTyped.getWindowSize(), true);
            this.computeDiff("LoggingEnabled", this.bean.isLoggingEnabled(), otherTyped.isLoggingEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DefaultSAFAgentBeanImpl original = (DefaultSAFAgentBeanImpl)event.getSourceBean();
            DefaultSAFAgentBeanImpl proposed = (DefaultSAFAgentBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BytesMaximum")) {
                  original.setBytesMaximum(proposed.getBytesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("DefaultRetryDelayBase")) {
                  original.setDefaultRetryDelayBase(proposed.getDefaultRetryDelayBase());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("DefaultRetryDelayMaximum")) {
                  original.setDefaultRetryDelayMaximum(proposed.getDefaultRetryDelayMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("DefaultRetryDelayMultiplier")) {
                  original.setDefaultRetryDelayMultiplier(proposed.getDefaultRetryDelayMultiplier());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("DefaultTimeToLive")) {
                  original.setDefaultTimeToLive(proposed.getDefaultTimeToLive());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("MaximumMessageSize")) {
                  original.setMaximumMessageSize(proposed.getMaximumMessageSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("MessageBufferSize")) {
                  original.setMessageBufferSize(proposed.getMessageBufferSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MessagesMaximum")) {
                  original.setMessagesMaximum(proposed.getMessagesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Notes")) {
                  original.setNotes(proposed.getNotes());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("PagingDirectory")) {
                  original.setPagingDirectory(proposed.getPagingDirectory());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("WindowInterval")) {
                  original.setWindowInterval(proposed.getWindowInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("WindowSize")) {
                  original.setWindowSize(proposed.getWindowSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("LoggingEnabled")) {
                  original.setLoggingEnabled(proposed.isLoggingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
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
            DefaultSAFAgentBeanImpl copy = (DefaultSAFAgentBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BytesMaximum")) && this.bean.isBytesMaximumSet()) {
               copy.setBytesMaximum(this.bean.getBytesMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultRetryDelayBase")) && this.bean.isDefaultRetryDelayBaseSet()) {
               copy.setDefaultRetryDelayBase(this.bean.getDefaultRetryDelayBase());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultRetryDelayMaximum")) && this.bean.isDefaultRetryDelayMaximumSet()) {
               copy.setDefaultRetryDelayMaximum(this.bean.getDefaultRetryDelayMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultRetryDelayMultiplier")) && this.bean.isDefaultRetryDelayMultiplierSet()) {
               copy.setDefaultRetryDelayMultiplier(this.bean.getDefaultRetryDelayMultiplier());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultTimeToLive")) && this.bean.isDefaultTimeToLiveSet()) {
               copy.setDefaultTimeToLive(this.bean.getDefaultTimeToLive());
            }

            if ((excludeProps == null || !excludeProps.contains("MaximumMessageSize")) && this.bean.isMaximumMessageSizeSet()) {
               copy.setMaximumMessageSize(this.bean.getMaximumMessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageBufferSize")) && this.bean.isMessageBufferSizeSet()) {
               copy.setMessageBufferSize(this.bean.getMessageBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagesMaximum")) && this.bean.isMessagesMaximumSet()) {
               copy.setMessagesMaximum(this.bean.getMessagesMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("Notes")) && this.bean.isNotesSet()) {
               copy.setNotes(this.bean.getNotes());
            }

            if ((excludeProps == null || !excludeProps.contains("PagingDirectory")) && this.bean.isPagingDirectorySet()) {
               copy.setPagingDirectory(this.bean.getPagingDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("WindowInterval")) && this.bean.isWindowIntervalSet()) {
               copy.setWindowInterval(this.bean.getWindowInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("WindowSize")) && this.bean.isWindowSizeSet()) {
               copy.setWindowSize(this.bean.getWindowSize());
            }

            if ((excludeProps == null || !excludeProps.contains("LoggingEnabled")) && this.bean.isLoggingEnabledSet()) {
               copy.setLoggingEnabled(this.bean.isLoggingEnabled());
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

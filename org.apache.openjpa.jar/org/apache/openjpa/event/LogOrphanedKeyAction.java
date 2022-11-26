package org.apache.openjpa.event;

import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.log.LogFactoryImpl;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ValueMetaData;

public class LogOrphanedKeyAction implements OrphanedKeyAction {
   private static final Localizer _loc = Localizer.forPackage(LogOrphanedKeyAction.class);
   private String _channel = "openjpa.Runtime";
   private short _level = 4;

   public String getChannel() {
      return this._channel;
   }

   public void setChannel(String channel) {
      this._channel = channel;
   }

   public short getLevel() {
      return this._level;
   }

   public void setLevel(short level) {
      this._level = level;
   }

   public void setLevel(String level) {
      this._level = LogFactoryImpl.getLevel(level);
   }

   public Object orphan(Object oid, OpenJPAStateManager sm, ValueMetaData vmd) {
      Log log = vmd.getRepository().getConfiguration().getLog(this._channel);
      Object owner = sm == null ? null : sm.getId();
      String msg = owner == null ? "orphaned-key" : "orphaned-key-owner";
      switch (this._level) {
         case 1:
            if (log.isTraceEnabled()) {
               log.trace(_loc.get(msg, oid, vmd, owner));
            }
         case 2:
         default:
            break;
         case 3:
            if (log.isInfoEnabled()) {
               log.info(_loc.get(msg, oid, vmd, owner));
            }
            break;
         case 4:
            if (log.isWarnEnabled()) {
               log.warn(_loc.get(msg, oid, vmd, owner));
            }
            break;
         case 5:
            if (log.isErrorEnabled()) {
               log.error(_loc.get(msg, oid, vmd, owner));
            }
            break;
         case 6:
            if (log.isFatalEnabled()) {
               log.fatal(_loc.get(msg, oid, vmd, owner));
            }
      }

      return null;
   }
}

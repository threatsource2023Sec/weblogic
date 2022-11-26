package com.bea.common.ldap;

import java.util.Map;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.meta.ClassMetaData;
import weblogic.utils.collections.ConcurrentHashMap;

public class ParserFactory {
   private Log log;
   private boolean isVDETimestamp;
   private Map parserMap;

   public ParserFactory(Log log) {
      this(log, false);
   }

   public ParserFactory(Log log, boolean isVDETimestamp) {
      this.parserMap = new ConcurrentHashMap();
      this.log = log;
      this.isVDETimestamp = isVDETimestamp;
   }

   public DataLoader getDataLoader(ClassMetaData meta) {
      return this.getParser(meta);
   }

   public StateParser getStateParser(ClassMetaData meta) {
      return this.getParser(meta);
   }

   public ObjectIdConverter getObjectIdConverter(ClassMetaData meta) {
      return this.getParser(meta);
   }

   private ParserImpl getParser(ClassMetaData meta) {
      ParserImpl p = (ParserImpl)this.parserMap.get(meta);
      if (p == null) {
         p = new ParserImpl(meta, this, this.isVDETimestamp);
         this.parserMap.put(meta, p);
      }

      return p;
   }

   public Log getLog() {
      return this.log;
   }
}

package weblogic.persistence;

import java.io.InputStream;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.ReaderEvent2;
import weblogic.application.descriptor.ReaderEventInfo;
import weblogic.application.descriptor.VersionMunger;

public final class PersistenceReader extends VersionMunger {
   private static final String SCHEMA_HELPER = "weblogic.j2ee.descriptor.PersistenceBeanImpl$SchemaHelper2";
   public static final String DUMMY_PERSISTENCE_UNIT = "__ORACLE_WLS_INTERNAL_DUMMY_PERSISTENCE_UNIT";
   private boolean addDummyPU;
   private boolean addExcludeUnlisted;

   public PersistenceReader(InputStream is, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      super(is, loader, "weblogic.j2ee.descriptor.PersistenceBeanImpl$SchemaHelper2");
   }

   protected String getLatestSchemaVersion() {
      return "2.1";
   }

   protected boolean isOldSchema() {
      if ("persistence".equals(this.currentEvent.getElementName())) {
         ReaderEventInfo rei = this.currentEvent.getReaderEventInfo();
         if (!this.getLatestSchemaVersion().equals(rei.getAttributeValue((String)null, "version"))) {
            this.isOldSchema = true;
         }
      }

      return this.isOldSchema;
   }

   protected boolean enableCallbacksOnSchema() {
      return this.isOldSchema;
   }

   protected void transformOldSchema() {
      if ("persistence".equals(this.currentEvent.getElementName())) {
         ReaderEventInfo info = this.currentEvent.getReaderEventInfo();
         int count = info.getAttributeCount();

         for(int i = 0; i < count; ++i) {
            if ("version".equals(info.getAttributeLocalName(i))) {
               this.versionInfo = info.getAttributeValue(i);
               this.currentEvent.getReaderEventInfo().setAttributeValue("2.1", i);
            }
         }

         if (this.versionInfo == null) {
            this.versionInfo = "1.0";
         }

         this.transformNamespace("http://xmlns.jcp.org/xml/ns/persistence", this.currentEvent, "http://java.sun.com/xml/ns/persistence");
      }

      this.tranformedNamespace = "http://xmlns.jcp.org/xml/ns/persistence";
   }

   protected VersionMunger.Continuation onStartElement(String localName) {
      if ("persistence".equals(localName)) {
         this.addDummyPU = "1.0".equals(this.versionInfo);
      } else if ("persistence-unit".equals(localName)) {
         this.addExcludeUnlisted = "1.0".equals(this.versionInfo);
         this.addDummyPU = false;
      } else if ("exclude-unlisted-classes".equals(localName)) {
         this.addExcludeUnlisted = false;
      } else if ("properties".equals(localName) && this.addExcludeUnlisted) {
         this.addExcludeUnlistedClasses();
         List children = this.currentEvent.getParent().getChildren();
         Object propertiesElem = children.remove(children.size() - 2);
         children.add(propertiesElem);
         return this.USE_BUFFER;
      }

      return CONTINUE;
   }

   protected VersionMunger.Continuation onEndElement(String localName) {
      if ("persistence".equals(localName) && this.addDummyPU) {
         this.addDummyPU();
         this.pushEndElement(localName);
         return this.USE_BUFFER;
      } else if ("persistence-unit".equals(localName) && this.addExcludeUnlisted) {
         this.addExcludeUnlistedClasses();
         this.pushEndElement(localName);
         return this.USE_BUFFER;
      } else {
         if ("exclude-unlisted-classes".equals(localName) && "1.0".equals(this.versionInfo)) {
            ReaderEventInfo info = this.lastEvent.getReaderEventInfo();
            char[] chars = info.getCharacters();
            if (chars == null || this.isAllWhiteSpace(chars)) {
               info.setCharacters("false".toCharArray());
            }
         }

         return CONTINUE;
      }
   }

   protected String getOriginalVersion() {
      return this.versionInfo;
   }

   private void addDummyPU() {
      this.forceSkipParent = true;
      this.pushStartElementLastEvent("persistence-unit");
      ReaderEventInfo info = ((ReaderEvent2)this.stack.peek()).getReaderEventInfo();
      info.setAttributeCount(1);
      info.setAttributeValue("__ORACLE_WLS_INTERNAL_DUMMY_PERSISTENCE_UNIT", (String)null, "name");
      this.pushEndElement("persistence-unit");
      this.forceSkipParent = false;
      this.addDummyPU = false;
   }

   private void addExcludeUnlistedClasses() {
      this.forceSkipParent = true;
      this.pushStartElementLastEvent("exclude-unlisted-classes");
      ReaderEventInfo info = ((ReaderEvent2)this.stack.peek()).getReaderEventInfo();
      info.setCharacters("false".toCharArray());
      this.pushEndElement("exclude-unlisted-classes");
      this.forceSkipParent = false;
      this.addExcludeUnlisted = false;
   }

   private boolean isAllWhiteSpace(char[] chars) {
      char[] var2 = chars;
      int var3 = chars.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char ch = var2[var4];
         if (!this.isWhiteSpace(ch)) {
            return false;
         }
      }

      return true;
   }

   private boolean isWhiteSpace(char ch) {
      return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
   }
}

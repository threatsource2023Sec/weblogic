package weblogic.application.descriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.descriptor.DescriptorManager;
import weblogic.utils.Debug;

public class PlaybackReader implements XMLStreamReader {
   private boolean debug = Debug.getCategory("weblogic.descriptor.playback").isEnabled();
   protected ReaderEvent2 root;
   protected ReaderEvent2 currentEvent;
   protected PlaybackIterator iterator;
   protected int currentState;
   protected ReaderEventInfo.Namespaces namespaces;
   protected ReaderEventInfo.Attributes attributes;
   private String absolutePath;
   String dtdNamespaceURI = null;

   public PlaybackReader(ReaderEvent2 root, String absolutePath) throws XMLStreamException {
      this.root = root;
      this.absolutePath = absolutePath;
      this.currentEvent = root;
      this.currentState = root.getReaderEventInfo().getEventType();
      this.iterator = new PlaybackIterator(root);
   }

   String getAbsolutePath() {
      return this.absolutePath;
   }

   public String getLocalName() {
      return this.currentEvent.getReaderEventInfo().getElementName();
   }

   public char[] getTextCharacters() {
      return this.currentEvent.getReaderEventInfo().getCharacters();
   }

   public int next() throws XMLStreamException {
      this.currentState = this.iterator.next();
      this.currentEvent = this.iterator.event();
      if (this.debug) {
         System.out.println("-> next: " + Utils.type2Str(this.currentState, this));
      }

      if (this.currentEvent.isDiscarded()) {
         if (this.debug) {
            System.out.println("-> skiped: " + Utils.type2Str(this.currentState, this));
         }

         return this.next();
      } else {
         return this.currentState;
      }
   }

   public int nextTag() throws XMLStreamException {
      if (this.debug) {
         System.out.println("->nextTag");
      }

      return this.next();
   }

   public String getElementText() throws XMLStreamException {
      if (this.debug) {
         System.out.println("->getElementText: " + new String(this.getTextCharacters()));
      }

      return new String(this.getTextCharacters());
   }

   public void require(int a, String b, String c) throws XMLStreamException {
      if (this.debug) {
         System.out.println("->require");
      }

   }

   public boolean hasNext() throws XMLStreamException {
      if (this.debug) {
         System.out.println("->hasNext " + (this.currentState != 8));
      }

      return this.currentState != 8;
   }

   public void close() throws XMLStreamException {
   }

   ReaderEventInfo.Namespaces getNamespaces() {
      return this.namespaces == null ? this.currentEvent.getReaderEventInfo().getNamespaces() : this.namespaces;
   }

   boolean hasDTD() {
      return this.dtdNamespaceURI != null;
   }

   public void setDtdNamespaceURI(String uri) {
      this.dtdNamespaceURI = uri;
   }

   public String getDtdNamespaceURI() {
      return this.dtdNamespaceURI;
   }

   public String getNamespaceURI() {
      if (this.debug) {
         System.out.println("->getNamespaceURI: usingDTD() =" + this.hasDTD());
      }

      if (this.hasDTD() && this.currentEvent.getReaderEventInfo().getNamespaceCount() == 0) {
         return this.getDtdNamespaceURI();
      } else {
         String prefix = this.currentEvent.getReaderEventInfo().getPrefix();
         return this.getNamespaceURI(prefix);
      }
   }

   public String getNamespaceURI(String prefix) {
      if (this.debug) {
         System.out.println("->getNamespaceURI(" + prefix + ") " + this.getNamespaces().getNamespaceURI(prefix));
      }

      String uri = this.currentEvent.getReaderEventInfo().getNamespaces().getNamespaceURI(prefix);
      if (uri == null) {
         uri = this.getNamespaces().getNamespaceURI(prefix);
         if (uri == null || uri.trim().length() == 0) {
            uri = this.getNamespaces().getNamespaceURI();
         }
      }

      return uri;
   }

   public NamespaceContext getNamespaceContext() {
      if (this.debug) {
         System.out.println("->getNamespaceContext(): " + this.currentEvent.getReaderEventInfo().getNamespaceContext());
      }

      return this.currentEvent.getReaderEventInfo().getNamespaceContext();
   }

   public boolean isStartElement() {
      if (this.debug) {
         System.out.println("->isStartElement " + (this.currentState == 1));
      }

      return this.currentState == 1;
   }

   public boolean isEndElement() {
      if (this.debug) {
         System.out.println("->isEndElement: " + (this.currentState == 2));
      }

      return this.currentState == 2;
   }

   public boolean isCharacters() {
      if (this.debug) {
         System.out.println("->isCharacters");
      }

      return this.currentState == 4;
   }

   private static boolean isSpace(char c) {
      return c == ' ' || c == '\t' || c == '\n' || c == '\r';
   }

   public boolean isWhiteSpace() {
      if (this.currentState == 4) {
         char[] c = (char[])this.currentEvent.getReaderEventInfo().getCharacters();

         for(int i = 0; i < c.length; ++i) {
            if (!isSpace(c[i])) {
               if (this.debug) {
                  System.out.println("->isWhiteSpace: false");
               }

               return false;
            }
         }

         if (this.debug) {
            System.out.println("->isWhiteSpace: true");
         }

         return true;
      } else {
         throw new IllegalStateException("isWhiteSpace on type " + Utils.type2Str(this.currentState, this));
      }
   }

   public String getAttributeValue(String a, String b) {
      String s = this.currentEvent.getReaderEventInfo().getAttributeValue(a, b);
      if (this.debug) {
         System.out.println("->getAttributeValue(" + a + ", " + b + ") returns: " + s);
      }

      return s;
   }

   public int getAttributeCount() {
      int c = this.currentEvent.getReaderEventInfo().getAttributeCount();
      if (this.debug) {
         System.out.println("->getAttributeCount() returns " + c);
      }

      return c;
   }

   public QName getAttributeName(int index) {
      QName q = this.currentEvent.getReaderEventInfo().getAttributeName(index);
      if (this.debug) {
         System.out.println("->getAttributeName(" + index + ") returns: " + q);
      }

      return q;
   }

   public String getAttributePrefix(int index) {
      String s = this.currentEvent.getReaderEventInfo().getAttributePrefix(index);
      if (this.debug) {
         System.out.println("->getAttributePrefix(" + index + ") return " + s);
      }

      return s;
   }

   public String getAttributeNamespace(int index) {
      String s = this.currentEvent.getReaderEventInfo().getAttributeNamespace(index);
      if (this.debug) {
         System.out.println("->getAttributeNamespace(" + index + ") returns " + s);
      }

      return s;
   }

   public String getAttributeLocalName(int index) {
      String s = this.currentEvent.getReaderEventInfo().getAttributeLocalName(index);
      if (this.debug) {
         System.out.println("->getAttributeLocalName(" + index + ") returns " + s);
      }

      return s;
   }

   public String getAttributeType(int a) {
      if (this.debug) {
         System.out.println("->getAttributeType returns CDATA");
      }

      return "CDATA";
   }

   public String getAttributeValue(int index) {
      String s = this.currentEvent.getReaderEventInfo().getAttributeValue(index);
      if (this.debug) {
         System.out.println("->getAttributeValue(" + index + ") returns: " + s);
      }

      return s;
   }

   public boolean isAttributeSpecified(int index) {
      boolean b = this.currentEvent.getReaderEventInfo().isAttributeSpecified(index);
      if (this.debug) {
         System.out.println("->isAttributeSpecified(" + index + ") returns " + b);
      }

      return b;
   }

   public int getNamespaceCount() {
      int c = this.currentEvent.getReaderEventInfo().getNamespaceCount();
      if (this.debug) {
         System.out.println("->getNamespaceCount return " + c);
      }

      return c;
   }

   public String getNamespacePrefix(int index) {
      String s = this.currentEvent.getReaderEventInfo().getNamespacePrefix(index);
      if (this.debug) {
         System.out.println("->getNamespacePrefix(" + index + ") return " + s);
      }

      return s;
   }

   public String getNamespaceURI(int a) {
      if (this.debug) {
         System.out.println("->getNamespaceURI(int) " + a);
      }

      return this.currentEvent.getReaderEventInfo().getNamespaceURI(a);
   }

   public int getEventType() {
      int c = this.currentState;
      if (this.debug) {
         System.out.println("->getEventType: " + Utils.type2Str(c, this));
      }

      return c;
   }

   public String getText() {
      if (this.debug) {
         System.out.println("->getText");
      }

      return new String(this.getTextCharacters());
   }

   public int getTextCharacters(int a, char[] b, int c, int d) throws XMLStreamException {
      throw new UnsupportedOperationException();
   }

   public int getTextStart() {
      if (this.debug) {
         System.out.println("->getTextStart");
      }

      return 0;
   }

   public int getTextLength() {
      return this.getTextCharacters().length;
   }

   public String getEncoding() {
      if (this.debug) {
         System.out.println("->getEncoding");
      }

      return this.currentEvent.getReaderEventInfo().getEncoding();
   }

   public boolean hasText() {
      if (this.debug) {
         System.out.println("->hasText");
      }

      switch (this.currentState) {
         case 4:
         case 5:
         case 6:
         case 9:
         case 11:
            return true;
         case 7:
         case 8:
         case 10:
         default:
            return false;
      }
   }

   public Location getLocation() {
      Location l = this.currentEvent.getReaderEventInfo().getLocation();
      if (this.debug) {
         System.out.println("->getLocation: " + l);
      }

      return l;
   }

   public QName getName() {
      if (this.debug) {
         System.out.println("->getName");
      }

      ReaderEventInfo info = this.currentEvent.getReaderEventInfo();
      return info.getNamespacePrefix(0) == null ? new QName(info.getNamespaceURI(0), info.getElementName()) : new QName(info.getNamespaceURI(0), info.getElementName(), info.getNamespacePrefix(0));
   }

   public boolean hasName() {
      if (this.debug) {
         System.out.println("->hasName");
      }

      return this.currentState == 1 || this.currentState == 2;
   }

   public String getPrefix() {
      if (this.debug) {
         System.out.println("->getPrefix " + this.currentEvent.getReaderEventInfo().getNamespacePrefix(0));
      }

      return this.currentEvent.getReaderEventInfo().getPrefix();
   }

   public String getVersion() {
      if (this.debug) {
         System.out.println("->getVersion");
      }

      return null;
   }

   public boolean isStandalone() {
      if (this.debug) {
         System.out.println("->isStandalone");
      }

      return false;
   }

   public boolean standaloneSet() {
      if (this.debug) {
         System.out.println("->standaloneSet");
      }

      return false;
   }

   public String getCharacterEncodingScheme() {
      if (this.debug) {
         System.out.println("->getCharacterEncodingScheme");
      }

      return this.currentEvent.getReaderEventInfo().getCharacterEncodingScheme();
   }

   public String getPITarget() {
      if (this.debug) {
         System.out.println("->getPITarget");
      }

      throw new IllegalStateException();
   }

   public String getPIData() {
      if (this.debug) {
         System.out.println("->getPIData");
      }

      throw new IllegalStateException();
   }

   public Object getProperty(String a) {
      if (this.debug) {
         System.out.println("->getProperty");
      }

      throw new UnsupportedOperationException("UNIMPLEMENTED");
   }

   public static void main(String[] args) throws Exception {
      InputStream in = null;
      String path = null;
      if (args.length == 0) {
         usage();
      } else {
         File f = new File(args[0]);
         in = new FileInputStream(f);
         path = args[0];
      }

      System.out.println("stamp out munger...");
      System.out.flush();
      BasicMunger2 reader = new BasicMunger2(in, path);
      System.out.println("hand munger to descriptor manger...");
      System.out.flush();
      (new DescriptorManager()).createDescriptor(reader).toXML(System.out);
      System.out.print("\n dump the bean from the playback reader:\n");
      System.out.flush();
      (new DescriptorManager()).createDescriptor(new PlaybackReader(reader.root, reader.getAbsolutePath())).toXML(System.out);
   }

   private static void usage() {
      System.err.println("usage: java weblogic.application.descriptor.PlaybackReader <descriptor file name>");
      System.exit(0);
   }

   private class MyLocation implements Location {
      Location l;

      MyLocation(Location l) {
         this.l = l;
      }

      public int getLineNumber() {
         return this.l.getLineNumber();
      }

      public int getColumnNumber() {
         return this.l.getColumnNumber();
      }

      public int getCharacterOffset() {
         return this.l.getCharacterOffset();
      }

      public String getPublicId() {
         return PlaybackReader.this.getAbsolutePath() + ":" + this.l.getLineNumber() + ":" + this.l.getColumnNumber();
      }

      public String getSystemId() {
         return this.l.getSystemId();
      }
   }

   class PlaybackIterator {
      int index = 0;
      int state = -1;
      ReaderEvent2 event;
      PlaybackIterator parent;

      PlaybackIterator(ReaderEvent2 event) {
         this.event = event;
      }

      PlaybackIterator(ReaderEvent2 event, PlaybackIterator parent) {
         this.event = event;
         this.parent = parent;
      }

      int next() throws XMLStreamException {
         int next = this.state();
         if (next == -2 || next == 7) {
            if (this.index < this.event.getChildren().size()) {
               PlaybackReader.this.iterator = PlaybackReader.this.new PlaybackIterator((ReaderEvent2)this.event.getChildren().elementAt(this.index), this);
               ++this.index;
               return PlaybackReader.this.iterator.next();
            }

            if (this.index == this.event.getChildren().size()) {
               if (this.parent == null) {
                  throw new XMLStreamException("unexpected end of xml stream");
               }

               PlaybackReader.this.iterator = this.parent;
               return PlaybackReader.this.iterator.next();
            }
         }

         return next;
      }

      ReaderEvent2 event() {
         return this.event;
      }

      int state() throws XMLStreamException {
         switch (this.state) {
            case -1:
               this.state = this.event.getReaderEventInfo().getEventType();
               if (this.state == 1) {
                  if (this.event.getReaderEventInfo().getNamespaceCount() > 0) {
                     if (PlaybackReader.this.namespaces == null) {
                        PlaybackReader.this.namespaces = this.event.getReaderEventInfo().getNamespaces();
                     } else {
                        ReaderEventInfo.Namespaces newContext = this.event.getReaderEventInfo().getNamespaces();

                        for(int ix = 0; ix < newContext.getNamespaceCount(); ++ix) {
                           PlaybackReader.this.namespaces.setNamespaceURI(newContext.getNamespacePrefix(ix), newContext.getNamespaceURI(ix));
                        }
                     }

                     if (PlaybackReader.this.debug) {
                        for(int i = 0; i < PlaybackReader.this.namespaces.getNamespaceCount(); ++i) {
                           System.out.println("===== namespace prefix = " + PlaybackReader.this.namespaces.getNamespacePrefix(i));
                           System.out.println("===== namespaceURI = " + PlaybackReader.this.namespaces.getNamespaceURI(i));
                        }
                     }
                  }

                  if (this.event.getReaderEventInfo().getAttributeCount() > 0) {
                     PlaybackReader.this.attributes = this.event.getReaderEventInfo().getAttributes();
                  }
               }
            case 0:
            case 3:
            default:
               break;
            case 1:
               if (this.event.getReaderEventInfo().getCharacters() != null) {
                  this.state = 4;
               } else {
                  if (this.index < this.event.getChildren().size()) {
                     PlaybackReader.this.iterator = PlaybackReader.this.new PlaybackIterator((ReaderEvent2)this.event.getChildren().elementAt(this.index), this);
                     ++this.index;
                     return PlaybackReader.this.iterator.next();
                  }

                  this.state = 2;
               }
               break;
            case 2:
               this.state = -2;
               break;
            case 4:
               if (this.index < this.event.getChildren().size()) {
                  PlaybackReader.this.iterator = PlaybackReader.this.new PlaybackIterator((ReaderEvent2)this.event.getChildren().elementAt(this.index), this);
                  ++this.index;
                  return PlaybackReader.this.iterator.next();
               }

               this.state = 2;
         }

         return this.state;
      }
   }
}

package org.apache.openjpa.lib.meta;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.xml.Commentable;
import org.apache.openjpa.lib.xml.XMLWriter;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.AttributesImpl;

public abstract class XMLMetaDataSerializer implements MetaDataSerializer {
   private static final Localizer _loc = Localizer.forPackage(XMLMetaDataSerializer.class);
   private static final SAXTransformerFactory _factory = (SAXTransformerFactory)TransformerFactory.newInstance();
   private Log _log = null;
   private final AttributesImpl _attrs = new AttributesImpl();
   private ContentHandler _handler = null;
   private int _flags = 0;
   private File _backup = null;

   public Log getLog() {
      return this._log;
   }

   public void setLog(Log log) {
      this._log = log;
   }

   public void serialize(int flags) throws IOException {
      this.serialize((Map)null, flags);
   }

   public void serialize(Map output, int flags) throws IOException {
      Map files = this.getFileMap();
      if (files != null) {
         Iterator itr = files.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            File file = (File)entry.getKey();
            Collection fileObjs = (Collection)entry.getValue();
            if (this._log != null && this._log.isInfoEnabled()) {
               this._log.info(_loc.get("ser-file", (Object)file));
            }

            try {
               TransformerHandler trans = _factory.newTransformerHandler();
               Object writer;
               if (output == null) {
                  this._backup = this.prepareWrite(file);
                  writer = new FileWriter(file);
               } else {
                  writer = new StringWriter();
               }

               Writer xml = writer;
               if ((flags & 1) > 0) {
                  xml = new XMLWriter((Writer)writer);
               }

               trans.setResult(new StreamResult((Writer)xml));
               this.serialize(fileObjs, trans, flags);
               if (output != null) {
                  output.put(file, ((StringWriter)writer).toString());
               }
            } catch (SAXException var11) {
               throw new IOException(var11.toString());
            } catch (TransformerConfigurationException var12) {
               throw new IOException(var12.toString());
            }
         }

      }
   }

   protected File prepareWrite(File file) throws IOException {
      File backup = Files.backup(file, false);
      if (backup == null) {
         File parent = file.getParentFile();
         if (parent != null && !(Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(parent))) {
            AccessController.doPrivileged(J2DoPrivHelper.mkdirsAction(parent));
         }
      }

      return backup;
   }

   protected Map getFileMap() {
      Collection objs = this.getObjects();
      if (objs != null && !objs.isEmpty()) {
         Map files = new HashMap();
         Iterator itr = objs.iterator();

         while(itr.hasNext()) {
            Object obj = itr.next();
            File file = this.getSourceFile(obj);
            if (file == null) {
               if (this._log != null && this._log.isTraceEnabled()) {
                  this._log.trace(_loc.get("no-file", obj));
               }
            } else {
               Collection fileObjs = (Collection)files.get(file);
               if (fileObjs == null) {
                  fileObjs = new LinkedList();
                  files.put(file, fileObjs);
               }

               ((Collection)fileObjs).add(obj);
            }
         }

         return files;
      } else {
         return null;
      }
   }

   protected File getSourceFile(Object obj) {
      return obj instanceof SourceTracker ? ((SourceTracker)obj).getSourceFile() : null;
   }

   public void serialize(File file, int flags) throws IOException {
      if (this._log != null) {
         this._log.info(_loc.get("ser-file", (Object)file));
      }

      this._backup = this.prepareWrite(file);

      try {
         FileWriter out = new FileWriter((String)AccessController.doPrivileged(J2DoPrivHelper.getCanonicalPathAction(file)), (flags & 2) > 0);
         this.serialize((Writer)out, flags);
         out.close();
      } catch (PrivilegedActionException var4) {
         throw (IOException)var4.getException();
      }
   }

   public void serialize(Writer out, int flags) throws IOException {
      try {
         if ((flags & 1) > 0) {
            this.serialize((Result)(new StreamResult(new XMLWriter(out))), flags);
         } else {
            this.serialize((Result)(new StreamResult(out)), flags);
         }

      } catch (SAXException var4) {
         throw new IOException(var4.toString());
      }
   }

   public void serialize(Result result, int flags) throws SAXException {
      try {
         TransformerHandler trans = _factory.newTransformerHandler();
         trans.setResult(result);
         this.serialize((ContentHandler)trans, flags);
      } catch (TransformerConfigurationException var4) {
         throw new SAXException(var4);
      }
   }

   public void serialize(ContentHandler handler, int flags) throws SAXException {
      this.serialize(this.getObjects(), handler, flags);
   }

   private void serialize(Collection objs, ContentHandler handler, int flags) throws SAXException {
      if (this._log != null && this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("ser-objs", (Object)objs));
      }

      this._handler = handler;
      this._flags = flags;

      try {
         if (!objs.isEmpty()) {
            handler.startDocument();
            this.serialize(objs);
            handler.endDocument();
         }
      } finally {
         this.reset();
      }

   }

   protected boolean isVerbose() {
      return (this._flags & 4) > 0;
   }

   protected File currentBackupFile() {
      return this._backup;
   }

   protected void startElement(String name) throws SAXException {
      this._handler.startElement("", name, name, this._attrs);
      this._attrs.clear();
   }

   protected void endElement(String name) throws SAXException {
      this._handler.endElement("", name, name);
   }

   protected void addText(String text) throws SAXException {
      this._handler.characters(text.toCharArray(), 0, text.length());
   }

   protected void addAttribute(String name, String value) {
      this._attrs.addAttribute("", name, name, "CDATA", value);
   }

   protected Attributes getAttributes() {
      return this._attrs;
   }

   protected void addComments(String[] comments) throws SAXException {
      if (comments != null && comments.length != 0 && this._handler instanceof LexicalHandler) {
         LexicalHandler lh = (LexicalHandler)this._handler;

         for(int i = 0; i < comments.length; ++i) {
            char[] chars = comments[i].toCharArray();
            lh.comment(chars, 0, chars.length);
         }

      }
   }

   protected void addComments(Object obj) throws SAXException {
      if (obj instanceof Commentable) {
         this.addComments(((Commentable)obj).getComments());
      }

   }

   protected void reset() {
      this._attrs.clear();
      this._handler = null;
      this._flags = 0;
      this._backup = null;
   }

   protected abstract void serialize(Collection var1) throws SAXException;

   protected abstract Collection getObjects();
}

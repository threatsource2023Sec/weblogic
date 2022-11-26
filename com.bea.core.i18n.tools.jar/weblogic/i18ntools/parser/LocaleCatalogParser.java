package weblogic.i18ntools.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.i18n.tools.Action;
import weblogic.i18n.tools.Cause;
import weblogic.i18n.tools.DuplicateElementException;
import weblogic.i18n.tools.MessageBody;
import weblogic.i18n.tools.MessageDetail;
import weblogic.i18n.tools.WrongTypeException;
import weblogic.i18ntools.internal.I18nConfig;
import weblogic.utils.XXEUtils;
import weblogic.xml.babel.reader.XmlReader;

public final class LocaleCatalogParser extends HandlerBase {
   private I18nConfig config;
   private static final String elem_locale_message_catalog = "locale_message_catalog";
   private static final String elem_locale_message = "message";
   private static final String elem_locale_logmessage = "logmessage";
   private static final String elem_messagebody = "messagebody";
   private static final String elem_messagedetail = "messagedetail";
   private static final String elem_cause = "cause";
   private static final String elem_action = "action";
   private LocaleMessageCatalog msgcat;
   private LocaleMessage msg;
   private LocaleLogMessage logmsg;
   private MessageBody body;
   private MessageDetail detail;
   private Cause cause;
   private Action action;
   private Parser parser;
   private String catname;
   private Locale lcl;
   private static L10nParserTextFormatter fmt = new L10nParserTextFormatter();
   private boolean validating = false;
   private boolean build = false;

   public void setDocumentLocator(Locator l) {
      this.config.setLocator(l);
   }

   public static L10nParserTextFormatter getFmt() {
      return fmt;
   }

   public Locale getLocale() {
      return this.lcl;
   }

   public LocaleCatalogParser(Locale l) {
      this.config = new I18nConfig();
      this.lcl = l;
   }

   public LocaleCatalogParser(Locale l, I18nConfig cfg) {
      this.config = cfg;
      this.lcl = l;
   }

   public LocaleCatalogParser(Locale l, I18nConfig cfg, boolean build) {
      this.config = cfg;
      this.lcl = l;
      this.build = build;
   }

   public I18nConfig getConfig() {
      return this.config;
   }

   public void error(SAXException s) {
      if (s.getException() != null) {
         s.getException().printStackTrace();
      } else {
         s.printStackTrace();
      }

   }

   public void fatalError(SAXException s) {
      this.error(s);
   }

   public void warning(SAXException s) {
      this.error(s);
   }

   public LocaleMessageCatalog parse(String catfile) throws SAXParseException, IOException, Exception {
      try {
         SAXParserFactory pf = XXEUtils.createSAXParserFactoryInstance();
         I18nConfig var10001 = this.config;
         pf.setValidating(true);
         this.parser = pf.newSAXParser().getParser();
         this.parser.setDocumentHandler(this);
         this.parser.setErrorHandler(this);
         LocaleEntityResolver msger = new LocaleEntityResolver();
         this.parser.setEntityResolver(msger);
         this.config.inform(fmt.parsingCatalog(catfile));
         this.catname = catfile;
         FileInputStream in = new FileInputStream(catfile);
         XmlReader reader = (XmlReader)XmlReader.createReader(in);
         this.parser.parse(new InputSource(reader));
         this.msgcat.setInputEncoding(reader.getEncoding());
         this.msgcat.setOutputEncoding(reader.getEncoding());
         this.config.debug("encoding used: " + this.msgcat.getInputEncoding());
      } catch (SAXException var6) {
         Throwable t = var6.getException();
         if (!this.validating) {
            this.config.linecol(fmt);
         }

         if (this.config.isDebug()) {
            if (t == null) {
               var6.printStackTrace();
            } else {
               t.printStackTrace();
            }
         }

         this.msgcat = null;
         throw var6;
      } catch (IOException var7) {
         if (!this.validating) {
            this.config.linecol(fmt);
         }

         this.msgcat = null;
         throw var7;
      } catch (Exception var8) {
         if (!this.validating) {
            this.config.linecol(fmt);
         }

         if (this.config.isDebug()) {
            var8.printStackTrace();
         }

         this.msgcat = null;
         throw var8;
      }

      this.msgcat.setLocale(this.getLocale());
      return this.msgcat;
   }

   public void startDocument() throws SAXException {
      this.config.debug(fmt.docStart());
   }

   public void endDocument() throws SAXException {
      this.validating = true;
      String errMess = this.msgcat.validate(this.build);
      if (errMess != null) {
         throw new SAXException(fmt.invalidCatalog(errMess, this.catname));
      }
   }

   public void startElement(String name, AttributeList attrs) throws SAXException {
      this.config.debug(fmt.receiveElement(name));
      Locator l = this.config.getLocator();
      String id;
      String nm;
      if (name.equals("locale_message_catalog")) {
         if (this.msgcat != null) {
            this.config.linecol(fmt);
            throw new SAXException(fmt.invalidDocument(this.catname, fmt.multipleElements("locale_message_catalog")));
         } else {
            this.config.debug(fmt.addCatalog());
            this.msgcat = new LocaleMessageCatalog(this.config);
            this.msgcat.setLocator(l);
            File f = new File(this.catname);
            id = f.getName();
            this.config.debug(id);
            this.msgcat.set("filename", id);
            int len = attrs.getLength();

            for(int i = 0; i < len; ++i) {
               String nm = attrs.getName(i);
               nm = attrs.getValue(i);
               this.config.debug(fmt.setAttr(nm, nm));
               this.msgcat.set(nm, nm);
            }

         }
      } else {
         int len;
         String val;
         String val;
         if (name.equals("message")) {
            if (this.msgcat == null) {
               this.config.linecol(fmt);
               throw new SAXException(fmt.invalidDocument(this.catname, fmt.unexpectedElement("message")));
            } else {
               this.config.debug("Adding new LocaleMessage");
               this.msg = new LocaleMessage(this.config);
               this.msg.setLocator(l);
               len = attrs.getLength();

               for(int i = 0; i < len; ++i) {
                  val = attrs.getName(i);
                  val = attrs.getValue(i);
                  this.config.debug(fmt.setAttr(val, val));
                  this.msg.set(val, val);
               }

            }
         } else if (name.equals("logmessage")) {
            if (this.msgcat == null) {
               this.config.linecol(fmt);
               throw new SAXException(fmt.invalidDocument(this.catname, fmt.unexpectedElement("logmessage")));
            } else {
               this.config.debug(fmt.addLogMessage());
               this.logmsg = new LocaleLogMessage(this.config);
               this.logmsg.setLocator(l);
               len = attrs.getLength();
               id = null;
               val = null;

               for(int i = 0; i < len; ++i) {
                  nm = attrs.getName(i);
                  val = attrs.getValue(i);
                  this.config.debug(fmt.setAttr(nm, val));
                  this.logmsg.set(nm, val);
               }

            }
         } else if (!name.equals("messagebody")) {
            if (name.equals("messagedetail")) {
               if (this.msgcat != null && this.logmsg != null) {
                  this.config.debug(fmt.addDetail());
                  this.detail = new MessageDetail();
                  this.detail.setLocator(l);
               } else {
                  this.config.linecol(fmt);
                  throw new SAXException(fmt.invalidDocument(this.catname, fmt.unexpectedElement("messagedetail")));
               }
            } else if (name.equals("cause")) {
               if (this.msgcat != null && this.logmsg != null) {
                  this.config.debug(fmt.addCause());
                  this.cause = new Cause();
                  this.cause.setLocator(l);
               } else {
                  this.config.linecol(fmt);
                  throw new SAXException(fmt.invalidDocument(this.catname, fmt.unexpectedElement("cause")));
               }
            } else if (name.equals("action")) {
               if (this.msgcat != null && this.logmsg != null) {
                  this.config.debug(fmt.addAction());
                  this.action = new Action();
                  this.action.setLocator(l);
               } else {
                  this.config.linecol(fmt);
                  throw new SAXException(fmt.invalidDocument(this.catname, fmt.unexpectedElement("action")));
               }
            } else {
               throw new SAXException("Unrecognized element: " + name);
            }
         } else if (this.msgcat != null && (this.logmsg != null || this.msg != null)) {
            this.config.debug(fmt.addBody());
            this.body = new MessageBody();
            this.body.setLocator(l);
         } else {
            this.config.linecol(fmt);
            throw new SAXException(fmt.invalidDocument(this.catname, fmt.unexpectedElement("messagebody")));
         }
      }
   }

   public void endElement(String name) throws SAXException {
      if (name.equals("logmessage")) {
         try {
            if (!this.logmsg.isRetired()) {
               this.config.debug(fmt.linkLogMessage(this.logmsg.getMessageId()));
               if (!this.msgcat.addLogMessage(this.logmsg)) {
                  this.config.linecol(fmt);
                  throw new SAXException(fmt.dupMessage(this.logmsg.getMessageId(), this.catname));
               }

               this.logmsg.setParent(this.msgcat);
            }
         } catch (WrongTypeException var3) {
            this.config.linecol(fmt);
            throw new SAXException(fmt.wrongExceptionCaught(this.catname));
         } catch (DuplicateElementException var4) {
            this.config.linecol(fmt);
            throw new SAXException(fmt.dupMessage(this.logmsg.getMessageId(), this.catname));
         }

         this.logmsg = null;
      } else if (name.equals("messagebody")) {
         if (this.logmsg != null) {
            this.config.debug(fmt.linkLogBody(this.logmsg.getMessageId()));
            this.logmsg.addMessageBody(this.body);
            this.body.setParent(this.logmsg);
         } else if (this.msg != null) {
            this.config.debug(fmt.linkMsgBody(this.msg.getMessageId()));
            this.msg.addMessageBody(this.body);
            this.body.setParent(this.msg);
         }

         this.body.normalize();
         this.body = null;
      } else if (name.equals("messagedetail")) {
         this.config.debug(fmt.linkDetail(this.logmsg.getMessageId()));
         this.logmsg.addMessageDetail(this.detail);
         this.detail.setParent(this.logmsg);
         this.detail.normalize();
         this.detail = null;
      } else if (name.equals("cause")) {
         this.config.debug(fmt.linkCause(this.logmsg.getMessageId()));
         this.logmsg.addCause(this.cause);
         this.cause.setParent(this.logmsg);
         this.cause.normalize();
         this.cause = null;
      } else if (name.equals("action")) {
         this.config.debug(fmt.linkAction(this.logmsg.getMessageId()));
         this.logmsg.addAction(this.action);
         this.action.setParent(this.logmsg);
         this.action.normalize();
         this.action = null;
      } else if (!name.equals("locale_message_catalog")) {
         if (!name.equals("message")) {
            this.config.linecol(fmt);
            throw new SAXException(fmt.unrecognizedElement(name));
         } else {
            try {
               if (!this.msg.isRetired()) {
                  this.config.debug(fmt.linkMessage(this.msg.getMessageId()));
                  if (!this.msgcat.addMessage(this.msg)) {
                     this.config.linecol(fmt);
                     throw new SAXException(fmt.dupMessage(this.msg.getMessageId(), this.catname));
                  }

                  this.msg.setParent(this.msgcat);
               }
            } catch (WrongTypeException var5) {
               this.config.linecol(fmt);
               throw new SAXException(fmt.wrongExceptionCaught(this.catname));
            } catch (DuplicateElementException var6) {
               this.config.linecol(fmt);
               throw new SAXException(fmt.dupMessage(this.msg.getMessageId(), this.catname));
            }

            this.msg = null;
         }
      }
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      String instr = new String(ch, start, length);
      this.config.debug(fmt.receivedChars(instr));
      if (this.body != null) {
         this.config.debug(fmt.addToBody());
         this.body.setCdata(instr);
      } else if (this.detail != null) {
         this.config.debug(fmt.addToDetail());
         this.detail.setCdata(instr);
      } else if (this.cause != null) {
         this.config.debug(fmt.addToCause());
         this.cause.setCdata(instr);
      } else {
         if (this.action == null) {
            this.config.linecol(fmt);
            throw new SAXException(fmt.unexpectedChars(instr));
         }

         this.config.debug(fmt.addToAction());
         this.action.setCdata(instr);
      }

   }
}

package weblogic.i18n.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.utils.XXEUtils;

public final class MessageCatalogParser extends HandlerBase {
   private static final boolean debug = false;
   private Config config;
   private static final String elem_message_catalog = "message_catalog";
   private static final String elem_message = "message";
   private static final String elem_logmessage = "logmessage";
   private static final String elem_exception = "exception";
   private static final String elem_messagebody = "messagebody";
   private static final String elem_messagedetail = "messagedetail";
   private static final String elem_cause = "cause";
   private static final String elem_action = "action";
   Parser parser;
   private MessageCatalog msgcat;
   private Message msg;
   private LogMessage logmsg;
   private MessageBody body;
   private MessageDetail detail;
   private Cause cause;
   private Action action;
   private String comment;
   private String catname;
   private boolean ignoreDups;
   private boolean verbose;
   private boolean validating;

   public void setDocumentLocator(Locator l) {
      this.config.setLocator(l);
   }

   public MessageCatalogParser(boolean vrbose) {
      this((Config)null, vrbose, false);
   }

   public MessageCatalogParser(Config cfg, boolean vrbose) {
      this(cfg, vrbose, false);
   }

   public MessageCatalogParser(Config cfg, boolean vrbose, boolean ignoreD) {
      this.comment = null;
      this.validating = false;
      this.ignoreDups = ignoreD;
      this.verbose = vrbose;
      if (cfg == null) {
         this.config = new Config();
      } else {
         this.config = cfg;
      }

      this.config.setVerbose(this.verbose);
   }

   public void setNoWarn(boolean nowarn) {
      this.config.setNoWarn(nowarn);
   }

   public Config getConfig() {
      return this.config;
   }

   public MessageCatalog parse(String catfile) throws SAXException, IOException, ParserConfigurationException {
      Exception t;
      try {
         Config var10000 = this.config;
         this.config.debug("Using validating parser");
         SAXParserFactory pf = XXEUtils.createSAXParserFactoryInstance();
         Config var10001 = this.config;
         pf.setValidating(true);
         this.parser = pf.newSAXParser().getParser();
         this.parser.setDocumentHandler(this);
         this.parser.setErrorHandler(this);
         MsgCatEntityResolver msger = new MsgCatEntityResolver();
         this.parser.setEntityResolver(msger);
         this.config.inform("Parsing catalog, " + catfile);
         this.catname = catfile;
         FileInputStream in = new FileInputStream(catfile);
         InputSource is = new InputSource(in);
         this.parser.parse(is);
         this.msgcat.setInputEncoding(is.getEncoding());
         this.msgcat.setOutputEncoding(is.getEncoding());
         this.config.debug("encoding used: " + this.msgcat.getInputEncoding());
         this.config.debug("Successfully parsed: " + this.msgcat.get("filename"));
         this.msgcat.setFullName(catfile);
      } catch (SAXException var6) {
         t = var6.getException();
         if (!this.validating) {
            this.config.linecol();
         }

         if (this.config.isDebug()) {
            if (t == null) {
               System.err.println(var6.toString());
            } else {
               t.printStackTrace();
            }
         }

         this.msgcat = null;
         throw var6;
      } catch (IOException var7) {
         this.config.inform(var7.toString());
         this.msgcat = null;
         throw var7;
      } catch (ParserConfigurationException var8) {
         this.config.inform(var8.toString());
         if (this.config.isDebug()) {
            var8.printStackTrace();
         }

         this.msgcat = null;
         throw var8;
      } catch (FactoryConfigurationError var9) {
         this.config.inform(var9.toString());
         if (this.config.isDebug()) {
            var9.printStackTrace();
            t = var9.getException();
            t.printStackTrace();
         }

         this.msgcat = null;
         throw var9;
      } catch (Error var10) {
         var10.printStackTrace();
      }

      return this.msgcat;
   }

   public void startDocument() throws SAXException {
      this.config.debug("Document start");
   }

   public void endDocument() throws SAXException {
      this.validating = true;
      String errMess = this.msgcat.validate();
      if (errMess != null) {
         throw new SAXException("Invalid message catalog: " + errMess);
      }
   }

   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
      for(int tryit = start; tryit < start + 10; ++tryit) {
         String temp = null;
         if (tryit + 3 < ch.length && ch[tryit] == '<') {
            if (ch[tryit + 1] == '!' && ch[tryit + 2] == '-' && ch[tryit + 3] == '-') {
               int startComment = 4;
               if (tryit + startComment < ch.length) {
                  while(Character.isWhitespace(ch[tryit + startComment])) {
                     ++startComment;
                     if (tryit + startComment >= ch.length) {
                        break;
                     }
                  }
               }

               int point;
               for(point = startComment; tryit + point + 2 < ch.length && (ch[tryit + point] != '-' || ch[tryit + point + 1] != '-' || ch[tryit + point + 2] != '>'); ++point) {
               }

               if (tryit + point + 2 <= ch.length) {
                  String newComment = String.valueOf(ch, tryit + startComment, point - startComment);
                  if (this.comment == null) {
                     this.comment = newComment;
                  } else {
                     this.comment = this.comment + "  " + newComment;
                  }
               }
            }
            break;
         }
      }

   }

   public void startElement(String name, AttributeList attrs) throws SAXException {
      this.config.debug("Receive element: " + name);
      String id;
      String nm;
      int i;
      String nm;
      if (name.equals("message_catalog")) {
         if (this.msgcat != null) {
            this.config.linecol();
            throw new SAXException("Invalid document: multiple message_catalog elements");
         } else {
            this.config.debug("Adding new message_catalog");
            this.msgcat = new MessageCatalog(this.config);
            this.msgcat.setLocator(this.config.getLocator());
            File f = new File(this.catname);
            id = f.getName();
            this.config.debug(id);
            this.msgcat.set("filename", id);
            int len = attrs.getLength();

            for(i = 0; i < len; ++i) {
               nm = attrs.getName(i);
               nm = attrs.getValue(i);
               this.config.debug("Setting attr: name=" + nm + ", value=" + nm);

               try {
                  this.msgcat.set(nm, nm);
               } catch (NoSuchElementException var10) {
                  throw new SAXException("Unrecognized attribute: " + var10.getMessage());
               } catch (Exception var11) {
                  throw new SAXException("Errors detected setting " + nm + " attribute on message_catalog element", var11);
               }
            }

            if (this.comment != null) {
               this.msgcat.setComment(this.comment);
               this.comment = null;
            }

         }
      } else {
         int len;
         String val;
         if (name.equals("message")) {
            if (this.msgcat == null) {
               throw new SAXException("Invalid document: unexpected message element");
            } else {
               this.config.debug("Adding new Message");
               this.msg = new Message(this.config);
               this.msg.setLocator(this.config.getLocator());
               len = attrs.getLength();
               id = null;

               for(i = 0; i < len; ++i) {
                  nm = attrs.getName(i);
                  val = attrs.getValue(i);
                  if (nm.equals("messageid")) {
                     ;
                  }

                  this.config.debug("Setting attr: name=" + nm + ", value=" + val);

                  try {
                     this.msg.set(nm, val);
                  } catch (NoSuchElementException var12) {
                     throw new SAXException("Unrecognized attribute: " + var12.getMessage());
                  } catch (Exception var13) {
                     throw new SAXException("Errors detected setting " + nm + " attribute on message element", var13);
                  }
               }

            }
         } else if (name.equals("logmessage")) {
            if (this.msgcat == null) {
               throw new SAXException("Invalid document: unexpected log message element");
            } else {
               this.config.debug("Adding new logmessage");
               this.logmsg = new LogMessage(this.config);
               this.logmsg.setLocator(this.config.getLocator());
               len = attrs.getLength();
               id = null;
               String method = null;

               for(int i = 0; i < len; ++i) {
                  nm = attrs.getName(i);
                  val = attrs.getValue(i);
                  if (nm.equals("messageid")) {
                     ;
                  }

                  if (nm.equals("method")) {
                     method = val;
                  }

                  this.config.debug("Setting attr: name=" + nm + ", value=" + val);

                  try {
                     this.logmsg.set(nm, val);
                  } catch (NoSuchElementException var15) {
                     throw new SAXException("Unrecognized attribute: " + var15.getMessage());
                  } catch (Exception var16) {
                     var16.printStackTrace();
                     throw new SAXException("Errors detected setting " + nm + " attribute on logmessage element", var16);
                  }
               }

               if (method != null) {
                  try {
                     this.logmsg.setMethod(method);
                  } catch (Exception var14) {
                     throw new SAXException("Errors detected setting method attribute on logmessage element", var14);
                  }
               }

            }
         } else if (name.equals("exception")) {
            if (this.msgcat == null) {
               throw new SAXException("Invalid document: unexpected exception element");
            } else {
               throw new SAXException("Unsupported catalog type: exception messages");
            }
         } else if (!name.equals("messagebody")) {
            if (name.equals("messagedetail")) {
               if (this.msgcat != null && this.logmsg != null) {
                  this.config.debug("Adding new messagedetail");
                  this.detail = new MessageDetail();
                  this.detail.setLocator(this.config.getLocator());
               } else {
                  throw new SAXException("Invalid document: unexpected message detail element");
               }
            } else if (name.equals("cause")) {
               if (this.msgcat != null && this.logmsg != null) {
                  this.config.debug("Adding new cause");
                  this.cause = new Cause();
                  this.cause.setLocator(this.config.getLocator());
               } else {
                  throw new SAXException("Invalid document: unexpected cause element");
               }
            } else if (name.equals("action")) {
               if (this.msgcat != null && this.logmsg != null) {
                  this.config.debug("Adding new action");
                  this.action = new Action();
                  this.action.setLocator(this.config.getLocator());
               } else {
                  throw new SAXException("Invalid document: unexpected action element");
               }
            } else {
               throw new SAXException("Unrecognized element: " + name);
            }
         } else if (this.msgcat != null && (this.logmsg != null || this.msg != null)) {
            this.config.debug("Adding new messagebody");
            this.body = new MessageBody();
            this.body.setLocator(this.config.getLocator());
         } else {
            throw new SAXException("Invalid document: unexpected message body element");
         }
      }
   }

   public void endElement(String name) throws SAXException {
      if (name.equals("logmessage")) {
         this.config.debug("linking logmessage " + this.logmsg.getMessageId() + " to message_catalog");
         if (this.comment != null) {
            this.logmsg.setComment(this.comment);
            this.comment = null;
         }

         try {
            this.msgcat.addLogMessage(this.logmsg);
            this.logmsg.setParent(this.msgcat);
         } catch (WrongTypeException var3) {
            throw new SAXException("Unexpected Element: " + name, var3);
         } catch (DuplicateElementException var4) {
            if (!this.ignoreDups) {
               throw new SAXException("Duplicate message, id = " + this.logmsg.getMessageId() + " method = " + this.logmsg.getMethod() + ": " + var4);
            }

            this.config.linecol();
            this.config.inform("Ignoring duplicate message: " + var4);
         }

         this.logmsg = null;
      } else if (name.equals("messagebody")) {
         if (this.logmsg != null) {
            this.config.debug("linking messagebody to logmessage " + this.logmsg.getMessageId());
            this.logmsg.addMessageBody(this.body);
            this.body.setParent(this.logmsg);
         } else if (this.msg != null) {
            this.config.debug("linking messagebody to message " + this.msg.getMessageId());
            this.msg.addMessageBody(this.body);
            this.body.setParent(this.msg);
         }

         this.body.normalize();
         this.body = null;
      } else if (name.equals("messagedetail")) {
         this.config.debug("linking messagedetail to logmessage " + this.logmsg.getMessageId());
         this.logmsg.addMessageDetail(this.detail);
         this.detail.setParent(this.logmsg);
         this.detail.normalize();
         this.detail = null;
      } else if (name.equals("cause")) {
         this.config.debug("linking cause to logmessage " + this.logmsg.getMessageId());
         this.logmsg.addCause(this.cause);
         this.cause.setParent(this.logmsg);
         this.cause.normalize();
         this.cause = null;
      } else if (name.equals("action")) {
         this.config.debug("linking action to logmessage " + this.logmsg.getMessageId());
         this.logmsg.addAction(this.action);
         this.action.setParent(this.logmsg);
         this.action.normalize();
         this.action = null;
      } else if (!name.equals("exception")) {
         if (!name.equals("message_catalog")) {
            if (!name.equals("message")) {
               throw new SAXException("Unrecognized element: " + name);
            } else {
               this.config.debug("linking message " + this.msg.getMessageId() + " to message_catalog");
               if (this.comment != null) {
                  this.msg.setComment(this.comment);
                  this.comment = null;
               }

               try {
                  this.msgcat.addMessage(this.msg);
                  this.msg.setParent(this.msgcat);
               } catch (WrongTypeException var5) {
                  throw new SAXException("Unexpected element: " + name, var5);
               } catch (DuplicateElementException var6) {
                  if (!this.ignoreDups) {
                     throw new SAXException("Duplicate message, id = " + this.msg.getMessageId() + ": " + var6);
                  }

                  this.config.linecol();
                  this.config.inform("Ignoring duplicate message: " + var6);
               }

               this.msg = null;
            }
         }
      }
   }

   private String removeLeadingWS(String s) {
      int len = s.length();

      int i;
      for(i = 0; i < len && Character.isWhitespace(s.charAt(i)); ++i) {
      }

      if (i == len) {
         this.config.debug("Ignoring all white space string");
         return "";
      } else {
         this.config.debug("edited string: " + s.substring(i, len));
         return s.substring(i, len);
      }
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      String instr = new String(ch, start, length);
      this.config.debug("Received chars: <" + instr + ">");
      if (this.body != null) {
         this.config.debug("adding to body");
         this.body.setCdata(instr);
      } else if (this.detail != null) {
         this.config.debug("adding to detail");
         this.detail.setCdata(instr);
      } else if (this.cause != null) {
         this.config.debug("adding to cause");
         this.cause.setCdata(instr);
      } else {
         if (this.action == null) {
            throw new SAXException("characters detected outside expected elements: " + instr);
         }

         this.config.debug("adding to action");
         this.action.setCdata(instr);
      }

   }

   public void processingInstruction(String target, String data) {
      System.err.println("processingInstruction target = " + target + "    data:" + data);
   }

   public void warning(SAXParseException e) throws SAXException {
   }

   public void error(SAXParseException e) throws SAXException {
   }

   public void fatalError(SAXParseException e) throws SAXException {
   }
}

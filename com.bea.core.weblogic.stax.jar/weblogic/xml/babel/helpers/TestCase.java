package weblogic.xml.babel.helpers;

import java.io.IOException;
import java.net.URL;
import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

class TestCase implements ErrorHandler {
   public final String id;
   public final String inputURI;
   public final String sections;
   public String description;
   public final String type;
   public final String nvOutputURI;
   public final String valOutputURI;
   public final String entities;
   public boolean skipped;
   public String diagnostic;
   private Exception exception;
   public int diagnosticLevel;
   public String outputDiagnostic;
   public boolean pass;

   public TestCase(Attributes attrs, URL baseURI) throws IOException {
      this.id = attrs.getValue("ID");
      this.inputURI = (new URL(baseURI, attrs.getValue("URI"))).toString();
      this.type = attrs.getValue("TYPE");
      this.sections = attrs.getValue("SECTIONS");
      this.description = null;
      String tmp;
      if ((tmp = attrs.getValue("OUTPUT")) != null) {
         this.nvOutputURI = (new URL(baseURI, tmp)).toString();
      } else {
         this.nvOutputURI = null;
      }

      if ((tmp = attrs.getValue("OUTPUT3")) != null) {
         this.valOutputURI = (new URL(baseURI, tmp)).toString();
      } else {
         this.valOutputURI = null;
      }

      this.entities = attrs.getValue("ENTITIES");
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("{XML Test");
      buf.append(", id=");
      buf.append(this.id);
      buf.append(", type=");
      buf.append(this.type);
      buf.append(", input=");
      buf.append(this.inputURI);
      buf.append("}");
      return buf.toString();
   }

   public void run(XMLReader parser, boolean isValidating) {
      try {
         InputSource in = new InputSource(this.inputURI);
         Outputter handler = null;
         if (!"valid".equals(this.type) && !"invalid".equals(this.type)) {
            this.pass = true;
         } else {
            this.pass = false;
         }

         this.diagnosticLevel = -1;
         this.exception = null;
         parser.setErrorHandler(this);
         if ("valid".equals(this.type)) {
            if (isValidating && this.valOutputURI != null) {
               handler = new Outputter(true, this.valOutputURI);
            } else if (this.nvOutputURI != null) {
               handler = new Outputter(false, this.nvOutputURI);
            }
         }

         if (handler != null) {
            parser.setContentHandler(handler);
            parser.setDTDHandler(handler);
         } else {
            DefaultHandler nopHandler = new DefaultHandler();
            parser.setContentHandler(nopHandler);
            parser.setDTDHandler(nopHandler);
         }

         parser.parse(in);
         if ("error".equals(this.type)) {
            this.pass = true;
         } else if ("not-wf".equals(this.type)) {
            this.pass = false;
            if (this.diagnostic == null) {
               this.diagnostic = "[Document not WF; no error reported]";
            }
         } else if (!"valid".equals(this.type) && (!"invalid".equals(this.type) || isValidating)) {
            if ("invalid".equals(this.type) && isValidating) {
               this.pass = this.diagnosticLevel == 0;
               if (!this.pass && this.diagnostic == null) {
                  this.diagnostic = "[Document invalid; no error reported]";
               }
            } else {
               this.diagnostic = "[ ?? ]";
            }
         } else if (this.diagnosticLevel == -1) {
            this.pass = true;
         } else {
            this.pass = false;
            if (this.diagnostic == null) {
               this.diagnostic = "[no diagnostic]";
            }
         }

         if (handler != null) {
            this.outputDiagnostic = handler.getDiagnostic(false);
         }
      } catch (SAXParseException var6) {
         if (this.exception != null && this.diagnosticLevel != -1) {
            if (var6 != this.exception) {
               this.diagnostic = "(odd SAXParseException) " + this.diagnostic;
            } else if (this.diagnostic == null) {
               this.diagnostic = "(odd SAXParseException) [null]";
            }
         } else {
            this.diagnostic = "(thrown SAXParseException) ";
            if (var6.getMessage() != null) {
               this.diagnostic = this.diagnostic + var6.getMessage();
            } else {
               this.diagnostic = this.diagnostic + "[no exception message]";
            }

            this.diagnosticLevel = 2;
         }
      } catch (SAXException var7) {
         if (this.exception != null && this.diagnosticLevel != -1) {
            this.diagnostic = "(odd SAXException) " + this.diagnostic;
         } else {
            this.diagnostic = "(thrown SAXException) ";
            if (var7.getMessage() != null) {
               this.diagnostic = this.diagnostic + var7.getMessage();
            } else {
               this.diagnostic = this.diagnostic + "[no exception message]";
            }

            this.diagnosticLevel = 2;
         }
      } catch (IOException var8) {
         if (this.exception != null && this.diagnosticLevel != -1) {
            this.diagnostic = "(odd IOException) " + this.diagnostic;
         } else {
            this.diagnostic = "(thrown IOException) ";
            if (var8.getMessage() != null) {
               this.diagnostic = this.diagnostic + var8.getMessage();
            } else {
               this.diagnostic = this.diagnostic + "[no exception message]";
            }

            this.diagnosticLevel = 2;
         }
      } catch (Exception var9) {
         String name = var9.getClass().getName();
         if (this.exception != null && this.diagnosticLevel != -1) {
            this.diagnostic = "(odd " + name + ") " + this.diagnostic;
         } else {
            this.diagnostic = "(thrown " + name + ") ";
            if (var9.getMessage() != null) {
               this.diagnostic = this.diagnostic + var9.getMessage();
            } else {
               this.diagnostic = this.diagnostic + "[no exception message]";
            }

            this.diagnosticLevel = 2;
         }
      }

      this.exception = null;
   }

   public void warning(SAXParseException x) throws SAXException {
      if (this.exception == null) {
         this.diagnostic = "(warning) ";
         if (x.getMessage() != null) {
            this.diagnostic = this.diagnostic + x.getMessage();
         } else {
            this.diagnostic = this.diagnostic + "[no exception message]";
         }

         this.diagnosticLevel = -1;
         this.exception = x;
      }
   }

   public void error(SAXParseException x) throws SAXException {
      if (this.exception == null || this.diagnosticLevel == -1) {
         this.diagnostic = "(error) ";
         if (x.getMessage() != null) {
            this.diagnostic = this.diagnostic + x.getMessage();
         } else {
            this.diagnostic = this.diagnostic + "[no exception message]";
         }

         this.diagnosticLevel = 0;
         this.exception = x;
      }
   }

   public void fatalError(SAXParseException x) throws SAXException {
      this.diagnostic = "(fatal) ";
      if (x.getMessage() != null) {
         this.diagnostic = this.diagnostic + x.getMessage();
      } else {
         this.diagnostic = this.diagnostic + "[no exception message]";
      }

      this.diagnosticLevel = 1;
      this.exception = x;
      throw x;
   }
}

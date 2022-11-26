package weblogic.xml.babel.adapters;

import java.io.IOException;
import org.xml.sax.SAXException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.CharDataElement;
import weblogic.xml.babel.baseparser.CommentElement;
import weblogic.xml.babel.baseparser.Element;
import weblogic.xml.babel.baseparser.EndElement;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.baseparser.PrefixMapping;
import weblogic.xml.babel.baseparser.ProcessingInstruction;
import weblogic.xml.babel.baseparser.Space;
import weblogic.xml.babel.baseparser.StartElement;
import weblogic.xml.babel.scanner.ScannerException;

public abstract class BaseFactory implements ElementFactory {
   protected BaseParser baseparser;

   public void setBaseParser(BaseParser baseparser) {
      this.baseparser = baseparser;
   }

   public abstract Object create(CharDataElement var1);

   public abstract Object create(StartElement var1);

   public abstract Object create(Space var1);

   public abstract Object create(CommentElement var1);

   public abstract Object create(EndElement var1);

   public abstract Object create(String var1, String var2);

   public abstract Object create(String var1);

   public abstract Object create(PrefixMapping var1);

   public abstract Object create(ProcessingInstruction var1);

   public abstract Throwable create(ParseException var1);

   public abstract Throwable create(ScannerException var1);

   public abstract Throwable create(SAXException var1);

   public abstract Throwable create(IOException var1);

   public Object dispatch(Element element) {
      switch (element.type) {
         case 0:
            return this.create((StartElement)element);
         case 1:
         case 2:
         default:
            return null;
         case 3:
            return this.create((EndElement)element);
         case 4:
            return this.create((ProcessingInstruction)element);
         case 5:
            return this.create((CharDataElement)element);
         case 6:
            return this.create((CommentElement)element);
         case 7:
            return this.create((Space)element);
      }
   }
}

package org.apache.xmlbeans.impl.validator;

import java.util.Collection;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import org.apache.xmlbeans.SchemaField;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.common.PrefixResolver;
import org.apache.xmlbeans.impl.common.ValidatorListener;
import org.apache.xmlbeans.impl.common.XmlWhitespace;

public class ValidatorUtil {
   public static boolean validateSimpleType(SchemaType type, String value, Collection errors, PrefixResolver prefixResolver) {
      if (!type.isSimpleType() && type.getContentType() != 2) {
         assert false;

         throw new RuntimeException("Not a simple type");
      } else {
         Validator validator = new Validator(type, (SchemaField)null, type.getTypeSystem(), (XmlOptions)null, errors);
         EventImpl ev = new EventImpl(prefixResolver, value);
         validator.nextEvent(1, ev);
         validator.nextEvent(3, ev);
         validator.nextEvent(2, ev);
         return validator.isValid();
      }
   }

   private static class EventImpl implements ValidatorListener.Event {
      PrefixResolver _prefixResolver;
      String _text;

      EventImpl(PrefixResolver prefixResolver, String text) {
         this._prefixResolver = prefixResolver;
         this._text = text;
      }

      public XmlCursor getLocationAsCursor() {
         return null;
      }

      public Location getLocation() {
         return null;
      }

      public String getXsiType() {
         return null;
      }

      public String getXsiNil() {
         return null;
      }

      public String getXsiLoc() {
         return null;
      }

      public String getXsiNoLoc() {
         return null;
      }

      public QName getName() {
         return null;
      }

      public String getText() {
         return this._text;
      }

      public String getText(int wsr) {
         return XmlWhitespace.collapse(this._text, wsr);
      }

      public boolean textIsWhitespace() {
         return false;
      }

      public String getNamespaceForPrefix(String prefix) {
         return this._prefixResolver.getNamespaceForPrefix(prefix);
      }
   }
}

package com.sun.faces.renderkit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AttributeManager {
   private static Map ATTRIBUTE_LOOKUP;

   public static String[] getAttributes(Key key) {
      return (String[])ATTRIBUTE_LOOKUP.get(key.value());
   }

   static {
      HashMap map = new HashMap();
      map.put("CommandButton", new String[]{"accesskey", "alt", "dir", "lang", "onblur", "onchange", "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onselect", "style", "tabindex", "title"});
      map.put("CommandLink", new String[]{"accesskey", "charset", "coords", "dir", "hreflang", "lang", "onblur", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "rel", "rev", "shape", "style", "tabindex", "title", "type"});
      map.put("DataTable", new String[]{"bgcolor", "border", "cellpadding", "cellspacing", "dir", "frame", "lang", "onclick", "ondblclick", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "rules", "style", "summary", "title", "width"});
      map.put("FormForm", new String[]{"accept", "dir", "enctype", "lang", "onclick", "ondblclick", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onreset", "onsubmit", "style", "target", "title"});
      map.put("GraphicImage", new String[]{"alt", "dir", "height", "lang", "longdesc", "onclick", "ondblclick", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "style", "title", "usemap", "width"});
      map.put("InputHidden", new String[0]);
      map.put("InputSecret", new String[]{"accesskey", "alt", "dir", "lang", "maxlength", "onblur", "onchange", "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onselect", "size", "style", "tabindex", "title"});
      map.put("InputText", new String[]{"accesskey", "alt", "dir", "lang", "maxlength", "onblur", "onchange", "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onselect", "size", "style", "tabindex", "title"});
      map.put("InputTextarea", new String[]{"accesskey", "cols", "dir", "lang", "onblur", "onchange", "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onselect", "rows", "style", "tabindex", "title"});
      map.put("MessageMessage", new String[]{"dir", "lang", "style", "title"});
      map.put("MessagesMessages", new String[]{"dir", "lang", "style", "title"});
      map.put("OutputFormat", new String[]{"dir", "lang", "style", "title"});
      map.put("OutputLabel", new String[]{"accesskey", "dir", "lang", "onblur", "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "style", "tabindex", "title"});
      map.put("OutputLink", new String[]{"accesskey", "charset", "coords", "dir", "hreflang", "lang", "onblur", "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "rel", "rev", "shape", "style", "tabindex", "title", "type"});
      map.put("OutputText", new String[]{"dir", "lang", "style", "title"});
      map.put("PanelGrid", new String[]{"bgcolor", "border", "cellpadding", "cellspacing", "dir", "frame", "lang", "onclick", "ondblclick", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "rules", "style", "summary", "title", "width"});
      map.put("PanelGroup", new String[]{"style"});
      map.put("SelectBooleanCheckbox", new String[]{"accesskey", "dir", "lang", "onblur", "onchange", "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onselect", "style", "tabindex", "title"});
      map.put("SelectManyCheckbox", new String[]{"accesskey", "dir", "lang", "onblur", "onchange", "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onselect", "tabindex", "title"});
      map.put("SelectManyListbox", new String[]{"accesskey", "dir", "lang", "onblur", "onchange", "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onselect", "style", "tabindex", "title"});
      map.put("SelectManyMenu", new String[]{"accesskey", "dir", "lang", "onblur", "onchange", "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onselect", "style", "tabindex", "title"});
      map.put("SelectOneListbox", new String[]{"accesskey", "dir", "lang", "onblur", "onchange", "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onselect", "style", "tabindex", "title"});
      map.put("SelectOneMenu", new String[]{"accesskey", "dir", "lang", "onblur", "onchange", "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onselect", "style", "tabindex", "title"});
      map.put("SelectOneRadio", new String[]{"accesskey", "dir", "lang", "onblur", "onchange", "onclick", "ondblclick", "onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onselect", "tabindex", "title"});
      ATTRIBUTE_LOOKUP = Collections.unmodifiableMap(map);
   }

   public static enum Key {
      COMMANDBUTTON("CommandButton"),
      COMMANDLINK("CommandLink"),
      DATATABLE("DataTable"),
      FORMFORM("FormForm"),
      GRAPHICIMAGE("GraphicImage"),
      INPUTHIDDEN("InputHidden"),
      INPUTSECRET("InputSecret"),
      INPUTTEXT("InputText"),
      INPUTTEXTAREA("InputTextarea"),
      MESSAGEMESSAGE("MessageMessage"),
      MESSAGESMESSAGES("MessagesMessages"),
      OUTPUTFORMAT("OutputFormat"),
      OUTPUTLABEL("OutputLabel"),
      OUTPUTLINK("OutputLink"),
      OUTPUTTEXT("OutputText"),
      PANELGRID("PanelGrid"),
      PANELGROUP("PanelGroup"),
      SELECTBOOLEANCHECKBOX("SelectBooleanCheckbox"),
      SELECTMANYCHECKBOX("SelectManyCheckbox"),
      SELECTMANYLISTBOX("SelectManyListbox"),
      SELECTMANYMENU("SelectManyMenu"),
      SELECTONELISTBOX("SelectOneListbox"),
      SELECTONEMENU("SelectOneMenu"),
      SELECTONERADIO("SelectOneRadio");

      private String key;

      private Key(String key) {
         this.key = key;
      }

      public String value() {
         return this.key;
      }
   }
}

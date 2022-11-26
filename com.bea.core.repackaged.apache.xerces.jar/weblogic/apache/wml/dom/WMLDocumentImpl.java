package weblogic.apache.wml.dom;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import weblogic.apache.wml.WMLDocument;
import weblogic.apache.xerces.dom.DocumentImpl;
import weblogic.apache.xerces.dom.ElementImpl;

public class WMLDocumentImpl extends DocumentImpl implements WMLDocument {
   private static final long serialVersionUID = -6582904849512384104L;
   private static Hashtable _elementTypesWML;
   private static final Class[] _elemClassSigWML;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLDocumentImpl;
   // $FF: synthetic field
   static Class class$java$lang$String;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLBElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLNoopElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLAElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLSetvarElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLAccessElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLStrongElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLPostfieldElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLDoElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLWmlElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLTrElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLGoElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLBigElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLAnchorElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLTimerElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLSmallElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLOptgroupElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLHeadElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLTdElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLFieldsetElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLImgElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLRefreshElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLOneventElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLInputElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLPrevElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLTableElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLMetaElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLTemplateElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLBrElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLOptionElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLUElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLPElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLSelectElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLEmElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLIElementImpl;
   // $FF: synthetic field
   static Class class$weblogic$apache$wml$dom$WMLCardElementImpl;

   public Element createElement(String var1) throws DOMException {
      Class var2 = (Class)_elementTypesWML.get(var1);
      if (var2 != null) {
         try {
            Constructor var3 = var2.getConstructor(_elemClassSigWML);
            return (Element)var3.newInstance(this, var1);
         } catch (Exception var6) {
            Object var5;
            if (var6 instanceof InvocationTargetException) {
               var5 = ((InvocationTargetException)var6).getTargetException();
            } else {
               var5 = var6;
            }

            System.out.println("Exception " + var5.getClass().getName());
            System.out.println(((Throwable)var5).getMessage());
            throw new IllegalStateException("Tag '" + var1 + "' associated with an Element class that failed to construct.");
         }
      } else {
         return new WMLElementImpl(this, var1);
      }
   }

   protected boolean canRenameElements(String var1, String var2, ElementImpl var3) {
      return _elementTypesWML.get(var2) == _elementTypesWML.get(var3.getTagName());
   }

   public WMLDocumentImpl(DocumentType var1) {
      super(var1, false);
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static {
      _elemClassSigWML = new Class[]{class$weblogic$apache$wml$dom$WMLDocumentImpl == null ? (class$weblogic$apache$wml$dom$WMLDocumentImpl = class$("weblogic.apache.wml.dom.WMLDocumentImpl")) : class$weblogic$apache$wml$dom$WMLDocumentImpl, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String};
      _elementTypesWML = new Hashtable();
      _elementTypesWML.put("b", class$weblogic$apache$wml$dom$WMLBElementImpl == null ? (class$weblogic$apache$wml$dom$WMLBElementImpl = class$("weblogic.apache.wml.dom.WMLBElementImpl")) : class$weblogic$apache$wml$dom$WMLBElementImpl);
      _elementTypesWML.put("noop", class$weblogic$apache$wml$dom$WMLNoopElementImpl == null ? (class$weblogic$apache$wml$dom$WMLNoopElementImpl = class$("weblogic.apache.wml.dom.WMLNoopElementImpl")) : class$weblogic$apache$wml$dom$WMLNoopElementImpl);
      _elementTypesWML.put("a", class$weblogic$apache$wml$dom$WMLAElementImpl == null ? (class$weblogic$apache$wml$dom$WMLAElementImpl = class$("weblogic.apache.wml.dom.WMLAElementImpl")) : class$weblogic$apache$wml$dom$WMLAElementImpl);
      _elementTypesWML.put("setvar", class$weblogic$apache$wml$dom$WMLSetvarElementImpl == null ? (class$weblogic$apache$wml$dom$WMLSetvarElementImpl = class$("weblogic.apache.wml.dom.WMLSetvarElementImpl")) : class$weblogic$apache$wml$dom$WMLSetvarElementImpl);
      _elementTypesWML.put("access", class$weblogic$apache$wml$dom$WMLAccessElementImpl == null ? (class$weblogic$apache$wml$dom$WMLAccessElementImpl = class$("weblogic.apache.wml.dom.WMLAccessElementImpl")) : class$weblogic$apache$wml$dom$WMLAccessElementImpl);
      _elementTypesWML.put("strong", class$weblogic$apache$wml$dom$WMLStrongElementImpl == null ? (class$weblogic$apache$wml$dom$WMLStrongElementImpl = class$("weblogic.apache.wml.dom.WMLStrongElementImpl")) : class$weblogic$apache$wml$dom$WMLStrongElementImpl);
      _elementTypesWML.put("postfield", class$weblogic$apache$wml$dom$WMLPostfieldElementImpl == null ? (class$weblogic$apache$wml$dom$WMLPostfieldElementImpl = class$("weblogic.apache.wml.dom.WMLPostfieldElementImpl")) : class$weblogic$apache$wml$dom$WMLPostfieldElementImpl);
      _elementTypesWML.put("do", class$weblogic$apache$wml$dom$WMLDoElementImpl == null ? (class$weblogic$apache$wml$dom$WMLDoElementImpl = class$("weblogic.apache.wml.dom.WMLDoElementImpl")) : class$weblogic$apache$wml$dom$WMLDoElementImpl);
      _elementTypesWML.put("wml", class$weblogic$apache$wml$dom$WMLWmlElementImpl == null ? (class$weblogic$apache$wml$dom$WMLWmlElementImpl = class$("weblogic.apache.wml.dom.WMLWmlElementImpl")) : class$weblogic$apache$wml$dom$WMLWmlElementImpl);
      _elementTypesWML.put("tr", class$weblogic$apache$wml$dom$WMLTrElementImpl == null ? (class$weblogic$apache$wml$dom$WMLTrElementImpl = class$("weblogic.apache.wml.dom.WMLTrElementImpl")) : class$weblogic$apache$wml$dom$WMLTrElementImpl);
      _elementTypesWML.put("go", class$weblogic$apache$wml$dom$WMLGoElementImpl == null ? (class$weblogic$apache$wml$dom$WMLGoElementImpl = class$("weblogic.apache.wml.dom.WMLGoElementImpl")) : class$weblogic$apache$wml$dom$WMLGoElementImpl);
      _elementTypesWML.put("big", class$weblogic$apache$wml$dom$WMLBigElementImpl == null ? (class$weblogic$apache$wml$dom$WMLBigElementImpl = class$("weblogic.apache.wml.dom.WMLBigElementImpl")) : class$weblogic$apache$wml$dom$WMLBigElementImpl);
      _elementTypesWML.put("anchor", class$weblogic$apache$wml$dom$WMLAnchorElementImpl == null ? (class$weblogic$apache$wml$dom$WMLAnchorElementImpl = class$("weblogic.apache.wml.dom.WMLAnchorElementImpl")) : class$weblogic$apache$wml$dom$WMLAnchorElementImpl);
      _elementTypesWML.put("timer", class$weblogic$apache$wml$dom$WMLTimerElementImpl == null ? (class$weblogic$apache$wml$dom$WMLTimerElementImpl = class$("weblogic.apache.wml.dom.WMLTimerElementImpl")) : class$weblogic$apache$wml$dom$WMLTimerElementImpl);
      _elementTypesWML.put("small", class$weblogic$apache$wml$dom$WMLSmallElementImpl == null ? (class$weblogic$apache$wml$dom$WMLSmallElementImpl = class$("weblogic.apache.wml.dom.WMLSmallElementImpl")) : class$weblogic$apache$wml$dom$WMLSmallElementImpl);
      _elementTypesWML.put("optgroup", class$weblogic$apache$wml$dom$WMLOptgroupElementImpl == null ? (class$weblogic$apache$wml$dom$WMLOptgroupElementImpl = class$("weblogic.apache.wml.dom.WMLOptgroupElementImpl")) : class$weblogic$apache$wml$dom$WMLOptgroupElementImpl);
      _elementTypesWML.put("head", class$weblogic$apache$wml$dom$WMLHeadElementImpl == null ? (class$weblogic$apache$wml$dom$WMLHeadElementImpl = class$("weblogic.apache.wml.dom.WMLHeadElementImpl")) : class$weblogic$apache$wml$dom$WMLHeadElementImpl);
      _elementTypesWML.put("td", class$weblogic$apache$wml$dom$WMLTdElementImpl == null ? (class$weblogic$apache$wml$dom$WMLTdElementImpl = class$("weblogic.apache.wml.dom.WMLTdElementImpl")) : class$weblogic$apache$wml$dom$WMLTdElementImpl);
      _elementTypesWML.put("fieldset", class$weblogic$apache$wml$dom$WMLFieldsetElementImpl == null ? (class$weblogic$apache$wml$dom$WMLFieldsetElementImpl = class$("weblogic.apache.wml.dom.WMLFieldsetElementImpl")) : class$weblogic$apache$wml$dom$WMLFieldsetElementImpl);
      _elementTypesWML.put("img", class$weblogic$apache$wml$dom$WMLImgElementImpl == null ? (class$weblogic$apache$wml$dom$WMLImgElementImpl = class$("weblogic.apache.wml.dom.WMLImgElementImpl")) : class$weblogic$apache$wml$dom$WMLImgElementImpl);
      _elementTypesWML.put("refresh", class$weblogic$apache$wml$dom$WMLRefreshElementImpl == null ? (class$weblogic$apache$wml$dom$WMLRefreshElementImpl = class$("weblogic.apache.wml.dom.WMLRefreshElementImpl")) : class$weblogic$apache$wml$dom$WMLRefreshElementImpl);
      _elementTypesWML.put("onevent", class$weblogic$apache$wml$dom$WMLOneventElementImpl == null ? (class$weblogic$apache$wml$dom$WMLOneventElementImpl = class$("weblogic.apache.wml.dom.WMLOneventElementImpl")) : class$weblogic$apache$wml$dom$WMLOneventElementImpl);
      _elementTypesWML.put("input", class$weblogic$apache$wml$dom$WMLInputElementImpl == null ? (class$weblogic$apache$wml$dom$WMLInputElementImpl = class$("weblogic.apache.wml.dom.WMLInputElementImpl")) : class$weblogic$apache$wml$dom$WMLInputElementImpl);
      _elementTypesWML.put("prev", class$weblogic$apache$wml$dom$WMLPrevElementImpl == null ? (class$weblogic$apache$wml$dom$WMLPrevElementImpl = class$("weblogic.apache.wml.dom.WMLPrevElementImpl")) : class$weblogic$apache$wml$dom$WMLPrevElementImpl);
      _elementTypesWML.put("table", class$weblogic$apache$wml$dom$WMLTableElementImpl == null ? (class$weblogic$apache$wml$dom$WMLTableElementImpl = class$("weblogic.apache.wml.dom.WMLTableElementImpl")) : class$weblogic$apache$wml$dom$WMLTableElementImpl);
      _elementTypesWML.put("meta", class$weblogic$apache$wml$dom$WMLMetaElementImpl == null ? (class$weblogic$apache$wml$dom$WMLMetaElementImpl = class$("weblogic.apache.wml.dom.WMLMetaElementImpl")) : class$weblogic$apache$wml$dom$WMLMetaElementImpl);
      _elementTypesWML.put("template", class$weblogic$apache$wml$dom$WMLTemplateElementImpl == null ? (class$weblogic$apache$wml$dom$WMLTemplateElementImpl = class$("weblogic.apache.wml.dom.WMLTemplateElementImpl")) : class$weblogic$apache$wml$dom$WMLTemplateElementImpl);
      _elementTypesWML.put("br", class$weblogic$apache$wml$dom$WMLBrElementImpl == null ? (class$weblogic$apache$wml$dom$WMLBrElementImpl = class$("weblogic.apache.wml.dom.WMLBrElementImpl")) : class$weblogic$apache$wml$dom$WMLBrElementImpl);
      _elementTypesWML.put("option", class$weblogic$apache$wml$dom$WMLOptionElementImpl == null ? (class$weblogic$apache$wml$dom$WMLOptionElementImpl = class$("weblogic.apache.wml.dom.WMLOptionElementImpl")) : class$weblogic$apache$wml$dom$WMLOptionElementImpl);
      _elementTypesWML.put("u", class$weblogic$apache$wml$dom$WMLUElementImpl == null ? (class$weblogic$apache$wml$dom$WMLUElementImpl = class$("weblogic.apache.wml.dom.WMLUElementImpl")) : class$weblogic$apache$wml$dom$WMLUElementImpl);
      _elementTypesWML.put("p", class$weblogic$apache$wml$dom$WMLPElementImpl == null ? (class$weblogic$apache$wml$dom$WMLPElementImpl = class$("weblogic.apache.wml.dom.WMLPElementImpl")) : class$weblogic$apache$wml$dom$WMLPElementImpl);
      _elementTypesWML.put("select", class$weblogic$apache$wml$dom$WMLSelectElementImpl == null ? (class$weblogic$apache$wml$dom$WMLSelectElementImpl = class$("weblogic.apache.wml.dom.WMLSelectElementImpl")) : class$weblogic$apache$wml$dom$WMLSelectElementImpl);
      _elementTypesWML.put("em", class$weblogic$apache$wml$dom$WMLEmElementImpl == null ? (class$weblogic$apache$wml$dom$WMLEmElementImpl = class$("weblogic.apache.wml.dom.WMLEmElementImpl")) : class$weblogic$apache$wml$dom$WMLEmElementImpl);
      _elementTypesWML.put("i", class$weblogic$apache$wml$dom$WMLIElementImpl == null ? (class$weblogic$apache$wml$dom$WMLIElementImpl = class$("weblogic.apache.wml.dom.WMLIElementImpl")) : class$weblogic$apache$wml$dom$WMLIElementImpl);
      _elementTypesWML.put("card", class$weblogic$apache$wml$dom$WMLCardElementImpl == null ? (class$weblogic$apache$wml$dom$WMLCardElementImpl = class$("weblogic.apache.wml.dom.WMLCardElementImpl")) : class$weblogic$apache$wml$dom$WMLCardElementImpl);
   }
}

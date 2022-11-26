package weblogic.apache.xerces.parsers;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import weblogic.apache.xerces.impl.XMLEntityManager;
import weblogic.apache.xerces.impl.XMLErrorReporter;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.grammars.Grammar;
import weblogic.apache.xerces.xni.grammars.XMLGrammarLoader;
import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;
import weblogic.apache.xerces.xni.parser.XMLEntityResolver;
import weblogic.apache.xerces.xni.parser.XMLErrorHandler;
import weblogic.apache.xerces.xni.parser.XMLInputSource;

public class XMLGrammarPreparser {
   private static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
   protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
   protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
   protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
   protected static final String GRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
   private static final Hashtable KNOWN_LOADERS = new Hashtable();
   private static final String[] RECOGNIZED_PROPERTIES;
   protected final SymbolTable fSymbolTable;
   protected final XMLErrorReporter fErrorReporter;
   protected XMLEntityResolver fEntityResolver;
   protected XMLGrammarPool fGrammarPool;
   protected Locale fLocale;
   private final Hashtable fLoaders;
   private int fModCount;

   public XMLGrammarPreparser() {
      this(new SymbolTable());
   }

   public XMLGrammarPreparser(SymbolTable var1) {
      this.fModCount = 1;
      this.fSymbolTable = var1;
      this.fLoaders = new Hashtable();
      this.fErrorReporter = new XMLErrorReporter();
      this.setLocale(Locale.getDefault());
      this.fEntityResolver = new XMLEntityManager();
   }

   public boolean registerPreparser(String var1, XMLGrammarLoader var2) {
      if (var2 == null) {
         if (KNOWN_LOADERS.containsKey(var1)) {
            String var3 = (String)KNOWN_LOADERS.get(var1);

            try {
               ClassLoader var4 = ObjectFactory.findClassLoader();
               XMLGrammarLoader var5 = (XMLGrammarLoader)ObjectFactory.newInstance(var3, var4, true);
               this.fLoaders.put(var1, new XMLGrammarLoaderContainer(var5));
               return true;
            } catch (Exception var6) {
               return false;
            }
         } else {
            return false;
         }
      } else {
         this.fLoaders.put(var1, new XMLGrammarLoaderContainer(var2));
         return true;
      }
   }

   public Grammar preparseGrammar(String var1, XMLInputSource var2) throws XNIException, IOException {
      if (this.fLoaders.containsKey(var1)) {
         XMLGrammarLoaderContainer var3 = (XMLGrammarLoaderContainer)this.fLoaders.get(var1);
         XMLGrammarLoader var4 = var3.loader;
         if (var3.modCount != this.fModCount) {
            var4.setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
            var4.setProperty("http://apache.org/xml/properties/internal/entity-resolver", this.fEntityResolver);
            var4.setProperty("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
            if (this.fGrammarPool != null) {
               try {
                  var4.setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarPool);
               } catch (Exception var6) {
               }
            }

            var3.modCount = this.fModCount;
         }

         return var4.loadGrammar(var2);
      } else {
         return null;
      }
   }

   public void setLocale(Locale var1) {
      this.fLocale = var1;
      this.fErrorReporter.setLocale(var1);
   }

   public Locale getLocale() {
      return this.fLocale;
   }

   public void setErrorHandler(XMLErrorHandler var1) {
      this.fErrorReporter.setProperty("http://apache.org/xml/properties/internal/error-handler", var1);
   }

   public XMLErrorHandler getErrorHandler() {
      return this.fErrorReporter.getErrorHandler();
   }

   public void setEntityResolver(XMLEntityResolver var1) {
      if (this.fEntityResolver != var1) {
         if (++this.fModCount < 0) {
            this.clearModCounts();
         }

         this.fEntityResolver = var1;
      }

   }

   public XMLEntityResolver getEntityResolver() {
      return this.fEntityResolver;
   }

   public void setGrammarPool(XMLGrammarPool var1) {
      if (this.fGrammarPool != var1) {
         if (++this.fModCount < 0) {
            this.clearModCounts();
         }

         this.fGrammarPool = var1;
      }

   }

   public XMLGrammarPool getGrammarPool() {
      return this.fGrammarPool;
   }

   public XMLGrammarLoader getLoader(String var1) {
      XMLGrammarLoaderContainer var2 = (XMLGrammarLoaderContainer)this.fLoaders.get(var1);
      return var2 != null ? var2.loader : null;
   }

   public void setFeature(String var1, boolean var2) {
      Enumeration var3 = this.fLoaders.elements();

      while(var3.hasMoreElements()) {
         XMLGrammarLoader var4 = ((XMLGrammarLoaderContainer)var3.nextElement()).loader;

         try {
            var4.setFeature(var1, var2);
         } catch (Exception var6) {
         }
      }

      if (var1.equals("http://apache.org/xml/features/continue-after-fatal-error")) {
         this.fErrorReporter.setFeature("http://apache.org/xml/features/continue-after-fatal-error", var2);
      }

   }

   public void setProperty(String var1, Object var2) {
      Enumeration var3 = this.fLoaders.elements();

      while(var3.hasMoreElements()) {
         XMLGrammarLoader var4 = ((XMLGrammarLoaderContainer)var3.nextElement()).loader;

         try {
            var4.setProperty(var1, var2);
         } catch (Exception var6) {
         }
      }

   }

   public boolean getFeature(String var1, String var2) {
      XMLGrammarLoader var3 = ((XMLGrammarLoaderContainer)this.fLoaders.get(var1)).loader;
      return var3.getFeature(var2);
   }

   public Object getProperty(String var1, String var2) {
      XMLGrammarLoader var3 = ((XMLGrammarLoaderContainer)this.fLoaders.get(var1)).loader;
      return var3.getProperty(var2);
   }

   private void clearModCounts() {
      XMLGrammarLoaderContainer var2;
      for(Enumeration var1 = this.fLoaders.elements(); var1.hasMoreElements(); var2.modCount = 0) {
         var2 = (XMLGrammarLoaderContainer)var1.nextElement();
      }

      this.fModCount = 1;
   }

   static {
      KNOWN_LOADERS.put("http://www.w3.org/2001/XMLSchema", "weblogic.apache.xerces.impl.xs.XMLSchemaLoader");
      KNOWN_LOADERS.put("http://www.w3.org/TR/REC-xml", "weblogic.apache.xerces.impl.dtd.XMLDTDLoader");
      RECOGNIZED_PROPERTIES = new String[]{"http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/error-handler", "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/grammar-pool"};
   }

   static class XMLGrammarLoaderContainer {
      public final XMLGrammarLoader loader;
      public int modCount = 0;

      public XMLGrammarLoaderContainer(XMLGrammarLoader var1) {
         this.loader = var1;
      }
   }
}

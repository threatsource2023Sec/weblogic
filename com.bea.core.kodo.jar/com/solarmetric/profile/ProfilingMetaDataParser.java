package com.solarmetric.profile;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.CFMetaDataParser;
import org.apache.openjpa.lib.util.Localizer;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ProfilingMetaDataParser extends CFMetaDataParser {
   public static final String DOCTYPE_DEC = "<!DOCTYPE profile SYSTEM 'file:/com/solarmetric/profile/profiling.dtd'>";
   private static final Localizer _loc = Localizer.forPackage(ProfilingMetaDataParser.class);
   private static final ProfilingEntityResolver _resolver = new ProfilingEntityResolver();
   private ProfilingClassMetaData _meta = null;
   private ProfilingMethodMetaData _method = null;
   private ProfilingFormattableMetaData _formattable = null;

   public ProfilingMetaDataParser(Log log) {
      this.setLog(log);
      this.setParseText(false);
      this.setSuffix(".profile");
   }

   protected void reset() {
      super.reset();
      this._meta = null;
      this._method = null;
   }

   protected Reader getDocType() throws IOException {
      return new StringReader("<!DOCTYPE profile SYSTEM 'file:/com/solarmetric/profile/profiling.dtd'>");
   }

   protected boolean startClass(String elem, Attributes attrs) throws SAXException {
      super.startClass(elem, attrs);
      Class type = this.classForName(this.currentClassName(), false);
      if (type == null) {
         throw this.getException(_loc.get("no-class", this.currentClassName()));
      } else {
         this._meta = new ProfilingClassMetaData(type);
         String agentSource = attrs.getValue("agent-source");
         ProfilingClassMetaData var10001;
         if ("field".equals(agentSource)) {
            var10001 = this._meta;
            this._meta.setAgentSource(1);
         } else if ("arg".equals(agentSource)) {
            var10001 = this._meta;
            this._meta.setAgentSource(2);
         } else {
            var10001 = this._meta;
            this._meta.setAgentSource(0);
         }

         String agentSourceSymbol = attrs.getValue("agent-source-symbol");
         this._meta.setAgentSourceSymbol(agentSourceSymbol);
         String envSource = attrs.getValue("env-source");
         if ("arg".equals(envSource)) {
            var10001 = this._meta;
            this._meta.setEnvSource(2);
         } else {
            var10001 = this._meta;
            this._meta.setEnvSource(1);
         }

         String envSourceSymbol = attrs.getValue("env-source-symbol");
         this._meta.setEnvSourceSymbol(envSourceSymbol);
         return true;
      }
   }

   protected void endClass(String elem) throws SAXException {
      this.addResult(this._meta);
      this._meta = null;
      super.endClass(elem);
   }

   protected boolean startClassElement(String name, Attributes attrs) throws SAXException {
      char firstLetter = name.charAt(0);
      switch (firstLetter) {
         case 'a':
            this.startArg(attrs);
            return true;
         case 'f':
            this.startFormatter(attrs);
            return true;
         case 'm':
            this.startMethod(attrs);
            return true;
         case 'p':
            this.startProperty(attrs);
            return true;
         default:
            return false;
      }
   }

   protected void endClassElement(String name) throws SAXException {
      if (name.charAt(0) == 'm') {
         this.endMethod();
      } else if (name.charAt(0) == 'a') {
         this.endArg();
      } else if (name.charAt(0) == 'p') {
         this.endProperty();
      }

   }

   private void startMethod(Attributes attrs) throws SAXException {
      String name = attrs.getValue("name");
      String profilingName = attrs.getValue("profile-name");
      String desc = attrs.getValue("description");
      String cat = attrs.getValue("category");
      this._method = new ProfilingMethodMetaData(name, profilingName, desc, cat);
      this._meta.add(this._method);
   }

   private void endMethod() {
      this._method = null;
   }

   private void startArg(Attributes attrs) throws SAXException {
      String type = attrs.getValue("arg-type");
      String isDescriberStr = attrs.getValue("is-describer");
      boolean isDescriber = "true".equals(isDescriberStr);
      String isAgentProviderStr = attrs.getValue("is-agent-provider");
      boolean isAgentProvider = "true".equals(isAgentProviderStr);
      String isEnvStr = attrs.getValue("is-env");
      boolean isEnv = "true".equals(isEnvStr);
      String name = attrs.getValue("name");
      this._formattable = this._method.addSignatureElement(type, isDescriber, name, isAgentProvider, isEnv);
   }

   private void endArg() {
      this._formattable = null;
   }

   private void startProperty(Attributes attrs) throws SAXException {
      String name = attrs.getValue("name");
      String profilingName = attrs.getValue("profile-name");
      this._formattable = this._method.addPropertyElement(name, profilingName);
   }

   private void endProperty() {
      this._formattable = null;
   }

   private void startFormatter(Attributes attrs) throws SAXException {
      String className = attrs.getValue("class-name");
      String methodName = attrs.getValue("method-name");
      this._formattable.setFormatter(className, methodName);
   }

   public InputSource resolveEntity(String pub, String sys) throws SAXException {
      InputSource source = _resolver.resolveEntity(pub, sys);

      try {
         return source == null ? super.resolveEntity(pub, sys) : source;
      } catch (SAXException var5) {
         throw var5;
      } catch (RuntimeException var6) {
         throw var6;
      } catch (Exception var7) {
         throw new SAXException(var7);
      }
   }
}

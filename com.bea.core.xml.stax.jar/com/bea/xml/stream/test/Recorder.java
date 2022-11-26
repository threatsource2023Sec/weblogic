package com.bea.xml.stream.test;

import com.bea.xml.stream.XMLStreamRecorder;
import com.bea.xml.stream.filters.NameFilter;
import com.bea.xml.stream.filters.TypeFilter;
import com.bea.xml.stream.util.ElementTypeNames;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.StringTokenizer;
import javax.xml.namespace.QName;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;

public class Recorder {
   public String resType;
   public String resName;
   public String filter;
   public QName name;
   private StringBuffer fSuffix;
   private String nSuffix;

   public Recorder() {
   }

   public Recorder(String[] params) throws Exception {
      if (params[0] != null && params[1] != null) {
         this.resType = params[0];
         this.resName = params[1];
         if (params.length == 3) {
            StringTokenizer st = new StringTokenizer(params[2], "=");
            String typeorname = st.nextToken();
            String value = st.nextToken();
            if (typeorname.equals("filter")) {
               this.filter = new String(value);
            } else {
               StringTokenizer nst = new StringTokenizer(value, "*");
               this.name = new QName(nst.nextToken(), nst.nextToken());
            }
         }

      } else {
         throw new Exception("You must provide resourceType and resourceName");
      }
   }

   private StreamFilter resolveFilter() throws Exception {
      int cnt = 0;
      StringTokenizer st = new StringTokenizer(this.filter, "*");
      this.fSuffix = new StringBuffer(st.countTokens());
      TypeFilter f = new TypeFilter();

      while(st.hasMoreTokens()) {
         String eventName = st.nextToken();
         PrintStream var10000 = System.out;
         StringBuffer var10001 = (new StringBuffer()).append("eName of filter");
         ++cnt;
         var10000.println(var10001.append(cnt).append(" is: ").append(eventName).toString());
         int eventType = ElementTypeNames.getEventType(eventName);
         System.out.println("its event val is: " + eventType);
         f.addType(eventType);
         this.fSuffix.append("_");
         this.fSuffix.append(eventName);
      }

      return f;
   }

   private StreamFilter resolveName() throws Exception {
      int cnt = false;
      this.nSuffix = "_" + this.name.getNamespaceURI() + "_" + this.name.getLocalPart();
      System.out.println("namespace of filter is: " + this.nSuffix);
      NameFilter f = new NameFilter(this.name);
      return f;
   }

   private void recordStream(String fName) throws Exception {
      XMLInputFactory xmlif = XMLInputFactory.newInstance();
      XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
      String fPrefix = "";
      String outFile = null;
      StringTokenizer st = new StringTokenizer(this.resName, ".");
      if (st.hasMoreTokens()) {
         fPrefix = st.nextToken();
      }

      XMLStreamReader xmlr;
      StreamFilter f;
      if (this.filter != null) {
         f = this.resolveFilter();
         xmlr = xmlif.createFilteredReader(xmlif.createXMLStreamReader(new FileReader(fName)), f);
         outFile = fPrefix + "_filtered" + this.fSuffix + ".stream";
         System.out.println("Resultant master file is: " + outFile);
      } else if (this.name != null) {
         f = this.resolveName();
         xmlr = xmlif.createFilteredReader(xmlif.createXMLStreamReader(new FileReader(fName)), f);
         outFile = fPrefix + "_filtered" + this.nSuffix + ".stream";
         System.out.println("Resultant master file is: " + outFile);
      } else {
         xmlr = xmlif.createXMLStreamReader(new FileReader(fName));
         outFile = fPrefix + ".stream";
      }

      XMLStreamRecorder r = new XMLStreamRecorder(new OutputStreamWriter(new FileOutputStream(outFile)));

      while(xmlr.hasNext()) {
         r.write(xmlr);
         xmlr.next();
      }

      r.write(xmlr);
      r.flush();
   }

   public void startRecording() throws Exception {
      if ("-file".equals(this.resType)) {
         this.recordStream(this.resName);
      } else if ("-dir".equals(this.resType)) {
         File d = new File(this.resName);
         if (!d.isDirectory()) {
            return;
         }

         File[] f = d.listFiles();

         for(int i = 0; i < f.length; ++i) {
            this.recordStream(f[i].getName());
         }
      } else {
         System.out.println("Input not properly specified");
      }

   }

   public static void main(String[] args) throws Exception {
      if ("-help".equals(args[0])) {
         System.out.println(" Usage : ");
         System.out.println(" java com.bea.xml.stream.test.Recorder [$option] [$resource] [filter=$filter | namespace=$namespace]");
         System.out.println(" $option can be [-file|-dir]");
         System.out.println(" $resource is either the file name or a directory name");
         System.out.println(" $filter is a set of Events seperated by \"*\" ");
         System.out.println(" $namespace is the namespace to be filtered on ");
         System.exit(0);
      }

      Recorder rec = new Recorder(args);
      rec.startRecording();
   }
}

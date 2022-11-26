package weblogic.ant.taskdefs.antline;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Main;
import weblogic.utils.Executable;
import weblogic.utils.StringUtils;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.XMLOutputStream;
import weblogic.xml.stream.XMLOutputStreamFactory;
import weblogic.xml.stream.XMLStreamException;

public class AntLauncher {
   private static String XML_TASK_NAME = "antline_task";
   private static String XML_PROJECT_NAME = "antline_project";
   private static String XML_TARGET_NAME = "antline_target";
   private static final int ERR_BUFFER_SIZE = 2048;
   private Collection antOpts;
   private Class task;
   private String command;
   private boolean externalProcess = false;

   public AntLauncher(Class taskClass, Collection opts) {
      this.antOpts = opts;
      this.task = taskClass;
      if (AntTool.debug) {
         System.out.println("task = " + taskClass.getName());
      }

   }

   public void launch() throws BuildException, IOException {
      File bxml = this.generateBuildXml();
      if (this.externalProcess) {
         launchExternal(bxml);
      } else {
         launchInternal(bxml);
      }

   }

   private static void launchInternal(File buildxml) throws BuildException, IOException {
      List antArgs = new ArrayList();
      int i = false;
      antArgs.add("-f");
      antArgs.add(buildxml.getCanonicalPath());
      antArgs.add("-logger");
      antArgs.add("weblogic.ant.taskdefs.antline.ToolLogger");
      if (AntTool.debug) {
         antArgs.add("-verbose");
      } else {
         antArgs.add("-quiet");
      }

      try {
         Main.start((String[])((String[])antArgs.toArray(new String[0])), (Properties)null, (ClassLoader)null);
      } finally {
         if (!AntTool.debug) {
            buildxml.delete();
         }

      }

   }

   private static void launchExternal(File buildxml) throws BuildException, IOException {
      List antArgs = new ArrayList();
      int i = false;
      antArgs.add("java");
      antArgs.add("org.apache.tools.ant.Main");
      antArgs.add("-f");
      antArgs.add(buildxml.getCanonicalPath());
      antArgs.add("-logger");
      antArgs.add("weblogic.ant.taskdefs.antline.ToolLogger");
      if (AntTool.debug) {
         antArgs.add("-verbose");
      } else {
         antArgs.add("-quiet");
      }

      CharArrayWriter err = new CharArrayWriter(2048);
      CharArrayWriter out = new CharArrayWriter(2048);
      Executable exec = new Executable();
      exec.setUseCharWriter(out, err);
      boolean success = exec.exec((String[])((String[])antArgs.toArray(new String[0])));
      if (!AntTool.debug) {
         buildxml.delete();
      }

      if (AntTool.debug) {
         System.out.println(out.toString());
      }

      if (!success) {
         throw new BuildException("Command failed. Error output was:\n" + err.toString());
      }
   }

   private File generateBuildXml() throws IOException {
      String tmpDir = System.getProperty("java.io.tmpdir");
      if (tmpDir == null) {
         tmpDir = ".";
      }

      File tmpDirFile = new File(tmpDir);
      tmpDirFile.mkdirs();
      File tmpFile = File.createTempFile("antline", ".xml", tmpDirFile);
      if (AntTool.debug) {
         System.out.println("tmpfile = " + tmpFile);
      } else {
         tmpFile.deleteOnExit();
      }

      XMLOutputStreamFactory xfact = XMLOutputStreamFactory.newInstance();
      XMLOutputStream xout = xfact.newOutputStream(new FileOutputStream(tmpFile));
      xout.add(ElementFactory.createStartElement("project"));
      xout.add(ElementFactory.createAttribute("name", XML_PROJECT_NAME));
      xout.add(ElementFactory.createAttribute("default", XML_TARGET_NAME));
      xout.add(ElementFactory.createStartElement("taskdef"));
      xout.add(ElementFactory.createAttribute("name", XML_TASK_NAME));
      xout.add(ElementFactory.createAttribute("classname", this.task.getName()));
      xout.add(ElementFactory.createEndElement("taskdef"));
      xout.add(ElementFactory.createStartElement("target"));
      xout.add(ElementFactory.createAttribute("name", XML_TARGET_NAME));
      ParamNode tree = this.buildParamTree(this.antOpts);
      xout = this.emitXML(tree, xout);
      xout.add(ElementFactory.createEndElement("target"));
      xout.add(ElementFactory.createEndElement("project"));
      xout.flush();
      return tmpFile;
   }

   private XMLOutputStream emitXML(ParamNode node, XMLOutputStream xout) throws XMLStreamException {
      String name = node.getName();
      xout.add(ElementFactory.createStartElement(name));
      Collection attrs = node.getAttributeChildren();
      Iterator i = attrs.iterator();

      while(i.hasNext()) {
         ParamNode p = (ParamNode)i.next();
         AntOpt opt = p.getParam();
         if (opt.getValue() != null) {
            xout.add(ElementFactory.createAttribute(opt.getAntAttrName(), opt.getValue()));
         }
      }

      Collection childElts = node.getElementChildren();
      Iterator i = childElts.iterator();

      while(i.hasNext()) {
         this.emitXML((ParamNode)i.next(), xout);
      }

      xout.add(ElementFactory.createEndElement(name));
      return xout;
   }

   private ParamNode buildParamTree(Collection antOpts) {
      ParamNode top = new ParamNode(XML_TASK_NAME);
      Iterator i = antOpts.iterator();

      while(i.hasNext()) {
         AntOpt opt = (AntOpt)i.next();
         if (opt.getValue() != null) {
            this.addNode(top, opt);
         }
      }

      return top;
   }

   private void addNode(ParamNode top, AntOpt opt) {
      String[] eltPath = StringUtils.splitCompletely(opt.getAntElementPath(), "/");
      ParamNode currNode = top;

      ParamNode cnode;
      for(int i = 0; i < eltPath.length; ++i) {
         cnode = currNode.getChildByName(eltPath[i]);
         if (cnode == null) {
            cnode = new ParamNode(eltPath[i]);
            currNode.addChild(cnode);
         }

         currNode = cnode;
      }

      String attrName = "@" + opt.getAntAttrName();
      if (attrName != null) {
         cnode = currNode.getChildByName(attrName);
         if (cnode == null) {
            cnode = new ParamNode(attrName);
            currNode.addChild(cnode);
         }

         currNode = cnode;
      }

      currNode.setParam(opt);
   }

   public void setExternalProcess(boolean val) {
      this.externalProcess = val;
   }

   public boolean getExternalProcess() {
      return this.externalProcess;
   }

   private static class ParamNode {
      private String name;
      private AntOpt value;
      private List children = new ArrayList();

      public ParamNode(String n) {
         this.name = n;
         if (AntTool.debug) {
            System.out.println("ParamNode[" + System.identityHashCode(this) + "] = " + this.name);
         }

      }

      public String getName() {
         return this.name;
      }

      public AntOpt getParam() {
         return this.value;
      }

      public void setParam(AntOpt val) {
         this.value = val;
      }

      public void addChild(ParamNode p) {
         if (AntTool.debug) {
            System.out.println("ParamNode[" + System.identityHashCode(this) + "].addChild(" + System.identityHashCode(p));
         }

         this.children.add(p);
      }

      public ParamNode getChildByName(String name) {
         Iterator i = this.children.iterator();

         ParamNode pn;
         do {
            if (!i.hasNext()) {
               return null;
            }

            pn = (ParamNode)i.next();
         } while(!pn.getName().equals(name));

         return pn;
      }

      public Collection getAttributeChildren() {
         List ac = new ArrayList();
         Iterator i = this.children.iterator();

         while(i.hasNext()) {
            ParamNode pn = (ParamNode)i.next();
            if (pn.getName().startsWith("@")) {
               ac.add(pn);
            }
         }

         return ac;
      }

      public Collection getElementChildren() {
         List ac = new ArrayList();
         Iterator i = this.children.iterator();

         while(i.hasNext()) {
            ParamNode pn = (ParamNode)i.next();
            if (!pn.getName().startsWith("@")) {
               ac.add(pn);
            }
         }

         return ac;
      }
   }
}

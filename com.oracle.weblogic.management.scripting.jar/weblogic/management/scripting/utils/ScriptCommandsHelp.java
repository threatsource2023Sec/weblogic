package weblogic.management.scripting.utils;

import java.io.InputStream;
import java.util.Collections;
import java.util.Locale;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import weblogic.management.scripting.WLScriptContext;
import weblogic.management.scripting.core.utils.ScriptCommandsCoreHelp;
import weblogic.utils.XXEUtils;

public class ScriptCommandsHelp extends ScriptCommandsCoreHelp {
   private static final String wlstHelpFileBase = "WLSTHelp";

   public ScriptCommandsHelp(WLScriptContext ctx) {
      super(ctx);
      this.loadHelp();
      this.addHelpType("lifecycle");
      this.addHelpType("editing");
      this.addHelpType("deployment");
      this.addHelpType("variables");
      this.addHelpType("trees");
      this.addHelpType("offline");
      this.addHelpType("online");
      this.addHelpType("common");
      this.addHelpType("diagnostics");
      this.addHelpType("storeadmin");
   }

   private void loadHelp() {
      if (helpTypes.isEmpty()) {
         helpTypes = Collections.synchronizedSortedMap(new TreeMap());
      }

      helpTypes.put("deployment", new Integer(14));
      helpTypes.put("editing", new Integer(13));
      helpTypes.put("lifecycle", new Integer(12));
      helpTypes.put("diagnostics", new Integer(18));
      helpTypes.put("variables", new Integer(19));
      helpTypes.put("trees", new Integer(20));
      helpTypes.put("common", new Integer(22));
      helpTypes.put("offline", new Integer(23));
      helpTypes.put("online", new Integer(24));
      helpTypes.put("storeadmin", new Integer(25));
      Locale loc = Locale.getDefault();
      String lang = loc.getLanguage();
      String country = loc.getCountry();
      String helpFile = "WLSTHelp_" + lang + "_" + country + ".xml";
      InputStream is = ScriptCommandsHelp.class.getResourceAsStream(helpFile);
      if (is == null) {
         helpFile = "WLSTHelp_" + lang + ".xml";
         is = ScriptCommandsHelp.class.getResourceAsStream(helpFile);
      }

      if (is == null) {
         helpFile = "WLSTHelp.xml";
         is = ScriptCommandsHelp.class.getResourceAsStream(helpFile);
      }

      if (is == null) {
         throw new AssertionError("Could not find " + helpFile);
      } else {
         try {
            DocumentBuilder builder = XXEUtils.createDocumentBuilderFactoryInstance().newDocumentBuilder();
            Document doc = builder.parse(is);
            Element root = doc.getDocumentElement();
            NodeList nodes = root.getChildNodes();
            if (nodes == null) {
               return;
            }

            Node topNode = null;

            for(int i = 0; i < nodes.getLength(); ++i) {
               topNode = nodes.item(i);
               if (topNode.getNodeType() == 1) {
                  if (topNode.getNodeName().equals("helpTopic")) {
                     this.handleHelpTopic(topNode);
                  } else if (topNode.getNodeName().equals("helpString")) {
                     this.handleHelpString(topNode);
                  }
               }
            }
         } catch (Exception var12) {
            var12.printStackTrace();
         }

      }
   }
}

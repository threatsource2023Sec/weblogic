package weblogic.management.provider.internal.federatedconfig;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.xml.namespace.QName;

class RequiredElementsTransformationGenerator {
   private static final int INDENT_DELTA = 2;
   private static final String SELECT_FOR_PREFIX_MATCHING_NAMESPACE_FORMAT = String.format("%1$s:node-set($%2$s)/%3$s:%4$s[@%3$s:namespace=namespace-uri(current())]/@%3$s:prefix", "exsl", "__nsMap", "__ns", "namespace-def");
   private static final String STAGE_TO_ENSURE_ALL_ELEMENTS_PREFIXED;
   private final Map requiredPaths;
   private final FederatedConfigFragmentsProcessor processor;
   private final String lookupMap;

   static boolean isDebug() {
      return FederatedConfigImpl.isDebug();
   }

   static void debug(String msg) {
      FederatedConfigImpl.debug(msg);
   }

   RequiredElementsTransformationGenerator(Map requiredPaths, FederatedConfigFragmentsProcessor processor, String lookupMapForNamespacesToPrefixes) {
      this.requiredPaths = requiredPaths;
      this.processor = processor;
      this.lookupMap = lookupMapForNamespacesToPrefixes;
   }

   String run() {
      StringBuilder result = new StringBuilder();
      result.append(this.addStageToPrefixAllElements());
      int familyNumber = 0;

      for(Iterator var3 = this.requiredPaths.entrySet().iterator(); var3.hasNext(); ++familyNumber) {
         Map.Entry requiredForGivenRoot = (Map.Entry)var3.next();
         result.append(this.addTransformationsForRoot(familyNumber, (QName)requiredForGivenRoot.getKey(), (TreeNode)requiredForGivenRoot.getValue()));
      }

      return result.toString();
   }

   private String addStageToPrefixAllElements() {
      StringBuilder result = new StringBuilder(this.lookupMap);
      result.append(STAGE_TO_ENSURE_ALL_ELEMENTS_PREFIXED);
      return result.toString();
   }

   private String addTransformationsForRoot(int familyNumber, QName rootName, TreeNode requiredPathsForRoot) {
      StringBuilder templatesForThisRoot = new StringBuilder();
      StringBuilder var10000 = templatesForThisRoot.append("<").append("xsl").append(":template match='");
      FederatedConfigFragmentsProcessor var10001 = this.processor;
      var10000.append(FederatedConfigFragmentsProcessor.getPrefixedName(rootName)).append("'>");
      BFTraversal traversal = new BFTraversal(rootName, familyNumber);
      requiredPathsForRoot.breadthFirstTraversal(traversal);
      return traversal.contentForInitialTemplate.toString() + traversal.body.toString();
   }

   static {
      STAGE_TO_ENSURE_ALL_ELEMENTS_PREFIXED = String.format("<%1$s:template mode='fixupNS' match='//*[not(contains(name(),\":\"))]'>\n  <%1$s:variable name='prefixFound' select='%2$s'/>\n  <%1$s:variable name='prefixToUse'> \n    <%1$s:choose> \n      <%1$s:when test='$prefixFound'><%1$s:value-of select='$prefixFound'/>:</%1$s:when> \n      <%1$s:otherwise/> \n    </%1$s:choose>\n  </%1$s:variable>\n  <%1$s:element name='{$prefixToUse}{local-name()}' namespace='{namespace-uri()}'>\n    <%1$s:apply-templates mode='fixupNS' select=\"@* | node()\"/>\n  </%1$s:element>\n</%1$s:template>\n\n<%1$s:template mode='fixupNS' match='@* | node()'>\n  <%1$s:copy>\n    <%1$s:apply-templates mode='fixupNS' select='@* | node()'/>\n  </%1$s:copy>\n</%1$s:template>\n", "xsl", SELECT_FOR_PREFIX_MATCHING_NAMESPACE_FORMAT);
   }

   private class BFTraversal implements TreeNode.BreadthFirstVisitor {
      private final String stagePrefix;
      private final String varNamePrefix;
      private final String finalModeName;
      private int stageNumber = 0;
      private final String XSL = "xsl:";
      private final String EXSL = "exsl:";
      private final String prefixedRootName;
      private final StringBuilder contentForInitialTemplate = new StringBuilder();
      private final StringBuilder body = new StringBuilder(this.comment("Start of body"));
      private AtomicInteger initialTemplateIndent = new AtomicInteger(1);
      private AtomicInteger bodyIndent = new AtomicInteger(1);

      BFTraversal(QName rootName, int familyNumber) {
         this.stagePrefix = "stage_" + familyNumber + "_";
         this.varNamePrefix = "output_" + familyNumber + "_";
         this.finalModeName = "final";
         this.prefixedRootName = FederatedConfigFragmentsProcessor.getPrefixedName(RequiredElementsTransformationGenerator.this.processor.choosePrefix(rootName), rootName);
         this.contentForInitialTemplate.append(this.comment("Start of initial template")).append(this.indent(this.initialTemplateIndent)).append(this.startTemplate("/"));
         this.contentForInitialTemplate.append(this.variable(0, this.applyTemplates("/", "fixupNS")));
      }

      private String comment(String note) {
         return "<!-- " + note + " -->" + FederatedConfigUtils.LINE_SEP;
      }

      private String indent(AtomicInteger indent) {
         return this.margin(indent.addAndGet(2));
      }

      private String unindent(AtomicInteger indent) {
         return this.margin(indent.addAndGet(-2));
      }

      private String margin(int margin) {
         StringBuilder result = new StringBuilder();

         for(int i = 0; i < margin; ++i) {
            result.append(" ");
         }

         return result.toString();
      }

      private String startTemplate(String match) {
         return this.startTemplate(match, -1);
      }

      private String startTemplate(String match, int stage) {
         return this.startTemplate(match, this.modeName(stage));
      }

      private String startTemplate(String match, String mode) {
         StringBuilder result = new StringBuilder();
         result.append("<").append("xsl:").append("template match=\"").append(match).append("\"").append(this.mode(mode)).append(">").append(FederatedConfigUtils.LINE_SEP);
         return result.toString();
      }

      private String modeName(int stage) {
         return stage == -1 ? "" : this.stagePrefix + stage;
      }

      private String mode(String modeName) {
         return modeName != null && !FederatedConfigUtils.isEmpty(modeName) ? " mode='" + modeName + "'" : "";
      }

      private String varName(int stage) {
         return this.varNamePrefix + stage;
      }

      private String endTemplate() {
         return "</xsl:template>" + FederatedConfigUtils.LINE_SEP;
      }

      private String variable(int stage, String value) {
         StringBuilder result = new StringBuilder();
         result.append("<").append("xsl:").append("variable name='").append(this.varName(stage)).append("'>").append(FederatedConfigUtils.LINE_SEP);
         result.append(value);
         result.append("</").append("xsl:").append("variable>").append(FederatedConfigUtils.LINE_SEP);
         return result.toString();
      }

      private String applyTemplates(String select, int stage) {
         return this.applyTemplates(select, this.modeName(stage));
      }

      private String applyTemplates(String select, String modeName) {
         StringBuilder result = new StringBuilder();
         result.append("<").append("xsl:").append("apply-templates ").append(this.select(select)).append(this.mode(modeName)).append("/>").append(FederatedConfigUtils.LINE_SEP);
         return result.toString();
      }

      private String select(String selectExpr) {
         return selectExpr != null && !FederatedConfigUtils.isEmpty(selectExpr) ? "select='" + selectExpr + "' " : "";
      }

      private String nodeset(int stage) {
         return "exsl:node-set($" + this.varName(stage) + ")";
      }

      private String templateForBody(int stage) {
         StringBuilder result = new StringBuilder();
         result.append(this.startTemplate("@* | node()", stage)).append(this.copy(this.applyTemplates("@* | node()", stage))).append(this.endTemplate());
         return result.toString();
      }

      private String copy(String body) {
         return this.startCopy() + FederatedConfigUtils.LINE_SEP + body + this.endCopy() + FederatedConfigUtils.LINE_SEP;
      }

      private String startCopy() {
         return "<xsl:copy>";
      }

      private String endCopy() {
         return "</xsl:copy>";
      }

      public void nextLevel() {
         ++this.stageNumber;
         String selectExpr = this.nodeset(this.stageNumber - 1);
         this.contentForInitialTemplate.append(this.variable(this.stageNumber, this.applyTemplates(selectExpr, this.stageNumber)));
         this.body.append(this.templateForBody(this.stageNumber));
      }

      public void nextNodeWithinLevel(TreeNode node) {
         this.body.append(this.startTemplate(this.XPathForNode(node), this.stageNumber));
         this.body.append(this.startCopy());
         this.body.append(this.applyTemplates("@* | node()", this.stageNumber));
         Iterator var2 = node.getChildren().iterator();

         while(var2.hasNext()) {
            TreeNode child = (TreeNode)var2.next();
            this.body.append(this.elementGuarantee((FederatedConfigFragmentsProcessor.Step)child.value()));
         }

         this.body.append(this.endCopy());
         this.body.append(this.endTemplate());
      }

      public void done() {
         this.contentForInitialTemplate.append(this.applyTemplates(this.nodeset(this.stageNumber), this.finalModeName));
         this.contentForInitialTemplate.append(this.endTemplate());
         this.contentForInitialTemplate.append(this.comment("End of initial template"));
         this.body.append(this.comment("End of body"));
      }

      private String elementGuarantee(FederatedConfigFragmentsProcessor.Step step) {
         StringBuilder result = new StringBuilder();
         String elementToInsert = this.element(step);
         result.append("<").append("xsl:").append("if test=\"not(").append(this.stepTypeAndNameXPath(step)).append(")\">");
         result.append(elementToInsert);
         result.append("</").append("xsl:").append("if>");
         return result.toString();
      }

      private String stepTypeAndNameXPath(FederatedConfigFragmentsProcessor.Step step) {
         return FederatedConfigFragmentsProcessor.XPathElementAndName(step);
      }

      private String element(FederatedConfigFragmentsProcessor.Step step) {
         return step.getNameValue() == null ? this.element(step.getName(), (String)null) : this.element(step.getName(), this.element(step.getNameName(), step.getNameValue()));
      }

      private String message(String msg) {
         return "<xsl:message>" + msg + "</" + "xsl:" + "message>\n";
      }

      private String copyOf(String select) {
         return "<xsl:copy-of select=\"" + select + "\"/>";
      }

      private String valueOf(String target) {
         return "<xsl:value-of select=\"" + target + "\"/>";
      }

      private String element(QName name, String value) {
         StringBuilder result = new StringBuilder(this.startElement(name));
         if (value != null) {
            result.append(value).append(FederatedConfigUtils.LINE_SEP);
         }

         result.append(this.endElement());
         return result.toString();
      }

      private String startElement(QName name) {
         StringBuilder result = new StringBuilder();
         result.append("<").append("xsl:").append("element name='").append(RequiredElementsTransformationGenerator.this.processor.getPrefix(name.getNamespaceURI())).append(":").append(name.getLocalPart()).append("' ");
         result.append("namespace='").append(name.getNamespaceURI()).append("'>\n");
         return result.toString();
      }

      private String endElement() {
         return "</xsl:element>\n";
      }

      private String XPathForNode(TreeNode node) {
         FederatedConfigDeque steps;
         for(steps = new FederatedConfigDeque(); node != null; node = node.parent()) {
            steps.addFirst(node.value());
         }

         return FederatedConfigFragmentsProcessor.navPath(steps.iterator());
      }
   }
}

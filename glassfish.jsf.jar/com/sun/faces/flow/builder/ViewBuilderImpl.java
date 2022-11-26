package com.sun.faces.flow.builder;

import com.sun.faces.flow.ViewNodeImpl;
import java.util.List;
import javax.faces.flow.builder.ViewBuilder;

public class ViewBuilderImpl extends ViewBuilder {
   private FlowBuilderImpl root;
   private ViewNodeImpl viewNode;

   public ViewBuilderImpl(FlowBuilderImpl root, String viewNodeId, String vdlDocumentId) {
      this.root = root;
      List viewNodes = root._getFlow()._getViews();
      this.viewNode = new ViewNodeImpl(viewNodeId, vdlDocumentId);
      viewNodes.add(this.viewNode);
   }

   public ViewBuilder markAsStartNode() {
      this.root._getFlow().setStartNodeId(this.viewNode.getId());
      return this;
   }
}

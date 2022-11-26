package weblogic.servlet.ejb2jsp.gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import weblogic.servlet.ejb2jsp.dd.BeanDescriptor;
import weblogic.servlet.ejb2jsp.dd.EJBMethodDescriptor;
import weblogic.servlet.ejb2jsp.dd.EJBTaglibDescriptor;
import weblogic.servlet.ejb2jsp.dd.FilesystemInfoDescriptor;
import weblogic.servlet.ejb2jsp.dd.MethodParamDescriptor;

public class EJBTaglibDescriptorTree extends JTree {
   EJBTaglibDescriptor _bean;
   DefaultTreeModel defaultModel = null;

   public EJBTaglibDescriptorTree(EJBTaglibDescriptor _b) throws Exception {
      this._bean = _b;
      this.defaultModel = new DefaultTreeModel(this.makeRootNode());
      this.setModel(this.defaultModel);
   }

   private TreeNode makeRootNode() throws Exception {
      DefaultMutableTreeNode root = null;
      root = new MyNode(this._bean, true, (String)null);
      this.add_nodes_EJBTaglibDescriptor(root, this._bean);
      return root;
   }

   public EJBTaglibDescriptor getBean() {
      return this._bean;
   }

   private void add_nodes_EJBTaglibDescriptor(DefaultMutableTreeNode root, EJBTaglibDescriptor bean) throws Exception {
      if (bean != null) {
         BeanDescriptor[] sub_beans = bean.getBeans();
         if (sub_beans != null && sub_beans.length > 0) {
            DefaultMutableTreeNode forArray = root;

            for(int i = 0; i < sub_beans.length; ++i) {
               DefaultMutableTreeNode nd = new MyNode(sub_beans[i], true);
               forArray.add(nd);
               this.add_nodes_BeanDescriptor(nd, sub_beans[i]);
            }
         }

         DefaultMutableTreeNode nd = new MyNode(bean.getFileInfo(), (String)null);
         root.add(nd);
         this.add_nodes_FilesystemInfoDescriptor(nd, bean.getFileInfo());
      }
   }

   private void add_nodes_FilesystemInfoDescriptor(DefaultMutableTreeNode root, FilesystemInfoDescriptor bean) throws Exception {
      if (bean != null) {
         ;
      }
   }

   private void add_nodes_MethodParamDescriptor(DefaultMutableTreeNode root, MethodParamDescriptor bean) throws Exception {
      if (bean != null) {
         ;
      }
   }

   private void add_nodes_EJBMethodDescriptor(DefaultMutableTreeNode root, EJBMethodDescriptor bean) throws Exception {
      if (bean != null) {
         MethodParamDescriptor[] sub_beans = bean.getParams();
         if (sub_beans != null && sub_beans.length > 0) {
            DefaultMutableTreeNode forArray = root;

            for(int i = 0; i < sub_beans.length; ++i) {
               DefaultMutableTreeNode nd = new MyNode(sub_beans[i], true);
               forArray.add(nd);
               this.add_nodes_MethodParamDescriptor(nd, sub_beans[i]);
            }
         }

      }
   }

   private void add_nodes_BeanDescriptor(DefaultMutableTreeNode root, BeanDescriptor bean) throws Exception {
      if (bean != null) {
         EJBMethodDescriptor[] sub_beans = bean.getHomeMethods();
         MyNode forArray;
         int i;
         MyNode nd;
         if (sub_beans != null && sub_beans.length > 0) {
            forArray = new MyNode(sub_beans, true, "Home Methods");
            root.add(forArray);

            for(i = 0; i < sub_beans.length; ++i) {
               nd = new MyNode(sub_beans[i], true);
               forArray.add(nd);
               this.add_nodes_EJBMethodDescriptor(nd, sub_beans[i]);
            }
         }

         sub_beans = bean.getEJBMethods();
         if (sub_beans != null && sub_beans.length > 0) {
            forArray = new MyNode(sub_beans, true, "EJB Methods");
            root.add(forArray);

            for(i = 0; i < sub_beans.length; ++i) {
               nd = new MyNode(sub_beans[i], true);
               forArray.add(nd);
               this.add_nodes_EJBMethodDescriptor(nd, sub_beans[i]);
            }
         }

      }
   }
}

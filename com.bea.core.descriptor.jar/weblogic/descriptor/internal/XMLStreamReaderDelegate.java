package weblogic.descriptor.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import weblogic.descriptor.BeanCreationInterceptor;
import weblogic.descriptor.DescriptorBean;

public class XMLStreamReaderDelegate extends StreamReaderDelegate implements XMLStreamReader, BeanCreationInterceptor {
   protected List comments;
   Stack beanStack = new Stack();
   DescriptorImpl descImpl;
   int counter = 0;
   Stack counterStack = new Stack();
   BeanCreationInterceptor delegate;

   public XMLStreamReaderDelegate(XMLStreamReader delegate) {
      super(delegate);
      if (delegate instanceof BeanCreationInterceptor) {
         this.delegate = (BeanCreationInterceptor)delegate;
      }

      this.resetCommentList();
   }

   public BeanCreationInterceptor getDelegate() {
      return this.delegate;
   }

   private void resetCommentList() {
      if (this.comments == null) {
         this.comments = new ArrayList();
      } else if (this.comments.size() > 0) {
         this.comments.clear();
      }

   }

   public int next() throws XMLStreamException {
      int next = super.next();
      switch (next) {
         case 1:
            this._onStartElement();
            break;
         case 2:
            this._onEndElement();
         case 3:
         case 4:
         default:
            break;
         case 5:
            this._onComment();
      }

      return next;
   }

   private void _onComment() {
      if (!this.isWhiteSpace()) {
         this.comments.add(this.getText());
      }

   }

   private void _onStartElement() {
      this.incCounter();
      if (this.comments.size() > 0) {
         this.storeBeanComments(this.getName().getLocalPart());
      }

   }

   private void _onEndElement() {
      this.decCounter();
   }

   public DescriptorBean beanCreated(DescriptorBean bean, DescriptorBean parent) {
      if (parent == null) {
         this.descImpl = (DescriptorImpl)bean.getDescriptor();
         this.storeDescriptorComments();
      }

      if (bean instanceof AbstractDescriptorBean) {
         this.resetCounter((AbstractDescriptorBean)bean);
      }

      return this.delegate == null ? bean : this.delegate.beanCreated(bean, parent);
   }

   private void storeBeanComments(String propertyName) {
      if (this.beanStack.size() > 0) {
         AbstractDescriptorBean lastBean = (AbstractDescriptorBean)this.beanStack.peek();
         lastBean.addXMLComments(propertyName, this.comments);
         this.resetCommentList();
      } else {
         this.storeDescriptorComments();
      }

   }

   private void storeDescriptorComments() {
      if (this.descImpl != null) {
         this.descImpl.addXMLComments(this.comments);
         this.resetCommentList();
      }

   }

   private void resetCounter(AbstractDescriptorBean newBean) {
      this.counterStack.push(new Integer(this.counter));
      this.counter = 0;
      this.beanStack.push(newBean);
   }

   private void incCounter() {
      ++this.counter;
   }

   private void decCounter() {
      --this.counter;
      if (this.counter < 0 && this.beanStack.size() > 0) {
         this.beanStack.pop();
         this.counter = (Integer)this.counterStack.pop();
      }

   }
}

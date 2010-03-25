package eu.europeana.core

/**
 *
 * @author Sjoerd Siebinga <sjoerd.siebinga@gmail.com>
 * @since Mar 11, 2010 11:51:02 PM
 */

trait StackPrinter {
  import javax.xml.namespace.QName
  import collection.mutable.Stack

  def prettyPrintStack(stack: Stack[QName]) = if (stack.isEmpty) "" else printStack(stack)

  private def printStack(stack: Stack[QName]): String =
        stack.reverse.map(x =>
          if (!x.getPrefix.isEmpty){ format("%s:%s", x.getPrefix, x.getLocalPart) } else x.getLocalPart)
                .mkString(start = "/", end = "", sep = "/")

}
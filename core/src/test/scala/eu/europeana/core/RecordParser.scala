package eu.europeana.core

import javax.xml.stream.XMLStreamReader
import javax.xml.namespace.QName
import java.util.Stack

/**
 *
 * @author Sjoerd Siebinga <sjoerd.siebinga@gmail.com>
 * @since Mar 12, 2010 7:34:13 AM
 */

class RecordParser(parser: XMLStreamReader, recordSeparator: QName) extends StackPrinter {
  import collection.mutable.Stack
  import javax.xml.stream.XMLStreamConstants

  private val stack = new Stack[QName]


  // find start record, populate the MDRecord, go to the next record separator start and return
  def getRecord: MDRecord = {
    if (stack.isEmpty) findNextSeparator
    val startLocation = parser.getLocation
    while (!isEndRecordSeparator(parser) && parser.hasNext) {
      parser getEventType match {
        case XMLStreamConstants.START_ELEMENT =>
          stack.push(parser.getName)
          prettyPrintStack(stack)
        case XMLStreamConstants.END_ELEMENT => stack.pop
        case _ =>
      }
      parser.next
    }
    MDRecord(List(), MDRecordOffset(1, 3), findNextSeparator)
  }

  def findNextSeparator: Boolean = {
    while (!isStartRecordSeparator(parser) && parser.hasNext) {
      val eventType: Int = parser.getEventType
      eventType match {
        case XMLStreamConstants.START_ELEMENT => stack.push(parser.getName); prettyPrintStack(stack)
        case XMLStreamConstants.END_ELEMENT => stack.pop
        case _ =>
      }
      parser.next
    }
    isStartRecordSeparator(parser)
  }

  def isStartRecordSeparator(parser: XMLStreamReader): Boolean =
    if (parser.getEventType.equals(XMLStreamConstants.START_ELEMENT) && parser.getName.equals(recordSeparator)) true else false

  def isEndRecordSeparator(parser: XMLStreamReader): Boolean =
    {
      val eventType: Int = parser.getEventType
      if (eventType.equals(XMLStreamConstants.END_ELEMENT) && parser.getName.equals(recordSeparator)) true else false
    }
}

case class MDRecordOffset(startLine: Int, endLine: Int)
case class MDRecord(fields: List[MDField], recordOffset: MDRecordOffset, hasNextRecord: Boolean)
case class MDField(stack: Stack[QName], fieldName: QName, attributes: List[MDFieldAttributes])
case class MDFieldAttributes(name: QName, value: String)

object RecordParser {
  import com.ctc.wstx.stax.WstxInputFactory
  import javax.xml.stream.XMLInputFactory
  import java.io.File

  private val inFactory: XMLInputFactory = new WstxInputFactory

  def createParser(sourceFile: File, recordSeparator: QName): RecordParser = {
    val reader: XMLStreamReader = createStreamReader(sourceFile)
    new RecordParser(reader, recordSeparator)
  }

  def createStreamReader(sourceFile: File): XMLStreamReader = {
    import java.io.FileInputStream
    import javax.xml.transform.stream.StreamSource
    val source = new StreamSource(new FileInputStream(sourceFile), "UTF-8")
    inFactory createXMLStreamReader source
  }

  def createStreamReader(stringToSourceFilePath: String): XMLStreamReader = {
    val sourceFile = new File(stringToSourceFilePath)
    createStreamReader(sourceFile)
  }
}
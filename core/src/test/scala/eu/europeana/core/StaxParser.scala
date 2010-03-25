package eu.europeana.core

/**
 *
 * @author Sjoerd Siebinga <sjoerd.siebinga@gmail.com>
 * @since Mar 11, 2010 8:44:15 PM
 */

class StaxParser extends StackPrinter {

//  def runRecordParser {
//    import javax.xml.namespace.QName
//    import java.io.File
//    val file: File = new File("/Volumes/Data/Experiments/gitHub/europeana/trunk/core/src/test/sample-metadata/92001_Ag_EU_TELtreasures.xml")
//    val parser: RecordParser = RecordParser.createParser(file, new QName("record"))
//    var mdRecord = parser.getRecord;
//    var counter: Int = 0
//    while (mdRecord.hasNextRecord) {
//      counter += 1
//      println("found record" + counter)
//      mdRecord = parser.getRecord
//    }
//  }

  def runParser {
    import collection.mutable.Stack
    import javax.xml.stream.XMLStreamReader
    import javax.xml.namespace.QName
    val xml: XMLStreamReader = RecordParser.createStreamReader("/Volumes/Data/Experiments/gitHub/europeana/trunk/core/src/test/sample-metadata/92001_Ag_EU_TELtreasures.xml")
    val stack: Stack[QName] = new Stack

    while (xml.hasNext) {
      import javax.xml.stream.XMLStreamConstants
      xml.getEventType match {
        case XMLStreamConstants.START_DOCUMENT => println("start document")
        case XMLStreamConstants.START_ELEMENT =>
          val qName: QName = xml.getName
          stack.push(qName)
          println(xml.getLocalName + xml.getLocation.toString + qName)
        case XMLStreamConstants.END_ELEMENT =>
          stack.pop
        case XMLStreamConstants.CHARACTERS if !(xml hasText) => println(xml.getText + xml.getTextLength)
        case XMLStreamConstants.END_DOCUMENT => println("end document")
        case _ =>
      }
      println(prettyPrintStack(stack))
      xml.next
    }

  }


}
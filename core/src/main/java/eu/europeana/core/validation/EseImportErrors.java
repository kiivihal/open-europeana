package eu.europeana.core.validation;

/**
 * @author Sjoerd Siebinga <sjoerd.siebinga@gmail.com>
 * @since Feb 21, 2010 11:33:11 AM
 */
interface ESEImportErrors {
    // what kind of usefull information do we want to provide
    String getEuropeanaUri();

    ImportErrorType getImportErrorType();

    ImportWarningType getImportWarningType();

    int getLineNumber();

    String getXmlFieldName();

    String getXmlFieldValue();

    public enum ImportErrorType {

        // ESE styple
        DUPLICATE_EUROPEANA_URI("europeana:uri unique key found more then once. Most like caused by non-unique"),
        DUPLICATE_RECORD_IDENTIFIER,
        ILLEGAL_EUROPEANA_TYPE_ENTRY,

        // XML validation
        ILLEGAL_XML_STRUCTURE,
        ILLEGAL_UNICODE_CHARACTER;

        String message;

        ImportErrorType() {
        }

        ImportErrorType(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum ImportWarningType {
        MULTIPLE_EUROPEANA_TYPE_ENTRIES("More then one europeana:type entry. Only first one will be used");

        String message;

        ImportWarningType() {
        }

        ImportWarningType(String message) {
            this.message = message;
        }
    }

}

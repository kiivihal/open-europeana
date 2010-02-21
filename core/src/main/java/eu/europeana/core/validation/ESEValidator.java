package eu.europeana.core.validation;

import java.io.File;

/**
 * This is the validation interface for ESE records
 *
 * @author Sjoerd Siebinga <sjoerd.siebinga@gmail.com>
 * @since Feb 21, 2010 11:29:32 AM
 */
public interface ESEValidator {

    boolean ingest(File eseImportFile);

    boolean validateToXmlSchema(File eseImportFile);

    

}

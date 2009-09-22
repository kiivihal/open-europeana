package eu.europeana.database.domain;

/**
 * An enumeration describing the current state of a collection
 *
 * @author Gerald de Jong, Beautiful Code BV, <geralddejong@gmail.com>
 */

public enum CacheState {
    EMPTY,
    UNCACHED,
    QUEUED,
    CACHEING,
    CACHED,
}
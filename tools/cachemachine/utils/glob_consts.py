
REQUEST_UPLOAD_PATH = 'requests_new'



# Generic states
# ST_ = STate
ST_INITIALIZING = 0 # just created
ST_PENDING = 10 # waiting to be processed
ST_RETRIEVED = 15 # item has been retrieved
ST_PARSING = 20
ST_IDLE = 30
ST_COMPLETED = 40
ST_ABORTED = 90
ST_FAILED = 91
ST_TIMEOUT = 92

# Table specific states, allways > 99

# CSS  = CacheSourceState
CSS_RETRIEVING = 100

# RS_ = RequestState
RS_PRE_PARSING = 200
RS_IMG_CHECKS = 205
RS_INV_INP_FILE = 210

# IS_ = ItemState
IS_NO_RESPONSE = 300
IS_NO_URI = 301
IS_HTTP_ERROR = 302
IS_DOWNLOAD_FAILED = 310
IS_IDENTIFICATION = 320

# IT_ = ItemType
IT_UNKNOWN = 1001
IT_IMAGE = 1002
IT_PDF = 1003
IT_MOVIE = 1004
IT_AUDIO = 1005


CACHESOURCE_STATES = {
    ST_IDLE: 'idle',
    CSS_RETRIEVING: 'retrieving',

}

REQUEST_STATES = {
    ST_INITIALIZING: 'inializing',
    ST_PENDING: 'pending',  # Created not handled yet
    RS_PRE_PARSING: 'pre parsing',
    ST_PARSING: 'parsing',  # Creating related image items
    ST_COMPLETED: 'completed',  # everything done for the moment
    ST_ABORTED: 'aborted',
    ST_FAILED: 'failed', # failure in identification of item
    RS_INV_INP_FILE: 'invalid input file',
}


ITEM_STATES = {
    ST_INITIALIZING: 'inializing',
    ST_PENDING: 'pending',
    ST_PARSING: 'parsing',
    ST_RETRIEVED: 'retrieved',
    ST_COMPLETED: 'completed',
    ST_ABORTED: 'aborted',
    ST_TIMEOUT: 'timeout',
    ST_FAILED: 'failed', # failure in identification of item
    IS_NO_RESPONSE: 'no response',
    IS_NO_URI: 'no uri',
    IS_HTTP_ERROR: 'http error',
    IS_DOWNLOAD_FAILED: 'download failed',
    IS_IDENTIFICATION: 'identification',
}


ITEM_TYPES = {
    IT_UNKNOWN: 'unknown',
    IT_IMAGE: 'image',
    IT_PDF: 'PDF',
    IT_MOVIE: 'movie',
    IT_AUDIO: 'audio',
}


# PMR_ = Process Monitoring Role
PMR_REQ_HANDLER = 1000

PROC_ROLES = {
    PMR_REQ_HANDLER: 'request handler',
}

PM_STATES = {
    ST_INITIALIZING: 'initialzing',
}



#
# Module locks (utils/proc_ctrl.py)
#
LCK_ITEM = 'CacheItem'
LCK_CACHESOURCE = 'CacheSource'

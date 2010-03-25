package eu.europeana.core.database.incoming.cache;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Sjoerd Siebinga <sjoerd.siebinga@gmail.com>
 * @since Mar 11, 2010 11:57:47 AM
 */
public class ObjectCacheImplTest {

    @Test
    public void testCreateHash() throws Exception {
        ObjectCacheImpl cache = new ObjectCacheImpl();
        String hash = cache.createHash("http://musees.lausanne.ch/SGCM/SGCMImages/ImgHD/Vignette/FAP62866_2.JPG");
        Assert.assertEquals("98C9CE52E914E5FB78DF1FF979DCA364AA45117D9389AD29EF3E9140918B6BAA", hash);
    }
}

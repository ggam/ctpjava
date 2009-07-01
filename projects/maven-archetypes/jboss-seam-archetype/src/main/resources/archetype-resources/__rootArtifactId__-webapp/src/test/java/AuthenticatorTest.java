package ${package};

import org.jboss.seam.mock.SeamTest;
import org.jboss.seam.security.Credentials;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthenticatorTest extends SeamTest {

    @Test
    public void shouldAuthenticate() throws Exception {
        new ComponentTest() {
            @Override 
            protected void testComponents() throws Exception {
                // given
                Credentials cred = (Credentials) getValue("#{credentials}");
                Authenticator auth = (Authenticator) getValue("#{authenticator}");
                
                // when
                cred.setUsername("admin");
                boolean success = auth.authenticate();
                
                // then
                Assert.assertTrue(success);
            }
        }.run();
    }

}

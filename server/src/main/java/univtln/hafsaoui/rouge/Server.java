package univtln.hafsaoui.rouge;


import jakarta.ejb.Startup;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;

@Singleton
@Slf4j
@Startup
/**
 * Main class.
 *
 */
public class Server {
    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        log.warn("l'API rest est active <C-c> pour la fermer");
    }
}

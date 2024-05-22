package br.com.repassa.bootstrap;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@ApplicationScoped
public class Aplicacao {

    private static final String REPASSA_SYMBOL = "symbol.txt";

    private static final Logger LOG = LoggerFactory.getLogger(Aplicacao.class);

    void onStart(@Observes StartupEvent event) {
        this.logarSimbolo();
        LOG.info("Aplicacao sendo inicializada...");
    }

    void onStop(@Observes ShutdownEvent event) {
        LOG.info("Aplicacao sendo encerrada...");
    }

    private void logarSimbolo() {
        try(Stream<String> stream = Files.lines(Paths.get(getClass().getClassLoader().getResource(REPASSA_SYMBOL).toURI()))) {
            stream.forEach(LOG::info);
        } catch (Exception e) {/** NOOP */}
    }
}
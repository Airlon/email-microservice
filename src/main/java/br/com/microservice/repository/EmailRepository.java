package br.com.repassa.repository;

import br.com.repassa.entity.Email;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmailRepository implements PanacheRepository<Email> {

}

package univtln.hafsaoui.rouge.daos.collection;

import jakarta.enterprise.inject.Alternative;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import univtln.hafsaoui.rouge.daos.Dao;
import univtln.hafsaoui.rouge.daos.Dto;
import univtln.hafsaoui.rouge.daos.SimpleDto;
import univtln.hafsaoui.rouge.entities.interfaces.Client;

import javax.xml.validation.Validator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Alternative
public class ClientDAO implements Dao<Client>{

    private Validator validator ;
    /**
     * Private construcator of ClientDAO using collection
     *
     */
    private ClientDAO() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = (Validator) factory.getValidator();
    }


    /**
     * Static factory of ClientDAO, Take nothing and return
     * something useless
     *
     * @return dao
     */
    public static ClientDAO of() {
        return new ClientDAO();
    }

    @Override
    public Optional<Dto> get(String name) {
        return Optional.ofNullable(new SimpleDto(
                Dao.setClients.stream()
                .filter(client -> client.getName().equals(name))
                .toList().get(0).getName(),"")
        );
    }

    @Override
    public List<Dto> getAllOf(Integer page) {
        return Dao.setClients.stream().map(client ->
               (Dto) new SimpleDto(client.getName(),"")
        ).toList();
    }

    @Override
    public void save(Client client) {
        Dao.setClients.add(client);
    }

    @Override
    public void update(Client client) {
        Optional<Client> targetOfUpdate = Optional.ofNullable(Dao.setClients.stream()
                .filter(iClient -> iClient.getName().equals(client.getName()))
                .toList().get(0));
        if (targetOfUpdate.isPresent()){
            Dao.setClients.remove(targetOfUpdate.get());
            Dao.setClients.add(client);
        }

    }

    @Override
    public void delete(Client client) {
        Dao.setClients.remove(client);
    }

}

package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;

import java.util.List;
import java.util.Optional;

public interface SnackRepository {
    Optional<Snack> findById(long id);
    void update(Snack snack);
    List<Snack> findByBeginNaam(String beginNaam);

}

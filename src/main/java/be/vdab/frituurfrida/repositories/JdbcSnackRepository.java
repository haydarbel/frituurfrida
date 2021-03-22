package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public class JdbcSnackRepository implements SnackRepository {
    private final JdbcTemplate template;
    private final RowMapper<Snack> snackMapper =
            ((resultSet, i) -> new Snack(resultSet.getLong("id"),
                    resultSet.getString("naam"), resultSet.getBigDecimal("prijs")));

    public JdbcSnackRepository(JdbcTemplate template) {
        this.template = template;
    }


    @Override
    public Optional<Snack> findById(long id) {
        var sql = "select id,naam,prijs from snacks where id = ?";
        try {
            return Optional.of(template.queryForObject(sql, snackMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Snack snack) {
        var sql = "update snacks set naam = ?,prijs = ? where id = ?";
        if (template.update(sql, snack.getNaam(), snack.getPrijs(), snack.getId())==0) {
            throw new SnackNietGevondenException();
        }
    }

    @Override
    public List<Snack> findByBeginNaam(String beginNaam) {
        var sql = "select id,naam,prijs from snacks where naam like ? order by naam";
        return template.query(sql, snackMapper, beginNaam + '%');

    }
}

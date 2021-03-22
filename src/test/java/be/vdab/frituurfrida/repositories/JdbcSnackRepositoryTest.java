package be.vdab.frituurfrida.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import be.vdab.frituurfrida.domain.Snack;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

@JdbcTest
@Import(JdbcSnackRepository.class)
@Sql("/insertSnacks.sql")
class JdbcSnackRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String SNACKS = "snacks";
    private final JdbcSnackRepository repository;

    JdbcSnackRepositoryTest(JdbcSnackRepository repository) {
        this.repository = repository;
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanSnackPizza())
                .get().getNaam()).isEqualTo("test3");
    }

    @Test
    void update() {
        var id = idVanSnackPizza();
        var snack = new Snack(id, "test5", BigDecimal.TEN);
        repository.update(snack);
        assertThat(countRowsInTableWhere(SNACKS, "naam='test5' and id=" + id)).isOne();
    }

    @Test
    void findByBeginNaam2() {
        assertThat(repository.findByBeginNaam("te"))
                .extracting(Snack::getNaam).containsOnly("test", "test2", "test3").isSorted();
    }

    @Test
    void findByBeginNaam() {
        assertThat(repository.findByBeginNaam("t"))
                .hasSize(countRowsInTableWhere(SNACKS, "naam like 't%'"))
                .extracting(snack -> snack.getNaam().toLowerCase())
                .allSatisfy(naam -> assertThat(naam.startsWith("t")))
                .isSorted();
    }

    private long idVanSnackPizza() {
        return jdbcTemplate.queryForObject(
                "select id from snacks where naam='test3'", Long.class);
    }
}
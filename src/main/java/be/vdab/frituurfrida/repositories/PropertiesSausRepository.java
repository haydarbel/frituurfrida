package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.exceptions.SausRepositoryException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Component
@Qualifier("properties")
public class PropertiesSausRepository implements SausRepository{
    @Override
    public List<Saus> findAll()  {
        try {
            Stream<String> rows = Files.lines(Paths.get("sauzen.properties"));
            return rows
                    .map(this::maakSaus)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new SausRepositoryException("Fout bij lezen"+"sauzen.properties");
        }
    }

    private Saus maakSaus(String regel) {
        var nummerEnRest = regel.split(":");
        var nummer = nummerEnRest[0];
        var onderdelen =nummerEnRest[1].split(",");
        if (onderdelen.length < 1) {
            throw new SausRepositoryException("sauzen.csv" + ":" + regel + ":minder dan 2 onderdelen");
        }
        try {
            return new Saus(
                    Long.parseLong(nummer),
                    onderdelen[0],
                    Arrays.copyOfRange(onderdelen, 1, onderdelen.length)
            );
        } catch (NumberFormatException exception) {
            throw new SausRepositoryException("sauzen.csv" + ":" + regel + ":verkeerde id");
        }
    }
}

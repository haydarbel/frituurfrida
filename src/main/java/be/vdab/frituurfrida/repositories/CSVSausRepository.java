package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.exceptions.SausRepositoryException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Qualifier("CSV")
public class CSVSausRepository implements SausRepository{
    @Override
    public List<Saus> findAll()  {
        try {
            Stream<String> rows = Files.lines(Paths.get("sauzen.csv"));
            return rows
                    .map(this::maakSaus)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new SausRepositoryException("Fout bij lezen"+"sauzen.csv");
        }
    }

    private Saus maakSaus(String regel) {
        var onderdelen =regel.split(",");
        if (onderdelen.length < 2) {
            throw new SausRepositoryException("sauzen.csv" + ":" + regel + ":minder dan 2 onderdelen");
        }
        try {
            return new Saus(
                    Long.parseLong(onderdelen[0]),
                    onderdelen[1],
                    Arrays.copyOfRange(onderdelen, 2, onderdelen.length)
            );
        } catch (NumberFormatException exception) {
            throw new SausRepositoryException("sauzen.csv" + ":" + regel + ":verkeerde id");
        }
    }
}

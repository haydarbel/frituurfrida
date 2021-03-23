package be.vdab.frituurfrida.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BeginLetters {

    @NotBlank
    private final String letters;

    public BeginLetters(String letters) {
        this.letters = letters;
    }

    public String getLetters() {
        return letters;
    }
}

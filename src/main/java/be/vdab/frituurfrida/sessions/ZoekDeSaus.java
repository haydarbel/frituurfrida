package be.vdab.frituurfrida.sessions;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.services.SausService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

@Component
@SessionScope
public class ZoekDeSaus implements Serializable {
    private static final long serialVersionUID = 1L;
    private final SausService sausService;
    private  Saus sausTeRaden;

    public ZoekDeSaus(SausService sausService) {
        this.sausService = sausService;
        reset();
    }


    public void reset() {
        var indexMetFriet = ThreadLocalRandom.current()
                .nextInt(sausService.findAll().size());
        sausTeRaden = sausService.findAll().get(indexMetFriet);

    }
}

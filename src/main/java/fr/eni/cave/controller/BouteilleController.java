package fr.eni.cave.controller;

import fr.eni.cave.bll.impl.BouteilleServiceImpl;
import fr.eni.cave.bo.vin.Bouteille;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/caveavin/bouteilles")
@RestController
public class BouteilleController {
    private final BouteilleServiceImpl bouteilleServiceImpl;

    public BouteilleController(BouteilleServiceImpl bouteilleServiceImpl) {
        this.bouteilleServiceImpl = bouteilleServiceImpl;
    }

    @GetMapping
    ResponseEntity<?> list() {
        List<Bouteille> bouteilles = bouteilleServiceImpl.chargerToutesBouteilles();

        if(bouteilles.isEmpty()){
            return ResponseEntity
                    .noContent().build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bouteilles);
    }
}

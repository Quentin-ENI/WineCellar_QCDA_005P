package fr.eni.cave.controller;

import fr.eni.cave.bll.BouteilleService;
import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.dto.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/caveavin/bouteilles")
@RestController
public class BouteilleController {
    private final BouteilleService bouteilleService;

    public BouteilleController(BouteilleService bouteilleService) {
        this.bouteilleService = bouteilleService;
    }

    @GetMapping
    ResponseEntity<ResponseApi<List<Bouteille>>> list() {
        List<Bouteille> bouteilles = bouteilleService.chargerToutesBouteilles();

        if(bouteilles.isEmpty()){
            return ResponseEntity
                    .noContent().build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseApi.<List<Bouteille>>builder()
                                .statusCode(HttpStatus.OK.value())
                                .message(HttpStatus.OK.name())
                                .data(bouteilles)
                                .build()
                );
    }
}

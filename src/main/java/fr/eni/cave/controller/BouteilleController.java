package fr.eni.cave.controller;

import fr.eni.cave.bll.BouteilleService;
import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.dto.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<Bouteille>> getById(@PathVariable String id) {

        try {
            int idBouteille = Integer.parseInt(id);

            Bouteille bouteille = bouteilleService.chargerBouteilleParId(idBouteille);

            return ResponseEntity.ok(
                    ResponseApi.<Bouteille>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatus.OK.name())
                            .data(bouteille)
                            .build()
            );

        } catch (NumberFormatException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(
                            ResponseApi.<Bouteille>builder()
                                    .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                                    .message("Identifiant invalide")
                                    .data(null)
                                    .build()
                    );
        }
    }

    @GetMapping("/region/{id}")
    public ResponseEntity<ResponseApi<List<Bouteille>>> getByRegion(@PathVariable String id) {

        try {
            int idRegion = Integer.parseInt(id);

            List<Bouteille> bouteilles =
                    bouteilleService.chargerBouteillesParRegion(idRegion);

            if (bouteilles.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(
                    ResponseApi.<List<Bouteille>>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatus.OK.name())
                            .data(bouteilles)
                            .build()
            );

        } catch (NumberFormatException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(
                            ResponseApi.<List<Bouteille>>builder()
                                    .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                                    .message("Identifiant région invalide")
                                    .data(null)
                                    .build()
                    );
        }
    }

    @GetMapping("/couleur/{id}")
    public ResponseEntity<ResponseApi<List<Bouteille>>> getByCouleur(@PathVariable String id) {

        try {
            int idCouleur = Integer.parseInt(id);

            List<Bouteille> bouteilles =
                    bouteilleService.chargerBouteillesParCouleur(idCouleur);

            if (bouteilles.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(
                    ResponseApi.<List<Bouteille>>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message(HttpStatus.OK.name())
                            .data(bouteilles)
                            .build()
            );

        } catch (NumberFormatException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(
                            ResponseApi.<List<Bouteille>>builder()
                                    .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                                    .message("Identifiant couleur invalidee")
                                    .data(null)
                                    .build()
                    );
        }
    }

}

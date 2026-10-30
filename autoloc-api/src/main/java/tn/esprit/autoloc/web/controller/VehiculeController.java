
package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicules")
public class VehiculeController {

    private final IVehiculeService iVehiculeService;

    @PostMapping("/add")
    public Vehicule createVehicule(@RequestBody Vehicule vehicule) {
        return iVehiculeService.addVehicule(vehicule);
    }
    @GetMapping("{id}")
    public Vehicule getVehicule(@PathVariable Long id) {
        return iVehiculeService.getVehicule(id);
    }

}

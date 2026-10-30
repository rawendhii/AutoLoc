package tn.esprit.autoloc.service.Impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@Service
@RequiredArgsConstructor

public class VehiculeService implements IVehiculeService {

    private final IVehiculeRepository iVehiculeRepository;


    @Override
    public Vehicule addVehicule(Vehicule vehicule) {
        return iVehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule getVehicule(Long id) {
        return iVehiculeRepository.findById(id).orElseThrow(()->new EntityNotFoundException("vehicule not found"));
    }

    @Override
    public Vehicule updateVehicule(Vehicule vehicule) {
        return iVehiculeRepository.save(vehicule);
    }

    @Override
    public void deleteVehicule(Long id) {

        iVehiculeRepository.deleteById(id);

    }

    @Override
    public List<Vehicule> getAll() {
        return iVehiculeRepository.findAll();
    }
}

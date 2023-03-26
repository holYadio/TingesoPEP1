package TINGESO.Evaluacion1.Services;

import TINGESO.Evaluacion1.Repositories.AcopioRepository;
import TINGESO.Evaluacion1.Entities.AcopioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;


@Service
public class AcopioService {
    @Autowired
    AcopioRepository acopioRepository;

    public AcopioEntity guardarAcopio(Date fecha, String turno, String proveedor, int cantidad) {
        AcopioEntity acopio = new AcopioEntity();
        acopio.setFecha(fecha);
        acopio.setTurno(turno);
        acopio.setProveedor(proveedor);
        acopio.setCantidad(cantidad);
        return acopioRepository.save(acopio);
    }

    public AcopioEntity obtenerAcopioPorProveedor(String proveedor) {
        return acopioRepository.findByProveedor(proveedor);
    }

    public ArrayList<AcopioEntity> obtenerAcopios() {
        return (ArrayList<AcopioEntity>) acopioRepository.findAll();
    }
}

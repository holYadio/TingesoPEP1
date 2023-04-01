package TINGESO.Evaluacion1.Services;

import TINGESO.Evaluacion1.Entities.SubirDatoLaboratorioEntity;
import TINGESO.Evaluacion1.Repositories.SubirDatoProveedorRepository;
import TINGESO.Evaluacion1.Entities.SubirDatoProveedorEntity;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class SubirDatoProveedorService {
    @Autowired
    SubirDatoProveedorRepository subirDatoProveedorRepository;

    private final Logger logg = LoggerFactory.getLogger(SubirDatoProveedorService.class);

    public ArrayList<SubirDatoProveedorEntity> obtenerSubirDatosProveedores() {
        return (ArrayList<SubirDatoProveedorEntity>) subirDatoProveedorRepository.findAll();
    }

    public SubirDatoProveedorEntity obtenerSubirDatoProveedorPorProveedor(String proveedor) {
        return subirDatoProveedorRepository.findByProveedor(proveedor);
    }

    @Generated
    public String guardarDatosProveedor(MultipartFile file){
        String filename = file.getOriginalFilename();
        if (filename != null) {
            if (!file.isEmpty()){
                try {
                    byte [] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo de Acopio subido correctamente");
                }
                catch (IOException e){
                    logg.error("Error", e);
                }
            }
            return "Archivo de Acopio guardado";
        }
        else {
            return "No se guardo el Archivo de Acopio";
        }
    }

    @Generated
    public void leerCsvProveedor(String Direccion){
        String texto = "";
        BufferedReader bf = null;
        try{
            bf = new BufferedReader(new FileReader(Direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while ((bfRead = bf.readLine()) != null) {
                if (count == 1) {
                    count = 0;
                }
                else {
                    guardarDatoProveedorDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], bfRead.split(";")[3]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo de Acopio leido correctamente");
        }
        catch (Exception e){
            System.out.println("Error al leer el archivo de Acopio");
        }
        finally {
            try{
                if (null != bf){
                    bf.close();
                }
            }
            catch (IOException e){
                logg.error("Error", e);
            }
        }
    }

    public void guardarDatoProveedor(SubirDatoProveedorEntity dato) {
        subirDatoProveedorRepository.save(dato);
    }

    public void guardarDatoProveedorDB(String fecha, String turno, String proveedor, String kls_leche){
        SubirDatoProveedorEntity newDato = new SubirDatoProveedorEntity();
        newDato.setFecha(fecha);
        newDato.setTurno(turno);
        newDato.setProveedor(proveedor);
        newDato.setKls_leche(kls_leche);
        guardarDatoProveedor(newDato);
    }
}

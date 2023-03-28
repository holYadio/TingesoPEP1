package TINGESO.Evaluacion1.Services;

import TINGESO.Evaluacion1.Entities.ProveedorEntity;
import TINGESO.Evaluacion1.Entities.SubirDatoLaboratorioEntity;
import TINGESO.Evaluacion1.Repositories.SubirDatoLaboratorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import lombok.Generated;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class SubirDatoLaboratorioService {
    @Autowired
    SubirDatoLaboratorioRepository subirDatoLaboratorioRepository;

    private final Logger logg = LoggerFactory.getLogger(SubirDatoLaboratorioService.class);

    public ArrayList<SubirDatoLaboratorioEntity> obtenerSubirDatosLaboratorio() {
        return (ArrayList<SubirDatoLaboratorioEntity>) subirDatoLaboratorioRepository.findAll();
    }

    public SubirDatoLaboratorioEntity obtenerSubirDatoLaboratorioPorProveedor(String proveedor) {
        return subirDatoLaboratorioRepository.findByProveedor(proveedor);
    }

    @Generated
    public String guardarDatosLab(MultipartFile file){
        String fileName = file.getOriginalFilename();
        if (fileName != null){
            if (!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo de analisis del Laboratiorio guardado");
                }
                catch (IOException e){
                    logg.error("Error", e);
                }
            }
            return "Archivo de analisis del Laboratiorio guardado";
        }
        else {
            return "No se guardo el Archivo de analisis del Laboratiorio";
        }
    }

    @Generated
    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        subirDatoLaboratorioRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarDatoDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo de analisis del Laboratiorio leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo de analisis del Laboratiorio");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }

    public void guardarDato(SubirDatoLaboratorioEntity data){
        subirDatoLaboratorioRepository.save(data);
    }

    public void guardarDatoDB(String proveedor, String porcentaje_grasa, String porcentaje_solido_total){
        SubirDatoLaboratorioEntity newData = new SubirDatoLaboratorioEntity();
        newData.setProveedor(proveedor);
        newData.setPorcentaje_grasa(porcentaje_grasa);
        newData.setPorcentaje_solido_total(porcentaje_solido_total);
        guardarDato(newData);
    }
}

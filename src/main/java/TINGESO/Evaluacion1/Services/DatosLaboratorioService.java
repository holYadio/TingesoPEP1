package TINGESO.Evaluacion1.Services;

import TINGESO.Evaluacion1.Entities.DatosLaboratorioEntity;
import TINGESO.Evaluacion1.Repositories.DatosLaboratorioRepository;
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
public class DatosLaboratorioService {
    @Autowired
    DatosLaboratorioRepository datosLaboratorioRepository;

    private final Logger logg = LoggerFactory.getLogger(DatosLaboratorioService.class);

    public ArrayList<DatosLaboratorioEntity> obtenerDatosLaboratorio() {
        return (ArrayList<DatosLaboratorioEntity>) datosLaboratorioRepository.findAll();
    }

    public ArrayList<DatosLaboratorioEntity> obtenerDatosLaboratorioPorProveedor(String proveedor) {
        return datosLaboratorioRepository.findByProveedor(proveedor);
    }

    public DatosLaboratorioEntity obtenerDatosLaboratorioPorProveedorYQuincena(String proveedor, String quincena) {
        return datosLaboratorioRepository.findByProveedorAndQuincena(proveedor, quincena);
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
    public void leerCsv(String direccion, String quincena){
        String texto = "";
        BufferedReader bf = null;
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
                    guardarDatoDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2],quincena);
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

    public void guardarDato(DatosLaboratorioEntity data){
        datosLaboratorioRepository.save(data);
    }

    public void guardarDatoDB(String proveedor, String porcentaje_grasa, String porcentaje_solido_total, String quincena){
        DatosLaboratorioEntity newData = new DatosLaboratorioEntity();
        newData.setProveedor(proveedor);
        newData.setPorcentaje_grasa(porcentaje_grasa);
        newData.setPorcentaje_solido_total(porcentaje_solido_total);
        newData.setQuincena(quincena);
        guardarDato(newData);
    }



}
package tingeso.monolitico.services;

import tingeso.monolitico.entities.DatosLaboratorioEntity;
import tingeso.monolitico.repositories.DatosLaboratorioRepository;
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
import java.util.List;

@Service
public class DatosLaboratorioService {
    @Autowired
    DatosLaboratorioRepository datosLaboratorioRepository;


    private final Logger logg = LoggerFactory.getLogger(DatosLaboratorioService.class);

    /**
     * Funcion para obtener todos los datos de los laboratorios
     * @return
     */
    public List<DatosLaboratorioEntity> obtenerDatosLaboratorio() {
        return datosLaboratorioRepository.findAll();
    }

    /**
     * Funcion para obtener los datos de los laboratorios asociados a un proveedor
     * @param proveedor: String con el nombre del proveedor
     * @return
     */
    public List<DatosLaboratorioEntity> obtenerDatosLaboratorioPorProveedor(String proveedor) {
        return datosLaboratorioRepository.findByProveedor(proveedor);
    }

    /**
     * Funcion para obtener los datos de los laboratorios asociados a una quincena
     * @param quincena: String con la quincena
     * @param proveedor: String con el codigo del proveedor
     * @return
     */
    public DatosLaboratorioEntity obtenerDatosLaboratorioPorProveedorYQuincena(String proveedor, String quincena) {
        return datosLaboratorioRepository.findByProveedorAndQuincena(proveedor, quincena);
    }

    /**
     * Funcion guardar los datos de los laboratorios
     * @param file: String con la quincena
     * @return
     */
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

    /**
     * Funcion para leer los datos de los laboratorios
     * @param direccion: String con la direccion del archivo
     * @param quincena: String con la quincena
     */
    @Generated
    public void leerCsv(String direccion, String quincena){
        BufferedReader bf = null;
        try{
            bf = new BufferedReader(new FileReader(direccion));
            StringBuilder temp = new StringBuilder();
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarDatoDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2],quincena);
                    temp.append("\n").append(bfRead);
                }
            }
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

    /**
     * Funcion para guardar los datos de los laboratorios en la base de datos
     * @param quincena: String con la quincena
     * @param proveedor: String con el nombre del proveedor
     * @param porcentajeGrasa: String con el porcentaje de grasa
     * @param porcentajeSolidoTotal: String con el porcentaje de solidos totales
     * @return
     */
    public void guardarDatoDB(String proveedor, String porcentajeGrasa, String porcentajeSolidoTotal, String quincena){
        DatosLaboratorioEntity newData = new DatosLaboratorioEntity();
        newData.setProveedor(proveedor);
        newData.setPorcentajeGrasa(porcentajeGrasa);
        newData.setPorcentajeSolidoTotal(porcentajeSolidoTotal);
        newData.setQuincena(quincena);
        datosLaboratorioRepository.save(newData);
    }

    /**
     * Funcion para obtener la variacion de solidos totales
     * @param quincenaActual: String con la quincena
     * @param proveedor: String con el codigo del proveedor
     * @param porcentajeSolidos: String con el porcentaje de solidos
     * @return
     */
    public double getVariacionSolidoTotal(String quincenaActual,
                                             String proveedor,
                                             String porcentajeSolidos) {
        double solidosAnterior;
        double solidosActual = Integer.parseInt(porcentajeSolidos);
        String quincenaAnterior = quincenaAnterior(quincenaActual);
        String porcentajeSolidosAnterior;
        try{
            porcentajeSolidosAnterior = obtenerDatosLaboratorioPorProveedorYQuincena(
                    proveedor,
                    quincenaAnterior).getPorcentajeSolidoTotal();

            solidosAnterior = Integer.parseInt(porcentajeSolidosAnterior);
        }catch (Exception e){
            solidosAnterior = solidosActual;
        }
        double variacion = solidosAnterior - solidosActual;
        if (variacion < 0) {
            variacion = 0;
        }
        return variacion;
    }

    /**
     * Funcion para obtener la variacion de grasa
     * @param quincenaActual
     * @param proveedor
     * @param porcentajeGrasa
     * @return
     */
    public double getVariacionGrasa(String quincenaActual,
                                      String proveedor,
                                      String porcentajeGrasa) {
        double grasaAnterior;
        double grasaActual = Integer.parseInt(porcentajeGrasa);
        String quincenaAnterior = this.quincenaAnterior(quincenaActual);
        String porcentajeGrasaAnterior;
        try{
            porcentajeGrasaAnterior = this.obtenerDatosLaboratorioPorProveedorYQuincena(
                    proveedor,
                    quincenaAnterior).getPorcentajeGrasa();
            grasaAnterior = Integer.parseInt(porcentajeGrasaAnterior);
        }catch (Exception e){
            grasaAnterior = grasaActual;
        }
        double variacion = grasaAnterior - grasaActual;
        if (variacion < 0) {
            variacion = 0;
        }
        return variacion;
    }

    /**
     * Funcion para obtener la quincena anterior
     * @param quincena
     * @return
     */
    public String quincenaAnterior(String quincena){
        String quincenaAnterior = "";
        String anioActual= quincena.split("/")[0];
        String mesActual= quincena.split("/")[1];
        String qActual= quincena.split("/")[2];
        if(qActual.equals("Q1")){
            if(mesActual.equals("01")){
                anioActual = Integer.toString(Integer.parseInt(anioActual) - 1);
                mesActual = "12";
            }
            else{
                mesActual = Integer.toString(Integer.parseInt(mesActual));
                if ((mesActual.length() == 1) || (mesActual.equals("10"))) {
                    mesActual = Integer.toString(Integer.parseInt(mesActual) - 1);
                    mesActual = "0" + mesActual;
                }else{
                    mesActual = Integer.toString(Integer.parseInt(mesActual) - 1);
                }
            }
            quincenaAnterior = anioActual + "/" + mesActual + "/" + "Q2";
        }
        else if(qActual.equals("Q2")){
            quincenaAnterior = anioActual + "/" + mesActual + "/" + "Q1";
        }
        return quincenaAnterior;
    }


}

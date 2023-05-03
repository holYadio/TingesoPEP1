package tingeso.monolitico.services;

import tingeso.monolitico.entities.DatosAcopioEntity;
import tingeso.monolitico.repositories.DatosAcopioRepository;
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
import java.util.List;

@Service
public class DatosAcopioService {
    @Autowired
    DatosAcopioRepository datosAcopioRepository;

    @Autowired
    DatosLaboratorioService datosLaboratorioService;

    private final Logger logg = LoggerFactory.getLogger(DatosAcopioService.class);

    public List<DatosAcopioEntity> obtenerDatosAcopio() {
        return datosAcopioRepository.findAll();
    }

    public List<DatosAcopioEntity> obtenerDatosAcopioPorProveedor(String proveedor) {
        return datosAcopioRepository.findByProveedor(proveedor);
    }

    /* Funcion para obtener los acopios asociados a una quincena en especifico
     * @param Quincena: String con el formato "AAAA/MM/Q1" o "AAAA/MM/Q2"
     * @return ArrayList<DatosAcopioEntity> con los datos de los acopios asociados a la quincena
     */
    public List<DatosAcopioEntity> obtenerDatosAcopioPorQuincenayProveedor(String quincena, String proveedor) {
        List<DatosAcopioEntity> datosAcopio = datosAcopioRepository.findAll();
        List<DatosAcopioEntity> datosAcopioPorQuincena = new ArrayList<>();
        int anioQuincena = Integer.parseInt(quincena.split("/")[0]);
        int mesQuincena = Integer.parseInt(quincena.split("/")[1]);
        String numQuincena = quincena.split("/")[2];
        for (DatosAcopioEntity datosAcopioEntity : datosAcopio) {
            String fechaDatoAcopio = datosAcopioEntity.getFecha();
            int anio = Integer.parseInt(fechaDatoAcopio.split("/")[0]);
            int mes = Integer.parseInt(fechaDatoAcopio.split("/")[1]);
            int dia = Integer.parseInt(fechaDatoAcopio.split("/")[2]);
            if (numQuincena.equals("Q1")) {
                if (anio == anioQuincena && mes == mesQuincena && (dia <= 15) && (dia >= 1) && datosAcopioEntity.getProveedor().equals(proveedor)) {
                    datosAcopioPorQuincena.add(datosAcopioEntity);
                }
            }else if (numQuincena.equals("Q2") && (anio == anioQuincena && mes == mesQuincena && (dia <= 31) && (dia >= 16) && datosAcopioEntity.getProveedor().equals(proveedor))) {
                    datosAcopioPorQuincena.add(datosAcopioEntity);

            }
        }
        return datosAcopioPorQuincena;
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
                    logg.error("Error",
                            e);
                }
            }
            return "Archivo de Acopio guardado";
        }
        else {
            return "No se guardo el Archivo de Acopio";
        }
    }

    @Generated
    public void leerCsvProveedor(String direccion){
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(direccion));
            StringBuilder temp = new StringBuilder();
            String bfRead;
            int count = 1;
            while ((bfRead = br.readLine()) != null) {
                if (count == 1) {
                    count = 0;
                }
                else {
                    guardarDatoProveedorDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], bfRead.split(";")[3]);
                    temp.append("\n").append(bfRead);
                }
            }
            logg.info("Archivo de Acopio leido correctamente");
        }
        catch (Exception e){
            logg.error("Error al leer el archivo de Acopio",e);
        }
        finally {
            try{
                if (null != br){
                    br.close();
                }
            }
            catch (IOException e){
                logg.error("Error", e);
            }
        }
    }

    public void guardarDatoProveedorDB(String fecha, String turno, String proveedor, String klsLeche){
        DatosAcopioEntity newDato = new DatosAcopioEntity();
        newDato.setFecha(fecha);
        newDato.setTurno(turno);
        newDato.setProveedor(proveedor);
        newDato.setKlsLeche(klsLeche);
        datosAcopioRepository.save(newDato);
    }



    public double klsTotalLeche(List<DatosAcopioEntity> datosAcopio){
        double klsTotalLeche = 0;
        for (DatosAcopioEntity datos : datosAcopio) {
            klsTotalLeche += Integer.parseInt(datos.getKlsLeche());
        }
        return klsTotalLeche;
    }


    public double diasEnvioLeche(List<DatosAcopioEntity> datosAcopio){
        double diasEnvioAcopio = 0;
        int i = 0;
        while (i < (datosAcopio.size())) {
            if(i < (datosAcopio.size() - 1)) {
                if ((datosAcopio.get(i).getFecha().equals(datosAcopio.get(i + 1).getFecha())) &&
                        !datosAcopio.get(i).getTurno().equals(datosAcopio.get(i + 1).getTurno())) {
                    i++;
                    diasEnvioAcopio++;
                }else if ((!datosAcopio.get(i).getFecha().equals(datosAcopio.get(i + 1).getFecha()))) {
                    diasEnvioAcopio++;
                }
            } else{
                try{
                    if (((datosAcopio.get(i).getFecha().equals(datosAcopio.get(i - 1).getFecha())) &&
                            !datosAcopio.get(i).getTurno().equals(datosAcopio.get(i - 1).getTurno())) || (!datosAcopio.get(i).getFecha().equals(datosAcopio.get(i - 1).getFecha()))) {
                        diasEnvioAcopio++;
                    }
                } catch (Exception e) {
                    logg.error("Error", e);
                }
            }
            i++;
        }
        if(datosAcopio.size() == 1){
            diasEnvioAcopio++;
        }
        return diasEnvioAcopio;
    }

    public double getVariacionLeche(String quincena, String codigoProveedor, double klsTotalLeche) {
        double klsLecheAnterior;
        String quincenaAnterior = datosLaboratorioService.quincenaAnterior(quincena);
        if (quincenaAnterior == null) {
            klsLecheAnterior = klsTotalLeche;
        }else{
            List<DatosAcopioEntity> datosAcopioQuincena = this.obtenerDatosAcopioPorQuincenayProveedor(
                    quincenaAnterior, codigoProveedor);
            klsLecheAnterior = this.klsTotalLeche(datosAcopioQuincena);
        }
        double variacion = klsLecheAnterior - klsTotalLeche;
        if (variacion <= 0) {
            variacion = 0;
        }
        return variacion;
    }
}

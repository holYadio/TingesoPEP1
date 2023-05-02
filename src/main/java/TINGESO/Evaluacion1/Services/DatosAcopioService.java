package TINGESO.Evaluacion1.Services;

import TINGESO.Evaluacion1.Entities.DatosAcopioEntity;
import TINGESO.Evaluacion1.Repositories.DatosAcopioRepository;
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
public class DatosAcopioService {
    @Autowired
    DatosAcopioRepository datosAcopioRepository;

    @Autowired
    DatosLaboratorioService datosLaboratorioService;

    private final Logger logg = LoggerFactory.getLogger(DatosAcopioService.class);

    public ArrayList<DatosAcopioEntity> obtenerDatosAcopio() {
        return (ArrayList<DatosAcopioEntity>) datosAcopioRepository.findAll();
    }

    public ArrayList<DatosAcopioEntity> obtenerDatosAcopioPorProveedor(String proveedor) {
        return (ArrayList<DatosAcopioEntity>) datosAcopioRepository.findByProveedor(proveedor);
    }

    /* Funcion para obtener los acopios asociados a una quincena en especifico
     * @param Quincena: String con el formato "AAAA/MM/Q1" o "AAAA/MM/Q2"
     * @return ArrayList<DatosAcopioEntity> con los datos de los acopios asociados a la quincena
     */
    public ArrayList<DatosAcopioEntity> obtenerDatosAcopioPorQuincenayProveedor(String Quincena, String proveedor) {
        ArrayList<DatosAcopioEntity> datosAcopio = (ArrayList<DatosAcopioEntity>) datosAcopioRepository.findAll();
        ArrayList<DatosAcopioEntity> datosAcopioPorQuincena = new ArrayList<>();
        int anioQuincena = Integer.parseInt(Quincena.split("/")[0]);
        int mesQuincena = Integer.parseInt(Quincena.split("/")[1]);
        String numQuincena = Quincena.split("/")[2];
        for (DatosAcopioEntity datosAcopioEntity : datosAcopio) {
            String fechaDatoAcopio = datosAcopioEntity.getFecha();
            int anio = Integer.parseInt(fechaDatoAcopio.split("/")[0]);
            int mes = Integer.parseInt(fechaDatoAcopio.split("/")[1]);
            int dia = Integer.parseInt(fechaDatoAcopio.split("/")[2]);
            if (numQuincena.equals("Q1")) {
                if (anio == anioQuincena && mes == mesQuincena && (dia <= 15) && (dia >= 1) && datosAcopioEntity.getProveedor().equals(proveedor)) {
                    datosAcopioPorQuincena.add(datosAcopioEntity);
                }
            }else if (numQuincena.equals("Q2")) {
                if (anio == anioQuincena && mes == mesQuincena && (dia <= 31) && (dia >= 16) && datosAcopioEntity.getProveedor().equals(proveedor)) {
                    datosAcopioPorQuincena.add(datosAcopioEntity);
                }
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

    public void guardarDatoProveedorDB(String fecha, String turno, String proveedor, String kls_leche){
        DatosAcopioEntity newDato = new DatosAcopioEntity();
        newDato.setFecha(fecha);
        newDato.setTurno(turno);
        newDato.setProveedor(proveedor);
        newDato.setKls_leche(kls_leche);
        datosAcopioRepository.save(newDato);
    }



    public double KlsTotalLeche(ArrayList<DatosAcopioEntity> datosAcopio){
        double KlsTotalLeche = 0;
        for (DatosAcopioEntity datos : datosAcopio) {
            KlsTotalLeche += Integer.parseInt(datos.getKls_leche());
        }
        return KlsTotalLeche;
    }


    public double diasEnvioLeche(ArrayList<DatosAcopioEntity> datosAcopio){
        double diasEnvioAcopio = 0;
        for (int i = 0; i < (datosAcopio.size()); i++) {
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
                    if ((datosAcopio.get(i).getFecha().equals(datosAcopio.get(i - 1).getFecha())) &&
                            !datosAcopio.get(i).getTurno().equals(datosAcopio.get(i - 1).getTurno())) {
                        diasEnvioAcopio++;
                    } else if ((!datosAcopio.get(i).getFecha().equals(datosAcopio.get(i - 1).getFecha()))) {
                        diasEnvioAcopio++;
                    }
                } catch (Exception e) {
                    logg.error("Error", e);
                }
            }
        }
        if(datosAcopio.size() == 1){
            diasEnvioAcopio++;
        }
        return diasEnvioAcopio;
    }

    public double getVariacion_leche(String quincena, String codigoProveedor, double klsTotalLeche) {
        double klsLecheAnterior;
        String quincenaAnterior = datosLaboratorioService.quincenaAnterior(quincena);
        if (quincenaAnterior == null) {
            klsLecheAnterior = klsTotalLeche;
        }else{
            ArrayList<DatosAcopioEntity> datosAcopioQuincena = this.obtenerDatosAcopioPorQuincenayProveedor(
                    quincenaAnterior, codigoProveedor);
            klsLecheAnterior = this.KlsTotalLeche(datosAcopioQuincena);
        }
        double variacion = klsLecheAnterior - klsTotalLeche;
        if (variacion <= 0) {
            variacion = 0;
        }
        return variacion;
    }
}

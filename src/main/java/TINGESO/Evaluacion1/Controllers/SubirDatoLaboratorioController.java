package TINGESO.Evaluacion1.Controllers;

import TINGESO.Evaluacion1.Entities.SubirDatoLaboratorioEntity;
import TINGESO.Evaluacion1.Services.SubirDatoLaboratorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class SubirDatoLaboratorioController {
    @Autowired
    SubirDatoLaboratorioService subirDatoLaboratorioService;

    @GetMapping("/SubirDatosLaboratorio")
    public String fileUpload() {
        return "SubirDatosLaboratorio";
    }
    @GetMapping("/SubirDatosLaboratorio")
    public String fileUpload(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        subirDatoLaboratorioService.guardarDatosLab(file);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo subido correctamente");
        subirDatoLaboratorioService.leerCsv("lab.csv");
        return "redirect:/SubirDatosLaboratorio";
    }

    @GetMapping("/obtenerDatosLab")
    public String obtenerDatosLab(Model model){
        ArrayList<SubirDatoLaboratorioEntity> datosLab = subirDatoLaboratorioService.obtenerSubirDatosLaboratorio();
        model.addAttribute("datosLab", datosLab);
        return "obtenerDatosLab";
    }
}

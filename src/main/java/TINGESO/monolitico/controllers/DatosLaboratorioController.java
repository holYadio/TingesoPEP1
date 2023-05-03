package tingeso.monolitico.controllers;

import tingeso.monolitico.entities.DatosLaboratorioEntity;
import tingeso.monolitico.services.DatosLaboratorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping
public class DatosLaboratorioController {
    @Autowired
    DatosLaboratorioService datosLaboratorioService;

    @GetMapping("/SubirDatosLaboratorio")
    public String fileUpload() {
        return "importarLab";
    }
    @PostMapping("/SubirDatosLaboratorio")
    public String fileUpload(@RequestParam("file")MultipartFile file,
                             @RequestParam("Quincena")String Quincena ,
                             RedirectAttributes redirectAttributes){
        datosLaboratorioService.guardarDatosLab(file);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo subido correctamente");
        datosLaboratorioService.leerCsv(file.getOriginalFilename(), Quincena);
        return "redirect:/SubirDatosLaboratorio";
    }

    @GetMapping("/obtenerDatosLab")
    public String obtenerDatosLab(Model model){
        List<DatosLaboratorioEntity> datosLab = datosLaboratorioService.obtenerDatosLaboratorio();
        model.addAttribute("datosLab", datosLab);
        return "informacionLab";
    }
}

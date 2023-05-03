package TINGESO.monolitico.Controllers;


import TINGESO.monolitico.Entities.DatosAcopioEntity;
import TINGESO.monolitico.Services.DatosAcopioService;
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
public class DatosAcopioController {
    @Autowired
    DatosAcopioService datosAcopioService;

    @GetMapping("/SubirDatosProveedor")
    public String fileUpload() {
        return "importarAcopio";
    }

    @PostMapping("/SubirDatosProveedor")
    public String fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        datosAcopioService.guardarDatosProveedor(file);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo subido correctamente");
        datosAcopioService.leerCsvProveedor(file.getOriginalFilename());
        return "redirect:/SubirDatosProveedor";
    }

    @GetMapping("/obtenerDatosProveedor")
    public String obtenerDatosProveedor(Model model){
        List<DatosAcopioEntity> datosProveedor = datosAcopioService.obtenerDatosAcopio();
        model.addAttribute("datosProveedor", datosProveedor);
        return "informacionAcopio";
    }
}

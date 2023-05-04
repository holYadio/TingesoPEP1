package tingeso.monolitico.controllers;

import tingeso.monolitico.services.ProveedorService;
import tingeso.monolitico.entities.ProveedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class ProveedorController {
    @Autowired
    ProveedorService proveedorService;

    @GetMapping("/obtenerProveedores")
    public String listar(Model model) {
        List<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
        model.addAttribute("proveedores", proveedores);
        return "listadoProveedores";
    }


    @GetMapping("/nuevoProveedor")
    public String nuevoProveedor() {
        return "nuevoProveedor";
    }

    @PostMapping("/nuevoProveedor")
    public String guardarProveedor(@RequestParam("codigo") String codigo,
                                   @RequestParam("nombre") String nombre,
                                   @RequestParam("categoria") String categoria,
                                   @RequestParam("retencion") String retencion) {
        proveedorService.guardarProveedor(codigo, nombre, categoria, retencion);
        return "redirect:/nuevoProveedor";
    }
}

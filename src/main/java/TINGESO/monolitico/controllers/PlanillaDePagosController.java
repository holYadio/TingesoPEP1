package tingeso.monolitico.controllers;

import tingeso.monolitico.entities.PagoEntity;
import tingeso.monolitico.services.PlanillaDePagos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping
public class PlanillaDePagosController {
    @Autowired
    PlanillaDePagos planillaDePagos;

    @GetMapping("/planillaDePagos")
    public String planillaDePagos(Model model) {
        planillaDePagos.deleteAll();
        planillaDePagos.calcularPagoFinal();
        List<PagoEntity> pagos = planillaDePagos.obtenerPagos();
        model.addAttribute("pagos", pagos);
        return "reporte";
    }
}

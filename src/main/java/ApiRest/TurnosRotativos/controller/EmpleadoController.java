package ApiRest.TurnosRotativos.controller;

import ApiRest.TurnosRotativos.dto.EmpleadoDTO;
import ApiRest.TurnosRotativos.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> getEmpelados() {
        List<EmpleadoDTO> empleados = this.empleadoService.getEmpleados();
        return ResponseEntity.ok(empleados);
    }
    @PostMapping
    public ResponseEntity<EmpleadoDTO> createEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        EmpleadoDTO empleado = this.empleadoService.createEmpleado(empleadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleado);
    }
}

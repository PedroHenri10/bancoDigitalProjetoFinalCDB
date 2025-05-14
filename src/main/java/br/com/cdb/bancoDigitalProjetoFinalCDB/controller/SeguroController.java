package br.com.cdb.bancoDigitalProjetoFinalCDB.controller;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Seguro;
import br.com.cdb.bancoDigitalProjetoFinalCDB.Service.SeguroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seguros")
public class SeguroController {

    @Autowired
    private SeguroService seguroService;

    @PostMapping("/contratar/viagem/{cartaoId}")
    public ResponseEntity<Seguro> contratarSeguroViagem(@PathVariable int cartaoId) {
        Seguro seguro = seguroService.contratarSeguroViagem(cartaoId);
        return ResponseEntity.ok(seguro);
    }

    @PostMapping("/contratar/fraude/{cartaoId}")
    public ResponseEntity<Seguro> contratarSeguroFraude(@PathVariable int cartaoId) {
        Seguro seguro = seguroService.contratarSeguroFraude(cartaoId);
        return ResponseEntity.ok(seguro);
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<Seguro> cancelarSeguro(@PathVariable Long id) {
        Seguro seguroCancelado = seguroService.cancelarSeguro(id);
        return ResponseEntity.ok(seguroCancelado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seguro> buscarSeguroPorId(@PathVariable Long id) {
        return ResponseEntity.ok(seguroService.buscarSeguroPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Seguro>> listarSegurosPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(seguroService.listarSegurosPorCliente(clienteId));
    }
}

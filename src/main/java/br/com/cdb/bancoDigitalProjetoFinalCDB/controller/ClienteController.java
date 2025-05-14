package br.com.cdb.bancoDigitalProjetoFinalCDB.controller;

import br.com.cdb.bancoDigitalProjetoFinalCDB.entity.Cliente;
import br.com.cdb.bancoDigitalProjetoFinalCDB.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@PostMapping
	public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente){
		return ResponseEntity.ok(clienteService.criarCliente(cliente));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id){
		return ResponseEntity.ok(clienteService.buscarClientePorId(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente){
		return ResponseEntity.ok(clienteService.atualizarCliente(id, cliente));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
        clienteService.removerCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodosClientes());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(clienteService.listarClientesPorNome(nome));
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<String> informacoes(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obterInformacoesBasicas(id));
    }
}


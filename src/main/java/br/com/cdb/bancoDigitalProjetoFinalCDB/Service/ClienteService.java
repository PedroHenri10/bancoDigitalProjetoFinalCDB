@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente criarCliente(Cliente cliente) {
        validarNome(cliente.getNome());
        validarCpf(cliente.getCpf());
        validarIdade(cliente.getDataNasc());
        validarEndereco(cliente.getRua(), cliente.getNumero(), cliente.getCep());

        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new DadosInvalidosException("CPF já cadastrado.");
        }

        cliente.setTipoCliente(TipoCliente.COMUM); // categoria inicial
        return clienteRepository.save(cliente);
    }

    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente com ID " + id + " não encontrado."));
    }

    public Cliente atualizarCliente(Long id, Cliente atualizado) {
        Cliente cliente = buscarClientePorId(id);

        validarNome(atualizado.getNome());
        validarCpf(atualizado.getCpf());
        validarIdade(atualizado.getDataNasc());
        validarEndereco(atualizado.getRua(), atualizado.getNumero(), atualizado.getCep());

        cliente.setNome(atualizado.getNome());
        cliente.setCpf(atualizado.getCpf());
        cliente.setDataNasc(atualizado.getDataNasc());
        cliente.setRua(atualizado.getRua());
        cliente.setNumero(atualizado.getNumero());
        cliente.setEstado(atualizado.getEstado());
        cliente.setCidade(atualizado.getCidade());
        cliente.setCep(atualizado.getCep());
        cliente.setSenha(atualizado.getSenha());

        return clienteRepository.save(cliente);
    }

    public void removerCliente(Long id) {
        Cliente cliente = buscarClientePorId(id);
        clienteRepository.delete(cliente);
    }

    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }

    public List<Cliente> listarClientesPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public String obterInformacoesBasicas(Long id) {
        Cliente cliente = buscarClientePorId(id);
        return "Nome: " + cliente.getNome() +
               " | CPF: " + cliente.getCpf() +
               " | Tipo: " + cliente.getTipoCliente();
    }

    public void atualizarCategoriaCliente(Cliente cliente, double saldo) {
        if (saldo >= 10000) {
            cliente.setTipoCliente(TipoCliente.PREMIUM);
        } else if (saldo >= 5000) {
            cliente.setTipoCliente(TipoCliente.SUPER);
        } else {
            cliente.setTipoCliente(TipoCliente.COMUM);
        }
        clienteRepository.save(cliente);
    }

    public void validarCpf(String cpf) {
        if (cpf == null || !Pattern.matches("\\d{11}", cpf) || !validarCPFAlgoritmo(cpf)) {
            throw new DadosInvalidosException("CPF inválido.");
        }
    }

    public void validarNome(String nome) {
        if (nome == null || nome.length() < 2 || nome.length() > 100 || !nome.matches("[A-Za-zÀ-ÿ ]+")) {
            throw new DadosInvalidosException("Nome inválido. Apenas letras e espaços (2 a 100 caracteres).");
        }
    }

    public void validarIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            throw new DadosInvalidosException("Data de nascimento não informada.");
        }
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        if (idade < 18) {
            throw new DadosInvalidosException("Cliente deve ter pelo menos 18 anos.");
        }
    }

    public void validarEndereco(String rua, int numero, String cep) {
        if (rua == null || rua.isBlank()) {
            throw new DadosInvalidosException("Rua não pode ser vazia.");
        }
        if (numero <= 0) {
            throw new DadosInvalidosException("Número inválido.");
        }
        if (!Pattern.matches("\\d{5}-\\d{3}", cep)) {
            throw new DadosInvalidosException("CEP inválido. Formato esperado: 00000-000.");
        }
    }

    private boolean validarCPFAlgoritmo(String cpf) {
        if (cpf.chars().distinct().count() == 1) return false;

        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 9; i++) {
            int digito = cpf.charAt(i) - '0';
            soma1 += digito * (10 - i);
            soma2 += digito * (11 - i);
        }
        int ver1 = 11 - (soma1 % 11);
        if (ver1 >= 10) ver1 = 0;
        soma2 += ver1 * 2;
        int ver2 = 11 - (soma2 % 11);
        if (ver2 >= 10) ver2 = 0;

        return ver1 == (cpf.charAt(9) - '0') && ver2 == (cpf.charAt(10) - '0');
    }
}

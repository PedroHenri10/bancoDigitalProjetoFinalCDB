package br.com.cdb.bancoDigitalProjetoFinalCDB.exception;

public class LimiteCartaoExcedidoException extends RuntimeException {
    public LimiteCartaoExcedidoException(String message) {
        super(message);
    }
}


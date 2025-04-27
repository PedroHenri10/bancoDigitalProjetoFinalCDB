package br.com.cdb.bancoDigitalProjetoFinalCDB.exception;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(String message)
    {
        super(message);
    }
}
